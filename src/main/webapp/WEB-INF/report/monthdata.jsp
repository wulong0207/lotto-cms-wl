<%@page import="java.util.Date"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
	<title>月度报表</title>
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
															onvaluechanged="monthData.clearActive('statis-channel')" nullItemText="请选择..." showNullItem="true" /> -->
															
								<input id="channel" name="userRegisterChannel" class="mini-treeselect" url="userdata/channelTree" multiSelect="false" 
									 valueFromSelect="false" style="width: 200px;" onvaluechanged="monthData.clearActive('statis-channel')"
								        textField="text" valueField="id" parentField="pid" allowInput="true" showRadioButton="true" showFolderCheckBox="true"/>
							&nbsp;提示：默认为空则查询全部渠道，下拉框可编辑、清空、搜索渠道
						</td>
				</tr>
				<tr>
					<td id="statis-time" width="20%" style="white-space: nowrap; padding-left: 5px;">
						时间：
						<a data-time="0" class="active">上月</a>|
						<a data-time="1">本月</a>|
						<a data-time="2">本季度</a>|
						<a data-time="3">上季度</a>|
					</td>
					<td>选择查询时间：<input id="startTime" name="startTime" class="mini-monthpicker" oncloseclick="Cms.onCloseClick"
						allowInput="false" style="width: 100px;" onvaluechanged="monthData.clearActive('statis-time')" format="yyyy-MM"
						showClearButton="true" showClose="true"/> 
						到
						<input id="endTime" name="endTime" class="mini-monthpicker" allowInput="false" style="width: 100px;margin-right:15px" format="yyyy-MM"
							oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true" onvaluechanged="monthData.clearActive('statis-time')"/>
					    <a class="mini-button" onclick="monthData.search()" style="width:60px;">查询</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="mini-panel" title="明细数据 :" style="width:100%;height:90%;margin-top:10px">
		<div>
			<label><input name="chartDetailData" type="radio" value="1" checked="checked" />图表数据</label> 
			<label><input name="chartDetailData" type="radio" value="2" />明细数据</label>
			<span id="excelBtn" style="display:none">
				<btn:operate privilege="EXPORT">
				      <a class="mini-button" iconCls="icon-download" plain="true" onclick="monthData.excel()">导出</a>
				</btn:operate>
			</span>
		</div>
		
		<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:90%;margin-top:10px" contextMenu="#tabsMenu" onactivechanged="monthData.activechanged">
		    <div name="platformTab" title="平台数据">
				<div size="100%" showCollapseButton="true" id="platformDataTable" style="display:none">
						<div id="platformDatagrid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
							url="monthdata/platformList" idField="id"
							pageSize="30" multiSelect="true" showColumnsMenu="true"
							showFilterRow="false" allowMoveColumn="true" showSummaryRow="true">
	
							<div property="columns">
								<div field="yearMonth" headerAlign="center" align="center" width="8%">月份</div>
								<div header="所有" headerAlign="center" align="center">
									<div property="columns">
										<div field="allBetUserCount" headerAlign="center" align="center" width="15%">投注人数</div>
										<div field="allBetMoney" headerAlign="center" align="center" width="20%">投注金额</div>
									</div>
								</div>
								<div header="Web" headerAlign="center" align="center">
									<div property="columns">
										<div field="webBetUserCount" headerAlign="center" align="center" width="10%">投注人数</div>
										<div field="webBetMoney" headerAlign="center" align="center" width="15%">投注金额</div>
									</div>
								</div>
								<div header="Wap" headerAlign="center" align="center">
									<div property="columns">
										<div field="wapBetUserCount" headerAlign="center" align="center" width="10%">投注人数</div>
										<div field="wapBetMoney" headerAlign="center" align="center" width="15%">投注金额</div>
									</div>
								</div>
								<div header="Android" headerAlign="center" align="center">
									<div property="columns">
										<div field="androidBetUserCount" headerAlign="center" align="center" width="10%">投注人数</div>
										<div field="androidBetMoney" headerAlign="center" align="center" width="15%">投注金额</div>
									</div>
								</div>
								<div header="Ios" headerAlign="center" align="center">
									<div property="columns">
										<div field="iosBetUserCount" headerAlign="center" align="center" width="10%">投注人数</div>
										<div field="iosBetMoney" headerAlign="center" align="center" width="15%">投注金额</div>
									</div>
								</div>
							</div>
						</div>
				</div>
				<div id="platformChart" style="margin-top:20px;">
					<div id="platformSaleMoneyChart" style="float: left;width: 700px; height: 400px;"></div>
					<div id="platformBuyCountChart" style="float: left;width: 700px; height: 400px;"></div>
				</div>
		    </div>
		    <div name="lotTab" title="彩种数据">
					<div id="lotSaleDataTable" size="100%" showCollapseButton="true" style="display:none;" >
						<div id="lotDatagrid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
							url="monthdata/lotList" idField="id"
							pageSize="30" multiSelect="true" showColumnsMenu="true"
							showFilterRow="false" allowMoveColumn="true" showSummaryRow="true">
	
							<div property="columns">
								<div field="yearMonth" headerAlign="center" align="center" width="4%">月份</div>
								<div header="双色球" headerAlign="center" align="center">
									<div property="columns">
										<div field="ssqBetUserCount" headerAlign="center" align="center" width="7%">投注人数</div>
										<div field="ssqBetMoney" headerAlign="center" align="center" width="8%">投注金额</div>
									</div>
								</div>
								<div header="大乐透" headerAlign="center" align="center">
									<div property="columns">
										<div field="dltBetUserCount" headerAlign="center" align="center" width="6%">投注人数</div>
										<div field="dltBetMoney" headerAlign="center" align="center" width="6%">投注金额</div>
									</div>
								</div>
								<div header="竞彩足球" headerAlign="center" align="center">
									<div property="columns">
										<div field="jzBetUserCount" headerAlign="center" align="center" width="6%">投注人数</div>
										<div field="jzBetMoney" headerAlign="center" align="center" width="8%">投注金额</div>
									</div>
								</div>
								<div header="竞彩篮球" headerAlign="center" align="center">
									<div property="columns">
										<div field="jlBetUserCount" headerAlign="center" align="center" width="6%">投注人数</div>
										<div field="jlBetMoney" headerAlign="center" align="center" width="6%">投注金额</div>
									</div>
								</div>
								<div header="北京单场" headerAlign="center" align="center">
									<div property="columns">
										<div field="bdBetUserCount" headerAlign="center" align="center" width="6%">投注人数</div>
										<div field="bdBetMoney" headerAlign="center" align="center" width="6%">投注金额</div>
									</div>
								</div>
								<div header="重庆时时彩" headerAlign="center" align="center">
									<div property="columns">
										<div field="cqsscBetUserCount" headerAlign="center" align="center" width="6%">投注人数</div>
										<div field="cqsscBetMoney" headerAlign="center" align="center" width="6%">投注金额</div>
									</div>
								</div>
								<div header="十一运夺金" headerAlign="center" align="center">
									<div property="columns">
										<div field="syydjBetUserCount" headerAlign="center" align="center" width="4%">投注人数</div>
										<div field="syydjBetMoney" headerAlign="center" align="center" width="6%">投注金额</div>
									</div>
								</div>
								<div header="江苏快三" headerAlign="center" align="center">
									<div property="columns">
										<div field="jsk3BetUserCount" headerAlign="center" align="center" width="4%">投注人数</div>
										<div field="jsk3BetMoney" headerAlign="center" align="center" width="6%">投注金额</div>
									</div>
								</div>
							</div>
						</div>
				</div>
				<div id="lotDataChart" style="margin-top:20px;">
					<div id="lotSaleMoney" style="width: 1200px; height: 400px;"></div>
					<div id="lotSaleCount" style="width: 1200px; height: 400px;"></div>
				</div>
		    </div>
		    <div name="buyUserCountTab" title="投注人数">
				<div size="100%" showCollapseButton="true" id="buyUserCountTable" style="display:none">
						<div id="buyUserCountDatagrid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
							url="monthdata/betList" idField="id"
							pageSize="30" multiSelect="true" showColumnsMenu="true"
							showFilterRow="false" allowMoveColumn="true" showSummaryRow="true">
	
							<div property="columns">
								<div field="yearMonth" headerAlign="center" align="center" width="5%">月份</div>
								<div field="totalUser" headerAlign="center" align="center" width="20%">投注人数</div>
								<div field="u_1_100" headerAlign="center" align="center" width="20%">1-100元</div>
								<div field="u_101_500" headerAlign="center" align="center" width="15%">101-500元</div>
								<div field="u_501_1000" headerAlign="center" align="center" width="15%">501-1000元</div>
								<div field="u_1001_5000" headerAlign="center" align="center" width="15%">1001-5000元</div>
								<div field="u_5001_10000" headerAlign="center" align="center" width="15%">5001-10000元</div>
								<div field="u_10001_50000" headerAlign="center" align="center" width="15%">10001-50000元</div>
								<div field="u_50001_100000" headerAlign="center" align="center" width="15%">50001-100000元</div>
								<div field="u_100000" headerAlign="center" align="center" width="15%">10万元以上</div>
							</div>
						</div>
				</div>
				<div id="buyUserCount" style="margin-top:20px">
					<div id="buyUserCountChart" style="float: left;width: 1200px; height: 400px;margin-top:20px;"></div>
					<div id="buyUserCountData" style="float: left;width: 500px; height: 400px;margin-top:100px;">
						<p>1-100元     投注用户：<span id="100_count"></span>人（新：<span id="100_new_count"></span>人）</p>
						<p>101-500元     投注用户：<span id="500_count"></span>人（新：<span id="500_new_count"></span>人）</p>
						<p>501-1000元     投注用户：<span id="1000_count"></span>人（新：<span id="1000_new_count"></span>人）</p>
						<p>1001-5000元     投注用户：<span id="5000_count"></span>人（新：<span id="5000_new_count"></span>人）</p>
						<p>5001-10000元     投注用户：<span id="10000_count"></span>人（新：<span id="10000_new_count"></span>人）</p>
						<p>10001-50000元     投注用户：<span id="50000_count"></span>人（新：<span id="50000_new_count"></span>人）</p>
						<p>50001-100000元     投注用户：<span id="100000_count"></span>人（新：<span id="100000_new_count"></span>人）</p>
						<p>10万元以上     投注用户：<span id="100000_n_count"></span>人（新：<span id="100000_n_new_count"></span>人）</p>
					</div>
				</div>
		    </div>
		    <div name="buyUserMoneyTab" title="投注金额">
				<div size="100%" showCollapseButton="true" id="buyMoneyTable" style="display:none">
						<div id="buyMoneyDatagrid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
							url="monthdata/betList" idField="id"
							pageSize="30" multiSelect="true" showColumnsMenu="true"
							showFilterRow="false" allowMoveColumn="true" showSummaryRow="true">
	
							<div property="columns">
								<div field="yearMonth" headerAlign="center" align="center" width="5%">月份</div>
								<div field="totalMoney" headerAlign="center" align="center" width="25%">投注金额</div>
								<div field="o_1_100" headerAlign="center" align="center" width="15%">1-100元</div>
								<div field="o_101_500" headerAlign="center" align="center" width="15%">101-500元</div>
								<div field="o_501_1000" headerAlign="center" align="center" width="15%">501-1000元</div>
								<div field="o_1001_5000" headerAlign="center" align="center" width="15%">1001-5000元</div>
								<div field="o_5001_10000" headerAlign="center" align="center" width="15%">5001-10000元</div>
								<div field="o_10001_50000" headerAlign="center" align="center" width="15%">10001-50000元</div>
								<div field="o_50001_100000" headerAlign="center" align="center" width="15%">50001-100000元</div>
								<div field="o_100000" headerAlign="center" align="center" width="15%">10万元以上</div>
							</div>
						</div>
				</div>
				<div id="buyMoney" style="margin-top:20px">
					<div id="buyMoneyChart" style="float: left;margin-top:20px;width: 1200px; height: 400px;"></div>
					<div id="buyMoneyData" style="float: left;width: 500px; height: 400px;margin-top:100px;">
						<p>1-100元     投注金额：<span id="100_money"></span>万（新：<span id="100_new_money"></span>万）</p>
						<p>101-500元     投注金额：<span id="500_money"></span>万（新：<span id="500_new_money"></span>万）</p>
						<p>501-1000元     投注金额：<span id="1000_money"></span>万（新：<span id="1000_new_money"></span>万）</p>
						<p>1001-5000元     投注金额：<span id="5000_money"></span>万（新：<span id="5000_new_money"></span>万）</p>
						<p>5001-10000元     投注金额：<span id="10000_money"></span>万（新：<span id="10000_new_money"></span>万）</p>
						<p>10001-50000元     投注金额：<span id="50000_money"></span>万（新：<span id="50000_new_money"></span>万）</p>
						<p>50001-100000元     投注金额：<span id="100000_money"></span>万（新：<span id="100000_new_money"></span>万）</p>
						<p>10万元以上     投注金额：<span id="100000_n_money"></span>万（新：<span id="100000_n_new_money"></span>万）</p>
					</div>
				</div>
		    </div>
		    <div name="registerTab" title="注册与有效">
				<div size="100%" showCollapseButton="true" id="registerTable" style="display:none">
						<div id="registerDatagrid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
							url="monthdata/registerList" idField="id"
							pageSize="30" multiSelect="true" showColumnsMenu="true"
							showFilterRow="false" allowMoveColumn="true"
							showSummaryRow="true">
	
							<div property="columns">
								<div field="yearMonth" headerAlign="center" align="center" width="5%">月份</div>
								<div field="newUserCount" headerAlign="center" align="center" width="20%">新注册用户</div>
								<div field="perfectUserCount" headerAlign="center" align="center" width="20%">完善用户 </div>
								<div field="firstRechargeCount" headerAlign="center" align="center" width="15%">首充用户</div>
								<div field="firstBuyCount" headerAlign="center" align="center" width="15%">首投用户</div>
								<div field="perfectRate" headerAlign="center" align="center" width="15%" renderer="monthData.addRateCell">完善转化率</div>
								<div field="firstRechargeRate" headerAlign="center" align="center" width="10%" renderer="monthData.addRateCell">首充转换率</div>
							</div>
						</div>
				</div>
				<div id="registerDataChart" style="margin-top:20px">
					<div id="registerChart" style="float: left;width: 700px; height: 400px;"></div>
					<div id="newAddChart" style="float: left;width: 700px; height: 400px;"></div>
				</div>
		    </div>
	<!-- 	    <div name="silentUserTab" title="沉默用户">
					<div id="siclientUserTable" size="100%" showCollapseButton="true" style="display:none;">
					 待定  暂注释
						 <div id="siclientUserDatagrid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
							url="userdata/findSilentUserData" idField="id"
							pageSize="30" multiSelect="true" showColumnsMenu="true"
							showFilterRow="false" allowMoveColumn="true"
							onpreload="MiniCom.onpreload"
							onrowdblclick="TicketObj.onrowdblclick()" showSummaryRow="true"
							ondrawsummarycell="TicketObj.drawSummaryCell">
							<div property="columns">
								<div field="date" headerAlign="center" align="center" width="10%">月份</div>
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
						
					</div>
				<div id="siclientUserChart" style="margin-top:20px">
					<div id="noLoginUser"
						style="float: left; width: 700px; height: 400px;"></div>
					<div id="noBetUser"
						style="float: left; width: 700px; height: 400px;"></div>
				</div>
		    </div> -->
		</div>
	</div>	
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/echarts.min.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/report/monthdata.js?version=<%=version%><%=System.currentTimeMillis() %>" type="text/javascript"></script>
</html>
