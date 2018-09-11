package com.hhly.cms.operatemgr.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.operatemgr.service.OperateHelpService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.operatemgr.bo.OperateHelpBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateHelpTypeBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateHelpTypeVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateHelpVO;

/**
 * @desc    帮助中心运营管理
 * @author  Tony Wang
 * @date    2017年2月28日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value="/operatemgr/help")
public class HelpCenterController extends BaseController { 
	@Autowired	
	private OperateHelpService operateHelpService;
	
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "operatemgr/help";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<OperateHelpBO> list(OperateHelpVO vo){
		return operateHelpService.listHelp(vo);
	}
	
	@RequestMapping(value = "/{helpId}",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public OperateHelpBO findSingleHelp(@PathVariable long helpId){
		return operateHelpService.findSingle(new OperateHelpVO(helpId));
	}
	
	@RequestMapping(value = "/helps")
	@Authority(privilege=AuthEnum.SEARCH)
	public String helps(){
		return "operatemgr/help_list";
	}
	
	@RequestMapping(value = "/help")
	@Authority(privilege={AuthEnum.ADD,AuthEnum.UPD})
	public  String helpMerge(){
		return "operatemgr/help_detail";
	}
	
	@RequestMapping(value = "/add", method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ParameterValid
	@ResponseBody
	public ResultBO<?> addHelp(@RequestBody@Valid(GroupValue.ADD) OperateHelpVO vo, HttpSession session) throws IOException{
		vo.setCreateBy(getUserCName(session));
		vo.setModifyBy(getUserCName(session));
		return getSaveResult(operateHelpService.addHelp(vo));
	}
	
	
	@RequestMapping(value = "/update", method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ParameterValid
	@ResponseBody
	public ResultBO<?> updateHelp(@RequestBody@Valid(GroupValue.UPD) OperateHelpVO vo, HttpSession session) throws IOException{
		vo.setModifyBy(getUserCName(session));
		return getSaveResult(operateHelpService.updateHelp(vo));
	}
	
	/**
	 * @desc   修改帮助中心状态 驳回 审核通过 发布
	 * @author Tony Wang
	 * @create 2017年4月25日
	 * @param vo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/status", method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public ResultBO<?> updateHelpStatus(OperateHelpVO vo, HttpSession session){
		// 设置操作人员昵称
		vo.setOperator(getUserCName(session));
		return getSaveResult(operateHelpService.updateHelpStatus(vo));
	}
	
	@RequestMapping(value = "/type", method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public List<OperateHelpTypeBO> listHelpType(){
		return operateHelpService.listHelpType();
	}
	
	
	@RequestMapping(value = "/type", method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ParameterValid
	@ResponseBody
	public ResultBO<?> addHelpType(@RequestBody@Valid(GroupValue.ADD) OperateHelpTypeVO vo, HttpSession session){
		Assert.isNull(vo.getId(), "帮助中心栏目已存在");
		return getSaveResult(operateHelpService.mergeHelpType(vo));
	}
	
	@RequestMapping(value = "/type", method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ParameterValid
	@ResponseBody
	public ResultBO<?> updateHelpType(@RequestBody@Valid(GroupValue.UPD) OperateHelpTypeVO vo, HttpSession session){
		Assert.notNull(vo.getId(),"更新栏目时，栏目id为空");
		return getSaveResult(operateHelpService.mergeHelpType(vo));
	}
	
	/**
	 * @desc   更新帮助中心栏目顺序
	 * @author Tony Wang
	 * @create 2017年4月17日
	 * @param vo
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/type/order", method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public ResultBO<?> updateHelpTypeOrder(@RequestBody List<OperateHelpTypeVO> vos){
		return getSaveResult(operateHelpService.updateHelpTypeOrder(vos));
	}
}
