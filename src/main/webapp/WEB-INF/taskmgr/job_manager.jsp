<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>定时任务管理</title>
  </head>
  <body>
  
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td width="15%" nowrap="nowrap" >
			        <btn:operate privilege="ADD">
			     	<a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="TaskObj.addTask()">新增</a>
			     	</btn:operate>
			     	<btn:operate privilege="UPD">
					<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="TaskObj.editTask()">修改</a>
					</btn:operate>
					<btn:operate privilege="DEL">
				 	<a class="mini-button" iconCls="icon-remove" plain="true" onclick="TaskObj.delTask()">删除</a>
				 	</btn:operate>	
					<btn:operate privilege="MANUAL_EXECUTE">
			        <a class="mini-button" iconCls="icon-goto" plain="true" onclick="TaskObj.exeTask()">手动执行</a>
			        </btn:operate>
				</td>
				<td width="5%" style="white-space:nowrap;">
				        任务名称：<input id="jobName" class="mini-textbox"  style="width:150px;" emptyText="请输入任务名称"/> 
				        任务组：<input id="jobGroup" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择分组" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
				         状态：<input id="jobStatus"  class="mini-combobox"  style="width:150px;"
					   emptyText="请选择状态" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" /> 
				         自动运行：<input id="jobAutorun" class="mini-combobox"  style="width:150px;"
					   emptyText="是否自动运行" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" /> 
					<a class="mini-button" onclick="TaskObj.search()" >查询</a>
				</td>
				<td width="30%"></td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="taskmgr/job/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" onrowdblclick="TaskObj.onrowdblclick()">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="jobId" align="center" headerAlign="center">任务编号</div>
					<div field="jobName" headerAlign="center" align="center">任务名称</div>
					<div field="jobGroup" headerAlign="center" align="center" type="comboboxcolumn">任务组
						<input property="editor" class="mini-combobox" data="Dic.jobGroup" />
					</div>
					<div field="quartzStatus" headerAlign="center" align="center" type="comboboxcolumn">运行状态
						<input property="editor" class="mini-combobox" data="Dic.quartzStatus" />
					</div>
					<div field="previousFireTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">上次执行时间</div>
					<div field="nextFireTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">下次执行时间</div>
					<div field="jobManualUrl" headerAlign="center" align="center">任务URL</div>
					<div field="jobCronExpression" headerAlign="center" align="center">时间表达式</div>
					
					<div field="jobStatus" headerAlign="center" align="center" type="comboboxcolumn">状态
						<input property="editor" class="mini-combobox" data="Dic.jobStatus" />
					</div>
					<div field="jobAutorun" type= "comboboxcolumn" headerAlign="center" align="center">自动运行
					    <input property="editor" class="mini-combobox" data="Dic.jobAutorun" />
					</div>
					<div field="restart"  headerAlign="center" align="center"  renderer="TaskObj.restartRenderer">重启定时任务</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="editWindow" class="mini-window" title="" 
		style="width:900px; height:500px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
			<btn:operate privilege="ADD|UPD">
			<a class="mini-button" onclick="TaskObj.doSubmit()">保存</a>
			</btn:operate>
		</div>
		
		<div id="editForm">
		    <input name="action"  class="mini-hidden"/>
			<input name="url"  class="mini-hidden" />
			<input name="id"  class="mini-hidden" />
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
	            <legend>任务信息：</legend>
	            <div style="padding:5px;">
	            <table width="100%;">
	            <tr>
	            <td width="13%">任务编号：</td>
	            <td width="20%"><input name="jobId" class="mini-textbox" vtype="maxLength:20" style="width:220px;"/></td>
	            </tr>
	            <tr>
	            <td width="13%">任务名称：</td>
	            <td width="20%"><input name="jobName" class="mini-textbox" vtype="maxLength:50" style="width:220px;" required="true"/></td>
	            <td width="13%">任务URL：</td>
	            <td width="20%"><input name="jobManualUrl" class="mini-textbox" vtype="maxLength:100" style="width:220px;" required="true"/></td>
	            </tr>
	            
	            <tr>
	            <td>执行时间表达式：</td>
	            <td><input name="jobCronExpression" class="mini-textbox" vtype="maxLength:50" style="width:220px;"/></td>
	            <td>任务分组：</td>
	            <td><input id="jobGroup_edit" name="jobGroup" class="mini-combobox" emptyText="请选择" valueFromSelect="true" nullItemText="请选择" style="width:200px;" required="true"/></td>
	            </tr>
	            
	            <tr>
	            <td>状态：</td>
	            <td><input id="jobStatus_edit" name="jobStatus" class="mini-combobox" emptyText="请选择" valueFromSelect="true" nullItemText="请选择" style="width:200px;" required="true"/></td>
	            <td>自动运行：</td>
	            <td><input id="jobAutorun_edit" name="jobAutorun" class="mini-combobox" emptyText="请选择" valueFromSelect="true" nullItemText="请选择" style="width:200px;" required="true"/></td>
	            </tr>
	            <tr>
	            <td>任务执行方式：</td>
	            <td><input id="jobWay_edit" name="jobWay" class="mini-combobox"  valueFromSelect="true"  style="width:200px;" required="true"/></td>
	            <td>重启立即执行：</td>
	            <td><input id="restartRun_edit" name="restartRun" class="mini-combobox"  valueFromSelect="true" style="width:200px;" required="true"/></td>
	            </tr>
	            <tr>
	            <td>参数字段1：</td>
	            <td><input name="paramKey1" class="mini-textbox" vtype="rangeChar:0,50" style="width:220px;" required="false"/></td>
	            <td>参数值1：</td>
	            <td><input name="paramValue1" class="mini-textbox" vtype="rangeChar:0,50" style="width:220px;" required="false"/></td>
	            </tr>
	            
	            <tr>
	            <td>参数字段2：</td>
	            <td><input name="paramKey2" class="mini-textbox" vtype="rangeChar:0,50" style="width:220px;" required="false"/></td>
	            <td>参数值2：</td>
	            <td><input name="paramValue2" class="mini-textbox" vtype="rangeChar:0,50" style="width:220px;" required="false"/></td>
	            </tr>
	            
	            <tr>
	            <td>参数字段3：</td>
	            <td><input name="paramKey3" class="mini-textbox" vtype="rangeChar:0,50" style="width:220px;" required="false"/></td>
	            <td>参数值3：</td>
	            <td><input name="paramValue3" class="mini-textbox" vtype="rangeChar:0,50" style="width:220px;" required="false"/></td>
	            </tr>
	            
	            </table>            
	            </div>
	        </fieldset>
        
		   	<fieldset style="border:solid 1px #aaa;padding:3px;">
            	<legend>操作信息：</legend>
            	<div style="padding:5px;">
             	<table width="100%;">
             	
				<tr>
	            <td>创建人：</td>
	            <td><input name="createBy" class="mini-textbox" enabled="false" allowInput="false" style="width:200px;"/></td>
	            <td>创建时间：</td>
	            <td><input name="createTime" class="mini-datepicker" allowInput="false" enabled="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </tr>
	            
	            <tr>
	            <td>修改人：</td>
	            <td><input name="modifyBy" class="mini-textbox" enabled="false" allowInput="false" style="width:200px;"/></td>
	            <td>修改时间：</td>
	            <td><input name="modifyTime" class="mini-datepicker" allowInput="false" enabled="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </tr>
	            
	            <tr>
	            <td>备注：</td>
	            <td colspan="3"><input name="remark" class="mini-textarea" vtype ="maxLength:100" style="width:100%;" required="false"/></td>
	            </tr>
	            
           	 	</table>            
            	</div>
        	</fieldset>
		</div>
	</div>
		
  </body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/taskmgr/jobmanager.js" type="text/javascript"></script>
</html>
