package com.hhly.cms.operatemgr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.operatemgr.service.RebateService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.operatemgr.vo.OperateRebateUserVO;

/**
 * 
 * @author lidecheng
 * @Version 1.0
 * @CreatDate 2017-2-23
 * @Desc 大客户管理
 */
@Controller
@RequestMapping(value = "/operatemgr/rebateuser")
public class RebateUserController extends BaseController {
	@Autowired 
	private RebateService rebateService;

	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "operatemgr/rebate_user";
	}
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object list(OperateRebateUserVO vo){
		vo.setType(1);
		return rebateService.findRebateUser(vo);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@ParameterValid
	public  Object add(@RequestBody@Valid(GroupValue.ADD)OperateRebateUserVO vo,HttpSession session) {
		vo.setCreateBy(getUserRealName(session));
		int num = rebateService.addRebateUser(vo);
		return getSaveResult(num);
	}
	/**
	 * 查询大客户最新的一期
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/newinfo/{userId}")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object findDetail(@PathVariable(value = "userId")int userId){
		return getResultSuccess(rebateService.findByUser(userId));
	}
	/**
	 * 查询大客户历史数据
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/listDetailInfo/{userId}")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object findDetailInfoList(@PathVariable(value = "userId")int userId){
		return getResultSuccess(rebateService.findUserHisList(userId));
	}
}
