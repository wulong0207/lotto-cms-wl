var Current = {
    "init" : function() {
        mini.parse();
        Current.activityCode = mini.get("activityCode");
        Current.platform = mini.get("platform");
        Current.rate = mini.get('rate');
        Current.shop = mini.get('shop');
        Current.minimum = mini.get('minimum');
        Current.activityConfigWindow = mini.get("activityConfigWindow");
        Current.activityConfigForm = mini.get("activityConfigForm");
        Current.choiceType = mini.get("choiceType");
        Current.valid = mini.get('valid');
        Current.multiple = mini.get('multiple');
        Current.returnType = mini.get("returnType");
        Current.returnMoney = mini.get("returnMoney");
        Current.lotteryCode = mini.get("lotteryCode");
        Current.playingMethod = mini.get("playingMethod");
        /*var play =[
            {id:1,text:'胜平负'},
            {id:2,text:'让球胜平负'},
            {id:3,text:'总进球'},
            {id:4,text:'比分'},
            {id:5,text:'半全场'},
            {id:6,text:'混投'}
        ];*/
        ComReq.dictionary("1004,1005,1006,1007,1008,0002", function(result) {
            Dic.choiceType = result["1004"];
            Dic.platform = result["1005"];
            Dic.returnType = result["1006"];
            Dic.isParticipate = result["1007"];
            Dic.ret = result["1008"];
            Dic.isOrNot = result["0002"];
            Current.choiceType.setData(Dic.choiceType);
            Current.platform.setData(Dic.platform);
            Current.valid.setData(Dic.isOrNot);
            Current.multiple.setData(Dic.isOrNot);
            Current.returnType.setData(Dic.returnType);
            Current.shop.setData(Dic.isParticipate);
            Current.minimum.setData(Dic.isParticipate);
            Current.rate.setData(Dic.ret);
        });
        ComReq.lottery("", function(result){
            Current.lotteryCode.setData(result);
            Dic.allCode = result;
            Current.lotteryCode.setData(Dic.allCode);
        });
    },
    "initData":function (code) {
        Current.init();
        Current.activityCode.setValue(code);
    },
    "addActivityConfig":function () {
        var form = new mini.Form("#activityConfigForm");
        form.clear();
        form.setData({
            operation : "add"
        });
        Current.activityConfigWindow.setTitle("新增活动配置详情");
        Current.activityConfigWindow.show();
    },
    "addActivityConfigRow":function (datagrid, detailFormId, detailWindow) {
        var form = new mini.Form(detailFormId);
        form.validate();
        if (form.isValid() == false) {
            mini.alert("表单数据不完整");
            return;
        }
        var grid = mini.get(datagrid);
        var row = grid.getSelected();
        var newRow = form.getData();
        newRow.operation === "edit" ? grid.updateRow(row, newRow) : grid
                .addRow(newRow, grid.data.length);
        mini.get(detailWindow).hide();
    },
    "closeActivityConfig":function () {
        Current.activityConfigWindow.hide();
    },
    "lotteryTypeChange" : function (){
        debugger;
        var code = Current.lotteryCode.getValue();
        if(code){
            ComReq.lotteryChild(code, function(result){
                Current.playingMethod.setData(result);
            });
        }
    },
    "merge" : function() {
        var form = new mini.Form("activityConfigForm"), indexChage = false;
        form.validate();
        if (!form.isValid()) {
            mini.alert("表单数据不完整");
            return;
        }
        var activityData = form.getData();
        if(!/^\d{2}:\d{2}:\d{2}$/.test(lotteryData.onlineTime)){ alert("请输入正确的计划上线时间格式,格式为00:00:00");return}
        if(!/^\d{2}:\d{2}:\d{2}$/.test(lotteryData.offlineTime)){ alert("请输入正确的计划下线时间格式,格式为00:00:00");return}
        var newActivityConfigInfo = mini.get("activityConfigDatagrid").data;
        // 详情有修改时才保存
        if (JSON.stringify(newLotteryInfos) !== JSON
                .stringify(operate_lottery.lotteryInfo)) {
            // 如果有增加或删除详情,则更新整个详情列表
            if (operate_lottery.lotteryInfo
                && newActivityConfigInfo.length !== operate_lottery.lotteryInfo.length) {
                // 设置排序id
                $.each(newLotteryInfos, function(index, item) {
                    item.orderId = index + 1;
                })
                lotteryData.lotteryInfos = newActivityConfigInfo;
            } else {
                /*
                 * 详情顺序有变化则更新所有详情列表，否则只更新变化部分
                 */
                $.each(newLotteryInfos,function(index, info) {
                    if (!info.id
                        || info.id != Current.lotteryInfo[index].id) {
                        indexChage = true;
                        return;
                    }
                });
                if (indexChage) {
                    // 设置排序id
                    $.each(newLotteryInfos, function(index, item) {
                        item.orderId = index + 1;
                    })
                    lotteryData.lotteryInfos = newLotteryInfos;
                } else {
                    lotteryData.lotteryInfos = mini.get("lotteryInfoDatagrid")
                        .getChanges();
                }
            }
        }
        // 若有删除详情记录，则传id给controller
        operate_lottery.ids && (lotteryData.infoIds = operate_lottery.ids);
        // lotteryData.lotteryInfos =
        // mini.get("lotteryInfoDatagrid").getChanges();
        $.each(lotteryData.lotteryInfos , function(index, item) {
            item.offlineTime = mini.formatDate(item.offlineTime, 'yyyy-MM-dd HH:mm:ss')
        })
        var param = {
            url : lotteryData.url,
            action : lotteryData.action,
            data : lotteryData
        };
        Cms.saveDataStringify(param, function() {
            // 把ids清空
            operate_lottery.ids = null;
            operate_lottery.detailWindow.hide();
            Cms.reload("datagrid");
        });
    },
}
