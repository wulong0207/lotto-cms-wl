var Current = {
	"init" : function() {
		mini.parse();
		 var tabs = mini.get("druidTab");
		var param ={
				"action":"get",
				"url":"monity/druid/list"
		 }
		Cms.ajax(param, function(data) {
			tabs.setTabs(data); 
		});
	}
}
Current.init();