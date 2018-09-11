<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>系统字典</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td width="15%" nowrap="nowrap" >
			         <btn:operate privilege="ADD">
					 <a id="addDic" class="mini-button" iconCls="icon-add" plain="true" onclick="addDic()">添加</a>
					 </btn:operate>
					 <btn:operate privilege="UPD">
					 <a id="editDic" class="mini-button" iconCls="icon-edit" plain="true" onclick="editDic()">修改</a>
					 </btn:operate>	
				</td>
				<td width="5%" style="white-space:nowrap;">
				         字典名称： <input id="name" name="name" class="mini-textbox" style="width:220px;" emptyText="请输入字典名称"/>
				         字典编码： <input id="code" name="code" class="mini-textbox" style="width:220px;" emptyText="请输入字典编码"/>
					<a class="mini-button" onclick="search()" >查询</a>
				</td>
				<td width="30%"></td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:96%;" style="border:0;">

		<div size="50%" showCollapseButton="true" style="border:0;">
			<div id="datagridDic" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;"
				 url="sysmgr/dic/list" idField="id" allowResize="true" pageSize="30" multiSelect="false"
				 onselectionchanged="onSelectionChanged" showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
				 onrowdblclick="onrowdblclick" >
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div field="dicCode" headerAlign="center" align="center">字典编码</div>
					<div field="dicName" headerAlign="center" align="center" >字典名称</div>
					<div field="remark" headerAlign="center" align="center" >备注</div>
					<div field="udpateTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
				</div>
			</div>
		</div>
		
		<div showCollapseButton="true">
			<div style="height: 100%">
				<div id="title_bar" class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
					<table style="width:100%;">
						<tr>
							<td width="15%"  style="white-space:nowrap;">
									<btn:operate privilege="ADD">
									<a  class="mini-button" iconCls="icon-add" plain="true" onclick="addDicDetail">添加</a>
									</btn:operate>
									<btn:operate privilege="UPD">
									<a  class="mini-button" iconCls="icon-ok" plain="true" onclick="editDicDetail" id="editDicDetail">修改</a>
									</btn:operate>
									<btn:operate privilege="DEL">
									<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="Cms.delete('datagridDicDetail','sysmgr/dic/detail','id')">删除</a>
									</btn:operate>
							</td>
						</tr>
					</table>
				</div>

				<div size="50%" showCollapseButton="true" style="border:0;">
					<div id="datagridDicDetail" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:93%;"
						 url="sysmgr/dic/list/detail" idField="id" allowResize="true" pageSize="20" multiSelect="false"
						 showColumnsMenu="true" showFilterRow="false" showFilterRow="false" onrowdblclick="onSelectionChanged">
						<div property="columns">
							<div type="checkcolumn" width="3%"></div>
							<div field="id" headerAlign="center" align="center">ID</div>
							<div field="dicDataName" headerAlign="center" align="center" >字典数据名称</div>
							<div field="dicDataValue" headerAlign="center" align="center" >字典数据值</div>
							<div field="orderBy" headerAlign="center" align="center" >排序</div>
							<div field="status" headerAlign="center" align="center" >状态</div>
							<div field="isFixed" headerAlign="center" align="center" >是否固定不变</div>
							<div field="updateTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
							<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
						</div>
					</div>
				</div>
             </div>
		</div>
	</div>
	
	<div id="editWindow" class="mini-window" title="" 
		style="width:380px; height:160px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		    <btn:operate privilege="ADD|UPD">
			<a class="mini-button" onclick="doSubmit">保存</a>
			</btn:operate>
		</div>
		<div id="editform">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					<tbody>
						<tr>
							<td width="30%" align="right" nowrap="nowrap">字典编码：</td>
							<td width="70%">
								<input name="url"  class="mini-hidden"/>
								<input name="action" class="mini-hidden" />
								<input name="dicCode" id ="dicCode" class="mini-textbox"  vtype="maxLength:20" style="width:220px;"  required="true"/>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">字典名称：</td>
							<td><input name="dicName" class="mini-textbox" vtype="maxLength:50" style="width:220px;"  required="true"/></td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">备注：</td>
							<td><input name="remark" class="mini-textbox" style="width:220px;"   required="true"/></td>
						</tr>
						
					</tbody>
				</table>
			</div>
		</div>
		
		
		<div id="editDetail" class="mini-window" title="" 
		style="width:380px; height:220px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
			<btn:operate privilege="ADD|UPD">
			<a class="mini-button" onclick="doSubmitDetail">保存</a>
			</btn:operate>
		</div>
		<div id="editformDetail">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					<tbody>
						<tr>
							<td width="30%" align="right" nowrap="nowrap">字典数据名称：</td>
							<td width="70%">
								<input name="action"  class="mini-hidden"/>
								<input name="id"  class="mini-hidden" />
								<input name="url"  class="mini-hidden" />
								<input name="dicCode"  class="mini-hidden" allowInput="false"  required="true"/>
								<input name="dicDataName" class="mini-textbox" vtype="maxLength:50" style="width:220px;"   required="true"/>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">字典数据值：</td>
							<td><input name="dicDataValue" class="mini-textbox" vtype="maxLength:128" style="width:220px;"   required="true"/></td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">排序：</td>
							<td><input name="orderBy" class="mini-textbox" vtype="int;maxLength:2" style="width:220px;"   required="true"/></td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">状态：</td>
							<td><input name="status"  class="mini-radiobuttonlist" value="0" data="Data.onOff" /> </td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">是否固定不变：</td>
							<td><input name="isFixed"  class="mini-radiobuttonlist" value="0" data="Data.yesNo" /> </td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/sysmgr/dictionary.js" type="text/javascript"></script>
</html>
