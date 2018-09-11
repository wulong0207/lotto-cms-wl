<%@page import="java.util.Date"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
	<link href="<%=basePath%>resources/css/public/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<title>盈利率</title>
	<style type="text/css">
		td{
			font-size:9px;
			white-space: nowrap;
			padding-left: 5px;
		}
		td a {
			cursor: hand;
			margin-left:8px;
			margin-right:8px;
		}
		.active {
			color:white;
			background-color:rgb(61, 116, 183)
		}
		.mini-toolbar {
			border-top: 0;
			border-left: 0;
			padding: 0px;
		}
	</style>
	
</head>
<body>
	<div class="mini-toolbar">
		<table style="width: 100%;">
			<tr>
					<td width="10%" id="statis-channel" colspan="2">
						注册渠道：
						<a data-channel="0" class="active">全部</a>|
					&nbsp;可选渠道：<input id="channel" name="userRegisterChannel" class="mini-treeselect" url="userdata/channelTree" multiSelect="false" 
								 valueFromSelect="false" style="width: 200px;" onvaluechanged="earnRate.clearActive('statis-channel')"
							        textField="text" valueField="id" parentField="pid" allowInput="true" showRadioButton="true" showFolderCheckBox="true"/>

					</td>
			</tr>
			<tr>
				<td width="20%" id="statis-code">
					彩种类型：
					<a class="active">全部</a>|
					<a data-code="100">双色球</a>|
					<a data-code="102">大乐透</a>|
					<a data-code="300">竞足</a>|
					<a data-code="301">竞篮</a>|
					<a data-code="304">胜负彩</a>|
					<a data-code="305">任九</a>|
					<a data-code="306">北单</a>|
					<a data-code="215">十一运夺金</a>|
					<a data-code="233">江苏快三</a>|
				</td>
				<td>
				其他彩种：<input id="lotteryCode" class="mini-combobox" emptyText="请选择" allowInput="true" showClose="true" onCloseClick="earnRate.comboxOnCloseClick" onvaluechanged="earnRate.clearActive('statis-code')"/>
				<td>
			</tr>
			<tr>
				<td width="20%" id="statis-time">
					时间：
					<a data-time="1"  class="active">昨天</a>|
					<a data-time="2">今天</a>|
					<a data-time="3">上周</a>|
					<a data-time="4">本周</a>|
					<a data-time="5">上月</a>|
					<a data-time="6">本月</a>|
				</td>
				<td>选择查询时间：
				<input style="width:200px;" id="startTime" name="startTime" class="mini-datepicker" format="yyyy-MM-dd" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="earnRate.clearActive('statis-time')"/>
			        到
			    <input style="width:200px;" id="endTime" name="endTime" class="mini-datepicker"  format="yyyy-MM-dd" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="earnRate.clearActive('statis-time')"/>
				  <button  id="searchBtn">查询</button></td>
			</tr>
		</table>
	</div>
	
	<div class="mini-panel" title="明细数据 :" style="width:100%;height:100%;margin-top:10px" showCollapseButton="true">
		
		<div  class="mini-tabs" activeIndex="0" style="width:100%;height:50%;margin-top:10px" contextMenu="#tabsMenu">
		    <div name="first" title="投注用户数">
				<table>
					<tr><td rowspan="13"><div id="betPersonChart" style="width: 900px; height: 350px;"></div></td></tr>
					<tr><td><div class="sumData">n&lt;=-30%    	      用户数：<tag id="u_n30"></tag>人，占比<tag id="ur_n30"></tag></div></td></tr>
					<tr><td><div class="sumData">-30%&lt;n≤0%             用户数：<tag id="u_n30_n0"></tag>人，占比<tag id="ur_n30_n0"></tag></div></td></tr>
					<tr><td><div class="sumData">-0%&lt;n≤30%            用户数：<tag id="u_p0_p30"></tag>人，占比<tag id="ur_p0_p30"></tag></div></td></tr>
					<tr><td><div class="sumData">30%&lt;n≤50%            用户数：<tag id="u_p30_p50"></tag>人，占比<tag id="ur_p30_p50"></tag></div></td></tr>
					<tr><td><div class="sumData">50%&lt;n≤80%            用户数：<tag id="u_p50_p80"></tag>人，占比<tag id="ur_p50_p80"></tag></div></td></tr>
					<tr><td><div class="sumData">80%&lt;n≤100%         用户数：<tag id="u_p80_p100"></tag>人，占比<tag id="ur_p80_p100"></tag></div></td></tr>
					<tr><td><div class="sumData">100%&lt;n≤150%    用户数：<tag id="u_p100_p150"></tag>人，占比<tag id="ur_p100_p150"></tag></div></td></tr>
					<tr><td><div class="sumData">150%&lt;n≤200%    用户数：<tag id="u_p150_p200"></tag>人，占比<tag id="ur_p150_p200"></tag></div></td></tr>
					<tr><td><div class="sumData">200%&lt;n≤300%    用户数：<tag id="u_p200_p300"></tag>人，占比<tag id="ur_p200_p300"></tag></div></td></tr>
					<tr><td><div class="sumData">n>300%                         用户数：<tag id="u_p300"></tag>人，占比<tag id="ur_p300"></tag></div></td></tr>
				</table>
		     </div>
	  	 	
		</div>	
		
		<div id="tabs2" class="mini-tabs" activeIndex="0" style="width:100%;height:50%;margin-top:10px" contextMenu="#tabsMenu">
		    <div name="first" title="投注金额">
				<table>
					<tr><td rowspan="13"><div id="betMoneyChart" style="width: 900px; height: 350px;"></div></td></tr>
					<tr><td><div class="sumData">n&lt;=-30%    	     投注额：<tag id="m_n30"></tag>元，占比<tag id="mr_n30"></tag></div></td></tr>
					<tr><td><div class="sumData">-30%&lt;n≤0%            投注额：<tag id="m_n30_n0"></tag>元，占比<tag id="mr_n30_n0"></tag></div></td></tr>
					<tr><td><div class="sumData">-0%&lt;n≤30%            投注额：<tag id="m_p0_p30"></tag>元，占比<tag id="mr_p0_p30"></tag></div></td></tr>
					<tr><td><div class="sumData">30%&lt;n≤50%            投注额：<tag id="m_p30_p50"></tag>元，占比<tag id="mr_p30_p50"></tag></div></td></tr>
					<tr><td><div class="sumData">50%&lt;n≤80%            投注额：<tag id="m_p50_p80"></tag>元，占比<tag id="mr_p50_p80"></tag></div></td></tr>
					<tr><td><div class="sumData">80%&lt;n≤100%         投注额：<tag id="m_p80_p100"></tag>元，占比<tag id="mr_p80_p100"></tag></div></td></tr>
					<tr><td><div class="sumData">100%&lt;n≤150%      投注额：<tag id="m_p100_p150"></tag>元，占比<tag id="mr_p100_p150"></tag></div></td></tr>
					<tr><td><div class="sumData">150%&lt;n≤200%      投注额：<tag id="m_p150_p200"></tag>元，占比<tag id="mr_p150_p200"></tag></div></td></tr>
					<tr><td><div class="sumData">200%&lt;n≤300%      投注额：<tag id="m_p200_p300"></tag>元，占比<tag id="mr_p200_p300"></tag></div></td></tr>
					<tr><td><div class="sumData">n>300%                       投注额：<tag id="m_p300"></tag>元，占比<tag id="mr_p300"></tag></div></td></tr>
				</table>
		     </div>
		</div>	
		
	</div>
	
	
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/echarts.min.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/report/earnRate.js?version=<%=version%><%=System.currentTimeMillis() %>" type="text/javascript"></script>
</html>
