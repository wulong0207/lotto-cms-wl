<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>完善资料送</title>
</head>
<body>
    <div id="activityConfigForm" class="form">
        <input name="id"  class="mini-hidden"/>
        <input name="url"  class="mini-hidden" value="operatemgr/activity/config/merge"/>
	    <input name="type" id="type"  class="mini-hidden"/>
        <fieldset style="border:solid 1px #aaa; padding:3px;">
            <legend>活动配置：</legend>
            <table style="width:100%;">
                <tr>
                    <td width="18%">活动编号</td>
                    <td width="32%"><input id="activityCode" name="activityCode" class="mini-textbox" enabled="false"/></td>
                    <td width="18%"></td>
                    <td width="32%"></td>
                </tr>
                <tr>
                    <td width="18%">用户行为</td>
                    <td width="32%">
                        <input id="userType" name="userType" class="mini-combobox" style="width: 180px" emptyText="请选择" valueFromSelect="true" required="true">
                    </td>
                    <td width="18%">相同姓名返还人数</td>
                    <td width="32%">
                        <input id="realUserNum" name="realUserNum" vtype="int;min:1" style="width:80%;" class="mini-textbox"/>
                    </td>
                </tr>
                <tr>
                    <td width="18%">同一手机号码参与次数</td>
                    <td width="32%">
                        <input id="mobileNum" name="mobileNum" vtype="int;min:1" style="width:80%;" class="mini-textbox"/>
                    </td>
                    <td width="18%">同一真实用户参与次数</td>
                    <td width="32%">
                        <input id="offerNum" name="offerNum" vtype="int;min:1" style="width:80%;" class="mini-textbox"/>
                    </td>
                </tr>
                <tr>
                    <td width="18%">单个用户获取上限</td>
                    <td width="32%">
                        <input id="singleUserMoney" name="singleUserMoney" vtype="int;min:1" style="width:80%;" class="mini-textbox"/>
                    </td>
                    <td width="18%">赠送总数</td>
                    <td width="32%">
                        <input id="activityDayNum" name="activityDayNum" vtype="int;min:1" style="width:80%;" class="mini-textbox"/>
                    </td>
                </tr>
                <tr>
                    <td width="18%">赠送</td>
                    <td width="32%">
                        <input id="configType" name="configType" class="mini-combobox" style="width: 180px" emptyText="请选择" valueFromSelect="true" required="true" onvaluechanged="Current.configTypeChange">
                    </td>
                    <td width="50%" colspan="2">
                        <div name="isRealUser" class="mini-checkbox" trueValue="1" falseValue="0" readOnly="false" text="已注册是否参与"></div>
                    </td>
                </tr>
            </table>
        </fieldset>

        <fieldset style="border:solid 1px #aaa; padding:3px;height: 550px;">
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
            <div id="activityCoupon_datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:350px;"
                 idField="id" multiSelect="true" showPager="false" url="operatemgr/activity"
                 showColumnsMenu="true" onrowdblclick="Current.edit" showFilterRow="false">
                <div property="columns">
                	<div type="checkcolumn"></div>
					<div field="redType"  type= "comboboxcolumn" headerAlign="center" align="center">红包类型
			     		<input property="editor" class="mini-combobox" data="Dic.redType" />
					</div>
					<div field="redValue"  headerAlign="center" align="center">红包面值</div>
					<div field="redStatus"  type= "comboboxcolumn" headerAlign="center" align="center">红包状态
					     <input property="editor" class="mini-combobox" data="Dic.redStatus" />
					</div>
					<div field="limitNum"  headerAlign="center" align="center">生成数量</div>	
					<div field="ectivityDay"  headerAlign="center" align="center">有效天数</div>						
					<div field="activeEndTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">激活截止天数</div>
				</div>
            </div>
            <div id="activityLotto_datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:350px;"
                 idField="id" multiSelect="true" showPager="false" url="operatemgr/activity"
                 showColumnsMenu="true" onrowdblclick="Current.edit" showFilterRow="false">
                <div property="columns">
                	<div type="checkcolumn"></div>
					<div field="lotteryCode"  type= "comboboxcolumn" headerAlign="center" align="center">彩种名称
						<input property="editor" class="mini-combobox" data="Dic.lotteryCode" />
					</div>
					<div field="lotteryChildName" headerAlign="center" align="center">子玩法
					</div>
					<div field="pickType"  type="comboboxcolumn" headerAlign="center" align="center">选号方式
						<input property="editor" class="mini-combobox" data="Dic.pickType" />
					</div>
					<div field="buyType"  type="comboboxcolumn" headerAlign="center" align="center">购买类型
						<input property="editor" class="mini-combobox" data="Dic.buyType" />
					</div>
					<div field="contentType"  type="comboboxcolumn" headerAlign="center" align="center">内容类型
						<input property="editor" class="mini-combobox" data="Dic.contentType" />
					</div>
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

       <div id="activityCouponWindow" class="mini-window" style="width:70%;height:40%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
            <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
                <btn:operate privilege="ADD|UPD">
                    <a class="mini-button" onclick="Current.mergeActivityCoupon">确认</a>
                    <a class="mini-button" onclick="Current.closeCouponWindow">取消</a>
                </btn:operate>
            </div>
            <div id="activityCouponForm" class="form">
		    	<input id="operation" name="operation"  class="mini-hidden"/>
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
							<input id="activeEndTime" name="activeEndTime" class="mini-textbox" style="width:200px;"  vtype="int"  required="true"/>
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
	    </div>
        </div>
        
        <div id="activityLottoWindow" class="mini-window" style="width:70%;height:20%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
            <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
                <btn:operate privilege="ADD|UPD">
                    <a class="mini-button" onclick="Current.mergeActivityLotto">确认</a>
                    <a class="mini-button" onclick="Current.closeLottoWindow">取消</a>
                </btn:operate>
            </div>
            <div id="activityLottoForm" class="form">
		    	<input id="operation" name="operation"  class="mini-hidden"/>
			    <table style="width:100%;">
	           		<tr>
						<td>彩种名称: </td>
						<td>
							<input id="lotteryCode" name="lotteryCode" class="mini-combobox" style="width:200px;" emptyText="请选择" required="true"  valueFromSelect = "true" onvaluechanged="Current.lotteryCodeChange"/>
						</td>
						<td>子玩法: </td>
						<td>
							<input id="lotteryChildCode"  name="lotteryChildCode" class="mini-combobox" style="width:200px;" emptyText="请选择" required="true" valueFromSelect = "true" />
						</td>
					</tr>
					<tr>
						<td>选号方式: </td>
						<td>
							<input id="pickType" name="pickType" class="mini-combobox" style="width:200px;" emptyText="请选择" required="true"  valueFromSelect = "true"/>
						</td>
						<td>购买类型: </td>
						<td>
							<input id="buyType" name="buyType" class="mini-combobox" style="width:200px;" emptyText="请选择" required="true" valueFromSelect = "true" />
						</td>
					</tr>
					<tr>
						<td>内容类型: </td>
						<td>
							<input id="contentType" name="contentType" class="mini-combobox" style="width:200px;" emptyText="请选择" required="true" valueFromSelect = "true" />
						</td>
						<td></td>
						<td></td>
					</tr>
	       </table>
	    </div>
        </div>
    </div>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/activityConfigDetail/new_user.js?vsersion=<%=version%>" type="text/javascript"></script>
<script type="text/javascript">
    mini.parse();
    function initData(code) {
        Current.init(code);
    }
</script>
</html>

