<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>合买用户</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="conditionForm">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
			        <btn:operate privilege="UPD">
			        	<a class="mini-button" id="setUserBtn" iconCls="icon-goto" plain="true" onclick="Current.setUser()">设置用户</a>
				        <a class="mini-button" id="recommendBtn" iconCls="icon-edit" plain="true" onclick="Current.visitRecommend(1)">推荐</a>
				        <a class="mini-button" id="cancelRecommendBtn" iconCls="icon-edit" plain="true" onclick="Current.visitRecommend(0)">取消推荐</a>
			        </btn:operate>
			        <btn:operate privilege="EXPORT">
				      <button  class="mini-button" iconCls="icon-download" plain="true" onclick="Current.excel()">导出Excel</button>
				</btn:operate>
				</td>
			</tr>
			<tr>
				<td width="5%" style="white-space:nowrap;">
						<input id="userType" name="userType"  class="mini-combobox"  style="width:120px;" allowInput="true" valueFromSelect = "true"/>
				  		<input id="userTypeVal" name="userTypeVal" class="mini-textbox" style="width:135px;"/>
						合买大厅显示：<input id="recommendStatus" name="isRecommend"  class="mini-combobox"  style="width:120px;" emptyText="请选择" 
						valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true"/>
	     			<a class="mini-button" onclick="Current.search()" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" allowAlternating="true" style="width:100%;height:100%;" 
		 url="groupUser/list" idField="id" allowResize="true" pageSize="30" onrowclick="Current.dispalyBtn"
				showColumnsMenu="true" allowMoveColumn="true" showSummaryRow="true">
		<div property="columns">
			<div type="checkcolumn"></div>
			<div type="indexcolumn" align="center" headerAlign="center">序号</div>
			<div field="userAccount" headerAlign="center" align="center">会员账号</div>
			<div field="flag"  type= "comboboxcolumn" headerAlign="center" align="center">
				标识<input property="editor" class="mini-combobox" data="Dic.groupUserFlag" />
			</div>
			
			<div field="orderCount" headerAlign="center" align="center">发单次数</div>
			<div field="orderSucRate" headerAlign="center" align="center" numberFormat="p">发单成功率</div>
			<div field="winCount" headerAlign="center" align="center">累积中奖次数</div>
			<div field="winAmount" headerAlign="center" align="center">累积合买中奖金额</div>
			<div field="profitAmount" headerAlign="center" align="center">盈利金额</div>
			<div field="profitRate" headerAlign="center" align="center" numberFormat="p">盈利率</div>
			<div field="customizationCount" headerAlign="center" align="center">定制人数</div>
			<div field="isRecommend" type= "comboboxcolumn"   headerAlign="center" align="center">
				是否推荐合买大厅<input property="editor" class="mini-combobox" data="Dic.yesNoData" /> 
			</div>
			<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
		</div>
	</div>
	
	<div id="setWindow" showToolbar="true" class="mini-window" style="width:250px;height:120px;text-align:center" showFooter="true" showModal="true" allowResize="true" allowDrag="true" showCloseButton="true">
	     <div> 将选中的用户：<span id="userName"></span>  设置为<input name="id" id="id" class="mini-hidden"/></div>
	     <div id="groupUserFlag" class="mini-radiobuttonlist" repeatItems="2" repeatLayout="table"
		    textField="text" valueField="id">
	</div>
	
	 <div style="text-align:right;padding:2px;padding-right:15px;">
	 	<btn:operate privilege="UPD">
		        	<a class="mini-button" iconCls="icon-save"  onclick="Current.update">确定</a>
		        </btn:operate>
	   		</div>
	  
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/ordermgr/group_user.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
