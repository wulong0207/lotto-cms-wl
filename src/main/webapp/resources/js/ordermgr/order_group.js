var Current = {
	init : function() {
		mini.parse();
		// 合买订单列表
		Current.datagrid = mini.get('datagrid');
		// 彩种分类下拉框
		Current.lotteryCategory = mini.get('lotteryCategory');
		// 彩种下拉框
		Current.lotteryCode = mini.get('lotteryCode');
		// 彩期
		Current.lotteryIssue = mini.get('lotteryIssue');
		// 中奖状态
		Current.winningStatus = mini.get('winningStatus');
		// 订单状态
		Current.orderStatus = mini.get("orderStatus");
		// 合买状态
		Current.grpbuyStatus = mini.get("grpbuyStatus");
		Current.channel = mini.get("channel");
		Current.platform = mini.get("platform");
		Current.userType = mini.get("userType");
		
		// 详情界面
		Current.detailLottery = mini.get('detail_LotteryCode');
		Current.detailBuyType = mini.get("detail_buyType");
		Current.detailPayStatus = mini.get("detail_payStatus");
		Current.detailOrderStatus = mini.get("detail_orderStatus");
		Current.detailGrpbuyStatus = mini.get("detail_grpbuyStatus");
		Current.detailWinningStatus = mini.get("detail_winningStatus");
		Current.bonusFlag = mini.get("bonusFlag");
		
		Dic.yesNoData = [{id:1,text:'是'},{id:0,text:'否'}];
		Dic.addPrizeType = [{id:1,text:'返合买发起人'},{id:2,text:'返所有合买人'}];
		Dic.groupBuyType = [{id:1,text:'普通认购'},{id:2,text:'发起人保底认购'},{id:3,text:'网站保底认购'}];
		Current.bonusFlag.setData(Dic.addPrizeType);
		
		ComReq.dictionary("0303,0607,0608,0609,0610,0630,0702,1701", function(result){
			Dic.lotteryCategory = result["0303"];
			Dic.buyType =result["0607"];
			Dic.payStatus = result["0608"];
			Dic.orderStatus =result["0609"];
			Dic.winningStatus = result["0610"];
			Dic.grpbuyStatus = result["0630"];
			Dic.platform = result["0702"];
			Dic.userType = result["1701"];

			Current.lotteryCategory.setData(Dic.lotteryCategory);
			Current.winningStatus.setData(Dic.winningStatus);
			Current.orderStatus.setData(Dic.orderStatus);
			Current.grpbuyStatus.setData(Dic.grpbuyStatus);
			Current.platform.setData(Dic.platform);
			
			Current.detailBuyType.setData(Dic.buyType);
			Current.detailPayStatus.setData(Dic.payStatus);
			Current.detailOrderStatus.setData(Dic.orderStatus);
			Current.detailWinningStatus.setData(Dic.winningStatus);
			Current.detailGrpbuyStatus.setData(Dic.grpbuyStatus);
			Current.detailBuyType.setData(Dic.buyType);
			Current.userType.setData(Dic.userType);
			
			Current.userType.setValue(1);
		});
		
		ComReq.lottery("",function(result){
			Dic.lottery = result;
			Current.lotteryCode.setData(result);
			Current.detailLottery.setData(result);
		});
		
		ComReq.channel(function(result){
			 Dic.channel = Cms.joinDictionary(result);
			 Current.channel.setData(Dic.channel);
		 });
		
		Current.datagrid.load();
	},
	search : function() {
		var form = new mini.Form("conditionForm");
		var data = form.getData(true,false);      //获取表单多个控件的数据
		Current.datagrid.load(data);
	},
	lotteryCategoryChange : function(){
		 ComReq.lottery(Current.lotteryCategory.getValue(), function(result){
			 Current.lotteryCode.setData(result);
		 });
	},
	lotteryCodeChange : function(){
		var code = Current.lotteryCode.getValue();
		if(code){
			ComReq.issue(code,function(result){
				Current.lotteryIssue.setData(result);
				Current.lotteryIssue.select(0);
			});
		}
	},
	openEditWin : function(e) {
		var w = mini.get("detailWindow"),
		row = e.row;
		w.set({
			title : row.orderCode + " 合买订单详情"
		});
		//row.action = "put";
		//row.url = "operatemgr/lottery";
		var form = new mini.Form("detailForm");
		form.clear();
		form.setData(row);
		w.show();
		// 加载合买明细列表
		mini.get('joinUsersDatagrid').load({'orderCode':row.orderCode});
	},
	searchGroupUser:function(){
		var orderCode = mini.get("orderCode").getValue();
		var userName = mini.get("userName").getValue();
		mini.get('joinUsersDatagrid').load({'orderCode':orderCode,'userName':userName});
	},
	update : function() {
		var grpbuyStatusVal = Current.detailGrpbuyStatus.getValue();
		var remarkVal = mini.get('remark').getValue();
		var idVal = mini.get('id').getValue();
		var param = {
			grpbuyStatus : grpbuyStatusVal,
			remark : remarkVal,
			id : idVal
		}
		$.ajax({
			url : 'ordermgr/group',
			data : param,
			type : 'PUT',
		}).done(
				function(res) {
					Current.datagrid.reload();
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
	excelGroupUsers: function() {
		var orderCode = mini.get("orderCode").getValue();
		var userName = mini.get("userName").getValue();
		var param = {
			'orderCode':orderCode,
			'userName':userName
		}
		location = "ordermgr/group/excel?" + Cms.jsonParamStr(param);
	},
	visitRecommend : function(status) {
		var row = Current.datagrid.getSelected();
		if(row) {
			var param = {
					isRecommend :status,
					id : row.id
				}
				$.ajax({
					url : 'ordermgr/group/recommand',
					data:param,
					type:'POST',
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
						Current.datagrid.reload();
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
		var row = Current.datagrid.getSelected();
		if(row) {
			var param = {
					isTop :status,
					id : row.id 
			}
			$.ajax({
				url : 'ordermgr/group/top',
				data : param,
				type:'POST',
			}).done(
					function(res) {
						if(param.isTop){
							$('#topBtn').hide();
							$('#cancelTopBtn').show();
						} else {
							$('#topBtn').show();
							$('#cancelTopBtn').hide();
						}
						Current.datagrid.reload();
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
	guaranteeRate : function() {
		var row = Current.datagrid.getSelected();
		if(row) {
			var param = {
					groupId:row.id
			}
			$.ajax({
				url : 'ordermgr/group/siteGuarantee',
				data : param,
				type:'POST',
			}).done(
					function(res) {
						Current.datagrid.reload();
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
		} else {
			mini.alert("请选择一行数据");
		}
	},
	cancelOrder : function() {
		mini.confirm("确认手动撤销合买订单", "提示", function (e) {
			var row = Current.datagrid.getSelected();
			if(row) {
				if(e=="ok"){
					var param = {
							orderCode:row.orderCode,
							cancelOrderType:3
					}
					$.ajax({
						url : 'ordermgr/group/abortionOfManual',
						data : param,
						type : "POST"
					}).done(
							function(res) {
								Current.datagrid.reload();
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
			} else {
				mini.alert("请选择一行数据");
			}  
        })
	},
}
Current.init();