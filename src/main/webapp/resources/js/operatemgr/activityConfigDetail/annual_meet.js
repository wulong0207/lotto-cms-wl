var Current = {
    "init" : function(code) {
        mini.parse();
        
        //窗体对象, form对象
        Current.activityConfigForm = new mini.Form("activityConfigForm");
        Current.datagrid = mini.get("addCodeDatagrid");
        Current.activityConfigId= mini.get("id");
        Current.addId= mini.get("addId");
        Current.activityCode = mini.get("activityCode");
        Current.lotteryCode = mini.get("lotteryCode");
        Current.lotteryChildCode = mini.get("lotteryChildCode");
        Current.urlForm = mini.get("url");
        Current.lotteryPassType = mini.get("lotteryPassType");
        //彩期
        Current.giveIssue = mini.get("giveIssue");
        Current.giveNum = mini.get("giveNum");       
        //红包     
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
		mini.get("type").setValue(14);
		Current.activityCode.setValue(code);
        ComReq.dictionary("1005,0307,1007,0303,1102,1103,1105,0702,0901,1106,1107", function(result) {        	
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
            
			//添加优惠券窗口
			mini.get("limitPlatform").setData(Dic.limitPlatform);
			Current.redType.setData(Dic.redType);
			Dic.redStatus = Dic.redStatus.slice(0,3);
			Current.redStatus.setData(Dic.redStatus);

        	//设置默认值
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
      //设置默认值
    	ComReq.issue(lotteryCode, function(result){
    	    Dic.lotteryCode = result;
		    Current.giveIssue.setData(Dic.lotteryCode);		
		});	
    },
    "merge": function(){
    	var form = Current.activityConfigForm
    	form.validate();
    	data = form.getData();
    	var configData={
    		activityCode:data.activityCode,
    		lotteryCode:data.lotteryCode,
    		id:data.id,
    		type:data.type,
    		isCancel:0
    	};
    	var addData = {
    			id:data.addId,
    			activityConfigId:data.id,
    			lotteryChildCode:data.lotteryChildCode,
    			ruleType:1,
    			addNum:1,
    			operateActivityCouponId:data.operateActivityCouponId,
    			giveNum:data.giveNum,
    			giveIssue:data.giveIssue,
    			redType:data.redType,
    			redName:data.redName,
    			redStatus:data.redStatus,
    			redValue:data.redValue,
    			minSpendAmount:data.minSpendAmount,
    			ectivityDay:data.ectivityDay,
    			activeEndTime:data.activeEndTime,
    			limitPlatform:data.limitPlatform,
    			limitLotteryChildType:data.limitLotteryChildType,
    			limitNum:data.limitNum,
    			redLabel:data.redLabel,
    			operateLotteryId:data.operateLotteryId,
    			limitLottery:data.limitLottery,
    			limitLotteryChild:data.limitLotteryChild
    	};
    	configData.operateActivityAddedVO=addData;  
    	var param = {
				url : "operatemgr/activity/config/merge",
				action : "post",
				data : configData
			};    	
    	Cms.saveDataStringify(param, function(){
    		setTimeout(function(){
    			Current.closeConfigWindow(); 
    			
    		},300) 		
    	})
    },    
    "send": function () {
    	var inputFile = $("#file")[0];
	 	var file = mini.get("file");
	 	var fileVal = file.getValue();
	 	var giveIssue = Current.giveIssue.getValue();
	 	var ruleType =mini.get("sendRed").getValue();
	 	var isSendRed = ruleType==1?1:0;
	 	if(StringUtils.isBank(giveIssue)){
	 		mini.alert("请选择彩期!!!");
			return;
	 	}
	 	var data = {
	 			activityCode:Current.activityCode.getValue(),
	 			giveIssue:giveIssue,
	 			isSendRed:isSendRed,
	 			addId:Current.addId.getValue()
	 	};
		if(StringUtils.isBank(fileVal)){
			mini.alert("请选择文件上传用户账号!!!");
			return;
		}	 
		 $.ajaxFileUpload({
	            url: "operatemgr/activity/annualmeet/send",   //用于文件上传的服务器端请求地址
	            fileElementId: inputFile,           //文件上传域的ID
	            action : "post",
				data : data,
	            dataType:'json',
	            success: function (data, status)    //服务器成功响应处理函数
	            {
	            	if(data.errorCode == Code.success){
	            		mini.showTips({
							content: data.message,
							state: "success",
							x: "center",
							y: "center",
							timeout: 2000
						});
	            	}else{
	            		mini.alert(data.message);
	            	}
	            },
	            error: function (data, status,e)   //服务器响应失败处理函数
	            {
	                mini.alert(e);
	            },
	            complete: function () {
	                var jq = $(id);
	                jq.before(inputFile);
	                jq.remove();
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
    		     var obj={};
    		     $.extend(true, obj, data, data.operateActivityRuleBOList[0])
    		     obj.addId=data.operateActivityRuleBOList[0].id;
    		     obj.id=data.id;
		    	Current.activityConfigForm.setData(obj);
		    	 var lotteryCode = data.lotteryCode;
    		     ComReq.lotteryCheckChild(lotteryCode, function(result){
    					Current.lotteryChildCode.setData(result);
    		    	 	Current.lotteryChildCode.setValue(data.operateActivityRuleBOList[0].lotteryChildCode);
    				});
    		     ComReq.issue(lotteryCode, function(result){
    		    	    Dic.lotteryCode = result;
    				    Current.giveIssue.setData(Dic.lotteryCode);	
    		    	 	Current.giveIssue.setValue(data.operateActivityRuleBOList[0].giveIssue);
    				});	
	    		mini.get("type").setValue(14);
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
		var ruleType =mini.get("sendRed").getValue();
		if(ruleType==1){
			$("#couponFieldSet").show();
		}else {
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
