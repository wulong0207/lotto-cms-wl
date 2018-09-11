package com.hhly.cms.transmgr.exceltransformer;

import com.hhly.skeleton.base.exception.ServiceRuntimeException;
import com.hhly.skeleton.pay.bo.TransTakenBO;
import net.sf.json.JSONArray;

import java.util.List;

public class BankExcelAdapter extends AbstractBankExcelHandler {

	@Override
	public void validateCustom(String bankId) {
		throw new ServiceRuntimeException("请重写！");
	}

	@Override
	public List<JSONArray> customTransform(String bankId) {
		throw new ServiceRuntimeException("请重写！");
	}

	@Override
	public List<TransTakenBO> getCustomTransTakens(String bankId) {
		throw new ServiceRuntimeException("请重写！");
	}

	@Override
	public List<String> getHeaders() {
		throw new ServiceRuntimeException("请重写！");
	}

	@Override
	public int getTransTakenCodeIndex() {
		throw new ServiceRuntimeException("请重写！");
	}

	@Override
	public int getTransStatusIndex() {
		throw new ServiceRuntimeException("请重写！");
	}

	@Override
	public int getRealAmountIndex() {
		throw new ServiceRuntimeException("请重写！");
	}

	@Override
	public int getBankAccountIndex() {
		throw new ServiceRuntimeException("请重写！");
	}

	@Override
	public int getBankUserIndex() {
		throw new ServiceRuntimeException("请重写！");
	}

	@Override
	public int getFailReasonIndex() {
		throw new ServiceRuntimeException("请重写！");
	}
}
