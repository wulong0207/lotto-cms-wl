<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>会员查询</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td width="15%" nowrap="nowrap">
			        <btn:operate privilege="SEARCH">
				     	<a class="mini-button" iconCls="icon-upload" plain="true" onclick="Current.uploadSearch()">批量查询</a>
			     	</btn:operate>
			        <btn:operate privilege="EXPORT">
			        <a class="mini-button" iconCls="icon-download" plain="true" onclick="Current.excel()">导出</a>
			        </btn:operate>
			     	<btn:operate privilege="UPD|SEARCH">
					<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.editwin()">修改</a>
					</btn:operate> 			
				</td>
				<td width="5%" style="white-space:nowrap;">
				   <input id="attrType"  class="mini-combobox"  style="width:80px;" allowInput="true" valueFromSelect = "true"/>
				   <input id="attrContent" class="mini-textbox" style="width:200px;"/>
				        状态：<input id="status"   class="mini-combobox"  style="width:80px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				      市场渠道：<input id="channel" class="mini-combobox" style="width:140px;"
				      emptyText="请选择或输入"      allowInput="true"  oncloseclick="Cms.onCloseClick" showClose="true"/>
				   <input id="timeType"  class="mini-combobox"  style="width:100px;"/>
				   <input id="startTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>  
					<a class="mini-button" onclick="Current.search()">查询</a>
				</td>
				<td width="30%"></td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="customermgr/user/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
				 onrowdblclick="Current.onrowdblclick" onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="accountName" headerAlign="center" align="center">会员账号</div>
					<div field="nickName" headerAlign="center" align="center">昵称</div>
					<div field="cusMobile" headerAlign="center" align="center">手机号码</div>
					<div field="actualName" headerAlign="center" align="center">姓名</div>
					<div field="idNum" headerAlign="center" align="center">身份证号码</div>
					<div field="accountStatus" type= "comboboxcolumn" headerAlign="center" align="center">状态
					     <input property="editor" class="mini-combobox" data="Dic.userstatus" />
					</div>
					<div field="lastLoginTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">最后登录时间</div>	
					<div field="channelId" type= "comboboxcolumn" headerAlign="center" align="center">渠道来源
					    <input property="editor" class="mini-combobox" data=" Dic.channel" />
					</div>
					<div field="platform" type= "comboboxcolumn" headerAlign="center" align="center">平台类型
					    <input property="editor" class="mini-combobox" data="Dic.platform" />
					</div>										
					<div field="registTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">注册时间</div>
					<div field="updateTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="editWindow" class="mini-window" title="修改会员信息" 
		style="width:1100px; height:550px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		    <btn:operate privilege="UPD">
			<a class="mini-button" onclick="Current.doSubmit">保存</a>
			</btn:operate>
		</div>
		<div  id="editform">
		     <input name="url"  class="mini-hidden"/>
			 <input name="action"  class="mini-hidden"/>
			 <div style="padding:5px;">
			 <table width="100%;">
			 <tr>
			  <td style="text-align: right;">
			  <a class="mini-button" onclick="Current.updatePassword()">修改密码</a>
			  <a class="mini-button" onclick="Current.resetPassword()">重置密码</a></td>
			 </tr>
			 </table>
			 </div>
			 
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
	            <legend >会员基本信息：</legend>
	            <div style="padding:5px;">
	             <table width="100%;">
	            <tr>
	            <td width="13%">用户ID</td>
	            <td width="20%"><input name="id" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	            <td width="13%">帐号名<span id="accountModify" style="float: right;"></span></td>
	            <td width="20%"><input name="accountName" class="mini-textbox" style="width:100%;" required="true" vtype ="maxLength:50"/></td>
	            <td width="13%" rowspan="4" style="text-align: center;">头像<span id="headCheck" style="float: right;"></span></td>
	            <td width="20%" rowspan="4"><img id ="img" alt="头像" width="100px;" height="100px;">
	            	<input id="headUrl_edit" name="headUrl"  class="mini-hidden"/><br/>
					<input type="button" value="从图库选择" onclick="Current.openImage()"/>
	            </td>
	            </tr>
	            <tr>
	            <td>手机号码<span id="mobileCheck" style="float: right;"></span></td>
	            <td><input name="cusMobile" class="mini-textbox" vtype ="int;rangeLength:11,11" style="width:100%;" /></td>
	            <td>邮箱<span id="emailCheck" style="float: right;"></span></td>
	            <td><input name="cusMail" class="mini-textbox" style="width:100%;" vtype="maxLength:50"/></td>
	            </tr>
	            <tr>
	            <td>短信验证码</span></td>
	            <td>
	            <input name="cusMobileCount" class="mini-textbox"  style="width:50%;" enabled="false" />
	            <a class="mini-button" onclick="Current.cleanMessage('0')">清空</a>
	            </td>
	            <td>邮箱验证码</span></td>
	            <td>
	            <input name="cusMailCount" class="mini-textbox" style="width:50%;" enabled="false"/>
	            <a class="mini-button" onclick="Current.cleanMessage('1')">清空</a>
	            </td>
	            </tr>
	             <tr>
	            <td>昵称</td>
	            <td><input name="nickName" class="mini-textbox" style="width:100%;" vtype="maxLength:50"/></td>
	            <td>性别</td>
	            <td><input id ="sex_edit" name="sex"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"  style="width:100%;"   required="true"/></td>
	            </tr>
	             <tr>
	            <td>真实姓名</td>
	            <td><input name="actualName" class="mini-textbox" style="width:100%;" vtype="maxLength:20"/></td>
	            <td>省份证号码</td>
	            <td><input name="idNum" class="mini-textbox" style="width:100%;" vtype="rangeLength:18,18"/></td>
	            </tr>
	            <tr>
	            <td>开启手机号登录</td>
	            <td><input id= "mobileLogin_edit" name="mobileLogin" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"  style="width:100%;"   required="true"/></td>
	            <td>开启邮箱登录</td>
	            <td><input id= "emailLogin_edit" name="emailLogin" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"  style="width:100%;"   required="true"/></td>
	            <td>头像状态</td>
	            <td><input id ="headStatus_edit" name="headStatus"  class="mini-combobox"  valueFromSelect = "true"   style="width:100%;"/></td>
	            </tr>
	             <tr>
	            <td>居住地</td>
	            <td colspan="3"><input name="address" class="mini-textbox" style="width:100%;" vtype="maxLength:100"/></td>
	            <td>渠道来源名称</td>
	            <td><input id="channelId_edit" name="channelId" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"  style="width:100%;"  enabled="false"/></td>
	            </tr>
	            <tr>
		            <td>平台类型</td>
		            <td><input id="platform_edit" name="platform" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"  style="width:100%;"  enabled="false"/></td>
		            <td>会员类型</td>
		            <td><input id="userType" name="userType" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"  style="width:100%;"  enabled="false"/></td>
		            <td>代理编号</td>
		            <td><input name="agentCode" class="mini-textbox" style="width:100%;" vtype="maxLength:100" enabled="false"/></td>
	            </tr>	            
	             <tr>
	            <td>注册时间</td>
	            <td><input name="registTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
	            <td>最后登录时间</td>
	            <td><input name="lastLoginTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
	            <td>会员状态</td>
	            <td><input name="forbitEndTime" class="mini-datepicker"  allowInput="false" style="width:75%;" format="yyyy-MM-dd HH:mm:ss"/><input id ="accountStatus" name="accountStatus"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"  style="width:25%;"   required="true"/></td>
	            </tr>
	             <tr>
	            <td>QQopenID</td>
	            <td><input name="qqOpenId" class="mini-textbox" enabled="false" style="width:100%;" vtype="maxLength:50" /></td>
	            <td>新浪微博openID</td>
	            <td><input name="sinaBlogOpenId" class="mini-textbox" enabled="false" style="width:100%;" vtype="maxLength:50"/></td>
	            <td>百度openID</td>
	            <td><input name="baiduOpenId" class="mini-textbox" enabled="false" style="width:100%;" vtype="maxLength:50"/></td>
	            </tr>
	             <tr>
	            <td>微信openID</td>
	            <td><input name="wechatOpenId" class="mini-textbox" enabled="false" style="width:100%;" vtype="maxLength:50"/></td>
	            <td>支付宝openID</td>
	            <td><input name="alipayOpenId" class="mini-textbox" enabled="false" style="width:100%;" vtype="maxLength:50"/></td>
	            <td>京东openID</td>
	            <td><input name="jdOpenId" class="mini-textbox" enabled="false" style="width:100%;" vtype="maxLength:50"/></td>
	            </tr>
	            </table>            
	            </div>
	        </fieldset>

			<fieldset style="border:solid 1px #aaa;">
				<legend >会员银行卡管理：</legend>
			<div id="bankCardDatagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:50%;"
				 url="paymentmgr/bankcard/page" idField="id" allowResize="true" ajaxType="get" multiSelect="true"
				 showColumnsMenu="true" showFilterRow="false" onrowdblclick="Cms.editRow('bankCardDatagrid')" showPager="false" showToolbar="true">
				<div property="toolbar"
					 style="text-align: right; padding: 2px; padding-right: 30px;">
					<btn:operate privilege="UPD">
						<a class="mini-button" iconCls="icon-save" onclick="Current.batchUpdateBankCard">保存</a>
					</btn:operate>
				</div>
				<div property="columns">
					<div type="checkcolumn" width="5%"></div>
					<div type="indexcolumn" width="5%" align="center" headerAlign="center">序号</div>
					<div field="bankId" type="comboboxcolumn" headerAlign="center" align="center">银行名称
						<input property="editor" class="mini-combobox" url="paymentmgr/index/banks/dic" onvaluechanged="Current.changeBankName"/>
					</div>
					<div field="bankName" headerAlign="center" align="center">银行名称</div>
					<div field="bankType" type="comboboxcolumn" headerAlign="center" align="center">卡类型
						<input property="editor" class="mini-combobox" data="[{id:1,text:'储蓄卡'},{id:2,text:'信用卡'}]"/>
					</div>
					<div field="overdue" property="editor" headerAlign="center" align="center">过期时间
						<input property="editor" class="mini-textbox" vtype="int;maxLength:4"/>
					</div>
					<div field="cardCode" property="editor" headerAlign="center" align="center" width="300">卡号
						<input property="editor" id="asdfasdfasdfasdfasdf" class="mini-textbox" vtype="int" style="width:300px;"/>
					</div>
					<div field="status" type="comboboxcolumn" headerAlign="center" align="center">状态
						<input property="editor" class="mini-combobox" data="[{id:0,text:'删除'},{id:1,text:'有效'}]"/>
					</div>
					<div headerAlign="center" align="center" renderer="Current.renderBtn">操作</div>
				</div>
			</div>
			</fieldset>
        
		   <fieldset style="border:solid 1px #aaa;padding:3px;">
            <legend >操作信息：</legend>
            <div style="padding:5px;">
             <table width="100%;">
			<tr>
			  <td width="13%">修改时间</td>
			  <td width="20%"><input name="modifyTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td width="13%" rowspan="2">备注</td>
			  <td width="20%" rowspan="2"><input name="remark" class="mini-textarea" vtype ="maxLength:200" style="width:100%;"/></td>
			  <td width="13%"></td>
			  <td width="20%"></td>
			</tr>
			<tr>
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
		<div id="upoladWindow" class="mini-window" title="批量查询" 
		style="width:400px; height:400px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
				
		  <div id="searchForm">
		        <table width="100%;">
               	 <!--  查询类型：<input id="uploadType"  class="mini-combobox"  style="width:80px;" allowInput="true" valueFromSelect = "true"/> -->
               	  <div id="beachType" name="type" class="mini-radiobuttonlist" value="account_name"   repeatLayout="table"
               	  data="[{id:'account_name',text:'会员帐户'},{id:'nick_name',text:'会员昵称'},{id:'cus_mobile',text:'手机号码'}]"> 
               	  </div>
               	  <br>
               	  <input id="beachContent" name="content" class="mini-textarea" style="width:100%;height: 200px;"/>
			      <a class="mini-button"   onclick="Current.batchSearch">查询</a>
			     <br>批量查询说明：<br>1.一行一个会员信息，不能有其它字符 ；
			     </table>
		  </div>
		</div>
		<div id="upoladPasswordWindow" class="mini-window" title="修改密码" 
		style="width:280px; height:120px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		  <div id="updatePasswordForm">
		        <table width="100%;">
		          <tr>
		            <td>会员账号</td>
		            <td><input id="accountName_upad" name="accountName" class="mini-textbox" enabled="false"/></td>
		          </tr>
		          <tr>
		            <td>设置密码</td>
		            <td><input id="password_upad" name="password" class="mini-textbox" vtype ="int;rangeLength:6,6"/></td>
		          </tr>
		          <tr>
		            <td align="center"><a class="mini-button"   onclick="Current.submitPassword()">确定</a></td>
		            <td align="center"><a class="mini-button"   onclick="Current.close()">取消</a></td>
		          </tr>
			    </table>
		  </div>
		</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/customermgr/user.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
