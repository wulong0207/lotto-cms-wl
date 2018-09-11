<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=basePath%>resources/js/public/boot.js"
	type="text/javascript"></script>
<title>修改客服电话</title>
</head>
<body>
	<div showCollapseButton="true">
		<div style="height: 100%">
			<div id="editform" style="text-align: center; padding-top: 20px;">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					<tbody>
						<tr>
							<td width="30%" align="right" nowrap="nowrap">请输入原电话号码：</td>
							<td width="70%">
								<input name="url" class="mini-hidden" />
								<input name="oldTelNo" id="oldTelNo" class="mini-textbox" readonly="readonly" style="width: 220px;" /></td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">请输入新电话号码：</td>
							<td><input name="newTelNo" id="newTelNo"
								class="mini-textbox" vtype="maxLength:50" style="width: 220px;"
								 /></td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap">请确认新电话号码：</td>
							<td><input name="telNo" id="telNo" class="mini-textbox"
								style="width: 220px;" /></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div style="text-align: center; padding: 5px; padding-right: 35%;">
				<btn:operate privilege="UPD">
					<a class="mini-button" onclick="updCustomerTel"
						style="width: 70px; height: 20px;">保存</a>
				</btn:operate>
			</div>
		</div>
	</div>

</body>
<script src="<%=basePath%>resources/js/public/common.js"
	type="text/javascript"></script>
<script src="<%=basePath%>resources/js/sysmgr/customertel.js"
	type="text/javascript"></script>
</html>
