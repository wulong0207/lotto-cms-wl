<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>方案管理</title>
  </head>
<body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td>
			     	<btn:operate privilege="UPD|SEARCH">
					<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.editwin()">修改</a>
					</btn:operate>
					<btn:operate privilege="CANCEL_ORDER">
					<a class="mini-button" iconCls="icon-goto" plain="true" onclick="Current.cancelwin()">撤单</a>
					</btn:operate>
					<btn:operate privilege="CANCEL_ORDER">
					<a class="mini-button" iconCls="icon-goto" plain="true" onclick="Current.cancellingRefund()">退款</a>
					</btn:operate>
					<btn:operate privilege="UPD" show="hide">
					修改订单状态：<input id="orderStatus_update"  class="mini-combobox"  style="width:120px;" emptyText="请选择"  valueFromSelect = "true"/>
					<a id="edit_status" class="mini-button"   onclick="Current.updateOrderStatus">确定</a>
					操作：<input id="order_operate"  class="mini-combobox"  style="width:120px;" emptyText="请选择"    valueFromSelect = "true"/>
					<a class="mini-button"   onclick="Current.orderOperate">确定</a>
					</btn:operate>		
				</td>
			</tr>
			<tr>
			   <td>
				  彩种类型：<input id="lotteryCategory"   class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种类型"  allowInput="true" valueFromSelect = "true"  oncloseclick="Current.onCloseClickType" showClose="true" onvaluechanged="Current.lotteryCategoryChange"/> 
				         彩种：<input id="lotteryCode"  class="mini-combobox"  style="width:150px;" 
					   emptyText="请选择彩种"   allowInput="true" valueFromSelect = "true"  oncloseclick="Current.onCloseClickCode" showClose="true" onvaluechanged="Current.lotteryCodeChange"  popupMaxHeight="700"/> 
				         彩期：<input id="issueCode"  class="mini-combobox"  style="width:150px;"
					   emptyText="请选择或输入"    allowInput="true"   oncloseclick="Cms.onCloseClick" showClose="true"/>
				  <input id="detailCodeType"  class="mini-combobox"  style="width:80px;" allowInput="true" valueFromSelect = "true"/>
				  <input id="detailCodeTypeVal" class="mini-textbox" style="width:120px;"/>
				     市场渠道：<input id="channel" class="mini-combobox" style="width:120px;"
				      emptyText="请选择或输入"      allowInput="true"  oncloseclick="Cms.onCloseClick" showClose="true"/>
				     投注平台：<input id="platform" class="mini-combobox" style="width:120px;"
				        emptyText="请选择"    allowInput="false"   oncloseclick="Cms.onCloseClick" showClose="true"/>
				   注册渠道：<input id="registerChannel" class="mini-combobox" style="width:120px;"
							   emptyText="请选择或输入"      allowInput="true"  oncloseclick="Cms.onCloseClick" showClose="true"/>
				  <!-- <input id="scoreType"  class="mini-combobox"  style="width:80px;"  valueFromSelect = "true"/>
				  <input id="sortDesc" class="mini-textbox" style="width:120px;" valueFromSelect = "true"/> -->
				  <div id="dlt" class="mini-checkboxlist" repeatDirection="vertical" repeatLayout="table" data="[{id:'1',text:'大乐透追加'}]" style="display:inline-block;"/>
				</td>
			</tr>
			<tr>
			   <td>
			            方案编号：<input id="orderCode" class="mini-textbox" style="width:100px;"/> 
			              追号计划编号：<input id="orderAddCode" class="mini-textbox" style="width:100px;"/> 
			      <input id="userType"  class="mini-combobox"  style="width:120px;" allowInput="true" valueFromSelect = "true"/>
				  <input id="userTypeVal" class="mini-textbox" style="width:120px;"/>
				  <input id="timeType"  class="mini-combobox"  style="width:100px;"/>
				   <input id="startTime" onButtonclick="MiniCom.queryTime('start','startTime')" class="mini-datepicker"  allowInput="false"  style="width:180px;" 
				   format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" onButtonclick="MiniCom.queryTime('end','endTime')" class="mini-datepicker"  allowInput="false"  style="width:180px;"
				         format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/> 
				  活动编号：<input id="activitySource" class="mini-textbox" style="width:100px;"/> 
				  活动名称：<input id="activityName" class="mini-textbox" style="width:100px;"/>
				  是否允许检票：  <input id="checkTicket"  class="mini-combobox"  style="width:70px;" oncloseclick="Cms.onCloseClick" showClose="true" valueFromSelect = "true" />
			   </td>
			</tr>
			<tr>
			<td>
			     追号方式：<input id="codeWay"  class="mini-combobox" style="width:120px;" emptyText="请选择"    allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true"/>
			     内容类型：<input id="contentType"  class="mini-combobox" style="width:120px;" emptyText="请选择"    allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true"/>
			     购买类型：<input id="buyType"  class="mini-combobox" style="width:120px;"  emptyText="请选择"    allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true"/>
			     支付状态：<input id="payStatus"  class="mini-combobox"  style="width:120px;" emptyText="请选择"    allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true"/>
			     方案状态：<input id="orderStatus"  class="mini-combobox"  style="width:120px;" emptyText="请选择"    allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true" popupMinHeight="230"/>
			     中奖状态：<input id="winStatus"  class="mini-combobox"  style="width:120px;"emptyText="请选择"    allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true"/>
			     奖项：<input id="winGrade"  class="mini-combobox"  style="width:120px;" emptyText="请选择"    allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true"/>
			    <a class="mini-button" onclick="Current.search()">查询</a>
			</td>
			</tr>
		</table>
	</div>
	</div>
	<div class="mini-splitter" vertical="true" style="width:100%;height:87%;" style="border:0;">
		<div size="100%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="ordermgr/basic/list" idField="id" allowResize="true" pageSize="30"  multiSelect="true" 
				showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" onrowdblclick="Current.onrowdblclick"
				showSummaryRow = "true" ondrawsummarycell="Current.onDrawSummaryCell">
				
				<div property="columns">
					<div type="checkcolumn"></div>
					<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
					<div field="lotteryCode"  type= "comboboxcolumn" headerAlign="center" align="center" >彩种名称
					     <input property="editor" class="mini-combobox" data="Dic.allCode" />
					</div>
					<div field="lotteryIssue"  headerAlign="center" align="center">彩票期号</div>
					<div field="nickName"  headerAlign="center" align="center">用户昵称</div>
					<div field="buyType"  type= "comboboxcolumn" headerAlign="center" align="center">购买类型
					     <input property="editor" class="mini-combobox" data="Dic.buyType" />
					</div>
					<div field="categoryId"  type= "comboboxcolumn" headerAlign="center" align="center">投注类型
					     <input property="editor" class="mini-combobox" data="Dic.categoryType" />
					</div>
					<div field="orderCode"  headerAlign="center" align="center" width="180px;">方案编号</div>
					<div field="payStatus"  type= "comboboxcolumn" headerAlign="center" align="center">支付状态
					     <input property="editor" class="mini-combobox" data="Dic.payStatus" />
					</div>
					<div field="orderAmount"  headerAlign="center" align="center" allowSort="true" sortField="order_amount" showSortIcon="true">方案金额(排)</div>
					<div field="orderStatus"  type= "comboboxcolumn" headerAlign="center" align="center">方案状态
					     <input property="editor" class="mini-combobox" data="Dic.orderStatus" />
					</div>
					<div field="winningStatus"  type= "comboboxcolumn" headerAlign="center" align="center">中奖状态
					     <input property="editor" class="mini-combobox" data="Dic.winningStatus" />
					</div>
					<div field="winningDetail"  headerAlign="center" align="center" allowSort="true" sortField="winning_detail" showSortIcon="true">中奖等级(排)</div>
					
					<div field="preBonus"  headerAlign="center" align="center" allowSort="true" dataType="currency" sortField="pre_bonus" showSortIcon="true">税前金额(排)</div>
					<div field="aftBonus"  headerAlign="center" align="center" allowSort="true" dataType="currency" sortField="aft_bonus" showSortIcon="true">税后金额(排)</div>
					<div field="addedBonus"  headerAlign="center" dataType="currency" align="center">加奖金额</div>
					<div field="websiteBonus"  headerAlign="center" dataType="currency" align="center">本站加奖金额</div>
					<div field="buyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true" sortField="buy_time">购买时间(排)</div>	
					<div field="endTicketTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true" sortField="end_ticket_time">出票截止时间(排)</div>
					<div field="channelName"  headerAlign="center" align="center">渠道来源</div>
					<div field="platform" type= "comboboxcolumn"   headerAlign="center" align="center">投注平台
						 <input property="editor" class="mini-combobox" data="Dic.platform" />
					</div>					
					<!-- <div field="updateTime" width="130px;" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="createTime" width="130px;" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div> -->
				</div>
			</div>
		</div>
	</div>
	
	
	<div id="editWindow" class="mini-window" title="方案" 
		style="width:1100px; height:700px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		    <btn:operate privilege="UPD">
			<a class="mini-button" onclick="Current.doSubmit">保存</a>
		    </btn:operate>
		</div>
		<div  id="editform">
		    <input name="url"  class="mini-hidden"/>
			<input name="action"  class="mini-hidden"/>
			<input name="id"  class="mini-hidden"/>
			<input name="orderCode"  class="mini-hidden"/>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
	            <legend >投注信息:<span id="orderCodeShow"></span></legend>
	            <div style="padding:5px;">
	             <table width="100%;">
	                <tr>
	                 <td width="13%">彩种</td>
	                 <td width="20%"><input id = "lotteryCode_edit" name="lotteryCode" class="mini-combobox" emptyText="请选择" valueFromSelect = "true" style="width:100%;" enabled="false"/></td>
	                 <td width="13%">用户昵称</td>
	                 <td width="20%"><input name="nickName" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td width="13%">购买类型</td>
	                 <td width="20%"><input id ="buyType_edit" name="buyType"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true" style="width:100%;" enabled="false"/></td>
	                </tr>
	                 <tr>
	                 <td>彩期</td>
	                 <td><input name="lotteryIssue" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>用户账号</td>
	                 <td><input name="accountName" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>支付状态</td>
	                 <td><input id ="payStatus_edit" name="payStatus"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"  enabled="false"/></td>
	                </tr>
	                 <tr>
	                 <td>购买时间</td>
	                 <td><input name="buyTime" class="mini-datepicker"  allowInput="false"  style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
	                 <td>用户手机号</td>
	                 <td><input name="cusMobile" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>方案状态</td>
	                 <td><input id ="orderStatus_edit" name="orderStatus"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  enabled="false"  style="width:100%;" required="true"/></td>
	                </tr>
	                 <tr>
	                 <td>截止出票时间</td>
	                 <td><input name="endTicketTime" class="mini-datepicker"  allowInput="false"  style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
	                 <td>方案金额</td>
	                 <td><input name="orderAmount" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>中奖状态</td>
	                 <td><input id ="winningStatus_edit" name="winningStatus"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;" enabled="false"/></td>
	                </tr>
	                 <tr>
	                 <td>最终检票时间</td>
	                 <td><input name="endCheckTime" class="mini-datepicker"  allowInput="false"  style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
	                 <td>方案倍数</td>
	                 <td><input name="multipleNum" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>投注平台</td>
	                 <td><input id="platform_edit" name="platform" class="mini-combobox" emptyText="请选择" valueFromSelect = "true" style="width:100%;" enabled="false"/></td>
	                </tr>
	                 <tr>
	                 <td>出票时间</td>
	                 <td><input name="comeOutTime" class="mini-datepicker"  allowInput="false"  style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
	                 <td>拆票数量</td>
	                 <td><input name="splitNum" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>市场渠道</td>
	                 <td><input name="channelName" class="mini-textbox" style="width:100%;" enabled="false" enabled="false"/></td>
	                </tr>
	                 <tr>
	                 <td>开奖时间</td>
	                 <td><input name="lotteryTime" class="mini-datepicker"  allowInput="false"  style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
	                 <td>中奖情况</td>
	                 <td><input name="winningDetail" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>加奖奖金</td>
	                 <td><input name="addedBonus" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	                 <tr>
	                 <td>派奖时间</td>
	                 <td><input name="sendTime" class="mini-datepicker"  allowInput="false"  style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
	                 <td>税前奖金</td>
	                 <td><input name="preBonus" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>支付红包</td>
	                 <td><input name="redCodeUsed" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	                 <tr>
	                 <td>大乐透追加</td>
	                 <td><input id ="isDltAdd_edit" name="isDltAdd"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  enabled="false"  style="width:100%;" required="true"/></td>
	                 <td>税后奖金</td>
	                 <td><input name="aftBonus" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>发放红包</td>
	                 <td><input name="redCodeGet" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	                 <tr>
	                 <td>是否允许检票</td>
	                 <td><input id ="checkTicket_edit" name="checkTicket"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  enabled="false"  style="width:100%;" required="true"/></td>
	                 <td>活动来源</td>
	                 <td>
	                 	<!-- <input name="activityName" class="mini-textbox" style="width:100%;" enabled="false"/> -->
	                 	<input name="activitySource" class="mini-textbox" style="width:100%;" enabled="false"/>
	                 </td>
	                 </tr>
	                 <tr>
	                 <td>最大编号</td>
	                    <td><input name="maxBuyScreen" class="mini-textbox" style="width:100%;" /></td>
	                    <td>场次编号</td>
	                    <td colspan="3"><input name="buyScreen" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 </tr>
	            </table>            
	            </div>
	        </fieldset>
            <fieldset id="sportContent" style="border:solid 1px #aaa; padding:3px;">
	            <legend >方案内容和开奖结果：</legend>
	            <div style="padding:5px;">
	              <table width="100%;">
	                  <tr>
	                    <td width="10%">开奖结果</td>
	                    <td><input name="drawCode" class="mini-textbox asLabel" style="width:100%;" readonly="readonly"/></td>
	                  </tr>
	                  <tr>
	                    <td>投注内容</td>
	                    <td><input name="bettingContentUrl" class="mini-textbox" style="width:40%;" readonly="readonly"/>
	                    <a  target="_blank" id="contentUrl" href="">查看</a>
	                    </td>
	                  </tr>
	                  <tr>
	                    <td>竞彩内容转换</td>
	                    <td><a onclick="Current.lookSportScreen()">查看</a></td>
	                  </tr>
	              </table>            
	            </div>
	        </fieldset>
	        <fieldset id="numHighContent" style="border:solid 1px #aaa;padding:3px;">
            <legend >方案内容和开奖结果：<span id="drawCode_edit"><span></legend>
            <div class="mini-splitter" vertical="true" style="width:100%;height:250px;" style="border:0;">
				<div size="95%" showCollapseButton="true" style="border:0;">
					<div id="contentgrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
						 url="ordermgr/basic/content/detail/list" idField="id" allowResize="true" showColumnsMenu="true" pageSize="10" multiSelect="true">
						
						<div property="columns">
							<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
							<div field="planContent"  headerAlign="center" align="center">方案内容</div>
							<div field="multiple"  headerAlign="center" align="center">倍数</div>
							<div field="amount"  headerAlign="center" align="center">金额</div>
							<div field="playIntro"  headerAlign="center" align="center">玩法</div>
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
            <legend >操作信息：</legend>
            <div style="padding:5px;">
             <table width="100%;">
			<tr>
			  <td width="13%">修改时间</td>
			  <td width="20%"><input name="modifyTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
			  <td width="13%" rowspan="2">备注</td>
			  <td width="20%" rowspan="2"><input name="remark" class="mini-textarea" vtype ="maxLength:200" style="width:100%;"/></td>
			  <td width="13%"></td>
			  <td width="20%"></td>
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
    
    <div id="sportEditWindow" class="mini-window" title="竞彩内容转换"
		style="width:1100px; height:300px;" showMaxButton="false"
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		<div class="mini-splitter" vertical="true" style="width:100%;height:250px;" style="border:0;">
				<div size="95%" showCollapseButton="true" style="border:0;">
					<div id="sportcontentgrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
						 url="ordermgr/basic/content/sport/list" idField="id" allowResize="true" showColumnsMenu="true" pageSize="10" multiSelect="true">
						
						<div property="columns">
							<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
							<div field=""  headerAlign="center" align="center">场次编号</div>
							<div field=""  headerAlign="center" align="center">主队</div>
							<div field=""  headerAlign="center" align="center">客队</div>
							<div field=""  headerAlign="center" align="center">半/全比分</div>
							<div field=""    headerAlign="center" align="center">投注类容（胆拖）</div>
							<div field=""    headerAlign="center" align="center">彩果</div>
							<div field=""    headerAlign="center" align="center">sp值</div>
						</div>
					</div>
				</div>
			</div>
    </div>
    
    <div id="upoladWindow" class="mini-window" title="上传方案" 
		style="width:250px; height:200px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
				
		  <div id="orderForm">
		        <table width="100%;">
		         <input  id="fileOrder" class="mini-htmlfile" name="file"   limitType="*.txt" style="width:180px;" required="true"/>
		         <btn:operate privilege="UPLOAD&ADD">
			     <a class="mini-button"   onclick="Current.ajaxFileUploadSearch('fileOrder')">确定</a>
			     </btn:operate>
			     <br>上传查询说明：<br>1.必须是“ .txt ”文件 ；
			     </table>
		  </div>
	</div>
	
    <div id="cancelWindow" class="mini-window" title="撤单操作" 
		style="width:500px; height:180px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
				
		  <div id="cancelOrderForm">
		        <table width="100%;" align="center" style="padding:5px;padding-right:15px;">
		        	<tr style="padding:5px;">
		        		<td>撤单对象</td>
		        		<td>
		        			是否对<span id="cancelInfo" style="color:red;"></span>进行撤单操作?
		        		</td>
		        	</tr>
		        	<tr style="padding:5px;">
		        		<td>撤单原因</td>
		        		<td>
		        			<input id="orderCancelType" class="mini-combobox"  style="width:120px;"   allowInput="true" valueFromSelect="true" onValuechanged="Current.cotChange();"/>
		        		</td>
		        	</tr>
		        	<tr style="padding:5px;">
		        		<td></td>
		        		<td>
		        			<input id="cancelDesc" class="mini-textbox" style="width:100%;" vtype="maxLength:50" visible="false"/>
		        		</td>
		        	</tr>
		        	<tr>
		        		<td colspan="2" align="right" style="padding:5px;">
				         <btn:operate privilege="CANCEL_ORDER">
					     <a class="mini-button"  onclick="Current.cancelOrder()">确定</a>
					     </btn:operate>
		        		</td>
		        	</tr>		        			        	
			    </table>
		  </div>
	</div>
	
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/ordermgr/basic.js??vsersion=<%=version%>" type="text/javascript"></script>
</html>