/**
 * Created by chenkn679 on 2018/1/6.
 */
var Current = {
    "init": function (code) {
        mini.parse();
        //窗体对象, form对象
        Current.activityRuleWindow = mini.get("activityRuleWindow");
        Current.activityConfigForm = new mini.Form("activityConfigForm");
        Current.activityRuleForm = new mini.Form("activityRuleForm");
        Current.datagrid = mini.get("activityRule_datagrid");
        Current.cdkDatagrid = mini.get("cdkey_datagrid");
        Current.cdkeyWindows = mini.get("cdkeyWindows");

        Current.type = mini.get("type");
        Current.configType = mini.get("configType");
        Current.activityCode = mini.get("activityCode");
        Current.preResType = mini.get("preResType");//优惠限制
        Current.realUserNum = mini.get("realUserNum");
        Current.offerNum = mini.get("offerNum");
        Current.minRechargeMoney = mini.get("minRechargeMoney");
        Current.userType = mini.get("userType");

        Current.cdkTotal = mini.get("cdkTotal");
        Current.invalidCdkCount = mini.get("invalidCdkCount");
        Current.validCdkCount = mini.get("validCdkCount");

        //规则明细
        Current.redType = mini.get("redType");
        Current.redStatus = mini.get("redStatus");
        Current.minSpendAmount = mini.get("minSpendAmount");
        Current.activeEndTime = mini.get("activeEndTime");
        Current.limitLottery = mini.get("limitLottery");
        Current.limitPlatform = mini.get("limitPlatform");
        Current.limitLotteryChild = mini.get("limitLotteryChild");
        Current.limitLotteryChildType = mini.get("limitLotteryChildType");
        Current.operateLotteryId = mini.get("operateLotteryId");
        Current.limitNum = mini.get("limitNum");
        Current.label = mini.get("label");
        Current.redLabel = mini.get("redLabel");
        Current.activityCode.setValue(code);
        mini.get("type").setValue(13);

        Dic.preResType = "[{'id':'1','text':'同一真实用户'},{'id':'2','text':'手机号'}]";
        Dic.exchangeStatus = [{id:'0',text:'未使用'},{id:'1',text:'已使用'}];
        Dic.redStatus = [{id:'1',text:'待激活'},{id:'3',text:'可使用'}]
        ComReq.dictionary("2703,0303,1102,1105,0702,0901,1106,1107", function (result) {
            Dic.redeemCodeType = result['2703'];
            Dic.lotteryCategory = result["0303"];
            Dic.redType = result["1102"];
            Dic.redType = result["1102"].slice(0, 4);
            Dic.limitPlatform = result["1105"];
            Dic.terminalPlatform = result["0702"];
            Dic.transType = result["0901"];
            Dic.redSource = result["1106"];

            Current.configType.setData(Dic.redeemCodeType);
            Current.redType.setData(Dic.redType);//红包类型
            Current.redStatus.setData(Dic.redStatus);//初始状态
            mini.get("limitPlatform").setData(Dic.limitPlatform);//限制平台
            Current.preResType.setData(Dic.preResType);//优惠限制
            Current.preResType.selectAll();

            ComReq.lottery("", function (result) {
                Dic.OperateLotteryId = result.concat();
                Dic.lotteryCode = Dic.lotteryCategory.concat(result);
                var obj = {};
                obj.id = "0";
                obj.text = "客户端下载";
                Dic.OperateLotteryId.unshift(obj);
                Current.limitLottery.setData(Dic.lotteryCode);
                Current.operateLotteryId.setData(Dic.OperateLotteryId);

                Current.initDetail(code);
            });

        });
    },
    "labelChange":function(){
        var labelVal = Current.label.getValue();
        Current.redLabel.setValue('');
        switch (labelVal) {
            case '1':
                Current.redLabel.set({
                    enabled:false
                })
                return;
            case '4':
                Current.redLabel.set({
                    enabled:true
                })
                return;
            case '2':
            case '3':
                Current.redLabel.set({
                    enabled:false
                })
                Current.redLabel.setValue(Current.label.getText());
                return;
        }
    },
    "setProperty" :function () {
        var val = Current.configType.getValue();
        if (val) {
            Current.configType.setEnabled(false);
        }
    },
    "initDetail": function (activityCode) {
        var type = mini.get("type").getValue();
        var param = {
            url: "operatemgr/activity/config/detail",
            action: "post",
            data: {activityCode: activityCode, type: type}
        };
        Cms.ajax(param, function (data) {
            if (data) {
                Current.activityConfigForm.setData(data);
                Current.datagrid.setData(data.operateActivityRuleBOList);
                mini.get("type").setValue(13);
                var str = '';
                if (data.isMobile) {
                    str = '2';
                }
                if (data.isRealUser) {
                    str = str === '' ? '1' : '1,2';
                }
                Current.preResType.setValue(str);
                Current.setProperty();
            }
        })
    },
    "cdkeyDetail": function () {
        var code = Current.activityCode.getValue();
        var vo = {
            url:"operatemgr/activity/cdkey/count",
            action :"post",
            data:{activityCode: code}
        }
        Cms.ajax(vo, function (data) {
            if (data) {
                Current.cdkTotal.setValue(data.cdkTotal);
                Current.validCdkCount.setValue(data.validCdkCount);
                Current.invalidCdkCount.setValue(data.invalidCdkCount);
            }
        })
        var param ={activityCode: code};
        Current.cdkDatagrid.load(param);
        Current.cdkeyWindows.setTitle("兑换详情");
        Current.cdkeyWindows.show();
    },
    "closeConfigWindow": function () {
        Current.ids = null;
        window.CloseOwnerWindow();
    },
    "closeRuleWindow": function () {
        Current.activityRuleWindow.hide();
    },
    "merge": function () {
        Current.type.setValue(13);
        var form = Current.activityConfigForm;
        if (form.validate() === false) {
            alert("表单数据不完整");
            return;
        }
        var data = form.getData();
        data.isMobile = 1;
        data.isRealUser = 1;
        data.operateActivityRuleVOList = Current.datagrid.getData(true, false);
        if (data.operateActivityRuleVOList.length === 0) {
            alert("请添加活动规则");
            return;
        }
        var configType = Current.configType.getValue();
        var preType = Current.preResType.getValue();
        if (preType !== null && preType !== "") {
            if (preType.indexOf("2") > -1) {
                data.isMobile = 1;
            }
            if (preType.indexOf("1") > -1) {
                data.isRealUser = 1;
            }
        }
        // 若有删除详情记录，则传id给controller
        Current.ids && (data.ruleIds = Current.ids);
        var param = {
            url: data.url,
            action: "post",
            data: data
        };
        Cms.saveDataStringify(param, function () {
            setTimeout(function () {
                Current.closeConfigWindow();
            }, 300)
        })
    },
    "add": function () {
        Current.activityRuleForm.clear();
        var row = {
            action: "post",
            url: "operatemgr/coupon/add",
            redBalance: 0
        };
        Current.activityRuleForm.setData({
            operation: "add",
            status: "0"
        });
        Current.activityRuleWindow.setTitle("新增活动配置规则");
        Current.activityRuleForm.setData(row);
        Current.redStatus.select(0);
        Current.limitNum.setValue(1);
        Current.label.select(1);
        Current.labelChange();
        Current.activityRuleWindow.show();
    },
    "edit": function () {
        var row = Current.datagrid.getSelected();
        if (row) {
            Current.activityRuleForm.clear();
            //Current.limitLotteryChange(row.limitLottery);
            row.operation = "edit";
            Current.activityRuleWindow.setTitle("修改活动配置规则");
            setTimeout(function () {
                Current.activityRuleForm.setData(row);
                Current.activityRuleWindow.show();
            }, 200)
        } else {
            mini.alert("请选择一行数据");
        }
    },
    "del": function () {
        var rows = Current.datagrid.getSelecteds();
        if (rows) {
            mini.confirm("确定删除?", "提示", function (e) {
                if (e === "ok") {
                    Current.datagrid.removeRows(rows);
                    Current.ids = Current.ids || [];
                    $.each(rows, function (index, row) {
                        // 记录要删除的id
                        Current.ids.push(row.id);
                    })
                }
            });
        } else {
            mini.alert("请选择一行数据");
        }
    },
    "mergeActivityRule": function () {
        Current.activityRuleForm.validate();
        console.log(Current.activityRuleForm.validate());
        if (Current.activityRuleForm.isValid() === false) {
            mini.alert("表单数据不完整");
            return;
        }
        var row = Current.datagrid.getSelected();
        var newRow = Current.activityRuleForm.getData(true, false);
        var rebateType = newRow.rebateType;
        if (rebateType === 2) {
            var re = new RegExp("^[0-9]*[1-9][0-9]*$");
            if (!re.test(newRow.rebateNum)) {
                alert("本站返利金额百分比请输入整数");
                return;
            }
        }
        newRow.operation === "edit" ? Current.datagrid.updateRow(row, newRow) : Current.datagrid
            .addRow(newRow, Current.datagrid.data.length);
        Current.activityRuleWindow.hide();
    }
};
