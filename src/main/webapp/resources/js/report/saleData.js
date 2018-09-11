$(function(){
	saleData.init();
});  

saleData={
    init:function(){
        mini.parse();
        saleData.saleDatagrid = mini.get("saleDatagrid");
        
        // 查询
        saleData.saleDatagrid.load();
    },
	excel:function() {
		var url = "userdata/saleReportExcel?startTime=";
			var param = saleData.getSearchParam();
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
		var param = saleData.getSearchParam();
		saleData.saleDatagrid.load(param);
	}
};