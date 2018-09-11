<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
	<title>单关加奖</title>
	<style type="text/css">
		.asLabel .mini-textbox-input{
			text-align: center;
			font-size: 15px;
		}
	</style>
</head>
<body>
<div id="activityConfigForm" class="form">
	<input name="id"  class="mini-hidden"/>
	<input name="url"  class="mini-hidden" value="operatemgr/activity/config/merge"/>
	<input name="type" id="type"  class="mini-hidden"/>
	<input name="matchStatus" id="matchStatus"  class="mini-hidden"/>
	<fieldset style="border:solid 1px #aaa; padding:3px;">
		<legend>活动配置：</legend>
		<table style="width:100%;">
			<tr>
				<td width="10%">活动编号</td>
				<td width="90%"><input id="activityCode" name="activityCode" class="mini-textbox" style="width:200px;" enabled="false"/></td>
			</tr>
		</table>
	</fieldset>

	<fieldset style="border:solid 1px #aaa; padding:3px;height: 580px;">
		<legend>活动规则信息：</legend>
		<div style="width:100%;">
			<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
				<table style="width:100%;">
					<tr>
						<td width="10%" nowrap="nowrap">
							<btn:operate privilege="ADD">
								<a class="mini-button" iconCls="icon-add" plain="true" onclick="Current.add">添加赛事</a>
							</btn:operate>
							<btn:operate privilege="UPD">
								<a class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.edit">编辑</a>
							</btn:operate>
							<btn:operate privilege="UPD">
								<a class="mini-button" iconCls="icon-remove" plain="true" onclick="Current.del">删除</a>
							</btn:operate>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div id="activityRule_datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:350px;"
			 idField="id" multiSelect="true" showPager="false" url="operatemgr/activity"
			 showColumnsMenu="true" onrowdblclick="Current.edit" showFilterRow="false">
			<div property="columns">
				<div type="checkcolumn" width="3%"></div>
				<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
				<div field="systemCode" width="8%" headerAlign="center" align="center">系统编号</div>
				<div field="officialMatchCode" width="8%" headerAlign="center" align="center">赛事编号</div>
				<div field="matchName" width="8%" headerAlign="center" align="center">联赛</div>
				<div field="homeName" width="8%" headerAlign="center" align="center">主队</div>
				<div field="visitiName" width="8%" headerAlign="center" align="center">客队</div>
				<div field="startTime" width="13%" headerAlign="center" align="center">比赛时间</div>
				<div field="saleEndTime" width="13%" headerAlign="center" align="center">销售截止</div>
				<div field="matchStatus" width="8%" headerAlign="center" align="center" type="comboboxcolumn">销售状态
					<input property="editor" class="mini-combobox" data="Dic.matchStatus" />
				</div>
				<div header="精选单关固赔活动状态"  headerAlign="center">
					<div property="columns">
						<div field="openStatus" width="8%" headerAlign="center" align="center" type="comboboxcolumn">是否开放
							<input property="editor" class="mini-combobox" data="Dic.isYesOrNo" />
						</div>
						<div field="spf" width="8%" headerAlign="center" align="center">胜平负</div>
						<div field="rfspf" width="8%" headerAlign="center" align="center">让球胜平负</div>
						<div field="passAdd" width="8%" headerAlign="center" align="center" renderer="Current.switchStyle">加奖选项</div>
					</div>
				</div>
			</div>
		</div>
	</fieldset>

	<fieldset style="border:solid 1px #aaa; padding:3px;">
		<legend>操作信息：</legend>
		<table style="width:100%;">
			<tr>
				<td style="width:100px;">创建时间</td>
				<td style="width:150px;"><input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td style="width:100px;">修改时间</td>
				<td style="width:150px;"><input name="modifyTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td style="width:100px;" rowspan="2">备注</td>
				<td style="width:150px;" rowspan="2"><input name="lotteryDesc" class="mini-textarea"/></td>
			</tr>
			<tr>
				<td style="width:100px;">创建人</td>
				<td style="width:150px;"><input name="createBy" class="mini-textbox" enabled="false"/></td>
				<td style="width:100px;">修改人</td>
				<td style="width:150px;"><input name="modifyBy" class="mini-textbox" enabled="false"/></td>
			</tr>
		</table>
	</fieldset>
	<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		<btn:operate privilege="ADD|UPD">
			<a class="mini-button" onclick="Current.merge">保存</a>
		</btn:operate>
		<a class="mini-button" onclick="Current.closeConfigWindow">关闭</a>
	</div>
</div>

<div id="sportAgainstWindow" class="mini-window" style="width:80%;height:74%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
	<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		<btn:operate privilege="ADD|UPD">
			<a class="mini-button" onclick="Current.mergeSportAgainst">确认</a>
			<a class="mini-button" onclick="Current.closeSportAgainstWindow">取消</a>
		</btn:operate>
	</div>
	<div id="sportAgainstForm" class="form">
		<input id="operation" name="operation"  class="mini-hidden"/>
		<div id="sportAgainst_datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;"
			 idField="id" multiSelect="true" url="operatemgr/activity/sport/list" pageSize="20"
			 showColumnsMenu="true" onrowdblclick="Current.edit" showFilterRow="false">
			<div property="columns">
				<div type="checkcolumn" width="3%"></div>
				<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
				<div field="systemCode" width="8%" headerAlign="center" align="center">系统编号</div>
				<div field="officialMatchCode" width="8%" headerAlign="center" align="center">赛事编号</div>
				<div field="matchName" width="8%" headerAlign="center" align="center">联赛</div>
				<div field="homeName" width="8%" headerAlign="center" align="center">主队</div>
				<div field="visitiName" width="8%" headerAlign="center" align="center">客队</div>
				<div field="matchStatus" width="8%" headerAlign="center" align="center" >销售状态</div>
			</div>
		</div>
	</div>
</div>

<div id="activitySportRuleWindow" class="mini-window" style="width:30%;height:30%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
	<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		<btn:operate privilege="ADD|UPD">
			<a class="mini-button" onclick="Current.mergeSportRule">确认</a>
			<a class="mini-button" onclick="Current.closeSportRuleWindow">取消</a>
		</btn:operate>
	</div>
	<div id="activitySportRuleForm" class="form">
		<input id="operate" name="operate"  class="mini-hidden"/>
		<input id="ruleId" name="id"  class="mini-hidden"/>
		<table  style="width:100%;">
			<tr>
				<td colspan="2" align="center">
					<input  name="homeName" class="mini-textbox asLabel" readOnly="true" value="textbox" />
					<span>VS</span>
					<input name="visitiName" class="mini-textbox asLabel" readOnly="true" value="textbox" />
				</td>
			</tr>
			<tr>
				<td width="34%">活动是否开放</td>
				<td width="66%">
					<div id="openStatus" name="openStatus" class="mini-radiobuttonlist" textField="text" valueField="id" required="true"></div>
				</td>
			</tr>
			<tr>
				<td>活动开放玩法</td>
				<td>
					<div id="lotteryChild" name="lotteryChild" class="mini-checkboxlist" textField="text" valueField="id" enabled="false"></div>
				</td>
			</tr>
			<tr>
				<td>赛事加奖选项</td>
				<td>
					<span id="win">胜</span>
					&nbsp;&nbsp;<input id="winPercent" name="winPercent" style="width:70px;" class="mini-textbox" required="true"/>
					&nbsp;&nbsp;<input id="winColor" name="winColor" value="000000"  style="width:70px;" class="jscolor {hash:true}" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<span id="draw">平</span>
					&nbsp;&nbsp;<input id="drawPercent" name="drawPercent" style="width:70px;" class="mini-textbox" required="true"/>
					&nbsp;&nbsp;<input id="drawColor" name="drawColor" value="000000"  style="width:70px;" class="jscolor {hash:true}" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<span id="lose">负</span>
					&nbsp;&nbsp;<input id="losePercent" name="losePercent" style="width:70px;" class="mini-textbox" required="true"/>
					&nbsp;&nbsp;<input id="loseColor" name="loseColor" value="000000"  style="width:70px;" class="jscolor {hash:true}" />
				</td>
			</tr>
		</table>
	</div>
</div>

</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>resources/js/public/jscolor.min.js"></script>
<script src="<%=basePath%>resources/js/operatemgr/activityConfigDetail/add_single.js?vsersion=<%=version%>" type="text/javascript"></script>
<script type="text/javascript">
    mini.parse();
    function initData(code) {
        Current.init(code);
    }
</script>
</html>

