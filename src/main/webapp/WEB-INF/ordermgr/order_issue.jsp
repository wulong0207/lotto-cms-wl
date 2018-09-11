<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>抄单管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="UPD">
		        <a class="mini-button" id="recommendBtn" iconCls="icon-edit" plain="true" onclick="order_issue.visitRecommend(1)">推荐</a>
	        </btn:operate>
	        <btn:operate privilege="UPD">
		        <a class="mini-button" id="cancelRecommendBtn" style="display:none;" iconCls="icon-edit" plain="true" onclick="order_issue.visitRecommend(0)">取消推荐</a>
	        </btn:operate>
	        <btn:operate privilege="UPD">
		        <a class="mini-button" id="topBtn" iconCls="icon-edit" plain="true" onclick="order_issue.visitTop(1)">置顶</a>
	        </btn:operate>
	        <btn:operate privilege="UPD">
		        <a class="mini-button" id="cancelTopBtn" style="display:none;" iconCls="icon-edit" plain="true" onclick="order_issue.visitTop(0)">取消置顶</a>
	        </btn:operate>
				</td>
			</tr>
			<tr>
				<td width="5%" style="white-space:nowrap;">
				         彩种类型<input id="lotteryCategory"  name="lotteryCategory" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种类型" allowInput="true" valueFromSelect ="true"  oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="order_issue.lotteryCategoryChange"/> 
				         彩种<input id="lotteryCode" name="lotteryCode" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种" allowInput="true" valueFromSelect ="true" oncloseclick="Cms.onCloseClick" showClose="true" onvaluechanged="order_issue.lotteryCodeChange"/> 
				         彩期<input id="lotteryIssue"  name="lotteryIssue" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择" allowInput="true" oncloseclick="Cms.onCloseClick" showClose="true"/> 
 					  推单编号<input name="orderCode" class="mini-textbox" style="width:200px;"/>
 					  跟单编号<input name="followOrderCode" class="mini-textbox" style="width:200px;"/>
				</td>
			</tr>
			<tr>
				<td>
					<input id="userType" name="userType" class="mini-combobox" valueFromSelect ="true"/>
				 	<input name="userTypeValue" class="mini-textbox" style="width:200px;"/> 
					<input id="timeType"  name="timeType" class="mini-combobox"  style="width:150px;" valueFromSelect ="true"/>
					<input name="startTime" class="mini-datepicker" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input name="endTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>  
					中奖状态：<input id="winStatus" name="winningStatus" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择"  valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					<a class="mini-button" onclick="order_issue.search" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:87%;" 
		 url="ordermgr/issue/list" idField="id" allowResize="true"
		 showColumnsMenu="true" showFilterRow="false" onrowdblclick="order_issue.toEdit" onrowclick="order_issue.dispalyBtn">
		<div property="columns">
			<div type="checkcolumn" width="5%"></div>
			<div type="indexcolumn" width="5%" align="center" headerAlign="center">序号</div>
			<div field="lotteryCode" type="comboboxcolumn" headerAlign="center" align="center">彩种名称
		     <input property="editor" class="mini-combobox" data="Dic.lottery" />
			</div>
			<div field="lotteryIssue" headerAlign="center" align="center">期号</div>
			<div field="nickName" headerAlign="center" align="center">用户昵称</div>
			<div field="buyType" type="comboboxcolumn" headerAlign="center" align="center">购买类型
		     <input property="editor" class="mini-combobox" data="Dic.buyType" />
			</div>
			<div field="level" type="comboboxcolumn" headerAlign="center" align="center">用户标识
		     <input property="editor" class="mini-combobox" data="Dic.level" />
			</div>
			<div field="orderCode" headerAlign="center" align="center" width="180px;">方案编号</div>
			<div field="orderAmount" headerAlign="center" align="center">方案金额</div>
			<div field="aftBonus" headerAlign="center" align="center">税后金额</div>
			<div field="maxRoi" headerAlign="center" align="center" numberFormat="p">回报率</div>
			<div field="orderStatus" type="comboboxcolumn" headerAlign="center" align="center">方案状态
		     <input property="editor" class="mini-combobox" data="Dic.orderStatus" />
			</div>
			<div field="winningStatus" type="comboboxcolumn" headerAlign="center" align="center">中奖状态
		     <input property="editor" class="mini-combobox" data="Dic.winningStatus" />
			</div>
			<div field="commissionRate" headerAlign="center" align="center">提成比例</div>
			<div field="commissionAmount" headerAlign="center" align="center">提成金额</div>
			<div field="buyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">购买时间</div>	
			<div field="isRecommend" type="comboboxcolumn" headerAlign="center" align="center">推荐
		     <input property="editor" class="mini-combobox" data="Dic.isRecommend" />
			</div>
			<div field="isTop" type="comboboxcolumn" headerAlign="center" align="center">置顶
		     <input property="editor" class="mini-combobox" data="Dic.isTop" />
			</div>
		</div>
	</div>
	
	<div id="detailWindow" showToolbar="true" class="mini-window" style="width:1100px;height:750px" showFooter="true" showModal="true" allowResize="true" allowDrag="true" showCloseButton="true">
	    <div property="toolbar" style="text-align:right;padding:2px;padding-right:15px;">
		<btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-save"  onclick="order_issue.update">保存</a>
	        </btn:operate>    </div>
	    <div id="detailForm" class="form">
	    <input name="id" id="id" class="mini-hidden"/>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <legend>投注信息：</legend>
			    <table>
	            <tr>
	                <td>彩种</td>
	                <td><input id="detailLotteryCode" name="lotteryCode" class="mini-combobox" enabled="false"/></td>
	                <td>用户昵称</td>
	                <td><input name="nickName" class="mini-textbox" enabled="false"/></td>
	                <td>用户标识</td>
	                <td><input id="level" name="level" class="mini-combobox" enabled="false"/></td>
	            </tr>
	            <tr>
	                <td>投注类型</td>
	                <td><input id="categoryId" name="categoryId" class="mini-combobox" enabled="false"/></td>
	                <td>彩期</td>
	                <td><input name="lotteryIssue" class="mini-textbox" enabled="false"/></td>
	                <td>购买类型</td>
	                <td><input id="buyType" name="buyType" class="mini-combobox" enabled="false"/></td>
	            </tr>
	            <tr>
	                <td>购买时间</td>
	                <td><input name="buyTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	                <td>用户帐号</td>
	                <td><input name="accountName" class="mini-textbox" enabled="false"/></td>
	                <td>出票状态</td>
	                <td><input id="orderStatus" name="orderStatus" class="mini-combobox" enabled="false"/></td>
	            </tr>
	            <tr>
	                <td>方案金额</td>
	                <td><input name="orderAmount" class="mini-textbox" enabled="false"/></td>
	                <td>方案倍数</td>
	                <td><input name="multipleNum" class="mini-textbox" enabled="false"/></td>
	                <td>中奖状态</td>
	                <td><input id="winningStatus" name="winningStatus" class="mini-combobox" enabled="false"/></td>
	            </tr>
	            <tr>
	                <td>税前奖金</td>
	                <td><input name="preBonus" class="mini-textbox" enabled="false"/></td>
	                <td>税后奖金</td>
	                <td><input name="aftBonus" class="mini-textbox" enabled="false"/></td>
	                <td>跟单人数</td>
	                <td><input name="followNum" class="mini-textbox" enabled="false"/></td>
	            </tr>
	            <tr>
	                <td>提成比例</td>
	                <td><input name="commissionRate" class="mini-textbox" enabled="false"/></td>
	                <td>跟单金额</td>
	                <td><input name="followAmount" class="mini-textbox" enabled="false"/></td>
	                <td>是否置顶</td>
	                <td>
	                <div id="isTop" name="isTop" class="mini-radiobuttonlist" repeatItems="2" repeatDirection="vertical"
    textField="text" valueField="id">
   
</div> 
	                </td>
	            </tr>
	            <tr>
	                <td>提成金额</td>
	                <td><input name="commissionAmount" class="mini-textbox" enabled="false"/></td>
	                <td>最高回报率</td>
	                <td><input name="maxRoi" class="mini-textbox" numberFormat="p" enabled="false"/></td>
	                <td>是否推荐</td>
	                <td>
	                <div id="isRecommend" name="isRecommend" class="mini-radiobuttonlist" repeatItems="2" repeatDirection="vertical"
    textField="text" valueField="id">
    
</div> 
	                </td>
	            </tr>
	            <tr>
	                <td>可见情况</td>
	                <td rowspan="3">
	                <div name="orderVisibleType" class="mini-radiobuttonlist" repeatItems="2" repeatDirection="vertical"
    textField="text" valueField="id" enabled="false"
    data="[{id:2,text:'全部可见'},{id:3,text:'抄单人可见'},{id:1,text:'开奖后可见'},{id:4,text:'关注人可见'}]" >
</div> 
	                </td>
	            </tr>
	        </table>
			  </fieldset>
			  
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <legend>推荐理由：
			    <btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-save"  onclick="order_issue.updateRecommendReason">保存</a>
	        </btn:operate>
	        <btn:operate privilege="UPD">
		        <a class="mini-button" id="showReasonBtn" iconCls="icon-edit" onclick="order_issue.updateRecommendReasonShow(1)">显示</a>
	        </btn:operate>
	        <btn:operate privilege="UPD">
		        <a class="mini-button" id="hideReasonBtn" iconCls="icon-edit" onclick="order_issue.updateRecommendReasonShow(0)">隐藏</a>
	        </btn:operate>
			    </legend>
			    <table>
	            <tr>
	            	<td>
	            	<textarea id="recommendReason" name="recommendReason" class="mini-textarea"  emptyText="输入推荐理由" style="width:1000px;"></textarea>
	            	</td>
	            </tr>
	        </table>
			  </fieldset>
			  
			  <fieldset style="border:solid 1px #aaa; padding:3px;height: 500px;">
			  	<legend>抄单明细：</legend>
			  	<div id="form2">
			        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
			            <table style="width:100%;">
			<tr>
				<td width="5%" style="white-space:nowrap;">
				         方案状态<input id="orderStatus2" name="orderStatus2" class="mini-combobox"
					   emptyText="请选择" valueFromSelect ="true"  oncloseclick="Cms.onCloseClick" showClose="true"/> 
 					  用户账户<input name="accountName2" class="mini-textbox"/>
 					  方案编号<input name="orderCode2" class="mini-textbox"/>
 				<btn:operate privilege="SEARCH">
		        <a class="mini-button" onclick="order_issue.pageFollow">查询</a>
	        	</btn:operate>
	        	<btn:operate privilege="EXPORT">
		         	<a class="mini-button" onclick="order_issue.excelFollow" >导出</a>
	        	</btn:operate>
				</td>
			</tr>
			            </table>           
			        </div>
			    </div>
			    
		  		<div id="followedDatagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height: 450px;" 
						  idField="id" multiSelect="true" url="ordermgr/followed/page"
						 showColumnsMenu="true">
						<div property="columns">
							<div type="checkcolumn" width="3%"></div>
							<div type="indexcolumn" align="center" headerAlign="center">序号</div>
							<%--
							<div field="type" type="comboboxcolumn" headerAlign="center" align="center">类型
						     <input property="editor" class="mini-combobox" data="" />
							</div>
							--%>
							<div field="orderAmount" headerAlign="center" align="center">抄单金额</div>
							<div field="orderStatus" type="comboboxcolumn" headerAlign="center" align="center">出票状态
						     <input property="editor" class="mini-combobox" data="Dic.orderStatus" />
							</div>
							<div field="orderCode" headerAlign="center" align="center">方案编号</div>
							<div field="preBonus" headerAlign="center" align="center">税前奖金</div>
							<div field="aftBonus" headerAlign="center" align="center">税后奖金</div>
							<div field="commissionAmount" headerAlign="center" align="center">推单人提成</div>
							<%--<div field="typeKey" headerAlign="center" align="center">平台提成</div>--%>
							<div field="sendAmount" headerAlign="center" align="center">派发奖金</div>
							<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">抄单时间</div>
						</div>
						</div>
			  </fieldset>
			  
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <legend>操作信息：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td style="width:100px;">修改时间</td>
	                <td style="width:150px;"><input name="modifyTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            		<td style="width:100px;" rowspan="2">备注</td>
	                <td style="width:150px;" rowspan="2"><input name="remark" id="remark" class="mini-textarea"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">修改人</td>
	                <td style="width:150px;"><input name="modifyBy" class="mini-textbox" enabled="false"/></td>
	            </tr>
	        </table>
			  </fieldset>
	    </div>
	    
	</div>
	
	
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/ordermgr/order_issue.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
