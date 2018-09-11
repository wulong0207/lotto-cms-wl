<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>发单人审核</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="conditionForm">
		<table style="width:100%;">
			<tr>
			     <td width="15%" nowrap="nowrap"> 
			        <btn:operate privilege="ADD">
						<a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="Current.openAddwin()">添加发单人</a>
					</btn:operate> 	
				</td>
			</tr>
			<tr>
			<td width="5%" style="white-space:nowrap;">
				        账号：<input id="userAccount" name="userAccount" class="mini-textbox" style="width:100px;"/>
				  ID编号：<input id="id" name="id" class="mini-textbox" style="width:100px;"/>
				      审核状态 ：<input id="verifyStatus" name="status"  class="mini-combobox"  style="width:120px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				       认证类型：<input id="applySource" name="applySource"  class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				       认证名称 ：<input id="applyType" name="applyType" class="mini-combobox"  style="width:120px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				</td>
			</tr>
			<tr>
			<td width="5%" style="white-space:nowrap;">
			 	       申请时间
				   <input id="applyStartTime" name="applyStartTime" class="mini-datepicker"  allowInput="false"  style="width:180px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="applyEndTime" name="applyEndTime" class="mini-datepicker"  allowInput="false"  style="width:180px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				   &nbsp;&nbsp;审核时间
				   <input id="startTime" name="startTime" class="mini-datepicker"  allowInput="false"  style="width:180px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" name="endTime" class="mini-datepicker"  allowInput="false"  style="width:180px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
					<a class="mini-button" onclick="Current.search()">查询</a>
				</td>
			</tr>
			</table>
		</div>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="90%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="ordermgr/rcmd/rcmdUserCheckList" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" onrowdblclick="Current.onrowdblclick" onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="id" headerAlign="center" align="center">ID编号</div>
					<div field="userAccount" headerAlign="center" align="center">帐号</div>
					<div field="applySource" type= "comboboxcolumn" headerAlign="center" align="center">认证类型
					     <input property="editor" class="mini-combobox" data="Dic.applySource" />
					</div>
					<div field="applyType" type= "comboboxcolumn" headerAlign="center" align="center">认证名称
					     <input property="editor" class="mini-combobox" data="Dic.applyType" />
					</div>
					<div field="lotteryCode" type= "comboboxcolumn" headerAlign="center" align="center">项目
					     <input property="editor" class="mini-combobox" data="Dic.lottery" />
					</div>
					<div field="idNum" headerAlign="center" align="center" renderer="Current.rowPublishStatus">身份证号</div>
					<div field="cusMobile" headerAlign="center" align="center">联系方式</div>
					<div field="applyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">申请时间</div>	
					<div field="checkTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">审核时间</div>
					<div field="checkStatus" type= "comboboxcolumn" headerAlign="center" align="center">审核状态
					     <input property="editor" class="mini-combobox" data="Dic.status" />
					</div>
					<div field="isShowRecord" type= "comboboxcolumn" headerAlign="center" align="center">显示战绩
					     <input property="editor" class="mini-combobox" data="Dic.yesNoData" />
					</div>
					<div field="remark" headerAlign="center" align="center">操作员</div>
					<div field="opt" renderer="Current.onActionRenderer"  width="12%" headerAlign="center" align="center">操作</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="detailWindow" showToolbar="true" class="mini-window" style="width:45%;height:380px" showFooter="true" showModal="true" allowDrag="true" showCloseButton="true">
	    <div id="detailForm" class="form">
	    	<input name="id" id="aid" class="mini-hidden"/>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <table>
	            <tr>
	                <td>用户账号</td>
	                <td><input name="userAccount" class="mini-textbox" enabled="false"/></td>
	            </tr>
	            <tr>
	            	<td>身份证号</td>
	                <td><input name="idNum" class="mini-textbox" enabled="false"/></td>
	            </tr>
	            <tr>
	                <td>验证手机号</td>
	                <td><input name="cusMobile" class="mini-textbox" enabled="false"/></td>
	            </tr>
	            
	             <tr>
		             <td>所属项目</td>
	                <td><input id="lottery" name="lotteryCode" class="mini-combobox" enabled="false"/></td>
	            </tr>
	            
	            <tr>
	                <td>分析师级别</td>
	                <td><input id="analystLevel" name="applyType" class="mini-combobox" enabled="false"/></td>
	            </tr>
	            
	            <tr>
	               <td>个人介绍</td>
	               <td><textarea id="summary" name="summary" class="mini-textarea" enabled="false" style="width:600px;height:150px;"></textarea></td>
	            </tr>
	        </table>
		</fieldset>
	    </div>
	    
	   	<div style="text-align:center;padding:2px;padding-right:15px;">
			<btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-no"  onclick="Current.setStatus(4)">不通过</a>&nbsp;&nbsp;&nbsp;&nbsp;
		        <a class="mini-button" iconCls="icon-ok"  onclick="Current.setStatus(3)">审核通过</a>  
	        </btn:operate>    
	    </div>
	    
	</div>
	
	<div id="addWindow" showToolbar="true" class="mini-window" style="width:80%;height:300px" showFooter="true" showModal="true" allowDrag="true" showCloseButton="true">
	    <div id="addForm" class="form">
	    	<input name="id" class="mini-hidden"/>
	    	<input name="action" class="mini-hidden"/>
	    	<input name="url" class="mini-hidden"/>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <table>
	            <tr>
	                <td>用户姓名</td>
	                <td colspan="3"><input name="userName" class="mini-textbox"/></td>
	            </tr>
	            <tr>
	            	<td>身份证号</td>
	                <td><input name="idNum" class="mini-textbox"/></td>
	                 <td>验证手机号</td>
	                <td><input name="cusMobile" class="mini-textbox"/></td>
	            </tr>
	            <tr>
		             <td>所属项目</td>
	                <td><input id="lottery" name="lotteryCode" class="mini-combobox"/></td>
	                <td>分析师级别</td>
	                <td><input id="analystLevel" name="applyType" class="mini-combobox"/></td>
	            </tr>
	             <tr>
		             <td>是否显示战绩</td>
	                <td><input id="isShowRecord" name="isShowRecord" class="mini-radiobuttonlist" textField="text" valueField="id"/></td>
	                <td>是否为签约专家</td>
	                <td><input id="source" name="applySource" class="mini-radiobuttonlist" textField="text" valueField="id"/></td>
	            </tr>
	            <tr>
	               <td>个人介绍</td>
	               <td  colspan="3"><textarea id="summary" name="summary" class="mini-textarea" style="width:1000px;height:100px;"></textarea></td>
	            </tr>
	        </table>
		</fieldset>
	    </div>
	    
	   	<div style="text-align:center;padding:2px;padding-right:15px;">
			<btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-cancel"  onclick="Current.clear">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;
		        <a class="mini-button" iconCls="icon-add"  onclick="Current.submit">添加</a>  
	        </btn:operate>    
	    </div>
	    
	</div>
	
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/ordermgr/rcmd_user_check.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
