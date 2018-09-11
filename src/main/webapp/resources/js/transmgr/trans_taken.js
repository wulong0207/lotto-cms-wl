trans_taken = {
	init : function() {
		mini.parse();
		trans_taken.grid = mini.get("datagrid");
		trans_taken.searchUserType = mini.get("searchUserType");
		trans_taken.searchTimeType = mini.get("searchTimeType");
		trans_taken.payChannel = mini.get("payChannel");
		trans_taken.takenBank = mini.get("takenBank");
		trans_taken.transStatus = mini.get("transStatus");
		trans_taken.batchStatus = mini.get("batchStatus");
		trans_taken.uploadBankTpl = mini.get("uploadBankTpl");
		trans_taken.other;
	 	// 查询银行
		$.ajax({
	    	url : "paymentmgr/index/banks/dic",
	    	async : false
	    }).done(function(data) {
	    	Dic.takenBank = data;
	    });
		ComReq.dictionary("0207,0603,0904,0905,0910,0911,0917",
			function(result) {
				Dic.searchUserType = result["0207"];
				Dic.searchTimeType = result["0904"];
				Dic.payChannel = result["0905"];
				//Dic.takenBank = result["0907"];
				Dic.transStatus = result["0910"];
				Dic.batchStatus = result["0911"];
				Dic.takenPlatform = result["0603"];
				Dic.takenExcelBank= result["0917"];
				Dic.uploadBankTpl= result["0917"];

				trans_taken.searchUserType.setData(Dic.searchUserType);
				trans_taken.searchTimeType.setData(Dic.searchTimeType);
				trans_taken.payChannel.setData(Dic.payChannel);
				trans_taken.takenBank.setData(Dic.takenBank);
				trans_taken.transStatus.setData(Dic.transStatus);
				// 只显示银行处理成功或失败
				var bankProcessResultDic = [];
				Dic.transStatus.forEach(function(dicStatus) {
					if(dicStatus.id == 3 || dicStatus.id == 4) {
						bankProcessResultDic.push(dicStatus);
					}
				});
				//mini.get('bankProcessResult').setData(bankProcessResultDic);
				trans_taken.batchStatus.setData(Dic.batchStatus);
				mini.get("payChannelDetail").setData(Dic.payChannel);
				mini.get("transStatusDetail").setData(Dic.transStatus);
				// mini.get("takenBankDetail").setData(Dic.takenBank);
				mini.get("takenPlatformDetail").setData(Dic.takenPlatform);
				mini.get("batchStatusDetail").setData(Dic.batchStatus);
//				mini.get("exportExcelBank").setData(Dic.takenExcelBank);
				trans_taken.uploadBankTpl.setData(Dic.uploadBankTpl);

				trans_taken.searchUserType.select(0);
				trans_taken.searchTimeType.select(0);
				trans_common.search(trans_taken.grid);
				// 填充支持的银行
				// <label class="radio-inline"><input type="radio" name="bank" value="1">中国银行模板</label>
				Dic.takenExcelBank.forEach(function(bank) {
					$bankRadio = $('<label>',{class : 'radio-inline', text : bank.text}).prepend($('<input>',{type : 'radio', name : 'bank', value : bank.id}));
					$('#excelBanks').append($bankRadio);
				});
				
			});
	},
	onDrawSummaryCell : function(e) {
		var index = e.column._index;
		if (index == 0) {
			trans_taken.other = e.result.other;
			e.cellHtml = "统"
		} else if (index == 1) {
			e.cellHtml = "计："
		} else if (index == 2) {
			e.cellHtml = "<input type=\"radio\" name=\"sis\" checked=\"checked\" onclick=\"trans_taken.getChangeSis('0')\" />单页统计";
		} else if (index == 3) {
			e.cellHtml = "<input type=\"radio\" name=\"sis\" onclick=\"trans_taken.getChangeSis('1')\"/>条件统计";
		} else if (e.field == "extractAmount") {
			e.cellHtml = "<span id =\"sisExtractAmount\">"
					+ e.result.other.singleExtractAmount + "</span>";
		} else if (e.field == "serviceCharge") {
			e.cellHtml = "<span id =\"sisServiceCharge\">"
					+ e.result.other.singleServiceCharge + "</span>";
		}
	},
	getChangeSis : function(type) {
		if (trans_taken.other) {
			if ("0" == type) {
				$("#sisExtractAmount").html(
						trans_taken.other.singleExtractAmount);
				$("#sisServiceCharge").html(
						trans_taken.other.singleServiceCharge);
			} else {
				$("#sisExtractAmount").html(
						trans_taken.other.queryExtractAmount);
				$("#sisServiceCharge").html(
						trans_taken.other.queryServiceCharge);
			}
		}
	},
	viewDetail : function() {
		var row = trans_taken.grid.getSelected(), detailWindow, form;
		if (row) {
			detailWindow = mini.get("detailWindow");
			detailWindow.setTitle(row.transTakenCode + " 流水详情");
			form = new mini.Form("#detailForm");
			form.clear();
			form.setData(row);
			mini.get("takenBankDetail").setValue(
					row.takenBank + ' ' + row.bankCardNum);
			mini.get("extractAmountDetail").setValue(row.extractAmount + '元');
			mini.get("realAmountDetail").setValue(row.realAmount + '元');
			mini.get("serviceChargeDetail").setValue(row.serviceCharge + '元');
			detailWindow.show();
		}
	},
	toCheckWindow : function() {
		trans_taken.toWindow('checkTakenWindow', function() {
			var rows = trans_taken.grid.getSelecteds();
			var ret = {};
			ret.success = true;
			if(!rows || rows.length < 1) {
				ret.success = false;
				ret.msg = '请至少选择一行记录'
				return ret;
			}
			if(rows.map(function(row) {return row.transStatus;})
					.some(function(status) {
						// 只有待审核的才能通过或驳回
						return status !== 6;
				})
			) {
				ret.success = false;
				ret.msg = '只有待审核的记录才能审核提款'
				return ret;
			}
			return ret;
		});
	},
	toProcessWindow : function() {
		new mini.Form('processTakenWindow').clear();
		mini.get('processTakenWindow').show();
	},
	toAlreadyProcessWindow : function() {
		new mini.Form('alreadyProcessTakenWindow').clear();
		var win = mini.get('alreadyProcessTakenWindow');
		win.show();
	},
    batchAlreadyProcess : function () {
        var rows = trans_taken.grid.getSelecteds();
        if(!rows || rows.length < 1) {
            mini.alert('请至少选择一行记录');
            return;
        }
        if(rows.map(function(row) {return row.transStatus;})
                .some(function(status) {
                    // 3银行处理成功
                    return status !== 3;
                })
        ) {
            mini.alert("只能处理'银行处理成功'的流水");
            return;
        }

        var bankArrived=5;
        var successList = rows.map(row=>{
        		return {
        			id : row.id,
					userId : row.userId,
                    transTakenCode : row.transTakenCode,
                    transStatus : bankArrived
				}
			});

        var param ={
            url:'transmgr/taken/alreadyProcess',
            action:"put",
            data: [successList,[]],
            tip : "确认更新提款状态？"
        };
        Cms.saveDataStringify(param, function() {
            Cms.reload("datagrid");
        });
    },
	preUploadBankExcel : function(e) {
		var bankId = trans_taken.uploadBankTpl.getValue();
		if(!bankId) {
			mini.alert("请选择一个上传银行模板");
			return;
		}
		var file = $("#file").val();
		if(!file || !(file.endsWith(".xlsx") || file.endsWith(".xls"))) {
			mini.alert("请选择一个Excel文件上传");
			return;
		}
		var url = "transmgr/taken/preUpload";
		$.ajaxFileUpload({  
            url:url,  
            secureuri:false,  
            fileElementId:'file',//file标签的id  
            dataType: 'json',//返回数据的类型  
            data:{bankId:bankId},//一同上传的数据  
            success: function (obj, status) { 
                if(obj.errorCode) {  
                    if(obj.errorCode == Code.success) {  
                    // 把旧记录清空
           			$('#successOrFailTaken thead').empty();
           			$('#successOrFailTaken tbody').empty();
           			obj.data.forEach(function(tran, index){
           				var $tr=$("<tr>");
           				var $trh=$("<tr>");
           				if(index === 0) {
           					tran.forEach(function(col) {
           						$trh.append($('<th>').text(col));
           					});
           					$('#successOrFailTaken thead').append($trh);
           				} else {
           					tran.forEach(function(col) {
   							 $tr.append($('<td>').text(col));
           					});
           					$('#successOrFailTaken tbody').append($tr);
           				} 
       				});
                    } else {  
                        mini.alert(obj.message);  
                    }  
                }  
            },  
            error: function (data, status, e) {
                mini.alert(e);  
            }  
        });  
	},
	toProcessSuccessWindow : function() {
		mini.get('processSuccessTakenWindow').show();
	},
	toWindow : function(window, condition){
		var ret = condition();
		if(ret.success) {
			new mini.Form(window).clear();
			var win = mini.get(window);
			win.show();
		} else {
			mini.alert(ret.msg);
			return;
		}
	},
	visitStatus : function(newTransStatus, url, mapper) {
		var allStatus = [1,2,3,4,7];
		if(allStatus.indexOf(newTransStatus) === -1) {
			mini.alert('操作非法');
			return;
		}
		var rows = trans_taken.grid.getSelecteds();
		var data;
		if(mapper) {
			data = rows.map(function(row) {
				return mapper(row, newTransStatus);
			});
		} else {
			data = rows.map(function(row) {
				var tmp = {};
				tmp.id = row.id;
				tmp.transStatus = newTransStatus;
				tmp.userId = row.userId;
				tmp.transTakenCode = row.transTakenCode;
				return tmp;
			});
		}
		
		var param ={
			url:url,
			action:"put",
			data: data
		};
		Cms.saveDataStringify(param, function() {
			mini.get('checkTakenWindow').hide();
			mini.get('processTakenWindow').hide();
			mini.get('alreadyProcessTakenWindow').hide();
			Cms.reload("datagrid");
		});
	},
	bankExcel : function(excelUrl) {
		var rows = trans_taken.grid.getSelecteds();
		if(!rows || rows.length < 1) {
			mini.alert('请至少选择一行记录');
			return;
		}
		if(rows.map(function(row) {return row.transStatus;})
				.some(function(status) {
					// 只有银行处理中的才能导出银行excel
					return status !== 1;
				})
		) {
			mini.alert('只有审核通过的记录才能导出');
			return;
		}
		var allExcelBanks = ["1"];
		var excelBank = mini.get("exportExcelBank").getValue();
		if(allExcelBanks.indexOf(excelBank)===-1) {
			mini.alert("暂不支持导出该银行模板");
			return;
		}
		var ids = rows.map(function(row) {return row.id;});
		location = String.format("{0}/excel?excelBank={1}&ids={2}", 'transmgr/taken/bank', mini.get("exportExcelBank").getValue(), ids.join(','));
	},
	clearCheckTakenWindow : function(e) {
		$('#rejectReason').val('');
		mini.get('checkTakenWindow').hide();
	},
	clearAlreadyProcessTakenWindow : function(e) {
		$('#batchNumInput').val('');
		$('#successTaken tbody').empty();
		$('#failTaken tbody').empty();
		mini.get('alreadyProcessTakenWindow').hide();
	},
	clearProcessTakenWindow : function(e) {
		//$('#processBatchNumInput').val('');
		//$('#processSuccessTaken tbody').empty();
		//$('#processFailTaken tbody').empty();
		$('#file').val('');
		$('#successOrFailTaken tbody').empty();
		$('#successOrFailTaken thead').empty();
		mini.get('uploadBankTpl').setValue('');
		mini.get('processTakenWindow').hide();
	},
	clearProcessSuccessTakenWindow : function(e) {
		$('#processSuccessBatchNumInput').val('');
		$('#processSuccessSuccessTaken tbody').empty();
		$('#processSuccessFailTaken tbody').empty();
		mini.get('processSuccessTakenWindow').hide();
	},
	showExcelBank : function(e) {
		var rows = trans_taken.grid.getSelecteds();
		if(!rows || rows.length < 1) {
			mini.alert('请至少选择一行记录');
			return;
		}
		if(rows.map(function(row) {return row.transStatus;})
				.some(function(status) {
					// 只有银行处理中的才能导出银行excel
					return status !== 1;
				})
		) {
			mini.alert('只有审核通过的记录才能导出');
			return;
		}
		mini.get('excelBankWindow').showAtPos(280,20);
	},
};
trans_taken.init();
$(function() {
	// 绑定"通过"提款按钮
	// 1审核通过; 2审核不通过; 3银行处理成功; 4银行处理失败; 5已到帐;6待审核;7银行处理中
	$("#confirmCheckBtn").click(function(){ 
		var yesOrNo = $('#checkTakenWindow input:radio:checked').val();
		if(yesOrNo == 1) {
			trans_taken.visitStatus(1, 'transmgr/taken/check')
		} else if(yesOrNo == 2) {
			trans_taken.visitStatus(2, 'transmgr/taken/check', function(row, newTransStatus){
				var tmp = {};
				tmp.id = row.id;
				tmp.transStatus = newTransStatus;
				tmp.transFailInfo = $("#rejectReason").val();
				tmp.userId = row.userId;
				tmp.transTakenCode = row.transTakenCode;
				return tmp;
			})
		} else {
			console.error("审核提款时状态值错误"+yesOrNo);
		}
	});
	$("#cancelCheckBtn").click(function(){ trans_taken.clearCheckTakenWindow(); });
	
	$("#cancelAlreadyProcessBtn").click(function(){ trans_taken.clearAlreadyProcessTakenWindow(); });
	$("#confirmAlreadyProcessBtn").click(function(){
		//var newStatus = Number(mini.get('bankProcessResult').getValue());
		//trans_taken.visitStatus(newStatus)
		// 如果表格中没有流水，则提示返回
		if($('#successTaken tbody tr').length===0 && $('#failTaken tbody tr').length===0) {
			mini.alert("请至少选择一条记录");
			return;
		}
		// 处理的批次号必须为‘银行处理成功’状态的记录，银行处理成功的提款标记为已到账，银行退款的提款标记为银行处理失败，
		// 并更新流水状态为交易失败,更新账户余额
		// 收集"成功"、"失败"表格的数据
		//3银行处理成功; 4银行处理失败;5已到帐
		var bankArrived=5,bankFail=4;
		var successList = [],failList = [];
		$("#successTaken tbody tr").each(function(index, ele) {
			var ret = {};
			ret.id = $(this).data('transid');
			ret.userId = $(this).data('userid');
			ret.transTakenCode = $(this).data('transtakencode');
			ret.transStatus = bankArrived;
			successList.push(ret);
		});
		$("#failTaken tbody tr").each(function(index, ele) {
			var ret = {};
			ret.id = $(this).data('transid');
			ret.userId = $(this).data('userid');
			ret.transTakenCode = $(this).data('transtakencode');
			ret.transStatus = bankFail;
			ret.transFailInfo = $(this).find("input").val();
			failList.push(ret);
		});
		var param ={
				url:'transmgr/taken/alreadyProcess',
				action:"put",
				data: [successList, failList],
				tip : "确认更新提款状态？"
			};
			Cms.saveDataStringify(param, function() {
				trans_taken.clearAlreadyProcessTakenWindow();
				Cms.reload("datagrid");
			});
	});
	
	$("#successTaken tr").live("dblclick", function(){
		$(this).remove();
		$('#failTaken').prepend(
				$(this)
				.append($('<td>').append($('<input>',{class:'text'})))
				);
	});
	
	$("#failTaken tr").live("dblclick",function(){
		$(this).remove();
		$(this).find("td:last").remove();
		$('#successTaken').prepend(
				$(this)
		);
	});
	
	$("#searchTransBtn").click(function() {
		var batchNum = $('#batchNumInput').val().trim();
		// 1审核通过; 2审核不通过; 3银行处理成功; 4银行处理失败; 5已到帐;6待审核;7银行处理中
		var bankSuccess = 3;
		if(!batchNum) {
			mini.alert("请输入批次号");
			return;
		}
		$.get(String.format("transmgr/taken/trans?batchNum={0}&transStatus={1}", batchNum, bankSuccess))
		.done(function(trans){
			// 把旧记录清空
			//$('#successTaken tbody').children().remove();
			$('#successTaken tbody').empty();
			$('#failTaken tbody').empty();
			if(trans.length>0) {
				trans.forEach(function(tran, index){
					$('#successTaken').append(
							$('<tr>',{'data-transid':tran.id,'data-userid':tran.userId,'data-transtakencode':tran.transTakenCode})
							.append($('<td>').text(index+1))
							.append($('<td>').text(tran.transTakenCode))
					);
				});
			} else {
				mini.showTips({
		            content: "未查询到该批次的流水记录",
		            state: "success",
		            x: "center",
		            y: "center",
		            timeout: 2000
		        });
			}
		})
		.fail(function(e) {
			console.error(e);
			mini.alert("查询提款记录出错");
		});
	});
	
	$("#cancelProcessBtn").click(function(){ trans_taken.clearProcessTakenWindow(); });
	$("#confirmProcessBtn").click(function(){
		var bankId = trans_taken.uploadBankTpl.getValue();
		if(!bankId) {
			mini.alert("请选择一个上传银行模板");
			return;
		}
		var file = $("#file").val();
		if(!file || !(file.endsWith(".xlsx") || file.endsWith(".xls"))) {
			mini.alert("请选择一个Excel文件上传");
			return;
		}
		var url = "transmgr/taken/process";
		$.ajaxFileUpload({  
            url:url,  
            secureuri:false,  
            fileElementId:'file',//file标签的id  
            dataType: 'json',//返回数据的类型  
            data:{bankId:bankId},//一同上传的数据  
            success: function (obj, status) { 
                if(obj.errorCode) {  
                    if(obj.errorCode == Code.success) {  
        				mini.showTips({
        		            content: "处理成功！",
        		            state: "success",
        		            x: "center",
        		            y: "center",
        		            timeout: 2000
        		        });
        				// 关闭窗口
        				trans_taken.clearProcessTakenWindow();
                    } else {  
                        mini.alert(obj.message);  
                    }  
                }  
            },  
            error: function (data, status, e) {
                mini.alert(e);  
            }  
        });  
		
//		// 如果表格中没有流水，则提示返回
//		if($('#processSuccessTaken tbody tr').length===0 && $('#processFailTaken tbody tr').length===0) {
//			mini.alert("请至少选择一条记录");
//			return;
//		}
//		// 收集"成功"、"失败"表格的数据
//		// 1审核通过; 2审核不通过; 3银行处理成功; 4银行处理失败; 5已到帐;6待审核;7银行处理中
//		var bankSuccess=3,bankFail=4;
//		var bankRetsultList = [];
//		$("#processSuccessTaken tbody tr").each(function(index, ele) {
//			var ret = {};
//			ret.id = $(this).data('transid');
//			ret.userId = $(this).data('userid');
//			ret.transTakenCode = $(this).data('transtakencode');
//			ret.transStatus = bankSuccess;
//			bankRetsultList.push(ret);
//		});
//		$("#processFailTaken tbody tr").each(function(index, ele) {
//			var ret = {};
//			ret.id = $(this).data('transid');
//			ret.userId = $(this).data('userid');
//			ret.transTakenCode = $(this).data('transtakencode');
//			ret.transStatus = bankFail;
//			ret.transFailInfo = $(this).find("input").val();
//			bankRetsultList.push(ret);
//		});
//		var param ={
//				url:'transmgr/taken/process',
//				action:"put",
//				data: bankRetsultList,
//				tip : "确认更新提款状态？"
//			};
//			Cms.saveDataStringify(param, function() {
//				trans_taken.clearProcessTakenWindow();
//				Cms.reload("datagrid");
//			});
	});
	$("#processSuccessTaken tr").live("dblclick", function(){
		//console.log($(this).children().eq(0).text());
		$(this).remove();
		$('#processFailTaken').prepend(
				$(this)
				.append($('<td>').append($('<input>',{class:'text'})))
				);
	});
	
	$("#processFailTaken tr").live("dblclick",function(){
		$(this).remove();
		$(this).find("td:last").remove();
		$('#processSuccessTaken').prepend(
				$(this)
		);
	});
	$("#processSearchTransBtn").click(function() {
		var batchNum = $('#processBatchNumInput').val().trim();
		if(!batchNum) {
			mini.alert("请输入批次号");
			return;
		}
		// 1审核通过; 2审核不通过; 3银行处理成功; 4银行处理失败; 5已到帐;6待审核;7银行处理中
		var pass = 1;
		$.get(String.format("transmgr/taken/trans?batchNum={0}&transStatus={1}", batchNum, pass))
		.done(function(trans){
			// 把旧记录清空
			//$('#successTaken tbody').children().remove();
			$('#processSuccessTaken tbody').empty();
			$('#processFailTaken tbody').empty();
			if(trans.length>0) {
				trans.forEach(function(tran, index){
					$('#processSuccessTaken').append(
							$('<tr>',{'data-transid':tran.id,'data-userid':tran.userId,'data-transtakencode':tran.transTakenCode})
							.append($('<td>').text(index+1))
							.append($('<td>').text(tran.transTakenCode))
					);
				});
			} else {
				mini.showTips({
					content: "未查询到该批次的流水记录",
					state: "success",
					x: "center",
					y: "center",
					timeout: 2000
				});
			}
		})
		.fail(function(e) {
			console.error(e);
			mini.alert("查询提款记录出错");
		});
	});
	
	$("#confirmBankExcel").click(function(){
		var selectBank = $('#excelBanks input:radio:checked').val();
		if(!selectBank) {
			mini.alert('请选择一个导出银行');
			return;
		}
		var rows = trans_taken.grid.getSelecteds();
		var ids = rows.map(function(row) {return row.id;});
		location = String.format("transmgr/taken/bank/excel?excelBank={0}&ids={1}", selectBank, ids.join(','));
		mini.get('excelBankWindow').hide();
	});
	
	$(".hideWindow").click(function() {
		var window = $(this).data('window');
		mini.get(window).hide(); 
	});
	
//	// 银行处理成功
//	$("#cancelProcessSuccessBtn").click(function(){ trans_taken.clearProcessSuccessTakenWindow(); });
//	$("#confirmProcessSuccessBtn").click(function(){
//		// 如果表格中没有流水，则提示返回
//		if($('#processSuccessSuccessTaken tbody tr').length===0 && $('#processSuccessSuccessTaken tbody tr').length===0) {
//			mini.alert("请至少选择一条记录");
//			return;
//		}
//		// 收集"成功"、"失败"表格的数据
//		//4银行处理失败; 5已到帐;
//		var bankFail=4,arrived=3;
//		var bankRetsultList = [];
//		$("#processSuccessSuccessTaken tbody tr").each(function(index, ele) {
//			var ret = {};
//			ret.id = $(this).data('transid');
//			ret.userId = $(this).data('userid');
//			ret.transTakenCode = $(this).data('transtakencode');
//			ret.transStatus = arrived;
//			bankRetsultList.push(ret);
//		});
//		$("#processSuccessFailTaken tbody tr").each(function(index, ele) {
//			var ret = {};
//			ret.id = $(this).data('transid');
//			ret.userId = $(this).data('userid');
//			ret.transTakenCode = $(this).data('transtakencode');
//			ret.transStatus = bankFail;
//			ret.transFailInfo = $(this).find("input").val();
//			bankRetsultList.push(ret);
//		});
//		var param ={
//				url:'transmgr/taken/arrive',
//				action:"put",
//				data: bankRetsultList,
//				tip : "确认更新提款状态？"
//			};
//			Cms.saveDataStringify(param, function() {
//				trans_taken.clearProcessSuccessTakenWindow();
//				Cms.reload("datagrid");
//			});
//	});
//	$("#processSuccessSuccessTaken tr").live("dblclick", function(){
//		//console.log($(this).children().eq(0).text());
//		$(this).remove();
//		$('#processSuccessFailTaken').prepend(
//				$(this)
//				.append($('<td>').append($('<input>',{class:'text'})))
//				);
//	});
//	
//	$("#processSuccessFailTaken tr").live("dblclick",function(){
//		$(this).remove();
//		$(this).find("td:last").remove();
//		$('#processSuccessSuccessTaken').prepend(
//				$(this)
//		);
//	});
//	$("#processSuccessSearchTransBtn").click(function() {
//		var batchNum = $('#processSuccessBatchNumInput').val().trim();
//		if(!batchNum) {
//			mini.alert("请输入批次号");
//			return;
//		}
//		// 1审核通过; 2审核不通过; 3银行处理成功; 4银行处理失败; 5已到帐;6待审核;7银行处理中
//		var bankSuccess = 3;
//		$.get(String.format("transmgr/taken/trans?batchNum={0}&transStatus={1}", batchNum, bankSuccess))
//		.done(function(trans){
//			// 把旧记录清空
//			//$('#successTaken tbody').children().remove();
//			$('#processSuccessSuccessTaken tbody').empty();
//			$('#processSuccessFailTaken tbody').empty();
//			if(trans.length>0) {
//				trans.forEach(function(tran, index){
//					$('#processSuccessSuccessTaken').append(
//							$('<tr>',{'data-transid':tran.id,'data-userid':tran.userId,'data-transtakencode':tran.transTakenCode})
//							.append($('<td>').text(index+1))
//							.append($('<td>').text(tran.transTakenCode))
//					);
//				});
//			} else {
//				mini.showTips({
//					content: "未查询到该批次的流水记录",
//					state: "success",
//					x: "center",
//					y: "center",
//					timeout: 2000
//				});
//			}
//		})
//		.fail(function(e) {
//			console.error(e);
//			mini.alert("查询提款记录出错");
//		});
//	});
});