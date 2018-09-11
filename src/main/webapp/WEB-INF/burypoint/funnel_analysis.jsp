<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <link href="<%=basePath%>resources/css/public/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <title>漏斗列表</title>
    <style type="text/css">
    	#conditonText {
    		font:normal normal 18px/20px arial,sans-serif;
    		color : green;
    		cursor: hand;
    	}
    	.closeStep {
    		cursor: hand;
    	}
		#datagrid a {
			text-decoration : underline;
			color : blue;
		}
		.row {
			-webkit-box-align: center;
			align-items:center;
		}
    </style>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="5%" style="white-space:nowrap;">
				         渠道：<input name="channelId" class="mini-treeselect" url="operatemgr/marketchannel/list"  
				emptyText="请选择" showClose="true" oncloseclick="Cms.onCloseClick" textField="channelName" valueField="channelId" 
				parentField="parentChannelId" style="width: 200px;"/>
				         终端：<input name="platform" id="platform" class="mini-combobox"
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
				         版本：<input name="versionId" id="versionId" class="mini-combobox"
			emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
				</td>
			</tr>
			<tr>
				<td width="5%" style="white-space:nowrap;">
				         漏斗：<input name="id" id="id" class="mini-combobox"  style="width:200px;" url="bp/funnel/dic"
					   emptyText="请选择" allowInput="true" valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="bp_funnel_analysis.whenChangeFunnle"/> 
					有序漏斗<input name="orderType" id="orderType" class="mini-combobox" data="[{id:1,text:'无序'},{id:2,text:'有序'}]" enabled="false"/>
					转化期：<input id="dayNumber" id="dayNumber" class="mini-textbox" style="width:30px;" enabled="false">天  
					<input name="beginTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
					至
					<input name="endTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
					<a class="mini-button" onclick="bp_funnel_analysis.toAdd">创建漏斗</a>
				</td>
			</tr>
			<tr>
				<td width="5%" style="white-space:nowrap;">
					切用户符合<span id="conditonText">+筛选条件</span>
		        <a class="mini-button" onclick="bp_funnel_analysis.search">查询</a>
				</td>
			</tr>
			<tr id="afterSearchTr"></tr>
		</table>
	</div>
	</div>
	
	<div class="row">
		<div class="col-3" id="funnelTransformDiv">
		转化漏斗
		</div>
		<div class="col-9" id="funnelChart" style="width: 900px; height: 400px;"></div>
	</div>
	
	转化详情
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" style="width:100%;height:80%;"
		 idField="id" allowResize="true" ajaxType="get" multiSelect="true"
		 showColumnsMenu="true" showFilterRow="false" onrowdblclick="bp_funnel.toUserDetail">
		<div property="columns">
			<%--
			<div type="checkcolumn" width="5%"></div>
			<div type="indexcolumn" width="5%" align="center" headerAlign="center">序号</div>
			--%>
			<div field="date" headerAlign="center" align="center">日期</div>
			<div field="rate" headerAlign="center" align="center">总体转化率</div>
		</div>
	</div>
  </body>
 <script src="<%=basePath%>resources/js/public/echarts.min.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/burypoint/bp_common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/burypoint/bp_funnel_analysis.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
