var Current = {
    "init" : function(code) {
        mini.parse();
        // 解析jscolor
        window.jscolor.installByClassName('jscolor');

        Current.type = mini.get("type");

        //窗体对象, form对象
        Current.activityConfigForm = new mini.Form("activityConfigForm");
        Current.sportAgainstForm = new mini.Form("sportAgainstForm");
        Current.activitySportRuleForm = new mini.Form("activitySportRuleForm");

        Current.activitySportRuleWindow = mini.get("activitySportRuleWindow");
        Current.sportAgainstWindow = mini.get("sportAgainstWindow");

        Current.datagrid = mini.get("activityRule_datagrid");
        Current.sportAgainstDatagrid = mini.get("sportAgainst_datagrid");

        Current.activityCode = mini.get("activityCode");
        Current.ruleIds = mini.get("ruleId");

        Current.matchStatus = mini.get("matchStatus");
        Current.openStatus = mini.get("openStatus");
        Current.passAdd = mini.get("passAdd");
        Current.lotteryChild = mini.get("lotteryChild");
        Current.ruleId = mini.get("ruleId");

        Current.winPercent = mini.get("winPercent");
        Current.drawPercent = mini.get("drawPercent");
        Current.losePercent = mini.get("losePercent");

        Current.activityCode.setValue(code);
        Current.type.setValue(10);

        Current.initDetail(code);

        ComReq.dictionary("0307,0507", function(result) {
        	Dic.isYesOrNo = result["0307"];
            Dic.matchStatus = result["0507"];

            Current.openStatus.setData(Dic.isYesOrNo);

            Current.openStatus.select(1);
        });


    },
    "add": function () {
    	Current.sportAgainstForm.clear();
    	Current.sportAgainstForm.setData({
			operation : "add",
			status : "0",
		});
        Current.sportAgainstDatagrid.load();
 		Current.sportAgainstWindow.setTitle("新增赛事加奖信息");
    	Current.sportAgainstWindow.show();
    },
    "edit": function() {
		var row = Current.datagrid.getSelected();
		if (row) {	
	    	Current.activitySportRuleForm.clear();
	    	row.operate = "edit";
	    	Current.activitySportRuleForm.setData(row);
	    	var passAdd = row.passAdd;
            var lotteryChild = [{id:'30002',text:'胜平负'},{id:'30003',text:'让球胜平负'}];
            Current.lotteryChild.setData(lotteryChild);
            Current.lotteryChild.selectAll();
            if (passAdd && passAdd != "待定") {
                var list = [];
                list = JSON.parse(passAdd);
                for (var i=0; i<list.length; i++) {
                    Current.winPercent.setValue(list[0].percent);
                    document.getElementById('winColor').jscolor.fromString(list[0].color);
                    Current.drawPercent.setValue(list[1].percent);
                    document.getElementById('drawColor').jscolor.fromString(list[1].color);
                    Current.losePercent.setValue(list[2].percent);
                    document.getElementById('loseColor').jscolor.fromString(list[2].color);
                }
            }
	        Current.activitySportRuleWindow.setTitle("编辑赛事");
	    	Current.activitySportRuleWindow.show();
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
    "merge": function(){
        Current.type.setValue(10);
        var form = Current.activityConfigForm;
        form.validate();
        if (!form.isValid()) {
            alert("参数不完整！");
        }
        data = form.getData();
        data.operateActivitySportRuleVOList = Current.datagrid.getData(true, false);
        if (data.operateActivitySportRuleVOList.length <=0) {
            alert("请添加对阵赛事");
            return;
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
    "closeConfigWindow": function() {
        // 确定关闭
        Current.ids = null;
        window.CloseOwnerWindow();
    },

    "mergeSportAgainst": function() {

        var row = Current.datagrid.getSelecteds();
        var newRow = Current.sportAgainstDatagrid.getSelecteds();
        var data = Current.datagrid.data;
        for (var i=0;i<newRow.length; i++) {
            for (var j=0; j<data.length; j++) {
                if (newRow[i].systemCode == data[j].systemCode) {
                    alert("数据选择重复，请重新选择");
                    return;
                }
            }
        }
        for(var i=0;i<newRow.length;i++) {
            newRow[i].openStatus = 0;
            newRow[i].spf = "√";
            newRow[i].rfspf = "√";
        }
        newRow.operation == "edit" ? Current.datagrid.updateRow(row, newRow) : Current.datagrid
                .addRows(newRow, Current.datagrid.data.length);
        Current.sportAgainstWindow.hide();
    },
    "closeSportAgainstWindow" : function(){
        Current.sportAgainstWindow.hide();
    },

	"mergeSportRule": function () {
        Current.activitySportRuleForm .validate();
        if (!Current.activitySportRuleForm .isValid()) {
            mini.alert("表单数据不完整");
            return;
        }
        var dataForm = Current.activityConfigForm.getData();
        var row = Current.datagrid.getSelected();
        var newRow = Current.activitySportRuleForm.getData(true,false);
        var re = new RegExp("^[0-9]*[1-9][0-9]*$");
        if (!re.test(newRow.winPercent) || !re.test(newRow.drawPercent) || !re.test(newRow.losePercent)){
            alert("胜平负百分比格式错误，请输入整数");
            return;
        }
        var winColor = document.getElementById('winColor').jscolor.toHEXString();
        var drawColor = document.getElementById('drawColor').jscolor.toHEXString();
        var loseColor = document.getElementById('loseColor').jscolor.toHEXString();
        newRow.passAdd = '[{"type":3,"percent":"'+newRow.winPercent+'","color":"'+winColor+'"},{"type":4,"percent":"'+newRow.drawPercent+'","color":"'+drawColor+'"},{"type":5,"percent":"'+newRow.losePercent+'","color":"'+loseColor+'"}]';
        data = newRow;
        var dataForm = Current.activityConfigForm.getData();
        if (row.activityConfigId) {
            data.activityConfigId = row.activityConfigId;
        } else {
            data.activityConfigId = dataForm.id;
        }
        data.lotteryChildCode = newRow.lotteryChild;
        data.systemCode = row.systemCode;
        data.activityCode = dataForm.activityCode;
        var param = {
            url : "operatemgr/activity/sport/add",
            action : "post",
            data : data
        };
        Current.saveDataStringify(param, function(e){
            newRow.id = e;
            Current.ruleId.setValue(e);
            newRow.operate == "edit" ? Current.datagrid.updateRow(row, newRow) : Current.datagrid
                    .addRow(newRow, Current.datagrid.data.length);
            setTimeout(function(){
                Current.closeSportRuleWindow();
            },300)
        })

    },
    "closeSportRuleWindow" : function(){
        Current.activitySportRuleWindow.hide();
    },

    "initDetail":function(activityCode){
    	var type = Current.type.getValue();
    	var param = {
    			url : "operatemgr/activity/config/detail",
    			action : "post",
    			data : {activityCode : activityCode, type: type}
    	}
    	Cms.ajax(param, function(data){
    		if(data){
    			Current.type.setValue(10);
                Current.activityConfigForm.setData(data);
                Current.datagrid.setData(data.operateActivitySportRuleBOList);
    		}
    	})
    },
    "switchStyle":function(e){
        var row = e.row;
        var passAdd = row.passAdd;
        var winPercent;
        var drawPercent;
        var losePercent;
        if (passAdd != null && passAdd != "待定" ) {
            var list = [];
            list = JSON.parse(passAdd);
            for (var i=0; i<list.length; i++) {
                winPercent = list[0].percent;
                drawPercent = list[1].percent;
                losePercent = list[2].percent;
            }
            return '<div width="8%" headerAlign="center" align="center" renderer="Current.switchClass">胜-'+winPercent+'% 平-'+drawPercent+'% 负-'+losePercent+'%</div>';
        }
        return '<div width="8%" headerAlign="center" align="center" renderer="Current.switchClass">待定</div>';
    },
    "saveDataStringify":function(param,successBack){
        mini.confirm(param.tip || "确定保存?", param.tipTitle || "提示", function(e) {
            if (e == "ok") {
                MiniCom.mask(param.ingTip || "保存中。。。");
                $.ajax({
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    url : param.url,
                    data :JSON.stringify(param.data),
                    type : param.action || 'GET',
                }).done(
                    function(res) {
                        MiniCom.unmask();
                        if(res.errorCode == Code.success){
                            successBack(res.data);
                            mini.showTips({
                                content: res.message,
                                state: "success",
                                x: "center",
                                y: "center",
                                timeout: 2000
                            });
                        }else{
                            mini.alert(res.message);
                        }
                    }).fail(
                    function(jqXHR, textStatus, errorThrown) {
                        MiniCom.unmask();
                        alert(jqXHR.responseText);
                    }
                );
            }
        });
    },

}
