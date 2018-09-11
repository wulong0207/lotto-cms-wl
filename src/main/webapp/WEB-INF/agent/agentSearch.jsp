<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>代理查询</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
				<td width="5%" style="white-space:nowrap;">
					账    号：<input id="accountName" class="mini-textbox" style="width:100px;" oncloseclick="Cms.onCloseClick" showClose="true"/>
				          手机号：<input id="cusMobile" class="mini-textbox" style="width:100px;" oncloseclick="Cms.onCloseClick" showClose="true"/>  
				          当前状态：<input id="agentStatus"   class="mini-combobox"  style="width:120px;" emptyText="全部"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />  
					<a class="mini-button" onclick="Current.search()">查询</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="90%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="agent/agentList" idField="agentId" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true"  onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div field="accountName" headerAlign="center" align="center">用户账号</div>
					<div field="actualName" headerAlign="center" align="center">真实姓名</div>
					<div field="agentCode" headerAlign="center" align="center">代理编码</div>
					<div field="cusMobile" headerAlign="center" align="center">手机号</div>
					<div field="monthDirectIncome" headerAlign="center" align="center"  numberFormat="¥#,0.00">本月业务返佣</div>
					<div field="monthAgentIncome" headerAlign="center" align="center"  numberFormat="¥#,0.00">本月代理返佣</div>
					<div field="agentWallet" headerAlign="center" align="center" numberFormat="¥#,0.00">可提现余额</div>
					<div field="agentStatus" headerAlign="center" align="center" renderer="Current.onOptRenderer" >代理状态</div>
					<div field="opt" renderer="Current.onActionRenderer"  width="12%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">操作</div>
				</div>
			</div>
		</div>
	</div>
	
	
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/agentSearch.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
