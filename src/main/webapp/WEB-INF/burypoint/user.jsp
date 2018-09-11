<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>用户列表</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<input name="userIds" id="userIds" class="mini-hidden"/>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:87%;" 
		 url="bp/user/page" idField="id" allowResize="true" ajaxType="get" multiSelect="true"
		 showColumnsMenu="true" showFilterRow="false">
		<div property="columns">
			<div type="checkcolumn" width="5%"></div>
			<div type="indexcolumn" width="5%" align="center" headerAlign="center">序号</div>
			<div field="userId" headerAlign="center" align="center">用户ID</div>
			<div field="userId" type="comboboxcolumn" headerAlign="center" align="center">用户名
				<input property="editor" class="mini-combobox" data="Dic.userNames"/>
			</div>
			<div field="ip" headerAlign="center" align="center">用户IP</div>
			<div field="terminal" headerAlign="center" align="center">设备ID</div>
			<div field="versionId" headerAlign="center" align="center">App版本</div>
			<div field="registerChannelId" headerAlign="center" align="center">注册渠道ID</div>
			<div field="registerChannelId" type="comboboxcolumn" headerAlign="center" align="center">注册渠道名称
				<input property="editor" class="mini-combobox" data="Dic.marketchannel"/>
			</div>
			<div field="registerTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">注册时间</div>
			<div field="channelId" headerAlign="center" align="center">登陆渠道id</div>
			<div field="channelId" type="comboboxcolumn" headerAlign="center" align="center">登陆渠道名称
				<input property="editor" class="mini-combobox" data="Dic.marketchannel"/>
			</div>
			<div field="loginLastTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">登陆时间</div>
			<%--
			<div field="orderLastTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">下单时间</div>
			<div field="payBeginTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">支付时间</div>
			<div field="payLastTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">下单完成时间</div>
			--%>
		</div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/burypoint/bp_user.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
