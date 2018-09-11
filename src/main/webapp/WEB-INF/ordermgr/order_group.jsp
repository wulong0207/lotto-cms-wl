<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>合买管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="conditionForm">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
			        <btn:operate privilege="UPD">
				        <a class="mini-button" id="recommendBtn" iconCls="icon-edit" plain="true" onclick="Current.visitRecommend(1)">推荐</a>
				        <a class="mini-button" id="cancelRecommendBtn" iconCls="icon-edit" plain="true" onclick="Current.visitRecommend(0)">取消推荐</a>
			        </btn:operate>
			        <btn:operate privilege="UPD">
				        <a class="mini-button" id="topBtn" iconCls="icon-edit" plain="true" onclick="Current.visitTop(1)">置顶</a>
				        <a class="mini-button" id="cancelTopBtn" iconCls="icon-edit" plain="true" onclick="Current.visitTop(0)">取消置顶</a>
			        </btn:operate>
			        <btn:operate privilege="UPD">
				        <a class="mini-button" id="cancelOrderBtn" iconCls="icon-goto" plain="true" onclick="Current.cancelOrder()">流产</a>
			        </btn:operate>
			        <btn:operate privilege="UPD">
				        <a class="mini-button" id="guaranteeBtn" onclick="Current.guaranteeRate()">网站保底</a>
			        </btn:operate>
				</td>
			</tr>
			<tr>
				<td width="5%" style="white-space:nowrap;">
					         彩种类型<input id="lotteryCategory"  name="lotteryCategory" class="mini-combobox"  style="width:150px;"
						   emptyText="请选择彩种类型" allowInput="true" valueFromSelect ="true"  oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="Current.lotteryCategoryChange"/> 
					         彩种<input id="lotteryCode" name="lotteryCode" class="mini-combobox"  style="width:150px;"
						   emptyText="请选择彩种" allowInput="true" valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="Current.lotteryCodeChange"/> 
					         彩期<input id="lotteryIssue"  name="lotteryIssue" class="mini-combobox"  style="width:150px;"
						   emptyText="请选择" allowInput="true" oncloseclick="Cms.onCloseClick" showClose="true"/>
						方案状态<input id="orderStatus" name="orderStatus"  class="mini-combobox"  style="width:120px;" emptyText="请选择" 
						allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true" popupMinHeight="230"/>
						<input id="userType" name="userType"  class="mini-combobox"  style="width:120px;" allowInput="true" valueFromSelect = "true"/>
				  		<input id="userTypeVal" name="userTypeVal" class="mini-textbox" style="width:135px;"/>
						<br/>
						合买状态<input id="grpbuyStatus" name="grpbuyStatus"  class="mini-combobox"  style="width:120px;" emptyText="请选择" 
						allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true" popupMinHeight="230"/>
						中奖状态<input id="winningStatus" name="winningStatus"  class="mini-combobox"  style="width:120px;"emptyText="请选择" 
						allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true"/>
						渠道来源<input id="channel" name="channelId" class="mini-combobox" style="width:120px;"
					      emptyText="请选择或输入"      allowInput="true"  oncloseclick="Cms.onCloseClick" showClose="true"/>
						投注平台<input id="platform" name="platform" class="mini-combobox" style="width:120px;"
					        emptyText="请选择" allowInput="false"   oncloseclick="Cms.onCloseClick" showClose="true"/>
	     				合买方案编号<input name="orderCode" class="mini-textbox" style="width:200px;"/>
	     			
	     			<a class="mini-button" onclick="Current.search" >查询</a>
     			
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" allowAlternating="true" style="width:100%;height:100%;" 
		 url="ordermgr/group/list" idField="id" allowResize="true" pageSize="30" onrowclick="Current.dispalyBtn"
				showColumnsMenu="true" allowMoveColumn="true" showSummaryRow="true" onrowdblclick="Current.openEditWin">
		<div property="columns">
			<div type="checkcolumn"></div>
			<div type="indexcolumn" align="center" headerAlign="center">序号</div>
			<div field="lotteryCode" type="comboboxcolumn" headerAlign="center" align="center">
		     	彩种名称<input property="editor" class="mini-combobox" data="Dic.lottery" />
			</div>
			<div field="lotteryIssue" headerAlign="center" align="center">期号</div>
			<!-- <div field="buyType" type="comboboxcolumn" headerAlign="center" align="center">
		     	购买类型<input property="editor" class="mini-combobox" data="Dic.buyType" />
			</div> -->
			<div field="accountName" headerAlign="center" align="center">发起人用户名</div>
			<div field="orderCode" headerAlign="center" align="center" width="180px;">方案编号</div>
			<div field="payStatus"  type= "comboboxcolumn" headerAlign="center" align="center">
				支付状态<input property="editor" class="mini-combobox" data="Dic.payStatus" />
			</div>
			<div field="grpbuyStatus"  type= "comboboxcolumn" headerAlign="center" align="center">
				合买状态<input property="editor" class="mini-combobox" data="Dic.grpbuyStatus" />
			</div>
			<div field="orderStatus" type="comboboxcolumn" headerAlign="center" align="center">
				方案状态<input property="editor" class="mini-combobox" data="Dic.orderStatus" />
			</div>
			<div field="orderAmount" headerAlign="center" align="center">方案金额</div>
			<div field="commissionRatio" headerAlign="center" align="center" numberFormat="p">提成比例</div>
			<div field="progress" headerAlign="center" align="center" numberFormat="p">合买进度</div>
			<div field="buyCount" headerAlign="center" align="center">参与人数</div>
			<div field="guaranteeRatio" headerAlign="center" align="center" numberFormat="p">发起人保底</div>
			<div field="winningStatus" type="comboboxcolumn" headerAlign="center" align="center">
				中奖状态<input property="editor" class="mini-combobox" data="Dic.winningStatus" />
			</div>
			<div field="aftBonus" headerAlign="center" align="center">税后金额</div>
			<div field="buyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">购买时间</div>
			<div field="endSysTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">截止时间</div>
			<div field="channelId" type= "comboboxcolumn" headerAlign="center" align="center">
				渠道来源<input property="editor" class="mini-combobox" data="Dic.channel" /></div>
			<div field="platform" type= "comboboxcolumn"   headerAlign="center" align="center">
				投注平台<input property="editor" class="mini-combobox" data="Dic.platform" />
			</div>
			<!-- <div field="isRecommend" type="comboboxcolumn" headerAlign="center" align="center">
		     	推荐<input property="editor" class="mini-combobox" data="Dic.yesNoData" />
			</div>
			<div field="isTop" type="comboboxcolumn" headerAlign="center" align="center">
		     	置顶<input property="editor" class="mini-combobox" data="Dic.yesNoData" />
			</div> -->
		</div>
	</div>
	
	<div id="detailWindow" showToolbar="true" class="mini-window" style="width:80%;height:750px" showFooter="true" showModal="true" allowResize="true" allowDrag="true" showCloseButton="true">
	    <div property="toolbar" style="text-align:right;padding:2px;padding-right:15px;">
			<btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-save"  onclick="Current.update">保存</a>
	        </btn:operate>    
	    </div>
	    <div id="detailForm" class="form">
	    	<input name="id" id="id" class="mini-hidden"/>
	    	<input name="orderCode" id="orderCode" class="mini-hidden"/>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <legend>投注信息：</legend>
			    <table>
	            <tr>
	                <td>彩种</td>
	                <td><input id="detail_LotteryCode" name="lotteryCode" class="mini-combobox" enabled="false"/></td>
	                <td>购买类型</td>
	                <td><input id="detail_buyType" name="buyType" class="mini-combobox" enabled="false"/></td>
	                <td>支付状态</td>
	                <td><input id="detail_payStatus" name="payStatus"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true" enabled="false"/></td>
	                <td>方案状态</td>
	                <td><input id="detail_orderStatus" name="orderStatus"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  enabled="false" required="true"/></td>
	            </tr>
	            <tr>
	            	<td>发起人昵称</td>
	                <td><input name="nickName" class="mini-textbox" enabled="false"/></td>
	                <td>发起人帐号</td>
	                <td><input name="accountName" class="mini-textbox" enabled="false"/></td>
	                <td>彩期</td>
	                <td><input name="lotteryIssue" class="mini-textbox" enabled="false"/></td>
	                <td>合买状态</td>
	                <td><input id ="detail_grpbuyStatus" name="grpbuyStatus"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true" required="true"/></td>
	            </tr>
	            <tr>
	                <td>发起时间</td>
	                <td><input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss" style="width:160px"/></td>
	                
	                <td>截止时间</td>
	                <td><input name="endSysTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss" style="width:160px"/></td>
	                
	                <td>加奖类型</td>
	                <td colspan="3"><input id="bonusFlag" name="bonusFlag" class="mini-combobox" enabled="false"/></td>
	            </tr>
	            
	             <tr>
	                <td>方案金额</td>
	                <td><input name="orderAmount" class="mini-textbox" enabled="false"/></td>
	                <td>方案倍数</td>
	                <td colspan="5"><input name="multipleNum" class="mini-textbox" enabled="false"/></td>
	            </tr>
	            
	            <tr>
	                <td>分佣比例</td>
	                <td><input name="commissionRatio" class="mini-textbox" enabled="false"/></td>
	                <td>跟单人数</td>
	                <td colspan="5"><input name="buyCount" class="mini-textbox" enabled="false"/></td>
	            </tr>
	            
	             <tr>
	                <td>税前奖金</td>
	                <td><input name="preBonus" class="mini-textbox" enabled="false"/></td>
	                <td>税后奖金</td>
	                <td><input name="aftBonus" class="mini-textbox" enabled="false"/></td>
	                <td>分佣金额</td>
	                <td colspan="3"><input name="commissionAmount" class="mini-textbox" enabled="false"/></td>
	            </tr>
	            
	            <tr>
	            
	             <td>中奖状态</td>
	                <td><input id="detail_winningStatus" name="winningStatus" class="mini-combobox" enabled="false"/></td>
	            
	                
	             <td>保密类型</td>
	             <td colspan="5">
					  <div name="visibleType" class="mini-radiobuttonlist" repeatItems="2" repeatDirection="vertical"
				    			textField="text" valueField="id" enabled="false" data="[{id:1,text:'公开'},{id:2,text:'跟单公开'},{id:3,text:'开奖公开'}]"></div>
				    </td>
	            </tr>
	           
	            <tr>
	                <td>合买宣言</td>
	                <td colspan="5"><input name="title" class="mini-textbox" enabled="false" style="width:1000px;"/></td>
	               
	            </tr>
	            <tr>
	               <td>合买描述</td>
	               <td colspan="5"><textarea id="description" name="description" class="mini-textarea" enabled="false"  emptyText="输入推荐理由" style="width:1000px;"></textarea></td>
	            </tr>
	        </table>
		</fieldset>
			  
		<fieldset style="border:solid 1px #aaa; padding:3px;height: 500px;">
			  	<legend>合买明细：</legend>
			  	<div id="form2">
			        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
		<table style="width:100%;">
			<tr>
				<td width="5%" style="white-space:nowrap;">
 					  用户名<input id="userName" name="userName" class="mini-textbox"/>
	 				<btn:operate privilege="SEARCH">
			        	<a class="mini-button" onclick="Current.searchGroupUser">查询</a>
		        	</btn:operate>
		        	<btn:operate privilege="EXPORT">
			         	<a class="mini-button" onclick="Current.excelGroupUsers" >导出</a>
		        	</btn:operate>
				</td>
			</tr>
		</table>
			   </div>
		</div>
			    
		  <div id="joinUsersDatagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height: 450px;" 
						  idField="id" multiSelect="true" url="ordermgr/group/userList" showColumnsMenu="true">
						<div property="columns">
							<div type="indexcolumn" align="center" headerAlign="center">序号</div>
							<div field="userName" headerAlign="center" align="center">跟单用户名</div>
							<div field="buyAmount" headerAlign="center" align="center">认购金额</div>
							<div field="buyRatio" headerAlign="center" align="center" numberFormat="p">认购比例</div>
							<div field="buyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">参与合买时间</div>
							<div field="preBonus" headerAlign="center" align="center">税前奖金</div>
							<div field="aftBonus" headerAlign="center" align="center">税后奖金</div>
							<div field="sendBonus" headerAlign="center" align="center">派发奖金</div>
							<div field="buyType" type="comboboxcolumn" headerAlign="center" align="center">
								认购类型<input property="editor" class="mini-combobox" data="Dic.groupBuyType" />
							</div>
							<div field="buyCode" headerAlign="center" align="center">认购编号</div>
							<div field="addedBonus" headerAlign="center" align="center">官方加奖</div>
							<div field="siteAddedBonus" headerAlign="center" align="center">本站加奖</div>
							
						</div>
					</div>
			  </fieldset>
			  
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <legend>操作信息：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td style="width:100px;">修改时间</td>
	                <td><input name="updateTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss" style="width:160px"/></td>
	            		<td style="width:100px;" rowspan="2">备注</td>
	                <td rowspan="2"><input name="remark" id="remark" class="mini-textarea" style="width:500px;" /></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">修改人</td>
	                <td><input name="modifyBy" class="mini-textbox" enabled="false" style="width:160px"/></td>
	            </tr>
	        </table>
			  </fieldset>
	    </div>
	    
	</div>
	
	
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/ordermgr/order_group.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
