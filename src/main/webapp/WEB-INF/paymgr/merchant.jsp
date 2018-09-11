<%@page import="java.util.Date" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/top.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>商户管理</title>


</head>
<body>
<div class="mini-toolbar" style="border-top: 0; border-left: 0; padding: 0px;">
    <div id="form">
        <table style="width: 100%;">
            <tr>
                <td>
                    <btn:operate privilege="ADD">
                        <a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="merchant._add">新增</a>
                    </btn:operate>
                </td>
            </tr>
            <tr>
                <td>
                    商户号：
                    <input id="codeSearch" name="codeSearch" class="mini-textbox" allowInput="true" showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    商户名称：
                    <input id="companySearch" name="companySearch" class="mini-textbox" allowInput="true"
                           showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    启用状态：
                    <input id="statusSearch" name="statusSearch" class="mini-combobox" allowInput="true"
                           showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           data="Dic.status"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    <button onclick="merchant.search()" style="width:60px;">查询</button>
                </td>
            </tr>
        </table>
    </div>
</div>

<div id="grid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
     url="paymgr/merchant/list" pageSize="30" showColumnsMenu="true" showPagerButtonText="true"
     onrowdblclick="merchant._edit"
     showPagerButtonIcon="true" showFilterRow="false" allowMoveColumn="true" showSummaryRow="true">

    <div property="columns">
        <div type="indexcolumn" headerAlign="center" align="center">序号</div>
        <div field="code" headerAlign="center" align="center">商户编号</div>
        <div field="company" headerAlign="center" align="center">商户名称</div>
        <div field="passwd" headerAlign="center" align="center">支付秘钥</div>
        <div field="name" headerAlign="center" align="center">联系人姓名</div>
        <div field="phone" headerAlign="center" align="center">联系电话</div>
        <div field="address" headerAlign="center" align="center">商户地址</div>
        <div field="status" type="comboboxcolumn" headerAlign="center" align="center">启用状态
            <input property="editor" class="mini-combobox" data="Dic.status"/>
        </div>
        <div field="returnUrl" headerAlign="center" align="center">回调地址</div>
        <div field="remark" headerAlign="center" align="center">备注</div>
        <div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" align="center">创建时间</div>
        <div field="modifyTime" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" align="center">修改时间</div>
        <div field="modifyBy" headerAlign="center" align="center">修改人</div>
        <div field="action" headerAlign="center" align="center">操作</div>
    </div>
</div>

<div id="editWindow" class="mini-window" title="商户信息管理"
     style="width:1100px; height:500px;" showMaxButton="false"
     showCollapseButton="false" showShadow="true" showToolbar="false"
     showFooter="true" showModal="true" allowResize="true" allowDrag="true">

    <div id="editform">
        <input id="id" name="id" class="mini-hidden"/>
        <input id="url" name="url" class="mini-hidden"/>
        <table width="100%" border="0" cellpadding="2" cellspacing="2">
            <tr>
                <td width="50%" style="text-align: right;">
                    <a class="mini-button" onclick="merchant.submit" iconCls="icon-save">保存</a>
                </td>
            </tr>
        </table>
        <table width="100%" border="0" cellpadding="2" cellspacing="2">
            <tr>
                <td width="60%" style="text-align: center;">
                    <table>
                        <tr>
                            <td>商户号:</td>
                            <td><input id="code" name="code" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/></td>
                            <td>商户名称：</td>
                            <td><input id="company" name="company" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       oncloseclick="Cms.onCloseClick"
                                       allowInput="false" style="width: 200px;"
                                       showClearButton="true" showClose="true"/></td>
                            <td>支付秘钥：</td>
                            <td><input id="passwd" name="passwd" class="mini-textbox" allowInput="true" readonly="true"
                                       showClose="true"
                                       oncloseclick="Cms.onCloseClick"
                                       allowInput="false" style="width: 200px;"
                                       showClearButton="true" showClose="true"/></td>
                        </tr>
                        <tr>
                            <td>联系人姓名:</td>
                            <td><input id="name" name="name" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       oncloseclick="Cms.onCloseClick"
                                       allowInput="false" style="width: 200px;"
                                       showClearButton="true" showClose="true"/></td>
                            <td>联系电话：</td>
                            <td><input id="phone" name="phone" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       oncloseclick="Cms.onCloseClick"
                                       allowInput="false" style="width: 200px;"
                                       showClearButton="true" showClose="true"/></td>
                            <td>回调地址：</td>
                            <td><input id="returnUrl" name="returnUrl" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       oncloseclick="Cms.onCloseClick"
                                       allowInput="false" style="width: 200px;"
                                       showClearButton="true" showClose="true"/></td>
                        </tr>
                        <tr>
                            <td>商户地址:</td>
                            <td><input id="address" name="address" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/></td>
                            <td>启用状态：</td>
                            <td><input id="status" name="status" class="mini-combobox" allowInput="true"
                                       showClose="true"
                                       oncloseclick="Cms.onCloseClick" data="Dic.status"
                                       allowInput="false" style="width: 200px;"
                                       showClearButton="true" showClose="true"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <div>
                    <div>
                        <table width="100%" class="table-bor" style="margin-top: 20px;">
                            <tr>
                                <td width="15%" style="text-align: center">
                                    创建时间
                                </td>
                                <td width="15%" style="text-align: center">
                                    <input name="createTime" class="mini-datepicker" allowInput="false"
                                           readonly="readonly" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td width="15%" style="text-align: center">
                                    修改时间
                                </td>
                                <td width="15%" style="text-align: center">
                                    <input id="modifyTime" name="modifyTime" class="mini-datepicker" allowInput="false"
                                           readonly="readonly" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td width="15%" style="text-align: center" rowspan="2">
                                    备注
                                </td>
                                <td width="25%" style="text-align: center" rowspan="2">
                                    <textarea id="remark" name="remark" class="mini-textarea" emptyText="请输入备注"
                                              width="100%" height="100%"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td width="15%" style="text-align: center">
                                    创建人
                                </td>
                                <td width="15%" style="text-align: center">
                                    <input id="createBy" name="createBy" class="mini-textbox asLabel" readOnly="true"/>
                                </td>
                                <td width="15%" style="text-align: center">
                                    修改人
                                </td>
                                <td width="15%" style="text-align: center">
                                    <input id="modifyBy" name="modifyBy" class="mini-textbox asLabel" readOnly="true"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </tr>
        </table>
    </div>
</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/paymgr/merchant.js?version=<%=version%><%=System.currentTimeMillis() %>"
        type="text/javascript"></script>
</html>
