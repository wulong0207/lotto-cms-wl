<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>红包流水管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="EXPORT">
		        <a class="mini-button" iconCls="icon-download" plain="true" onclick="trans_common.excel('transmgr/red')">导出Excel</a>
	        </btn:operate>
				</td>
			</tr>
			<tr>
				<td width="5%" style="white-space:nowrap;">
				         彩种类型：<input id="lotteryCategory"  name="lotteryCategory" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种类型" allowInput="true" valueFromSelect ="true"  oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="trans_red.lotteryCategoryChange"/> 
				         彩种：<input id="lotteryCode" name="lotteryCode" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种" allowInput="true" valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="trans_red.lotteryCodeChange"/> 
				         彩期：<input id="startIssue"  name="startIssue" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择或输入" allowInput="true" oncloseclick="Cms.onCloseClick" showClose="true"/> 
 					 <input id="endIssue" name="endIssue" class="mini-textbox" style="width:50px;"/>
 					  订单信息：  <input id="orderInfo" name="orderInfo" class="mini-textbox" style="width:200px;"/>
 					  红包类型：<input id="redType" name="redType" class="mini-combobox"  style="width:150px;" emptyText="请选择"  valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true"/> 
				</td>
			</tr>
			<tr>
				<td>
					系统流水编号：  <input id="redTransCode" name="redTransCode" class="mini-textbox" style="width:200px;"/>
					方案编号：  <input id="orderCode" name="orderCode" class="mini-textbox" style="width:200px;"/>
					红包编号：  <input id="redCode" name="redCode" class="mini-textbox" style="width:200px;"/>
				</td>
			</tr>
			<tr>
				<td>
					<input id="userSearchType" name="userSearchType" class="mini-combobox"  style="width:150px;" valueFromSelect ="true"/>
				 	<input id="userSearchValue" name="userSearchValue" class="mini-textbox" style="width:200px;"/> 
					<input id="searchTimeType"  name="searchTimeType" class="mini-combobox"  style="width:150px;" valueFromSelect ="true"/>
					<input id="startTime" name="startTime" class="mini-datepicker" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" name="endTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>  
					交易分类：<input id="transType" name="transType" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					交易状态：<input id="transStatus" name="transStatus" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择"  valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					<a class="mini-button" onclick="trans_common.search(trans_red.grid)" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:87%;" 
		 url="transmgr/red/list" idField="id" allowResize="true" multiSelect="true" 
		 showColumnsMenu="true" showFilterRow="false" onrowdblclick="trans_red.viewDetail" showSummaryRow = "true" ondrawsummarycell="trans_red.onDrawSummaryCell">
		<div property="columns">
			<div type="checkcolumn" width="3%"></div>
			<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
			<div field="nickName" headerAlign="center" align="center">会员昵称</div>
			<div field="redTransCode" headerAlign="center" align="center">系统流水编号</div>
			<div field="redCode" headerAlign="center" align="center">红包编号</div>
			<div field="redType" type= "comboboxcolumn" headerAlign="center" align="center">红包类型
		      	<input property="editor" class="mini-combobox" data="Dic.redType" />
			</div>
			<div field="transType" type= "comboboxcolumn" headerAlign="center" align="center">交易分类
		     <input property="editor" class="mini-combobox" data="Dic.transType" />
			</div>
			<div field="transStatus" type= "comboboxcolumn" headerAlign="center" align="center">交易状态
		     <input property="editor" class="mini-combobox" data="Dic.transStatus" />
			</div>
			<div field="transAmount" headerAlign="center" align="center">交易金额</div>
			<div field="aftTransAmount" headerAlign="center" align="center">交易后红包金额</div>
			<div field="orderInfo" headerAlign="center" align="center">订单信息</div>
			<div field="transTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">交易时间</div>	
			<div field="orderCode" headerAlign="center" align="center">订单编号</div>
		</div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/transmgr/trans_common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/transmgr/trans_red.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
