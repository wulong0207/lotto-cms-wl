<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/tickermgr/monitorcommon.css">
    <title>出票监控</title>
  </head>
<body >    
<!-- <input type="button" value="获取数据" onclick="Current.getMonitorData()"/> 
彩种编号<input id="lotteryCode" class="mini-textbox"/>
<textarea id= "content" width ="800" height="500" class="mini-textarea"></textarea>  -->
	<div class="lotM-body">
		<div class="lotM-header">
			<h1>彩票监控</h1>
			<ul class="lotM-headlist">
				<li>彩种分类
					 <select id="lotteryCategory" name="lotteryCategory" onchange="Current.changeVal()">
						<option value="-1">全部</option>
					</select>
				</li>
				<li><label for="totop8"><input type="checkbox" id="totop8" />按照官方截止时间排序</label></li>
				<li><label for="stopChange"><input type="checkbox" id="stopChange" />停止方案和出票商切换</label></li>
				<li><span class="account-money">保底帐号余额：<em>1,234,567.00 元</em></span></li>
			</ul>
			<div class="now-time">现在时间：<em></em></div>
		</div>
		<div class="lotM-main">
			<table id="tableList" class="lotM-table">
				<thead>
					<tr>
						<th width=8%>彩种名称/彩期</th>
						<th width=8%>截止时间</th>
						<th width=8%>未出票总金额</th>
						<th width=8%>截止未出票数</th>
						<th width=8%>未送票</th>
						<th width=8%>已送票</th>
						<th width=8%>出票失败</th>
						<th width=425 class="cps">
							<span>出票商名称-权重</span>
							<span>已送票数</span>
							<span>已送票金额</span>
							<span>最早送票时间</span>
						</th>
						<th width=425 class="cps">
							<span>出票商名称-权重</span>
							<span>已送票数</span>
							<span>已送票金额</span>
							<span>最早送票时间</span>
						</th>
						<th width=170 class="stop-plan">未拆票方案</th>
						<th width=170 class="stop-plan">未拆票金额</th>
						<th width=170 class="stop-plan">合买金额：80%-90%</th>
						<th width=170 class="stop-plan">合买金额：70%-80%</th>
						<th width=170 class="stop-plan">合买金额：70%以下</th>
					</tr>
				</thead>
				<tbody id="ticketDetail">
				</tbody>
			</table>
		</div>
		<div class="lotM-footer">
			<a class="btn-red">报警查看</a>
			<div class="fail-tip">
			<marquee id="alramInfoTag">
			<span></span>
			<%--
			<span>1. xx彩种xx渠道送票失败</span><span>2. xx彩种xx渠道送票失败</span><span>3. xx彩种xx渠道送票失败</span>
			--%>
			</marquee>
			</div>
		</div>
	</div>

</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/ticketmgr/monitor_common.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
