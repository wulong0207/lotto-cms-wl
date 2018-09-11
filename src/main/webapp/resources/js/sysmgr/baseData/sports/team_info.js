var Current={
    "init": function(){
        mini.parse();

    	Current.datagrid = mini.get("datagrid");
    	Current.teamWindow = mini.get("teamWindow");
    	Current.teamform = new mini.Form("teamform");

    	Current.teamType = mini.get("teamType");
    	Current.searchTeamType = mini.get("searchTeamType");

        Current.teamFullName = mini.get("teamFullName");
    	Current.teamShortName = mini.get("teamShortName");

    	Current.logoUrl = mini.get("logoUrl");
    	
    	//绑定渠道球队
    	Current.teamSourceWindow = mini.get("teamSourceWindow");
    	Current.teamSourceDatagrid = mini.get("teamSourceDatagrid");
    	
    	Current.teamSourceName = mini.get("teamSourceName");
    	Current.teamSourceAbbrName = mini.get("teamSourceAbbrName");
    	Current.teamSourceType = mini.get("teamSourceType");
    	Current.teamSource = mini.get("teamSource");
    	Current.teamId = mini.get("teamId");    	

        ComReq.dictionary("0501,0002,0520", function(result){
            Dic.isOrNot = result["0002"];
            Dic.teamType = result["0501"];
            Dic.source = result["0520"];

            Current.teamType.setData(Dic.teamType);
            Current.searchTeamType.setData(Dic.teamType);
            Current.teamType.select(0);
            
            //渠道球队
            Current.teamSourceType.setData(Dic.teamType);
            Current.teamSource.setData(Dic.source);
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
        var teamFullName = Current.teamFullName.getValue();
        var teamShortName = Current.teamShortName.getValue();
        var teamType = Current.searchTeamType.getValue();
        var para = {
            teamFullName : teamFullName,
            teamShortName : teamShortName,
            teamType : teamType
        };
        return para;
    },
    "edit": function() {
        Current.teamWindow.setTitle("修改赛事信息");
        var row = Current.datagrid.getSelected();
        if (row) {
            row.operation = "edit";
            var form = Current.teamform;
            $("#img").removeAttr("src");
            form.clear();
            form.setData(row);
            if(row.logoImg)
            	$("#img").attr("src",row.logoImg);
            Current.teamWindow.show();
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
        Current.teamWindow.setTitle("新增赛事信息");
        var form = Current.teamform;
        $("#img").removeAttr("src");
        form.clear();
        form.setData({
            operation : "add",
            status : "0",
        });
        data = form.getData();
        Current.teamWindow.show();
    },
    "del" : function(){
        var row = Current.datagrid.getSelected();
        if(row){
            Cms.del(Current.datagrid, "sport/teamInfo/del", function(){});
        } else {
            mini.alert("请选择一行数据");
        }
    },
    "merge": function () {
        var form = Current.teamform;
        form.validate();
        if (!form.isValid()) {
            mini.alert("表单数据不完整");
            return;
        }
        data = form.getData();
        var url = "";
        if (data.operation == "edit") {
            url = "sport/teamInfo/upd";
        } else {
            url = "sport/teamInfo/add";
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
        Current.teamWindow.hide();
    },"teamOpt" : function(e){
    	var record =  e.record;
    	return '<a class="mini-button"  plain="true" href="javascript:Current.initTeamSource('+ record.id + ');">渠道球队</a>'
    },
    "initTeamSource" : function(teamId){
    	var param = {
    			teamId:teamId
    	}
    	Current.teamId.setValue(teamId);
    	Current.teamSourceDatagrid.load(param);
    	Current.teamSourceWindow.show();
    },
    "searchSource" : function(){
    	Current.teamSourceDatagrid.load(Current.getSourceParamJson());
    },
    "teamSourceOpt" : function(e){
    	 var grid = e.sender;
		 var record = e.record;
	     var uid = record._uid;
		 var teamId = record.teamId;
		 var html = '';
		 if(teamId){
			 html = '<a class="mini-button" plain="true" href="javascript:Current.removeBind(\''+ record.id +'\')">解除</a>'; 
		 }else{
			 html = '<a class="mini-button"  plain="true" href="javascript:Current.bind(\''+ record.id +'\')">绑定</a>';
		 }
		 html += ' <a class="mini-button"  plain="true" href="javascript:Current.delRow(\''+ record.id +'\')">删除</a>';
	     if (grid.isEditingRow(record)) {
              html = '<a class="mini-button" plain="true" href="javascript:Current.updRow(\'teamSourceDatagrid\', \'' + uid + '\')">修改</a>'
               + ' <a class="mini-button" plain="true" href="javascript:Cms.cancelEditRow(\'teamSourceDatagrid\',\'' + uid + '\')">取消</a>'
         }
		 return html;	     
    },
    "bind" : function(id){
		var teamId = Current.teamId.getValue();
    	var param = {
    			url:"sport/teamInfo/source/upd",
    			action:"post",
    			data:{'id':id, 'teamId':teamId}
    	}    	
    	Cms.saveDataStringify(param, function(){
        	Current.teamSourceDatagrid.reload();
    	})
    },
    "removeBind" : function(id){
    	var param = {
    			url:"sport/teamInfo/source/remove?id=" + id
    	}
    	Cms.saveDataStringify(param, function(data){
        	Current.teamSourceDatagrid.load(Current.getSourceParamJson());
    	})    	
    },
    "getSourceParamJson" : function(){
    	var param = {};
    	var teamSourceNameVal = Current.teamSourceName.getValue();
    	var teamSourceAbbrNameVal = Current.teamSourceAbbrName.getValue();
    	var teamSourceTypeVal = Current.teamSourceType.getValue();
    	var teamSourceVal = Current.teamSource.getValue();
    	var teamId = Current.teamId.getValue();
    	
    	if(teamSourceNameVal || teamSourceAbbrNameVal || teamSourceTypeVal || teamSourceVal){
    		param = {
    	    		teamName : teamSourceNameVal,
    	    		teamAbbrName : teamSourceAbbrNameVal,
    	    		teamType : teamSourceTypeVal,
    	    		source: teamSourceVal
    	    	};
    	}else{
    		param = {
    			teamId:teamId
    		}
    	}
    	return param;
    },
    "updRow" : function(gridname,uid){
    	Cms.rowFormValidate("#" + gridname, function() {
    		
   		 var row = Current.teamSourceDatagrid.getRowByUID(uid);
   		 	Current.teamSourceDatagrid.commitEdit();
   	        var rowData = Current.teamSourceDatagrid.getChanges();
   	        if(rowData.length == 0){
   	            mini.alert("请先修改表格数据，再保存");
   	            return;
   	        }
   	    	var param = {
   	    			url:"sport/teamInfo/source/upd",
   	    			action:"post",
   	    			data:rowData[0]
   	    	}     	        
   	        Cms.saveDataStringify(param, function(){
   	            Current.teamSourceDatagrid.reload();
   	        })   	        
   		
   	});    	
    },
    "delRow" : function(id){
        if (id) {
        	Cms.del(Current.teamSourceDatagrid, "sport/teamInfo/source/del" , function(){});
        }
    } 


};
Current.init();