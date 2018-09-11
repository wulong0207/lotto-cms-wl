$(function(){
	report_brief.init();
});  

var report_brief={
    init:function(){
    	mini.parse();
    	report_brief.renderUserChart();
    	report_brief.renderBetChart();
    	report_brief.renderBuyerChart();
    	report_brief.renderMoneyChart();
        // 点击查询条件时，改变样式
    	$("#statis-time a, #statis-platform a").click(function(){
    		$(this).siblings().removeClass("active");
			$(this).toggleClass("active");
		});
    	$("#statis-channel a").click(function(){
			if($(this).data("channel")=="" ||$(this).data("channel")==null ){
				$(".qudao").show();
			}else{
				$(".qudao").hide();
			}
			mini.get('channel').setValue(null);
    		$(this).siblings().removeClass("active");
			$(this).toggleClass("active");
		});
    	
    	// 点击选项时，把时间空
    	$("#statis-time a").click(function(){
    		mini.get('startTime').setValue(null);
    		mini.get('endTime').setValue(null);
    	});
    	// 切换
    	$('input[type=radio][name=userChartRadio]').change(function(){
    		$("#userChart").toggle();
    		$("#userGrid").toggle();
    	});
    	$('input[type=radio][name=betChartRadio]').change(function(){
    		$("#betChart").toggle();
    		$("#betGrid").toggle();
    	});
    	$('input[type=radio][name=lotterySailRadio]').change(function(){
    		$("#buyerData").toggle();
    		$("#moneyData").toggle();
    		$("#buyerGrid").toggle();
    		$("#moneyGrid").toggle();
    	});
    	// 绑定查询按钮
    	$('#searchBtn').click(function(e){
    		var param = report_common.getParam();
    		// 更新数据概况
    		report_brief.updateOverview(param);
    		// 更新新用户数据和全站投注情况
    		report_brief.updateRookieData(param);
//    		param = report_common.getParam();
    		report_brief.updateBetData(param);
    		// TODO 用户分布是购买人数排序  投注金额分布是根据购买金额排序，不能共用一个方法
    		// 更新投注用户分布
    		report_brief.updateBuyer(param);
    		// 更新投注金额分布
    		report_brief.updateMoney(param);
    	});
    	
    	// 导出excel
    	$('.exportBtn').click(function(e) {
    		//var excel = e.srcElement.dataset.excel;
    		var excelUrl = $(e.srcElement).data('excel-url');
    		location = excelUrl + "/excel?" + Cms.jsonParamStr(report_common.getParam());
    	});
    	
		var param = report_common.getParam();
		// 更新数据概况
		report_brief.updateOverview(param);
		// 更新新用户数据和全站投注情况
		report_brief.updateRookieData(param);
		report_brief.updateBetData(param);
		// TODO 用户分布是购买人数排序  投注金额分布是根据购买金额排序，不能共用一个方法
		// 更新投注用户分布
		report_brief.updateBuyer(param);
		// 更新投注金额分布
		report_brief.updateMoney(param);
	},
	// 更新数据概况
	updateOverview : function(param) {
		$.get("report/brief/overview",
				param,
				function(data) {
			if(data.errorCode && (data.errorCode != Code.success)) {
				console.error(data.message);
				return;
			}
			// 注册数据
			$('#regUser').text(data.all.regUser);
			$('#checkUser').text(data.all.checkUser);
			$('#regUserByChannel').text(data.channel ? data.channel.regUser : '-');
			$('#checkUserByChannel').text(data.channel ? data.channel.checkUser : '-');
			// 首充数据
			$('#newFillUser').text(data.all.newFillUser);
			$('#newFillRatio').text(data.all.newFillRatio+'%');
			$('#newFillUserByChannel').text(data.channel ? data.channel.newFillUser : '-');
			$('#newFillRatioByChannel').text(data.channel ? data.channel.newFillRatio+'%' : '-');
			// 投注人数
			$('#orderUser').text(data.all.orderUser);
			$('#orderTimes').text(data.all.orderTimes);
			$('#liveness').text(Math.round(data.all.liveness));
			$('#orderUserByChannel').text(data.channel ? data.channel.orderUser : '-');
			// 投注额
			$('#orderMoney').text(data.all.orderMoney);
			$('#newOrderMoney').text(data.all.newOrderMoney);
			$('#avgOrderMoney').text(Math.round(data.all.avgOrderMoney));
			$('#orderMoneyByChannel').text(data.channel ? data.channel.orderMoney : '-');
		}
		);
	},
	//  更新新用户数据
	updateRookieData : function(param) {
//		param.platform = null;
		$.get("report/brief/rookie",param, function(data) {
			if(data.errorCode && (data.errorCode != Code.success)) {
				console.error(data.message);
				return;
			}
			// 新用户数据
			// 更新"新用户数据"图表
			var chart = report_brief.userChart;
			var option = chart.getOption();
			var regUsers=[], checkUsers =[], newFillUsers=[], addDates=[],newFillRatios=[];
			data.forEach(function(record) {
				regUsers.push(record.regUser);
				checkUsers.push(record.checkUser);
				newFillUsers.push(record.newFillUser);
				addDates.push(record.addDate);
				newFillRatios.push(record.newFillRatio);
			});
			// 设置X轴
			option.xAxis[0].interval = Math.ceil(addDates.length/10);
			option.xAxis[0].data = addDates;
			// 设置Y轴最大值
			var maxOfMax = Math.max(
					Math.max.apply(null, regUsers),
					Math.max.apply(null, checkUsers),
					Math.max.apply(null, newFillUsers) 
					);
			option.yAxis[0].max = maxOfMax;
			option.yAxis[0].interval = Math.ceil(maxOfMax/10);
			option.series[0].data = regUsers;
			option.series[1].data = checkUsers;
			option.series[2].data = newFillUsers;
			option.series[3].data = newFillRatios;
			// 设置第二个Y轴最大值,百分比正常不会超过100%
			var maxNewFillRatio = Math.max.apply(null, newFillRatios);
			maxNewFillRatio = maxNewFillRatio > 100 ? maxNewFillRatio : 100;
			option.yAxis[1].max = maxNewFillRatio;
			option.yAxis[1].interval = Math.ceil(maxNewFillRatio/10);
			chart.setOption(option,true);
			// 更新新用户数据的表格数据
    		mini.get('userGrid').load(param);
		});
	},	
	// 全站投注情况
	updateBetData : function(param) {
		$.get("report/brief/rookie",param, function(data) {
			if(data.errorCode && (data.errorCode != Code.success)) {
				console.error(data.message);
				return;
			}
			// 全站投注情况
			var chartBet = report_brief.betChart;
			var option = chartBet.getOption();
			var orderMoneys=[], orderUsers=[], arpus=[],addDates=[];
			data.forEach(function(record) {
				orderMoneys.push(record.orderMoney);
				orderUsers.push(record.orderUser);
				arpus.push(record.arpu);
				addDates.push(record.addDate);
			});
			var maxOrderMoney = Math.max.apply(null, orderMoneys);
			var maxOrderUsers = Math.max.apply(null, orderUsers);
			var maxArpu = Math.max.apply(null, arpus);
			option.series[0].data = orderMoneys;
			option.series[1].data = orderUsers;
			option.series[2].data =  arpus;
			maxOfMax = maxOrderMoney > maxOrderUsers ? maxOrderMoney : maxOrderUsers;
			// 左边的Y轴
			option.yAxis[0].max = maxOfMax;
			option.yAxis[0].interval = Math.ceil(maxOfMax/10);
			// 右边的Y轴
			option.yAxis[1].max = maxArpu;
			option.yAxis[1].interval = Math.ceil(maxArpu/10);
			// 设置X轴
			option.xAxis[0].interval = Math.ceil(addDates.length/10);
			option.xAxis[0].data = addDates;
			chartBet.setOption(option,true);

    		// 全站投注情况的表格数据
    		mini.get('betGrid').load(param);
		});
	},
	// 更新投注用户分布
	updateBuyer : function(param) {
		$.get("report/brief/distribution/user",param, function(data) {
			if(data.errorCode && (data.errorCode != Code.success)) {
				console.error(data.message);
				return;
			}
			var chart,option,$ele;
			// 更新"玩法分布"图表
			chart = report_brief.buyerChart;
			option = chart.getOption();
			
			// 提取各种数据
			var orderUsers = [],lotteryNames=[],totalUser=0;
			data.forEach(function(cur){
				if(!cur.orderUser){
					cur.orderUse =0;
				}
				orderUsers.push(cur.orderUser);
				lotteryNames.push(cur.lotteryName);
				totalUser += cur.orderUser;
			});
			var userRatio = orderUsers.map(function(cur) {
				return  totalUser ==0?'0.00':mini.formatNumber(100*cur/totalUser,'0.00');
			});
			// 设置X轴
			option.xAxis[0].data = lotteryNames;
			// 设置左边Y轴
			var max = Math.max.apply(null, orderUsers);
			option.yAxis[0].max = max;
			option.yAxis[0].interval = Math.ceil(max/10);
			// 设置右边Y轴
			max = Math.max.apply(null, userRatio);
			option.yAxis[1].max = max;
			option.yAxis[1].interval = Math.ceil(max/10);
			option.series[0].data = orderUsers;
			option.series[1].data = userRatio;	
			option.toolbox[0].feature.dataView.show=false;
			chart.setOption(option,true);
			// 更新列表
			// 竞彩足球：65%       13000人（新3000人 ）
			$ele = $('#buyerList ul');
			$ele.empty();
			data.forEach(function(cur, index, array) {
				$ele.append("<p></p>").append($('<li>').text(
						String.format('{0}：{1}%       {2}人（新{3}人 ）',
								cur.lotteryName,
								userRatio[index],
								cur.orderUser?cur.orderUser:0,
								cur.newOrderUser?cur.newOrderUser:0
						)
				));
			});
			// 更新新用户数据的表格数据
			mini.get('buyerGrid').load(param);
		});
	},
	// 更新投注金额分布
	updateMoney : function(param) {
		$.get("report/brief/distribution/money",param, function(data) {
			if(data.errorCode && (data.errorCode != Code.success)) {
				console.error(data.message);
				return;
			}
			var chart,option,$ele;
			// 提取各种数据
			var orderMoneys = [],lotteryNames=[],totalMoney=0;
			data.forEach(function(cur){
				if(!cur.orderMoney){
					cur.orderMoney = 0.00;
				}
				orderMoneys.push(cur.orderMoney);
				lotteryNames.push(cur.lotteryName);
				totalMoney += cur.orderMoney;
			});
			var moneyRatio = orderMoneys.map(function(cur) {
				return totalMoney == 0 ? '0.00%' : mini.formatNumber(100*cur/totalMoney,'0.00');
			});
			// 更新投注金额分布
			chart = report_brief.moneyChart;
			option = chart.getOption();
			// 设置X轴
			option.xAxis[0].data = lotteryNames;
			// 设置左边Y轴
			var max = Math.max.apply(null, orderMoneys);
			option.yAxis[0].max = max;
			option.yAxis[0].interval = Math.ceil(max/10);
			// 设置右边Y轴
			max = Math.max.apply(null, moneyRatio);
			option.yAxis[1].max = max;
			option.yAxis[1].interval = Math.ceil(max/10);
			
			option.series[0].data = orderMoneys;
			option.series[1].data = moneyRatio;
			option.toolbox[0].feature.dataView.show=false;
			chart.setOption(option,true);
			// 更新列表
			// 竞彩足球：65%       130万元（新3万元 ）
			$ele = $('#moneyList ul');
			$ele.empty();
			data.forEach(function(cur, index, array) {
				$ele.append("<p></p>").append($('<li>').text(
						String.format('{0}：{1}%       {2}元（新{3}元 ）',
								cur.lotteryName,
								moneyRatio[index],
								cur.orderMoney?cur.orderMoney:0.00,
								cur.newOrderMoney?cur.newOrderMoney:0.00
						)
				));
			});
			// TODO 全站投注情况的表格数据
			mini.get('moneyGrid').load(param);
		});
	},
	setHeader : function(sender) {
		// 设置表头
		var grid =mini.get(sender.source.id);
		var headers = sender.result.other;
		if(headers){
			grid.columns[0].columns.forEach(function(col, index) {
				// 第1，2列是"日期"、"总投注人数",不用换
				if(index === 0 || index===1) return;
				grid.updateColumn(col, {header: headers[index-2]});
			});
		}

	},
	// 当选择下拉或自定义时间时，清除选项清单样式
	clearActive : function(id) {
		$('#'+id).children().removeClass("active");
		if(id="statis-channel"){
			var channel= mini.get("channel").getValue();
			if(channel=="" ||channel==null ){
				$(".qudao").show();
			}else{
				$(".qudao").hide();
			}
		}
	},
	
	/*
	 * 渲染新用户数据图表
	 */
	renderUserChart : function () {
		report_brief.userChart = echarts.init(document.getElementById('userChart'));
		var option = {
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
			            dataView: {show: false, readOnly: false},
			            magicType: {show: true, type: ['line', 'bar']},
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    legend: {
			        data:['新用户注册','完善用户','首充用户','有效用户转化']
			    },
			    xAxis: [
			        {
			            type: 'category',
			            //data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			            axisPointer: {
			                type: 'shadow'
			            }
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value',
			            //name: '水量',
			            min: 0,
			            max: 250,
			            interval: 50,
			            axisLabel: {
			                formatter: '{value}'
			            }
			        },
			        {
			            type: 'value',
			            //name: '温度',
			            min: 0,
			            max: 100,
			            interval: 10,
			            axisLabel: {
			                formatter: '{value} %'
			            }
			        }
			    ],
			    series: [
			        {
			            name:'新用户注册',
			            type:'bar',
			            //data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
			        },
			        {
			            name:'完善用户',
			            type:'bar',
			            //data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
			        },
			        {
			        	name:'首充用户',
			        	type:'bar',
			        	//data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
			        },
			        {
			            name:'有效用户转化',
			            type:'line',
			            yAxisIndex: 1,
			            //data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
			        }
			    ]
			};
		report_brief.userChart.setOption(option,true);
	},
	
	// 全站投注情况
	renderBetChart : function() {
		report_brief.betChart = echarts.init(document.getElementById('betChart'));
		var option = {
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
			            dataView: {show: false, readOnly: false},
			            magicType: {show: true, type: ['line', 'bar']},
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    legend: {
			        data:['全部投注金额','全部投注人数','ARUP值']
			    },
			    xAxis: [
			        {
			            type: 'category',
			            //data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			            axisPointer: {
			                type: 'shadow'
			            }
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value',
			            //name: '全部投注情况/100',
			            min: 0,
			            max: 250,
			            interval: 50,
			            axisLabel: {
			                formatter: '{value}'
			            }
			        },
			        {
			            type: 'value',
			            //name: '全部投注人数',
			            min: 0,
			            max: 25,
			            interval: 5,
			            axisLabel: {
			                formatter: '{value}'
			            }
			        }
			    ],
			    series: [
			        {
			            name:'全部投注金额',
			            type:'bar',
			            //data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
			        },
			        {
			            name:'全部投注人数',
			            type:'bar',
			            //data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
			        },
			        {
			            name:'ARUP值',
			            type:'line',
			            yAxisIndex: 1,
			            //data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
			        }
			    ]
			};
		report_brief.betChart.setOption(option,true);
	},
	
	// 投注用户分布
	renderBuyerChart : function() {
		var option = {
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
			            dataView: {show: true, readOnly: false},
			            magicType: {show: true, type: ['line', 'bar']},
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    legend: {
			        data:['投注人数', '占比']
			    },
			    xAxis: [
			        {
			            type: 'category',
			            //data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			            axisPointer: {
			                type: 'shadow'
			            },
			        	axisLabel :{  
						    interval:0   
						}
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value',
			            //name: '全部投注情况/100',
			            min: 0,
			            max: 250,
			            interval: 50,
			            axisLabel: {
			                formatter: '{value}'
			            }
			        },
			        {
			            type: 'value',
			            //name: '全部投注人数',
			            min: 0,
			            max: 25,
			            interval: 5,
			            axisLabel: {
			                formatter: '{value}'
			            }
			        }
			    ],
			    series: [
			        {
			            name:'投注人数',
			            type:'bar',
			            //data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
			        },
			        {
			            name:'占比',
			            type:'line',
			            yAxisIndex: 1,
			            //data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
			        }
			    ]
			};
		// 投注用户分布
		report_brief.buyerChart = echarts.init(document.getElementById('buyerChart'));
		report_brief.buyerChart.setOption(option,true);
	},
	
	// 投注金额分布
	renderMoneyChart : function() {
		var option = {
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
			            dataView: {show: true, readOnly: false},
			            magicType: {show: true, type: ['line', 'bar']},
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    legend: {
			        data:['投注金额', '占比']
			    },
			    xAxis: [
			        {
			            type: 'category',
			            //data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			            axisPointer: {
			                type: 'shadow'
			            },
			        	axisLabel :{  
						    interval:0   
						}
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value',
			            //name: '全部投注情况/100',
			            min: 0,
			            max: 250,
			            interval: 50,
			            axisLabel: {
			                formatter: '{value}'
			            }
			        },
			        {
			            type: 'value',
			            //name: '全部投注人数',
			            min: 0,
			            max: 25,
			            interval: 5,
			            axisLabel: {
			                formatter: '{value}'
			            }
			        }
			    ],
			    series: [
			        {
			            name:'投注金额',
			            type:'bar',
			            //data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
			        },
			        {
			            name:'占比',
			            type:'line',
			            yAxisIndex: 1,
			            //data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
			        }
			    ]
			};
		// 投注金额分布
		report_brief.moneyChart = echarts.init(document.getElementById('moneyChart'));
		report_brief.moneyChart.setOption(option,true);
	}
};

