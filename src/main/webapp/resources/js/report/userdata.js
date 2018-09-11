$(function(){
	userData.init();
});  

userData={
    init:function(){
        mini.parse();
        userData.registerDatagrid = mini.get("registerDatagrid");
        userData.newOrOldUserDatagrid = mini.get("newOrOldUserDatagrid");
        //userData.siclientUserDatagrid = mini.get("siclientUserDatagrid");
        
        // 图表初始化
        userData.myRegisterChart = echarts.init(document.getElementById('registerChart'));
        userData.myBetUserChart = echarts.init(document.getElementById('betUser'));
        userData.myBetAmountChart = echarts.init(document.getElementById('betAmount'));
        userData.mySilentUserChart = echarts.init(document.getElementById('noLoginUser'));
        userData.myNoBetUserChart = echarts.init(document.getElementById('noBetUser'));
        
        // 默认数据
        userData.showType = 1;// 1图表数据，2明细数据
        userData.showData = 1;// 1注册与有效，2新老用户，3沉默用户
        
//        // 渠道下拉框初始化
//        $.get("operatemgr/marketchannel/dic", function (data) {
//            data.shift();
//            mini.get("channel").setData(data);
//        });
        
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
    	
    	
//    	$.get("userdata/channelTree",function(data){
//    		mini.get("channel").loadList(data, 'id', 'pid');
//    	});

        // 选择图表数据或明显数据
        $('input[name="chartDetailData"]').click(function(){
        	if($(this).val() == 1){
    			$("#registerTable").hide();
    			$("#newOrOldUserTable").hide();
    			//$("#siclientUserTable").hide();
    			$("#registerChart").show();
    			$("#newOrOldUserChart").show();
    			$("#siclientUserChart").show();
    			$("#excelBtn").hide();
    			userData.showType = 1;// 1图表数据，2明细数据
        	}else if($(this).val() == 2){
        		$("#registerChart").hide();
    			$("#newOrOldUserChart").hide();
    			$("#siclientUserChart").hide();
    			$("#registerTable").show();
    			$("#newOrOldUserTable").show();
    			//$("#siclientUserTable").show();
    			$("#excelBtn").show();
    			userData.showType = 2;// 1图表数据，2明细数据
        	}
        	mini.parse();
        });
        // 查询
        userData.search();
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
		// 1注册与有效，2新老用户，3沉默用户
		if(e.tab.name == "registerTab")
			userData.showData = 1;
		else if(e.tab.name == "newOldUserTab")
			userData.showData = 2;
		else if(e.tab.name == "silentUserTab")
			userData.showData = 3;
		else
			userData.showData = 0;
	},
	excel:function() {
		// 1注册与有效，2新老用户，3沉默用户
		if(userData.showType == 2){
			var url = "";
			if(userData.showData == 1){
				url = "userdata/registerUserDataexcel?";
			}
			else if(userData.showData == 2){
				url = "userdata/newOldUserDataexcel?";
			}
//			else if(userData.showData == 3){
//				url = "userdata/silentUserDataexcel?";
//			}
			else{
				mini.alert("当前没有需要导出的明细数据！");
				return;
			}
			var param = userData.getSearchParam();
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
		var startTime = mini.get('startTime').getFormValue();
		var endTime = mini.get('endTime').getFormValue();
		if(typeof(param.timeType)=="undefined"){
			if(!startTime || !endTime)
			{
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
		var param = userData.getSearchParam();
		if(!param){
			mini.alert("请选择时间！");
			return;
		}
		// 注册与有效数据概况
		$.get("userdata/statistics",
				param,
				function(data) {
					// 刷新用户数据概况
					userData.loadRegisterTotalData(data);
					// 刷新新老用户投注图表数据
					var oldUserCount = data.buyUserCount- data.newUserCount;
					userData.refreshBetUserChart(data.newUserCount,oldUserCount);
				}
		);
		
		// 加载用户注册与有效图表数据
		$.get("userdata/findRegisterSum",
				param,
				function(data) {
					// 注册用户
					var registerData =  [data.newUserCount,data.perfectUserCount,data.firstRechargeCount,data.firstBuyCount];
					userData.userMoney = [data.firstBuyMoney,data.firstRechargeMoney];
					userData.refreshRegisterChart(registerData);
			}
		);
		
		// 加载新老用户投注金额图表数据
		$.get("userdata/findNewOldUserSum",
				param,
				function(data) {
					userData.refreshBetAmountChart(data);
			}
		);
		
		// 加载沉默用户图表数据
		$.get("userdata/findSilentUserSum",
				param,
				function(data) {
					userData.refreshNoUserChart(data);
			}
		);
		
		userData.registerDatagrid.load(param);
		userData.newOrOldUserDatagrid.load(param);
		//userData.siclientUserDatagrid.load(param);
	},
	loadRegisterTotalData:function(data){
		// 注册用户
		$('#registerUserCount').text(data.registerUserCount);
		$('#firstRechargeCount').text(data.firstRechargeCount);
		$('#firstRechargeRate').text(data.registerUserCount==0?"0.00":(data.firstRechargeCount/data.registerUserCount*100).toFixed(2));
		// 投注用户
		$('#buyUserCount').text(data.buyUserCount);
		$('#userBuyCount').text(data.userBuyCount);
		$('#buyMoney').text(data.buyMoney);
		$('#oneBuyCount').text(data.buyUserCount==0?"0":Math.floor(data.userBuyCount/data.buyUserCount));
		$('#buyArpu').text(data.buyUserCount==0?"0":Math.floor(data.buyMoney/data.buyUserCount));
		
		//新用户
		$('#newUserCount').text(data.newUserCount);
		$('#newUserBuyCount').text(data.newUserBuyCount);
		$('#newUserBuyMoney').text(data.newUserBuyMoney);
		$('#oneNewBuyCount').text(data.newUserCount==0?"0":Math.floor(data.newUserBuyCount/data.newUserCount));
		$('#newUserBuyArpu').text(data.newUserCount==0?"0":Math.floor(data.newUserBuyMoney/data.newUserCount));
		
		var oldUserCount = data.buyUserCount - data.newUserCount;
		var oldUserBuyCount = data.userBuyCount - data.newUserBuyCount;
		var oldUserBuyMoney = data.buyMoney - data.newUserBuyMoney;
		
		//老用户
		$('#oldUserCount').text(oldUserCount);
		$('#oldUserBuyCount').text(oldUserBuyCount);
		$('#oldUserBuyMoney').text(oldUserBuyMoney);
		$('#oneOldBuyCount').text(oldUserCount==0?"0":Math.floor(oldUserBuyCount/oldUserCount));
		$('#oldUserBuyArpu').text(oldUserCount==0?"0":Math.floor(oldUserBuyMoney/oldUserCount));
	},
	// 注册与有效图表
	refreshRegisterChart:function(registerData){
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
				                    		text = params['value'] + "人, " + userData.userMoney[0] + "元";
				                    	else if(params['name'] =='首充用户')
				                    		text = params['value'] + "人, " + userData.userMoney[1] + "元";
				                    	else	
				                    		text = params['value'] + "人";
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
			userData.myRegisterChart.setOption(registerOption);
	},
	// 新老用户投注
	refreshBetUserChart:function(newUserBetCount,oldUserBetCount){
		var betUserOption = {
		    title : {
		        text: '投注用户',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data: ['新用户','老用户']
		    },
		    series : [
		        {
		            name: '访问来源',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[
		                {value:newUserBetCount, name:'新用户'},
		                {value:oldUserBetCount, name:'老用户'}
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
		userData.myBetUserChart.setOption(betUserOption);
	},
	refreshBetAmountChart:function(data){
		var betAmountOption = {
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
			        data: ['新用户','老用户']
			    },
			    series : [
			        {
			            name: '访问来源',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:[
			                  {value:data==""?0:data.newUserMoney, name:'新用户'},
				              {value:data==""?0:(data.userMoney-data.newUserMoney), name:'老用户'}
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
		userData.myBetAmountChart.setOption(betAmountOption);
	},
	refreshNoUserChart:function(data){
		var noLoginUserOption =  {
			    title: {
			        text: '未登录人数',
			        x:'center'
			    },
			    tooltip: {
			        trigger: 'axis'
			    },
			    xAxis:  {
			        type: 'category',
			        boundaryGap: false,
			        data: ['近3天','近7天','近15天','近30天','近60天','近90天']
			    },
			    yAxis: {
			        type: 'value',
			        axisLabel: {
			            formatter: '{value} '
			        }
			        
			    },
			    series: [
			        {
			            type:'line',
			            data:[data.noLoginDay3,data.noLoginDay7,data.noLoginDay15,data.noLoginDay30,data.noLoginDay60,data.noLoginDay90],
			            markPoint: {
			                data: [
			                    {type: 'max', name: '最大值'},
			                    {type: 'min', name: '最小值'}
			                ]
			            },
			            markLine: {
			                data: [
			                    {type: 'average', name: '平均值'}
			                ]
			            }
			        }
			        
			    ]
			};
		userData.mySilentUserChart.setOption(noLoginUserOption);
		var noBetUserOption =  {
			    title: {
			        text: '未投注人数',
			        x:'center'
			    },
			    tooltip: {
			        trigger: 'axis'
			    },
			    xAxis:  {
			        type: 'category',
			        boundaryGap: false,
			        data: ['近3天','近7天','近15天','近30天','近60天','近90天']
			    },
			    yAxis: {
			        type: 'value',
			        axisLabel: {
			            formatter: '{value} '
			        }
			        
			    },
			    series: [
			        {
			            type:'line',
			            data:[data.noBuyDay3,data.noBuyDay7,data.noBuyDay15,data.noBuyDay30,data.noBuyDay60,data.noBuyDay90],
			            markPoint: {
			                data: [
			                    {type: 'max', name: '最大值'},
			                    {type: 'min', name: '最小值'}
			                ]
			            },
			            markLine: {
			                data: [
			                    {type: 'average', name: '平均值'}
			                ]
			            }
			        }
			        
			    ]
			};
		userData.myNoBetUserChart.setOption(noBetUserOption);
	}
};