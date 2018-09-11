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
 * @desc haproxy 负载均衡监控
 * @author jiangwei
 * @date 2017年3月29日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/monity/haproxy")
public class HaproxyController extends BaseController {
	
	 
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(HttpServletResponse response){
		Cookie cookie = new Cookie("haproxyUrl",PropertyUtil.getPropertyValue(Constants.SYS_DYNAMIC,"rabbitmq_haproxy"));
		response.addCookie(cookie);
		return "monity/haproxy";
	}
	
}
