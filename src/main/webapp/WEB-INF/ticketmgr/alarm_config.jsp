<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>报警信息配置</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;" id="searchCondition">
			<tr>
			     <td width="15%" nowrap="nowrap"> 
			        <btn:operate privilege="ADD">
					<a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="Current.addwin()">添加</a>
					</btn:operate> 	
			     	<btn:operate privilege="UPD|SEARCH">
					<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.editwin()">修改</a>
					</btn:operate>
				</td>
			</tr>
			<tr>
			<td width="5%" style="white-space:nowrap;">
				      报警类型：<input id="alarmType"  name="alarmType"  class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="Current.alarmTypeChange"/>
				      报警子类型：<input id="alarmChild" name="alarmChild"   class="mini-combobox"  style="width:170px;" emptyText="请选择"  allowInput="true" valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				      报警等级：<input id="alarmLevel" name="alarmLevel"  class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				      报警开关：<input id="status" name="status"  class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				 <a class="mini-button" onclick="Current.search()">查询</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="90%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="ticketmgr/alarmconfig/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" onrowdblclick="Current.onrowdblclick" ondrawcell="Current.onDrawcell">
				
				<div property="columns">
					<div type="checkcolumn" ></div>
					<div type="indexcolumn" align="center" headerAlign="center">序号</div>
					<div field="alarmType"  type= "comboboxcolumn" headerAlign="center" align="center">警报类型
					     <input property="editor" class="mini-combobox" data="Dic.alarmType" />
					</div>
					<div field="alarmChild"  headerAlign="center" align="center">警报子类型</div>
					<div field="alarmLevel"  type= "comboboxcolumn" headerAlign="center" align="center">警报等级
					     <input property="editor" class="mini-combobox" data="Dic.alarmLevel" />
					</div>
					<div field="status"  type= "comboboxcolumn" headerAlign="center" align="center">报警开关
					     <input property="editor" class="mini-combobox" data="Dic.status" />
					</div>
					<div field="parameter"  headerAlign="center" align="center">报警上限</div>
					<div field="format"  headerAlign="center" align="center">报警格式</div>
					<div field="modifyTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="createTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="editWindow" class="mini-window" title="" 
		style="width:1100px; height:370px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		    <btn:operate privilege="ADD|UPD">
			<a class="mini-button" onclick="Current.doSubmit">保存</a>
			</btn:operate>
		</div>
		<div  id="editform">
			 <input name="id"  class="mini-hidden"/>
		     <input name="url"  class="mini-hidden"/>
			 <input name="action"  class="mini-hidden"/>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
	            <legend >渠道基本信息：</legend>
	            <div style="padding:5px;">
	             <table width="100%;">
	            <tr>
	            <td width="13%">警报类型</td>
	            <td width="20%"><input id ="alarmType_edit" name="alarmType"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"  onvaluechanged="Current.alarmTypeChangeEdit" required="true"/></td>
	            <td width="13%">警报子类型</td>
	            <td width="20%"><input id ="alarmChild_edit" name="alarmChild"  class="mini-combobox" emptyText="请选择" allowInput="true" valueFromSelect = "true"   style="width:100%;"   required="true"/></td>
	            <td width="13%">警报等级</td>
	            <td width="20%"><input id ="alarmLevel_edit" name="alarmLevel"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"/></td>
	            </tr>
	            <tr>
	            <td>报警开关</td>
	            <td><input id ="status_edit" name="status"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"/></td>
	            <td>报警上限</td>
	            <td><input name="parameter" class="mini-textbox" style="width:100%;" required="true" vtype ="int"/></td>
	            </tr>
	             <tr>
	            <td>报警格式</td>
	            <td colspan="5"><input name="format" class="mini-textbox" style="width:100%;"required="true" vtype="maxLength:1000"/></td>
	            </tr>
	            </table>            
	            </div>
	        </fieldset>
        
		   <fieldset style="border:solid 1px #aaa;padding:3px;">
            <legend >操作信息：</legend>
            <div style="padding:5px;">
             <table width="100%;">
			<tr>
			  <td width="13%">创建时间</td>
			  <td width="20%"><input name="createTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td width="13%">修改时间</td>
			  <td width="20%"><input name="modifyTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
			  <td>创建人</td>
			  <td><input name="createBy" class="mini-textbox" enabled="false" allowInput="false" style="width:100%;" /></td>
			  <td>修改人</td>
			  <td><input name="modifyBy" class="mini-textbox" enabled="false" allowInput="false" style="width:100%;" /></td>
			  <td ></td>
			  <td ></td>
			</tr>
            </table>            
            </div>
        </fieldset> 
		</div>
</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/ticketmgr/alarm_config.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
