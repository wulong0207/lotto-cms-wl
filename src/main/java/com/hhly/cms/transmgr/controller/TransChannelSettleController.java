package com.hhly.cms.transmgr.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.transmgr.service.TransMgrService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.cms.transmgr.vo.TransChannelSettleVO;

/**
 * 渠道结算管理
 * @author wytong
 * @Version 1.0
 * @CreatDate 2017年1月3日 下午6:35:16
 */
@Controller
@RequestMapping(value="/transmgr/channelsettle")
public class TransChannelSettleController extends BaseController {

	@Autowired	
	private TransMgrService transMgrService;
	
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "transmgr/trans_channel_settle";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object list(TransChannelSettleVO vo){
		return transMgrService.findChannelSettleTrans(vo);
	}
	
	@RequestMapping(value = "/settle")
	@Authority(privilege=AuthEnum.SETTLE)
	public @ResponseBody Object resupply(@RequestBody List<TransChannelSettleVO> vos, HttpSession session){
		return null;
//		return transMgrService.settle(vos);
	}
	
	@RequestMapping("excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void exportOpExcel(HttpServletResponse response,TransChannelSettleVO vo) throws IOException{
		excel("settle", transMgrService.getTransChannelSettleExcel(vo), response);
	}
}
