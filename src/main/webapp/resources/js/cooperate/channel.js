var channel = {
    init: function () {
        mini.parse();


        // 所有彩种加载
        ComReq.lottery("", function (result) {
            Dic.allCode = result;
        });

        ComReq.ticketChannel(function (result) {
            Dic.ticketChannelId = result;
            mini.get("ticketChannel").setData(result);
            var grid = mini.get("datagrid");
            grid.load();
        });

        ComReq.dictionary("1604", function (result) {
            mini.get("type").setData(result["1604"]);
            Dic.type = result["1604"];
        });

        channel.getChannelId();
    },
    channel_query_onclick: function () {
        var id = mini.get("channelId").value;
        var name = mini.get("channelName").value;

        var params = {};
        if (id != null && id != undefined && id != '') {
            params.marketChannelId = id;
        }
        if (name != null && name != undefined && name != '') {
            params.cooperateName = name;
        }
        var grid = mini.get("datagrid");
        grid.load(params);
    },
    column_onclick: function () {
        var grid = mini.get("datagrid");
        var data = grid.getSelected();
        if (data.length == 0) {
            mini.alert("请选择修改数据");
            return;
        }

        data.marketChannelId_tree=data.marketChannelId;
        var editWindow = mini.get("editWindow");
        var editForm = new mini.Form("#editForm");
        editForm.clear();
        editForm.setData(data);
        mini.get("treeselect").disable();
        if (data.type == 1) {
            var lotteryDatagrid = mini.get("lotteryDatagrid");
            var childChannelGrid = mini.get("childChannelGrid");
            var addLottery = mini.get("addLottery");
            var add_childChannel = mini.get("add_childChannel");
            addLottery.show();
            add_childChannel.hide();
            childChannelGrid.hide();
            lotteryDatagrid.show();
            lotteryDatagrid.load({"channelId": data.marketChannelId});
        } else {
            var lotteryDatagrid = mini.get("lotteryDatagrid");
            var childChannelGrid = mini.get("childChannelGrid");
            var addLottery = mini.get("addLottery");
            var add_childChannel = mini.get("add_childChannel");
            addLottery.hide();
            add_childChannel.show();
            lotteryDatagrid.hide();
            childChannelGrid.show();
            childChannelGrid.load({"cooperateChannelId": data.marketChannelId});
        }

        editWindow.show();
    },
    column_channel_render: function (e) {
        var data = {"1": "福彩积分兑换", "2": "体彩积分兑换"};
        var value = e.value;

        var str = value.split(",");
        var result = "";
        str.forEach(function (value) {
            if (result == "") {
                result += data[value];
            } else {
                result += "," + data[value];
            }

        });
        return result;
    },
    add_lottery: function () {
        Cms.addEditRow("lotteryDatagrid");
    },
    add_childChannel: function () {
        Cms.addEditRow("childChannelGrid");
    },
    save_channel: function () {
        var editWindow = mini.get("editWindow");
        var editForm = new mini.Form("#editForm");
        editForm.validate();
        var data = editForm.getData(true, false);
        var params = {
            url: "cooperate/channel/save",
            action: "POST",
            data: data
        }

        Cms.ajax(params, function (data) {
            editWindow.hide();
            Cms.reload("datagrid");
        });

    },
    treegrid_onclick: function () {
        var row = mini.get("treeselect").getValue();
        // var value = mini.get("marketChannelId").getValue();
        // if ((value == '' || value == null || value == undefined) && row.indexOf(",") == -1) {
            mini.get("marketChannelId").setValue(row);
        // }
    },
    add_channel: function () {
        var editForm = new mini.Form("#editForm");
        editForm.clear();
        mini.get("treeselect").enable();
        var editWindow = mini.get("editWindow");
        var lotteryDatagrid = mini.get("lotteryDatagrid");
        var childChannelGrid = mini.get("childChannelGrid");
        lotteryDatagrid.load();
        childChannelGrid.load();
        editWindow.show();
    },
    del_channel: function () {
        var grid = mini.get("datagrid");
        var row = grid.getSelected();
        if(row.length<1){
            mini.alert("请选择记录");
        }
        mini.confirm("确定删除记录？", "确定？",
            function (action) {
                if (action == "ok") {
                    var grid = mini.get("datagrid");
                    var row = grid.getSelected();
                    var params = {
                        url: "cooperate/channel/del",
                        action: "POST",
                        data: {marketChannelId: row.marketChannelId, id: row.id}
                    };
                    Cms.ajax(params, function (data) {
                        if(data==1){
                            mini.alert("删除成功");
                        }else{
                            mini.alert("删除失败");
                        }

                        Cms.reload("datagrid");
                    });

                }
            }
        );
    },
    onActionRenderer: function (e) {
        var grid = e.sender;
        var record = e.record;
        var uid = record._uid;

        var s = ' <a class="mini-button"  plain="true" href="javascript:Cms.editRow(\'lotteryDatagrid\',\'' + uid + '\')" >修改</a>&nbsp;&nbsp;&nbsp;' +
            '<a class="mini-button"  plain="true" href="javascript:channel.delRow(\'lotteryDatagrid\',\'' + uid + '\')">删除</a>';

        if (grid.isEditingRow(record)) {
            var s = ' <a class="mini-button"  plain="true" href="javascript:channel.saveLotteryRow(\'lotteryDatagrid\',\'' + uid + '\')" >保存</a>&nbsp;&nbsp;&nbsp;' +
                '<a class="mini-button"  plain="true" href="javascript:Cms.cancelEditRow(\'lotteryDatagrid\',\'' + uid + '\')">取消</a>';
        }
        return s;
    },
    onChannelActionRenderer: function (e) {
        var grid = e.sender;
        var record = e.record;
        var uid = record._uid;

        var s = ' <a class="mini-button"  plain="true" href="javascript:Cms.editRow(\'childChannelGrid\',\'' + uid + '\')" >修改</a>&nbsp;&nbsp;&nbsp;' +
            '<a class="mini-button"  plain="true" href="javascript:channel.delChildChannel(\'childChannelGrid\',\'' + uid + '\')">删除</a>';

        if (grid.isEditingRow(record)) {
            var s = ' <a class="mini-button"  plain="true" href="javascript:channel.saveChildChannel(\'childChannelGrid\',\'' + uid + '\')" >保存</a>&nbsp;&nbsp;&nbsp;' +
                '<a class="mini-button"  plain="true" href="javascript:Cms.cancelEditRow(\'childChannelGrid\',\'' + uid + '\')">取消</a>';
        }
        return s;
    },
    saveChildChannel: function (gridname, uid) {
        var lotteryDatagrid = mini.get("childChannelGrid");
        Cms.rowFormValidate("#" + gridname, function () {
            var row = lotteryDatagrid.getRowByUID(uid);
            lotteryDatagrid.commitEdit();
            var rowData = lotteryDatagrid.getChanges();
            if (rowData.length == 0) {
                mini.alert("请先修改表格数据，再保存");
                return;
            }
            row.cooperateChannelId = mini.get("marketChannelId").getValue();
            var params = {
                url: "cooperate/channel/save-child-channel",
                action: "POST",
                data: row
            }
            Cms.ajax(params, function (data) {
                Cms.reload(gridname);
            })
        });
    },
    delChildChannel: function (gridname, uid) {
        var lotteryDatagrid = mini.get("childChannelGrid");
        Cms.rowFormValidate("#" + gridname, function () {
            mini.confirm("确定删除记录？", "确定？",
                function (action) {
                    if (action == "ok") {
                        var row = lotteryDatagrid.getRowByUID(uid);
                        var params = {
                            url: "cooperate/channel/del-child-channel",
                            action: "POST",
                            data: row
                        }
                        Cms.ajax(params, function (data) {
                            Cms.reload(gridname);
                        });
                    }
                }
            );
        });
    },
    saveLotteryRow: function (gridname, uid) {
        var lotteryDatagrid = mini.get("lotteryDatagrid");
        Cms.rowFormValidate("#" + gridname, function () {
            var row = lotteryDatagrid.getRowByUID(uid);
            lotteryDatagrid.commitEdit();
            var rowData = lotteryDatagrid.getChanges();
            if (rowData.length == 0) {
                mini.alert("请先修改表格数据，再保存");
                return;
            }
            row.channelId = mini.get("marketChannelId").getValue();
            var params = {
                url: "cooperate/lottery/save",
                action: "POST",
                data: row
            }
            Cms.ajax(params, function (data) {
                Cms.reload(gridname);
            })
        });
    },
    delRow: function (gridname, uid) {
        var lotteryDatagrid = mini.get("lotteryDatagrid");
        Cms.rowFormValidate("#" + gridname, function () {
            mini.confirm("确定删除记录？", "确定？",
                function (action) {
                    if (action == "ok") {
                        var row = lotteryDatagrid.getRowByUID(uid);
                        var params = {
                            url: "cooperate/lottery/del",
                            action: "POST",
                            data: row
                        }
                        Cms.ajax(params, function (data) {
                            Cms.reload(gridname);
                        });
                    }
                }
            );
        });
    },
    "getChannelId": function (channelId) {
        mini.get("treeselect").load("operatemgr/activity/channel?channelId=" + channelId);
    },
    "resetPassword": function () {
        mini.confirm("确定重置密码？", "确定？",
            function (action) {
                if (action == "ok") {
                    var id = mini.get("id").getValue();
                    var params = {
                        url: "cooperate/channel/reset-password",
                        action: "POST",
                        data: {id: id}
                    }
                    Cms.ajax(params, function (data) {
                        var editWindow = mini.get("editWindow");
                        var grid = mini.get("datagrid");
                        editWindow.hide();
                        grid.reload();
                    });
                }
            }
        );


    }
};
channel.init();