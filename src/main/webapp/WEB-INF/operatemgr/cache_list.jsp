<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>缓存管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td width="15%" nowrap="nowrap">
			        <btn:operate privilege="ADD">
		            <a id="addCache" class="mini-button" iconCls="icon-add" plain="true" onclick="Current.addwin()">新增</a>
		            </btn:operate> 
		            <btn:operate privilege="UPD|SEARCH">
					<a id="updCache" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.editwin()">修改</a>
					</btn:operate>	
					<btn:operate privilege="UPD">
					<a id="delCache" class="mini-button" iconCls="icon-remove" plain="true" onclick="Current.delCache()">删除</a>
					</btn:operate>		
				</td>
				<td width="5%" style="white-space:nowrap;">
				   缓存分组：<input id="cacheGroup"   class="mini-combobox"  style="width:80px;" emptyText="请选择"  valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true"  />
				    缓存名称：  <input id="name" class="mini-textbox" style="width:200px;"/>
				  	缓存KEY值：  <input id="cacheKey" class="mini-textbox" style="width:200px;"/>
					<a class="mini-button" onclick="Current.search()">查询</a>
				</td>
				<td width="30%"></td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="cmscache/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
				 onrowdblclick="Current.onrowdblclick" onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="5%" align="center" headerAlign="center">序号</div>
					<div field="cacheGroup" headerAlign="center" align="center">缓存分组</div>
					<div field="name" headerAlign="center" align="center">缓存名称</div>
					<div field="cacheKey" headerAlign="center" align="center">缓存KEY值</div>
					<div headerAlign="center" align="center" renderer="Current.onClearRenderer" cellStyle="padding:0;">操作</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<div id="addWindow" class="mini-window" title="新增缓存信息" 
		style="width:600px; height:350px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		<div id="addForm">
		<fieldset style="border:solid 1px #aaa; padding:3px;">
	        <legend >缓存信息：</legend>
	        <div style="padding:5px;">
			<table width="100%;">
				<tr>
	            <td>缓存类型</td>
	            <td><input id="cacheGroup_add" name="cacheGroup" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"  style="width:100%;" /></td>
	            </tr>
	            <tr>
	            <td>缓存名称</td>
	            <td><input id="name_add" name="name" class="mini-textbox"  style="width:100%;" vtype ="maxLength:50"  /></td>
	            </tr>
	             <tr>
	            <td>缓存KEY值</td>
	            <td><input id="cacheKey_add" name="cacheKey" class="mini-textbox" style="width:100%;" vtype="maxLength:50"/></td>
	            </tr>
				<tr>
					<td colspan="2" align="center">
						<button style="margin-top: 50px;" id="operateBtn" onclick="Current.add()">保存</button>
						 <input name="status"  class="mini-hidden"/>
					     <input name="url"  class="mini-hidden"/>
						 <input name="action"  class="mini-hidden"/>
						 &nbsp;&nbsp;		
						<button style="margin-top: 50px;" id="closeBtn" onclick="Current.closeAddWin();">取消</button>
					</td>					
				</tr>
			</table>
			</div>
        	</fieldset> 
		</div>
	</div>
	
	
	<div id="editWindow" class="mini-window" title="修改缓存信息" 
		style="width:600px; height:350px;" showMaxButton="false" 
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
	            <legend >缓存信息：</legend>
	            <div style="padding:5px;">
	            <table width="100%;">
	            	<tr>
		            <td>缓存类型</td>
		            <td><input id="cacheGroup_edit"  name="cacheGroup"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"  style="width:100%;"  enabled="true"/></td>
		            </tr>
		            <tr>
		            <td>缓存名称</td>
		            <td><input id="name_edit" name="name" class="mini-textbox"  style="width:100%;" vtype ="maxLength:50"  /></td>
		            </tr>
		             <tr>
		            <td>缓存KEY值</td>
		            <td><input id="cacheKey_edit" name="cacheKey" class="mini-textbox" style="width:100%;" vtype="maxLength:50"/></td>
		            </tr>
	            </table>            
	            </div>
        	</fieldset> 
		</div>
	</div>


  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/cache.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
