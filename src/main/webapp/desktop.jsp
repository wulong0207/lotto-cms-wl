<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.mini-panel-border
{    
    border:0px solid #99bce8;     
}
</style>
<script src="resources/js/public/boot.js" type="text/javascript"></script>
</head>

<body>
<div class="mini-panel"  bodyStyle="padding:0px;border:0;" style="width:100%;height:830px;background-image:url(resources/images/public/mainback.jpg); "
    showToolbar="false"   showCloseButton="false" showHeader="false" >
    <div id="form" style=" height:100%">
        <table width="100%" height="100%">
            <tbody>
            <tr>
                <td align="center" valign="middle">
                    <fieldset id="fd1" style="width:500px;height:200px;">
                        <table style="margin-top: 20px;">
                            <tr>
                                <td>
                                   	 欢迎您！${sessionScope.loginName}
                                </td>
                                <td>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                   	 本月登陆次数:
                                </td>
                                <td>
                                	${sessionScope.loginUserInfo.monthLoginCount}次
                                </td>
                            </tr>
                            <tr>
                                <td>
                                   	本次登陆IP：
                                </td>
                                <td>
                                    ${sessionScope.loginUserInfo.thisLoginIP}
                                </td>
                                <td>
                                   	本次登陆时间：
                                </td>
                                <td>
                                    ${sessionScope.loginUserInfo.thisLoginTime}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                   	上次登陆IP：
                                </td>
                                <td>
                                    ${sessionScope.loginUserInfo.lastLoginIp}
                                </td>
                                <td>
                                   	上次登陆时间：
                                </td>
                                <td>
                                    ${sessionScope.loginUserInfo.lastLoginTime}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4">
                                	提示：为了帐号的安全，如果上面登陆情况不正常，建议你马上  <a href="sysmgr/user/pwd">修改密码</a>
                                </td>
                            </tr>
                            
                        </table>
                    </fieldset>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
<style type="text/css">
.mini-panel-border
{    
    border:0px solid #99bce8;     
}
</style>