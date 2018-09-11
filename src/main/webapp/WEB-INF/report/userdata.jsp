<%@page import="java.util.Date"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
	<title>用户数据</title>
	<style type="text/css">
		td{
			font-size:9px
		}
		
		#form a{
			margin-left:8px;
			margin-right:8px;
			cursor:hand;
		}
		
		.mini-tabs-space {
   			border-color: #ffffff;
		}
		
		.mini-tabs-bodys {
    		border-color: #ffffff;
		}
		
		.active{
			color:white;
			background-color:rgb(61, 116, 183);
		}
	</style>
	
</head>
<body>
	<div class="mini-toolbar" style="border-top: 0; border-left: 0; padding: 0px;">
		<div id="form">
			<table style="width: 100%;">
				<tr>
					<td width="10%" id="statis-channel" colspan="2" style="white-space: nowrap;">
							<!--注册渠道：
							 <a data-channel="0" class="active">全部</a>|
							<a data-channel="-1">本站</a>|
							<a data-channel="-2">本站移动端</a>|
							<a data-channel="-3">本站客户端</a>|
							<a data-channel="1">本站PC</a>|
							<a data-channel="3">本站Android</a>|
							<a data-channel="4">本站ios</a>|
							<a data-channel="2">本站wap</a>| -->
						&nbsp;可选渠道：<!-- <input id="channel" name="userRegisterChannel" class="mini-combobox" oncloseclick="Cms.onCloseClick"
														style="width: 150px;" emptyText="" allowInput="false" showClose="true" 
															onvaluechanged="userData.clearActive('statis-channel')" nullItemText="请选择..." showNullItem="true" /> -->
															
								<input id="channel" name="userRegisterChannel" class="mini-treeselect" url="userdata/channelTree" multiSelect="false" 
									 valueFromSelect="false" style="width: 200px;" onvaluechanged="userData.clearActive('statis-channel')"
								        textField="text" valueField="id" parentField="pid" allowInput="true" showRadioButton="true" showFolderCheckBox="true"/>
							&nbsp;提示：默认为空则查询全部渠道，下拉框可编辑、清空、搜索渠道
						</td>
				</tr>
				<tr>
					<td id="statis-time" width="20%" style="white-space: nowrap; padding-left: 5px;">
						时间：
						<a data-time="0" class="active">昨天</a>|
						<a data-time="1">今天</a>|
						<a data-time="2">上周</a>|
						<a data-time="3">本周</a>|
						<a data-time="4">上月</a>|
						<a data-time="5">本月</a>|

					</td>
					<td>选择查询时间：<input id="startTime" name="startTime" class="mini-datepicker" oncloseclick="Cms.onCloseClick"
						allowInput="false" style="width: 200px;" onvaluechanged="userData.clearActive('statis-time')" format="yyyy-MM-dd"
						showClearButton="true" showClose="true"/> 
						到
						<input id="endTime" name="endTime" class="mini-datepicker" allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
							oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true" onvaluechanged="userData.clearActive('statis-time')"/>
					    <button  onclick="userData.search()" style="width:60px;">查询</button>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="mini-panel" title="数据概况 :" style="width:100%;height:180px;margin-top:10px" showCollapseButton="true">
			<table style="width: 100%; height: 100%;" cellpadding="2"
				cellspacing="0" border="0" bordercolor="#000000" align="center" style="background-color:rgba(242, 242, 242, 1)">
				<tbody>
					<tr style="width: 100%;font-size:14px">
						<td
							style="width: 25%; padding-left: 8%; border-right: 1px solid #D1D1D1;">
							<span style="font-weight:bold;">注册用户：</span></br> <span id="registerUserCount" style="font-weight:bold;color:red;"></span></br> <span></span></br> <span>首次充值用户：<tag id="firstRechargeCount"></tag>人</span></br>
							<span>首充转化率：<tag id="firstRechargeRate"></tag>%</span></br> <span></span></br> <span></span>
						</td>
						<td
							style="width: 25%; padding-left: 8%; border-right: 1px solid #D1D1D1;">
							<span style="font-weight:bold;">投注用户：</span></br> <span id="buyUserCount" style="font-weight:bold;color:red;"></span></br> <span></span></br> <span>投注次数：<tag id="userBuyCount"></tag>次</span></br>
							<span>活跃度：<tag id="oneBuyCount"></tag>次/人</span></br> <span>投注额：<tag id="buyMoney"></tag>元</span></br> <span>Arpu值：<tag id="buyArpu"></tag>元</span>
						</td>
						<td
							style="width: 25%; padding-left: 8%; border-right: 1px solid #D1D1D1;">
							<span style="font-weight:bold;">新用户投注：</span></br> <span id="newUserCount" style="font-weight:bold;color:red;"></span></br> <span></span></br> <span>投注次数：<tag id="newUserBuyCount"></tag>次</span></br>
							<span>活跃度：<tag id="oneNewBuyCount"></tag>次/人</span></br> <span>投注额：<tag id="newUserBuyMoney"></tag>元</span></br> <span>Arpu值：<tag id="newUserBuyArpu"></tag>元</span>
						</td>
						<td style="width: 25%; padding-left: 8%;"><span style="font-weight:bold;">老用户投注：</span></br>
							<span id="oldUserCount" style="font-weight:bold;color:red;"></span></br> <span></span></br> <span>投注次数：<tag id="oldUserBuyCount"></tag>次</span></br> <span>活跃度：<tag id="oneOldBuyCount"></tag>次/人</span></br>
							<span>投注额：<tag id="oldUserBuyMoney"></tag>元</span></br> <span>Arpu值：<tag id="oldUserBuyArpu"></tag>元</span></td>
					</tr>
				</tbody>
			</table>
   </div>
	
	<div class="mini-panel" title="明细数据 :" style="width:100%;height:550px;margin-top:10px" showCollapseButton="true">
		<div>
			<label><input name="chartDetailData" type="radio" value="1" checked="checked" />图表数据</label> 
			<label><input name="chartDetailData" type="radio" value="2" />明细数据</label>
			<span id="excelBtn" style="display:none">
				<btn:operate privilege="EXPORT">
				      <button  iconCls="icon-download" plain="true" onclick="userData.excel()">导出</button>
				</btn:operate>
			</span>
		</div>
		
		<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:90%;margin-top:10px" contextMenu="#tabsMenu" onactivechanged="userData.activechanged">
		    <div name="registerTab" title="注册与有效">
				<div size="100%" showCollapseButton="true" id="registerTable" style="display:none">
						<div id="registerDatagrid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
							url="userdata/findRegisterList" idField="id"
							pageSize="30" multiSelect="true" showColumnsMenu="true" showPagerButtonText="true" showPagerButtonIcon="true"
							showFilterRow="false" allowMoveColumn="true"
							showSummaryRow="true">
	
							<div property="columns">
								<div field="registerDate"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd" allowSort="true">日期</div>
								<div field="newUserCount" headerAlign="center" align="center">新注册用户</div>
								<div field="perfectUserCount" headerAlign="center" align="center">完善用户</div>
								<div field="firstRechargeCount" headerAlign="center" align="center">首充用户</div>
								<div field="firstBuyCount" headerAlign="center" align="center">首投用户</div>
								<div field="perfectRate" headerAlign="center" align="center" renderer="userData.addRateCell">完善转化率</div>
								<div field="firstRechargeRate" headerAlign="center" align="center" renderer="userData.addRateCell">首充转换率</div>
							</div>
						</div>
				</div>
				
				<div id="registerChart" style="width:70%; height: 350px;"></div>
		    </div>
		    <div name="newOldUserTab" title="新老用户">
					<div id="newOrOldUserTable" size="100%" showCollapseButton="true" style="display:none;" >
						<div id="newOrOldUserDatagrid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
							url="userdata/findNewOldUserData" idField="id"
							pageSize="30" multiSelect="true" showColumnsMenu="true" showPagerButtonText="true" showPagerButtonIcon="true"
							showFilterRow="false" allowMoveColumn="true"
							showSummaryRow="true">
	
							<div property="columns">
								<div field="buyDate" headerAlign="center" align="center" dateFormat="yyyy-MM-dd" allowSort="true">日期</div>
								<div header="投注人数" headerAlign="center" align="center">
								<div property="columns">
									<div field="orderUserCount" headerAlign="center" align="center">所有</div>
									<div field="newUserCount" headerAlign="center" align="center">新用户</div>
									<div field="oldUserCount" headerAlign="center" align="center">老用户</div>
								</div>
								</div>
								<div header="投注金额"  headerAlign="center" align="center">
								<div property="columns">
									<div field="userMoney" headerAlign="center" align="center">所有</div>
									<div field="newUserMoney" headerAlign="center" align="center">新用户</div>
									<div field="oldUserMoney" headerAlign="center" align="center">老用户</div>
								</div>
								</div>
							</div>
						</div>
				</div>
				<div id="newOrOldUserChart">
					<div id="betUser" style="float: left; width: 700px; height: 300px;"></div>
					<div id="betAmount" style="float: left; width: 700px; height: 300px;"></div>
				</div>
		    </div>
		    <div name="silentUserTab" title="沉默用户">
					<div id="siclientUserTable" size="100%" showCollapseButton="true" style="display:none;">
					<!--  待定  暂注释
						 <div id="siclientUserDatagrid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
							url="userdata/findSilentUserData" idField="id"
							pageSize="30" multiSelect="true" showColumnsMenu="true"
							showFilterRow="false" allowMoveColumn="true"
							onpreload="MiniCom.onpreload"
							onrowdblclick="TicketObj.onrowdblclick()" showSummaryRow="true"
							ondrawsummarycell="TicketObj.drawSummaryCell">
							<div property="columns">
								<div field="date" headerAlign="center" align="center" width="10%">日期</div>
								<div header="未登录人数" headerAlign="center" align="center">
								<div property="columns">
									<div field="noLoginDay3" width="10%" headerAlign="center" align="center">近3天</div>
									<div field="noLoginDay7" width="7%" headerAlign="center" align="center">近7天</div>
									<div field="noLoginDay15" width="7%" headerAlign="center" align="center">近15天</div>
									<div field="noLoginDay30" width="7%" headerAlign="center" align="center">近30天</div>
									<div field="noLoginDay60" width="7%" headerAlign="center" align="center">近60天</div>
									<div field="noLoginDay90" width="7%" headerAlign="center" align="center">近90天</div>
								</div>
								</div>
								<div header="未投注人数" headerAlign="center" align="center">
								<div property="columns">
									<div field="noBuyDay3" width="10%" headerAlign="center" align="center">近3天</div>
									<div field="noBuyDay7" width="7%" headerAlign="center" align="center">近7天</div>
									<div field="noBuyDay15" width="7%" headerAlign="center" align="center">近15天</div>
									<div field="noBuyDay30" width="7%" headerAlign="center" align="center">近30天</div>
									<div field="noBuyDay60" width="7%" headerAlign="center" align="center">近60天</div>
									<div field="noBuyDay90" width="7%" headerAlign="center" align="center">近90天</div>
								</div>
								</div>
							</div>
						</div>
						 -->
					</div>
				<div id="siclientUserChart">
					<div id="noLoginUser"
						style="float: left; width: 700px; height: 300px;"></div>
					<div id="noBetUser"
						style="float: left; width: 700px; height: 300px;"></div>
				</div>
		    </div>
		</div>
	</div>	
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/echarts.min.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/report/userdata.js?version=<%=version%><%=System.currentTimeMillis() %>" type="text/javascript"></script>
</html>
