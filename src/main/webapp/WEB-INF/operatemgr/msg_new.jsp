<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	<script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
  	<script src="<%=basePath%>resources/js/public/insert_atcaret.js" type="text/javascript"></script>
  	
  	<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/operatemgr/msg_info.css" />
    <title>发送信息管理</title>
  </head>
  
<body>
	 <!-- 发送信息管理 -->
<div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
				<table style="width:100%;">
					<tr>
					     <td width="25%" nowrap="nowrap"> 
							<btn:operate privilege="ADD">
								<a id="addNewMsgWin" class="mini-button" iconCls="icon-add" plain="true" onclick="Current.addNewMsgWin()">发送新信息</a>
							</btn:operate>
							<btn:operate privilege="UPD">
								<a id="cancelNewMsg" class="mini-button" iconCls="icon-cancel" plain="true" onclick="Current.cancelNewMsg()">取消发送</a>
							</btn:operate>    
							<btn:operate privilege="UPD">
								<a id="editNewMsgWin" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.editNewMsgWin()">修改</a>
							</btn:operate>         		
						</td>
					</tr>
					<tr>
					  <td width="5%" style="white-space:nowrap;">
					  	  <span class="condition-name" style="font-size:9pt"> 发送批次号</span>&nbsp; <input id="publishMsgBatch" class="mini-textbox" style="width:150px;"/>&nbsp;&nbsp;
					  	  <span class="condition-name" style="font-size:9pt">信息分类</span>&nbsp;<input id="newMsgType" class="mini-combobox" onvaluechanged="Current.findTemplateListByType('newMsgType','msgTemplateId')"  style="width:100px;" emptyText="请选择"  valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" />&nbsp;&nbsp;
						  <span class="condition-name" style="font-size:9pt">消息类型</span>&nbsp;<input id="msgTemplateId"   class="mini-combobox"  style="width:150px;" emptyText="请选择"  valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" />&nbsp;&nbsp;
						</td>
					</tr>
					<tr>
					  <td width="5%" style="white-space:nowrap;">
						  <input id="newMsgTimeType"   class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" />
						  <input id="publishStartTime" class="mini-datepicker"  allowInput="false"  style="width:180px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
						        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
						        <span class="condition-name" style="font-size:9pt">到&nbsp;</span><input id="publishEndTime" class="mini-datepicker"  allowInput="false"  style="width:180px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
						        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>&nbsp;&nbsp;
						  <span class="condition-name" style="font-size:9pt">信息状态</span>&nbsp;<input id="newMsgStatus"   class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" />&nbsp;&nbsp;
						  <a class="mini-button" onclick="Current.search()">查询</a>
						</td>
					</tr>
				</table>
		</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
		<div size="90%" showCollapseButton="true" style="border:0;">
			<div id="publish-datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="operatemgr/msginfo/newmsglist" idField="id" allowResize="false" pageSize="30" multiSelect="true" onrowdblclick="Current.editNewMsgWin">
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="msgBatch" headerAlign="center" align="center">发送批次号</div>
					<div field="msgType" type="comboboxcolumn" headerAlign="center" align="center">消息分类
						     <input property="editor" class="mini-combobox" data="Dic.msgType" />
						</div>
					<div field="templateId" type="comboboxcolumn" headerAlign="center" align="center">消息类型
					     <input property="editor" class="mini-combobox" data="Dic.template" />
					</div>
					<div field="preSendTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">发送时间</div>
					<div field="status" type= "comboboxcolumn" headerAlign="center" align="center">信息状态
					     <input property="editor" class="mini-combobox" data="Dic.newMsgStatus" />
					</div>
					<div field="mobTitle" headerAlign="center" align="center">手机短信标题</div>
					<div field="siteTitle" headerAlign="center" align="center">站内信标题</div>
					<div field="appTitle" headerAlign="center" align="center">APP通知标题</div>
					<!-- <div field="wechatTitle" headerAlign="center" align="center">微信公众号标题</div> -->
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 发送新信息窗体 -->
	<div id="sendMsgWindow" class="mini-window" title="发送新信息"
		style="width:1100px; height:800px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowDrag="true">
		
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		    <btn:operate privilege="ADD">
				<a class="mini-button" onclick="Current.doSubmit()">确认</a>
			</btn:operate>
		</div>
		<div  id="editform">
			 <input name="id"  class="mini-hidden"/>
		     <input name="url"  class="mini-hidden"/>
			 <input name="action"  class="mini-hidden"/>
			 <input name="sendUsersPath"  class="mini-hidden"/>
			 <input name="msgBatch"  class="mini-hidden"/>
			 <input id="appFields" name="appFields"  class="mini-hidden"/>
			 <input id="wechatFields" name="wechatFields"  class="mini-hidden"/>
				<fieldset style="border:solid 1px #aaa; padding:3px;">
	             	<legend><span>基本信息</span></legend>
			        <div style="padding:5px;">
	                    <table style="width:100%">
						 	<tr>
						 		<td style="width:15px;">消息分类</td>
						 		<td><input id ="msgType_add" name="msgType"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true" required="true" onvaluechanged="Current.findTemplateListByType('msgType_add','template_add')"/></td>
						 		<td style="width:100px;">信息状态</td>
						 		<td><input id ="status_add" name="status" class="mini-combobox" emptyText="请选择" valueFromSelect ="true"  required="true"/></td>
						 	</tr>
						 	<tr>
						 		<td style="width:100px;">信息类型</td>
						 		<td><input id ="template_add" name="templateId" class="mini-combobox" emptyText="请选择" valueFromSelect ="true"  required="true" onvaluechanged="Current.selectTemplate"/></td>
						 		<td>待发送时间</td>
						 		<td><input id ="preSendTime_add" name="preSendTime" class="mini-datepicker"  allowInput="false" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" width="55%"/></td>
						 	</tr>
						 	<tr>
						 		<td>发送平台</td>
						 		<td colspan="3">
						 			<div id="clientSendStatus">
							 			<div id ="mobType" name="mobStatus"  class="mini-checkbox" trueValue="1" falseValue="0" text="手机短信" onvaluechanged="Current.onValueChanged('mobType')"></div>
							 			<div id ="siteType" name="siteStatus"  class="mini-checkbox" trueValue="1" falseValue="0" text="站内信" onvaluechanged="Current.onValueChanged('siteType')"></div>
							 			<div id ="appType" name="appStatus"  class="mini-checkbox" trueValue="1" falseValue="0" text="APP通知" onvaluechanged="Current.onValueChanged('appType')"></div>
							 			<div id ="wechatType" name="wechatStatus"  class="mini-checkbox" style="display:none" trueValue="1" falseValue="0" text="微信公众号" onvaluechanged="Current.onValueChanged('wechatType')"></div>
							 		</div>
						 		</td>
						 	</tr>
						 	<tr>
						 		<td>发送用户名单</td>
						 		<td colspan="3">
							 		<div id="sendUsers" class="mini-radiobuttonlist" repeatItems="2" repeatLayout="table" repeatDirection="horizontal" onvaluechanged="Current.selectUserType(this)"
				    					name="userType" textField="text" valueField="id" data="[{'id':'allUser','text':'所有用户'},{'id':'uploadUser','text':'上传用户名单'}]" style="float:left"></div>
				    				<div style="font-size:9pt" id="upload_div"><input id="uploadUserfile" class="mini-htmlfile" name="file" limitType="*.txt" required="true"/>&nbsp;说明: 1.必须是“.txt”文件; 2.一行一个会员id</div>
						 		</td>
						 	</tr>
						 	<tr>
						 		<td>跳转界面</td>
						 		<td><input id ="urlLottery" name="toLotteryCode" class="mini-combobox" emptyText="请选择" valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true"/></td>
						 		<td>跳转链接地址</td>
						 		<td><input id="activityUrl" name="activityUrl"  class="mini-textbox" style="width:100%;"/></td>
						 	</tr>
						 	<tr>
						 		<td>发送理由</td>
						 		<td colspan="3"><input name="sendReason" class="mini-textarea" style="width:100%;height:100%;"/></td>
						 	</tr>
						 </table>
	               </div>
	         </fieldset>
			
			<span>&nbsp;</span>
		     <fieldset style="border:solid 1px #aaa; padding:3px;">
			     <div id="templateContent">
		            <div style="padding:5px;" class="mobType">
		             	<fieldset style="border:solid 1px #aaa; padding:3px;">
		             		<legend><span>手机短信</span></legend>
				            <div style="padding:5px;">
				             	 <table border="0" cellpadding="1" cellspacing="2" width="100%">
					                <tr>
					                    <td style="width:15px;">信息标题</td>
					                    <td style="width:150px">
					                        <input name="mobTitle" class="mini-textbox" style="width:100%"/>
					                    </td>
					                </tr>
					                <tr>
					                    <td style="width:15px;">信息内容</td>
					                    <td style="width:150px">
					                        <input name="mobContent" class="mini-textarea" style="width:100%;height:60px;"/>
					                    </td>
					                </tr>
	                			</table>
			            	</div>
	        			</fieldset>
	            	</div>
	            	
	            	<div style="padding:5px;" class="siteType">
		             	<fieldset style="border:solid 1px #aaa; padding:3px;">
		             		<legend><span>站内信</span></legend>
				            <div style="padding:5px;">
				             	 <table border="0" cellpadding="1" cellspacing="2" width="100%">
					                <tr>
					                    <td style="width:15px;">信息标题</td>
					                    <td style="width:150px">
					                        <input name="siteTitle" class="mini-textbox" style="width:100%"/>
					                    </td>
					                </tr>
					                <tr>
					                    <td style="width:15px;">信息内容</td>
					                    <td style="width:150px">
					                        <input name="siteContent" class="mini-textarea" style="width:100%;height:60px;"/>
					                    </td>
					                </tr>
	                			</table>
			            	</div>
	        			</fieldset>
	            	</div>
	            	<div style="padding:5px;" class="appType">
		             	<fieldset style="border:solid 1px #aaa; padding:3px;">
		             		<legend><span>APP通知</span></legend>
				            <div style="padding:5px;">
				             	 <table border="0" cellpadding="1" cellspacing="2" width="100%">
					                <tr id="appTitleTr">
					                    <td style="width:15px;">信息标题</td>
					                    <td style="width:150px">
					                        <input name="appTitle" class="mini-textbox" style="width:100%"/>
					                    </td>
					                </tr>
					                <tr>
					                    <td style="width:15px;">信息内容</td>
					                    <td style="width:150px">
					                        <input name="appContent" class="mini-textarea" style="width:100%;height:60px;"/>
					                    </td>
					                </tr>
	                			</table>
			            	</div>
	        			</fieldset>
	            	</div>
	            	<div style="padding:5px;display:none" class="wechatType">
		             	<fieldset style="border:solid 1px #aaa; padding:3px;">
		             		<legend><span>微信公众号</span></legend>
				            <div style="padding:5px;">
				             	 <table border="0" cellpadding="1" cellspacing="2" width="100%">
				             	 	<tr>
					                    <td style="width:15px;">微信公众号模板</td>
					                    <td style="width:150px">
					                        <input id="wechatTemplate_add" name="wechatId" emptyText="" valueFromSelect ="true" class="mini-combobox" style="width:150px;"/>
					                    </td>
				                	</tr>
					                <tr>
					                    <td style="width:15px;">信息标题</td>
					                    <td style="width:150px">
					                        <input name="wechatTitle" class="mini-textbox" style="width:100%"/>
					                    </td>
					                </tr>
					                <tr id="tr_after_add">
					                    <td style="width:15px;">头部内容</td>
					                    <td style="width:150px">
					                        <input name="headerCon" class="mini-textarea" style="width:100%;height:60px;"/>
					                    </td>
					                </tr>
					                <tr>
					                    <td style="width:15px;">尾部内容</td>
					                    <td style="width:150px">
					                        <input name="footerCon" class="mini-textarea" style="width:100%;height:60px;"/>
					                    </td>
					                </tr>
	                			</table>
			            	</div>
	        			</fieldset>
            		</div>
            	</div>
        	  </fieldset>
        	  <div style="padding:5px;">
	        	   <table style="width:100%;">
	        	   		<tr>
	        	   			<td>创建时间</td>
	        	   			<td><input name="createTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
	        	   			<td>修改时间</td>
	        	   			<td><input name="updateTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
	        	   			<td rowspan="2">备注</td>
	        	   			<td rowspan="2"><input name="remark" class="mini-textarea" style="width:100%;height:100%;"/></td>
	        	   		</tr>
	        	   		<tr>
	        	   			<td>创建人</td>
	        	   			<td><input name="createBy" class="mini-textbox" enabled="false"/></td>
	        	   			<td>修改人</td>
	        	   			<td><input name="modifyBy" class="mini-textbox" enabled="false"/></td>
	        	   		</tr>
	        	   </table>
        	  </div>
		</div>
	</div>
	
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/msg_new.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
