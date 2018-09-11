<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>会员类别</title>
  </head>
<body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td width="15%" nowrap="nowrap">
			        <btn:operate privilege="ADD">
				     <a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="UserType.toAdd()">新增</a>
			     	</btn:operate>
			        <btn:operate privilege="UPD">
			        <a id="upd" class="mini-button" iconCls="icon-edit" plain="true" onclick="UserType.toEdit()">修改</a>
			        </btn:operate>
			     	<btn:operate privilege="DEL">
					<a id="del" class="mini-button" iconCls="icon-remove" plain="true" onclick="UserType.del()">删除</a>
					</btn:operate>
					<btn:operate privilege="ADD">
					<a id="addTypeToUser" class="mini-button" iconCls="icon-add" plain="true" onclick="UserType.addTypeToUser()">贴标签</a> 
					</btn:operate>			
				</td>
				<td width="5%" style="white-space:nowrap;">
				        会员类别: <input id="userType"  class="mini-combobox"  style="width:120px;" url="customermgr/usertype/basic" allowInput="true" showClose="true" emptyText="请选择"
				         valueFromSelect = "true" oncloseclick="Cms.onCloseClick"/>
				   <input id="timeType"  class="mini-combobox"  style="width:100px;" showClose="true"/>
				   <input id="beginTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>  
					<a class="mini-button" onclick="UserType.search()">查询</a>
				</td>
				<td width="30%"></td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="dataGrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="customermgr/usertype/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
				 onrowdblclick="UserType.onRowdblClick()" onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" align="center" headerAlign="center">序号</div>
					<div field="code" align="center" headerAlign="center">类别编号</div>
					<div field="name" headerAlign="center" align="center">会员类别</div>
					<div field="describe" headerAlign="center" align="center">对象描述</div>
					<div field="excel" headerAlign="center" align="center" renderer="UserType.excelRenderer">会员</div>
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
					<div field="modifyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="remark" headerAlign="center" align="center">备注</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="UserTypeWindow" class="mini-window" 
		style="width:900px; height:400px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showModal="true" allowResize="true" allowDrag="true" >
		
		<div id="UserTypeForm">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					 <tr>
						<td style="width:80px;">类别编号：</td>
						<td colspan="3">
							<input name="code" id="code" class="mini-textbox" style="width:200px;" vtype="range:1000,9999" emptyText="请输入类别编号" />
						</td>
					</tr>
					<tr>
						<td style="width:80px;" >会员类别：</td>
						<td colspan="3">
							<input name="name" id="name" class="mini-textbox" style="width:200px;" required="true" emptyText="请输入会员类别"/>
						</td>					
					</tr>
					<tr>
						<td style="width:80px;">对象描述：</td>
						<td colspan="3"><input id="describe" name="describe" class="mini-textbox" emptyText="请输入对象描述"  style="width:300px;" required="true"/></td>
					</tr> 
					<tr>
						<td colspan="3" align="right">
							 <input id="id" name="id" class="mini-hidden"/>
		 					 <input name="url" class="mini-hidden"/>
							 <input name="action"  class="mini-hidden"/>
							<button style="margin-top: 50px;" id="operateBtn">保存</button>
						</td>
						<td colspan="3" align="left">
							<button style="margin-top: 50px;" id="closeBtn" onclick="UserType.close();">取消</button>
						</td>
					</tr>
					  <br/>
					   <tr>
					    <td colspan="6">操作信息:</td>
					  </tr>							  
					  <tr>
					    <td >创建时间</td>
					    <td ><input name="createTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/></td>
					    <td >修改时间</td>
					    <td ><input name="modifyTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/></td>
					    <td rowspan="2">备注</td>
					    <td rowspan="2"><input name="remark" class="mini-textarea" vtype ="maxLength:200" style="width:200px;"/>
					    </td>
					  </tr>
					  <tr>
					    <td>创建人</td>
					    <td><input name="createBy" class="mini-textbox"  enabled="false" allowInput="false" style="width:200px;"/></td>
					    <td>修改人</td>
					    <td><input name="modifyBy" class="mini-textbox"  enabled="false" allowInput="false" style="width:200px;" /></td>
					  </tr>				
				</table>
		</div>
	</div>		
		
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/customermgr/user_type.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>