<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>追号计划</title>
  </head>
<body>
  	<div id="searchForm" class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td>
			        <btn:operate privilege="EXPORT">
			        <a class="mini-button" iconCls="icon-download" plain="true" onclick="ChaseObj.excelChase('info')">导出追号计划</a>
			        <a class="mini-button" iconCls="icon-download" plain="true" onclick="ChaseObj.excelChase('user')">导出联系人</a>
			        </btn:operate>
			     	<btn:operate privilege="UPD">
					<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="ChaseObj.editChase()">修改</a>
					</btn:operate>
					<btn:operate privilege="CANCEL_ORDER">
					<a class="mini-button" iconCls="icon-goto" plain="true" onclick="ChaseObj.batCancelChaseIssue()">撤单</a>
					</btn:operate>	
				</td>
			</tr>
			<tr>
			   <td>
				  彩种类型：<input id="lotteryCategory" class="mini-combobox" style="width:150px;"
					   emptyText="请选择彩种类型" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="ChaseObj.closeLotCategory" onvaluechanged="ChaseObj.changeLotCategory"/> 
				         彩种：<input id="lotteryCode" class="mini-combobox" style="width:150px;"
					   emptyText="请选择彩种" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="ChaseObj.closeLotCode" onvaluechanged="ChaseObj.changeLotCode"/> 
				         彩期：<input id="issueCode" class="mini-combobox" style="width:150px;"
					   emptyText="请选择或输入" allowInput="true" oncloseclick="Cms.onCloseClick" showClose="true"/>
				     活动编号：<input id="activityId" class="mini-textbox" style="width:150px;"/> 
				     活动名称：<input id="activityName" class="mini-textbox" style="width:150px;"/> 
				     市场渠道：<input id="channel" class="mini-combobox" style="width:120px;"
				      emptyText="请选择或输入"    allowInput="true"   oncloseclick="Cms.onCloseClick" showClose="true"/>
				     投注平台：<input id="platform" class="mini-combobox" style="width:120px;"
				        emptyText="请选择"    allowInput="false"   oncloseclick="Cms.onCloseClick" showClose="true"/> 
				</td>
			</tr>
			<tr>
			   <td>
			            追号编号：<input id="orderAddCode" class="mini-textbox" style="width:150px;"/> 
			      <input id="userType" class="mini-combobox" style="width:120px;" allowInput="true" valueFromSelect="true"/>
				  <input id="userTypeVal" class="mini-textbox" style="width:120px;"/>
				  <input id="timeType" class="mini-combobox" style="width:100px;"/>
				    <input id="startQryTime" class="mini-datepicker" allowInput="false" style="width:180px;" 
				   format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick" />
				        到<input id="endQryTime" class="mini-datepicker"  allowInput="false"  style="width:180px;"
				   format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick" />
				   
				   <div id="isDltAdd" class="mini-checkboxlist" repeatDirection="vertical" repeatLayout="table" data="[{id:'1',text:'大乐透追加'}]" style="display:inline-block;"/> 
			   </td>
			</tr>
			<tr>
			   <td>
			            追号类型：<input id="addType" class="mini-combobox" style="width:120px;" emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" />
			            选号方式：<input id="codeWay" class="mini-combobox" style="width:120px;" emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" />
			            内容类型：<input id="contentType" class="mini-combobox" style="width:120px;" emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" />
			            支付状态：<input id="payStatus" class="mini-combobox" style="width:120px;" emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" />
			            追号状态：<input id="addStatus" class="mini-combobox" style="width:120px;" emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" />
			            停追类型：<input id="stopType" class="mini-combobox" style="width:120px;" emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" />
			    	<a class="mini-button" onclick="ChaseObj.search()">查询</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:87%;" style="border:0;">
		<div size="100%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="ordermgr/chase/list" idField="id" allowResize="true" pageSize="30"  multiSelect="false" 
				showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" onrowdblclick="ChaseObj.onrowdblclick()"
				showSummaryRow="true" ondrawsummarycell="ChaseObj.drawSummaryCell">
				
				<div property="columns">
					<div type="checkcolumn"></div>
					<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
					<div field="lotteryCode"  type= "comboboxcolumn" headerAlign="center" align="center" >彩种名称
					     <input property="editor" class="mini-combobox" data="Dic.allCode" />
					</div>
					<div field="issueCode"  headerAlign="center" align="center">彩期编号</div>
					<div field="user.nickName"  headerAlign="center" align="center">用户昵称</div>
					<div field="orderAddCode" headerAlign="center" align="center" width="180px;">追号编号</div>
					<div field="payStatus"  type= "comboboxcolumn" headerAlign="center" align="center">支付状态
					     <input property="editor" class="mini-combobox" data="Dic.payStatus" />
					</div>
					<div field="addAmount" headerAlign="center" align="center" allowSort="true" sortField="add_amount" showSortIcon="true" dataType="currency">追号金额(排)</div>
					<div field="addStatus"  type= "comboboxcolumn" headerAlign="center" align="center">追号状态
					     <input property="editor" class="mini-combobox" data="Dic.addStatus" />
					</div>
					<div headerAlign="center" align="center" renderer="ChaseObj.renderChaseIssue">已追/总期数</div>
					<div field="stopType"  type= "comboboxcolumn" headerAlign="center" align="center">停追类型
					     <input property="editor" class="mini-combobox" data="Dic.stopType" />
					</div>
					<div field="stopCondition"  headerAlign="center" align="center" renderer="ChaseObj.renderStopCondition">停追条件</div>
					<div field="preBonus" headerAlign="center" align="center" allowSort="true" sortField="pre_bonus" showSortIcon="true" dataType="currency">税前金额(排)</div>
					<div field="aftBonus" headerAlign="center" align="center" allowSort="true" sortField="aft_bonus" showSortIcon="true" dataType="currency">税后金额(排)</div>
					<div field="buyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true" sortField="buy_time">购买时间(排)</div>	
					<div field="endTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true" sortField="end_time">结束时间(排)</div>
					<div field="channel.channelName" headerAlign="center" align="center">渠道来源</div>
					<div field="platform" type="comboboxcolumn" headerAlign="center" align="center">投注平台
						<input property="editor" class="mini-combobox" data="Dic.platform" />
					</div>
					<div field="updateTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<div id="editWindow" class="mini-window" title="追号计划" 
		style="width:1300px; height:800px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="false" showModal="true" allowResize="true" allowDrag="true" >
		
		<%-- <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		    <btn:operate privilege="UPD">
			<a class="mini-button" onclick="ChaseObj.doSubmit">保存</a>
		    </btn:operate>
		</div> --%>
		<div id="editForm">
		    <input name="url"  class="mini-hidden"/>
			<input name="action"  class="mini-hidden"/>
			<input name="id"  class="mini-hidden"/>
			<input id="orderAddCode_edit" name="orderAddCode"  class="mini-hidden"/>
		   
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
	            <legend>追号信息:</legend>
	            <div style="padding:5px;">
	             <table width="100%;">
	                <tr>
	                 <td width="13%">彩种</td>
	                 <td width="20%"><input id="lotteryCode_edit" name="lotteryCode" class="mini-combobox" emptyText="请选择" valueFromSelect="true" style="width:100%;" enabled="false"/></td>
	                 <td width="13%">用户昵称</td>
	                 <td width="20%"><input name="user.nickName" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td width="13%">支付状态</td>
	                 <td width="20%"><input id="payStatus_edit" name="payStatus" class="mini-combobox" emptyText="请选择" valueFromSelect="true"  style="width:100%;"  enabled="false"/></td>
	                </tr>
	                <tr>
	                 <td>彩期</td>
	                 <td><input name="issueCode" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>用户账号</td>
	                 <td><input name="user.accountName" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>追号状态</td>
	                 <td><input id="addStatus_edit" name="addStatus"  class="mini-combobox" emptyText="请选择" valueFromSelect="true" style="width:100%;"  enabled="false"/></td>
	                </tr>
	                <tr>
	                 <td>购买时间</td>
	                 <td><input name="buyTime" class="mini-datepicker"  allowInput="false"  style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
	                 <td>用户手机号</td>
	                 <td><input name="user.cusMobile" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>停追类型</td>
	                 <td><input id="stopType_edit" name="stopType"  class="mini-combobox" emptyText="请选择" valueFromSelect="true" style="width:100%;" enabled="false"/></td>
	                </tr>
	                <tr>
	                 <td>结束时间</td>
	                 <td><input name="endTime" class="mini-datepicker"  allowInput="false"  style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
	                 <td>追号金额</td>
	                 <td><input name="addAmount" class="mini-textbox" style="width:100%;" enabled="false" /></td>
	                 <td>停追条件</td>
	                 <td><input id="stopCondition_edit" name="stopCondition"  class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	                <tr>
	                 <td>投注平台</td>
	                 <td><input id="platform_edit" name="platform"  class="mini-combobox" emptyText="请选择" valueFromSelect="true" style="width:100%;" enabled="false"/></td>
	                 <td>追号来源</td>
	                 <td><input name="channel.channelName" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>追号倍数</td>
	                 <td><input name="multipleNum" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	                <tr>
                 	 <td>使用红包ID</td>
	                 <td><input name="redCodeUsed"  class="mini-textbox" style="width:100%;" enabled="false"/></td>	                
	                 <td>追号类型</td>
	                 <td><input id="addType_edit" name="addType"  class="mini-combobox" emptyText="请选择" valueFromSelect="true" style="width:100%;" enabled="false"/></td>
	                 <td>税前奖金</td>
	                 <td><input name="preBonus" class="mini-textbox" style="width:100%;" enabled="false"/></td>

	                </tr>
	                <tr>
	                 <td>大乐透追加</td>
	                 <td><input id ="isDltAdd_edit" name="isDltAdd"  class="mini-combobox" emptyText="请选择" valueFromSelect="true" style="width:100%;" enabled="false"/></td>	                
	                 <td>活动来源</td>
	                 <td>
	                 	<!-- <input name="activity.activityName" class="mini-textbox" style="width:100%;" enabled="false"/> -->
	                 	<input name="activityId" class="mini-textbox" style="width:100%;" enabled="false"/>
	                 </td>
	                 <td>税后奖金</td>
	                 <td><input name="aftBonus" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	                <tr>
	                 <td>追号期数</td>
	                 <td><input name="issueAmount" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	                <tr>
	                 <td>活动说明</td>
	                 <td colspan="3"><input name="activity.activityDes" class="mini-textarea" style="width:100%;" enabled="false"/></td>
	                 <td>已追期数</td>
	                 <td><input name="hadIssue" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	            </table>            
	            </div>
	        </fieldset>
	        
            <fieldset style="border:solid 1px #aaa;padding:3px;">
            <legend>追号内容：</legend>
            <div class="mini-splitter" vertical="true" style="width:100%;height:220px;" style="border:0;">
				<div size="95%" showCollapseButton="true" style="border:0;">
					<div id="chaseContentGrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
						 url="ordermgr/chase/content/list" idField="id" allowResize="true" showColumnsMenu="true" pageSize="5" sizeList="[5,10,20,50,100]" multiSelect="true">
						
						<div property="columns">
							<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
							<div field="planContent" width="40%" headerAlign="center" align="center">方案内容</div>
							<div field="multiple"  headerAlign="center" align="center">倍数</div>
							<div field="amount"  headerAlign="center" align="center" dataType="currency">金额</div>
							<div field="lotteryChildCode"  headerAlign="center" align="center" renderer="ChaseObj.renderPlayIntro">玩法</div>
							<div field="codeWay"  type= "comboboxcolumn"  headerAlign="center" align="center">选号方式
							   <input property="editor" class="mini-combobox" data="Dic.codeWay" />
							</div>
							<div field="contentType"  type= "comboboxcolumn"  headerAlign="center" align="center">内容类型
							   <input property="editor" class="mini-combobox" data="Dic.contentType" />
							</div>
						</div>
					</div>
				</div>
			</div>
            </fieldset>
	        
	        <fieldset style="border:solid 1px #aaa;padding:3px;">
            <legend>追号期数：</legend>
            <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
				<table style="width:100%;">
					<tr>
					    <td>
					        <btn:operate privilege="EXPORT">
					        <a class="mini-button" iconCls="icon-download" plain="true" onclick="ChaseObj.excelChaseIssue()">导出追号明细</a>
					        </btn:operate>
					        <!-- 20170916 add 批量撤单：即单个计划剩余期撤单 -->
					        <btn:operate privilege="CANCEL_ORDER">
							<a class="mini-button" iconCls="icon-goto" plain="true" onclick="ChaseObj.cancelChaseRemainIssue()">批量撤单</a>
							</btn:operate>
							<!-- 20171116 add 撤单中退款：即单个计划中包含的撤单中（包括系统撤单中，停追撤单中，用户撤单中）彩期退款 ，若不包含则不执行操作-->
					        <btn:operate privilege="CANCEL_ORDER">
							<a class="mini-button" iconCls="icon-goto" plain="true" onclick="ChaseObj.cancellingRefund()">退款</a>
							</btn:operate>
						</td>
						<td width="5%" style="white-space:nowrap;">
						       追号状态：<input id="addIssueStatus" class="mini-combobox" style="width:150px;"
							   emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" /> 
							<a class="mini-button" onclick="ChaseObj.searchChaseIssue()">查询</a>
						</td>
						<td width="50%"></td>
					</tr>
				</table>
			</div>
            <div class="mini-splitter" vertical="true" style="width:100%;height:200px;" style="border:0;">
				<div size="95%" showCollapseButton="true" style="border:0;">
					<div id="chaseIssueGrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
						 url="ordermgr/chase/issue/list" idField="id" allowResize="true" showColumnsMenu="true" pageSize="5" sizeList="[5,10,20,50,100]" multiSelect="true">
						
						<div property="columns">
							<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
							<div field="issueCode"  headerAlign="center" align="center">彩期编号</div>
							<div field="buyAmount"  headerAlign="center" align="center" dataType="currency">认购金额</div>
							<div field="multiple"  headerAlign="center" align="center">倍数</div>
							<div field="addStatus"  type="comboboxcolumn" headerAlign="center" align="center">追号状态
							   <input property="editor" class="mini-combobox" data="Dic.addIssueStatus" />
							</div>
							<div field="orderCode" width="150px;" headerAlign="center" align="center">方案编号</div>
							<div field="preBonus"  headerAlign="center" align="center" dataType="currency">税前金额</div>
							<div field="addTime" width="130px;" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">追号时间</div>
							<div field="updateTime" width="130px;" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
							<div field="createTime" width="130px;" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
							<div width="130px;" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" renderer="ChaseObj.renderChaseIssueOperate">操作</div>
						</div>
					</div>
				</div>
			</div>
            </fieldset>
            
		</div>
    </div>
</body>
<script type="text/javascript">
  	var _hasCancelOrderPri = false; // 是否有撤单权限
</script>
<btn:operate privilege="CANCEL_ORDER">
	<script type="text/javascript">_hasCancelOrderPri = true;</script>
</btn:operate>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/ordermgr/chase.js" type="text/javascript"></script>
</html>