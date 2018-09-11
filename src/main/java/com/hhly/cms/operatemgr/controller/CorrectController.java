package com.hhly.cms.operatemgr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.operatemgr.service.OperateHelpCorrectService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.common.OperateHelpCorrectEnum;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.operatemgr.vo.OperateHelpCorrectVO;

/**
 * @desc    
 * @author  cheng chen
 * @date    2017年4月21日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping("/operatemgr/correct")
public class CorrectController extends BaseController {

	@Autowired
	OperateHelpCorrectService operateHelpCorrectService;

	/**
	 * 跳转意见箱管理页面
	 * @return
	 * @date 2017年5月25日上午11:52:48
	 * @author cheng.chen
	 */
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(){
		return "operatemgr/correct";
	}

	/**
	 * 查询意见箱集合信息
	 * @param vo
	 * @return
	 * @date 2017年5月25日上午11:53:02
	 * @author cheng.chen
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object list(OperateHelpCorrectVO vo){
		vo.setSortField("C.CREATE_TIME");
		vo.setSortOrder("desc");
		return operateHelpCorrectService.findOperateCorrectList(vo);
	}

	/**
	 * 修改意见箱对象
	 * @param vo
	 * @param session
	 * @return
	 * @date 2017年5月25日上午11:53:09
	 * @author cheng.chen
	 */
	@RequestMapping(value = "/update")
	@Authority(privilege=AuthEnum.UPD)
	@ParameterValid
	@ResponseBody
	public  Object update(@Valid(GroupValue.UPD)OperateHelpCorrectVO vo ,HttpSession session){
		vo.setModifyBy(getUserName(session));
		vo.setStatus(OperateHelpCorrectEnum.Status.Yes.getValue());
		return getSaveResult(operateHelpCorrectService.updateOperateCorrect(vo));
	}	
}
