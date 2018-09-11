var Current = {
    "init" : function(code) {
        mini.parse();
        
        //窗体对象, form对象
        Current.activityRuleWindow = mini.get("activityRuleWindow");
        Current.activityConfigForm = new mini.Form("activityConfigForm");
        Current.activityRuleForm = new mini.Form("activityRuleForm");
        Current.datagrid = mini.get("addCodeDatagrid");
        Current.activityConfigId= mini.get("id");
        
        Current.activityCode = mini.get("activityCode");
        Current.lotteryCode = mini.get("lotteryCode");
        Current.lotteryChildCode = mini.get("lotteryChildCode");
        Current.urlForm = mini.get("url");
        Current.lotteryPassType = mini.get("lotteryPassType");
        Current.isDltAdd = mini.get("isDltAdd");
        Current.userType = mini.get("userType");
        Current.isCancel = mini.get("isCancel");
        Current.isAward = mini.get("isAward");
        Current.isFollowOrder = mini.get("isFollowOrder");
        Current.platfrom= mini.get("platfrom");
        //详情
        Current.ruleType = mini.get("ruleType");
        //规则明细        
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
		Current.giveNum=mini.get("giveNum");
		mini.get("type").setValue(6);
		Current.activityCode.setValue(code);
        Dic.userType = "[{'id':'1','text':'所有用户'},{'id':'4','text':'新用户'}]";
        Dic.ruleType = [{'id':'1','text':'期数'},{'id':'2','text':'优惠券'}];
        ComReq.dictionary("1005,0307,1007,0303,1102,1103,1105,0702,0901,1106,1107", function(result) {
        	Dic.isYesOrNo = result["0307"];   
        	Dic.isTrueOrFalse = result["1007"];
        	Dic.platfrom = result["1105"];
        	Current.userType.setData(Dic.userType);
        	Current.isDltAdd.setData(Dic.isYesOrNo);
        	Current.isCancel.setData(Dic.isYesOrNo);
            Current.isAward.setData(Dic.isYesOrNo); 
            Current.isFollowOrder.setData(Dic.isTrueOrFalse);
            Current.platfrom.setData(Dic.platfrom);
            
            Dic.lotteryCategory = result["0303"];
			Dic.redType = result["1102"];
			Dic.redType = result["1102"].slice(0,4);
			Dic.redStatus = result["1103"];
			Dic.limitPlatform = result["1105"];
			Dic.terminalPlatform = result["0702"];
			Dic.transType =  result["0901"];
			Dic.redSource =  result["1106"];			
			Current.redType.setData(Dic.redType);
			Current.redStatus.setData(Dic.redStatus);
			Current.userType.setData(Dic.userType);
            
			//添加优惠券窗口
			mini.get("limitPlatform").setData(Dic.limitPlatform);
			Current.redType.setData(Dic.redType);
			Dic.redStatus = Dic.redStatus.slice(0,3);
			Current.redStatus.setData(Dic.redStatus);
        	//规则配置
        	Current.ruleType.setData(Dic.ruleType);

        	//设置默认值
        	Current.ruleType.select(0);
        	Current.isDltAdd.select(1);
        	Current.isCancel.select(1);
            Current.isAward.select(1);
            Current.isFollowOrder.select(0);
            Current.userType.select(0);
        	
        	ComReq.lottery("", function(result){
        	    Dic.lotteryCode = result;
 		        Current.lotteryCode.setData(Dic.lotteryCode);
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
    "lotteryCodeChange": function(){
        var lotteryCode = Current.lotteryCode.getValue();
        ComReq.lotteryCheckChild(lotteryCode, function(result){
			Current.lotteryChildCode.setData(result);
		});
    },
    "merge": function(){
    	var form = Current.activityConfigForm
    	form.validate();
    	data = form.getData();     
    	data.operateActivityRuleVOList = Current.datagrid.getData(true, false);
    	if(data.operateActivityRuleVOList.length==0){
    		alert("请添加活动规则");
    		return;
    	}
    	for(var i=0;i<data.operateActivityRuleVOList.length;i++){
    		var obj = {orderId:i+1,id:data.operateActivityRuleVOList[i].id,lotteryChildCode:Current.lotteryChildCode.getValue()};
    		data.operateActivityRuleVOList[i] = obj;
    	}
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
		var row = {
			action : "post",
			url : "operatemgr/coupon/add",
			redBalance:0
		}
    	Current.activityRuleForm.setData({
			operation : "add",
			status : "0",
		});
        Current.activityRuleWindow.setTitle("新增活动配置规则");
        Current.activityRuleForm.setData(row);
        Current.ruleType.select(0);
    	Current.activityRuleWindow.show();
    	$("#couponFieldSet").hide();
    	mini.get("giveNum").setEnabled(true)

    },
    "edit": function() {
		var row = Current.datagrid.getSelected();
		if (row) {	
	    	Current.activityRuleForm.clear();
	    	row.operation = "edit";
	        Current.activityRuleWindow.setTitle("修改活动配置规则");
	        if(row.ruleType==2){
				$("#couponFieldSet").show();
				mini.get("giveNum").setEnabled(false)
			}else{
				$("#couponFieldSet").hide();
				mini.get("giveNum").setEnabled(true)
			}
	        setTimeout(function(){
	        	Current.activityRuleForm.setData(row);	
	        	if(row.ruleType==2||row.ruleType=='2'){
	        		Current.giveNum.setValue(null);
	    		}	        	
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
			    			url : "operatemgr/activity/addcode/del",
			    			action : "post",
			    			data : {id:data.id,ruleType:data.ruleType}
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
		debugger;
		var row = Current.datagrid.getSelected();
		var newRow = Current.activityRuleForm.getData(true,false);	
		var activityCode = Current.activityCode.getValue();
		var data = newRow;
		data.activityCode = activityCode;
		data.activityConfigId = Current.activityConfigId.getValue();
		data.orderId =newRow.operation == "edit"? row.orderId:Current.datagrid.getData().length+1;
		var operation = data.operation;
		delete data.operation;
		var param = {
    			url : "operatemgr/activity/addcode/merge",
    			action : "post",
    			data : data
    	}
		mini.confirm(param.tip || "确定保存?", param.tipTitle || "提示", function(e) {
			if (e == "ok") {
				MiniCom.mask(param.ingTip || "保存中。。。");
		    	Cms.ajax(param, function(data){
		    		if(data){
		    			mini.showTips({
		    				content: "操作成功",
							state: "success",
							x: "center",
							y: "center",
							timeout: 2000
						});
		    			Current.activityConfigId.setValue(data.activityConfigId);
		    			newRow.id=data.id;
		    			newRow.operation = operation;
		    			newRow.operation == "edit" ? Current.datagrid.updateRow(row, newRow) : Current.datagrid
		    					.addRow(newRow, Current.datagrid.data.length);
		    			Current.activityRuleWindow.hide(); 
		    		}
		    	})
			}
		})
		   	
    },
    "initDetail":function(activityCode){
    	var type =mini.get("type").getValue();
    	var param = {
    			url : "operatemgr/activity/config/detail",
    			action : "post",
    			data : {activityCode : activityCode,type:type}
    	}
    	Cms.ajax(param, function(data){
    		if(data){        
    			 var lotteryCode = data.lotteryCode;
    		     ComReq.lotteryCheckChild(lotteryCode, function(result){
    					Current.lotteryChildCode.setData(result);
    		    	 	Current.lotteryChildCode.setValue(data.operateActivityRuleBOList[0].lotteryChildCode);
    				});
		    	Current.activityConfigForm.setData(data);		   
		    	for(var i=0;i<data.operateActivityRuleBOList.length;i++){
		    		data.operateActivityRuleBOList[i].orderId = i+1;
		    	}
	    		Current.datagrid.setData(data.operateActivityRuleBOList);
	    		mini.get("type").setValue(6);
	    		Current.lotteryChildCode.setValue("10401,10402");
    		}
    	})
    },
   
    "closeConfigWindow": function() {
    	// 确定关闭
    	Current.ids = null;
    	window.CloseOwnerWindow();   	
	},
	"closeRuleWindow" : function(){
		Current.activityRuleWindow.hide();
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
	ruleTypeChange:function(){
		var ruleType =mini.get("ruleType").getValue();
		if(ruleType==2){
			$("#couponFieldSet").show();
			mini.get("giveNum").setEnabled(false)
		}else {
			mini.get("giveNum").setEnabled(true)
			$("#couponFieldSet").hide();
		}
	},
	"floatValid" : function(e){
        if (e.isValid) {
            var re = new RegExp("^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$");
            if (re.test(e.value)){
            	return true;
            }
            e.errorText = "只能输入数字,允许两位小数!!!";
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
}
