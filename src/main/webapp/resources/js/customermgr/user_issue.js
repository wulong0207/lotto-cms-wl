var user_issue = {
	init : function() {
		mini.parse();
		user_issue.grid = mini.get('datagrid');
		
		Dic.timeType = [{id:1,text:'创建时间'},{id:2,text:'最后推单时间'}];
		mini.get('timeType').setData(Dic.timeType);
		
		mini.get('timeType').select(0);
		ComReq.dictionary("0303,0602,0212,0213", function(result){
			mini.get('lotteryCategory').setData(result["0303"]);
			mini.get('lotteryCategory2').setData(result["0303"]);
			mini.get('lotteryCategory').select(2);
			mini.get('lotteryCategory2').select(2);
			var cat = mini.get('lotteryCategory2');
			ComReq.lottery(mini.get('lotteryCategory').getValue(), function(result){
				mini.get('lotteryCode').setData(result);
			});
			mini.get('userType').setData(result["0602"]);
			mini.get('userType').select(0);
			// 0:禁用; 1:启用
			Dic.status = [{id:0,text:'禁用'},{id:1,text:'启用'}];
			// 0：普通用户,1:专家'
			Dic.level = [{id:0,text:'用户'},{id:1,text:'专家'}];
			mini.get('level').setData(Dic.level);
			// 设置专家的单选框
			mini.get('level2').setData(Dic.level);
			mini.get('status').setData(Dic.status);
		}); 
		user_issue.grid.load();
	},
	search : function() {
		var form = new mini.Form("form1");
		var data = form.getData(true, false);      //获取表单多个控件的数据
		user_issue.grid.load(data);
	},
	lotteryCategoryChange : function(e){
		 ComReq.lottery(e.value, function(result){
			 mini.get('lotteryCode').setData(result);
		 });
	},
	lotteryCategoryChange2 : function(e){
		ComReq.lottery(e.value, function(result){
			mini.get('lotteryCode2').setData(result);
		});
	},
	visitStatus : function(status) {
		var row = user_issue.grid.getSelected();
		if(row) {
			var param = {
					status :status,
					id : row.id 
				}
				$.ajax({
					url : 'customermgr/issue/status',
					data : param,
					type : 'PUT',
				}).done(
					function(res) {
						user_issue.grid.reload();
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
				})
		} else {
			mini.alert("请选择一行数据");
		}
	},
	toVisitLevel : function(e) {
		var row = user_issue.grid.getSelected();
		if(row) {
			var w = mini.get("visitLevelWindow");
			var form = new mini.Form("visitLevelForm");
			form.clear();
			var cat = mini.get('lotteryCategory2');
			cat.select(2);
			$('#accountName2').text(row.accountName);
			ComReq.lottery(cat.getValue(), function(result){
				mini.get('lotteryCode2').setData(result);
				mini.get('lotteryCode2').select(0);
				user_issue.findOneIssueLevel(row.id, mini.get('lotteryCode2').getValue(), function() {
					w.show();
				});
			});
		} else {
			mini.alert("请选择一行数据");
		}
	},
	visitLevel : function() {
		var row = user_issue.grid.getSelected();
		var param = {
			level : mini.get('level2').getValue(),
			userIssueInfoId : row.id,
			lotteryCode : mini.get('lotteryCode2').getValue(),
			id : mini.get('id').getValue()
		}
		$.ajax({
			url : 'customermgr/issue/level',
			data : param,
			type : 'PUT',
		}).done(function(res) {
			if (res.errorCode == Code.success) {
				// 如果是插入记录成功，则把id记录到隐藏域中，以防止重复插入
				if(!param.id) {
					var param2 ={
							userIssueInfoId : param.userIssueInfoId,
							lotteryCode : param.lotteryCode
						}
					$.get('customermgr/issue/level',param2)
					.done(function(data) {
						// 0：普通用户,1:专家
						mini.get('id').setValue(data.id);
					});
				}
				// 重新加载datagrid
				user_issue.grid.reload();
				mini.showTips({
					content : res.message,
					state : "success",
					x : "center",
					y : "center",
					timeout : 2000
				});
			} else {
				mini.alert(res.message);
			}
		})
	},
	hideWindow : function() {
		var w = mini.get("visitLevelWindow");
		w.hide();
	},
	lotteryCodeChange2 : function(e) {
		var row = user_issue.grid.getSelected();
		user_issue.findOneIssueLevel(row.id, e.value);
	},
	findOneIssueLevel : function(userIssueInfoId, lotteryCode, callBack) {
		var param ={
			userIssueInfoId : userIssueInfoId,
			lotteryCode : lotteryCode
		}
		$.get('customermgr/issue/level',param)
		.done(function(data) {
			// 0：普通用户,1:专家
			if(data.success == 0) {
				console.error(data.message);
			} else {
				var level = mini.get('level2');
				if(data) {
					level.setValue(data.level);
					mini.get('id').setValue(data.id);
				}else {
					level.setValue(0);
					mini.get('id').setValue(null);
				}
				callBack && callBack();
			}
		});
	},
	excel : function() {
		var form = new mini.Form("form1");
		var data = form.getData(true, false);      //获取表单多个控件的数据
		location = "customermgr/issue/excel?" + Cms.jsonParamStr(data);
	},
	renderRecentRecord:function(e){
		var hit = e.row.recentRecord;
    	return hit ?  hit.replace('|','中') : '';
    },
}
user_issue.init();