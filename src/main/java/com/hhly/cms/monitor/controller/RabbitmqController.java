package com.hhly.cms.monitor.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.common.Constants;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.util.PropertyUtil;


/**
 * 
 * @desc rabbitmq 监控
 * @author jiangwei
 * @date 2017年3月29日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/monity/rabbitmq")
public class RabbitmqController extends BaseController {
	
	 
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(HttpServletResponse response){
		Cookie cookie = new Cookie("rabbitmqManagement",PropertyUtil.getPropertyValue(Constants.SYS_DYNAMIC,"rabbitmq_management"));
		response.addCookie(cookie);
		return "monity/rabbitmq";
	}
	
}
