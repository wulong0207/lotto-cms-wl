var Current={
    "init": function(){
        mini.parse();

        // 解析jscolor
        window.jscolor.installByClassName('jscolor');

    	Current.datagrid = mini.get("datagrid");
    	Current.matchWindow = mini.get("matchWindow");
    	Current.matchform = new mini.Form("matchform");

    	Current.matchType = mini.get("matchType");
    	Current.fiveLeague = mini.get("fiveLeague");
        Current.searchMatchType = mini.get("searchMatchType");
    	Current.isFiveLeague = mini.get("isFiveLeague");

    	Current.matchFullName = mini.get("matchFullName");
    	Current.matchShortName = mini.get("matchShortName");
    	Current.logoUrl = mini.get("logoUrl");
    	
    	//绑定渠道赛事
    	Current.matchSourceWindow = mini.get("matchSourceWindow");
    	Current.matchSourceDatagrid = mini.get("matchSourceDatagrid");
    	
    	Current.matchSourceName = mini.get("matchSourceName");
    	Current.matchSourceAbbrName = mini.get("matchSourceAbbrName");
    	Current.matchSourceType = mini.get("matchSourceType");
    	Current.matchSource = mini.get("matchSource");
    	Current.matchId = mini.get("matchId");
    	
    	
    	

        ComReq.dictionary("0501,0002,0520", function(result){
            Dic.isOrNot = result["0002"];
            Dic.matchType = result["0501"];
            Dic.source = result["0520"];

            Current.matchType.setData(Dic.matchType);
            Current.fiveLeague.setData(Dic.isOrNot);
            Current.searchMatchType.setData(Dic.matchType);
            Current.isFiveLeague.setData(Dic.isOrNot);

            Current.matchType.select(0);
            Current.fiveLeague.select(1);
            
            //渠道赛事
            Current.matchSourceType.setData(Dic.matchType);
            Current.matchSource.setData(Dic.source);
        });

        Current.initDetail();
    },
    "initDetail": function () {
        var json = {};
        Current.datagrid.load();
    },
    "search" : function() {
        Current.datagrid.load(Current.getParamJson());
    },
    "getParamJson" : function() {
        var matchFullName = Current.matchFullName.getValue();
        var matchShortName = Current.matchShortName.getValue();
        var matchType = Current.searchMatchType.getValue();
        var fiveLeague = Current.isFiveLeague.getValue();
        var para = {
            matchFullName : matchFullName,
            matchShortName : matchShortName,
            matchType : matchType,
            fiveLeague : fiveLeague
        };
        return para;
    },
    "edit": function() {
        Current.matchWindow.setTitle("修改赛事信息");
        var row = Current.datagrid.getSelected();
        if (row) {
            row.operation = "edit";
            var form = Current.matchform;
            //初始化颜色
            if (row.matchColor) {
                document.getElementById('matchColor').jscolor.fromString(row.matchColor);
            }
            $("#img").removeAttr("src");
            form.clear();
            form.setData(row);
            //初始化图片
            if(row.logoImg)
            	$("#img").attr("src",row.logoImg);
            Current.matchWindow.show();
        } else {
            mini.alert("请选择一条数据");
        }
    },
    "openImage": function(){
        var lotterytypeCatalogue = '_upload_images/sport';
        Cms.imageManage(lotterytypeCatalogue,function(data){
            Current.setImage(data);
        });
    },
    "setImage": function(data){
        $("#img").attr("src",data.url);
        Current.logoUrl.setValue(data.dir);
    },
    "add": function () {
        Current.matchWindow.setTitle("新增赛事信息");
        var form = Current.matchform;
        $("#img").removeAttr("src");
        form.clear();
        form.setData({
            operation : "add",
            status : "0",
        });
        data = form.getData();
        Current.matchWindow.show();
    },
    "del" : function(){
        var row = Current.datagrid.getSelected();
        if(row){
            Cms.del(Current.datagrid, "sport/matchInfo/del", function(){});
        } else {
            mini.alert("请选择一行数据");
        }
    },
    "merge": function () {
        var form = Current.matchform;
        form.validate();
        if (!form.isValid()) {
            mini.alert("表单数据不完整");
            return;
        }
        var row = Current.datagrid.getSelected();
        data = form.getData();
        var matchColor = document.getElementById('matchColor').jscolor.toHEXString();
        data.matchColor = (matchColor);
        var url = "";
        if (data.operation == "edit") {
            url = "sport/matchInfo/upd";
        } else {
            url = "sport/matchInfo/add";
        }

        var param = {
            url: url,
            action: "post",
            data: data
        }
        Cms.saveDataStringify(param, function(){
                Current.close();
                Current.datagrid.reload();
        })

    },
    "close": function () {
        Current.matchWindow.hide();
    },
    "matchOpt" : function(e){
    	var record =  e.record;
    	return '<a class="mini-button"  plain="true" href="javascript:Current.initMatchSource('+ record.id + ');">渠道赛事</a>'
    },
    "initMatchSource" : function(matchId){
    	var param = {
    			matchId:matchId
    	}
    	Current.matchId.setValue(matchId);
    	Current.matchSourceDatagrid.load(param);
    	Current.matchSourceWindow.show();
    },
    "searchSource" : function(){
    	Current.matchSourceDatagrid.load(Current.getSourceParamJson());
    },
    "matchSourceOpt" : function(e){
	   	 var grid = e.sender;
		 var record = e.record;
	     var uid = record._uid;
		 var matchId = record.matchId;
		 var html = '';
		 if(matchId){
			 html = '<a class="mini-button" plain="true" href="javascript:Current.removeBind('+ record.id +')">解除</a>';
		 }else{
			 html = '<a class="mini-button"  plain="true" href="javascript:Current.bind('+ record.id +')">绑定</a>';
		 }
		 html += ' <a class="mini-button"  plain="true" href="javascript:Current.delRow('+ record.id +')">删除</a>'
	     if (grid.isEditingRow(record)) {
             html = '<a class="mini-button" plain="true" href="javascript:Current.updRow(\'matchSourceDatagrid\', \'' + uid + '\')">修改</a>'
              + ' <a class="mini-button" plain="true" href="javascript:Cms.cancelEditRow(\'matchSourceDatagrid\',\'' + uid + '\')">取消</a>'
         }		 
	     return html;
    },
    "bind" : function(id){
		var matchId = Current.matchId.getValue();
    	var param = {
    			url:"sport/matchInfo/source/upd",
    			action:"post",
    			data:{'id':id, 'matchId':matchId}
    	}    	
    	Cms.saveDataStringify(param, function(){
        	Current.matchSourceDatagrid.reload();
    	})
    },
    "removeBind" : function(id){
    	var param = {
    			url:"sport/matchInfo/source/remove?id=" + id
    	}
    	Cms.saveDataStringify(param, function(data){
        	Current.matchSourceDatagrid.load(Current.getSourceParamJson());
    	})    	
    },
    "getSourceParamJson" : function(){
    	var param = {};
    	var matchSourceNameVal = Current.matchSourceName.getValue();
    	var matchSourceAbbrNameVal = Current.matchSourceAbbrName.getValue();
    	var matchSourceTypeVal = Current.matchSourceType.getValue();
    	var matchSourceVal = Current.matchSource.getValue();
    	var matchId = Current.matchId.getValue();
    	
    	if(matchSourceNameVal || matchSourceAbbrNameVal || matchSourceTypeVal || matchSourceVal){
    		param = {
    	    		matchName : matchSourceNameVal,
    	    		matchAbbrName : matchSourceAbbrNameVal,
    	    		matchType : matchSourceTypeVal,
    	    		source: matchSourceVal
    	    	};
    	}else{
    		param = {
    			matchId:matchId
    		}
    	}
    	return param;
    },
    "updRow" : function(gridname,uid){
    	Cms.rowFormValidate("#" + gridname, function() {
    		
   		 var row = Current.matchSourceDatagrid.getRowByUID(uid);
   		 	Current.matchSourceDatagrid.commitEdit();
   	        var rowData = Current.matchSourceDatagrid.getChanges();
   	        if(rowData.length == 0){
   	            mini.alert("请先修改表格数据，再保存");
   	            return;
   	        }
   	    	var param = {
   	    			url:"sport/matchInfo/source/upd",
   	    			action:"post",
   	    			data:rowData[0]
   	    	}     	        
   	        Cms.saveDataStringify(param, function(){
   	            Current.matchSourceDatagrid.reload();
   	        })   	        
   		
   	});    	
    },    
    "delRow" : function(id){
        if (id) {
        	Cms.del(Current.matchSourceDatagrid, "sport/matchInfo/source/del" , function(){});
        }
    }
    

};
Current.init();