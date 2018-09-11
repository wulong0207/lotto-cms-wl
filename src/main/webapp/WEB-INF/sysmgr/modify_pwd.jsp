<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>修改密码</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
  </head>
  
  <body>
  <div id="form" style=" height:100%">
      <table width="100%" height="100%">
          <tbody>
          <tr>
              <td align="center" valign="middle">
                  <fieldset id="fd1" style="width:270px;height:130px;padding-top:10px;">
                      <table>
                          <tr>
                              <td align="right" width="100px;">旧密码：</td>
                              <td><input id="oldPwd" name="oldPwd" class="mini-password"
                                         required="true" /></td>
                          </tr>
                          <tr>
                              <td align="right">新密码：</td>
                              <td align="right"><input id="pwd" name="pwd"
                                                       class="mini-password" required="true" /></td>
                          </tr>
                          <tr>
                              <td>确认新密码：</td>
                              <td><input id="newpwd" name="newpwd" class="mini-password"
                                         required="true" /></td>
                          </tr>
                          <tr>
                              <td height="44" colspan="2" align="center">
                                  <a class="mini-button" iconCls="icon-ok" onclick="onSubmit">提交</a>
                              </td>
                          </tr>

                          <input name="action" class="mini-hidden" value="post"/>
                      </table>
                  </fieldset>
              </td>
          </tr>
          </tbody>
      </table>
  </div>
  </body>
<script type="text/javascript">
    mini.parse();
    function onSubmit(){
        var form = new mini.Form("#form");

        var data = form.getData();

        if(data.pwd.length<6||data.pwd.length>10){
            mini.alert("密码长度必须大于6小于10!");
        }

        if(data.pwd != data.newpwd){
            mini.alert("两次输入密码不一致!");
            return ;
        }

        Cms.submit(form,function(){
            mini.showTips({
                content: "新密码修改成功",
                state: "success",
                x: "center",
                y: "center",
                timeout: 2000
            });
        });
        form.clear();
    }
</script>
</html>
