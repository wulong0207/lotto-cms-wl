<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>下级代理查询</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	         		代理账号：<input name="agentAccount" class="mini-textbox"/>
					下级代理账号：<input name="memberAccount" class="mini-textbox"/>
					&nbsp;&nbsp;&nbsp;
			        <a class="mini-button" onclick="agent_sub.search">查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:87%;" 
		 url="agent/sub/page" idField="id" allowResize="true" multiSelect="true"
		 showColumnsMenu="true" showFilterRow="false">
		<div property="columns">
			<div type="checkcolumn" width="5%"></div>
			<div type="indexcolumn" width="5%" align="center" headerAlign="center">序号</div>
			<div field="agentAccount" headerAlign="center" align="center">代理账号</div>
			<div field="agentRealName" headerAlign="center" align="center">代理账号真实姓名</div>
			<div field="agentMobile" headerAlign="center" align="center">代理绑定手机号</div>
			<div field="memberAccount" headerAlign="center" align="center">下级代理用户账号</div>
			<div field="memberRealName" headerAlign="center" align="center">下级代理真实姓名</div>
			<div field="monthOrderAmount" headerAlign="center" align="center">本月投注金额</div>
			<div field="accruedOrderAmount" headerAlign="center" align="center">累计投注金额</div>
			<div field="accruedOrderNum" headerAlign="center" align="center">累计投注次数</div>
		</div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/agent/agent_sub.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
