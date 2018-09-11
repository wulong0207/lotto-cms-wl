<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>  	    
    <title>年会赠送活动</title>
</head>
<body>
<div id="activityConfigForm" class="form">
    <input name="id"   id="id" class="mini-hidden"/>
    <input name="addId"   id="addId" class="mini-hidden"/>
    <input name="operateActivityCouponId"   id="operateActivityCouponId" class="mini-hidden"/>
    
    <input name="url"  class="mini-hidden" value=""/>
   <input name="type" id="type"  class="mini-hidden"/>
    
    <fieldset style="border:solid 1px #aaa; padding:3px;">
        <legend>活动配置：</legend>
        <table style="width:100%;">
           		<tr>
				    <td width="13%">活动编号：</td>
	                <td>
	               		 <input id="activityCode" name="activityCode" style="width:200px;" class="mini-textbox" enabled="false" />
	                </td>
	                <td>彩种:</td>
	                <td>
	                    <input id="lotteryCode" name="lotteryCode" style="width:200px;"  class="mini-combobox" emptyText="请选择" valueFromSelect="true" onValuechanged="Current.lotteryCodeChange"/>
	                </td>
	                <td>玩法:</td>
                	<td>
                   	   <div id="lotteryChildCode" name="lotteryChildCode" style="width:200px;" class="mini-combobox"   popupWidth="170px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true"  oncloseclick="Current.limitLotteryAddClose">     
						    <div property="columns">
						        <div header="全部" field="text"></div>
						    </div>
						</div>
					</td>
				</tr>
				<tr>
					<td>赠送名单：</td>
					<td colspan="3"><input id="file" name="file" class="mini-htmlfile" limitType="*.txt" style="width:220px;"/> 上传文件声明: 一行一个姓名的" *.txt " 文本。</td>
					<td>是否派发红包：</td>
	                <td>
						<div id="sendRed"  class="mini-checkboxlist" textField="text" valueField="id" data="[{id:'1',text:'是'}]" onvaluechanged="Current.ruleTypeChange()" value="1">
						</div>
	                </td>
				</tr>
			</table>
    </fieldset> 
    <fieldset style="border:solid 1px #aaa; padding:3px;">
         <legend>彩期信息：</legend>
	   	 <table style="width:100%;">
	   		 <tr>
				<td>购买类型: </td>
				<td>
					<input id="giveSendType" class="mini-textbox" style="width:200px;" value="追号"  enabled="false"/>					
				</td>
				<td width="13%">赠送彩期: </td>
				<td>
					<input id ="giveIssue" name="giveIssue"  class="mini-combobox" style="width:200px;" emptyText="请选择" valueFromSelect = "true" />				
				</td>
								
			</tr>
			 <tr>
				<td>每人注数: </td>
				<td>
					<input name="giveNum" id="giveNum"  class="mini-textbox" style="width:200px;" required="true"/>
				</td>				
			</tr>							
		</table>
    </fieldset>   
	<fieldset id="couponFieldSet" style="border:solid 1px #aaa; padding:3px;">
	  	<legend>优惠券信息：</legend>
	  	 <table style="width:100%;">
           		<tr width="13%">
					<td>红包类型: </td>
					<td>
					    <input id ="redType" name="redType"  class="mini-combobox" style="width:200px;" emptyText="请选择" valueFromSelect = "true" required="true" />
					</td>
					<td>初始状态: </td>
					<td>
						<input id ="redStatus" name="redStatus"  class="mini-combobox" style="width:200px;" emptyText="请选择"  valueFromSelect = "true" required="true"/>
					</td>
				</tr>
				<tr>
					<td>红包面额: </td>
					<td>
						<input name="redValue" class="mini-textbox" style="width:200px;" required="true" onvalidation="Current.onMoneyValidation"/>
					</td>
					<td>最低消费: </td>
					<td>
						<input id="minSpendAmount" name="minSpendAmount" class="mini-textbox" vtype="int" style="width:200px;" vtype="float"/>
					</td>
				</tr>
				<tr>
					<td>有效天数: </td>
					<td>
						<input name="ectivityDay" class="mini-textbox" style="width:200px;" vtype="int" required="true"/>
					</td>
					<td>激活截止天数: </td>
					<td>
						<input id="activeEndTime" name="activeEndTime" class="mini-textbox" style="width:200px;"/>
					</td>					
				</tr>
				<tr>
					<td>生成数量: </td>
					<td>
						<input name="limitNum" id="limitNum" class="mini-textbox" required="true" style="width:200px;" vtype="int;range:0,10"/>
					</td>
					<td>限制平台: </td>
					<td>
						<div id="limitPlatform" name="limitPlatform" class="mini-combobox" style="width:200px;"  popupWidth="200px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true"  oncloseclick="Cms.onCloseClick">     
						    <div property="columns">
						        <div header="全部" field="text"></div>
						    </div>
						</div>						
					</td>					
				</tr>
				<tr>
					<td>限制彩种: </td>
					<td>
						<div id="limitLottery" name="limitLottery" class="mini-combobox" style="width:200px;"  popupWidth="170px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true"  oncloseclick="Current.limitLotteryAddClose" onvaluechanged="Current.limitLotteryChange()" >     
						    <div property="columns">
						        <div header="全部" field="text"></div>
						    </div>
						</div>
					</td>
					<td>加奖玩法: </td>
					<td>
						<div id="limitLotteryChild" name="limitLotteryChild" class="mini-combobox" style="width:200px;"  popupWidth="200px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true"  oncloseclick="Cms.onCloseClick">     
						    <div property="columns">
						        <div header="全部" field="text"></div>
						    </div>
						</div>						
					</td>											
				</tr>				
				<tr>																				
					<td>加奖类型: </td>
					<td>
						<div id="limitLotteryChildType" name="limitLotteryChildType" class="mini-combobox" style="width:200px;"  popupWidth="200px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true"  oncloseclick="Cms.onCloseClick" >     
						    <div property="columns">
						        <div header="全部" field="text"></div>
						    </div>
						</div>						
					</td>
					<td>红包名称: </td>
					<td>
						<input id="redName" name="redName" class="mini-textbox"  style="width:200px;"/>
					</td>
				</tr>				
				<tr>
					<td>推荐入口: </td>
					<td>
						<div id="operateLotteryId" name="operateLotteryId" class="mini-combobox" style="width:200px;" 
						    emptyText="请选择" required="true" valueFromSelect = "true" multiSelect="true"  showClose="true"  oncloseclick="Cms.onCloseClick">     
						</div>
					</td>
					<td clospan="2" align="left">
						注:可复选。
					</td>
				</tr>
				<tr>
					<td>红包标签: </td>
					<td colspan="3">
						<input id="label" class="mini-combobox" style="width:18%;" emptyText="请选择"  valueFromSelect = "true"
						 data="[{id:'1',text:'无'},{id:'2',text:'新用户专用'},{id:'3',text:'VIP专用'},{id:'4',text:'自定义'},]" onvaluechanged="Current.labelChange()" />
						<input id="redLabel" name="redLabel" class="mini-textbox" style="width:61%;" emptyText="最多输入5个字" enabled="false" vtype="maxLength:5"/>
					</td>
				</tr>	
			</table>	
	  </fieldset>
	  
	   <fieldset style="border:solid 1px #aaa; padding:3px;">
	    <legend>操作信息：</legend>
	    <table style="width:100%;">
	          <tr>
	              <td style="width:100px;">创建时间</td>
	              <td style="width:150px;"><input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	              <td style="width:100px;">修改时间</td>
	              <td style="width:150px;"><input name="modifyTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	          		<td style="width:100px;" rowspan="2">备注</td>
	              <td style="width:150px;" rowspan="2"><input name="lotteryDesc" class="mini-textarea"/></td>
	          </tr>
	          <tr>
	              <td style="width:100px;">创建人</td>
	              <td style="width:150px;"><input name="createBy" class="mini-textbox" enabled="false"/></td>
	              <td style="width:100px;">修改人</td>
	              <td style="width:150px;"><input name="modifyBy" class="mini-textbox" enabled="false"/></td>
	          </tr>
	      </table>
	  </fieldset>
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
	    	<btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="Current.merge">保存</a>&nbsp&nbsp&nbsp
				<a class="mini-button" onclick="Current.send">活动派送</a>&nbsp&nbsp&nbsp
			</btn:operate>
				<a class="mini-button" onclick="Current.closeConfigWindow">关闭</a>
		</div>			  
</div>	    

</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/activityConfigDetail/annual_meet.js?vsersion=<%=version%>" type="text/javascript"></script>
<script type="text/javascript">
    mini.parse();
    function initData(code) {
        Current.init(code);
    }
</script>
</html>

