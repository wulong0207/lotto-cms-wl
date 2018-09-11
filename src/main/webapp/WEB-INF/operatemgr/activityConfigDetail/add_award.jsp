<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>加奖活动</title>
</head>
<body>
	    <div id="activityConfigForm" class="form">
	    	<input name="id"  class="mini-hidden"/>
	     	<input name="url"  class="mini-hidden" value="operatemgr/activity/config/merge"/>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <legend>活动配置：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td width="15%">活动编号</td>
	                <td width="35%"><input id="activityCode" name="activityCode" class="mini-textbox" style="width:80%;" enabled="false"/></td>
	                <td width="15%">彩种</td>
	                <td width="35%">
	                 <input id="lotteryCode" name="lotteryCode" style="width:50%;"  class="mini-combobox" emptyText="请选择" valueFromSelect="true" onValuechanged="Current.lotteryCodeChange"/>
					</td>
	            </tr>
	            <tr>
	                <td width="15%"></td>
	                <td width="35%"></td>
	                <td colspan="2" width="50%">
					    <div id="lotteryChildCode" name="lotteryChildCode" class="mini-checkboxlist"
					        textField="text" valueField="id" >
					    </div>
	                </td>
	            </tr>
	            <tr>
	                <td width="15%">单个方案返奖上限</td>
	                <td width="35%">
	                <input id="singleOrderMoney" name="singleOrderMoney" style="width:80%;" class="mini-textbox"/>
	                </td>
	                <td width="15%">用户单日返奖上限</td>
	                <td width="35%">
	                <input id="singleUserDayMoney" name="singleUserDayMoney" style="width:80%;" class="mini-textbox"/>
	                </td>
	            </tr>
	            <tr>
	                <td width="15%">返奖范围</td>
	                <td width="35%">
						<div id="rebateUserType" name="rebateUserType" class="mini-radiobuttonlist"
						    textField="text" valueField="id">
						</div>
	                </td>
	                <td width="15%">合买是否参加</td>
	                <td width="35%">
						<div id="isFollowOrder" name="isFollowOrder" class="mini-radiobuttonlist"
						    textField="text" valueField="id">
						</div>
	                </td>
	            </tr>
	            <tr>
	                <td width="15%">保底是否参加</td>
	                <td width="35%">
						<div id="isCareOrder" name="isCareOrder" class="mini-radiobuttonlist"
						    textField="text" valueField="id">
						</div>
	                </td>
	                <td width="15%">平台来源</td>
	                <td width="35%">
						<div id="platfrom" name="platfrom" class="mini-checkboxlist" textField="text" valueField="id" >
						</div>
	                </td>
	            </tr>
	            <tr>
					 <td>图片：</td>
					 <td>
						 <input id="icon" name="icon"  class="mini-hidden"/>
						 <btn:operate privilege="UPD">
						 <input type="button" value="从图库选择" onclick="Current.openImage()"/>
						 </btn:operate>
					 </td>
					 <td><img  id ="img" alt="奖项图片" width="100px;" height="100px;"></td>
					 <td></td>
				 </tr>
				   <tr>
	                <td width="15%">移动端图标跳转链接</td>
	                <td width="35%">
	                <input id="mobileIconUrl" name="mobileIconUrl" style="width:80%;" class="mini-textbox"/>
	                </td>
	                <td width="15%">pc端图标跳转链接</td>
	                <td width="35%">
	                <input id="pcIconUrl" name="pcIconUrl" style="width:80%;" class="mini-textbox"/>
	                </td>
	            </tr>
	        </table>
			  </fieldset>
			  
			  <fieldset style="border:solid 1px #aaa; padding:3px;height: 500px;">
			  	<legend>活动规则信息：</legend>
			  	<div style="width:100%;">
			        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
			            <table style="width:100%;">
	            			<tr>
									<td width="10%" nowrap="nowrap">
								        <btn:operate privilege="ADD">
									        <a class="mini-button" iconCls="icon-add" plain="true" onclick="Current.add">新增</a>
								        </btn:operate>
								        <btn:operate privilege="UPD">
									        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.edit">修改</a>
								        </btn:operate>
								        <btn:operate privilege="UPD">
									        <a class="mini-button" iconCls="icon-remove" plain="true" onclick="Current.del">删除</a>
								        </btn:operate>
									</td>
								</tr>
			            </table>
			        </div>
			    </div>
		  		<div id="activityRule_datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:350px;"
						  idField="id" multiSelect="true" showPager="false" url="operatemgr/activity"
						 showColumnsMenu="true" onrowdblclick="Current.edit" showFilterRow="false">
						<div property="columns">
							<div type="checkcolumn" width="3%"></div>
							<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
<!-- 							<div field="lotteryCode" width="8%" type="comboboxcolumn" headerAlign="center" align="center">彩种
								<input property="editor" class="mini-combobox" data="Dic.lotteryCode" />
							</div> -->
							<div field="lotteryChildCode" type="comboboxcolumn" headerAlign="center" align="center">玩法
								<input property="editor" class="mini-combobox" data="Dic.lotteryChildCode" />
							</div>
							<div field="contentType" type="comboboxcolumn" headerAlign="center" align="center">内容类型
								<input property="editor" class="mini-combobox" data="Dic.contentType" />
							</div>
							<div field="lotteryPassType" headerAlign="center" align="center">过关方式
								<input property="editor" class="mini-combobox" data="Dic.lotteryPassType" />
							</div>
							<div field="ruleType" type="comboboxcolumn" headerAlign="center" align="center">规则类型
						     <input property="editor" class="mini-combobox" data="Dic.ruleType" />
							</div>
							<div field="ruleStart" headerAlign="center" align="center">开始</div>
							<div field="ruleEnd" headerAlign="center" align="center">结束</div>
							<div field="winStartMoney" headerAlign="center" align="center">中奖金额开始</div>
							<div field="winEndMoney" headerAlign="center" align="center">中奖金额结束</div>
							<div field="rebateType" type="comboboxcolumn" headerAlign="center" align="center">返利类型
								<input property="editor" class="mini-combobox" data="Dic.rebateType" />
							</div>
							<div field="rebateNum" headerAlign="center" align="center">返利金额</div>
						</div>
						</div>
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
				<a class="mini-button" onclick="Current.merge">保存</a>
			</btn:operate>
				<a class="mini-button" onclick="Current.closeConfigWindow">关闭</a>
		</div>			  
	    </div>
	    
	<div id="activityRuleWindow" class="mini-window" style="width:55%;height:35%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
 		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
	    <btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="Current.mergeActivityRule">确认</a>
				<a class="mini-button" onclick="Current.closeRuleWindow">取消</a>
			</btn:operate>
		</div>
	    <div id="activityRuleForm" class="form">
	    	<input id="operation" name="operation"  class="mini-hidden"/>
		    <table style="width:100%;">
           <tr>
               <td width="25%">玩法</td>
               <td><input id="lotteryPassType" name="lotteryPassType" class="mini-combobox" style="width: 180px;" emptyText="请选择" valueFromSelect="true" required="true"/>               		
              </td>               
           </tr>
            <tr>
            <td id="addTd" width="33%"> 仅限追加投注 </td>
             <td id="addDiv">
                <div id="isAdd" name="isAdd" class="mini-radiobuttonlist"
						    textField="text" valueField="id">
					</div>
              </td>
           </tr>
           <tr>
              <td width="25%">内容类型</td>
               <td>
              		 <div id="contentType" name="contentType" class="mini-combobox" style="width:200px;"  popupWidth="200px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true" required="true" oncloseclick="Cms.onCloseClick">     
						    <div property="columns">
						    </div>
						</div>
               </td>  
           </tr>
           <tr>
               <td> <input id="ruleType" name="ruleType" style="width:70%;"  class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true" onValuechanged="Current.ruleTypeChange"/></td>
               <td id="ruleObj">
               </td>
           </tr>
           <tr>
               <td>中奖金额区间</td>
               <td>
				   <input id="winStartMoney" name="winStartMoney" class="mini-textbox" style="width: 180px;" required="true" onvalidation="Current.floatValid" /> 至
				   <input id="winEndMoney" name="winEndMoney" class="mini-textbox" style="width: 180px;" required="true" onvalidation="Current.floatValid"/></td>
           </tr>
           <tr>
               <td>本站返利金额</td>
               <td>
				   <input id="rebateType" name="rebateType" class="mini-combobox" style="width: 180px;" emptyText="请选择" valueFromSelect="true" required="true"/>
				   &nbsp;&nbsp;&nbsp;
				   <input id="rebateNum" name="rebateNum" class="mini-textbox" style="width: 180px;" required="true"/></td>
           </tr> 
           <tr>
               <td>是否倍数</td>
               <td>
					<div id="isMultiple" name="isMultiple" class="mini-radiobuttonlist"
						    textField="text" valueField="id">
					</div>	               
               </td>
           </tr>                     
       </table>
    </div>
	</div>	    
	    	
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/activityConfigDetail/add_award.js?vsersion=<%=version%>" type="text/javascript"></script>
<script type="text/javascript">
    mini.parse();
    function initData(code,type,activityStartTime,activityEndTime) {
        Current.init(code,type,activityStartTime,activityEndTime);
    }
</script>
</html>

