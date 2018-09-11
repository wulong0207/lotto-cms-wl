
//方案和出票商切换
function cpsExchange() {
  $('.lotM-table .cps').toggle();
  $('.lotM-table .stop-plan').toggle();
}
//获取当前时间
function nowTime() {
  function p(s) {
    return s < 10 ? '0' + s : s;
  }
  var myDate = new Date();
  //获取当前年
  var year = myDate.getFullYear();
  //获取当前月
  var month = myDate.getMonth() + 1;
  //获取当前日
  var date = myDate.getDate();
  var h = myDate.getHours(); //获取当前小时数(0-23)
  var m = myDate.getMinutes(); //获取当前分钟数(0-59)
  var s = myDate.getSeconds();
  var now = year + '-' + p(month) + '-' + p(date) + ' ' + p(h) + ':' + p(m) + ':' + p(s);
  $('.lotM-header .now-time em').text(now);
  // 判断是否8分钟排序
  if ($('#totop8').attr('checked') == 'checked' && (myDate - myDate % 1000) / 1000 % 4 == 0) {
    sortTable();
  }
}

//固定底部
$(function() {
  $(window).resize(function() {
    barFixed();
  });
  $(document).ready(function() {
    barFixed();
    setHeight();
  });
  $(window).bind('scroll',
    function() {
      barFixed();
    });

  function barFixed() {
    var buyH = $('.lotM-footer').offset().top - document.documentElement.clientHeight + $('.lotM-footer').height();
    var wH = $(document).scrollTop();
    if (buyH < wH) {
      $('.lotM-footer').removeClass('fixed');
    } else {
      $('.lotM-main').css('margin-bottom', 40);
      $('.lotM-footer').addClass('fixed');
    }
  }
});

//获取高度防止数据变化页面抖动
function setHeight() {
  $.each($('.lotM-table tbody tr'), function() {
    var cps = $(this).find('.cps').eq(0);
    if (cps != undefined) {
      var num = cps.find('.cps-item').length;
      if (num < 2) {
        num = 2;
      }
      var trH = num * 30;
      $(this).find('.lotM-num').css('line-height', trH + 'px');
    }
  })

}


//置顶八分钟后截止的彩种
var sortTable = function(bool) {
  $('#tableList tbody').html($('#tableList tbody tr').sort(sortByType(bool)));
}

var sortByType = function(sortType) {
//  var now = 1495194900000;
   var now = +new Date();
  if (sortType !== false) {
    return function sortByDate(a, b) {
      var aid = $(a).data('id');
      var bid = $(b).data('id');
      var aVal = $(a).data('endTime');
      var bVal = $(b).data('endTime');
      aVal = +new Date(aVal.replace(/-/, '/'));
      bVal = +new Date(bVal.replace(/-/, '/'));
//      console.log( $(a).data(aid + '_endTime'), aVal - bVal, $(b).data(bid + '_endTime'), (aVal - now) / (60 * 1000), (bVal - now) / (60 * 1000));
      if (aVal - now > 0 && bVal - now > 0) {
        // 判断都在8分钟内，按时间排序
        return aVal - bVal;
      } else if (aVal - now > 0) {
        return -1;
      } else if (bVal - now > 0) {
        return 1;
      } else {
    	  console.log(+$(a).data('id') - (+$(b).data('id')));
    	  return +$(a).data('id') - (+$(b).data('id'))
      }
    }
  } else {
    return function sortByDate(a, b) {
      return +$(a).data('id') - (+$(b).data('id'))
    }
  }

}

var Current = {
	"init" : function() {
		
        $.get("sysmgr/dic/dictionary?code=0303", function(result){
        	Dic.lotteryCategory = result["0303"];
//        	Dic.lotteryCode = [];
//        	Dic.lotteryType = {};
			var selhtml = "";
			$.each(Dic.lotteryCategory, function(i,val){
				selhtml += "<option value=\"" + val.id +"\">"+ val.text +"</option>"
//				
//				ComReq.lottery(val.id, function(result){
//					Dic.lotteryType[val.id] = result;
//					Dic.lotteryCode.push.apply(Dic.lotteryCode, result);
//				})
			})
			$("#lotteryCategory").append(selhtml);
        });
        setTimeout(function(){
        	 Current.getMonitorData();       
        },2000);
//        setTimeout(function(){
//        	$.each(Dic.lotteryCode, function(i, data){
//        		 		
//        	})
//        }, 500)
	  //出票监控页面动态效果js
	  //通用表格隔行背景
	  $('.lotM-table tbody tr:nth-child(even)').addClass('trOdd');
	  //现在时间
	  nowTime();
	  setInterval(nowTime, 1000); //现在时间

	  //方案和出票商定时切换
	  var timer, ms = 4000;
	  if (!$('#stopChange').attr('checked')) {
	    timer = setInterval(cpsExchange, ms);
	  }
	  $('#stopChange').click(function() {
	    if ($(this).attr('checked') == 'checked') {
	      clearInterval(timer);
	    } else {
	      timer = setInterval(cpsExchange, ms);
	    }
	  });
	  $('#totop8').on('change', function() {
	    if ($(this).attr('checked') != 'checked') {
	      sortTable(false);
	    }
	  });
	  
		var tid = setInterval(function(){
			Current.selectBase();				
		},10000)
		
		// 跑马灯每60秒刷新数据
		var $alramInfoTag = $('#alramInfoTag');
		var oldAlramInfo;
		setInterval(function() {
			$.get('ticketmgr/alarm/marquee').done(function(data) {
				if(data && JSON.stringify(oldAlramInfo) !== JSON.stringify(data)) {
					$div = $('<div>');
					var index = 0;
					for ( var key in data) {
						$div.append($('<span>').text((++index)+ '. '+data[key]));
					}
					$alramInfoTag.html($div.children());
					oldAlramInfo = data;
				}
			});
		},60*1000)
		// 警报跑马灯一开始先读表，后续刷新读map
		$.ajax({
			url : 'ticketmgr/alarm/marquee',
			type : 'PUT'
		}).done(
			function(res) {
				$.get('ticketmgr/alarm/marquee').done(function(data) {
					$div = $('<div>');
					var index = 0;
					for ( var key in data) {
						$div.append($('<span>').text((++index)+ '. '+data[key]));
					}
					$alramInfoTag.html($div.children());
					oldAlramInfo = data;
				});
		})
	},
	"getMonitorData" : function(codes, lotteryCategory) {
		var html = "";
		$.ajax({
			url : "ticketmgr/monitorcommon/list",
			type : "POST",
			data : {'lotteryCodeStr':codes, 'lotteryCategory': lotteryCategory},
			success : function(res) {
				if (res.errorCode == Code.success) {
					if(res.data && res.data !=null){
						if(res.data == null)
							return;
						$.each(res.data, function(i,data){
							 if(data != null){
								if($("#" + data.lotteryCode).length == 0){
									$("#ticketDetail").append(Current.converHtml(data));
								}else{
									Current.replaceText(data);
								}								 
							 }
						})	
					}
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				mini.alert(jqXHR.responseText);
			}
		});
	}, 
	"converHtml" : function(data){
		var trs = "";
		 if(data != null){
			   var code = data.lotteryCode;
			   var suppliers = data.channelInfo;
			   
	    	   var sport = "";
	    	   var red1 ="",red2="",red3="",red4="",red5="";
	    	   var supplierHtml = Current.converSupplier(suppliers);
//	    	   if(code == '300' || code == '301' ||
//	    			   code == '306' || code == '307')
//	    		   sport = "<br/><span id=\""+ code + "_notOutTicketCountSport\">"+data.notOutTicketCountSport+"</span>";
	     	   
	    	   if(data.isWarnMap){
	        	   if(data.isWarnMap.isNoOutMoney && data.isWarnMap.isNoOutMoney == 1){
	        		   red1 = " bg-red";
	        	   }
	        	   if(data.isWarnMap.isNoOut && data.isWarnMap.isNoOut == 1){
	        		   red2 = " bg-red";
	        	   }
	        	   if(data.isWarnMap.isNoSend && data.isWarnMap.isNoSend == 1){
	        		   red3 = " bg-red";
	        	   }
	        	   if(data.isWarnMap.isSend && isWarnMap.isSend == 1){
	        		   red4 = " bg-red";
	        	   }
	        	   if(data.isWarnMap.isNoSplit && data.isWarnMap.isNoSplit == 1){
	        		   red5 = " bg-red";
	        	   }		    		   
	    	   }	    	 
	    	   trs = "<tr id=\""+code+"\"data-end-time=\""+ data.endTime +"\" data-id=\""+code+"\">" +
            		"<td class=\"lotM-id\"><span id=\""+ code + "_lotteryName\">"+ data.lotteryName +"</span>" +
            				"<em id=\""+ code + "_lotteryIssue\">"+ data.lotteryIssue +"</em></td> " +
            			   "<td class=\"lotM-date\"><span id=\""+ code + "_endTime\">"+ data.endTime+"</span></td>" +
            			   "<td class=\"lotM-num" +red1+ "\"><span id=\""+ code + "_notOutTicketMoney\">"+data.notOutTicketMoney+"</span>" +
            			   			sport + "</td>" +
            			   "<td class=\"lotM-num" +red2+ "\"><span id=\""+ code + "_notOutTicketCount\">"+data.notOutTicketCount+"</span></td>" + 
            			   "<td class=\"lotM-num" +red3+ "\"><span id=\""+ code + "_notSendTicketCount\">"+ data.notSendTicketCount +"</span></td>" +
            			   "<td class=\"lotM-num" +red4+ "\"><span id=\""+ code + "_sendTicketCount\">"+ data.sendTicketCount +"</span></td>" +
            			   "<td class=\"lotM-num\"><span id=\""+ code + "_failOutTicketCount\">"+ data.failOutTicketCount +"</span></td>" +
            			   "<td id=\""+ code + "_sup1\" class=\"cps\">" + supplierHtml.div1 + "</td>" + "<td id=\""+ code + "_sup2\" class=\"cps\">" + supplierHtml.div2 + "</td>" + 
            			   "<td id=\""+ code + "_notSplitOrderCount\" class=\"stop-plan" +red5+ "\">"+ data.notSplitOrderCount+"</td> + " +
            			   "<td id=\""+ code + "_notSplitOrderMoney\" class=\"stop-plan\">" + data.notSplitOrderMoney+ "</td>" + 
            			   "<td id=\""+ code + "_cooperationMoneyOne\" class=\"stop-plan\">" + data.cooperationMoneyOne+ "</td>" +
            			   "<td id=\""+ code + "_cooperationMoneyTwo\" class=\"stop-plan\">" + data.cooperationMoneyTwo+ "</td>" + 
            			   "<td id=\""+ code + "_cooperationMoneyThree\" class=\"stop-plan\">" + data.cooperationMoneyThree+ "</td>" + 
            			   "</tr>";
		 }
		return trs;
	},
	"replaceText":function(data){
		var code = data.lotteryCode;
		var suppliers = data.channelInfo;
		var supplierHtml = Current.converSupplier(suppliers);
		
		$("#" + code + "_lotteryName").text(data.lotteryName);
		$("#" + code + "_lotteryIssue").text(data.lotteryIssue);
		$("#" + code + "_endTime").text(data.endTime);
		$("#" + code + "_notOutTicketMoney").text(data.notOutTicketMoney);
/* 	   if(code == '300' || code == '301' ||
			   code == '306' || code == '307')
		$("#" + code + "_notOutTicketCountSport").text(data.notOutTicketCountSport);*/
		$("#" + code + "_notOutTicketCount").text(data.notOutTicketCount);
		$("#" + code + "_notSendTicketCount").text(data.notSendTicketCount);
		$("#" + code + "_sendTicketCount").text(data.sendTicketCount);
		$("#" + code + "_failOutTicketCount").text(data.failOutTicketCount);
		$("#" + code + "_sup1").html(supplierHtml.div1);
		$("#" + code + "_sup2").html(supplierHtml.div2);
		$("#" + code + "_notSplitOrderCount").text(data.notSplitOrderCount);
		$("#" + code + "_notSplitOrderMoney").text(data.notSplitOrderMoney);
		$("#" + code + "_cooperationMoneyOne").text(data.cooperationMoneyOne);
		$("#" + code + "_cooperationMoneyTwo").text(data.cooperationMoneyTwo);
		$("#" + code + "_cooperationMoneyThree").text(data.cooperationMoneyThree);
		
 	   if(data.isWarnMap){
    	   if(data.isWarnMap.isNoOutMoney != null && data.isWarnMap.isNoOutMoney != undefined){
    		   Current.isWarn(data.isWarnMap.isNoOutMoney, $("#" + code + "_notOutTicketMoney"));
    	   }
    	   if(data.isWarnMap.isNoOut != null && data.isWarnMap.isNoOut != undefined){
    		   Current.isWarn(data.isWarnMap.isNoOut, $("#" + code + "_notOutTicketCount"));
    	   }
    	   if(data.isWarnMap.isNoSend != null && data.isWarnMap.isNoSend != undefined){
    		   Current.isWarn(data.isWarnMap.isNoSend, $("#" + code + "_notSendTicketCount"));
    	   }
    	   if(data.isWarnMap.isSend != null && data.isWarnMap.isSend != undefined){
     		   Current.isWarn(isWarnMap.isSend, $("#" + code + "_sendTicketCount"));
    	   }
    	   if(data.isWarnMap.isNoSplit != null && data.isWarnMap.isNoSplit != undefined){
    		   Current.isWarn(data.isWarnMap.isNoSplit, $("#" + code + "_notSplitOrderCount"));
    	   }		    		   
	   }
		
	},"converSupplier" : function(suppliers){
		var html = {};
		var div1 = "";
		var div2 = "";
		if(suppliers instanceof Array && suppliers.constructor == Array){
			var len = suppliers.length;
			if (len > 0) {
				var num = Math.ceil(len / 2);
				for (var y = 0; y < num; y++) {
					var supplier = suppliers[y];
					div1 += "<div class=\"cps-item\"><span><em class=\"cps-name\">"
							+ supplier.channelName + "</em> - "
							+ supplier.ticketChannelId + "</span>" + "<span>"
							+ supplier.sendTicketCount + "</span><span>"
							+ supplier.sendTicketMoney + "</span>" +
									/*"<span>"
							+ supplier.comeOutTime + "</span>" +*/
									"</div>";
				}
				for (var z = num; z < len; z++) {
					var supplier = suppliers[z];
					div2 += "<div class=\"cps-item\"><span><em class=\"cps-name\">"
							+ supplier.channelName + "</em> - "
							+ supplier.ticketChannelId + "</span>" + "<span>"
							+ supplier.sendTicketCount + "</span><span>"
							+ supplier.sendTicketMoney + "</span>" 
							+
									/*"<span>"
							+ supplier.comeOutTime + "</span>" +*/
									"</div>";
				}
			}			
		}
		html.div1 = div1;
		html.div2 = div2;
		return html;
	},"changeVal" : function(){
		$("#ticketDetail").html("");
		Current.selectBase();
	},"selectBase" : function(){
		var category = $("#lotteryCategory").val();
		if(category == "-1"){
//			Current.converStrByArr(Dic.lotteryCode)
			Current.getMonitorData();
		}else{
//			Current.converStrByArr(Dic.lotteryType[category])
			Current.getMonitorData('', category);
		}		
	},
//	"converStrByArr" : function(arr){
//		 var idArray = [];
//         for (var i = 0;i < arr.length; i++) {
//             var r = arr[i];
//             idArray.push(r.id);
//         }
//		return idArray.join('_');
//	},
	"isWarn" : function(val, eve){
	   if(val == 1){
		   if(!$(eve).parent().is(".bg-red")){
			  $(eve).parent().addClass("bg-red");
		   } 
	   }else{
		   if($(eve).parent().is(".bg-red")){
				$(eve).parent().removeClass("bg-red");
			}
	   }
	}
}
Current.init();





