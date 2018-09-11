package com.hhly.cms.agent.exceltransformer;

import com.hhly.cms.transmgr.exceltransformer.BankExcelAdapter;
import com.hhly.cms.transmgr.exceltransformer.IBankExcelHandler;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.exception.ServiceRuntimeException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class AgentBankExcelHandler extends BankExcelAdapter {

	@Autowired
	private IBankExcelHandler agentCmbExcelHandler;
	
	/**
	 * @desc   
	 * @author Tony Wang
	 * @create 2017年11月21日
	 * @param bankId 对应字典"0917"
	 * @param multipartFile
	 * @return 
	 */
	@Override
	public String transform(String bankId, MultipartFile multipartFile) {
		switch (bankId) {
		case "1":
			return agentCmbExcelHandler.transform(bankId, multipartFile);
		// 其他银行暂时禁用
		default:
			return String.valueOf(JSONObject.fromObject (ResultBO.err("不存在该银行模板:"+bankId)));
		}
	}
	
	@Override
	public List<com.hhly.skeleton.pay.bo.TransTakenBO> getTransTakens(String bankId, MultipartFile multipartFile) {
		switch (bankId) {
		case "1":
			return agentCmbExcelHandler.getTransTakens(bankId, multipartFile);
		// 其他银行暂时禁用
		default:
			throw new ServiceRuntimeException("不存在该银行模板:"+bankId);
		}
	}
	

}
