<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	<script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>会员钱包查询</title>
  </head>
  
  <body>
	<div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			    <td width="15%" nowrap="nowrap">
			        <btn:operate privilege="SEARCH">
				     	<a class="mini-button" iconCls="icon-upload" plain="true" onclick="WalletObj.uploadSearch()">上传查询</a>
			     	</btn:operate>
			        <btn:operate privilege="EXPORT">
			        	<a class="mini-button" iconCls="icon-download" plain="true" onclick="WalletObj.excel()">导出</a>
			        </btn:operate>
			        <btn:operate privilege="UPD">
			        	<a class="mini-button" iconCls="icon-edit" plain="true" onclick="WalletObj.enableWallet()">启用</a>
			        	<a class="mini-button" iconCls="icon-edit" plain="true" onclick="WalletObj.disableWallet()">禁用</a>
			        </btn:operate>
				</td>
				
				<td width="5%" style="white-space:nowrap;">
				   <input id="userQryType" class="mini-combobox" style="width:80px;" allowInput="true" valueFromSelect="true" />
				   <input id="userQryVal" class="mini-textbox" style="width:200px;" />
				        状态：<input id="status" class="mini-combobox" style="width:80px;" emptyText="请选择" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick" />
					<a class="mini-button" onclick="WalletObj.search()">查询</a>
				</td>
				
				<td width="30%"></td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="customermgr/user/wallet/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" 
				 showSummaryRow="true" ondrawsummarycell="WalletObj.drawSummaryCell" onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="user.accountName" headerAlign="center" align="center">会员帐号</div>
					<div field="user.nickName" headerAlign="center" align="center">昵称</div>
					<div field="totalCashBalance" headerAlign="center" align="center" dataType="currency">现金总余额</div>
					<div field="winningBalance" headerAlign="center" align="center" dataType="currency">中奖余额</div>
					<div field="top80Balance" headerAlign="center" align="center" dataType="currency">充值80%余额</div>
					<div field="top20Balance" headerAlign="center" align="center" dataType="currency">充值20%余额</div>
					<div field="effRedBalance" headerAlign="center" align="center" dataType="currency">可用红包余额</div>
					<div field="readyRedBalance" headerAlign="center" align="center" dataType="currency">待发红包余额</div>
					<div field="status" type="comboboxcolumn" headerAlign="center" align="center">状态
					     <input property="editor" class="mini-combobox" data="Dic.walletStatus" />
					</div>
					<div field="updateTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="upoladWindow" class="mini-window" title="上传查询" 
		style="width:250px; height:200px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
				
		<div id="searchForm">
			<table width="100%;">
				<!--  查询类型：<input id="uploadType"  class="mini-combobox"  style="width:80px;" allowInput="true" valueFromSelect = "true"/> -->
				<div id="uploadType" class="mini-radiobuttonlist" repeatLayout="table"
					 data="[{id:'account_name',text:'会员帐户'},{id:'nick_name',text:'会员昵称'},{id:'cus_mobile',text:'手机号码'}]"> 
				</div>
              	<br>
	         	<input id="fileSearch" class="mini-htmlfile" name="file" limitType="*.txt" style="width:180px;" required="true"/>
	         	<btn:operate privilege="UPLOAD">
		     		<a class="mini-button"   onclick="WalletObj.ajaxFileUploadSearch('fileSearch')">确定</a>
		     	</btn:operate>
		     	<br>上传查询说明：<br>1.必须是“ .txt ”文件 ；<br>2.一行一个会员信息，不能有其它字符 ；
			</table>
		</div>
	</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/customermgr/user_wallet.js" type="text/javascript"></script>
</html>
