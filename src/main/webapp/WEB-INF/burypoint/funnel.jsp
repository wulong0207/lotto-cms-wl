<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>漏斗列表</title>
    <style type="text/css">
    	div.funnel-step{
    		margin:8px 0 8px 0;
    	}
    	.closeStep {
    		margin-left : 10px;
    		font:normal normal 20px/20px arial,sans-serif;
    		color : grey;
    		cursor: hand;
    	}
    </style>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
			<btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="bp_funnel.toEdit">编辑</a>
	        </btn:operate>
	        <btn:operate privilege="ADD">
		        <a class="mini-button" iconCls="icon-add" plain="true" onclick="bp_funnel.toAdd">新增</a>
	        </btn:operate>
	        
	        <btn:operate privilege="DEL">
		        <a class="mini-button" iconCls="icon-remove" plain="true" onclick="bp_funnel.toDelete">删除</a>
	        </btn:operate>
	         &nbsp;&nbsp;&nbsp;<input name="nameLike" class="mini-textbox"
					   emptyText="请输入你要查询的漏斗名称" width="200px"/> 
			        <a class="mini-button" onclick="bp_funnel.search">查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:87%;" 
		 url="bp/funnel/page" idField="id" allowResize="true" ajaxType="get" multiSelect="true"
		 showColumnsMenu="true" showFilterRow="false" onrowdblclick="bp_funnel.toFunnelAnalysis" ajaxOptions="{type:'post'}">
		<div property="columns">
			<div type="checkcolumn" width="5%"></div>
			<div type="indexcolumn" width="5%" align="center" headerAlign="center">序号</div>
			<div field="name" headerAlign="center" align="center">漏斗名称</div>
			<div field="bpCodeText" headerAlign="center" align="center">转化步骤</div>
			<div field="platformsIdText" headerAlign="center" align="center">对应终端</div>
			<div field="channelsIdText" headerAlign="center" align="center">对应渠道</div>
			<div field="versionsIdText" headerAlign="center" align="center">对应版本号</div>
			<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">添加时间</div>
			<div field="modifyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
		</div>
	</div>
	
	<div id="detailWindow" showToolbar="true" class="mini-window" style="width:900px;height:750px" showFooter="true" showModal="true" allowResize="true" allowDrag="true" showCloseButton="true">
	    
	    <div property="toolbar" style="text-align:right;padding:2px;padding-right:15px;">
			<btn:operate privilege="ADD">
		        <a class="mini-button" onclick="bp_funnel.merge">保存</a>
	        </btn:operate>
		        <a class="mini-button" onclick="bp_funnel.cancel('detailWindow')">取消</a>
        </div>
        
	    <input name="id" class="mini-hidden"/>
	
	渠道：<input name="channelsId" class="mini-treeselect" url="operatemgr/marketchannel/list" multiSelect="true" 
				emptyText="请选择" showClose="true" oncloseclick="Cms.onCloseClick" textField="channelName" valueField="channelId" parentField="parentChannelId" style="width: 500px;" required="true"/>  
  </br></br>   
    
     终端：   <input name="platformsId" id="platformsId" class="mini-combobox" style="width:500px"
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" multiSelect="true" required="true"/>
  </br></br> 
  
     版本：   <input name="versionsId" id="versionsId" class="mini-combobox" style="width:500px"
			emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" multiSelect="true" required="true"/>
	</br></br> 
	漏斗名称：<input name="name" class="mini-textbox" required="true"/>
	
	</br></br>
	
	
	漏斗步骤：
	</br>
	<div class="funnel-step">
	步骤一：<input name="bpCode1" id="bpCode1" class="mini-combobox" style="width:500px"
			emptyText="请选择或输入" valueFromSelect ="true" showClose="true" allowInput="true" oncloseclick="Cms.onCloseClick" required="true"/>
	</div>
	
	<div class="funnel-step">
	步骤二：<input name="bpCode2" id="bpCode2" class="mini-combobox" style="width:500px"
			emptyText="请选择或输入" valueFromSelect ="true" showClose="true" allowInput="true" oncloseclick="Cms.onCloseClick" required="true"/>
	</div>
	
		<a class="mini-button" id="addStepBtn" onclick="bp_funnel.addStep" style="width:500px;margin-left:45px;">+</a>
	
	</br></br>
	漏斗类型：
	<%--排序类型 1,无序,2有序 --%>
	<div name="orderType" required="true" class="mini-radiobuttonlist" repeatItems="1" repeatDirection="vertical" data="[{id:1,text:'无序'},{id:2,text:'有序'}]"></div>
	
	</br>
	转化时间：
	<input name="dayNumber" class="mini-combobox"
	data="[{id:7,text:'7天'},{id:30,text:'30天'},{id:60,text:'60天'},{id:90,text:'90天'},{id:180,text:'180天'},{id:360,text:'360天'}]"
			emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" required="true"/>
	
	</div>
	
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/burypoint/bp_funnel.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
