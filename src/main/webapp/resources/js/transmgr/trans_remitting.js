trans_remitting = {
	init : function() {
		mini.parse();
		trans_remitting.grid = mini.get("datagrid");
		trans_remitting.remittingBank = mini.get("remittingBank");
		trans_remitting.userSearchType = mini.get("userSearchType");
		trans_remitting.status = mini.get("status");
		trans_remitting.statusUpd = mini.get("statusUpd");

		ComReq.dictionary("0207,0918",
			function(result) {
                Dic.userSearchType = result["0207"];
				Dic.remittingBank = result["0918"];
				Dic.status = [{id:1,text:'待充值'},{id:2,text:'已充值'},{id:3,text:'误充值'}];
                trans_remitting.remittingBank.setData(Dic.remittingBank);
				trans_remitting.userSearchType.setData(Dic.userSearchType);
				trans_remitting.userSearchType.select(0);
                trans_remitting.status.setData(Dic.status);
                trans_remitting.statusUpd.setData(Dic.status);
                trans_remitting.statusUpd.select(0);
                trans_common.search(trans_remitting.grid);
			});
    },
	visitStatus : function(obj) {
        var rows = trans_remitting.grid.getSelecteds();
        if(!rows.length) {
        	mini.alert('请至少选择一条记录');
        	return;
		}
		var newStatus = trans_remitting.statusUpd.value;
        if(!newStatus) {
            mini.alert('请选择状态值');
            return;
		}
        $.ajax({
            url : 'transmgr/remitting',
            data : {ids:rows.map(row=>row.id).join(),status:newStatus},
            type : 'PUT',
        }).done(
            function(res) {
                trans_common.search(trans_remitting.grid);
                if(res.errorCode == Code.success){
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
            })
	},

    image:function(e){
        var screenshot = e.row.screenshot;
        var html = '<img src={0} style="width: 80px;height: 20px;" alt="点击放大图片">'.format(screenshot);
        return html;
    },


};
trans_remitting.init();
$(function () {
	$('img').live('click',function () {
		$('#img').prop('src',$(this).prop('src'));
        mini.get('imgWin').show();
    });
});
