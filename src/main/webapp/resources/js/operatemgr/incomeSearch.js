var Current = {
	"init" : function() {
		mini.parse();
		Current.grid = mini.get("datagrid");
		Current.accountName = mini.get("accountName");
		Current.startTime = mini.get("startTime");
		Current.endTime = mini.get("endTime");
		Current.search();
		
		$('#exportBtn').click(function(e) {
			var excelUrl = $(e.srcElement).data('excel-url');
			var param = Current.getParamJson();
			location = excelUrl+"?" + Cms.jsonParamStr(param);
		});
	},
	"search" : function() {
		Current.grid.load(Current.getParamJson());
	},
	"getParamJson" : function() {
		var accountName = Current.accountName.getValue();
		var startTime = Current.startTime.getFormValue();
		var endTime = Current.endTime.getFormValue();
		if(startTime!=""){
			startTime+="-01 00:00:00";
		}
		if(endTime!=""){
			endTime+="-01 00:00:00";
		}
		var para = {
				"accountName" : accountName,
				"startTime" : startTime,
				"endTime" : endTime
		};
		return para;
	},
	"onActionRenderer":function(e){
        var grid = e.sender;
        var record = e.record;
        var uid = record.agentId;
        var mt = record.mt;
        var row = grid.getRowByUID(record._uid);
		var s = ' <a  plain="true"  onclick="Current.incomeDetail('+uid+',\''+mt+'\')" >查看详情</a>&nbsp;';
		return s;
	},
	"incomeDetail":function(id,mt){
        var height = 600,width= 1200;
		mini.open({
            url: "agent/dayIncomeSearch",
            title: "月度详情",
            width: width, height: height,
            onload: function () {
                var iframe = this.getIFrameEl();
                iframe.contentWindow.initData(id,mt);
            },
            ondestroy: function (action) {
               // Cms.reload("datagrid");
            }
        });
	}
}
Current.init();