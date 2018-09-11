package com.hhly.cms.monitor.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.common.Constants;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.bo.TabBO;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.PropertyUtil;


/**
 * 
 * @desc 数据库请求监控
 * @author jiangwei
 * @date 2017年3月29日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/monity/druid")
public class DruidController extends BaseController {
	
	 
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(HttpServletResponse response){
		return "monity/druid";
	}
	
	/**
	 * 获取数据库监控页面
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月30日 上午10:21:36
	 * @return
	 */
	@RequestMapping("/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object  getDruid(){
		List<TabBO> tabBO  = new ArrayList<>();
		String durid = PropertyUtil.getPropertyValue(Constants.SYS_DYNAMIC,"druid_management");
		String[] durids = StringUtils.tokenizeToStringArray(durid, SymbolConstants.COMMA);
		for (String str : durids) {
			String title = str.replace("http://", "").replace("/druid", "");
			TabBO bo = new TabBO(title, str, true, false);
			tabBO.add(bo);
		}
		return getResultSuccess(tabBO);
	}
}
