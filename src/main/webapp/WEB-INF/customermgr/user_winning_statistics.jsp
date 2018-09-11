<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>用户中奖统计</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
				<td width="15%" nowrap="nowrap">
			        <btn:operate privilege="SEARCH">
				     	<a class="mini-button" iconCls="icon-upload" plain="true" onclick="Current.uploadSearch()">批量查询</a>
			     	</btn:operate>
			        <btn:operate privilege="EXPORT">
			        <a class="mini-button" iconCls="icon-download" plain="true" onclick="Current.excel()">导出</a>
			        </btn:operate>
				</td>
				<td width="5%" style="white-space:nowrap;">
				   <input id="attrType"  class="mini-combobox"  style="width:80px;" allowInput="true" valueFromSelect = "true"/>
				   <input id="attrContent" class="mini-textbox" style="width:200px;"/>
					<a class="mini-button" onclick="Current.search()">查询</a>
					<a class="mini-button" onclick="Current.countnew()">统计最新数据</a>
				</td>
				<td width="30%"></td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="customermgr/uws/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
				 onrowdblclick="Current.onrowdblclick" onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="accountName" headerAlign="center" align="center">会员账号</div>
					<div field="nickName"  headerAlign="center" align="center">昵称</div>
					<div field="lotteryName" headerAlign="center" align="center">彩种</div>
					<div field="score" headerAlign="center" align="center">战绩</div>
					<div header="代购" headerAlign="center" align="center">
					   <div property="columns">
                        	<div field="agentBuyMoney" headerAlign="center" align="center">购买总金额</div>
                        	<div field="agentBuyTimes" headerAlign="center" align="center">总次数</div>
							<div field="agentWinningMoney" headerAlign="center" align="center">中奖总金额</div>
							<div field="agentWinningTimes" headerAlign="center" align="center">中奖总次数</div>
                    	</div>					
					</div>
					<div header="发起合买" headerAlign="center" align="center">
					   <div property="columns">
                        	<div field="joinCreateMoney" headerAlign="center" align="center">发单总金额</div>
							<div field="joinCreateTimes" headerAlign="center" align="center">发单总次数</div>
							<div field="joinSuccessTimes" headerAlign="center" align="center">成功总次数</div>
							<div field="joinWinningMoney" headerAlign="center" align="center">中奖总金额</div>
							<div field="joinWinningTimes" headerAlign="center" align="center">中奖总次数</div>
							<div field="joinBuyMoney" headerAlign="center" align="center">实购总金额</div>
							<div field="joinIncomeMoney" headerAlign="center" align="center">实收入总金额</div>
							<div field="joinSuccessWinningTimes" headerAlign="center" align="center">成功中奖总次数</div>
                    	</div>					
					</div>
					<div header="跟单" headerAlign="center" align="center">
					   <div property="columns">
                        	<div field="followBuyMoney" headerAlign="center" align="center">跟单总金额</div>
							<div field="followBuyTimes" headerAlign="center" align="center">跟单总次数</div>
							<div field="followSuccessTimes" headerAlign="center" align="center">成功总次数</div>
							<div field="followWinningMoney" headerAlign="center" align="center">中奖总金额</div>
							<div field="followWinningTimes" headerAlign="center" align="center">中奖总次数</div>
							<div field="followIncomeMoney" headerAlign="center" align="center">实收总金额</div>
							<div field="followSuccessWinningTimes" headerAlign="center" align="center">成功中奖总次数</div>
                    	</div>					
					</div>
					<div header="追号" headerAlign="center" align="center">
					   <div property="columns">
                        	<div field="addBuyMoney" headerAlign="center" align="center">追号总金额</div>
							<div field="addBuyTimes" headerAlign="center" align="center">追号总次数</div>
							<div field="addWinningMoney" headerAlign="center" align="center">中奖总金额</div>
                    	</div>					
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
		<div id="upoladWindow" class="mini-window" title="批量查询" 
		style="width:400px; height:400px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
				
		  <div id="searchForm">
		        <table width="100%;">
               	 <!--  查询类型：<input id="uploadType"  class="mini-combobox"  style="width:80px;" allowInput="true" valueFromSelect = "true"/> -->
               	  <div id="beachType" name="type" class="mini-radiobuttonlist" value="account_name"   repeatLayout="table"
               	  data="[{id:'account_name',text:'会员帐户'},{id:'nick_name',text:'会员昵称'},{id:'cus_mobile',text:'手机号码'}]"> 
               	  </div>
               	  <br>
               	  <input id="beachContent" name="content" class="mini-textarea" style="width:100%;height: 200px;"/>
			      <a class="mini-button"   onclick="Current.batchSearch">查询</a>
			     <br>批量查询说明：<br>1.一行一个会员信息，不能有其它字符 ；
			     </table>
		  </div>
		</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/customermgr/user_winning_statistics.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
