var TicketObj = {
		"init":function() {
			mini.parse();
			// 主页查询条件
			TicketObj.lotteryCategory = mini.get("lotteryCategory");
			TicketObj.lotteryCode = mini.get("lotteryCode");
			TicketObj.lotteryChildCode = mini.get("lotteryChildCode");
			TicketObj.lotteryIssue = mini.get("lotteryIssue");
			TicketObj.buyScreenType = mini.get("buyScreenType");
			TicketObj.buyScreenTypeVal = mini.get("buyScreenTypeVal");
			TicketObj.minQryMoney = mini.get("minQryMoney");
			TicketObj.maxQryMoney = mini.get("maxQryMoney");
			TicketObj.querySpecialCondition = mini.get("querySpecialCondition");
			TicketObj.orderCode = mini.get("orderCode");
			TicketObj.ticketId = mini.get("ticketId");
			TicketObj.userType = mini.get("userType");
			TicketObj.userTypeVal = mini.get("userTypeVal");
			TicketObj.timeType = mini.get("timeType");
			TicketObj.startQryTime = mini.get("startQryTime");
			TicketObj.endQryTime = mini.get("endQryTime");
			TicketObj.batchNum = mini.get("batchNum");
			TicketObj.thirdNum = mini.get("thirdNum");
			TicketObj.officialNum = mini.get("officialNum");
			TicketObj.contentType = mini.get("contentType");
			TicketObj.ticketStatus = mini.get("ticketStatus");
			TicketObj.winningStatus = mini.get("winningStatus");
			TicketObj.winningDetail = mini.get("winningDetail");
			TicketObj.editStatus = mini.get("edit_status");
			TicketObj.ticketOperate = mini.get("ticket_operate");
			TicketObj.ticketType = mini.get("ticketType");
			TicketObj.ticketOperateChannel = mini.get("ticket_operate_channel");
			
			// 主页控件
			TicketObj.grid = mini.get("datagrid");
			TicketObj.editBtn = mini.get("edit");
			TicketObj.ticketOperateChannel.hide();
			// 主页查询统计数据
			TicketObj.other;
			
			// 修改页查询条件
			TicketObj.lotteryCode_edit = mini.get("lotteryCode_edit");
			TicketObj.contentType_edit = mini.get("contentType_edit");
			TicketObj.ticketStatus_edit = mini.get("ticketStatus_edit");
			TicketObj.winningStatus_edit = mini.get("winningStatus_edit");
			// 修改页控件
			TicketObj.editWindow = mini.get("editWindow");
			TicketObj.uploadTicketWindow = mini.get("uploadTicketWindow");
			TicketObj.channel;
			
			// 数据字典加载
			ComReq.dictionary("0303,0601,0602,1409,0606,1410,0610,0611,0403,1411,1412,1413", function(result) {
				 Dic.lotteryCategory = result["0303"];// 彩种类型
				 Dic.buyScreenType = result["0601"];// 竞彩编号类型
				 Dic.userType = result["0602"]; // 用户查询类型
				 Dic.timeType = result["1409"]; // 时间查询类型
				 Dic.contentType = result["0606"]; // 内容类型
				 Dic.ticketStatus = result["1410"]; // 票状态
				 Dic.winningStatus = result["0610"]; // 中奖状态
				 //Dic.winGrade = result["0611"];// 奖项     20170224->从彩种奖项表中关联
				 Dic.ssqPrizeDrade = result["0403"]; // 双色球中奖等级
				 
				 TicketObj.lotteryCategory.setData(Dic.lotteryCategory);
				 TicketObj.buyScreenType.setData(Dic.buyScreenType);
				 TicketObj.buyScreenType.select(0);
				 TicketObj.userType.setData(Dic.userType);
				 TicketObj.userType.select(0);
				 TicketObj.timeType.setData(Dic.timeType);
				 TicketObj.timeType.select(0);
				 TicketObj.contentType.setData(Dic.contentType);
				 TicketObj.ticketStatus.setData(Dic.ticketStatus);
				 TicketObj.winningStatus.setData(Dic.winningStatus);
				 TicketObj.editStatus.setData(result["1411"]);
				 TicketObj.ticketOperate.setData(result["1412"]);
				 TicketObj.ticketType.setData(result["1413"]);
				 //TicketObj.winningDetail.setData(Dic.winGrade);
				 // 修改页
				 TicketObj.contentType_edit.setData(Dic.contentType);
				 TicketObj.ticketStatus_edit.setData(Dic.ticketStatus);
				 TicketObj.winningStatus_edit.setData(Dic.winningStatus);
				// 关联彩种
					ComReq.lotteryChild("",function(result) {
						Dic.lotteryChildCodeAll = result;
					});
			});
			
			// 所有彩种加载
			ComReq.lottery("",function(result) {
				Dic.allCode = result;
				TicketObj.lotteryCode.setData(Dic.allCode);
				TicketObj.lotteryCode_edit.setData(Dic.allCode);
			});
			// 加载出票渠道
			$.get("ticketmgr/ticketchannel/drawer/idname/dictionary",function(data) {
				mini.get('channelId').setData(data);
				TicketObj.channel = data;
			});
		},
		"search":function() {
			var lotteryCode = TicketObj.lotteryCode.getValue();
			var orderCode = TicketObj.orderCode.getValue();
			var ticketId = TicketObj.ticketId.getValue();
			var batchNum = TicketObj.batchNum.getValue();
			var startQryTime = TicketObj.startQryTime.getFormValue();
			var endQryTime = TicketObj.endQryTime.getFormValue();
			var timeType = TicketObj.timeType.getValue();
			if(!orderCode && !ticketId && !batchNum && 
					(!startQryTime || !endQryTime || timeType != "4")){
				if(!lotteryCode){
					mini.alert("请选择查询条件（彩种，订单号，票号，批次号，出票成功时间）");
					return false;
				}
			}
					    
		    TicketObj.grid.load(TicketObj.getParamJson());
		},
		"excelTicket":function() {
			//添加条件判断
			var lotteryCode = TicketObj.lotteryCode.getValue();
			var orderCode = TicketObj.orderCode.getValue();
			var ticketId = TicketObj.ticketId.getValue();
			var batchNum = TicketObj.batchNum.getValue();
			var startQryTime = TicketObj.startQryTime.getFormValue();
			var endQryTime = TicketObj.endQryTime.getFormValue();
			var timeType = TicketObj.timeType.getValue();
			if(!orderCode && !ticketId && !batchNum && 
					(!startQryTime || !endQryTime || timeType != "4")){
				if(!lotteryCode){
					mini.alert("请选择查询条件（彩种，订单号，票号，批次号，出票成功时间）");
					return false;
				}
			}
						
		    if (MiniCom.isValidForm("searchForm") == false) {
	             mini.alert("请输入正确的查询条件在导出！");
		    	 return;
		    } 
			var param = Cms.jsonParamStr(TicketObj.getParamJson());
			var url = "ticketmgr/ticketinfo/excel/?"+param;
			location = url;
	     },
		"getParamJson":function() {
			var para = {};
			para.lotteryCode = TicketObj.lotteryCode.getValue();
			para.lotteryIssue = TicketObj.lotteryIssue.getValue();
			para.lotteryChildCode = TicketObj.lotteryChildCode.getValue();
			var buyScreenType = TicketObj.buyScreenType.getValue();
			var buyScreenTypeVal = TicketObj.buyScreenTypeVal.getValue();
			// 竞彩编号类型(有查询值才处理该参数)
			if(buyScreenTypeVal) {
				switch (buyScreenType) {
				case "0":
					para.buyScreen = buyScreenTypeVal;
					break;
				case "1":
					para.maxBuyScreen = buyScreenTypeVal;
					break;
				}
			}
			para.minQryMoney = TicketObj.minQryMoney.getValue();
			para.maxQryMoney = TicketObj.maxQryMoney.getValue();
			para.orderCode = TicketObj.orderCode.getValue();
			para.id = TicketObj.ticketId.getValue();
			var userType = TicketObj.userType.getValue();
			var userTypeVal = TicketObj.userTypeVal.getValue();
			// 用户查询类型(有查询值才处理该参数)
			if(userTypeVal) {
				switch (userType) {
				case "0":
					para["user.nickName"] = userTypeVal;
					break;
				case "1":
					para["user.accountName"] = userTypeVal;
					break;
				case "2":
					para["user.cusMobile"] = userTypeVal;
					break;
				case "3":
					para["user.id"] = userTypeVal;
					break;
				}
			}
			para.timeType = TicketObj.timeType.getValue();
			para.startQryTime = TicketObj.startQryTime.getFormValue();
			para.endQryTime = TicketObj.endQryTime.getFormValue();
			para.batchNum = TicketObj.batchNum.getValue();
			para.thirdNum = TicketObj.thirdNum.getValue();
			para.officialNum = TicketObj.officialNum.getValue();
			para.contentType = TicketObj.contentType.getValue();
			para.ticketStatus = TicketObj.ticketStatus.getValue();
			para.winningStatus = TicketObj.winningStatus.getValue();
			para.winningDetail = TicketObj.winningDetail.getText(); // 因为票表中中奖情况是汇总信息(格式：一等奖_1注,二等奖_3注....)，所以传名称过去模糊匹配查询
			if(mini.get('channelId').getValue()) {
				para.channelId = mini.get('channelId').getValue();
			}
			var querySpecialCondition = TicketObj.querySpecialCondition.getValue();
			if(querySpecialCondition){
				 var qsc = querySpecialCondition.split(",").forEach(function(s){
					 if(s == "1"){
						 para.queryReceipt = "1";
					 }else if (s == "2"){
						 para.queryChange = "2";
					 }
				 });
			}
			return para;
		},
		"closeLotCategory":function(e) {
			var obj = e.sender;
			obj.setText("");
			obj.setValue("");
			// 关联彩种
			ComReq.lottery("",function(result) {
				TicketObj.lotteryCode.setData(result);
				// 关联奖级（按彩种对应）
				TicketObj.winningDetail.setData([]);
			});
		},
		"changeLotCategory":function() {
			 var code = TicketObj.lotteryCategory.getValue();
			// 关联彩种
			 ComReq.lottery(code, function(result) {
				 TicketObj.lotteryCode.setData(result);
				 // 关联奖级（按彩种对应）
				 TicketObj.winningDetail.setData([]);
			 });
		},
		"closeLotCode":function(e) {
			var obj = e.sender;
			   obj.setText("");
			   obj.setValue("");
			   // 关联彩期(当前期开始的20期)
			   TicketObj.lotteryIssue.setData([]);
			   // 关联奖级（按彩种对应）
			   TicketObj.winningDetail.setData([]);
			   TicketObj.lotteryChildCode.setData([])
		},
		"changeLotCode":function() {
			var code = TicketObj.lotteryCode.getValue();
			if(code) {
				// 关联彩期(当前期开始的20期)
				ComReq.issue(code, function(result) {
					TicketObj.lotteryIssue.setData(result);
					TicketObj.lotteryIssue.select(0);
//					ComReq.findissue(code, function(currentResult){
//						if(currentResult){
//							var re = TicketObj.lotteryIssue.getData();
//							for(var b in re){
//								if(re[b].id == currentResult.issueCode){
//									TicketObj.lotteryIssue.select(b);
//									break;
//								}
//							}
//						}
//					});
				});
				// 关联奖级（按彩种对应）
				ComReq.lotteryWinning(code, function(result) {
					TicketObj.winningDetail.setData(result);
				});
				// 关联彩种
				ComReq.lotteryChild(code,function(result) {
					TicketObj.lotteryChildCode.setData(result);
				});
			}
						
		},
		/**
		 * 画汇总单元格内容
		 */
		"drawSummaryCell":function(e) {
			var index = e.column._index;
			// 按指定列统计的，统计列与汇总列对齐(一一对应)
			if(index == 0) {
				TicketObj.other = e.result.other;
				e.cellHtml="统"
			} else if(index == 1) {
				e.cellHtml="计："
			} else if (e.field == "lotteryCode") { 
				e.cellHtml = "<input type=\"radio\" name=\"sis\" checked=\"checked\" onclick=\"TicketObj.getChangeSis('0')\" />单页统计";
			} else if(e.field == "lotteryIssue") {
				e.cellHtml = "<input type=\"radio\" name=\"sis\" onclick=\"TicketObj.getChangeSis('1')\"/>条件统计";
			} else if(e.field == "ticketMoney") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;border-right:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisTicketMoney\">" + numUtils.fmoney(e.result.other[0], 2) + "</span>";
			} else if(e.field == "preBonus") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisPreBonus\">" + numUtils.fmoney(e.result.other[1], 2) + "</span>";
			} else if(e.field == "aftBonus") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;border-right:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisAftBonus\">" + numUtils.fmoney(e.result.other[2], 2) + "</span>";
			}else if(e.field == "addedBonus") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;border-right:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisAddedBonus\">" + numUtils.fmoney(e.result.other[3], 2) + "</span>";
			}
		},
		"getChangeSis":function(type) {
			if(TicketObj.other) {
				if("0" == type) {
					$("#sisTicketMoney").html(numUtils.fmoney(TicketObj.other[0], 2));
					$("#sisPreBonus").html(numUtils.fmoney(TicketObj.other[1], 2));
					$("#sisAftBonus").html(numUtils.fmoney(TicketObj.other[2], 2));
					$("#sisAddedBonus").html(numUtils.fmoney(TicketObj.other[3], 2));
				} else {
					$("#sisTicketMoney").html(numUtils.fmoney(TicketObj.other[4], 2));
					$("#sisPreBonus").html(numUtils.fmoney(TicketObj.other[5], 2));
					$("#sisAftBonus").html(numUtils.fmoney(TicketObj.other[6], 2));
					$("#sisAddedBonus").html(numUtils.fmoney(TicketObj.other[7], 2));
				}
			}
		},
		/**
	      * 渲染::渲染-奖项
	      */
	    "renderWinDetail":function(e) {
	    	var _record = e.record;
	    	var _val = e.value;
	    	
	    	return TicketObj.handlerWinDetail(_record.lotteryCode, _val);
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
	    "viewTicket":function() {
	    	TicketObj.editTicket();
	    },
	    "editTicket":function() {
        	var row = TicketObj.grid.getSelected();
     	    if (row) {
     	    	 TicketObj.openEditWin(row.id); // 用id
     	    } else {
     	    	 mini.alert("请选择一行数据");
     	    }
        },
        "openEditWin":function(id) {
        	if(id) {
        		TicketObj.grid.loading("加载中。。。。");
     	    	var param = {
	     	    	url:"ticketmgr/ticketinfo/detail/"+id,
	     	    	action:"GET",
	     	    	data:"",
    	     	}	     	   
    	     	Cms.ajax(param, function(row) {
    	     	    	row.action = "put";
    	    	        row.url="ticketmgr/ticketinfo";
    	    	        var form = new mini.Form("#editForm");
    	    	        var ticketChange = row.ticketChange;
    	    	        //切票记录拼接
    	    	        if(ticketChange){
    	    	        	var temp = "";
    	    	            ticketChange.split(";").forEach(function(s){
    	    	        	    var channel =  s.split("#");
    	    	        	    for(var c in TicketObj.channel){
    	    	        	    	if(channel[0] == TicketObj.channel[c].id){
    	    	        	    		temp += TicketObj.channel[c].text;
    	    	        	    		break;
    	    	        	    	}
    	    	        	    }
    	    	        	    temp = temp + "#" + channel[1] +";";
    	    	        	});
    	    	        	row.ticketChange = temp;
    	    	        }
    	    	        form.clear();
    	    	        form.setData(row);
    	    	        // 票图片预览
    	    	        $("#preTicketImg").attr("src", row.ticketImg);
    	    	        TicketObj.editWindow.show();
    	     	});
     	    	TicketObj.grid.unmask();
        	}
        },
		"onrowdblclick":function() {
			TicketObj.editTicket();
		},
		"doSubmit" : function() {
			Cms.submit(new mini.Form("#editForm"), function() {
				TicketObj.editWindow.hide();
				TicketObj.grid.reload();
		    });
		},
		/**
		 * @desc 票图片上传
		 * @param uploadId 上传控件id
		 */
		"ajaxFileUpload":function(uploadId) {
            Cms.uploadWindow("image/ticket",function(data){
            	mini.get("ticketImg_edit").setValue(data.url);
                $("#preTicketImg").attr("src", data.url);
    		});
         },
         "updateTicketStatus":function(){
				var rows = TicketObj.grid.getSelecteds();
				if (rows.length == 0) {
					mini.alert("请选择一行数据");
					return;
				}
				var status = TicketObj.editStatus.getValue();
				if (!status) {
					mini.alert("请选择修改状态");
					return;
				}
				mini.confirm("确认修改", "提示", function(e) {
			    	if(e=="ok"){
			    		var id = "";
						for (var i = 0; i < rows.length; i++) {
							id += rows[i].id + ",";
						}
						var param ={
								url:"ticketmgr/ticketinfo/status",
								action:"put",
								data:{
									status:status,
									id:id
								}
						}
						Cms.ajax(param,function(data){
							mini.alert("修改票状态成功数："+data);
							Cms.reload("datagrid");
						});
			    	}
			    });
         },
	     "ticketOperate" : function() {
			var operate = TicketObj.ticketOperate.getValue();
			if (!operate) {
				mini.alert("请选择操作");
				return;
			}
			switch (operate) {
			case "1":
				var row = {
					url:"ticketmgr/ticketinfo/upload/ticket",
					action:"post",
					ticketType:"1"
				}
				var form = new mini.Form("#uploadTikcetForm");
				form.setData(row);
				TicketObj.uploadTicketWindow.show();
				break;
			case "2":
				TicketObj.excelTicket();
				break;
			case "3":
			case "4":
			case "5":
				var rows = TicketObj.grid.getSelecteds();
				if (rows.length == 0) {
					mini.alert("请选择一行数据");
					return;
				}
				var  channelId = TicketObj.ticketOperateChannel.getValue();
				mini.confirm("确认执行操作", "提示", function(e) {
			    	if(e=="ok"){
			    		var id = "";
						for (var i = 0; i < rows.length; i++) {
							id += rows[i].id + ",";
						}
						var param ={
								url:"ticketmgr/ticketinfo/operate",
								action:"put",
								data:{
									operate:operate,
									id:id,
									channelId:channelId
								}
						}
						Cms.ajax(param,function(data){
							mini.alert("执行操作成功");
							Cms.reload("datagrid");
						});
			    	}
			    	TicketObj.ticketOperateChannel.hide();
			    });
				break;
			default:
				break;
		}
	},
	"uploadTicket":function(){
		Cms.submit(new mini.Form("#uploadTikcetForm"), function() {
			TicketObj.uploadTicketWindow.hide();
	    });
	},
	"changeOperate":function(){
		var operate = TicketObj.ticketOperate.getValue();
		if("5" == operate){
			var lotteryCode = TicketObj.lotteryCode.getValue();
			$.get("ticketmgr/ticketchannel/drawer/idname/dictionary?lotteryCode="+lotteryCode,function(data) {
				TicketObj.ticketOperateChannel.show();
				TicketObj.ticketOperateChannel.setData(data);
			});
		}else{
			TicketObj.ticketOperateChannel.hide();
		}
		
	}
}

TicketObj.init();