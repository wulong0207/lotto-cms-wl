<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>大客户管理</title>
    <style>
	   	.mini-datepicker {
	   		width: 180px;
	   	}
    </style>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="ADD">
		        <a class="mini-button" iconCls="icon-add" plain="true" onclick="rebate_user.add()">新增</a>
	        </btn:operate>
				</td>
			</tr>
			<tr>
				<td>
					用户名<input id="accountNameSearch" name="accountName" class="mini-textbox"  style="width:150px;"/> 
					手机号码<input id="mobileSearch" name="mobile" class="mini-textbox"  style="width:150px;"/> 
					状态<input id="statusSearch" name="status" class="mini-combobox"  style="width:150px;" emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
					合作开始时间<input id="startTime" name="startTime" class="mini-datepicker" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" name="endTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>  
					<a class="mini-button" onclick=" Cms.search('datagrid','form1',['startTime','endTime'])" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:91%;" 
		 url="operatemgr/rebateuser/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
		 showColumnsMenu="true" onrowdblclick="rebate_user.edit" showFilterRow="false" onpreload="MiniCom.onpreload">
		<div property="columns">
			<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
			<div field="accountName" width="10%" headerAlign="center" align="center">用户名</div>
			<div field="mobile" width="9%" headerAlign="center" align="center">手机号</div>			
			<div field="status" width="6%" type="comboboxcolumn" headerAlign="center" align="center">状态
		     <input property="editor" class="mini-combobox" data="Dic.status" />
			</div>
			<div field="matchRuleType" width="3%" type="comboboxcolumn" headerAlign="center" align="center">竞彩
		     <input property="editor" class="mini-combobox" data="Dic.lotteryStatus" />
			</div>
			<div field="matchRulePro" width="4%" headerAlign="center" align="center">返佣</div>
			<div field="sportRuleType" width="4%" type="comboboxcolumn" headerAlign="center" align="center">竞技彩
		     <input property="editor" class="mini-combobox" data="Dic.lotteryStatus" />
			</div>
			<div field="sportRulePro" width="4%" headerAlign="center" align="center">返佣</div>
			<div field="highRuleType" width="4%" type="comboboxcolumn" headerAlign="center" align="center">高频彩
		     <input property="editor" class="mini-combobox" data="Dic.lotteryStatus" />
			</div>
			<div field="highRulePro" width="4%" headerAlign="center" align="center">返佣</div>
			<div field="numRuleType" width="4%" type="comboboxcolumn" headerAlign="center" align="center">数字彩
		     <input property="editor" class="mini-combobox" data="Dic.lotteryStatus" />
			</div>
			<div field="numRulePro" width="4%" headerAlign="center" align="center">返佣</div>
			
			<div field="rebateType" type="comboboxcolumn" width="6%" headerAlign="center" align="center">返佣周期
		     <input property="editor" class="mini-combobox" data="Dic.rebateType" />
			</div>
			<div field="sendAmount" width="10%" headerAlign="center" align="center">累积返佣</div>
			<div field="cooperateTime" width="11%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">合作开始时间</div>	
			<div field="createTime" width="11%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">最近修改时间</div>	
			<div field="createBy" width="8%" headerAlign="center" align="center">修改人</div>
			<div headerAlign="center" align="center" renderer="rebate_user.history" cellStyle="padding:0;">操作</div>
		</div>
	</div>
	
	<div id="findListWindow" title="查询历史信息" class="mini-window" style="width:1100px;height:85%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
		    <div id="datagridDetailList" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:95%" 
				url="operatemgr/rebateuser/listDetailInfo" idField="id" allowResize="true"  multiSelect="true" 
		  showFilterRow="false" showPager="false">
			<div property="columns">
			<div type="indexcolumn" width="2%" align="center" headerAlign="center">序号</div>
			<div field="accountName" width="12%" headerAlign="center" align="center">用户名</div>
			<div field="mobile" width="9%" headerAlign="center" align="center">手机号</div>			
			<div field="status" width="6%" type="comboboxcolumn" headerAlign="center" align="center">状态
		     <input property="editor" class="mini-combobox" data="Dic.status" />
			</div>
			<div field="matchRuleType" width="3%" type="comboboxcolumn" headerAlign="center" align="center">竞彩
		     <input property="editor" class="mini-combobox" data="Dic.lotteryStatus" />
			</div>
			<div field="matchRulePro" width="4%" headerAlign="center" align="center">返佣</div>
			<div field="sportRuleType" width="4%" type="comboboxcolumn" headerAlign="center" align="center">竞技彩
		     <input property="editor" class="mini-combobox" data="Dic.lotteryStatus" />
			</div>
			<div field="sportRulePro" width="4%" headerAlign="center" align="center">返佣</div>
			<div field="highRuleType" width="4%" type="comboboxcolumn" headerAlign="center" align="center">高频彩
		     <input property="editor" class="mini-combobox" data="Dic.lotteryStatus" />
			</div>
			<div field="highRulePro" width="4%" headerAlign="center" align="center">返佣</div>
			<div field="numRuleType" width="4%" type="comboboxcolumn" headerAlign="center" align="center">数字彩
		     <input property="editor" class="mini-combobox" data="Dic.lotteryStatus" />
			</div>
			<div field="numRulePro" width="4%" headerAlign="center" align="center">返佣</div>
			
			<div field="rebateType" type="comboboxcolumn" width="6%" headerAlign="center" align="center">返佣周期
		     <input property="editor" class="mini-combobox" data="Dic.rebateType" />
			</div>
			<div field="cooperateTime" width="11%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">合作开始时间</div>	
			<div field="createTime" width="11%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">最近修改时间</div>	
			<div field="createBy" width="8%" headerAlign="center" align="center">修改人</div>
		</div>
	</div>
	<div id="detailWindow" title="新增广告图详情" class="mini-window" style="width:800px;" showFooter="true" showModal="true" allowResize="true" allowDrag="true" onbuttonclick="rebate_user.clearData">
 		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
	    <btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="rebate_user.merge">保存</a>
			</btn:operate>
		</div>
	    <div id="detailForm" class="form">
	    	<input name="id"  class="mini-hidden"/>
	     	<input name="url"  class="mini-hidden"/>
			 	<input name="action"  class="mini-hidden"/>
			 	<input id="userId" name="userId"  class="mini-hidden"/>
			 	<input name="orderType"  value="1" class="mini-hidden"/>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <legend>大客户信息：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td>用户名</td>
	                <td><input id="accountName" name="accountName" class="mini-textbox" required="true"  onblur="rebate_user.findUserName()"/></td>
	                <td>真实姓名</td>
	                <td><input id="actualName"  name="actualName" class="mini-textbox"  enabled="false"/></td>
	            </tr>
	            <tr>
	                <td width="15%">竞彩</td>
	                <td width="5%">
						<div id="matchRuleType" name="matchRuleType"  class="mini-checkboxlist" textField="text" valueField="id" data="[{id:'1',text:'选择'}]" value="1" >
						</div>
	                </td>
	                <td>包含彩种: </td>
					<td>
						<div id="matchLimitLottery" name="matchLimitLottery" class="mini-combobox" style="width:200px;"  popupWidth="170px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true">     
						    <div property="columns">
						    </div>
						</div>
					</td>
	                <td>返佣百分比</td>
	                <td><input name="matchRulePro" id="matchRulePro" class="mini-spinner"  minValue="0" maxValue="15" decimalPlaces="1"/></td>
	            </tr>
	           <tr>
	                <td width="15%">竞技彩</td>
	                <td width="5%">
						<div id="sportRuleType" name="sportRuleType" class="mini-checkboxlist" textField="text" valueField="id" data="[{id:'1',text:'选择'}]" value="1">
						</div>
	                </td>
	                <td>包含彩种: </td>
					<td>
						<div id="sportLimitLottery" name="sportLimitLottery" class="mini-combobox" style="width:200px;"  popupWidth="170px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true">     
						    <div property="columns">
						    </div>
						</div>
					</td>
	                <td>返佣百分比</td>
	                <td><input name="sportRulePro" id="sportRulePro" class="mini-spinner"  minValue="0" maxValue="15" decimalPlaces="1"/></td>
	            </tr>
	            <tr>
	                <td width="15%">高频彩</td>
	                <td width="5%">
						<div id="highRuleType" name="highRuleType" class="mini-checkboxlist" textField="text" valueField="id" data="[{id:'1',text:'选择'}]" value="1" >
						</div>
	                </td>
	                <td>包含彩种: </td>
					<td>
						<div id="highLimitLottery" name="highLimitLottery" class="mini-combobox" style="width:200px;"  popupWidth="170px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true"  >     
						    <div property="columns">
						    </div>
						</div>
					</td>
	                <td>返佣百分比</td>
	                <td><input name="highRulePro" id="highRulePro" class="mini-spinner"  minValue="0" maxValue="15" decimalPlaces="1"/></td>
	            </tr>
	             <tr>
	                <td width="15%">数字彩</td>
	                <td width="5%">
						<div id="numRuleType" name="numRuleType"  class="mini-checkboxlist" textField="text" valueField="id" data="[{id:'1',text:'选择'}]" value="1">
						</div>
	                </td>
	                <td>包含彩种: </td>
					<td>
						<div id="numLimitLottery" name="numLimitLottery" class="mini-combobox" style="width:200px;"  popupWidth="170px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true"   >     
						    <div property="columns">
						    </div>
						</div>
					</td>
	                <td>返佣百分比</td>
	                <td><input name="numRulePro" id="highRulePro" class="mini-spinner"  minValue="0" maxValue="15" decimalPlaces="1"/></td>
	            </tr>
	            <tr>
	                <td>返佣周期</td>
	                <td><input id="rebateType" name="rebateType" class="mini-combobox" emptyText="请选择"  required="true"/></td>
	                <td>合作开始时间</td>
	      			<td><input id="cooperateTime"   name="cooperateTime" style="width:200px;"  class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" required="true"/>
	      			</td>
	                <td>状态</td>
	                <td><input id="status" name="status" class="mini-combobox" emptyText="请选择"  required="true"/></td>
	      			
	      		</tr>	           
	        </table>
			  </fieldset>
	    </div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/rebate_user.js" type="text/javascript"></script>
</html>
