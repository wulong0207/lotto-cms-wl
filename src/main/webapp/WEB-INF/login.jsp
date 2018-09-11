<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>登录</title>
</head>
<style type="text/css">
    body{
        width: 100%;
        height: 100%;
        margin: 0;
        overflow: hidden;
    }
</style>
<body>
<div id="loginForm" style=" height:100%;margin: 0 auto;" >
    <table width="100%" height="100%">
        <tbody>
        <tr>
            <td align="center" valign="middle">
                <fieldset id="fd1" style="width:270px;height:130px;padding-top:10px;">
                    <table>
                        <tr>
                            <td align="right" width="100px;">账号：</td>
                            <td>&nbsp;<input id="userName" name="userName"  class="mini-textbox" required="true" /></td>
                        </tr>
                        <tr>
                            <td align="right" width="100px;">密码：</td>
                            <td align="right"><input id="password" name="password"class="mini-password" required="true" /></td>
                        </tr>
                        <tr>
                            <td align="right" nowrap="nowrap" style="width:60px;"></td>
                            <td colspan="2">
                                <a href="get_back_password" target="_blank">忘记密码</a>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td style="padding-top:5px;"><a onclick="hhly_login._login" class="mini-button" style="width:60px;">登录</a> <a onclick="hhly_login._reset" class="mini-button" style="width:60px;">重置</a></td>
                        </tr>
                    </table>
                </fieldset>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>
<script   language="javascript">    
    if (top != window){
    	top.location.href = window.location.href;
    }
 </script>
<script src="<%=basePath%>resources/js/sysmgr/login.js" type="text/javascript"></script>
