<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=basePath%>resources/js/public/boot.js"
	type="text/javascript"></script>
<title>thread</title>
</head>
<body>
	<!-- <input type="button" value="addTab" onclick="Current.addTab()" /> -->
	<div id="threadTab" class="mini-tabs" activeIndex="0"
		style="width: 100%; height: 100%;" arrowPosition="side"
		showNavMenu="true">
		<div name="tab1" title="task项目">
<!-- 			监控路径:<input type="text" id="url" name="url" style="width: 400px;" /> 
			<input type="button" value="确定" onclick="Current.initData()" /> -->
			<br/><br/>
			<div id='threadPool'></div>
			<br/><br/>
			<div id='scheduleThreadPool'></div>
		</div>
	</div>
</body>
<script src="<%=basePath%>resources/js/public/ichartjs/ichart.1.2.min.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/monity/thread.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
