var Current = {
    "init" : function(code) {
        mini.parse();

        Current.type = mini.get("type");
        //窗体对象, form对象
        Current.activityConfigForm = new mini.Form("activityConfigForm");
        Current.sportAgainstForm = new mini.Form("sportAgainstForm");

        Current.activitySportRuleWindow = mini.get("activitySportRuleWindow");
        Current.sportAgainstWindow = mini.get("sportAgainstWindow");

        Current.datagrid = mini.get("activityRule_datagrid");
        Current.userGrid = mini.get("user_datagrid");
        Current.sportAgainstDatagrid = mini.get("sportAgainst_datagrid");

        Current.activityCode = mini.get("activityCode");

        Current.activityCode.setValue(code);
        // 冠军活动
        Current.type.setValue(15);
        Current.orderIds = [];
        $.get("sysmgr/dic/dictionary?code=0507", function(result){
            Dic.matchStatus = result['0507'];
            Current.initDetail(code);
        });
    },
    "initDetail":function(activityCode){
        Current.userGrid.load({activityCode : activityCode});
        var type = Current.type.getValue();
        var param = {
            url : "operatemgr/activity/config/detail",
            action : "post",
            data : {activityCode : activityCode, type: type}
        }
        Cms.ajax(param, function(data){
            if(data){
                var discountArr = [];
                data.isRealUser && discountArr.push(1);
                data.isMobile && discountArr.push(2);
                data.discountStr = discountArr.join(',');
                data.type = type;
                Current.activityConfigForm.setData(data);
                Current.datagrid.setData(data.operateActivityRuleBOList);
                mini.get('targetTeam').setData(data.operateActivityRuleBOList);
                Current.datagrid.getData().forEach(row=>{
                    Current.datagrid.beginEditRow(row);
                    Current.orderIds.push(row.orderId);
                });
            }
        })
    },
    "toAdd": function () {
        Current.sportAgainstDatagrid.load(
            {lotteryCode: 308},
            (result)=>{
            result.data.forEach((d)=>{
                d.sportAgainstInfoId = d.id;
                delete d.id;
            });
            }
        );
        Current.sportAgainstWindow.show();
    },
    saveTeamBeforeEdit : function() {
        Current.datagrid.commitEditRow(Current.datagrid.getSelected());
    },
    "merge": function(){
        Cms.rowFormValidate("activityRule_datagrid",()=>{
            var data = Current.activityConfigForm.getData();
            // 15, "世界杯冠军竞猜活动"
            data.type = Current.type.getValue();
            // 真实用户,手机号码
            var discountArr = data.discountStr.split(',');
            data.isRealUser = discountArr.contains('1') ? 1 : 0;
            data.isMobile = discountArr.contains('2') ? 1 : 0;
            Current.datagrid.commitEdit();
            data.operateActivityRuleVOList = Current.datagrid.getChanges(null);
            for(var i=0,l=data.operateActivityRuleVOList.length,row; i < l;i++) {
                row = data.operateActivityRuleVOList[i];
                if(row.localUser<row.realityUser) {
                    mini.alert('竞猜总人数不能小于竞猜总人数')
                    return;
                }
                if(row.localJackpot<row.realityJackpot) {
                    mini.alert('奖金池不能小于奖金池')
                    return;
                }
            }
            // 检查排序是否有变
            var newOrderIds = Current.datagrid.data.map(row => row.orderId);
            Current.orderIds;
            if(newOrderIds.join() !== Current.orderIds.join()) {
                data.operateActivityRuleVOList = Current.datagrid.data;
                data.operateActivityRuleVOList.forEach((row,index) =>{
                    row.orderId = index;
                });
            }
        var param = {
                url : 'operatemgr/activity/config/merge',
                action : "post",
                data : data
            };
            Cms.saveDataStringify(param, function(){
                setTimeout(function(){
                    Current.closeConfigWindow();
                },300)
            })
        });

    },
    "closeConfigWindow": function() {
        // 确定关闭
        Current.orderIds = [];
        window.CloseOwnerWindow();
    },

    "mergeSportAgainst": function() {
        var newRow = Current.sportAgainstDatagrid.getSelecteds();
        var sportAgainstInfoIds = Current.datagrid.data.map(row => row.sportAgainstInfoId);
        for (var i=0,l=newRow.length;i<l; i++) {
            if (sportAgainstInfoIds.contains(newRow[i].sportAgainstInfoId)) {
                mini.alert("对阵重复，请重新选择");
                return;
            }
        }
        Current.datagrid.addRows(newRow, Current.datagrid.data.length);
        Current.sportAgainstWindow.hide();
    },
    upload : function(e) {
        var file = $("#file").val();
        if(!file || !(file.endsWith(".xlsx") || file.endsWith(".xls"))) {
            mini.alert("请选择一个Excel文件上传");
            return;
        }
        var url = "operatemgr/activity/chp/user/upload";
        $.ajaxFileUpload({
            url:url,
            secureuri:false,
            fileElementId:'file',//file标签的id
            dataType: 'json',//返回数据的类型
            data:{activityCode : mini.get('activityCode').value},//一同上传的数据
            success: function (obj, status) {
                if(obj.errorCode) {
                    if(obj.errorCode == Code.success) {
                        mini.showTips({
                            content: "上传成功",
                            state: "success",
                            x: "center",
                            y: "center",
                            timeout: 2000
                        });
                        Current.userGrid.reload();
                        Current.teamGridReload();
                    } else {
                        mini.alert(obj.message);
                    }
                }
            },
            error: function (data, status, e) {
                mini.alert(e);
            }
        });
    },
    teamGridReload : function () {
        $.get('operatemgr/activity/chp/team',{activityCode:mini.get('activityCode').value}).done((ret)=>{
            Current.datagrid.setData(ret);
        Current.orderIds=[];
        Current.datagrid.getData().forEach(row=>{
            Current.datagrid.beginEditRow(row);
        Current.orderIds.push(row.orderId);});
        });
    },
    excel : function () {
        location = "operatemgr/activity/chp/user/excel?activityCode="+mini.get('activityCode').value;
    },
    draw : function () {
        var myTeam = mini.get('targetTeam').value;
        if(!myTeam) {
            mini.alert("请选择一个球队");
            return;
        }
        var message = "确定是否执行 <span style='color:red'>开奖</span>?";
        mini.confirm(message, "提示", function(e) {
            if (e == "ok") {
                $.get("operatemgr/activity/chp/draw",{ruleId : myTeam})
                    .done(
                        (result) =>{
                    if(result.errorCode == Code.success) {
                    mini.showTips({
                        content: "开奖成功",
                        state: "success",
                        x: "center",
                        y: "center",
                        timeout: 2000
                    });
                    Current.teamGridReload();
                } else {
                    mini.alert(result.message);
                }
            }
            )
            .fail(
                    (a,b,c)=>{
                    console.error(a)
                mini.alert("开奖失败")
            }
            );
            }
        });

    },
    send : function () {
        var myTeam = mini.get('targetTeam').value;
        if(!myTeam) {
            mini.alert("请选择一个球队");
            return;
        }

        var message = "确定是否执行 <span style='color:red'>派奖</span>?";
        mini.confirm(message, "提示", function(e) {
            $.get("operatemgr/activity/chp/send",{ruleId : myTeam})
                .done(
                    (result) =>{
                if(result.errorCode == Code.success) {
                mini.showTips({
                    content: "派奖成功",
                    state: "success",
                    x: "center",
                    y: "center",
                    timeout: 2000
                });
                Current.teamGridReload();
            } else {
                mini.alert(result.message);
            }
        }
            )
            .fail(
                (a,b,c)=>{
                console.error(a)
            mini.alert("派奖失败")
        }
            );
        });
    }
}
