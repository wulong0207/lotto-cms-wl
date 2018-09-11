<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>返佣数据查询</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
				<td width="5%" style="white-space:nowrap;">
					账    号：<input id="accountName" class="mini-textbox" style="width:100px;"/>
					查询时间：<input id="startTime" name="startTime" class="mini-monthpicker" allowInput="false" style="width: 200px;"  format="yyyy-MM"
						showClearButton="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
						至
						<input id="endTime" name="endTime" class="mini-monthpicker" allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM"
							 showClearButton="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
							 	<a class="mini-button" onclick="Current.search()">查询</a>
							 	<button id="exportBtn" data-excel-url="agent/excelAboutIncome">导出</button>
					</td>
				
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="90%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="agent/incomeList" idField="agentId" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" >
				
				<div property="columns">
					<div field="mt" headerAlign="center" align="center">月份</div>
					<div field="accountName" headerAlign="center" align="center">账号</div>
					<div field="directBuyCount" headerAlign="center" align="center">业务投注人数</div>
					<div field="directBuyMoney" headerAlign="center" align="center">业务投注金额</div>
					<div field="directIncome" headerAlign="center" align="center">业务返佣金额</div>
					<div field="directRation" headerAlign="center" align="center">直属返佣比例</div>
					<div field="agentBuyCount" headerAlign="center" align="center" >代理投注人数</div>
					<div field="agentBuyMoney" headerAlign="center" align="center" >代理投注金额</div>
					<div field="agentIncome" headerAlign="center" align="center" >代理返佣金额</div>
					<div field="agentRation" headerAlign="center" align="center">代理返佣比例</div>
					<div field="monthIncome" headerAlign="center" align="center">累计返佣金额</div>
					<div field="opt" width="12%" headerAlign="center" align="center" renderer="Current.onActionRenderer">月度详情</div>
				</div>
			</div>
		</div>
	</div>
	
	
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/incomeSearch.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
