agent_taken = {
    init : function() {
        mini.parse();
        agent_taken.grid = mini.get("datagrid");
        agent_taken.searchUserType = mini.get("searchUserType");
        agent_taken.searchTimeType = mini.get("searchTimeType");
        agent_taken.payChannel = mini.get("payChannel");
        agent_taken.takenBank = mini.get("takenBank");
        agent_taken.transStatus = mini.get("transStatus");
        agent_taken.batchStatus = mini.get("batchStatus");
        agent_taken.uploadBankTpl = mini.get("uploadBankTpl");
        agent_taken.other;
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
                Dic.searchTimeType.push({id:3,text:'到账时间'});
                Dic.payChannel = result["0905"];
                //Dic.takenBank = result["0907"];
                Dic.transStatus = result["0910"];
                Dic.batchStatus = result["0911"];
                Dic.takenPlatform = result["0603"];
                Dic.takenExcelBank= result["0917"];
                Dic.uploadBankTpl= result["0917"];

                agent_taken.searchUserType.setData(Dic.searchUserType);
                agent_taken.searchTimeType.setData(Dic.searchTimeType);
                agent_taken.payChannel.setData(Dic.payChannel);
                agent_taken.takenBank.setData(Dic.takenBank);
                agent_taken.transStatus.setData(Dic.transStatus);
                // 只显示银行处理成功或失败
                var bankProcessResultDic = [];
                Dic.transStatus.forEach(function(dicStatus) {
                    if(dicStatus.id == 3 || dicStatus.id == 4) {
                        bankProcessResultDic.push(dicStatus);
                    }
                });
                //mini.get('bankProcessResult').setData(bankProcessResultDic);
                agent_taken.batchStatus.setData(Dic.batchStatus);
                mini.get("payChannelDetail").setData(Dic.payChannel);
                mini.get("transStatusDetail").setData(Dic.transStatus);
                // mini.get("takenBankDetail").setData(Dic.takenBank);
                mini.get("takenPlatformDetail").setData(Dic.takenPlatform);
                mini.get("batchStatusDetail").setData(Dic.batchStatus);
//				mini.get("exportExcelBank").setData(Dic.takenExcelBank);
                agent_taken.uploadBankTpl.setData(Dic.uploadBankTpl);

                agent_taken.searchUserType.select(0);
                agent_taken.searchTimeType.select(0);
                agent_taken.search(agent_taken.grid);
                // 填充支持的银行
                // <label class="radio-inline"><input type="radio" name="bank" value="1">中国银行模板</label>
                Dic.takenExcelBank.forEach(function(bank) {
                    $bankRadio = $('<label>',{class : 'radio-inline', text : bank.text}).prepend($('<input>',{type : 'radio', name : 'bank', value : bank.id}));
                    $('#excelBanks').append($bankRadio);
                });

            });
    },
    search:function(grid){
        if(!MiniCom.isValidForm("form1")) {
            mini.alert('查询条件有误，请检查!');
            return;
        }
        grid.load(new mini.Form("#form1").getData(true));
    },
    excel:function(url){
        location = url + "/excel?" + Cms.jsonParamStr(new mini.Form("#form1").getData(true));
    },
    onDrawSummaryCell : function(e) {
        var index = e.column._index;
        if (index == 0) {
            agent_taken.other = e.result.other;
            e.cellHtml = "统"
        } else if (index == 1) {
            e.cellHtml = "计："
        } else if (index == 2) {
            e.cellHtml = "<input type=\"radio\" name=\"sis\" checked=\"checked\" onclick=\"agent_taken.getChangeSis('0')\" />单页统计";
        } else if (index == 3) {
            e.cellHtml = "<input type=\"radio\" name=\"sis\" onclick=\"agent_taken.getChangeSis('1')\"/>条件统计";
        } else if (e.field == "extractAmount") {
            e.cellHtml = "<span id =\"sisExtractAmount\">"
                + e.result.other.singleExtractAmount + "</span>";
        } else if (e.field == "serviceCharge") {
            e.cellHtml = "<span id =\"sisServiceCharge\">"
                + e.result.other.singleServiceCharge + "</span>";
        } else if (e.field == "taxCharge") {
            e.cellHtml = "<span id =\"sisTaxCharge\">"
                + e.result.other.singleTaxCharge + "</span>";
        }
    },
    getChangeSis : function(type) {
        if (agent_taken.other) {
            if ("0" == type) {
                $("#sisExtractAmount").html(
                    agent_taken.other.singleExtractAmount);
                $("#sisServiceCharge").html(
                    agent_taken.other.singleServiceCharge);
                $("#sisTaxCharge").html(
                    agent_taken.other.singleTaxCharge);
            } else {
                $("#sisExtractAmount").html(
                    agent_taken.other.queryExtractAmount);
                $("#sisServiceCharge").html(
                    agent_taken.other.queryServiceCharge);
                $("#sisTaxCharge").html(
                    agent_taken.other.queryTaxCharge);
            }
        }
    },
    viewDetail : function() {
        var row = agent_taken.grid.getSelected(), detailWindow, form;
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
        agent_taken.toWindow('checkTakenWindow', function() {
            var rows = agent_taken.grid.getSelecteds();
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
    preUploadBankExcel : function(e) {
        var bankId = agent_taken.uploadBankTpl.getValue();
        if(!bankId) {
            mini.alert("请选择一个上传银行模板");
            return;
        }
        var file = $("#file").val();
        if(!file || !(file.endsWith(".xlsx") || file.endsWith(".xls"))) {
            mini.alert("请选择一个Excel文件上传");
            return;
        }
        var url = "agent/taken/preUpload";
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
        var rows = agent_taken.grid.getSelecteds();
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
                tmp.agentId = row.agentId;
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
        var rows = agent_taken.grid.getSelecteds();
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
        location = String.format("{0}/excel?excelBank={1}&ids={2}", 'agent/taken/bank', mini.get("exportExcelBank").getValue(), ids.join(','));
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
        var rows = agent_taken.grid.getSelecteds();
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
    excel:function(url){
        location = url + "/excel?" + Cms.jsonParamStr(new mini.Form("#form1").getData(true));
    }
};
agent_taken.init();
$(function() {
    // 绑定"通过"提款按钮
    // 1审核通过; 2审核不通过; 3银行处理成功; 4银行处理失败; 5已到帐;6待审核;7银行处理中
    $("#confirmCheckBtn").click(function(){
        var yesOrNo = $('#checkTakenWindow input:radio:checked').val();
        if(yesOrNo == 1) {
            agent_taken.visitStatus(1, 'agent/taken/check')
        } else if(yesOrNo == 2) {
            agent_taken.visitStatus(2, 'agent/taken/check', function(row, newTransStatus){
                var tmp = {};
                tmp.id = row.id;
                tmp.transStatus = newTransStatus;
                tmp.transFailInfo = $("#rejectReason").val();
                tmp.agentId = row.agentId;
                tmp.transTakenCode = row.transTakenCode;
                return tmp;
            })
        } else {
            console.error("审核提款时状态值错误"+yesOrNo);
        }
    });
    $("#cancelCheckBtn").click(function(){ agent_taken.clearCheckTakenWindow(); });

    $("#cancelAlreadyProcessBtn").click(function(){ agent_taken.clearAlreadyProcessTakenWindow(); });
    $("#confirmAlreadyProcessBtn").click(function(){
        //var newStatus = Number(mini.get('bankProcessResult').getValue());
        //agent_taken.visitStatus(newStatus)
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
            url:'agent/taken/alreadyProcess',
            action:"put",
            data: [successList, failList],
            tip : "确认更新提款状态？"
        };
        Cms.saveDataStringify(param, function() {
            agent_taken.clearAlreadyProcessTakenWindow();
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
        $.get(String.format("agent/taken/trans?batchNum={0}&transStatus={1}", batchNum, bankSuccess))
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

    $("#cancelProcessBtn").click(function(){ agent_taken.clearProcessTakenWindow(); });
    $("#confirmProcessBtn").click(function(){
        var bankId = agent_taken.uploadBankTpl.getValue();
        if(!bankId) {
            mini.alert("请选择一个上传银行模板");
            return;
        }
        var file = $("#file").val();
        if(!file || !(file.endsWith(".xlsx") || file.endsWith(".xls"))) {
            mini.alert("请选择一个Excel文件上传");
            return;
        }
        var url = "agent/taken/process";
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
                        agent_taken.clearProcessTakenWindow();
                    } else {
                        mini.alert(obj.message);
                    }
                }
            },
            error: function (data, status, e) {
                mini.alert(e);
            }
        });

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
        $.get(String.format("agent/taken/trans?batchNum={0}&transStatus={1}", batchNum, pass))
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
        var rows = agent_taken.grid.getSelecteds();
        var ids = rows.map(function(row) {return row.id;});
        location = String.format("agent/taken/bank/excel?excelBank={0}&ids={1}", selectBank, ids.join(','));
        mini.get('excelBankWindow').hide();
    });

    $(".hideWindow").click(function() {
        var window = $(this).data('window');
        mini.get(window).hide();
    });

});