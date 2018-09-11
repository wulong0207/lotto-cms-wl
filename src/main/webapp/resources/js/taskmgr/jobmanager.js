var TaskObj = {
		"init" : function() {
			mini.parse();
			
			TaskObj.jobName = mini.get("jobName");
			TaskObj.jobGroup = mini.get("jobGroup");
			TaskObj.jobStatus = mini.get("jobStatus");
			TaskObj.jobAutorun = mini.get("jobAutorun");
			TaskObj.jobGroup_edit = mini.get("jobGroup_edit");
			TaskObj.jobStatus_edit = mini.get("jobStatus_edit");
			TaskObj.jobAutorun_edit = mini.get("jobAutorun_edit");
			TaskObj.jobWay_edit = mini.get("jobWay_edit");
			TaskObj.restartRun_edit = mini.get("restartRun_edit");
			TaskObj.grid = mini.get("datagrid");
			TaskObj.editWindow = mini.get("editWindow");
			
			ComReq.dictionary("0801,0802,0803,0004,0002", function(result) {
				 Dic.jobStatus = result["0801"];
			     Dic.jobAutorun = result["0802"];
			     Dic.jobGroup = result["0803"];
			     Dic.quartzStatus = result["0004"];
			     
			    // alert(mini.encode(result["0803"]));
			     
			     TaskObj.jobGroup.setData(Dic.jobGroup);
			     TaskObj.jobStatus.setData(Dic.jobStatus);
			     TaskObj.jobAutorun.setData(Dic.jobAutorun);
			     TaskObj.jobGroup_edit.setData(Dic.jobGroup);
				 TaskObj.jobStatus_edit.setData(Dic.jobStatus);
				 TaskObj.jobAutorun_edit.setData(Dic.jobAutorun);
				 TaskObj.restartRun_edit.setData(result["0002"]);
			     TaskObj.search();
			});
			 var param = {
		     	    	url:"taskmgr/job/way",
		     	    	action:"GET",
		     };
			 Cms.ajax(param, function(data) {
				 TaskObj.jobWay_edit.setData(data);
			 });
		},
		"search" : function() {
			TaskObj.grid.load(TaskObj.getParamJson());
		},
		"getParamJson" : function() {
			var para = {
					jobName:TaskObj.jobName.getValue(),
					jobGroup:TaskObj.jobGroup.getValue(),
					jobStatus:TaskObj.jobStatus.getValue(),
					jobAutorun:TaskObj.jobAutorun.getValue(),
				};
			return para;
		},
		"onrowdblclick" : function() {
			 mini.get("edit").doClick();
		},
		"addTask" : function() {
			TaskObj.editWindow.setTitle("添加任务");
			TaskObj.editWindow.show();
	    	var form = new mini.Form("#editForm");
	    	form.clear();
	    	// 添加的请求方式和url
	    	var param = {
	    	    action:"post",
	    	   	url:"taskmgr/job",
	    	   	jobWay:"0",
	    	   	restartRun:"0",
	    	};
	    	form.setData(param);
		},
		"editTask" : function() {
        	var row = TaskObj.grid.getSelected();
     	    if (row) {
     	    	TaskObj.editWindow.set({title:"修改任务"});
     	    	// 查详情
	     	    var param = {
	     	    	url:"taskmgr/job/detail/" + row.id,
	     	    	action:"GET",
	     	    	data:"",
	     	    };
	     	    Cms.ajax(param, function(row) {
	     	    	// 设置修改操作的提交信息
	     	    	row.action = "put";
	    	        row.url = "taskmgr/job";
	    	        var form = new mini.Form("#editForm");
	    	        form.clear();
	    	        form.setData(row);
	    	        TaskObj.editWindow.show();
	     	   	});
	     	  // TaskObj.grid.unmask();
     	    } else {
     	    	 mini.alert("请选择一行数据");
     	    }
		},
		"doSubmit" : function() {
			Cms.submit(new mini.Form("#editForm"), function() {
				TaskObj.editWindow.hide();
		        Cms.reload("datagrid");
		    });
		},
		"delTask" : function() {
			Cms.delSelecteds(TaskObj.grid, {
				idField : "id",
				separate : ",",
				url	: "taskmgr/job/del",
				type : "post"  // delete请求方式参数穿不过去
			});
		},
		"exeTask" : function() {
			var row = TaskObj.grid.getSelecteds();
			if(!row || row.length < 1) {
				mini.alert("请选择一行数据!");
			} else if(row.length > 1) {
				mini.alert("一次只能执行一个任务!");
			} else {
	     	    Cms.ajaxSend({
	     	    	url:"taskmgr/job/manual/" + row[0].id,
	     	    },function(res) {
	     	    	if (res.status == 0) { // 0-成功
                		mini.showTips({
                			content : res.message,
                			state : "success",
							x : "center",
							y : "center",
							timeout : 3000
						});
					} else {
						mini.alert(res.message);
					}
	     	    });
			}
		},
		restartRenderer:function(e){
			//父标签对象
			var grid = e.sender;
			//单条标签数据
			var row = e.record;
			var id = row.id;
	        var html = '<a plain="true" href="javascript:TaskObj.restart(\''+id+'\')" >重启</a>';
			return html;
		},
		restart:function(id){
			var param = {
					url:"taskmgr/job/restart/"+id,
					action:"get"
			}
			Cms.ajax(param,function(result){
				Cms.showTips(result,3000);
			});
		}
} 

// 初始化
TaskObj.init();