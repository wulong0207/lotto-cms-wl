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
	<title>充值数据</title>
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
								 valueFromSelect="false" style="width: 200px;" onvaluechanged="rechargeReport.clearActive('statis-channel')"
							        textField="text" valueField="id" parentField="pid" allowInput="true" showRadioButton="true" showFolderCheckBox="true"/>

					</td>
			</tr>
			<tr>
				<td width="20%" id="statis-time">
					投注时间：
					<a data-time="1"  class="active">昨天</a>|
					<a data-time="2">今天</a>|
					<a data-time="3">上周</a>|
					<a data-time="4">本周</a>|
					<a data-time="5">上月</a>|
					<a data-time="6">本月</a>|
				</td>
				<td>选择查询时间：
				<input style="width:200px;" id="startTime" name="startTime" class="mini-datepicker" format="yyyy-MM-dd" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="rechargeReport.clearActive('statis-time')"/>
			        到
			    <input style="width:200px;" id="endTime" name="endTime" class="mini-datepicker"  format="yyyy-MM-dd" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="rechargeReport.clearActive('statis-time')"/>
				</td>
			</tr>
			<tr>
				<td width="20%" id="statis-paychannel">
					充值渠道：
					<a class="active">所有</a>
					<a data-paychannel="1">支付宝</a>
					<a data-paychannel="2">微信</a>
					<a data-paychannel="3">连连</a>
				</td>
				<td>
				可选充值渠道：<input id="paychannel" class="mini-combobox" emptyText="请选择" allowInput="true" showClose="true" onCloseClick="rechargeReport.comboxOnCloseClick" onvaluechanged="rechargeReport.clearActive('statis-paychannel')"/>
					    <button  id="searchBtn">查询</button>
				<td>
			</tr>
		</table>
	</div>
	<div class="mini-panel" title="数据概况 :" style="width:100%;height:180px;margin-top:10px" showCollapseButton="true">
			<table style="width: 100%; height: 100%;" cellpadding="2"
				cellspacing="0" border="0" bordercolor="#000000" align="center" style="background-color:rgba(242, 242, 242, 1)">
				<tbody>
					<tr style="width: 100%;font-size:14px">
						<td
							style="width: 25%; padding-left: 8%; border-right: 1px solid #D1D1D1;">
							<span style="font-weight:bold;">充值人数：</span></br> <span id="fillUser" style="font-weight:bold;color:red;"></span></br> <span></span></br> <span>首充用户：<tag id="newFillUser"></tag>人</span></br>
							<span>首充用户占比：<tag id="newPersonRate"></tag></span></br> <span></span></br> <span></span>
						</td>
						<td
							style="width: 25%; padding-left: 8%; border-right: 1px solid #D1D1D1;">
							<span style="font-weight:bold;">充值次数：</span></br> <span id="fillTimes" style="font-weight:bold;color:red;"></span></br> <span></span></br> <span>首充用户：<tag id="newFillTimes"></tag>次</span></br>
							<span>充值活跃度：<tag id="liveness"></tag>次/人</span></br> 
						</td>
						<td
							style="width: 25%; padding-left: 8%; border-right: 1px solid #D1D1D1;">
							<span style="font-weight:bold;">充值金额：</span></br> <span id="fillMoney" style="font-weight:bold;color:red;"></span></br> <span></span></br> <span>首充金额：<tag id="newFillMoney"></tag>元</span></br>
							<span>首充金额占比：<tag id="newMoneyRate"></tag></span></br> 
						</td>
						<td style="width: 25%; padding-left: 8%;"><span style="font-weight:bold;">人均充值：</span></br>
							<span id="perRecharge" style="font-weight:bold;color:red;">元/人</span></br> <span></span></br> <span>首充用户：<tag id="newPerRecharge"></tag>元/人</span></br> 
					</tr>
				</tbody>
			</table>
   </div>
	
	<div class="mini-panel" title="明细数据 :" style="width:100%;height:550px;margin-top:10px" showCollapseButton="true">
		<div>
			<label><input name="chartDetailData" type="radio" value="1" checked="checked" />图表数据</label> 
			<label><input name="chartDetailData" type="radio" value="2" />明细数据</label>
			<button id="exportBtn" data-excel-url="report/recharge/exportExcel">导出</button>
			<label>(说明:n-m表示n&lt;查询数据&lt;=m)</label>
		</div>
		
		<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:90%;margin-top:10px" contextMenu="#tabsMenu">
		    <div name="first" title="充值人数">
				<div size="100%" showCollapseButton="true" id="rechargePersonNumTable" style="display:none">
						<div id="datagrid_person" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
							url="report/recharge/rechargePersonList" idField="id"  
							pageSize="30" multiSelect="true" showColumnsMenu="true" showPagerButtonText="true" showPagerButtonIcon="true"
							showFilterRow="false" allowMoveColumn="true"
							onpreload="MiniCom.onpreload"
							showSummaryRow="true">
							<div property="columns">
								<div field="dt" headerAlign="center" align="center" dateFormat="yyyy-MM-dd">日期</div>
								<div field="fillUser" headerAlign="center" align="center">充值总人数</div>
								<div field="u0_50" headerAlign="center" align="center">0-50元</div>
								<div field="u51_100" headerAlign="center" align="center">50-100元</div>
								<div field="u101_500" headerAlign="center" align="center">100-500元</div>
								<div field="u501_1000" headerAlign="center" align="center">500-1000元</div>
								<div field="u1001_5000" headerAlign="center" align="center">1000-5000元</div>
								<div field="u5001_10000" headerAlign="center" align="center">5000-10000元</div>
								<div field="u10001_50000" headerAlign="center" align="center">1万-5万元</div>
								<div field="u50001_100000" headerAlign="center" align="center">5万-10万元</div>
								<div field="u100000" headerAlign="center" align="center">10万元以上</div>
							</div>
						</div>
				</div>
				<table>
					<tr><td rowspan="10"><div id="rechargePersonNumChart" style="width: 900px; height: 350px;"></div></td></tr>
					<tr><td><div class="sumData">0-50元     充值人数：<tag id="u0_50"></tag>人，占比<tag id="u0_50_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">50-100元     充值人数：<tag id="u51_100"></tag>人，占比<tag id="u51_100_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">100-500元     充值人数：<tag id="u101_500"></tag>人，占比<tag id="u101_500_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">500-1000元     充值人数：<tag id="u501_1000"></tag>人，占比<tag id="u501_1000_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">1000-5000元     充值人数：<tag id="u1001_5000"></tag>人，占比<tag id="u1001_5000_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">5000-10000元     充值人数：<tag id="u5001_10000"></tag>人，占比<tag id="u5001_10000_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">10000-50000元     充值人数：<tag id="u10001_50000"></tag>人，占比<tag id="u10001_50000_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">50000-100000元     充值人数：<tag id="u50001_100000"></tag>人，占比<tag id="u50001_100000_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">100000+元     充值人数：<tag id="u100000"></tag>人，占比<tag id="u100000_ratio"></tag>%</div></td></tr>
				</table>
		    </div>
		  
		    <div name="first" title="充值金额">
				<div size="100%" showCollapseButton="true" id="rechargeMoneyNumTable" style="display:none">
						<div id="datagrid_money" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
							url="report/recharge/rechargeMoneyList" idField="id1"
							pageSize="30" multiSelect="true" showColumnsMenu="true" showPagerButtonText="true" showPagerButtonIcon="true"
							showFilterRow="false" allowMoveColumn="true"
							onpreload="MiniCom.onpreload"
							showSummaryRow="true">
							<div property="columns">
								<div field="dt" headerAlign="center" align="center" dateFormat="yyyy-MM-dd">日期</div>
								<div field="fillMoney" headerAlign="center" align="center">充值金额</div>
								<div field="m0_50" headerAlign="center" align="center">0-50元</div>
								<div field="m51_100" headerAlign="center" align="center">50-100元</div>
								<div field="m101_500" headerAlign="center" align="center">100-500元</div>
								<div field="m501_1000" headerAlign="center" align="center">500-1000元</div>
								<div field="m1001_5000" headerAlign="center" align="center">1000-5000元</div>
								<div field="m5001_10000" headerAlign="center" align="center">5000-10000元</div>
								<div field="m10001_50000" headerAlign="center" align="center">1万-5万元</div>
								<div field="m50001_100000" headerAlign="center" align="center">5万-10万元</div>
								<div field="m100000" headerAlign="center" align="center">10万元以上</div>
							</div>
						</div>
				</div>
				<table>
					<tr><td rowspan="10"><div id="rechargeMoneyNumChart" style="width: 900px; height: 350px;"></div></td></tr>
					<tr><td><div class="sumData">0-50元     充值金额：<tag id="m0_50"></tag>元，占比<tag id="m0_50_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">50-100元     充值金额：<tag id="m51_100"></tag>元，占比<tag id="m51_100_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">100-500元     充值金额：<tag id="m101_500"></tag>元，占比<tag id="m101_500_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">500-1000元     充值金额：<tag id="m501_1000"></tag>元，占比<tag id="m501_1000_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">1000-5000元     充值金额：<tag id="m1001_5000"></tag>元，占比<tag id="m1001_5000_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">5000-10000元     充值金额：<tag id="m5001_10000"></tag>元，占比<tag id="m5001_10000_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">10000-50000元     充值金额：<tag id="m10001_50000"></tag>元，占比<tag id="m10001_50000_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">50000-100000元     充值金额：<tag id="m50001_100000"></tag>元，占比<tag id="m50001_100000_ratio"></tag>%</div></td></tr>
					<tr><td><div class="sumData">100000+元     充值金额：<tag id="m100000"></tag>元，占比<tag id="m100000_ratio"></tag>%</div></td></tr>
				</table>
		    </div>
	
		    <div name="third" title="充值方式">
					<div id="rechargeStyleTable" size="100%" showCollapseButton="true" style="display:none;">
						<div id="datagrid_style" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
							url="report/recharge/rechargeStyleList" idField="id2"
							pageSize="30" multiSelect="true" showColumnsMenu="true" showPagerButtonText="true" showPagerButtonIcon="true"
							showFilterRow="false" allowMoveColumn="true"
							onpreload="MiniCom.onpreload"
							showSummaryRow="true">
							<div property="columns">
								<div field="dt" headerAlign="center" align="center" dateFormat="yyyy-MM-dd">日期</div>
								<div header="网银支付" headerAlign="center" align="center">
									<div property="columns">
										<div field=bankUser headerAlign="center" align="center">充值人数</div>
										<div field="bankMoney" headerAlign="center" align="center">充值金额</div>
									</div>
								</div>
								<div header="快捷支付" headerAlign="center" align="center">
									<div property="columns">
										<div field="fastUser" headerAlign="center" align="center">充值人数</div>
										<div field="fastMoney" headerAlign="center" align="center">充值金额</div>
									</div>
								</div>
								<div header="第三方支付" headerAlign="center" align="center">
									<div property="columns">
										<div field="otherUser" headerAlign="center" align="center">充值人数</div>
										<div field="otherMoney" headerAlign="center" align="center">充值金额</div>
									</div>
								</div>
								<div header="支付宝" headerAlign="center" align="center">
									<div property="columns">
										<div field="alipayUser" headerAlign="center" align="center">充值人数</div>
										<div field="alipayMoney" headerAlign="center" align="center">充值金额</div>
									</div>
								</div>
								
								<div header="微信" headerAlign="center" align="center">
									<div property="columns">
										<div field="wxpayUser" headerAlign="center" align="center">充值人数</div>
										<div field="wxpayMoney" headerAlign="center" align="center">充值金额</div>
									</div>
								</div>
								<div header="QQ" headerAlign="center" align="center">
									<div property="columns">
										<div field="qqUser" headerAlign="center" align="center">充值人数</div>
										<div field="qqMoney" headerAlign="center" align="center">充值金额</div>
									</div>
								</div>
								<div header="充值卡" headerAlign="center" align="center">
									<div property="columns">
										<div field="cardUser" headerAlign="center" align="center">充值人数</div>
										<div field="cardMoney" headerAlign="center" align="center">充值金额</div>
									</div>
								</div>
								<div header="人工充值" headerAlign="center" align="center">
									<div property="columns">
										<div field="handUser" headerAlign="center" align="center">充值人数</div>
										<div field="handMoney" headerAlign="center" align="center">充值金额</div>
									</div>
								</div>
								<div header="代理充值" headerAlign="center" align="center">
									<div property="columns">
										<div field="agentUser" headerAlign="center" align="center">充值人数</div>
										<div field="agentMoney" headerAlign="center" align="center">充值金额</div>
									</div>
								</div>
								<div header="京东支付" headerAlign="center" align="center">
									<div property="columns">
										<div field="jdUser" headerAlign="center" align="center">充值人数</div>
										<div field="jdMoney" headerAlign="center" align="center">充值金额</div>
									</div>
								</div>

							</div>
						</div>
				</div>
				<div id="rechargeStyleChart">
					<div id="personNumChart" style="float: left; width: 700px; height: 300px;"></div>
					<div id="moneyNumChart" style="float: left; width: 700px; height: 300px;"></div>
				</div>
		</div>	
		
	    <div name="four" title="成功与失败">
			<div id="rechargeStatusTable" size="100%" showCollapseButton="true" style="display:none;">
				<div id="datagrid_status" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
					url="report/recharge/rechargeStatusList" idField="id3"
					pageSize="30" multiSelect="true" showColumnsMenu="true" showPagerButtonText="true" showPagerButtonIcon="true"
					showFilterRow="false" allowMoveColumn="true"
					onpreload="MiniCom.onpreload"
					showSummaryRow="true">
					<div property="columns">
						<div field="dt" headerAlign="center" align="center" width="10%" dateFormat="yyyy-MM-dd">日期</div>
						<div header="网银" headerAlign="center" align="center">
							<div property="columns">
								<div field="bankSuccess" headerAlign="center" align="center" >成功笔数</div>
								<div field="bankFailThird" headerAlign="center" align="center" >失败笔数(第三方返回)</div>
								<div field="bankFailUser" headerAlign="center" align="center" >失败笔数(用户原因)</div>
							</div>
						</div>
						<div header="快捷" headerAlign="center" align="center">
							<div property="columns">
								<div field="fastSuccess" headerAlign="center" align="center" >成功笔数</div>
								<div field="fastFailThird" headerAlign="center" align="center">失败笔数(第三方返回)</div>
								<div field="fastFailUser" headerAlign="center" align="center" >失败笔数(用户原因)</div>
							</div>
						</div>
						<div header="第三方" headerAlign="center" align="center">
							<div property="columns">
								<div field="otherSuccess" headerAlign="center" align="center" >成功笔数</div>
								<div field="otherFailThird" headerAlign="center" align="center" >失败笔数(第三方返回)</div>
								<div field="otherFailUser" headerAlign="center" align="center" >失败笔数(用户原因)</div>
							</div>
						</div>
						<div header="支付宝" headerAlign="center" align="center">
							<div property="columns">
								<div field="alipaySuccess" headerAlign="center" align="center" >成功笔数</div>
								<div field="alipayFailThird" headerAlign="center" align="center" >失败笔数(第三方返回)</div>
								<div field="alipayFailUser" headerAlign="center" align="center" >失败笔数(用户原因)</div>
							</div>
						</div>
						<div header="微信" headerAlign="center" align="center">
							<div property="columns">
								<div field="wxpaySuccess" headerAlign="center" align="center" >成功笔数</div>
								<div field="wxpayFailThird" headerAlign="center" align="center" >失败笔数(第三方返回)</div>
								<div field="wxpayFailUser" headerAlign="center" align="center" >失败笔数(用户原因)</div>
							</div>
						</div>
						<div header="QQ" headerAlign="center" align="center">
							<div property="columns">
								<div field="qqSuccess" headerAlign="center" align="center" >成功笔数</div>
								<div field="qqFailThird" headerAlign="center" align="center" >失败笔数(第三方返回)</div>
								<div field="qqFailUser" headerAlign="center" align="center" >失败笔数(用户原因)</div>
							</div>
						</div>
						<div header="充值卡" headerAlign="center" align="center">
							<div property="columns">
								<div field="cardSuccess" headerAlign="center" align="center" >成功笔数</div>
								<div field="cardFailThird" headerAlign="center" align="center" >失败笔数(第三方返回)</div>
								<div field="cardFailUser" headerAlign="center" align="center" >失败笔数(用户原因)</div>
							</div>
						</div>
						<div header="人工充值" headerAlign="center" align="center">
							<div property="columns">
								<div field="handSuccess" headerAlign="center" align="center" >成功笔数</div>
								<div field="handFailThird" headerAlign="center" align="center" >失败笔数(第三方返回)</div>
								<div field="handFailUser" headerAlign="center" align="center" >失败笔数(用户原因)</div>
							</div>
						</div>
						<div header="代理充值" headerAlign="center" align="center">
							<div property="columns">
								<div field="agentSuccess" headerAlign="center" align="center" >成功笔数</div>
								<div field="agentFailThird" headerAlign="center" align="center" >失败笔数(第三方返回)</div>
								<div field="agentFailUser" headerAlign="center" align="center" >失败笔数(用户原因)</div>
							</div>
						</div>
						<div header="京东支付" headerAlign="center" align="center">
							<div property="columns">
								<div field="jdSuccess" headerAlign="center" align="center" >成功笔数</div>
								<div field="jdFailThird" headerAlign="center" align="center">失败笔数(第三方返回)</div>
								<div field="jdFailUser" headerAlign="center" align="center">失败笔数(用户原因)</div>
							</div>
						</div>

					</div>
				</div>
	  	  	</div>
	  	  <div id="rechargeStatusChart" style="float: left; width: 900px; height: 300px;"></div>
		</div>	
		
	</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/echarts.min.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/report/recharge.js?version=<%=version%><%=System.currentTimeMillis() %>" type="text/javascript"></script>
</html>
