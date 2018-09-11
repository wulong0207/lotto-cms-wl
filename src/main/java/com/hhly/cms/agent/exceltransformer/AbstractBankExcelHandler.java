package com.hhly.cms.agent.exceltransformer;

import com.hhly.cms.agent.service.AgentService;
import com.hhly.cms.transmgr.exceltransformer.IBankExcelHandler;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.TransEnum.TakenStatus;
import com.hhly.skeleton.base.util.ExcelUtil;
import com.hhly.skeleton.cms.agent.vo.AgentTransTakenVO;
import com.hhly.skeleton.pay.bo.TransTakenBO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractBankExcelHandler implements IBankExcelHandler {

	@Autowired	
	private AgentService agentService;
	
	private List<List<String>> excels;
	
	@Override
	public String transform(String bankId, MultipartFile multipartFile) {
		try {
			setExcels(multipartFile);
			validateCommon(bankId);
			// 显示excel内容给页面时，不验证流水的合法性
			validateCustom(bankId);
			return String.valueOf(JSONObject.fromObject(ResultBO.ok(customTransform(bankId))));
		} catch (Exception e) {
			ResultBO<?> ret = ResultBO.err();
			ret.setMessage(e.getMessage());
			return String.valueOf(JSONObject.fromObject(ret)); 
		}
	}

	@Override
	public List<TransTakenBO> getTransTakens(String bankId, MultipartFile multipartFile) {
		setExcels(multipartFile);
		validateCommon(bankId);
		validateCustom(bankId);
		validateTakenTrans(bankId);
		return getCustomTransTakens(bankId);
	}

	@Override
	public List<JSONArray> customTransform(String bankId) {
		List<JSONArray> rows = new ArrayList<>();
		for(List<String> excelRow : getExcels()) {
			JSONArray row = new JSONArray();
			// 页面展示  系统流水  交易状态 失败原因   收方户名   金额上限
			row.add(excelRow.get(getTransTakenCodeIndex()));
			row.add(excelRow.get(getTransStatusIndex()));
			row.add(excelRow.get(getFailReasonIndex()));
			row.add(excelRow.get(getBankUserIndex()));
			row.add(excelRow.get(getRealAmountIndex()));
			rows.add(row);
		}
		return rows;
	}

	@Override
	public List<TransTakenBO> getCustomTransTakens(String bankId) {
		List<List<String>> excels = getExcels();
		List<TransTakenBO> transTakens = new ArrayList<>();
		// 下标为0的元素是表头
		TransTakenBO taken;
		for(int i=1 ; i < excels.size() ; i++) {
			List<String> row = excels.get(i);
			taken = new TransTakenBO();
			taken.setTransTakenCode(row.get(getTransTakenCodeIndex()).trim());
			taken.setTransFailInfo(row.get(getFailReasonIndex()).trim());
			if(row.get(getTransStatusIndex()).indexOf("成功")>-1) {
				taken.setTransStatus((short) TakenStatus.BANK_SUCCESS.getValue());
			} else {
				taken.setTransStatus((short) TakenStatus.BANK_FAIL.getValue());
			}
			transTakens.add(taken);
		}
		return transTakens;
	}
	
	@Override
	public final void validateCommon(String bankId) {
		List<List<String>> excels = getExcels();
		// 要手动把返回值转化为string,否则前端的$.ajaxFileUpload方法会按错误处理
		List<String> headRow;
		Assert.isTrue(!CollectionUtils.isEmpty(excels),"excel的第一个工作表内容为空");
		Assert.isTrue(excels.size()>1, "excel文件只有表头行，没有其他数据");
		List<String> headers = getHeaders();
		for(List<String> row : excels) {
			Assert.isTrue(row.size()==headers.size(), "存在列数与表头列数不一致的行");
		}
		// 表头行
		headRow = excels.get(0);
		// 验证表头内容
		for(int i = 0 ; i < headRow.size() ; i++) {
			Assert.isTrue(Objects.equals(headRow.get(i), headers.get(i)),String.format("表头:%s列应为:%s", headRow.get(i), headers.get(i)));
		}
		for(int i = 1 ; i < excels.size(); i++) {
			// 验证"交易状态"必须包含成功或失败字眼
			List<String> row = excels.get(i);
			Assert.isTrue(row.get(getTransStatusIndex()).indexOf("成功")>-1 || row.get(getTransStatusIndex()).indexOf("失败")>-1,String.format("第%d,第%d列(流水号)的'交易状态'为'%s',必须包含成功或失败", i+1,getTransTakenCodeIndex()+1,row.get(getTransStatusIndex())));
			// 验证流水号不为空
			Assert.isTrue(StringUtils.hasText(row.get(getTransTakenCodeIndex())),String.format("第%d,第%d列(流水号)的数据为空", i+1,getTransTakenCodeIndex()+1));
			// 验证收方账号不为空
			// 2017-12-19 运营人员说：原路返回时，当是退款到微信或支付宝，则只有银行名称，没有账号，故可为空
			// Assert.isTrue(StringUtils.hasText(row.get(getBankAccountIndex())),String.format("第%d,第%d列(收方账号)的数据为空", i+1,getBankAccountIndex()+1));
			// 收方户名不为空
			Assert.isTrue(StringUtils.hasText(row.get(getBankUserIndex())),String.format("第%d,第%d列(收方户名)的数据为空", i+1,getBankUserIndex()+1));
			// 验证到账金额不为空
			Assert.isTrue(StringUtils.hasText(row.get(getRealAmountIndex())),String.format("第%d,第%d列(金额)的数据为空", i+1,getRealAmountIndex()+1));
		}
	}
	
	@Override
	public void validateTakenTrans(String bankId) {
		// TODO　尽可能多地验证交易信息
		for(int i = 1 ; i < excels.size(); i++) {
			/*
			 * 在处理前，excel表的记录都是审核通过
			 * 检查流水
			 */
			List<String> row = excels.get(i);

			String transTakenCode = row.get(getTransTakenCodeIndex()).trim();
			// 查询用户提款表中对应的记录是否有银行账号
			AgentTransTakenVO vo2 = new AgentTransTakenVO();
			vo2.setTransTakenCode(transTakenCode);
			int count = agentService.countTaken(vo2);
			Assert.isTrue(count == 1, String.format("不存在流水号为:%s的提款记录", transTakenCode));
			String bankAccount = row.get(getBankAccountIndex()).trim();
			AgentTransTakenVO vo = new AgentTransTakenVO();
			vo.setBankCardNum(bankAccount);
			BigDecimal realAmount =  new BigDecimal(row.get(getRealAmountIndex()).replace(",","").trim());
			String createBy = row.get(getBankUserIndex()).trim();
			vo.setTransTakenCode(transTakenCode);
			// excel表的金额是本站的到账金额(提款金额-手续费)
			vo.setRealAmount(realAmount);
			vo.setTransStatus(TakenStatus.PASS.getValue());
			//vo.setCreateBy(createBy);
			count = agentService.countTaken(vo);
			Assert.isTrue(count==1, String.format("不存在流水号为:%s，到账金额为：%f,收方账号为%s，收方户名为%s，状态为'审核通过'的提款记录", transTakenCode,realAmount,bankAccount,createBy));
		}
		
	}

	protected final List<List<String>> getExcels() {
		return excels;
	}
	
	private final void setExcels(MultipartFile multipartFile) {
		this.excels = ExcelUtil.readExcel(multipartFile);
	}
}
