<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
        <input id = "id" name="id" class="mini-hidden" />
        <input name="url"  class="mini-hidden"/>
		<input name="action"  class="mini-hidden" />
		<input id = "drawDetail" name="drawDetail" class="mini-hidden" />
		<input id = "drawCode" name="drawCode" class="mini-hidden" />
		<input id = "lotteryCode_edit" name="lotteryCode" class="mini-hidden" />
		<input id = "lotteryName_edit" name="lotteryName" class="mini-hidden" />
		<fieldset style="border:solid 1px #aaa;padding:3px;">
            <legend >基本信息：</legend>
            <div style="padding:5px;">
             <table>
             			<tr>
			  <td width="15%">彩期编号</td>
			  <td width="15%"><input id="issueCode_edit" name="issueCode" class="mini-textbox" style="width:200px;"  vtype ="maxLength:15"  required="true"/></td>
			  <td width="15%" >彩期状态</td>
			  <td width="15%"><input id="currentIssue" name="currentIssue" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择" style="width:200px;"   required="true"/></td>
			  <td width="15%">销售状态</td>
			  <td width="15%"><input id="saleStatus_edit" name="saleStatus" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择" style="width:200px;"   required="true"/></td>
			</tr>
			<tr>
			  <td width="12%">官方截止销售时间</td>
			  <td width="15%"><input name="officialEndTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/></td>
			  <td width="12%" >官方开奖时间</td>
			  <td width="15%"><input name="lotteryTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/></td>
			  <td width="15%">销售总金额</td>
			  <td width="15%"><input id="salesAmount_edit" name="salesAmount" class="mini-textbox" style="width:200px;" vtype ="int:maxLength:10" /></td>
			</tr>
			<tr>
			  <td width="15%">本站开始销售时间</td>
			  <td width="15%"><input name="saleTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/></td>
			  <td width="15%" >本站截止销售时间</td>
			  <td width="15%"><input name="saleEndTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/></td>
			  <td width="15%">奖池金额</td>
			  <td width="15%"><input id="jackpotAmount_edit" name="jackpotAmount" class="mini-textbox" style="width:200px;" vtype ="int;maxLength:10"/></td>
			</tr>
			<tr>
				<td width="15%">是否最新开奖</td>
				<td><input id="issueLastest" name="issueLastest" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择" style="width:200px;"   required="true"/></td>
			</tr>
            </table>            
            </div>
        </fieldset>
        <div id="operateButton">
			<btn:operate privilege="CHECK">
				<a class="mini-button" iconCls="icon-edit" plain="true"
					onclick="Current.check()" enabled="true">审核</a>
			</btn:operate>
			<btn:operate privilege="MANUAL_EXECUTE">
				<a class="mini-button" iconCls="icon-edit" plain="true"
					onclick="Current.drowCode()" enabled="true" >开奖</a>
			</btn:operate>
		</div>