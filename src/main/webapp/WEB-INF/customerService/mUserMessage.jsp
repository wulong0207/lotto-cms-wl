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
				<td width="5%"  style="white-space:nowrap;">
					 用户ID： <input id="userId" class="mini-textbox" style="width:220px;" emptyText="请输入用户ID" />
					 手机号码/邮箱： <input id="account" class="mini-textbox" style="width:220px;" emptyText="请输入手机号码/邮箱" />
					 发送状态：<input id="sendStatus"   class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" />
					 发送时间： <input id="beginTime" name="beginTime" class="mini-datepicker" allowInput="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" 
					 		showTime="true" showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick"/>
                    	到<input id="endTime" name="endTime" class="mini-datepicker" allowInput="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss"
                    		 showTime="true" showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick"/>
					 
					<a class="mini-button" onclick="Current.search" >查询</a>
				</td>
				<td width="30%"></td>
			</tr>
		</table>
	</div> 
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="customerService/mUserMessage/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
				 onrowdblclick="Current.onrowdblclick" onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="userId" headerAlign="center" align="center" allowSort="true" sortField="issue_code">用户ID</div>
					<div field="nickName" headerAlign="center" align="center">用户昵称</div>
					
					<div field="type" headerAlign="center" align="center" type= "comboboxcolumn" >发送类型
						 <input property="editor" class="mini-combobox" data="Dic.type" />
					</div>
					
					<div field="account" headerAlign="center" align="center">接收手机号/邮箱</div>
					
					<div field="status" headerAlign="center" align="center" type= "comboboxcolumn" >发送状态
						 <input property="editor" class="mini-combobox" data="Dic.sendStatus" />
					</div>
					
					<div field="messageType" headerAlign="center" align="center" type= "comboboxcolumn">消息类型
						<input property="editor" class="mini-combobox" data="Dic.messageType" />
					</div>
					<div field="message" headerAlign="center" align="center" renderer="Current.renderMsgContent">消息内容</div>
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">发送时间</div>
					<div field="drawCode" headerAlign="center" align="center" renderer="Current.reSend">操作</div>
				</div>
			</div>
		</div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/customerService/mUserMessage.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
