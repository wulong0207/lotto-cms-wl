var order_issue = {
	init : function() {
		mini.parse();
		// 加载字典初始化
		// 彩种下拉框
		order_issue.datagrid = mini.get('datagrid');
		order_issue.lotteryCategory = mini.get('lotteryCategory');
		order_issue.lotteryCode = mini.get('lotteryCode');
		order_issue.lotteryIssue = mini.get('lotteryIssue');
		order_issue.userType = mini.get('userType');
		order_issue.timeType = mini.get('timeType');
		order_issue.winStatus = mini.get('winStatus');
		Dic.level = [{id:0,text:'用户'},{id:1,text:'专家'}];
		Dic.isTop = [{id:1,text:'是'},{id:0,text:'否'}];
		Dic.isRecommend = [{id:1,text:'是'},{id:0,text:'否'}];
		mini.get('isTop').setData(Dic.isTop);
		mini.get('isRecommend').setData(Dic.isRecommend);
		mini.get('level').setData(Dic.level);
		
		ComReq.dictionary("0303,0602,0604,0607,0609,0610,1512", function(result){
			order_issue.lotteryCategory.setData(result["0303"]);
			order_issue.userType.setData(result["0602"]);
			order_issue.timeType.setData(result["0604"]);
			order_issue.userType.select(0);
			order_issue.timeType.select(0);
			order_issue.winStatus.setData(result["0610"]);
			Dic.buyType = result["0607"];
			Dic.orderStatus =result["0609"];
			Dic.winningStatus = result["0610"];
			mini.get('buyType').setData(Dic.buyType);
			mini.get('orderStatus').setData(Dic.orderStatus);
			mini.get('orderStatus2').setData(Dic.orderStatus);
			mini.get('winningStatus').setData(Dic.winningStatus);
			result["1512"].unshift({
				id : 0,
				text : '代购'
			});
			mini.get('categoryId').setData(result["1512"]);
		}); 
		ComReq.lottery("",function(result){
			Dic.lottery = result;
			order_issue.lotteryCode.setData(result);
			mini.get('detailLotteryCode').setData(result);
		});
		order_issue.datagrid.load();
	},
	toEdit : function(e) {
		var w = mini.get("detailWindow"),
		row = e.row;
		w.set({
			title : row.orderCode + " 推单详情"
		});
		//row.action = "put";
		//row.url = "operatemgr/lottery";
		var form = new mini.Form("detailForm");
		form.clear();
		form.setData(row);
		w.show();
		// 1：显示， 0：隐藏
		if(row.isShow === 1) {
			$('#showReasonBtn').hide();
			$('#hideReasonBtn').show();
		} else {
			$('#showReasonBtn').show();
			$('#hideReasonBtn').hide();
		}
		// 加载抄单明细列表
		mini.get('followedDatagrid').load({'orderIssueId':row.id});
	},
	lotteryCategoryChange : function(){
		 ComReq.lottery(order_issue.lotteryCategory.getValue(), function(result){
			 order_issue.lotteryCode.setData(result);
		 });
	},
	lotteryCodeChange : function(){
		var code = order_issue.lotteryCode.getValue();
		if(code){
			ComReq.issue(code,function(result){
				order_issue.lotteryIssue.setData(result);
				order_issue.lotteryIssue.select(0);
			});
		}
	},
	search : function() {
		var form = new mini.Form("form1");
		var data = form.getData(true,false);      //获取表单多个控件的数据
		order_issue.datagrid.load(data);
	},
	updateRecommendReason : function() {
		var param = {
			recommendReason : mini.get('recommendReason').getValue(),
			id : mini.get('id').getValue() 
		}
		$.ajax({
			url : 'ordermgr/issue/recommendReason',
			data : param,
			type : 'PUT',
		}).done(
			function(res) {
				if(res.errorCode == Code.success){
					order_issue.datagrid.reload();
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
	},
	updateRecommendReasonShow : function(isShow) {
		var param = {
			isShow : isShow,
			id : mini.get('id').getValue() 
		}
		$.ajax({
			url : 'ordermgr/issue/recommendReason/showStatus',
			data : param,
			type : 'PUT',
		}).done(
				function(res) {
					if(res.errorCode == Code.success){
						// 切换'显示隐藏'按钮
						$('#showReasonBtn').toggle();
						$('#hideReasonBtn').toggle();
						order_issue.datagrid.reload();
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
	},
	update : function() {
		var param = {
			isTop : mini.get('isTop').getValue() ,
			isRecommend : mini.get('isRecommend').getValue() ,
			remark : mini.get('remark').getValue() ,
			id : mini.get('id').getValue()
		}
		$.ajax({
			url : 'ordermgr/issue',
			data : param,
			type : 'PUT',
		}).done(
				function(res) {
					order_issue.datagrid.reload();
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
	},
	pageFollow : function() {
		var form = new mini.Form("form2");
		var data = form.getData();      //获取表单多个控件的数据
		var param = {
			orderIssueId : mini.get('id').getValue(),
			orderStatus : data.orderStatus2,
			accountName : data.accountName2,
			orderCode : data.orderCode2
		}
		mini.get('followedDatagrid').load(param);
	},
	excelFollow : function() {
		var form = new mini.Form("form2");
		var data = form.getData();      //获取表单多个控件的数据
		var param = {
			orderIssueId : mini.get('id').getValue(),
			orderStatus : data.orderStatus2,
			accountName : data.accountName2,
			orderCode : data.orderCode2
		}
		location = "ordermgr/followed/excel?" + Cms.jsonParamStr(param);
	},
	visitRecommend : function(status) {
		var row = order_issue.datagrid.getSelected();
		if(row) {
			var param = {
					isRecommend :status,
					id : row.id 
				}
				$.ajax({
					url : 'ordermgr/issue',
					data : param,
					type : 'PUT',
				}).done(
					function(res) {
						$('#cancelRecommendBtn').hide();
						if(param.isRecommend){
							$('#recommendBtn').hide();
							$('#cancelRecommendBtn').show();
						} else {
							$('#recommendBtn').show();
							$('#cancelRecommendBtn').hide();
						}
						order_issue.datagrid.reload();
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
	visitTop : function(status) {
		var row = order_issue.datagrid.getSelected();
		if(row) {
			var param = {
					isTop :status,
					id : row.id 
			}
			$.ajax({
				url : 'ordermgr/issue',
				data : param,
				type : 'PUT',
			}).done(
					function(res) {
						if(param.isTop){
							$('#topBtn').hide();
							$('#cancelTopBtn').show();
						} else {
							$('#topBtn').show();
							$('#cancelTopBtn').hide();
						}
						order_issue.datagrid.reload();
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
	dispalyBtn : function(e) {
		var row = e.row;
		if(row.isRecommend){
			$('#recommendBtn').hide();
			$('#cancelRecommendBtn').show();
		} else {
			$('#recommendBtn').show();
			$('#cancelRecommendBtn').hide();
		}
		if(row.isTop){
			$('#topBtn').hide();
			$('#cancelTopBtn').show();
		} else {
			$('#topBtn').show();
			$('#cancelTopBtn').hide();
		}
	},
}
order_issue.init();