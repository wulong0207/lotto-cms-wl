package com.hhly.cms.monitor.controller;


import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.common.Constants;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.util.HttpUtil;
import com.hhly.skeleton.base.util.PropertyUtil;


/**
 * @desc 线程池监控
 * @author jiangwei
 * @date 2017年3月30日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/monity/thread")
public class ThreadController extends BaseController {
	
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(HttpServletResponse response){
		return "monity/thread";
	}
	
	@RequestMapping(value = "/pool")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object pool() throws IOException, URISyntaxException{
		String url = PropertyUtil.getPropertyValue(Constants.SYS_DYNAMIC,"lotto_task_url");
		return HttpUtil.doGet(url+"monitor/thread/pool/status");
	}
}
