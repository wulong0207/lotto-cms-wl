package com.hhly.cms.sysmgr.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.sysmgr.service.PayBankSegmentService;
import com.hhly.cms.utils.Authority;
import com.hhly.cmscore.cms.remote.service.ICmsCacheService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.cms.sysmgr.vo.PayBankSegmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @desc    银行卡BIN码管理
 * @author  Tony Wang
 * @date    2017年12月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RequestMapping("sysmgr/banksegment")
@Controller
public class PayBankSegmentController extends BaseController {

	@Autowired
	private PayBankSegmentService payBankSegmentService;

	@Autowired
	private ICmsCacheService iCmsCacheService;



	@RequestMapping(method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "sysmgr/pay_bank_segment";
	}

	@RequestMapping(value="page",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<PayBankSegmentVO> page(PayBankSegmentVO vo){
		vo.setSortField("id");
		vo.setSortOrder("desc");
		return payBankSegmentService.page(vo);
	}

	@RequestMapping(method={RequestMethod.PUT,RequestMethod.POST})
	@Authority(privilege={AuthEnum.ADD,AuthEnum.UPD})
	@ResponseBody
	public ResultBO<?> merge(PayBankSegmentVO vo, HttpSession session){
		String realName = getUserRealName(session);
		if(vo.getId() == null) {
			vo.setCreateBy(realName);
			vo.setCreateTime(new Date());
		} else {
			vo.setModifyBy(realName);
			vo.setModifyTime(new Date());
		}
		//
		vo.setTopCut2(vo.getTopCut());
		vo.setTopCutLength2(vo.getTopCutLength());
		int ret = payBankSegmentService.merge(vo);
		// 如果执行成功则清除相关缓存
		if(ret > 0) {
			iCmsCacheService.clearCache(CacheConstants.PAY_BANK_SEGMENTBO_LIST_KEY);
			iCmsCacheService.clearCache(CacheConstants.P_CORE_PAY_BANK_CARD_SEGMENT_LIST);
		}
		// P_CORE_PAY_BANK_CARD_SEGMENT_LIST
		// 用户银行卡列表USER_BANK_CARD_LIST_+userId  P_CORE_USER_BANK_CARD_LIST
		return getSaveResult(ret);
	}
}
