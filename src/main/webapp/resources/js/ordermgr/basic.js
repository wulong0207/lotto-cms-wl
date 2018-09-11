var Current ={
		"init":function(){
			mini.parse();
			Current.grid = mini.get("datagrid");
			Current.contentGrid = mini.get("contentgrid");
			Current.editWindow = mini.get("editWindow");
			Current.sportEditWindow = mini.get("sportEditWindow");
			Current.upoladWindow = mini.get("upoladWindow");
			Current.cancelWindow = mini.get("cancelWindow");
			Current.channel = mini.get("channel");
			Current.lotteryCategory = mini.get("lotteryCategory");
			Current.lotteryCode = mini.get("lotteryCode");
			Current.detailCodeType = mini.get("detailCodeType");
			Current.userType = mini.get("userType");
			Current.channel = mini.get("channel");
			Current.registerChannel = mini.get("registerChannel");
			Current.platform = mini.get("platform");
			Current.timeType = mini.get("timeType");
			Current.codeWay = mini.get("codeWay");
			Current.contentType = mini.get("contentType");
			Current.buyType = mini.get("buyType");
			Current.payStatus = mini.get("payStatus");
			Current.orderStatus = mini.get("orderStatus");
			Current.winStatus = mini.get("winStatus");
			Current.winGrade = mini.get("winGrade");
			Current.issueCode = mini.get("issueCode");
			Current.statistics = mini.get("statistics");
			Current.orderStatus_update = mini.get("orderStatus_update");
			Current.checkTicket = mini.get("checkTicket");
			Current.orderOperate = mini.get("order_operate");
			Current.orderCode = mini.get("orderCode");
			Current.orderAddCode = mini.get("orderAddCode");
			Current.orderCancelType = mini.get("orderCancelType");
			Current.other;
			
			ComReq.dictionary("0303,0601,0602,0603,0604,0605,0606,0607,0608,0609,0610,0611,0618,0619,0620,0621,0622,0702,1512", function(result){
				 Dic.lotteryCategory = result["0303"];
				 Dic.buyType =result["0607"];
				 Dic.orderStatus =result["0609"];
				 Dic.winningStatus = result["0610"];
				 Dic.platform = result["0702"];
				 Dic.payStatus = result["0608"];
				 Dic.codeWay = result["0605"];
				 Dic.contentType = result["0606"];
				 Dic.isDltAdd = result["0618"];
				 Dic.checkTicket  = result["0620"];
				 Dic.orderOperate  = result["0621"];
				 Dic.orderCancelType = result["0622"];
				 Dic.platform = result["0702"];
				 Dic.categoryType= result["1512"];
				 
				 //Current.winGrade.setData(result["0611"]);// 奖项     20170224->从彩种奖项表中关联
				 
				 Current.winStatus.setData(Dic.winningStatus);
				 Current.orderStatus.setData(Dic.orderStatus);
				 
				 Current.orderStatus_update.setData(result["0619"]);
				 Current.orderOperate.setData(Dic.orderOperate);
				 Current.payStatus.setData(Dic.payStatus);
				 Current.buyType.setData( Dic.buyType);
				 Current.contentType.setData(Dic.contentType);
				 Current.codeWay.setData(Dic.codeWay);
				 Current.timeType.setData(result["0604"]);
				 Current.userType.setData(result["0602"]);
				 Current.detailCodeType.setData(result["0601"]);
				 Current.lotteryCategory.setData(Dic.lotteryCategory);
				 Current.checkTicket.setData(Dic.checkTicket);
				 Current.platform.setData(Dic.platform);
				 Current.orderCancelType.setData(Dic.orderCancelType);
				 Current.detailCodeType.select(0);
				 Current.userType.select(0);
				 Current.timeType.select(0);
				 
		
				 Current.openEditWin(Cms.getCookie('orderCode'));
			});
			ComReq.lottery("",function(result){
				 Dic.allCode = result;
				 Current.lotteryCode.setData(result);
			 });
			 ComReq.lottery("3",function(result){
				 Dic.sportCode = result;
			 });
			 ComReq.channel(function(result){
				 Dic.channel = result;
				 Current.channel.setData(Cms.joinDictionary(result));
				 Current.registerChannel.setData(Cms.joinDictionary(result));
			 });
			 			 
		},
		"search":function(){
			/*var lotteryCode = Current.lotteryCode.getValue();
			var orderCode = Current.orderCode.getValue();
			var orderAddCode = Current.orderAddCode.getValue();
			if(!orderCode && !orderAddCode){
				if(!lotteryCode){
					mini.alert("请选择彩种");
					return false;
				}
			}*/
			var para = Current.getParamJson();
			var v = false;
			for(var key in para){
				if(key == "timeType"
					|| key == "codeWay"
					|| key == "contentType"
				    || key == "buyType"
				    || key == "checkTicket"
				    || key == "dltAdd"
				    || key == "winningDetail"){
					continue;
				}
	            var value=para[key];
	            if(value){
	            	v = true;
	            	break;
	            }
	        }
			if(v){
				Current.grid.load(para);
			}else{
				mini.alert("请至少输入一个合理查询条件");
			}
			
		},
		"getChangeSis":function(type){
			if(Current.other){
				if("0"== type){
					$("#sisOrderAmount").html(numUtils.fmoney(Current.other[0], 2));
					$("#sisPreBonus").html(numUtils.fmoney(Current.other[1], 2));
					$("#sisAftBonus").html(numUtils.fmoney(Current.other[2], 2));
					$("#sisAddedBonus").html(numUtils.fmoney(Current.other[3], 2));
					$("#sisWebsiteBonus").html(numUtils.fmoney(Current.other[8], 2));
				}else{
					$("#sisOrderAmount").html(numUtils.fmoney(Current.other[4], 2));
					$("#sisPreBonus").html(numUtils.fmoney(Current.other[5], 2));
					$("#sisAftBonus").html(numUtils.fmoney(Current.other[6], 2));
					$("#sisAddedBonus").html(numUtils.fmoney(Current.other[7], 2));
					$("#sisWebsiteBonus").html(numUtils.fmoney(Current.other[9], 2));
				}
				
			}
		},
		"onDrawSummaryCell":function(e){
			var index = e.column._index;
			if(index==0){
				Current.other = e.result.other;
				e.cellHtml="统"
			}else if(index==1){
				e.cellHtml="计："
			}else if (e.field == "lotteryCode") { 
				e.cellHtml ="<input type=\"radio\" name=\"sis\" checked=\"checked\" onclick=\"Current.getChangeSis('0')\" />单页统计";
			}else if(e.field == "lotteryIssue"){
				e.cellHtml ="<input type=\"radio\" name=\"sis\" onclick=\"Current.getChangeSis('1')\"/>条件统计";
			}else if(e.field =="orderAmount"){
				e.cellStyle = "border-left:#d2d2d2 1px solid;border-right:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml ="<span id =\"sisOrderAmount\">"+numUtils.fmoney(e.result.other[0], 2)+"</span>";
			}else if(e.field =="preBonus"){
				e.cellStyle = "border-left:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml ="<span id =\"sisPreBonus\">"+numUtils.fmoney(e.result.other[1], 2)+"</span>";
			}else if(e.field =="aftBonus"){
				e.cellStyle = "border-left:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml ="<span id =\"sisAftBonus\">"+numUtils.fmoney(e.result.other[2], 2)+"</span>";
			}else if(e.field =="addedBonus"){
				e.cellStyle = "border-left:#d2d2d2 1px solid;border-right:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml ="<span id =\"sisAddedBonus\">"+numUtils.fmoney(e.result.other[3], 2)+"</span>";
			}else if(e.field =="websiteBonus"){
				e.cellStyle = "border-left:#d2d2d2 1px solid;border-right:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml ="<span id =\"sisWebsiteBonus\">"+numUtils.fmoney(e.result.other[8], 2)+"</span>";
			}
		},
		"getParamJson":function(){
			var para ={};
			para.lotteryCode = Current.lotteryCode.getValue();
			para.lotteryIssue = Current.issueCode.getValue();
			var detailCodeType = Current.detailCodeType.getValue();
			if(detailCodeType=="0"){
				para.buyScreen = mini.get("detailCodeTypeVal").getValue();
			}else if(detailCodeType=="1"){
				para.maxBuyScreen = mini.get("detailCodeTypeVal").getValue();
			}
			para.platform = Current.platform.getValue();
			para.channelId = Current.channel.getValue();
			para.registerChannelId = Current.registerChannel.getValue();
			para.orderCode = mini.get("orderCode").getValue();
			para.orderAddCode = Current.orderAddCode.getValue();
			var userType = Current.userType.getValue();
			var userTypeVal = mini.get("userTypeVal").getValue();
			switch (userType) {
			case "0":
				para.nickName = userTypeVal;
				break;
			case "1":
				para.accountName = userTypeVal;
				break;
			case "2":
				para.cusMobile = userTypeVal;
				break;
			case "3":
				para.userId = userTypeVal;
				break;
			}
			var dltAdd = mini.get("dlt").getValue();
			if(dltAdd){
				para.dltAdd = dltAdd;
			}
			para.codeWay = Current.codeWay.getValue();
			para.contentType = Current.contentType.getValue();
			para.buyType = Current.buyType.getValue();
			para.payStatus = Current.payStatus.getValue();
			para.orderStatus =  Current.orderStatus.getValue();
			para.winningStatus =  Current.winStatus.getValue();
			para.checkTicket = Current.checkTicket.getValue();
			para.winningDetail = Current.winGrade.getText(); // 因为订单表中中奖情况是汇总信息(格式：一等奖_1注,二等奖_3注....)，所以传名称过去模糊匹配查询
			para.timeType = Current.timeType.getValue();
			para.startTime = mini.get("startTime").getFormValue();
			para.endTime = mini.get("endTime").getFormValue();
			para.activityName = mini.get("activityName").getValue();
			para.activitySource = mini.get("activitySource").getValue();   // 20170905 add 添加活动编号查询
			return para;
		},
        "editwin":function(){
    		var row = Current.grid.getSelected();
    		if(row){				
    		    var code = row.lotteryCode;
    		    var height = 800;
    		    
    		    mini.open({
                    url: "ordermgr/basic/editwin/"+code,
                    title: "修改方案信息",
                    width: 1200, height: height,
                    onload: function () {
                    	var iframe = this.getIFrameEl();
                        iframe.contentWindow.initData(row.orderCode);
                    }
    		    });
    		}else{
    			mini.alert("请选择一行数据！");
    		}
        },
        "openEditWin":function(orderCode){
        	if(orderCode && orderCode!='""'){
     	    	var param ={
    	     	    	url:"ordermgr/basic/detail/"+orderCode,
    	     	    	action:"GET",
    	     	    	data:"",
    	     	    }
     	    	var height = 800;
    	     	Cms.ajax(param, function(row){
        		    mini.open({
                        url: "ordermgr/basic/editwin/"+row.lotteryCode,
                        title: "修改方案信息",
                        width: 1200, height: height,
                        onload: function () {
                        	var iframe = this.getIFrameEl();
                            iframe.contentWindow.initData(orderCode);
                        }
        		});    	     		
//    	     	    	row.action = "put";
//    	    	        row.url="ordermgr/basic";
//    	    	        var form = new mini.Form("#editform");
//    	    	        form.clear();
//    	    	        form.setData(row);
//    	    	        Current.editWindow.show();
//    	    	        $("#orderCodeShow").html(row.orderCode);
//    	    	        $("#drawCode_edit").html(row.drawCode);
//    	    	        Current.loadDetail(row.orderCode,row.lotteryCode);
    	     	});
        	}
        },
		"lotteryCategoryChange":function(){
			 var code = Current.lotteryCategory.getValue();
			 ComReq.lottery(code,function(result){
				 Current.lotteryCode.setData(result);
				 // 关联奖级（按彩种对应）
				 Current.winGrade.setData([]);
			 });
		},
		"onCloseClickType":function(e){
			var obj = e.sender;
			obj.setText("");
			obj.setValue("");
			ComReq.lottery("",function(result){
				 Current.lotteryCode.setData(result);
				 // 关联奖级（按彩种对应）
				 Current.winGrade.setData([]);
			});
		},
		"lotteryCodeChange":function(){
			var code = Current.lotteryCode.getValue();
			if(code){
				ComReq.issue(code,function(result){
					Current.issueCode.setData(result);
					Current.issueCode.select(0);
//					ComReq.findissue(code, function(currentResult){
//						if(currentResult){
//							var re = Current.issueCode.getData();
//							for(var b in re){
//								if(re[b].id == currentResult.issueCode){
//									Current.issueCode.select(b);
//									break;
//								}
//							}
//						}
//					});
				});
				// 关联奖级（按彩种对应）
				ComReq.lotteryWinning(code, function(result) {
					Current.winGrade.setData(result);
				});
			}
		},
		"onCloseClickCode":function(e){
			var obj = e.sender;
			   obj.setText("");
			   obj.setValue("");
			   Current.issueCode.setData([]);
			   // 关联奖级（按彩种对应）
			   Current.winGrade.setData([]);
		},
		"onrowdblclick":function(){
			 mini.get("edit").doClick();
		},
		/**
		 * 加载订单内容详情
		 */
//		"loadDetail":function(orderCode,lotteryCode){
//			var param ={
//					orderCode:orderCode
//	     	}
//			var isSport = false;
//			//判断老足彩（14场，6场半，4场）,显示弹出和数字彩统一
//			if("ZC6"!=lotteryCode
//					&&"JQ4"!=lotteryCode
//					&&"SFC"!=lotteryCode){
//				for(var i = 0;i<Dic.sportCode.length;i++){
//					if(Dic.sportCode[i].id == lotteryCode){
//						isSport = true;
//						break;
//					}
//				}
//			}
//			//判断是否竞技彩，弹出窗体不同
//			if(isSport){
//				$("#sportContent").show();
//				$("#numHighContent").hide();
//				param.pageIndex =1;
//				param.pageSize =30;
//				$.get("ordermgr/basic/content/detail/list?"+Cms.jsonParamStr(param), function(result){
//					 if(result.total==1){
//						 $("#sportContent").find("input[name=bettingContentUrl]").val(result.data[0].bettingContentUrl);
//						 $("#contentUrl").attr("href",result.data[0].bettingContentUrl);
//					 }else if(result.total>1){
//						 mini.alert("数据有误！");
//					 }
//				});
//			}else{
//				$("#sportContent").hide();
//				$("#numHighContent").show();
//				Current.contentGrid.load(param,function(data){
//					if(data.result.errorCode && data.result.errorCode!=Code.success){
//						mini.alert(data.result.message);
//					}
//				});
//			}
//			
//		},
		"doSubmit":function(){
	        Cms.submit(new mini.Form("#editform"),function(){
	        	Current.editWindow.hide();
	        	var lotteryCode = Current.lotteryCode.getValue();
	        	if(lotteryCode){
	        		Cms.reload("datagrid");
	        	}	        
		    });
	     },
	     "lookSportScreen":function(){
	    	 Current.sportEditWindow.show();
	     },
	     "excelOrder":function(type){
	    	 var param = Cms.jsonParamStr(Current.getParamJson());
			 var url = "ordermgr/basic/excel/"+type+"?"+param;
			 location = url;
	     },
	     "uploadOrder":function(){
	    	 var form = new mini.Form("#orderForm");
	    	 form.clear()
	    	 Current.upoladWindow.show("left","30px");
	     },
	     "ajaxFileUploadSearch":function(id){
	    	    var form = new mini.Form("#orderForm");
	    	    form.validate();
			    if (form.isValid() == false){
		             mini.alert("选择文件类型错误");
			    	 return;
			    }
				var h = "#"+id+" > input:file";
	            Cms.upload(Current.upoladWindow,h,"ordermgr/basic/upload/add?",function(data){
	            	 Current.upoladWindow.hide();
	            	 Cms.reload("datagrid");
	            });
	     },
	     "updateOrderStatus" : function() {
				var rows = Current.grid.getSelecteds();
				if (rows.length == 0) {
					mini.alert("请选择一行数据");
					return;
				}
				var status = Current.orderStatus_update.getValue();
				if (!status) {
					mini.alert("请选择修改状态");
					return;
				}
				mini.confirm("确认修改", "提示", function(e) {
			    	if(e=="ok"){
			    		var order = "";
						for (var i = 0; i < rows.length; i++) {
							order += rows[i].orderCode + ",";
						}
						var param ={
								url:"ordermgr/basic/status",
								action:"put",
								data:{
									orderStatus:status,
									orderCode:order
								}
						}
						Cms.ajax(param,function(data){
							mini.alert("修改成功订单数："+data);
							Cms.reload("datagrid");
						});
			    	}
			    });
				
	     },
	     "orderOperate" : function() {
			var value = Current.orderOperate.getValue();
			if (!value) {
				mini.alert("请选择操作");
				return;
			}
			switch (value) {
			case "0":
			case "1":
			case "2":
				var rows = Current.grid.getSelecteds();
				if (rows.length == 0) {
					mini.alert("请选择一行数据");
					return;
				}
				mini.confirm("确认执行操作", "提示", function(e) {
					if (e == "ok") {
						var order = "";
						for (var i = 0; i < rows.length; i++) {
							order += rows[i].orderCode + ",";
						}
						var param = {
							url : "ordermgr/basic/operate",
							action : "put",
							data : {
								operate : value,
								orderCode : order
							}
						}
						Cms.ajax(param, function(data) {
							var orders = data.split(",");
							var msg = "执行操作成功订单：<br/>";
							for(var i =0;i<orders.length;i++){
								if(orders[i]){
									msg =msg +  (i+1)+":"+orders[i] + "<br/>";	
								}
							}
							mini.alert("执行操作成功");
							Cms.reload("datagrid");
						});
					}
				});
				break;
			case "3":
				Current.excelOrder('user')
				break;
			case "4":
				Current.excelOrder('info')
				break;
			default:
				break;
			}
	},
	"cancelwin" : function(){
		// 选择行
		var _row = Current.grid.getSelected();
		// 彩种、彩期
		var _lotteryCode = Current.lotteryCode.getValue();
		var _issueCode = Current.issueCode.getValue();
		var _buyScreen = mini.get("detailCodeTypeVal").getValue();
		if(_row) {
			var _orderCode = _row.orderCode; // 订单编号
			var _orderStatus = _row.orderStatus; // 订单状态
			if(!_orderCode) {
				mini.alert("订单编号不存在！");
				return;
			}
			if(!_orderStatus || _orderStatus != 7) {
				mini.alert("只有订单状态为'出票失败'的订单才允许撤单！");
				return;
			}
			// 按订单号提示撤单
    	    var form = new mini.Form("#cancelOrderForm");
    	    form.clear();
			$("#cancelInfo").text(" " + _orderCode + "  ");
			Current.cancelWindow.show();
		} else if(_lotteryCode && _issueCode) {
			if((_lotteryCode == 300 || _lotteryCode == 301 ||  _lotteryCode == 306 ||  _lotteryCode == 307)&& !_buyScreen) {
				mini.alert("竞技彩赛事编号不能为空！");
				return;
			}
			var _lotteryName = Current.lotteryCode.getText();
			// 按彩种彩期提示撤单
    	    var form = new mini.Form("#cancelOrderForm");
    	    form.clear();
			$("#cancelInfo").text(" " + _lotteryName + _issueCode + "期 ");
			Current.cancelWindow.show();
		} else {
			mini.alert("请勾选一条记录或在下拉框中选择彩种和彩期进行撤单操作！");
		}		
	},
	/**
	 * 撤单操作（分2种情况：1.勾选记录，按订单编号撤单；2.按彩种、彩期查询数据并撤单）
	 */
	"cancelOrder" : function() {
		// 选择行
		var _row = Current.grid.getSelected();
		// 彩种、彩期、对阵列表
		var _lotteryCode = Current.lotteryCode.getValue();
		var _issueCode = Current.issueCode.getValue();
		var _buyScreen = mini.get("detailCodeTypeVal").getValue();
		// 撤单选项及原因
		var _cancelOrderType = Current.orderCancelType.getValue();
		var _cancelDesc = mini.get("cancelDesc").getValue();
		if(!_cancelOrderType) {
			mini.alert("撤销原因不能选择为空");
			return;
		} else {
			if(_cancelOrderType == "0" && !_cancelDesc) {
				mini.alert("手动输入时，原因不能输入为空!");
				return;
			}
		}
		var form = new mini.Form("#cancelOrderForm");
		form.validate();
		if (!form.isValid()) {
			mini.alert("撤单信息填写有误!");
			return;
		}
		
		// 按选择订单撤
		if(_row) {
			var _orderCode = _row.orderCode; // 订单编号
			var _orderStatus = _row.orderStatus; // 订单状态
			if(!_orderCode) {
				mini.alert("订单编号不存在！");
				return;
			}
			if(!_orderStatus || _orderStatus != 7) {
				mini.alert("只有订单状态为'出票失败'的订单才允许撤单！");
				return;
			}
			// 勾选记录，单条撤单
			mini.confirm("确定对订单：<span style=\"color: red;\">" + _orderCode +  "</span>执行撤单操作吗?", "提示", function(e) {
				if (e == "ok") {
					Current.grid.loading("操作中，请稍后......");
	                Cms.ajaxSend({
	         	    	url:"ordermgr/basic/cancel",
	         	    	type:"post",
	         	    	data:{orderCode : _orderCode, isCheck : "true", cancelOrderType : _cancelOrderType, cancelDesc: _cancelDesc } // 是否验证时间，在确定是否强撤
	         	    },function(res) {
	         	    	if (res.errorCode == Code.success) { // 不弹框提示，直接就撤单了
	                		mini.showTips({
	                			content : "操作成功",
	                			state : "success",
	    						x : "center",
	    						y : "center",
	    						timeout : 3000
	    					});
	                		Current.cancelWindow.hide();
	                		Current.grid.reload();
	    				} else if (res.errorCode == "30125") { // 提示剩余时间，是否强制撤单
	    					
	    					Current.grid.unmask();
	    					mini.confirm(res.message, "提示", function(e) {
	    						if (e == "ok") {
	    							Current.grid.loading("操作中，请稍后......");
	    			                Cms.ajaxSend({
	    			         	    	url:"ordermgr/basic/cancel",
	    			         	    	type:"post",
	    			         	    	data:{orderCode : _orderCode, isCheck : "false", cancelOrderType : _cancelOrderType, cancelDesc: _cancelDesc } // 是否验证时间，强撤
	    			         	    },function(res) {
	    			         	    	if (res.errorCode == Code.success) {
	    			                		mini.showTips({
	    			                			content : "操作成功",
	    			                			state : "success",
	    			    						x : "center",
	    			    						y : "center",
	    			    						timeout : 3000
	    			    					});
	    			                		Current.cancelWindow.hide();
	    			                		Current.grid.reload();
	    			    				} else {
	    			    					Current.grid.unmask();
	    			    					mini.alert(res.message);
	    			    				}
	    			         	    });
	    						}
	    					});
	    				} else {
	    					Current.grid.unmask();
	    					mini.alert(res.message);
	    				}
	         	    });
				}
			});
		} else if(_lotteryCode && _issueCode) {
			if((_lotteryCode == 300 || _lotteryCode == 301 ||  _lotteryCode == 306 ||  _lotteryCode == 307)&& !_buyScreen) {
				mini.alert("竞技彩赛事编号不能为空！");
				return;
			}
			var _lotteryName = Current.lotteryCode.getText();
			mini.confirm("确定执行<span style=\"color: red;\">" + _lotteryName + 
					"</span>的<span style=\"color: red;\">"+_issueCode+"</span>期撤单吗?", "提示", function(e) {
				if(e == "ok") {
					var param = {
							url:"ordermgr/basic/batchCancel",
							data:{
								"lotteryCode" : _lotteryCode, 
								"issueCode" : _issueCode,
								"buyScreen" : _buyScreen,
								"cancelOrderType" : _cancelOrderType,
								"cancelDesc" : _cancelDesc
							},
							action:"post"
					}
					Cms.ajax(param,function() {
						mini.alert("后台已开始处理你的请求！","提示", function(){
							Current.cancelWindow.hide();
							Current.grid.reload();
						});
					});
				}
			});
		} else {
			mini.alert("请勾选一条记录或在下拉框中选择彩种和彩期进行撤单操作！");
		}
	},"cotChange" : function() {
		var val = Current.orderCancelType.getValue();
		if(val == 0){
			mini.get("cancelDesc").setValue("");
			mini.get("cancelDesc").show();
		}else{
			mini.get("cancelDesc").hide();
		}
	},
	/**
	 * 撤单中订单退款操作（勾选一条记录，按订单编号退款）
	 */
	"cancellingRefund" : function() {
		// 选择行
		var _row = Current.grid.getSelected();
		if(_row) {
			var _orderCode = _row.orderCode; // 订单编号
			var _orderStatus = _row.orderStatus; // 订单状态
			if(!_orderCode) {
				mini.alert("订单编号不存在！");
				return;
			}
			if(!_orderStatus || _orderStatus != 10) {
				mini.alert("只有订单状态为'撤单中'的订单才允许退款！");
				return;
			}
			// 勾选记录，单条退款
			mini.confirm("确定对订单：<span style=\"color: red;\">" + _orderCode +  "</span>执行退款操作吗?", "提示", function(e) {
				if (e == "ok") {
					Current.grid.loading("操作中，请稍后......");
	                Cms.ajaxSend({
	         	    	url:"ordermgr/basic/refund",
	         	    	type:"post",
	         	    	data:{orderCode : _orderCode }
	         	    },function(res) {
	         	    	Current.grid.unmask();
	         	    	if (res.errorCode == Code.success) {
	                		mini.alert("操作成功！","提示", function(){
	                			Current.grid.reload();
							});
	    				} else {
	    					mini.alert(res.message);
	    				}
	         	    });
				}
			});
		} else {
			mini.alert("请勾选一条记录进行退款操作！");
		}		
	}
}

Current.init();