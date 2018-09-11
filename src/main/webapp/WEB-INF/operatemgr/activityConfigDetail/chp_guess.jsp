<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>世界杯冠军竞猜</title>
    <style type="text/css">
        .asLabel .mini-textbox-input{
            text-align: center;
            font-size: 15px;
        }
    </style>
</head>
<body>
<div id="activityConfigForm" class="form">
    <input name="id"  class="mini-hidden"/><%--operate_activity_config表的id,用来判断是新增还是更新--%>
    <input name="type" id="type"  class="mini-hidden"/>
    <fieldset style="border:solid 1px #aaa; padding:3px;">
        <legend>活动配置：</legend>
        <table style="width:100%;">
            <tr>
                <td>活动编号</td>
                <td><input id="activityCode" name="activityCode" class="mini-textbox" style="width:200px;" enabled="false"/></td>
                <td>
                    <input name="myTeam" id="targetTeam" class="mini-combobox"
                           emptyText="请选择球队" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" textField="homeName"/>
                    <a class="mini-button" onclick="Current.draw">开奖</a>
                    <a class="mini-button" onclick="Current.send">派奖</a>
                </td>
            </tr>
            <tr>
                <td>优惠限制</td>
                <td>
                    <div name="discountStr" class="mini-checkboxlist"
                         data="[{id:1,text:'真实用户'},{id:2,text:'手机号码'}]" >
                    </div>
                </td>
            </tr>
            <tr>
                <td>同一真实名称享受人数</td>
                <td>
                    <input name="realUserNum" class="mini-spinner" maxValue="4294967296" />
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
                                <a class="mini-button" iconCls="icon-add" plain="true" onclick="Current.toAdd">添加赛事</a>
                            </btn:operate>
                            <a class="mini-button" iconCls="icon-upload" plain="true" onclick="Cms.moveUp('activityRule_datagrid')">上移</a>
                            <a class="mini-button" iconCls="icon-download" plain="true" onclick="Cms.moveDown('activityRule_datagrid')">下移</a>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="activityRule_datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:500px;"
             idField="id" multiSelect="true" showPager="false" url="operatemgr/activity/chp/team" onrowdblclick="Cms.editRow('activityRule_datagrid',Current.saveTeamBeforeEdit)"
             showColumnsMenu="true" showFilterRow="false"
             editNextRowCell="true">
            <div property="columns">
                <div type="checkcolumn" width="3%"></div>
                <div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
                <div field="teamGroup" width="8%" headerAlign="center" align="center">小组
                    <input property="editor" class="mini-combobox" data="[{id:'A',text:'A'},{id:'B',text:'B'},{id:'C',text:'C'},{id:'D',text:'D'},{id:'E',text:'E'},{id:'F',text:'F'},{id:'G',text:'G'},{id:'H',text:'H'}]"/>
                </div>
                <div field="homeName" width="8%" headerAlign="center" align="center">球队</div>
                <div field="matchStatus" type="comboboxcolumn" headerAlign="center" align="center">竞猜状态
                    <input property="editor" class="mini-combobox" data="Dic.matchStatus" enabled="false"/>
                </div>
                <div field="winStatus" type="comboboxcolumn" headerAlign="center" align="center">开/派奖
                    <input property="editor" class="mini-combobox" data="[{id:1,text:'未开奖'},{id:2,text:'已开奖'},{id:3,text:'已派奖'}]" enabled="false"/>
                </div>
                <div field="realityUser" headerAlign="center" align="center">实际人数</div>
                <div field="localUser" headerAlign="center" align="center">竞猜总人数
                    <input property="editor" class="mini-textbox" vtype="int"/>
                </div>
                <div field="realityJackpot" headerAlign="center" align="center">实际奖池</div>
                <div field="localJackpot" headerAlign="center" align="center">奖金池（元）
                    <input property="editor" class="mini-textbox" vtype="float"/>
                </div>
                <div field="realScore" headerAlign="center" align="center">实际贡献值</div>
                <div field="localScore" headerAlign="center" align="center">虚拟贡献值</div>                
            </div>
        </div>
    </fieldset>

    <fieldset style="border:solid 1px #aaa; padding:3px;height: 400px;">
        <legend>用户榜</legend>
        <div style="width:100%;">
            <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
                <table style="width:100%;">
                    <tr>
                        <td width="10%" nowrap="nowrap">
                            <a class="mini-button" iconCls="icon-download" plain="true" onclick="Current.excel">导出</a>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="user_datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:350px;"
             idField="id" multiSelect="true" url="operatemgr/activity/chp/user"
             showColumnsMenu="true" showFilterRow="false" pageSize="50"
             >
            <div property="columns">
                <div type="checkcolumn" width="3%"></div>
                <div type="indexcolumn" width="3%" align="center" headerAlign="center">排名</div>
                <div field="nickName" width="8%" headerAlign="center" align="center">用户名</div>
                <div field="totalScore" headerAlign="center" align="center">贡献值</div>
                <div field="totalRebateMoney" headerAlign="center" align="center">瓜分奖金（元）</div>
            </div>
        </div>
    </fieldset>

    <fieldset style="border:solid 1px #aaa; padding:3px;">
        <legend>竞猜用户</legend>
        <form method="post" enctype="multipart/form-data" id="uploadFileForm" style="align:inline;">
            <input type="file" name="file" id="file">
            <btn:operate privilege="UPLOAD">
            <a class="mini-button" onclick="Current.upload">上传</a>
            </btn:operate>
        </form>
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
            <a class="mini-button" onclick="Current.merge">保存</a>
        <a class="mini-button" onclick="Current.closeConfigWindow">关闭</a>
    </div>
</div>

<div id="sportAgainstWindow" class="mini-window" style="width:80%;height:80%" showFooter="true" showModal="true" allowResize="true" allowDrag="true" title="新增冠军竞猜信息">
        <a class="mini-button" onclick="Current.mergeSportAgainst">确认</a>
        <a class="mini-button" onclick="Current.closeSportAgainstWindow">取消</a>
    <div id="sportAgainstForm" class="form">
        <input id="operation" name="operation" class="mini-hidden"/>
        <div id="sportAgainst_datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;"
             url="lotterymgr/gj/page" idField="id" allowResize="true"  multiSelect="true" pageSize="50"
             showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
             onrowdblclick="Current.edit" onpreload="MiniCom.onpreload">
            <div property="columns">
                <div type="checkcolumn" width="3%"></div>
                <div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
                <div field="homeName" width="8%" headerAlign="center" align="center">球队</div>
            </div>
        </div>
    </div>
</div>

</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/activityConfigDetail/chp_guess.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
<script type="text/javascript">
    mini.parse();
    function initData(code) {
        Current.init(code);
    }
</script>
</html>