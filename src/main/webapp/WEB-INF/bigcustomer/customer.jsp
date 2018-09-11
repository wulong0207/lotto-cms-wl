<%@page import="java.util.Date"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=basePath%>resources/js/public/boot.js"
	type="text/javascript"></script>
<title>用户数据</title>
<style type="text/css">
table {
	border-collapse: collapse;
}

.table-bor td {
	border: 1px solid #CCCCCC;
}
</style>
</head>
<body>
	<div class="mini-toolbar"
		style="border-top: 0; border-left: 0; padding: 0px;">
		<div id="form">
			<table style="width: 100%;">
				<tr>
					<td width="15%"><label style="font-weight:bold;">注册渠道：</label><input id="userRegisterChannel"
						name="userRegisterChannel" class="mini-combobox"
						style="width: 150px;" emptyText="" allowInput="true" value=""
						onCloseClick="userdata.comboxOnCloseClick" showClose="true"
						nullItemText="请选择..." showNullItem="true" />

					</td>
				</tr>
				<tr>
					<td width="15%"><label style="font-weight:bold;">彩种类型：</label><input id="lotteryType"
						name="lotteryType" class="mini-combobox" style="width: 150px;"
						emptyText="" allowInput="true" value=""
						onCloseClick="userdata.comboxOnCloseClick" showClose="true"
						nullItemText="请选择..." showNullItem="true" />

					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap;"
						id="fixedTime"><label style="font-weight:bold;">时间：</label><a
						onclick="javascript:userdata.checkFixedTime(0)"
						style="cursor: hand">昨天</a>|<a
						onclick="userdata.checkFixedTime(1)" style="cursor: hand">今天</a>|<a
						onclick="javascript:userdata.checkFixedTime(2)"
						style="cursor: hand">上周</a>|<a
						onclick="javascript:userdata.checkFixedTime(3)"
						style="cursor: hand">本周</a>|<a
						onclick="javascript:userdata.checkFixedTime(4)"
						style="cursor: hand">上月</a>|<a
						onclick="javascript:userdata.checkFixedTime(5)"
						style="cursor: hand">本月</a>

					</td>
					<td style="padding-left: 2px;"><label style="font-weight:bold;">选择查询时间：</label><input
						id="matchBeginTime" name="matchBeginTime" class="mini-datepicker"
						allowInput="false" style="width: 200px;"
						format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
						showOkButton="true" showClearButton="false" /> 到<input
						id="matchEndTime" name="matchEndTime" class="mini-datepicker"
						allowInput="false" style="width: 200px;"
						format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
						showOkButton="true" showClearButton="false" />
					</td>
					
				</tr>
				<tr>
					<td width="20%" style="white-space: nowrap;"id="fixedTime">
						<label style="font-weight:bold;">投注金额：</label><input type="text" placeholder="请输入最低金额2000"> <label style="color:#949494;">注：统计单个用户大于等于该数值的投注金额数据</label>
					</td>
					<td><a class="mini-button" onclick="lottery_bb.search()"
						style="float: left !important;font-weight:bold;width:90px;">查询</a></td>
				</tr>
			</table>
		</div>
	</div>
	<div vertical="true"
		style="width: 100%; height: 23px; background-color: rgba(215, 215, 215, 1);"
		style="border:0;">
		<span style="float: left !important; font-size: medium;">明细数据:</span>
	</div>
	<div
		style="width: 100%; height: 30px; top: 5px; border: 0; marign-top: 20px !important;">
		<a class="mini-button dtwl" id="1">投注数据</a> 
		<a class="mini-button dtwl" id="2">投注平台</a>
		<a class="mini-button dtwl" id="3">投注彩种</a> 
		<a class="mini-button dtwl" id="4">明细数据</a>
	</div>
	<div class="mini-splitter" vertical="true"
		style="width: 1700px; height: 53%; border: 0;" id="registerTable">
		<div size="100%" showCollapseButton="true" style="border: 0;">
			<div id="datagrid" class="mini-datagrid" allowAlternating="true"
				borderStyle="border:solid 0px;" style="width: 100%; height: 100%;"
				url="ticketmgr/ticketinfo/list" idField="id" allowResize="true"
				pageSize="30" multiSelect="true" showColumnsMenu="true"
				showFilterRow="false" allowMoveColumn="true"
				onpreload="MiniCom.onpreload"
				onrowdblclick="TicketObj.onrowdblclick()" showSummaryRow="true"
				ondrawsummarycell="TicketObj.drawSummaryCell">

				<div property="columns">
					<div type="checkcolumn"></div>
					<div type="indexcolumn" headerAlign="center" align="center">序列号</div>
					<div field="lotteryCode" headerAlign="center" align="center"
						type="comboboxcolumn">
						用户名<input property="editor" class="mini-combobox"
							data="Dic.allCode" />
					</div>
					<div field="lotteryChildCode" headerAlign="center" align="center"
						type="comboboxcolumn">
						充值金额 <input property="editor" class="mini-combobox"
							data="Dic.lotteryChildCodeAll" />
					</div>
					<div field="lotteryIssue" headerAlign="center" align="center">投注金额</div>
					<div field="user.nickName" headerAlign="center" align="center">中奖金额</div>
					<div field="id" headerAlign="center" align="center">盈利率</br>(中奖-投注)/投注</div>
					<div field="ticketMoney" headerAlign="center" align="center"
						dataType="currency" allowSort="true" sortField="ticket_money"
						showSortIcon="true">流水价值</br>(投注金额/充值金额)</div>
				</div>
			</div>
		</div>
	</div>
	<div id="platformChart" style="height: 350px; display: none;">
		<div id="platformSale" style="float: left; width: 700px; height: 300px;"></div>
		<div id="platformDetail" style="float: left; width: 400px; height: 300px;margin-top:66px;"></div>
	</div>
	<div id="newOrOldUserChart" style="display: none;">
		<div id="betUser" style="float: left; width: 700px; height: 300px;"></div>
		<div id="betAmount" style="float: left; width: 700px; height: 300px;"></div>
	</div>
	<div id="siclientUserChart" style="display: none;">
		<div id="noLoginUser" style="float: left; width: 700px; height: 300px;"></div>
		<div id="noBetUser" style="float: left;width: 900px; height: 500px;"></div>
	</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/echarts.min.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/bigcustomer/customer.js?version=<%=version%><%=System.currentTimeMillis() %>" type="text/javascript"></script>
</html>
