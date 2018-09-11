<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>监控配置</title>
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
			                    彩种：<input id="lotteryCode"  name="lotteryId" class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种"   allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true"/> 
				      <a class="mini-button" onclick="Current.search()">查询</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:90%;" style="border:0;">
	
		<div size="60%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="ticketmgr/monitorconfig/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" onrowdblclick="Current.onrowdblclick"
				 ondrawcell="Current.onDrawcell">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="lotteryName"  headerAlign="center" align="center">彩种名称</div>
					<div field="noSplit"  headerAlign="center" align="center">未拆方案数</div>
					<div field="noOut"  headerAlign="center" align="center">截止未出数</div>
					<div field="noSend"  headerAlign="center" align="center">未送票数</div>
					<div field="send"  headerAlign="center" align="center">已送票数</div>
					<div field="noOutMoney"  headerAlign="center" align="center">未出票金额</div>
					<div field="endTime"  headerAlign="center" align="center">截止时间</div>
					<div field="channelTicketNum"  headerAlign="center" align="center">出票商票数</div>
					<div field="channelMoney"  headerAlign="center" align="center">出票商金额</div>
					<div field="maxSendtime"  headerAlign="center" align="center">最早送票时间</div>
					<div field="refreshTime"  headerAlign="center" align="center">刷新时间</div>
					<div field="status"  type= "comboboxcolumn" headerAlign="center" align="center" >显示
					     <input property="editor" class="mini-combobox" data="Dic.status" />
					</div>
					<div field="updateTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="createTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
				</div>
			</div>
		</div>

		<div size="40%" showCollapseButton="true">
			<div style="height: 100%">
				<div class="mini-toolbar">
					<table style="width:100%;">
						<tr>
							<td style="white-space:nowrap;">
							    <btn:operate privilege="UPD">
									<a class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.editRow('common_grid')">修改</a>
								</btn:operate> 
								<a class="mini-button" iconCls="icon-reload" plain="true" onclick="Cms.reload('common_grid')">刷新</a> 
								<btn:operate privilege="UPD">
									<a class="mini-button" iconCls="icon-save" plain="true" onclick="Current.saveCommon()">保存</a>
								</btn:operate>
							</td>
							<td style="float: right;">常规配置</td>
						</tr>
					</table>
				</div>
				<div showCollapseButton="true" style="border:0;height:80%">
				       <div id="common_grid" class="mini-datagrid" allowAlternating="true" onpreload="MiniCom.onpreload"  borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
							 url="ticketmgr/monitorconfig/common/list" idField="id" allowResize="true" multiSelect="true" 
							 showColumnsMenu="true" showFilterRow="false" showFilterRow="false" showPager="false"
							 onrowdblclick="Cms.editRow('common_grid')" >
							 
						<div property="columns">
							<div type="checkcolumn" width="3%"></div>
							<div headerAlign="center" align="center" renderer="Current.onActionRendererChild" cellStyle="padding:0;">#</div>
							<div field="id" headerAlign="center" align="center">key</div>
							<div field="remark" headerAlign="center" align="center">
								描述 <input property="editor" vtype="maxLength:30" class="mini-textbox" required="true" />
							</div>
							<div field="parameter" headerAlign="center" align="center">
								值 <input property="editor" vtype="int;maxLength:10" class="mini-textbox" required="true" />
							</div>
							<div field="updateTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
						</div>
					</div>
				</div>

			</div>
		</div>

	</div>
			
	<div id="editWindow" class="mini-window" title="" 
		style="width:1100px; height:700px;" showMaxButton="false" 
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
			 <input id="lotteryName" name="lotteryName"  class="mini-hidden"/>
			 <input id="refreshTime_edit" name="refreshTime" class="mini-hidden"/>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
	            <legend >渠道基本信息：</legend>
	            <div style="padding:5px;">
	             <table width="100%;">
	            <tr>
	            <td width="15%">彩种</td>
	            <td width="18%"><input id ="lotteryId_edit" name="lotteryId"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"   required="true"  enabled="false" onvaluechanged="Current.lotteryIdChange"/></td>
	            <td width="15%">未拆方案数</td>
	            <td width="18%"><input name="noSplit" class="mini-textbox" style="width:100%;"  vtype ="int"/></td>
	            <td width="15%" >截止未出数</td>
	            <td width="18%" ><input name="noOut" class="mini-textbox" style="width:100%;"  vtype ="int"/></td>
	            </tr>
	            <tr>
	            <td>未送票数</td>
	            <td><input name="noSend" class="mini-textbox" style="width:100%;" vtype ="int"/></td>
	            <td>已送票数</td>
	            <td><input name="send" class="mini-textbox" style="width:100%;" vtype ="int"/></td>
	            <td>未出票金额</td>
	            <td><input name="noOutMoney" class="mini-textbox" style="width:100%;" vtype ="int" /></td>
	            </tr>
	            <tr>
	            <td>出票商票数</td>
	            <td><input name="channelTicketNum" class="mini-textbox" style="width:100%;" vtype ="int"/></td>
	            <td>出票商金额</td>
	            <td><input name="channelMoney" class="mini-textbox" style="width:100%;" vtype ="int"/></td>
	            <td>最早送票时间</td>
	            <td><input name="maxSendtime" class="mini-textbox" style="width:100%;" vtype ="int"/></td>
	            </tr>
	            <tr>
	            <td>截止时间</td>
	            <td><input name="endTime" class="mini-textbox" style="width:100%;" vtype ="int" /></td>
	            <td>显示</td>
	            <td><input id ="status_edit" name="status"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"   style="width:100%;"  required="true"/></td>
	            </tr>
	            </table>            
	            </div>
	        </fieldset>
        <fieldset style="border:solid 1px #aaa; padding:3px;">
        <legend >彩期刷新时间：</legend>
              <table width="100%;">
			   <tr>
			  <td width="15%">截止前时间（秒）</td>
			  <td width="18%"><input name="beforeIssueEnd" class="mini-textbox" style="width:100%;" vtype ="int" /></td>
			  <td width="15%">刷新频率（秒）</td>
			  <td width="18%"><input name="beforeIssueEndSecond" class="mini-textbox" style="width:100%;" vtype ="int" /></td>
			  <td width="15%"></td>
			  <td width="18%"></td>
			  </tr>
			  </table>
			  <div  class="mini-toolbar">
								<table style="width:100%;">
									<tr>
										<td  style="white-space:nowrap;">
						 					<a  class="mini-button" iconCls="icon-add" plain="true" onclick="Cms.addEditRow('send_grid')">新增</a>
						 					<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="Cms.deleteRow('send_grid')">删除</a>
						 					<a  class="mini-button" iconCls="icon-save" plain="true" onclick="Ticket.sendTicketOK('refreshTime_edit')">确定</a>
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
						        		<div headerAlign="center" align="center" renderer="Ticket.onActionRendererSend" cellStyle="padding:0;">#</div>
						                <div    field="week" type= "comboboxcolumn" headerAlign="center" align="center">星期
							                <input  property="editor"  class="mini-combobox"  data="Dic.week" />
						                </div>
						                <div    field="startDate" headerAlign="center" align="center" dateFormat="HH:mm:ss">开始时间
							                <input  property="editor"  class="mini-timespinner"  required="true"/>
						                </div>
						                <div    field="endDate" headerAlign="center" align="center" dateFormat="HH:mm:ss">结束时间
							                <input  property="editor"  class="mini-timespinner"  required="true"/>
						                </div>
						                <div   field="time" headerAlign="center" align="center">刷新间隔(秒)
							                <input  property="editor" vtype ="int;maxLength:10" class="mini-textbox"  required="true"/>
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
<script src="<%=basePath%>resources/js/ticketmgr/monitor_config.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
