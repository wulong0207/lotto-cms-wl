var UserType = {
	"init" : function(){
		mini.parse();
		
		//页面标签对象
		UserType.dataGrid = mini.get("dataGrid");
		UserType.UserTypeWindow = mini.get("UserTypeWindow");
		
		//查询条件
		UserType.userType = mini.get("userType");
		UserType.timeType = mini.get("timeType");
		UserType.beginTime = mini.get("beginTime");
		UserType.endTime = mini.get("endTime");

		//form对象
		UserType.UserTypeForm = new mini.Form("#UserTypeForm");
		UserType.code = mini.get("code");
		//提交按钮
		UserType.operateBtn = mini.get("operateBtn");
		//取消按钮
		UserType.closeBtn = mini.get("closeBtn");
		
		var timeType = [{ "id": "1", "text": "创建时间" },{ "id": "2", "text": "修改时间" }];
		
		UserType.timeType.setData(timeType);
		UserType.timeType.select(0);
		UserType.search();
	},
	"excelRenderer": function(e){
		//父标签对象
		var grid = e.sender;
		//单条标签数据
		var row = e.record;
		var id = row.id;
		var html = ' <a plain="true" href="javascript:UserType.excel(\''+id+'\')" >导出</a>';
		return html;
	},
	"onRowdblClick" : function(){
		UserType.toEdit();
	},
	"getParamJson" : function(){
		var _userType = UserType.userType.getValue();
		var _timeType = UserType.timeType.getValue();
		var _beginTime = UserType.beginTime.getFormValue();
		var _endTime = UserType.endTime.getFormValue();
		
		var params = {
				id:_userType,
				timeType:_timeType,
				beginTime:_beginTime,
				endTime:_endTime,
		};
		return params;
	},
	"search" : function(){
		UserType.dataGrid.load(UserType.getParamJson());
	},
	"toEdit" : function(){
		var row = UserType.dataGrid.getSelected();
		if(row){
			UserType.UserTypeWindow.set({
				title : "编辑"
			});
			UserType.code.set({
				allowInput: false
			})
			UserType.UserTypeForm.clear();
			row.action = "post";
		    row.url= "customermgr/usertype/upd";
		    $("#operateBtn").attr("onClick","UserType.edit();")
		    UserType.UserTypeForm.setData(row);
		    
			UserType.UserTypeWindow.show();
		} else {
	        mini.alert("请选择一行数据");
	    }		
	},
	"edit" : function(){
		Cms.submit(UserType.UserTypeForm,function(){
			UserType.UserTypeWindow.hide();
			UserType.dataGrid.reload();  
		});
	},
	"toAdd" : function(){
		UserType.UserTypeWindow.set({
			title : "新增"
		});
		UserType.code.set({
			allowInput: true
		})
		UserType.UserTypeForm.clear();
		var row = {};
		row.action = "post";
	    row.url= "customermgr/usertype/add";
	    UserType.UserTypeForm.setData(row);
	    $("#operateBtn").attr("onClick","UserType.add();")
		UserType.UserTypeWindow.show();
	},
	"add" : function(){
		Cms.submit(UserType.UserTypeForm,function(){
			UserType.UserTypeWindow.hide();
			UserType.dataGrid.reload();  
		});
	},
	"del" : function(){
		var row = UserType.dataGrid.getSelected();
		if(row){
			Cms.del(UserType.dataGrid, "customermgr/usertype/del", function(){});
		} else {
	        mini.alert("请选择一行数据");
	    }
	},
	"excel" : function(typeId){
		var url = "customermgr/usertype/excel?typeId="+typeId;
		location = url;
	},
	"addTypeToUser" : function(){
		var row = UserType.dataGrid.getSelected();
		if(row){
			var typeCode = row.code;
			var param = {};
			param.url = "customermgr/usertype/addTypeToUser?typeCode=" + typeCode;
			param.type= "get";
			Cms.ajaxFirm("确定要执行<span style=\"color: red;\">" + row.name +"</span>吗?", param, function(data){
			})			
		} else {
	        mini.alert("请选择一行数据");
	    }
	},
	"close":function(){
		UserType.UserTypeWindow.hide();	
	}
}
UserType.init();