package com.hhly.cms.sysmgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.customermgr.service.UserLogService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.cms.sysmgr.vo.CmsUserLogVO;

/**
 * @desc    用户操作日志
 * @author  Tony Wang
 * @date    2017年5月15日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/sysmgr/user/log")
public class UserLogController extends BaseController {

	@Autowired
	UserLogService userLogService;
	
	@RequestMapping()
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "sysmgr/user_log";
	}
	
	
	@RequestMapping(value = "/list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object list(CmsUserLogVO vo) {
		return userLogService.list(vo);
	}
	
	@RequestMapping(value = "/{id}")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object detail(CmsUserLogVO vo) {
		return userLogService.find(vo);
	}
}
