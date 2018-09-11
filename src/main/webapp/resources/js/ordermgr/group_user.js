var Current = {
	init : function() {
		mini.parse();
		// 合买订单列表
		Current.datagrid = mini.get('datagrid');
		Current.userType = mini.get("userType");
		Current.setWindow = mini.get("setWindow");
		Current.groupUserFlag = mini.get("groupUserFlag");
		Current.recommendStatus = mini.get("recommendStatus");
		Dic.yesNoData = [{id:1,text:'是'},{id:0,text:'否'}];
		Dic.groupUserFlag = [{id:1,text:'普通用户'},{id:2,text:'合买红人'}];
		Current.recommendStatus.setData(Dic.yesNoData);
		
		
		ComReq.dictionary("1701", function(result){
			Dic.userType = result["1701"];
//			Dic.groupUserFlag = result["1711"];//合买用户标识
			Current.groupUserFlag.setData(Dic.groupUserFlag);
			Current.userType.setData(Dic.userType);
			Current.userType.setValue(1);
		});
		Current.datagrid.load();
	},
	search : function() {
		var form = new mini.Form("conditionForm");
		var data = form.getData(true,false);     //获取表单多个控件的数据
		Current.datagrid.load(data);
	},
	excel: function() {
		var form = new mini.Form("conditionForm");
		var data = form.getData(true,false);      //获取表单多个控件的数据
		location = "groupUser/excel?" + Cms.jsonParamStr(data);
	},
	visitRecommend : function(status) {
		var row = Current.datagrid.getSelected();
		if(row) {
			var param = {
					isRecommend :status,
					id : row.id
				}
				$.ajax({
					url : 'groupUser/recommand',
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
	dispalyBtn : function(e) {
		var row = e.row;
		if(row.isRecommend){
			$('#recommendBtn').hide();
			$('#cancelRecommendBtn').show();
		} else {
			$('#recommendBtn').show();
			$('#cancelRecommendBtn').hide();
		}
	},
	setUser : function() {
		var row = Current.datagrid.getSelected();
		if(row) {
			mini.get("id").setValue(row.id);
			$("#userName").html(row.userAccount);
			Current.groupUserFlag.setValue(null);
			Current.setWindow.show();
		}else {
			mini.alert("请选择一行数据");
		}
	},
	update : function() {
		var id = mini.get("id").getValue();
		var status = Current.groupUserFlag.getValue();
		var param = {
				id :id,
				flag : status
		}
		$.ajax({
			url : 'groupUser/setUserFlag',
			data : param,
			type:'POST',
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
						Current.setWindow.hide();
						Current.datagrid.reload();
					}else{
						mini.alert(res.message);
					}
				})
	},
}
Current.init();