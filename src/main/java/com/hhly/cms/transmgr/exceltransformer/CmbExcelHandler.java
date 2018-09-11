package com.hhly.cms.transmgr.exceltransformer;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CmbExcelHandler extends AbstractBankExcelHandler {
	
	private static final List<String> HEADERS = Arrays.asList("支票编号","交易状态","失败原因","付款账号","金额上限","交易日期","交易时间","收方户名","收方账号","收方行名","支付汇路","收方行地址","用途/附言","系统流水编号");
	
	@Override
	public void validateCustom(String bankId) {}
	
	@Override
	public List<String> getHeaders() {
		return HEADERS;
	}

	@Override
	public int getTransTakenCodeIndex() {
		return 13;
	}

	@Override
	public int getTransStatusIndex() {
		return 1;
	}

	@Override
	public int getRealAmountIndex() {
		return 4;
	}

	@Override
	public int getBankAccountIndex() {
		return 8;
	}

	@Override
	public int getBankUserIndex() {
		return 7;
	}

	@Override
	public int getFailReasonIndex() {
		return 2;
	}
}
