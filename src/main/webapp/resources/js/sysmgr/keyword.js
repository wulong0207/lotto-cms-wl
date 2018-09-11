var Current={
	init:function(){
		mini.parse();
		ComReq.dictionary("2001,2002", function(result){
			 Dic.grade = result["2001"];		
		     Dic.status = result["2002"];
		     
		     mini.get("grade").setData(Dic.grade);
			 mini.get("status").setData(Dic.status);
		   		    
		     Current.search();
		});
	},
	search:function(){
		var json={};
		json.keyword=mini.get("keyword").getValue();
		
		Current.datagrid = mini.get("datagrid");
		Current.datagrid.load(json);
	},
	edit : function() {

		var row = Current.datagrid.getSelected();
		if (row) {
			Current.editWindow = mini.get("editWindow");

			Current.editWindow.set({
				width : "640px",
				height : "200px",
				title : "编辑"
			});
			
			$("#editKeyword").show();
			$("#editKeywords").hide();
			
			var form = new mini.Form("#editform");
			form.clear();
			form.setData(row);
			$("#url").val("sysmgr/keyword/updateList");
			$("#action").val("post");
			
			$("#saveBtn").attr("onClick","Current.update();");
			
			Current.editWindow.show();

		}
	},
	addShow:function(){
		Current.editWindow = mini.get("editWindow");

		Current.editWindow.set({
			width : "640px",
			height : "200px",
			title : "新增"
		});
		
		$("#editKeywords").show();
		$("#editKeyword").hide();
		
		var form = new mini.Form("#editform");
		form.clear();
		mini.get("status").select(1);
		
		
		$("#url").val("sysmgr/keyword/addList");
		$("#action").val("post");
		
		
		$("#saveBtn").attr("onClick","Current.save();");
		Current.editWindow.show();
	},
	save:function(){
		
		Cms.submit(new mini.Form("#editform"), function() {
			Current.editWindow.hide();
			Cms.reload("datagrid");
		});
	},
	update:function(){
		
		var data = new mini.Form("#editform").getData(true, false);
		var json = "["+mini.encode(data)+"]";
		Cms.save(json, "sysmgr/keyword/updateList", function() {
			Current.editWindow.hide();
			Cms.reload("datagrid");
		});
	},
	exportXls:function(){
		location.href="sysmgr/keyword/export?keyword="+mini.get("keyword").getValue();
	}
};
Current.init();