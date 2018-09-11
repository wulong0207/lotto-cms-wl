var lottery_gyj = {
	init : function() {
		mini.parse();
        lottery_gyj.grid = mini.get('datagrid');
        lottery_gyj.lotteryIssue = mini.get('lotteryIssue');
        $.get("sysmgr/dic/dictionary?code=0507", function(result){
            Dic.matchStatus = result['0507'];
            // 冠军竞猜彩种ID：308
            // 冠亚军竞猜彩种ID：309
            mini.get('matchStatus').setData(Dic.matchStatus);
            ComReq.issue(309,function(result){
                lottery_gyj.lotteryIssue.setData(result);
                lottery_gyj.lotteryIssue.select(0);
                lottery_gyj.search();
            });
        });
	},
    search : function() {
        var data = new mini.Form("form1").getData();
        data.lotteryCode = 309;
        lottery_gyj.grid.load(data);
    },
    onActionRenderer:function(e){
        var grid = e.sender;
        var record = e.record;
        var uid = record._uid;
        var s = ' <a class="mini-button"  plain="true" href="javascript:Cms.editRow(\'datagrid\')" >修改</a>';
        if (grid.isEditingRow(record)) {
            var s = '<a class="mini-button"  plain="true" href="javascript:lottery_gyj.updateRow(\'datagrid\',\'' + uid + '\')" >保存</a>&nbsp;&nbsp;&nbsp;'+
                '<a class="mini-button"  plain="true" href="javascript:Cms.cancelEditRow(\'datagrid\',\'' + uid + '\')">取消</a>';
        }
        return s;
    },
    updateRow:function(gridname,uid){
        Cms.rowFormValidate("#" + gridname, function() {
            var g = lottery_gyj.grid;
            var row = g.getRowByUID(uid);
            g.commitEdit();
            var rowData = g.getChanges(null,true);
            if(rowData.length == 0){
                mini.alert("请先修改表格数据，再保存");
                return;
            }
            var modifyRow = rowData[0];
            modifyRow.againstId =modifyRow.id;
            // 如果修改状态为淘汰要发mq
            // 18已淘汰
            if(modifyRow.matchStatus == 18) {
                modifyRow.lotteryCode = 309;
                modifyRow.systemCode = row.systemCode;
                modifyRow.homeTeamFullName = row.homeName;
            }
            $.ajax({
                url : 'lotterymgr/gyj',
                data : modifyRow,
                type : 'PUT',
            }).done(
                ret => {
                    if(ret>0){
                    g.reload();
                    mini.showTips({
                        content: '保存成功',
                        state: "success",
                        x: "center",
                        y: "center",
                        timeout: 2000
                    });
                }else{
                    mini.alert('保存失败');
                }
            })
            .fail(a=>console.error('保存失败'));
        });

    },
    draw:function(){
        var param = {
            url: "taskmgr/job/runTask",
            action: "get"
        }
        param.data = {'jobId':'000020','lotteryCode':'309','lotteryIssue':lottery_gyj.lotteryIssue.value};
        var message = "确定是否执行 <span style='color:red'>开奖</span>?";
        Cms.ajaxFirm(message, param, function (data) {
            Cms.drawSchedule(309, lottery_gyj.lotteryIssue.value, 1, () => {}
            )
            ;
        })
    }
}
lottery_gyj.init();