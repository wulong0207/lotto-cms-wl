function findControllerByType(type){
	var oArr = mini.uids;
	var retArr = new Array();
	for(var obj in oArr){
		if(typeof(oArr[obj]) == "object" && oArr[obj].type == type){
			retArr.push(oArr[obj]);
		}
	}
	return retArr;
}

//日期格式化
var dateFormat = function(time,fmt)
{	
	if(time ==null) return "";
	var newDate = new Date();
	newDate.setTime(time);
	var o = {
			"M+" : newDate.getMonth() + 1, //month
			"d+" : newDate.getDate(), //day
			"h+" : newDate.getHours(), //hour
			"m+" : newDate.getMinutes(), //minute
			"s+" : newDate.getSeconds(), //second
			"q+" : Math.floor((newDate.getMonth() + 3) / 3), //quarter
			"S" : newDate.getMilliseconds()
		};
	var format = "yyyy-MM-dd hh:mm:ss";
	if(typeof(fmt) != "undefined"){
		format = fmt;
	}
		if (/(y+)/.test(format))
			format = format.replace(RegExp.$1, (newDate.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(format))
				format = format.replace(RegExp.$1,
						RegExp.$1.length == 1 ? o[k] : ("00" + o[k])
								.substr(("" + o[k]).length));
		return format;
}

function setAuthButton(auth){
	 var k = findControllerByType("button");
	 for(var i =0 ; i < k.length ; i++){
	 	k[i].disable();
	 }
}