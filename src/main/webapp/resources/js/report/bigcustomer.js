$(function(){
	bigCustomer.init();
});  

bigCustomer={
    init:function(){
        mini.parse();
        // 明细数据
        bigCustomer.platformDatagrid = mini.get("detailDatagrid");

        // 图表初始化
        bigCustomer.betUserCountChart = echarts.init(document.getElementById("betUserCountChart"));
        bigCustomer.betMoneyChart = echarts.init(document.getElementById("betMoneyChart"));
        bigCustomer.platformSaleChart = echarts.init(document.getElementById("platformSaleChart"));
        bigCustomer.lotMoneyChart = echarts.init(document.getElementById("lotMoneyChart"));
        bigCustomer.lotUserCountChart = echarts.init(document.getElementById("lotUserCountChart"));
       
        // 点击查询条件时，改变样式
    	$("#statis-time a, #statis-channel a, #statis-lot a").click(function(){
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
    	
    	// 点击选项时，把彩种空
    	$("#statis-lot a").click(function(){
    		mini.get('lotteryType').setValue(null);
    		mini.get('lotteryCode').setValue(null);
    	});

        // 彩种下拉框
    	ComReq.dictionary("0303", function(result){
		     mini.get('lotteryType').setData(result["0303"]);
		});
    	ComReq.lottery("",function(result){
			mini.get('lotteryCode').setData(result);
		});
    	
        // 查询
        bigCustomer.search();
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
	},
	addRateCell : function(e){
        var value = e.value;
        return value + '%';
	},
	addCell : function(e){
        var value = e.value;
        return value + '倍';
	},
	excel:function() {
		var param = bigCustomer.getSearchParam();
		if(!param){
			mini.alert("请选择时间！");
			return;
		}
		var url = "bigcustomer/bigCustomerDataExcel?";
		var requestParamStr = "channel="+param.channel+"&maxMoney="+param.maxMoney;
		if(typeof(param.timeType)=="undefined")
			requestParamStr += "&startTime="+param.startTime+"&endTime="+param.endTime;
		else
			requestParamStr += "&timeType="+param.timeType;
		url = url + requestParamStr;
		location = url;
	},
	getSearchParam:function(){
		var param = {};
		// 注册渠道
		param.channel = mini.get('channel').getValue();
		// 时间段
		param.timeType = $('#statis-time a.active').data('time');
		param.lotteryCode = mini.get('lotteryCode').getValue() || $('#statis-lot a.active').data('lot');
		var startTime = mini.get("startTime").getFormValue();
		var endTime = mini.get("endTime").getFormValue();
		param.maxMoney = mini.get('maxMoney').getValue();
		if(typeof(param.timeType)=="undefined"){
			if(!startTime || !endTime){
				return false;
			}else{
				var time = " 00:00:00";
				startTime = startTime + time;
				endTime = endTime + time;
			}
		}
		param.startTime = startTime;
		param.endTime =  endTime;
		return param;
	},
	search:function(){
		var param = bigCustomer.getSearchParam();
		if(!param){
			mini.alert("请选择时间！");
			return;
		}
		
		if(param.maxMoney < 2000){
			mini.alert("投注金额不能小于2000！");
			return;
		}
		
		// 加载图表数据
		$.get("bigcustomer/findStatistics",
				param,
				function(data) {
					// 投注人数
					bigCustomer.betUserCountData(data);
					// 投注金额
					bigCustomer.betMoneyData(data);
					
			}
		);
		
		// 投注平台销量图表数据
		$.get("bigcustomer/findPlatformSale",
				param,
				function(data) {
				// 投注平台
				bigCustomer.platformSaleMoneyData(data);
			}
		);
		
		if(param.lotteryCode){
			$("#alertMsg").show();
			// 彩种销量
			bigCustomer.lotSaleMoneyData([],[]);
			// 彩种投注人数
			bigCustomer.lotBuyUserData([],[]);
			
		}else{
			$("#alertMsg").hide();
			// 彩种销量图表数据
			$.get("bigcustomer/findLotSale",
					param,
					function(data) {
						var lots = new Array();
						var values = new Array();
						$.each(data,function(i,item){
							var o = new Object();
							o.value= item.orderMoney;
							o.name=item.lotteryName;
							lots[i] = item.lotteryName;
							values[i] = o;
						});
					// 彩种销量
					bigCustomer.lotSaleMoneyData(lots,values);
				}
			);
			
			// 彩种投注人数图表数据
			$.get("bigcustomer/findLotBetCount",
					param,
					function(data) {
						var lots = new Array();
						var values = new Array();
						$.each(data,function(i,item){
							lots[i] = item.lotteryName;
							var o = new Object();
							o.value= item.orderUser;
							o.name=item.lotteryName;
							values[i] = o;
						});
					// 彩种投注人数
					bigCustomer.lotBuyUserData(lots,values);
				}
			);
		}
		
		
		bigCustomer.platformDatagrid.load(param);
	},
	//投注人数
	betUserCountData:function(data){
		var option = {
			    title : {
			        text: '投注人数',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			        data: ['大客户','非大客户']
			    },
			    series : [
			        {
			            name: '访问来源',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:[
			                  {value:data==""?0:data.bigBetUserCount, name:'大客户'},
				              {value:data==""?0:data.noBigBetUserCount, name:'非大客户'}
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
		bigCustomer.betUserCountChart.setOption(option);
	},
	//投注金额
	betMoneyData:function(data){
		var option = {
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
			        data: ['大客户','非大客户']
			    },
			    series : [
			        {
			            name: '访问来源',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:[
			                  {value:data==""?0:data.bigOrderMoney, name:'大客户'},
				              {value:data==""?0:data.noBigOrderMoney, name:'非大客户'}
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
		bigCustomer.betMoneyChart.setOption(option);
	},
	//投注平台
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
			                  {value:data==""?0:data.webOrderMoney, name:'Web'},
				              {value:data==""?0:data.wapOrderMoney, name:'Wap'},
				              {value:data==""?0:data.iosOrderMoney, name:'IOS'},
				              {value:data==""?0:data.androidOrderMoney, name:'Android'}
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
		bigCustomer.platformSaleChart.setOption(platformSaleOption);
	},
	//彩种数据
	lotSaleMoneyData:function(lots,values){
		var lotSaleOption = {
			    title : {
			        text: '投注额(排名统计)',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			    	orient : 'vertical',
			    	y:'top',
			    	x:'left',
			        data:lots
			    },
			    series : [
			        {
			            type: 'pie',
			            radius : '50%',
			            center: ['50%', '50%'],
			            data:values,
			            itemStyle: {
			                normal: {
				                label:{  
					                show:true,
					                formatter:'{b} : {c} ({d}%)'  
					            },  
				            labelLine:{show:true}}
			            }
			        }
			    ]
			};
		bigCustomer.lotMoneyChart.setOption(lotSaleOption);
	},
	lotBuyUserData:function(lots,values){
		var option = {
			    title : {
			        text: '投注人数(排名统计)',
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
			            data:lots
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
			            data:values
			        }
			    ]
			};
        bigCustomer.lotUserCountChart.setOption(option);
	}
};