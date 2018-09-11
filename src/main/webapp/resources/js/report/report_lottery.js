$(function(){
	report_lottery.init();
});  

var report_lottery={
    init:function(){
    	mini.parse();
    	report_lottery.renderLotteryChild();
    	report_lottery.renderOrderUserChart();
    	report_lottery.renderOrderMoneyChart();
    	report_lottery.renderLotteryWay();
    	report_lottery.renderWinning();
    	// 彩种下拉框
    	ComReq.dictionary("0303", function(result){
		     mini.get('lotteryType').setData(result["0303"]);
		});
    	ComReq.lottery("",function(result){
			mini.get('lotteryCode').setData(result);
		});
        // 点击查询条件时，改变样式
    	$("#statis-time a, #statis-channel a, #statis-platform a, #statis-lotteryCode a").click(function(){
    		$(this).siblings().removeClass("active");
			$(this).toggleClass("active");
		});
    	// 点击选项时，把渠道下拉框置空
    	$("#statis-channel a").click(function(){
    		mini.get('channel').setValue(null);
		});
    	// 点击选项时，把彩种下拉置空
    	$("#statis-lotteryCode a").click(function(){
    		mini.get('lotteryType').setValue(null);
    		mini.get('lotteryCode').setValue(null);
    	});
    	// 点击选项时，把时间空
    	$("#statis-time a").click(function(){
    		mini.get('startTime').setValue(null);
    		mini.get('endTime').setValue(null);
    	});
    	// 切换
    	$('input[type=radio][name=orderUserRadio]').change(function(){
    		$("#orderUserData").toggle();
    		$("#orderUserGrid").toggle();
    	});
    	$('input[type=radio][name=orderMoneyRadio]').change(function(){
    		$("#orderMoneyData").toggle();
    		$("#orderMoneyGrid").toggle();
    	});
    	$('input[type=radio][name=lotteryChildRadio]').change(function(){
    		$("#lotteryChildData").toggle();
    		$("#lotteryChildGrid").toggle();
    	});
    	$('input[type=radio][name=lotteryWayRadio]').change(function(){
    		$("#lotteryWayData").toggle();
    		$("#lotteryWayGrid").toggle();
    	});
    	$('input[type=radio][name=winningRadio]').change(function(){
    		$("#winningData").toggle();
    		$("#winningGrid").toggle();
    	});
    	// 绑定查询按钮
    	$('#searchBtn').click(function(e){
    		var param = report_common.getParam();
    		// 更新数据概况
    		report_lottery.updateOverview(param);
    		// 投注人数、投注金额查同一张表，同时更新
    		report_lottery.updateOrderUserAndMoney(param);
    		// 玩法分布
    		report_lottery.updateChild(param);
    		// 更新"投注方式"饼图
    		report_lottery.updateLotteryWay(param);
    		// 返奖数据
    		report_lottery.updateWinning(param);
    	});
    	
    	// 导出excel
    	$('.exportBtn').click(function(e) {
    		var excelUrl = $(e.srcElement).data('excel-url');
    		location = excelUrl + "/excel?" + Cms.jsonParamStr(report_common.getParam());
    	});
    	
    	var param = report_common.getParam();

			
		// 更新数据概况
		report_lottery.updateOverview(param);
		// 投注人数、投注金额查同一张表，同时更新
		report_lottery.updateOrderUserAndMoney(param);
		// 玩法分布
		report_lottery.updateChild(param);
		// 更新"投注方式"饼图
		report_lottery.updateLotteryWay(param);
		// 返奖数据
		report_lottery.updateWinning(param);
	},
	// 当选择下拉或自定义时间时，清除选项清单样式
	clearActive : function(id) {
		$('#'+id).children().removeClass("active");
	},
	
	lotteryTypeChange :function(){
		 var code = mini.get('lotteryType').getValue();
		 ComReq.lottery(code,function(result){
			 mini.get('lotteryCode').setData(result);
		 });
		 report_lottery.clearActive('statis-lotteryCode');
	},
	
	// 更新数据概况
	updateOverview : function(param) {
		$.get("report/lottery/overview",
				param,
				function(data) {
			if(data.errorCode && (data.errorCode != Code.success)) {
				console.error(data.message);
				return;
			}
			$('#orderUser').text(data.orderUser);
			$('#newOrderUser').text(data.newOrderUser);
			$('#orderMoney').text(data.orderMoney);
			$('#newOrderMoney').text(data.newOrderMoney);
			$('#avgOrderMoney').text(data.avgOrderMoney);
			$('#newAvgOrderMoney').text(data.newAvgOrderMoney);
			$('#liveness').text(data.liveness);
			$('#newLiveness').text(data.newLiveness);
		});
	},
	updateOrderUserAndMoney : function(param) {
		$.get("report/lottery/orderrange",
				param,
				function(data) {
			if(data.errorCode && (data.errorCode != Code.success)) {
				console.error(data.message);
				return;
			}
			var orderUsers = [];
			orderUsers.push(data.u_1_100);
			orderUsers.push(data.u_101_500);
			orderUsers.push(data.u_501_1000);
			orderUsers.push(data.u_1001_5000);
			orderUsers.push(data.u_5001_10000);
			orderUsers.push(data.u_10001_50000);
			orderUsers.push(data.u_50001_100000);
			orderUsers.push(data.u_100000);
			
			var newOrderUsers = [];
			newOrderUsers.push(data.u_1_100_new);
			newOrderUsers.push(data.u_101_500_new);
			newOrderUsers.push(data.u_501_1000_new);
			newOrderUsers.push(data.u_1001_5000_new);
			newOrderUsers.push(data.u_5001_10000_new);
			newOrderUsers.push(data.u_10001_50000_new);
			newOrderUsers.push(data.u_50001_100000_new);
			newOrderUsers.push(data.u_100000_new);
			// 更新"投注人数"图表
			var chart = report_lottery.orderUserChart;
			var option = chart.getOption();
			// 人数占比=区间人数/总人数
//			var sum = orderUsers.reduce(function (a, b) {
//				  return a + b;
//				}, 0);
			var sum;
			var ratios = orderUsers.map(function(cur, index) {
				sum = data.totalUser;
				//return (sum == 0 ? 0 : (cur/sum)*100).toFixed(2)+"%" ;
				return (sum == 0 ? 0 : (cur/sum)*100).toFixed(2);
			});
			// 设置数据
			option.series[0].data = orderUsers;
			option.series[1].data = ratios;
			// 设置左边的Y轴
			var max = Math.max.apply(null, orderUsers);
			option.yAxis[0].max = max;
			option.yAxis[0].interval = Math.ceil(max/10);
			// 设置右边的Y轴
			max = Math.max.apply(null, ratios);
			option.yAxis[1].max = max;
			option.yAxis[1].interval = Math.ceil(max/10);
			chart.setOption(option);
			
			var $ele = $('#orderUserList table');
			$ele.empty();
			// 更新列表
			// 1-100元     投注用户：5000人（新：3000人）
			var xAxisData = option.xAxis[0].data;
			var content;
			orderUsers.forEach(function(cur, index, array) {
				if(index==0){
					content='<tr><td>范围</td><td>投注用户</td><td>新投注用户</td></tr>';
					$ele.append(content);
				}
				content = '<tr  align="right"><td>'+(xAxisData[index])+'</td>'+
				'<td>'+cur+'人</td>'+
				'<td>'+newOrderUsers[index]+'人</td></tr>';
				$ele.append(content);	
			});
			// 更新投注人数的表格数据
			mini.get('orderUserGrid').load(param);
			var orderMoneys = [];
			orderMoneys.push(data.o_1_100);
			orderMoneys.push(data.o_101_500);
			orderMoneys.push(data.o_501_1000);
			orderMoneys.push(data.o_1001_5000);
			orderMoneys.push(data.o_5001_10000);
			orderMoneys.push(data.o_10001_50000);
			orderMoneys.push(data.o_50001_100000);
			orderMoneys.push(data.o_100000);
			var newOrderMoneys = [];
			newOrderMoneys.push(data.o_1_100_new);
			newOrderMoneys.push(data.o_101_500_new);
			newOrderMoneys.push(data.o_501_1000_new);
			newOrderMoneys.push(data.o_1001_5000_new);
			newOrderMoneys.push(data.o_5001_10000_new);
			newOrderMoneys.push(data.o_10001_50000_new);
			newOrderMoneys.push(data.o_50001_100000_new);
			newOrderMoneys.push(data.o_100000_new);
			// 更新"投注人数"图表
			chart = report_lottery.orderMoneyChart;
			option = chart.getOption();
			// 人数占比=区间人数/总人数
			ratios = orderMoneys.map(function(cur, index) {
				sum = data.totalMoney;
				return sum == 0 ? 0 : ((cur/sum)*100).toFixed(2);
			});
			option.series[0].data = orderMoneys;
			option.series[1].data = ratios;
			// 设置左边的Y轴
			max = Math.max.apply(null, orderMoneys);
			option.yAxis[0].max = max;
			option.yAxis[0].interval = Math.ceil(max/10);
			// 设置右边的Y轴
			max = Math.max.apply(null, ratios);
			option.yAxis[1].max = max;
			option.yAxis[1].interval = Math.ceil(max/10);
			
			chart.setOption(option);
			$ele = $('#orderMoneyList table');
			$ele.empty();
			// 更新列表
			// 1-100元     投注金额：50万（新：35万）
			xAxisData = option.xAxis[0].data;
			orderMoneys.forEach(function(cur, index, array) {
//				$ele.append($('<li>').text(
//						String.format('{0}     投注金额：{1}元（新：{2}元）',
//								xAxisData[index],
//								cur,
//								newOrderMoneys[index]
//						)
//				));
				if(index==0){
					content='<tr><td>范围</td><td>投注金额</td><td>新投注金额</td></tr>';
					$ele.append(content);
				}
				content = '<tr  align="right"><td>'+(xAxisData[index])+'</td>'+
				'<td>'+cur+'元</td>'+
				'<td>'+newOrderMoneys[index]+'元</td></tr>';
				$ele.append(content);	
				
			});
			mini.get('orderMoneyGrid').load(param);
		});
	},
	// "返奖数据"：当选择单个彩种时按子玩法展示，当选择"所有彩种时取销量前8的彩种展示 ：
	// "玩法分布"：当选择单个彩种时按子玩法更新，否则清空
	// 如果有选择某种玩法，则查询"玩法分布"
	updateChild : function(param) {
		var $ele,chart,option,max,max0,max1,x0Data=[],x1Data=[],x2Data=[],x0=[];
		chart = report_lottery.lotteryChildChart;
		option = chart.getOption();
		$ele = $('#lotteryChildList ul');
		if(param.lotteryCode && param.lotteryCode!=0) {
			$.get("report/lottery/child",
					param,
					function(data) {
				if(data.errorCode && (data.errorCode != Code.success)) {
					console.error(data.message);
					return;
				}
				// 更新"玩法分布"图表，当选择单个彩种时按子玩法更新，否则清空
				data.forEach(function(record) {
					x0.push(record.childName);
					x0Data.push(record.distinctUserCount);
					x1Data.push(record.orderAmount);
					// arpu值：投注金额/投注人数（人均投注额）
					x2Data.push(record.distinctUserCount == 0 ? 0 : (record.orderAmount/record.distinctUserCount).toFixed(2));
				});
				// 设置数据
				option.series[0].data = x0Data;
				option.series[1].data = x1Data;
				option.series[2].data = x2Data;
				// 设置X轴
				option.xAxis[0].data = x0;
				max0 = Math.max.apply(null, x0Data);
				max1 = Math.max.apply(null, x1Data);
				max =  max0 > max1 ? max0 : max1;
				// 设置Y轴
				option.yAxis[0].max = max
				option.yAxis[0].interval = Math.ceil(max/10);
				max = Math.max.apply(null, x2Data);
				option.yAxis[1].max = max;
				option.yAxis[1].interval = Math.ceil(max/10);
				chart.setOption(option);
				// 更新图表右边的列表
				// 任一    投注人数：6000人 ，投注金额：60万
				$ele.empty();
				data.forEach(function(cur, index, array) {
					$ele.append($('<li>').text(
							String.format('{0}    投注人数：{1}人 ，投注金额：{2}元',
									cur.childName,
									cur.distinctUserCount,
									(cur.orderAmount).toFixed(2)
							)
					));
				});
				// 加载表格数据
				mini.get('lotteryChildGrid').load(param);
			});
		} else {
			// 设置数据
			option.series[0].data = null;
			option.series[1].data = null;
			option.series[2].data = null;
			// 设置X轴
			option.xAxis[0].data = null;
			// 设置Y轴
			option.yAxis[0].max = null
			//option.yAxis[0].interval = Math.ceil(max/10);
			option.yAxis[1].max = null;
			//option.yAxis[1].interval = Math.ceil(max/10);
			chart.setOption(option);
			// 更新图表右边的列表
			// 任一    投注人数：6000人 ，投注金额：60万
			$ele.empty();
			// 清空表格
			// 设置表头
			var grid =mini.get('lotteryChildGrid');
			// 清除所有行
			grid.clearRows();
			var columns = [];
			// 日期列
			columns.push({ field: "dt", headerAlign: "center", header: "日期",align:"center"});
			// “所有”列
			columns.push({
				header: "所有",
				headerAlign: "center",
				columns: [
				          { field: "totalOrderUser", headerAlign: "center", header: "投注人数",align:"right"},
				          { field: "totalOrderMoney", headerAlign: "center", header: "投注金额",align:"right"},
				          { field: "totalArpu", headerAlign: "center", header: "arpu值",align:"right"}
				          ]
			});
			grid.set({
				columns: columns
			});
		}
		
	},
	updateWinning : function(param) {
			$.get("report/lottery/winning",
					param,
					function(data) {
				if(data.errorCode && (data.errorCode != Code.success)) {
					console.error(data.message);
					return;
				}
				
				var $ele,chart,option,max,max0,max1,x0Data=[],x1Data=[],x2Data=[],x0=[];
				
				// 更新"返奖数据"
				chart = report_lottery.winningChart;
				option = chart.getOption();
				// 设置X轴
				x0Data=[],x1Data=[],x2Data=[],x0=[];
				data.forEach(function(record) {
					x0.push(record.lotteryName || record.childName);
					x0Data.push(record.orderAmount);
					x1Data.push(record.winningAmount);
					// 返奖率 = 中奖金额/投注金额 * 100%
					x2Data.push(record.orderAmount == 0 ? 0 : (record.winningAmount/record.orderAmount*100).toFixed(2));
				});
				option.xAxis[0].data = x0;
				option.series[0].data = x0Data;
				option.series[1].data = x1Data;
				option.series[2].data = x2Data;
				max0 = Math.max.apply(null, x0Data);
				max1 = Math.max.apply(null, x1Data);
				max =  max0 > max1 ? max0 : max1;
				option.yAxis[0].max = max
				option.yAxis[0].interval = Math.ceil(max/10);
				max = Math.max.apply(null, x2Data);
				option.yAxis[1].max = max;
				option.yAxis[1].interval = Math.ceil(max/10);
				chart.setOption(option);
				// 更新图表右边的列表
				// 任一    投注金额：600万元 ，中奖金额：60万，返奖率：80%
//				$ele = $('#winningList ul');
				$ele = $('#winningList table');
				$ele.empty();
				var content;
				data.forEach(function(cur, index, array) {
					if(index==0){
						content='<tr><td>玩法</td><td>投注金额</td><td>中奖金额</td><td>返奖率</td></tr>';
						$ele.append(content);
					}
					content = '<tr  align="right"><td>'+(cur.lotteryName || cur.childName)+'</td>'+
					'<td>'+((cur.orderAmount).toFixed(2))+'</td>'+
					'<td>'+((cur.winningAmount).toFixed(2))+'</td>'+
					'<td>'+(cur.orderAmount == 0 ? 0 : (cur.winningAmount/cur.orderAmount*100).toFixed(2))+'%</td></tr>';
					$ele.append(content);		
				});
				// 更新对应的表格
				mini.get('winningGrid').load(param);
			});
	},
	updateLotteryWay : function(param) {
		$.get("report/lottery/way",
				param,
				function(data) {
			if(data.errorCode && (data.errorCode != Code.success)) {
				console.error(data.message);
				return;
			}
			// 投注金额
			var chart = report_lottery.lotteryMoneyChart;
			var option = chart.getOption();
	        var lotteryWays = [];
	        lotteryWays.push({value:data.buyMoney, name:'代购'});
	        lotteryWays.push({value:data.groupMoney, name:'合买'});
	        lotteryWays.push({value:data.addMoney, name:'追号'});
			option.series[0].data = lotteryWays;
			chart.setOption(option);
			// 投注人数
			chart = report_lottery.lotteryUserChart;
			option = chart.getOption();
			lotteryWays = [];
			lotteryWays.push({value:data.buyUser, name:'代购'});
			lotteryWays.push({value:data.groupUser, name:'合买'});
			lotteryWays.push({value:data.addUser, name:'追号'});
			option.series[0].data = lotteryWays;
			chart.setOption(option);
			
			// 更新图表右边的列表
			// 代购   投注人数：6000人，投注金额：60万
			var $ele = $('#lotteryWayList ul');
			$ele.empty();
			$ele.append($('<li>').text(String.format('代购   投注人数：{0}人，投注金额：{1}元',data.buyUser||0,data.buyMoney||0)))
			.append($('<li>').text(String.format('合买   投注人数：{0}人，投注金额：{1}元',data.groupUser||0,data.groupMoney||0)))
			.append($('<li>').text(String.format('追号  投注人数：{0}人，投注金额：{1}元',data.addUser||0,data.addMoney||0)));
			// 更新"投注方式"的表格
			mini.get('lotteryWayGrid').load(param);
		});
	},
	// 设置"玩法分布"表格的表头
	setLotteryChildHeader : function(sender) {
		// 设置表头
		var grid =mini.get(sender.source.id);
		var childNames = sender.result.other;
		if(!childNames){return;}
		var i;
		var columns = [];
		// 日期列
		columns.push({ field: "dt", headerAlign: "center", header: "日期",align:"center"});
		// “所有”列
		columns.push({
			header: "所有",
			headerAlign: "center",
		    columns: [
		        { field: "totalOrderUser", headerAlign: "center", header: "投注人数",align:"right"},
		        { field: "totalOrderMoney", headerAlign: "center", header: "投注金额",align:"right"},
		        { field: "totalArpu", headerAlign: "center", header: "arpu值",align:"right"}
		    ]
		});
		childNames.forEach(function(child, i) {
			columns.push({
				header: child,
				headerAlign: "center",
			    columns: [
			        { field: "orderUser"+i, headerAlign: "center", header: "投注人数",align:"right"},
			        { field: "orderMoney"+i, headerAlign: "center", header: "投注金额",align:"right"},
			        { field: "arpu"+i, headerAlign: "center", header: "arpu值",align:"right"}
			    ]
			});
		});
		grid.set({
	        columns: columns
	    });
	},
	// 设置"返奖数据"表格的表头
	setWinningHeader : function(sender) {
		// 设置表头
		var grid =mini.get(sender.source.id);
		var includeNames = sender.result.other;
		if(!includeNames){return;}
		var i;
		var columns = [];
		// 日期列
		columns.push({ field: "dt", headerAlign: "center", header: "日期",align:"center"});
		// “所有”列
		columns.push({
			header: "所有",
			headerAlign: "center",
			columns: [
			          { field: "totalOrderMoney", headerAlign: "center", header: "投注金额",align:"right"},
			          { field: "totalWinningMoney", headerAlign: "center", header: "中奖金额",align:"right"},
			          { field: "totalWinningRatio", headerAlign: "center", header: "返奖率",align:"right"}
			          ]
		});
		includeNames.forEach(function(child, i) {
			columns.push({
				header: child,
				headerAlign: "center",
				columns: [
				          { field: "orderMoney"+i, headerAlign: "center", header: "投注金额",align:"right"},
				          { field: "winningMoney"+i, headerAlign: "center", header: "中奖金额",align:"right"},
				          { field: "winningRatio"+i, headerAlign: "center", header: "返奖率",align:"right"}
				          ]
			});
		});
		grid.set({
			columns: columns
		});
	},
	// 投注人数
	renderOrderUserChart : function() {
		report_lottery.orderUserChart = echarts.init(document.getElementById('orderUserChart'));
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
			        data:['投注人数', '人数占比']
			    },
			    xAxis: [
			        {
			            type: 'category',
			            data: ['1-100元','100-500元','500-1000元','1000-5000元','5000-10000元','10000-50000元','50000-100000元','10万元以上'],
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
			            max: 100,
			            interval: 5,
			            axisLabel: {
			                formatter: '{value}%'
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
			            name:'人数占比',
			            type:'line',
			            yAxisIndex: 1,
			            //data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
			        }
			    ]
			};
		// 投注用户分布
		report_lottery.orderUserChart = echarts.init(document.getElementById('orderUserChart'));
		report_lottery.orderUserChart.setOption(option);
	},
	
	// 投注金额
	renderOrderMoneyChart : function() {
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
					data:['投注金额', '金额占比']
				},
				
				xAxis: [
				        {
				        	type: 'category',
				        	data: ['1-100元','100-500元','500-1000元','1000-5000元','5000-10000元','10000-50000元','50000-100000元','10万元以上'],
				        	axisPointer: {
				        		type: 'shadow'
				        	},
				        	axisLabel :{  
							    interval:0   
							},
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
				                		formatter: '{value}%'
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
				                        	 name:'金额占比',
				                        	 type:'line',
				                        	 yAxisIndex: 1,
				                        	 //data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
				                         }
				                         ]
		};
		// 投注用户分布
		report_lottery.orderMoneyChart = echarts.init(document.getElementById('orderMoneyChart'));
		report_lottery.orderMoneyChart.setOption(option);
	},
	
	// 玩法分布
	renderLotteryChild : function() {
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
			        data:['投注人数','投注金额','ARUP值']
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
			            name:'投注金额',
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
		report_lottery.lotteryChildChart = echarts.init(document.getElementById('lotteryChildChart'));
		report_lottery.lotteryChildChart.setOption(option);
	},
	
	// 投注方式
	renderLotteryWay : function() {
		var option = {
			    title : {
			        text: '投注金额',
//			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'right',
			        data: ['代购','合买','追号']
			    },
			    series : [
			        {
			            name: '投注金额',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};
		
		var option1 = {
			    title : {
			        text: '投注人数',
//			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'right',
			        data: ['代购','合买','追号']
			    },
			    series : [
			        {
			            name: '投注人数',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};
		report_lottery.lotteryMoneyChart = echarts.init(document.getElementById('lotteryMoneyChart'));
		report_lottery.lotteryUserChart = echarts.init(document.getElementById('lotteryUserChart'));
		report_lottery.lotteryMoneyChart.setOption(option);
		report_lottery.lotteryUserChart.setOption(option1);
	},
	
	// 返奖数据
	renderWinning : function() {
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
			        data:['投注金额','中奖金额','返奖率']
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
			            name:'中奖金额',
			            type:'bar',
			            //data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
			        },
			        {
			            name:'返奖率',
			            type:'line',
			            yAxisIndex: 1,
			            //data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
			        }
			    ]
			};
		report_lottery.winningChart = echarts.init(document.getElementById('winningChart'));
		report_lottery.winningChart.setOption(option);
	}
};

