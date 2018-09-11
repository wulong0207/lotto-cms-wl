<%@page import="java.util.Date" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/top.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>交易记录</title>


</head>
<body>
<div class="mini-toolbar" style="border-top: 0; border-left: 0; padding: 0px;">
    <div id="form">
        <table style="width: 100%;">
            <%--<tr>--%>
            <%--<td>--%>
            <%--<btn:operate privilege="EXPORT">--%>
            <%--&nbsp;&nbsp;<button iconCls="icon-download" plain="true" onclick="rcmd.excel()">导出</button>--%>
            <%--</btn:operate>--%>
            <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td>收款账号：<input id="accountId" name="accountId" class="mini-textbox" allowInput="true" showClose="true"
                                oncloseclick="Cms.onCloseClick"
                                allowInput="false" style="width: 200px;"
                                showClearButton="true" showClose="true"/>
                    商户号：
                    <input id="companyCode" name="companyCode" class="mini-textbox" allowInput="true" showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    商户名称：
                    <input id="companyName" name="companyName" class="mini-textbox" allowInput="true" showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                </td>
            </tr>
            <tr>
                <td>
                    交易流水编号：
                    <input id="transCode" name="transCode" class="mini-textbox" allowInput="true" showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    商户订单编号:：
                    <input id="orderCode" name="orderCode" class="mini-textbox" allowInput="true" showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                </td>
            </tr>
            <td>
                第三方交易会员姓名:
                <input id="thirdName" name="thirdName" class="mini-textbox" allowInput="true" showClose="true"
                       allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                第三方交易流水号:
                <input id="thirdCode" name="thirdCode" class="mini-textbox" allowInput="true" showClose="true"
                       allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                第三方交易流水状态:
                <input id="thirdStatus" name="thirdStatus" class="mini-combobox" allowInput="true" showClose="true"
                       data="Dic.thirdStatus"
                       allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                第三方交易支付金额:
                <input id="thirdAmount" name="thirdAmount" class="mini-textbox" allowInput="true" showClose="true"
                       allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
            </td>
            <tr>
            </tr>
            <td>
                第三方交易类型:
                <input id="thirdType" name="thirdType" class="mini-combobox" allowInput="true" showClose="true"
                       data="Dic.thirdType"
                       allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                回调支付状态:
                <input id="returnStatus" name="returnStatus" class="mini-combobox" allowInput="true"
                       showClose="true" data="Dic.returnStatus"
                       allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                交易开始时间：<input id="startTime" name="startTime" class="mini-datepicker"
                              oncloseclick="Cms.onCloseClick"
                              allowInput="false" style="width: 200px;" format="yyyy-MM-dd"
                              showClearButton="true" showClose="true"/>
                交易结束时间:
                <input id="endTime" name="endTime" class="mini-datepicker" allowInput="false"
                       style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                <button onclick="trans_log.search()" style="width:60px;">查询</button>
            </td>
            </tr>
        </table>
    </div>
</div>

<div id="transLogGrid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
     url="paymgr/trans-log/list" pageSize="30" showColumnsMenu="true" showPagerButtonText="true"
     showPagerButtonIcon="true" showFilterRow="false" allowMoveColumn="true" showSummaryRow="true">

    <div property="columns">
        <div type="indexcolumn" headerAlign="center" align="center">序号</div>
        <div field="getAccount" headerAlign="center" align="center">收款账号</div>
        <div field="companyCode" headerAlign="center" align="center">商户号</div>
        <div field="company" headerAlign="center" align="center">商户名称</div>
        <div field="transCode" headerAlign="center" align="center">交易流水编号</div>
        <div field="orderCode" headerAlign="center" align="center">商户订单编号</div>
        <div field="thirdName" headerAlign="center" align="center">第三方交易会员姓名</div>
        <div field="thirdStatus" headerAlign="center" align="center">第三方交易流水号</div>
        <div field="thirdCode" type="comboboxcolumn" headerAlign="center" align="center">第三方交易流水状态
            <input property="editor" class="mini-combobox" data="Dic.thirdStatus"/>
        </div>
        <div field="thirdAmount" headerAlign="center" align="center">第三方交易支付金额</div>
        <div field="thirdType" type="comboboxcolumn" headerAlign="center" align="center">第三方交易类型
            <input property="editor" class="mini-combobox" data="Dic.thirdType"/>
        </div>
        <div field="returnStatus" type="comboboxcolumn" headerAlign="center" align="center">回调支付状态
            <input property="editor" class="mini-combobox" data="Dic.returnStatus"/>
        </div>
        <div field="returnTimes" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" align="center">回调次数</div>
        <div field="pushNextTime" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" align="center">预计下次推送时间</div>
        <div field="remark" headerAlign="center" align="center">备注</div>
        <div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" align="center">创建时间</div>
        <div field="modifyTime" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" align="center">修改时间</div>
        <div field="modifyBy" headerAlign="center" align="center">修改人</div>
        <div field="action" headerAlign="center" align="center">操作</div>
    </div>
</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/paymgr/trans_log.js?version=<%=version%><%=System.currentTimeMillis() %>"
        type="text/javascript"></script>
</html>
