var PrizeObj = {
		"init":function(){
			mini.parse();
			// 主页查询条件
			PrizeObj.lotteryCategory = mini.get("lotteryCategory");
			PrizeObj.lotteryCode = mini.get("lotteryCode");
			PrizeObj.lotteryIssue = mini.get("lotteryIssue");
			PrizeObj.buyScreen = mini.get("buyScreen");
			PrizeObj.minBonus = mini.get("minBonus");
			PrizeObj.maxBonus = mini.get("maxBonus");
			PrizeObj.orderCode = mini.get("orderCode");
			PrizeObj.userType = mini.get("userType");
			PrizeObj.userTypeVal = mini.get("userTypeVal");
			PrizeObj.timeType = mini.get("timeType");
			PrizeObj.startTime = mini.get("startTime");
			PrizeObj.endTime = mini.get("endTime");
			PrizeObj.buyType = mini.get("buyType");
			PrizeObj.winGrade = mini.get("winGrade");
            PrizeObj.winningStatus = mini.get("winningStatus");
			// 主页控件
			PrizeObj.grid = mini.get("datagrid");
			// 主页查询统计数据
			PrizeObj.other;
			
			// 数据字典加载
			ComReq.dictionary("0303,0602,0604,0607,0611,0403,0610", function(result) {
				 Dic.lotteryCategory = result["0303"];// 彩种类型
				 Dic.userType = result["0602"];// 用户查询类型
				 Dic.timeType = result["0604"];// 时间查询类型
				 Dic.buyType = result["0607"];// 购买类型
				 //Dic.winGrade = result["0611"];// 奖项
				 Dic.ssqPrizeDrade = result["0403"]; // 双色球中奖等级(用于数据渲染)
				 Dic.winningStatus = result["0610"];
				 PrizeObj.lotteryCategory.setData(Dic.lotteryCategory);
				 PrizeObj.userType.setData(Dic.userType);
				 PrizeObj.userType.select(0);
				 PrizeObj.timeType.setData(Dic.timeType);
				 PrizeObj.timeType.select(0);
				 PrizeObj.buyType.setData(Dic.buyType);
				 //PrizeObj.winGrade.setData(Dic.winGrade);
                PrizeObj.winningStatus.setData(Dic.winningStatus);
			});
			
			// 所有彩种加载
			ComReq.lottery("",function(result) {
				 Dic.allCode = result;
				 PrizeObj.lotteryCode.setData(result);
			});
		},
		"search":function() {
			if (MiniCom.isValidForm("searchForm") == false) {
	             mini.alert("请输入正确的查询条件！");
		    	 return;
		    }
			PrizeObj.grid.load(PrizeObj.getParamJson());
		},
		"excel":function() {
		    if (MiniCom.isValidForm("searchForm") == false) {
	             mini.alert("请输入正确的查询条件在导出！");
		    	 return;
		    }
			var param = Cms.jsonParamStr(PrizeObj.getParamJson());
			var url = "lotterymgr/prize/excel?"+param;
			location = url;
		},
		"getParamJson":function() {
			var para = {};
			para.lotteryCode = PrizeObj.lotteryCode.getValue();
			para.lotteryIssue = PrizeObj.lotteryIssue.getValue();
			para.buyScreen = PrizeObj.buyScreen.getValue();
			para.minBonus = PrizeObj.minBonus.getValue(); 
			para.maxBonus = PrizeObj.maxBonus.getValue();
			para.orderCode = PrizeObj.orderCode.getValue();
			para.winningStatus = PrizeObj.winningStatus.getValue();
			var userType = PrizeObj.userType.getValue();
			var userTypeVal = PrizeObj.userTypeVal.getValue();
			// 用户查询类型(有查询值才处理该参数)
			if(userTypeVal) {
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
			}
			para.timeType = PrizeObj.timeType.getValue();
			para.startTime = PrizeObj.startTime.getFormValue();
			para.endTime = PrizeObj.endTime.getFormValue();
			para.buyType = PrizeObj.buyType.getValue();
			para.winningDetail = PrizeObj.winGrade.getText(); // 因为订单表中中奖情况是汇总信息(格式：一等奖_1注,二等奖_3注....)，所以传名称过去模糊匹配查询
			return para;
		},
		"closeLotCategory":function(e) {
			var obj = e.sender;
			obj.setText("");
			obj.setValue("");
			// 关联彩种
			ComReq.lottery("",function(result) {
				PrizeObj.lotteryCode.setData(result);
				// 关联奖级（按彩种对应）
				PrizeObj.winGrade.setData([]);
			});
		},
		"changeLotCategory":function() {
			 var code = PrizeObj.lotteryCategory.getValue();
			 // 关联彩种
			 ComReq.lottery(code, function(result) {
				 PrizeObj.lotteryCode.setData(result);
				 // 关联奖级（按彩种对应）
				 PrizeObj.winGrade.setData([]);
			 });
		},
		"closeLotCode":function(e) {
			var obj = e.sender;
		    obj.setText("");
		    obj.setValue("");
		    // 关联彩期(当前期开始的20期)
		    PrizeObj.lotteryIssue.setData([]);
		    // 关联奖级（按彩种对应）
		    PrizeObj.winGrade.setData([]);
		},
		"changeLotCode":function() {
			var code = PrizeObj.lotteryCode.getValue();
			if(code) {
				// 关联彩期(当前期开始的20期)
				ComReq.issue(code, function(result) {
					PrizeObj.lotteryIssue.setData(result);
					PrizeObj.lotteryIssue.select(0);
				});
				// 关联奖级（按彩种对应）
				ComReq.lotteryWinning(code, function(result) {
					PrizeObj.winGrade.setData(result);
				});
			}
		},
		"drawSummaryCell":function(e) {
			var index = e.column._index;
			if(index == 0) {
				PrizeObj.other = e.result.other;
				e.cellHtml = "统"
			} else if(index == 1) {
				e.cellHtml = "计："
			} else if (e.field == "lotteryCode") { 
				e.cellHtml = "<input type=\"radio\" name=\"sis\" checked=\"checked\" onclick=\"PrizeObj.getChangeSis('0')\" />单页统计";
			} else if(e.field == "lotteryIssue") {
				e.cellHtml = "<input type=\"radio\" name=\"sis\" onclick=\"PrizeObj.getChangeSis('1')\"/>条件统计";
			} else if(e.field == "orderAmount") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisOrderAmount\">" + numUtils.fmoney(e.result.other[0], 2) + "</span>";
			} else if(e.field == "preBonus") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisPreBonus\">" + numUtils.fmoney(e.result.other[1], 2) + "</span>";
			} else if(e.field == "aftBonus") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisAftBonus\">" + numUtils.fmoney(e.result.other[2], 2) + "</span>";
			} else if(e.field == "sendBonus") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;border-right:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisSendBonus\">" + numUtils.fmoney(e.result.other[3], 2) + "</span>";
			} else if(e.field == "addedBonus") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;border-right:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisAddedBonus\">" + numUtils.fmoney(e.result.other[8], 2) + "</span>";
			} else if(e.field == "websiteBonus") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;border-right:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisWebsiteBonus\">" + numUtils.fmoney(e.result.other[9], 2) + "</span>";
			}
		},
		"getChangeSis":function(type) {
			if(PrizeObj.other) {
				if("0" == type) {
					$("#sisOrderAmount").html(numUtils.fmoney(PrizeObj.other[0], 2));
					$("#sisPreBonus").html(numUtils.fmoney(PrizeObj.other[1], 2));
					$("#sisAftBonus").html(numUtils.fmoney(PrizeObj.other[2], 2));
					$("#sisSendBonus").html(numUtils.fmoney(PrizeObj.other[3], 2));
					$("#sisAddedBonus").html(numUtils.fmoney(PrizeObj.other[8], 2));
					$("#sisWebsiteBonus").html(numUtils.fmoney(PrizeObj.other[9], 2));
				} else {
					$("#sisOrderAmount").html(numUtils.fmoney(PrizeObj.other[4], 2));
					$("#sisPreBonus").html(numUtils.fmoney(PrizeObj.other[5], 2));
					$("#sisAftBonus").html(numUtils.fmoney(PrizeObj.other[6], 2));
					$("#sisSendBonus").html(numUtils.fmoney(PrizeObj.other[7], 2));
					$("#sisAddedBonus").html(numUtils.fmoney(PrizeObj.other[10], 2));
					$("#sisWebsiteBonus").html(numUtils.fmoney(PrizeObj.other[11], 2));
				}
			}
		},
		/**
	      * 渲染::渲染-奖项
	      */
	    "renderWinDetail":function(e) {
	    	var _record = e.record;
	    	var _val = e.value;
	    	
	    	return PrizeObj.handlerWinDetail(_record.lotteryCode, _val);
	    },
	    "handlerWinDetail":function(lotteryCode, winGrade) {
	    	// （后续扩展 按不同彩种的奖项来）
	    	if(lotteryCode == 100) { // 双色球奖项
	    		for (var i = 0; i < Dic.ssqPrizeDrade.length; i++) {
	    			if(Dic.ssqPrizeDrade[i].id == winGrade) {
	    				return Dic.ssqPrizeDrade[i].text;
	    			}
				}
	    	}
	    	return winGrade;
	    },
	    "sendPrize" : function(){
	    	var lotteryText = PrizeObj.lotteryCode.getText();
	    	var lotteryCode = PrizeObj.lotteryCode.getValue();
	    	var lotteryIssue = PrizeObj.lotteryIssue.getValue();
	    	var selecteds = PrizeObj.grid.getSelecteds();
	    	if(lotteryCode == null || lotteryCode == ''
	    		|| lotteryCode == undefined){
	    		mini.alert("彩种不能为空");
	    		return;
	    	}
	    	//判断是否是竞彩
	    	if(lotteryCode == 300 || lotteryCode == 301 ||lotteryCode == 306){
	    		//按照最大赛事编号派奖
	    		lotteryIssue = '';
	    	}else{
	    		//按照彩期派奖
	    		if(lotteryIssue == null || lotteryIssue == ''
		    		|| lotteryIssue == undefined){
		    		mini.alert("彩期不能为空");
		    		return;
		    	}
	    	}
	        var paramAward = {
		                url: "lotterymgr/prize/award",
		                action: "get",
		                data:{lotteryCode:lotteryCode, lotteryIssue:lotteryIssue}	
		    }
			var orderCodes = '';
			if(selecteds.length>0){
                for(var i = 0;i<selecteds.length;i++){
					var temp = selecteds[i];
					if(orderCodes.indexOf(",") == -1){
                        orderCodes = temp.orderCode;
					}else{
                        orderCodes += (","+temp.orderCode);
					}
				}
			}



	        Cms.ajax(paramAward,function(data){
	        	var message = "确定是否执行 <span style='color:red'>"+ lotteryText + " _ " + lotteryIssue +"派奖操作</span>?";
	        	if("1" == data){
	        		 message = "订单派奖金额超出限制，是否继续派奖 <span style='color:red'>"+ lotteryText + " _ " + lotteryIssue +"</span>，请在报警信息中查询对应订单";
	        	}
	        	 var param = {
	 	                url: "taskmgr/job/runTask",
	 	                action: "get",
	 	                data:{lotteryCode:lotteryCode, lotteryIssue:lotteryIssue,orderCodes:orderCodes,jobId:'000024'}
	 	        }
	 	        Cms.ajaxFirm(message, param, function (data) {
	 	        	Cms.drawSchedule(lotteryCode,lotteryIssue,2,function(){
	 	    			PrizeObj.grid.load(PrizeObj.getParamJson());
	 	        	});
	 	        }) 	
	        });
	    }
}

PrizeObj.init();