<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>resources/js/public/jscolor.min.js"></script>  
    <title>抽奖活动</title>
</head>
<body>
	    <div id="activityConfigForm" class="form">
	    	<input id="id" name="id"  class="mini-hidden"/>
	     	<input name="url"  class="mini-hidden" value=""/>
	     	<input name="type" id="type"  class="mini-hidden"/>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <legend>活动配置：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td style="width:100px;">活动编号</td>
	                <td style="width:150px;"><input id="activityCode" name="activityCode" class="mini-textbox" style="width:80%;" enabled="false"/></td>	                
	            </tr>            
	            <tr>
	                
	                <td style="width:100px;">优惠限制</td>
	                <td style="width:150px;">
						<div id="preResType" name="preResType" class="mini-checkboxlist" textField="text" valueField="id">
						</div>					
					</td>
	                <td style="width:100px;">享受优惠次数上限</td>
	                <td style="width:150px;">
	                <input id="offerNum" name="offerNum" style="width:80%;" class="mini-textbox"/>	                
	                </td>
	            </tr>
	            <tr>
	          	    <td style="width:100px;">同一真实名称享受人数</td>
	                <td style="width:150px;">
	                <input id="realUserNum" name="realUserNum" style="width:80%;" class="mini-textbox"/>
	                </td>					
	                <td style="width:100px;">每日派送次数</td>                
	                <td style="width:150px;">
	                	<input id="activityDayNum" name="activityDayNum" style="width:80%;" class="mini-textbox"/>	                
	                </td>
	            </tr>
	            <tr>
	                <td style="width:100px;">活动对象</td>
	                <td style="width:150px;">
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
										<btn:operate privilege="UPD">
									        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.moveUp('prizeAwardDatagrid')">上移</a>
								        </btn:operate>
												<btn:operate privilege="UPD">
									        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.moveDown('prizeAwardDatagrid')">下移</a>
								        </btn:operate>
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
		  		<div id="prizeAwardDatagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:350px;" 
						  idField="id" multiSelect="true" showPager="false" 
						 showColumnsMenu="true" onrowdblclick="Current.edit" showFilterRow="false">
						<div property="columns">
							<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
							<div field="awardText"  headerAlign="center" align="center">奖品名称</div>
							<div field="probability"  headerAlign="center" align="center">概率</div>
							<div field="type"  type= "comboboxcolumn" headerAlign="center" align="center">抽奖类型
					     		<input property="editor" class="mini-combobox" data="Dic.awardType" />
							</div>
							<div field="typeCode"  headerAlign="center" align="center">活动编号</div>
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
	                <td width="12%">抽奖类型:</td>
	                <td width="38%">
						<div id="awardType" name="type" class="mini-radiobuttonlist"
						    textField="text" valueField="id" onValuechanged="Current.awardTypeChange">
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
           		<tr>
					<td width="12%">红包类型: </td>
					<td width="38%"> 
					    <input id ="redType" name="redType"  class="mini-combobox" style="width:200px;" emptyText="请选择" valueFromSelect = "true" required="true" />
					</td>
					<td width="12%">最低消费: </td>
					<td width="38%">
						<input id="minSpendAmount" name="minSpendAmount" class="mini-textbox" vtype="int" style="width:200px;" vtype="float"/>
					</td>
				</tr>
				<tr>
					<td>红包面额: </td>
					<td>
						<input name="redValue" class="mini-textbox" style="width:200px;" required="true" onvalidation="Current.onMoneyValidation"/>
					</td>
					<td>可用渠道: </td>
					<td>
						<div id="channelId_add" name="channelId" class="mini-treeselect" style="width:200px;"  popupWidth="300px;"
						textField="name" valueField="id" parentField="pid" checkRecursive="true" 
        				showFolderCheckBox="true"  expandOnLoad="true" showTreeLines="true"autoCheckParent="true" 
						multiSelect="true" emptyText="请选择" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"> 
    					<div property="columns">
					        <div type="indexcolumn"></div>
					        <div field="name" name="name"  width="120" >全部</div>
					    </div>						    
						</div>						
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
					<td>有效天数: </td>
					<td>
						<input name="ectivityDay" class="mini-textbox" style="width:200px;" vtype="int" required="true"/>
					</td>				
				</tr>	
				</table>														
		   </fieldset> 
		   <fieldset id="couponFieldSet" style="border:solid 1px #aaa; padding:3px;">
		   	 <legend>优惠券信息：</legend>
		   	 <table style="width:100%;">
				<tr>
					<td width="12%">奖品名称: </td>
					<td width="38%">
						<input name="awardText" class="mini-textbox" style="width:200px;" required="true"/>
					</td>
					<td width="12%">颜色: </td>
					<td width="38%">
						<input id="awardColor" name="awardColor" class="jscolor {hash:true}" style="width:200px;" value="000000"/>
					</td>					
				</tr>
				<tr>
					<td>概率: </td>
					<td>
						<input id="probability" name="probability" class="mini-textbox" style="width:200px;" required="true"/>
					</td>
					<td id="typeCodeTd">活动编号: </td>
					<td>
					    <input allowInput="true"  id ="typeCode" name="typeCode"  class="mini-combobox" style="width:200px;" emptyText="请选择" valueFromSelect = "true" />
					</td>
				</tr>
				 <tr>
					 <td>图片：</td>
					 <td>
						 <input id="awardImg" name="awardImg"  class="mini-hidden"/>
						 <btn:operate privilege="UPD">
						 <input type="button" value="从图库选择" onclick="Current.openImage()"/>
						 </btn:operate>
					 </td>
					 <td><img  id ="img" alt="奖项图片" width="100px;" height="100px;"></td>
					 <td></td>
				 </tr>
				</table>													
		   </fieldset>                     
       
    </div>
	</div>	    
	    	
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/activityConfigDetail/prize_award.js?vsersion=<%=version%>" type="text/javascript"></script>
<script type="text/javascript">
    mini.parse();
    function initData(code) {
        Current.init(code);
    }
</script>
</html>

