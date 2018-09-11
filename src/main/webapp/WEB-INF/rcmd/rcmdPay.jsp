<%@page import="java.util.Date" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/top.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>推单数据统计</title>


</head>
<body>
<div class="mini-toolbar" style="border-top: 0; border-left: 0; padding: 0px;">
    <div id="form">
        <table style="width: 100%;">
            <tr>
                <td>
                    <btn:operate privilege="EXPORT">
                        &nbsp;&nbsp;<button iconCls="icon-download" plain="true" onclick="rcmd.excel()">导出</button>
                    </btn:operate>
                </td>
            </tr>
            <tr>
                <td>统计周期：<input id="type" name="type" class="mini-combobox" allowInput="true" showClose="true"
                                oncloseclick="Cms.onCloseClick"
                                allowInput="false" style="width: 200px;"
                                showClearButton="true" showClose="true"/>
                    玩法
                    <input id="passWay" name="passWay" class="mini-combobox" allowInput="true" showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>

                    发布时间：<input id="startTime" name="startTime" class="mini-datepicker" oncloseclick="Cms.onCloseClick"
                                allowInput="false" style="width: 200px;" format="yyyy-MM-dd"
                                showClearButton="true" showClose="true"/>
                    到
                    <input id="endTime" name="endTime" class="mini-datepicker" allowInput="false"
                           style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    <button onclick="rcmd.search()" style="width:60px;">查询</button>
                </td>
            </tr>
        </table>
    </div>
</div>

<div id="rcmdGrid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
     url="rcmd/list" pageSize="30" showColumnsMenu="true" showPagerButtonText="true"
     showPagerButtonIcon="true" showFilterRow="false" allowMoveColumn="true" showSummaryRow="true">

    <div property="columns">
        <div type="indexcolumn" headerAlign="center" align="center">序号</div>
        <div field="dt" headerAlign="center" align="center">日期</div>
        <div field="times" headerAlign="center" align="center">总发单数</div>
        <div field="payTimes" headerAlign="center" align="center">付费推单</div>
        <div field="freeTimes" headerAlign="center" align="center">免费推单</div>
        <div field="counts" headerAlign="center" align="center">总发单人</div>
        <div field="payCounts" headerAlign="center" align="center">收费发单人</div>
        <div field="freeCounts" headerAlign="center" align="center">无偿发单人</div>
        <div field="pay18Times" headerAlign="center" align="center">18推单数</div>
        <div field="buy18Counts" headerAlign="center" align="center">18购买数</div>
        <div field="pay28Times" headerAlign="center" align="center">28推单数</div>
        <div field="buy28Counts" headerAlign="center" align="center">28购买数</div>
        <div field="pay58Times" headerAlign="center" align="center">58推单数</div>
        <div field="buy58Counts" headerAlign="center" align="center">58购买数</div>
        <div field="pay198Times" headerAlign="center" align="center">198推单数</div>
        <div field="buy198Counts" headerAlign="center" align="center">198购买数</div>
        <div field="buyCounts" headerAlign="center" align="center">总购买人数</div>
        <div field="action" headerAlign="center" align="center">操作</div>
    </div>
</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/rcmd/rcmdPay.js?version=<%=version%><%=System.currentTimeMillis() %>"
        type="text/javascript"></script>
</html>
