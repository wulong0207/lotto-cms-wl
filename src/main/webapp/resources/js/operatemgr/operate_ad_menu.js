operate_ad_menu = {
	init : function() {
		mini.parse();
		operate_ad_menu.grid = mini.get("datagrid");
		operate_ad_menu.menu = mini.get("menu");
		operate_ad_menu.position = mini.get("position");
		operate_ad_menu.detailWindow = mini.get("detailWindow");
		ComReq.dictionary("1503,1504", function(result) {
			Dic.menu = result["1503"];
			Dic.position = result["1504"];
			operate_ad_menu.menu.setData(Dic.menu);
			operate_ad_menu.position.setData(Dic.position);
			mini.get('datagrid').load();
		});
	},
	toEdit : function() {
		var row = operate_ad_menu.grid.getSelecteds(), w = operate_ad_menu.detailWindow;
		if (row && row.length===1) {
			w.set({
				title : "修改"
			});
			var form = new mini.Form("#detailWindow");
			form.clear();
			form.setData(row[0]);
			w.show();
		} else {
			mini.alert("修改时只能请一行数据");
		}
	},
	toAdd : function() {
		var form = new mini.Form("#detailWindow");
		form.clear();
		operate_ad_menu.detailWindow.set({
			title : "新增"
		});
		operate_ad_menu.detailWindow.show();
	},
	merge : function() {
		var form = new mini.Form("#detailWindow");
		form.validate();
		if (form.isValid() == false) {
			mini.alert("表单数据未填写完整");
			return;
		}
		var data = form.getData(true, false);
		// 如果是新增，判断有无重复添加同一广告页的数据
		if(!data.id) {
			if(
				operate_ad_menu.grid.data
				.map(function(row) {
					return row.menu
				}).some(function(rowMenu) {
					return rowMenu == data.menu
				})
			) {
				mini.alert("同一个广告页面不能重复添加广告位置管理");
				return;
			}
		}
		data.url = "operatemgr/ad/menu";
		data.action= data.id ? "PUT" : "POST";
		debugger;
		Cms.submitByData(data, function() {
			operate_ad_menu.detailWindow.hide();
			Cms.reload("datagrid");
		});
	},
	del : function() {
		var row = operate_ad_menu.grid.getSelecteds();
		if (row && row.length>0) {
			var param = {
				url : "operatemgr/ad/menu",
				action : "DELETE",
				data : {"ids":row.map(function(menu){return menu.id})},
				tip : "确定删除此{0}条数据?".format(row.length),
				ingTip : "删除中。。。",
			}
			Cms.saveDataStringify(param, function() {
				Cms.reload("datagrid");
			});
		} else {
			mini.alert("请至少选择一行数据");
		}
	},
	cancel : function() {
		operate_ad_menu.detailWindow.hide();
	}
};
operate_ad_menu.init();