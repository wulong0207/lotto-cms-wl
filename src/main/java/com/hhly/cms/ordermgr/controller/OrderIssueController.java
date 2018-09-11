package com.hhly.cms.ordermgr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.ordermgr.service.OrderIssueService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderIssueBO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderIssueVO;

/**
 * @desc    发单管理
 * @author  Tony Wang
 * @date    2017年2月7日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping("/ordermgr/issue")
public class OrderIssueController extends BaseController {
	
	@Autowired
	private OrderIssueService orderIssueService;

	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "ordermgr/order_issue";
	}
	
	@RequestMapping("list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<OrderIssueBO> list(OrderIssueVO vo){
		return orderIssueService.page(vo);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public ResultBO<?> update(OrderIssueVO vo,HttpSession session){
		OrderIssueVO target = new OrderIssueVO();
		// 只更新'是否置顶'、'是否推荐'、操作信息
		target.setId(vo.getId());
		target.setIsRecommend(vo.getIsRecommend());
		target.setIsTop(vo.getIsTop());
		target.setModifyBy(getUserRealName(session));
		target.setRemark(vo.getRemark());
		int ret = orderIssueService.update(target);
		return getSaveResult(ret);
	}
	
	@RequestMapping(value="recommendReason",method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public ResultBO<?> updateRecommendReason(OrderIssueVO vo,HttpSession session){
		OrderIssueVO target = new OrderIssueVO();
		target.setId(vo.getId());
		target.setRecommendReason(vo.getRecommendReason());
		target.setModifyBy(getUserRealName(session));
		int ret = orderIssueService.update(target);
		return getSaveResult(ret);
	}
	
	@RequestMapping(value="recommendReason/showStatus",method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public ResultBO<?> updateRecommendReasonShow(OrderIssueVO vo,HttpSession session){
		OrderIssueVO target = new OrderIssueVO();
		target.setId(vo.getId());
		target.setIsShow(vo.getIsShow());
		target.setModifyBy(getUserRealName(session));
		int ret = orderIssueService.update(target);
		return getSaveResult(ret);
	}
	
	
}
