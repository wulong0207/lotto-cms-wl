<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>消息管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
				<td>
					<btn:operate privilege="ADD">
 						<a  class="mini-button" iconCls="icon-add" plain="true" onclick="Current.addShow()">新增</a>
 					</btn:operate>
 					<btn:operate privilege="UPD">
 						<a  class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.edit()">修改</a>
 					</btn:operate>
<%--  					<btn:operate privilege="DEL">
 						<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="Current.delete()">删除</a>
 					</btn:operate> --%>
					<btn:operate privilege="EXPORT">
			        	<a class="mini-button" iconCls="icon-download" plain="true" onclick="Current.exportXls()">导出</a>
			        </btn:operate>
				</td>
			</tr>
			<tr>
				<td width="5%"  style="white-space:nowrap;">
					 敏感词： <input id="keyword" class="mini-textbox" style="width:220px;" emptyText="请输入敏感词" />
					 
					<a class="mini-button" onclick="Current.search()" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="sysmgr/keyword/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
				 onrowdblclick="Current.edit" onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="keyword" headerAlign="center" align="center" allowSort="true" >敏感词</div>
					<div field="grade" headerAlign="center" align="center" type= "comboboxcolumn" >敏感级别
						 <input property="editor" class="mini-combobox" data="Dic.grade" />
					</div>
					<div field="status" headerAlign="center" align="center" type= "comboboxcolumn" >状态
						 <input property="editor" class="mini-combobox" data="Dic.status" />
					</div>
					<div field="replaced" headerAlign="center" align="center">替换内容</div>
					
					<div field="updateTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<div id="editWindow" class="mini-window" title="" 
		style="width:1300px; height:800px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showModal="true" allowResize="true" allowDrag="true" >
		
		<div  id="editform">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					<tr>
						<td>敏感词：</td>
						<td>
							<input name="keywords" id="editKeywords" property="editor" vtype ="maxLength:50" class="mini-textbox" style="width:200px;" required="true" emptyText="多个敏感词间用逗号分隔"/>
							<input name="keyword" id="editKeyword" property="editor" vtype ="maxLength:50" class="mini-textbox" style="width:200px;" required="true" emptyText="请输入敏感词"/>
						</td>
						<td>替换内容：</td>
						<td><input name="replaced" property="editor" vtype ="maxLength:10" class="mini-textbox" 	emptyText="请输入替换内容"/></td>
					</tr>
					<tr>
						<td>敏感级别：</td>
						<td><input id="grade" name="grade" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择" style="width:100px;"   required="true"/></td>
						<td>状态：</td>
						<td><input id="status" name="status" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择" style="width:100px;"   required="true"/></td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							 <input name="id"class="mini-hidden"/>
							 <input id="url" name="url" value="" class="mini-hidden"/>
							 <input id="action" name="action" value="post" class="mini-hidden"/>
							<button style="margin-top: 50px;" id="saveBtn">保存信息</button>
						</td>
					</tr>
				</table>
		</div>
	</div>
		
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/sysmgr/keyword.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
