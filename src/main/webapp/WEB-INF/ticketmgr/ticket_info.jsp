<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>票管理</title>
  </head>
  
  <body>
  	<div id="searchForm" class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
				<td>
			        <%-- <btn:operate privilege="SEARCH">
					<a class="mini-button" iconCls="icon-search" plain="true" onclick="TicketObj.viewTicket()">查看详情</a>
					</btn:operate> --%>
			     	<btn:operate privilege="UPD" show="hide">
					<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="TicketObj.editTicket()">修改</a>
					修改票状态：<input id="edit_status"  class="mini-combobox"  style="width:120px;" emptyText="请选择"  valueFromSelect = "true"/>
					<a class="mini-button"   onclick="TicketObj.updateTicketStatus">确定</a>
					操作：<input id="ticket_operate"  class="mini-combobox"  style="width:120px;" emptyText="请选择" onvaluechanged="TicketObj.changeOperate"   valueFromSelect = "true"/>
					<input id="ticket_operate_channel"  class="mini-combobox"  style="width:120px;" emptyText="请选择出票商" />
					<a class="mini-button"   onclick="TicketObj.ticketOperate">确定</a>
					</btn:operate>
				</td>
			</tr>
			
			<tr>
			   <td>
				  彩种类型：<input id="lotteryCategory" class="mini-combobox" style="width:150px;"
					   emptyText="请选择彩种类型" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="TicketObj.closeLotCategory" onvaluechanged="TicketObj.changeLotCategory"/> 
				         彩种：<input id="lotteryCode" class="mini-combobox" style="width:150px;"
					   emptyText="请选择彩种" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="TicketObj.closeLotCode" onvaluechanged="TicketObj.changeLotCode" popupMaxHeight="700"/> 
				         彩期：<input id="lotteryIssue" class="mini-combobox" style="width:150px;"
					   emptyText="请选择或输入" allowInput="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					           子玩法<input id="lotteryChildCode" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					   <input id="buyScreenType" class="mini-combobox" style="width:80px;" allowInput="false" valueFromSelect="true"/>
				  	   <input id="buyScreenTypeVal" class="mini-textbox" style="width:120px;"/>
				     票金额：<input id="minQryMoney" class="mini-textbox" style="width:50px;" vtype="float"/> 至 <input id="maxQryMoney" class="mini-textbox" style="width:50px;" vtype="float"/>
				 	   <div id="querySpecialCondition" class="mini-checkboxlist" repeatDirection="vertical" repeatLayout="table" data="[{id:'1',text:'回执为空'},{id:'2',text:'切换出票商'}]" style="display:inline-block;"/>
				</td>
			</tr>
			
			<tr>
			   <td>
			              方案编号：<input id="orderCode" class="mini-textbox" style="width:150px;"/>
			              本地票号：<input id="ticketId" class="mini-textbox" style="width:150px;"/>
			      <input id="userType" class="mini-combobox" style="width:120px;" allowInput="false" valueFromSelect="true"/>
				  <input id="userTypeVal" class="mini-textbox" style="width:120px;"/>
				  <input id="timeType" class="mini-combobox" style="width:100px;" allowInput="false" valueFromSelect="true"/>
				  <input id="startQryTime" onButtonclick="MiniCom.queryTime('start','startQryTime')" class="mini-datepicker" allowInput="false" style="width:180px;" 
				   format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick" />
				        到<input id="endQryTime"  onButtonclick="MiniCom.queryTime('end','endQryTime')" class="mini-datepicker" allowInput="false" style="width:180px;"
				   format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick" /> 
 出票渠道：<input id="channelId" class="mini-combobox" emptyText="请选择出票渠道" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
			   </td>
			</tr>
			
			<tr>
			   <td>
			                批次号：<input id="batchNum" class="mini-textbox" style="width:150px;"/>
			                第三方票号：<input id="thirdNum" class="mini-textbox" style="width:150px;"/>
			            官方编号：<input id="officialNum" class="mini-textbox" style="width:150px;"/>
			            内容类型：<input id="contentType" class="mini-combobox" style="width:120px;" emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" />
			                票状态：<input id="ticketStatus" class="mini-combobox" style="width:120px;" emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" />
			            中奖状态：<input id="winningStatus" class="mini-combobox" style="width:120px;" emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" />
			                    奖项：<input id="winningDetail" class="mini-combobox" style="width:120px;" emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" />
			    	<a class="mini-button" onclick="TicketObj.search()">查询</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:87%;" style="border:0;">
		<div size="100%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="ticketmgr/ticketinfo/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" 
				onpreload="MiniCom.onpreload" onrowdblclick="TicketObj.onrowdblclick()"
				showSummaryRow="true" ondrawsummarycell="TicketObj.drawSummaryCell">
				
				<div property="columns">
					<div type="checkcolumn"></div>
					<div type="indexcolumn" headerAlign="center" align="center">序号</div>
					<div field="lotteryCode" headerAlign="center" align="center" type="comboboxcolumn">彩种名称
					     <input property="editor" class="mini-combobox" data="Dic.allCode" />
					</div>
					 <div field="lotteryChildCode" headerAlign="center" align="center" type="comboboxcolumn">子玩法
					     <input property="editor" class="mini-combobox" data="Dic.lotteryChildCodeAll" />
					</div>		
					<div field="lotteryIssue" headerAlign="center" align="center">彩期编号</div>
					<div field="user.nickName" headerAlign="center" align="center">用户昵称</div>
					<div field="id"  headerAlign="center" align="center">本地票号</div>
					<div field="ticketMoney"  headerAlign="center" align="center" dataType="currency" allowSort="true" sortField="ticket_money" showSortIcon="true">票金额(排)</div>
					<div field="ticketStatus" headerAlign="center" align="center" type="comboboxcolumn">票状态
					     <input property="editor" class="mini-combobox" data="Dic.ticketStatus" />
					</div>
					<div field="ticketChannel.drawerName" headerAlign="center" align="center">出票渠道名称</div>
					<div field="thirdNum" headerAlign="center" align="center">第三方票号</div>
					<div field="orderCode" headerAlign="center" align="center" width="170px;">所属方案编号</div>
					<div field="winningStatus" headerAlign="center" align="center" type="comboboxcolumn">中奖状态
					     <input property="editor" class="mini-combobox" data="Dic.winningStatus" />
					</div>
					<div field="preBonus"  headerAlign="center" align="center" dataType="currency" allowSort="true" sortField="pre_bonus" showSortIcon="true">税前金额(排)</div>
					<div field="aftBonus" headerAlign="center" align="center" dataType="currency" allowSort="true" sortField="aft_bonus" showSortIcon="true">税后金额(排)</div>
					<div field="addedBonus" headerAlign="center" align="center" dataType="currency" >加奖金额</div>
					<div field="createTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true" sortField="create_time" showSortIcon="true">票生成时间(排)</div>	
					<div field="endTicketTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true" sortField="end_ticket_time" showSortIcon="true">出票截止时间(排)</div>
					<div field="winningDetail"  headerAlign="center" align="center" allowSort="true" sortField="winning_detail" showSortIcon="true" >奖项(排)</div><!--renderer="TicketObj.renderWinDetail"  -->
					<!-- <div field="modifyTime" width="130px;" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div> -->
				</div>
			</div>
		</div>
	</div>
	
	<div id="editWindow" class="mini-window" title="票详情" 
		style="width:1300px; height:750px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		    <btn:operate privilege="UPD">
			<a class="mini-button" onclick="TicketObj.doSubmit">保存</a>
		    </btn:operate>
		</div>
		
		<div id="editForm">
		    <input name="url"  class="mini-hidden"/>
			<input name="action"  class="mini-hidden"/>
			<input name="id"  class="mini-hidden"/>
		   
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
	            <legend>票信息:</legend>
	            <div style="padding:5px;">
	             <table width="100%;">
	                <tr>
	                 <td width="13%">彩种</td>
	                 <td width="20%"><input id="lotteryCode_edit" name="lotteryCode" class="mini-combobox" emptyText="请选择" valueFromSelect="true" style="width:100%;" enabled="false"/></td>
	                 <td width="13%">用户昵称</td>
	                 <td width="20%"><input name="user.nickName" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td width="13%">内容类型</td>
	                 <td width="20%">
	                 	<input id="contentType_edit" name="contentType" class="mini-combobox" emptyText="请选择" valueFromSelect="true" style="width:50%;" enabled="false"/>
	                 	<div name="lottoAdd" class="mini-checkboxlist" repeatDirection="vertical" repeatLayout="table" data="[{id:'1',text:'大乐透追号'}]" style="display:inline-block;" enabled="false"/>
	                 </td>
	                </tr>
	                
	                <tr>
	                 <td>彩期</td>
	                 <td><input name="lotteryIssue" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>用户账号</td>
	                 <td><input name="user.accountName" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>票状态</td>
	                 <td><input id="ticketStatus_edit" name="ticketStatus" class="mini-combobox" emptyText="请选择" valueFromSelect="true" style="width:100%;" enabled="false" /></td>
	                </tr>
	                
	                <tr>
	                 <td>票生成时间</td>
	                 <td><input name="createTime" class="mini-datepicker" allowInput="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
	                 <td>用户手机号</td>
	                 <td><input name="user.cusMobile" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>中奖状态</td>
	                 <td><input id="winningStatus_edit" name="winningStatus" class="mini-combobox" emptyText="请选择" valueFromSelect="true" style="width:100%;" enabled="false"/></td>
	                </tr>
	                
	                <tr>
	                 <td>出票截止时间</td>
	                 <td><input name="endTicketTime" class="mini-datepicker" allowInput="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"  timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/></td>
	                 <td>票金额</td>
	                 <td><input name="ticketMoney" class="mini-textbox" style="width:100%;" enabled="false" /></td>
	                 <td>中奖情况</td>
	                 <td><input name="winningDetail" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	                
	                <tr>
	                 <td>送票时间</td>
	                 <td><input name="sendChannelTime" class="mini-datepicker" allowInput="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
	                 <td>票倍数</td>
	                 <td><input name="multipleNum" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>税前奖金</td>
	                 <td><input name="preBonus" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	                
	                <tr>
	                 <td>出票成功时间</td>
	                 <td><input name="comeOutTime" class="mini-datepicker" allowInput="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" /></td>
	                 <td>所属方案</td>
	                 <td><input name="orderCode" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>税后奖金</td>
	                 <td><input name="aftBonus" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	                
	                <tr>
	                 <td>票回执时间</td>
	                 <td><input name="receiptTime" class="mini-datepicker" allowInput="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
	                 <td>送票批次号</td>
	                 <td><input name="batchNum" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>加奖奖金</td>
	                 <td><input name="addedBonus" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	                
	                <tr>
	                 <td>开奖时间</td>
	                 <td><input name="lotteryTime" class="mini-datepicker" allowInput="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
	                 <td>第三方票序号</td>
	                 <td><input name="thirdNum" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>红包ID</td>
	                 <td><input name="redCode" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	                
	                <tr>
	                 <td>派奖时间</td>
	                 <td><input name="sendTime" class="mini-datepicker" allowInput="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
	                 <td>官方票面流水号</td>
	                 <td><input name="officialNum" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>出票商返回备注</td>
	                 <td><input name="channelRemark" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	                
	                <tr>
	                 <td>出票渠道</td>
	                 <td><input name="ticketChannel.drawerName" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                 <td>分配送票时间</td>
	                 <td><input name="channelAllocationSendTime" class="mini-datepicker" allowInput="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false"/></td>
	                 <td>积分兑换码</td>
	                 <td><input name="redeemCode" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	                <tr>
	                 <td>切票记录</td>
	                 <td colspan="5"><input name="ticketChange" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
	            </table>            
	            </div>
	        </fieldset>
	        
            <fieldset style="border:solid 1px #aaa;padding:3px;">
            	<legend>票内容：</legend>
            	<div style="padding:2px;">
	             <table width="100%;">
	                <tr>
	                 <td width="13%">投注内容</td>
	                 <td ><input name="ticketContent" class="mini-textarea" style="width:100%;" enabled="false"/></td>
	                </tr>
	                <tr>
	                 <td width="13%">回执内容</td>
	                 <td ><input name="receiptContent" class="mini-textarea" style="width:100%;" vtype="rangeChar:1,3000"/></td>
	                </tr>
	                <tr>
	                 <td width="13%">回执内容详情(竞彩)</td>
	                 <td ><input name="receiptContentDetail" class="mini-textarea" style="width:100%;" vtype="rangeChar:1,3000"/></td>
	                </tr>
	            </table>            
	            </div>
            </fieldset>
            
            <fieldset style="border:solid 1px #aaa;padding:3px;">
            	<legend>票样信息：</legend>
            	<div style="padding:5px;height:100px;">
	             <table width="100%;" height="100%;">
	                <tr>
	                 <td width="13%">票图片</td>
	                 <td width="35%">
						<img id="preTicketImg" alt="票图片" width="100%;" height="70px;"><br/>
		            	<input id="ticketImg_edit" name="ticketImg" class="mini-textbox" style="width:45%;" allowInput="false" vtype="rangeChar:1,100" requiredErrorText="请先上传图片"/>
			            <btn:operate privilege="UPLOAD">
						<input type="button" value="上传" onclick="TicketObj.ajaxFileUpload()"/>
						</btn:operate>
	                 </td>
	                 <td width="13%">票内容</td>
	                 <td ><input name="ticketImgTxt" class="mini-textarea" style="width:100%;height:100%;" vtype="rangeChar:1,100"/></td>
	                </tr>
	            </table>            
	            </div>
            </fieldset>
	        
	        <fieldset style="border:solid 1px #aaa;padding:3px;">
            	<legend>操作信息：</legend>
            	<div style="padding:5px;">
             	<table width="100%;">
             		<tr>
	                 <td width="13%">修改时间</td>
	                 <td width="35%"><input name="modifyTime" class="mini-datepicker" allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
	                 <td width="13%">备注</td>
	                 <td colspan="3" rowspan="2"><input name="ticketRemark" class="mini-textarea" style="width:100%;" vtype="maxLength:100"/></td>
	                </tr>
	            
		            <tr>
	                 <td >修改人</td>
	                 <td ><input name="modifyBy" class="mini-textbox" style="width:100%;" enabled="false"/></td>
	                </tr>
           	 	</table>            
            	</div>
        	</fieldset>
            
		</div>
    </div>
    <div id="uploadTicketWindow" class="mini-window" title="上传票号" 
		style="width:400px; height:150px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		    <btn:operate privilege="UPD">
				<a class="mini-button" onclick="TicketObj.uploadTicket">上传</a>
		    </btn:operate>
		</div>
		<div id="uploadTikcetForm">
		<table width="100%;">
			<input name="url"  class="mini-hidden"/>
			<input name="action"  class="mini-hidden"/>
			<tr>
				<td width="30%"><input id="ticketType" name="ticketType" class="mini-combobox" emptyText="请选择" valueFromSelect="true" style="width:100%;"  required="true" /></td>
				<td width="70%"><input name="ticketNo" class="mini-textbox" style="width:100%;"  required="true" /></td>
			</tr>
		</table>
		</div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/ticketmgr/ticket_info.js?vsersion=1<%=version%>" type="text/javascript"></script>
</html>