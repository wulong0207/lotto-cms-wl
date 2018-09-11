<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>用户流水管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="EXPORT">
		        <a class="mini-button" iconCls="icon-download" plain="true" onclick="trans_common.excel('transmgr/user')">导出Excel</a>
	        </btn:operate>
				</td>
			</tr>
			<tr>
				<td width="5%" style="white-space:nowrap;">
				         彩种类型：<input id="lotteryCategory"  name="lotteryCategory" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种类型" allowInput="true" valueFromSelect ="true"  oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="trans_user.lotteryCategoryChange"/> 
				         彩种：<input id="lotteryCode" name="lotteryCode" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种" allowInput="true" valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="trans_user.lotteryCodeChange"/> 
				         彩期：<input id="startIssue"  name="startIssue" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择或输入" allowInput="true" oncloseclick="Cms.onCloseClick" showClose="true"/> 
 					 <input id="endIssue" name="endIssue" class="mini-textbox" style="width:150px;"/>
 					  订单信息：  <input id="orderInfo" name="orderInfo" class="mini-textbox" style="width:200px;"/>
 					 <%-- 
 					  来源：<input id="source"   class="mini-combobox"  style="width:150px;"
					   emptyText="请选择"   nullItemText="请选择" showNullItem="true" allowInput="true" valueFromSelect ="true" showClose="true"/>
					--%>
					市场渠道：
					<input id="channelId" name="channelId" class="mini-combobox" style="width:120px;" url="operatemgr/marketchannel/dic"
						   emptyText="请选择" allowInput="true" oncloseclick="Cms.onCloseClick" showClose="true"/>
				</td>
			</tr>
			<tr>
				<td>
					系统流水编号：  <input id="transCode" name="transCode" class="mini-textbox" style="width:200px;"/>
					第三方流水编号：  <input id="thirdTransId" name="thirdTransId" class="mini-textbox" style="width:200px;"/>
					订单编号：  <input id="orderCode" name="orderCode" class="mini-textbox" style="width:200px;"/>
				</td>
			</tr>
			<tr>
				<td>
					<input id="userSearchType" name="userSearchType" class="mini-combobox"  style="width:150px;" valueFromSelect ="true"/>
				 	<input id="userSearchValue" name="userSearchValue" class="mini-textbox" style="width:200px;"/> 
					<input id="transTimeType"  name="transTimeType" class="mini-combobox"  style="width:150px;" valueFromSelect ="true"/>
					<input id="startTime" name="startTime" class="mini-datepicker" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" name="endTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>  
					交易分类：<input id="transType" name="transType" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					交易状态：<input id="transStatus" name="transStatus" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择"  valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					<a class="mini-button" onclick="trans_common.search(trans_user.grid)" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<%-- 
	<div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
	  <table style="width:100%;border:2">
	   <tr>
	   	<td>统计条件：</td>
	  	<td><div id="statisType" class="mini-radiobuttonlist" textField="text" valueField="id" value="1"/></td>
			<td>
	      <span id="sisOrderAmount">1</span>
	      <span id ="sisAftBonus">2</span>
	      <span id ="sisPreBonus">3</span>
	      <span id ="sisAddedBonus">4</span>
	   	</td>
	   </tr>
	  </table>
	</div>
	--%>
<!-- 	<div class="mini-splitter" vertical="true" style="width:100%;height:90%;" style="border:0;"> -->
	
<!-- 		<div size="100%" showCollapseButton="true" style="border:0;"> -->
			<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:87%;" 
				 url="transmgr/user/list" idField="id" allowResize="true" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" onrowdblclick="trans_user.viewDetail" showSummaryRow = "true" ondrawsummarycell="trans_user.onDrawSummaryCell">
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="nickName" headerAlign="center" align="center">会员昵称</div>
					<div field="transCode" headerAlign="center" align="center">系统流水编号</div>
					<div field="transType" type= "comboboxcolumn" headerAlign="center" align="center">交易分类
				     <input property="editor" class="mini-combobox" data="Dic.transType" />
					</div>
					<div field="transStatus" type= "comboboxcolumn" headerAlign="center" align="center">交易状态
				     <input property="editor" class="mini-combobox" data="Dic.transStatus" />
					</div>
					<div field="transAmount" headerAlign="center" align="center" width="4%">交易金额</div>
					<div header="交易金额组成" headerAlign="center" align="center">
					<div property="columns">
						<div field="amountWin" headerAlign="center" align="center" width="3%">中奖</div>
						<div field="cashAmount" headerAlign="center" align="center" width="4%">充值</div>
						<div field="redTransAmount" headerAlign="center" align="center" width="3%">红包</div>
						<div field="serviceCharge" headerAlign="center" align="center" width="3%">服务费</div>
					</div>
					</div>
					<div field="totalCashBalance" headerAlign="center" align="center">现金总余额</div>
					<div field="totalRedBalance" headerAlign="center" align="center">红包总余额</div>
					<div field="orderInfo" headerAlign="center" align="center">订单信息</div>
					
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>	
					<div field="transEndTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">交易结束时间</div>	
					<div field="orderCode" headerAlign="center" align="center">订单编号</div>
					<div field="thirdTransId" headerAlign="center" align="center">第三方流水号</div>
					<div field="channelName" headerAlign="center" align="center">市场渠道</div>
				</div>
			</div>
<!-- 		</div> -->
<!-- 	</div> -->
			<div id="detailWindow" class="mini-window" style="width:800px;" showModal="true" allowResize="true" allowDrag="true">
			    <div id="detailForm" class="form" >
				    <fieldset>
					    <legend>投注信息：</legend>
					    <table style="width:100%;">
			            <tr>
			                <td style="width:100px;">交易分类</td>
			                <td style="width:150px;"><input id="transTypeDetail" name="transType" class="mini-combobox" enabled="false"/></td>
			                <td style="width:100px;">用户昵称</td>
			                <td style="width:150px;"><input name="nickName" class="mini-textbox" enabled="false"/></td>
			                <td style="width:100px;">交易状态</td>
			                <td style="width:150px;"><input id="transStatusDetail" name="transStatus" class="mini-combobox" enabled="false"/></td>
			            </tr>
			            <tr>
			                <td style="width:100px;">订单信息</td>
			                <td style="width:150px;"><input name="orderInfo" class="mini-textbox" enabled="false"/></td>
			                <td style="width:100px;">用户帐号</td>
			                <td style="width:150px;"><input name="accountName" class="mini-textbox" enabled="false"/></td>
			                <td style="width:100px;">方案来源</td>
			                <td style="width:150px;"><input name="terminalPlatform" class="mini-textbox" enabled="false"/></td>
			            </tr>
			            <tr>
			                <td style="width:100px;">创建时间</td>
			                <td style="width:150px;"><input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
			                <td style="width:100px;">用户手机号码</td>
			                <td style="width:150px;"><input name="cusMobile" class="mini-textbox" enabled="false"/></td>
			                <td style="width:100px;">市场渠道</td>
			                <td style="width:150px;"><input name="channelName" class="mini-textbox" enabled="false"/></td>
			            </tr>
			            <tr>
			                <td style="width:100px;">交易时间</td>
			                <td style="width:150px;"><input name="transTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
			                <td style="width:100px;">交易金额</td>
			                <td style="width:150px;"><input id="transAmountDetail" name="transAmount" class="mini-textbox" enabled="false"/></td>
			                <td style="width:100px;">订单编号</td>
			                <td style="width:150px;"><input name="orderCode" class="mini-textbox" enabled="false"/></td>
			            </tr>
			            <tr>
			                <td style="width:100px;">结束时间</td>
			                <td style="width:150px;"><input name="transEndTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
			                <td style="width:100px;">现金金额</td>
			                <td style="width:150px;"><input id="cashAmountDetail" name="cashAmount" class="mini-textbox" enabled="false"/></td>
			                <td style="width:100px;">第三方流水号</td>
			                <td style="width:150px;"><input name="thirdTransId" class="mini-textbox" enabled="false"/></td>
			            </tr>
			            <tr>
			                <td style="width:100px;">第三方交易时间</td>
			                <td style="width:150px;"><input name="thirdTransTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
			                <td style="width:100px;">红包金额</td>
			                <td style="width:150px;"><input id="redTransAmountDetail" name="redTransAmount" class="mini-textbox" enabled="false"/></td>
			                <td style="width:100px;">服务费</td>
			                <td style="width:150px;"><input id="serviceChargeDetail" name="serviceCharge" class="mini-textbox" enabled="false"/></td>
			            </tr>
			            <tr>
			            	<td style="width:100px;">现金总余额</td>
			                <td style="width:150px;"><input id="totalCashBalance" name="totalCashBalance" class="mini-textbox" enabled="false"/></td>
			                <td style="width:100px;">红包总余额</td>
			                <td style="width:150px;"><input id="totalRedBalance" name="totalRedBalance" class="mini-textbox" enabled="false"/></td>
			            </tr>
			        </table>
					  </fieldset>
			    </div>
			</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/transmgr/trans_common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/transmgr/trans_user.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
