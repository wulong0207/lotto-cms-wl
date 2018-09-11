var Current = {
	"init" : function() {
		mini.parse();
		Current.grid = mini.get("datagrid1");
		Current.grid.load();
	},
    "addRow":function() {          
        var newRow = { name: "New Row" };
        Current.grid.addRow(newRow);
        Current.grid.beginEditCell(newRow, "directMinMoney");
    },
    "removeRow":function() {
        var rows = Current.grid.getSelecteds();
        if (rows.length > 0) {
            if (confirm("确定删除选中记录？")) {
            	Current.grid.removeRows(rows);
            }
        } else {
            alert("请选中一条记录");
        }
    },
    "saveData":function() {   
        Current.grid.validate();
        if (Current.grid.isValid() == false) {
            //alert("请校验输入单元格内容");
            var error = Current.grid.getCellErrors()[0];
            Current.grid.beginEditCell(error.record, error.column);
            return;
        }
        var data = Current.grid.getChanges();
        var json = mini.encode(data);
        //为空则不提交
        if(json=='[]'){return;}
        var lists = [];
        data = Current.grid.getData();
		$.each(data, function(index, item) {
			 var obj={};
			 obj.directMinMoney = item.directMinMoney;
			 obj.directMaxMoney = item.directMaxMoney;
			 obj.directRatio = item.directRatio;
			 obj.agentMinMoney = item.agentMinMoney;
			 obj.agentMaxMoney = item.agentMaxMoney;
			 obj.agentRatio = item.agentRatio;
			 lists.add(obj);
		});
		var param = {
				url : "agent/addDefaultConfigs",
				action : "post",
				data : lists
			};
		Cms.saveDataStringify(param, function() {
			Cms.reload("datagrid1");
		});
    },
    "applyAllAgents":function(e){
        Current.grid.validate();
        if (Current.grid.isValid() == false) {
            var error = Current.grid.getCellErrors()[0];
            Current.grid.beginEditCell(error.record, error.column);
            return;
        }
        var lists = [];
        data = Current.grid.getData();
		$.each(data, function(index, item) {
			 var obj={};
			 obj.directMinMoney = item.directMinMoney;
			 obj.directMaxMoney = item.directMaxMoney;
			 obj.directRatio = item.directRatio;
			 obj.agentMinMoney = item.agentMinMoney;
			 obj.agentMaxMoney = item.agentMaxMoney;
			 obj.agentRatio = item.agentRatio;
			 lists.add(obj);
		});
		var param = {
				url : "agent/applyAllAgents",
				action : "post",
				data : lists,
				tip : "您确认要将当前默认返佣应用到当前所有代理人吗？",
				tipTitle : "当前默认返佣应有到所有代理人",
				ingTip : "应用中..."
			};
		Cms.saveDataStringify(param, function() {
			Cms.reload("datagrid1");
		});
    },
    "onCellValidation":function(e){
    	var record = e.record;
    	var directMinMoney =record.directMinMoney;
    	var directMaxMoney = record.directMaxMoney;
    	if(directMinMoney!='' && directMaxMoney!='' && directMinMoney>=directMaxMoney){
        	e.isValid = false;
        	e.errorText = "金额不合法";
    	}
    },
    "OnbeginEditRow":function(row){
    	console.info(mini.encode(row));
    }
}
Current.init();