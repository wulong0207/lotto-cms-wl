<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>市场渠道</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td width="15%" nowrap="nowrap"> 
			        <btn:operate privilege="ADD">
					<a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="Current.addwin()">添加</a>
					</btn:operate> 	
			     	<btn:operate privilege="UPD|SEARCH">
					<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.editwin()">修改</a>
					</btn:operate> 			
					 <btn:operate privilege="EXPORT">
			        <a class="mini-button" iconCls="icon-download" plain="true" onclick="Current.excel()">导出</a>
			        </btn:operate>
				</td>
			</tr>
			<tr>
			<td width="5%" style="white-space:nowrap;">
				        渠道id：<input id="channelId" class="mini-textbox" style="width:100px;"/>
				          渠道名称：<input id="channelName" class="mini-textbox" style="width:100px;"/>
				   父级渠道ID：<input id="parentChannelId" class="mini-textbox" style="width:100px;"/>      
				       级别：<input id="grade"   class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				   终端平台    ：<input id="terminalPlatform"   class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				   马甲包渠道    ：<input id="majiaSerach" class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				</td>
			</tr>
			<tr>
			<td width="5%" style="white-space:nowrap;">
				       结算类型          ：<input id="settlementType"   class="mini-combobox"  style="width:120px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				   <input id="timeType"  class="mini-combobox"  style="width:100px;"/>
				   <input id="startTime" class="mini-datepicker"  allowInput="false"  style="width:180px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" class="mini-datepicker"  allowInput="false"  style="width:180px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>  
					<a class="mini-button" onclick="Current.search()">查询</a>
				</td>
			</tr>
		</table>
	</div>


			<div id="channelTreegrid" class="mini-treegrid" style="width:100%;height:100%"     
    treeColumn="channelName" idField="channelId" parentField="parentChannelId" resultAsTree="false"
    allowResize="true" expandOnLoad="true" showTreeIcon="true"
    showCheckBox="false" allowSelect="true" allowCellSelect="false"
    enableHotTrack="true" showTreeLines="false"
    ajaxOptions="{type : 'post'}">
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="channelId" width="7%" headerAlign="center" align="center">渠道ID</div>
					<div field="channelName" name="channelName" width="20%" headerAlign="center">渠道名称</div>
					<div field="parentChannelId" width="8%" headerAlign="center" align="center">父级渠道ID</div>
					<div field="grade" width="7%" type= "comboboxcolumn" headerAlign="center" align="center">级别
					     <input property="editor" class="mini-combobox" data=" Dic.grade" />
					</div>
					<div field="terminalPlatform" width="7%" type= "comboboxcolumn" headerAlign="center" align="center">终端平台
					     <input property="editor" class="mini-combobox" data="Dic.terminalPlatform" />
					</div>
					<div field="majia" width="7%" type= "comboboxcolumn" headerAlign="center" align="center">马甲包渠道
					     <input property="editor" class="mini-combobox" data="Dic.majia" />
					</div>
					<div field="resourceUrl" width="10%" headerAlign="center" align="center">资源URL</div>
					<div field="resourceName" width="10%" headerAlign="center" align="center">资源名称</div>
					<div field="settlementType" width="7%" type= "comboboxcolumn" headerAlign="center" align="center">结算类型
					     <input property="editor" class="mini-combobox" data=" Dic.settlementType" />
					</div>
					<div field="settlementRate" width="10%" headerAlign="center" align="center" renderer="Current.onSettlementRate">结算费率</div>
					<div field="coopStartTime" width="12%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">合作开始时间</div>	
					<div field="coopEndTime" width="12%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">合作结算时间</div>
					<div field="remark" width="8%" headerAlign="center" align="center">备注</div>	
					<div field="updateTime" width="12%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="createTime" width="12%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
				</div>
		</div>



	<%--
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
		<div size="90%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="operatemgr/marketchannel/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" onrowdblclick="Current.onrowdblclick">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="channelId" width="7%" headerAlign="center" align="center">渠道ID</div>
					<div field="channelName" width="8%" headerAlign="center" align="center">渠道名称</div>
					<div field="parentChannelId" width="8%" headerAlign="center" align="center">父级渠道ID</div>
					<div field="grade" width="7%" type= "comboboxcolumn" headerAlign="center" align="center">级别
					     <input property="editor" class="mini-combobox" data=" Dic.grade" />
					</div>
					<div field="terminalPlatform" width="7%" type= "comboboxcolumn" headerAlign="center" align="center">终端平台
					     <input property="editor" class="mini-combobox" data="Dic.terminalPlatform" />
					</div>
					<div field="resourceUrl" width="10%" headerAlign="center" align="center">资源URL</div>
					<div field="resourceName" width="10%" headerAlign="center" align="center">资源名称</div>
					<div field="settlementType" width="7%" type= "comboboxcolumn" headerAlign="center" align="center">结算类型
					     <input property="editor" class="mini-combobox" data=" Dic.settlementType" />
					</div>
					<div field="settlementRate" width="10%" headerAlign="center" align="center" renderer="Current.onSettlementRate">结算费率</div>
					<div field="coopStartTime" width="12%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">合作开始时间</div>	
					<div field="coopEndTime" width="12%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">合作结算时间</div>
					<div field="remark" width="8%" headerAlign="center" align="center">备注</div>	
					<div field="updateTime" width="12%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="createTime" width="12%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
				</div>
			</div>
		</div>
	</div>
	--%>
	
	<div id="editWindow" class="mini-window" title="修改会员信息" 
		style="width:1100px; height:350px;" showMaxButton="false" 
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
	            <td width="20%"><input id="channelId_edit" name="channelId" class="mini-textbox" style="width:100%;" vtype ="maxLength:20" enabled="true"/></td>
	            <td width="13%">渠道名称</td>
	            <td width="20%"><input name="channelName" class="mini-textbox" style="width:100%;" required="true" vtype ="maxLength:30"/></td>
	            <td width="13%" >终端平台</td>
	            <td width="20%" ><input id ="terminalPlatform_edit" name="terminalPlatform"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"/></td>
	            </tr>
	            <tr>
	            <td>级别</td>
	            <td><input id ="grade_edit" name="grade"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true" onvaluechanged="Current.gradeChange()"/></td>
	            <td>父级渠道ID</td>
	            <td><input id ="parentChannelId_edit" name="parentChannelId"  class="mini-combobox" emptyText="请选择" style="width:100%;" allowInput="true"/></td>
	            <td >结算类型</td>
	            <td ><input id ="settlementType_edit" name="settlementType"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true" required="true"  style="width:100%;" /></td>
	            </tr>
	             <tr>
	            <td>资源名称</td>
	            <td><input name="resourceName" class="mini-textbox" style="width:100%;" vtype="maxLength:30"/></td>
	            <td>资源Url</td>
	            <td><input name="resourceUrl" class="mini-textbox" style="width:100%;" vtype="maxLength:100"/></td>
	            <td >结算费率</td>
	            <td><input name="settlementRate" class="mini-textbox" style="width:90%;" vtype="float"/>%</td>
	            </tr>
	             <tr>
	            <td>合作开始时间</td>
	            <td><input name="coopStartTime" class="mini-datepicker"  allowInput="false"  style="width:100%;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" /></td>
	            <td>合作结束时间</td>
	            <td><input name="coopEndTime" class="mini-datepicker"  allowInput="false"  style="width:100%;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/></td>
	            <td>渠道状态</td>
	            <td ><input id ="channelStatus_edit" name="channelStatus"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"/></td>
	            </tr>
				<tr>
					<td>马甲包渠道</td>
					<td ><input name="majia" id="majia" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"/></td>
					<td>渠道秘钥</td>
					<td ><input name="secretKey" id="secretKey" class="mini-textbox" style="width:100%;" /></td>					
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
			  <td width="20%"><input name="modifyTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
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
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/market_channel.js?vsersion=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
</html>
