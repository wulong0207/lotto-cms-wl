var Current = {
	"init" : function(id,mt) {
		mini.parse();
		Current.grid = mini.get("datagrid");
		Current.id = id;
		Current.mt = mt;
		Current.search();
		
	},
	"search" : function() {
		var mt = Current.mt+"-01 00:00:00"
		Current.grid.load({"agentId":Current.id,"startTime":mt,"endTime":mt});
	}
}
