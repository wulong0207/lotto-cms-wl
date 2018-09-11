<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	<script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>会员访问数据分析</title>
  </head>
  
  <body>
	<div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			    <td width="15%" nowrap="nowrap">
			        <btn:operate privilege="SEARCH">
				     	<a class="mini-button" iconCls="icon-upload" plain="true" onclick="UserAnalysisObj.uploadSearch()">上传查询</a>
			     	</btn:operate>
			        <btn:operate privilege="EXPORT">
			        	<a class="mini-button" iconCls="icon-download" plain="true" onclick="UserAnalysisObj.excel()">导出</a>
			        </btn:operate>
				</td>
				
				<td width="5%" style="white-space:nowrap;">
				   <input id="userQryType" class="mini-combobox" style="width:80px;" allowInput="true" valueFromSelect="true" />
				   <input id="userQryVal" class="mini-textbox" style="width:200px;" />
				        渠道号：  <input id="channelId" class="mini-textbox" style="width:200px;"/>
				       访问时间： <input id="startTime" class="mini-datepicker" allowInput="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick"/>
				        到<input id="endTime" class="mini-datepicker" allowInput="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick"/> 
					<a class="mini-button" onclick="UserAnalysisObj.search()">查询</a>
				</td>
				
				<td width="30%"></td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="operatemgr/useranalysis/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" 
				 onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="nickName" headerAlign="center" align="center">昵称</div>
					<div field="channelId" type="comboboxcolumn" headerAlign="center" align="center">渠道来源
					    <input property="editor" class="mini-combobox" data="Dic.channelName" />
					</div>
					<div field="ip" headerAlign="center" align="center">操作IP</div>
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">访问时间</div>
					<div field="seconds" headerAlign="center" align="center">停留时长(秒)</div>
					<div field="webName" headerAlign="center" align="center">访问功能名称</div>
					<div field="url" headerAlign="center" align="center">访问Url</div>
					<div field="goWebName" headerAlign="center" align="center">去向访问功能名称</div>
					<div field="goUrl" headerAlign="center" align="center">去向访问Url</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="upoladWindow" class="mini-window" title="上传查询" 
		style="width:250px; height:200px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
				
		<div id="searchForm">
			<table width="100%;">
				<!--  查询类型：<input id="uploadType"  class="mini-combobox"  style="width:80px;" allowInput="true" valueFromSelect = "true"/> -->
				<div id="uploadType" class="mini-radiobuttonlist" repeatLayout="table"
					 data="[{id:'account_name',text:'会员帐户'},{id:'nick_name',text:'会员昵称'},{id:'cus_mobile',text:'手机号码'}]"> 
				</div>
              	<br>
	         	<input id="fileSearch" class="mini-htmlfile" name="file" limitType="*.txt" style="width:180px;" required="true"/>
	         	<btn:operate privilege="UPLOAD">
		     		<a class="mini-button"   onclick="UserAnalysisObj.ajaxFileUploadSearch('fileSearch')">确定</a>
		     	</btn:operate>
		     	<br>上传查询说明：<br>1.必须是“ .txt ”文件 ；<br>2.一行一个会员信息，不能有其它字符 ；
			</table>
		</div>
	</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/user_analysis.js" type="text/javascript"></script>
</html>
