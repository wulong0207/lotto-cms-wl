var Current = {
    "init" : function(code) {
        mini.parse();
       
        //窗体对象, form对象
        Current.activityRuleWindow = mini.get("activityRuleWindow");
        Current.activityConfigForm = new mini.Form("activityConfigForm");
        Current.activityRuleForm = new mini.Form("activityRuleForm");
        Current.datagrid = mini.get("activityRule_datagrid");
        
        Current.activityCode = mini.get("activityCode");
        Current.preResType = mini.get("preResType");
        Current.realUserNum = mini.get("realUserNum");
        Current.offerNum = mini.get("offerNum");
        Current.minRechargeMoney = mini.get("minRechargeMoney");
        Current.userType = mini.get("userType");
        
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
	    Current.activityCode.setValue(code);
	    mini.get("type").setValue(8);
        
        Dic.userType = "[{'id':'1','text':'所有用户'},{'id':'4','text':'首次充值用户'},{'id':'5','text':'活动时间内首充用户'}]";
        Dic.preResType= "[{'id':'1','text':'同一真实用户'},{'id':'2','text':'手机号'}]";
        ComReq.dictionary("0303,1102,1103,1105,0702,0901,1106,1107", function(result) {
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
			Current.preResType.setData(Dic.preResType);
			//添加优惠券窗口
			mini.get("limitPlatform").setData(Dic.limitPlatform);
			Current.redType.setData(Dic.redType);
			Dic.redStatus = Dic.redStatus.slice(0,3);
			Current.redStatus.setData(Dic.redStatus);
			
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
   
    "merge": function(){
    	var form = Current.activityConfigForm
    	form.validate();
    	data = form.getData();     
    	data.operateActivityRuleVOList = Current.datagrid.getData(true, false);
    	if(data.operateActivityRuleVOList.length==0){
    		alert("请添加活动规则");
    		return;
    	}
    	debugger;
    	var preType = Current.preResType.getValue();
    	if(preType!=null&&preType!=""){
    		if(preType.indexOf("2") >-1){
    			data.isMobile = 1; 
    		}
    		if(preType.indexOf("1") > -1){
    			data.isRealUser = 1; 
    		}
    	}
		// 若有删除详情记录，则传id给controller
    	Current.ids && (data.ruleIds = Current.ids);
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
    	Current.activityRuleForm.clear();
    	//平台数据默认全选
		var row = {
			action : "post",
			url : "operatemgr/coupon/add",
			redBalance:0,
		}
    	Current.activityRuleForm.setData({
			operation : "add",
			status : "0",
		});
        Current.activityRuleWindow.setTitle("新增活动配置规则");
        Current.activityRuleForm.setData(row);
        Current.redStatus.select(0);
        Current.limitNum.setValue(1);
        Current.label.select(1);
        Current.labelChange();
    	Current.activityRuleWindow.show();
    },
    "edit": function() {
		var row = Current.datagrid.getSelected();
		if (row) {	
	    	Current.activityRuleForm.clear();
	    	Current.limitLotteryChange(row.limitLottery);
	    	row.operation = "edit";
	        Current.activityRuleWindow.setTitle("修改活动配置规则");
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
			mini.confirm("确定删除?", "提示", function(e) {
				if (e === "ok") {
					Current.datagrid.removeRows(rows)
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
    "mergeActivityRule": function() {
    	Current.activityRuleForm .validate();
		if (Current.activityRuleForm .isValid() == false) {
			mini.alert("表单数据不完整");
			return;
		}
		var row = Current.datagrid.getSelected();
		var newRow = Current.activityRuleForm.getData(true,false);
		var rebateType = newRow.rebateType;
		if(rebateType==2){
			var re = new RegExp("^[0-9]*[1-9][0-9]*$");
            if (!re.test(newRow.rebateNum)){
            	alert("本站返利金额百分比请输入整数");
            	return;
            } 
		}
		newRow.operation == "edit" ? Current.datagrid.updateRow(row, newRow) : Current.datagrid
				.addRow(newRow, Current.datagrid.data.length);
		Current.activityRuleWindow.hide();    	
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
		    	Current.activityConfigForm.setData(data);
	    		Current.datagrid.setData(data.operateActivityRuleBOList);
	    		mini.get("type").setValue(8);
	    		var str = '';
	    		if(data.isMobile !=null&&data.isMobile!=''){
	    			str='2'; 
	    		}
	    		if(data.isRealUser !=null&&data.isRealUser!=''){
	    			str = str==''?'1':'1,2';
	    			
	    		}
	    		Current.preResType.setValue(str);
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
