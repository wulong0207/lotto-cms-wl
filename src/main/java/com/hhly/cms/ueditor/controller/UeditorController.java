package com.hhly.cms.ueditor.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baidu.ueditor.ActionEnter;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.utils.Authority;

/**
 * @desc    文章运营管理
 * @author  Tony Wang
 * @date    2017年2月28日
 * @company 益彩网络科技公司
 * @version 1.0
 */
/*
 * 在ueditor.config.js中设置了'serverUrl: applicationPath + "/editor/config"',所以会访问此controller
 */
@Controller
@RequestMapping(value="/editor")
public class UeditorController { 
	
	private static Logger logger = LogManager.getLogger(UeditorController.class);

	
	@RequestMapping("/config")
	@Authority(privilege=AuthEnum.SEARCH)
	/*
	 * ActionEnter ( HttpServletRequest request, String rootPath ) -->com.baidu.ueditor.ConfigManager.getInstance( this.rootPath, this.contextPath, request.getRequestURI() )
	 * --》com.baidu.ueditor.ConfigManager.initEnv()
	 * --》com.baidu.ueditor.ConfigManager.getConfigPath() 返回"/usr/local/tomcat/lotto-cms/src/main/webapp/editor/config.json", 路径中的"editor"对应UeditorController的@RequestMapping
	 */
	public void config(HttpServletRequest request, HttpServletResponse response){
		System.out.println("-------------------------UeditorController----config---------------------------");
		try {
			response.setContentType("application/json");
			request.setCharacterEncoding("utf-8");
			response.setHeader("Content-Type", "text/html");
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			String exec = new ActionEnter(request, rootPath).exec();
			System.out.println(exec);
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			logger.error(e.getStackTrace());
		}
	}
}
