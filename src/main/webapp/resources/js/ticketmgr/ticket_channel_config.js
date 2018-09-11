var Current = {
	"init" : function() {
		mini.parse();

		Current.grid = mini.get("datagrid");
		Current.editWindow = mini.get("editWindow");
		Current.sendGrid = mini.get("send_grid");

		Current.lotteryCategory = mini.get("lotteryCategory");
		Current.lotteryCode = mini.get("lotteryCode");
		Current.lotteryCode_edit = mini.get("lotteryCode_edit");
		Current.childType = mini.get("childType");

		ComReq.dictionary("0001,0303,1201,1202", function(result) {
			Dic.lotteryCategory = result["0303"];
			Dic.sendStatus = result["1201"];
			Dic.lotteryAuto = result["1202"];
			Dic.week =  result["0001"];
			mini.get("sendStatus").setData(Dic.sendStatus);
			mini.get("sendStatus_edit").setData(Dic.sendStatus);
			mini.get("searchAuto_edit").setData(Dic.lotteryAuto);
			Current.lotteryCategory.setData(Dic.lotteryCategory);

			ComReq.ticketChannel(function(result) {
				Dic.ticketChannelId = result;
				mini.get("ticketChannelId").setData(result);
				mini.get("ticketChannelId_edit").setData(result);
				Current.search();
			})
		});
		ComReq.lottery("", function(result) {
			Dic.allCode = result;
			Current.lotteryCode.setData(result);
			Current.lotteryCode_edit.setData(result);
		});

	},
	"editwin" : function() {
		var row = Current.grid.getSelected();
		if (row) {
			Current.editWindow.set({
				title : "修改出票渠道配置信息"
			});
			row.action = "put";
			row.url = "ticketmgr/ticketconfig";
			row.ticketChannelId_show = row.ticketChannelId;
			var form = new mini.Form("#editform");
			form.clear();
			ComReq.lotteryChild(row.lotteryCode, function(result) {
				Current.childType.setData(result);
				form.setData(row);
				Current.editWindow.show();
			});
			var timeDatas =Ticket.split(row.allowSendTime);
			Current.sendGrid.setData(timeDatas);
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
		Ticket.sendTicketOK("allowSendTime_edit");
		Cms.submit(new mini.Form("#editform"), function() {
			Current.editWindow.hide();
			Cms.reload("datagrid");
		});
	},
	"addwin" : function() {
		Current.editWindow.set({
			title : "修改出票渠道配置信息"
		});
		var row = {
			action : "post",
			url : "ticketmgr/ticketconfig",
		}
		var form = new mini.Form("#editform");
		form.clear();
		form.setData(row);
		Current.childType.setData([]);
		Current.sendGrid.setData([]);
		Current.editWindow.show();
	},
	"lotteryCategoryChange" : function() {
		var code = Current.lotteryCategory.getValue();
		ComReq.lottery(code, function(result) {
			Current.lotteryCode.setData(result);
		});
	},
	"lotteryCodeChange" : function() {
		var lotteryCode = Current.lotteryCode_edit.getValue();
		ComReq.lotteryChild(lotteryCode, function(result) {
			Current.childType.setData(result);
		});
	},
    "onCloseClickType":function (e){
 	   var obj = e.sender;
 	   obj.setText("");
 	   obj.setValue("");
 	   ComReq.lottery("", function(result){
 		  Current.lotteryCode.setData(result);
 	   });
 	}

}
Current.init();