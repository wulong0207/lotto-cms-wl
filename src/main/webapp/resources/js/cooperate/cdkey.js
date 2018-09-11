var cdkey = {
    init: function () {
        mini.parse();


        cdkey.importWindow = mini.get("importWindow");
        cdkey.importForm = new mini.Form("#importForm");

        ComReq.ticketChannel(function (result) {
            Dic.ticketChannelId = result;
            mini.get("ticketChannel_info").setData(result);
            mini.get("ticketChannel").setData(result);
        });

        ComReq.dictionary("1603", function (result) {
            Dic.status = result["1603"];
            mini.get("stauts").setData(result["1603"]);
        });
        // 所有彩种加载
        ComReq.lottery("", function (result) {
            Dic.allCode = result;
            Dic.allCode.push({id: "-1", text: "总数量"});
            var grid = mini.get("datagrid");
            grid.load();
        });
        cdkey.set_balance("");
    },
    set_balance: function (channelId) {
        var params = {
            url: "cooperate/channel/balance",
            action: "POST",
            data: {marketChannelId:channelId}
        };

        Cms.ajax(params, function (data) {
            mini.get("balance").setValue(data);
        });
    },
    channel_value_change: function () {
        var channelId = mini.get("channelId").getValue();
        if(channelId == 9999){
            channelId = "";
        }
        cdkey.set_balance(channelId);
    },
    add_cdkey: function () {
        cdkey.importForm.clear();
        cdkey.importWindow.show();
    },
    upload: function () {
        mini.get("url").setValue("cooperate/cdkey/upload");
        mini.get("action").setValue("POST");
        var h = "#file > input:file";
        Cms.uploadData(cdkey.importWindow, cdkey.importForm, h, function () {
            cdkey.importWindow.hide();
            Cms.reload("datagrid");
        });
    },
    action_renderer: function (e) {
        var grid = e.sender;
        var record = e.record;
        var uid = record._uid;

        var s = ' <a class="mini-button"  plain="true" href="javascript:cdkey.lotteryInfo(\'datagrid\',\'' + uid + '\')" >详情</a>';
        return s;
    },
    lotteryInfo: function (grid, uid) {
        var lotteryDatagrid = mini.get(grid);
        var row = lotteryDatagrid.getRowByUID(uid);
        var channelId = mini.get("channelId").getValue();
        var params = {
            channelId: channelId,
            lotteryCode: row.lotteryCode
        };
        var editForm = new mini.Form("#editForm");
        var editWindow = mini.get("editWindow");
        var gridname = mini.get("recode_list_grid");

        var queryForm = new mini.Form("#queryForm");
        queryForm.setData(params);
        gridname.on("load", function () {
            gridname.mergeColumns(["rowid", "myCdKey"]);
        });
        gridname.load(params);

        editForm.setData(row);
        editWindow.show();
    },
    query: function () {
        var channelId = mini.get("channelId").getValue();
        var params = {
            channelId: channelId
        };
        mini.get("datagrid").load(params);
    },
    queryInfo: function () {
        var gridname = mini.get("recode_list_grid");
        var queryForm = new mini.Form("#queryForm");
        gridname.on("load", function () {
            gridname.mergeColumns(["rowid", "myCdKey"]);
        });
        gridname.load(queryForm.getData(true, false));
    },
    export_excel: function () {
        var queryForm = new mini.Form("#queryForm");
        var data = queryForm.getData(true, false);
        location.href = "cooperate/cdkey/export?" + Cms.jsonParamStr(data);

    },
};
cdkey.init();