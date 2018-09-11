package com.hhly.cms.operatemgr.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.operatemgr.service.AdService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.operatemgr.vo.OperateAdMenuVO;

/**
 * @desc    广告图页面管理
 * @author  Tony Wang
 * @date    2017年2月7日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value="/operatemgr/ad/menu")
public class AdMenuController extends BaseController { 
	@Autowired	
	private AdService adService;

	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "operatemgr/ad_menu";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object list(OperateAdMenuVO vo){
		return adService.listMenu(vo);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ParameterValid
	@ResponseBody
	public Object udpate(@Valid(GroupValue.UPD)OperateAdMenuVO vo,HttpSession session) {
		vo.setModifyBy(getUserRealName(session));
		return getSaveResult(adService.mergeMenu(vo));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ParameterValid
	@ResponseBody
	public Object add(@Valid(GroupValue.ADD)OperateAdMenuVO vo,HttpSession session) {
		Assert.isNull(vo.getId(), "新增广告图页面管理不能包含主键id");
		vo.setCreateBy(getUserRealName(session));
		return getSaveResult(adService.mergeMenu(vo));
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@Authority(privilege=AuthEnum.DEL)
	@ResponseBody
	public Object delete(@RequestBody OperateAdMenuVO vo,HttpSession session) {
		vo.setCreateBy(getUserRealName(session));
		return getSaveResult(adService.deleteMenu(vo));
	}
	
	@RequestMapping("/dic")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public List<DictionaryBO> findMenuAsDic(OperateAdMenuVO vo) {
		return adService.findMenuAsDic(vo);
	}
	
	@RequestMapping("/position/dic/{menu}")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public List<DictionaryBO> findMenuPositionAsDic(OperateAdMenuVO vo) {
		return adService.findMenuPositionAsDic(vo);
	}
}
