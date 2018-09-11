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

        Current.lotteryCode = mini.get("lotteryCode");
        Current.lotteryChildCode = mini.get("lotteryChildCode");
        Current.rebateUserType = mini.get("rebateUserType");
        Current.isFollowOrder = mini.get("isFollowOrder");

        Current.matchStatus = mini.get("matchStatus");
        Current.openStatus = mini.get("openStatus");

        Current.singlePercent = mini.get("singlePercent");
        Current.passPercent = mini.get("passPercent");


        Current.activityCode.setValue(code);
        Current.type.setValue(11);

        Current.initDetail(code);

        ComReq.dictionary("0307,1007,1008,1013", function(result) {
        	Dic.isYesOrNo = result["0307"];
            Dic.isTrueOrFalse = result["1007"];
            Dic.rebateUserType = result["1008"];

            Current.isFollowOrder.setData(Dic.isTrueOrFalse);
            Current.rebateUserType.setData(Dic.rebateUserType);

            Current.isFollowOrder.select(0);
            Current.rebateUserType.select(0);
        });

        ComReq.lottery("3", function(result){
            Dic.lotteryCode = result;
            Current.lotteryCode.setData(Dic.lotteryCode);
        });


    },
    "add": function () {
        var lotteryCode = parseInt(Current.lotteryCode.getValue());
        if (lotteryCode) {
            Current.sportAgainstDatagrid.load({
                lotteryCode: lotteryCode
            });
            Current.sportAgainstWindow.setTitle("新增赛事加奖信息");
            Current.sportAgainstWindow.show();
        } else {
            alert("请选择彩种");
        }
    },
    "edit": function() {
        var row = Current.datagrid.getSelected();
        if (row) {
            Current.activitySportRuleForm.clear();
            row.operate = "edit";
            Current.activitySportRuleForm.setData(row);
            var passAdd = row.passAdd;
            if (passAdd) {
                var list = [];
                list = JSON.parse(passAdd);
                for (var i=0; i<list.length; i++) {
                    Current.singlePercent.setValue(list[0].percent);
                    Current.passPercent.setValue(list[1].percent);
                }
            }
            Current.activitySportRuleWindow.setTitle("编辑赛事");
            Current.activitySportRuleWindow.show();
        } else {
            mini.alert("请选择一行数据");
        }
    },

    "lotteryCodeChange": function(){
        var lotteryCode = parseInt(Current.lotteryCode.getValue());
        ComReq.lotteryChild(lotteryCode, function(result){
            Dic.lotteryChildCode = result;
            switch (lotteryCode) {
                case 300:
                case 301:
                    Current.lotteryChildCode.setData(result);
                    var str = '';
                    for(var i=0;i<result.length;i++){
                        var data = result[i];
                        str =str==''? data.id:str+","+data.id
                    }
                    Current.lotteryChildCode.setValue(str);
                    break;
                default :
                    Current.lotteryChildCode.setData("");
                    break;
            }
        })
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
        Current.type.setValue(11);
        var form = Current.activityConfigForm;
        form.validate();
        if (!form.isValid()) {
            mini.alert("参数不完整！");
            return;
        }
        data = form.getData();
        data.operateActivitySportRuleVOList = Current.datagrid.getData(true, false);
        if (data.operateActivitySportRuleVOList.length <=0) {
            alert("请添加对阵赛事");
            return;
        }
        var lotteryCode = parseInt(Current.lotteryCode.getValue());
        if(lotteryCode==300||lotteryCode==301)
            $.each(data.operateActivitySportRuleVOList, function(index, item) {
                item.lotteryChildCode =Current.lotteryChildCode.getValue();
            })
        var re = new RegExp("^[0-9]*[1-9][0-9]*$");
        if (data.singleUserMoney && !re.test(data.singleUserMoney)){
            alert("返奖上限格式错误，请输入整数");
            return;
        }
        if (data.realUserNum && !re.test(data.realUserNum)) {
            alert("享受人数格式错误，请输入整数");
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
        var row = Current.datagrid.getSelected();
        var newRow = Current.activitySportRuleForm.getData(true,false);
        var re = new RegExp("^[0-9]*[1-9][0-9]*$");
        if (!re.test(newRow.singlePercent) || !re.test(newRow.passPercent)){
            alert("百分占比格式错误，请输入整数");
            return;
        }
        newRow.passAdd = '[{"type":1,"percent":"'+newRow.singlePercent+'"},{"type":2,"percent":"'+newRow.passPercent+'"}]';
        newRow.operate == "edit" ? Current.datagrid.updateRow(row, newRow) : Current.datagrid
                .addRow(newRow, Current.datagrid.data.length);
        Current.activitySportRuleWindow.hide();
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
                var lotteryCode = data.lotteryCode;
                Current.lotteryCode.set({
                    enabled:false
                })
                Current.type.setValue(11);
                Current.lotteryCodeChange(data.lotteryCode);
                ComReq.lotteryChild(lotteryCode, function(result){
                    Dic.lotteryChildCode = result;
                    switch (lotteryCode) {
                        case 300:
                        case 301:
                            Current.lotteryChildCode.setData(Dic.lotteryChildCode);
                            Current.activityConfigForm.setData(data);
                            if (data.operateActivitySportRuleBOList.length > 0) {
                                Current.lotteryChildCode.setValue(data.operateActivitySportRuleBOList[0].lotteryChildCode);
                            }
                            break;
                        default :
                            Current.lotteryPassType.setData(Dic.lotteryChildCode);
                            Current.activityConfigForm.setData(data);
                            break;
                    }
                    Current.datagrid.setData(data.operateActivitySportRuleBOList);
                })
            }
        })
    },

    "switchSingleStyle":function(e){
        var row = e.row;
        var passAdd = row.passAdd;
        var singlePercent;
        if (passAdd) {
            var list = [];
            list = JSON.parse(passAdd);
            for (var i=0; i<list.length; i++) {
                singlePercent = list[0].percent;
            }
            return '<div width="8%" headerAlign="center" align="center" renderer="Current.switchSingleStyle">'+singlePercent+'%</div>';
        }
        return '<div width="8%" headerAlign="center" align="center" renderer="Current.switchSingleStyle">待定</div>';
    },

    "switchPassStyle":function(e){
        var row = e.row;
        var passAdd = row.passAdd;
        var passPercent;
        if (passAdd != null) {
            var list = [];
            list = JSON.parse(passAdd);
            for (var i=0; i<list.length; i++) {
                passPercent = list[1].percent;
            }
            return '<div width="8%" headerAlign="center" align="center" renderer="Current.switchPassStyle">'+passPercent+'%</div>';
        }
        return '<div width="8%" headerAlign="center" align="center" renderer="Current.switchPassStyle">待定</div>';
    },

}
