<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
	<title>赛事信息管理</title>
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
					赛事全称：<input id="matchFullName"  class="mini-textbox"  style="width:100px; showClose:true"/>&nbsp;
					赛事简称：<input id="matchShortName"  class="mini-textbox"  style="width:100px; showClose:true"/>
					赛事类型：<input id="searchMatchType"  class="mini-combobox"  style="width:80px;" allowInput="true" valueFromSelect = "true"  emptyText="请选择" />
					是否是五大联赛：<input id="isFiveLeague"  class="mini-combobox"  style="width:80px;" allowInput="true" valueFromSelect = "true"  emptyText="请选择" />
					<a class="mini-button" onclick="Current.search()">查询</a>
				</td>
			</tr>
		</table>
	</div>

	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;"
				 url="sport/matchInfo/list" idField="id" allowResize="true" pageSize="30" multiSelect="true"
				 showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
				 onrowdblclick="Current.edit" onpreload="MiniCom.onpreload">

				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div field="id" headerAlign="center" align="center">ID</div>
					<div field="matchDataUrl" headerAlign="center" align="center" >一比分赛事ID</div>
					<div field="matchFullName" headerAlign="center" align="center">赛事全称</div>
					<div field="matchShortName" headerAlign="center" align="center">赛事简称</div>
					<div field="matchType" type= "comboboxcolumn" headerAlign="center" align="center">赛事类型
						<input property="editor" class="mini-combobox" data="Dic.matchType" />
					</div>
					<div field="fiveLeague" type= "comboboxcolumn" headerAlign="center" align="center">是否是5大联赛
						<input property="editor" class="mini-combobox" data="Dic.isOrNot" />
					</div>
					<div field="createTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
					<div field="modifyTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="remark" headerAlign="center" align="center">备注</div>
					<div field="opt" renderer="Current.matchOpt" width="3%" headerAlign="center" align="center">操作</div>
				</div>
			</div>
		</div>
	</div>


	<div id="matchWindow" class="mini-window" style="width:700px; height:380px;" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
			<btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="Current.merge">保存</a>
			</btn:operate>
			<a class="mini-button" onclick="Current.close">关闭</a>
		</div>
		<div  id="matchform" class="form">
			<input name="id"  class="mini-hidden"/>
			<input id="operation" name="operation"  class="mini-hidden"/>
			<table width="100%" border="0" cellpadding="2" cellspacing="2">
				<tr>
					<td>一比分赛事ID：</td>
					<td><input id="matchDataUrl" name="matchDataUrl" class="mini-textbox" style="width: 180px;" /></td>
					<td>赛事类型：</td>
					<td><input id="matchType" name="matchType" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择" style="width:150px;"   required="true"/></td>					
				</tr>
				<tr>
					<td>赛事全称：</td>
					<td><inpu name="matchFullName" class="mini-textbox" style="width: 180px;"  required="true" /></td>
					<td>赛事简称：</td>
					<td><input name="matchShortName" class="mini-textbox" style="width: 180px;"  required="true" /></td>
				</tr>
				<tr>
					<td>是否是五大联赛：</td>
					<td><input id="fiveLeague" name="fiveLeague" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择" style="width:100px;"   required="true"/></td>
					<td>赛事MD5值：</td>
					<td><input id="md5Value" name="md5Value" class="mini-textbox" style="width: 180px;" required="true" enabled="false"/></td>					
				</tr>
				<tr>
					<td>赛事颜色配置：</td>
					<td><input id="matchColor" name="matchColor" value="000000"  style="width:100px;" class="jscolor {hash:true}" /></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>赛事Logo：</td>
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
						<td style="width:150px;" rowspan="2"><input name="remark" class="mini-textarea" enabled="false"/></td>
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

	<div id="matchSourceWindow" class="mini-window" title="赛事信息渠道管理" style="width:1300px; height:800px;" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
		<div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
			<table style="width:100%;">
				<tr>
					<td style="white-space:nowrap;">
						赛事全称：<input id="matchSourceName"  class="mini-textbox"  style="width:100px; showClose:true"/>&nbsp;
						赛事简称：<input id="matchSourceAbbrName"  class="mini-textbox"  style="width:100px; showClose:true"/>
						赛事类型：<input id="matchSourceType"  class="mini-combobox"  style="width:80px;" allowInput="true" valueFromSelect = "true"  emptyText="请选择" oncloseclick="Cms.onCloseClick" />
						渠道：<input id="matchSource"  class="mini-combobox"  style="width:80px;" allowInput="true" valueFromSelect = "true"  emptyText="请选择" oncloseclick="Cms.onCloseClick" />
						<input id="matchId" class="mini-hidden">
						<a class="mini-button" onclick="Current.searchSource()">查询</a>
					</td>
				</tr>
			</table>
		</div>

	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="matchSourceDatagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;"
				 url="sport/matchInfo/source/list" idField="id" allowResize="true" pageSize="30" multiSelect="true"
				 showColumnsMenu="true" showFilterRow="false" showFilterRow="false" onpreload="MiniCom.onpreload"
				 onrowdblclick="Cms.editRow('matchSourceDatagrid')">
				 
				<div property="columns">
					<div field="id" headerAlign="center" align="center">ID</div>
					<div field="matchId" headerAlign="center" align="center">赛事ID</div>
					<div field="matchName" headerAlign="center" align="center">赛事全称
						<input property="editor" class="mini-textbox"  />
					</div>
					<div field="matchAbbrName" headerAlign="center" align="center">赛事简称
						<input property="editor" class="mini-textbox"  />
					</div>
					<div field="matchType" type= "comboboxcolumn" headerAlign="center" align="center">赛事类型
						<input property="editor" class="mini-combobox" data="Dic.matchType" />
					</div>
					<div field="sourceId" headerAlign="center" align="center">渠道id
						<input property="editor" class="mini-textbox"  />
					</div>
					<div field="source" type= "comboboxcolumn" headerAlign="center" align="center">渠道
						<input property="editor" class="mini-combobox" data="Dic.source" />
					</div>
					<div field="opt" renderer="Current.matchSourceOpt" width="3%" headerAlign="center" align="center">操作</div>
				</div>
			</div>
		</div>
	</div>
	</div>



</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>resources/js/public/jscolor.min.js"></script>
<script src="<%=basePath%>resources/js/sysmgr/baseData/sports/match_info.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
