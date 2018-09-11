mini.parse();
var form = new mini.Form("#loginForm");
hhly_login={
    _init:function() {
        this._keyEvent();
    },
    _validationPwd:function(e) {
        if (e.isValid) {
            if (e.value.length < 5) {
                e.errorText = "密码不能少于5个字符";
                e.isValid = false;
            }
        }
    },
    _reset:function() {
        form.clear();
    },
    _login:function() {
        form.validate();
        if (form.isValid() == false){
            mini.alert("请输入用户名和密码");
            return;
        }
        var data = form.getData(true);

        $.ajax({
            url : "login",
            data : data,
            type : "post",
            success : function(res) {
                if(res.errorCode == "1005"){
                    mini.showTips({
                        content: res.message,
                        state: "success",
                        x: "center",
                        y: "center",
                        timeout: 2000
                    });
                    window.location.href="index";
                }else{
                    mini.alert(res.message);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
            }
        });

    },
    _keyEvent:function(){
        if (document.addEventListener) {//如果是Firefox
            document.addEventListener("keypress", fireFoxHandler, true);
        } else {
            document.attachEvent("onkeypress", ieHandler);
        }
        function fireFoxHandler(evt) {
            if (evt.keyCode == 13) {
                hhly_login._login();
            }
        }
        function ieHandler(evt) {
            if (evt.keyCode == 13) {
                hhly_login._login();
            }
        }
    }
};
hhly_login._init();
