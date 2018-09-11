var exchange_record = {
    init: function () {
        mini.parse();
        exchange_record.grid = mini.get("datagrid");
        exchange_record.searchForm = new mini.Form("#searchForm");
        exchange_record.editForm = new mini.Form("#editForm");
        exchange_record.editWindow = mini.get("editWindow");
        exchange_record.rechargeForm = new mini.Form("#rechargeForm");
        exchange_record.rechargeWindow = mini.get("rechargeWindow");

        // 所有彩种加载
        ComReq.lottery("", function (result) {
            Dic.allCode = result;
            mini.get("lotteryCode").setData(result);
        });

        exchange_record.grid.load();
    },
    query_onclick: function () {
        exchange_record.grid.load(exchange_record.searchForm.getData(true, false));
    },
    grid_onrowdblclick: function () {
        var row = exchange_record.grid.getSelected();
        exchange_record.editForm.setData(row);
        exchange_record.editWindow.show();
    },
    export_excel: function () {
        var data = exchange_record.searchForm.getData(true,false);
        location.href = "cooperate/exchange-record/export?" + Cms.jsonParamStr(data);
        ;
    },
    recharge_add: function () {
        exchange_record.rechargeForm.clear();
        mini.get("url").setValue("cooperate/exchange-record/save");
        mini.get("action").setValue("POST");
        exchange_record.rechargeWindow.show();
    },
    recharge_change: function () {
        var combox = mini.get("recharge");
        var value = combox.getValue();
        var lotteryCode_edit = mini.get("lotteryCode_edit");
        if (value == 1) {
            var channelId = mini.get("channelId").getValue();
            var url = "cooperate/lottery/list-by-channelId?channelId=" + channelId;
            lotteryCode_edit.load(url);
            lotteryCode_edit.setVisible(true);
        } else {
            lotteryCode_edit.setVisible(false);
        }
    },
    save_recharge: function () {

        var data = exchange_record.rechargeForm.getData();

        if(data.payClass==1){
            if(data.lotteryCode==null ||data.lotteryCode==""){
                mini.alert("请输入充值彩种");
                return;
            }
        }

        Cms.submit(exchange_record.rechargeForm, function (data) {
            exchange_record.rechargeWindow.hide();
            exchange_record.grid.reload();
        });
    }

};
exchange_record.init();