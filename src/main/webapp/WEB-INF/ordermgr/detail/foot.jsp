<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<fieldset style="border: solid 1px #aaa; padding: 3px;">
	<legend>操作信息：</legend>
	<div style="padding: 5px;">
		<table width="100%;">
			<tr>
				<td width="13%">修改时间</td>
				<td width="20%"><input name="modifyTime"
					class="mini-datepicker" allowInput="false" enabled="false"
					style="width: 100%;" format="yyyy-MM-dd HH:mm:ss" enabled="false" /></td>
				<td width="13%" rowspan="2">备注</td>
				<td width="20%" rowspan="2"><input name="remark"
					class="mini-textarea" vtype="maxLength:200" style="width: 100%;" /></td>
				<td width="13%"></td>
				<td width="20%"></td>
			</tr>
			<tr>
				<td>修改人</td>
				<td><input name="modifyBy" class="mini-textbox" enabled="false"
					allowInput="false" style="width: 100%;" /></td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
</fieldset>
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
		    <btn:operate privilege="UPD">
			<a class="mini-button" onclick="Current.doSubmit">保存</a>
		    </btn:operate>
		</div>	
