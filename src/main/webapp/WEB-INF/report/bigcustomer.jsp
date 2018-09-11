<%@page import="java.util.Date"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
	<title>大客户数据</title>
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
					<td width="10%" id="statis-channel" colspan="2">
							<!--注册渠道：
							 <a data-channel="0" class="active">全部</a>|
							<a data-channel="-1">本站</a>|
							<a data-channel="-2">本站移动端</a>|
							<a data-channel="-3">本站客户端</a>|
							<a data-channel="1">本站PC</a>|
							<a data-channel="3">本站Android</a>|
							<a data-channel="4">本站ios</a>|
							<a data-channel="2">本站wap</a>| -->
						&nbsp;注册渠道：<!-- <input id="channel" name="userRegisterChannel" class="mini-combobox" oncloseclick="Cms.onCloseClick"
														style="width: 150px;" emptyText="" allowInput="false" showClose="true" 
															onvaluechanged="bigCustomer.clearActive('statis-channel')" nullItemText="请选择..." showNullItem="true" /> -->
															
								<input id="channel" name="userRegisterChannel" class="mini-treeselect" url="userdata/channelTree" multiSelect="false" 
									 valueFromSelect="false" style="width: 200px;" onvaluechanged="bigCustomer.clearActive('statis-channel')"
								        textField="text" valueField="id" parentField="pid" allowInput="true" showRadioButton="true" showFolderCheckBox="true"/>
								&nbsp;提示：默认为空则查询全部渠道，下拉框可编辑、清空、搜索渠道
						</td>
				</tr>
				<tr>
					<td id="statis-lot" style="white-space: nowrap;">
						彩种类型：
						<a data-lot="" class="active">全部</a>|
						<a data-lot="100">双色球</a>|
						<a data-lot="102">大乐透</a>|
						<a data-lot="300">竞足</a>|
						<a data-lot="301">竞篮</a>|
		<!-- 				<a data-lot="304">胜负彩</a>|
						<a data-lot="305">任九</a>| -->
						<a data-lot="306">北单</a>|
						<a data-lot="215">十一运夺金</a>|
						<a data-lot="233">江苏快3</a>|
					其他彩种：<input id="lotteryType" class="mini-combobox" emptyText="请选择" allowInput="true" showClose="true" oncloseclick="Cms.onCloseClick" onvaluechanged="bigCustomer.lotteryTypeChange"/>
					--<input id="lotteryCode" class="mini-combobox" emptyText="请选择" allowInput="true" showClose="true" oncloseclick="Cms.onCloseClick" onvaluechanged="bigCustomer.clearActive('statis-lot')"/>
					</td>
				</tr>
				<tr>
					<td id="statis-time" style="white-space: nowrap; padding-left: 5px;">
						时间：
						<a data-time="0" class="active">昨天</a>|
						<a data-time="1">今天</a>|
						<a data-time="2">上周</a>|
						<a data-time="3">本周</a>|
						<a data-time="4">上月</a>|
						<a data-time="5">本月</a>|
					选择查询时间：<input id="startTime" name="startTime" class="mini-datepicker" oncloseclick="Cms.onCloseClick"
						allowInput="false" style="width: 200px;" onvaluechanged="bigCustomer.clearActive('statis-time')" format="yyyy-MM-dd"
						showClearButton="true" showClose="true"/> 
						到
						<input id="endTime" name="endTime" class="mini-datepicker" allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
							oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true" onvaluechanged="bigCustomer.clearActive('statis-time')"/>
					</td>
				</tr>
				<tr>
					<td>
						投注金额：<input id="maxMoney" class="mini-textbox" vtype="int" value="2000"/>
						<span style="color:#949494">注：统计单个用户大于或等于该数值的投注金额数据</span>
						<button  onclick="bigCustomer.search()" style="width:60px;">查询</button>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="mini-panel" style="width:100%;height:80%;margin-top:10px" showCollapseButton="true">
		<span id="excelBtn">
							<btn:operate privilege="EXPORT">
							      <button  iconCls="icon-download" plain="true" onclick="bigCustomer.excel()">导出明细数据</button>
							</btn:operate>
						</span>
		<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:90%;margin-top:10px" contextMenu="#tabsMenu" onactivechanged="bigCustomer.activechanged">
		    <div name="betDataTab" title="投注数据">
				<div id="betDataChart" style="margin-top:20px">
					<div id="betUserCountChart" style="float:left;width: 700px; height: 400px;"></div>
					<div id="betMoneyChart" style="float:left;width: 700px; height: 400px;"></div>
				</div>
		    </div>
		    <div name="platformTab" title="投注平台">
				<div id="platformSaleChart" style="margin-top:20px;width: 700px; height: 300px;"></div>
		    </div>
		    <div name="betLotTab" title="投注彩种" style="width:900px">
		    	<div id="alertMsg" style="display:none;color:red;">提示：没有符合筛选条件的数据哦！</div>
				<div id="betLotChart"  style="width:900px">
					<div id="lotMoneyChart" style="width: 1200px; height: 600px;margin-top:20px;"></div>
					<div id="lotUserCountChart" style="width: 900px; height: 400px;margin-top:20px;"></div>
				</div>
		    </div>
		    <div name="detailDataTab" title="明细数据">
				<div size="100%" showCollapseButton="true" id="detailDataTable">
						<div id="detailDatagrid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
							url="bigcustomer/detailDataList" idField="id"
							pageSize="30" multiSelect="true" showColumnsMenu="true"
							showFilterRow="false" allowMoveColumn="true"
							showSummaryRow="true">
	
							<div property="columns">
								<div type="indexcolumn" width="3%">序列号</div>
								<div field="userName" headerAlign="center" align="center" width="10%">用户名</div>
								<div field="rechargeMoney" headerAlign="center" align="center" width="17%">充值金额</div>
								<div field="orderMoney" headerAlign="center" align="center" width="20%">投注金额</div>
								<div field="prizeMoney" headerAlign="center" align="center" width="15%">中奖金额</div>
								<div field="profitRate" headerAlign="center" align="center" width="10%" renderer="bigCustomer.addRateCell">盈利率(中奖-投注)/投注</div>
								<div field="betValue" headerAlign="center" align="center" width="15%" renderer="bigCustomer.addCell">流水价值(投注金额/充值金额)</div>
							</div>
						</div>
				</div>
		    </div>
		</div>
	</div>	
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/echarts.min.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/report/bigcustomer.js?version=<%=version%><%=System.currentTimeMillis() %>" type="text/javascript"></script>
</html>
