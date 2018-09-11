package com.hhly.cms.paymentmgr.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.paymentmgr.service.PaymentService;
import com.hhly.cms.utils.Authority;
import com.hhly.cmscore.cms.remote.service.ICmsCacheService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.cms.payment.vo.PayBankCardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @desc    用户银行卡管理
 * @author  Tony Wang
 * @date    2017年12月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RequestMapping("paymentmgr/bankcard")
@Controller
public class PaymentBankCardController extends BaseController {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private ICmsCacheService iCmsCacheService;
	
	@RequestMapping(value="page",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.ALL)
	@ResponseBody
	public PagingBO<PayBankCardVO> page(PayBankCardVO vo){
		Assert.notNull(vo.getUserId(),"查询银行卡信息时用户Id不能为空");
		vo.setSortField("status");
		vo.setSortOrder("desc");
		return paymentService.pageBankCard(vo);
	}

	@RequestMapping(method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.ALL)
	@ResponseBody
	public int update(PayBankCardVO vo){
		Assert.isTrue(vo.getId() != null || vo.getUserId() != null,"更新银行卡信息时必须传用户id或id");
		vo.setUpdateTime(new Date());
		int ret = paymentService.updateBankCard(vo);
		if(ret > 0) {
			iCmsCacheService.clearCache(CacheConstants.P_CORE_USER_BANK_CARD_LIST+vo.getUserId());
			iCmsCacheService.clearCache(CacheConstants.P_CORE_USER_PAY_CHANNEL+vo.getUserId()+"*");
		}
		return ret;
	}

	@RequestMapping(value = "s", method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.ALL)
	@ResponseBody
	public int batchUpdate(@RequestBody List<PayBankCardVO> vos){
		Date now = new Date();
		for(PayBankCardVO vo : vos) {
			Assert.isTrue(vo.getId() != null || vo.getUserId() != null,"更新银行卡信息时必须传用户id或id");
			vo.setUpdateTime(now);
		}
		int ret = paymentService.batchUpdateBankCard(vos);
		for(PayBankCardVO vo : vos) {
			Assert.isTrue(vo.getId() != null || vo.getUserId() != null,"更新银行卡信息时必须传用户id或id");
			vo.setUpdateTime(now);
		}
		if(ret > 0) {
			for(PayBankCardVO vo : vos) {
				iCmsCacheService.clearCache(CacheConstants.P_CORE_USER_BANK_CARD_LIST+vo.getUserId());
				iCmsCacheService.clearCache(CacheConstants.P_CORE_USER_PAY_CHANNEL+vo.getUserId()+"*");
			}
		}
		return ret;
	}

}
