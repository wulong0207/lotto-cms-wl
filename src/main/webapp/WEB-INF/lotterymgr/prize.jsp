<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
	<title>派奖管理</title>
</head>
<body>
	<div id="searchForm" class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
				<td>
			        <btn:operate privilege="EXPORT">
			        <a class="mini-button" iconCls="icon-download" plain="true" onclick="PrizeObj.excel()">导出</a>
			        </btn:operate>
			        <btn:operate privilege="DISPATCH">
			        <a class="mini-button" iconCls="icon-ok" plain="true" onclick="PrizeObj.sendPrize()">派奖</a>
			        </btn:operate> 
				</td>
			</tr>
			<tr>
			   <td>
				  彩种类型：<input id="lotteryCategory" class="mini-combobox" style="width:150px;"
					   emptyText="请选择彩种类型" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="PrizeObj.closeLotCategory" onvaluechanged="PrizeObj.changeLotCategory"/> 
				         彩种：<input id="lotteryCode" class="mini-combobox" style="width:150px;"
					   emptyText="请选择彩种" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="PrizeObj.closeLotCode" required="true" onvaluechanged="PrizeObj.changeLotCode"/> 
				         彩期：<input id="lotteryIssue" class="mini-combobox" style="width:150px;"
					   emptyText="请选择或输入" allowInput="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
				        竞彩编号：<input id="buyScreen" class="mini-textbox" style="width:120px;"/>
				        税前金额：<input id="minBonus" class="mini-textbox" style="width:120px;" vtype="float"/> 至 <input id="maxBonus" class="mini-textbox" style="width:120px;" vtype="float"/>
				</td>
			</tr>
			<tr>
			   <td>
			            方案编号：<input id="orderCode" class="mini-textbox" style="width:100px;"/> 
			      <input id="userType" class="mini-combobox" style="width:120px;" allowInput="true" valueFromSelect="true"/>
				  <input id="userTypeVal" class="mini-textbox" style="width:120px;"/>
				  <input id="timeType" class="mini-combobox" style="width:100px;"/>
				  <input id="startTime" class="mini-datepicker" allowInput="false" style="width:180px;" 
				     format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick"/>
				        到<input id="endTime" class="mini-datepicker" allowInput="false" style="width:180px;"
				     format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick"/> 
				  购买类型：<input id="buyType" class="mini-combobox" style="width:120px;" emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" />
				    奖项：<input id="winGrade" class="mini-combobox" style="width:120px;" emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
				    中奖状态：<input id="winningStatus" name="winningStatus" class="mini-combobox"  style="width:120px;"emptyText="请选择"    allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true"/>
				    <a class="mini-button" onclick="PrizeObj.search()">查询</a>
			   </td>
			</tr>
		</table>
	</div>

	<div class="mini-splitter" vertical="true" style="width:100%;height:90%;" style="border:0;">
		<div size="100%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				url="lotterymgr/prize/list" idField="id" allowResize="true" pageSize="30" multiSelect="true"
				showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" onrowdblclick="PrizeObj.onrowdblclick"
				showSummaryRow="true" ondrawsummarycell="PrizeObj.drawSummaryCell"
				onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn"></div>
					<div type="indexcolumn" align="center" headerAlign="center">序号</div>
					<div field="lotteryCode" type="comboboxcolumn" headerAlign="center" align="center">彩种名称
					     <input property="editor" class="mini-combobox" data="Dic.allCode" />
					</div>
					<div field="lotteryIssue" headerAlign="center" align="center">彩期编号</div>
					<div field="nickName" headerAlign="center" align="center">用户昵称</div>
					<div field="buyType" type="comboboxcolumn" headerAlign="center" align="center">购买类型
					     <input property="editor" class="mini-combobox" data="Dic.buyType" />
					</div>
					<div field="orderCode" headerAlign="center" align="center">方案编号</div>
					<div field="winningStatus"  type= "comboboxcolumn" headerAlign="center" align="center">中奖状态
					     <input property="editor" class="mini-combobox" data="Dic.winningStatus" />
					</div>
					<div field="orderAmount" headerAlign="center" align="center" dataType="currency" allowSort="true" sortField="order_amount" showSortIcon="true" >方案金额(排)</div>
					<div field="preBonus" headerAlign="center" align="center" dataType="currency" allowSort="true" sortField="pre_bonus" showSortIcon="true" >税前金额(排)</div>
					<div field="aftBonus" headerAlign="center" align="center" dataType="currency" allowSort="true" sortField="aft_bonus" showSortIcon="true" >税后金额(排)</div>
					<div field="sendBonus" headerAlign="center" align="center" dataType="currency" allowSort="true" sortField="aft_bonus" showSortIcon="true" >应派金额(排)</div>
					<div field="addedBonus" headerAlign="center" align="center" dataType="currency">官方加奖方案金额</div>
					<div field="websiteBonus" headerAlign="center" align="center" dataType="currency">本站加奖方案金额</div>
					<div field="lotteryTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">开奖时间</div>
					<div field="winningDetail" headerAlign="center" align="center" allowSort="true" sortField="winning_detail" showSortIcon="true" >中奖奖项(排)</div><!--renderer="PrizeObj.renderWinDetail"  -->
				</div>
				<div field="lotteryIssue" headerAlign="center" align="center">彩期编号</div>
				<div field="nickName" headerAlign="center" align="center">用户昵称</div>
				<div field="buyType" type="comboboxcolumn" headerAlign="center" align="center">购买类型
					<input property="editor" class="mini-combobox" data="Dic.buyType"/>
				</div>
				<div field="orderCode" headerAlign="center" align="center">方案编号</div>
				<div field="winningStatus" type="comboboxcolumn" headerAlign="center" align="center">中奖状态
					<input property="editor" class="mini-combobox" data="Dic.winningStatus"/>
				</div>
				<div field="orderAmount" headerAlign="center" align="center" dataType="currency" allowSort="true"
					 sortField="order_amount" showSortIcon="true">方案金额(排)
				</div>
				<div field="preBonus" headerAlign="center" align="center" dataType="currency" allowSort="true"
					 sortField="pre_bonus" showSortIcon="true">税前金额(排)
				</div>
				<div field="aftBonus" headerAlign="center" align="center" dataType="currency" allowSort="true"
					 sortField="aft_bonus" showSortIcon="true">税后金额(排)
				</div>
				<div field="sendBonus" headerAlign="center" align="center" dataType="currency" allowSort="true"
					 sortField="aft_bonus" showSortIcon="true">应派金额(排)
				</div>
				<div field="addedBonus" headerAlign="center" align="center" dataType="currency">官方加奖方案金额</div>
				<div field="websiteBonus" headerAlign="center" align="center" dataType="currency">本站加奖方案金额</div>
				<div field="lotteryTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">开奖时间</div>
				<div field="winningDetail" headerAlign="center" align="center" allowSort="true"
					 sortField="winning_detail" showSortIcon="true">中奖奖项(排)
				</div><!--renderer="PrizeObj.renderWinDetail"  -->
			</div>
		</div>
	</div>
</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/lotterymgr/prize.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>