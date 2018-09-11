package com.hhly.cms.transmgr.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.dicmgr.service.DictionaryService;
import com.hhly.cms.transmgr.exceltransformer.BankExcelHandler;
import com.hhly.cms.transmgr.service.TransMgrService;
import com.hhly.cms.utils.Authority;
import com.hhly.pay.service.TransTakenConfirmService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.TransEnum.TakenStatus;
import com.hhly.skeleton.base.constants.PayConstants.TakenOperateTypeEnum;
import com.hhly.skeleton.base.model.DicDataEnum;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.dicmgr.bo.DicDataDetailBO;
import com.hhly.skeleton.cms.transmgr.bo.TransTakenBO;
import com.hhly.skeleton.cms.transmgr.vo.TransTakenVO;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 红包流水管理
 * @author wytong
 * @Version 1.0
 * @CreatDate 2017年1月3日 下午6:35:16
 */
@Controller
@RequestMapping(value="/transmgr/taken")
public class TransTakenController extends BaseController {

	@Autowired	
	private TransMgrService transMgrService;
	@Autowired	
	private DictionaryService dictionaryService;
	@Autowired	
	private TransTakenConfirmService transTakenConfirmService;
	@Autowired	
	private BankExcelHandler bankExcelHandler;
	
	private static final String UPLOAD_TAKEN_EXCEL = "_transmgr/taken/excel";
	
	private static Logger logger = LogManager.getLogger(TransTakenController.class);

	@RequestMapping()
	@Authority(privilege= AuthEnum.SEARCH)
	public String index(){
		return "transmgr/trans_taken";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege= AuthEnum.SEARCH)
	public @ResponseBody Object list(TransTakenVO vo){
		return transMgrService.findTakenTrans(vo);
	}
	
	/**
	 * @desc   查询提款申请记录
	 * @author Tony Wang
	 * @create 2017年8月4日
	 * @param vo
	 * @return 
	 */
	@RequestMapping(value = "/trans", method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public List<TransTakenBO> findTrans(TransTakenVO vo) {
		return transMgrService.findTakenTransNoPaging(vo);
	}
	
	@RequestMapping("excel")
	@Authority(privilege = AuthEnum.EXPORT)
	public void exportOpExcel(HttpServletResponse response,TransTakenVO vo) throws IOException{
		excel("withdraw", transMgrService.getTransTakenExcel(vo), response);
	}
	
	/**
	 * @desc   导出银行对私excel
	 * @author Tony Wang
	 * @create 2017年8月4日
	 * @param response
	 * @param vo
	 * @throws IOException 
	 */
	@RequestMapping("bank/excel")
	@Authority(privilege = AuthEnum.EXPORT)
	public void exportBankExcel(HttpServletResponse response,TransTakenVO vo) throws IOException{
		//List<Integer> allExcelBanks = Arrays.asList(1);
		//Integer excelBank = vo.getExcelBank();
		//Assert.isTrue(allExcelBanks.contains(excelBank), "暂不支持导出该银行模板");
		//vo = new TransTakenVO();
		// 只导出审核通过的记录
		vo.setTransStatus(TakenStatus.PASS.getValue());
		List<DicDataDetailBO> dicDetails = dictionaryService.findDetail(new StringVO(DicDataEnum.TRANS_TAKEN_EXCEL_BANK.getDicCode()));
		Assert.isTrue(!CollectionUtils.isEmpty(dicDetails), "没有可导出的银行，请检查字典配置");
		// 提取字典的value字段
		List<Integer> allExcelBanks = new ArrayList<>();
		for(DicDataDetailBO bank : dicDetails) {
			allExcelBanks.add(Integer.parseInt(bank.getDicDataValue()));
		}
		Assert.isTrue(allExcelBanks.contains(vo.getExcelBank()), "不支持导出此银行模板");
		excel("bank", transMgrService.getTransTakenBankExcel(vo), response);
	}
	
	@RequestMapping(value = "/check", method=RequestMethod.PUT)
	@Authority(privilege={AuthEnum.UPD, AuthEnum.CHECK_TAKEN})
	@ResponseBody
	public ResultBO<?> check(@RequestBody List<com.hhly.skeleton.pay.bo.TransTakenBO> takens, HttpSession session) throws Exception{
		// 设置操作人员昵称
		Assert.notEmpty(takens, "请至少选择一行记录");
		String operator = getUserCName(session);
		for(com.hhly.skeleton.pay.bo.TransTakenBO taken : takens) {
			taken.setReviewBy(operator);
		}
		return transTakenConfirmService.updateTakenStatusByBatch(takens, TakenOperateTypeEnum.AUDIT.getKey());
	}
	
	@RequestMapping(value = "/process")
	@Authority(privilege={AuthEnum.UPD, AuthEnum.PROCESS_TAKEN, AuthEnum.UPLOAD})
	@ResponseBody
	public Object process(@RequestParam MultipartFile file,HttpServletRequest request, HttpSession session) throws Exception{
		// 设置操作人员昵称
		// 要手动转化为string,否则前端的$.ajaxFileUpload方法会按错误处理
		String bankId = request.getParameter("bankId");
		ResultBO<?> ret;
		try {
			Assert.notNull(null != file, "请选择一个Excel文件");
			Assert.isTrue(StringUtils.hasText(bankId), "请选择一个上传银行模板");
			String originalFilename=file.getOriginalFilename();  
			Assert.isTrue(originalFilename.endsWith(".xls") || originalFilename.endsWith(".xlsx"), "请上传excel文件");
			// 以银行编号区分文件夹
			String path=beforeFileDir+UPLOAD_TAKEN_EXCEL+"/"+bankId;
			File excelFile = new File(path,originalFilename);
			// 检查文件是否已存在
			Assert.isTrue(!excelFile.exists(), String.format("文件'%s'已存在，请重命名后再上传", originalFilename));
			List<com.hhly.skeleton.pay.bo.TransTakenBO> takens = bankExcelHandler.getTransTakens(bankId, file);
			FileUtils.copyInputStreamToFile(file.getInputStream(), excelFile);
			ret = transTakenConfirmService.updateTakenStatusByBatch(takens, TakenOperateTypeEnum.BANK_COMPLETE.getKey());
			return String.valueOf(JSONObject.fromObject(ret)); 
		} catch (Exception e) {
			logger.error(e);
			ret = ResultBO.err();
			ret.setMessage(e.getMessage());
			return String.valueOf(JSONObject.fromObject(ret)); 
		}
	}
	
	@RequestMapping(value = "/preUpload")
	@Authority(privilege = AuthEnum.UPLOAD)
	@ResponseBody
	public Object preUpload(@RequestParam MultipartFile file,HttpServletRequest request)
			throws IllegalStateException, IOException {
		// 要手动转化为string,否则前端的$.ajaxFileUpload方法会按错误处理
		String bankId = request.getParameter("bankId");
		try {
			Assert.notNull(null != file, "请选择一个Excel文件");
			Assert.isTrue(StringUtils.hasText(bankId), "请选择一个上传银行模板");
			String originalFilename=file.getOriginalFilename();  
			Assert.isTrue(originalFilename.endsWith(".xls") || originalFilename.endsWith(".xlsx"), "请上传excel文件");
			// 根据银行，验证excel格式并按页需要的格式输出
			// TODO 只能上传只有一个工作表的excel，若有多个，也只处理第一个工作表
			return bankExcelHandler.transform(bankId, file);
		} catch (Exception e) {
			logger.error(e);
			ResultBO<?> ret = ResultBO.err();
			ret.setMessage(e.getMessage());
			return String.valueOf(JSONObject.fromObject(ret)); 
		}
	}
	
//	@RequestMapping(value = "/process", method=RequestMethod.PUT)
//	@Authority(privilege={AuthEnum.UPD,AuthEnum.PROCESS_TAKEN})
//	@ResponseBody
//	public ResultBO<?> process(@RequestBody List<com.hhly.skeleton.pay.bo.TransTakenBO> takens, HttpSession session) throws Exception{
//		// 设置操作人员昵称
//		return transTakenConfirmService.updateTakenStatusByBatch(takens, TakenOperateTypeEnum.BANK_COMPLETE.getKey());
//	}
	
	@RequestMapping(value = "/alreadyProcess", method=RequestMethod.PUT)
	@Authority(privilege={AuthEnum.UPD, AuthEnum.ALREADY_PROCESS_TAKEN})
	@ResponseBody
	public ResultBO<?> alreadyProcess(@RequestBody List<com.hhly.skeleton.pay.bo.TransTakenBO>[] takens, HttpSession session) throws Exception{
		// 设置操作人员昵称
		List<com.hhly.skeleton.pay.bo.TransTakenBO> successList = takens[0];
		List<com.hhly.skeleton.pay.bo.TransTakenBO> failList = takens[1];
		Assert.isTrue(!CollectionUtils.isEmpty(successList) || !CollectionUtils.isEmpty(failList), "要处理的流水记录不能都为空");
		ResultBO<?> ret = ResultBO.err();
		if(!CollectionUtils.isEmpty(successList)) {
			ret = transTakenConfirmService.updateTakenStatusByBatch(successList, TakenOperateTypeEnum.CMS_COMPLETE.getKey());
			if(ret.isError()) return ret;
		}
		if(!CollectionUtils.isEmpty(failList)) {
			ret = transTakenConfirmService.updateTakenStatusByBatch(failList, TakenOperateTypeEnum.SUCCESS_TO_FAIL.getKey());
		}
		return ret;
	}
	
//	@RequestMapping(value = "/upload")
//	@Authority(privilege = AuthEnum.UPLOAD)
//	@ResponseBody
//	public Object upload(@RequestParam MultipartFile file,HttpServletRequest request)
//			throws IllegalStateException, IOException {
//		// 要手动转化为string,否则前端的$.ajaxFileUpload方法会按错误处理
//		String bankId = request.getParameter("bankId");
//		try {
//			Assert.notNull(null != file, "请选择一个Excel文件");
//			Assert.isTrue(StringUtils.hasText(bankId), "请选择一个上传银行模板");
//			String originalFilename=file.getOriginalFilename();  
//			Assert.isTrue(originalFilename.endsWith(".xls") || originalFilename.endsWith(".xlsx"), "请上传excel文件");
//		} catch (Exception e) {
//			ResultBO<?> ret = ResultBO.err();
//			ret.setMessage(e.getMessage());
//			return String.valueOf(JSONObject.fromObject(ret)); 
//		}
//		// TODO 相同名字的excel文件如何处理？上传到本地
//		try {
//			uploadFile(file, UPLOAD_TAKEN_EXCEL);
//		} catch (Exception e) {
//			logger.error(e);
//			return ResultBO.err();
//		}
//		return ResultBO.ok();
//	}
//	
//	@RequestMapping(value = "/status", method=RequestMethod.PUT)
//	@Authority(privilege={AuthEnum.UPD,AuthEnum.CHECK_TAKEN})
//	@ResponseBody
//	public ResultBO<?> updateStatus(@RequestBody List<com.hhly.skeleton.pay.bo.TransTakenBO> takens, HttpSession session) throws Exception{
//		// 设置操作人员昵称
//		Assert.notEmpty(takens, "请至少选择一行记录");
//		String operator = getUserCName(session);
//		for(TransTakenVO vo : vos) {
//			vo.setOperator(operator);
//		}
//		return transTakenConfirmService.updateTakenStatusByBatch(takens, TakenOperateTypeEnum.BANK_COMPLETE.getKey());
//	}
//	
//	/**
//	 * @desc   查询批次号
//	 * @author Tony Wang
//	 * @create 2017年8月4日
//	 * @param vo
//	 * @return 
//	 */
//	@RequestMapping(value = "batchNums", method=RequestMethod.GET)
//	@Authority(privilege =AuthEnum.SEARCH)
//	@ResponseBody
//	public List<String> batchNums(TransTakenVO vo) {
//		vo = new TransTakenVO();
//		// 只导出审核通过的批次号
//		vo.setTransStatus(TakenStatus.PASS.getValue());
//		return transMgrService.findTransTakenBatchNums(vo);
//	}
}
