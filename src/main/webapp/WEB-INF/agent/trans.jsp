<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>代理钱包流水查询</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	         		代理账号：<input name="agentAccount" class="mini-textbox"/>
					&nbsp;&nbsp;&nbsp;
					时间区间：
					<input name="start" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
						   showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
					至
					<input name="end" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
						   showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
					&nbsp;&nbsp;&nbsp;
			        <a class="mini-button" onclick="agent_trans.search">查询</a>
					&nbsp;&nbsp;&nbsp;
					<btn:operate privilege="EXPORT">
						<a class="mini-button" iconCls="icon-download" plain="true" onclick="agent_trans.excel('agent/taken')">导出Excel</a>
					</btn:operate>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:87%;" 
		 url="agent/trans/page" idField="id" allowResize="true" ajaxType="get" multiSelect="true"
		 showColumnsMenu="true" showFilterRow="false">
		<div property="columns">
			<div type="checkcolumn" width="5%"></div>
			<div type="indexcolumn" width="5%" align="center" headerAlign="center">序号</div>
			<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">时间</div>
			<div field="accountName" headerAlign="center" align="center">账号</div>
			<div field="transAmount" headerAlign="center" align="center">变动数值</div>
			<div field="transInfo" headerAlign="center" align="center">变更原因</div>
			<div field="taxCharge" headerAlign="center" align="center">税费</div>
            <%--
			<div field="" headerAlign="center" align="center">充值银行卡</div>
			<div field="" headerAlign="center" align="center">充值账号</div>
			--%>
		</div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/agent/agent_trans.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
