<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <link href="<%=basePath%>resources/css/public/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <title>代理用户提款管理</title>
    <style>
    	#excelBanks label {
    		margin-right : 20px;
    	}
    	#checkTakenWindow label + label {
		    margin-left: 100px;
		} 
    </style>
  </head>
  <body>
  
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="50%" nowrap="nowrap">
					<btn:operate privilege="CHECK_TAKEN">
				        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="agent_taken.toCheckWindow">审核提款</a>
			        </btn:operate>
					<btn:operate privilege="PROCESS_TAKEN">
				        <a class="mini-button" iconCls="icon-upload" plain="true" onclick="agent_taken.toProcessWindow">处理提款</a>
			        </btn:operate>
					<btn:operate privilege="ALREADY_PROCESS_TAKEN">
				        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="agent_taken.toAlreadyProcessWindow">已处理提款</a>
			        </btn:operate>
			        <btn:operate privilege="EXPORT">
				        <a class="mini-button" iconCls="icon-download" plain="true" onclick="agent_taken.excel('agent/taken')">导出Excel</a>
			        </btn:operate>
			        <btn:operate privilege="EXPORT">
				        <a class="mini-button" iconCls="icon-download" plain="true" onclick="agent_taken.showExcelBank">导出银行提款Excel</a>
			        </btn:operate>
				</td>
			</tr>
			<tr>
				<td>
					系统流水编号<input id="transTakenCode" name="transTakenCode" class="mini-textbox" style="width:200px;"/>
					第三方流水编号 <input id="thirdTransNum" name="thirdTransNum" class="mini-textbox" style="width:200px;"/>
					批次号 <input id="batchNum" name="batchNum" class="mini-textbox" style="width:200px;"/>
					付款渠道<input id="payChannel" name="payChannel" class="mini-combobox"
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					银行名称<input id="takenBank" name="takenBank" class="mini-combobox"
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					提款金额小于<input name="extractAmountLt" class="mini-textbox" vtype="float"/>
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
					批次状态<input id="batchStatus" name="batchStatus" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择"  valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					<a class="mini-button" onclick="agent_taken.search(agent_taken.grid)" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:90%;" 
		 url="agent/taken/page" idField="id" allowResize="true" multiSelect="true"
		 showColumnsMenu="true" onrowdblclick="agent_taken.viewDetail" showFilterRow="false" showSummaryRow = "true" ondrawsummarycell="agent_taken.onDrawSummaryCell" sizeList="[10,20,50,100,500]" pageSize=100>
		<div property="columns">
			<div type="checkcolumn" width="3%"></div>
			<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
			<div field="accountName" headerAlign="center" align="center">会员账号</div>
			<div field="transTakenCode" headerAlign="center" align="center">系统流水编号</div>
			<div field="transStatus" type= "comboboxcolumn" headerAlign="center" align="center">交易状态
		     <input property="editor" class="mini-combobox" data="Dic.transStatus" />
			</div>
			<div field="extractAmount" headerAlign="center" align="center" width="8%">提款金额</div>
			<div field="realAmount" headerAlign="center" align="center" width="8%">到账金额</div>
			<div field="serviceCharge" headerAlign="center" align="center" width="8%">服务费</div>
			<div field="taxCharge" headerAlign="center" align="center" width="8%">税费</div>
			<div field="actualName" headerAlign="center" align="center">会员姓名</div>
			<div field="payChannel" type= "comboboxcolumn" headerAlign="center" align="center" width="10%">付款渠道
		     <input property="editor" class="mini-combobox" data="Dic.payChannel" />
			</div>
			<div field="takenBank" type= "comboboxcolumn" headerAlign="center" align="center">银行分类
		     <input property="editor" class="mini-combobox" data="Dic.takenBank" />
			</div>
			<div field="bankCardNum" headerAlign="center" align="center">银行卡号</div>
			<div field="bankInfo" headerAlign="center" align="center">银行分支行名称和地址</div>
			<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" width="9%">创建时间</div>	
			<div field="dealTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" width="9%">银行处理时间</div>
			<div field="arrivalTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" width="9%">到账时间</div>	
			<div field="thirdTransNum" headerAlign="center" align="center" width="8%">第三方流水号</div>
			<div field="batchNum" headerAlign="center" align="center" width="8%">批次号</div>
			<div field="batchStatus" type="comboboxcolumn" headerAlign="center" width="8%" align="center">批次状态
		     <input property="editor" class="mini-combobox" data="Dic.batchStatus" />
			</div>
			<div value="红包" type="text" headerAlign="center" align="center">操作</div>
		</div>
	</div>
	<div id="detailWindow" class="mini-window" style="width:800px;" showModal="true" allowResize="true" allowDrag="true">
	    <div id="detailForm" class="form" >
		    <fieldset>
			    <legend>投注信息：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td style="width:100px;">充值渠道</td>
	                <td style="width:150px;"><input id="payChannelDetail" name="payChannel" class="mini-combobox" enabled="false"/></td>
	                <td style="width:100px;">用户昵称</td>
	                <td style="width:150px;"><input name="nickName" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">交易状态</td>
	                <td style="width:150px;"><input id="transStatusDetail" name="transStatus" class="mini-combobox" enabled="false"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">提款银行</td>
	                <td style="width:150px;"><input id="takenBankDetail" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">会员姓名</td>
	                <td style="width:150px;"><input name="actualName" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">发起平台</td>
	                <td style="width:150px;"><input id="takenPlatformDetail" name="takenPlatform" class="mini-combobox" enabled="false"/></td>
	            </tr>
	                <td style="width:100px;">用户手机号码</td>
	                <td style="width:150px;"><input name="cusMobile" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">市场渠道</td>
	                <td style="width:150px;"><input name="channelName" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">审核时间</td>
	                <td style="width:150px;"><input name="reviewTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">提款金额</td>
	                <td style="width:150px;"><input id="extractAmountDetail" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">到账金额</td>
	                <td style="width:150px;"><input id="realAmountDetail" class="mini-textbox" enabled="false"/></td>
	                <%--
	                <td style="width:100px;">订单编号</td>
	                <td style="width:150px;"><input name="orderCode" class="mini-textbox" enabled="false"/></td>
	                --%>
	                <td style="width:100px;">汇款时间</td>
	                <td style="width:150px;"><input name="remitTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">服务费</td>
	                <td style="width:150px;"><input id="serviceChargeDetail" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">第三方流水号</td>
	                <td style="width:150px;"><input name="thirdTransNum" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">到帐时间</td>
	                <td style="width:150px;"><input name="arrivalTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">审核人</td>
	                <td style="width:150px;"><input name="reviewBy" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">批次号</td>
	                <td style="width:150px;"><input name="batchNum" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">银行处理时间</td>
	                <td style="width:150px;"><input name="dealTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">银行交易凭证</td>
	                <td style="width:150px;"><input name="transCert" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">批次状态</td>
	                <td style="width:150px;"><input id="batchStatusDetail" name="batchStatus" class="mini-combobox" enabled="false"/></td>
	                <td style="width:100px;">下发时间</td>
	                <td style="width:150px;"><input name="sendDownTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">交易失败原因</td>
	                <td style="width:150px;"><input name="transFailInfo" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">银行返回信息</td>
	                <td style="width:150px;"><input name="bankReturnInfo" class="mini-textbox" enabled="false"/></td>
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
	                <td style="width:150px;"><input name="updateTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            		<td style="width:100px;" rowspan="2">备注</td>
	                <td style="width:150px;" rowspan="2"><input name="remark" class="mini-textbox" enabled="false"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">创建人</td>
	                <td style="width:150px;"><input name="createBy" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">修改人</td>
	                <td style="width:150px;"><input name="updateBy" class="mini-textbox" enabled="false"/></td>
	            </tr>
	        </table>
			  </fieldset>
	    </div>
	</div>
	
	<div id="checkTakenWindow" class="mini-window" title="审核提款" style="width:500px;height:400px;" onbuttonclick="agent_taken.clearCheckTakenWindow">
	<div style="text-align:center;">
		<label class="radio-inline"><input type="radio" name="check" value="1" checked>审核通过</label>
		<label class="radio-inline"><input type="radio" name="check" value="2">审核不通过</label>
		
		<div class="input-group input-group-sm col-sm-6" style="margin: 10px 10px 10px 100px;">
  <span class="input-group-addon" id="basic-addon3">驳回原因</span>
  <input type="text" class="form-control" id="rejectReason" aria-describedby="basic-addon3">
</div>
<button type="button" class="btn btn-info btn-sm"
				id="confirmCheckBtn">确认</button>
			<button type="button" class="btn btn-info btn-sm"
				id="cancelCheckBtn">取消</button>
		<table class="table table-bordered table-sm">
		  <tbody>
		    <tr>
                <td>创建时间</td>
                <td><input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
           		<td rowspan="2">备注</td>
                <td rowspan="2"><input name="remark" class="mini-textarea"/></td>
            </tr>
            <tr>
                <td>创建人</td>
                <td><input name="createBy" class="mini-textbox" enabled="false"/></td>
            </tr>
		  </tbody>
		</table>
		</div>
	</div>
	
	<div id="processTakenWindow" class="mini-window" title="处理提款"
		style="width: 770px; height: 600px;"
		onbuttonclick="agent_taken.clearProcessTakenWindow">
		<div style="text-align:center;">
			<div class="input-group input-group-sm col-sm-6" style="margin-left : 180px;">
					<form method="post" enctype="multipart/form-data" id="uploadFileForm" style="align:inline;">
					<input name="uploadBankTpl" id="uploadBankTpl" class="mini-combobox" 
			emptyText="选择银行模板" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" required="true"/>
						<input type="file" name="file" id="file">
						<btn:operate privilege="UPLOAD">
				    	<a class="mini-button" onclick="agent_taken.preUploadBankExcel">上传</a>
				    	</btn:operate>
					</form>
			</div>

			<div class="row">
				<div style="padding: 10px 10px 0 50px;" class="text-center">
					<table class="table table-bordered table-sm" id="successOrFailTaken">
						<thead class="thead-default">
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
			<button type="button" class="btn btn-info btn-sm"
				id="confirmProcessBtn">确认</button>
			<button type="button" class="btn btn-info btn-sm"
				id="cancelProcessBtn">取消</button>
		</div>
	</div>

	<div id="alreadyProcessTakenWindow" class="mini-window" title="已处理提款"
		style="width: 770px; height: 600px;"
		onbuttonclick="agent_taken.clearAlreadyProcessTakenWindow">
		<div style="text-align:center;">
			<div class="input-group input-group-sm col-sm-6" style="margin-left : 180px;">
				<span class="input-group-addon">批次号</span> <input
					type="text" class="form-control" placeholder="请输入批次号"
					aria-describedby="sizing-addon" id="batchNumInput"> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button" id="searchTransBtn">查询!</button>
				</span>
			</div>
			<div class="row">
				<div style="padding: 10px 10px 0 50px;" class="text-center">
					<table class="table table-bordered table-sm" id="successTaken"
						data-toggle="tooltip" title="双击移动记录">
						<thead class="thead-default">
							<tr>
								<th>序号</th>
								<th>成功</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<div style="padding: 10px 10px 0 10px;">
					<table class="table table-bordered table-sm" id="failTaken"
						data-toggle="tooltip" title="双击移动记录">
						<thead class="thead-default">
							<tr>
								<th>序号</th>
								<th>失败</th>
								<th>失败原因</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
			<button type="button" class="btn btn-info btn-sm"
				id="confirmAlreadyProcessBtn">确认</button>
			<button type="button" class="btn btn-info btn-sm"
				id="cancelAlreadyProcessBtn">取消</button>
		</div>
	</div>
	
	<div id="processSuccessTakenWindow" class="mini-window" title="银行处理成功提款"
		style="width: 770px; height: 600px;"
		onbuttonclick="agent_taken.clearProcessSuccessTakenWindow">
		<div style="text-align:center;">
			<div class="input-group input-group-sm col-sm-6" style="margin-left : 180px;">
				<span class="input-group-addon">批次号</span> <input
					type="text" class="form-control" placeholder="请输入批次号"
					aria-describedby="sizing-addon" id="processSuccessBatchNumInput"> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button" id="processSuccessSearchTransBtn">查询!</button>
				</span>
			</div>

			<div class="row">
				<div style="padding: 10px 10px 0 50px;" class="text-center">
					<table class="table table-bordered table-sm" id="processSuccessSuccessTaken"
						data-toggle="tooltip" title="双击移动记录">
						<thead class="thead-default">
							<tr>
								<th>序号</th>
								<th>成功</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<div style="padding: 10px 10px 0 10px;">
					<table class="table table-bordered table-sm" id="processSuccessFailTaken"
						data-toggle="tooltip" title="双击移动记录">
						<thead class="thead-default">
							<tr>
								<th>序号</th>
								<th>失败</th>
								<th>失败原因</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
			<button type="button" class="btn btn-info btn-sm"
				id="confirmProcessSuccessBtn">确认</button>
			<button type="button" class="btn btn-info btn-sm"
				id="cancelProcessSuccessBtn">取消</button>
		</div>
	</div>
	<%--导出银行弹框 --%>
	<div class="mini-window" title="导出Excel" id="excelBankWindow">
		<div style="text-align:center;">
		<div id="excelBanks">
		</div>
		<div>
		<button type="button" class="btn btn-info btn-sm" id="confirmBankExcel">确定</button>
		<button type="button" class="btn btn-info btn-sm hideWindow" data-window="excelBankWindow">取消</button>
		</div>
		</div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/ie-fix.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/agent/agent_taken.js" type="text/javascript"></script>
</html>
