<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/top.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>商户渠道管理</title>
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
                       onclick="channel.add_channel">新增</a>
                </btn:operate>
                <btn:operate privilege="UPD|SEARCH">
                    <a id="update " class="mini-button" iconCls="icon-edit" plain="true"
                       onclick="channel.column_onclick">修改</a>
                </btn:operate>
                <btn:operate privilege="DEL">
                    <a id="delete " class="mini-button" iconCls="icon-remove" plain="true"
                       onclick="channel.del_channel">删除</a>
                </btn:operate>
            </td>
        </tr>
        <tr>
            <td>
                渠道ID：<input id="channelId" class="mini-textbox" style="width:150px;"
                            emptyText="请输入渠道ID" allowInput="true" valueFromSelect="true" showClose="true"/>
                渠道名称：<input id="channelName" class="mini-textbox" style="width:150px;"
                            emptyText="请输入渠道名称" allowInput="true" valueFromSelect="true" showClose="true"/>
                <a class="mini-button" onclick="channel.channel_query_onclick">查询</a>
            </td>
        </tr>
    </table>
</div>

<div class="mini-splitter" vertical="true" style="width:100%;height:87%;" style="border:0;">
    <div size="100%" showCollapseButton="true" style="border:0;">
        <div id="datagrid" class="mini-datagrid" allowAlternating="true" borderStyle="border:solid 0px;"
             style="width:100%;height:100%;"
             url="cooperate/channel/list" idField="id" allowResize="true" pageSize="30" multiSelect="true"
             showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true" onrowdblclick="channel.column_onclick"
             showSummaryRow="true">

            <div property="columns">
                <div type="checkcolumn"></div>
                <div type="indexcolumn" headerAlign="center" align="center">序号</div>
                <div field="marketChannelId" headerAlign="center" align="center">渠道id
                </div>
                <div field="cooperateName" headerAlign="center" align="center">渠道名称
                </div>
                <div field="balance" headerAlign="center" align="center">账户余额</div>
                <div field="isStop" headerAlign="center" type="comboboxcolumn" property="editor"  align="center">是否启动
                    <input property="editor" class="mini-combobox"  data="[{id:'0',text:'禁止'},{id:'1',text:'启动'}]"/>
                </div>
                <div field="type" headerAlign="center" type="comboboxcolumn" property="editor" align="center">商户类型
                    <input property="editor" class="mini-combobox"
                           data="Dic.type"/>
                </div>
                <div field="ticketChannel" headerAlign="center" property="editor" type="comboboxcolumn" align="center">
                    出票渠道
                    <div class="mini-combobox" property="editor" style="width:250px;" popupWidth="400" textField="text"
                         valueField="id"
                         data="Dic.ticketChannelId" multiSelect="true">
                    </div>
                </div>
                <div field="actionTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">操作时间</div>
                <div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss"
                     allowSort="true" showSortIcon="true">创建时间
                </div>
            </div>
        </div>
    </div>
</div>

<div id="editWindow" class="mini-window" title="渠道信息"
     style="width:1000px; height:550px;" showMaxButton="false"
     showCollapseButton="false" showShadow="true" showToolbar="false"
     showFooter="true" showModal="true" allowResize="true" allowDrag="true">

    <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
        <btn:operate privilege="SAVE">
            <a class="mini-button" onclick="channel.save_channel">保存</a>
        </btn:operate>
    </div>

    <div id="editForm">
        <input id="id" name="id" class="mini-hidden"/>
        <fieldset style="border:solid 1px #aaa; padding:3px;">
            <legend>渠道信息:</legend>
            <div style="padding:5px;">
                <table width="100%;">
                    <tr>
                        <td width="13%">渠道ID:</td>
                        <td width="20%"><input id="marketChannelId" required="true" enabled="false"
                                               name="marketChannelId" class="mini-textbox" emptyText="请输入"
                                               valueFromSelect="true" style="width:100%;" enabled="false"/></td>
                        <td width="13%">渠道名称:</td>
                        <td width="20%"> <div id="treeselect" class="mini-treeselect" name="marketChannelId_tree" style="width:100%;"
                                              multiSelect="false"
                                              textField="name" idField="id" parentField="pid"
                                              showRadioButton="true" showClose="true" oncloseclick="Cms.onCloseClick"
                                              popupWidth="200"
                                              onvaluechanged="channel.treegrid_onclick">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div field="name" name="name" width="120">渠道名称</div>
                                <!-- <div field="functions" width="100%">子节点</div> -->
                            </div>
                        </div></td>
                        <td width="13%">出票渠道：</td>
                        <td width="40%">
                            <div id="ticketChannel" name="ticketChannel" class="mini-combobox" style="width:250px;"
                                 popupWidth="400" textField="text" valueField="id"
                                 data="Dic.ticketChannelId" value="cn,usa" multiSelect="true">
                                <div property="columns">
                                    <div header="ID" field="id"></div>
                                    <div header="国家" field="text"></div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>合作开始时间：</td>
                        <td><input id="cooperateBeginTime" name="cooperateBeginTime" enabled="false"
                                   class="mini-datepicker" required="true" format="yyyy-MM-dd HH:mm:ss"
                                   timeFormat="HH:mm:ss" style="width:100%;"/></td>
                        <td>合作结束时间：</td>
                        <td><input id="cooperateEndTime" name="cooperateEndTime" enabled="false" class="mini-datepicker"
                                   required="true" style="width:100%;" format="yyyy-MM-dd HH:mm:ss"
                                   timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
                        </td>
                        <td>渠道状态：</td>
                        <td><input id="isStop" name="isStop" required="true" class="mini-combobox" style="width:100%;"
                                   data="[{id:'0',text:'禁止'},{id:'1',text:'启动'}]"/></td>
                    </tr>
                    <tr>
                        <td>渠道接口密码：</td>
                        <td><input id="key" name="key" class="mini-textbox" maxlength="24" enabled="false"
                                   required="true"
                                   format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" style="width:100%;"/></td>
                        <td>账户余额：</td>
                        <td><input id="balance" name="balance" class="mini-textbox" style="width:150px;"
                                   allowInput="true" valueFromSelect="true" enabled="false" showClose="true"/></td>
                        <td>结算类型：</td>
                        <td><input id="settlementType" name="settlementType" class="mini-combobox" required="true"
                                   style="width:150px;"
                                   data="[{id:'1',text:'余额（可欠款）'},{id:'2',text:'余额（不可欠款）'},{id:'3',text:'彩种库存'}]"/></td>
                    </tr>
                    <tr>
                        <td>商户类型：</td>
                        <td><input id="type" name="type" class="mini-combobox"
                                   data="Dic.type"
                                   required="true"
                                   style="width:100%;"/></td>
                        <td>渠道登录密码：</td>
                        <td><input id="password" name="password" class="mini-textbox" style="width:150px;"
                                   allowInput="true" valueFromSelect="true" enabled="false" showClose="true"/></td>
                        <td></td>
                        <td><a id="resetPassword" name="resetPassword" class="mini-button"
                               onclick="channel.resetPassword">重置密码</a></td>
                    </tr>
                    <tr>
                    	<td>单笔充值限额：</td>
                    	<td><input name="singleRechargeAmount" class="mini-textbox" style="width:100%;"
                                   vtype="float" showClose="true"/></td>
                        <td>充值类型：</td>
                        <td><input name="rechargeType" valueFromSelect="true" showNullItem="true" nullItemText="请选择" emptyText="请选择" class="mini-combobox" style="width:100%;"
                                   data="[{id:'1',text:'现金'},{id:'2',text:'彩金'}]"/></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><a id="addLottery" name="addLottery" class="mini-button"
                               onclick="channel.add_lottery">新增彩种</a>
                            <a id="add_childChannel" name="addLottery" class="mini-button"
                               onclick="channel.add_childChannel">新增代理渠道</a></td>
                    </tr>
                </table>
            </div>
            <div size="100%" showCollapseButton="true" style="border:0;">
                <div id="lotteryDatagrid" class="mini-datagrid" allowAlternating="true" borderStyle="border:solid 0px;"
                     style="width:100%;height:100%;"
                     idField="id" allowResize="true" pageSize="30" multiSelect="true" url="cooperate/lottery/list"
                     showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true"
                     showSummaryRow="true"
                     onrowdblclick="Cms.editRow('lotteryDatagrid')">
                    <div property="columns">
                        <div type="checkcolumn"></div>
                        <div type="indexcolumn" headerAlign="center" align="center">序号</div>
                        <div field="lotteryCode" headerAlign="center" type="comboboxcolumn" property="editor"
                             align="center">彩种名称
                            <input property="editor" class="mini-combobox" data="Dic.allCode"/>
                        </div>
                        <div field="denomination" property="editor" headerAlign="center" align="center">面额
                            <input property="editor" class="mini-textbox"/>
                        </div>
                        <div field="exchangeBalance" property="editor" headerAlign="center" align="center">兑换金额
                            <input property="editor" class="mini-textbox"/>
                        </div>
                        <div field="opt" renderer="channel.onActionRenderer" width="100" headerAlign="center"
                             align="center">操作
                        </div>
                    </div>
                </div>

                <div id="childChannelGrid" class="mini-datagrid" allowAlternating="true" borderStyle="border:solid 0px;"
                     style="width:100%;height:100%;"
                     idField="id" allowResize="true" pageSize="30" multiSelect="true"
                     url="cooperate/channel/list-by-channelId"
                     showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true"
                     showSummaryRow="true"
                     onrowdblclick="Cms.editRow('childChannelGrid')">
                    <div property="columns">
                        <div type="checkcolumn"></div>
                        <div type="indexcolumn" headerAlign="center" align="center">序号</div>
                        <div field="channelId" headerAlign="center" type="comboboxcolumn" property="editor"
                             align="center">渠道名称
                            <input property="editor" class="mini-combobox" url="cooperate/channel/dist"/>
                        </div>
                        <div field="returnRate" property="editor" headerAlign="center" align="center">返点值
                            <input property="editor" class="mini-textbox"/>
                        </div>
                        <div field="opt" renderer="channel.onChannelActionRenderer" width="100" headerAlign="center"
                             align="center">操作
                        </div>
                    </div>
                </div>
            </div>
        </fieldset>
    </div>
</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/cooperate/channel.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>