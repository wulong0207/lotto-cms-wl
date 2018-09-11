<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=basePath%>resources/js/public/boot.js"
	type="text/javascript"></script>
<title>重置开奖</title>
</head>
<body>
	<fieldset style="border:solid 1px #aaa; padding:3px;">
		<legend>按订单号重置(可填写多个，格式为111,222,333)</legend>
		<div style="padding:5px;" id="order">
			<table width="100%;">
				<tr>
					<%--
				    <input name="url" value="lotterymgr/draw/ticket" class="mini-hidden"/>
			        <input name="action" value="PUT" class="mini-hidden"/>
			        --%>
					<td width="5%" align="right">订单号</td>
					<td width="40%"><input name="orderCodes" class="mini-textarea" style="width:100%;" required="true"/></td>
					<td><btn:operate privilege="UPD">
							<a class="mini-button" onclick="Current.triggerResteDraw('order')">重置开奖</a>
						</btn:operate></td>
				</tr>
			</table>
		</div>
	</fieldset>
<br/>
<br/>
<br/>
<br/>
	<fieldset style="border:solid 1px #aaa; padding:3px;">
		<legend>按彩期重置</legend>
		<div style="padding:5px;" id="issue">
			<%--
		        <input name="url" value="lotterymgr/draw/issue" class="mini-hidden"/>
			        <input name="action" value="PUT" class="mini-hidden"/>
			         --%>
			<table width="100%;">
				<tr>
					<td width="5%" align="right">彩种类型</td>
					<td width="10%"><input id="lotteryCategory"
						class="mini-combobox" style="width:100%;" emptyText="请选择彩种类型"
						allowInput="true" valueFromSelect="true"
						oncloseclick="Current.onCloseClickType" showClose="true"
						onvaluechanged="Current.lotteryCategoryChange" /></td>
					<td width="5%" align="right">彩种</td>
					<td width="10%"><input id="lotteryCode" name="lotteryCode" class="mini-combobox"style="width:100%;" emptyText="请选择彩种" allowInput="true"
						valueFromSelect="true" oncloseclick="Current.onCloseClickCode"
						showClose="true" onvaluechanged="Current.lotteryCodeChange" /></td>
					<td width="5%" align="right">彩期</td>
					<td width="10%"><input id="issueCode" name="lotteryIssue" class="mini-combobox" style="width:100%;" emptyText="请选择或输入" allowInput="true" oncloseclick="Cms.onCloseClick" showClose="true"/>
					</td>
					<td><btn:operate privilege="UPD">
							<a class="mini-button" onclick="Current.triggerResteDraw('issue')">重置开奖</a>
							<a class="mini-button" onclick="Current.drawSchedule('issue')">开奖进度</a>
						</btn:operate>
						</td>
					<td width="20%"></td>
				</tr>
			</table>
		</div>
	</fieldset>
	<br/>
<br/>
<br/>
<br/>
	<fieldset style="border:solid 1px #aaa; padding:3px;">
		<legend>按竞彩场次重置</legend>
		<div style="padding:5px;" id="sport">
		 <input name="url" value="lotterymgr/draw/sport" class="mini-hidden"/>
			        <input name="action" value="PUT" class="mini-hidden"/>
			<table width="100%;">
				<tr>
					<td width="5%" align="right">父彩种</td>
					<td width="10%"><input id="sportLottery" name="lotteryCode"
						class="mini-combobox" style="width:100%;" emptyText="请选择父彩种"
						allowInput="true" valueFromSelect="true"
						oncloseclick="Current.onCloseClickType" showClose="true"
						onvaluechanged="Current.sportLotteryChange" /></td>
					<td width="5%" align="right">子彩种</td>
					<td width="10%"><input id="sportLotteryChild" name="lotteryChild" class="mini-combobox"
						style="width:100%;" emptyText="请选择子彩种" allowInput="true"
						valueFromSelect="true" oncloseclick="Current.onCloseClickCode"
						showClose="true" onvaluechanged="Current.lotteryCodeChange" /></td>
					<td width="5%" align="right">系统编号</td>
					<td width="10%"><input id="code" name="code" class="mini-combobox"
						style="width:100%;" allowInput="true"
						oncloseclick="Cms.onCloseClick" showClose="true"/>
					</td>
					<td>
					    <btn:operate privilege="UPD">
							<a class="mini-button" onclick="Current.triggerResteDraw('sport')">重置开奖</a>
							<a class="mini-button" onclick="Current.drawSchedule('sport')">开奖进度</a>
						</btn:operate></td>
					<td width="20%"></td>
				</tr>
			</table>
		</div>
	</fieldset>
</body>
<script
	src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>"
	type="text/javascript"></script>
<script
	src="<%=basePath%>resources/js/lotterymgr/draw.js?vsersion=<%=version%>"
	type="text/javascript"></script>
</html>
