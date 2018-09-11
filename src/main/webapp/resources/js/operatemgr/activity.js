var Current = {
	"init" : function() {
		mini.parse();

		Current.timeType = mini.get("timeType");
		Current.grid = mini.get("datagrid");
		Current.editWindow = mini.get("editWindow");
		Current.treegrid = mini.get("treegrid");
		
		Current.showAllSelect = true;
		Current.activityCodeEdit = mini.get("activityCode_edit");
		Current.activityCode = mini.get("activityCode");
		Current.activityName = mini.get("activityName");
		Current.activityType = mini.get("activityType");
		Current.activityStatus = mini.get("activityStatus");
		Current.publishStatus = mini.get("publishStatus");
		
		Current.timeType = mini.get("timeType");
		
		

		ComReq.dictionary("1001,1002,1003,1502", function(result) {
			Dic.activityType = result["1001"];
			Dic.activityStatus = result["1002"];
			Dic.timeType = result["1003"];
			Dic.publishStatus = result["1502"];
			
			Current.activityType.setData(Dic.activityType);
			Current.activityStatus.setData(Dic.activityStatus);
			Current.timeType.setData(Dic.timeType);
			Current.publishStatus.setData(Dic.publishStatus);
			
			Current.timeType.select(0);
			Current.search();
			mini.get("activityType_edit").setData(Dic.activityType);
			mini.get("activityStatus_edit").setData(Dic.activityStatus);
		});
	},
	"editwin" : function() {
		var row = Current.grid.getSelected();
		if (row) {	
			Current.editWindow.set({
				title : "修改活动信息"
			});
			Current.activityCodeEdit.set({
				enabled:false,
			});
			var param ={
	     	    	url:"operatemgr/activity/detail/"+row.id,
	     	    	action:"GET",
	     	    	data:"",
	     	}
			Cms.ajax(param, function(row){
				row.action = "put";
				row.url = "operatemgr/activity";
    	        var form = new mini.Form("#editform");
    	        form.clear();
    	        form.setData(row);
    	        Current.editWindow.show();
    	        Current.getChannelId(row.channelId);
     	   	});
		} else {
			mini.alert("请选择一行数据");
		}
	},
	"getChannelId":function(channelId){
		Current.treegrid.load("operatemgr/activity/channel?channelId="+channelId);
	},
	"search" : function() {
		Current.grid.load(Current.getParamJson());
	},
	"getParamJson" : function() {
		var activityCode = Current.activityCode.getValue();
		var activityName = Current.activityName.getValue();
		var activityType = Current.activityType.getValue();
		var activityStatus = Current.activityStatus.getValue();
		var publishStatus = Current.publishStatus.getValue();

		var para = {
				activityCode : activityCode,
				activityName : activityName,
				activityType : activityType,
				activityStatus : activityStatus,
				publishStatus:publishStatus
		};

		var timeType = Current.timeType.getValue();
		var startTime = mini.get("startTime").getFormValue();
		var endTime = mini.get("endTime").getFormValue();
		// 参数判断，与赋值
		switch (timeType) {
		case "0":
			para.activityStartTime = startTime;
			para.activityEndTime = endTime;
			break;
		case "1":
			para.createStartTime = startTime;
			para.createEndTime = endTime;
			break;
		case "2":
			para.updateStartTime = startTime;
			para.updateEndTime = endTime;
			break;
		}
		return para;
	},
	"excel" : function() {
		var param = Cms.jsonParamStr(Current.getParamJson());
		var url = "operatemgr/activity/excel?" + param;
		location = url;
	},
	"onrowdblclick" : function() {
		mini.get("edit").doClick();
	},
	"doSubmit" : function() {
		mini.get("channelId_edit").setValue(Current.treegrid.getValue());
		Cms.submit(new mini.Form("#editform"), function() {
			Current.editWindow.hide();
			Cms.reload("datagrid");
		});
	},
	"addwin" : function() {
		Current.editWindow.set({
			title : "添加活动信息"
		});
		Current.activityCodeEdit.set({
			enabled:true,
		});
		var row = {
			action : "post",
			url : "operatemgr/activity",
		}
		var form = new mini.Form("#editform");
		form.clear();
		form.setData(row);
		Current.editWindow.show();
		 Current.getChannelId("");
	},
	"rowPublishStatus":function(e){
		var grid = e.sender;
        
		var activityStartTime=e.row.activityStartTime.getTime();
        var activityEndTime=e.row.activityEndTime.getTime();
        var nowTime=new Date().getTime();
        
        if(nowTime<activityStartTime){
        	return "待发布";
        }else if(nowTime>=activityStartTime && nowTime<=activityEndTime){
        	return "发布中";
        }else if(nowTime>activityEndTime){
        	return "发布结束";
        }	
        	
	},
    "onActionRenderer":function (e) {
        var grid = e.sender;
        var record = e.record;
        var uid = record._uid;
        var rowIndex = e.rowIndex;

        var s = ' <a class="mini-button"  plain="true" onclick="Current.editDeatal()" >配置</a>';
        return s;
    },
    "editDeatal":function(){
        var row = Current.grid.getSelected();
        if(row){
            var type = row.activityType;
            var code = row.activityCode;
            var activityEndTime = row.activityEndTime;
            var activityStartTime = row.activityStartTime; 
            var height = 830,width= 1200;
            if(type==14){
            	height=600;
            	width= 1030;
            };
            mini.open({
                url: "operatemgr/activity/editwin/"+type,
                title: "编辑活动",
                width: width, height: height,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.initData(code,type,activityStartTime,activityEndTime);
                },
                ondestroy: function (action) {
                    Cms.reload("datagrid");
                }
            });
        }else{
            mini.alert("请选择一行数据！");
        }
    },
    "reverseSelect":function(){
    	var tree = mini.get("treegrid");
        var data = tree.getData();      
        for(var i=0 ;i<data.length;i++){
        	var obj =data[i];
			if(obj.checked){
				obj.checked=false;
			}else{
				if(!obj.children)
    				obj.checked=true
    		}			
			checkChildren(obj.children);
        }
        function checkChildren(childrens){
        	if(childrens){
        		for(var i=0 ;i<childrens.length;i++){
        			var obj =childrens[i];
        			if(obj.checked){
        				obj.checked=false;
        			}else{
        				if(!obj.children)
        				obj.checked=true
        			}
        			checkChildren(obj.children);
        		}
        	}
        }
        tree.setData(data);
    },
    "allSelect":function(){
    	var tree = mini.get("treegrid");
        var selectValue = tree.getValue();
        var selectValues = selectValue.split(",");
        var data = tree.getData();
       
        for(var i=0 ;i<data.length;i++){
        	var obj =data[i];
        	obj.checked=true		
			checkChildren(obj.children);
        }
        function checkChildren(childrens){
        	if(childrens){
        		for(var i=0 ;i<childrens.length;i++){
        			var obj =childrens[i];
        			obj.checked=true
        			checkChildren(obj.children);
        		}
        	}
        }
        tree.setData(data);
    }
}
Current.init();