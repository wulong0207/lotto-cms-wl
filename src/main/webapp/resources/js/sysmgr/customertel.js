mini.parse();
getCustomerTel();

function getCustomerTel() {
	$.get("sysmgr/customertel/customerTel", function(result) {
		mini.get("oldTelNo").setValue(result.dicDataName);
	});
}

function updCustomerTel() {
	var newTelNo = mini.get("newTelNo").getValue();
	var telNo = mini.get("telNo").getValue();

	/*var isPhone = /^[0-9|\-]+$/;
	if (!isPhone.test(newTelNo) && !isPhone.test(telNo)) {
		mini.alert("请填写正确的客服热线！");
		return;
	}*/
	if (newTelNo != telNo) {
		mini.alert("两次电话输入不一致，请重新输入！");
		mini.get("newTelNo").setValue("");
		mini.get("telNo").setValue("");
		return;
	}

	var param = {
		url : "sysmgr/customertel/updCustomerTel",
		action : "put",
		data : {
			"telNo" : telNo
		},
	}
	Cms.ajax(param, function(data) {
		mini.get("newTelNo").setValue("");
		mini.get("telNo").setValue("");
		getCustomerTel();
	});
}
