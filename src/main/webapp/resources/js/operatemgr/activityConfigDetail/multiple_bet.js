var Current = {
    "init" : function(code) {
        mini.parse();
        
        //窗体对象, form对象
        Current.activityRuleWindow = mini.get("activityRuleWindow");
        Current.activityConfigForm = new mini.Form("activityConfigForm");
        Current.activityRuleForm = new mini.Form("activityRuleForm");
        Current.activityBetForm = new mini.Form("activityBetForm");
        Current.datagrid = mini.get("activityRule_datagrid");
        Current.betdatagrid = mini.get("activityBet_datagrid");

        
        Current.activityCode = mini.get("activityCode");
        Current.lotteryCode = mini.get("lotteryCode");
        Current.lotteryChildCode = mini.get("lotteryChildCode");
        Current.url = mini.get("url");
        Current.userType = mini.get("userType");
        Current.rebateUserType = mini.get("rebateUserType");
        Current.isFollowOrder = mini.get("isFollowOrder");
        
        //规则明细
        Current.lotteryPassType = mini.get("lotteryPassType");
        Current.ruleType = mini.get("ruleType");
        Current.rebateType = mini.get("rebateType");
        Current.rebateNum = mini.get("rebateNum");

        //那啥啥啥明细
        Current.lotteryPassType = mini.get("lotteryPassType");
        Current.money = mini.get("money");

        Dic.userType = "[{'id':'1','text':'所有用户'},{'id':'4','text':'新用户'}]";
        Dic.lotteryPassType = [{id:'2_1',text:'2串1'},{id:'1_1',text:'单关'}];
        ComReq.dictionary("1004,1005,1007,1008", function(result) {
            Dic.ruleType = result["1004"];
            Dic.isYesOrNo = result["1005"];
           	Dic.isTrueOrFalse = result["1007"];
        	Dic.rebateUserType = result["1008"];

        	Current.userType.setData(Dic.userType);
        	Current.rebateUserType.setData(Dic.rebateUserType);
        	Current.ruleType.setData(Dic.ruleType);
        	Current.isFollowOrder.setData(Dic.isTrueOrFalse);
        	
        	//设置默认值
        	Current.rebateUserType.select(0);
        	Current.userType.select(1);
        	Current.ruleType.select(0);
        	Current.isFollowOrder.select(0);
        });
        
        ComReq.lottery("", function(result){
            Dic.lotteryCode = result;
            Current.lotteryCode.setData(Dic.lotteryCode);
            Current.initDetail(code);
        });

    },
    "lotteryCodeChange": function(){
        var lotteryCode = Current.lotteryCode.getValue();
        ComReq.lotteryChild(lotteryCode, function(result){
            Dic.lotteryChildCode = result;
            switch (lotteryCode) {
                case "300":
                case "301":
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
			    .append($("<input>", {'id' : 'ruleStart', 'name' : 'ruleStart', class : 'mini-datepicker', style: 'width:185px;', required : 'true', showTime : 'true', format:'yyyy-MM-dd HH:mm:ss', timeFormat :'HH:mm:ss', showOkButton: 'true', showClearButton : 'false', oncloseclick : 'Cms.onCloseClick', showTodayButton : 'false', showClose : 'true'}))
				.append("&nbsp;至&nbsp;")
			    .append($("<input>", {'id' : 'ruleEnd', 'name' : 'ruleEnd', class : 'mini-datepicker', style: 'width:185px;', required : 'true', showTime : 'true', format:'yyyy-MM-dd HH:mm:ss', timeFormat :'HH:mm:ss', showOkButton :'true', showClearButton : 'false', oncloseclick : 'Cms.onCloseClick', showTodayButton : 'false', showClose : 'true'}))
			break;
		case "2":
		case "3":
			$div = $("<div>")
			.append($("<input>", {'id' : 'ruleStart', 'name' : 'ruleStart', class : 'mini-textbox', required : 'true', style: 'width:185px;'}))
			.append("&nbsp;至&nbsp;")
			.append($("<input>", {'id' : 'ruleEnd', 'name' : 'ruleEnd', class : 'mini-textbox',  checked: 'true', required : 'true',  style: 'width:185px;'}))
			break;
		}
    	$("#ruleObj").html($div);
    	mini.parse();
    },
    "merge": function(){
    	var form = Current.activityConfigForm;
    	data = form.getData();  	
    	data.operateActivityRuleVOList = Current.datagrid.getData(true, false);
        var re = new RegExp("^[0-9]*[1-9][0-9]*$");
        if (data.offerNum && !re.test(data.offerNum)){
            alert("同一用户享受次数格式错误，请输入整数");
            return;
        }
        if (data.realUserNum && !re.test(data.realUserNum)){
            alert("同一真实姓名享受人数格式错误，请输入整数");
            return;
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
    	Current.ruleTypeChange();
        Current.activityRuleWindow.setTitle("新增活动配置规则");
    	Current.activityRuleWindow.show();
    },
    "edit": function() {
		var row = Current.datagrid.getSelected();
		Current.ruleType.select(row.ruleType);
    	Current.ruleTypeChange(row.ruleType);
		if (row) {	
	    	Current.activityRuleForm.clear();
            Current.betdatagrid.clearRows();
	    	row.operation = "edit";
	    	if(row.lotteryCode != "300" || row.lotteryCode != "301");
	    		row.lotteryPassType = row.lotteryChildCode;
			if (row.multipleSub != null) {
                var beData = [];
                betData = JSON.parse(row.multipleSub);
                Current.betdatagrid.setData(betData);
			}
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
		if (Current.betdatagrid.getData() != null) {
            var betList = Current.betdatagrid.getData();
            var list = [];
            for (var i=0; i<betList.length; i++) {
                var obj = {};
                obj.num = betList[i].num;
                obj.money = betList[i].money;
                var re = new RegExp("^[0-9]*[1-9][0-9]*$");
                var reg = new RegExp("^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$");
                if (obj.num && !re.test(obj.num)){
                    alert("倍数格式错误，请输入整数");
                    return;
                }
                if (obj.money && !reg.test(obj.money) && !re.test(obj.money)){
                    alert("减免金额必须为数字，允许两位小数！");
                    return;
                }
                list.add(obj);
            }
            newRow.multipleSub = JSON.stringify(list);
		}
		var rebateType = newRow.rebateType;
		var lotteryCode = Current.lotteryCode.getValue();
    	switch (lotteryCode) {
		case "300":
		case "301":
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
	"mergeActivityBet":function () {
        Current.betdatagrid.commitEdit();
    },
    "initDetail":function(activityCode){
    	var param = {
    			url : "operatemgr/activity/config/detail",
    			action : "post",
    			data : {activityCode : activityCode,type:5}
    	}
    	Cms.ajax(param, function(data){
    		if(data){
        		var lotteryCode = data.lotteryCode;
    			Current.lotteryCode.set({
    				enabled:false
    			})
        		ComReq.lotteryChild(lotteryCode, function(result){
        				Dic.lotteryChildCode = result;
        		    	switch (lotteryCode) {
        				case "300":
        				case "301":
        					Current.lotteryChildCode.setData(Dic.lotteryChildCode);
        					Current.lotteryPassType.setData(Dic.lotteryPassType);
        					break;
        				default :
        						Current.lotteryPassType.setData(Dic.lotteryChildCode);
        						break;
        				}	        				
                		Current.activityConfigForm.setData(data);
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
}
