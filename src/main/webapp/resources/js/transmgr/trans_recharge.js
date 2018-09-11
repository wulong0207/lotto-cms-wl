trans_recharge={
	status : {
		ing : {val : 1},
		success : {val : 2},
		fail : {val : 3},
		close : {val : 4}
	},
  init:function(){
    mini.parse();
    trans_recharge.grid = mini.get("datagrid");
    trans_recharge.searchUserType = mini.get("searchUserType");
    trans_recharge.searchTimeType = mini.get("searchTimeType");
    trans_recharge.rechargeChannel = mini.get("rechargeChannel");
    trans_recharge.bankCardType = mini.get("bankCardType");
    trans_recharge.rechargeBank = mini.get("rechargeBank");
    trans_recharge.payType = mini.get("payType");
    
    trans_recharge.transStatus = mini.get("transStatus");
    trans_recharge.other;

    ComReq.dictionary("0207,0603,0901,0904,0905,0906,0908,0916,1804", function(result){
 		Dic.searchUserType = result["0207"];		
 		Dic.searchTimeType = result["0904"];		
 		Dic.transStatus = result["0908"];		
 		Dic.rechargeChannel = result["0905"];		
 		Dic.bankCardType = result["0906"];		
 		//Dic.rechargeBank = result["0907"];		
 		Dic.payType = result["1804"];		
 		Dic.moneyType = result["0916"];		
 		Dic.rechargePlatform = result["0603"];
 		// 9175 人工充值，与刘琼确认交易分类只保留3个 增加金额的选项
 		Dic.tradeType = [];
 		result["0901"].forEach(function(type) {
 			var text= type.text;
 			if(text =='充值' || text =='返奖' ||text =='活动赠送') 
 				Dic.tradeType.push(type);
 		});
 		trans_recharge.searchUserType.setData(Dic.searchUserType);
 		var manualUserTypeDic = [];
 		Dic.searchUserType.forEach(function(dic) {
		if (dic.text === '会员昵称' ||dic.text === '会员账号' || dic.text === '手机号码')
			manualUserTypeDic.push(dic);
		});
	 	// 查询银行
		$.ajax({
	    	url : "paymentmgr/index/banks/dic",
	    	async : false
	    }).done(function(data) {
	    	Dic.rechargeBank = data;
	    });
		// 人工充值下拉框只有"会员昵称"、"会员账号"、"手机号码"
	    mini.get('manualUserType').setData(manualUserTypeDic);
	    mini.get('tradeType').setData(Dic.tradeType);
	    trans_recharge.searchUserType.setData(Dic.searchUserType);
	    trans_recharge.searchTimeType.setData(Dic.searchTimeType);
	    trans_recharge.transStatus.setData(Dic.transStatus);
	    trans_recharge.rechargeChannel.setData(Dic.rechargeChannel);
	    trans_recharge.bankCardType.setData(Dic.bankCardType);
	    trans_recharge.rechargeBank.setData(Dic.rechargeBank);
	    trans_recharge.payType.setData(Dic.payType);
	    mini.get("rechargeChannelDetail").setData(Dic.rechargeChannel);
	    mini.get("rechargePlatformDetail").setData(Dic.rechargePlatform);
	    mini.get("transStatusDetail").setData(Dic.transStatus);
	    mini.get("rechargeBankDetail").setData(Dic.rechargeBank);
	    mini.get("moneyTypeResupply").setData(Dic.moneyType);
	    mini.get("rechargeType").setData(Dic.moneyType);
	    mini.get("payTypeDetail").setData(Dic.payType);
	    mini.get("bankCardTypeDetail").setData(Dic.bankCardType);
	    trans_recharge.searchUserType.select(0);
	    trans_recharge.searchTimeType.select(0);
	    trans_common.search(trans_recharge.grid);
	    });
  },
	onDrawSummaryCell:function(e){
		var index = e.column._index;
		if(index==0){
			trans_recharge.other = e.result.other;
			e.cellHtml="统"
		}else if(index==1){
			e.cellHtml="计："
		}else if (index==2) { 
			e.cellHtml ="<input type=\"radio\" name=\"sis\" checked=\"checked\" onclick=\"trans_recharge.getChangeSis('0')\" />单页统计";
		}else if(index==3){
			e.cellHtml ="<input type=\"radio\" name=\"sis\" onclick=\"trans_recharge.getChangeSis('1')\"/>条件统计";
		}else if(e.field =="rechargeAmount"){
			e.cellHtml ="<span id =\"sisRechargeAmount\">"+e.result.other.singleRechargeAmount+"</span>";
		}else if(e.field =="serviceCharge"){
			e.cellHtml ="<span id =\"sisServiceCharge\">"+e.result.other.singleServiceCharge+"</span>";
		}
	},
	getChangeSis:function(type){
		if(trans_recharge.other){
			if("0"== type){
				$("#sisRechargeAmount").html(trans_recharge.other.singleRechargeAmount);
				$("#sisServiceCharge").html(trans_recharge.other.singleServiceCharge);
			}else{
				$("#sisRechargeAmount").html(trans_recharge.other.queryRechargeAmount);
				$("#sisServiceCharge").html(trans_recharge.other.queryServiceCharge);
			}
		}
	},
	viewDetail: function() {
		var row = trans_recharge.grid.getSelected(), detailWindow,form;
    if (row) {
    	detailWindow = mini.get("detailWindow");
    	detailWindow.setTitle(row.transRechargeCode + " 流水详情");
    	form = new mini.Form("#detailForm");
      form.clear();
      form.setData(row);
      mini.get("rechargeAmountDetail").setValue(row.rechargeAmount + '元');
      var arrivalAmount = row.arrivalAmount;
      if(typeof(arrivalAmount)=="undefined"){ 
    	  arrivalAmount = 0;
      } 
      mini.get("arrivalAmountDetail").setValue(arrivalAmount + '元');
      mini.get("serviceChargeDetail").setValue(row.serviceCharge + '元');
      mini.get("redAmountDetail").setValue(row.redAmount + '元');
      detailWindow.show();
    }
	},
	toResupply : function(){
		var rows = trans_recharge.grid.getSelecteds();
		if(!rows || rows.length !== 1) {
			mini.alert("请选中一条记录!");
			return;
		}
		new mini.Form('resupplyWindow').setData(trans_recharge.grid.getSelected());
		var resupplyWindow = mini.get("resupplyWindow");
		resupplyWindow.show();
	},
	toManualRecharge : function(){
		new mini.Form('manualRechargeWindow').clear();
		mini.get('manualUserType').select(1);
		var manualRechargeWindow = mini.get("manualRechargeWindow");
		manualRechargeWindow.show();
	},
	// 补单
	resupply : function() {
//		var rows,param;
//		rows = trans_recharge.grid.getSelecteds();
//		if(!rows || rows.length === 0) {
//			mini.alert("至少选中一条记录!");
//			return;
//		}
//		// 只有状态为进行中的才能补单
//		if( rows.some(function(row) {return row.transStatus !==  trans_recharge.status.ing.val}) ) {
//			mini.alert("只有状态为进行中的才能补单!");
//			return;
//		}
		var data = {};
		$.extend(data, trans_recharge.grid.getSelected(), new mini.Form('resupplyWindow').getData());
		param = {};
		param.url = "transmgr/recharge/resupply";
		param.data = data;
		param.action="POST";
		Cms.saveDataStringify(param, function() {
			mini.get("resupplyWindow").hide();
			trans_recharge.grid.reload();
		});
		//param.data = {ids : [1,2]};
//		Cms.ajaxSend(
//			param, 
//			function(){
//				mini.showTips({
//           content: "补单成功!",
//           state: "success",
//           x: "center",
//           y: "center",
//           timeout: 2000
//       	});	
//				trans_recharge.grid.reload();
//			}
//		);
	},
	manualRecharge : function() {
		if(!MiniCom.isValidForm('manualRechargeWindow')) {
			mini.alert('请填写完整数据');
			return;
		}
//		var data = new mini.Form('manualRechargeWindow').getData();
//		var op = {
//			data : data,
//			done : function() {
//				mini.alert("充值成功");
//				return;
//			},
//			fail : function() {
//				mini.alert("充值失败");
//				return;
//			}
//		};
//		Task.runTask(op);
		var data = {};
		$.extend(data, new mini.Form('manualRechargeWindow').getData());
		var userParam = {};
		if(data.manualUserType==1) {
			userParam.nickName = data.manualUserTypeValue;
		} else if(data.manualUserType==2) {
			userParam.accountName = data.manualUserTypeValue;
		} else if(data.manualUserType==3) {
			userParam.cusMobile = data.manualUserTypeValue;
		} else {
			mini.alert("用户信息不正确，请检查");
			return;
		}
		var userId,error=false, errorMsg;
		$.ajax({
			url : "customermgr/user/ids",
			data : userParam,
			async : false
		}).done(function(data){
			if(data.length===0) {
				error = true;
				errorMsg = '用户不存在，请检查填写的用户信息'
			} else if(data.length>1) {
				error = true;
				errorMsg = '存在多个符合条件的用户'
			} else {
				userId = data[0];
			}
		});
		if(error){
			mini.alert(errorMsg);
			return;
		}
		data.userId = userId;
		param = {};
		param.url = "transmgr/recharge/manual";
		param.data = data;
		param.action="POST";
		param.tip = "确定充值?";
		param.ingTip = "充值中";
		Cms.saveDataStringify(param, function() {
			mini.get("manualRechargeWindow").hide();
			trans_recharge.grid.reload();
		});
	},
	/*
	 *点击补单按钮，执行调度任务的中的用户充值补单任务
	 */
	triggerResupply : function() {
		var rows = trans_recharge.grid.getSelecteds();
		if(!rows || rows.length !== 1) {
			mini.alert("请选中一条记录!");
			return;
		}
		// 只有状态为订单关闭或交易失败的充值记录，才能进行补单
		var row = rows[0];
		if(!(row.transStatus === 3 || row.transStatus == 4)) {
			mini.alert("只有状态为订单关闭或交易失败的充值记录，才能进行补单!");
			return;
		}
		var op = {
			data : {jobId : "000040"},
			done : function() {
				mini.alert("补单成功");
				return;
			},
			fail : function() {
				mini.alert("系统流水编号：{0}补单失败".format(row.transRechargeCode));
				return;
			}
		};
		Task.runTask(op);
	}
};
trans_recharge.init();