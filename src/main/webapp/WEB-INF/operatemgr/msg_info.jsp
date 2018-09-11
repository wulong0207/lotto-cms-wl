<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	
  	<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/operatemgr/msg_info.css" />
    <title>通知信息管理</title>
  </head>
  
<body>
  <!-- 通知信息管理 -->
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td width="25%" nowrap="nowrap"> 
					<btn:operate privilege="UPD">
						<a id="repeatSend" class="mini-button" iconCls="icon-reload" plain="true" onclick="Current.updateMsgStatus(0)">重新发送</a>
					</btn:operate>
				</td>
			</tr>
			<tr>
				<td width="5%" style="white-space:nowrap;">
				    <input id="searchTimeType"   class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" />
				 	<input id="startTime" class="mini-datepicker"  allowInput="false"  style="width:180px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        <span class="condition-name" style="font-size:9pt">到&nbsp;</span><input id="endTime" class="mini-datepicker"  allowInput="false"  style="width:180px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/> &nbsp;&nbsp;
				        <span class="condition-name" style="font-size:9pt">发送方式</span>&nbsp;<input id="sendType"   class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" />&nbsp;&nbsp;
				  <span class="condition-name" style="font-size:9pt">信息状态</span>&nbsp;<input id="status"   class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" />
				</td>
			</tr>
			<tr>
			  <td width="5%" style="white-space:nowrap;">
				 <input id="searchUserType"   class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" />
				 <input id="searchUserInfo" class="mini-textbox" style="width:150px;"/>&nbsp;&nbsp;
				 <span class="condition-name" style="font-size:9pt">信息分类</span>&nbsp;<input id="msgType" class="mini-combobox" onvaluechanged="Current.findTemplateListByType('msgType','templateId')"  style="width:100px;" emptyText="请选择"  valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" />&nbsp;&nbsp;
				 <span class="condition-name" style="font-size:9pt">信息类型</span>&nbsp;
				 <input id="templateId" class="mini-combobox"  style="width:150px;" emptyText="请选择"  valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" />&nbsp;&nbsp;
				 <span class="condition-name" style="font-size:9pt"> 发送批次号</span>&nbsp; 
				 <input id="msgBatch" class="mini-textbox" style="width:150px;"/>&nbsp;&nbsp;
					<a class="mini-button" onclick="Current.search()">查询</a>
			  </td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
		<div size="90%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="operatemgr/msginfo/list" idField="id" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" onrowdblclick="Current.onRowClick">
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="id" headerAlign="center" align="center">信息ID</div>
					<div field="nickName" headerAlign="center" align="center">用户昵称</div>
					<div field="msgType" type="comboboxcolumn" headerAlign="center" align="center">消息分类
					     <input property="editor" class="mini-combobox" data="Dic.msgType" />
					</div>
					<div field="templateId" type="comboboxcolumn" headerAlign="center" align="center">消息类型
					     <input property="editor" class="mini-combobox" data="Dic.template" />
					</div>
					<div field="sendType" type="comboboxcolumn" headerAlign="center" align="center">发送方式
					     <input property="editor" class="mini-combobox" data="Dic.sendType" />
					</div>
					<div field="status" type= "comboboxcolumn" headerAlign="center" align="center">信息状态
					     <input property="editor" class="mini-combobox" data="Dic.status" />
					</div>
					<!-- <div field="msgTitle" headerAlign="center" align="center">信息标题</div> -->
					<div field="msgBatch" headerAlign="center" align="center">发送批次号</div>
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
					<div field="sendTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">发送时间</div>	
					<div field="readTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">阅读时间</div>
					<!-- <div field="msgDesc" headerAlign="center" align="center">备注</div> -->
				</div>
			</div>
		</div>
	</div>
	
	<!-- 通知信息详情 -->
	<div id="msgDetailWindow" class="mini-window" title="通知信息详情"
		style="width:1100px; height:500px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowDrag="true" >
		<div id="msgDetail_div" style="width:100%;margin-top:10px;">
			<div><b>信息详情：</b></div>
    		<table style="width:100%;" class="detail_table">
    			<tr>
    				<td class="td_name">用户账号</td><td><input name="accountName" class="mini-textbox asLabel" readOnly="true" isValid="true" style="width:100%" /></td>
    				<td class="td_name">用户昵称</td><td><input name="nickName" class="mini-textbox asLabel" readOnly="true" isValid="true" style="width:100%" /></td>
    				<td class="td_name">创建人</td><td><input name="createBy" class="mini-textbox asLabel" readOnly="true" isValid="true" style="width:100%" /></td>
    			</tr>
    			<tr>
    				<td class="td_name">发送时间</td><td><input name="sendTime" class="mini-datepicker asLabel"  readOnly="true" isValid="true" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
    				<td class="td_name">发送方式</td><td><input id ="msgSendType_detail" name="sendType"  class="mini-combobox asLabel" readOnly="true" isValid="true" style="width:100%;"/></td>
    				<td class="td_name">阅读时间</td><td><input name="readTime" class="mini-datepicker asLabel"  readOnly="true" isValid="true" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
    			</tr>
    			<tr>
    				<td class="td_name">用户手机号码</td><td><input name="cusMobile" class="mini-textbox asLabel" readOnly="true" isValid="true" style="width:100%" /></td>
    				<td class="td_name">信息状态</td><td><input id ="msgStatus_detail" name="status"  class="mini-combobox asLabel" readOnly="true" isValid="true" style="width:100%;"/></td>
    				<td class="td_name">创建时间</td><td><input name="createTime" class="mini-datepicker asLabel"  readOnly="true" isValid="true" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
    			</tr>
    			<tr>
    				<td class="td_name">发送批次号</td><td><input name="msgBatch" class="mini-textbox asLabel" readOnly="true" isValid="true" style="width:100%" /></td>
    				<td class="td_name">消息分类</td><td><input id ="msg_type_detail" name="msgType" class="mini-combobox asLabel" readOnly="true" isValid="true" style="width:100%;"/></td>
    				<td class="td_name">消息类型</td><td><input id ="template_detail" name="templateId"  class="mini-combobox asLabel" readOnly="true" isValid="true" style="width:100%;"/></td>
    			</tr>
    			<tr>
    				<td class="td_name">信息标题</td><td id="msgTitle" colspan="5"></td>
    			</tr>
    			<tr>
    				<td class="td_name">发送内容</td><td id="msgContent" colspan="5"></td>
    			</tr>
    			
    			<tr>
    				<td class="td_name">发送失败原因</td><td id="sendError" colspan="5"></td>
    			</tr>
    			<tr>
    				<td class="td_name">备注</td><td id="msgDesc" colspan="5"></td>
    			</tr>
    		</table>
        </div>
       	<!-- <div style="width:100%;margin-top:10px;" >
    		<div><b>用户设置接收信息详情：</b></div>
    		<div id="UserConfigGrid" class="mini-datagrid" url="operatemgr/msginfo/userconfiglist" showPager="false" style="width:100%;height:50%;" >
					
					<div property="columns">
						<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
						<div field="typeId" width="7%" type="comboboxcolumn" headerAlign="center">通知类型
					     	<input property="editor" class="mini-combobox" data="Dic.template" />
					    </div>
						<div type="checkboxcolumn" field="mob" trueValue="1" falseValue="0" width="8%" headerAlign="center">手机短信</div>
						<div type="checkboxcolumn" field="site" trueValue="1" falseValue="0" width="8%" headerAlign="center">站内信</div>
						<div type="checkboxcolumn" field="app" trueValue="1" falseValue="0" width="8%" headerAlign="center">APP通知</div>
						<div type="checkboxcolumn" field="wechat" trueValue="1" falseValue="0" width="8%" headerAlign="center">微信公众号</div>
					</div>
				</div>
        </div> -->
	</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/msg_info.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
