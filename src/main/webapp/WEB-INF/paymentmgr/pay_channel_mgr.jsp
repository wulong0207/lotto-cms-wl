<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>支付渠道管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="ADD">
		        <a class="mini-button" iconCls="icon-add" plain="true" onclick="pay_channel_mgr.toAdd">新增</a>
	        </btn:operate>
	        <btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="pay_channel_mgr.toEdit">修改</a>
	        </btn:operate>
	        <btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-download" plain="true" onclick="pay_channel_mgr.excel">导出Exce</a>
	        </btn:operate>
				</td>
			</tr>
			<tr>
				<td width="5%" style="white-space:nowrap;">
				         渠道名称<input name="id" class="mini-combobox"  style="width:200px;" url="paymentmgr/channel/dic"
					   emptyText="请选择" allowInput="true" valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true"/> 
				         是否可用<input name="available" class="mini-combobox" data="Dic.available"
					   emptyText="请选择" allowInput="true" valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true"/> 
			        <a class="mini-button" onclick="pay_channel_mgr.search">查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:87%;" 
		 url="paymentmgr/channel/page" idField="id" allowResize="true" ajaxType="get"
		 showColumnsMenu="true" showFilterRow="false" onrowdblclick="pay_channel_mgr.toEdit">
		<div property="columns">
			<div type="checkcolumn" width="5%"></div>
			<div type="indexcolumn" width="5%" align="center" headerAlign="center">序号</div>
			<div field="name" headerAlign="center" align="center">渠道名称</div>
			<div field="available" type="comboboxcolumn" headerAlign="center" align="center">是否可用
		     <input property="editor" class="mini-combobox" data="Dic.available" />
			</div>
			<div field="pause" type="comboboxcolumn" headerAlign="center" align="center">启用暂停
		     <input property="editor" class="mini-combobox" data="Dic.pause" />
			</div>
			<div field="beginTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">暂停开始时间</div>
			<div field="endTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">暂停结束时间</div>
			<div field="orderId" headerAlign="center" align="center">排序</div>
			<div header="投注平台" headerAlign="center" align="center">
				<div property="columns">
					<div field="pc" type="checkboxcolumn" headerAlign="center" align="center">Web
						<input property="editor" class="mini-checkbox" data="Dic.pause" />
					</div>
					<div field="h5" type="checkboxcolumn" headerAlign="center" align="center">Wap
						<input property="editor" class="mini-checkbox" data="Dic.pause" />
					</div>
					<div field="android" type="checkboxcolumn" headerAlign="center" align="center">Android
						<input property="editor" class="mini-checkbox" data="Dic.pause" />
					</div>
					<div field="ios" type="checkboxcolumn" headerAlign="center" align="center">iOS
						<input property="editor" class="mini-checkbox" data="Dic.pause" />
					</div>
				</div>
			</div>
			<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
			<div field="modifyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
		</div>
	</div>
	
	<div id="detailWindow" showToolbar="true" class="mini-window" style="width:1100px;height:750px" showFooter="true" showModal="true" allowResize="true" allowDrag="true" showCloseButton="true">
	    <div property="toolbar" style="text-align:right;padding:2px;padding-right:15px;">
		<btn:operate privilege="SAVE">
		        <a class="mini-button" iconCls="icon-save"  onclick="pay_channel_mgr.merge">保存</a>
	        </btn:operate>    
	        </div>
	    <div id="detailForm" class="form">
	    <input name="id" id="id" class="mini-hidden"/>
			    <table style="border:solid 0px;">
			    <tr><td>基本信息：</td></tr>
	            <tr>
	                <td>渠道名称</td>
	                <td><input name="name" id="name" class="mini-textbox" enabled="false" required="true"/></td>
	                <td>编码</td>
	                <td><input name="code" id="code" class="mini-textbox" enabled="false" required="true"/></td>
	                <td>默认排序值</td>
	                <td><input name="orderId" class="mini-textbox"/></td>
	            </tr>
	            <tr>
	                <td>最低支付金额</td>
	                <td><input name="minPay" class="mini-textbox" required="true"/></td>
	                <td>最高支付金额</td>
	                <td><input name="maxPay" class="mini-textbox" required="true"/></td>
	                <td>费率</td>
	                <td><input name="rate" id="rate" class="mini-textbox"/></td>
	            </tr>
	            <tr>
	                <td>可用平台</td>
	                <td>
	                	<input name="pc" class="mini-checkbox"/>PC
	                	<input name="h5" class="mini-checkbox"/>H5
	                	<input name="android" class="mini-checkbox"/>Android
	                	<input name="ios" class="mini-checkbox"/>iOS
	                </td>
	                <td>是否可用</td>
	                <td>
	                <div id="available" name="available" class="mini-radiobuttonlist" repeatItems="2" repeatDirection="vertical"
    textField="text" valueField="id">
	                </td>
	            </tr>
	            <tr>
	             	<td>启用暂停</td>
	                <td>
	                <div id="pause" name="pause" class="mini-radiobuttonlist" repeatItems="2" repeatDirection="vertical"
    textField="text" valueField="id">
	                </td>
	                <td>暂停开始时间</td>
	                <td>
	                <input name="beginTime" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showClearButton="false" showTime="true"showOkButton="true" style="width:170px;"/>
	                </td>
	                <td>暂停结束时间</td>
	                <td>
         	        <input name="endTime" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showClearButton="false" showTime="true"showOkButton="true" style="width:170px;"/>
	                </td>
	            </tr>
	        </table>
			  <br/>
			  
			  
			  <div id="form2">
			        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
			            <table style="width:100%;">
			<tr>
				<td width="5%" style="white-space:nowrap;">
				银行配置：
				<btn:operate privilege="ADD">
				<input id="bankList" class="mini-combobox" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" repeatItems="2" oncloseclick="Cms.onCloseClick" multiSelect="true"/>
				<a  class="mini-button" iconCls="icon-add" plain="true" onclick="pay_channel_mgr.toAddBankChannel">新增</a>
				</btn:operate>
				<btn:operate privilege="UPD">
				<a  class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.editRow('bankDatagrid')">修改</a>
				</btn:operate>
				<btn:operate privilege="SAVE">
				<a  class="mini-button" iconCls="icon-save" plain="true" onclick="pay_channel_mgr.batchMergeBankChannel">保存</a>
				</btn:operate>
				<btn:operate privilege="DEL">
				<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="pay_channel_mgr.deleteBankChannel">删除</a>
				</btn:operate>
 					  银行名称<input id="bankName" class="mini-textbox"/>
 				<btn:operate privilege="SEARCH">
		        <a class="mini-button" onclick="pay_channel_mgr.pageChangeBankInfo">查询</a>
	        	</btn:operate>
				</td>
			</tr>
			            </table>           
			        </div>
			    </div>
		  		<div id="bankDatagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" style="width:100%;height: 450px;" 
						  idField="id" multiSelect="true" url="paymentmgr/channel/bank/page" onrowdblclick="pay_channel_mgr.editIt"
						 showColumnsMenu="true">
						<div property="columns">
							<div type="checkcolumn" width="3%"></div>
							<div type="indexcolumn" align="center" headerAlign="center">序号</div>
							<div  field="id" headerAlign="center" align="center" >支付渠道id(隐藏)
				                <input property="editor" class="mini-textbox"/>
			                </div>
							<div field="bankid" type="comboboxcolumn" headerAlign="center" align="center">银行名称
						     <input property="editor" class="mini-combobox" data="Dic.bankList" />
							</div>
							<div field="rate" headerAlign="center" align="center">费率
								<input  property="editor"  class="mini-textbox"/>
							</div>
							<div field="charge" type="comboboxcolumn" headerAlign="center" align="center">是否收费
						     <input property="editor" class="mini-combobox" data="Dic.charge" />
							</div>
							<div field="type" type="comboboxcolumn" headerAlign="center" align="center">支付方式
						     <input property="editor" class="mini-combobox" data="Dic.payType" required="true"/>
							</div>
							<div field="cardtype" type="comboboxcolumn" headerAlign="center" align="center">卡类型
						     <input property="editor" class="mini-combobox" data="Dic.cardType" required="true"/>
							</div>
						</div>
						</div>
			  <br/>
			  
			  
			  
			  		        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
			            <table style="width:100%;">
				<tr>
					<td width="5%" style="white-space:nowrap;">
					付款金额超限说明：
					<btn:operate privilege="ADD">
				<a  class="mini-button" iconCls="icon-add" plain="true" onclick="Cms.addEditRow('limitDatagrid')">新增</a>
				</btn:operate>
				<btn:operate privilege="UPD">
				<a  class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.editRow('limitDatagrid')">修改</a>
				</btn:operate>
				<btn:operate privilege="SAVE">
				<a  class="mini-button" iconCls="icon-save" plain="true" onclick="pay_channel_mgr.batchMergeChannelLimit">保存</a>
				</btn:operate>
				<btn:operate privilege="DEL">
				<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="pay_channel_mgr.deleteChannelLimit">删除</a>
				</btn:operate>
					</td>
				</tr>
			            </table>           
			        </div>
			        
		  		<div id="limitDatagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" style="width:100%;height: 300px;" 
						  idField="id" multiSelect="true" url="paymentmgr/channel/limit/page" ajaxType="get" onrowdblclick="pay_channel_mgr.editIt"
						 showColumnsMenu="true" showPager="false" >
						<div property="columns">
							<div type="checkcolumn" width="3%"></div>
							<div type="indexcolumn" align="center" headerAlign="center">序号</div>
							<div field="limittime" headerAlign="center" align="center">单笔限额(元)
								<input  property="editor"  class="mini-textbox" required="true"/>
							</div>
							<div field="limitday" headerAlign="center" align="center">每日限额(元)
								<input property="editor" class="mini-textbox" required="true"/>
							</div>
							<div field="limitmonth" headerAlign="center" align="center">每月限额(元)
								<input property="editor" class="mini-textbox" required="true"/>
							</div>
							<div field="payType" type="comboboxcolumn" headerAlign="center" align="center">支付方式
						     <input property="editor" class="mini-combobox" data="Dic.payType" required="true"/>
							</div>
							<div field="cardType" type="comboboxcolumn" headerAlign="center" align="center">卡类型
						    	<input property="editor" class="mini-combobox" data="Dic.cardType" required="true"/>
							</div>
							<div field="remark" headerAlign="center" align="center">备注
								<input property="editor" class="mini-textbox"/>
							</div>
						</div>
						</div>
			  <br/>
			  
			  
			  
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <legend>操作信息：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td style="width:100px;">创建时间</td>
	                <td style="width:150px;"><input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	                <td style="width:100px;">修改时间</td>
	                <td style="width:150px;"><input name="modifyTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            		<td style="width:100px;" rowspan="2">备注</td>
	                <td style="width:150px;" rowspan="2"><input name="remark" id="remark" class="mini-textarea"/></td>
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
	
	
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/paymentmgr/pay_channel_mgr.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
