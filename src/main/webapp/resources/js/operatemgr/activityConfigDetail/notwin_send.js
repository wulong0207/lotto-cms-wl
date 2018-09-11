var Current = {
    "init" : function(code) {
        mini.parse();
        
        //窗体对象, form对象
        Current.activityRuleWindow = mini.get("activityRuleWindow");
        Current.activityConfigForm = new mini.Form("activityConfigForm");
        Current.activityRuleForm = new mini.Form("activityRuleForm");
        Current.datagrid = mini.get("activityRule_datagrid");
        
        Current.activityCode = mini.get("activityCode");
        Current.lotteryCode = mini.get("lotteryCode");
        Current.lotteryChildCode = mini.get("lotteryChildCode");
        Current.singleOrderMoney = mini.get("singleOrderMoney");
        Current.realUserNum = mini.get("realUserNum");
        Current.userType = mini.get("userType");
        Current.isFollowOrder = mini.get("isFollowOrder");
        Current.isCareOrder = mini.get("isCareOrder");
        Current.rebateUserType = mini.get("rebateUserType");
        
        //规则明细
        Current.lotteryPassType = mini.get("lotteryPassType");
        Current.ruleType = mini.get("ruleType");
        Current.ruleStart = mini.get("ruleStart");
        Current.ruleEnd = mini.get("ruleEnd");
        Current.winStartMoney = mini.get("winStartMoney");
        Current.winEndMoney = mini.get("winEndMoney");
        Current.rebateType = mini.get("rebateType");
        Current.rebateNum = mini.get("rebateNum");
        Current.status = mini.get("status");

        Dic.lotteryPassType = [{'id':0,'text':'全部'},{'id':'2_1','text':'2串1'},{'id':'1_1','text':'单关'}];
        Dic.SSQ_DLTPassType=[{id:'一等奖',text:'一等奖'},{id:'二等奖',text:'二等奖'},{id:'三等奖',text:'三等奖'},{id:'四等奖',text:'四等奖'},{id:'五等奖',text:'五等奖'},{id:'六等奖',text:'六等奖'}];
        Dic.userType = "[{'id':'1','text':'所有用户'},{'id':'4','text':'新用户'}]";
        ComReq.dictionary("1004,0002,1006,1007,1008,1106", function(result) {
        	Dic.ruleType = result["1004"];
        	Dic.isYesOrNo = result["0002"];
        	Dic.rebateType = result["1006"];
           	Dic.isTrueOrFalse = result["1007"];
        	Dic.rebateUserType = result["1008"];
        	
        	Current.rebateUserType.setData(Dic.rebateUserType);
        	Current.isFollowOrder.setData(Dic.isTrueOrFalse);
        	Current.isCareOrder.setData(Dic.isTrueOrFalse);
        	Current.userType.setData(Dic.userType);
        	
        	//规则配置
        	Current.ruleType.setData(Dic.ruleType);
        	Current.rebateType.setData(Dic.rebateType);
        	Current.status.setData(Dic.isYesOrNo);
        	
        	//设置默认值
        	Current.userType.select(0);
        	Current.isFollowOrder.select(0);
        	Current.isCareOrder.select(0);
        	Current.rebateUserType.select(0);
        });

        ComReq.lottery("2", function(result){
			Dic.lotteryCode = result;
			Current.lotteryCode.setData(Dic.lotteryCode);

            Current.initDetail(code);
        });

    },
    "lotteryCodeChange": function(){
    	var lotteryCode = parseInt(Current.lotteryCode.getValue());
		ComReq.lotteryChild(lotteryCode, function(result){
			Dic.lotteryChildCode = result;
	    	switch (lotteryCode) {
			case 300:
			case 301:
				Current.lotteryChildCode.setData(result);
				Current.lotteryPassType.setData(Dic.lotteryPassType);
				break;
			default :
				Current.lotteryChildCode.setData("");
				Current.lotteryPassType.setData(result);
				break;
			}			
		})    	
    },
    "ruleTypeChange":function(){
    	//1 : 时间, 2:期号, 3:场次
    	var ruleType = Current.ruleType.getValue();
    	var $div;
    	switch (ruleType) {
		case "1":
			$div = $("<div>")
				.append($("<input>", {'id' : 'ruleStart', 'name' : 'ruleStart', class : 'mini-datepicker', required : 'true', style:'width:180px', showTime : 'true', format:'yyyy-MM-dd HH:mm:ss', timeFormat :'HH:mm:ss', showOkButton: 'true', showClearButton : 'false', oncloseclick : 'Cms.onCloseClick', showTodayButton : 'false', showClose : 'true'}))
				.append("&nbsp;至&nbsp;")
				.append($("<input>", {'id' : 'ruleEnd', 'name' : 'ruleEnd', class : 'mini-datepicker', required : 'true', style:'width:180px', showTime : 'true', format:'yyyy-MM-dd HH:mm:ss', timeFormat :'HH:mm:ss', showOkButton :'true', showClearButton : 'false', oncloseclick : 'Cms.onCloseClick', showTodayButton : 'false', showClose : 'true'}))
			break;
		case "2":
		case "3":
			$div = $("<div>")
				.append($("<input>", {'id' : 'ruleStart', 'name' : 'ruleStart', class : 'mini-textbox', required : 'true', style:'width:180px'}))
				.append("&nbsp;至&nbsp;")
				.append($("<input>", {'id' : 'ruleEnd', 'name' : 'ruleEnd', class : 'mini-textbox',  checked: 'true', required : 'true', style:'width:180px'}))
			break;
		}
    	$("#ruleObj").html($div);
    	mini.parse();
    },
    "merge": function(){
    	var form = Current.activityConfigForm
    	form.validate();
    	data = form.getData();     
    	data.operateActivityRuleVOList = Current.datagrid.getData(true, false);
        var re = new RegExp("^[0-9]*[1-9][0-9]*$");
        var reg = new RegExp("^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$");
        if (!re.test(data.singleOrderMoney) && !reg.test(data.singleOrderMoney)){
            alert("单个方案返奖上限必须为数字，允许两位小数！");
            return;
        }
        if (!re.test(data.realUserNum)){
            alert("同真实姓名享受人数格式错误，请输入整数");
            return;
        }
    	if(data.operateActivityRuleVOList.length==0){
    		alert("请添加活动规则");
    		return;
    	}
    	var lotteryCode = parseInt(Current.lotteryCode.getValue());
    	if(lotteryCode==300||lotteryCode==301)
    		$.each(data.operateActivityRuleVOList, function(index, item) {
				item.lotteryChildCode =Current.lotteryChildCode.getValue();
			})
    
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
		var lotteryCode = Current.lotteryCode.getValue();
		if(!lotteryCode){
			mini.alert("请选择彩种!!!");
			return;
		}
    	Current.activityRuleForm.clear();
    	Current.activityRuleForm.setData({
			operation : "add",
		});
    	Current.ruleType.select(0);
        Current.status.select(0);
    	Current.ruleTypeChange();
        Current.activityRuleWindow.setTitle("新增活动配置规则");
    	Current.activityRuleWindow.show();
    },
    "edit": function() {
		var row = Current.datagrid.getSelected();
		var lotteryCode =Current.lotteryCode.getValue();
		Current.ruleType.select(row.ruleType);
    	Current.ruleTypeChange(row.ruleType);
		if (row) {	
	    	Current.activityRuleForm.clear();
	    	row.operation = "edit";
	    	if(lotteryCode != 300 && lotteryCode != 301)
	    		row.lotteryPassType = row.lotteryChildCode;
	    	Current.activityRuleForm.setData(row);
	        Current.activityRuleWindow.setTitle("修改活动配置规则");
	    	Current.activityRuleWindow.show();
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
        var re = new RegExp("^[0-9]*[1-9][0-9]*$");
        var reg = new RegExp("^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$");
        /*if ((!reg.test(newRow.winStartMoney) && !re.test(newRow.winStartMoney)) || (!reg.test(newRow.winEndMoney) && !re.test(newRow.winEndMoney))) {
            alert("购彩金额必须为数字，允许两位小数！");
            return;
        }*/
        if (rebateType == 1) {
            if (!reg.test(newRow.rebateNum) && !re.test(newRow.rebateNum)){
                alert("具体金额必须为数字，允许两位小数！");
                return;
            }
        } else {
            if (!re.test(newRow.rebateNum)){
                alert("金额百分比格式错误，请输入整数");
                return;
            }
        }
		var lotteryCode = parseInt(Current.lotteryCode.getValue());
    	switch (lotteryCode) {
		case 300:
		case 301:
			newRow.lotteryChildCode = Current.lotteryChildCode.getValue();
			break;
		default :
				newRow.lotteryChildCode = newRow.lotteryPassType;
				newRow.lotteryPassType = "";
				break;
		}	
		newRow.lotteryCode = Current.lotteryCode.getValue();
		newRow.operation == "edit" ? Current.datagrid.updateRow(row, newRow) : Current.datagrid
				.addRow(newRow, Current.datagrid.data.length);
		Current.activityRuleWindow.hide();    	
    },
    "initDetail":function(activityCode){
    	var param = {
    			url : "operatemgr/activity/config/detail",
    			action : "post",
    			data : {activityCode : activityCode,type:7}
    	}
    	Cms.ajax(param, function(data){
    		if(data){
        		var lotteryCode = parseInt(data.lotteryCode);
    			Current.lotteryCode.set({
    				enabled:false
    			})
        		ComReq.lotteryChild(lotteryCode, function(result){
        				Dic.lotteryChildCode = result;
        		    	switch (lotteryCode) {
        				case 300:
        				case 301:
        					Current.lotteryChildCode.setData(Dic.lotteryChildCode);
        					Current.lotteryPassType.setData(Dic.lotteryPassType);
                    		Current.activityConfigForm.setData(data);
        					Current.lotteryChildCode.setValue(data.operateActivityRuleBOList[0].lotteryChildCode);
        					break;
						case 100:
						case 102:
							Dic.lotteryChildCode = Dic.SSQ_DLTPassType;
        				default :
							Current.lotteryPassType.setData(Dic.lotteryChildCode);
							Current.activityConfigForm.setData(data);
							break;
        				}	
                		Current.datagrid.setData(data.operateActivityRuleBOList);
        		})
    		}else{
    			Current.activityCode.setValue(activityCode);
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
	"floatValid" : function(e){
        if (e.isValid) {
            var re = new RegExp("^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$");
            var re1 = new RegExp("^[0-9]*[1-9][0-9]*$");
            if (re.test(e.value)||re1.test(e.value)){
            	return true;
            } 
            e.errorText = "只能输入数字,允许两位小数!!!";
            e.isValid = false;            
            return false;        	
        }	
	},
}
