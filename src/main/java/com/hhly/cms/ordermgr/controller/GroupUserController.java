package com.hhly.cms.ordermgr.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.ordermgr.service.OrderGroupService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.ordermgr.bo.GroupUserBO;
import com.hhly.skeleton.cms.ordermgr.vo.GroupUserVO;

/**
 * 
* @Description: 发单用户
 */
@Controller
@RequestMapping("/groupUser")
public class GroupUserController extends BaseController {
	
	@Autowired
	private OrderGroupService orderGroupService;

	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "ordermgr/group_user";
	}
	
	@RequestMapping("/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<GroupUserBO> list(GroupUserVO vo){
		return orderGroupService.findGroupUser(vo);
	}
	
	@RequestMapping("/excel")
	@Authority(privilege=AuthEnum.EXPORT)
	@ResponseBody
	public void excel(HttpServletResponse response,HttpSession session,GroupUserVO vo)throws IOException{
		excel("groupUser",orderGroupService.exprotGroupUserExcel(vo),response);
	}
	
	@RequestMapping(value="/recommand",method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public ResultBO<?> recommand(GroupUserVO vo) {
		orderGroupService.updateRecommand(vo);
		return ResultBO.ok();
	}

	@RequestMapping(value="/setUserFlag",method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public ResultBO<?> setUserFlag(GroupUserVO vo) {
		orderGroupService.updateUserFlag(vo);
		return ResultBO.ok();
	}
}
