<%@ page import="java.util.Date" %><%
String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
            + path + "/";
String baseUrl=request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort();
    String version = String.valueOf(new Date().getTime());
%>
<%@ taglib uri="/button" prefix="btn" %>
