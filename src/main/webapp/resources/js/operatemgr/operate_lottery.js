operate_lottery = {
	init : function() {
		mini.parse();
		operate_lottery.grid = mini.get("datagrid");
		operate_lottery.platform = mini.get("platform");
		operate_lottery.status = mini.get("status");
		operate_lottery.publishStatus = mini.get("publishStatus");
		operate_lottery.searchTimeType = mini.get("searchTimeType");
		operate_lottery.detailWindow = mini.get("detailWindow");
		operate_lottery.lotteryInfoWindow = mini.get("lotteryInfoWindow");
		operate_lottery.categoryId = mini.get("categoryId");
		operate_lottery.typeId = mini.get("typeId");
		operate_lottery.lotteryChildCode = mini.get("lotteryChildCode");
		operate_lottery.catalogue = "_upload_images/operate/lottery";
		operate_lottery.isDefault = mini.get("isDefault");
		// 保存"删除运营详情"操作的id
		// operate_lottery.ids = [];

		ComReq.dictionary("0001,1501,1002,1502,1505,0303,1508,1509,1510,1524", function(
				result) {
			Dic.week =  result["0001"];
			Dic.platform = result["1501"];
			Dic.status = result["1002"];
			Dic.publishStatus = result["1502"];
			Dic.searchTimeType = result["1505"];
			Dic.categoryType = result["0303"];
			Dic.target = result["1508"];
			Dic.display = result["1509"];
			Dic.icon = result["1510"];
			Dic.isDefault=result["1524"];
			operate_lottery.isDefault.setData(Dic.isDefault);
			operate_lottery.isDefault.setData(Dic.isDefault);
			mini.get("isDefaultDetail").setData(Dic.isDefault);
			operate_lottery.platform.setData(Dic.platform);
			operate_lottery.status.setData(Dic.status);
			operate_lottery.searchTimeType.setData(Dic.searchTimeType);
			mini.get("platformDetail").setData(Dic.platform);
			mini.get("statusDetail").setData(Dic.status);
			mini.get("display").setData(Dic.display);
			mini.get("icon").setData(Dic.icon);
			mini.get("target").setData(Dic.target);
			operate_lottery.searchTimeType.select(0);
			mini.get("onlineWeekDetail").setData(Dic.week);
			mini.get("offlineWeekDetail").setData(Dic.week);
			mini.get("startWeek").setData(Dic.week);
			mini.get("endWeek").setData(Dic.week);
			operate_lottery.categoryId.setData(Dic.categoryType);
			Cms.search("datagrid", "form1", [ "startTime", "endTime" ]);
		});
		ComReq.lottery("", function(result) {
			Dic.allCode = result;
			operate_lottery.typeId.setData(Dic.allCode);
		});
	},
	edit : function() {
		var row = operate_lottery.grid.getSelected(), w = operate_lottery.detailWindow;
		if (row) {
			w.set({
				title : "修改彩种运营详情"
			});
			row.action = "put";
			row.url = "operatemgr/lottery";
			var form = new mini.Form("#detailForm");
			form.clear();
			form.setData(row);
			var g = mini.get("lotteryInfoDatagrid");
			// 加载运营详情
			$.get('operatemgr/lottery/listLotteryInfo',{lotteryId : row.id},function(ret){
				 //deep copy原始数据以判断详情是否有被修改
				operate_lottery.lotteryInfo = $.extend(true, [],ret);
				g.setData(ret);	
			});
			w.show();
		} else {
			mini.alert("请选择一行数据");
		}
	},
	add : function() {
		var form = new mini.Form("#detailForm");
		form.clear();
		var datagrForm = new mini.get("#lotteryInfoDatagrid");	
		datagrForm.setTotalCount(1);
		mini.get("detailWindow").set({
			title : "新增彩种运营详情"
		});
		form.setData({
			action : "post",
			url : "operatemgr/lottery",
		});
		var g = mini.get("lotteryInfoDatagrid");
		// 清除所有行
		g.clearRows();
		operate_lottery.detailWindow.show();
	},
	merge : function() {
		var form = new mini.Form("detailForm"), indexChage = false;
		form.validate();
		if (!form.isValid()) {
			mini.alert("表单数据不完整");
			return;
		}
		var lotteryData = form.getData();
		lotteryData.createTime = null;
		lotteryData.modifyTime = null;
		if(!/^\d{2}:\d{2}:\d{2}$/.test(lotteryData.onlineTime)){ alert("请输入正确的计划上线时间格式,格式为00:00:00");return}
		if(!/^\d{2}:\d{2}:\d{2}$/.test(lotteryData.offlineTime)){ alert("请输入正确的计划下线时间格式,格式为00:00:00");return}
		var newLotteryInfos = mini.get("lotteryInfoDatagrid").data;
		// 详情有修改时才保存
		if (JSON.stringify(newLotteryInfos) !== JSON
				.stringify(operate_lottery.lotteryInfo)) {
			// 如果有增加或删除详情,则更新整个详情列表
			if (operate_lottery.lotteryInfo
					&& newLotteryInfos.length !== operate_lottery.lotteryInfo.length) {
				// 设置排序id
				$.each(newLotteryInfos, function(index, item) {
					item.orderId = index + 1;
				})
				lotteryData.lotteryInfos = newLotteryInfos;
			} else {
				/*
				 * 详情顺序有变化则更新所有详情列表，否则只更新变化部分
				 */
				$.each(newLotteryInfos,function(index, info) {
					if (!info.id
							|| info.id != operate_lottery.lotteryInfo[index].id) {
						indexChage = true;
						return;
					}
				});
				if (indexChage) {
					// 设置排序id
					$.each(newLotteryInfos, function(index, item) {
						item.orderId = index + 1;
					})
					lotteryData.lotteryInfos = newLotteryInfos;
				} else {
					lotteryData.lotteryInfos = mini.get("lotteryInfoDatagrid")
							.getChanges();
				}
			}
		}
		// 若有删除详情记录，则传id给controller
		operate_lottery.ids && (lotteryData.infoIds = operate_lottery.ids);
		// lotteryData.lotteryInfos =
		// mini.get("lotteryInfoDatagrid").getChanges();
		$.each(lotteryData.lotteryInfos , function(index, item) {
			item.offlineTime = mini.formatDate(item.offlineTime, 'yyyy-MM-dd HH:mm:ss')
		})
		var param = {
			url : lotteryData.url,
			action : lotteryData.action,
			data : lotteryData
		};
		Cms.saveDataStringify(param, function() {
			// 把ids清空
			operate_lottery.ids = null;
			operate_lottery.detailWindow.hide();
			Cms.reload("datagrid");
		});
	},
	editLotteryInfo : function() {
		var grid = mini.get("lotteryInfoDatagrid");
		var row = grid.getSelected();
		var offlineTime = mini.get("infoOfflineTime");
		var able = offlineTime.enabled;
		operate_lottery.cleanImage();
		if (row) {
			var form = new mini.Form("lotteryInfoForm");
			form.clear();
			row.operation = "edit";
			// 根据彩种设置子玩法数据字典
			ComReq.lotteryChild(row.typeId, function(result) {
				operate_lottery.lotteryChildCode.setData(result);
				form.setData(row);
			});
			// 有颜色则设置颜色
			//document.getElementById('color').jscolor.valueElement.value = row.color;
			row.color && document.getElementById('color').jscolor.fromString(row.color);
			operate_lottery.typeId.setData(Dic.allCode);
			// 设置下线时间选择框是否可选
			row.display == 2 ? !able && offlineTime.enable() : able
					&& offlineTime.disable();
			operate_lottery.lotteryInfoWindow.show();
			// 查询图片信息，url是相对路径
			if(row.url){
				ComReq.imageDetail(row.url,function(data){
					operate_lottery.setImage(data);
				});
			}
		} else {
			mini.alert("请选择一行数据");
		}
	},
	addLotteryInfo : function() {
		var form = new mini.Form("#lotteryInfoForm");
		form.clear();
		form.setData({
			operation : "add"
		});
		mini.get("icon").select(0);
		mini.get("target").select(0);
		operate_lottery.cleanImage();
		operate_lottery.lotteryInfoWindow.setTitle("彩种增加");
		operate_lottery.lotteryInfoWindow.show();
		// 把子玩法数据字典置空
		operate_lottery.lotteryChildCode.setData(null);
		// 默认黑色
		document.getElementById('color').jscolor.fromString('#000000');
	},
	categoryIdChange : function() {
		var code = operate_lottery.categoryId.getValue();
		ComReq.lottery(code, function(result) {
			operate_lottery.typeId.setData(result);
		});
	},
	/**
	 * 彩种运营详情显示方式为"下线时间"时，才可设置下线时间
	 */
	displayChange : function() {
		var offlineTime = mini.get("infoOfflineTime");
		var able = offlineTime.enabled;
		if (this.value == 2) {
			!able && offlineTime.enable();
		} else {
			offlineTime.setValue();
			able && offlineTime.disable();
		}
	},
	cancelLotteryInfo : function() {
		operate_lottery.lotteryInfoWindow.hide();
	},
	/**
	 * 根据newRow是否有主键更新或添加详情记录到主表的datagrid中
	 * 
	 * @param gridId
	 *            详情页的datagrid id
	 * @param detailFormId
	 *            详情页的form id
	 * @param detailWindowId
	 *            详情页的window id
	 */
	mergeDetailRow : function(gridId, detailFormId, detailWindowId) {
		var form = new mini.Form(detailFormId);
		form.validate();
		if (form.isValid() == false) {
			mini.alert("表单数据不完整");
			return;
		}
		var grid = mini.get(gridId);
		var row = grid.getSelected();
		var newRow = form.getData();
		// 获取颜色值
		newRow.color = document.getElementById('color').jscolor.toHEXString();
		newRow.operation === "edit" ? grid.updateRow(row, newRow) : grid
				.addRow(newRow, grid.data.length);
		mini.get(detailWindowId).hide();
	},
	deleteLotteryInfo : function() {
		var grid = mini.get("lotteryInfoDatagrid");
		var rows = grid.getSelecteds();
		if (rows) {
			mini.confirm("确定删除?", "提示", function(e) {
				if (e === "ok") {
					grid.removeRows(rows)
					operate_lottery.ids = operate_lottery.ids || [];
					$.each(rows, function(index, row) {
						// 记录要删除的id
						operate_lottery.ids.push(row.id);
					})
				}
			});
		} else {
			mini.alert("请选择一行数据");
		}

	},
	closeWindow : function(e) {
		// 如查彩种详情有变，则提示
		if(mini.get("lotteryInfoDatagrid").isChanged()) {
		    mini.confirm("有未保存的数据，确定关闭?", "提示", function(ret) {
		    	if(ret=="ok"){
		    		// 确定关闭
		    		operate_lottery.ids = null;
		    		mini.get("detailWindow").hide();
		    	}
		    }); 
		} else {
			// 数据没有变化则直接关闭
			mini.get("detailWindow").hide();
		}
	},
	pushOnlineWeekTime:function(e){
		var grid = e.sender;
		var onlineTime=e.row.onlineTime;
		var onlineWeek=e.row.onlineWeek;
		var weekName = Cms.getDictionaryValue(Dic.week,onlineWeek);
		var onlineWeekTime = '('+weekName+')'+onlineTime;
		return onlineWeekTime;
	},		
	pushOfflineWeekTime:function(e){
		var grid = e.sender;
		var offlineTime=e.row.offlineTime;
		var offlineWeek=e.row.offlineWeek;
		var weekName = Cms.getDictionaryValue(Dic.week,offlineWeek);
		var offlineWeekTime = '('+weekName+')'+offlineTime;
		return offlineWeekTime;
	},
	upload : function() {
		   Cms.uploadWindow(operate_lottery.catalogue,function(data){
			   operate_lottery.setImage(data);
		   });
	},
	cleanImage:function(){
		$("#adImg").attr("src","");
		mini.get("url").setValue("");
	},
	setImage:function(data){
		var splitUrl = data.url.split(".");
		$("#adImg").attr("src",data.url);
		mini.get("url").setValue(data.dir);
	},
	openImage:function(){
		 Cms.imageManage(operate_lottery.catalogue,function(data){
			 operate_lottery.setImage(data);
		 });
	},
	lotteryTypeChange : function (){
	   var code = operate_lottery.typeId.getValue();
	   if(code){
		   ComReq.lotteryChild(code, function(result){
			   operate_lottery.lotteryChildCode.setData(result);
		   });
	   }
	}
};
operate_lottery.init();