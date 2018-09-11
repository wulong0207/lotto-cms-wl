<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/operatemgr/msg_info.css" />
    <title>信息接收设置管理</title>
  </head>
  
<body>

	<!-- 用户接收消息配置管理窗体 -->
	<div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
				<table style="width:100%;">
					<tr>
					     <td width="25%" nowrap="nowrap"> 
					     
					     	<span class="condition-name" style="font-size:9pt"> 手机号</span>&nbsp; 
				 			<input id="cusMobile" class="mini-textbox" style="width:150px;"/>
				 			
				 			<span class="condition-name" style="font-size:9pt"> 会员账号</span>&nbsp; 
				 			<input id="accountName" class="mini-textbox" style="width:150px;"/>
				 			
				 			<span class="condition-name" style="font-size:9pt"> 会员昵称</span>&nbsp; 
				 			<input id="nickName" class="mini-textbox" style="width:150px;"/>
					     
					     	<btn:operate privilege="SEARCH">
								<a id="editTemplateWin" class="mini-button" iconCls="icon-search" plain="true" onclick="Current.search()">查询</a>
							</btn:operate> 		
							
					     	<btn:operate privilege="UPD">
								<a id="editTemplateWin" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.edit()">修改</a>
							</btn:operate> 		
							 <btn:operate privilege="EXPORT">
					        	<a class="mini-button" iconCls="icon-download" plain="true" onclick="Current.excel()">导出</a>
					        </btn:operate>
						</td>
					</tr>
				</table>
			</div>
		
		<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
			<div size="90%" showCollapseButton="true" style="border:0;">
				<div id="userMsgConfigGrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" multiSelect="true"
						 url="operatemgr/msginfo/userList" idField="id" allowResize="false" pageSize="50" style="width:100%;height:100%;" 
						 onrowdblclick="Current.edit()">
						
						<div property="columns">
							<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
							<div field="accountName" headerAlign="center" align="center">用户名</div>
							<div field="cusMobile" headerAlign="center" align="center">手机号码</div>
							<div field="msgMob" type="comboboxcolumn" headerAlign="center" align="center">手机短信
							     <input property="editor" class="mini-combobox" data="Dic.reciveStatus" />
							</div>
							<div field="msgSite" type="comboboxcolumn" headerAlign="center" align="center">站内信
							     <input property="editor" class="mini-combobox" data="Dic.reciveStatus" />
							</div>
							<div field="msgApp" type="comboboxcolumn" headerAlign="center" align="center">APP通知
							     <input property="editor" class="mini-combobox" data="Dic.reciveStatus" />
							</div>
							<!-- <div field="msgWechat" type="comboboxcolumn" headerAlign="center" align="center">微信公众号
							     <input property="editor" class="mini-combobox" data="Dic.reciveStatus" />
							</div> -->
							<div field="mobNotDisturb" headerAlign="center" align="center">短信免打扰时间段</div>
							<div field="appNotDisturb" headerAlign="center" align="center">APP通知免打扰时间段</div>
						</div>
					</div>
	        </div>
	</div>
	
	<!-- 信息设置修改管理窗体 -->
	<div id="userSettingWindow" class="mini-window" title="信息接收设置"
		style="width:900px; height:600px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="true"
		showFooter="true" showModal="true" allowDrag="true" >
			<div property="toolbar" style="padding:5px;">
				<table style="width:100%;">
					<tr>
					     <td width="25%" nowrap="nowrap"> 
					     
					     	<btn:operate privilege="UPD">
								<a id="editTemplateWin" class="mini-button" iconCls="icon-save" plain="true" onclick="Current.saveUserConfig">保存</a>
							</btn:operate> 		
						</td>
					</tr>
				</table>
			</div>
		
			 <input id="userId" class="mini-hidden"/>
			<div>用户端开关： <label style="margin-right:10px">手机短信 <input type="checkbox" id="msgMob"></label>  <label style="margin-right:10px">站内信<input type="checkbox" id="msgSite"></label> <label>app通知<input type="checkbox" id="msgApp"></label></div>
			<div id="userSettingGrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" multiSelect="true" showPager="false" allowCellEdit="true"
					 url="operatemgr/msginfo/userConfig" idField="id" allowResize="false" pageSize="50" style="width:100%;height:80%;" 
					 onrowdblclick="Current.editUserConfig">
					
					<div property="columns">
						<div field="templateName" align="center" headerAlign="center" allowResize="true">信息类型</div>
						<div field="mob" type="checkboxcolumn" width="40"  trueValue="1" falseValue="0">
							手机短信
						</div>
						<div field="site" type="checkboxcolumn" width="40"  trueValue="1" falseValue="0">
							
							站内信
						</div>
						<div field="app" type="checkboxcolumn" width="40"  trueValue="1" falseValue="0">
							
							APP通知
							</div>
						<!-- <div field="wechat" type="checkboxcolumn" width="40"  trueValue="1" falseValue="0">
							<input type="checkbox" id="msgWechat">
							微信公众号
						</div> -->
						
					</div>
				</div>
				
				<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:280px;margin-top:8px" contextMenu="#tabsMenu">
				    <div title="开奖号码APP提醒设置">
				        <div id="openCodeReceiveSetGrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" multiSelect="true" showPager="false" allowCellEdit="true"
					 url="operatemgr/msginfo/lotteryConfig" idField="id" allowResize="false" pageSize="50" style="width:100%;height:100%;" 
					 onrowdblclick="Current.editUserConfig">
							<div property="columns">
								<div field="lotteryCode" type="comboboxcolumn" align="center" headerAlign="center">彩种
								 	<input property="editor" class="mini-combobox" data="Dic.lotterys" />
								</div>
								<div field="app" type="checkboxcolumn" trueValue="1" falseValue="0">
									<input type="checkbox" id="openCodeNotice" onclick="Current.onColsAllSelect('openCodeReceiveSetGrid','openCodeNotice')"/>开奖号码
								</div>
							</div>
						</div>
				    </div>
				    <div title="购彩APP提醒设置">
				        <div id="buyLotRemindSetGrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" multiSelect="true" showPager="false" allowCellEdit="true"
					 url="operatemgr/msginfo/lotteryConfig" idField="id" allowResize="false" pageSize="50" style="width:100%;height:100%;" 
					 onrowdblclick="Current.editUserConfig">
						<div property="columns">
							<div field="lotteryCode" type="comboboxcolumn" align="center" headerAlign="center">彩种
							 	<input property="editor" class="mini-combobox" data="Dic.lotterys" />
							</div>
							<div field="app" type="checkboxcolumn" trueValue="1" falseValue="0">
								<input type="checkbox" id="buyLotNotice" onclick="Current.onColsAllSelect('buyLotRemindSetGrid','buyLotNotice')"/>购彩提醒
							</div>
							</div>
						</div>
				    </div>
				</div>

			<fieldset style="border:solid 1px #aaa; padding:3px;">
	             <legend><span>免打扰时间</span></legend>
			       <div style="padding:5px;">
	                    <table style="width:100%">
	                    	<tr>
	                    		<td>短信免打扰时间</td>
	                    		<td>
	                    			<input id="mobNotDisturb" class="mini-textbox" style="width:250px;" isValid="true" onvalidation="Current.onTimeValidation"/>
	                    		</td>
	                    	</tr>
	                    	<tr>
	                    		<td>APP免打扰时间</td>
	                    		<td>
	                    			<input id="appNotDisturb" class="mini-textbox" style="width:250px;" isValid="true" onvalidation="Current.onTimeValidation"/>
	                    		</td>
	                    	</tr>
	                    </table>
	               </div>
	            </fieldset>
	</div>

</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/msg_user_config.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
