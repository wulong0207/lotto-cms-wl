var Current = {
	"init" : function() {
		mini.parse();

		Current.grid = mini.get("datagrid");
		Current.detailWin = mini.get("detailWindow");
		Current.addWin = mini.get("addWindow");

		Current.verifyStatus = mini.get("verifyStatus");
		Current.applySource = mini.get("applySource");
		Current.applyType = mini.get("applyType");
		
		Current.id = mini.get("id");
		Current.userAccount = mini.get("userAccount");
		Current.applyStartTime = mini.get("applyStartTime");
		Current.applyEndTime = mini.get("applyEndTime");
		Current.startTime = mini.get("startTime");
		Current.endTime = mini.get("endTime");

		Dic.yesNoData = [{id:1,text:'是'},{id:0,text:'否'}];
		Dic.isExpert = [{id:2,text:'是'},{id:1,text:'否'}];
		
		$.get("sysmgr/dic/findRcmdUserLevel", function(result){
			Dic.applyType = result;
			Current.applyType.setData(Dic.applyType);
		});

		ComReq.dictionary("2900,2902", function(result) {
			Dic.applySource = result["2900"];
			Dic.status = result["2902"];
			
			Current.applySource.setData(Dic.applySource);
			Current.verifyStatus.setData(Dic.status);
			
			ComReq.lottery("",function(result){
				Dic.lottery = result;
				Current.grid.load();
			});
		});
	},
	"search" : function() {
		var form = new mini.Form("conditionForm");
		var data = form.getData(true,false);      //获取表单多个控件的数据
		Current.grid.load(data);
	},
	"cancelQualification":function(){
		mini.confirm("确认取消资格", "提示", function (e) {
			if(e=="ok"){
				var row = Current.grid.getSelected();
				var param = {
						id:row.id,
						status:5
				}
				$.ajax({
					url : 'ordermgr/rcmd/setCheckStatus',
					data : param,
					type : "POST"
				}).done(
						function(res) {
							Current.search();
							if(res.errorCode == Code.success){
								mini.showTips({
									content: res.message,
									state: "success",
									x: "center",
									y: "center",
									timeout: 2000
								});
							}else{
								mini.alert(res.message); 
							}
						});
			}
        })
	},
	"openDetailWin":function(){
		Current.detailWin.set({
			title : "审核"
		});
		mini.get("analystLevel").setData(Dic.applyType);
		mini.get("lottery").setData(Dic.lottery);
		var row = Current.grid.getSelected();
		var form = new mini.Form("detailForm");
		form.clear();
		form.setData(row);
		Current.detailWin.show();
	},
	"setStatus":function(status){
		var aid = mini.get("aid").getValue(); 
		var param = {
				id:aid,
				status:status
		};
		$.ajax({
			url : 'ordermgr/rcmd/setCheckStatus',
			data : param,
			type : "POST"
		}).done(
				function(res) {
					if(res.errorCode == Code.success){
						mini.showTips({
							content: res.message,
							state: "success",
							x: "center",
							y: "center",
							timeout: 2000
						});
						Current.detailWin.hide();
						Current.search();
					}else{
						mini.alert(res.message);
					}
				});
		
	},
	"openAddwin" : function() {
		Current.addWin.set({
			title : "添加发单人"
		});
		var row = {
			action : "post",
			url : "ordermgr/rcmd/addRcmdUser"
		}
		mini.get("lottery").setData(Dic.lottery);
		mini.get("analystLevel").setData(Dic.applyType);
		mini.get("isShowRecord").setData(Dic.yesNoData);
		mini.get("source").setData(Dic.isExpert);
		var form = new mini.Form("#addForm");
		form.clear();
		form.setData(row);
		mini.get("analystLevel").setValue("");
		mini.get("lottery").setValue("");
		Current.addWin.show();
	},
	"submit" : function() {
		Cms.submit(new mini.Form("#addForm"),function(){
			Current.addWin.hide();
			Current.search();
	    });
	},
	"clear":function(){
		Current.addWin.hide();
	},
    "onActionRenderer":function (e) {
        var grid = e.sender;
        var record = e.record;
        var uid = record._uid;
        var rowIndex = e.rowIndex;

        var s = ' <a class="mini-button" plain="true" onclick="Current.openDetailWin()" >审核</a> &nbsp;&nbsp;'
        	+ '<a class="mini-button" plain="true" onclick="Current.cancelQualification()" >取消资格</a>';
        return s;
    }
}
Current.init();