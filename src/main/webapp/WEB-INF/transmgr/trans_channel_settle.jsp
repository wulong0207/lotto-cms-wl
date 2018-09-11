<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>渠道结算管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="SETTLE">
		        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="trans_channel_settle.settle()">结算</a>
	        </btn:operate>
	        <btn:operate privilege="DISPATCH">
		        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="trans_channel_settle.dispatch()">派发</a>
	        </btn:operate>
	        <btn:operate privilege="EXPORT">
		        <a class="mini-button" iconCls="icon-download" plain="true" onclick="trans_common.excel('transmgr/channelsettle')">导出Excel</a>
	        </btn:operate>
				</td>
			</tr>
			<tr>
				<td>
					渠道名称<input name="channelName" class="mini-textbox" style="width:200px;"/>
					结算状态<input id="settleStatus" name="settleStatus" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
				</td>
			</tr>
			<tr>
				<td>
					结算类型<input id="settleType" name="settleType" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
					<input id="searchTimeType"  name="searchTimeType" class="mini-combobox"  style="width:150px;" valueFromSelect ="true"/>
					<input id="startTime" name="startTime" class="mini-datepicker" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" name="endTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>  
					<a class="mini-button" onclick="trans_common.search(trans_channel_settle.grid)" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:90%;" 
		 url="transmgr/channelsettle/list" idField="id" allowResize="true" multiSelect="true" 
		 showColumnsMenu="true" onrowdblclick="trans_channel_settle.viewDetail" showFilterRow="false">
		<div property="columns">
			<div type="checkcolumn" width="3%"></div>
			<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
			<div field="transChannelSettleCode" headerAlign="center" align="center">结算流水ID</div>
			<div field="channelName" headerAlign="center" align="center">渠道名称</div>
			<div field="settleCommission" headerAlign="center" align="center">应结佣金</div>
			<div field="settleStatus" type="comboboxcolumn" headerAlign="center" align="center">结算状态
		     <input property="editor" class="mini-combobox" data="Dic.settleStatus" />
			</div>
			<div field="settleType" type="comboboxcolumn" headerAlign="center" align="center">结算类型
		     <input property="editor" class="mini-combobox" data="Dic.settleType" />
			</div>
			<div field="settleCount" headerAlign="center" align="center">结算总量</div>
			<div field="settleRate" headerAlign="center" align="center">结算费率</div>
			<div field="settleStartTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">结算开始时间</div>	
			<div field="settleEndTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">结算结束时间</div>	
			<div field="settleTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">结算时间</div>	
			<div field="remark" headerAlign="center" align="center">备注</div>
		</div>
	</div>
	<div id="detailWindow" class="mini-window" style="width:800px;" showModal="true" allowResize="true" allowDrag="true">
	    <div id="detailForm" class="form" >
		    <fieldset>
			    <legend>渠道详情：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td style="width:100px;">渠道ID</td>
	                <td style="width:150px;"><input name="channelId" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">渠道名称</td>
	                <td style="width:150px;"><input name="channelName" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">结算类型</td>
	                <td style="width:150px;"><input id="settleTypeDetail" name="settleType" class="mini-combobox" enabled="false"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">应结佣金</td>
	                <td style="width:150px;"><input name="settleCommission" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">结算总量</td>
	                <td style="width:150px;"><input name="settleCount" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">结算费率</td>
	                <td style="width:150px;"><input name="settleRate" class="mini-textbox" enabled="false"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">结算状态</td>
	                <td style="width:150px;"><input id="settleStatusDetail" name="settleStatus" class="mini-combobox" enabled="false"/></td>
	                <td style="width:100px;">结算时间</td>
	                <td style="width:150px;"><input name="settleTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	                <td style="width:100px;">派发时间</td>
	                <td style="width:150px;"><input name="sendTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">结算开始时间</td>
	                <td style="width:150px;"><input name="settleStartTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	                <td style="width:100px;">结算结束时间</td>
	                <td style="width:150px;"><input name="settleEndTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </tr>
	        </table>
			  </fieldset>
		    <fieldset>
			    <legend>操作信息：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td style="width:100px;">创建时间</td>
	                <td style="width:150px;"><input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	                <td style="width:100px;">修改时间</td>
	                <td style="width:150px;"><input name="modifyTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            		<td style="width:100px;" rowspan="2">备注</td>
	                <td style="width:150px;" rowspan="2"><input name="remark" class="mini-textbox" enabled="false"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">创建人</td>
	                <td style="width:150px;"><input name="createBy" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">修改人</td>
	                <td style="width:150px;"><input name="modifyBy" class="mini-textbox" enabled="false"/></td>
	            </tr>
	        </table>
			  </fieldset>
	    </div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/transmgr/trans_common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/transmgr/trans_channel_settle.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
