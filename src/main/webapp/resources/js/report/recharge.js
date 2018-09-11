$(function(){
	rechargeReport.init();
});  


rechargeReport={
    init:function(){
        mini.parse();
        
        //打开的时候会首先渲染一次    	
        rechargeReport.showOverview();
        rechargeReport.showPersonChart();
        rechargeReport.showMoneyChart();
    	rechargeReport.showStyleChart();
    	rechargeReport.showStatusChart();
        
        // 渠道下拉框
//        rechargeReport.registerChannel = mini.get("registerChannel");
//    	// 注册渠道
//    	$.ajax({
//	    	url : "operatemgr/marketchannel/dic",
//	    }).done(function(data) {
//	    	data.shift();
//	    	mini.get('channel').setData(data);
//	    });
    	//第三方支付渠道
    	$.ajax({
	    	url : "report/recharge/rechargeStyles",
	    }).done(function(data) {
	    	mini.get('paychannel').setData(data);
	    });
        // 点击查询条件时，改变样式
    	$("#statis-time a, #statis-channel a, #statis-paychannel a").click(function(){
    		$(this).siblings().removeClass("active");
			$(this).toggleClass("active");
		});
    	// 点击选项时，把渠道下拉框置空
    	$("#statis-channel a").click(function(){
    		mini.get('channel').setValue(null);
		});
    	// 点击选项时，把注册渠道下拉框置空
    	$("#statis-paychannel a").click(function(){
    		mini.get('paychannel').setValue(null);
    	});
    	// 点击选项时，把时间空
    	$("#statis-time a").click(function(){
    		mini.get('startTime').setValue(null);
    		mini.get('endTime').setValue(null);
    	});
        // 1图表 2明细
        $('input[name="chartDetailData"]').click(function(){
      
        	if($(this).val() == 1){
    			$("#rechargePersonNumTable").hide();
    			$("#rechargePersonNumChart").show();
    			$("#rechargeMoneyNumTable").hide();
    			$("#rechargeMoneyNumChart").show();
    			$("#rechargeStyleTable").hide();
    			$("#rechargeStyleChart").show();
    			$("#rechargeStatusTable").hide();
    			$("#rechargeStatusChart").show();
    			$(".sumData").show();
        	}else if($(this).val() == 2){
        		$("#rechargePersonNumChart").hide();
        		$("#rechargePersonNumTable").show();
    			$("#rechargeMoneyNumChart").hide();
    			$("#rechargeMoneyNumTable").show();
    			$("#rechargeStyleTable").show();
    			$("#rechargeStyleChart").hide();
     			$("#rechargeStatusTable").show();
    			$("#rechargeStatusChart").hide();
    			$(".sumData").hide();
    			
    			mini.get("datagrid_person").load(rechargeReport.getParam());
    			mini.get("datagrid_money").load(rechargeReport.getParam());
    			mini.get("datagrid_style").load(rechargeReport.getParam());
    			mini.get("datagrid_status").load(rechargeReport.getParam());
        	}        	
        });

    	// 绑定查询按钮
    	$('#searchBtn').click(function(e){
    		rechargeReport.showOverview();
    		rechargeReport.showPersonChart();
    		rechargeReport.showMoneyChart();
    		rechargeReport.showStyleChart();
    		rechargeReport.showStatusChart();
    		//重新加载列表
			mini.get("datagrid_person").load(rechargeReport.getParam());
			mini.get("datagrid_money").load(rechargeReport.getParam());
			mini.get("datagrid_style").load(rechargeReport.getParam());
			mini.get("datagrid_status").load(rechargeReport.getParam());
    	});
    	
    	$('#exportBtn').click(function(e) {
    		var excelUrl = $(e.srcElement).data('excel-url');
    		var param = rechargeReport.getParam();
    		//确实是哪个列表的数据需要导出
    		var tabs = mini.get("tabs1");
    		param.excel =tabs.getActiveIndex()+'';
    		location = excelUrl+"?" + Cms.jsonParamStr(param);
    	});
    	
    },
    getParam:function(){
		var param = {};
		// 注册渠道
		param.channel = $('#statis-channel a.active').data('channel') || mini.get('channel').getValue();		
		// 时间
		param.timeType = $('#statis-time a.active').data('time');
		// 支付渠道
		param.payChannel = $('#statis-paychannel a.active').data('paychannel') ||mini.get('paychannel').getValue();
		param.startTime = mini.get('startTime').getFormValue()==""?mini.get('startTime').getFormValue():mini.get('startTime').getFormValue()+" 00:00:00";
		param.endTime = mini.get('endTime').getFormValue()==""?mini.get('endTime').getFormValue():mini.get('endTime').getFormValue()+" 23:59:59";		
		return param;
    },
    showOverview:function(){
		// 数据概况
		$.get("report/recharge/rechargeOverview",
				rechargeReport.getParam(),
				function(data) {
			if(data.errorCode && (data.errorCode != Code.success)) {
				mini.alert(data.message);
				return;
			}
			// 充值人数
			$('#fillUser').text(data.fillUser);
			$('#newFillUser').text(data.newFillUser);
			$("#newPersonRate").text(mini.formatNumber(data.fillUser==0 ? 0:data.newFillUser/data.fillUser,'p'));
			// 充值次数
			$('#fillTimes').text(data.fillTimes);
			$('#newFillTimes').text(data.newFillTimes);
			$('#liveness').text(mini.formatNumber(data.fillUser==0 ? 0:data.fillTimes/data.fillUser,'n'));
			// 充值金额
			$('#fillMoney').text(mini.formatNumber(data.fillMoney, 'n'));
			$('#newFillMoney').text(mini.formatNumber(data.newFillMoney, 'n'));
			$('#newMoneyRate').text(mini.formatNumber(data.fillMoney==0 ? 0:data.newFillMoney/data.fillMoney,'p'));
			// 人均充值
			$('#perRecharge').text(mini.formatNumber(data.fillUser==0 ? 0:data.fillMoney/data.fillUser, 'n'));
			$('#newPerRecharge').text(mini.formatNumber(data.newFillUser==0 ? 0:data.newFillMoney/data.newFillUser, 'n'));
		});
    },
    showPersonChart:function(){
    	//显示充值人数图表
		$.get("report/recharge/personStatistics",rechargeReport.getParam(),function(data) {
			if(data.errorCode && (data.errorCode != Code.success)) {
				mini.alert(data.message);
				return;
			}
			rechargeReport.renderPersonNumberChart(data);
		});
    },
    showMoneyChart:function(){
    	//显示充值金额图表
		$.get("report/recharge/moneyStatistics",rechargeReport.getParam(),function(data) {
			if(data.errorCode && (data.errorCode != Code.success)) {
				mini.alert(data.message);
				return;
			}
			rechargeReport.renderMoneyNumChart(data);
		});
    },
    showStyleChart:function(){
    	//显示充值方式图表
		$.get("report/recharge/styleStatistics",rechargeReport.getParam(),function(data) {
			if(data.errorCode && (data.errorCode != Code.success)) {
				mini.alert(data.message);
				return;
			}
			rechargeReport.renderStyleChart(data);
		});
    },
    showStatusChart:function(){
    	//成功与失败
		$.get("report/recharge/statusStatistics",rechargeReport.getParam(),function(data) {
			if(data.errorCode && (data.errorCode != Code.success)) {
				mini.alert(data.message);
				return;
			}
			if(data.bank){
				rechargeReport.renderStatusChart(data);
			}
		});
    },
    checkFixedTime:function(a){
		//选择固定时间
		mini.get("beginTime").setValue("");
		mini.get("endTime").setValue("");
		$("#fixedTime").find('a').each(function(i){
			if(i==a){
				rechargeReport.timeType = i;
				$(this).attr("style","color:white;background-color:rgb(61, 116, 183);cursor:hand");
			}else{
				$(this).attr("style","color:#333;cursor:hand");
			}
		});
	},
	comboxOnCloseClick:function(e){
	    var obj = e.sender;
	    obj.setText("");
	    obj.setValue("");
	},
	clearActive : function(id) {
		$('#'+id).children().removeClass("active");
	},
	renderPersonNumberChart:function(range){
		$('#u0_50').text(range.u0_50);
		$('#u0_50_ratio').text(range.r0_50);
		$('#u51_100').text(range.u51_100);
		$('#u51_100_ratio').text(range.r51_100);
		$('#u101_500').text(range.u101_500);
		$('#u101_500_ratio').text(range.r101_500);
		$('#u501_1000').text(range.u501_1000);
		$('#u501_1000_ratio').text(range.r501_1000);
		$('#u1001_5000').text(range.u1001_5000);
		$('#u1001_5000_ratio').text(range.r1001_5000);
		$('#u5001_10000').text(range.u5001_10000);
		$('#u5001_10000_ratio').text(range.r5001_10000);
		$('#u10001_50000').text(range.u10001_50000);
		$('#u10001_50000_ratio').text(range.r10001_50000);
		$('#u50001_100000').text(range.u50001_100000);
		$('#u50001_100000_ratio').text(range.r50001_100000);
		$('#u100000').text(range.u100000);
		$('#u100000_ratio').text(range.r100000);
		//渲染充值人数图形
		var myRechargeChart = echarts.init(document.getElementById('rechargePersonNumChart'));
		var rechargePersonNumOption = {
			    color: ['#3398DB'],
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    toolbox: {
			        feature: {
			            dataView: {show: true, readOnly: true,
			            	optionToContent: function(opt) {
			            	    var axisData = opt.xAxis[0].data;
			            	    var series = opt.series;
			            	    var table = '<table style="width:100%;text-align:center"><tbody><tr>'
			            	                 + '<td>金额范围</td>'
			            	                 + '<td>充值人数</td>'
			            	                 + '</tr>';
			            	    for (var i = 0, l = axisData.length; i < l; i++) {
			            	        table += '<tr>'
			            	                 + '<td>' + axisData[i] + '</td>'
			            	                 + '<td>' + series[0].data[i] + '</td>'
			            	                 + '</tr>';
			            	    }
			            	    table += '</tbody></table>';
			            	    return table;
			            	}
			            },
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
			            data : ['0-50', '50-100', '100-500', '500-1000', '1千-5千', '5千-1万', '1万-5万','5万-10万','10万以上'],
			            axisTick: {
			                alignWithLabel: true
			            }
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
				        minInterval: 1
			        }
			    ],
			    series : [
			        {
			            name:'充值人数',
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
			            data:[range.u0_50, range.u51_100, range.u101_500, range.u501_1000, range.u1001_5000, range.u5001_10000, range.u10001_50000, range.u50001_100000,range.u100000]
			        }
			    ]
			};
		//可选，是否不跟之前设置的option进行合并，默认为false，即合并。
		myRechargeChart.setOption(rechargePersonNumOption,true);
	},
	renderMoneyNumChart:function(range){
		//充值金额
		$('#m0_50').text(range.m0_50);
		$('#m0_50_ratio').text(range.r0_50);
		$('#m51_100').text(range.m51_100);
		$('#m51_100_ratio').text(range.r51_100);
		$('#m101_500').text(range.m101_500);
		$('#m101_500_ratio').text(range.r101_500);
		$('#m501_1000').text(range.m501_1000);
		$('#m501_1000_ratio').text(range.r501_1000);
		$('#m1001_5000').text(range.m1001_5000);
		$('#m1001_5000_ratio').text(range.r1001_5000);
		$('#m5001_10000').text(range.m5001_10000);
		$('#m5001_10000_ratio').text(range.r5001_10000);
		$('#m10001_50000').text(range.m10001_50000);
		$('#m10001_50000_ratio').text(range.r10001_50000);
		$('#m50001_100000').text(range.m50001_100000);
		$('#m50001_100000_ratio').text(range.r50001_100000);
		$('#m100000').text(range.m100000);
		$('#m100000_ratio').text(range.r100000);
		
		var myRechargeMoneyNumChart = echarts.init(document.getElementById('rechargeMoneyNumChart'));
		var rechargeMoneyNumOption = {
			    color: ['#3398DB'],
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    toolbox: {
			        feature: {
			            dataView: {show: true, readOnly: true,
			            	optionToContent: function(opt) {
		            	    var axisData = opt.xAxis[0].data;
		            	    var series = opt.series;
		            	    var table = '<table style="width:100%;text-align:center"><tbody><tr>'
		            	                 + '<td>金额范围</td>'
		            	                 + '<td>总金额</td>'
		            	                 + '</tr>';
		            	    for (var i = 0, l = axisData.length; i < l; i++) {
		            	        table += '<tr>'
		            	                 + '<td>' + axisData[i] + '</td>'
		            	                 + '<td>' + series[0].data[i] + '</td>'
		            	                 + '</tr>';
		            	    }
		            	    table += '</tbody></table>';
		            	    return table;
		            	}},
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
			            data : ['0-50', '50-100', '100-500', '500-1000', '1千-5千', '5千-1万', '1万-5万','5万-10万','10万以上'],
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
			            name:'总金额',
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
			            data:[range.m0_50, range.m51_100, range.m101_500, range.m501_1000, range.m1001_5000, range.m5001_10000, range.m10001_50000, range.m50001_100000,range.m100000]
			        }
			    ]
			};
		//可选，是否不跟之前设置的option进行合并，默认为false，即合并。
		myRechargeMoneyNumChart.setOption(rechargeMoneyNumOption,true);
	},
	renderStyleChart:function(data){
		//充值方式
		var personNumChart =echarts.init(document.getElementById('personNumChart'));
		var moneyNumChart =echarts.init(document.getElementById('moneyNumChart'));
		var personNumOption = {
			    title : {
			        text: '充值金额'
			    },
			    toolbox: {
			        feature: {
			            dataView: {show: true, readOnly: true},
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'horizontal',
			        top: '20',
			        data: data.moneyKinds
			    },
			    series : [
			        {
			            name: '充值金额',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:data.moneyData,
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                },
			                normal: {label:{  
			                show:true,  
			                formatter:'{b} : {c} ({d}%)'  
			            },  
			            labelLine:{show:true}}, 
			            }
			        }
			    ]
			};
		var moneyNumOption = {
			    title : {
			        text: '充值人数'
			    },			   
			    toolbox: {
			        feature: {
			            dataView: {show: true, readOnly: true},
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'horizontal',
			        top: '20',
			        data: data.personKinds
			    },
			    series : [
			        {
			            name: '充值人数',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:data.personData,
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                },
			                normal: {label:{  
			                show:true,  
			                formatter:'{b} : {c} ({d}%)'  
			            },  
			            labelLine:{show:true}}, 
			            }
			        }
			    ]
			};
		////可选，是否不跟之前设置的option进行合并，默认为false，即合并。
		personNumChart.setOption(personNumOption,true);
		moneyNumChart.setOption(moneyNumOption,true);
	},
	renderStatusChart:function(data){
		//成功与失败
		var myRechargeStatusChart=echarts.init(document.getElementById('rechargeStatusChart'));
		var rechargeStatusOption = {
			    tooltip: {
			        trigger: 'axis',
			        axisPointer: {
			            type: 'cross',
			            crossStyle: {
			                color: '#999'
			            }
			        }
			    },
			    toolbox: {
			        feature: {
			            dataView: {show: true, readOnly: true,
			            	optionToContent: function(opt) {
			            	    var axisData = opt.xAxis[0].data;
			            	    var series = opt.series;
			            	    var table = '<table style="width:100%;text-align:center"><tbody><tr>'
			            	                 + '<td>充值方式</td>'
			            	                 + '<td>充值成功</td>'
			            	                 + '<td>充值失败(第三方接口返回)</td>'
			            	                 + '<td>充值失败(用户原因)</td>'
			            	                 + '<td>充值成功率</td>'
			            	                 + '</tr>';
			            	    for (var i = 0, l = axisData.length; i < l; i++) {
			            	        table += '<tr>'
			            	                 + '<td>' + axisData[i] + '</td>'
			            	                 + '<td>' + series[0].data[i] + '</td>'
			            	                 + '<td>' + series[1].data[i] + '</td>'
			            	                 + '<td>' + series[2].data[i] + '</td>'
			            	                 + '<td>' + series[3].data[i] + '%</td>'
			            	                 + '</tr>';
			            	    }
			            	    table += '</tbody></table>';
			            	    return table;
			            	}
			            },
			            magicType: {show: true, type: ['line', 'bar']},
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    legend: {
			        data:['充值成功','充值失败(第三方接口返回)','充值失败(用户原因)','充值成功率']
			    },
			    xAxis: [
			        {
			            type: 'category',
			            data: ['支付宝','微信','银联','快捷','QQ','充值卡','第三方','京东'],
			            axisPointer: {
			                type: 'shadow'
			            }
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value',
			            name: '笔数',
			            axisLabel: {
			                formatter: '{value}'
			            }
			        },
			        {
			            type: 'value',
			            name: '成功率',
			            min: 0,
			            max: 100,
			            interval: 10,
			            axisLabel: {
			                formatter: '{value}'
			            }
			        }
			    ],
			    series: [
			        {
			            name:'充值成功',
			            type:'bar',
			            data:[data.alipay.success, data.wxpay.success, data.bank.success,data.fast.success,data.qq.success,data.card.success,data.other.success,data.jd.success]
			        },
			        {
			            name:'充值失败(第三方接口返回)',
			            type:'bar',
			            data:[data.alipay.failThird, data.wxpay.failThird, data.bank.failThird,data.fast.failThird,data.qq.failThird,data.card.failThird,data.other.failThird,data.jd.failThird]
			        },
			        {
			            name:'充值失败(用户原因)',
			            type:'bar', 
			            data:[data.alipay.failUser, data.wxpay.failUser, data.bank.failUser,data.fast.failUser,data.qq.failUser,data.card.failUser,data.other.failUser,data.jd.failUser]
			        },
			        {
			            name:'充值成功率',
			            type:'line',
			            yAxisIndex: 1,
			            data:[data.alipay.ratio, data.wxpay.ratio, data.bank.ratio,data.fast.ratio,data.qq.ratio,data.card.ratio,data.other.ratio,data.jd.ratio]
			        }
			    ]
			};
		////可选，是否不跟之前设置的option进行合并，默认为false，即合并。
		myRechargeStatusChart.setOption(rechargeStatusOption,true);
	}
};
