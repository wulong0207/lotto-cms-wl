var pay_bank_segment = {
	init : function() {
		mini.parse();
		Data.cardType = [{id:1,text:'储蓄卡'},{id:2,text:'信用卡'},{id:3,text:'预付费卡'}];
		mini.get('cardType').setData(Data.cardType);
        pay_bank_segment.datagrid = mini.get('datagrid');
        pay_bank_segment.detailWindow = mini.get('detailWindow');
        pay_bank_segment.detailForm = new mini.Form('detailWindow');
        pay_bank_segment.datagrid.load();
	},
	toMerge : function(action) {
        var w = pay_bank_segment.detailWindow,
			f = pay_bank_segment.detailForm,
			g = pay_bank_segment.datagrid,
			row,bankName;
        f.clear();
		if(action === 'add') {
			w.setTitle('新增银行卡');
		} else {
			row = g.getSelected();
			if(row) {
				f.setData(row);
			} else {
				mini.alert('请选择一行数据');
				return;
			}
			// 中国银联支付标记(00010000)，拆分银行名称和银行代码
            bankName = row.bankName;
			if(bankName.indexOf('(')>-1 && bankName.indexOf(')')>-1) {
                row.bankName = bankName.substring(0,bankName.indexOf('('));
                row.bankCode = bankName.substring(bankName.indexOf('(')+1,bankName.indexOf(')'));
			}
            if(!row.bankId) {
                row.bankId = row.bankName;
            }
            w.setTitle('修改银行卡');
            f.setData(row);
		}
		w.show();
	},
	search : function() {
		var topCutLike = mini.get('topCutLike').getValue();
        pay_bank_segment.datagrid.load({topCutLike : topCutLike});
	},
    cancel : function() {
        pay_bank_segment.detailWindow.hide();
    },
	merge : function() {
		var f = pay_bank_segment.detailForm,data,row;
		if(!f.validate()) {
			mini.alert('数据有误，请检查！');
			return;
		}
		data = f.getData();
		// 如果bankId是手动输入中文
		if(isNaN(data.bankId)) {
			data.bankName = "{0}({1})".format(data.bankId, data.bankCode);
			delete data.bankId;
		} else {
			var bankNameCn = mini.get('bankId').getSelected().text;
            data.bankName = "{0}({1})".format(bankNameCn, data.bankCode);
		}
		data.topCutLength = data.topCut.length;
        delete data.bankCode;
        // 如果是修改
		if(data.id) {
            row = pay_bank_segment.datagrid.getSelected();
            data.createTime = dateUtils.formatDate(row.createTime);
		}
        debugger;
        $.post(
            'sysmgr/banksegment',
            data
        ).done(
            function(res) {
                if(res.errorCode == Code.success){
                    pay_bank_segment.datagrid.reload();
                    mini.showTips({
                        content: res.message,
                        state: "success",
                        x: "center",
                        y: "center",
                        timeout: 2000
                    });
                    pay_bank_segment.detailWindow.hide();
                }else{
                    mini.alert(res.message);
                }
		}).fail(function (jq) {
            console.error(jq);
        });
	}
}
pay_bank_segment.init();