var Current = {
	"init" : function() {
		mini.parse();

		Current.timeType = mini.get("timeType");
		//Current.grid = mini.get("datagrid");
		Current.editWindow = mini.get("editWindow");

		Current.grade = mini.get("grade");
		Current.terminalPlatform = mini.get("terminalPlatform");
		Current.settlementType = mini.get("settlementType");
		Current.timeType = mini.get("timeType");
		Current.channelIdEdit = mini.get("channelId_edit");

		ComReq.dictionary("0701,0702,0703,0704,0705", function(result) {
			Dic.grade = result["0701"];
			Dic.terminalPlatform = result["0702"];
			Dic.settlementType = result["0703"];
			Dic.timeType = result["0704"];
			Dic.channelStatus = result["0705"];
            Dic.majia = [{id:0,text:'否'},{id:1,text:'是'}];

			Current.grade.setData(Dic.grade);
			Current.terminalPlatform.setData(Dic.terminalPlatform);
			Current.settlementType.setData(Dic.settlementType);
			Current.timeType.setData(Dic.timeType);
			Current.timeType.select(0);
			//Current.search();
			
			Current.getParentId(0)

			mini.get("grade_edit").setData(Dic.grade);
			mini.get("terminalPlatform_edit").setData(Dic.terminalPlatform);
			mini.get("settlementType_edit").setData(Dic.settlementType);
			mini.get("channelStatus_edit").setData(Dic.channelStatus);
			mini.get("majiaSerach").setData(Dic.majia);
			mini.get("majia").setData(Dic.majia);
		});

		mini.get('channelTreegrid').load("operatemgr/marketchannel/list");
	},
	"editwin" : function() {
		var row = mini.get('channelTreegrid').getSelectedNode();
		if (row) {
			//加载父级ID
			Current.getParentId(row.grade - 1);
			Current.editWindow.set({
				title : "修改市场渠道信息"
			});
			Current.channelIdEdit.set({
				enabled:false,
			});
			row.action = "put";
			row.url = "operatemgr/marketchannel";
			var form = new mini.Form("#editform");
			form.clear();
			form.setData(row);
			mini.get('terminalPlatform_edit').setEnabled(false);
			mini.get('secretKey').setEnabled(false);
			Current.editWindow.show();
		} else {
			mini.alert("请选择一行数据");
		}
	},
	"search" : function() {
		//Current.grid.load(Current.getParamJson());
		mini.get('channelTreegrid').load(Current.getParamJson());
	},
	"getParamJson" : function() {
		var grade = Current.grade.getValue();
		var settlementType = Current.settlementType.getValue();
		var terminalPlatform = Current.terminalPlatform.getValue();
		var channelId = mini.get("channelId").getValue();
		var channelName = mini.get("channelName").getValue();
		var parentChannelId = mini.get("parentChannelId").getValue();
		var para = {
			grade : grade,
			settlementType : settlementType,
			terminalPlatform : terminalPlatform,
			channelId : channelId,
			channelName : channelName,
			parentChannelId : parentChannelId,
			majia : mini.get('majiaSerach').getValue()
		};

		var timeType = Current.timeType.getValue();
		var startTime = mini.get("startTime").getFormValue();
		var endTime = mini.get("endTime").getFormValue();
		// 参数判断，与赋值
		switch (timeType) {
		case "0":
			para.coopStartTime = startTime;
			para.coopEndTime = endTime;
			break;
		case "1":
			para.createStartTime = startTime;
			para.createEndTime = endTime;
			break;
		case "2":
			para.updateStartTime = startTime;
			para.updateEndTime = endTime;
			break;
		}
		return para;
	},
	"excel" : function() {
		var param = Cms.jsonParamStr(Current.getParamJson());
		var url = "operatemgr/marketchannel/excel?" + param;
		location = url;
	},
	"onrowdblclick" : function() {
		mini.get("edit").doClick();
	},
	"doSubmit" : function() {
		Cms.submit(new mini.Form("#editform"), function() {
			Current.editWindow.hide();
			//Cms.reload("datagrid");
			Cms.reload("channelTreegrid");
		});
	},
	"addwin" : function() {
		Current.getParentId(0);
		Current.editWindow.set({
			title : "添加市场渠道"
		});
		Current.channelIdEdit.set({
			enabled:true,
		});
		var row = {
			action : "post",
			url : "operatemgr/marketchannel",
		}
		var form = new mini.Form("#editform");
		form.clear();
		form.setData(row);
		mini.get('terminalPlatform_edit').setEnabled(true);
		mini.get('secretKey').setEnabled(false);
		Current.editWindow.show();
	},
	"getParentId" : function(grade,channelId) {
		var param = {
			action : "get",
			url : "operatemgr/marketchannel/parent/id?grade="+grade+"&channelId="+channelId
		}
		Cms.ajax(param, function(data) {
			mini.get("parentChannelId_edit").setData(data);
		});
	},
	"gradeChange":function(){
		var grade = mini.get("grade_edit").getValue();
		if(grade){
			var channelId = Current.channelIdEdit.getValue();
			Current.getParentId(grade - 1,channelId);
		}
	},
	"onSettlementRate" : function(e){
        var value = e.value;
        return (value==0 || value) ? value + '%' : '';
	}
}
Current.init();