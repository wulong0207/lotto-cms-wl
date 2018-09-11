<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>支付管理</title>
  </head>
  <body>
  
    <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td width="5%" style="white-space:nowrap;">
				         排序方式：<input id="sortType"   class="mini-combobox"  style="width:220px;" emptyText="选择排序方式"  allowInput="false" valueFromSelect = "true"  oncloseclick="onCloseClickType" showClose="false" onvaluechanged="payment.changeSortType"/>
				</td>
				<td width="15%" nowrap="nowrap" >
			        <btn:operate privilege="UPD">
			     		<a id="update" class="mini-button" iconCls="icon-save" plain="true" onclick="payment.save()">保存</a>
			     	</btn:operate>
				</td>
				<td width="30%"></td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="paymentmgr/sort/list" idField="id" allowResize="true" pageSize="30" multiSelect="false" 
				 showColumnsMenu="true" showFilterRow="false" showPager="false"
				 onrowdblclick="payment.edit" onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="cname" headerAlign="center" align="center">银行简称</div>
					<div field="status" type= "comboboxcolumn" headerAlign="center" align="center">银行状态
						<input property="editor" class="mini-combobox" data="Dic.status" />
					</div>
					<div field="paytype" type= "comboboxcolumn" headerAlign="center" align="center">支付类型
						<input property="editor" class="mini-combobox" data="Dic.paytype" />
					</div>
					<div headerAlign="center" align="center" renderer="payment.orderHtml">排序</div>
				</div>
			</div>
    		
		</div>
    
	</div>
		
  </body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/paymentmgr/sort.js" type="text/javascript"></script>
</html>
