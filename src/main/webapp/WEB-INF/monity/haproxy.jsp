<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>rabbitmq haproxy</title>
  </head>
<body>
<iframe id="iframe" style="width: 100%;height: 100%"></iframe>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script type="text/javascript">
   var url = Cms.getCookie('haproxyUrl').replace('"','').replace('"','');
   document.getElementById("iframe").src=url;
</script>
</html>
