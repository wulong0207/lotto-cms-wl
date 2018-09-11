<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <link href="<%=basePath%>resources/css/public/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <title>用户汇款管理</title>
    <style>
		img{
			width: 100%;
			height: 100%;
		}
    </style>
  </head>
  <body>
  
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="50%" nowrap="nowrap">
					&nbsp;&nbsp;
			        <btn:operate privilege="UPD">
						<input id="statusUpd" class="mini-combobox"/>
						<a class="mini-button" onclick="trans_remitting.visitStatus">确定</a>
			        </btn:operate>
					<btn:operate privilege="EXPORT">
				        <a class="mini-button" iconCls="icon-download" plain="true" onclick="trans_common.excel('transmgr/remitting')">导出Excel</a>
			        </btn:operate>
				</td>
			</tr>
			<tr>
				<td>
					<input id="userSearchType" name="userSearchType" class="mini-combobox"  style="width:150px;" valueFromSelect ="true"/>
				 	<input id="userSearchValue" name="userSearchValue" class="mini-textbox" style="width:200px;"/>
					&nbsp;&nbsp;
					汇款银行<input id="remittingBank" name="remittingBank" class="mini-combobox"  style="width:250px;"
							   emptyText="请选择"  valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					&nbsp;&nbsp;
					汇款提交时间
					<input id="startTime" name="startTime" class="mini-datepicker" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" name="endTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>

					&nbsp;&nbsp;
					手动充值状态<input id="status" name="status" class="mini-combobox"  style="width:150px;"
					emptyText="请选择"  valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" data="[{id:1,text:'待充值'},{id:2,text:'已充值'}]"/>

					<a class="mini-button" onclick="trans_common.search(trans_remitting.grid)" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:90%;" 
		 url="transmgr/remitting/page" idField="id" allowResize="true" multiSelect="true"
		 showColumnsMenu="true" onrowdblclick="" showFilterRow="false" allowCellSelect="true"
		 oncelleditenter="trans_remitting.visitStatus"
		>
		<div property="columns">
			<div type="checkcolumn" width="3%"></div>
			<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
			<div field="nickName" headerAlign="center" align="center">会员昵称</div>
			<div field="accountName" headerAlign="center" align="center">会员账号</div>
			<div field="cusMobile" headerAlign="center" align="center">用户手机</div>
			<div field="actualName" headerAlign="center" align="center">真实姓名</div>
			<div field="remittingBank" type= "comboboxcolumn" headerAlign="center" align="center">汇款银行
		     <input property="editor" class="mini-combobox" data="Dic.remittingBank" enabled="false"/>
			</div>
			<div field="remittingAmount" headerAlign="center" align="center">汇款金额(元)</div>
			<div field="commitTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">汇款提交时间</div>
			<div field="status" type= "comboboxcolumn" headerAlign="center" align="center">手动充值状态
				<input property="editor" class="mini-combobox" data="[{id:1,text:'待充值'},{id:2,text:'已充值'},{id:3,text:'误充值'}]" />
			</div>
			<div headerAlign="center" align="center" renderer="trans_remitting.image">汇款转账图片</div>
		</div>
	</div>


  <div id="imgWin" class="mini-window" style="width: 1500px;height: 800px;" allowResize="true" allowDrag="true" showMaxButton="true" showModal="true">
	  <img id="img">
  </div>

  </body>
<script src="<%=basePath%>resources/js/public/ie-fix.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/transmgr/trans_common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/transmgr/trans_remitting.js" type="text/javascript"></script>
</html>
