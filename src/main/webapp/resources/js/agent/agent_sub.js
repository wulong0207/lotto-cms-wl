var agent_sub = {
	init : function() {
		mini.parse();
        agent_sub.grid = mini.get('datagrid');
        agent_sub.grid.load();
	},
    search : function() {
        var data = new mini.Form("form1").getData();
        agent_sub.grid.load(data);
    },
}
agent_sub.init();