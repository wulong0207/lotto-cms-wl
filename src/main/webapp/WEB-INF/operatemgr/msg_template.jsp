<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	<script src="<%=basePath%>resources/js/public/insert_atcaret.js" type="text/javascript"></script>
  	<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/operatemgr/msg_info.css" />
    <title>信息模板管理</title>
    <style>
    	legend{
    		cursor:pointer;
    	}
    </style>
  </head>
  
  
  
<body>
	<!-- 设置微信公众号模板 -->
	<div id="editWechatSetWindow" class="mini-window" title="微信公众号"
		style="width:550px; height:250px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowDrag="true" >
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		    <btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="Current.doWechatTemplateSubmit()">保存</a>
			</btn:operate>
		</div>
		<div  id="editWechatSetform">
			<input name="id"  class="mini-hidden"/>
		     <input name="url"  class="mini-hidden"/>
			 <input name="action"  class="mini-hidden"/>
			 <input id="titles" name="title"  class="mini-hidden"/>
			 <input id="colors" name="color"  class="mini-hidden"/>
			 <table id="addWechatTemplateSet" style="width:100%">
			 	<tr>
			 		<td style="width:15%;">模板标题</td>
			 		<td style="width:85%;" colspan="3"><input id="wechatTemplateTitle" name="typeName" class="mini-textbox" style="width:90%" required="true"/></td>
			 	</tr>
			 	<tr id="headColorAddRow">
			 		<td style="width:15%;">头部颜色</td>
			 		<td style="width:85%;" colspan="3"><input name="headerColor" class="mini-textbox" style="width:23%" required="true"/></td>
			 	</tr>
			 	<tr class='dynamicTr'>
			 		<td style="width:15%;">字段名称</td>
			 		<td style="width:20%;"><input name="titles" class="mini-textbox" style="width:100%" required="true"/></td>
			 		<td style="width:13%;">字体颜色</td>
			 		<td>
			 			<input name="colors" class="mini-textbox" style="width:40%" required="true" />
			 			<a href="javascript:void(0)" onclick="Current.delRow(this,'dynamicTr')">删除</a>
			 			<a style="margin-left:5px" href="javascript:void(0)" onclick="Current.addWechatRow(this)">增加</a>
			 		</td>
			 	</tr>
			 	<tr>
			 		<td style="width:15%;">尾部颜色</td>
			 		<td style="width:85%;" colspan="3"><input name="footerColor" class="mini-textbox" style="width:23%" required="true"/></td>
			 	</tr>
			 </table>
		</div>
	</div>
	
	<!-- 信息模板管理窗体 -->
		<div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
				<table style="width:100%;">
					<tr>
					     <td width="80%" nowrap="nowrap"> 
					        <btn:operate privilege="ADD">
								<a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="Current.addtemplatewin()">新增</a>
							</btn:operate>
					     	<btn:operate privilege="UPD">
								<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.edittemplatewin()">修改</a>
							</btn:operate> 		
							
							<span class="condition-name" style="font-size:9pt">信息类型</span>&nbsp;
				 <input id="selectTemplateId" class="mini-combobox"  style="width:150px;" emptyText="请选择"  valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" />&nbsp;&nbsp;
				 
				 <a class="mini-button" onclick="Current.search()">查询</a>
							
							 <btn:operate privilege="EXPORT">
					        	<a class="mini-button" iconCls="icon-download" plain="true" onclick="Current.excel()">导出</a>
					        </btn:operate>
						</td>
					</tr>
				</table>
		</div>
		
		<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
			<div size="90%" showCollapseButton="true" style="border:0;">
				<div id="templateGrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" multiSelect="true"
						 url="operatemgr/msginfo/templatelist" idField="id" allowResize="false" pageSize="50" style="width:100%;height:100%;" 
						 onrowdblclick="Current.edittemplatewin()">
						
						<div property="columns">
							<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
							<div field="typeId" headerAlign="center" align="center">模板编号</div>
							<div field="msgType" type="comboboxcolumn" headerAlign="center" align="center">消息分类
							     <input property="editor" class="mini-combobox" data="Dic.msgType" />
							</div>
							<div field="typeName" headerAlign="center" align="center">信息类型</div>
							<div field="typeNode" type="comboboxcolumn" headerAlign="center" align="center">执行节点
							     <input property="editor" class="mini-combobox" data="Dic.typeNode" />
							</div>
							<div field="status" type= "comboboxcolumn" headerAlign="center" align="center">状态
							     <input property="editor" class="mini-combobox" data="Dic.templateStatus" />
							</div>
							<div field="mobStatus" type="comboboxcolumn" headerAlign="center" align="center">手机短信
							     <input property="editor" class="mini-combobox" data="Dic.templateStatus" />
							</div>
							<div field="siteStatus" type="comboboxcolumn" headerAlign="center" align="center">站内信
							     <input property="editor" class="mini-combobox" data="Dic.templateStatus" />
							</div>
							<div field="appStatus" type="comboboxcolumn" headerAlign="center" align="center">APP通知
							     <input property="editor" class="mini-combobox" data="Dic.templateStatus" />
							</div>
							<!-- <div field="wechatStatus" type="comboboxcolumn" headerAlign="center" align="center">微信公众号
							     <input property="editor" class="mini-combobox" data="Dic.templateStatus" />
							</div> -->
						</div>
				</div>
		</div>
	</div>

	<!-- 更新信息模板窗体 -->
	<div id="editTemplateWindow" class="mini-window" title="信息模板管理"
		style="width:1100px; height:800px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowDrag="true" >
		
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		    <btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="Current.doTemplateSubmit">保存</a>
			</btn:operate>
		</div>
		<a class="mini-button" iconCls="icon-edit" onclick="Current.ManagerCoupon">添加优惠券</a>
		
		<div id="edittemplateform">
		     <input id="id" name="id" class="mini-hidden"/>
		     <input name="url"  class="mini-hidden"/>
			 <input name="action"  class="mini-hidden"/>
			 <input id="appAddFields" name="appAddFields"  class="mini-hidden"/>
			 <input id="mobTitle" name="mobTitle"  class="mini-hidden"/>
			 <input id="mobContent" name="mobContent"  class="mini-hidden"/>
			 <input id="siteTitle" name="siteTitle"  class="mini-hidden"/>
			 <input id="siteContent" name="siteContent"  class="mini-hidden"/>
			 <input id="appTitle" name="appTitle"  class="mini-hidden"/>
			 <input id="appContent" name="appContent"  class="mini-hidden"/>
			 <input id="wechatTitle" name="wechatTitle"  class="mini-hidden"/>
			 <input id="headerCon" name="headerCon"  class="mini-hidden"/>
			 <input id="footerCon" name="footerCon"  class="mini-hidden"/>
			 
			<fieldset style="border:solid 1px #aaa; padding:3px;">
				
	             <legend><span>信息模板内容</span></legend>
			       <div style="padding:5px;">
	                    <table style="width:100%">
	                    	<tr>
						 		<td style="width:15%">消息分类</td>
						 		<td style="width:25%"><input id ="templateMsgType" name="msgType" class="mini-combobox" onvaluechanged="Current.userAwakenConditionSet()" emptyText="请选择" valueFromSelect ="true" style="width:100%;" required="true"/></td>
						 		<td style="width:15%">模板编号</td>
						 		<td style="width:15%"><input id="typeId" name="typeId"  class="mini-textbox" onvalidation="Current.onNoReplaceTypeId" required="true" /></td>
						 		<td style="width:15%">选择彩种</td>
						 		<td style="width:15%"><input id ="sendLottery" name="sendLotteryCode" class="mini-combobox" multiSelect="true" valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true"/></td>
						 	</tr>
						 	<tr>
						 		<td style="width:15%">信息类型</td>
						 		<td style="width:25%"><input id ="templateTypeName" name="typeName"  class="mini-textbox" style="width:100%;"   required="true"/></td>
						 		<td style="width:15%">执行节点</td>
						 		<td style="width:15%"><input id ="templateTypeNode" name="typeNode"  class="mini-combobox" emptyText="请选择" valueFromSelect ="true" style="width:100%;" required="true"/></td>
						 		<td style="width:15%">信息状态</td>
						 		<td style="width:15%"><input id ="templateStatus" name="status"  class="mini-combobox" emptyText="请选择" valueFromSelect ="true" style="width:100%;" required="true"/></td>
						 	</tr>
						 	<tr>
						 		<td>发送目标</td>
						 		<td>
						 			<div id ="templateMobType" name="mobStatus"  class="mini-checkbox" text="手机短信" trueValue="1" falseValue="0" onvaluechanged="Current.onValueChanged('templateMobType')"></div>
						 			<div id ="templateSiteType" name="siteStatus"  class="mini-checkbox" text="站内信" trueValue="1" falseValue="0" onvaluechanged="Current.onValueChanged('templateSiteType')"></div>
						 			<div id ="templateAppType" name="appStatus"  class="mini-checkbox" text="APP通知" trueValue="1" falseValue="0" onvaluechanged="Current.onValueChanged('templateAppType')"></div>
						 			<div id ="templateWechatType" name="wechatStatus"  class="mini-checkbox" text="微信公众号" style="display:none" trueValue="1" falseValue="0" onvaluechanged="Current.onValueChanged('templateWechatType')"></div>
						 		</td>
						 		<td style="width:15%">短信发送渠道</td>
						 		<td style="width:15%"><input id ="smsSendChannel" name="smsSendChannel" class="mini-combobox" valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" required="true"/></td>
						 		<td style="width:15%">选择渠道</td>
						 		<td style="width:15%"><input id="noSendChannel" name="noSendChannel" class="mini-treeselect" url="userdata/channelTree" multiSelect="true" 
									 checkRecursive="true" valueFromSelect="false" style="width: 200px;"
								        textField="text" valueField="id" parentField="pid" allowInput="true" showRadioButton="true" showFolderCheckBox="true"/></td>
						 	</tr>
						 	<tr>
						 		<td style="width:15%">跳转界面</td>
						 		<td style="width:25%"><input id ="urlLottery" name="toLotteryCode" class="mini-combobox" emptyText="请选择" valueFromSelect ="true" style="width:100%;" oncloseclick="Cms.onCloseClick" showClose="true"/></td>
						 		<td style="width:15%">跳转链接地址</td>
						 		<td colspan="3"><input id="activityUrl" name="activityUrl"  class="mini-textbox" style="width:100%;"/></td>
						 	</tr>
						 </table>
	               </div>
	         </fieldset>
			 <span>&nbsp;</span>
			 
			 <fieldset id="userAwakenConditionSet" style="border:solid 1px #aaa; padding:3px;display:none">
	             <legend><span>用户唤醒条件设置</span></legend>
			       <div style="padding:5px;">
	                    <table style="width:100%">
	                    	<tr>
						 		<td style="width:15%">条件列表</td>
						 		<td style="width:25%"><input id ="condition" name="conditionKey" class="mini-combobox" onvaluechanged="Current.conditionLimit()" emptyText="请选择" valueFromSelect ="true" style="width:100%;"/></td>
						 		<td style="width:15%">限制彩种</td>
						 		<td style="width:15%"><input id ="limitLottery" name="lotteryCodeLimit" class="mini-combobox" emptyText="请选择" valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true"/></td>
						 		<td style="width:15%">金额阀值</td>
						 		<td style="width:15%"><input id="setMoney" name="setMoney"  class="mini-textbox"/>&nbsp;元</td>
						 	</tr>
						 	<tr>
						 		<td style="width:15%">余额阀值</td>
						 		<td style="width:25%"><input id="setBalance" name="setBalance"  class="mini-textbox"/>&nbsp;元</td>
						 		<td style="width:15%">距上次天数范围</td>
						 		<td colspan="3">第&nbsp;<input id="startDays" name="startDays"  class="mini-textbox"/>&nbsp;天 ~&nbsp;第&nbsp;<input id="endDays" name="endDays"  class="mini-textbox"/>&nbsp;天</td>
						 		
						 	</tr>
						 </table>
	               </div>
	         </fieldset>
			 <span>&nbsp;</span>
			 
		     <fieldset id="template_contents" style="border:solid 1px #aaa; padding:3px;float: left;width: 680px;">
	            <div style="padding:5px;" class="templateMobType">
	             	<fieldset style="border:solid 1px #aaa; padding:3px;">
	             		<legend><span>手机短信</span></legend>
			            <div style="padding:5px;">
			             	 <table border="0" cellpadding="1" cellspacing="2" width="100%">
				                <tr>
				                    <td style="width:15px;">信息标题</td>
				                    <td style="width:150px">
				                        <input name="mobTitle" id="mt" type="text" style="width:100%"/>
				                    </td>
				                </tr>
				                <tr>
				                    <td style="width:15px;">信息内容</td>
				                    <td style="width:150px">
				                        <textarea name="mobContent" id="mc" style="width:100%;height:60px;resize: none;"></textarea>
				                    </td>
				                </tr>
                			</table>
		            	</div>
        			</fieldset>
            	</div>
            	<div style="padding:5px;" class="templateSiteType">
	             	<fieldset style="border:solid 1px #aaa; padding:3px;">
	             		<legend><span>站内信</span></legend>
			            <div style="padding:5px;">
			             	 <table border="0" cellpadding="1" cellspacing="2" width="100%">
				                <tr>
				                    <td style="width:15px;">信息标题</td>
				                    <td style="width:150px">
				                        <input name="siteTitle" id="st" type="text" style="width:100%"/>
				                    </td>
				                </tr>
				                <tr>
				                    <td style="width:15px;">信息内容</td>
				                    <td style="width:150px">
				                        <textarea name="siteContent" id="sc" style="width:100%;height:60px;resize: none;"></textarea>
				                    </td>
				                </tr>
                			</table>
		            	</div>
        			</fieldset>
            	</div>
            	<div style="padding:5px;" class="templateAppType">
	             	<fieldset style="border:solid 1px #aaa; padding:3px;">
	             		<legend><span>APP通知</span></legend>
			            <div style="padding:5px;">
			             	 <table border="0" cellpadding="1" cellspacing="2" width="100%">
				                <tr id="temAppTitleTr">
				                    <td style="width:15px;">信息标题</td>
				                    <td style="width:150px">
				                        <input name="appTitle" id="at" type="text" style="width:50%"/>&nbsp;&nbsp;
				                        <a style="margin-left:5px" href="javascript:void(0)" onclick="Current.addAppTemRow(this)">附加字段</a>
				                    </td>
				                </tr>
				                
				                <tr>
				                    <td style="width:15px;">信息内容</td>
				                    <td style="width:150px">
				                        <textarea name="appContent" id="ac" style="width:100%;height:60px;resize: none;"></textarea>
				                    </td>
				                </tr>
				                
                			</table>
		            	</div>
        			</fieldset>
            	</div>
            	<div style="padding:5px;display:none" class="templateWechatType">
	             	<fieldset style="border:solid 1px #aaa; padding:3px;">
	             		<legend><span>微信公众号</span></legend>
			            <div style="padding:5px;">
			             	 <table id="wechat_template_table" border="0" cellpadding="1" cellspacing="2" width="100%">
			             	 	<tr>
				                    <td style="width:15px;">选择模板</td>
				                    <td style="width:150px">
				                        <input id="wechatTemplate" name="wechatId" class="mini-combobox" onvaluechanged="Current.updWechatTitle()" style="width:150px;" emptyText="请选择"  valueFromSelect="true" required="true"/>
				                        <span style="float:right;">
					                        <a href="javascript:void(0)" onclick="Current.editwechatset()">修改</a>&nbsp;&nbsp;
					                        <a href="javascript:void(0)" onclick="Current.addwechatset()">新增</a>
				                        </span>
				                    </td>
				                </tr>
				                <tr>
				                    <td style="width:15px;">信息标题</td>
				                    <td style="width:150px">
				                        <input name="wechatTitle" id="wt" type="text" style="width:100%"/>
				                    </td>
				                </tr>
				                <tr id="afterAdd">
				                    <td style="width:15px;">头部内容</td>
				                    <td style="width:150px">
				                        <textarea name="headerCon" id="hc" style="width:100%;height:60px;resize: none;"></textarea>
				                    </td>
				                </tr>
				                <tr>
				                    <td style="width:15px;">尾部内容</td>
				                    <td style="width:150px">
				                        <textarea name="footerCon" id="fc" style="width:100%;height:60px;resize: none;"></textarea>
				                    </td>
				                </tr>
                			</table>
		            	</div>
        			</fieldset>
            	</div>
        	  </fieldset>
        	  
        	  <div style="border:solid 1px #aaa; padding:3px;float: right;width: 350px; ">
    				<fieldset style="border:solid 1px #aaa; padding:3px;">
	             		<legend><span>用户信息</span></legend>
			            <div style="padding:5px;">
			            	<table cellpadding="1" cellspacing="2" width="100%">
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="昵称" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="账户" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="真实姓名" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="手机号" />
			            			</td>
			            		</tr>
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="密码" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="身份证号" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="会员邮箱" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="会员id" />
			            			</td>
			            		</tr>
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="兑换码" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="触发禁用时间" />
			            			</td>
			            			<td colspan="2">
			            				<input type="button" class="readbtns" value="禁用解除时间" />
			            			</td>
			            		</tr>
			            	</table>
			            </div>
			    </fieldset>
    			<fieldset style="border:solid 1px #aaa; padding:3px;">
	             		<legend><span>方案详情</span></legend>
			            <div style="padding:5px;">
			            	<table cellpadding="1" cellspacing="2" width="100%">
			            		<tr>
			            		
			            			<td>
			            				<input type="button" class="readbtns" value="彩种" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="彩期" />
			            			</td>

			            			<td>
			            				<input type="button" class="readbtns" value="购买类型" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="方案编号" />
			            			</td>
			            		</tr>
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="方案id" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="账户" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="中奖金额" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="中奖等级" />
			            			</td>
			            		</tr>
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="红包类型" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="红包面额" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="支付状态" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="方案进度" />
			            			</td>
			            		</tr>
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="昵称" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="购买时间" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="撤单原因" />
			            			</td>
			            			<td  colspan="2">
			            				<input type="button" class="readbtns" value="彩种路径名称" />
			            			</td>
			            		</tr>
			            	</table>
			            </div>
			   	</fieldset>
			   	<fieldset style="border:solid 1px #aaa; padding:3px;">
	             		<legend><span>彩期</span></legend>
			            <div style="padding:5px;">
			            	<table cellpadding="1" cellspacing="2" width="100%">
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="彩种" />
			            			</td>
			            			
			            			<td>
			            				<input type="button" class="readbtns" value="彩种销售状态" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="子玩法销售状态" />
			            			</td>
			            				
			            		</tr>
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="彩期" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="彩期销售状态" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="开奖号码" />
			            			</td>
			            			
			            		</tr>
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="滚存" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="子玩法" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="本站截止时间" />
			            			</td>
			            		</tr>
			            	</table>
			            </div>
			    </fieldset>
    			<fieldset style="border:solid 1px #aaa; padding:3px;">
	             		<legend><span>财务管理</span></legend>
			            <div style="padding:5px;">
			            	<table cellpadding="1" cellspacing="2" width="100%">
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="昵称" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="充值金额" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="当前余额" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="银行失败原因" />
			            			</td>
			            			
			            		</tr>
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="提款卡号" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="手机号码" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="提款金额" />
			            			</td>
			            			
			            			<td>
			            				<input type="button" class="readbtns" value="兑换码" />
			            			</td>
			            		</tr>
			            		
			            	</table>
			            </div>
			    </fieldset>
    			<fieldset style="border:solid 1px #aaa; padding:3px;">
	             		<legend><span>运营管理</span></legend>
			            <div style="padding:5px;">
			            	<table cellpadding="1" cellspacing="2" width="100%">
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="红包名称" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="红包类型" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="红包金额" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="有效期" />
			            			</td>
			            		</tr>
			            	</table>
			            </div>
				</fieldset>
    			<fieldset style="border:solid 1px #aaa; padding:3px;">
	             		<legend><span>追号计划</span></legend>
			            <div style="padding:5px;">
			            	<table cellpadding="1" cellspacing="2" width="100%">
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="彩种" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="彩期" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="追号期数" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="追号中奖金额" />
			            			</td>
			            		</tr>
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="已追期数" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="发起时间" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="追号状态" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="追号编号" />
			            			</td>
			            		
			            		</tr>
			            	</table>
			            </div>
			    </fieldset>
    			<fieldset style="border:solid 1px #aaa; padding:3px;">
	             		<legend><span>对阵管理(竞彩,单场)</span></legend>
			            <div style="padding:5px;">
			            	<table cellpadding="1" cellspacing="2" width="100%">
			            		<tr>
			            			<td colspan="4">
			            				<input type="button" class="readbtns" value="对阵" />
			            			
			            				<input type="button" class="readbtns" value="球队名称" />
			            			
			            				<input type="button" class="readbtns" value="销售状态" />
			            				
			            				<input type="button" class="readbtns" value="竞猜时间" />
			            				
			            				<input type="button" class="readbtns" value="瓜分奖金" />
			            				
			            			</td>
			            		</tr>
			            	</table>
			            </div>
				</fieldset>
				<fieldset style="border:solid 1px #aaa; padding:3px;">
	             		<legend><span>抄单</span></legend>
			            <div style="padding:5px;">
			            	<table cellpadding="1" cellspacing="2" width="100%">
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="方案编号" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="跟单总金额" />
			            			</td>
			            			
			            			<td>
			            				<input type="button" class="readbtns" value="推荐理由" />
			            			</td>
			            			
			            			<td>
			            				<input type="button" class="readbtns" value="佣金" />
			            			</td>
			            			
			            			
			            		</tr>
			            		<tr>
			            			<td>
			            				<input type="button" class="readbtns" value="跟单总人数" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="提成比率" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="回报率" />
			            			</td>
			            			<td>
			            				<input type="button" class="readbtns" value="发起时间" />
			            			</td>
			            		</tr>
			            	</table>
			            </div>
				</fieldset>
				<fieldset style="border:solid 1px #aaa; padding:3px;">
	             		<legend><span>专家信息</span></legend>
			            <div style="padding:5px;">
			            	<table cellpadding="1" cellspacing="2" width="100%">
			            		<tr>
			            			<td colspan="4">
			            				<input type="button" class="readbtns" value="专家昵称" />
			            				<input type="button" class="readbtns" value="专家账户" />
			            				<input type="button" class="readbtns" value="专家id" />
			            				<input type="button" class="readbtns" value="专家真实姓名" />
			            			</td>
			            		</tr>
			            		<tr>
			            			<td colspan="4">
			            				<input type="button" class="readbtns" value="专家密码" />
			            				<input type="button" class="readbtns" value="专家身份号" />
			            				<input type="button" class="readbtns" value="专家邮箱" />
			            				<input type="button" class="readbtns" value="专家手机号" />
			            			</td>
			            		</tr>
			            		
			            	</table>
			            </div>
			    </fieldset>
        	  </div>
        	  
        	  <div style="padding:5px;">
	        	   <table style="width:100%;">
	        	   		<tr>
	        	   			<td>创建时间</td>
	        	   			<td><input name="createTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
	        	   			<td>修改时间</td>
	        	   			<td><input name="updateTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
	        	   			<td rowspan="2">备注</td>
	        	   			<td rowspan="2"><input name="typeDesc" class="mini-textarea" style="width:100%;height:100%;"/></td>
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
<script src="<%=basePath%>resources/js/operatemgr/msg_template.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
