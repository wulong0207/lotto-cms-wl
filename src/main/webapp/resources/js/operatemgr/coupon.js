var Current = {
	"init" : function() {
		mini.parse();

		Current.grid = mini.get("datagrid");
		Current.editWindow = mini.get("editWindow");
		Current.addWindow = mini.get("addWindow");
		Current.contentgrid = mini.get("contentgrid");

		Current.redCategory = mini.get("redCategory");
		Current.redType = mini.get("redType");
		Current.redStatus = mini.get("redStatus");
		Current.timeType = mini.get("timeType");
		//修改窗口标签
		Current.editLimitLottery = mini.get("limitLottery_edit");
		Current.editOperateLotteryId = mini.get("operateLotteryId_edit");
		Current.editRandomRedType = mini.get("randomRedType_edit");
		//新增窗口 标签
		Current.addForm = new mini.Form("#addForm");
		Current.activityCode = mini.get("activityCode");
		Current.addRedSource = mini.get("redSource_add");
		Current.addRedType = mini.get("redType_add");
		Current.addRedStatus = mini.get("redStatus_add");
		Current.minSpendAmount = mini.get("minSpendAmount");
		Current.activeEndTime = mini.get("activeEndTime");
		Current.addChannelId = mini.get("channelId_add");
		Current.addLimitLottery = mini.get("limitLottery_add");
		Current.addLimitLotteryChild = mini.get("limitLotteryChild_add");
		Current.addLimitLotteryChildType = mini.get("limitLotteryChildType_add");
		Current.addOperateLotteryId = mini.get("operateLotteryId_add");
		Current.addLimitPlatform = mini.get("limitPlatform_add")
		Current.label=mini.get("label");
		Current.redLabel=mini.get("redLabel");
		Current.userAccountNameStr = mini.get("userAccountNameStr");
		//新增窗口 标签
		Current.reissueWindow = mini.get("reissueWindow");
		Current.ectivityDay = mini.get("ectivityDay");
		Current.reissueForm = new mini.Form("#reissueForm");
		Current.reissueActivityCode =  mini.get("reissueActivityCode");
		ComReq.dictionary("0303,1101,1102,1103,1104,1105,0702,0901,1106,1107", function(result) {
			Dic.lotteryCategory = result["0303"];
			Dic.redCategory = result["1101"];
			Dic.redType = result["1102"];
			Dic.addRedType = result["1102"].slice(0,4);
			Dic.redStatus = result["1103"];
			Dic.timeType = result["1104"];
			Dic.limitPlatform = result["1105"];
			Dic.terminalPlatform = result["0702"];
			Dic.transType =  result["0901"];
			Dic.redSource =  result["1106"];
			Dic.randomRedType = result["1107"];
			
			Current.redCategory.setData(Dic.redCategory);
			Current.redType.setData(Dic.redType);
			Current.redStatus.setData(Dic.redStatus);
			Current.timeType.setData(Dic.timeType);
			Current.timeType.select(0);
			Current.search();
			
			mini.get("redCategory_edit").setData(Dic.redCategory);
			mini.get("redType_edit").setData(Dic.redType);
			mini.get("redStatus_edit").setData(Dic.redStatus);
			mini.get("limitPlatform_edit").setData(Dic.limitPlatform);
			mini.get("terminalPlatform_edit").setData(Dic.terminalPlatform);
			mini.get("redSource_edit").setData(Dic.redSource);
			Current.editRandomRedType.setData(Dic.randomRedType);
			
			
			//添加优惠券窗口
			mini.get("redCategory_add").setData(Dic.redCategory);
			Current.addLimitPlatform.setData(Dic.limitPlatform);
			Current.addRedType.setData(Dic.addRedType);
			Dic.addRedStatus = Dic.redStatus.slice(0,3);
			Current.addRedStatus.setData(Dic.addRedStatus);
			Current.initActivityStatusTrue();
			ComReq.lottery("", function(result){
				Dic.OperateLotteryId = result.concat();
				Dic.lotteryCode = Dic.lotteryCategory.concat(result);
				var obj = new Object();
				obj.id = "0";
				obj.text = "客户端下载";
				Dic.OperateLotteryId.unshift(obj);
				Current.addLimitLottery.setData(Dic.lotteryCode);
				Current.editLimitLottery.setData(Dic.lotteryCode);
				Current.addOperateLotteryId.setData(Dic.OperateLotteryId);
				Current.editOperateLotteryId.setData(Dic.OperateLotteryId);
			});	
		});
		
		ComReq.channelTree("", function(result){
			Current.addChannelId.loadList(result, "id", "pid");
		})
		
	},
	"editwin" : function() {
		var row = Current.grid.getSelected();
		if (row) {	
			//var detailWindow = mini.get("editWindow");
	    	//detailWindow.setTitle("修改优惠券【 "+ row.redCode + " 】信息");
			Current.redCode = row.redCode;
			var param ={
	     	    	url:"operatemgr/coupon/detail/"+Current.redCode,
	     	    	action:"GET",
	     	    	data:"",
	     	}
			Cms.ajax(param, function(row){
				row.action = "put";
				row.url = "operatemgr/coupon";
    	        var form = new mini.Form("#editform");
    	        form.clear();
    	        form.setData(row);
    	        $('#redCodeShow').text(row.redCode);
    	        Current.editWindow.show();
    	        Current.contentgrid.load({
    	        	redCode:row.redCode
    	        });
     	   	});
		} else {
			mini.alert("请选择一行数据");
		}
	},
	"search" : function() {
		Current.grid.load(Current.getParamJson());
	},
	"getParamJson" : function() {
		var redCategory = Current.redCategory.getValue();
		var redType = Current.redType.getValue();
		var redStatus=Current.redStatus.getValue();
		var para = {
				redCategory : redCategory,
				redType : redType,
				redStatus : redStatus,
				redCode:mini.get("redCode").getValue(),
				redName:mini.get("redName").getValue(),
				accountName:mini.get("accountName").getValue(),
				activityCode:mini.get("activityCodeSearch").getValue()
		};
		var timeType = Current.timeType.getValue();
		var startTime = mini.get("startTime").getFormValue();
		var endTime = mini.get("endTime").getFormValue();
		// 参数判断，与赋值
		switch (timeType) {
		case "1":
			para.useTimeStart = startTime;
			para.useTimeEnd = endTime;
			break;
		case "2":
			para.obtainTimeStart = startTime;
			para.obtainTimeEnd = endTime;
			break;
		case "3":
			para.activeEndTimeStart = startTime;
			para.activeEndTimeEnd = endTime;
			break;
		case "4":
			para.redOverdueTimeStart = startTime;
			para.redOverdueTimeEnd = endTime;
			break;
		case "5":
			para.createStartTime = startTime;
			para.createEndTime = endTime;
			break;
		}
		if(mini.get("orderCode4Search").getValue()) {
			para.orderCode = mini.get("orderCode4Search").getValue();
		}
		return para;
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
	"addwin" : function() {
		Current.addForm.clear();
		
		//平台数据默认全选
//		var platform = "";
//		var platforms = Current.addLimitPlatform.getData();
//		if(platforms instanceof Array && platforms){
//			$.each(platforms, function(i, data){
//				platform += "," + data.id;
//			})
//			 platform= platform.substr(1);
//		}
		
		//渠道数据默认全选
//		var channel = "";
//		var channels = Current.addChannelId.getList();
//		if(channels instanceof Array && channels){
//			$.each(channels, function(i, data){
//				channel += "," + data.id;
//			})
//			channel= channel.substr(1);
//		}
		var row = {
			action : "post",
			url : "operatemgr/coupon/add",
			redBalance:0
		}
		
		Current.addForm.setData(row);
		Current.addWindow.show();
	},
	"add" : function(){
		var redStatus = Current.addRedStatus.getValue();
		var activeEndTime = Current.activeEndTime.getValue();
		var ectivityDay = Current.ectivityDay.getValue();
		if(ectivityDay>10000){
			mini.alert("有效天数不能超过10000");
			return;
		}
		if(redStatus == '1'){
			if(!activeEndTime){
				mini.alert("红包状态为待激活, 激活截止时间不能为空!!!");
				return;
			}			
		}
	
		var redType = Current.addRedType.getValue();
		var minSpendAmount = Current.minSpendAmount.getValue();
		if(redType == "1" || redType == "2"){
			if(!minSpendAmount){
			   mini.alert("请输入最低消费!!!");
			   return;
			}
		}
		Current.addForm.validate();
	    if (Current.addForm.isValid() == false){
             mini.alert("表单数据有误");
	    	 return;
	    }
	 	var inputFile = $("#file")[0];
	 	var file = mini.get("file");
	 	var userAccountNameStr = Current.userAccountNameStr.getValue();
	 	var fileVal = file.getValue();
		if(StringUtils.isBank(fileVal) && StringUtils.isBank(userAccountNameStr)){
			mini.alert("请选择文件上传用户昵称或手动输入用户账号!!!");
			return;
		}	    
	    var data = Current.addForm.getData(true, false);
	    Current.addWindow.loading("上传中...");
        $.ajaxFileUpload({
            url: data.url,                 //用于文件上传的服务器端请求地址
            fileElementId: inputFile,           //文件上传域的ID
            data: data,          //附加的额外参
            dataType:'json',
            type: data.action,
            success: function (data, status)    //服务器成功响应处理函数
            {
            	Current.addWindow.unmask();
            	if(data.errorCode == Code.success){
            		Current.msgWindow(data.data)
            		Current.addWindow.hide();	
            		Current.search();
            	}else{
            		Current.msgWindow(data.data);
            	}
            },
            error: function (data, status,e)   //服务器响应失败处理函数
            {
            	Current.addWindow.unmask();
                mini.alert(e);
            },
            complete: function () {
                var jq = $(id);
                jq.before(inputFile);
                jq.remove();
            }
        })	
	},
	"onActionRenderer":function(e){
        var record = e.record;
        var uid = record._uid;
        var orderCode = record.orderCode;
        var s = '<a class="mini-button"  plain="true" href="javascript:MiniCom.openMainTab(\''+orderCode+'\', \'方案管理\', \'ordermgr/basic?orderCode='+orderCode+'\')">方案</a>';
        return s;
	},
	"cancelCoupon":function(index){
		var row = Current.grid.getRow(index);
		var param ={
     	    	url:"operatemgr/coupon/cancel",
     	    	action:"PUT",
     	    	data:{
     	    		id:row.id,
     	    		redCode:row.redCode,
     	    		redType:row.redType,
     	    		redBalance:row.redBalance,
     	    		userId:row.userId,
     	    		redStatus:5
     	    	},
     	}
		Cms.ajaxFirm("确定作废？",param, function(row){
			mini.alert("操作成功！")
			Cms.reload("datagrid");
 	   	});
	},
	"onCencelRenderer":function(e){
		 var record = e.record;
	     var id = record.id;
	     var index = e.rowIndex;
	     if(record.redStatus==5){
	    	 return '<a class="mini-button" enabled="false" plain="true" href="javascript:void(0)">已作废</a>';
	     }
	     var s = '<a class="mini-button"  plain="true" href="javascript:Current.cancelCoupon('+index+')">作废</a>';
	     return s;
	},
	"closeAddWin":function(){
		Current.addWindow.hide();	
	},
	"activityCodeChange" : function(){
		var activityCode = Current.activityCode.getValue();
		if(StringUtils.isBank(activityCode)){
			Current.addRedSource.setValue('');
		}else{
			Current.addRedSource.setValue(1);
		}
	},
	"limitLotteryChange" : function(){
		var lotteryCodeStr = Current.addLimitLottery.getValue();
		switch (lotteryCodeStr) {
		case "300":
		case "301":
		case "306":
			Current.addLimitLotteryChildType.setData("[{'id':'1','text':'3串1'},{'id':'2','text':'2串1'},{'id':'3','text':'单关'}]");				
			break;
		case "307":
			Current.addLimitLotteryChildType.setData("[{'id':'1','text':'3串1'}]");	
			break;
		default :
			Current.addLimitLotteryChildType.setData();	
			break;
		}
		
		ComReq.lotteryCheckChild(lotteryCodeStr, function(result){
			Current.addLimitLotteryChild.setData(result);
		});
		//处理推荐入口数据
		Current.addOperateLotteryId.setData();
		var operateLotArr = Current.clone(Dic.OperateLotteryId);
		var limitlotArr = Current.addLimitLottery.getSelecteds();
		for (var i = 0; i < operateLotArr.length; i++) {
			var obj = operateLotArr[i];
			for (var j = 0; j < limitlotArr.length; j++) {
				if(obj.id == limitlotArr[j].id)
				   obj.enabled = false;
			}
		}
		Current.addOperateLotteryId.setData(operateLotArr);
	},
	"limitLotteryChildChange" : function(){
		var arr = ['300', '301', '306','307'];
		var lotteryCodeStr = Current.addLimitLottery.getValue();
	},
//	"operateLotteryIdChange" : function(e){
//		var id = Current.addOperateLotteryId.getValue();
//		if(id.indexOf("-1") > -1){
//			if(id.length > 2){
//				mini.alert("客户端下载和彩种 只能二选一!!!");
//				Current.addOperateLotteryId.select(0);				
//			}
//		}
//	},
	"labelChange":function(){	
		var labelVal = Current.label.getValue();
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
	reissueWin : function(){
		Current.reissueForm.clear();
		Current.reissueWindow.show();
	},
	reissueAdd: function(){
		var form = Current.reissueForm
    	form.validate();
		if (Current.addForm.isValid() == false){
            mini.alert("表单数据有误");
	    	 return;
	    }
    	data = form.getData();     
		var param = {
				url : "operatemgr/coupon/reissueadd",
				action : "post",
				data : data
			};    	
    	Cms.saveDataStringify(param, function(){
    		setTimeout(function(){
    			Current.closeConfigWindow(); 
    			
    		},300) 		
    	})
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
Current.init();