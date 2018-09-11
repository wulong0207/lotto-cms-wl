package com.hhly.cms.transmgr.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.transmgr.service.TransMgrService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.cms.transmgr.vo.TransRedVO;

/**
 * 红包流水管理
 * @author wytong
 * @Version 1.0
 * @CreatDate 2017年1月3日 下午6:35:16
 */
@Controller
@RequestMapping(value="/transmgr/red")
public class TransRedController extends BaseController {

	@Autowired	
	private TransMgrService transMgrService;
	
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "transmgr/trans_red";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object list(TransRedVO vo){
		return transMgrService.findRedTrans(vo);
	}
	
	@RequestMapping("excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void exportOpExcel(HttpServletResponse response,TransRedVO vo) throws IOException{
		excel("red", transMgrService.getTransRedExcel(vo), response);
	}
	
}
