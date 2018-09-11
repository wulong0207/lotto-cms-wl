<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<fieldset style="border:solid 1px #aaa;padding:3px;">
            <legend >操作信息：</legend>
            <div style="padding:5px;">
             <table>
			<tr>
			  <td width="12%" >创建时间</td>
			  <td width="20%"><input name="createTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td width="12%">修改时间</td>
			  <td width="20%"><input name="modifyTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/></td>
			  <td width="15%" rowspan="2">备注</td>
			  <td width="15%" rowspan="2"><input name="remark" class="mini-textarea" vtype ="maxLength:200" style="width:200px;"/>
			  </td>
			</tr>
			<tr>
			  <td>创建人</td>
			  <td><input name="createBy" class="mini-textbox"  enabled="false" allowInput="false" style="width:200px;"/></td>
			  <td>修改人</td>
			  <td><input name="modifyBy" class="mini-textbox"  enabled="false" allowInput="false" style="width:200px;" /></td>
			</tr>
            </table>            
            </div>
        </fieldset> 
        <div style="text-align:center;padding:10px;">   
        <btn:operate privilege="ADD|UPD">            
            <a class="mini-button" onclick="dosubmit" style="width:60px;margin-right:20px;">保存</a>    
            </btn:operate>   
            <a class="mini-button" onclick="Current.cancel" style="width:60px;">关闭</a>       
</div>  