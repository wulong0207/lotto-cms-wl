$(function(){  
	$("#registerTable").hide();
	$("#newOrOldUserTable").hide();
	$("#siclientUserTable").hide();
});  


userdata={
    init:function(){
        mini.parse();
        userdata.userRegisterChannel = mini.get("userRegisterChannel");
        userdata.lotteryType = mini.get("lotteryType");
        $.get("sysmgr/dic/dictionary?code=0603", function (result) {
        	 var a = result['0603'],b = [{"id":"-1","text":"全部"}];
             for(var i = 0;i<a.length;i++){
             	b[i+1] = a[i];
             }
             Dic.userRegisterChannel = b;
            userdata.userRegisterChannel.setData(Dic.userRegisterChannel);
        });
        $.get("lotterymgr/type/dictionary", function (result) {
            Dic.lotteryType = result;
            userdata.lotteryType.setData(Dic.lotteryType);
        }); 
        $('input[name="chartDetailData"]').click(function(){
			$("#platformChart").hide();
			$("#newOrOldUserChart").hide();
			$("#siclientUserChart").hide();
			$("#registerTable").hide();
        });
        $(".dtwl").click(function(){
        	var val = parseInt($(this).attr("id"));
        	$(".dtwl").each(function(i){
    			if(i+1 == val){
    				$(this).find('span').attr("style","background-color: rgb(61, 116, 183)");
    			}else{
    				$(this).find('span').attr("style","");
    			}
    		});
        	switch (val) {
	        	case 1:
	        		$("#platformChart").hide();
	        		$("#newOrOldUserChart").show();
	        		$("#siclientUserChart").hide();
	        		$("#registerTable").hide();
	        		break;
	        	case 2:
	        		$("#platformChart").show();
	        		$("#newOrOldUserChart").hide();
	        		$("#siclientUserChart").hide();
	        		$("#registerTable").hide();
	        		break;
	        	case 3:
	        		$("#platformChart").hide();
	        		$("#newOrOldUserChart").hide();
	        		$("#siclientUserChart").show();
	        		$("#registerTable").hide();
	        		break;
	        	default:
	        		$("#platformChart").hide();
		        	$("#newOrOldUserChart").hide();
		        	$("#siclientUserChart").hide();
		        	$("#registerTable").show();
		        	break;
        	}
        });
    },
	comboxOnCloseClick:function(e){
	    var obj = e.sender;
	    obj.setText("");
	    obj.setValue("");
	},
	/**
	 * 选择固定时间
	 * @param a
	 */
	checkFixedTime:function(a){
		$("#fixedTime").find('a').each(function(i){
			if(i==a){
				$(this).attr("style","color:white;background-color:rgb(61, 116, 183);cursor:hand");
			}else{
				$(this).attr("style","color:#333;cursor:hand");
			}
		});
	}
};
userdata.init();

var platformSale = echarts.init(document.getElementById('platformSale'));
var myBetUserChart = echarts.init(document.getElementById('betUser'));
var myBetAmountChart = echarts.init(document.getElementById('betAmount'));
var mySilentUserChart = echarts.init(document.getElementById('noLoginUser'));
var myNoBetUserChart = echarts.init(document.getElementById('noBetUser'));

var registerOption = {
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
	                {value:335, name:'Web'},
	                {value:310, name:'Wap'},
	                {value:500, name:'IOS'},
	                {value:600, name:'Android'}
	            ],
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
	        data: ['大客户','非大客户']
	    },
	    series : [
	        {
	            name: '访问来源',
	            type: 'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[
	                {value:335, name:'大客户'},
	                {value:310, name:'非大客户'}
	            ],
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
	        data: ['大客户','非大客户']
	    },
	    series : [
	        {
	            name: '访问来源',
	            type: 'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[
	                  {value:335, name:'大客户'},
		              {value:310, name:'非大客户'}
	            ],
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

var noLoginUserOption =  {
		title : {
	        text: '投注金额/万',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: ['双色球', '大乐透', '竞足', '竞篮', '十一运', '广东十一选五', '江西十一选五']
	    },
	    series : [
	        {
	            name: '访问来源',
	            type: 'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[
	                  {value:10, name:'双色球'},
		              {value:52, name:'大乐透'},
		              {value:200, name:'竞足'},
		              {value:334, name:'竞篮'},
		              {value:390, name:'十一运'},
		              {value:330, name:'广东十一选五'},
		              {value:220, name:'江西十一选五'}
	            ],
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
var noBetUserOption =  {
		title : {
	        text: '投注人数',
	        x:'center'
	    },
		 color: ['#3398DB'],
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : ['双色球', '大乐透', '竞足', '竞篮', '十一运', '广东十一选五', '江西十一选五','湖北快3','重庆时时彩','江西时时彩','幸运赛车'],
		            axisTick: {
		                alignWithLabel: true
		            },
		            axisLabel:{
		                textStyle:{
		                   color:"red",
		                   fontSize:16
		                },
		                interval:0,
		                formatter:function(value)  
                        {  
                            return value.split("").join("\n");  
                        } 
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
		            type:'bar',
		            barWidth: '30%',
		            barMaxWidth: '35',
		            data:[10, 52, 200, 334, 390, 330, 220,400,600,900,1000]
		        }
		    ]
	};

var platformDetail = '<span style="margin-left:10px; width:100px; height:50px; line-height:50px;">pc端                投注金额：500万，占比：30%</span></br><span style="margin-left:10px; width:100px; height:50px; line-height:50px;">wap端                投注金额：500万，占比：30%</span></br><span style="margin-left:10px; width:100px; height:50px; line-height:50px;">android端                投注金额：500万，占比：30%</span></br><span style="margin-left:10px; width:100px; height:50px; line-height:50px;">ios端                投注金额：500万，占比：30%</span>';
$('#platformDetail').html(platformDetail);

mySilentUserChart.setOption(noLoginUserOption);
myNoBetUserChart.setOption(noBetUserOption);
myBetAmountChart.setOption(betAmountOption);
myBetUserChart.setOption(betUserOption);
platformSale.setOption(registerOption);