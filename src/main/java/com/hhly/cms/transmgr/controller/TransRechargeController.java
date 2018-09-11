package com.hhly.cms.transmgr.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.transmgr.service.TransMgrService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.cms.transmgr.vo.TransRechargeVO;
import com.hhly.skeleton.pay.vo.CmsRechargeVO;

/**
 * @desc    用户充值管理管理
 * @author  Tony Wang
 * @date    2017年1月3日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value="/transmgr/recharge")
public class TransRechargeController extends BaseController {

	@Autowired	
	private TransMgrService transMgrService;
	
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "transmgr/trans_recharge";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object list(TransRechargeVO vo){
		return transMgrService.findRechargeTrans(vo);
	}
	
	@RequestMapping(value = "/resupply")
	@Authority(privilege=AuthEnum.RESUPPLY)
	@ResponseBody
	public Object resupply(@RequestBody TransRechargeVO vo, HttpSession session){
		vo.setCreateBy(getUserRealName(session));
		return transMgrService.resupply(vo);
		//return getResult(true);
	}
	
	@RequestMapping(value = "/manual", method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.MANUAL_RECHARGE)
	@ResponseBody
	public Object manual(@RequestBody CmsRechargeVO vo, HttpSession session){
		vo.setOperator(getUserRealName(session));
		return transMgrService.manual(vo);
	}
	
	@RequestMapping("excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void exportOpExcel(HttpServletResponse response,TransRechargeVO vo) throws IOException{
		excel("charge", transMgrService.getTransRechargeExcel(vo), response);
	}
	
}
