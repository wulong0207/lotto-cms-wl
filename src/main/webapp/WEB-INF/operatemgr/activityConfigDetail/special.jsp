<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>特定赛事加奖</title>
    <style type="text/css">
        .asLabel .mini-textbox-input{
            text-align: center;
            font-size: 15px;
        }
    </style>
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
                <td>活动编号</td>
                <td><input id="activityCode" name="activityCode" class="mini-textbox" style="width:200px;" enabled="false"/></td>
                <td>彩种</td>
                <td>
                    <input id="lotteryCode" name="lotteryCode" style="width: 150px;"  class="mini-combobox" emptyText="请选择" valueFromSelect="true" onValuechanged="Current.lotteryCodeChange"/>
                </td>
            </tr>
            <tr>
                <td colspan="3" width="60%"></td>
                <td width="40%">
                    <div id="lotteryChildCode" name="lotteryChildCode" class="mini-checkboxlist"
                         textField="text" valueField="id" >
                    </div>
                </td>
            </tr>
            <tr>
                <td>同一用户返奖上限</td>
                <td>
                    <input id="singleUserMoney" name="singleUserMoney" style="width:200px;" class="mini-textbox" required="true"/>
                </td>
                <td>同真实姓名享受人数</td>
                <td>
                    <input id="realUserNum" name="realUserNum" style="width:200px;" class="mini-textbox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>合买是否参加</td>
                <td>
                    <div id="isFollowOrder" name="isFollowOrder" class="mini-radiobuttonlist"
                         textField="text" valueField="id">
                    </div>
                </td>
                <td>返奖范围</td>
                <td>
                    <div id="rebateUserType" name="rebateUserType" class="mini-radiobuttonlist"
                         textField="text" valueField="id">
                    </div>
                </td>
            </tr>
        </table>
    </fieldset>

    <fieldset style="border:solid 1px #aaa; padding:3px;height: 520px;">
        <legend>活动规则信息：</legend>
        <div style="width:100%;">
            <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
                <table style="width:100%;">
                    <tr>
                        <td width="10%" nowrap="nowrap">
                            <btn:operate privilege="ADD">
                                <a class="mini-button" iconCls="icon-add" plain="true" onclick="Current.add">添加赛事</a>
                            </btn:operate>
                            <btn:operate privilege="UPD">
                                <a class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.edit">编辑</a>
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
                <div field="systemCode" width="8%" headerAlign="center" align="center">系统编号</div>
                <div field="officialMatchCode" width="8%" headerAlign="center" align="center">赛事编号</div>
                <div field="matchName" width="8%" headerAlign="center" align="center">联赛</div>
                <div field="homeName" width="8%" headerAlign="center" align="center">主队</div>
                <div field="visitiName" width="8%" headerAlign="center" align="center">客队</div>
                <div field="singlePercent" width="8%" headerAlign="center" align="center" renderer="Current.switchSingleStyle">单关加奖</div>
                <div field="passPercent" width="8%" headerAlign="center" align="center" renderer="Current.switchPassStyle">过关加奖</div>
                <div field="startTime" width="20%" headerAlign="center" align="center">开赛时间 - 结束时间</div>
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

<div id="sportAgainstWindow" class="mini-window" style="width:80%;height:75%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
        <btn:operate privilege="ADD|UPD">
            <a class="mini-button" onclick="Current.mergeSportAgainst">确认</a>
            <a class="mini-button" onclick="Current.closeSportAgainstWindow">取消</a>
        </btn:operate>
    </div>
    <div id="sportAgainstForm" class="form">
        <input id="operation" name="operation"  class="mini-hidden"/>
        <div id="sportAgainst_datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;"
             url="operatemgr/activity/sport/list" idField="id" allowResize="true"  multiSelect="true" pageSize="20"
             showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
             onrowdblclick="Current.edit" onpreload="MiniCom.onpreload">
            <div property="columns">
                <div type="checkcolumn" width="3%"></div>
                <div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
                <div field="systemCode" width="8%" headerAlign="center" align="center">系统编号</div>
                <div field="officialMatchCode" width="8%" headerAlign="center" align="center">赛事编号</div>
                <div field="matchName" width="8%" headerAlign="center" align="center">联赛</div>
                <div field="homeName" width="8%" headerAlign="center" align="center">主队</div>
                <div field="visitiName" width="8%" headerAlign="center" align="center">客队</div>
                <div field="matchStatus" width="8%" headerAlign="center" align="center">销售状态</div>
            </div>
        </div>
    </div>
</div>

<div id="activitySportRuleWindow" class="mini-window" style="width:30%;height:20%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
        <btn:operate privilege="ADD|UPD">
            <a class="mini-button" onclick="Current.mergeSportRule">确认</a>
            <a class="mini-button" onclick="Current.closeSportRuleWindow">取消</a>
        </btn:operate>
    </div>
    <div id="activitySportRuleForm" class="form">
        <input id="operate" name="operate"  class="mini-hidden"/>
        <table  style="width:100%;">
            <tr>
                <td colspan="2" align="center">
                    <input id="homeName"  name="homeName" class="mini-textbox asLabel"  readOnly="true" value="textbox" />
                    <span >VS</span>
                    <input id="visitiName" name="visitiName" class="mini-textbox asLabel" readOnly="true" value="textbox" />
                </td>
            </tr>
            <tr>
                <td width="40%" align="center"><span>单关加奖</span></td>
                <td align="center">
                    <input id="singlePercent" name="singlePercent" style="width:150px;" class="mini-textbox" />
                </td>
            </tr>
            <tr>
                <td align="center"><span>过关加奖</span></td>
                <td align="center">
                    <input id="passPercent" name="passPercent" style="width:150px;" class="mini-textbox" />
                </td>
            </tr>
        </table>
    </div>
</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/activityConfigDetail/special.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>resources/js/public/jscolor.min.js"></script>
<script type="text/javascript">
    mini.parse();
    function initData(code) {
        Current.init(code);
    }
</script>
</html>