<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=basePath%>resources/js/public/boot.js"
	type="text/javascript"></script>
<title>图片上传</title>
</head>
<body>
	<div>
		<form method="post" enctype="multipart/form-data">
			<input id="catalogue" name="catalogue" type="hidden" /> 
			图片分组：<input id="imageGroup_edit" name="groupid" class="mini-combobox" style="width: 100px;" valueFromSelect="true" /> 
			<br /> <br />
			<span id="prompt">选择要上传的文件(一次可以选择多张)：</span>
			<br /> 
			<span id="files"> </span>
		</form>
		<br />
		
		<br /> 
		<btn:operate privilege="UPLOAD">
		<input type="button" value="上传" onclick="Current.upload()" />
		</btn:operate> 
		<br />
		<br /> 上传进度：
		<progress></progress>
		<span id="progress">0 bytes</span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span
			id="info">"总大小: " + 0 + "bytes"</span>
	</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/image_upload_page.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
