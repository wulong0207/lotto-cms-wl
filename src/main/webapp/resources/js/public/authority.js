$(document).ajaxComplete(function (evt, request, settings) {
    var text = request.responseText;
    var i = text.indexOf("<title>用户登录");
    if (i > -1) {
    	window.location = '../login';
    }
});

var winAlerts = window.alert;
window.alert = function (e) {
	if (e != null && e.indexOf("试用到期")>-1 ){
		//和谐了
	}else{
		winAlerts (e);
	}
}
