var Current = {
    "init" : function(code) {
        mini.parse();
       
        //窗体对象, form对象
        Current.activityRuleWindow = mini.get("activityRuleWindow");
        Current.activityConfigForm = new mini.Form("activityConfigForm");
        Current.activityRuleForm = new mini.Form("activityRuleForm");
        Current.datagrid = mini.get("prizeAwardDatagrid");
        
        Current.activityCode = mini.get("activityCode");
        Current.preResType = mini.get("preResType");
        Current.realUserNum = mini.get("realUserNum");
        Current.offerNum = mini.get("offerNum");
        Current.minRechargeMoney = mini.get("minRechargeMoney");
        Current.userType = mini.get("userType");
        Current.awardImg = mini.get("awardImg");
        
        //规则明细        
        Current.redType = mini.get("redType");
		Current.minSpendAmount = mini.get("minSpendAmount");
		Current.activeEndTime = mini.get("activeEndTime");
		Current.operateLotteryId = mini.get("operateLotteryId");
		Current.awardType = mini.get("awardType");
		Current.typeCode= mini.get("typeCode");
		Current.addChannelId = mini.get("channelId_add");
		Current.activityConfigId= mini.get("id");
	    Current.activityCode.setValue(code);
	    mini.get("type").setValue(12);
        
        Dic.userType = "[{'id':'1','text':'所有用户'},{'id':'4','text':'首次充值用户'}]";
        Dic.preResType= "[{'id':'1','text':'同一真实用户'},{'id':'2','text':'手机号'}]";
        ComReq.dictionary("0002,0303,1102,1103,1105,0702,0901,1106,1107,1110", function(result) {
        	Dic.lotteryCategory = result["0303"];
			Dic.redType = result["1102"];
			Dic.redType = result["1102"].slice(0,4);
			Dic.terminalPlatform = result["0702"];
			Dic.transType =  result["0901"];
			Dic.redSource =  result["1106"];
			Dic.awardType=result["1110"];
			Current.awardType.setData(Dic.awardType);			
			Current.redType.setData(Dic.redType);
			Current.userType.setData(Dic.userType);
			Current.preResType.setData(Dic.preResType);
			//添加优惠券窗口
			Current.redType.setData(Dic.redType);			
			Current.initActivityStatusTrue();
			ComReq.lottery("", function(result){
				Dic.OperateLotteryId = result.concat();
				Dic.lotteryCode = Dic.lotteryCategory.concat(result);
				var obj = new Object();
				obj.id = "0";
				obj.text = "客户端下载";
				Dic.OperateLotteryId.unshift(obj);
				Current.operateLotteryId.setData(Dic.OperateLotteryId);
				
				Current.initDetail(code);
			});	
			ComReq.channelTree("", function(result){
				Current.addChannelId.loadList(result, "id", "pid");
			})
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
    	var preType = Current.preResType.getValue();
    	if(preType!=null&&preType!=""){
    		if(preType.indexOf("2") >-1){
    			data.isMobile = 1; 
    		}
    		if(preType.indexOf("1") > -1){
    			data.isRealUser = 1; 
    		}
    	}
    	for(var i=0;i<data.operateActivityRuleVOList.length;i++){
    		var obj = {orderId:i+1,id:data.operateActivityRuleVOList[i].id};
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
    	Current.cleanImage();
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
        Current.awardType.select(0);
    	Current.activityRuleWindow.show();
    	mini.get("typeCode").setEnabled(false)
    	document.getElementById('awardColor').jscolor.fromString('#000000');
    	$("#couponFieldSet").show();
    },
    "openImage": function(){
        var lotterytypeCatalogue = 'lotterymgr/type';
        Cms.imageManage(lotterytypeCatalogue,function(data){
            Current.setImage(data);
        });
    },
	"cleanImage":function(){
        $("#img").attr("src","");
        Current.awardImg.setValue("");
    },
    "setImage": function(data){
        $("#img").attr("src",data.url);
        Current.awardImg.setValue(data.dir);
    },
    "edit": function() {
        Current.cleanImage();
		var row = Current.datagrid.getSelected();
		if (row) {	
	    	Current.activityRuleForm.clear();
            $("#img").attr("src",row.awardImg);
	    	row.operation = "edit";
	        Current.activityRuleWindow.setTitle("修改活动配置规则");
	        if(row.type==1){
				$("#couponFieldSet").show();
				mini.get("typeCode").setEnabled(false)
			}else{
				$("#couponFieldSet").hide();
				mini.get("typeCode").setEnabled(true)
			}
	        document.getElementById('awardColor').jscolor.fromString('#000000');
	        row.awardColor && document.getElementById('awardColor').jscolor.fromString(row.awardColor);
	        setTimeout(function(){
	        	Current.activityRuleForm.setData(row);	
	        	if(row.type==1||row.type=='1'){
	        		Current.typeCode.setValue(null);
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
			    			url : "operatemgr/activity/prizeaward/del",
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
		newRow.awardColor = document.getElementById('awardColor').jscolor.toHEXString();
		var activityCode = Current.activityCode.getValue();
		var data = newRow;
		data.activityCode = activityCode;
		data.activityConfigId = Current.activityConfigId.getValue();
		data.orderId =newRow.operation == "edit"? row.orderId:Current.datagrid.getData().length+1;
		var awardType = data.awardType;
		var operation = data.operation;
		if(newRow.operation != "edit"){
			data.typeCode =null;
		}		
		var rebateType = newRow.rebateType;
		if((newRow.type==1||newRow.type=='1')&&newRow.operation == "edit"){
			data.typeCode = row==undefined?null:row.typeCode;
		}
		delete data.operation;
		delete data.awardType;
		if(newRow.type==2||newRow.type=='2'){
			if( data.typeCode==null||data.typeCode==''){
				alert("请选择活动编号");
				return;
			}
		}
		if(rebateType==2){
			var re = new RegExp("^[0-9]*[1-9][0-9]*$");
            if (!re.test(newRow.rebateNum)){
            	alert("本站返利金额百分比请输入整数");
            	return;
            } 
		}
		var param = {
    			url : "operatemgr/activity/prizeaward/merge",
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
		    			newRow.awardType = awardType;
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
		    	Current.activityConfigForm.setData(data);
		    	for(var i=0;i<data.operateActivityRuleBOList.length;i++){
		    		data.operateActivityRuleBOList[i].orderId = i+1;
		    	}
	    		Current.datagrid.setData(data.operateActivityRuleBOList);
	    		mini.get("type").setValue(12);
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
    "initActivityStatusTrue":function(){
    	var param = {
    			url : "operatemgr/activity/config/activitystatustrue",
    			action : "post"
    	}
    	Cms.ajax(param, function(data){
    		if(data){        
    			Current.typeCode.setData(data);
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
	awardTypeChange:function(){
		var awardType =mini.get("awardType").getValue();
		if(awardType==1){
			$("#couponFieldSet").show();
			mini.get("typeCode").setEnabled(false)
		}else {
			$("#couponFieldSet").hide();
			mini.get("typeCode").setEnabled(true)
		}
		if(awardType==0){
			mini.get("typeCode").setEnabled(false)
			mini.get("typeCode").setValue(null);
		}
	}
}
