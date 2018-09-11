<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>出票管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;" id="searchCondition">
			<tr>
			     <td width="15%" nowrap="nowrap">
			     	<a id="synMoney" class="mini-button" plain="true" onclick="Current.synMoney()">同步余额</a>
			        <btn:operate privilege="ADD">
					<a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="Current.addwin()">添加</a>
					</btn:operate> 	
			     	<btn:operate privilege="UPD|SEARCH">
					<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.editwin()">修改</a>
					</btn:operate>
				</td>
			</tr>
			<tr>
			<td width="5%" style="white-space:nowrap;">
				        渠道ID：<input id="ticketChannelId" name ="ticketChannelId" class="mini-textbox" style="width:100px;"/>     
				       出票商名称：<input id="drawerName"   name ="drawerName" class="mini-combobox" allowInput="true" style="width:150px;" emptyText="请选择"   oncloseclick="Cms.onCloseClick" showClose="true" />
				 <a class="mini-button" onclick="Current.search()">查询</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="90%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="ticketmgr/ticketchannel/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" onrowdblclick="Current.onrowdblclick">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="ticketChannelId"  headerAlign="center" align="center">渠道ID</div>
					<div field="drawerName"  headerAlign="center" align="center">出票商名称</div>
					<div field="drawerAccount"  headerAlign="center" align="center">出票账户</div>
					<div field="accountPassword"  headerAlign="center" align="center">账户密码</div>
					<div field="authCode"  headerAlign="center" align="center">认证码</div>
					<div field="sendUrl"  headerAlign="center" align="center">送票地址</div>
					<div field="searchUrl"  headerAlign="center" align="center">查询地址</div>
					<div field="noticeUrl"  headerAlign="center" align="center">通知地址</div>
					<div field="accountBalance" headerAlign="center" align="center" numberFormat="c">账户余额</div>
					<div field="modifyBy"  headerAlign="center" align="center">操作人员</div>
					<div field="remark"  headerAlign="center" align="center">备注</div>
					<div field="updateTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">操作时间</div>
					<div field="createTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="editWindow" class="mini-window" title="" 
		style="width:1100px; height:370px;" showMaxButton="false" 
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
	            <legend >渠道基本信息：</legend>
	            <div style="padding:5px;">
	             <table width="100%;">
	            <tr>
	            <td width="13%">渠道ID</td>
	            <td width="20%"><input id="ticketChannelId" name="ticketChannelId" class="mini-textbox" style="width:100%;" required="true" vtype ="maxLength:20" enabled="true"/></td>
	            <td width="13%">出票商名称</td>
	            <td width="20%"><input name="drawerName" class="mini-textbox" style="width:100%;" required="true" vtype ="maxLength:20"/></td>
	            <td width="13%" >出票账户</td>
	            <td width="20%" ><input name="drawerAccount" class="mini-textbox" style="width:100%;" required="true" vtype ="maxLength:128"/></td>
	            </tr>
	            <tr>
	            <td>帐户密码</td>
	            <td><input name="accountPassword" class="mini-textbox" style="width:100%;" required="true" vtype ="maxLength:128"/></td>
	            <td>认证码</td>
	            <td><input name="authCode" class="mini-textbox" style="width:100%;" required="true" vtype ="maxLength:100"/></td>
	            <td>账户余额</td>
	            <td><input name="accountBalance" class="mini-textbox" style="width:100%;" required="true"/></td>
	            </tr>
	            <tr>
	            <td>渠道状态</td>
	            <td><input id="channelStatus_edit"  name ="channelStatus" class="mini-combobox"  style="width:100%;" emptyText="请选择" valueFromSelect = "true" required="true"/></td>
	            <td>开奖状态</td>
	            <td><input id="lotteryStatus_edit"  name ="lotteryStatus" class="mini-combobox"  style="width:100%;" emptyText="请选择" valueFromSelect = "true" required="true"/></td>
	            <td>出票状态</td>
	            <td><input id="ticketStatus_edit"  name ="ticketStatus" class="mini-combobox"  style="width:100%;" emptyText="请选择" valueFromSelect = "true" required="true"/></td>
	            </tr>
	            <tr>
	            <td>送票地址</td>
	            <td colspan="5"><input name="sendUrl" class="mini-textbox" style="width:100%;"required="true" vtype="maxLength:100"/></td>
	            </tr>
	             <tr>
	            <td>查询地址</td>
	            <td colspan="5"><input name="searchUrl" class="mini-textbox" style="width:100%;" required="true"vtype="maxLength:100"/></td>
	            </tr>
	             <tr>
	            <td>查询地址备用</td>
	            <td colspan="5"><input name="searchUrlSpare" class="mini-textbox" style="width:100%;" vtype="maxLength:100"/></td>
	            </tr>
	             <tr>
	            <td>通知地址</td>
	            <td colspan="5"><input name="noticeUrl" class="mini-textbox" style="width:100%;" required="true"vtype="maxLength:100"/></td>
	            </tr>
	            </table>            
	            </div>
	        </fieldset>
        
		   <fieldset style="border:solid 1px #aaa;padding:3px;">
            <legend >操作信息：</legend>
            <div style="padding:5px;">
             <table width="100%;">
			<tr>
			  <td width="13%">创建时间</td>
			  <td width="20%"><input name="createTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td width="13%">修改时间</td>
			  <td width="20%"><input name="updateTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td width="13%" rowspan="2">备注</td>
			  <td width="20%" rowspan="2"><input name="remark" class="mini-textarea" vtype ="maxLength:100" style="width:100%;"/></td>
			</tr>
			<tr>
			  <td>创建人</td>
			  <td><input name="createBy" class="mini-textbox" enabled="false" allowInput="false" style="width:100%;" /></td>
			  <td>修改人</td>
			  <td><input name="modifyBy" class="mini-textbox" enabled="false" allowInput="false" style="width:100%;" /></td>
			  <td ></td>
			  <td ></td>
			</tr>
            </table>            
            </div>
        </fieldset> 
		</div>
</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/ticketmgr/ticket_channel.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
