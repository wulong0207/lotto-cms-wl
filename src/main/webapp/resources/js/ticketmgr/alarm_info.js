var Current = {
	"init" : function() {
		mini.parse();

		Current.grid = mini.get("datagrid");
		Current.editWindow = mini.get("editWindow");

		Current.timeType = mini.get("timeType");
		Current.alarmType = mini.get("alarmType");
		Current.status = mini.get("status");
		Current.alarmLevel = mini.get("alarmLevel");
		Current.alarmChild = mini.get("alarmChild");

		ComReq.dictionary("1401,1402,1403,1404,1405,1406", function(result) {
			Dic.alarmType = result["1401"], 
			Dic.status = result["1402"],
			Dic.alarmLevel = result["1403"],
			Dic.timeType = result["1404"],
			Dic.alarmChildService = result["1405"],
			Dic.alarmChildSystem = result["1406"],
			Current.alarmType.setData(Dic.alarmType);
			Current.status.setData(Dic.status);
			Current.alarmLevel.setData(Dic.alarmLevel);
			Current.timeType.setData(Dic.timeType);
			Current.timeType.select(0);
			Current.search();
		});
	},
	"search" : function() {
		Current.grid.load(Current.getParamJson());
	},
	"getParamJson" : function() {
		var form = new mini.Form("#searchCondition");
		var para = form.getData();
		var timeType = para.timeType;
		
		var startTime = mini.get("startTime").getFormValue();
		var endTime = mini.get("endTime").getFormValue();
		switch (timeType) {
		case "0":
			para.alarmTimeStart = startTime;
			para.alarmTimeEnd = endTime;
			break;
		case "1":
			para.dealTimeStart = startTime;
			para.dealTimeEnd = endTime;
			break;
		default:
			break;
		}
		return para;
	},
	"dispose":function(){
		var param = {};
		param.url = "ticketmgr/alarm/dispose";
		param.action = "PUT";
		param.confirmMsg = "确定要处理选中行吗?";
		param.successMsg = "处理成功!";
		param.id = "id";
		Cms.dispose(Current.grid, param,function(){
			
		});
	},
	"doSubmit" : function() {
		Cms.submit(new mini.Form("#editform"), function() {
			Current.editWindow.hide();
			Cms.reload("datagrid");
		});
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
	}
}
Current.init();