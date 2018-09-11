<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <link href="<%=basePath%>resources/css/public/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <title>添加埋点</title>
  </head>
  <body>
  
  <div id="detailWindow" showToolbar="true" class="mini-window" style="width:100%;height:100%" showFooter="true" showModal="true" allowResize="true" allowDrag="true" showCloseButton="false">
	    <div property="toolbar" style="text-align:left;padding:4px;">
	    <btn:operate privilege="SAVE">
	        <a class="mini-button" iconCls="icon-save"  onclick="bp_point_add.save">保存</a>
        </btn:operate>
	        <a class="mini-button"  onclick="bp_point_add.closeTab">取消</a>
	        </div>
	        
	    <div id="detailForm" class="form">
	    埋点序号：<input name="code" id="name" class="mini-textbox" required="true" vtype="int"/></br></br>
	   埋点名称： <input name="name" id="name" class="mini-textbox" required="true"/></br></br>
	关联埋点位置：<input name="pageId" id="pageId" class="mini-combobox" url="bp/point/pointpage/dic" onvaluechanged="bp_point_add.findMode('pageId', 'modeId','buttonId')" emptytext="请选择" required="true"/> 
	<input name="modeId" id="modeId" class="mini-combobox" onvaluechanged="bp_point_add.findButton('modeId','buttonId')" emptytext="请选择"/> 
	<input name="buttonId" id="buttonId" class="mini-combobox" emptytext="请选择"/> 
	<a class="mini-button" iconCls="icon-edit" onclick="bp_point_add.toWindow('positionWindow',null,bp_point_add.clearPageModeButton)">编辑</a>
	    </div>
	    
	</div>

	<div id="positionWindow" showToolbar="true" class="mini-window"
		style="width: 450px; height: 700px" showModal="true"
		allowResize="true" allowDrag="true" showCloseButton="true">
		<div property="toolbar"
			style="text-align: right; padding: 2px; padding-right: 15px;">
			<a class="mini-button" onclick="bp_point_add.cancel('positionWindow')">保存</a>
			<btn:operate privilege="DEL">
				<a class="mini-button" iconCls="icon-remove"
					onclick="bp_point_add.deletePosition">删除</a>
			</btn:operate>
		</div>
		
		<div id="positionForm" class="row form">
			<div class="col-md-3">
			页面
				<div name="pageId" id="pageId2" class="mini-radiobuttonlist"
					repeatItems="1" repeatDirection="vertical" textField="text"
					valueField="id" 
					url="bp/point/pointpage/dic" onvaluechanged="bp_point_add.findMode('pageId2', 'modeId2', 'buttonId2')">
				</div>
				<btn:operate privilege="ADD">
					<a class="mini-button" iconCls="icon-add" onclick="bp_point_add.toWindow('pageWindow')">新增</a>
				</btn:operate>
			</div>
			
			<div class="col-md-3">
			板块
				<div name="modeId" id="modeId2" class="mini-radiobuttonlist"
					repeatItems="1" repeatDirection="vertical" textField="text"
					valueField="id" onvaluechanged="bp_point_add.findButton('modeId2','buttonId2')"
					>
				</div>
				<btn:operate privilege="ADD">
					<a class="mini-button" iconCls="icon-add" onclick="bp_point_add.toWindow('modeWindow',bp_point_add.sureSelectPage)">新增</a>
				</btn:operate>
			</div>
			
			<div class="col-md-3">
			按钮
				<div name="buttonId" id="buttonId2" class="mini-radiobuttonlist"
					repeatItems="1" repeatDirection="vertical" textField="text"
					valueField="id"
					>
				</div>
				<btn:operate privilege="ADD">
					<a class="mini-button" iconCls="icon-add" onclick="bp_point_add.toWindow('buttonWindow',bp_point_add.sureSelectMode)">新增</a>
				</btn:operate>
			</div>
			
		</div>
	</div>

	<div id="pageWindow" showToolbar="true" class="mini-window"
		style="width: 300px; height: 150px" showModal="true"
		allowResize="true" allowDrag="true" showCloseButton="true" title="新增页面">
		名称：<input id="pageName" class="mini-textbox" required="true"/>
		<br/></br>
		<a class="mini-button" onclick="bp_point_add.addPage">保存</a>
		<a class="mini-button" onclick="bp_point_add.cancel('pageWindow')">取消</a>
	</div>
	<div id="modeWindow" showToolbar="true" class="mini-window"
		style="width: 300px; height: 150px" showModal="true"
		allowResize="true" allowDrag="true" showCloseButton="true" title="新增板块">
		名称：<input id="modeName" class="mini-textbox" required="true"/>
		<br/></br>
		<a class="mini-button" onclick="bp_point_add.addMode">保存</a>
		<a class="mini-button" onclick="bp_point_add.cancel('modeWindow')">取消</a>
	</div>
	<div id="buttonWindow" showToolbar="true" class="mini-window"
		style="width: 300px; height: 150px" showModal="true"
		allowResize="true" allowDrag="true" showCloseButton="true" title="新增按钮">
		名称：<input id="buttonName" class="mini-textbox" required="true"/>
		<br/></br>
		<a class="mini-button" onclick="bp_point_add.addButton">保存</a>
		<a class="mini-button" onclick="bp_point_add.cancel('buttonWindow')">取消</a>
	</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/burypoint/bp_point_add.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
