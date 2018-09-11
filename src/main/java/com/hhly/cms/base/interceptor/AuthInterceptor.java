package com.hhly.cms.base.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.WebConstant;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.cms.sysmgr.bo.CMSUserMenuBO;

/**
 *
 * @author MoHaiWang
 * @Version 1.0
 * @CreatDate 2015年8月14日 下午5:36:52
 */
public class AuthInterceptor implements HandlerInterceptor {
	/**
	 * 拦截处理前操作
	 * @author tangxiaobo
	 * @Version 1.0
	 * @CreatDate 2017年3月29日 18:09:39
	 * @return
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();

		HandlerMethod method = null;
		if (handler instanceof HandlerMethod)
			method = (HandlerMethod) handler;
		else {
			return true;
		}

		String mName = method.getMethod().getName();
		
		if (mName.toLowerCase().equals("login") || mName.toLowerCase().equals("loginout")|| mName.toLowerCase().equals("getbackpassword"))
		return true;

		// 登录验证
		if (session.getAttribute(WebConstant.USERID) == null|| session.getAttribute(WebConstant.USERID).toString().isEmpty()) {
			
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + SymbolConstants.COLON+ request.getServerPort() + path + "/";
			response.sendRedirect(basePath+"login");
			
			return false;
		}
		
		String controllerPath = request.getServletPath();

		// 主页没有无需操作权限
		if (controllerPath.equals("/index") || controllerPath.equals("/index/menu")) {
			return true;
		}

		String urlAccess = SymbolConstants.ENPTY_STRING;

		@SuppressWarnings("unchecked")
		List<CMSUserMenuBO> userAuthList = (List<CMSUserMenuBO>) session.getAttribute(WebConstant.AUTERITY);
		
		for (CMSUserMenuBO bo : userAuthList) {
			if(ObjectUtil.isBlank(bo.getUrl()))
				continue;
			if (controllerPath.equals("/"+bo.getUrl())||controllerPath.contains("/"+bo.getUrl())) {
				urlAccess+=bo.getUserAuth();
				break;
			}
			
		}

		Authority authority = method.getMethodAnnotation(Authority.class);
		AuthEnum[] privilege = {};

		if (authority != null)
		privilege = authority.privilege();
		
		boolean flag = false;
		for (AuthEnum pItem : privilege) {
			
			 if(pItem == AuthEnum.ALL){
				 flag=true;
				 break;
		        }
			 
				String[] auths = urlAccess.split(SymbolConstants.COMMA);
				for (String auth : auths) {
					if (auth.equals(pItem.getCode())) {
						 flag=true;
						 break;
					}
				}
		}
		if (!flag) {
			responseWriterAuth(response);
			return false;
		}

		Set<String> userAuthSet = new HashSet<String>();
		for (String s : urlAccess.split(SymbolConstants.COMMA)) {
			userAuthSet.add(s);
		}
		session.setAttribute("privilege", userAuthSet);
		return true;
	}

	/**
	 * 打印无权限
	 * 
	 * @param response
	 */
	private void responseWriterAuth(HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.write("<script type=\"text/javascript\">\n" + "    alert(\"对不起！您无此操作权限，请联系管理员。\");\n" + "</script>");
		writer.flush();
		writer.close();
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
