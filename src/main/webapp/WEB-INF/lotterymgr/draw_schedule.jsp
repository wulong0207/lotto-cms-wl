<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=basePath%>resources/js/public/boot.js"
	type="text/javascript"></script>
<title>开奖进度</title>
</head>
<body>
	<div>
	   <br/>&nbsp;&nbsp;&nbsp;&nbsp;当期号码：<span id = "drawCode"></span>
	   <br/>&nbsp;&nbsp;&nbsp;&nbsp;处理方案：<span id = "order"></span>
	   <br/>&nbsp;&nbsp;&nbsp;&nbsp;剩余时间：<span id = "planSecond"></span>
	   <br/>&nbsp;&nbsp;&nbsp;&nbsp;执行进度：<progress></progress><span id="progress"></span>
	   <br/>&nbsp;&nbsp;&nbsp;&nbsp;成功订单：<span id="success"></span>
	   <br/>&nbsp;&nbsp;&nbsp;&nbsp;中奖订单：<span id="winCount"></span>
	   <br/>&nbsp;&nbsp;&nbsp;&nbsp;失败订单：<span id="fail"></span>
	   <br/>&nbsp;&nbsp;&nbsp;&nbsp;订单信息：<textarea id="message" style="width:280px;height:300px;"></textarea>
	</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/lotterymgr/draw_schedule.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
