var TaskInfoObj = {
		"init" : function() {
			mini.parse();
			
			TaskInfoObj.jobName = mini.get("jobName");
			TaskInfoObj.runWay = mini.get("runWay");
			TaskInfoObj.status = mini.get("status");
			TaskInfoObj.result = mini.get("result");
			TaskInfoObj.grid = mini.get("datagrid");
			
			ComReq.dictionary("0804,0805,0803", function(result) {
				 Dic.runWay = result["0804"];
				 Dic.status = result["0805"];
				 Dic.result = result["0805"];
				 Dic.jobGroup = result["0803"];
			    // alert(mini.encode(result["0803"]));
			     
				 TaskInfoObj.runWay.setData(Dic.runWay);
				 TaskInfoObj.status.setData(Dic.status);
				 TaskInfoObj.result.setData(Dic.result);
				 TaskInfoObj.search();
			});
		},
		"search" : function() {
			TaskInfoObj.grid.load(TaskInfoObj.getParamJson());
		},
		"getParamJson" : function() {
			var para = {
					"task.jobName":TaskInfoObj.jobName.getValue(),
					"runWay":TaskInfoObj.runWay.getValue(),
					"status":TaskInfoObj.status.getValue(),
					"result":TaskInfoObj.result.getValue()
				};
			return para;
		}
} 

// 初始化
TaskInfoObj.init();