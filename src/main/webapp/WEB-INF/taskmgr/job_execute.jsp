<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>定时任务执行记录</title>
  </head>
  <body>
  
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
				<td width="5%" style="white-space:nowrap;">
				        任务名称：<input id="jobName" class="mini-textbox"  style="width:150px;" emptyText="请输入任务名称"/> 
				        运行方式：<input id="runWay" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择运行方式" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
				         运行状态：<input id="status"  class="mini-combobox"  style="width:150px;"
					   emptyText="请选择运行状态" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" /> 
				         执行结果：<input id="result" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择执行结果" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" /> 
					<a class="mini-button" onclick="TaskInfoObj.search()" >查询</a>
				</td>
				<td width="30%"></td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="taskmgr/job/exe/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" allowResize="true">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="task.jobName" headerAlign="center" align="center">任务名称</div>
					<div field="task.jobGroup" headerAlign="center" align="center" type="comboboxcolumn">任务分组
						<input property="editor" class="mini-combobox" data="Dic.jobGroup" />
					</div>
					<div field="runTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">触发时间</div>
					<div field="runWay" headerAlign="center" align="center" type="comboboxcolumn">运行方式
						<input property="editor" class="mini-combobox" data="Dic.runWay" />
					</div>
					<div field="status" headerAlign="center" align="center" type="comboboxcolumn">运行状态
						<input property="editor" class="mini-combobox" data="Dic.status" />
					</div>
					<div field="startTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">任务开始时间</div>
					<div field="endTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">任务结束时间</div>
					<div field="spendTime" headerAlign="center" align="center">任务耗时(秒)</div>
					<div field="result" headerAlign="center" align="center" type="comboboxcolumn">执行结果
						<input property="editor" class="mini-combobox" data="Dic.result" />
					</div>
					<div field="remark" headerAlign="center" align="center">备注</div>
				</div>
			</div>
		</div>
	</div>
	
  </body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/taskmgr/jobexecute.js" type="text/javascript"></script>
</html>
