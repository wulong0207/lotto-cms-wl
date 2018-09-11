operate_fast = {
	init : function() {
		mini.parse();
		operate_fast.grid = mini.get("datagrid");
		operate_fast.platform = mini.get("platform");
		operate_fast.position = mini.get("position");
		operate_fast.status = mini.get("status");
		operate_fast.searchTimeType = mini.get("searchTimeType");
		operate_fast.detailWindow = mini.get("detailWindow");
		operate_fast.fastInfoWindow = mini.get("fastInfoWindow");
		operate_fast.categoryId = mini.get("categoryId");
		operate_fast.typeId = mini.get("typeId");
		operate_fast.isDefault = mini.get("isDefault");
		

		ComReq.dictionary("0001,1501,1002,1502,1505,1508,0307,1510,1511,1512,1516,1517,1518,1519,1520,1521,1524,1525,1528,1529,1530", function(
				result) {
			Dic.week =  result["0001"];
			Dic.platform = result["1501"];
			Dic.position = result["1511"];
			Dic.status = result["1002"];
			Dic.searchTimeType = result["1505"];
			Dic.isDefault=result["1524"];
			operate_fast.isDefault.setData(Dic.isDefault);
			operate_fast.isDefault.setData(Dic.isDefault);
			mini.get("isDefaultDetail").setData(Dic.isDefault);
			operate_fast.jczCategoryType = result["1512"];
			operate_fast.ssqCategoryType = result["1516"];
			operate_fast.dltCategoryType = result["1517"];
			operate_fast.x115CategoryType = result["1518"];
			operate_fast.k3CategoryType = result["1519"];
			operate_fast.sfcCategoryType = result["1520"];
			operate_fast.sscCategoryType = result["1528"];
			operate_fast.kl10CategoryType = result["1529"];
			operate_fast.klpkCategoryType = result["1530"];
			operate_fast.ptCategoryType = result["1521"];
			Dic.target = result["1508"];
			Dic.fastSelected = result["0307"];
			Dic.icon = result["1510"];
			operate_fast.platform.setData(Dic.platform);
			operate_fast.position.setData(Dic.position);
			operate_fast.status.setData(Dic.status);
			operate_fast.searchTimeType.setData(Dic.searchTimeType);
			mini.get("platformDetail").setData(Dic.platform);
			mini.get("positionDetail").setData(Dic.position);
			mini.get("statusDetail").setData(Dic.status);
			mini.get("fastSelected").setData(Dic.fastSelected);
			mini.get("icon").setData(Dic.icon);
			mini.get("target").setData(Dic.target);
			mini.get("onlineWeekDetail").setData(Dic.week);
			mini.get("offlineWeekDetail").setData(Dic.week);
			mini.get("startWeek").setData(Dic.week);
			mini.get("endWeek").setData(Dic.week);
			operate_fast.categoryId.setData(Dic.categoryType);
			operate_fast.searchTimeType.select(0);
			Cms.search("datagrid", "form1");
			Dic.allCode = result["1525"];
			operate_fast.typeId.setData(Dic.allCode);
		});
	},
	edit : function() {
		var row = operate_fast.grid.getSelected(), w = operate_fast.detailWindow;
		if (row) {
			w.set({
				title : "修改快投运营详情"
			});
			// Current.channelIdEdit.set({
			// enabled:false,
			// });
			row.action = "put";
			row.url = "operatemgr/fast";
			var form = new mini.Form("#detailForm");
			form.clear();
			form.setData(row);
			// 加载运营详情
			mini.get("fastInfoDatagrid").load({fastId : row.id},function(ret) {
				// deep copy原始数据以判断详情是否有被修改
				operate_fast.fastInfo = $.extend(true, [], ret.result.data);
			});
			w.show();
		} else {
			mini.alert("请选择一行数据");
		}
	},
	add : function() {
		var form = new mini.Form("#detailForm");	
		operate_fast.detailWindow.set({
				title : "新增快投运营详情"
		});
		form.clear();
		form.setData({
			action : "post",
			url : "operatemgr/fast",
		});
		operate_fast.detailWindow.show();
		// 清除所有行
		mini.get("fastInfoDatagrid").clearRows();
		mini.get("fastInfoDatagrid").setTotalCount(1);
	},
	merge : function() {
		var form = new mini.Form("detailForm"), indexChage = false;
		form.validate();
		if (!form.isValid()) {
			mini.alert("表单数据不完整");
			return;
		}
		var fastData = form.getData();
		if(!/^\d{2}:\d{2}:\d{2}$/.test(fastData.onlineTime)){ alert("请输入正确的计划上线时间格式,格式为00:00:00");return}
		if(!/^\d{2}:\d{2}:\d{2}$/.test(fastData.offlineTime)){ alert("请输入正确的计划下线时间格式,格式为00:00:00");return}
		var newFastInfos = mini.get("fastInfoDatagrid").data;
		if(fastData.position==1&&fastData.platform==1&&newFastInfos.length>3){
			alert("主站web最多显示3个快投信息");return;
		}
		// 详情有修改时才保存
		if (JSON.stringify(newFastInfos) !== JSON
				.stringify(operate_fast.fastInfo)) {
			// 如果有增加或删除详情,则更新事个详情列表
			if(operate_fast.fastInfo && newFastInfos.length !== operate_fast.fastInfo.length) {

				// 设置排序id
				$.each(newFastInfos, function(index, item) {
					item.orderId = index + 1;
				})
				fastData.fastInfos = newFastInfos;
			} else {
				/*
				 * 详情顺序有变化则更新所有详情列表，否则只更新变化部分
				 */
				$
						.each(
								newFastInfos,
								function(index, info) {
									if (!info.id
											|| info.id != operate_fast.fastInfo[index].id) {
										indexChage = true;
										return;
									}
								});
				if (indexChage) {
					// 设置排序id
					$.each(newFastInfos, function(index, item) {
						item.orderId = index + 1;
					})
					fastData.fastInfos = newFastInfos;
				} else {
					fastData.fastInfos = mini.get("fastInfoDatagrid")
							.getChanges();
				}
			}
		}
		// 若有删除详情记录，则传id给controller
		operate_fast.ids && (fastData.infoIds = operate_fast.ids);
		// fastData.fastInfos = mini.get("fastInfoDatagrid").getChanges();
		var param = {
			url : fastData.url,
			action : fastData.action,
			data : fastData
		};
		Cms.saveDataStringify(param, function() {
			operate_fast.ids = null;
			operate_fast.detailWindow.hide();
			Cms.reload("datagrid");
		});
	},
	editFastInfo : function() {
		var grid = mini.get("fastInfoDatagrid");
		var row = grid.getSelected();
		if (row) {
			var form = new mini.Form("fastInfoForm");
			
			form.clear();
			row.operation = "edit";		
			operate_fast.typeId.setData(Dic.allCode);
			var typeId = row.typeId;
			var data = operate_fast.getCategoryData(typeId);
			mini.get("categoryId").setData(data);
			form.setData(row);			
			operate_fast.fastInfoWindow.show();
		} else {
			mini.alert("请选择一行数据");
		}
	},
	addFastInfo : function() {
		var form = new mini.Form("#fastInfoForm");
		form.clear();
		form.setData({
			operation : "add"
		});
		mini.get("icon").select(0);
		mini.get("target").select(0);
		operate_fast.fastInfoWindow.setTitle("彩种增加");
		mini.get("categoryId").setData(null)
		operate_fast.fastInfoWindow.show();
	},
	categoryIdChange : function() {
		var code = operate_fast.categoryId.getValue();
		ComReq.fast(code, function(result) {
			operate_fast.typeId.setData(result);
		});
	},
	cancelFastInfo : function() {
		operate_fast.fastInfoWindow.hide();
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
	
		if (newRow.operation === "edit") {
			grid.updateRow(row, newRow);
			grid.commitEditRow(row); 
		} else {
			grid.addRow(newRow, grid.data.length);
		}
		mini.get(detailWindowId).hide();
	},
	deleteFastInfo : function() {
		var grid = mini.get("fastInfoDatagrid");
		var rows = grid.getSelecteds();
		if (rows) {
			mini.confirm("确定删除?", "提示", function(e) {
				if (e === "ok") {
					grid.removeRows(rows)
					operate_fast.ids = operate_fast.ids || [];
					$.each(rows, function(index, row) {
						// 记录要删除的id
						operate_fast.ids.push(row.id);
					})
				}
			});
		} else {
			mini.alert("请选择一行数据");
		}

	},
	orderHtml:function(e){
        var data = operate_fast.getCategoryData(e.row.typeId); 
        for(var i=0;i<data.length;i++){
        	if(data[i].id ==e.row.categoryId){
        		return '<a>'+data[i].text+'</a>';
        	}
        }
        
    },
	getCategoryData : function(typeId){
		var data;
		if(typeId==300){
			data=operate_fast.jczCategoryType;			
		}else if(typeId==100){
			data=operate_fast.ssqCategoryType;
		}
		else if(typeId==102){
			data=operate_fast.dltCategoryType;
		}
		else if((typeId>=210&&typeId<=219)||(typeId>=260&&typeId<=277)){
			data=operate_fast.x115CategoryType;
		}
		else if((typeId>=230&&typeId<=236)||(typeId>=278&&typeId<=283)){
			data=operate_fast.k3CategoryType;
		}else if(typeId>=201&&typeId<=205){
			data=operate_fast.sscCategoryType;
		}else if(typeId==225){
			data=operate_fast.klpkCategoryType;
		}else if(typeId==222){
			data=operate_fast.kl10CategoryType;
		}else if(typeId==304){
			data=operate_fast.sfcCategoryType;
		}else{
			data=operate_fast.ptCategoryType;
		}
		return data;
	} ,
	getCategory : function(){
		var typeId = mini.get("typeId").getValue();
		var data = operate_fast.getCategoryData(typeId);
		mini.get("categoryId").setData(data);
	},
	onDrawcell:function(obj){
		if(obj.field == "categoryId"){
		    var categoryId = obj.record.categoryId;
		    var typeId = obj.record.typeId;		    
		    var data = operate_fast.getCategoryData(typeId)
		    var text = Cms.getDictionaryValue(data,categoryId);
		    obj.cellHtml = text;
		}
	},
	closeWindow : function() {
		// 把ids清空
		operate_fast.ids = null;
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
	}
};
operate_fast.init();