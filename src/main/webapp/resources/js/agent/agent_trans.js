var agent_trans = {
	init : function() {
		mini.parse();
        agent_trans.grid = mini.get('datagrid');
        agent_trans.grid.load();
	},
    search : function() {
        var data = new mini.Form("form1").getData(true);
        agent_trans.grid.load(data);
    },
    excel:function(url){
        location = "agent/trans/excel?" + Cms.jsonParamStr(new mini.Form("#form1").getData(true));
    },
}
agent_trans.init();