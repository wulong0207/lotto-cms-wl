$(function(){
	earnRate.init();
});  


earnRate={
	timeType:null,
    init:function(){
        mini.parse();
        // 渠道下拉框
//        earnRate.registerChannel = mini.get("registerChannel");
//    	// 注册渠道
//    	$.ajax({
//	    	url : "operatemgr/marketchannel/dic",
//	    }).done(function(data) {
//	    	data.shift();
//	    	mini.get('channel').setData(data);
//	    });
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
        
        //渲染图表
        earnRate.renderChart();
        
        
    	$('#searchBtn').click(function(e){
    	    earnRate.renderChart();
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
		earnRate.renderChart();
	},
	renderChart:function(){
		$.get("report/earnRate/rateStatistics",
				earnRate.getParam(),
				function(data) {
			if(data.errorCode && (data.errorCode != Code.success)) {
				mini.alert(data.message);
				return;
			}
			$('#u_n30').text(data.u_n30);
			$("#u_n30_n0").text(data.u_n30_n0);
			$("#u_p0_p30").text(data.u_p0_p30);
			$("#u_p30_p50").text(data.u_p30_p50);
			$("#u_p50_p80").text(data.u_p50_p80);
			$("#u_p80_p100").text(data.u_p80_p100);
			$("#u_p100_p150").text(data.u_p100_p150);
			$("#u_p150_p200").text(data.u_p150_p200);
			$("#u_p200_p300").text(data.u_p200_p300);
			$("#u_p300").text(data.u_p300);
			
			$('#ur_n30').text(mini.formatNumber(data.ur_n30,'p'));
			$("#ur_n30_n0").text(mini.formatNumber(data.ur_n30_n0,'p'));
			$("#ur_p0_p30").text(mini.formatNumber(data.ur_p0_p30,'p'));
			$("#ur_p30_p50").text(mini.formatNumber(data.ur_p30_p50,'p'));
			$("#ur_p50_p80").text(mini.formatNumber(data.ur_p50_p80,'p'));
			$("#ur_p80_p100").text(mini.formatNumber(data.ur_p80_p100,'p'));
			$("#ur_p100_p150").text(mini.formatNumber(data.ur_p100_p150,'p'));
			$("#ur_p150_p200").text(mini.formatNumber(data.ur_p150_p200,'p'));
			$("#ur_p200_p300").text(mini.formatNumber(data.ur_p200_p300,'p'));
			$("#ur_p300").text(mini.formatNumber(data.ur_p300,'p'));
			
			$('#m_n30').text(data.m_n30);
			$("#m_n30_n0").text(data.m_n30_n0);
			$("#m_p0_p30").text(data.m_p0_p30);
			$("#m_p30_p50").text(data.m_p30_p50);
			$("#m_p50_p80").text(data.m_p50_p80);
			$("#m_p80_p100").text(data.m_p80_p100);
			$("#m_p100_p150").text(data.m_p100_p150);
			$("#m_p150_p200").text(data.m_p150_p200);
			$("#m_p200_p300").text(data.m_p200_p300);
			$("#m_p300").text(data.m_p300);
			
			$('#mr_n30').text(mini.formatNumber(data.mr_n30,'p'));
			$("#mr_n30_n0").text(mini.formatNumber(data.mr_n30_n0,'p'));
			$("#mr_p0_p30").text(mini.formatNumber(data.mr_p0_p30,'p'));
			$("#mr_p30_p50").text(mini.formatNumber(data.mr_p30_p50,'p'));
			$("#mr_p50_p80").text(mini.formatNumber(data.mr_p50_p80,'p'));
			$("#mr_p80_p100").text(mini.formatNumber(data.mr_p80_p100,'p'));
			$("#mr_p100_p150").text(mini.formatNumber(data.mr_p100_p150,'p'));
			$("#mr_p150_p200").text(mini.formatNumber(data.mr_p150_p200,'p'));
			$("#mr_p200_p300").text(mini.formatNumber(data.mr_p200_p300,'p'));
			$("#mr_p300").text(mini.formatNumber(data.mr_p300,'p'));
			
			earnRate.showPersonChart(data);
			earnRate.showMoneyChart(data);
			
		});
		
	},
	getParam:function(){
		var param = {};
		// 注册渠道
		param.channel = $('#statis-channel a.active').data('channel') || mini.get('channel').getValue();
		// 时间
		param.timeType = $('#statis-time a.active').data('time');
		// 支付渠道
		param.lotteryCode = $('#statis-code a.active').data('code') || mini.get('lotteryCode').getValue();
		param.startTime = mini.get('startTime').getFormValue()==""?mini.get('startTime').getFormValue():mini.get('startTime').getFormValue()+" 00:00:00";
		param.endTime = mini.get('endTime').getFormValue()==""?mini.get('endTime').getFormValue():mini.get('endTime').getFormValue()+" 23:59:59";		
		return param;
	},
	clearActive : function(id) {
		$('#'+id).children().removeClass("active");
	},
	showPersonChart:function(data){
		//投注用户
		var personChart = echarts.init(document.getElementById('betPersonChart'));
		var personChartOption = {
			    color: ['#3398DB'],
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    toolbox: {
			        feature: {
			            dataView: {show: true, readOnly: true},
			            magicType: {show: true, type: ['line', 'bar']},
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data : ['<=-30%', '-30%<n≤0%', '0%<n≤30%', '30%<n≤50%', '50%<n≤80%','80%<n≤100%',
			                    '100%<n≤150%','150%<n≤200%','200%<n≤300%','n>300%'],
			            axisTick: {
			                alignWithLabel: true
			            }
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'投注用户',
			            type:'bar',
			            barWidth: '60%',
			            itemStyle: {
			                normal: {                   // 系列级个性化，横向渐变填充
			                    borderRadius: 5,
			                    label : {
			                        show : true,
			                        textStyle : {
			                            fontSize : '15',
			                            fontFamily : '微软雅黑',
			                            fontWeight : 'bold',
			                            color:'red'
			                        },
			                        position:'top'
			                    }
			                }
			            },
			            data:[data.u_n30,data.u_n30_n0,data.u_p0_p30,data.u_p30_p50,data.u_p50_p80,data.u_p80_p100,data.u_p100_p150,
			                  data.u_p150_p200,data.u_p200_p300,data.u_p300]
			        }
			    ]
			};
		////可选，是否不跟之前设置的option进行合并，默认为false，即合并。
		personChart.setOption(personChartOption,false);
	},
	showMoneyChart:function(data){
		//投注金额
		var moneyChart = echarts.init(document.getElementById('betMoneyChart'));
		var moneyChartOption = {
			    color: ['#3398DB'],
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    toolbox: {
			        feature: {
			            dataView: {show: true, readOnly: true},
			            magicType: {show: true, type: ['line', 'bar']},
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data : ['<=-30%','-30%<n≤0%', '0%<n≤30%', '30%<n≤50%', '50%<n≤80%','80%<n≤100%',
			                    '100%<n≤150%','150%<n≤200%','200%<n≤300%','n>300%'],
			            axisTick: {
			                alignWithLabel: true
			            }
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'投注金额',
			            type:'bar',
			            barWidth: '60%',
			            itemStyle: {
			                normal: {                   // 系列级个性化，横向渐变填充
			                    borderRadius: 5,
			                    label : {
			                        show : true,
			                        textStyle : {
			                            fontSize : '15',
			                            fontFamily : '微软雅黑',
			                            fontWeight : 'bold',
			                            color:'red'
			                        },
			                        position:'top'
			                    }
			                }
			            },
			            data:[data.m_n30,data.m_n30_n0,data.m_p0_p30,data.m_p30_p50,data.m_p50_p80,
			                  data.m_p80_p100,data.m_p100_p150,data.m_p150_p200,data.m_p200_p300,data.m_p300]
			        }
			    ]
			};
		moneyChart.setOption(moneyChartOption,false);
	}
};






