<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>用户充值管理</title>
    <style type="text/css">
    </style>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="RESUPPLY">
		        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="trans_recharge.triggerResupply">补单</a>
	        </btn:operate>
	        <btn:operate privilege="MANUAL_RECHARGE">
		        <a class="mini-button" iconCls="icon-add" plain="true" onclick="trans_recharge.toManualRecharge">人工充值</a>
	        </btn:operate>
	        <btn:operate privilege="EXPORT">
		        <a class="mini-button" iconCls="icon-download" plain="true" onclick="trans_common.excel('transmgr/recharge')">导出Excel</a>
	        </btn:operate>
				</td>
			</tr>
			<tr>
				<td>
					系统流水编号<input id=transRechargeCode name="transRechargeCode" class="mini-textbox" style="width:200px;"/>
					第三方流水编号 <input id="thirdTransNum" name="thirdTransNum" class="mini-textbox" style="width:200px;"/>
					充值渠道<input id="rechargeChannel" name="rechargeChannel" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					银行卡分类<input id="bankCardType" name="bankCardType" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					市场渠道
					<input id="channelId" name="channelId" class="mini-combobox" style="width:120px;" url="operatemgr/marketchannel/dic"
						   emptyText="请选择" allowInput="true" oncloseclick="Cms.onCloseClick" showClose="true"/>
				</td>
			</tr>
			<tr>
				<td>
					<input id="searchUserType" name="searchUserType" class="mini-combobox"  style="width:150px;" valueFromSelect ="true"/>
				 	<input id="searchUserValue" name="searchUserValue" class="mini-textbox" style="width:200px;"/> 
					<input id="searchTimeType"  name="searchTimeType" class="mini-combobox"  style="width:150px;" valueFromSelect ="true"/>
					<input id="startTime" name="startTime" class="mini-datepicker" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" name="endTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>  
					交易状态<input id="transStatus" name="transStatus" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择"  valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					银行名称<input id="rechargeBank" name="rechargeBank" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择"  valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					支付方式分类<input id="payType" name="payType" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择"  valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					<a class="mini-button" onclick="trans_common.search(trans_recharge.grid)" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:90%;" 
		 url="transmgr/recharge/list" idField="id" allowResize="true" multiSelect="true" 
		 showColumnsMenu="true" onrowdblclick="trans_recharge.viewDetail" showFilterRow="false" showSummaryRow = "true" ondrawsummarycell="trans_recharge.onDrawSummaryCell">
		<div property="columns">
			<div type="checkcolumn" width="25px" align="center" headerAlign="center"></div>
			<div type="indexcolumn" width="40px" align="center" headerAlign="center">序号</div>
			<div field="nickName" headerAlign="center" align="center">会员昵称</div>
			<div field="transRechargeCode" headerAlign="center" align="center">系统流水编号</div>
			<div field="transStatus" type= "comboboxcolumn" headerAlign="center" align="center">交易状态
		     <input property="editor" class="mini-combobox" data="Dic.transStatus" />
			</div>
			<div field="rechargeAmount" headerAlign="center" align="center" width="40px">充值金额</div>
			<div field="serviceCharge" headerAlign="center" align="center" width="40px">服务费</div>
			<div field="redAmount" headerAlign="center" align="center" width="40px">红包金额</div>
			<div field="arrivalAmount" headerAlign="center" align="center" width="40px">到账金额</div>
			<div field="rechargeChannel" type= "comboboxcolumn" headerAlign="center" align="center">充值渠道
		     <input property="editor" class="mini-combobox" data="Dic.rechargeChannel" />
			</div>
			<div field="bankCardType" type= "comboboxcolumn" headerAlign="center" align="center">银行卡分类
		     <input property="editor" class="mini-combobox" data="Dic.bankCardType" />
			</div>
			<div field="rechargeBank" type= "comboboxcolumn" headerAlign="center" align="center">银行名称
		     <input property="editor" class="mini-combobox" data="Dic.rechargeBank" />
			</div>
			<div field="payType" type="comboboxcolumn" headerAlign="center" align="center" width="8%">支付方式分类
		     <input property="editor" class="mini-combobox" data="Dic.payType" />
			</div>
			<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>	
			<div field="transEndTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">交易结束时间</div>	
			<div field="orderCode" headerAlign="center" align="center">订单编号</div>
			<div field="thirdTransNum" headerAlign="center" align="center">第三方流水号</div>
			<div field="rechargePlatform" type="comboboxcolumn" headerAlign="center" align="center">充值平台
		     <input property="editor" class="mini-combobox" data="Dic.rechargePlatform" />
			</div>
			<div field="supplementBy" headerAlign="center" align="center"  width="8%">补单人</div>
			<div field="remark" headerAlign="center" align="center">备注</div>
			<div field="channelName" headerAlign="center" align="center">市场渠道</div>
		</div>
	</div>
	<div id="detailWindow" class="mini-window" style="width:800px;" showModal="true" allowResize="true" allowDrag="true">
	    <div id="detailForm" class="form" >
		    <fieldset>
			    <legend>投注信息：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td style="width:100px;">充值渠道</td>
	                <td style="width:150px;"><input id="rechargeChannelDetail" name="rechargeChannel" class="mini-combobox" enabled="false"/></td>
	                <td style="width:100px;">用户昵称</td>
	                <td style="width:150px;"><input name="nickName" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">交易状态</td>
	                <td style="width:150px;"><input id="transStatusDetail" name="transStatus" class="mini-combobox" enabled="false"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">充值银行</td>
	                <td style="width:150px;"><input id="rechargeBankDetail" name=rechargeBank class="mini-combobox" enabled="false"/></td>
	                <td style="width:100px;">用户帐号</td>
	                <td style="width:150px;"><input name="accountName" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">充值平台</td>
	                <td style="width:150px;"><input id="rechargePlatformDetail" name="rechargePlatform" class="mini-combobox" enabled="false"/></td>
	            </tr>
	            <tr>
<!-- 	                <td style="width:100px;">创建时间</td> -->
<!-- 	                <td style="width:150px;"><input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td> -->
	                <td style="width:100px;">用户手机号码</td>
	                <td style="width:150px;"><input name="cusMobile" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">市场渠道</td>
	                <td style="width:150px;"><input name="channelName" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">交易时间</td>
	                <td style="width:150px;"><input name="transTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">充值金额</td>
	                <td style="width:150px;"><input id="rechargeAmountDetail" class="mini-textbox" enabled="false"/></td>
	                <%--
	                <td style="width:100px;">订单编号</td>
	                <td style="width:150px;"><input name="orderCode" class="mini-textbox" enabled="false"/></td>
	                --%>
	                <td style="width:100px;">结束时间</td>
	                <td style="width:150px;"><input name="transEndTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">到帐金额</td>
	                <td style="width:150px;"><input id="arrivalAmountDetail" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">第三方流水号</td>
	                <td style="width:150px;"><input name="thirdTransNum" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">第三方交易时间</td>
	                <td style="width:150px;"><input name="thirdTransTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">服务费</td>
	                <td style="width:150px;"><input id="serviceChargeDetail" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">红包金额</td>
	                <td style="width:150px;"><input id="redAmountDetail" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">请求响应时间</td>
	                <td style="width:150px;"><input name="responseTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">充值描述</td>
	                <td style="width:150px;"><input name="rechargeRemark" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">银行卡号</td>
	                <td style="width:150px;"><input name="bankCardNum" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">支付方式</td>
	                <td style="width:150px;"><input name="payType" id="payTypeDetail" class="mini-combobox" enabled="false"/></td>
	            </tr>
	             <tr>
	                <td style="width:100px;">银行卡类型</td>
	                <td style="width:150px;"><input name="bankCardType" id="bankCardTypeDetail" class="mini-combobox" enabled="false"/></td>
	            </tr>
	        </table>
			  </fieldset>
		    <fieldset>
			    <legend>操作信息：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td style="width:100px;">创建时间</td>
	                <td style="width:150px;"><input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	                <td style="width:100px;">修改时间</td>
	                <td style="width:150px;"><input name="modifyTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            		<td style="width:100px;" rowspan="2">备注</td>
	                <td style="width:150px;" rowspan="2"><input name="remark" class="mini-textbox" enabled="false"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">创建人</td>
	                <td style="width:150px;"><input name="createBy" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">修改人</td>
	                <td style="width:150px;"><input name="modifyBy" class="mini-textbox" enabled="false"/></td>
	            </tr>
	        </table>
			  </fieldset>
	    </div>
	</div>
	
	<div id="resupplyWindow" class="mini-window" title="补单" style="width:500px;height:400px;">
	    <table>
            <tr>
                <td>*会员昵称</td>
                <td><input name="nickName" class="mini-textbox" required="true"/></td>
                <td>*充值金额</td>
                <td><input name="rechargeAmount" class="mini-textbox" required="true"/></td>
                <td>*资金类型</td>
                <td><input id="moneyTypeResupply" name="moneyType" class="mini-combobox" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" required="true"/></td>
            </tr>
            <tr>
                <td>*第三方流水号</td>
                <td colspan="5"><input name="thirdTransNum" class="mini-textbox" required="true"/></td>
            </tr>
            <tr>
                <td>*补单描述</td>
                <td colspan="5"><input name="rechargeRemark" class="mini-textbox" required="true"/></td>
            </tr>
            <tr>
            	<td>
	            <a class="mini-button" onclick="trans_recharge.resupply">确认</a>
	            <a class="mini-button">取消</a>
            	</td>
            </tr>
        </table>
	    <fieldset style="border:0">
		    <legend >操作信息：</legend>
		    <table border="1px #fff" cellspacing="0" cellpadding="0">
            <tr>
                <td>创建时间</td>
                <td><input name="resupplyCreateTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
           		<td rowspan="2">备注</td>
                <td rowspan="2" width="200px;"><input name="remark" class="mini-textarea"/></td>
            </tr>
            <tr>
                <td>创建人</td>
                <td><input name="resupplyCreateBy" class="mini-textbox" enabled="false"/></td>
            </tr>
        </table>
	  	</fieldset>
	</div>
	
	<div id="manualRechargeWindow" class="mini-window" title="人工充值" style="width:800px;height:400px;" allowResize="true">
	    <table>
            <tr>
                <td><input id="manualUserType" name="manualUserType" class="mini-combobox" valueFromSelect ="true"/></td>
                <td><input name="manualUserTypeValue" class="mini-textbox" required="true"/></td>
                <td>*充值金额</td>
                <td><input name="rechargeAmount" class="mini-textbox" vtype="float" required="true"/></td>
                <td>*资金类型</td>
                <td><input id="rechargeType" name="rechargeType" class="mini-combobox" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" required="true"/></td>
            </tr>
            <tr>
            	<td>*交易分类</td>
                <td><input id="tradeType" name="tradeType" class="mini-combobox" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" required="true"/></td>
                <td>*充值描述</td>
                <td colspan="5"><input name="rechargeRemark" class="mini-textbox" required="true"/></td>
            </tr>
            <tr>
            	<td>
	            <a class="mini-button" onclick="trans_recharge.manualRecharge">确认</a>
	            <a class="mini-button">取消</a>
            	</td>
            </tr>
        </table>
	    <fieldset style="border:0">
		    <legend >操作信息：</legend>
		    <table border="1px #fff" cellspacing="0" cellpadding="0">
            <tr>
                <td>创建时间</td>
                <td><input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
           		<td rowspan="2">备注</td>
                <td rowspan="2" width="200px;"><input name="remark" class="mini-textarea"/></td>
            </tr>
            <tr>
                <td>创建人</td>
                <td><input name="createBy" class="mini-textbox" enabled="false"/></td>
            </tr>
        </table>
	  	</fieldset>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/transmgr/trans_common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/transmgr/trans_recharge.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
