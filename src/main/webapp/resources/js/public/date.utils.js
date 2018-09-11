/**
 * 日期操作类工具
 */
var dateUtils = {
	/**
	 * @param longStr 'yyyy-MM-dd HH:mm:ss'格式的字符串
	 * @returns 'yyyy-MM-dd HH:mm:ss'格式的字符串转日期对象
	 */
	longStrToDate : function(longStr) {
		var tempStrs = longStr.split(" ");
		var dateStrs = tempStrs[0].split("-");
		var year = parseInt(dateStrs[0], 10);
		var month = parseInt(dateStrs[1], 10) - 1;
		var day = parseInt(dateStrs[2], 10);
		var timeStrs = tempStrs[1].split(":");
		var hour = parseInt(timeStrs[0], 10);
		var minute = parseInt(timeStrs[1], 10);
		var second = parseInt(timeStrs[2], 10);
		var date = new Date(year, month, day, hour, minute, second);
		return date;
	},
	/**
	 * @param srcDate 源日期对象
	 * @param targetDate 目标日期对象
	 * @returns 比较两个时间的大小：srcDate 等于targetDate，则返回值 0；如果srcDate在targetDate之前，则返回小于 0 的值；如果srcDate在targetDate之后，则返回大于 0 的值
	 */
	compareTo : function(srcDate, targetDate) {
		var thisTime = srcDate.getTime();
		var anotherTime = targetDate.getTime();
		return (thisTime < anotherTime ? -1 : (thisTime == anotherTime ? 0 : 1));
	},
	formatDate : function(date) {
		if(!date) return "";
		var day = date.getDate();
		var month = date.getMonth()+1;
		var year = date.getFullYear();
		return year + '-' + month + '-' + day + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
	}
}