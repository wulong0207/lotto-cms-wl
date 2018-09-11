var Current = {
	"init" : function(templateId) {
		mini.parse();
		Current.grid = mini.get("coupon_datagrid");
		Current.couponWindow = mini.get("couponWindow");
		Current.couponForm = new mini.Form("#couponForm");
		
		Current.templateId=templateId;
		
		Current.LimitLottery = mini.get("limitLottery");
		Current.operateLotteryId = mini.get("operateLotteryId");
		Current.redType = mini.get("redType");
		Current.redStatus = mini.get("redStatus");
		Current.minSpendAmount = mini.get("minSpendAmount");
		Current.activeEndTime = mini.get("activeEndTime");
		Current.channelId = mini.get("channelId");
		Current.limitLotteryChild = mini.get("limitLotteryChild");
		Current.limitLotteryChildType = mini.get("limitLotteryChildType");
		Current.limitPlatform = mini.get("limitPlatform");
		Current.redLabel=mini.get("redLabel");
		Current.ectivityDay = mini.get("ectivityDay");

		ComReq.dictionary("0303,1101,1102,1103,1104,1105,0702,0901,1106,1107", function(result) {
			Dic.lotteryCategory = result["0303"];
			Dic.redCategory = result["1101"];
			Dic.redType = result["1102"].slice(0,4);
			Dic.redStatus = result["1103"].slice(0,3);
			Dic.timeType = result["1104"];
			Dic.limitPlatform = result["1105"];
			
			Current.redType.setData(Dic.redType);
			Current.redStatus.setData(Dic.redStatus);
	
			
			mini.get("redType").setData(Dic.redType);
			mini.get("redStatus").setData(Dic.redStatus);
			mini.get("limitPlatform").setData(Dic.limitPlatform);
			
			
			//添加优惠券窗口
			Current.limitPlatform.setData(Dic.limitPlatform);
			Current.redType.setData(Dic.redType);
			Current.redStatus.setData(Dic.redStatus);
			Current.search();
			ComReq.lottery("", function(result){
				Dic.OperateLotteryId = result.concat();
				Dic.lotteryCode = Dic.lotteryCategory.concat(result);
				var obj = new Object();
				obj.id = "0";
				obj.text = "客户端下载";
				Dic.OperateLotteryId.unshift(obj);
				mini.get("limitLottery").setData(Dic.lotteryCode);
				Current.operateLotteryId.setData(Dic.OperateLotteryId);
			});	
		});
		
		ComReq.channelTree("", function(result){
			Current.channelId.loadList(result, "id", "pid");
		})
		
	},
	"edit" : function() {
		var row = mini.get("coupon_datagrid").getSelected();
		if(row){
			Current.couponForm.clear();
			row.action="post";
			row.url="operatemgr/msginfo/updateCouponTemp";
			row.configId=Current.templateId;
			row.configType=2;
			row.operation="edit";
			Current.couponWindow.setTitle("修改优惠券信息");
			Current.couponForm.setData(row);
			Current.couponWindow.show();
		}else {
			mini.alert("请选择一行数据");
		}
	},
	"search" : function() {
		Current.grid.load(Current.getParamJson());
	},
	"getParamJson" : function() {
		var redType = Current.redType.getValue();
		var templateId = Current.templateId;
		var para = {
				redType : redType,
				configId : templateId
		};
		return para;
	},
	"addwin" : function() {
		Current.couponForm.clear();
		var row = {
			action : "post",
			url : "operatemgr/msginfo/insertCouponTemp",
			configId :Current.templateId,
			operation:"add",
			configType:2
		}
		Current.couponForm.setData(row);
		Current.couponWindow.show();
	},
	"add" : function(){
		var redStatus = Current.redStatus.getValue();
		var activeEndTime = Current.activeEndTime.getValue();
		var ectivityDay = Current.ectivityDay.getValue();
		var redType = Current.redType.getValue();
		var minSpendAmount = Current.minSpendAmount.getValue();
		if(redType == "1" || redType == "2"){
			if(!minSpendAmount){
			   mini.alert("请输入最低消费!!!");
			   return;
			}
		}
		Current.couponForm.validate();
	    if (Current.couponForm.isValid() == false){
             mini.alert("表单数据有误");
	    	 return;
	    }
		var row = mini.get("coupon_datagrid").getSelected();
	    //getData(formatter,deep) formatter 针对时间  deep在json的时候是否加上对象名
	    var newRow = Current.couponForm.getData(true,false);
	    //addRow ( row, index )
		newRow.operation == "edit" ? mini.get("coupon_datagrid").updateRow(row, newRow) : mini.get("coupon_datagrid").addRow(newRow, mini.get("coupon_datagrid").data.length);
		Current.couponWindow.hide();        
	},
	"del" : function(){
		Current.datagrid = mini.get("coupon_datagrid");
		var rows = Current.datagrid.getSelecteds();
		if (rows) {
			mini.confirm("确定删除?", "提示", function(e) {
				if (e === "ok") {
					Current.datagrid.removeRows(rows)
					Current.ids = Current.ids || [];
					$.each(rows, function(index, row) {
						Current.ids.push(row.id);
					})
					var row = {};
					row.action="post";
					row.url="operatemgr/msginfo/deleteCouponTemp";
					row.configId=Current.templateId;
					row.operation="delete";
					Current.couponForm.setData(row);	
				}
			});
		} else {
			mini.alert("请选择一行数据");
		}    	
    },    
    "merge": function(){
    	var form = Current.couponForm;
    	form.validate();
    	data = form.getData();   
    	Current.ids && (data.id = Current.ids[0]);
		var param = {
				url : data.url,
				action : data.aciton,
				data : data
			}; 
		if(param.url==""){
			return;
		}
    	Cms.ajax(param, function(result){
    		Current.couponWindow.hide();
    	})
    },
	"excel" : function() {
		var param = Cms.jsonParamStr(Current.getParamJson());
		var url = "operatemgr/coupon/excel?" + param;
		location = url;
	},
	"excelRed":function(){
		var url = "operatemgr/coupon/excel/red?redCode=" + Current.redCode;
		location = url;
	},
	"onrowdblclick" : function() {
		mini.get("edit").doClick();
	},
	"doSubmit" : function() {
		Cms.submit(new mini.Form("#editform"), function() {
			Current.editWindow.hide();
			Cms.reload("datagrid");
		});
	},
	"closeAddWin":function(){
		Current.couponWindow.hide();	
	},
	"limitLotteryChange" : function(){
		var lotteryCodeStr = mini.get("limitLottery").getValue();
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
		//处理推荐入口数据
		Current.operateLotteryId.setData();
		var operateLotArr = Current.clone(Dic.OperateLotteryId);
		var limitlotArr = mini.get("limitLottery").getSelecteds();
		for (var i = 0; i < operateLotArr.length; i++) {
			var obj = operateLotArr[i];
			for (var j = 0; j < limitlotArr.length; j++) {
				if(obj.id == limitlotArr[j].id)
				   obj.enabled = false;
			}
		}
		Current.operateLotteryId.setData(operateLotArr);
	},
	"limitLotteryChildChange" : function(){
		var arr = ['300', '301', '306','307'];
		var lotteryCodeStr = Current.limitLottery.getValue();
	},
	"labelChange":function(){	
		Current.label = mini.get("label");
		var labelVal = Current.label.getValue();
		Current.redLabel = mini.get("redLabel");
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
	"closeConfigWindow": function() {
    	// 确定关闭
    	Current.ids = null;
    	window.CloseOwnerWindow();   	
	},
	"onMoneyValidation" : function(e){
        if (e.isValid) {
            var re = new RegExp("^(([0-9]*[1-9][0-9]*)|([0-9]+\.[0-9]{1,2}))$");
            if (re.test(e.value)){
            	return true;
            } 
            e.errorText = "只能输入大于0的数字,允许两位小数!!!";
            e.isValid = false;            
            return false;        	
        }	
	},
	"limitLotteryAddClose" : function(e){
		var obj = e.sender;
		obj.setText("");
		obj.setValue("");
		Current.addOperateLotteryId.setData(Dic.OperateLotteryId);		
	},
	"clone" : function(obj) {
		var o;
		if (typeof obj == "object") {
			if (obj === null) {
				o = null;
			} else {
				if (obj instanceof Array) {
					o = [];
					for (var i = 0, len = obj.length; i < len; i++) {
						o.push(Current.clone(obj[i]));
					}
				} else {
					o = {};
					for ( var j in obj) {
						o[j] = Current.clone(obj[j]);
					}
				}
			}
		} else {
			o = obj;
		}
		return o;
	},
	 "initActivityStatusTrue":function(){
	    	var param = {
	    			url : "operatemgr/activity/config/activitystatustrue",
	    			action : "post"
	    	}
	    	Cms.ajax(param, function(data){
	    		if(data){        
	    			Current.reissueActivityCode.setData(data);
	    		}
	    	})
	    },
	    "closeReissueWin":function(){
			Current.reissueWindow.hide();	
	},
	"msgWindow" : function(data){
		var message = '';
		if(data&&data.length>0){
			 $.each(data, function(){     
				 message += this + ","   
			});
			message = message.substring(0, message.length - 1);
			message += ' 未能找到账户id, 发送失败';
		}else{
			message = '红包发送成功';
		}
		mini.alert(message);
	}
}