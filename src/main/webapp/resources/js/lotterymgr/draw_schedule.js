var Current = {
	"init" : function() {
		mini.parse();
		Current.totalSize = 0;
		Current.lotteryCode = Cms.getCookie('lotteryCode');
		Current.lotteryIssue = Cms.getCookie('lotteryIssue');
		Current.handleType =  Cms.getCookie('handleType');
		Current.count = 0;
		Current.getSchedule();
		Current.schedule = window.setInterval("Current.getSchedule()",3000);
	},
	getSchedule :function(){
		var param = {};
		param.lotteryCode = Current.lotteryCode;
		param.lotteryIssue = Current.lotteryIssue; 
		param.handleType = Current.handleType;
		param.jobId = "000023";
		var op = {
				data : param
	    };
	    Task.runTask(op,function(result){
	    	var data = eval('(' + result + ')'); 
	    	var load  = parseInt(data.success) + parseInt(data.fail);
	    	var total = parseInt(data.total)
	    	$('progress').attr({
				value : load,
				max : total
			}); // 更新数据到进度条
			var percent = load / total * 100;
			if(!percent && percent !=0){
				Current.count = Current.count + 1;
				percent = 0;
			}
			$('#progress').html(percent.toFixed(2) + "%,"+Current.getMinute(parseInt(data.awardSecond)));
			$('#drawCode').html(data.drawCode);
			$('#planSecond').html(Current.getMinute(parseInt(data.planSecond)));
			$('#success').html(data.success+"个");
			$('#winCount').html(data.winCount+"个");
			$('#fail').html(data.fail+"个");
			$('#order').html(data.total+"个");
			var str ="";
			 $.each(data.failMessage, function (n, value) {
				 str = str + value + "\n";
	         });
			$('#message').val(str);
			if(percent == 100 || Current.count == 3){
				window.clearInterval(Current.schedule);
				if(Current.handleType == '1'){
					if(str.length > 0 ){
						mini.alert("开奖失败！");
					}else{
						mini.alert("开奖成功！");
					}
				}else if(Current.handleType == '2'){
					mini.alert("派奖完成！");
				}
			}
	    });
	}
	,getMinute:function(second){
		return parseInt(second/60)+"分" + second%60+"秒"
	}
}
Current.init();