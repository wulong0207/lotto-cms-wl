<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>报警管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;"  id="searchCondition">
			<tr>
			     <td width="15%" nowrap="nowrap"> 	
			     	<btn:operate privilege="UPD">
						<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.dispose()">处理</a>
					</btn:operate>
				</td>
			</tr>
			<tr>
			    <td width="5%" style="white-space:nowrap;">
				      报警类型：<input id="alarmType"  name="alarmType"  class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="Current.alarmTypeChange"/>
				      报警子类型：<input id="alarmChild" name="alarmChild"   class="mini-combobox"  style="width:170px;" emptyText="请选择"  allowInput="true" valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				     报警时间：   <input id="timeType"  name="timeType" class="mini-combobox"  style="width:100px;"/>
				   <input id="startTime"  class="mini-datepicker"  allowInput="false"  style="width:150px;" 
				   format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime"  class="mini-datepicker"  allowInput="false"  style="width:150px;"
				         format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/> 
				      报警等级：<input id="alarmLevel" name="alarmLevel"  class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				         处理状态：<input id="status" name="status"  class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				         处理人：<input id="dealBy" name="dealBy" class="mini-textbox" style="width:100px;"/>     
				</td>
			</tr>
			<tr>
			    <td width="5%" style="white-space:nowrap;">
				       报警内容：<input id="alarmInfo" name="alarmInfo" class="mini-textbox" style="width:200px;"/>      			    
				       订单号：<input name="orderCode" class="mini-textbox" style="width:200px;"/>      			    
				 <a class="mini-button" onclick="Current.search()">查询</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="90%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="ticketmgr/alarm/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" ondrawcell="Current.onDrawcell">
				
				<div property="columns">
					<div type="checkcolumn" ></div>
					<div type="indexcolumn" align="center" headerAlign="center">序号</div>
					<div field="alarmInfo"  headerAlign="center" align="center">警报内容</div>
					<div field="alarmType"  type= "comboboxcolumn" headerAlign="center" align="center">警报类型
					     <input property="editor" class="mini-combobox" data="Dic.alarmType" />
					</div>
					<div field="alarmChild"  headerAlign="center" align="center">警报子类型</div>
					<div field="alarmTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">警报时间</div>
					<div field="alarmLevel"  type= "comboboxcolumn" headerAlign="center" align="center">警报等级
					     <input property="editor" class="mini-combobox" data="Dic.alarmLevel" />
					</div>
					<div field="status"  type= "comboboxcolumn" headerAlign="center" align="center">处理状态
					     <input property="editor" class="mini-combobox" data="Dic.status" />
					</div>
					<div field="dealTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">处理时间</div>
					<div field="remark"  headerAlign="center" align="center">备注</div>
					<div field="dealBy"  headerAlign="center" align="center">处理人</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/ticketmgr/alarm_info.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
