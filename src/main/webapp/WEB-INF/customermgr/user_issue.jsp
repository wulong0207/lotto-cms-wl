<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>推单用户</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="user_issue.toVisitLevel">设置专家</a>
	        </btn:operate>
	        <btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="user_issue.visitStatus(1)">启用</a>
	        </btn:operate>
	        <btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="user_issue.visitStatus(0)">禁用</a>
	        </btn:operate>
	        <btn:operate privilege="EXPORT">
		        <a class="mini-button" iconCls="icon-download" plain="true" onclick="user_issue.excel">导出Excel</a>
	        </btn:operate>
			</td>
			</tr>
			<tr>
				<td>
				<input id="userType" name="userType" class="mini-combobox" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
				 <input name="userTypeValue" class="mini-textbox" style="width:200px;"/>
				 推单状态<input id="status" name="status" class="mini-combobox" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
				彩种类型<input id="lotteryCategory"  name="lotteryCategory" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种类型" allowInput="true" valueFromSelect ="true"  oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="user_issue.lotteryCategoryChange"/> 
				         彩种<input id="lotteryCode" name="lotteryCode" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种" allowInput="true" valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="user_issue.lotteryCodeChange"/> 
				   标识    <input id="level" name="level" class="mini-combobox" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>  
				<input id="timeType"  name="timeType" class="mini-combobox"  style="width:150px;" showClose="true" oncloseclick="Cms.onCloseClick"/>
					<input name="startTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
						   showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input name="endTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>  
				<a class="mini-button" onclick="user_issue.search" >查询</a>
					
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
		 url="customermgr/issue/page" idField="id" allowResize="true" 
		 showColumnsMenu="true" showFilterRow="false" onrowdblclick="user_issue.toVisitLevel">
		<div property="columns">
			<div type="checkcolumn" width="5%"></div>
			<div type="indexcolumn" width="5%" align="center" headerAlign="center">序号</div>
			<div field="accountName" headerAlign="center" align="center">会员帐号</div>
			<div field="level" type="comboboxcolumn" headerAlign="center" align="center">标识
		     <input property="editor" class="mini-combobox" data="Dic.level" />
			</div>
			<%--<div field="redCode" headerAlign="center" align="center">最佳战绩</div>--%>
			<div headerAlign="center" align="center" renderer="user_issue.renderRecentRecord">近期战绩</div>
			<div field="hitRate" headerAlign="center" align="center">命中率</div>
			<div field="profitRate" headerAlign="center" align="center">盈利率</div>
			<div field="issueNum" headerAlign="center" align="center">发单次数</div>
			<div field="issueAmount" headerAlign="center" align="center">发单金额</div>
			<div field="hitNum" headerAlign="center" align="center">命中次数</div>
			<div field="hitMoney" headerAlign="center" align="center">命中金额</div>
			<div field="winAmount" headerAlign="center" align="center">发单总奖金</div>
			<div field="followNum" headerAlign="center" align="center">跟单人数</div>
			<div field="followAmount" headerAlign="center" align="center">跟单金额</div>
			<div field="commissionAmount" headerAlign="center" align="center">总佣金</div>
			<div field="status" type="comboboxcolumn" headerAlign="center" align="center">状态
		     <input property="editor" class="mini-combobox" data="Dic.status" />
			</div>
			<div field="createTime" width="12%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>	
			<div field="remark" headerAlign="center" align="center">备注</div>
		</div>
	</div>
	
		<div id="visitLevelWindow" title="设置专家" class="mini-window" style="width:400px;height:300px" showFooter="true" showModal="true" allowResize="true" allowDrag="true" showCloseButton="true">
	    <div id="visitLevelForm" class="form">
	    <%-- findOneIssueLevel方法返回结果后，记录查询到的id --%>
	    <input name="id" id="id" class="mini-hidden"/>

是否将选中的用户：<span id="accountName2"></span>   设置为
<div id="level2" class="mini-radiobuttonlist" repeatItems="2" repeatDirection="vertical"
    textField="text" valueField="id">
</div> 

<br/>
	    
	    彩种类型<input id="lotteryCategory2"  name="lotteryCategory" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种类型" allowInput="true" valueFromSelect ="true"  oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="user_issue.lotteryCategoryChange2"/> 
				         彩种<input id="lotteryCode2" name="lotteryCode" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种" allowInput="true" valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="user_issue.lotteryCodeChange2"/> 
				   

<a class="mini-button" onclick="user_issue.visitLevel">确定</a>
<a class="mini-button" onclick="user_issue.hideWindow">取消</a>
	    </div>
	    
	</div>
	
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/customermgr/user_issue.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
