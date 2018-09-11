var report_common={
	getParam : function() {
		var param = {};
		// 注册渠道
		var tmp;
		tmp = $('#statis-channel a.active').data('channel');
		param.channel = (tmp || tmp == 0) ? tmp : mini.get('channel').getValue();
		// 投注时间
		param.timeType = $('#statis-time a.active').data('time');
		// 投注平台
		param.platform = $('#statis-platform a.active').data('platform');
		param.startTime = mini.get('startTime').getFormValue()==""?mini.get('startTime').getFormValue():mini.get('startTime').getFormValue()+" 00:00:00";
		param.endTime = mini.get('endTime').getFormValue()==""?mini.get('endTime').getFormValue():mini.get('endTime').getFormValue()+" 23:59:59";		
		// 彩种类型
		if(mini.get('lotteryType'))
			param.lotteryType = mini.get('lotteryType').getValue();
		if(mini.get('lotteryCode')) {
			tmp = $('#statis-lotteryCode a.active').data('lotterycode');
			param.lotteryCode = (tmp || tmp == 0) ? tmp : mini.get('lotteryCode').getValue();
		}
		return param;
	}
};