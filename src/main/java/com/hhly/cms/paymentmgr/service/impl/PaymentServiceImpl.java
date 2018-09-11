package com.hhly.cms.paymentmgr.service.impl;

import com.hhly.cms.base.service.FileProxy;
import com.hhly.cms.paymentmgr.service.PaymentService;
import com.hhly.cmscore.cms.remote.service.IPaymentMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.payment.bo.*;
import com.hhly.skeleton.cms.payment.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @desc 支付管理接口实现类
 * @author tangxiaobo
 * @date 2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class PaymentServiceImpl implements PaymentService{
	@Autowired
	private IPaymentMgrService iPaymentMgrService;

	@Autowired
	protected FileProxy uploadFile;
	
	@Override
	public List<PayBankBO> findBank(PayBankVO vo) {
		List<PayBankBO> banks = iPaymentMgrService.findBank(vo);
		if(!CollectionUtils.isEmpty(banks)) {
			for(PayBankBO bank : banks) {
				bank.setBlogo(uploadFile.getUrl() + bank.getBlogo());
				bank.setSlogo(uploadFile.getUrl() + bank.getSlogo());
			}
		}
		return banks;
	}

	@Override
	public List<PayChannelBO> findChannel(PayChannelVO vo) {
		return iPaymentMgrService.findChannel(vo);
	}

	@Override
	public int addBank(PayBankVO vo) {
		return iPaymentMgrService.addBank(vo);
	}

	@Override
	public PayBankBO findPayBankById(Integer id) {
		PayBankBO bank = iPaymentMgrService.findPayBankById(id);
		if(bank != null) {
			bank.setBlogo(uploadFile.getUrl() + bank.getBlogo());
			bank.setSlogo(uploadFile.getUrl() + bank.getSlogo());
		}
		return bank;
	}

	@Override
	public int updateBankById(PayBankVO vo) {
		return iPaymentMgrService.updateBankById(vo);
	}

	@Override
	public int saveOrUpdateChannelList(List<PayChannelVO> list) {
		
		return iPaymentMgrService.saveOrUpdateChannelList(list);
	}

	@Override
	public List<PayBankLimitBO> findBankLimit(Integer bankId) {
		return iPaymentMgrService.findBankLimit(bankId);
	}

	@Override
	public int saveOrUpdateBankLimitList(List<PayBankLimitVO> list) {
		return iPaymentMgrService.saveOrUpdateBankLimitList(list);
	}

	@Override
	public int updateBankList(List<PayBankVO> list) {
		return iPaymentMgrService.updateBankList(list);
	}

	/**
	 * @desc   删除支付渠道
	 * @author Tony Wang
	 * @create 2017年6月27日
	 * @param vo
	 * @return 
	 */
	@Override
	public int deleteChannel(PayChannelVO vo) {
		return iPaymentMgrService.deleteChannel(vo);
	}

	/**
	 * @desc   删除付款金额超限说明
	 * @author Tony Wang
	 * @create 2017年6月27日
	 * @param vo
	 * @return 
	 */
	@Override
	public int deleteBankLimit(PayBankLimitVO vo) {
		return iPaymentMgrService.deleteBankLimit(vo);
	}

	@Override
	public PagingBO<PayChannelMgrBO> pageChannelMgrInfo(PayChannelMgrVO vo) {
		return iPaymentMgrService.pageChannelMgrInfo(vo);
	}

	@Override
	public PagingBO<PayChannelLimitBO> pageChannelLimitInfo(PayChannelLimitVO vo) {
		return iPaymentMgrService.pageChannelLimitInfo(vo);
	}

	@Override
	public int mergeChannelMgrInfo(PayChannelMgrVO vo) {
		return iPaymentMgrService.mergeChannelMgrInfo(vo);
	}

	@Override
	public List<PayChannelMgrBO> findChannelMgrInfo(PayChannelMgrVO vo) {
		return iPaymentMgrService.findChannelMgrInfo(vo);
	}

	@Override
	public List<PayChannelMgrExcelBO> findChannelMgrExcelInfo(PayChannelMgrVO vo) {
		return iPaymentMgrService.findChannelMgrExcelInfo(vo);
	}
	
	@Override
	public PagingBO<PayChannelBO> pageChannelBankInfo(PayChannelVO vo) {
		return iPaymentMgrService.pageChannelBankInfo(vo);
	}

	@Override
	public int batchMergeChannelBankInfo(List<PayChannelVO> vos) {
		return iPaymentMgrService.batchMergeChannelBankInfo(vos);
	}

	@Override
	public int batchMergeChannelLimitInfo(List<PayChannelLimitVO> vos) {
		return iPaymentMgrService.batchMergeChannelLimitInfo(vos);
	}

	@Override
	public int deleteChannelLimitInfo(PayChannelLimitVO vo) {
		return iPaymentMgrService.deleteChannelLimitInfo(vo);
	}

	@Override
	public PagingBO<PayBankCardVO> pageBankCard(PayBankCardVO vo) {
		return iPaymentMgrService.pageBankCard(vo);
	}

	@Override
	public int updateBankCard(PayBankCardVO vo) {
		return iPaymentMgrService.updateBankCard(vo);
	}

	@Override
	public int batchUpdateBankCard(List<PayBankCardVO> vos) {
		return iPaymentMgrService.batchUpdateBankCard(vos);
	}
}
