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
    <input name="reqUrl"  class="mini-hidden" value="operatemgr/activity/config/merge"/>
    <fieldset style="border:solid 1px #aaa; padding:3px;">
        <legend>活动配置：</legend>
        <table style="width:100%;">
            <tr>
                <td width="15%">活动编号</td>
                <td width="35%"><input id="activityCode" name="activityCode" class="mini-textbox" style="width: 200px;" enabled="false"/></td>
                <td width="17%">彩种</td>
                <td width="33%">
                    <input id="lotteryCode" name="lotteryCode" style="width:80%;"  class="mini-combobox" emptyText="请选择" valueFromSelect="true" onValuechanged="Current.lotteryCodeChange"/>
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
                <td width="15%">同一用户享受次数</td>
                <td width="35%">
                    <input id="offerNum" name="offerNum" style="width: 100px" class="mini-textbox"/>
                </td>
                <td width="17%">同一真实姓名享受人数</td>
                <td width="33%">
                    <input id="realUserNum" name="realUserNum" style="width:100px" class="mini-textbox"/>
                </td>
            </tr>
            <tr>
                <td width="15%">合买是否参加</td>
                <td width="35%">
                    <div id="isFollowOrder" name="isFollowOrder" class="mini-radiobuttonlist"
                         textField="text" valueField="id">
                    </div>
                </td>
                <td width="17%">方案来源</td>
                <td width="33%">
                    <input id="url" name="url" style="width:200px" class="mini-textbox"/>
                </td>
            </tr>
            <tr>
                <td width="15%">返奖范围</td>
                <td width="35%">
                    <div id="rebateUserType" name="rebateUserType" class="mini-radiobuttonlist" textField="text" valueField="id"></div>
                </td>
                <td width="17%">活动对象</td>
                <td width="33%">
                    <div id="userType" name="userType" class="mini-radiobuttonlist" textField="text" valueField="id" ></div>
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
                <div field="lotteryCode" type="comboboxcolumn" headerAlign="center" align="center">彩种
                    <input property="editor" class="mini-combobox" data="Dic.lotteryCode" />
                </div>
                <div field="lotteryChildCode" type="comboboxcolumn" headerAlign="center" align="center">子玩法
                    <input property="editor" class="mini-combobox" data="Dic.lotteryChildCode" />
                </div>
                <div field="lotteryPassType" headerAlign="center" align="center">过关方式
                    <input property="editor" class="mini-combobox" data="Dic.lotteryPassType" />
                </div>
                <div field="ruleType" type="comboboxcolumn" headerAlign="center" align="center">规则类型
                    <input property="editor" class="mini-combobox" data="Dic.ruleType" />
                </div>
                <div field="ruleStart" headerAlign="center" align="center">开始</div>
                <div field="ruleEnd" headerAlign="center" align="center">结束</div>
                <div field="status" type="comboboxcolumn" headerAlign="center" align="center">是否有效
                    <input property="editor" class="mini-combobox" data="Dic.isYesOrNo" />
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
</div>

<div id="activityRuleWindow" class="mini-window" style="width:50%;height:40%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
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
                <td width="25%">
                    <input id="lotteryPassType" name="lotteryPassType" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/>
                </td>
                <td width="25%" align="center"></td>
                <td width="25%">
                </td>
            </tr>
            <tr>
                <td width="25%">
                    <input id="ruleType" name="ruleType" style="width:100px;"  class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true" onValuechanged="Current.ruleTypeChange"/>
                </td>
                <td id="ruleObj" colspan="3" width="75%">
                </td>
            </tr>
        </table>
        <fieldset style="border:solid 1px #aaa; padding:3px;height: 170px;">
            <div style="width:100%;">
                <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
                    <table style="width:100%;">
                        <tr>
                            <td width="10%" nowrap="nowrap">
                                <a class="mini-button" iconCls="icon-add" plain="true" onclick="Cms.addEditRow('activityBet_datagrid')">新增</a>
                                <a  class="mini-button" iconCls="icon-remove" plain="true" onclick="Cms.deleteRow('activityBet_datagrid')">删除</a>
                                <a  class="mini-button" iconCls="icon-save" plain="true" onclick="Current.mergeActivityBet()">确定</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="activityBetForm" class="form">
            <div showCollapseButton="true">
                <div id="activityBet_datagrid" class="mini-datagrid" style="width:100%;height:140px;"
                     onrowdblclick="Cms.editRow('activityBet_datagrid')" allowResize="true"  showPager="false"   multiSelect="true">
                    <div property="columns">
                        <div type="checkcolumn" width="3%"></div>
                        <div field="num" headerAlign="center" align="center">投注倍数
                            <input  property="editor"  class="mini-textbox"  required="true"/>
                        </div>
                        <div field="money" headerAlign="center" align="center">减免金额
                            <input  property="editor"  class="mini-textbox"  required="true"/>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </fieldset>
    </div>
</div>

</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/activityConfigDetail/multiple_bet.js?vsersion=<%=version%>" type="text/javascript"></script>
<script type="text/javascript">
    mini.parse();
    function initData(code) {
        Current.init(code);
    }
</script>
</html>

