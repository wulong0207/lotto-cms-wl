<%@page import="java.util.Date" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/top.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>请求记录</title>


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
                <td>
                    商户号：
                    <input id="companyCode" name="companyCode" class="mini-textbox" allowInput="true" showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    交易编号：
                    <input id="transCode" name="transCode" class="mini-textbox" allowInput="true" showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    商户订单编号:：
                    <input id="orderCode" name="orderCode" class="mini-textbox" allowInput="true" showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    订单金额:
                    <input id="orderAmount" name="orderAmount" class="mini-textbox" allowInput="true" showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                </td>
            </tr>
            <tr>
                <td>
                    交易类别:
                    <input id="payType" name="payType" class="mini-combobox" allowInput="true" showClose="true"
                           data="Dic.payType"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    交易平台:
                    <input id="platform" name="platform" class="mini-combobox" allowInput="true" showClose="true"
                           data="Dic.platform"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    签名串:
                    <input id="paySign" name="paySign" class="mini-textbox" allowInput="true" showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    请求开始时间：<input id="startTime" name="startTime" class="mini-datepicker"
                                  oncloseclick="Cms.onCloseClick"
                                  allowInput="false" style="width: 200px;" format="yyyy-MM-dd"
                                  showClearButton="true" showClose="true"/>
                    请求结束时间:
                    <input id="endTime" name="endTime" class="mini-datepicker" allowInput="false"
                           style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    <button onclick="request_log.search()" style="width:60px;">查询</button>
                </td>
            </tr>
        </table>
    </div>
</div>

<div id="grid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
     url="paymgr/request-log/list" pageSize="30" showColumnsMenu="true" showPagerButtonText="true"
     showPagerButtonIcon="true" showFilterRow="false" allowMoveColumn="true" showSummaryRow="true">

    <div property="columns">
        <div type="indexcolumn" headerAlign="center" align="center">序号</div>
        <div field="companyCode" headerAlign="center" align="center">商户号</div>
        <div field="transCode" headerAlign="center" align="center">交易流水编号</div>
        <div field="orderCode" headerAlign="center" align="center">商户订单编号</div>
        <div field="orderAmount" headerAlign="center" align="center">订单金额</div>
        <div field="payType" type="comboboxcolumn" headerAlign="center" align="center">支付类型
            <input property="editor" class="mini-combobox" data="Dic.payType"/>
        </div>
        <div field="platform" type="comboboxcolumn" headerAlign="center" align="center">支付平台
            <input property="editor" class="mini-combobox" data="Dic.platform"/>
        </div>
        <div field="syncUrl" headerAlign="center" align="center">同步地址</div>
        <div field="asyncUrl" headerAlign="center" align="center">异步地址</div>
        <div field="paySign" headerAlign="center" align="center">签名串</div>
        <div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" align="center">请求时间</div>
    </div>
</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/paymgr/request_log.js?version=<%=version%><%=System.currentTimeMillis() %>"
        type="text/javascript"></script>
</html>
