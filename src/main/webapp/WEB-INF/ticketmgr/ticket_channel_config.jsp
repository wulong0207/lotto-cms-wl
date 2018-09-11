<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>出票渠道配置</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;" id="searchCondition">
			<tr>
			     <td width="15%" nowrap="nowrap"> 
			        <btn:operate privilege="ADD">
					<a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="Current.addwin()">添加</a>
					</btn:operate> 	
			     	<btn:operate privilege="UPD|SEARCH">
					<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.editwin()">修改</a>
					</btn:operate>
				</td>
			</tr>
			<tr>
			<td width="5%" style="white-space:nowrap;">
				     彩种类型：<input id="lotteryCategory" name="lotteryCategory"   class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种类型"  allowInput="true" valueFromSelect = "true"  oncloseclick="Current.onCloseClickType" showClose="true" onvaluechanged="Current.lotteryCategoryChange"/> 
				         彩种：<input id="lotteryCode"  name="lotteryCode" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种"   allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true"/>  
				       出票商名称：<input id="ticketChannelId"  name="ticketChannelId" class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				       出票状态：<input id="sendStatus"   name = "sendStatus" class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
				 <a class="mini-button" onclick="Current.search()">查询</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="90%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="ticketmgr/ticketconfig/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" onrowdblclick="Current.onrowdblclick">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="ticketChannelId"  type= "comboboxcolumn" headerAlign="center" align="center">出票商名称
					     <input property="editor" class="mini-combobox" data="Dic.ticketChannelId" />
					</div>
					<div field="lotteryCode"  type= "comboboxcolumn" headerAlign="center" align="center">彩种名称
					     <input property="editor" class="mini-combobox" data="Dic.allCode" />
					</div>
					<div field="sendStatus"  type= "comboboxcolumn" headerAlign="center" align="center">出票状态
					     <input property="editor" class="mini-combobox" data="Dic.sendStatus" />
					</div>
					<div field="preBatch"  headerAlign="center" align="center">批号前缀</div>
					<div field="preTicket"  headerAlign="center" align="center">票号前缀</div>
					<div field="sendWeight"  headerAlign="center" align="center">送票权重</div>
					<div field="sendEachBatch"  headerAlign="center" align="center" numberFormat="n">每批数量</div>
					<div field="threadCount"  headerAlign="center" align="center">送票线程数</div>
					<div field="searchAuto"  type= "comboboxcolumn" headerAlign="center" align="center">主动查票
					     <input property="editor" class="mini-combobox" data="Dic.lotteryAuto" />
					</div>
					<div field="searchMaxTicket"  headerAlign="center" align="center">查票最大票数</div>
					<div field="endSendSpace"  headerAlign="center" align="center">截止送票间隔</div>
					<div field="stopLotteryName" headerAlign="center" align="center">停送玩法</div>
					<div field="remark" headerAlign="center" align="center">备注</div>	
				</div>
			</div>
		</div>
	</div>
	
	<div id="editWindow" class="mini-window" title="出票配置详情" 
		style="width:1100px; height:670px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		    <btn:operate privilege="ADD|UPD">
			<a class="mini-button" onclick="Current.doSubmit">保存</a>
			</btn:operate>
		</div>
		<div  id="editform">
			 <input name="id"  class="mini-hidden"/>
		     <input name="url"  class="mini-hidden"/>
			 <input name="action"  class="mini-hidden"/>
			 <input id ="allowSendTime_edit" name="allowSendTime"  class="mini-hidden"/>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
	            <legend >出票配置详情：</legend>
	            <div style="padding:5px;">
	             <table width="100%;">
	            <tr>
	            <td width="13%">出票渠道ID</td>
	            <td width="20%"><input name="ticketChannelId_show" class="mini-textbox" style="width:100%;" required="true" vtype ="maxLength:20" enabled="false"/></td>
	            <td width="13%">批次号前缀</td>
	            <td width="20%"><input name="preBatch" class="mini-textbox" style="width:100%;" required="true" /></td>
	            <td width="13%" >送票权重</td>
	            <td width="20%" ><input name="sendWeight" class="mini-textbox" vtype ="int" style="width:100%;" required="true" /></td>
	            </tr>
	            <tr>
	            <td>出票渠道名称</td>
	            <td><input id ="ticketChannelId_edit" name="ticketChannelId"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"/></td>
	            <td>第三方票号前缀</td>
	            <td><input name="preTicket" class="mini-textbox" style="width:100%;" required="true" /></td>
	            <td>每批数量</td>
	            <td><input name="sendEachBatch" class="mini-textbox" vtype ="int" style="width:100%;" required="true" /></td>
	            </tr>
	            <tr>
	             <td>出票状态</td>
	             <td><input id ="sendStatus_edit" name="sendStatus"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"/></td>
	             <td>查票方式</td>
	             <td><input id ="searchAuto_edit" name="searchAuto"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"/></td>
	             <td>查票最大票数</td>
	             <td><input name="searchMaxTicket" class="mini-textbox" vtype ="int" style="width:100%;" required="true" /></td></td>
	            </tr>
	             
	            <tr>
	             <td>出票彩种</td>
	             <td><input id ="lotteryCode_edit" name="lotteryCode"  class="mini-combobox" allowInput="true" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true" onvaluechanged="Current.lotteryCodeChange"/></td>
	             <td>查票标识</td>
	             <td><input name="searchIdent" class="mini-textbox" style="width:100%;" required="true" /></td></td>
	             <td>送票线程数</td>
	             <td><input name="threadCount" class="mini-textbox" vtype ="int" style="width:100%;" required="true" /></td></td>
	            </tr>
	            <tr>
	             <td>停送玩法</td>
	             <td colspan="5"><div id="childType" name="stopLottery" class="mini-checkboxlist" repeatDirection="vertical" repeatLayout="table"  style="display:inline-block;"/></td></td>
	            </tr>
	            <tr>
	             <td>送票间隔（前）</td>
	             <td><input name="beforeTime" class="mini-textbox" vtype ="int" style="width:100%;" required="true" /></td></td>
	             <td>送票间隔（分割）</td>
	             <td><input name="interval" class="mini-textbox" vtype ="int" style="width:100%;" required="true" /></td></td>
	             <td>送票间隔（后）</td>
	             <td><input name="afterTime" class="mini-textbox" vtype ="int" style="width:100%;" required="true" /></td></td>
	             </tr>
	             <tr>
	             <td>出票商截止时间</td>
	             <td><input name="dealerEndTime" class="mini-textbox" vtype ="int" style="width:100%;" required="true" value = "0"/></td></td>
	             <td>送票最小金额</td>
	             <td><input name="startMoney" class="mini-textbox" vtype ="int" style="width:100%;"/></td></td>
	             <td>送票最大金额</td>
	             <td><input name="endMoeny" class="mini-textbox" vtype ="int" style="width:100%;" /></td></td>
	             </tr>
	            </table>            
	            </div>
	        </fieldset>
	        <fieldset style="border:solid 1px #aaa; padding:3px;">
		      <div  class="mini-toolbar">
								<table style="width:100%;">
									<tr>
										<td  style="white-space:nowrap;">
						 					<a  class="mini-button" iconCls="icon-add" plain="true" onclick="Cms.addEditRow('send_grid')">新增</a>
						 					<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="Cms.deleteRow('send_grid')">删除</a>
						 					<a  class="mini-button" iconCls="icon-save" plain="true" onclick="Ticket.sendTicketOK()">确定</a>
										</td>
									</tr>
								</table>
							</div>
						   <div showCollapseButton="true">
						   <div title="高频彩销售周期 " style="height: 280px">
						   <div id="send_grid" class="mini-datagrid"  style="width:100%;height:100%;" 
							     onrowdblclick="Cms.editRow('send_grid')" allowResize="true"  showPager="false"   multiSelect="true">
						        <div property="columns"> 
						        		<div type="checkcolumn" width="3%"></div>    
						        		<div  headerAlign="center" align="center" renderer="Ticket.onActionRendererSend" cellStyle="padding:0;">#</div>
						                <div    field="week" type= "comboboxcolumn" headerAlign="center" align="center">星期
							                <input  property="editor"  class="mini-combobox"  data="Dic.week" />
						                </div>
						                <div    field="startDate" headerAlign="center" align="center" dateFormat="HH:mm:ss">开始时间
							                <input  property="editor"  class="mini-timespinner"  required="true"/>
						                </div>
						                <div    field="endDate" headerAlign="center" align="center" dateFormat="HH:mm:ss">结束时间
							                <input  property="editor"  class="mini-timespinner"  required="true"/>
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
			  <td width="13%">创建时间</td>
			  <td width="20%"><input name="createTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td width="13%">修改时间</td>
			  <td width="20%"><input name="modifyTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td width="13%" rowspan="2">备注</td>
			  <td width="20%" rowspan="2"><input name="remark" class="mini-textarea" vtype ="maxLength:100" style="width:100%;"/></td>
			</tr>
			<tr>
			  <td>创建人</td>
			  <td><input name="createBy" class="mini-textbox" enabled="false" allowInput="false" style="width:100%;" /></td>
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
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/ticketmgr/ticket.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/ticketmgr/ticket_channel_config.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
