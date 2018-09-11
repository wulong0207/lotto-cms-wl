var Current = {
    "init" : function(code) {
        mini.parse();
        //窗体对象, form对象
        Current.activityCouponWindow = mini.get("activityCouponWindow");
        Current.activityCouponForm = new mini.Form("activityCouponForm");
        Current.activityLottoWindow = mini.get("activityLottoWindow");
        Current.activityLottoForm = new mini.Form("activityLottoForm");
        Current.activityConfigForm = new mini.Form("activityConfigForm");
        Current.activityCouponDatagrid = mini.get("activityCoupon_datagrid");
        Current.activityLottoDatagrid = mini.get("activityLotto_datagrid");

        Current.activityCode = mini.get("activityCode");
        Current.userType = mini.get("userType");
        Current.configType = mini.get("configType");
        Current.configType = mini.get("configType");
        Current.type = mini.get("type");
        Current.type.setValue(9);
        
        Current.limitLottery = mini.get("limitLottery");
		Current.limitPlatform=mini.get("limitPlatform");
		Current.limitLotteryChild = mini.get("limitLotteryChild");
		Current.limitLotteryChildType = mini.get("limitLotteryChildType");
		Current.operateLotteryId = mini.get("operateLotteryId");
		Current.label=mini.get("label");
		Current.redLabel=mini.get("redLabel");
		
		Current.lotteryCode = mini.get("lotteryCode");
		Current.lotteryChildCode = mini.get("lotteryChildCode");

    	Dic.pickType = [{id:'1',text:'机选'},{id:'2',text:'自选'}];

        ComReq.dictionary("0303,1011,1012,1102,1103,1105,0606,0607", function(result) {
        	Dic.lotteryCategory = result["0303"];
        	Dic.userType = result["1011"];
        	Dic.configType = result["1012"];
        	Dic.redType = result["1102"].slice(0,4);
        	Dic.redStatus = result["1103"].slice(0,3);
        	Dic.limitPlatform = result["1105"];

        	//规则配置
        	Current.userType.setData(Dic.userType);
        	Current.configType.setData(Dic.configType);
        	mini.get("redType").setData(Dic.redType);
        	mini.get("redStatus").setData(Dic.redStatus);
        	Current.limitPlatform.setData(Dic.limitPlatform);
        	
        	Dic.contentType = result["0606"];
        	Dic.buyType = result["0607"];
        	mini.get("contentType").setData(Dic.contentType);
        	mini.get("buyType").setData(Dic.buyType);
        	mini.get("pickType").setData(Dic.pickType);
        	
        	ComReq.lottery("", function(result){
				Dic.OperateLotteryId = result.concat();
				Dic.lotteryCode = result;
				var obj = new Object();
				obj.id = "0";
				obj.text = "客户端下载";
				Dic.OperateLotteryId.unshift(obj);
				Current.limitLottery.setData(Dic.lotteryCategory.concat(result));
				Current.operateLotteryId.setData(Dic.OperateLotteryId);
				
				Current.lotteryCode.setData(Dic.lotteryCode);
				
				//设置默认值
				Current.initDetail(code);
			})

        });


    },
    "merge": function(){
    	Current.type.setValue(9);
    	var form = Current.activityConfigForm;
    	debugger;
    	form.validate();
    	data = form.getData();
        data.operateActivityRuleVOList = Current.activityCouponDatagrid.getData(true, false);
        data.operateActivityLottoRuleVOList = Current.activityLottoDatagrid.getData(true, false);
		// 若有删除详情记录，则传id给controller
        Current.ruleIds && (data.ruleIds = Current.ruleIds);
    	Current.lottoRuleIds && (data.lottoRuleIds = Current.lottoRuleIds);
		var param = {
				url : data.url,
				action : "post",
				data : data
		};
        Cms.saveDataStringify(param, function(){
            setTimeout(function(){
                Current.closeConfigWindow();
            },300)
        })
    },
    "add": function () {
    	var configType = Current.configType.getValue();
    	var acitivityRuleWindow, acitivityRuleForm;
    	if(configType == "") {
    		mini.alert("请先选择赠送类型");
    		return;
    	}else if(configType == 1) {//优惠券
    		acitivityRuleWindow = Current.activityCouponWindow;
    		acitivityRuleForm = Current.activityCouponForm;
    	}else if(configType == 2) {//彩票
    		acitivityRuleWindow = Current.activityLottoWindow;
    		acitivityRuleForm = Current.activityLottoForm;
    	}
    	acitivityRuleForm.clear();
    	acitivityRuleForm.setData({
            operation : "add",
            status : "0",
        });
    	acitivityRuleWindow.setTitle("新增活动配置规则");
    	acitivityRuleWindow.show();
    },
    "edit": function() {
        var configType = Current.configType.getValue();
        if(configType == 1) {
        	Current.editActivityCoupon();
        }else if(configType == 2) {
        	Current.editActivityLotto();
        }
    },
    "del" : function(){
    	var configType = Current.configType.getValue();
        if(configType == 1) {
        	Current.delActivityCoupon();
        }else if(configType == 2) {
        	Current.delActivityLotto();
        }
    },
    "editActivityCoupon": function() {
    	var row = Current.activityCouponDatagrid.getSelected();;
        if (row) {
            Current.activityCouponForm.clear();
            Current.limitLotteryChange(row.limitLottery);
            row.operation = "edit";
            Current.activityCouponWindow.setTitle("修改活动配置规则");
            setTimeout(function(){
	        	Current.activityCouponForm.setData(row);
	        	Current.activityCouponWindow.show();
    		},200)
        } else {
            mini.alert("请选择一行数据");
        }
    },
    "editActivityLotto": function() {
    	var row = Current.activityLottoDatagrid.getSelected();;
        if (row) {
            Current.activityLottoForm.clear();
            Current.lotteryCode.setValue(row.lotteryCode);
            Current.lotteryCodeChange();
            row.operation = "edit";
            Current.activityLottoWindow.setTitle("修改活动配置规则");
            setTimeout(function(){
            	Current.activityLottoForm.setData(row);
            	Current.activityLottoWindow.show();
    		},200)
        } else {
            mini.alert("请选择一行数据");
        }
    },
    "delActivityCoupon": function() {
    	var rows = Current.activityCouponDatagrid.getSelecteds();
        if (rows) {
            mini.confirm("确定删除?", "提示", function(e) {
                if (e === "ok") {
                    Current.activityCouponDatagrid.removeRows(rows)
                    Current.ruleIds = Current.ruleIds || [];
                    $.each(rows, function(index, row) {
                        // 记录要删除的id
                        Current.ruleIds.push(row.id);
                    });
                    if(Current.activityCouponDatagrid.getList().length == 0) {
	                	Current.configType.setEnabled(true);
	                }
                }
            });
        } else {
            mini.alert("请选择一行数据");
        }
    },
    "delActivityLotto": function() {
    	var rows = Current.activityLottoDatagrid.getSelecteds();
        if (rows) {
            mini.confirm("确定删除?", "提示", function(e) {
                if (e === "ok") {
                    Current.activityLottoDatagrid.removeRows(rows)
                    Current.lottoRuleIds = Current.lottoRuleIds || [];
                    $.each(rows, function(index, row) {
                        // 记录要删除的id
                        Current.lottoRuleIds.push(row.id);
                    });
                    if(Current.activityLottoDatagrid.getList().length == 0) {
	                	Current.configType.setEnabled(true);
	                }
                }
            });
        } else {
            mini.alert("请选择一行数据");
        }
    },
    "mergeActivityCoupon": function() {
        Current.activityCouponForm .validate();
        if (Current.activityCouponForm .isValid() == false) {
            mini.alert("表单数据不完整");
            return;
        }
        var row = Current.activityCouponDatagrid.getSelected();
        var newRow = Current.activityCouponForm.getData(true,false);
        if(newRow.operation == "add") {
        	Current.configType.setEnabled(false);
        	Current.activityCouponDatagrid.addRow(newRow, Current.activityCouponDatagrid.data.length)
        }else if(newRow.operation == "edit") {
        	Current.activityCouponDatagrid.updateRow(row, newRow);
        }
        Current.activityCouponWindow.hide();
    },
    "mergeActivityLotto": function() {
        Current.activityLottoForm .validate();
        if (Current.activityLottoForm .isValid() == false) {
            mini.alert("表单数据不完整");
            return;
        }
        
        var row = Current.activityLottoDatagrid.getSelected();
        var newRow = Current.activityLottoForm.getData(true,false);
        var lotteryChildCodeSelected = Current.lotteryChildCode.getSelected();
        newRow.lotteryChildName = lotteryChildCodeSelected.text;
        if(newRow.operation == "add") {
        	Current.configType.setEnabled(false);
        	Current.activityLottoDatagrid.addRow(newRow, Current.activityCouponDatagrid.data.length)
        }else if(newRow.operation == "edit") {
        	Current.activityLottoDatagrid.updateRow(row, newRow);
        }
        Current.activityLottoWindow.hide();
    },
    "closeConfigWindow": function() {
    	// 确定关闭
    	Current.ids = null;
    	window.CloseOwnerWindow();   	
	},
    "closeCouponWindow" : function(){
        Current.activityCouponWindow.hide();
    },
    "closeLottoWindow" : function(){
        Current.activityLottoWindow.hide();
    },
    "initDetail":function(activityCode){
        var param = {
            url : "operatemgr/activity/config/detail",
            action : "post",
            data : {activityCode : activityCode, type: 9}
        }
        Cms.ajax(param, function(data) {
            Current.activityCode.setValue(activityCode);
            if (!data){
               return;
            }
            data.url = "operatemgr/activity/config/merge";
            Current.activityConfigForm.setData(data);
            if(data.configType) {
            	if(data.configType == 1) {
            		Current.activityLottoDatagrid.setVisible(false);
            		if(data.operateActivityRuleBOList && data.operateActivityRuleBOList.length > 0) {
            			Current.activityCouponDatagrid.setData(data.operateActivityRuleBOList);
                    	Current.configType.setEnabled(false);
            		}
            	}else if(data.configType == 2) {
            		Current.activityCouponDatagrid.setVisible(false);
            		if(data.operateActivityLottoRuleBOList && data.operateActivityLottoRuleBOList.length > 0) {
            			Current.activityLottoDatagrid.setData(data.operateActivityLottoRuleBOList);
                    	Current.configType.setEnabled(false);
            		}
            	}
            }else{
            	Current.configType.select(0);
            	Current.activityLottoDatagrid.setVisible(false);
            }
        })
    },
	"onMoneyValidation" : function(e){
        if (e.isValid) {
            var re = new RegExp("^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$");
            var re1 = new RegExp("^[0-9]*[1-9][0-9]*$");
            if (re.test(e.value)||re1.test(e.value)){
            	return true;
            } 
            e.errorText = "只能输入数字," +
            		"!!!";
            e.isValid = false;            
            return false;        	
        }	
	},
	"labelChange":function(labelVal){	
		if(labelVal==null){
			var labelVal = Current.label.getValue();
		}
		Current.redLabel.setValue('');
		switch (labelVal) {
		case '1':
			Current.redLabel.set({
				enabled:false
			})
			return;
		case '4':	
			Current.redLabel.set({
				enabled:true
			})
			return;
		case '2':
		case '3':
			Current.redLabel.set({
				enabled:false
			})	
			Current.redLabel.setValue(Current.label.getText());
			return;
		}
	},
	"limitLotteryChange" : function(lotteryCodeStr){
		if(lotteryCodeStr==undefined||lotteryCodeStr==null){
			lotteryCodeStr = Current.limitLottery.getValue();
			//处理推荐入口数据
			var lotteryArr = Current.limitLottery.getSelecteds();
			var obj = new Object();
			obj.id = "0";
			obj.text = "客户端下载";
			lotteryArr.unshift(obj);
			Current.operateLotteryId.setData(lotteryArr);
		}
		switch (lotteryCodeStr) {
		case "300":
		case "301":
		case "306":
			Current.limitLotteryChildType.setData("[{'id':'1','text':'3串1'},{'id':'2','text':'2串1'},{'id':'3','text':'单关'}]");				
			break;
		case "307":
			Current.limitLotteryChildType.setData("[{'id':'1','text':'3串1'}]");	
			break;
		default :
			Current.limitLotteryChildType.setData();	
			break;
		}
		
		ComReq.lotteryCheckChild(lotteryCodeStr, function(result){
			Current.limitLotteryChild.setData(result);
		});
		
	},
	"limitLotteryChildChange" : function(){
		var arr = ['300', '301', '306','307'];
		var lotteryCodeStr = Current.limitLottery.getValue();
	},
	"lotteryCodeChange" : function() {
		var lotteryCode = Current.lotteryCode.getValue();
		ComReq.lotteryChild(lotteryCode, function(result){
			Current.lotteryChildCode.setData(result);
		});
	},
	"configTypeChange" : function() {
		var configType = Current.configType.getValue();
		if(configType == 1) {//优惠券
			Current.activityCouponDatagrid.setVisible(true);
			Current.activityLottoDatagrid.setVisible(false);
		}else if(configType == 2) {//彩票
			Current.activityCouponDatagrid.setVisible(false);
			Current.activityLottoDatagrid.setVisible(true);
		}
	}
}
