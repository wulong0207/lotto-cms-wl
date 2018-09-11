var payment = {
	init : function() {
		mini.parse();

		// 加载字典初始化
		ComReq.dictionary("1801,1802,1803,1804,1805,1806", function(result) {
			Dic.paytype = result["1801"];
			Dic.status = result["1802"];
			Dic.available = result["1803"];
			Dic.type = result["1804"];
			Dic.cardtype = result["1805"];
			Dic.limitCardType = result["1806"];

			mini.get("paytype").setData(Dic.paytype);
			mini.get("status").setData(Dic.status);
			
			// 支付渠道下拉字典值
			$.ajax({
				url : 'paymentmgr/channel/dic',
				async : false
			}).done(function(data) {
				Dic.payChannel=data;
			});
			
			// 支付渠道map
			$.ajax({
				url : 'paymentmgr/channel/map',
				async : false
			}).done(function(data) {
				Dic.payChannelMap=data;
			});
			/*
			// 由于支付渠道是用支付编码对应，所以特殊处理
			var payChannels = result["0905"];
			Dic.payChannel = [];
			var tmpArr,entry;
			payChannels.forEach(function(payChannelDic) {
				tmpArr = payChannelDic.text.split('|');
				entry = {};
				entry.text=tmpArr[0];
				entry.id=tmpArr[1];
				Dic.payChannel.push(entry);
			});
			*/
		});

		payment.datagrid = mini.get("datagrid");
		payment.datagrid.load();

	},
	loadBlogo : function(e) {
		var grid = e.sender;
		var row = e.record;

		var html = '<img src="' + row.blogo + '" width="126"  height="36"/>';
		return html;
	},
	loadSlogo : function(e) {
		var grid = e.sender;
		var row = e.record;

		var html = '<img src="' + row.slogo + '"  width="25"  height="15" />';
		return html;
	},
	showChannel : function(e) {
		var grid = e.sender;
		var row = e.record;

		if (row.paytype == 1) {

			var listDtail = document.getElementById("listDtail");

			var td = grid.getRowDetailCellEl(row);
			td.appendChild(listDtail);

			listDtail.style.display = "block";

			var channelGrid = mini.get("channelGrid");

			var data = {};
			data.bankid = row.id;

			channelGrid.on("load", function() {
				channelGrid.mergeColumns([ "type" ]);
			});

			channelGrid.load(data);

		}

	},
	add : function() {

		var row = {
			action : "post",
			url : "paymentmgr/index/addBank"
		};

		var form = new mini.Form("#editform");
		form.clear();
		form.setData(row);

		$("#bigBankLogoImg").attr("src", "");
		$("#smallBankLogoImg").attr("src", "");

		payment.editWindow = mini.get("editWindow");

		payment.editWindow.set({
			width : "1200px",
			height : "300px",
			title : "添加银行"
		});

		$("#secondWindow").hide();
		$("#thirdWindow").hide();
		$("#thirdPartyWindow").hide();
		$("#fourthWindow").hide();

		payment.editWindow.show();
	},
	upload : function(fileInputId) {
		Cms.uploadWindow("image/pay",function(data){
			mini.get(fileInputId + "Url").setValue(data.dir);
			$("#" + fileInputId + "Img").attr("src", data.url);
		});
	},
	save : function() {
		Cms.submit(new mini.Form("#editform"), function() {
			payment.editWindow.hide();
			Cms.reload("datagrid");
		});
	},
	edit : function() {

		var row = payment.datagrid.getSelected();
		if (row) {
			payment.editWindow = mini.get("editWindow");

			payment.editWindow.set({
				width : "1300px",
				height : "800px",
				title : "详情"
			});

			payment.indexBankId = row.id;

			var param = {};
			param.action = "get";
			param.url = "paymentmgr/index/getBank/" + row.id;
			Cms.ajax(param, function(res) {

				if (res) {
					res.url = "paymentmgr/index/updateBank"
					res.action = "post";

					var form = new mini.Form("#editform");
					form.clear();
					form.setData(res);
					mini.get('blogo').setValue("");
					mini.get('slogo').setValue("");
					$("#blogoImg").attr("src", res.blogo);
					$("#slogoImg").attr("src", res.slogo);
				}

			});
			if (row.paytype == 1) {
				var onlineBrankChannelGrid = mini.get("onlineBrankChannelGrid");
				onlineBrankChannelGrid.load({
					bankid : row.id,
					type : 1
				});
				// 隐藏支付渠道文本列
				onlineBrankChannelGrid.hideColumn(3);
				onlineBrankChannelGrid.hideColumn(4);
				var quickPayChannelGrid = mini.get("quickPayChannelGrid");
				quickPayChannelGrid.load({
					bankid : row.id,
					type : 2
				});
				// 隐藏支付渠道文本列
				quickPayChannelGrid.hideColumn(3);
				quickPayChannelGrid.hideColumn(4);
				$("#secondWindow").show();
				$("#thirdWindow").show();
				$("#thirdPartyWindow").hide();

			}

			if (row.paytype == 2) {

				var thirdPartyPayChannelGrid = mini
						.get("thirdPartyPayChannelGrid");
				thirdPartyPayChannelGrid.load({
					bankid : row.id,
					type : 3
				});
				// 隐藏支付渠道文本列
				thirdPartyPayChannelGrid.hideColumn(3);
				thirdPartyPayChannelGrid.hideColumn(4);
				$("#secondWindow").hide();
				$("#thirdWindow").hide();
				if(!row.code || row.code.indexOf('WECHAT')<0) {
                    thirdPartyPayChannelGrid.hideColumn(12);
				} else {
                    thirdPartyPayChannelGrid.showColumn(12);
				}
				$("#thirdPartyWindow").show();
			}

			var bankLimitGrid = mini.get("bankLimitGrid");
			bankLimitGrid.load({
				bankId : row.id
			});

			$("#fourthWindow").show();

			payment.editWindow.show();

		}
	},
	saveChannel : function(id) {
		var indexRow = payment.datagrid.getSelected();
		var rows;

		var grid = mini.get(id);

		Cms.formValidate("#" + id, "保存数据有误", function() {

			grid.commitEdit();

			if (!payment.changeOrderIded) {
				rows = grid.getChanges();
				if (rows.length == 0) {
					mini.alert("请先修改表格数据，再保存");
					return;
				}

			} else {
				rows = grid.data;
			}
			payment.changeOrderIded = false;// 恢复初值

			for (var i = 0; i < rows.length; i++) {

				rows[i].bankid = indexRow.id;

				if (id == "onlineBrankChannelGrid") {
					rows[i].type = 1;
				} else if (id == "quickPayChannelGrid") {
					rows[i].type = 2;
				} else if (id == "thirdPartyPayChannelGrid") {
					rows[i].type = 3;
				}
				
				if(rows[i].begintime==""){
					rows[i].begintime=null;
				}
				if(rows[i].endtime==""){
					rows[i].endtime=null;
				}

			}
			// 由天lotto-pay项目要用到pay_channel表的code字段，所以要维护
			var json = mini.encode(rows,"yyyy-MM-dd HH:mm:ss");
			Cms.save(json, "paymentmgr/index/saveChannel", function() {
				Cms.reload(id);
			});
		});

	},
	saveBankLimit : function(id) {

		Cms.formValidate("#" + id, "保存数据有误", function() {

			var grid = mini.get(id);
			grid.commitEdit();

			var rows = grid.getChanges();
			if (rows.length == 0) {
				mini.alert("请先修改表格数据，再保存");
				return;
			}

			for (i = 0; i < rows.length; i++) {
				rows[i].bankid = payment.indexBankId;
			}

			var json = mini.encode(rows);
			Cms.save(json, 'paymentmgr/index/saveBankLimit', function() {
				Cms.reload(id);
			});
		});

	},
	orderHtml : function(e) {
		var grid = e.sender;
		var rowIndex = e.rowIndex;

		var s = '<a class="mini-button"  plain="true" href="javascript:payment.changeOrderNo(\'up\',\''
				+ grid.id
				+ '\','
				+ rowIndex
				+ ')" >▲</a>&nbsp;&nbsp;&nbsp;'
				+ '<a class="mini-button"  plain="true" href="javascript:payment.changeOrderNo(\'down\',\''
				+ grid.id + '\',' + rowIndex + ')" >▼</a>';

		return s;
	},
	changeOrderNo : function(doname, id, rowIndex) {

		var grid = mini.get(id);
		var row = grid.getSelected();

		if (doname == "up") {
			grid.moveRow(row, Number(rowIndex) - 1);
		} else {
			grid.moveRow(row, Number(rowIndex) + 2);
		}

		var array = grid.data;
		for (var i = 0,len=array.length; i < len; i++) {
			var indexRow = array[i];
			// 需求改为值越在越优先
			array[i].orderId = len - i;
			grid.updateRow(indexRow, array[i]);
		}

		payment.changeOrderIded = true;

	},
	
     openImage:function(fileInputId){
    	 var lotterytypeCatalogue = '_upload_images/pay';
		 Cms.imageManage(lotterytypeCatalogue,function(data){
			 payment.setImage(fileInputId,data);
		   });
	},
	setImage:function(fileInputId,data){
		$("#" + fileInputId + "Img").attr("src",data.url);
		mini.get(fileInputId).setValue(data.dir);
		//mini.get(fileInputId+"Url").setValue(data.dir);
	},
	deleteChannel : function(grid, type) {
		var miniGrid = mini.get(grid);
		var rows = miniGrid.getSelecteds();
		if (rows.length > 0) {
			var ids = rows.map(function(row){
				return row.id;
			});
			var data = {
				ids : ids,
				type : type	
			}
			var param = {
				data : data,
				tip : "确定删除？",
				url :'paymentmgr/index/paychannel',
				action:'delete',
			};
			Cms.saveDataStringify(param, function() {
				miniGrid.reload();
			});
		} else {
			mini.alert("请选中一条记录");
		}
	},
	deleteBankLimit : function() {
		var miniGrid = mini.get('bankLimitGrid');
		var rows = miniGrid.getSelecteds();
		if (rows.length > 0) {
			var ids = rows.map(function(row){
				return row.id;
			});
			var data = {
				ids : ids
			}
			var param = {
				data : data,
				tip : "确定删除？",
				url :'paymentmgr/index/banklimit',
				action:'delete',
			};
			Cms.saveDataStringify(param, function() {
				miniGrid.reload();
			});
		} else {
			mini.alert("请选中一条记录");
		}
	},
	search : function() {
		var bankName = mini.get('searchBankName').getValue().trim();
		if(!bankName) {
			mini.alert('请输入要搜索的银行名称')
			return
		}
		payment.datagrid.load({"name":bankName})
	},
	changePayChannelText : function(sender) {
		payment.changeText(sender, 'onlineBrankChannelGrid','code','name');
	},
	changeQuickChannelText : function(sender) {
		payment.changeText(sender, 'quickPayChannelGrid','code','name');
	},
	changeThirdPartyPayChannelText : function(sender) {
		payment.changeText(sender, 'thirdPartyPayChannelGrid','code','name');
	},
	changeText : function(sender, gridId, codeField,nameField) {
		var grid = mini.get(gridId),
		row = grid.getSelected();
		// 选提交，否则修改过的值会丢失
		grid.commitEditRow(row); 
		var newData = {};
		Dic.payChannelMap;
		newData[codeField] = Dic.payChannelMap[sender.selected.id]['code'];
		newData[nameField] = Dic.payChannelMap[sender.selected.id]['name'];
		row = grid.getSelected();
		grid.updateRow(row, newData);
	}
};

payment.init();
