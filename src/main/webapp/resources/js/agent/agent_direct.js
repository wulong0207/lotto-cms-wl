var agent_direct = {
	init : function() {
		mini.parse();
        agent_direct.grid = mini.get('datagrid');
        agent_direct.grid.load();
	},
    search : function() {
        var data = new mini.Form("form1").getData();
        agent_direct.grid.load(data);
    },
}
agent_direct.init();