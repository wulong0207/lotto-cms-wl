<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>活动</title>
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
				        活动ID：<input id="activityCode" class="mini-textbox" style="width:100px;"/>
				          活动名称：<input id="activityName" class="mini-textbox" style="width:100px;"/>  
				        活动类型：<input id="activityType"   class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				</td>
			</tr>
			<tr>
			<td width="5%" style="white-space:nowrap;">
				   <input id="timeType"  class="mini-combobox"  style="width:100px;"/>
				   <input id="startTime" class="mini-datepicker"  allowInput="false"  style="width:180px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" class="mini-datepicker"  allowInput="false"  style="width:180px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				          活动状态 ：<input id="activityStatus"   class="mini-combobox"  style="width:120px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />  
				          发布状态 ：<input id="publishStatus"   class="mini-combobox"  style="width:120px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />  
					<a class="mini-button" onclick="Current.search()">查询</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="90%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="operatemgr/activity/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" onrowdblclick="Current.onrowdblclick" onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="activityCode" headerAlign="center" align="center">活动ID</div>
					<div field="activityName" headerAlign="center" align="center">活动名称</div>
					<div field="activityType" type= "comboboxcolumn" headerAlign="center" align="center">活动类型
					     <input property="editor" class="mini-combobox" data=" Dic.activityType" />
					</div>
					<div headerAlign="center" align="center" renderer="Current.rowPublishStatus">发布状态</div>
					<div field="partNum" headerAlign="center" align="center">参与人数</div>
					<div field="prizeGetNum" headerAlign="center" align="center"  numberFormat="n">获取奖品人数</div>
					<div field="totalCost" headerAlign="center" align="center"  numberFormat="c">成本总投入</div>
					<div field="costPaid" headerAlign="center" align="center"  numberFormat="c">成本实付</div>
					<div field="uvCount" headerAlign="center" align="center"  numberFormat="n">UV总数</div>
					<div field="pvCount" headerAlign="center" align="center"  numberFormat="n">PV总数</div>
					<div field="activityStartTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">活动开始时间</div>	
					<div field="activityEndTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">活动结束时间</div>
					<div field="remark" headerAlign="center" align="center">备注</div>
					<div field="updateTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>	
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
					<div field="opt" renderer="Current.onActionRenderer"  width="12%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">操作</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="editWindow" class="mini-window" title="修改会员信息" 
		style="width:1100px; height:700px;" showMaxButton="false" 
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
	            <legend >活动基本信息：</legend>
	            <div style="padding:5px;">
	             <table width="100%;">
	            <tr>
	            <td width="13%">活动ID</td>
	            <td width="20%"><input id="activityCode_edit" name="activityCode" class="mini-textbox" style="width:100%;" required="true" vtype ="maxLength:20" enabled="true"/></td>
	            <td width="13%">活动名称</td>
	            <td width="20%"><input name="activityName" class="mini-textbox" style="width:100%;" required="true" vtype ="maxLength:20"/></td>
	            <td width="13%" >活动类型</td>
	            <td width="20%" ><input id ="activityType_edit" name="activityType"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"/></td>
	            </tr>
	            <tr>
	            <td>活动开始时间</td>
	            <td><input name="activityStartTime" class="mini-datepicker"  allowInput="false"  style="width:100%;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" required="true"/></td>
	            <td>活动Url</td>
	            <td><input  name="activityUrl"  class="mini-textbox" style="width:100%;"  vtype ="maxLength:100"/></td>
	            <td >活动状态</td>
	            <td ><input id ="activityStatus_edit" name="activityStatus"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"/></td>
	            </tr>
	            <tr>
	            <td>活动结束时间</td>
	            <td><input name="activityEndTime" class="mini-datepicker"  allowInput="false"  style="width:100%;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" required="true"/></td>
	            <td>UV总数</td>
	            <td><input name="uvCount" class="mini-textbox" style="width:100%;" vtype="int" enabled="false" /></td>
	            <td>PV总数</td>
	            <td><input name="pvCount" class="mini-textbox" style="width:100%;" vtype="int" enabled="false"/></td>
	            </tr>
	             <tr>
	            <td>参与人数</td>
	            <td><input name="partNum" class="mini-textbox" style="width:100%;" vtype="int" enabled="false"/></td>
	            <td rowspan="4">参与渠道</td>
	            <td rowspan="4" colspan="3">
	             <a class="mini-button" onclick="Current.reverseSelect">反选</a>
	             <a class="mini-button" onclick="Current.allSelect">全选</a>
	             <div id="treegrid" class="mini-treegrid" style="width:100%;height:150px;"
					    treeColumn="name" idField="id" parentField="pid" resultAsTree="false"
                        allowResize="true" expandOnLoad="true" showTreeIcon="true"
                        showCheckBox="true" allowSelect="false" allowCellSelect="false"
                        enableHotTrack="false" showTreeLines="true">
					    <div property="columns">
					        <div type="indexcolumn"></div>
					        <div field="name" name="name"  width="120" >渠道名称</div>
					        <!-- <div field="functions" width="100%">子节点</div> -->
					    </div>
				  </div>
 	            <input id ="channelId_edit" name="channelId" class="mini-hidden"/></td> 
	            </td>
	            </tr>
	            <tr>
	            <td>获取奖品人数</td>
	            <td><input name="prizeGetNum" class="mini-textbox" style="width:100%;" vtype="float" enabled="false"/></td>
	            </tr>
	            <tr>
	            <td>成本总投入</td>
	            <td><input name="totalCost" class="mini-textbox" style="width:100%;" vtype="float" enabled="false"/></td>
	            </tr>
	            <tr>
	            <td>成本实付</td>
	            <td><input name="costPaid" class="mini-textbox" style="width:100%;" vtype="int" enabled="false"/></td>
	            </tr>
	            <tr>
	            <td>活动描述</td>
	            <td colspan="5"><input name="activityDes" class="mini-textarea"  style="width:100%;height: 200px;"/></td>
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
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/activity.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
