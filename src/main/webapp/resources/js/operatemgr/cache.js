var Current ={
		"init":function(){
			mini.parse();
			
			Current.grid = mini.get("datagrid");
			Current.editWindow = mini.get("editWindow");
			Current.addWindow = mini.get("addWindow");
			
			Current.cacheGroup =  mini.get("cacheGroup");
			Current.name = mini.get("name");
			Current.cacheKey = mini.get("cacheKey");
			
			ComReq.dictionary("0519", function(result) {
				Dic.cacheGroup = "[{\"id\":\"1\",\"text\":\"公共组\"},{\"id\":\"2\",\"text\":\"竞技组\"},{\"id\":\"3\",\"text\":\"数字彩\"},{\"id\":\"4\",\"text\":\"支撑组\"}]";
				Current.cacheGroup.setData(Dic.cacheGroup);
				mini.get("cacheGroup_edit").setData(Dic.cacheGroup);
				mini.get("cacheGroup_add").setData(Dic.cacheGroup);
			});
			Current.search();
		},
		"search":function(){
			Current.grid.load(Current.getParamJson());
		},
		"getParamJson":function(){
			var type = Current.cacheGroup.getValue();
			var name = Current.name.getValue();
			var cKey = Current.cacheKey.getValue();
			var para ={
					cacheGroup:type,
					name:name,
					cacheKey:cKey
			};
			return para;
		},
		"editwin" : function() {
			var row = Current.grid.getSelected();
			if (row) {	
				var param ={
		     	    	url:"cmscache/detail/"+row.cacheKey,
		     	    	action:"GET",
		     	    	data:"",
		     	}
				Cms.ajax(param, function(row){
					row.action = "put";
					row.url = "cmscache/update";
	    	        var form = new mini.Form("#editform");
	    	        form.clear();
	    	        form.setData(row);
	    	        Current.editWindow.show();
	     	   	});
			} else {
				mini.alert("请选择一行数据");
			}
		},
		"addwin" : function() {
			var addForm = new mini.Form("#addForm");
			addForm.clear();
			addForm.setData({
				status : 1,
				action : "post",
				url : "cmscache/add"
			});
			Current.addWindow.show();
		},
		"add" : function(){
			var addForm = new mini.Form("#addForm");
		    var data = addForm.getData(true, false);			
			var param = {};
			param.url = "cmscache/add";
			param.data = data;
			param.action = "post";
			Cms.ajaxFirm("确定保存?", param, function(data){
				Current.addWindow.hide();	
				Cms.reload("datagrid");  
				Cms.showTips("保存成功!", 2000);
			})
		},
		"delCache":function(){
			var row = Current.grid.getSelected();
			if (row) {
				var param ={
		     	    	url:"cmscache/del/"+row.id,
		     	    	action:"GET",
		     	    	data:"",
		     	}
				Cms.ajaxFirm("确定删除缓存？",param, function(row){
					mini.alert("操作成功！");
					Cms.reload("datagrid");
		 	   	});
			}else{
				mini.alert("请选择一行数据");
			}
		},
		"clearCache":function(index){
			var row = Current.grid.getRow(index);
			var param ={
	     	    	url:"cmscache/clear/"+row.cacheKey,
	     	    	action:"GET",
	     	    	data:"",
	     	}
			Cms.ajaxFirm("确定清除缓存？",param, function(row){
				mini.alert("操作成功！");
				Cms.reload("datagrid");
	 	   	});
		},
		"onClearRenderer":function(e){
		     var index = e.rowIndex;
		     var s = '<a class="mini-button"  plain="true" href="javascript:Current.clearCache('+index+')">清除</a>';
		     return s;
		},
		"closeAddWin":function(){
			Current.addWindow.hide();	
		},
		"onrowdblclick":function(){
			 mini.get("updCache").doClick();
		},
		"doSubmit":function(){
	        Cms.submit(new mini.Form("#editform"),function(){
	        	Current.editWindow.hide();
		        Cms.reload("datagrid");  
		    });
	     }
}
Current.init();