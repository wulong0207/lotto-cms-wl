<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>银行卡BIN表管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="ADD">
		        <a class="mini-button" iconCls="icon-add" plain="true" onclick="pay_bank_segment.toMerge('add')">新增</a>
	        </btn:operate>
	        <btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="pay_bank_segment.toMerge">修改</a>
	        </btn:operate>
			</tr>
			<tr>
				<td width="10%" nowrap="nowrap">
					Bin码
					&nbsp;&nbsp;&nbsp;<input id="topCutLike" class="mini-textbox" vtype="int" emptyText="请输入" width="200px"/>
					<a class="mini-button" onclick="pay_bank_segment.search">查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:87%;" 
		 url="sysmgr/banksegment/page" idField="id" allowResize="true" ajaxType="get" multiSelect="true"
		 showColumnsMenu="true" showFilterRow="false" onrowdblclick="pay_bank_segment.toMerge">
		<div property="columns">
			<div type="checkcolumn" width="5%"></div>
			<div type="indexcolumn" width="5%" align="center" headerAlign="center">序号</div>
			<div field="bankName" headerAlign="center" align="center">发卡行名称(机构代码)</div>
			<div field="cardName" headerAlign="center" align="center">卡名</div>
			<div field="cardType" type="comboboxcolumn" headerAlign="center" align="center">卡类型
				<input property="editor" class="mini-combobox" data="Data.cardType"/>
			</div>
			<div field="cardLength" headerAlign="center" align="center">卡号长度</div>
			<div field="topCut" headerAlign="center" align="center">bin码</div>
			<div field="topCutLength" headerAlign="center" align="center">长度</div>
			<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
			<div field="modifyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
			<div field="createBy" headerAlign="center" align="center">修改人</div>
			<div field="modifyBy" headerAlign="center" align="center">创建人</div>
		</div>
	</div>

  <div id="detailWindow" showToolbar="true" class="mini-window" style="width:700px;height:200px" showFooter="true" showModal="true" allowResize="true" allowDrag="true" showCloseButton="true">

	  <div property="toolbar" style="text-align:right;padding:2px;padding-right:15px;">
		  <btn:operate privilege="SAVE">
			  <a class="mini-button" onclick="pay_bank_segment.merge">保存</a>
		  </btn:operate>
		  <a class="mini-button" onclick="pay_bank_segment.cancel">取消</a>
	  </div>

	  <input name="id" class="mini-hidden"/>

	  *发卡行名称<input name="bankId" id="bankId" url="paymentmgr/index/banks/dic" class="mini-combobox" emptyText="请选择或输入" style="width:200px;" showClose="true" allowInput="true" oncloseclick="Cms.onCloseClick" required="true"/>
	  *机构代码<input name="bankCode" class="mini-textbox" vtype="int" required="true"/>
	  卡名<input name="cardName" class="mini-textbox" style="width:200px;"/>
	  <br/><br/>
	  *卡类型<input name="cardType" id="cardType" class="mini-combobox" emptyText="请选择" showClose="true" oncloseclick="Cms.onCloseClick" required="true"/>
	  *Bin码<input name="topCut" class="mini-textbox" vtype="int;rangeLength:2,10" required="true"/>
	  *卡号长度<input name="cardLength" class="mini-textbox" vtype="int" required="true"/>
	  </br></br>




  </div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/date.utils.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/sysmgr/pay_bank_segment.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
