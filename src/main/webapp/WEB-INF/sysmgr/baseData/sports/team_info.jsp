<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
	<title>球队信息管理</title>
</head>
<body>
	<div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
				<td>
					<btn:operate privilege="ADD">
						<a  class="mini-button" iconCls="icon-add" plain="true" onclick="Current.add()">新增</a>
					</btn:operate>
					<btn:operate privilege="UPD">
						<a  class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.edit()">修改</a>
					</btn:operate>
					<btn:operate privilege="DEL">
						<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="Current.del()">删除</a>
					</btn:operate>
				</td>
				<td style="white-space:nowrap;">
					球队全称：<input id="teamFullName"  class="mini-textbox"  style="width:100px; showClose:true"/>&nbsp;
					球队简称：<input id="teamShortName"  class="mini-textbox"  style="width:100px; showClose:true"/>
					球队类型：<input id="searchTeamType"  class="mini-combobox"  style="width:80px;" allowInput="true" valueFromSelect = "true"  emptyText="请选择" />
					<a class="mini-button" onclick="Current.search()">查询</a>
				</td>
			</tr>
		</table>
	</div>

	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;"
				 url="sport/teamInfo/list" idField="id" allowResize="true" pageSize="30" multiSelect="true"
				 showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
				 onrowdblclick="Current.edit" onpreload="MiniCom.onpreload">

				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div field="id" width="3%" align="center" headerAlign="center">ID</div>
					<div field="teamDataUrl" headerAlign="center" align="center" >一比分球队ID</div>
					<div field="teamFullName" headerAlign="center" align="center">球队全称</div>
					<div field="teamShortName" headerAlign="center" align="center">球队简称</div>
					<div field="teamType" type= "comboboxcolumn" headerAlign="center" align="center">球队类型
						<input property="editor" class="mini-combobox" data="Dic.teamType" />
					</div>
					<div field="teamOrder" headerAlign="center" align="center">球队排名</div>
					<div field="createTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
					<div field="modifyTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="remark" headerAlign="center" align="center">备注</div>
					<div field="opt" renderer="Current.teamOpt" width="3%" headerAlign="center" align="center">操作</div>
				</div>
			</div>
		</div>
	</div>


	<div id="teamWindow" class="mini-window" style="width:700px; height:380px;" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
			<btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="Current.merge">保存</a>
			</btn:operate>
			<a class="mini-button" onclick="Current.close">关闭</a>
		</div>
		<div  id="teamform" class="form">
			<input name="id"  class="mini-hidden"/>
			<input id="operation" name="operation"  class="mini-hidden"/>
			<table width="100%" border="0" cellpadding="2" cellspacing="2">
				<tr>
					<td>一比分球队ID：</td>
					<td><input id="teamDataUrl" name="teamDataUrl" class="mini-textbox" style="width: 180px;" /></td>
					<td>球队类型：</td>
					<td><input id="teamType" name="teamType" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择" style="width:150px;"   required="true"/></td>					
				</tr>
				<tr>
					<td>球队全称：</td>
					<td><input name="teamFullName" class="mini-textbox" style="width: 180px;" required="true"/></td>
					<td>球队简称：</td>
					<td><input name="teamShortName" class="mini-textbox" style="width: 180px;" required="true"/></td>
				</tr>
				<tr>
					<td>球队排名：</td>
					<td><input id="teamOrder" name="teamOrder" class="mini-textbox" style="width: 180px;"/></td>
					<td>球队MD5：</td>
					<td><input id="md5Value" name="md5Value" class="mini-textbox" style="width: 180px;" enabled="false"/></td>					
				</tr>
				<tr>
					<td>球队Logo：</td>
					<td>
						<input id="logoUrl" name="logoUrl"  class="mini-hidden"/>
						<input type="button" value="从图库选择" onclick="Current.openImage()"/>
					</td>
					<td><img  id ="img" alt="彩种Logo" width="100px;" height="100px;"></td>
					<td></td>
				</tr>
			</table>


			<fieldset style="border:solid 1px #aaa; padding:3px;">
				<legend>操作信息：</legend>
				<table style="width:100%;">
					<tr>
						<td style="width:100px;">创建时间</td>
						<td style="width:150px;"><input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td style="width:100px;">修改时间</td>
						<td style="width:150px;"><input name="modifyTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td style="width:100px;" rowspan="2">备注</td>
						<td style="width:150px;" rowspan="2"><input name="remark" class="mini-textarea"  enabled="false"/></td>
					</tr>
					<tr>
						<td style="width:100px;">创建人</td>
						<td style="width:150px;"><input name="createBy" class="mini-textbox" enabled="false"/></td>
						<td style="width:100px;">修改人</td>
						<td style="width:150px;"><input name="modifyBy" class="mini-textbox" enabled="false"/></td>
					</tr>
				</table>
			</fieldset>
		</div>
	</div>

	<div id="teamSourceWindow" class="mini-window" title="球队信息渠道管理" style="width:1300px; height:800px;" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
		<div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
			<table style="width:100%;">
				<tr>
					<td style="white-space:nowrap;">
						球队全称：<input id="teamSourceName"  class="mini-textbox"  style="width:100px; showClose:true"/>&nbsp;
						球队简称：<input id="teamSourceAbbrName"  class="mini-textbox"  style="width:100px; showClose:true"/>
						球队类型：<input id="teamSourceType"  class="mini-combobox"  style="width:80px;" allowInput="true" valueFromSelect = "true"  emptyText="请选择" oncloseclick="Cms.onCloseClick" />
						渠道：<input id="teamSource"  class="mini-combobox"  style="width:80px;" allowInput="true" valueFromSelect = "true"  emptyText="请选择" oncloseclick="Cms.onCloseClick" />
						<input id="teamId" class="mini-hidden">
						<a class="mini-button" onclick="Current.searchSource()">查询</a>
					</td>
				</tr>
			</table>
		</div>

	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="teamSourceDatagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;"
				 url="sport/teamInfo/source/list" idField="id" allowResize="true" pageSize="30" multiSelect="true"
				 showColumnsMenu="true" showFilterRow="false" showFilterRow="false" onpreload="MiniCom.onpreload"
				 onrowdblclick="Cms.editRow('teamSourceDatagrid')">

				<div property="columns">
					<div field="id" headerAlign="center" align="center">ID</div>
					<div field="teamId" headerAlign="center" align="center">球队ID</div>
					<div field="teamName" headerAlign="center" align="center">球队全称
						<input property="editor" class="mini-textbox"/>
					</div>
					<div field="teamAbbrName" headerAlign="center" align="center">球队简称
						<input property="editor" class="mini-textbox"/>
					</div>
					<div field="teamType" type="comboboxcolumn" headerAlign="center" align="center">球队类型
						<input property="editor" class="mini-combobox" data="Dic.teamType" />
					</div>
					<div field="sourceId" headerAlign="center" align="center">球队id
						<input property="editor" class="mini-textbox"/>
					</div>
					<div field="source" type= "comboboxcolumn" headerAlign="center" align="center">渠道
						<input property="editor" class="mini-combobox" data="Dic.source" />
					</div>
					<div field="opt" renderer="Current.teamSourceOpt" width="3%" headerAlign="center" align="center">操作</div>
				</div>
			</div>
		</div>
	</div>
	</div>



</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/sysmgr/baseData/sports/team_info.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
