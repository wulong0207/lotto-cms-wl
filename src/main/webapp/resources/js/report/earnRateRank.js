$(function(){
	earnRateRank.init();
});  


earnRateRank={
	timeType:null,
    init:function(){
        mini.parse();
    	$.ajax({
	    	url : "report/earnRate/lotteryTypes",
	    }).done(function(data) {
	    	mini.get('lotteryCode').setData(data);
	    });
        // 点击查询条件时，改变样式
    	$("#statis-time a, #statis-channel a, #statis-code a").click(function(){
    		$(this).siblings().removeClass("active");
			$(this).toggleClass("active");
		});
    	// 点击选项时，把渠道下拉框置空
    	$("#statis-channel a").click(function(){
    		mini.get('channel').setValue(null);
		});
    	// 点击选项时，把注册渠道下拉框置空
    	$("#statis-code a").click(function(){
    		mini.get('lotteryCode').setValue(null);
    	});
    	// 点击选项时，把时间空
    	$("#statis-time a").click(function(){
    		mini.get('startTime').setValue(null);
    		mini.get('endTime').setValue(null);
    	});
    	
    	earnRateRank.search();
    	
    	$('#exportBtn').click(function(e) {
    		var excelUrl = $(e.srcElement).data('excel-url');
    		var param = earnRateRank.getParam();
    		location = excelUrl+"?" + Cms.jsonParamStr(param);
    	});
    },
	/**
	 * 选择固定时间
	 * @param a
	 */
	checkFixedTime:function(a){
		mini.get("beginTime").setValue("");
		mini.get("endTime").setValue("");
		$("#fixedTime").find('a').each(function(i){
			if(i==a){
				monthdata.timeType = i;
				$(this).attr("style","color:white;background-color:rgb(61, 116, 183);cursor:hand");
			}else{
				$(this).attr("style","color:#333;cursor:hand");
			}
		});
	},
	search:function(){
		var param = earnRateRank.getParam();
		mini.get('datagridRank').load(param);
	},
	getParam:function(){
		var param = {};
		// 注册渠道
		param.channel = $('#statis-channel a.active').data('channel') || mini.get('channel').getValue();
		// 时间
		param.timeType = $('#statis-time a.active').data('time');
		// 支付渠道
		param.lotteryCode = $('#statis-code a.active').data('code') || mini.get('lotteryCode').getValue();
		param.startTime = mini.get('startTime').getFormValue();
		param.startTime = mini.get('startTime').getFormValue()==""?mini.get('startTime').getFormValue():mini.get('startTime').getFormValue()+" 00:00:00";
		param.endTime = mini.get('endTime').getFormValue()==""?mini.get('endTime').getFormValue():mini.get('endTime').getFormValue()+" 23:59:59";		
		param.userName= mini.get('userName').getFormValue();
		return param;
	},
	clearActive : function(id) {
		$('#'+id).children().removeClass("active");
	}
};






