<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	<script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>  	 
    <title>优惠券</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td width="15%" nowrap="nowrap"> 
			     	<btn:operate privilege="ADD">
					<a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="Current.addwin()">新增</a>
					</btn:operate>			     	
			     	<btn:operate privilege="UPD|SEARCH">
					<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.editwin()">修改</a>
					</btn:operate>
					<btn:operate privilege="ADD">
					<a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="Current.reissueWin()">补发红包</a>
					</btn:operate>
					<btn:operate privilege="EXPORT">
			        <a class="mini-button" iconCls="icon-download" plain="true" onclick="Current.excel()">导出</a>
			        </btn:operate>
				</td>
			</tr>
			<tr>
			<td width="5%" style="white-space:nowrap;">
				        红包编号：<input id="redCode" class="mini-textbox" style="width:100px;"/>
				        红包种类：<input id="redCategory"   class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				        红包类型：<input id="redType"   class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				         所属用户   ：<input id="accountName" class="mini-textbox" style="width:100px;"/>
				         订单编号   ：<input id="orderCode4Search" class="mini-textbox"/>
				         活动ID   ：<input id="activityCodeSearch" class="mini-textbox"/>
				</td>
			</tr>
			<tr>
			<td width="5%" style="white-space:nowrap;">
			   红包状态 ：<input id="redStatus"   class="mini-combobox"  style="width:120px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
			   红包名称：<input id="redName" class="mini-textbox" style="width:100px;"/>
				   <input id="timeType"  class="mini-combobox"  style="width:100px;"/>
				   <input id="startTime" class="mini-datepicker"  allowInput="false"  style="width:180px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" class="mini-datepicker"  allowInput="false"  style="width:180px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        <a class="mini-button" onclick="Current.search()">查询</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="90%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="operatemgr/coupon/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" onrowdblclick="Current.onrowdblclick" 
				 onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn" ></div>
					<div type="indexcolumn" align="center" headerAlign="center">序号</div>
					<div field="redCode"  headerAlign="center" align="center">红包编号</div>
					<div field="redCategory"  type= "comboboxcolumn" headerAlign="center" align="center">红包种类
					     <input property="editor" class="mini-combobox" data="Dic.redCategory" />
					</div>
					<div field="redSource"  type= "comboboxcolumn" headerAlign="center" align="center">来源类型
					     <input property="editor" class="mini-combobox" data="Dic.redSource" />
					</div>
					<div field="activityCode"  headerAlign="center" align="center">活动ID</div>
					<div field="redType"  type= "comboboxcolumn" headerAlign="center" align="center">红包类型
					     <input property="editor" class="mini-combobox" data="Dic.redType" />
					</div>
					<div field="redName"  headerAlign="center" align="center">红包名称</div>
					<div field="redValue"  headerAlign="center" align="center">红包面值</div>
					<div field="redBalance"  headerAlign="center" align="center">红包余额</div>
					<div field="redStatus"  type= "comboboxcolumn" headerAlign="center" align="center">红包状态
					     <input property="editor" class="mini-combobox" data="Dic.redStatus" />
					</div>
					<div field="accountName" headerAlign="center" align="center">所属用户</div>
					<div field="useTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">使用时间</div>
					<div field="activeEndTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">激活截止时间</div>
					<div field="redOverdueTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">红包过期时间</div>
					<div field="obtainTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">获取时间</div>	
					<div field="redRemark"  headerAlign="center" align="center">备注</div>
					<div field="createTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
					<div headerAlign="center" align="center" renderer="Current.onCencelRenderer" cellStyle="padding:0;">操作</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="editWindow" class="mini-window" title="修改优惠券信息" 
		style="width:1100px; height:700px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		
		<%-- <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		    <btn:operate privilege="UPD">
			<a class="mini-button" onclick="Current.doSubmit">保存</a>
			</btn:operate>
		</div> --%>
		<div  id="editform">
			 <input name="id"  class="mini-hidden"/>
		     <input name="url"  class="mini-hidden"/>
			 <input name="action"  class="mini-hidden"/>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
	            <legend >红包信息：<span id="redCodeShow"></span></legend>
	            <div style="padding:5px;">
	             <table width="100%;">
	            <tr>
	            <td width="13%">红包种类</td>
	            <td width="20%"><input id ="redCategory_edit" name="redCategory"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"  enabled="false"/></td>
	            <td width="13%">红包类型</td>
	            <td width="20%"><input id ="redType_edit" name="redType"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"  enabled="false"/></td>
	            <td width="13%">红包状态</td>
	            <td width="20%"><input id ="redStatus_edit" name="redStatus"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"  enabled="false"/></td>
	            </tr>
	            <tr>
	            <td>活动ID</td>
	            <td><input name="activityCode" class="mini-textbox" style="width:100%;"  enabled="false"/></td>
	            <td>红包名称</td>
	            <td><input name="redName" class="mini-textbox" style="width:100%;"  enabled="false"/></td>
	            <td>限制平台</td>
	            <td><input id ="limitPlatform_edit" name="limitPlatform"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"  enabled="false"/></td>
	            </tr>
	             <tr>
	            <td>推荐入口</td>
	            <td><!-- <input name="operateLotteryId" class="mini-textbox" style="width:100%;"  enabled="false"/> -->
	            	<input id ="operateLotteryId_edit" name="operateLotteryId"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"  enabled="false"/>
	            </td>
	            <td>红包面额</td>
	            <td><input name="redValue" class="mini-textbox" style="width:100%;"  enabled="false"/></td>
	            <td>限制彩种</td>
	            <td><!-- <input id="limitLottery_edit" name="limitLottery" class="mini-textbox" style="width:100%;" /> -->
	            	<input id ="limitLottery_edit" name="limitLottery"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"  enabled="false"/>
	            </td>
	            </tr>
	            <tr>
	            <td>创建时间</td>
	            <td><input name="createTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
	            <td>最低消费金额</td>
	            <td><input name="minSpendAmount" class="mini-textbox" style="width:100%;"  enabled="false"/></td>
	            <td rowspan="2">使用规则</td>
	            <td rowspan="2"><input name="useRule" class="mini-textarea" style="width:100%;"  enabled="false"/></td>
	            </tr>
	            <tr>
	            <td>激活截止时间</td>
	            <td><input name="activeEndTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
	            <td>所属用户</td>
	            <td><input name="accountName" class="mini-textbox" style="width:100%;"  enabled="false"/></td>
	            </tr>
	            <tr>
	            <td>获取时间</td>
	            <td><input name="obtainTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
	            <td>有效天数</td>
	            <td><input name="ectivityDay" class="mini-textbox" style="width:100%;"  enabled="false"/></td>
	            <td rowspan="2">备注</td>
	            <td rowspan="2"><input name="redRemark" class="mini-textarea" style="width:100%;"  enabled="false"/></td>
	            </tr>
	            <tr>
	            <td>红包过期时间</td>
	            <td><input name="redOverdueTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
	            <td>发放渠道</td>
	             <td><input id ="terminalPlatform_edit" name="terminalPlatform"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"  enabled="false"/></td>
	            </tr>   
	             <tr>
	            <td>使用时间时间</td>
	            <td><input name="useTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
	            <td>红包标签</td>
	            <td><input name="redLabel" class="mini-textbox" style="width:100%;"  enabled="false"/></td>
	            <td>随机红包类型</td>
	            <td><input id="randomRedType_edit" name="randomRedType"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true" enabled="false"/></td>
	            </tr>
	             <tr>
	            <td>来源类型</td>
	            <td><input id ="redSource_edit" name="redSource"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"  enabled="false"/></td>
	            <td></td>
	            <td></td>
	            <td></td>
	            <td></td>
	            </tr>              
	            </table>            
	            </div>
	        </fieldset>
	        
           <fieldset style="border:solid 1px #aaa;padding:3px;">
            <legend>红包消费记录： 
                    <btn:operate privilege="EXPORT">
			        <a class="mini-button" iconCls="icon-download" plain="true" onclick="Current.excelRed()">导出跟单明细</a>
			        </btn:operate>
			</legend>
              <div class="mini-splitter" vertical="true" style="width:100%;height:200px;" style="border:0;">
				<div size="95%" showCollapseButton="true" style="border:0;">
					<div id="contentgrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
						 url="operatemgr/coupon/trans/list" idField="id" allowResize="true" showColumnsMenu="true" pageSize="10" multiSelect="true" onpreload="MiniCom.onpreload">
						<div property="columns">
							<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
							<div field="transType"  type= "comboboxcolumn"  headerAlign="center" align="center">交易类型
							   <input property="editor" class="mini-combobox" data="Dic.transType" />
							</div>
							<div field="redTransCode"  headerAlign="center" align="center">流水编号</div>
							<div field="orderCode"  headerAlign="center" align="center">订单编号</div>
							<div field="transAmount"  headerAlign="center" align="center">交易金额</div>
							<div field="aftTransAmount"  headerAlign="center" align="center">交易后余额</div>
							<div field="transTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">交易时间</div>
							<div headerAlign="center" align="center" renderer="Current.onActionRenderer">操作</div>
						</div>
					</div>
				</div>
			</div>
        </fieldset> 
            '
		   <fieldset style="border:solid 1px #aaa;padding:3px;">
            <legend >操作信息：</legend>
            <div style="padding:5px;">
             <table width="100%;">
			<tr>
			  <td width="13%">修改时间</td>
			  <td width="20%"><input name="modifyTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td width="13%" rowspan="2">备注</td>
			  <td width="20%" rowspan="2"><input name="remark" class="mini-textarea" vtype ="maxLength:100" style="width:100%;"  enabled="false"/></td>
			</tr>
			<tr>
			  <td>修改人</td>
			  <td><input name="modifyBy" class="mini-textbox" enabled="false" allowInput="false" style="width:100%;" /></td>
			  <td ></td>
			  <td ></td>
			</tr>
            </table>            
            </div>
        </fieldset> 
		</div>
</div>

	<div id="addWindow" class="mini-window" title="创建优惠券信息" 
		style="width:900px; height:500px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		<div id="addForm">
			<table width="100%;">
				<tr>
					<td>红包名称: </td>
					<td>
					    <input id ="redName_add" name="redName" class="mini-textbox" style="width:200px;" required="true" vtype="maxLength:20"/>
					</td>
					<td>活动编号: </td>
					<td>
						<input id="activityCode" name="activityCode" class="mini-textbox" style="width:200px;" vtype="maxLength:20" onblur="Current.activityCodeChangeChange()"/>
						<!-- 仅输入活动编码,默认红包来源为活动 -->
						<input id="redSource_add" name="redSource" class="mini-hidden" value=""/>
					</td>
				</tr>			
				<tr>
					<td>红包种类: </td>
					<td>
					    <input id ="redCategory_add" name="redCategory"   class="mini-combobox" style="width:200px;" emptyText="请选择" valueFromSelect = "true" required="true"/>
					</td>
					<td>红包类型: </td>
					<td>
					    <input id ="redType_add" name="redType"  class="mini-combobox" style="width:200px;" emptyText="请选择" valueFromSelect = "true" required="true" />
					</td>
				</tr>
				<tr>
					<td>红包面额: </td>
					<td>
						<input name="redValue" class="mini-textbox" style="width:200px;" required="true" onvalidation="Current.onMoneyValidation"/>
					</td>
					<td>最低消费: </td>
					<td>
						<input id="minSpendAmount" name="minSpendAmount" class="mini-textbox" vtype="int" style="width:200px;" vtype="float"/>
					</td>
				</tr>
				<tr>
					<td>初始状态: </td>
					<td>
						<input id ="redStatus_add" name="redStatus"  class="mini-combobox" style="width:200px;" emptyText="请选择"  valueFromSelect = "true" required="true"/>
					</td>
					<td>激活截止: </td>
					<td>
						<input id="activeEndTime" name="activeEndTime" class="mini-datepicker" style="width:200px;" showTime="true" allowInput="false" showClose="true" showOkButton="true" showClearButton="false" timeFormat="HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" oncloseclick="Cms.onCloseClick"/>
					</td>					
				</tr>
				<tr>
					<td>有效天数: </td>
					<td>
						<input name="ectivityDay" id="ectivityDay" class="mini-textbox" style="width:200px;" vtype="int" required="true"/>
					</td>
					<td>限制平台: </td>
					<td>
<!-- 						<input id ="limitPlatform_add" name="limitPlatform"  class="mini-combobox" style="width:140px;" emptyText="请选择"  valueFromSelect = "true" required="true" /> -->
						<div id="limitPlatform_add" name="limitPlatform" class="mini-combobox" style="width:200px;"  popupWidth="200px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick">     
						    <div property="columns">
						        <div header="全部" field="text"></div>
						    </div>
						</div>
					</td>					
				</tr>
				<tr>
					<td>限制渠道: </td>
					<td>
<!-- 						<input id ="limitPlatform_add" name="limitPlatform"  class="mini-combobox" style="width:140px;" emptyText="请选择"  valueFromSelect = "true" required="true" /> -->
						<div id="channelId_add" name="channelId" class="mini-treeselect" style="width:200px;"  popupWidth="300px;"
						textField="name" valueField="id" parentField="pid" checkRecursive="true" 
        				showFolderCheckBox="true"  expandOnLoad="true" showTreeLines="true"
						multiSelect="true" emptyText="请选择" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"> 
    					<div property="columns">
					        <div type="indexcolumn"></div>
					        <div field="name" name="name"  width="120" >全部</div>
					    </div>						    
						</div>						
					</td>
					<td>限制彩种: </td>
					<td>
						<!-- <input id="limitLottery_add" name="limitLottery"  class="mini-combobox" style="width:140px;" emptyText="请选择"   required="true" /> -->
						<div id="limitLottery_add" name="limitLottery" class="mini-combobox" style="width:200px;"  popupWidth="170px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true"  oncloseclick="Current.limitLotteryAddClose" onvaluechanged="Current.limitLotteryChange()" >     
						    <div property="columns">
						        <div header="全部" field="text"></div>
						    </div>
						</div>
					</td>										
				</tr>
				<tr>
					<td>加奖玩法: </td>
					<td>
					<!-- 	<input id="limitLotteryChild" name="limitLotteryChild"  class="mini-combobox" style="width:140px;" emptyText="请选择"  valueFromSelect = "true"/> -->
						<div id="limitLotteryChild_add" name="limitLotteryChild" class="mini-combobox" style="width:200px;"  popupWidth="200px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true"  oncloseclick="Cms.onCloseClick">     
						    <div property="columns">
						        <div header="全部" field="text"></div>
						    </div>
						</div>						
					</td>
					<td>加奖类型: </td>
					<td>
						<!-- <input id="limitLotteryChildType_add" name="limitLotteryChildType"  class="mini-combobox" style="width:140px;" emptyText="请选择"  valueFromSelect = "true" required="true" /> -->
						<div id="limitLotteryChildType_add" name="limitLotteryChildType" class="mini-combobox" style="width:200px;"  popupWidth="200px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true"  oncloseclick="Cms.onCloseClick" >     
						    <div property="columns">
						        <div header="全部" field="text"></div>
						    </div>
						</div>						
					</td>						
				</tr>
				<tr>
					<td>生成数量: </td>
					<td>
						<input name="num" class="mini-textbox" required="true" style="width:200px;" vtype="int;range:0,10"/>
					</td>
				</tr>
				<tr>
					<td>推荐入口: </td>
					<td>
						<!-- <input id="operateLotteryId_add" name="operateLotteryId"  class="mini-combobox" style="width:140px;" emptyText="请选择"  valueFromSelect = "true" required="true" /> -->
						<div id="operateLotteryId_add" name="operateLotteryId" class="mini-combobox" style="width:200px;" 
						    emptyText="请选择" required="true" valueFromSelect = "true" multiSelect="true"  showClose="true"  oncloseclick="Cms.onCloseClick">     
						</div>
					</td>
					<td clospan="2" align="left">
						注:可复选。
					</td>
				</tr>
				<tr>
					<td>红包标签: </td>
					<td colspan="3">
						<input id="label" class="mini-combobox" style="width:18%;" emptyText="请选择"  valueFromSelect = "true" required="true"
						 data="[{id:'1',text:'无'},{id:'2',text:'新用户专用'},{id:'3',text:'VIP专用'},{id:'4',text:'自定义'},]" onvaluechanged="Current.labelChange()" />
						<input id="redLabel" name="redLabel" class="mini-textbox" style="width:61%;" emptyText="最多输入5个字" enabled="false" vtype="maxLength:5"/>
					</td>
				</tr>
				<tr>
					<td>使用说明: </td>
					<td colspan="3">
						<input name="useRule" class="mini-textbox" style="width:100%;" vtype="maxLength:100"/>
					</td>
				</tr>				
				<tr>
					<td>备注说明: </td>
					<td colspan="3">
						<input name="redRemark" class="mini-textbox" style="width:100%;" vtype="maxLength:100"/>
					</td>
				</tr>
				<tr>
					<td>用户账号: </td>
					<td colspan="3">
						<input id="userAccountNameStr" name="userAccountNameStr" class="mini-textbox" style="width:100%;"/>
					</td>
				</tr>
				<tr>
					<td>用户名单</td>
					<td colspan="3"><input id="file" name="file" class="mini-htmlfile" limitType="*.txt" style="width:220px;"/> 上传文件声明: 一行一个姓名的" *.txt " 文本。</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<button style="margin-top: 50px;" id="operateBtn" onclick="Current.add()">保存</button>
					     <input name="url"  class="mini-hidden"/>
						 <input name="action"  class="mini-hidden"/>
						 <input name="redBalance" class="mini-hidden"/>						
					</td>
					<td colspan="2" align="left">
						<button style="margin-top: 50px;" id="closeBtn" onclick="Current.closeAddWin();">取消</button>
					</td>					
				</tr>
			</table>
		</div>
	
</div>

<div id="reissueWindow" class="mini-window" title="补发红包信息" 
		style="width:700px; height:200px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		<div id="reissueForm">
			<table width="100%;">
				<tr>
					<td>用户id: </td>
					<td>
					    <input id ="reissueUserId" name="userId" class="mini-textbox" style="width:200px;" required="true"/>
					</td>
					<td>方案编号: </td>
					<td>
						<input id="reissueOrderCode" name="orderCode" class="mini-textbox" style="width:200px;" />
					</td>
				</tr>			
				<tr>
					<td>活动编号: </td>
					<td>
					    <input id ="reissueActivityCode" name="activityCode"   class="mini-combobox" style="width:200px;" emptyText="请选择" valueFromSelect = "true" required="true"/>
					</td>					
				</tr>
				<tr>
					<td colspan="2" align="right">
						<button style="margin-top: 50px;" id="operateBtn" onclick="Current.reissueAdd()">保存</button>
					     <input name="url"  class="mini-hidden"/>
						 <input name="action"  class="mini-hidden"/>
						 <input name="redBalance" class="mini-hidden"/>						
					</td>
					<td colspan="2" align="left">
						<button style="margin-top: 50px;" id="closeBtn" onclick="Current.closeReissueWin();">取消</button>
					</td>					
				</tr>				
			</table>
		</div>
	
</div>



</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/coupon.js?vsersion=654" type="text/javascript"></script>
</html>
