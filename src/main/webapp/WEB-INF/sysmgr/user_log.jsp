<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
  	 <link href="<%=basePath%>resources/css/public/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <title>用户操作日志</title>
    <style>
	   	.mini-datepicker {
	   		width: 180px;
	   	}
    </style>
  </head>
  <body>
  <div class="mini-toolbar" id="form1" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
				<td>
					用户账户<input name="userName" class="mini-textbox" emptyText="请输入"/> 
					操作菜单<input id="menu" name="menu" class="mini-combobox" emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" onvaluechanged="changeTypeNameDic"/> 
					模块名称<input id="typeName" name="typeName" class="mini-combobox"	 emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
					操作开始时间<input id="startTime" name="startTime" class="mini-datepicker"  format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				          操作结束时间<input id="endTime" name="endTime" class="mini-datepicker"  allowInput="false"  format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>  
					<a class="mini-button" onclick="reload" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:91%;" 
		 url="sysmgr/user/log/list" idField="id" allowResize="true" multiSelect="true" 
		 showColumnsMenu="true" showFilterRow="false" onrowdblclick="detail">
		<div property="columns">
			<div type="checkcolumn" width="3%"></div>
			<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
			<div field="userName" headerAlign="center" align="center">用户帐号</div>
			<div field="typeName" headerAlign="center" align="center">操作模块</div>
			<div field="details" headerAlign="center" align="center">操作详情</div>
			<div field="inContent" headerAlign="center" align="center">操作输入内容</div>
			<%--<div field="outContent" headerAlign="center" align="center">操作输出内容</div>--%>
			<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">操作时间</div>	
		</div>
	</div>
	
	<div id="detailWindow" class="mini-window" title="用户操作日志详情" allowResize="true" style="width:1100px;height:500px;">
		<table class="table table-hover table-sm">
			<tbody>
				<tr>
					<td>用户帐号</td>
					<td id="userNameTd"></td>
				</tr>
				<tr>
					<td>操作模块</td>
					<td id="typeNameTd"></td>
				</tr>
				<tr>
					<td>操作详情</td>
					<td id="detailsTd"></td>
				</tr>
				<tr>
					<td>操作输入内容</td>
					<td id="inContentTd"></td>
				</tr>
				<tr>
					<td>操作输出内容</td>
					<td><input class="mini-textarea" id="outContentTag" enabled="false" width="950" height="200"/></td>
				</tr>
				<tr>
					<td>操作时间</td>
					<td><input id="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
			</tbody>
		</table>

	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		Cms.search("datagrid", "form1", ["startTime", "endTime"]);
		// 查询所有一级菜单 {menuLevel:1, menuLevel:2}
		$.get("sysmgr/menu/dic", {menuLevel:0}, function(menus) {
			mini.get('menu').setData(menus);
		});
	});
	function detail(e) {
		var row = e.row;
		$("#userNameTd").text(row.userName);
		$("#typeNameTd").text(row.typeName);
		$("#detailsTd").text(row.details);
		$("#inContentTd").text(row.inContent);
		mini.get('createTime').setValue(row.createTime);
		$.get("sysmgr/user/log/"+row.id, function(usrLog) {
			mini.get('outContentTag').setValue(usrLog.outContent);
		});
		mini.get('detailWindow').show();
	}
	// 根据一级菜单查询子菜单
	function changeTypeNameDic(e) {
		$.get("sysmgr/menu/dic", {parentMenuId:e.value}, function(subMenus) {
			var c = mini.get('typeName');
			c.setData(subMenus);
			c.select(0);
		});
	}
	function reload(e) {
		var form = new mini.Form('form1');
		var data = form.getData();      //获取表单多个控件的数据
		data.startTime = data.startTime.format("yyyy-MM-dd hh:mm:ss");
		data.endTime = data.endTime.format("yyyy-MM-dd hh:mm:ss");
		// "模块名称"下拉框要获取文本值而非id
		var select = mini.get('typeName').getSelected();
		if(select)
			data.typeName = select.text;
		mini.get('datagrid').load(data);
	}
</script>
</html>
