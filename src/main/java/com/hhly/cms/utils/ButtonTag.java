package com.hhly.cms.utils;


import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.hhly.cms.base.common.AuthEnum;


public class ButtonTag extends BodyTagSupport {
	private static final long serialVersionUID = -532517444654109642L;
	private String privilege;
	private String show;
	


	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}


	public int doAfterBody() throws JspException {
		try {
			BodyContent bodycontent = getBodyContent();
			if(bodycontent == null)
			return SKIP_BODY;
			
			String body = bodycontent.getString();
			JspWriter out = bodycontent.getEnclosingWriter();		
			HttpSession session = pageContext.getSession();
			
			@SuppressWarnings("unchecked")
			Set<String>  privilegeSet =  (Set<String>) session.getAttribute("privilege");
			String[] priArr = null;
			//是否都需要包含权限，还是只要有其中一个权限
			boolean isAnd = false;
			if(privilege.indexOf("&")!=-1){
				isAnd = true;
				priArr = privilege.split("&");
			}else{
				priArr = privilege.split("\\|");
			}
			 
			int i = 0;
			if (privilegeSet != null) {
					for (String s1 : priArr) {
						AuthEnum auth = Enum.valueOf(AuthEnum.class,s1);
						if(privilegeSet.contains(String.valueOf(auth.ordinal()))){
							i++;
						}
					}
					if ((!isAnd && i>0)
							||(i == priArr.length && isAnd)){
						if (body != null) {
							out.print(body);														
						}
					}else{
						show = show == null ? "false" : show;
						if (body != null) {
							if(show.equals("true")){
								body = body.replace("enabled=\"true\"", "");
								body = body.replace("<a ", "<a enabled=\"false\" ");
								out.print(body);
							}else if(show.equals("hide")){
								out.print("<div style=\"display: none;\">"+body+"</div>");
							}
						}
					}
						   
				}

		} catch (IOException ioe) {
			throw new JspException("Error:" + ioe.getMessage());
		}
		return SKIP_BODY;

	}
}
