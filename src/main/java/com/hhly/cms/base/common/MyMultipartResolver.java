package com.hhly.cms.base.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class MyMultipartResolver extends CommonsMultipartResolver {

	/**
	 * 用于过滤百度编辑器上传文件时阻止CommonsMultipartResolver对文件进行包装
	 */
	@Override
	public boolean isMultipart(HttpServletRequest request) {
		String uri = request.getRequestURI();
		// 过滤使用百度Ueditor的URI
		if(uri.indexOf("editor/config") > 0) {
			return false;
		}
		return super.isMultipart(request);
	}

	
}
