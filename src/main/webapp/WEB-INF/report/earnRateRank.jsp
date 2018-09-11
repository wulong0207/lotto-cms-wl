
<%@page import="java.util.Date"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
	<link href="<%=basePath%>resources/css/public/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<title>盈利率排行榜</title>
	<style type="text/css">
		td{
			font-size:9px;
			white-space: nowrap;
			padding-left: 5px;
		}
		td a {
			cursor: hand;
			margin-left:8px;
			margin-right:8px;
		}
		.active {
			color:white;
			background-color:rgb(61, 116, 183)
		}
		.mini-toolbar {
			border-top: 0;
			border-left: 0;
			padding: 0px;
		}
	</style>
	
</head>
<body>
	<div class="mini-toolbar">
		<table style="width: 100%;">
			<tr>
					<td width="10%" id="statis-channel" colspan="2">
						注册渠道：
						<a data-channel="0" class="active">全部</a>|
					&nbsp;可选渠道：<input id="channel" name="userRegisterChannel" class="mini-treeselect" url="userdata/channelTree" multiSelect="false" 
								 valueFromSelect="false" style="width: 200px;" onvaluechanged="earnRateRank.clearActive('statis-channel')"
							        textField="text" valueField="id" parentField="pid" allowInput="true" showRadioButton="true" showFolderCheckBox="true"/>

					</td>
			</tr>
			<tr>
				<td width="20%" id="statis-code">
					彩种类型：
					<a class="active">全部</a>
					<a data-code="100" >双色球</a>|
					<a data-code="102">大乐透</a>|
					<a data-code="300">竞足</a>|
					<a data-code="301">竞篮</a>|
					<a data-code="304">胜负彩</a>|
					<a data-code="305">任九</a>|
					<a data-code="306">北单</a>|
					<a data-code="215">十一运夺金</a>|
					<a data-code="233">江苏快三</a>|
				</td>
				<td>
				其他彩种：<input id="lotteryCode" class="mini-combobox" emptyText="请选择" allowInput="true" showClose="true" onCloseClick="earnRateRank.comboxOnCloseClick" onvaluechanged="earnRateRank.clearActive('statis-code')"/>
				<td>
			</tr>
			<tr>
				<td width="20%" id="statis-time">
					时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp间;：
					<a >全部</a>|
					<a data-time="1" class="active">昨天</a>|
					<a data-time="2">今天</a>|
					<a data-time="3">上周</a>|
					<a data-time="4">本周</a>|
					<a data-time="5">上月</a>|
					<a data-time="6">本月</a>|
				</td>
				<td>选择查询时间：
					<input style="width:200px;" id="startTime" name="startTime" class="mini-datepicker" format="yyyy-MM-dd" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="earnRateRank.clearActive('statis-time')"/>
				        到
				    <input style="width:200px;" id="endTime" name="endTime" class="mini-datepicker"  format="yyyy-MM-dd" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="earnRateRank.clearActive('statis-time')"/>
			
				</td>
			</tr>
			<tr>
				<td width="20%">用户名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;<input id='userName' name="userName" style="width:200px;" class="mini-textBox"  allowInput="true" showClose="true" /></td>
				<td>
					<button id="searchBtn" onclick="earnRateRank.search()">查询</button> &nbsp;&nbsp;&nbsp;
					<button id="exportBtn" data-excel-url="report/earnRate/exportExcel">导出</button>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-panel" title="明细数据 :" style="width:100%;height:100%;margin-top:10px" showCollapseButton="true">
		<!-- <div class="mini-tabs" activeIndex="0" style="width:100%;height:100%;margin-top:10px" contextMenu="#tabsMenu"> -->
			<div name="frist" title="明细数据">  
				<div id="datagridRank" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 85%;"
					url="report/earnRate/earnRateDetail" idField="id"  
					pageSize="30"  showColumnsMenu="true" showPagerButtonText="true" showPagerButtonIcon="true"
					showFilterRow="false" allowMoveColumn="true" onpreload="MiniCom.onpreload"
					showSummaryRow="true">
					<div property="columns">
						<div type="indexcolumn" width="3%" align="center" headerAlign="center">序列号</div>
						<div field="userName" headerAlign="center" align="center">用户名</div>
						<div field="fillAmount" headerAlign="center" align="center" allowSort="true">充值金额</div>
						<div field="orderAmount" headerAlign="center" align="center" allowSort="true">投注金额</div>
						<div field="winningAmount" headerAlign="center" align="center" allowSort="true">中奖金额</div>
						<div field="winningRate" headerAlign="center" align="center" allowSort="true">盈利率(中奖-投注)/投注(%)</div>
						<div field="waterValue" headerAlign="center" align="center" allowSort="true">流水价值(投注/充值)</div>
					</div>
				</div>
			</div>
		<!-- </div>	 -->
	</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/echarts.min.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/report/earnRateRank.js?version=<%=version%><%=System.currentTimeMillis() %>" type="text/javascript"></script>
</html>
