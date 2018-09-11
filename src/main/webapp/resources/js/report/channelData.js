$(function(){
	channelData.init();
});  

channelData={
    init:function(){
        mini.parse();
        channelData.channelDatagrid = mini.get("channelDatagrid");
        // 查询
        channelData.channelDatagrid.load();
    },
	excel:function() {
		var url = "userdata/channelReportExcel?startTime=";
			var param = channelData.getSearchParam();
			if(param.startTime){
				requestParamStr = param.startTime;
			}
			if(param.endTime){
				requestParamStr +="&endTime="+param.endTime;
			}
			url = url + requestParamStr;
			location = url;
	},
	getSearchParam:function(){
		var param = {};
		// 注册渠道
		// 时间段
		var startTime = mini.get('startTime').getFormValue();
		var endTime = mini.get('endTime').getFormValue();
		var time = " 00:00:00";
		if(startTime)
		{
			startTime = startTime + time;
		}
		if(endTime){
			endTime = endTime + time;
		}
		param.startTime = startTime;
		param.endTime =  endTime;
		return param;
	},
	search:function(){
		var param = channelData.getSearchParam();
		channelData.channelDatagrid.load(param);
	}
};