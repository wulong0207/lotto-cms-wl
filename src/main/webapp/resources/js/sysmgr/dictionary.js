mini.parse();
	var grid = mini.get("datagridDic");
	var gridDetail = mini.get("datagridDicDetail");
	var editWindow = mini.get("editWindow");
	var editDetail = mini.get("editDetail");
	
	search();
	function search(){
		var para ={
				dicName:mini.get("name").getValue(),
				dicCode:mini.get("code").getValue()
			};
		grid.load(para);
	}
	
	function addDic(){
	    editWindow.setTitle("添加字典");
	 	editWindow.show();
    	var form = new mini.Form("#editform");
    	form.clear();
    	var row = {
    	    action:"post",
    	   	url:"sysmgr/dic"
    	}
    	form.setData(row);
    	
    	mini.get("dicCode").set({allowInput:true});
	}
	
	function doSubmit(){
		Cms.submit(new mini.Form("#editform"),function(){
	       editWindow.hide();
	       grid.reload();
	    });
	}
	function onrowdblclick(){
	    mini.get("editDic").doClick();
	}
	function editDic(){
	    var row = grid.getSelected();
	    if (row) {
	     	row.action = "put";
	        row.url="sysmgr/dic";
	        
	   	 	editWindow.setTitle("修改编码");
	   	 	editWindow.show();
	        var form = new mini.Form("#editform");
	        form.clear();
	        form.setData(row);
	        
            mini.get("dicCode").set({allowInput:false});
	    } else {
	        mini.alert("请选择一行数据");
	    }
	}
	
	//数据字典详情
	function onSelectionChanged(){
	   var record = grid.getSelected();
	   if(record){
	       var para ={
				dicCode:record.dicCode
			};
		 gridDetail.load(para);
	   }
	}
	
	
	function onDrawcellDetail(obj){
		 var status;
		 if(obj.field == "isFixed"){
			status = obj.record.isFixed;
			switch(status){
				case 1: obj.cellHtml = "是"; break;
				case 0: obj.cellHtml = "否"; break;
			}
		}else if(obj.field == "status"){
			status = obj.record.status;
			switch(status){
				case 1: obj.cellHtml = "启用"; break;
				case 0: obj.cellHtml = "禁用"; break;
			}
		}
	}
	function onrowdblclickDetail(){
	    var ed = mini.get("editDicDetail");
	    if(ed){
	        editDicDetail();
	    }
	}
	function editDicDetail(){
	   var row = gridDetail.getSelected();
	    if (row) {
	        row.action = "put";
	        row.url = "sysmgr/dic/detail";
	        
	    	editDetail.setTitle("修改编码详情");
	   	 	editDetail.show();
	        var form = new mini.Form("#editformDetail");
	        form.clear();
	        form.setData(row);
	    } else {
	        mini.alert("请选择一行数据");
	    }
	}
	
	function doSubmitDetail(){
	   Cms.submit(new mini.Form("#editformDetail"),function(){
	       editDetail.hide();
	       gridDetail.reload();
	   });
	}
	function addDicDetail(){
	    var record = grid.getSelected();
	    if(record){
	    	var row = {
		    	isFixed:"1",
		    	status:"1",
		    	action:"post",
		    	dicCode:record.dicCode,
		    	url:"sysmgr/dic/detail"
	    	};
	    	
	    	editDetail.setTitle("添加字典详情");
		 	editDetail.show();
	    	var form = new mini.Form("#editformDetail");
	    	form.clear();
	    	form.setData(row);
	    }else{
	        mini.alert("请选择一行字典数据");
	    }
   	}