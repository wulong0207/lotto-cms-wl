package com.hhly.cms.agent.controller;

import com.hhly.cms.agent.exceltransformer.AgentBankExcelHandler;
import com.hhly.cms.agent.service.AgentService;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.dicmgr.service.DictionaryService;
import com.hhly.cms.utils.Authority;
import com.hhly.pay.service.IAgentService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.TransEnum;
import com.hhly.skeleton.base.constants.PayConstants;
import com.hhly.skeleton.base.model.DicDataEnum;
import com.hhly.skeleton.cms.agent.vo.AgentTransTakenVO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.dicmgr.bo.DicDataDetailBO;
import com.hhly.skeleton.pay.agent.bo.AgentTransTakenBO;
import com.hhly.skeleton.pay.bo.TransTakenBO;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
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
 * 代理用户提款管理
 * @author wytong
 * @Version 1.0
 * @CreatDate 2017年1月3日 下午6:35:16
 */
@Controller
@RequestMapping(value="agent/taken")
public class AgentTakenController extends BaseController {

	@Autowired	
	private AgentService agentService;
	@Autowired	
	private DictionaryService dictionaryService;
	@Autowired
	private IAgentService payAgentService;
	@Autowired	
	private AgentBankExcelHandler agentBankExcelHandler;
	
	private static final String UPLOAD_TAKEN_EXCEL = "_agent/taken/excel";
	
	private static Logger logger = LogManager.getLogger(AgentTakenController.class);

	@RequestMapping()
	@Authority(privilege= AuthEnum.SEARCH)
	public String index(){
		return "agent/taken";
	}
	
	@RequestMapping(value = "/page")
	@Authority(privilege= AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<AgentTransTakenVO> page(AgentTransTakenVO vo){
		return agentService.pageTaken(vo);
	}

	@RequestMapping(value = "/check", method= RequestMethod.PUT)
	@Authority(privilege={AuthEnum.UPD, AuthEnum.CHECK_TAKEN})
	@ResponseBody
	public ResultBO<?> check(@RequestBody List<AgentTransTakenBO> takens, HttpSession session) throws Exception{
		// 设置操作人员昵称
		Assert.notEmpty(takens, "请至少选择一行记录");
		String operator = getUserCName(session);
		takens.stream().forEach(taken -> {
			taken.setReviewBy(operator);
		});
		return payAgentService.updateTakenStatusByBatch(takens, PayConstants.TakenOperateTypeEnum.AUDIT.getKey());
	}

	@RequestMapping(value = "/preUpload")
	@Authority(privilege = AuthEnum.UPLOAD)
	@ResponseBody
	public Object preUpload(@RequestParam MultipartFile file, HttpServletRequest request)
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
			return agentBankExcelHandler.transform(bankId, file);
		} catch (Exception e) {
			logger.error(e);
			ResultBO<?> ret = ResultBO.err();
			ret.setMessage(e.getMessage());
			return String.valueOf(JSONObject.fromObject(ret));
		}
	}

	@RequestMapping(value = "/process")
	@Authority(privilege={AuthEnum.PROCESS_TAKEN})
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
			List<TransTakenBO> takens = agentBankExcelHandler.getTransTakens(bankId, file);
			FileUtils.copyInputStreamToFile(file.getInputStream(), excelFile);
			List<AgentTransTakenBO> agnetTakens = new ArrayList<>();
			for(TransTakenBO bo : takens) {
				AgentTransTakenBO agnetTaken = new AgentTransTakenBO();
				BeanUtils.copyProperties(bo,agnetTaken);
				agnetTakens.add(agnetTaken);
			}
			ret = payAgentService.updateTakenStatusByBatch(agnetTakens, PayConstants.TakenOperateTypeEnum.BANK_COMPLETE.getKey());
			return String.valueOf(JSONObject.fromObject(ret));
		} catch (Exception e) {
			logger.error(e);
			ret = ResultBO.err();
			ret.setMessage(e.getMessage());
			return String.valueOf(JSONObject.fromObject(ret));
		}
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
	public List<AgentTransTakenVO> findTrans(AgentTransTakenVO vo) {
		return agentService.findTaken(vo);
	}

	@RequestMapping(value = "/alreadyProcess", method=RequestMethod.PUT)
	@Authority(privilege={AuthEnum.UPD, AuthEnum.ALREADY_PROCESS_TAKEN})
	@ResponseBody
	public ResultBO<?> alreadyProcess(@RequestBody List<AgentTransTakenBO>[] takens, HttpSession session) throws Exception{
		// 设置操作人员昵称
		List<AgentTransTakenBO> successList = takens[0];
		List<AgentTransTakenBO> failList = takens[1];
		Assert.isTrue(!CollectionUtils.isEmpty(successList) || !CollectionUtils.isEmpty(failList), "要处理的流水记录不能都为空");
		ResultBO<?> ret = ResultBO.err();
		if(!CollectionUtils.isEmpty(successList)) {
			ret = payAgentService.updateTakenStatusByBatch(successList, PayConstants.TakenOperateTypeEnum.CMS_COMPLETE.getKey());
			if(ret.isError()) return ret;
		}
		if(!CollectionUtils.isEmpty(failList)) {
			ret = payAgentService.updateTakenStatusByBatch(failList, PayConstants.TakenOperateTypeEnum.SUCCESS_TO_FAIL.getKey());
		}
		return ret;
	}

	@RequestMapping("excel")
	@Authority(privilege = AuthEnum.EXPORT)
	public void exportOpExcel(HttpServletResponse response, AgentTransTakenVO vo) throws IOException{
		excel("agent_withdraw", agentService.getTakenExcel(vo), response);
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
	public void exportBankExcel(HttpServletResponse response,AgentTransTakenVO vo) throws IOException{
		// 只导出审核通过的记录
		vo.setTransStatus(TransEnum.TakenStatus.PASS.getValue());
		List<DicDataDetailBO> dicDetails = dictionaryService.findDetail(new StringVO(DicDataEnum.TRANS_TAKEN_EXCEL_BANK.getDicCode()));
		Assert.isTrue(!CollectionUtils.isEmpty(dicDetails), "没有可导出的银行，请检查字典配置");
		// 提取字典的value字段
		List<Integer> allExcelBanks = new ArrayList<>();
		for(DicDataDetailBO bank : dicDetails) {
			allExcelBanks.add(Integer.parseInt(bank.getDicDataValue()));
		}
		Assert.isTrue(allExcelBanks.contains(vo.getExcelBank()), "不支持导出此银行模板");
		excel("bank", agentService.getTakenBankExcel(vo), response);
	}
}
