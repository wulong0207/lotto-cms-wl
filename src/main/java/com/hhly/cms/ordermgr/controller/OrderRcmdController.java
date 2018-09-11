package com.hhly.cms.ordermgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.ordermgr.service.OrderRcmdUserService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.recommend.bo.RcmdUserCheckBO;
import com.hhly.skeleton.cms.recommend.vo.RcmdUserCheckVO;

/**
 * 
* @Description: 合买
* @author HouXiangBao289
* @date 2018年5月3日 下午3:58:58 
* @version V1.0.0
 */
@Controller
@RequestMapping("/ordermgr/rcmd")
public class OrderRcmdController extends BaseController {
	
	@Autowired
	private OrderRcmdUserService orderRcmdUserService;

	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "ordermgr/rcmd_user_check";
	}
	
	@RequestMapping("/rcmdUserCheckList")
	@ResponseBody
	@Authority(privilege=AuthEnum.SEARCH)
	public PagingBO<RcmdUserCheckBO> rcmdUserCheckList(RcmdUserCheckVO vo){
		return orderRcmdUserService.findRcmdUserCheckList(vo);
	}
	
	@RequestMapping(value="/setCheckStatus",method=RequestMethod.POST)
	@ResponseBody
	@Authority(privilege=AuthEnum.UPD)
	public ResultBO<?> setCheckStatus(RcmdUserCheckVO vo){
		int ret = orderRcmdUserService.setStatus(vo);
		return getSaveResult(ret);
	}
	
	@RequestMapping(value="/addRcmdUser",method=RequestMethod.POST)
	@ResponseBody
	@Authority(privilege=AuthEnum.ADD)
	public ResultBO<?> addRcmdUser(RcmdUserCheckVO vo){
		int ret = orderRcmdUserService.addRcmdUser(vo);
		return getSaveResult(ret);
	}
}
