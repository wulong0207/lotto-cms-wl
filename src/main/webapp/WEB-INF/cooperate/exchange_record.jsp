<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/top.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>商户流水管理</title>
</head>

<body>
<div id="searchForm" class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
    <table>
        <tr>
            <td>
                <btn:operate privilege="ADD">
                    <a id="add " class="mini-button" iconCls="icon-add" plain="true"
                       onclick="exchange_record.recharge_add">资金充值</a>
                </btn:operate>
                <btn:operate privilege="EXPORT">
                    <a id="update " class="mini-button" iconCls="icon-download" plain="true"
                       onclick="exchange_record.export_excel">导出Excel</a>
                </btn:operate>
            </td>
        </tr>
        <tr>
            <td>系统流水号：<input id="serialNumber" name="serialNumber" class="mini-textbox" style="width:150px;"
                             emptyText="请输入系统流水号" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
            </td>
        </tr>
        <tr>
            <td>
                渠道名称：<input required="true" name="channelId" id="channelId_query" showClose="true" oncloseclick="Cms.onCloseClick"
                            class="mini-combobox" style="width:50%;"
                            url="cooperate/channel/dist"/>
            </td>
            <td>
                交易分类：<input id="payClass" name="payClass" class="mini-combobox" style="width:150px;"
                            emptyText="请输入交易分类" allowInput="true"  showClose="true"
                            data="[{id:'1',text:'充值'},{id:'2',text:'兑换'}]" oncloseclick="Cms.onCloseClick"/>
            </td>
            <td>
                彩种：<input name="lotteryCode" id="lotteryCode" emptyText="请输入彩种" showClose="true"
                          class="mini-combobox" style="width:150px;" data="Dic.allCode" oncloseclick="Cms.onCloseClick"/>
            </td>
            <td>
                本站兑换码：<input id="cdKey" name="cdKey" class="mini-textbox" style="width:150px;"  oncloseclick="Cms.onCloseClick"
                             emptyText="请输入本站兑换码" allowInput="true" valueFromSelect="true" showClose="true"/>
            </td>
            <td>
                <input id="field" name="field" class="mini-combobox" style="width:150px;"
                       allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"
                       data="[{id:'account_name',text:'账户名'},{id:'cus_mobile',text:'手机号码'}]"/>
                <input id="fieldValue" name="fieldValue" class="mini-textbox" style="width:150px;"
                       allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
                交易兑换时间：<input id="exchangeRecordBeginTime" name="exchangeRecordBeginTime"  showClose="true" oncloseclick="Cms.onCloseClick"
                              class="mini-datepicker" required="true" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss"
            />
                到 <input id="exchangeRecordEndTime" name="exchangeRecordEndTime" class="mini-datepicker" showClose="true" oncloseclick="Cms.onCloseClick"
                         required="true" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss"/>
            </td>
            <td>
                <a class="mini-button" onclick="exchange_record.query_onclick()">查询</a>
            </td>
        </tr>

    </table>
</div>

<div class="mini-splitter" vertical="true" style="width:100%;height:87%;" style="border:0;">
    <div size="100%" showCollapseButton="true" style="border:0;">
        <div id="datagrid" class="mini-datagrid" allowAlternating="true" borderStyle="border:solid 0px;"
             style="width:100%;height:100%;"
             url="cooperate/exchange-record/list" idField="id" allowResize="true" pageSize="30" multiSelect="true"
             showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true"
             onrowdblclick="exchange_record.grid_onrowdblclick"
             showSummaryRow="true">

            <div property="columns">
                <div type="checkcolumn"></div>
                <div type="indexcolumn" headerAlign="center" align="center">序号</div>
                <div field="channelName" headerAlign="center" align="center">渠道名称
                </div>
                <div field="serialNumber" headerAlign="center" align="center">系统流水号
                </div>
                <div field="payClass" type="comboboxcolumn" headerAlign="center" align="center">交易类型
                    <input property="editor" class="mini-combobox" data="[{id:'1',text:'充值'},{id:'2',text:'兑换'}]"/>
                </div>
                <div field="payAmount" headerAlign="center" align="center">交易金额</div>
                <div field="orderInfo" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">订单信息</div>
                <div field="channelBalance" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss"
                     allowSort="true" showSortIcon="true">渠道总余额
                </div>
                <div field="account" headerAlign="center" align="center" allowSort="true" showSortIcon="true">兑换账户</div>
                <div field="phoneNum" headerAlign="center" align="center" allowSort="true" showSortIcon="true">兑换手机号码
                </div>
                <div field="exchangeRecordTime" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" align="center"
                     allowSort="true" showSortIcon="true">兑换交易时间
                </div>
                <div field="orderCode" headerAlign="center" align="center" allowSort="true" showSortIcon="true">订单编号
                </div>
            </div>
        </div>
    </div>
</div>

<div id="editWindow" class="mini-window" title="流水详情"
     style="width:1000px; height:550px;" showMaxButton="false"
     showCollapseButton="false" showShadow="true" showToolbar="false"
     showFooter="true" showModal="true" allowResize="true" allowDrag="true">

    <div id="editForm">
        <fieldset style="border:solid 1px #aaa; padding:3px;">
            <legend>流水详情</legend>
            <div style="padding:5px;">
                <table width="100%;">
                    <tr>
                        <td width="13%">流水编号:</td>
                        <td width="20%"><input required="true" enabled="false"
                                               name="serialNumber" class="mini-textbox"
                                               valueFromSelect="true" style="width:100%;"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="13%">渠道名称:</td>
                        <td width="20%"><input required="true" enabled="false"
                                               name="channelName" class="mini-textbox" emptyText="请输入"
                                               valueFromSelect="true" style="width:100%;" enabled="false"/></td>
                        <td width="13%">交易类型:</td>
                        <td width="20%"><input required="true" enabled="false" name="payClass"
                                               class="mini-combobox" style="width:100%;"
                                               data="[{id:'1',text:'充值'},{id:'2',text:'兑换'}]"/></td>
                        <td width="13%">渠道余额：</td>
                        <td width="40%">
                            <input id="channelBalance" required="true" enabled="false"
                                   name="channelBalance" class="mini-textbox" emptyText="请输入"
                                   valueFromSelect="true" style="width:100%;" enabled="false"/>
                        </td>
                    </tr>
                    <tr>
                        <td>交易金额：</td>
                        <td><input id="payAmount" name="payAmount" required="true" enabled="false"
                                   class="mini-textbox" style="width:100%;"/>
                        </td>
                        <td>订单信息：</td>
                        <td><input id="orderInfo" name="orderInfo" required="true" enabled="false"
                                   class="mini-textbox" style="width:100%;width:250px;"/>
                        </td>
                        <td>兑换时间：</td>
                        <td><input name="exchangeRecordTime" required="true" enabled="false"
                                   class="mini-datepicker" style="width:100%;width:250px;" format="yyyy-MM-dd HH:mm:ss"
                                   timeFormat="HH:mm:ss"/>
                        </td>
                    </tr>
                    <tr>
                        <td>兑换账号：</td>
                        <td><input id="account" name="account" required="true" enabled="false" class="mini-textbox"
                                   style="width:100%;"/></td>
                        <td>兑换手机号码：</td>
                        <td><input id="phoneNum" name="phoneNum" class="mini-textbox" enabled="false" maxlength="24"
                                   required="true"
                                   style="width:100%;"/></td>
                        <td>订单编号：</td>
                        <td><input id="orderCode" name="orderCode" class="mini-textbox" style="width:250px;"
                                   allowInput="true" valueFromSelect="true" enabled="false" showClose="true"/></td>
                    </tr>

                    <tr>
                        <td>创建时间：</td>
                        <td><input id="createTime" name="createTime" enabled="false" class="mini-datepicker"
                                   maxlength="24" required="true"
                                   format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" style="width:100%;"/></td>
                        <td>修改时间：</td>
                        <td><input id="modifyTime" name="modifyTime" enabled="false" class="mini-datepicker"
                                   maxlength="24" required="true"
                                   format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" style="width:100%;"/></td>
                    </tr>
                    <tr>
                        <td>创建人：</td>
                        <td><input id="createBy" name="createBy" class="mini-textbox" style="width:150px;"
                                   allowInput="true" valueFromSelect="true" enabled="false" showClose="true"/></td>
                        <td>修改人：</td>
                        <td><input id="modifyBy" name="modifyBy" class="mini-textbox" style="width:150px;"
                                   allowInput="true" valueFromSelect="true" enabled="false" showClose="true"/></td>
                        <td>描述：</td>
                        <td><input id="desc" name="desc" class="mini-textbox" style="width:150px;"
                                   allowInput="true" valueFromSelect="true" enabled="false" showClose="true"/></td>
                    </tr>
                    </tr>
                </table>
            </div>
        </fieldset>
    </div>
</div>

<div id="rechargeWindow" class="mini-window" title="资金充值"
     style="width:1000px; height:200px;" showMaxButton="false"
     showCollapseButton="false" showShadow="true" showToolbar="false"
     showFooter="true" showModal="true" allowResize="true" allowDrag="true">

    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
        <btn:operate privilege="SAVE">
            <a class="mini-button" onclick="exchange_record.save_recharge">保存</a>
        </btn:operate>
    </div>

    <div id="rechargeForm">
        <fieldset style="border:solid 1px #aaa; padding:3px;">
            <legend>资金充值</legend>
            <input required="true" id="url" name="url" value="cooperate/exchange-record/save" class="mini-hidden"/>
            <input required="true" id="action" name="action" value="POST" class="mini-hidden"/>
            <div style="padding:5px;">
                <table width="100%;">
                    <tr>
                        <td width="20%">渠道名称:<input required="true" name="channelId" id="channelId"
                                                    class="mini-combobox" style="width:50%;"
                                                    url="cooperate/channel/dist"/>
                        </td>
                        <td width="20%">充值:<input required="true" id="recharge" name="payClass"
                                                  class="mini-combobox" style="width:50%;" value="2"
                                                  onvaluechanged="exchange_record.recharge_change"
                                                  data="[{id:'2',text:'余额'},{id:'1',text:'彩种'}]"/>
                            <input name="lotteryCode" id="lotteryCode_edit" visible="false"
                                   class="mini-combobox" style="width:30%;"/>
                        </td>
                    </tr>
                    <tr>
                        <td>充值数量:<input name="payAmount" required="true"
                                        class="mini-textbox" style="width:50%;"/>
                        </td>
                        <td>充值描述:<input name="desc"
                                        class="mini-textarea" style="width:100%;width:250px;"/>
                        </td>
                    </tr>
                </table>
            </div>
        </fieldset>
    </div>
</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/cooperate/exchange_record.js?vsersion=<%=version%>"
        type="text/javascript"></script>
</html>