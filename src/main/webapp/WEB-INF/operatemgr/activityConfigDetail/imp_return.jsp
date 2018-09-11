<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>不中返</title>
</head>
<body>
<div id="detailWindow" title="新增彩种运营详情" showCloseButton="false" <%--class="mini-window"--%> style="width:1100px;height:750px" showFooter="true" showModal="true" allowResize="true" allowDrag="true" showCloseButton="true">
    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
        <btn:operate privilege="ADD|UPD">
            <a class="mini-button" onclick="operate_lottery.merge">保存</a>
        </btn:operate>
        <a class="mini-button" onclick="operate_lottery.closeWindow">关闭</a>
    </div>
    <div id="detailForm" class="form">
        <input name="id"  class="mini-hidden"/>
        <input name="url"  class="mini-hidden"/>
        <input name="action"  class="mini-hidden"/>
        <fieldset style="border:solid 1px #aaa; padding:3px;">
            <legend>配置详情</legend>
            <table width="100%">
                <tr height="30px">
                    <td width="25%">
                        活动编号
                    </td>
                    <td width="25%">
                        <input id="activityCode_edit" name="activityCode" class="mini-textbox" enabled="false" style="width:200px;" vtype ="maxLength:15" required="true" />
                    </td>
                    <td width="20%">
                        彩种
                        <%--<div id="playMethod" class="mini-checkboxlist" repeatItems="1" textField="text" valueField="id"></div>--%>
                    </td>
                    <td width="30%">
                        <input id="lotteryId_edit" name="lotteryId" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  allowInput="true" style="width:200px;" required="true" />
                    </td>
                </tr>
                <tr height="30px">
                    <td width="25%">
                        单个方案返奖上限
                    </td>
                    <td width="25%">
                        <input id="single_edit" name="single" class="mini-textbox" style="width:200px;"  vtype ="maxLength:15"  required="true" />
                    </td>
                    <td width="20%">
                        同一用户购彩金上限
                    </td>
                    <td width="30%">
                        <input id="same_edit" name="same" class="mini-textbox" style="width:200px;"  vtype ="maxLength:15"  required="true"/>
                    </td>
                </tr>
                <tr height="30px">
                    <td width="25%">
                        返奖范围
                    </td>
                    <td width="25%">
                        <input id="rate_edit" name="rate" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  allowInput="true" style="width:100px;" required="true" />
                    </td>
                    <td width="20%">
                        合买是否参与
                    </td>
                    <td width="30%">
                        <input id="shop_edit" name="shop" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  allowInput="true" value="参与" style="width:100px;" required="true" />
                    </td>
                </tr>
                <tr height="30px">
                    <td width="25%">
                        保底是否参与
                    </td>
                    <td width="25%">
                        <input id="minimum_edit" name="minimum" class="mini-combobox" emptyText="请选择" valueFromSelect = "true" value="是"  allowInput="true" style="width:100px;" required="true" />
                    </td>
                    <td width="20%">
                        来源
                    </td>
                    <td width="30%">
                        <div id="platform" class="mini-checkboxlist" repeatItems="1" textField="text" valueField="id"></div>
                    </td>
                </tr>
            </table>
        </fieldset>

        <fieldset style="border:solid 1px #aaa; padding:3px;height: 500px;">
            <legend>彩种显示信息：</legend>
            <div style="width:100%;">
                <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
                    <table style="width:100%;">
                        <tr>
                            <td width="10%" nowrap="nowrap">
                                <btn:operate privilege="UPD">
                                    <a class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.moveUp('lotteryInfoDatagrid')">上移</a>
                                </btn:operate>
                                <btn:operate privilege="UPD">
                                    <a class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.moveDown('lotteryInfoDatagrid')">下移</a>
                                </btn:operate>
                                <btn:operate privilege="ADD">
                                    <a class="mini-button" iconCls="icon-add" plain="true" onclick="operate_lottery.addLotteryInfo">新增</a>
                                </btn:operate>
                                <btn:operate privilege="UPD">
                                    <a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_lottery.editLotteryInfo">修改</a>
                                </btn:operate>
                                <btn:operate privilege="UPD">
                                    <a class="mini-button" iconCls="icon-remove" plain="true" onclick="operate_lottery.deleteLotteryInfo">删除</a>
                                </btn:operate>
                                <%--
                                <btn:operate privilege="UPD">
                                    <a class="mini-button" iconCls="icon-save" plain="true" onclick="operate_lottery.edit()">保存</a>
                                </btn:operate>
                                --%>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="lotteryInfoDatagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height: 450px;"
                 idField="id" multiSelect="true" showPager="false"
                 showColumnsMenu="true" onrowdblclick="operate_lottery.editLotteryInfo" showFilterRow="false">
                <div property="columns">
                    <div type="checkcolumn" width="3%"></div>
                    <div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
                    <div field="typeAlias" headerAlign="center" align="center">彩种别名</div>
                    <div field="categoryId" type="comboboxcolumn" headerAlign="center" align="center">彩种类型
                        <input property="editor" class="mini-combobox" data="Dic.categoryType" />
                    </div>
                    <div field="icon" type="comboboxcolumn" headerAlign="center" align="center">图标
                        <input property="editor" class="mini-combobox" data="Dic.icon" />
                    </div>
                    <div field="typeKey" headerAlign="center" align="center">运营文案</div>
                    <div field="typeKeyUrl" headerAlign="center" align="center">文案链接</div>
                    <div field="typeUrl" headerAlign="center" align="center">彩种链接</div>
                    <div field="display" type="comboboxcolumn" headerAlign="center" align="center">是否显示
                        <input property="editor" class="mini-combobox" data="Dic.display" />
                    </div>
                </div>
            </div>
        </fieldset>
    </div>
</div>




<%--<div id="lotteryInfoWindow" title="彩种修改" class="mini-window" style="width:50%;height:50%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
        <btn:operate privilege="ADD|UPD">
            <a class="mini-button" onclick="operate_lottery.mergeDetailRow('lotteryInfoDatagrid','lotteryInfoForm','lotteryInfoWindow')">确认</a>
            <a class="mini-button" onclick="operate_lottery.cancelLotteryInfo">取消</a>
        </btn:operate>
    </div>
    <div id="lotteryInfoForm" class="form">
        <input class="mini-hidden" name="id"/>
        <input class="mini-hidden" name="operation"/>

        <table style="width:100%;">


            <tr>
                <td>彩种类型</td>
                <td><input id="categoryId" name="categoryId" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true" onvaluechanged="operate_lottery.categoryIdChange"/></td>
                <td>彩种名称</td>
                <td><input id="typeId" name="typeId" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true" onvaluechanged="operate_lottery.lotteryTypeChange"/></td>
                <td>子彩种</td>
                <td><input id="lotteryChildCode" name="lotteryChildCode" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="false"/></td>
            </tr>
            <tr>
                <td>彩种别名</td>
                <td><input name="typeAlias" class="mini-textbox" required="true"/></td>
                <td>彩种链接</td>
                <td><input name="typeUrl" class="mini-textbox"/></td>
                <td>彩种颜色</td>
                <td><input id="color" class="jscolor {hash:true}" value="FF6600"></td>
            </tr>
            <tr>
                <td>是否显示</td>
                <td><input id="display" name="display" class="mini-combobox" emptyText="请选择" onvaluechanged="operate_lottery.displayChange" valueFromSelect="true" required="true"/></td>
                <td>下线时间</td>
                <td><input id="infoOfflineTime" name="offlineTime" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" enabled="false" showTime="true" timeFormat="HH:mm:ss"/></td>

            </tr>
        </table>
        <fieldset style="border:solid 1px #aaa; padding:3px;">
            <table style="width:100%;">
                <tr>
                    <td>运营图标</td>
                    <td><input id="icon" name="icon" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
                    <td>打开方式</td>
                    <td><input id="target" name="target" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
                </tr>
                <tr>
                    <td>运营文案</td>
                    <td><input name="typeKey" class="mini-textbox"/></td>
                </tr>
                <tr>
                    <td>文案链接</td>
                    <td><input name="typeKeyUrl" class="mini-textbox"/></td>
                </tr>
                <tr>
                    <td rowspan="2">广告图地址</td>
                    <td colspan="3"><input name="url" id="url" class="mini-textbox" style="width:100%;" enabled="false"/></td>
                    <td>
                        <btn:operate privilege="UPD">
                            <input type="button" value="从图库选择" onclick="operate_lottery.openImage()"/>
                        </btn:operate>
                    </td>
                </tr>
                <tr>
                    <td colspan="4"><img id="adImg" alt="广告图片" width="500px;" height="300px;"></td>
                </tr>
            </table>
        </fieldset>
    </div>
</div>--%>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/edit/configDetail.js" type="text/javascript"></script>
</html>