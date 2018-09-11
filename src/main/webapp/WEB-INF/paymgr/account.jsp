<%@page import="java.util.Date" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/top.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>账户管理</title>


</head>
<body>
<div class="mini-toolbar" style="border-top: 0; border-left: 0; padding: 0px;">
    <div id="form">
        <table style="width: 100%;">
            <tr>
                <td>
                    <btn:operate privilege="ADD">
                        <a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="account._add">新增</a>
                    </btn:operate>
                </td>
            </tr>
            <tr>
                <td>收款账号：<input id="getAccountSearch" name="getAccount" class="mini-textbox" allowInput="true"
                                showClose="true"
                                oncloseclick="Cms.onCloseClick"
                                allowInput="false" style="width: 200px;"
                                showClearButton="true" showClose="true"/>
                    商户号：
                    <input id="companyCodeSearch" name="companyCode" class="mini-textbox" allowInput="true"
                           showClose="true"
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
                    启用状态：
                    <input id="statusSearch" name="status" data="Dic.status" class="mini-combobox" allowInput="true"
                           showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    手机号码:：
                    <input id="phoneSearch" name="phone" class="mini-textbox" allowInput="true" showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    收款人姓名:
                    <input id="getNameSearch" name="getName" class="mini-textbox" allowInput="true" showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                </td>
            </tr>
            <tr>
                <td>
                    支付类型:
                    <input id="typeSearch" name="type" class="mini-combobox" allowInput="true" data="Dic.type"
                           showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    提款银行卡号:
                    <input id="cardCodeSearch" name="cardCode" class="mini-textbox" allowInput="true" showClose="true"
                           allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                           oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/>
                    <button onclick="account.search()" style="width:60px;">查询</button>
                </td>
            </tr>
        </table>
    </div>
</div>

<div id="grid" class="mini-datagrid" allowAlternating="true" style="width: 100%; height: 100%;"
     url="paymgr/account/list" pageSize="30" showColumnsMenu="true" showPagerButtonText="true"
     onrowdblclick="account._edit"
     showPagerButtonIcon="true" showFilterRow="false" allowMoveColumn="true" showSummaryRow="true">

    <div property="columns">
        <div type="checkcolumn" width="3%"></div>
        <div type="indexcolumn" headerAlign="center" align="center">序号</div>
        <div field="companyCode" headerAlign="center" align="center">商户号</div>
        <div field="getAccount" headerAlign="center" align="center">收款账号</div>
        <div field="getPasswd" headerAlign="center" align="center">收款密码</div>
        <div field="phone" headerAlign="center" align="center">手机号码</div>
        <div field="getName" headerAlign="center" align="center">收款人姓名</div>
        <div field="timesLimit" headerAlign="center" align="center">当天交易次数限制</div>
        <div field="totalLimit" headerAlign="center" align="center">当天交易总金额限制</div>
        <div field="singleLimit" headerAlign="center" align="center">单笔金额限制</div>
        <div field="status" type="comboboxcolumn" headerAlign="center" align="center">启用状态
            <input property="editor" class="mini-combobox" data="Dic.status"/>
        </div>
        <div field="payPasswd" headerAlign="center" align="center">支付密码</div>
        <div field="type" type="comboboxcolumn" headerAlign="center" align="center">交易类型
            <input property="editor" class="mini-combobox" data="Dic.type"/>
        </div>
        <div field="cardCode" headerAlign="center" align="center">提款银行卡号
        </div>
        <div field="bankName" headerAlign="center" align="center">提款银行名称</div>
        <div field="remark" headerAlign="center" align="center">备注</div>
        <div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" align="center">创建时间</div>
        <div field="modifyTime" dateFormat="yyyy-MM-dd HH:mm:ss" headerAlign="center" align="center">修改时间</div>
        <div field="modifyBy" headerAlign="center" align="center">修改人</div>
        <%--<div field="action" headerAlign="center" align="center">操作</div>--%>
    </div>
</div>

<div id="editWindow" class="mini-window" title="账号信息"
     style="width:1100px; height:500px;" showMaxButton="false"
     showCollapseButton="false" showShadow="true" showToolbar="false"
     showFooter="true" showModal="true" allowResize="true" allowDrag="true">

    <div id="editform">
        <input id="id" name="id" class="mini-hidden"/>
        <input id="url" name="url" class="mini-hidden"/>
        <table width="100%" border="0" cellpadding="2" cellspacing="2">
            <tr>
                <td width="50%" style="text-align: right;">
                    <a class="mini-button" onclick="account.submit" iconCls="icon-save">保存</a>
                </td>
            </tr>
        </table>
        <table width="100%" border="0" cellpadding="2" cellspacing="2">
            <tr>
                <td width="60%" style="text-align: center;">
                    <table>
                        <tr>
                            <td>商户号:</td>
                            <td><input id="companyCode" name="companyCode" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/></td>
                            <td>收款账号：</td>
                            <td><input id="getAccount" name="getAccount" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       oncloseclick="Cms.onCloseClick"
                                       allowInput="false" style="width: 200px;"
                                       showClearButton="true" showClose="true"/></td>
                            <td>收款密码：</td>
                            <td><input id="getPasswd" name="getPasswd" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       oncloseclick="Cms.onCloseClick"
                                       allowInput="false" style="width: 200px;"
                                       showClearButton="true" showClose="true"/></td>
                        </tr>
                        <tr>
                            <td>手机号:</td>
                            <td><input id="phone" name="phone" class="mini-textbox" allowInput="true" showClose="true"
                                       allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/></td>
                            <td>收款人姓名:</td>
                            <td><input id="getName" name="getName" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       oncloseclick="Cms.onCloseClick"
                                       allowInput="false" style="width: 200px;"
                                       showClearButton="true" showClose="true"/></td>
                            <td>当天交易次数限制：</td>
                            <td><input id="timesLimit" name="timesLimit" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       oncloseclick="Cms.onCloseClick"
                                       allowInput="false" style="width: 200px;"
                                       showClearButton="true" showClose="true"/></td>
                        </tr>
                        <tr>
                            <td>当天交易总金额限制:</td>
                            <td><input id="totalLimit" name="totalLimit" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/></td>
                            <td>单笔金额限制：</td>
                            <td><input id="singleLimit" name="singleLimit" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       oncloseclick="Cms.onCloseClick"
                                       allowInput="false" style="width: 200px;"
                                       showClearButton="true" showClose="true"/></td>
                            <td>启用状态：</td>
                            <td><input id="status" name="status" class="mini-combobox" allowInput="true"
                                       showClose="true"
                                       oncloseclick="Cms.onCloseClick" data="Dic.status"
                                       allowInput="false" style="width: 200px;"
                                       showClearButton="true" showClose="true"/></td>
                        </tr>
                        <tr>
                            <td>支付密码:</td>
                            <td><input id="payPasswd" name="payPasswd" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/></td>
                            <td>交易类型：</td>
                            <td><input id="type" name="type" class="mini-combobox" allowInput="true" showClose="true"
                                       oncloseclick="Cms.onCloseClick" data="Dic.type"
                                       allowInput="false" style="width: 200px;"
                                       showClearButton="true" showClose="true"/></td>
                            <td>提款银行卡号：</td>
                            <td><input id="cardCode" name="cardCode" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       oncloseclick="Cms.onCloseClick"
                                       allowInput="false" style="width: 200px;"
                                       showClearButton="true" showClose="true"/></td>
                        </tr>
                        <tr>
                            <td>提款银行名称:</td>
                            <td><input id="bankName" name="bankName" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/></td>
                        </tr>

                        <tr>
                            <td>登录密码:</td>
                            <td><input id="loginPwd" name="loginPwd" class="mini-textbox" allowInput="true"
                                       showClose="true"
                                       allowInput="false" style="width: 200px;margin-right:15px" format="yyyy-MM-dd"
                                       oncloseclick="Cms.onCloseClick" showClearButton="true" showClose="true"/></td>
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
<script src="<%=basePath%>resources/js/paymgr/account.js?version=<%=version%><%=System.currentTimeMillis() %>"
        type="text/javascript"></script>
</html>
