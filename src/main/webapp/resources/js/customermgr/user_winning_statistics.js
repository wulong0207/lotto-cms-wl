var Current ={
		"init":function(){
			mini.parse();
			Current.grid = mini.get("datagrid");
			Current.attrType =  mini.get("attrType");
			Current.upoladWindow=  mini.get("upoladWindow");
			Current.beachType = mini.get("beachType");
			ComReq.dictionary("0207", function(result){
				 Current.attrType.setData(result["0207"]);
				 Current.attrType.select(0);
				 Current.search();
			});
		}
         ,
		"search":function(){
			Current.grid.load(Current.getParamJson());
		},
		"getParamJson":function(){
			var type = Current.attrType.getValue();
			var typeValue = mini.get("attrContent").getValue();
			var para ={};
			//参数判断，与赋值
			switch (type) {
			case "1":
				para.nickName = typeValue;
				break;
			case "2":
				para.accountName = typeValue;
				break;
			case "3":
				para.cusMobile = typeValue;
				break;
			case "4":
				para.actualName = typeValue;
				break;
			case "5":
				para.idNum = typeValue;
				break;
			}
			return para;
		}
		,
		"excel":function(){
			var param = Cms.jsonParamStr(Current.getParamJson());
			var url = "customermgr/uws/excel?"+param;
			location = url;
		},
	     "batchSearch":function(){
	    	  var param ={
		     	    	url:"customermgr/uws/beach/search",
		     	    	action:"post",
		     	    	data:{
		     	    		"type":Current.beachType.getValue(),
		     	    		"content":mini.get("beachContent").getValue()
		     	    	},
		     	    }	     	   
		     	    Cms.ajax(param, function(data){
		     	    	 Current.upoladWindow.hide();
		            	 Current.grid.setData(data);
		     	    });
	     },
	     "uploadSearch":function(){
	    	 Current.upoladWindow.show("left","30px");
	     },
	     "countnew":function(){
	    	 var param  ={
	    				"url":"customermgr/uws/countnew",
	    				 "action" : "get" 
	    			}
	    			Cms.ajaxResultJsonStr(param,function(data){
	    				debugger;
	    			});    	   		     	    
	     },
}
Current.init();