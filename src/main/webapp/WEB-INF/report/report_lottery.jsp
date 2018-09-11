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
<title></title>

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
					<td width="20%" id="statis-channel">
						注册渠道：
						<a data-channel="0" class="active">全部</a>|
						<a data-channel= "1">本站</a>|
						<a data-channel= "3">本站移动端</a>|
						<a data-channel= "2">本站PC</a>|
						<a data-channel= "4">本站Android</a>|
						<a data-channel= "5">本站IOS</a>|
						<a data-channel= "6">本站WAP</a>|
					</td>
					<td>
					可选渠道：
					<!--<input id="channel" class="mini-combobox" emptyText="请选择" allowInput="true" showClose="true" onvaluechanged="report_lottery.clearActive('statis-channel')"/>-->
					<input id="channel" name="userRegisterChannel" class="mini-treeselect" url="userdata/channelTree" multiSelect="false" 
									 valueFromSelect="false" style="width: 300px;" onvaluechanged="report_lottery.clearActive('statis-channel')" allowInput="true" showRadioButton="true" showFolderCheckBox="true"/>
					</td>
				</tr>
				<tr>
					<td width="20%" id="statis-lotteryCode">
						彩种类型：
						<a data-lotteryCode="0" class="active">全部</a>|
						<a data-lotteryCode= "100">双色球</a>|
						<a data-lotteryCode= "102">大乐透</a>|
						<a data-lotteryCode="300">竞足</a>|
						<a data-lotteryCode= "301">竞篮</a>|
						<a data-lotteryCode= "304">胜负彩</a>|
						<a data-lotteryCode= "305">任九</a>|
						<a data-lotteryCode= "306">北单</a>|
						<a data-lotteryCode= "215">十一运夺金</a>|
						<a data-lotteryCode= "233">江苏快3</a>|
					</td>
					<td>
					其他彩种：<input id="lotteryType" class="mini-combobox" emptyText="请选择" allowInput="true" showClose="true" onvaluechanged="report_lottery.lotteryTypeChange" oncloseclick="Cms.onCloseClick"/>
					--<input id="lotteryCode" class="mini-combobox" emptyText="请选择" allowInput="true" showClose="true" onvaluechanged="report_lottery.clearActive('statis-lotteryCode')" oncloseclick="Cms.onCloseClick"/>
					</td>
				</tr>
				<tr>
					<td width="20%" id="statis-platform">
						投注平台：
						<a data-platform="0" class="active">全部</a>|
						<a data-platform="1">Web</a>|
						<a data-platform="2">Wap</a>|
						<a data-platform="3">Android</a>|
						<a data-platform="4">IOS</a>|
					</td>
				</tr>
				<tr>
					<td width="20%" id="statis-time">
						投注时间：
						<a data-time="1" class="active">昨天</a>|
						<a data-time="2">今天</a>|
						<a data-time="3">上周</a>|
						<a data-time="4">本周</a>|
						<a data-time="5">上月</a>|
						<a data-time="6" >本月</a>|
					</td>
					<td>选择查询时间：
					<input style="width:200px;" id="startTime" name="startTime" class="mini-datepicker" format="yyyy-MM-dd" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="report_lottery.clearActive('statis-time')"/>
				        到
				    <input style="width:200px;" id="endTime" name="endTime" class="mini-datepicker"  format="yyyy-MM-dd" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="report_lottery.clearActive('statis-time')"/>
					 <button id="searchBtn">查询</button>
					</td>
				</tr>
				
			</table>
	</div>
	
	<div class="mini-panel" title="数据概况 :" style="width:100%;height:200px;margin-top:10px;text-align: center;" showCollapseButton="true">
			<table style="width: 100%; height: 100%;" cellpadding="2"
				cellspacing="0" border="0" bordercolor="#000000" align="center" style="background-color:rgba(242, 242, 242, 1)">
				<tbody>
					<tr style="width: 100%;font-size:14px">
						<td
							style="width: 25%; padding-left: 8%; border-right: 1px solid #D1D1D1;">
							<span style="font-weight:bold;">投注用户：</span></br> <span id="orderUser" style="font-weight:bold;color:red;"></span></br> 
							<hr/>
							<span>新用户投注人数：<tag id="newOrderUser"></tag></span></br>
						</td>
						<td
							style="width: 25%; padding-left: 8%; border-right: 1px solid #D1D1D1;">
							<span style="font-weight:bold;">投注金额：</span></br> 
							<span style="font-weight:bold;color:red;" id="orderMoney"></span></br> <span></span></br> 
							<hr/>
							<span>新用户投注金额：<tag id="newOrderMoney"></tag></span></br>
						</td>
						<td
							style="width: 25%; padding-left: 8%; border-right: 1px solid #D1D1D1;">
							<span style="font-weight:bold;">人均投注：</span></br> 
							<span style="font-weight:bold;color:red;" id="avgOrderMoney"></span></br> <span></span></br> 
							<hr/>
							<span>新用户人均投注：<tag id="newAvgOrderMoney"></tag></span></br>
						</td>
						<td
							style="width: 25%; padding-left: 8%; border-right: 1px solid #D1D1D1;">
							<span style="font-weight:bold;">活跃度：</span></br>
							<span style="font-weight:bold;color:red;" id="liveness"></span>次/人</br> <span></span></br> 
							<hr/>
							<span>新用户活跃度：<tag id="newLiveness"></tag>次/人</span></br>
						</td>
					</tr>
				</tbody>
			</table>
			
   </div>
	

	
	
	
	<div class="mini-panel" title="详细数据:" style="width:100%;height:500px;margin-top:10px" showCollapseButton="true">
      <div id="tabs" name="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
			<div title="投注人数">
				<div>
					<label><input name="orderUserRadio" type="radio"
						value="1" checked="checked" />图表数据</label> <label><input
						name="orderUserRadio" type="radio" value="2" />明细数据</label>
					<button class="exportBtn"
						data-excel-url="report/lottery/orderuser">导出</button>
					 (说明:n-m表示n&lt;查询数据&lt;=m)		
				</div>

				<div class="row" id="orderUserData">
					<div id="orderUserChart"
						style="width: 900px; height: 350px; margin-left: 20px;"></div>
					<div id="orderUserList">
						
						<table border="1" >
						
						</table>
						
					</div>
				</div>


				<div id="orderUserGrid" class="mini-datagrid" style="display: none;"
					url="report/lottery/orderrange/paging" ajaxOptions="{type : 'get'}">
					<div property="columns">
						<div field="dt" headerAlign="center" align="center">日期</div>
						<div field="orderUser" headerAlign="center" align="center">投注人数</div>
						<div field="u_1_100" headerAlign="center" align="center">1-100元</div>
						<div field="u_101_500" headerAlign="center" align="center">100-500元</div>
						<div field="u_501_1000" headerAlign="center" align="center">500-1000元</div>
						<div field="u_1001_5000" headerAlign="center" align="center">1000-5000元</div>
						<div field="u_5001_10000" headerAlign="center" align="center">5000-10000元</div>
						<div field="u_10001_50000" headerAlign="center" align="center">10000-50000元</div>
						<div field="u_50001_100000" headerAlign="center" align="center">50000-100000元</div>
						<div field="u_100000" headerAlign="center" align="center">10万元以上</div>
					</div>
				</div>
			</div>
			
			
			<div title="投注金额">
				<div>
					<label><input name="orderMoneyRadio" type="radio"
						value="1" checked="checked" />图表数据</label> <label><input
						name="orderMoneyRadio" type="radio" value="2" />明细数据</label>
					<button class="exportBtn"
						data-excel-url="report/lottery/ordermoney">导出</button>
				</div>

				<div class="row" id="orderMoneyData">
					<div id="orderMoneyChart"
						style="width: 900px; height: 350px; margin-left: 20px;"></div>
					<div id="orderMoneyList">
						<table border="1">
						
						</table>
					</div>
				</div>


				<div id="orderMoneyGrid" class="mini-datagrid" style="display: none;"
					url="report/lottery/orderrange/paging" ajaxOptions="{type : 'get'}">
					<div property="columns">
						<div field="dt" headerAlign="center" align="center">日期</div>
						<div field="totalMoney" headerAlign="center" align="center">投注金额</div>
						<div field="o_1_100" headerAlign="center" align="center">1-100元</div>
						<div field="o_101_500" headerAlign="center" align="center">100-500元</div>
						<div field="o_501_1000" headerAlign="center" align="center">500-1000元</div>
						<div field="o_1001_5000" headerAlign="center" align="center">1000-5000元</div>
						<div field="o_5001_10000" headerAlign="center" align="center">5000-10000元</div>
						<div field="o_10001_50000" headerAlign="center" align="center">10000-50000元</div>
						<div field="o_50001_100000" headerAlign="center" align="center">50000-100000元</div>
						<div field="o_100000" headerAlign="center" align="center">10万元以上</div>
					</div>
				</div>
			</div>


			<div title="玩法分布">
				<div>
					<label><input name="lotteryChildRadio" type="radio"
						checked="checked" />图表数据</label> <label><input
						name="lotteryChildRadio" type="radio" />明细数据</label>
						<%--
					<button class="exportBtn"
						data-excel-url="report/lotttery/child">导出</button>
						--%>
				</div>

				<div class="row" id="lotteryChildData">
					<div id="lotteryChildChart"
						style="width: 900px; height: 350px; margin-left: 20px;"></div>
					<div id="lotteryChildList">
						<ul>
							
						</ul>
						
					</div>
				</div>

				
				<div id="lotteryChildGrid" class="mini-datagrid" style="display: none;"
					url="report/lottery/child/paging"
					ajaxOptions="{type : 'get'}" onload="report_lottery.setLotteryChildHeader">
					<div property="columns">
						<div field="addDate" headerAlign="center" align="center">日期</div>
						<div header="所有" headerAlign="center" align="center">
							<div property="columns">
								<div field="orderUser" headerAlign="center" align="center">投注人数</div>
								<div field="orderMoney" headerAlign="center" align="center">投注金额</div>
								<div field="arpu" headerAlign="center" align="center">arpu值</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
		<div title="投注方式">
	        	<div>
					<label><input name="lotteryWayRadio" type="radio"
						value="1" checked="checked" />图表数据</label> <label><input
						name="lotteryWayRadio" type="radio" value="2" />明细数据</label>
					<button class="exportBtn"
						data-excel-url="report/lottery/way">导出</button>
				</div>

				<div class="row" id="lotteryWayData">
					<div id="lotteryMoneyChart" style="width: 400px; height: 350px; margin-left: 20px;" headerAlign="center">投注金额</div>
					<div id="lotteryUserChart" style="width: 400px; height: 350px; margin-left: 20px;" headerAlign="center">投注人数</div>
					<div id="lotteryWayList">
						<ul>
							
						</ul>
					</div>
				</div>


				<div id="lotteryWayGrid" class="mini-datagrid" style="display: none;"
					url="report/lottery/way/paging" ajaxOptions="{type : 'get'}">
					<div property="columns">
						<div field="dt" headerAlign="center" align="center">日期</div>
						<div header="所有" headerAlign="center" align="center">
							<div property="columns">
								<div field="orderUser" headerAlign="center" align="center">投注人数</div>
								<div field="orderMoney" headerAlign="center" align="center">投注金额</div>
							</div>
						</div>
						<div header="代购" headerAlign="center" align="center">
							<div property="columns">
								<div field="buyUser" headerAlign="center" align="center">投注人数</div>
								<div field="buyUserRatio" headerAlign="center" align="center">占比</div>
								<div field="buyMoney" headerAlign="center" align="center">投注金额</div>
								<div field="buyMoneyRatio" headerAlign="center" align="center">占比</div>
							</div>
						</div>
						<div header="追号" headerAlign="center" align="center">
							<div property="columns">
								<div field="addUser" headerAlign="center" align="center">投注人数</div>
								<div field="addUserRatio" headerAlign="center" align="center">占比</div>
								<div field="addMoney" headerAlign="center" align="center">投注金额</div>
								<div field="addMoneyRatio" headerAlign="center" align="center">占比</div>
							</div>
						</div>
						<div header="合买" headerAlign="center" align="center">
							<div property="columns">
								<div field="groupUser" headerAlign="center" align="center">投注人数</div>
								<div field="groupUserRatio" headerAlign="center" align="center">占比</div>
								<div field="groupMoney" headerAlign="center" align="center">投注金额</div>
								<div field="groupMoneyRatio" headerAlign="center" align="center">占比</div>
							</div>
						</div>
					</div>
				</div>
	    </div>
	    
	    
	    
	    <div title="返奖数据">
	        				<div>
					<label><input name="winningRadio" type="radio"
						checked="checked" />图表数据</label> <label><input
						name="winningRadio" type="radio" />明细数据</label>
			<!-- 		<button class="exportBtn"
						data-excel-url="report/lotttery/winning">导出</button> -->
						
				</div>

				<div class="row" id="winningData">
					<div id="winningChart"
						style="width: 1000px; height: 350px; margin-left: 20px;"></div>
					<div id="winningList">
						<ul>
							
						</ul>
						
						<table border="1">
						
						</table>
					</div>
				</div>

				<div id="winningGrid" class="mini-datagrid" style="display: none;"
					url="report/lottery/winning/paging"
					ajaxOptions="{type : 'get'}" onload="report_lottery.setWinningHeader">
					<div property="columns">
						<div field="addDate" headerAlign="center" align="center">日期</div>
						<div header="所有" headerAlign="center" align="center">
							<div property="columns">
								<div field="orderMoney" headerAlign="center" align="center">投注金额</div>
								<div field="winningMoney" headerAlign="center" align="center">中奖金额</div>
								<div field="winningRatio" headerAlign="center" align="center">中奖金额</div>
							</div>
						</div>
					</div>
				</div>
	    </div>
	    
	    
   	  </div> 
	</div>
			

</body>
<script src="<%=basePath%>resources/js/public/ie-fix.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/echarts.min.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/report/report_common.js?version=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/report/report_lottery.js?version=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
</html>
