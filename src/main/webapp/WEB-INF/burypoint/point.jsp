<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>漏斗列表</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="ADD">
		        <a class="mini-button" iconCls="icon-add" plain="true" onclick="bp_point.toAdd">新增</a>
	        </btn:operate>
	        
	        <btn:operate privilege="DEL">
		        <a class="mini-button" iconCls="icon-remove" plain="true" onclick="bp_point.toDelete">删除</a>
	        </btn:operate>
	         &nbsp;&nbsp;&nbsp;<input id="codeOrNameLike" class="mini-textbox"
					   emptyText="请输入你要查询的埋点序号或名称" width="200px"/>
			        <a class="mini-button" onclick="bp_point.search">查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:87%;" 
		 url="bp/point/page" idField="id" allowResize="true" ajaxType="get" multiSelect="true"
		 showColumnsMenu="true" showFilterRow="false">
		<div property="columns">
			<div type="checkcolumn" width="5%"></div>
			<div type="indexcolumn" width="5%" align="center" headerAlign="center">序号</div>
			<div field="code" headerAlign="center" align="center">埋点序号</div>
			<div field="name" headerAlign="center" align="center">埋点名称</div>
			<div field="pageId" type="comboboxcolumn" headerAlign="center" align="center">所属页面
		     <input property="editor" id="pageId" class="mini-combobox" url="bp/point/pointpage/dic"/>
			</div>
			<div field="modeId" type="comboboxcolumn" headerAlign="center" align="center">所属板块
		     <input property="editor"  id="modeId" class="mini-combobox" url="bp/point/mode/dic"/>
			</div>
			<div field="buttonId" type="comboboxcolumn" headerAlign="center" align="center">所属按钮
		     <input property="editor" id="buttonId"  class="mini-combobox" url="bp/point/button/dic"/>
			</div>
		</div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/burypoint/bp_point.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
