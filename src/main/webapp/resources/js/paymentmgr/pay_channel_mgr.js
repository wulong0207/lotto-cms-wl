var pay_channel_mgr = {
		init : function() {
			$.ajax({
				url : 'sysmgr/dic/dictionary?code=0307',
				async : false
			}).done(function(result) {
				// 是否可用:0不可用;1可用
				Dic.available = result['0307'];
				// 定义字典数据
				// 是否暂停:0不可用;1可用
				Dic.pause = result['0307'];
				// 是否收费 0否，1是
				Dic.charge = result['0307'];
			});
			// 渠道支付类型:1网银支付;2快捷支付,3第三方支付
			Dic.payType = [{id:1,text:'网银支付'},{id:2,text:'快捷支付'},{id:3,text:'第三方支付'}];
			// '卡类型:1储蓄卡;2信用卡,3第三方支付',
			Dic.cardType = [{id:1,text:'储蓄卡'},{id:2,text:'信用卡'},{id:3,text:'第三方支付'}];
			
			mini.parse();
			
			mini.get('available').setData(Dic.available);
			mini.get('pause').setData(Dic.pause);
			
			pay_channel_mgr.datagrid = mini.get('datagrid');
			pay_channel_mgr.bankDatagrid = mini.get('bankDatagrid');
			pay_channel_mgr.limitDatagrid = mini.get('limitDatagrid');
			pay_channel_mgr.detailWindow = mini.get('detailWindow');
			
			// 市场渠道下拉框赋值
			$.ajax({
				url : 'paymentmgr/index/banks/dic',
				async : false
			}).done(function(data) {
				Dic.bankList=data;
				mini.get("bankList").setData(Dic.bankList);
			});
			pay_channel_mgr.datagrid.load();
		},
		toAdd : function(e) {
			// 把id隐藏域置空
			mini.get('id').setValue(null);
			mini.get('name').setEnabled(true);
			mini.get('code').setEnabled(true);
			var form = new mini.Form("detailForm");
			form.clear();
			pay_channel_mgr.bankDatagrid.hideColumn(2);
			pay_channel_mgr.bankDatagrid.load();
			pay_channel_mgr.limitDatagrid.load();
			pay_channel_mgr.detailWindow.show();
		},
		toEdit : function(e) {
			var row = pay_channel_mgr.datagrid.getSelected();
			if(row) {
				mini.get('name').setEnabled(false);
				mini.get('code').setEnabled(false);
				pay_channel_mgr.detailWindow.set({
					title : row.name
				});
				var form = new mini.Form("detailForm");
				form.clear();
				form.setData(row);
				pay_channel_mgr.detailWindow.show();
				pay_channel_mgr.bankDatagrid.hideColumn(2);
				// 加载银行配置
				pay_channel_mgr.bankDatagrid.load({'payChannelMgrId':row.id});
				// 加载付款金额超限说明
				pay_channel_mgr.limitDatagrid.load({'payChannelMgrId':row.id});
			} else {
				mini.alert('请选择一行数据');
			}
		},
		merge : function(e) {
			if(!MiniCom.isValidForm('detailForm')) {
				mini.alert('表单数据未填写完整');
				return;
			}
			var form = new mini.Form("detailForm");
			var param = form.getData(true,false);      //获取表单多个控件的数据
			param.pc = param.pc === 'true' ? 1 : 0;
			param.h5 = param.h5 === 'true' ? 1 : 0;
			param.android = param.android == 'true' ? 1 : 0;
			param.ios = param.ios == 'true' ? 1 : 0;
				$.ajax({
					url : 'paymentmgr/channel',
					data : param,
					type : 'PUT',
				}).done(
					function(res) {
						if(res.errorCode == Code.success){
							pay_channel_mgr.datagrid.reload();
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
		pageChangeBankInfo : function() {
			var id = mini.get('id').getValue();
			var param = {
				payChannelMgrId : id,
				bankName : mini.get('bankName').getValue()
			}
			pay_channel_mgr.bankDatagrid.load(param);
		},
		toAddBankChannel : function() {
			var bankList = mini.get('bankList').getValue(),grid,
			rate = mini.get('rate').getValue();
			if(bankList) {
				var bankArray = bankList.split(','), row, rows = [];
				bankArray.forEach(function(bankid) {
					row = {};
					row.bankid = bankid;
					row.rate = rate;
					rows.push(row);
				});
				pay_channel_mgr.bankDatagrid.addRows(rows,0);
				rows.forEach(function(row) {
					pay_channel_mgr.bankDatagrid.beginEditRow(row);
				});
				
			} else {
				mini.alert('至少选择一个银行');
			}
		},
		batchMergeBankChannel : function() {
			var payChannelMgrId = mini.get('id').getValue();
			if(!payChannelMgrId) {
				mini.alert('请先保存支付渠道信息');
				return;
			}
			pay_channel_mgr.bankDatagrid.commitEdit();
			var 
			rows = pay_channel_mgr.bankDatagrid.getChanges();
			;
			// 若有新增或修改的行
			if(rows.length>0) {
				// TODO 是否要分新增的记录才同步渠道配置信息，原有记录不用同步？
				// 加上支付渠道id,同步渠道配置的"可用平台"、"是否可用"、"启用暂停"、"暂停开始时间"、"暂停结束时间"、"排序值"、""、
				// 获取表单多个控件的数据
				var formData = new mini.Form("detailForm").getData(false,false);
				rows.forEach(function(row) {
					row.payChannelMgrId = payChannelMgrId;
					row.name = formData.name;
					row.code = formData.code;
					row.pc = formData.pc === 'true' ? 1 : 0;
					row.h5 = formData.h5 === 'true' ? 1 : 0;
					row.android = formData.android == 'true' ? 1 : 0;
					row.ios = formData.ios == 'true' ? 1 : 0;
					row.available = formData.available;
					row.pause = formData.pause;
					row.begintime = formData.beginTime;
					row.endtime = formData.endTime;
					row.orderId = formData.orderId;
				});
				$.ajax({
					headers: { 
						'Accept': 'application/json',
						'Content-Type': 'application/json' 
					},
					url : 'paymentmgr/channel/bank',
					data :JSON.stringify(rows),
					type : 'POST',
				}).done(
					function(res) {
						if(res.errorCode == Code.success){
							pay_channel_mgr.bankDatagrid.reload();
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
				}).fail(
					function(jqXHR, textStatus, errorThrown) {
						console.error(jqXHR);
					}
				);
			}
		},
		deleteBankChannel : function() {
			var rows = pay_channel_mgr.bankDatagrid.getSelecteds();
			if(rows.length>0) {
				mini.confirm("确定删除?", "提示", function(e) {
					var ids = [];
					if (e == "ok") {
						rows.forEach(function(row) {
							row.id && ids.push(row.id);
						});
						// 如果有删除表中的数据
						if(ids.length > 0) {
							$.ajax({
								headers: {
									'Accept': 'application/json',
									'Content-Type': 'application/json' 
								},
								url : 'paymentmgr/index/paychannel',
								data :JSON.stringify({ids : ids}),
								type : 'delete',
							}).done(
								function(res) {
									if(res.errorCode == Code.success){
										pay_channel_mgr.bankDatagrid.removeRows(rows);
										pay_channel_mgr.bankDatagrid.reload();
										mini.showTips({
											content: res.message,
											state: "success",
											x: "center",
											y: "center",
											timeout: 2000
										});
									}else{
										debugger;
										mini.alert(res.message); 
									}
							}).fail(
									function(jqXHR, textStatus, errorThrown) {
										mini.alert(jqXHR.responseText);
									}
							);
						}
					}
				});
			} else {
				mini.alert('请至少选择一行数据');
			}
		},
		batchMergeChannelLimit : function() {
			var payChannelMgrId = mini.get('id').getValue();
			if(!payChannelMgrId) {
				mini.alert('请先保存支付渠道信息');
				return;
			}
			pay_channel_mgr.limitDatagrid.commitEdit();
			var 
			rows = pay_channel_mgr.limitDatagrid.getChanges()
			;
			if(rows.length>0) {
				rows.forEach(function(row) {
					row.payChannelMgrId = payChannelMgrId;
				});
				$.ajax({
					headers: { 
						'Accept': 'application/json',
						'Content-Type': 'application/json' 
					},
					url : 'paymentmgr/channel/limit',
					data :JSON.stringify(rows),
					type : 'PUT',
				}).done(
					function(res) {
						if(res.errorCode == Code.success){
							pay_channel_mgr.limitDatagrid.reload();
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
				}).fail(
					function(jqXHR, textStatus, errorThrown) {
						console.error(jqXHR);
					}
				);
			}
		},
		deleteChannelLimit : function() {
			var rows = pay_channel_mgr.limitDatagrid.getSelecteds();
			if(rows.length>0) {
				mini.confirm("确定删除?", "提示", function(e) {
					var ids = [];
					if (e == "ok") {
						rows.forEach(function(row) {
							row.id && ids.push(row.id);
						});
						pay_channel_mgr.limitDatagrid.removeRows(rows);
						// 如果有删除表中的数据
						if(ids.length > 0) {
							var payChannelMgrId = mini.get('id').getValue();
							$.ajax({
								headers: { 
									'Accept': 'application/json',
									'Content-Type': 'application/json' 
								},
								url : 'paymentmgr/channel/limit',
								data :JSON.stringify({ids : ids, payChannelMgrId : payChannelMgrId}),
								type : 'delete',
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
									}else{
										mini.alert(res.message); 
									}
							}).fail(
									function(jqXHR, textStatus, errorThrown) {
										mini.alert(jqXHR.responseText);
									}
							);
						}
					}
				});
			} else {
				mini.alert('请至少选择一行数据');
			}
		},
		editIt : function(obj) {
			mini.get(obj.source.id).beginEditRow(obj.row);
		},
		search : function() {
			var form = new mini.Form("form1");
			var data = form.getData(true,false);      //获取表单多个控件的数据
			pay_channel_mgr.datagrid.load(data);
		},
		excel : function() {
			var form = new mini.Form("form1");
			var data = form.getData();      //获取表单多个控件的数据
			location = "paymentmgr/channel/excel?" + Cms.jsonParamStr(data);
		}
	}
	pay_channel_mgr.init();