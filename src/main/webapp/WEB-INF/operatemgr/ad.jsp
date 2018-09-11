<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>广告图管理</title>
    <style>
	   	.mini-datepicker {
	   		width: 180px;
	   	}
    </style>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="ADD">
		        <a class="mini-button" iconCls="icon-add" plain="true" onclick="operate_ad.add()">新增</a>
	        </btn:operate>
	        <btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_ad.edit()">修改</a>
	        </btn:operate>
				</td>
			</tr>
			<tr>
				<td>
					发布平台<input id="platform" name="platform" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
					广告图页面<input id="menu" name="menu" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" onvaluechanged="operate_ad.handleMenuChange"/> 
					广告图位置<input id="position" name="position" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
					广告图状态<input id="status" name="status" class="mini-combobox"  style="width:150px;" emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
					发布状态<input id="publishStatus" name="publishStatus" class="mini-combobox"  style="width:150px;" emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
					<input id="searchTimeType"  name="searchTimeType" class="mini-combobox"  style="width:150px;" valueFromSelect ="true"/>
					<input id="startTime" name="startTime" class="mini-datepicker" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" name="endTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>  
					<a class="mini-button" onclick=" Cms.search('datagrid','form1',['startTime','endTime'])" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:91%;" 
		 url="operatemgr/ad/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
		 showColumnsMenu="true" onrowdblclick="operate_ad.edit" showFilterRow="false">
		<div property="columns">
			<div type="checkcolumn" width="3%"></div>
			<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
			<div field="advCode" headerAlign="center" align="center">发布编号</div>
			<div field="platform" type="comboboxcolumn" headerAlign="center" align="center">发布平台
		     <input property="editor" class="mini-combobox" data="Dic.platform" />
			</div>
			<div field="menu" type="comboboxcolumn" headerAlign="center" align="center">广告图页面
		     <input property="editor" class="mini-combobox" data="Dic.menu" />
			</div>
			<div field="position" type="comboboxcolumn" headerAlign="center" align="center">广告图位置
		     <input property="editor" class="mini-combobox" data="Dic.position" />
			</div>
			<div field="orderId" headerAlign="center" align="center">排序值</div>
			<div headerAlign="center" align="center" renderer="operate_ad.rowPublishStatus">发布状态</div>
			<div field="advTitle" headerAlign="center" align="center">广告标题</div>
			<div field="onlineTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">上线时间</div>	
			<div field="offlineTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">下线时间</div>	
			<div field="modifyBy" headerAlign="center" align="center">修改人</div>
		</div>
	</div>
	<div id="detailWindow" title="新增广告图详情" class="mini-window" style="width:1200px;" showFooter="true" showModal="true" allowResize="true" allowDrag="true" onbuttonclick="operate_ad.clearData">
 		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
	    <btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="operate_ad.merge">保存</a>
			</btn:operate>
		</div>
	    <div id="detailForm" class="form">
	    	<input name="id"  class="mini-hidden"/>
	     	<input name="url"  class="mini-hidden"/>
			 	<input name="action"  class="mini-hidden"/>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <legend>广告图信息：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td style="width: 100px">发布平台</td>
	                <td><input id="platformDetail" name="platform" class="mini-combobox" emptyText="请选择" valueFromSelect="true" multiSelect="true" required="true"/></td>
	                <td>发布编号</td>
	                <td><input name="advCode" class="mini-textbox"  enabled="false"/></td>
	                <td>广告图状态</td>
	                <td><input id="statusDetail" name="status" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
	                <td>是否默认</td>
	                <td><input id="defaultAd" name="defaultAd" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
	            </tr>
	            <tr>
	                <td>广告图页面</td>
	                <td><input id="menuDetail" name="menu" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true" onvaluechanged="operate_ad.handleDetailMenuChange"/></td>
	                <td>广告图位置</td>
	                <td><input id="positionDetail" name="position" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
	                <td>广告图排序</td>
	                <td><input name="orderId" class="mini-spinner"  minValue="1" maxValue="5"/></td>
	                <td>跳转彩种页面</td>
	                <td><input id="lotteryCode" name="lotteryCode" class="mini-combobox" emptyText="请选择" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"/></td>
	            </tr>
					<tr>
						<td>计划上线时间</td>
						<td><input id="onlineTime" name="onlineTime" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" required="true" timeFormat="HH:mm:ss" showTime="true" showClearButton="false" showOkButton="true"/></td>
						<td>计划下线时间</td>
						<td><input id="offlineTime" name="offlineTime" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" required="true" timeFormat="HH:mm:ss" showTime="true" showClearButton="false" showOkButton="true"/></td>
					</tr>

					<tr>
						<td>广告可见次数</td>
						<td><input id="displayDetail" name="display" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
						<td>打开方式</td>
						<td><input id="targetDetail" name="target" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
					</tr>
					<tr>
						<td>广告对象</td>
						<td><input id="userTypesDetail" name="userTypes" url="sysmgr/user/type/dic" class="mini-combobox" emptyText="请选择" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" multiSelect="true" required="true" onitemclick="operate_ad.checkUserTypeValue"/></td>

						<td>广告渠道</td>
						<td rowspan="1" colspan="5">
							<a class="mini-button" onclick="operate_ad.checkAll">全选</a>
							<a class="mini-button" onclick="operate_ad.checkReverse">反选</a>
							<div id="channels" name="channels" class="mini-treegrid" style="width:100%;height:150px;"
								 treeColumn="channelName" idField="channelId" parentField="parentChannelId" resultAsTree="false"
								 allowResize="true" showTreeIcon="true"
								 showCheckBox="true" allowSelect="false" allowCellSelect="false"
								 enableHotTrack="false" showTreeLines="true">
								<div property="columns">
									<div type="indexcolumn"></div>
									<div field="channelName" name="channelName"  width="120" >渠道名称</div>
									<!-- <div field="functions" width="100%">子节点</div> -->
								</div>
							</div>
							<%--
                            <input id="channels" name="channels" checkRecursive="true" expandOnLoad="true" showFolderCheckBox="true" class="mini-treegrid" textField="channelName" valueField="channelId" parentField="parentChannelId" valueFromSelect="true" allowInput="true" multiSelect="true" emptyText="请选择" valueFromSelect="true" showRadioButton="true" style="width:180px;" showClose="true" oncloseclick="Cms.onCloseClick" multiSelect="true" required="true" onbeforenodecheck="operate_ad.isCheckAllChannel" onvaluechanged="operate_ad.checkChannelValue"/>
                            --%>
						</td>
					</tr>

				<tr>
	                <td>广告链接</td>
	                <td colspan="5"><input name="adUrl" class="mini-textbox" style="width:100%;"/></td>
	            </tr>
	            <tr>
	                <td>广告图标题</td>
	                <td colspan="5"><input name="advTitle" class="mini-textbox" style="width:100%;" required="true"/></td>
	            </tr>
	            <tr>
	                <td>广告图描述</td>
	                <td colspan="5"><input name="positionInfo" class="mini-textbox" style="width:100%;"/></td>
	            </tr>
	            <tr>
		            <td rowspan="2">广告图地址</td>
		            <td colspan="4"><input name="img" id="img" class="mini-hidden"/></td>
		            <td>
		            	<btn:operate privilege="UPD">
							<input type="button" value="从图库选择" onclick="operate_ad.openImage()"/>
						</btn:operate>
		            </td>
	            </tr>
	            <tr>
	            	<td colspan="4"><img id="adImg" alt="广告图片" width="500px;" height="300px;"></td>
	            	<td>
	            		文件名：<input name="fileName" id="fileName" class="mini-textbox" enabled="false"/><br/>
	            		图片格式：<input name="fileFormat" id="fileFormat" class="mini-textbox" enabled="false"/><br/>
	            		大小：<input name="fileSize" id="fileSize" class="mini-textbox" enabled="false"/><br/>
	            		创建日期：<input name="fileCreateTime" id="fileCreateTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/>
	            	</td>

	            </tr>
	        </table>
			  </fieldset>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <legend>操作信息：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td style="width:100px;">创建时间</td>
	                <td style="width:150px;"><input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	                <td style="width:100px;">修改时间</td>
	                <td style="width:150px;"><input name="modifyTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            		<td style="width:100px;" rowspan="2">备注</td>
	                <td style="width:150px;" rowspan="2"><input name="remark" class="mini-textarea"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">创建人</td>
	                <td style="width:150px;"><input name="createBy" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">修改人</td>
	                <td style="width:150px;"><input name="modifyBy" class="mini-textbox" enabled="false"/></td>
	            </tr>
	        </table>
			  </fieldset>
	    </div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/operate_ad.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
