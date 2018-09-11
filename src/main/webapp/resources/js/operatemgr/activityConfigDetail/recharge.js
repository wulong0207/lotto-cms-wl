var Current = {
    "init" : function(code) {
        mini.parse();
        Current.activityRuleWindow = mini.get("activityRuleWindow");
        Current.activityConfigForm = new mini.Form("activityConfigForm");
        Current.activityRuleForm = new mini.Form("activityRuleForm");
        Current.activityCouponTempForm = new mini.Form("activityCouponTempForm");
        Current.activityCouponTempWindow = mini.get("activityCouponTempWindow");
        
        Current.couponDatagrid = mini.get("activityRuleCoupon_datagrid");
        Current.datagrid = mini.get("activityRule_datagrid");

        Current.userType = mini.get("userType");
        Current.platfrom = mini.get("platfrom");
        Current.activityCode = mini.get("activityCode");
        Current.month = mini.get("installment");
        Current.offerNum = mini.get("offerNum");
        Current.rebate = mini.get("installmentMoney");
        Current.offerMoney = mini.get("offerMoney");
        Current.winStartMoney = mini.get("winStartMoney");
        Current.winEndMoney = mini.get("winEndMoney");
        Current.activityConfigId= mini.get("id");
        Current.isActivityPage = mini.get("isActivityPage");

        Current.redType = mini.get("redType");
		Current.redStatus = mini.get("redStatus");
		Current.minSpendAmount = mini.get("minSpendAmount");
		Current.activeEndTime = mini.get("activeEndTime");
		Current.limitLottery = mini.get("limitLottery");
		Current.limitPlatform=mini.get("limitPlatform");
		Current.limitLotteryChild = mini.get("limitLotteryChild");
		Current.limitLotteryChildType = mini.get("limitLotteryChildType");
		Current.operateLotteryId = mini.get("operateLotteryId");
		Current.limitNum = mini.get("limitNum");
		Current.label=mini.get("label");
		Current.redLabel=mini.get("redLabel");
	    Current.activityCode.setValue(code);
	    Current.type = 4;
	    Dic.userType = "[{'id':'1','text':'所有用户'},{'id':'4','text':'首次充值用户'}]";
        ComReq.dictionary("1006,1009,1008,1105,0303,1102,1103,0702,0901,1106,1107,0307", function (result) {
        	Dic.platfrom = result["1105"];
        	Dic.redType = result["1102"];
			Dic.redType = result["1102"].slice(0,4);
			Dic.redStatus = result["1103"];
			Dic.limitPlatform = result["1105"];
			Dic.redSource =  result["1106"];
			Dic.isYesOrNo = result["0307"]; 
			Current.isActivityPage.setData(Dic.isYesOrNo);
			Current.redType.setData(Dic.redType);
			Current.redStatus.setData(Dic.redStatus);
			Current.userType.setData(Dic.userType);
        	Current.userType.setData(Dic.userType); 
        	Current.platfrom.setData(Dic.platfrom);
        	//添加优惠券窗口
			mini.get("limitPlatform").setData(Dic.limitPlatform);
			Current.redType.setData(Dic.redType);
			Dic.redStatus = Dic.redStatus.slice(0,3);
			Current.redStatus.setData(Dic.redStatus);
        	//设置默认值
        	Current.userType.select(0);
            
            ComReq.lottery("", function(result){
				Dic.OperateLotteryId = result.concat();
				Dic.lotteryCode = Dic.lotteryCategory.concat(result);
				var obj = new Object();
				obj.id = "0";
				obj.text = "客户端下载";
				Dic.OperateLotteryId.unshift(obj);
				Current.limitLottery.setData(Dic.lotteryCode);
				Current.operateLotteryId.setData(Dic.OperateLotteryId);
				
				Current.initDetail(code);
			});	
        });
    },
	//初始化详情
    "initDetail":function(activityCode){
        var param = {
            url : "operatemgr/activity/config/detail",
            action : "post",
            data : {activityCode : activityCode,type:4}
        }
        Cms.ajax(param, function(data){
            if(data){
                Current.activityConfigForm.setData(data);
                Current.datagrid.setData(data.operateActivityRuleBOList);
            }else{
                Current.activityCode.setValue(activityCode);
            }
        })
    },
    "merge": function(){
    	var form = Current.activityConfigForm
    	form.validate();
    	data = form.getData();     
    	if(Current.datagrid.getData(true, false).length==0){
    		alert("请添加活动规则");
    		return;
    	}
    	data.type=Current.type;
		// 若有删除详情记录，则传id给controller
    	Current.ids && (data.ruleIds = Current.ids);    
    	var param = {
				url : "operatemgr/activity/config/merge",
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
    	Current.activityRuleForm.clear();
    	//平台数据默认全选		
    	Current.activityRuleForm.setData({
			operation : "add",
			status : "0",
		});
        Current.activityRuleWindow.setTitle("新增活动配置规则");
    	Current.activityRuleWindow.show();
    },
    "edit": function() {
		var row = Current.datagrid.getSelected();
		if (row) {	
	    	Current.activityRuleForm.clear();        
	    	row.operation = "edit";
	        Current.activityRuleWindow.setTitle("修改活动配置规则");
	        var param = {
	                url : "operatemgr/activity/recharge/detail",
	                action : "post",
	                data : {id : row.id}
	            }
	            Cms.ajax(param, function(data){
	                if(data){
	                    Current.couponDatagrid.setData(data);
	                }
	            })
	        setTimeout(function(){
	          Current.activityRuleForm.setData(row);		        		        	
	          Current.activityRuleWindow.show();
    		},200) 	    	    		    	
		} else {
			mini.alert("请选择一行数据");
		}
    },
    "del" : function(){
		var rows = Current.datagrid.getSelecteds();
		if (rows) {
			var data = rows[0];
			mini.confirm("确定删除?", "提示", function(e) {
				if (e === "ok") {					
					var param = {
			    			url : "operatemgr/activity/rule/del",
			    			action : "post",
			    			data : {id:data.id,typeCode:data.typeCode,type:data.type}
			    	}
			    	Cms.ajax(param, function(data){ 
			    		mini.showTips({
							content: "操作成功",
							state: "success",
							x: "center",
							y: "center",
							timeout: 2000
						});
		    			Current.datagrid.removeRows(rows)
						Current.ids = Current.ids || [];
						$.each(rows, function(index, row) {
							// 记录要删除的id
							Current.ids.push(row.id);
						})
			    	})					
				}
			});
		} else {
			mini.alert("请选择一行数据");
		}    	
    },
    "mergeActivityRule": function() {
    	Current.activityRuleForm .validate();
		if (Current.activityRuleForm .isValid() == false) {
			mini.alert("表单数据不完整");
			return;
		}      	
		var row = Current.datagrid.getSelected();
		var newRow = Current.activityRuleForm.getData(true,false);	
		var activityCode = Current.activityCode.getValue();
		var data = newRow
		data.couponTemp = Current.couponDatagrid.getData(true, false);
    	if(data.couponTemp.length==0){
    		alert("请添加活动规则");
    		return;
    	};
		var operation = data.operation;
		data.activityCode = activityCode;
		data.activityConfigId = Current.activityConfigId.getValue();
		debugger;
		var param = {
				headers: { 
					'Accept': 'application/json',
					'Content-Type': 'application/json' 
				},
    			url : "operatemgr/activity/recharge/merge",
    			type : "post",
    			data : JSON.stringify(data)
    	}
		mini.confirm(param.tip || "确定保存?", param.tipTitle || "提示", function(e) {
			if (e == "ok") {
				MiniCom.mask(param.ingTip || "保存中。。。");
				$.ajax(param).done(
					function(res) {
						MiniCom.unmask();
						if(res.errorCode == "10001"){
			    			mini.showTips({
			    				content: "操作成功",
								state: "success",
								x: "center",
								y: "center",
								timeout: 2000
							});
			    			var data= res.data;
			    			Current.activityConfigId.setValue(data.activityConfigId);
			    			newRow.id=data.id;
			    			newRow.operation = operation;
			    			newRow.operation == "edit" ? Current.datagrid.updateRow(row, newRow) : Current.datagrid
			    					.addRow(newRow, Current.datagrid.data.length);
			    			Current.activityRuleWindow.hide(); 
			    		}else{
			    			mini.alert(res.message); 
			    		}
				}).fail(
						function(jqXHR, textStatus, errorThrown) {
							debugger;
							MiniCom.unmask();
							alert(jqXHR.responseText);
						}
				);
			 }
		});	   	  
    },
	//关闭
    "closeConfigWindow": function() {
    	// 确定关闭
    	Current.ids = null;
    	window.CloseOwnerWindow();   	
	},
	//取消
	"closeRuleWindow" : function(){
		Current.activityRuleWindow.hide();
	},
	
	//新增
    "addCoupon": function () {
    	Current.activityCouponTempForm.clear();
    	Current.activityCouponTempForm.setData({
			operation : "add",
		});
        Current.activityCouponTempWindow.setTitle("新增活动配置规则");
        Current.redStatus.select(0);
        Current.limitNum.setValue(1);
        Current.label.select(1);
        Current.labelChange();
    	Current.activityCouponTempWindow.show();
    },
	//修改
    "editCoupon": function() {
		var row = Current.couponDatagrid.getSelected();
		if (row) {	
	    	Current.activityCouponTempForm.clear();
	    	row.operation = "edit";
	    	Current.limitLotteryChange(row.limitLottery);
	    	Current.activityCouponTempForm.setData(row);
	        Current.activityCouponTempWindow.setTitle("修改活动配置规则");
	    	Current.activityCouponTempWindow.show();
		} else {
			mini.alert("请选择一行数据");
		}
    },
	//删除
    "delCoupon" : function(){
		var rows = Current.couponDatagrid.getSelecteds();
		if (rows) {
			mini.confirm("确定删除?", "提示", function(e) {
				if (e === "ok") {
					Current.couponDatagrid.removeRows(rows)
					Current.ids = Current.ids || [];
					$.each(rows, function(index, row) {
						// 记录要删除的id
						Current.ids.push(row.id);
					})
				}
			});
		} else {
			mini.alert("请选择一行数据");
		}    	
    },
  //确认
    "mergeActivityCoupon": function() {
    	Current.activityCouponTempForm .validate();
		if (Current.activityCouponTempForm .isValid() == false) {
			mini.alert("表单数据不完整");
			return;
		}
		var row = Current.couponDatagrid.getSelected();
		var newRow = Current.activityCouponTempForm.getData(true,false);
		newRow.configType=3;
		var operation = newRow.operation;
		delete newRow.operation;
		operation == "edit" ? Current.couponDatagrid.updateRow(row, newRow) : Current.couponDatagrid
				.addRow(newRow, Current.couponDatagrid.data.length);
		Current.activityCouponTempWindow.hide();    	
    },
	//取消
	"closeCouponWindow" : function(){
		Current.activityCouponTempWindow.hide();
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
}
