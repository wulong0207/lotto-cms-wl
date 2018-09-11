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
						<a data-channel="" class="active">全部</a>|
						<a data-channel= "1">本站</a>|
						<a data-channel= "3">本站移动端</a>|
						<a data-channel= "2">本站PC</a>|
						<a data-channel= "4">本站Android</a>|
						<a data-channel= "5">本站iOS</a>|
						<a data-channel= "6">本站wap</a>|
					</td>
					<td>
					可选渠道：
					
					<input id="channel" name="userRegisterChannel" class="mini-treeselect" url="userdata/channelTree" multiSelect="false" 
									 valueFromSelect="false" style="width: 300px;" onvaluechanged="report_brief.clearActive('statis-channel')"
								        textField="text" valueField="id" parentField="pid" allowInput="true" showRadioButton="true" showFolderCheckBox="true"/>
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
						<a data-time="6">本月</a>|
					</td>
					<td>选择查询时间：
					<input style="width:200px;" id="startTime" name="startTime" class="mini-datepicker" format="yyyy-MM-dd" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="report_brief.clearActive('statis-time')"/>
				        到
				    <input style="width:200px;" id="endTime" name="endTime" class="mini-datepicker"  format="yyyy-MM-dd" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="report_brief.clearActive('statis-time')"/>
					</td>
				</tr>
				<tr>
					<td width="20%" id="statis-platform">
						投注平台：
						<a class="active">全部</a>|
						<a data-platform="1">Web</a>|
						<a data-platform="2">Wap</a>|
						<a data-platform="3">Android</a>|
						<a data-platform="4">IOS</a>|
					</td>
					<td>
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
							<span style="font-weight:bold;">注册人数：</span></br> <span id="regUser" style="font-weight:bold;color:red;"></span></br> 
							<span></span></br> 
							<span>实名认证：<tag id="checkUser"></tag>人</span></br>
							<hr/>
							<span class="qudao">渠道注册：<tag id="regUserByChannel"></tag>人</span></br>
							<span class="qudao">渠道实名认证：<tag id="checkUserByChannel"></tag>人</span></br>
						</td>
						<td
							style="width: 25%; padding-left: 8%; border-right: 1px solid #D1D1D1;">
							<span style="font-weight:bold;">首充人数：</span></br> 
							<span style="font-weight:bold;color:red;" id="newFillUser"></span></br> <span></span></br> 
							<span>首充转化率：<tag id="newFillRatio"></tag></span></br>
							<hr/>
							<span class="qudao">渠道首充：<tag id="newFillUserByChannel"></tag>人</span></br>
							<span class="qudao">渠道转化率：<tag id="newFillRatioByChannel"></tag></span></br>
						</td>
						<td
							style="width: 25%; padding-left: 8%; border-right: 1px solid #D1D1D1;">
							<span style="font-weight:bold;">投注人数：</span></br> 
							<span style="font-weight:bold;color:red;" id="orderUser"></span></br> <span></span></br> 
							<span>投注次数：<tag id="orderTimes"></tag>次</span></br>
							<%--<span>投注会员数：<tag id="firstRechargeCountByChannel"></tag>人</span></br>--%>
							<span>活跃度：<tag id="liveness"></tag>次/人</span></br>
							<hr/>
							<span class="qudao">渠道投注人数：<tag id="orderUserByChannel"></tag></span></br>
						</td>
						<td
							style="width: 25%; padding-left: 8%; border-right: 1px solid #D1D1D1;">
							<span style="font-weight:bold;">投注额：</span></br> 
							<span style="font-weight:bold;color:red;" id="orderMoney"></span></br> <span></span></br> 
							<span>新用户投注额：<tag id="newOrderMoney"></tag>元</span></br>
							<span>人均投注额：<tag id="avgOrderMoney"></tag>元</span></br>
							<hr/>
							<span class="qudao">渠道投注额：<tag id="orderMoneyByChannel"></tag>元</span></br>
						</td>
					</tr>
				</tbody>
			</table>
			
   </div>
	<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:90%;margin-top:10px" contextMenu="#tabsMenu">
		<div class="mini-panel" title="新用户数据:" style="width:100%;height:500px;margin-top:10px" showCollapseButton="true">
		<div>
			<label><input name="userChartRadio" type="radio" checked="checked" />图表数据</label> 
			<label><input name="userChartRadio" type="radio" />明细数据</label>
			<button class="exportBtn" data-excel-url="report/brief/rookie">导出</button>
			
		</div>
		(备注： ①首充用户：第一次进行充值的用户            ②有效用户转化：首充用户/新注册用户 * 100%)
		<div id="userChart" style="width: 1200px; height: 500px;"></div>
		
		
		<div id="userGrid" class="mini-datagrid" style="display:none;" url="report/brief/rookie/paging" ajaxOptions="{type : 'get'}">
			<div property="columns">
				<div field="addDate" headerAlign="center" align="center">日期</div>
				<div field="regUser" headerAlign="center" align="center">注册用户</div>
				<div field="checkUser" headerAlign="center" align="center">完善用户</div>
				<div field="newFillUser" headerAlign="center" align="center">首充用户</div>
				<div field="checkRatio" headerAlign="center" align="center">完善转化</div>
				<div field="newFillRatio" headerAlign="center" align="center">首充转化(%)</div>
			</div>
		</div>
		
	</div>	
	
	<div class="mini-panel" title="全站投注情况:" style="width:100%;height:500px;margin-top:10px" showCollapseButton="true">
		<div>
			<label><input name="betChartRadio" type="radio" checked="checked" />图表数据</label> 
			<label><input name="betChartRadio" type="radio" />明细数据</label>
			<button class="exportBtn" data-excel-url="report/brief/distribution">导出</button>
		</div>
		(备注： ①arpu值：投注金额/投注人数（人均投注额）)
		<div id="betChart" style="width: 1200px; height: 500px;"></div>
		
		<div id="betGrid" class="mini-datagrid" style="display:none;" url="report/brief/rookie/paging" ajaxOptions="{type : 'get'}">
			<div property="columns">
				<div field="addDate" headerAlign="center" align="center">日期</div>
				<div header="所有用户" headerAlign="center" align="center">
					<div property="columns">
						<div field="orderUser" headerAlign="center" align="center">投注人数</div>
						<div field="orderMoney" headerAlign="center" align="center">投注金额</div>
						<div field="arpu" headerAlign="center" align="center">arpu值</div>
						<div field="orderTimes" headerAlign="center" align="center">投注次数</div>
						<div field="liveness" headerAlign="center" align="center">活跃度</div>
					</div>
				</div>
				<div header="新用户" headerAlign="center" align="center">
					<div property="columns">
						<div field="newOrderUser" headerAlign="center" align="center">投注人数</div>
						<div field="newOrderMoney" headerAlign="center" align="center">投注金额</div>
						<div field="newArpu" headerAlign="center" align="center">arpu值</div>
						<div field="newOrderTimes" headerAlign="center" align="center">投注次数</div>
						<div field="newLiveness" headerAlign="center" align="center">活跃度</div>
					</div>
				</div>
			</div>
		</div>
	</div>	
	
	<div class="mini-panel" title="彩种销售数据:" style="width:100%;height:500px;margin-top:10px" showCollapseButton="true">
		<div>
			<label><input name="lotterySailRadio" type="radio" value="1" checked="checked" />图表数据</label> 
			<label><input name="lotterySailRadio" type="radio" value="2" />明细数据</label>
			<%--<button class="exportBtn" data-excel-url="report/brief/rookie">导出</button>--%>
		</div>
		
			<div class="row" id="buyerData">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;投注用户分布   （新：代表首次投注某个彩种的用户）
				<div id="buyerChart" style="width: 900px; height: 350px;margin-left:20px;"></div>
				<div id="buyerList">
				<ul>
					
				</ul>
				</div>
			</div>
		
			<div id="buyerGrid" class="mini-datagrid" style="display:none;" url="report/brief/distribution/user/paging" ajaxOptions="{type : 'get'}" onload="report_brief.setHeader">
				<div property="columns">
				<div header="投注人数" headerAlign="center" align="center">
					<div property="columns">
						<div field="dt" headerAlign="center" align="center">日期</div>
						<div field="totalOrderUser" headerAlign="center" align="center">总投注人数</div>
						<div field="orderUser0" headerAlign="center" align="center"></div>
						<div field="orderUser1" headerAlign="center" align="center"></div>
						<div field="orderUser2" headerAlign="center" align="center"></div>
						<div field="orderUser3" headerAlign="center" align="center"></div>
						<div field="orderUser4" headerAlign="center" align="center"></div>
						<div field="orderUser5" headerAlign="center" align="center"></div>
						<div field="orderUser6" headerAlign="center" align="center"></div>
						<div field="orderUser7" headerAlign="center" align="center"></div>
					</div>
				</div>
				</div>
			</div>
					
			<div class="row" id="moneyData">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;投注金额分布   （新：代表首次投注某个彩种的用户）
				<div id="moneyChart" style="width: 900px; height: 350px;margin-left:20px;"></div>
				<div id="moneyList">
				<ul>
					
				</ul>
				</div>
			</div>
				
			<hr/>
			<div id="moneyGrid" class="mini-datagrid" style="display:none;" url="report/brief/distribution/money/paging" ajaxOptions="{type : 'get'}" onload="report_brief.setHeader">
				<div property="columns">
					<div header="投注金额" headerAlign="center" align="center">
					<div property="columns">
						<div field="dt" headerAlign="center" align="center">日期</div>
						<div field="totalOrderMoney" headerAlign="center" align="center">总投注金额</div>
						<div field="orderMoney0" headerAlign="center" align="center"></div>
						<div field="orderMoney1" headerAlign="center" align="center"></div>
						<div field="orderMoney2" headerAlign="center" align="center"></div>
						<div field="orderMoney3" headerAlign="center" align="center"></div>
						<div field="orderMoney4" headerAlign="center" align="center"></div>
						<div field="orderMoney5" headerAlign="center" align="center"></div>
						<div field="orderMoney6" headerAlign="center" align="center"></div>
						<div field="orderMoney7" headerAlign="center" align="center"></div>
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
<script src="<%=basePath%>resources/js/report/report_brief.js?version=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
</html>
