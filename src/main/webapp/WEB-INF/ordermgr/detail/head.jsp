<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<input name="url" class="mini-hidden" />
<input name="action" class="mini-hidden" />
<input name="id" class="mini-hidden" />
<input name="orderCode" class="mini-hidden" />
<input id="lotteryChildCode" name="lotteryChildCode" class="mini-hidden" />
<fieldset style="border: solid 1px #aaa; padding: 3px;">
	<legend>
		投注信息:<span id="orderCodeShow"></span>
	</legend>
	<div style="padding: 5px;">
		<table width="100%;">
			<tr>
				<td width="13%">彩种</td>
				<td width="20%"><input id="lotteryCode_edit" name="lotteryCode"
					class="mini-combobox" emptyText="请选择" valueFromSelect="true"
					style="width: 100%;" enabled="false" /></td>
				<td width="13%">用户昵称</td>
				<td width="20%"><input name="nickName" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
				<td width="13%">购买类型</td>
				<td width="20%"><input id="buyType_edit" name="buyType"
					class="mini-combobox" emptyText="请选择" valueFromSelect="true"
					style="width: 100%;" enabled="false" /></td>
			</tr>
			<tr>
				<td>彩期</td>
				<td><input id="lotteryIssue" name="lotteryIssue" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
				<td>用户账号</td>
				<td><input name="accountName" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
				<td>支付状态</td>
				<td><input id="payStatus_edit" name="payStatus"
					class="mini-combobox" emptyText="请选择" valueFromSelect="true"
					style="width: 100%;" enabled="false" /></td>
			</tr>
			<tr>
				<td>购买时间</td>
				<td><input name="buyTime" class="mini-datepicker"
					allowInput="false" style="width: 100%;"
					format="yyyy-MM-dd HH:mm:ss" enabled="false" /></td>
				<td>用户手机号</td>
				<td><input name="cusMobile" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
				<td>方案状态</td>
				<td><input id="orderStatus_edit" name="orderStatus"
					class="mini-combobox" emptyText="请选择" valueFromSelect="true"
					enabled="false" style="width: 100%;" required="true" /></td>
			</tr>
			<tr>
				<td>截止出票时间</td>
				<td><input name="endTicketTime" class="mini-datepicker"
					allowInput="false" style="width: 100%;"
					format="yyyy-MM-dd HH:mm:ss" enabled="false" /></td>
				<td>方案金额</td>
				<td><input name="orderAmount" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
				<td>中奖状态</td>
				<td><input id="winningStatus_edit" name="winningStatus"
					class="mini-combobox" emptyText="请选择" valueFromSelect="true"
					style="width: 100%;" enabled="false" /></td>
			</tr>
			<tr>
				<td>最终检票时间</td>
				<td><input name="endCheckTime" class="mini-datepicker"
					allowInput="false" style="width: 100%;"
					format="yyyy-MM-dd HH:mm:ss" enabled="false" /></td>
				<td>方案倍数</td>
				<td><input name="multipleNum" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
				<td>投注平台</td>
				<td><input id="platform_edit" name="platform"
					class="mini-combobox" emptyText="请选择" valueFromSelect="true"
					style="width: 100%;" enabled="false" /></td>
			</tr>
			<tr>
				<td>出票时间</td>
				<td><input name="comeOutTime" class="mini-datepicker"
					allowInput="false" style="width: 100%;"
					format="yyyy-MM-dd HH:mm:ss" enabled="false" /></td>
				<td>拆票数量</td>
				<td><input name="splitNum" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
				<td>市场渠道</td>
				<td><input name="channelName" class="mini-textbox"
					style="width: 100%;" enabled="false" enabled="false" /></td>
			</tr>
			<tr>
				<td>开奖时间</td>
				<td><input name="lotteryTime" class="mini-datepicker"
					allowInput="false" style="width: 100%;"
					format="yyyy-MM-dd HH:mm:ss" enabled="false" /></td>
				<td>中奖情况</td>
				<td><input name="winningDetail" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
				<td>加奖奖金</td>
				<td><input name="addedBonus" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
			</tr>
			<tr>
				<td>派奖时间</td>
				<td><input name="sendTime" class="mini-datepicker"
					allowInput="false" style="width: 100%;"
					format="yyyy-MM-dd HH:mm:ss" enabled="false" /></td>
				<td>税前奖金</td>
				<td><input name="preBonus" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
				<td>支付红包</td>
				<td><input name="redCodeUsed" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
			</tr>
			<tr>
				<td>大乐透追加</td>
				<td><input id="isDltAdd_edit" name="isDltAdd"
					class="mini-combobox" emptyText="请选择" valueFromSelect="true"
					enabled="false" style="width: 100%;" required="true" /></td>
				<td>税后奖金</td>
				<td><input name="aftBonus" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
				<td>发放红包</td>
				<td><input name="redCodeGet" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
			</tr>
			<tr>
				<td>是否允许检票</td>
				<td><input id="checkTicket_edit" name="checkTicket"
					class="mini-combobox" emptyText="请选择" valueFromSelect="true"
					enabled="false" style="width: 100%;" required="true" /></td>
				<td>活动来源</td>
				<td>
					<!-- <input name="activityName" class="mini-textbox" style="width: 100%;" enabled="false" /> -->
					<input name="activitySource" class="mini-textbox" style="width:100%;" enabled="false"/>
				</td>
			</tr>
			<tr>
				<td>最大编号</td>
				<td><input name="maxBuyScreen" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
				<td>场次编号</td>
				<td colspan="3"><input id="buyScreen" name="buyScreen" class="mini-textbox"
					style="width: 100%;" enabled="false" /></td>
			</tr>
		</table>
	</div>
</fieldset>
