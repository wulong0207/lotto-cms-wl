var ChaseObj = {
		"init":function() {
			mini.parse();
			// 主页查询条件
			ChaseObj.lotteryCategory = mini.get("lotteryCategory");
			ChaseObj.lotteryCode = mini.get("lotteryCode");
			ChaseObj.issueCode = mini.get("issueCode");
			ChaseObj.activityId = mini.get("activityId"); // 20170905 add 活动id
			ChaseObj.activityName = mini.get("activityName");
			ChaseObj.channel = mini.get("channel");
			ChaseObj.platform = mini.get("platform");
			ChaseObj.orderAddCode = mini.get("orderAddCode");
			ChaseObj.userType = mini.get("userType");
			ChaseObj.userTypeVal = mini.get("userTypeVal");
			ChaseObj.timeType = mini.get("timeType");
			ChaseObj.startQryTime = mini.get("startQryTime");
			ChaseObj.endQryTime = mini.get("endQryTime");
			ChaseObj.addType = mini.get("addType");
			ChaseObj.isDltAdd = mini.get("isDltAdd"); // 20170505添加
			ChaseObj.codeWay = mini.get("codeWay");
			ChaseObj.contentType = mini.get("contentType");
			ChaseObj.payStatus = mini.get("payStatus");
			ChaseObj.addStatus = mini.get("addStatus");
			ChaseObj.stopType = mini.get("stopType");
			// 主页控件
			ChaseObj.grid = mini.get("datagrid");
			ChaseObj.editBtn = mini.get("edit");
			// 主页查询统计数据
			ChaseObj.other;
			
			// 修改页查询条件
			ChaseObj.lotteryCode_edit = mini.get("lotteryCode_edit");
			ChaseObj.orderAddCode_edit = mini.get("orderAddCode_edit");
			ChaseObj.payStatus_edit = mini.get("payStatus_edit");
			ChaseObj.addStatus_edit = mini.get("addStatus_edit");
			ChaseObj.stopType_edit = mini.get("stopType_edit");
			ChaseObj.addType_edit = mini.get("addType_edit");
			ChaseObj.stopCondition_edit = mini.get("stopCondition_edit");
			ChaseObj.isDltAdd_edit = mini.get("isDltAdd_edit");
			ChaseObj.platform_edit = mini.get("platform_edit");
			
			ChaseObj.addIssueStatus = mini.get("addIssueStatus");
			// 修改页控件
			ChaseObj.editWindow = mini.get("editWindow");
			ChaseObj.chaseContentGrid = mini.get("chaseContentGrid");
			ChaseObj.chaseIssueGrid = mini.get("chaseIssueGrid");
			
			// 数据字典加载
			ComReq.dictionary("0303,0602,0613,0614,0605,0606,0608,0615,0616,0702,0617,0403,0618", function(result) {
				 Dic.lotteryCategory = result["0303"];// 彩种类型
				 Dic.userType = result["0602"]; // 用户查询类型
				 Dic.timeType = result["0613"]; // 时间查询类型
				 Dic.addType = result["0614"]; // 追号类型
				 Dic.codeWay = result["0605"];// 选号方式
				 Dic.contentType = result["0606"]; // 内容类型
				 Dic.payStatus = result["0608"]; // 支付状态
				 Dic.addStatus = result["0615"]; // 追号状态
				 Dic.stopType = result["0616"]; // 停追类型
				 //Dic.ssqPrizeDrade = result["0403"]; // 双色球中奖等级(用于停追条件-奖项映射)   20170224->从彩种奖项表中关联
				 Dic.platform = result["0702"] //投注平台
				 // 修改页
				 Dic.addIssueStatus = result["0617"]; // 追号彩期状态
				 Dic.isDltAdd = result["0618"]; // 是否大乐透追加

				 
				 ChaseObj.lotteryCategory.setData(Dic.lotteryCategory);
				 ChaseObj.userType.setData(Dic.userType);
				 ChaseObj.userType.select(0);
				 ChaseObj.timeType.setData(Dic.timeType);
				 ChaseObj.timeType.select(0);
				 ChaseObj.addType.setData(Dic.addType);
				 ChaseObj.codeWay.setData(Dic.codeWay);
				 ChaseObj.contentType.setData(Dic.contentType);
				 ChaseObj.payStatus.setData(Dic.payStatus);
				 ChaseObj.addStatus.setData(Dic.addStatus);
				 ChaseObj.stopType.setData(Dic.stopType);
				 ChaseObj.platform.setData(Dic.platform);
				 // 修改页
				 ChaseObj.payStatus_edit.setData(Dic.payStatus);
				 ChaseObj.addStatus_edit.setData(Dic.addStatus);
				 ChaseObj.stopType_edit.setData(Dic.stopType);
				 ChaseObj.addType_edit.setData(Dic.addType);
				 ChaseObj.isDltAdd_edit.setData(Dic.isDltAdd);
				 ChaseObj.platform_edit.setData(Dic.platform);
				 
				 ChaseObj.addIssueStatus.setData(Dic.addIssueStatus);

				 
				 
				 
				// ChaseObj.openEditWin(Cms.getCookie('orderCode'));
			});
			// 所有彩种加载
			ComReq.lottery("",function(result) {
				Dic.allCode = result;
				ChaseObj.lotteryCode.setData(Dic.allCode);
				ChaseObj.lotteryCode_edit.setData(Dic.allCode);
			});
			// 关联彩种子玩法
			ComReq.lotteryChild("", function(result) {
				Dic.lotteryChild = result;
			});
			// 关联奖级（按彩种对应）
			ChaseObj.searchPrizeGrade("");
			
			//所以渠道加载
			ComReq.channel(function(result){
				Dic.channel = result;
				ChaseObj.channel.setData(Cms.joinDictionary(result));
			})
		},
		/**
		 * @desc 根据彩种查询奖级列表
		 * @param lotteryCode 彩种code
		 */
		"searchPrizeGrade" : function(lotteryCode) {
			lotteryCode = !lotteryCode ? "" : lotteryCode;
			// 关联奖级（按彩种对应）
			ComReq.lotteryWinning(lotteryCode, function(result) {
				Dic.prizeGrade = result;
			});
		},
		"search":function() {
			var lotteryCode = ChaseObj.lotteryCode.getValue();
			var orderAddCode = ChaseObj.orderAddCode.getValue();
			if(!orderAddCode)
			if(!lotteryCode){
				mini.alert("请选择彩种!!!");
				return false;
			}
			ChaseObj.grid.load(ChaseObj.getParamJson());
		},
		"excelChase":function(type) {
			if (MiniCom.isValidForm("searchForm") == false) {
	             mini.alert("请输入正确的查询条件在导出！");
		    	 return;
		    }
			var param = Cms.jsonParamStr(ChaseObj.getParamJson());
			var url = "ordermgr/chase/excel/"+type+"?"+param;
			location = url;
	     },
		"getParamJson":function() {
			var para = {};
			para.lotteryCode = ChaseObj.lotteryCode.getValue();
			para.issueCode = ChaseObj.issueCode.getValue();
			para.activityId = ChaseObj.activityId.getValue(); // 20170905 add 
			var _activityName = ChaseObj.activityName.getValue();
			if(_activityName) {
				para["activity.activityName"] = _activityName;
			}
			
			para.platform = ChaseObj.platform.getValue();
			para.channelId = ChaseObj.channel.getValue();
			para.orderAddCode = ChaseObj.orderAddCode.getValue();
			var userType = ChaseObj.userType.getValue();
			var userTypeVal = ChaseObj.userTypeVal.getValue();
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
			para.timeType = ChaseObj.timeType.getValue();
			para.startQryTime = ChaseObj.startQryTime.getFormValue();
			para.endQryTime = ChaseObj.endQryTime.getFormValue();
			// 20170505添加
			var _isDltAdd = ChaseObj.isDltAdd.getValue();
			if(_isDltAdd) {
				para.isDltAdd = _isDltAdd;
			}
			
			para.addType = ChaseObj.addType.getValue();
			var _codeWay = ChaseObj.codeWay.getValue();
			var _contentType = ChaseObj.contentType.getValue();
			if(_codeWay) {
				para["orderAddContent.codeWay"] = _codeWay;
			}
			if(_contentType) {
				para["orderAddContent.contentType"] = _contentType;
			}
			para.payStatus = ChaseObj.payStatus.getValue();
			para.addStatus =  ChaseObj.addStatus.getValue();
			para.stopType =  ChaseObj.stopType.getValue();
			return para;
		},
		"editChase":function() {
        	var row = ChaseObj.grid.getSelected();
     	    if (row) {
     	    	 ChaseObj.openEditWin(row.id); // 用id 还是orderAddCode
     	    } else {
     	    	 mini.alert("请选择一行数据");
     	    }
        },
        "openEditWin":function(id) {
        	if(id) {
        		ChaseObj.grid.loading("加载中。。。。");
        		ChaseObj.editWindow.set({
    				title:"追号计划详情"
    			});
     	    	var param = {
	     	    	url:"ordermgr/chase/detail/"+id,
	     	    	action:"GET",
	     	    	data:"",
    	     	}	     	   
    	     	Cms.ajax(param, function(row) {
    	     	    	row.action = "put";
    	    	        row.url="ordermgr/chase";
    	    	        var form = new mini.Form("#editForm");
    	    	        form.clear();
    	    	        form.setData(row);
    	    	        ChaseObj.editWindow.show();
    	    	        // 停追条件处理（针对 停追条件=奖项的）
    	    	        var _stopCondition = ChaseObj.handlerStopCondition(row.lotteryCode, row.stopType, row.stopCondition);
    	    	        ChaseObj.stopCondition_edit.setValue(_stopCondition);
    	    	        // 关联彩种子玩法
    	    			ComReq.lotteryChild(row.lotteryCode, function(result) {
    	    				Dic.lotteryChild = result;
    	    			});
    	    	        // 加载追号内容
    	    	        ChaseObj.loadChaseContent(row.orderAddCode);
    	    	        // 加载追号彩期
    	    	        ChaseObj.loadChaseIssue(row.orderAddCode);
    	     	});
     	    	ChaseObj.grid.unmask();
        	}
        },
        "loadChaseContent":function(orderAddCode) {
			var param = {
				orderAddCode:orderAddCode
	     	}
			ChaseObj.chaseContentGrid.load(param, function(data) {
				if(data.result.errorCode && data.result.errorCode != Code.success) {
					mini.alert(data.result.message);
				}
			});
		},
		"loadChaseIssue":function(orderAddCode) {
			var param = {
				orderAddCode:orderAddCode
	     	}
			ChaseObj.chaseIssueGrid.load(param, function(data) {
				if(data.result.errorCode && data.result.errorCode != Code.success) {
					mini.alert(data.result.message);
				}
			});
		},
		"closeLotCategory":function(e) {
			var obj = e.sender;
			obj.setText("");
			obj.setValue("");
			// 关联彩种
			ComReq.lottery("",function(result) {
				ChaseObj.lotteryCode.setData(result);
				// 关联奖级（按彩种对应）
				ChaseObj.searchPrizeGrade(ChaseObj.lotteryCode.getValue());
			});
		},
		"changeLotCategory":function() {
			 var code = ChaseObj.lotteryCategory.getValue();
			// 关联彩种
			 ComReq.lottery(code, function(result) {
				 ChaseObj.lotteryCode.setData(result);
				 // 关联奖级（按彩种对应）
				 ChaseObj.searchPrizeGrade(ChaseObj.lotteryCode.getValue());
			 });
		},
		"closeLotCode":function(e) {
			var obj = e.sender;
			obj.setText("");
			obj.setValue("");
			// 关联彩期(当前期开始的20期)
		    ChaseObj.issueCode.setData([]);
		    // 关联奖级（按彩种对应）
		    ChaseObj.searchPrizeGrade("");
		},
		"changeLotCode":function() {
			var code = ChaseObj.lotteryCode.getValue();
			if(code) {
				// 关联彩期(当前期开始的20期)
				ComReq.issue(code, function(result) {
					ChaseObj.issueCode.setData(result);
					ChaseObj.issueCode.select(0);
				});
				// 关联奖级（按彩种对应）
				ChaseObj.searchPrizeGrade(code);
			}
		},
		"onrowdblclick":function() {
			ChaseObj.editBtn.doClick();
		},
		/**
		 * 画汇总单元格内容
		 */
		"drawSummaryCell":function(e) {
			var index = e.column._index;
			// 按指定列统计的，统计列与汇总列对齐(一一对应)
			if(index == 0) {
				ChaseObj.other = e.result.other;
				e.cellHtml="统"
			} else if(index == 1) {
				e.cellHtml="计："
			} else if (e.field == "lotteryCode") { 
				e.cellHtml = "<input type=\"radio\" name=\"sis\" checked=\"checked\" onclick=\"ChaseObj.getChangeSis('0')\" />单页统计";
			} else if(e.field == "issueCode") {
				e.cellHtml = "<input type=\"radio\" name=\"sis\" onclick=\"ChaseObj.getChangeSis('1')\"/>条件统计";
			} else if(e.field == "addAmount") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;border-right:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisAddAmount\">" + numUtils.fmoney(e.result.other[0], 2) + "</span>";
			} else if(e.field == "preBonus") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisPreBonus\">" + numUtils.fmoney(e.result.other[1], 2) + "</span>";
			} else if(e.field == "aftBonus") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;border-right:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisAftBonus\">" + numUtils.fmoney(e.result.other[2], 2) + "</span>";
			}
		},
		"getChangeSis":function(type) {
			if(ChaseObj.other) {
				if("0" == type) {
					$("#sisAddAmount").html(numUtils.fmoney(ChaseObj.other[0], 2));
					$("#sisPreBonus").html(numUtils.fmoney(ChaseObj.other[1], 2));
					$("#sisAftBonus").html(numUtils.fmoney(ChaseObj.other[2], 2));
				} else {
					$("#sisAddAmount").html(numUtils.fmoney(ChaseObj.other[3], 2));
					$("#sisPreBonus").html(numUtils.fmoney(ChaseObj.other[4], 2));
					$("#sisAftBonus").html(numUtils.fmoney(ChaseObj.other[5], 2));
				}
			}
		},
		/**
		 * 渲染::已追/总期数（同列渲染）
		 */
		"renderChaseIssue":function(e) {
			var _record = e.record;
			/*var _statisInfo = _record.orderAddIssueStatis;
			if(!_statisInfo) {
				return "";
			}
			var _chased = _statisInfo.count1 + _statisInfo.count2 + _statisInfo.count3 + _statisInfo.count4 ; // 已追的期数
	        return _chased + "/" + _statisInfo.countAll;*/
			var _issueAmount = !_record.issueAmount ? 0 : _record.issueAmount; // 总期数
			var _hadIssue = !_record.hadIssue ? 0 : _record.hadIssue; // 已追期数
			return _hadIssue  + "/" + _issueAmount;
	     },
	     /**
	      * 渲染::渲染停追条件-奖项
	      */
	    "renderStopCondition":function(e) {
	    	var _record = e.record;
	    	var _val = e.value;
	    	
	    	return ChaseObj.handlerStopCondition(_record.lotteryCode, _record.stopType, _val);
	    },
	    "handlerStopCondition":function(lotteryCode, stopType, stopCondition) {
	    	if(stopType != 1) {
	    		return stopCondition;
	    	}
	    	// 仅 停追类型-奖项 做渲染  （后续扩展 按不同彩种的奖项来）
	    	/*if(lotteryCode == 100) { // 双色球奖项
	    		for (var i = 0; i < Dic.ssqPrizeDrade.length; i++) {
	    			if(Dic.ssqPrizeDrade[i].id == stopCondition) {
	    				return Dic.ssqPrizeDrade[i].text;
	    			}
				}
	    	}*/
	    	for (var i = 0; i < Dic.prizeGrade.length; i++) {
    			if(Dic.prizeGrade[i].id == stopCondition) {
    				return Dic.prizeGrade[i].text;
    			}
			}
	    	
	    	return stopCondition;
	    },
	    /**
	      * 渲染::渲染彩种子玩法
	      */
	    "renderPlayIntro" : function(e) {
	    	var _val = e.value;
	    	for (var i = 0; i < Dic.lotteryChild.length; i++) {
    			if(Dic.lotteryChild[i].id == _val) {
    				return Dic.lotteryChild[i].text;
    			}
			}
	    },
	    /**
	      * 渲染::渲染追号彩期相关操作
	      */
	    "renderChaseIssueOperate" : function(e) {
	    	var _record = e.record;
	    	var _link = "";
	    	var _payStatusEdit = ChaseObj.payStatus_edit.getValue();
	    	var _addStatusEdit = ChaseObj.addStatus_edit.getValue();
	    	// 有撤单权限并且追号计划支付状态=2（支付成功），追号计划状态=1（追号中）且追号彩期状态为 等待追号或追号失败的可撤单
	    	// 20170622 bug编号：4143，可能会有追号结束，但还有等待追号的期数(异常情况下回出现这种)，所以针对这种情况，下面不加追号计划状态的限制，以便出现该情况时可以有撤单按钮
	    	if(_hasCancelOrderPri && _payStatusEdit == 2 
	    			&& (_record.addStatus == 2 || _record.addStatus == 5)) {
	    		_link = '<a href="javascript:ChaseObj.cancelChaseIssue(\'' + _record.id + '\')">撤单</a>';
	    	}
	    	if(_record.orderCode) { // 有方案编号可查看
	    		_link +=  '&nbsp;&nbsp;<a href="javascript:MiniCom.openMainTab(\''+_record.orderCode+'\',\'方案详情\',\'ordermgr/basic?orderCode='+_record.orderCode+'\')">查看方案</a>';
	    	}
	    	return _link;
	    },
	    /**
	      * 撤单操作
	      */
	    "cancelChaseIssue" : function(chaseIssueId) {
	    	if(!chaseIssueId) {
	    		mini.alert("请选择一条记录撤销!");
	    		return;
	    	}
	    	 mini.confirm("确定要撤单吗?", "提示", function(e) {
				if (e == "ok") {
					ChaseObj.chaseIssueGrid.loading("操作中，请稍后......");
	                Cms.ajaxSend({
	         	    	url:"ordermgr/chase/issue/cancel",
	         	    	data:{chaseIssueId : chaseIssueId}
	         	    },function(res) {
	         	    	if (res.errorCode == Code.success) {
	                		mini.showTips({
	                			content : "操作成功",
	                			state : "success",
	    						x : "center",
	    						y : "center",
	    						timeout : 3000
	    					});
	                		ChaseObj.chaseIssueGrid.reload();
	    				} else {
	    					ChaseObj.chaseIssueGrid.unmask();
	    					mini.alert(res.message);
	    				}
	         	    });
				}
			});
	    },
	    /**
	      * 批量撤单操作
	      */
		"batCancelChaseIssue" : function() {
			var _lotteryCode = ChaseObj.lotteryCode.getValue();
			var _issueCode = ChaseObj.issueCode.getValue();
			if(!_lotteryCode || !_issueCode){
				mini.alert("请在下拉框中选择彩种和彩期！");
				return;
			}
			var _lotteryName = ChaseObj.lotteryCode.getText();
			mini.confirm("确定执行<span style=\"color: red;\">" + _lotteryName + 
					"</span>的<span style=\"color: red;\">"+_issueCode+"</span>期撤单吗?", "提示", function(e) {
				if(e == "ok") {
					var param = {
							url:"ordermgr/chase/issue/batchCancel",
							data:{
								"lotteryCode" : _lotteryCode, 
								"issueCode" : _issueCode
							},
							action:"post"
					}
					Cms.ajax(param,function() {
						mini.alert("后台已开始处理你的请求！","提示", function(){
							ChaseObj.grid.reload();
						});
					});
				}
			});
		},
	    "searchChaseIssue" : function() {
	    	ChaseObj.chaseIssueGrid.load(ChaseObj.getChaseIssueParamJson());
		},
	    "excelChaseIssue":function() {
	    	 var params = Cms.jsonParamStr(ChaseObj.getChaseIssueParamJson());
			 var url = "ordermgr/chase/issue/excel?" + params;
			 location = url;
	     },
	     "getChaseIssueParamJson":function() {
			 var params = {
				 orderAddCode : ChaseObj.orderAddCode_edit.getValue(),
				 addStatus :  ChaseObj.addIssueStatus.getValue()
			 };
			 return params;
		 },
		 /**
	      * 批量撤单：即单个计划剩余期撤单(当前计划所有等待追号与追号失败的期数做撤单处理; 撤单后，追号明细状态与追号计划状态都为“系统撤单”并退回购买金额至钱包相应账户)
	      */
	    "cancelChaseRemainIssue" : function() {
	    	var _orderAddCode = ChaseObj.orderAddCode_edit.getValue(); // 当前追号计划编号
	    	var _payStatusEdit = ChaseObj.payStatus_edit.getValue(); // 当前追号计划支付状态
	    	// 追号计划支付状态=2（支付成功），追号计划状态不做限制(理由同单期撤单，有些异常情况追号结束的计划还存在等待追号的期)
	    	if(_payStatusEdit != 2) {
	    		mini.alert("目标计划：" + _orderAddCode + ",不符合批量撤单条件！");
				return;
	    	}
	    	 mini.confirm("确定执行<span style=\"color: red;\">" + _orderAddCode + "</span>的批量撤单吗?", "提示", function(e) {
				if (e == "ok") {
					ChaseObj.chaseIssueGrid.loading("操作中，请稍后......");
	                Cms.ajaxSend({
	         	    	url:"ordermgr/chase/issue/cancelRemain",
	         	    	data:{orderAddCode : _orderAddCode}
	         	    },function() {
	         	    	ChaseObj.chaseIssueGrid.unmask();
	         	    	mini.alert("后台已开始处理你的请求！","提示", function(){
							ChaseObj.chaseIssueGrid.reload();
						});
	         	    });
				}
			});
	    },
	    /**
	      * 20171116 add 撤单中追号彩期退款：即单个计划中包含的撤单中（包括系统撤单中，停追撤单中，用户撤单中）彩期退款 ，若不包含则不执行操作
	      */
	    "cancellingRefund" : function() {
	    	var _orderAddCode = ChaseObj.orderAddCode_edit.getValue(); // 当前追号计划编号
	    	var _payStatusEdit = ChaseObj.payStatus_edit.getValue(); // 当前追号计划支付状态
	    	// 追号计划支付状态=2（支付成功），追号计划状态不做限制(理由同单期撤单，有些异常情况追号结束的计划还存在等待追号的期)
	    	if(_payStatusEdit != 2) {
	    		mini.alert("目标计划：" + _orderAddCode + ",不符合撤单中退款的条件！");
				return;
	    	}
	    	 mini.confirm("确定执行<span style=\"color: red;\">" + _orderAddCode + "</span>的撤单中退款吗?", "提示", function(e) {
				if (e == "ok") {
					ChaseObj.chaseIssueGrid.loading("操作中，请稍后......");
	                Cms.ajaxSend({
	         	    	url:"ordermgr/chase/issue/refund",
	         	    	data:{orderAddCode : _orderAddCode}
	         	    },function(res) {
	         	    	ChaseObj.chaseIssueGrid.unmask();
	         	    	if (res.errorCode == Code.success) {
	                		mini.alert("操作成功！","提示", function(){
								ChaseObj.chaseIssueGrid.reload();
							});
	    				} else {
	    					mini.alert(res.message);
	    				}
	         	    });
				}
			});
	    }
}

ChaseObj.init();