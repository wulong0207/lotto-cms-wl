<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/top.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <script src="<%=basePath%>resources/js/public/insert_atcaret.js" type="text/javascript"></script>
    <title>库存管理</title>
</head>

<body>
<div id="searchForm" class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
    <table style="width:100%;">
        <tr>
            <td>
                <%-- <btn:operate privilege="SEARCH">
                <a class="mini-button" iconCls="icon-search" plain="true" onclick="TicketObj.viewTicket()">查看详情</a>
                </btn:operate> --%>
                <btn:operate privilege="ADD">
                    <a id="add " class="mini-button" iconCls="icon-add" plain="true"
                       onclick="cdkey.add_cdkey">导入</a>
                </btn:operate>
            </td>
        </tr>
        <tr>
            <td>
                渠道名称:<input required="true" name="channelId" id="channelId" onvaluechanged="cdkey.channel_value_change"
                            class="mini-combobox" style="width:20%;" url="cooperate/channel/dist"/>
                <a class="mini-button" onclick="cdkey.query">查询</a>
            </td>
            <td>
                渠道余额:<input enabled="false" name="balance" id="balance"
                            class="mini-textbox" style="width:20%;" />
            </td>
            <td style="width:20%;" >

            </td>
            <td style="width:20%;" >

            </td>
        </tr>
    </table>
</div>

<div class="mini-splitter" vertical="true" style="width:100%;height:87%;" style="border:0;">
    <div size="100%" showCollapseButton="true" style="border:0;">
        <div id="datagrid" class="mini-datagrid" allowAlternating="true" borderStyle="border:solid 0px;"
             style="width:100%;height:100%;"
             url="cooperate/cdkey/list" idField="id" allowResize="true" pageSize="30" multiSelect="true"
             showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" onrowdblclick="channel.column_onclick"
             showSummaryRow="true">

            <div property="columns">
                <div type="checkcolumn"></div>
                <div type="indexcolumn" headerAlign="center" align="center">序号</div>
                <div field="lotteryCode" headerAlign="center" type="comboboxcolumn" property="editor" align="center">
                    彩种名称
                    <input class="mini-combobox" property="editor" data="Dic.allCode"/>
                </div>
                <div field="total" headerAlign="center" align="center">总数量
                </div>
                <div field="usedTotal" headerAlign="center" align="center">已使用数量</div>
                <div field="usedNewTotal" headerAlign="center" align="center" renderer="channel.column_channel_render">
                    剩余数量
                </div>
                <div headerAlign="center" align="center" renderer="cdkey.action_renderer"
                     dateFormat="yyyy-MM-dd HH:mm:ss">操作
                </div>
            </div>
        </div>
    </div>
</div>

<div id="editWindow" class="mini-window" title="兑换详情"
     style="width:1500px; height:600px;" showMaxButton="false"
     showCollapseButton="false" showShadow="true" showToolbar="false"
     showFooter="true" showModal="true" allowResize="true" allowDrag="true">
    <div id="queryForm" class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
        <table style="width:100%;">
            <tr>
                <td>
                    兑换状态:<input required="true" name="stauts" id="stauts" data="Dic.status"
                                oncloseclick="Cms.onCloseClick" emptyText="请选择兑换状态" allowInput="true"
                                valueFromSelect="true" showClose="true"
                                class="mini-combobox"/>
                    中心来源:<input name="ticketChannel" id="ticketChannel_info" data="Dic.ticketChannelId"
                                oncloseclick="Cms.onCloseClick"
                                class="mini-combobox" emptyText="请选择中心来源" allowInput="true" valueFromSelect="true"
                                showClose="true"/>
                    兑换渠道:<input name="channelId" id="channelId_info" url="cooperate/channel/dist"
                                class="mini-combobox" emptyText="请选择兑换渠道" oncloseclick="Cms.onCloseClick"
                                allowInput="true" valueFromSelect="true" showClose="true"/>
                    本站兑换码:<input name="myCdkey" id="myCdkey" oncloseclick="Cms.onCloseClick"
                                 class="mini-textbox"/>
                </td>
                <td>
                    变动时间开始:<input required="true" name="modifyTimeBegin" id="modifyTimeBegin"
                                  oncloseclick="Cms.onCloseClick" emptyText="请输入变动时间" allowInput="true"
                                  valueFromSelect="true" showClose="true" class="mini-datepicker" required="true"
                                  format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss"
                />
                    变动时间结束:<input name="modifyTimeEnd" id="modifyTimeEnd" class="mini-datepicker" required="true"
                                  format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss"
                                  oncloseclick="Cms.onCloseClick"
                                  emptyText="请输入变动时间" allowInput="true" valueFromSelect="true"
                                  showClose="true"/>
                    <a class="mini-button" onclick="cdkey.queryInfo">查询</a>
                    <a class="mini-button" onclick="cdkey.export_excel">导出</a>
                    <input required="true" name="lotteryCode" id="lotteryCode"
                           class="mini-hidden"/>
                </td>
            </tr>
        </table>
    </div>
    <div id="editForm">

        <fieldset style="border:solid 1px #aaa; padding:3px;">
            <legend>兑换详情:</legend>
            <div style="padding:5px;">
                <table width="100%;">
                    <tr>
                        <td width="13%">兑换码总数量:</td>
                        <td width="20%"><input id="total" required="true" class="mini-textbox"
                                               name="total" enabled="false"/></td>
                        <td width="13%">已使用数量:</td>
                        <td width="20%"><input id="usedTotal" required="true" enabled="false" name="usedTotal"
                                               class="mini-textbox" style="width:100%;"/></td>
                        <td width="13%">剩余总数量：</td>
                        <td width="20%"><input id="usedNewTotal" required="true" enabled="false" name="usedNewTotal"
                                               class="mini-textbox" style="width:100%;"/></td>
                    </tr>
                </table>
            </div>
            <div size="100%" showCollapseButton="true" style="border:0;">
                <div id="recode_list_grid" class="mini-datagrid" allowAlternating="true" borderStyle="border:solid 0px;"
                     style="width:100%;height:100%;"
                     idField="id" allowResize="true" pageSize="30" multiSelect="true" url="cooperate/cdkey/recode-list"
                     showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true"
                     showSummaryRow="true" showfooter="false">
                    <div property="columns">
                        <div type="indexcolumn" headerAlign="center" align="center">序号</div>
                        <div field="" align="center" headeralign="center" width="250">
                            兑换码
                            <div property="columns" align="center">
                                <div field="lottoCdkey" name="lottoCdkey" width="150" headeralign="center" allowsort="true" align="center">
                                    中心兑换码
                                </div>
                                <div field="myCdKey" name="myCdKey" width="100" headeralign="center" allowsort="true" align="center">
                                    本站兑换码
                                </div>
                            </div>
                        </div>
                        <div field="exchangeChannel" width="100" type="comboboxcolumn" property="editor"
                             headerAlign="center" align="center">中心来源
                            <input property="editor" class="mini-combobox" data="Dic.ticketChannelId"/>
                        </div>
                        <div field="stauts" width="100" type="comboboxcolumn" property="editor" headerAlign="center"
                             align="center">兑换状态
                            <input class="mini-combobox" property="editor" data="Dic.status"/>
                        </div>
                        <div field="lotteryCode" width="100" type="comboboxcolumn" property="editor"
                             headerAlign="center"
                             align="center">兑换彩种
                            <input class="mini-combobox" property="editor" data="Dic.allCode"/>
                        </div>
                        <div field="channelId" type="comboboxcolumn" property="editor" width="100" headerAlign="center"
                             align="center">兑换渠道
                            <input class="mini-combobox" property="editor" url="cooperate/channel/dist"/>
                        </div>
                        <div field="userId" width="100" headerAlign="center" align="center">兑换用户ID
                        </div>
                        <div field="account" width="100" headerAlign="center" align="center">兑换用户账号
                        </div>
                        <div field="createTime" width="100" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss"
                             align="center">创建时间
                        </div>
                        <div field="exchangeOverTime" width="100" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss"
                             align="center">兑换过期时间
                        </div>
                        <div field="exchangeRecordTime" width="100" headerAlign="center"
                             dateFormat="yyyy-MM-dd HH:mm:ss" align="center">兑换时间
                        </div>
                        <div field="modifyTime" width="100" headerAlign="center"
                             dateFormat="yyyy-MM-dd HH:mm:ss" align="center">修改时间
                        </div>
                    </div>
                </div>
            </div>
        </fieldset>
    </div>
</div>

<div id="importWindow" class="mini-window" title="导入库存"
     style="width:350px; height:200px;" showMaxButton="false"
     showCollapseButton="false" showShadow="true" showToolbar="false"
     showFooter="true" showModal="true" allowResize="true" allowDrag="true">

    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
        <btn:operate privilege="SAVE|UPLOAD">
            <a class="mini-button" onclick="cdkey.upload">保存</a>
        </btn:operate>
    </div>

    <div id="importForm">
        <fieldset style="border:solid 1px #aaa; padding:3px;">
            <legend>导入库存</legend>
            <input required="true" id="url" name="url" class="mini-hidden"/>
            <input required="true" id="action" name="action" class="mini-hidden"/>
            <div style="padding:5px;">
                <form method="post" enctype="multipart/form-data" id="uploadFileForm" style="align:inline;">
                    <table width="100%;">
                        <tr>
                            <td width="20%">渠道名称:<input required="true" name="ticketChannel" id="ticketChannel"
                                                        class="mini-combobox" style="width:70%;"
                                                        data="Dic.ticketChannelId"/>
                            </td>

                        </tr>
                        <%--<tr>--%>
                            <%--<td width="20%">彩种:--%>
                                <%--<input name="lotteryCode" id="lotteryCode"--%>
                                       <%--class="mini-combobox" data="Dic.allCode" style="width:80%;"/>--%>
                            <%--</td>--%>
                        <%--</tr>--%>
                        <tr>
                            <td width="20%">文件:
                                <input name="file" id="file"
                                       class="mini-htmlfile" style="width:80%;"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </fieldset>
    </div>
</div>

</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/cooperate/cdkey.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>