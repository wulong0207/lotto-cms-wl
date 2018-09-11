var Current = {
	"init" : function() {
		mini.parse();

		Current.grid = mini.get("datagrid");
		Current.editWindow = mini.get("editWindow");

		Current.alarmType = mini.get("alarmType");
		Current.status = mini.get("status");
		Current.alarmLevel = mini.get("alarmLevel");
		Current.alarmChild = mini.get("alarmChild");
		Current.alarmChildEdit = mini.get("alarmChild_edit");

		ComReq.dictionary("1401,1407,1403,1405,1406", function(result) {
			Dic.alarmType = result["1401"], 
			Dic.status = result["1407"],
			Dic.alarmLevel = result["1403"],
			Dic.alarmChildService = result["1405"],
			Dic.alarmChildSystem = result["1406"],
			Current.alarmType.setData(Dic.alarmType);
			Current.status.setData(Dic.status);
			Current.alarmLevel.setData(Dic.alarmLevel);
			mini.get("alarmType_edit").setData(Dic.alarmType);
			mini.get("status_edit").setData(Dic.status);
			mini.get("alarmLevel_edit").setData(Dic.alarmLevel);
			Current.search();
		});
	},
	"onDrawcell":function(obj){
		if(obj.field == "alarmChild"){
		    var alarmChild = obj.record.alarmChild;
		    var alarmType = obj.record.alarmType;
		    var text;
		    switch (alarmType) {
			case 1:
				text = Cms.getDictionaryValue(Dic.alarmChildService, alarmChild);
				break;
			case 2:
				text = Cms.getDictionaryValue(Dic.alarmChildSystem, alarmChild);
				break;
			default:
				break;
			}
		    obj.cellHtml = text;
		}
	},
	"editwin" : function() {
		var row = Current.grid.getSelected();
		if (row) {
			Current.editWindow.set({
				title : "修改报警配置信息"
			});
			Current.changeChildEdit(row.alarmType);
			row.action = "put";
			row.url = "ticketmgr/alarmconfig";
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
			title : "添加报警配置"
		});
		var row = {
			action : "post",
			url : "ticketmgr/alarmconfig",
		}
		var form = new mini.Form("#editform");
		form.clear();
		form.setData(row);
		Current.editWindow.show();
		Current.alarmChildEdit.setData([]);
	},
	"alarmTypeChange" : function() {
		var code = Current.alarmType.getValue();
		if (code) {
			if (code == "1") {
				Current.alarmChild.setData(Dic.alarmChildService);
			} else if (code == "2") {
				Current.alarmChild.setData(Dic.alarmChildSystem);
			}
		}
	},
	"alarmTypeChangeEdit" : function() {
		var code = mini.get("alarmType_edit").getValue();
		Current.changeChildEdit(code);
	},
	"changeChildEdit":function(code){
		if (code) {
			if (code == "1") {
				Current.alarmChildEdit.setData(Dic.alarmChildService);
			} else if (code == "2") {
				Current.alarmChildEdit.setData(Dic.alarmChildSystem);
			}
		}
	}
}
Current.init();