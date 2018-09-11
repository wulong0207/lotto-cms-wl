<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>找回密码</title>
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
                            <td align="right" width="100px;">真实姓名：</td>
                            <td align="right"><input id="userRealName" name="userRealName"class="mini-textbox" required="true" /></td>
                        </tr>
                        <tr>
                            <td align="right" width="100px;">手机号码：</td>
                            <td align="right"><input id="userMobile" name="userMobile"class="mini-textbox" required="true" /></td>
                        </tr>
                        <tr>
                            <td align="right" width="100px;">邮箱：</td>
                            <td align="right"><input id="userEmail" name="userEmail"class="mini-textbox" required="true" /></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td style="padding-top:5px;"><a onclick="" class="mini-button" style="width:60px;">提交</a> <a onclick="reset" class="mini-button" style="width:60px;">重置</a></td>
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
<script type="text/javascript">
    var form = new mini.Form("#loginForm");
    function reset(){
        form.clear();
    }

    function get_back_password(){
        form.validate();
        if (form.isValid() == false){
            mini.alert("请输入文本框内容");
            return;
        }

        var data = form.getData(true);
        $.ajax({
            url : "get_back_password",
            data : data,
            type : "post",
            success : function(res) {
                if(res>0){
                    mini.alert("新密码已经成功发送至您的邮箱请登录邮箱查看");
                }else{
                    mini.alert("输入错误");
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
            }
        });
    }
</script>
