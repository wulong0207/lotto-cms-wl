var Current = {
	"init" : function() {
		mini.parse();

		Current.grid = mini.get("datagrid");
		Current.editWindow = mini.get("editWindow");

		Current.ticketChannelId = mini.get("ticketChannelId");

		$.get("ticketmgr/ticketchannel/drawer/dictionary", function(result){
			mini.get("drawerName").setData(result);
		});
		ComReq.dictionary("0003", function(result) {
			Dic.use = result["0003"];
			mini.get("channelStatus_edit").setData(Dic.use);
			mini.get("lotteryStatus_edit").setData(Dic.use);
			mini.get("ticketStatus_edit").setData(Dic.use);
		});
		Current.search();
		
	},
	"editwin" : function() {
		var row = Current.grid.getSelected();
		if (row) {
			Current.editWindow.set({
				title : "修改出票渠道信息"
			});
			Current.ticketChannelId.set({
				enabled:false,
			});
			row.action = "put";
			row.url = "ticketmgr/ticketchannel";
			var form = new mini.Form("#editform");
			form.clear();
			form.setData(row);
			Current.editWindow.show();
		} else {
			mini.alert("请选择一行数据");
		}
	},
	"search" : function() {
		Current.grid.load(Current.getParamJson());
	},
	"getParamJson" : function() {
		var form = new mini.Form("#searchCondition");
		var para = form.getData();
		return para;
	},
	"onrowdblclick" : function() {
		mini.get("edit").doClick();
	},
	"doSubmit" : function() {
		Cms.submit(new mini.Form("#editform"), function() {
			Current.editWindow.hide();
			Cms.reload("datagrid");
		});
	},
	"addwin" : function() {
		Current.editWindow.set({
			title : "添加出票渠道"
		});
		Current.ticketChannelId.set({
			enabled:true,
		});
		var row = {
			action : "post",
			url : "ticketmgr/ticketchannel",
		}
		var form = new mini.Form("#editform");
		form.clear();
		form.setData(row);
		Current.editWindow.show();
	},
	"synMoney" : function(){
		var param = {
				url:"taskmgr/job/runTask",
				action:"get",
				data:{'jobId':'000003'}
		}
		Cms.ajaxFirm("确认执行?", param, function(data){
			Cms.showTips(data.message, 2000);
		})
	}
}
Current.init();