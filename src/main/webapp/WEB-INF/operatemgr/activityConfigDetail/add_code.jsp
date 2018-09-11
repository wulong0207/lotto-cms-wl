<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>追号送</title>
</head>
<body>
<div id="activityConfigForm" class="form">
    <input name="id"   id="id" class="mini-hidden"/>
    <input name="url"  class="mini-hidden" value=""/>
   <input name="type" id="type"  class="mini-hidden"/>
    
    <fieldset style="border:solid 1px #aaa; padding:3px;">
        <legend>活动配置：</legend>
        <table style="width:100%;">
            <tr>
                <td width="15%">活动编号</td>
                <td width="15%"><input id="activityCode" name="activityCode" width="80%" class="mini-textbox" enabled="false" /></td>
                <td width="10%">彩种</td>
                <td width="15%">
                    <input id="lotteryCode" name="lotteryCode" width="80%"  class="mini-combobox" emptyText="请选择" valueFromSelect="true" onValuechanged="Current.lotteryCodeChange"/>
                </td>
                <td width="10%">玩法</td>
                <td width="15%">
                    <div id="lotteryChildCode" name="lotteryChildCode" width="80%" class="mini-combobox"   popupWidth="170px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true"  oncloseclick="Current.limitLotteryAddClose">     
						    <div property="columns">
						        <div header="全部" field="text"></div>
						    </div>
						</div>
                <td width="10%">大乐透追加</td>
               <td width="8%">
						<div id="isDltAdd" name="isDltAdd" width="80%" class="mini-radiobuttonlist"
						    textField="text" valueField="id">
						</div>
	            </td>
            </tr>
           
            <tr>
			        <td>同一真实名称享受人数</td>
	                <td>
	                <input id="realUserNum" name="realUserNum" width="80%" class="mini-textbox"/>
	                </td>	             
	                <td>每日派送次数</td>                
	                <td>
	                	<input id="activityDayNum" name="activityDayNum"  width="80%" class="mini-textbox"/>	                
	                </td>
	                <td>是否允许撤单</td>
	                <td>
						<div id="isCancel" name="isCancel" width="80%" class="mini-radiobuttonlist"
						    textField="text" valueField="id">
						</div>
	           		 </td>
	           	    <td>中奖是否享受</td>
	                <td>
						<div id="isAward" name="isAward" class="mini-radiobuttonlist"
						    textField="text" valueField="id">
						</div>
	           		 </td>
	          </tr>
	          <tr>
	          		<td>同一用户享受次数</td>
	                <td>
	                <input id="offerNum" name="offerNum" width="80%" class="mini-textbox"/>	                
	                </td>
	                <td>合买是否参加</td>
	                <td>
						<div id="isFollowOrder" name="isFollowOrder" class="mini-radiobuttonlist"
						    textField="text" valueField="id">
						</div>
	                </td>	               
	           		 <td>平台来源</td>
	                 <td colspan="4" width="80%">
						<div id="platfrom" name="platfrom" class="mini-checkboxlist" textField="text" valueField="id" >
						</div>
	                 </td>
	           </tr>
	            <tr>
                <td>活动来源</td>
                <td>
                    <input id="url" name="url" class="mini-textbox" width="80%"/>
                </td>
                <td>活动对象</td>
                <td>
					<div id="userType" name="userType" class="mini-radiobuttonlist"
					    textField="text" valueField="id">
					</div>
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
		  		<div id="addCodeDatagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:350px;" 
						  idField="id" multiSelect="true" showPager="false" 
						 showColumnsMenu="true" onrowdblclick="Current.edit" showFilterRow="false">
						<div property="columns">
							<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
							<div field="ruleType"  type= "comboboxcolumn" headerAlign="center" align="center">活动类型
					     		<input property="editor" class="mini-combobox" data="Dic.ruleType" />
							</div>
							<div field="addNum"  headerAlign="center" align="center">追号期数</div>
							<div field="giveNum"  headerAlign="center" align="center">赠送期数</div>
							<div field="singleUserNum"  headerAlign="center" align="center">同一用户享受次数</div>
							<div field="ruleOfferNum"  headerAlign="center" align="center">每日派送次数</div>
							
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
	    
	<div id="activityRuleWindow" class="mini-window" style="width:70%;height:56%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
 		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
	    <btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="Current.mergeActivityRule">确认</a>
				<a class="mini-button" onclick="Current.closeRuleWindow">取消</a>
			</btn:operate>
		</div>
	    <div id="activityRuleForm" class="form">
	    	<input id="operation" name="operation"  class="mini-hidden"/>
	    	<input  name="id"  class="mini-hidden"/>
	    	<fieldset style="border:solid 1px #aaa; padding:3px;">
	    	 <legend>抽奖类型：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td width="12%">赠送类型:</td>
	                <td width="38%">
						<div id="ruleType" name="ruleType" class="mini-radiobuttonlist"
						    textField="text" valueField="id" onValuechanged="Current.ruleTypeChange">
						</div>
	                </td>
	                 <td width="12%"></td>
	                 <td width="38%">
	                </td>
	            </tr>
	        </table>
	    	</fieldset>
	    	<fieldset id="couponFieldSet" style="border:solid 1px #aaa; padding:3px;">
		   	 <legend>优惠券信息：</legend>
		   	 <table style="width:100%;">
           		 <table style="width:100%;">
           		<tr>
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
		   <fieldset id="couponFieldSet" style="border:solid 1px #aaa; padding:3px;">
		   	 <legend>期数信息：</legend>
		   	 <table style="width:100%;">
				<tr>
					<td width="12%">追号期数: </td>
					<td>
						<input name="addNum" class="mini-textbox" style="width:200px;" required="true"/>
					</td>
					<td>赠送期数: </td>
					<td>
						<input name="giveNum" id="giveNum"  class="mini-textbox" style="width:200px;"/>
					</td>					
				</tr>
				<tr>
					<td>同一用户享受次数: </td>
					<td>
						<input name="singleUserNum" class="mini-textbox" style="width:200px;"/>					
					</td>
					<td>每日派送次数</td>                
	                <td>
	                	<input id="ruleOfferNum" name="ruleOfferNum" style="width:200px;" class="mini-textbox"/>	                
	                </td>
				</tr>
				
				</table>													
		   </fieldset>                     
       
    </div>
	</div>	    

</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/activityConfigDetail/add_code.js?vsersion=<%=version%>" type="text/javascript"></script>
<script type="text/javascript">
    mini.parse();
    function initData(code) {
        Current.init(code);
    }
</script>
</html>

