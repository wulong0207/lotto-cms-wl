var Current = {
	"init" : function() {
		mini.parse();

		Current.grid = mini.get("datagrid");
		Current.editWindow = mini.get("editWindow");
		Current.sendGrid = mini.get("send_grid");
		
		Current.commonGrid = mini.get("common_grid");

		Current.lotteryCode = mini.get("lotteryCode");
		Current.lotteryIdEdit = mini.get("lotteryId_edit");
		ComReq.dictionary("1408,0001", function(result) {
			Dic.week =  result["0001"];
			Dic.status = result["1408"];
			mini.get("status_edit").setData(Dic.status);
			Current.search();
		});

		ComReq.lottery("", function(result) {
			Dic.allCode = result;
			Current.lotteryCode.setData(result);
			mini.get("lotteryId_edit").setData(Dic.allCode);
		});
		Current.commonGrid.load();
	},
	"editwin" : function() {
		var row = Current.grid.getSelected();
		if (row) {
			Current.editWindow.set({
				title : "修改监控配置信息"
			});
			Current.lotteryIdEdit.set({
				enabled : false,
			});
			row.action = "put";
			row.url = "ticketmgr/monitorconfig";
			var form = new mini.Form("#editform");
			form.clear();
			form.setData(row);
			Current.editWindow.show();
			var timeDatas = [];
			if(row.refreshTime){
				timeDatas =Ticket.split(row.refreshTime,"time");				
			}
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
		Ticket.sendTicketOK("refreshTime_edit","time");
		Cms.submit(new mini.Form("#editform"), function() {
			Current.editWindow.hide();
			Cms.reload("datagrid");
		});
	},
	"addwin" : function() {
		Current.editWindow.set({
			title : "添加出票渠道"
		});
		Current.lotteryIdEdit.set({
			enabled : true,
		});
		var row = {
			action : "post",
			url : "ticketmgr/monitorconfig",
		}
		var form = new mini.Form("#editform");
		form.clear();
		form.setData(row);
		Current.sendGrid.setData([]);
		Current.editWindow.show();
	},
	"lotteryIdChange" : function() {
		var lotteryId = mini.get("lotteryId_edit").getValue();
		mini.get("lotteryName").setValue(
				Cms.getDictionaryValue(Dic.allCode, lotteryId));
	},
	"onActionRendererChild" : function(e) {
		var gridAdd = e.sender;
		var record = e.record;
		var uid = record._uid;
		var s = '<a class="mini-button"  plain="true" href="javascript:Cms.cancelEditRow(\'common_grid\',\''
				+ uid + '\')">取消</a>';
		return s;
	},
	"saveCommon" : function() {
		Cms.formValidate("#common_grid","保存数据有误",function(){
			Current.commonGrid.commitEdit();
	        var rows = Current.commonGrid.getChanges();
	        if(rows.length == 0){
	           mini.alert("请先修改表格数据，再保存");
	           return;
	        } 
	        var json = mini.encode(rows);
	        Cms.save(json, "ticketmgr/monitorconfig/common", function(){
	            Cms.reload("common_grid");
	        });
        });
	}
}
Current.init();