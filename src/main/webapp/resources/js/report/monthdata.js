$(function(){
	monthData.init();
});  

monthData={
    init:function(){
        mini.parse();
        // 明细数据
        monthData.platformDatagrid = mini.get("platformDatagrid");
        monthData.lotDatagrid = mini.get("lotDatagrid");
        monthData.buyUserCountDatagrid = mini.get("buyUserCountDatagrid");
        monthData.buyMoneyDatagrid = mini.get("buyMoneyDatagrid");
        monthData.registerDatagrid = mini.get("registerDatagrid");

        // 图表初始化
        monthData.platformSaleMoneyChart = echarts.init(document.getElementById("platformSaleMoneyChart"));
        monthData.platformBuyCountChart = echarts.init(document.getElementById("platformBuyCountChart"));
        monthData.lotSaleMoney = echarts.init(document.getElementById("lotSaleMoney"));
        monthData.lotSaleCount = echarts.init(document.getElementById("lotSaleCount"));
        monthData.buyUserCountChart = echarts.init(document.getElementById("buyUserCountChart"));
        monthData.buyMoneyChart = echarts.init(document.getElementById("buyMoneyChart"));
        monthData.registerChart = echarts.init(document.getElementById("registerChart"));
        monthData.newAddChart = echarts.init(document.getElementById("newAddChart"));
        
        // 默认数据
        monthData.showType = 1;// 1图表数据，2明细数据
        monthData.showData = 1;// 1平台数据，2彩种数据，3投注人数，4投注金额，5注册与有效，6沉默用户
        
        // 点击查询条件时，改变样式
    	$("#statis-time a, #statis-channel a").click(function(){
    		$(this).siblings().removeClass("active");
			$(this).toggleClass("active");
		});
    	
    	// 点击选项时，把渠道下拉框置空
    	$("#statis-channel a").click(function(){
    		mini.get('channel').setValue(null);
		});
    	// 点击选项时，把时间空
    	$("#statis-time a").click(function(){
    		mini.get('startTime').setValue(null);
    		mini.get('endTime').setValue(null);
    	});

        // 选择图表数据或明显数据
        $('input[name="chartDetailData"]').click(function(){
        	if($(this).val() == 1){
        		$("#platformDataTable").hide();
    			$("#lotSaleDataTable").hide();
    			$("#buyUserCountTable").hide();
    			$("#buyMoneyTable").hide();
    			$("#registerTable").hide();
        		
    			$("#platformChart").show();
    			$("#lotDataChart").show();
    			$("#buyUserCount").show();
    			$("#buyMoney").show();
    			$("#registerDataChart").show();
//    			$("#siclientUserChart").show();
    			
    			$("#excelBtn").hide();
    			monthData.showType = 1;// 1图表数据，2明细数据
        	}else if($(this).val() == 2){
        		$("#platformDataTable").show();
    			$("#lotSaleDataTable").show();
    			$("#buyUserCountTable").show();
    			$("#buyMoneyTable").show();
    			$("#registerTable").show();
        		
    			$("#platformChart").hide();
    			$("#lotDataChart").hide();
    			$("#buyUserCount").hide();
    			$("#buyMoney").hide();
    			$("#registerDataChart").hide();
//    			$("#siclientUserChart").hide();

    			$("#excelBtn").show();
    			monthData.showType = 2;// 1图表数据，2明细数据
        	}
        	mini.parse();
        });
        // 查询
        monthData.search();
    },
 // 当选择下拉或自定义时间时，清除选项清单样式
	clearActive : function(id) {
		$('#'+id).children().removeClass("active");
	},
	addRateCell : function(e){
        var value = e.value;
        return value + '%';
	},
	activechanged:function(e){
		// 1平台数据，2彩种数据，3投注人数，4投注金额，5注册与有效，6沉默用户
		if(e.tab.name == "platformTab")
			monthData.showData = 1;
		else if(e.tab.name == "lotTab")
			monthData.showData = 2;
		else if(e.tab.name == "buyUserCountTab")
			monthData.showData = 3;
		else if(e.tab.name == "buyUserMoneyTab")
			monthData.showData = 4;
		else if(e.tab.name == "registerTab")
			monthData.showData = 5;
//		else if(e.tab.name == "silentUserTab")
//			monthData.showData = 6;
		else
			monthData.showData = 0;
	},
	excel:function() {
		// 1注册与有效，2新老用户，3沉默用户
		if(monthData.showType == 2){
			var url = "";
			if(monthData.showData == 1){
				url = "monthdata/platformDataExcel?";
			}
			else if(monthData.showData == 2){
				url = "monthdata/lotDataExcel?";
			}
			else if(monthData.showData == 3){
				url = "monthdata/betUserDataExcel?";
			}
			else if(monthData.showData == 4){
				url = "monthdata/betMoneyDataExcel?";
			}
			else if(monthData.showData == 5){
				url = "monthdata/registerUserDataexcel?";
			}
//			else if(monthData.showData == 6){
//				url = "monthdata/silentUserExcel?";
//			}
			else{
				mini.alert("当前没有需要导出的明细数据！");
				return;
			}
			var param = monthData.getSearchParam();
			if(!param){
				mini.alert("请选择时间！");
				return;
			}
			var requestParamStr = "";
			if(typeof(param.timeType)=="undefined")
				requestParamStr = "channel="+param.channel+"&startTime="+param.startTime+"&endTime="+param.endTime;
			else
				requestParamStr = "channel="+param.channel+"&timeType="+param.timeType;
			url = url + requestParamStr;
			location = url;
		}
	},
	getSearchParam:function(){
		var param = {};
		// 注册渠道
		param.channel = mini.get('channel').getValue();
		// 时间段
		param.timeType = $('#statis-time a.active').data('time');
		var startTime = mini.get("startTime").getFormValue();
		var endTime = mini.get("endTime").getFormValue();
		if(typeof(param.timeType)=="undefined"){
			if(!startTime || !endTime){
				return false;
			}else{
				var time = "-01 00:00:00";
				startTime = startTime + time;
				endTime = endTime + time;
			}
		}
		param.startTime = startTime;
		param.endTime =  endTime;
		return param;
	},
	search:function(){
		var param = monthData.getSearchParam();
		if(!param){
			mini.alert("请选择时间！");
			return;
		}
		
		// 加载用户注册与有效图表数据
		$.get("monthdata/findRegisterSum",
				param,
				function(data) {
					// 注册用户
					var registerData =  [data.newUserCount,data.perfectUserCount,data.firstRechargeCount,data.firstBuyCount];
					monthData.userMoney = [data.firstBuyMoney,data.firstRechargeMoney];
					monthData.refreshRegisterData(registerData);
			}
		);
		
		// 加载平台销量数据图表数据
		$.get("monthdata/findPlatformSumMoney",
				param,
				function(data) {
					// 注册用户
					monthData.platformSaleMoneyData(data);
			}
		);
		
		// 加载平台投注人数图表数据
		$.get("monthdata/findPlatformUserCount",
				param,
				function(data) {
					// 注册用户
					monthData.platformBuyUserData(data);
			}
		);
		
		// 加载彩种投注人数图表数据
		$.get("monthdata/selectLotUserSum",
				param,
				function(data) {
					// 彩种数据
					monthData.lotBuyUserData(data);
			}
		);
		
		
		// 加载彩种投注人数图表数据
		$.get("monthdata/selectLotBetMoneySum",
				param,
				function(data) {
					// 彩种数据
					monthData.lotSaleMoneyData(data);
			}
		);
		
		// 加载投注金额图表数据
		$.get("monthdata/findBetDataSum",
				param,
				function(data) {
					// 投注人数
					monthData.buyUserCountData(data);
					$("#100_count").text(data.u_1_100);
					$("#500_count").text(data.u_101_500);
					$("#1000_count").text(data.u_501_1000);
					$("#5000_count").text(data.u_1001_5000);
					$("#10000_count").text(data.u_5001_10000);
					$("#50000_count").text(data.u_10001_50000);
					$("#100000_count").text(data.u_50001_100000);
					$("#100000_n_count").text(data.u_100000);
					
					// 投注金额
					monthData.userBuyMoneyData(data);
					$("#100_money").text(data.o_1_100/10000);
					$("#500_money").text(data.o_101_500/10000);
					$("#1000_money").text(data.o_501_1000/10000);
					$("#5000_money").text(data.o_1001_5000/10000);
					$("#10000_money").text(data.o_5001_10000/10000);
					$("#50000_money").text(data.o_10001_50000/10000);
					$("#100000_money").text(data.o_50001_100000/10000);
					$("#100000_n_money").text(data.o_100000/10000);
			}
		);
		
		// 加载平台新增有效图表数据
		$.get("monthdata/findNewUserBetMoneySum",
				param,
				function(data) {
					// 更新投注数据
				$("#100_new_money").text(data.o_1_100/10000);
				$("#500_new_money").text(data.o_101_500/10000);
				$("#1000_new_money").text(data.o_501_1000/10000);
				$("#5000_new_money").text(data.o_1001_5000/10000);
				$("#10000_new_money").text(data.o_5001_10000/10000);
				$("#50000_new_money").text(data.o_10001_50000/10000);
				$("#100000_new_money").text(data.o_50001_100000/10000);
				$("#100000_n_new_money").text(data.o_100000/10000);
				$("#100_new_count").text(data.u_1_100);
				$("#500_new_count").text(data.u_101_500);
				$("#1000_new_count").text(data.u_501_1000);
				$("#5000_new_count").text(data.u_1001_5000);
				$("#10000_new_count").text(data.u_5001_10000);
				$("#50000_new_count").text(data.u_10001_50000);
				$("#100000_new_count").text(data.u_50001_100000);
				$("#100000_n_new_count").text(data.u_100000);
			}
		);
		
		// 加载平台新增有效图表数据
		$.get("monthdata/findPlatformUserSum",
				param,
				function(data) {
					// 平台新增有效
					monthData.newAddUserData(data);
			}
		);

		// 加载沉默用户图表数据
//		$.get("monthdata/findSilentUserSum",
//				param,
//				function(data) {
//					monthData.refreshNoUserChart(data);
//			}
//		);
		
		monthData.platformDatagrid.load(param);
        monthData.lotDatagrid.load(param);
        monthData.buyUserCountDatagrid.load(param);
        monthData.buyMoneyDatagrid.load(param);
        monthData.registerDatagrid.load(param);
	},
	//平台报表
	platformSaleMoneyData:function(data){
		var platformSaleOption = {
			    title : {
			        text: '平台销量数据',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			        data: ['Web','Wap','IOS','Android']
			    },
			    series : [
			        {
			            name: '访问来源',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:[
			                  {value:data.webBetMoney, name:'Web'},
				              {value:data.wapBetMoney, name:'Wap'},
				              {value:data.iosBetMoney, name:'IOS'},
				              {value:data.androidBetMoney, name:'Android'}
			            ],
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
				            labelLine:{show:true}}
			            }
			        }
			    ]
			};
		monthData.platformSaleMoneyChart.setOption(platformSaleOption);
	},
	platformBuyUserData:function(data){
		var option = {
			    title : {
			        text: '平台投注人数',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            data : ['Web','Wap','IOS','Android']
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'投注人数',
			            type:'bar',
			            data:[data.webBetUserCount,data.wapBetUserCount, data.iosBetUserCount, data.androidBetUserCount]
			        }
			    ]
			};
		monthData.platformBuyCountChart.setOption(option);
	},
	//彩种数据
	lotSaleMoneyData:function(data){
		var lotSaleOption = {
			    title : {
			        text: '投注金额',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			        data: ['竞彩足球','双色球','大乐透','竞彩篮球','北京单场','十一运夺金','重庆时时彩','江苏快3']
			    },
			    series : [
			        {
			            name: '访问来源',
			            type: 'pie',
			            radius : '50%',
			            center: ['50%', '50%'],
			            data:[
			                  {value:data.jzBetMoney,name:"竞彩足球"},{value:data.ssqBetMoney,name:"双色球"},{value:data.dltBetMoney,name:"大乐透"},{value:data.jlBetMoney,name:"竞彩篮球"},
			                	  {value:data.bdBetMoney,name:"北京单场"},{value:data.syydjBetMoney,name:"十一运夺金"},{value:data.cqsscBetMoney,name:"重庆时时彩"},{value:data.jsk3BetMoney,name:"江苏快3"}
			            ],
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
				            labelLine:{show:true}}
			            }
			        }
			    ]
			};
		monthData.lotSaleMoney.setOption(lotSaleOption);
	},
	lotBuyUserData:function(data){
		var option = {
			    title : {
			        text: '投注人数',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {
			            	show : true,
			                title : '数据视图',
			                readOnly: true,
			                lang : ['数据视图', '关闭', '刷新'],
			                optionToContent: function(opt) {
			                    var axisData = opt.xAxis[0].data;
			                    var series = opt.series;
			                    var table = '<table style="width:60%;text-align:center"><tbody><tr>'
			                                 + '<td>彩种</td>'
			                                 + '<td>' + series[0].name + '</td>'
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
			            magicType : {show: true, type: ['bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            data : ['竞彩足球','双色球','大乐透','竞彩篮球','北京单场','十一运夺金','重庆时时彩','江苏快3']
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'投注人数',
			            type:'bar',
			            data:[data.jzBetUserCount,data.ssqBetUserCount,data.dltBetUserCount,data.jlBetUserCount,
			                  data.bdBetUserCount,data.syydjBetUserCount,data.cqsscBetUserCount,data.jsk3BetUserCount]
			        }
			    ]
			};
        monthData.lotSaleCount.setOption(option);
	},
	//投注人数
	buyUserCountData:function(data){
		var sum = data.u_1_100+data.u_101_500+data.u_501_1000+data.u_1001_5000+data.u_5001_10000+data.u_10001_50000+
        				data.u_50001_100000+data.u_100000;
		var option = {
			    title : {
			        text: '投注人数',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			    	y:'bottom',
			        data:['投注人数','人数占比']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {
			            	show : true,
			                title : '数据视图',
			                readOnly: true,
			                lang : ['数据视图', '关闭', '刷新'],
			                optionToContent: function(opt) {
			                    var axisData = opt.xAxis[0].data;
			                    var series = opt.series;
			                    var table = '<table style="width:60%;text-align:center"><tbody><tr>'
			                                 + '<td>投注金额范围</td>'
			                                 + '<td>' + series[0].name + '</td>'
			                                 + '<td>' + series[1].name + '</td>'
			                                 + '</tr>';
			                    for (var i = 0, l = axisData.length; i < l; i++) {
			                        table += '<tr>'
			                                 + '<td>' + axisData[i] + '</td>'
			                                 + '<td>' + series[0].data[i] + '</td>'
			                                 + '<td>' + series[1].data[i] + '</td>'
			                                 + '</tr>';
			                    }
			                    table += '</tbody></table>';
			                    return table;
			                }
			            },
			            magicType : {show: true, type: ['line','bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            data : ['1-100元','101-500元','501-1000元','1001-5000元','5001-10000元','10001-50000元','50001-100000元','10万元以上']
			        }
			    ],
			    yAxis : [
			        {
			        	type:'value'
	                },
	                {
	                    type:'value',
	                    axisLabel : {
	                        formatter: '{value}%'
	                    }
	                }
			    ],
			    series : [
			        {
			            name:'投注人数',
			            type:'bar',
			            data:[data.u_1_100,data.u_101_500,data.u_501_1000,data.u_1001_5000,data.u_5001_10000,data.u_10001_50000,
			                  data.u_50001_100000,data.u_100000]
			        },
			        {
			            name:'人数占比',
			            type:'line',
			            itemStyle:{
			                normal: {
			                    label : {
			                        show: true,
			                        formatter: '{c}%'
			                    }
			                }
			            },
			            yAxisIndex: 1,
			            data:[(data.u_1_100/sum*100).toFixed(2),(data.u_101_500/sum*100).toFixed(2),(data.u_501_1000/sum*100).toFixed(2),(data.u_1001_5000/sum*100).toFixed(2),(data.u_5001_10000/sum*100).toFixed(2),(data.u_10001_50000/sum*100).toFixed(2),
			                  (data.u_50001_100000/sum*100).toFixed(2),(data.u_100000/sum*100).toFixed(2)]
			        }
			    ]
			};
        monthData.buyUserCountChart.setOption(option);
	},
	//投注金额
	userBuyMoneyData:function(data){
		var sum = data.o_1_100+data.o_101_500+data.o_501_1000+data.o_1001_5000+data.o_5001_10000
							+ data.o_10001_50000+data.o_50001_100000+data.o_100000;
		var option = {
			    title : {
			        text: '投注金额',
			        x:'center'
			    },
			    tooltip:{
		            trigger: 'axis',
		            axisPointer: {
		                type: 'cross',
		                crossStyle: {
		                    color: '#999'
		                }
		            }
		        },
			    legend: {
			    	y:'bottom',
			        data:['投注金额','金额占比']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {
			            	show : true,
			                title : '数据视图',
			                readOnly: true,
			                lang : ['数据视图', '关闭', '刷新'],
			                optionToContent: function(opt) {
			                    var axisData = opt.xAxis[0].data;
			                    var series = opt.series;
			                    var table = '<table style="width:60%;text-align:center"><tbody><tr>'
			                                 + '<td>投注金额范围</td>'
			                                 + '<td>' + series[0].name + '</td>'
			                                 + '<td>' + series[1].name + '</td>'
			                                 + '</tr>';
			                    for (var i = 0, l = axisData.length; i < l; i++) {
			                        table += '<tr>'
			                                 + '<td>' + axisData[i] + '</td>'
			                                 + '<td>' + series[0].data[i] + '</td>'
			                                 + '<td>' + series[1].data[i] + '</td>'
			                                 + '</tr>';
			                    }
			                    table += '</tbody></table>';
			                    return table;
			                }
			            },
			            magicType : {show: true, type: ['line','bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            data : ['1-100元','101-500元','501-1000元','1001-5000元','5001-10000元','10001-50000元','50001-100000元','10万元以上'],
			            axisPointer:{
			                type:'shadow'
			            }
			        }
			    ],
			    yAxis : [
			        {
			        	type:'value'
	                },
	                {
	                    type:'value',
	                    axisLabel : {
	                        formatter: '{value}%'
	                    }
	                }
			    ],
			    series : [
			        {
			            name:'投注金额',
			            type:'bar',
			            data:[data.o_1_100,data.o_101_500,data.o_501_1000,data.o_1001_5000,data.o_5001_10000,data.o_10001_50000,
			                  data.o_50001_100000,data.o_100000]
			        },
			        {
			            name:'金额占比',
			            type:'line',
			            itemStyle:{ 
			                normal:{
			                    label:{
			                        show: true,
			                        formatter: '{c}%'
			                    }
			                }
			            },
			            yAxisIndex: 1,
			            data:[(data.o_1_100/sum*100).toFixed(2),(data.o_101_500/sum*100).toFixed(2),(data.o_501_1000/sum*100).toFixed(2),(data.o_1001_5000/sum*100).toFixed(2),(data.o_5001_10000/sum*100).toFixed(2),(data.o_10001_50000/sum*100).toFixed(2),
			                  (data.o_50001_100000/sum*100).toFixed(2),(data.o_100000/sum*100).toFixed(2)]
			        }
			    ]
			};
		monthData.buyMoneyChart.setOption(option);
	},
	// 注册与有效图表
	refreshRegisterData:function(registerData){
		var registerOption = {
			    tooltip: {
			        trigger: 'axis'
			    },
			    xAxis: [{
			        type: 'value',
			        boundaryGap:  [0, 0.01]
			    }],
			    yAxis: {
			        type: 'category',
			        data: ['注册人数','完善用户','首充用户','首投用户']
			    },
			    series: [
			        {
			            type: 'bar',
			            data: registerData,
			            itemStyle: {
			                normal:{
				                label:{
				                    show: true, 
				                    formatter: function(params){
				                    	var text = '';
				                    	if(params['name'] =='首投用户')
				                    		text = params['value'] + "人, " + monthData.userMoney[0] + "元";
				                    	else if(params['name'] =='首充用户')
				                    		text = params['value'] + "人, " + monthData.userMoney[1] + "元";
				                    	else	
				                    		text = params['value'];
				                    	return text;
				                    },
				                    position: 'right',
				                    textStyle:{
				                    	fontSize:15,
				                    	fontWeight:'bold',
				                    	color:"#333333"
				                    },
			                  }
			                }
			            },
			            barWidth : 30,//柱图宽度
			        }
			    ],
			    color:['#1e88d2']
			};
		monthData.registerChart.setOption(registerOption);
	},
	//注册新增有效
	newAddUserData:function(data){
		var option = {
			    title : {
			        text: '平台新增有效',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			        data: ['Web','Wap','IOS','Android']
			    },
			    series : [
			        {
			            name: '访问来源',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:[
			                  {value:data==""?0:data.webFirstRechargeCount, name:'Web'},
				              {value:data==""?0:data.wapFirstRechargeCount, name:'Wap'},
				              {value:data==""?0:data.iosFirstRechargeCount, name:'IOS'},
				              {value:data==""?0:data.androidFirstRechargeCount, name:'Android'}
			            ],
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
				            labelLine:{show:true}}
			            }
			        }
			    ]
			};
        monthData.newAddChart.setOption(option);
	}
};