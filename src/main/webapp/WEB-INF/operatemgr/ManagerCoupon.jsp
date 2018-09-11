<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>管理优惠券</title>
</head>
<body>
    <div id="couponConfigForm" class="form">
		  <fieldset style="border:solid 1px #aaa; padding:3px;height: 90%;">
		  	<legend>优惠券信息：</legend>
		  	<div style="width:100%;">
		        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
		            <table style="width:100%;">
            			<tr>
							<td width="10%" nowrap="nowrap">
						        <btn:operate privilege="ADD">
							        <a class="mini-button" iconCls="icon-add" plain="true" onclick="Current.addwin">新增</a>
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
	  		<div id="coupon_datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:350px;" 
					  idField="id" multiSelect="true" showPager="false" url="operatemgr/msginfo/couponList"
					 showColumnsMenu="true" onrowdblclick="Current.edit" showFilterRow="false">
					<div property="columns">
						<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
						<div field="redName"  headerAlign="center" align="center">红包名称</div>
						<div field="redType"  type= "comboboxcolumn" headerAlign="center" align="center">红包类型
					    <input property="editor" class="mini-combobox" data="Dic.redType" />
						</div>
						<div field="activityCode"  headerAlign="center" align="center">活动编号</div>
						<div field="redValue"  headerAlign="center" align="center">红包面值</div>	
						<div field="redStatus"  type= "comboboxcolumn" headerAlign="center" align="center">红包状态
					     <input property="editor" class="mini-combobox" data="Dic.redStatus" />
						</div>						
						<div field="ectivityDay"  headerAlign="center" align="center">有效天数</div>						
					</div>
			</div>
		  </fieldset>
 		 <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
	    	<btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="Current.merge">保存</a>
			</btn:operate>
				<a class="mini-button" onclick="Current.closeConfigWindow">关闭</a>
		</div>	
    </div>
	    
	<div id="couponWindow" class="mini-window" title="创建优惠券信息" 
		style="width:900px; height:500px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		<div id="couponForm">
			<input id="operation" name="operation"  class="mini-hidden"/>
			<table width="100%;">
				<tr>
					<td>红包名称: </td>
					<td>
					    <input id ="redName" name="redName" class="mini-textbox" style="width:200px;" required="true" vtype="maxLength:20"/>
					</td>
					<td>活动编号: </td>
					<td>
						<input id="activityCode" name="activityCode" class="mini-textbox" style="width:200px;" vtype="maxLength:20" required="true"/>
					</td>
				</tr>			
				<tr>
					<td>红包类型: </td>
					<td>
					    <input id ="redType" name="redType"  class="mini-combobox" style="width:200px;" emptyText="请选择" valueFromSelect = "true" required="true" />
					</td>
					<td colspan="2"> </td>
				</tr>
				<tr>
					<td>红包面额: </td>
					<td>
						<input name="redValue" class="mini-textbox" style="width:200px;" required="true" onvalidation="Current.onMoneyValidation"/>
					</td>
					<td>最低消费: </td>
					<td>
						<input id="minSpendAmount" name="minSpendAmount" class="mini-textbox" vtype="int" style="width:200px;" vtype="float" required="true"/>
					</td>
				</tr>
				<tr>
					<td>初始状态: </td>
					<td>
						<input id ="redStatus" name="redStatus"  class="mini-combobox" style="width:200px;" emptyText="请选择"  valueFromSelect = "true" required="true"/>
					</td>
					<td>激活截止: </td>
					<td>
						<input id="activeEndTime" name="activeEndTime" class="mini-datepicker" style="width:200px;" showTime="true" allowInput="false" showClose="true" showOkButton="true" showClearButton="false" timeFormat="HH:mm:ss" format="yyyy-MM-dd HH:mm:ss"/>
					</td>					
				</tr>
				<tr>
					<td>有效天数: </td>
					<td>
						<input name="ectivityDay" id="ectivityDay" class="mini-textbox" style="width:200px;" vtype="int" required="true"/>
					</td>
					<td>限制平台: </td>
					<td>
						<div id="limitPlatform" name="limitPlatform" class="mini-combobox" style="width:200px;"  popupWidth="200px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick">     
						    <div property="columns">
						        <div header="全部" field="text"></div>
						    </div>
						</div>
					</td>					
				</tr>
				<tr>
					<td>限制渠道: </td>
					<td>
						<div id="channelId" name="channelId" class="mini-treeselect" style="width:200px;"  popupWidth="300px;"
						textField="name" valueField="id" parentField="pid" checkRecursive="true" 
        				showFolderCheckBox="true"  expandOnLoad="true" showTreeLines="true"
						multiSelect="true" emptyText="请选择" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"> 
    					<div property="columns">
					        <div type="indexcolumn"></div>
					        <div field="name" name="name"  width="120" >全部</div>
					    </div>						    
						</div>						
					</td>
					<td>限制彩种: </td>
					<td>
						<div id="limitLottery" name="limitLottery" class="mini-combobox" style="width:200px;"  popupWidth="170px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true"  oncloseclick="Current.limitLotteryAddClose" onvaluechanged="Current.limitLotteryChange()" >     
						    <div property="columns">
						        <div header="全部" field="text"></div>
						    </div>
						</div>
					</td>										
				</tr>
				<tr>
					<td>加奖玩法: </td>
					<td>
						<div id="limitLotteryChild" name="limitLotteryChild" class="mini-combobox" style="width:200px;"  popupWidth="200px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true"  oncloseclick="Cms.onCloseClick">     
						    <div property="columns">
						        <div header="全部" field="text"></div>
						    </div>
						</div>						
					</td>
					<td>加奖类型: </td>
					<td>
						<div id="limitLotteryChildType" name="limitLotteryChildType" class="mini-combobox" style="width:200px;"  popupWidth="200px;" 
						multiSelect="true" emptyText="请选择" valueFromSelect = "true" showClose="true"  oncloseclick="Cms.onCloseClick" >     
						    <div property="columns">
						        <div header="全部" field="text"></div>
						    </div>
						</div>						
					</td>						
				</tr>
				<tr>
					<td>生成数量: </td>
					<td>
						<input name="limitNum" class="mini-textbox" required="true" style="width:200px;" vtype="int;range:0,10"/>
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
						<input id="label" class="mini-combobox" style="width:18%;" emptyText="请选择"  valueFromSelect = "true" required="true"
						 data="[{id:'1',text:'无'},{id:'2',text:'新用户专用'},{id:'3',text:'VIP专用'},{id:'4',text:'自定义'},]"  onvaluechanged="Current.labelChange()"/>
						 <input id="redLabel" name="redLabel" class="mini-textbox" style="width:61%;" emptyText="最多输入5个字" enabled="false" vtype="maxLength:5"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<button style="margin-top: 50px;" id="operateBtn" onclick="Current.add()">确认</button>
					     <input name="url"  class="mini-hidden"/>
						 <input name="action"  class="mini-hidden"/>
						 <input name="configId"  class="mini-hidden"/>
						 <input name="configType"  class="mini-hidden" value="2"/>
						 <input name="id"  class="mini-hidden" />
					</td>
					<td colspan="2" align="left">
						<button style="margin-top: 50px;" id="closeBtn" onclick="Current.closeAddWin();">取消</button>
					</td>					
				</tr>
			</table>
		</div>
</div>
	     	
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/manager_coupon.js?vsersion=<%=version%>" type="text/javascript"></script>
<script type="text/javascript">
    mini.parse();
    function initData(templateId) {
        Current.init(templateId);
    }
</script>
</html>

