<%@page import="java.util.Date"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
	<title>渠道报表</title>
	<style type="text/css">
		td{
			font-size:9px
		}
		
		#form a{
			margin-left:8px;
			margin-right:8px;
			cursor:hand;
		}
		
		.mini-tabs-space {
   			border-color: #ffffff;
		}
		
		.mini-tabs-bodys {
    		border-color: #ffffff;
		}
		
		.active{
			color:white;
			background-color:rgb(61, 116, 183);
		}
	</style>
	
</head>
<body>
	<div class="mini-toolbar" style="border-top: 0; border-left: 0; padding: 0px;">
		<div id="form">
			<table style="width: 100%;">	
				<tr>
					<td>选择查询时间：<input id="startTime" name="startTime" class="mini-datepicker" oncloseclick="Cms.onCloseClick"
						allowInput="false" style="width: 200px;" format="yyyy-MM-dd"
						showClearButton="true" showClose="true"/> 
						到
						<input id="endTime" name="endTime" class="mini-datepicker" allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
							oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
					    <button  onclick="channelData.search()" style="width:60px;">查询</button>
					    
					    <btn:operate privilege="EXPORT">
						      &nbsp;&nbsp;<button  iconCls="icon-download" plain="true" onclick="channelData.excel()">导出</button>
						</btn:operate>
					</td>
				</tr>
			</table>
		</div>
	</div>
		
						<div id="channelDatagrid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
							url="userdata/findChannelDataList" pageSize="30" showColumnsMenu="true" showPagerButtonText="true" 
							showPagerButtonIcon="true" showFilterRow="false" allowMoveColumn="true" showSummaryRow="true">
	
							<div property="columns">
								<div field="date"  headerAlign="center" align="center">日期</div>
								<div field="channelName" headerAlign="center" align="center">渠道名称</div>
								<div field="channelId" headerAlign="center" align="center">渠道id</div>
								<div field="regUser" headerAlign="center" align="center">注册用户</div>
								<div field="perfectNumber" headerAlign="center" align="center">完善用户</div>
								<div field="ffUser" headerAlign="center" align="center">首充用户</div>
								<div field="ffMoney" headerAlign="center" align="center">首充金额</div>
								<div field="foUser" headerAlign="center" align="center">首投人数</div>
								<div field="foMoney" headerAlign="center" align="center">首投金额</div>
								<div field="fUser" headerAlign="center" align="center">充值用户</div>
								<div field="fMoney" headerAlign="center" align="center">充值金额</div>
								<div field="oUser" headerAlign="center" align="center">投注人数</div>
								<div field="oMoney" headerAlign="center" align="center">投注金额</div>
							</div>
						</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/report/channelData.js?version=<%=version%><%=System.currentTimeMillis() %>" type="text/javascript"></script>
</html>
