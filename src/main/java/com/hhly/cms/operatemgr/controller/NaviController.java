package com.hhly.cms.operatemgr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.utils.Authority;

/**
 * @desc    导航运营管理
 * @author  Tony Wang
 * @date    2017年2月7日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value="/operatemgr/navi")
public class NaviController extends BaseController { 
//	@Autowired	
//	private TransMgrService transMgrService;
	
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "operatemgr/navi";
	}
	
//	@RequestMapping(value = "/list")
//	@Authority(privilege=AuthEnum.SEARCH)
//	public @ResponseBody Object list(TransChannelSettleVO vo){
//		return transMgrService.findChannelSettleTrans(vo);
//	}
	
}
