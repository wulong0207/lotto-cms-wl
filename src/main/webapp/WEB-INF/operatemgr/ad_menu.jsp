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
    <title>广告图页面管理</title>
    <style>
	   	.mini-datepicker {
	   		width: 180px;
	   	}
	   	.mini-combobox {
	   		width: 200px;
	   	}
    </style>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="ADD">
		        <a class="mini-button" iconCls="icon-add" plain="true" onclick="operate_ad_menu.toAdd">新增广告图页面位置</a>
	        </btn:operate>
	        <btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_ad_menu.toEdit">修改</a>
	        </btn:operate>
	        <btn:operate privilege="DEL">
		        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_ad_menu.del">删除</a>
	        </btn:operate>
				</td>
			</tr>
			<tr>
				<td>
					广告图页面<input id="menu" name="menu" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
					<a class="mini-button" onclick="Cms.search('datagrid','form1')" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:93%;" 
		 url="operatemgr/ad/menu/list" idField="id" allowResize="true" multiSelect="true"
		 showColumnsMenu="true" onrowdblclick="operate_ad_menu.toEdit()" showFilterRow="false">
		<div property="columns">
			<div type="checkcolumn" width="3%"></div>
			<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
			<div field="menu" type="comboboxcolumn" headerAlign="center" align="center">广告图页面
		     <input property="editor" class="mini-combobox" data="Dic.menu" />
			</div>
			<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>	
			<div field="modifyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>	
			<div field="remark" headerAlign="center" align="center">备注</div>
		</div>
	</div>
	<div id="detailWindow" class="mini-window" title="新增" style="width:800px;height:400px;">
		<br/>
		<input name="id" class="mini-hidden"/>
	    &nbsp;&nbsp;&nbsp;新增广告图页面<input id="menu" name="menu" class="mini-combobox" emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" required="true"/>
	 <br/><br/>   
	    &nbsp;&nbsp;&nbsp;新增广告图位置<input id="position" name="position" class="mini-combobox" emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" required="true" multiSelect="true"/>
	 <br/><br/>
	 <div style="text-align: center">
	 <a class="mini-button" onclick="operate_ad_menu.merge"  style="margin:10px;">保存</a>
	 <a class="mini-button" onclick="operate_ad_menu.cancel" style="margin:10px;">取消</a>	
	 </div>
	 <br/>
	    <fieldset style="border:0">
		    <legend style="font-size:12px;">操作信息：</legend>
		    <table class="table-bordered">
            <tr>
                <td>创建时间</td>
                <td><input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
                <td>修改时间</td>
                <td><input name="modifyTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
           		<td rowspan="2">备注</td>
                <td rowspan="2" width="200px;"><input name="remark" class="mini-textarea"/></td>
            </tr>
            <tr>
                <td>创建人</td>
                <td><input name="createBy" class="mini-textbox" enabled="false"/></td>
                <td>修改人</td>
                <td><input name="modifyBy" class="mini-textbox" enabled="false"/></td>
            </tr>
        </table>
	  	</fieldset>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/ie-fix.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/operate_ad_menu.js" type="text/javascript"></script>
</html>
