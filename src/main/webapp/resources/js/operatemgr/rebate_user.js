rebate_user = {
	init : function() {
		mini.parse();
		rebate_user.detailWindow = mini.get("detailWindow");
		rebate_user.findListWindow = mini.get("findListWindow");
		rebate_user.grid= mini.get("datagrid");
		rebate_user.matchLimitLottery= mini.get("matchLimitLottery");
		rebate_user.sportLimitLottery= mini.get("sportLimitLottery");
		rebate_user.numLimitLottery= mini.get("numLimitLottery");
		rebate_user.highLimitLottery= mini.get("highLimitLottery");
		rebate_user.rebateType = mini.get("rebateType");
		rebate_user.status = mini.get("status");
		rebate_user.accountName =  mini.get("accountName");
		rebate_user.actualName =  mini.get("actualName");
		rebate_user.userId =  mini.get("userId");
		rebate_user.statusSearch= mini.get("statusSearch");
		Dic.lotteryStatus = [{id:'1',text:'√'},{id:'0',text:'X'}];
		ComReq.dictionary("1108,1109", function(result) {
			Dic.rebateType = result["1108"];
			rebate_user.rebateType.setData(Dic.rebateType);
			Dic.status = result["1109"];
			rebate_user.statusSearch.setData(Dic.status);
			rebate_user.status.setData(Dic.status);
			Cms.search("datagrid", "form1");
			ComReq.lottery("", function(result){
				Dic.OperateLotteryId = result.concat();
				Dic.lotteryCode = Dic.lotteryCategory.concat(result);
				rebate_user.getLottery(Dic.lotteryCode);			
			});	
		});
	},
	getLottery : function(lotteryCodes) {
		var match,matchs=[];
		var sport,sports=[];
		var high,highs=[];
		var num,nums=[];

		for(var i=0;i<lotteryCodes.length;i++){
			var lotteryCode = lotteryCodes[i];
			lotteryCode.enabled = false;
			var lotterId = lotteryCode.id;
			var str = lotterId.substring(0,1);
			if(str=='1'){
				num=num==null?lotterId:num+","+lotterId;
				nums.push(lotteryCode);
			}
			if(str=='2'){
				high=high==null?lotterId:high+","+lotterId;
				highs.push(lotteryCode);
			}
			if(str=='3'){
				if(lotterId==300||lotterId==301){
					match=match==null?lotterId:match+","+lotterId;
					matchs.push(lotteryCode);
				}else{
					sport=sport==null?lotterId:sport+","+lotterId;
					sports.push(lotteryCode);
				}
				
			}
		}
		rebate_user.matchLimitLottery.setData(matchs);
		rebate_user.sportLimitLottery.setData(sports);
		rebate_user.numLimitLottery.setData(nums);
		rebate_user.highLimitLottery.setData(highs);		
		rebate_user.num = num;
		rebate_user.high = high;
		rebate_user.match = match;
		rebate_user.sport = sport;
	},
	edit : function() {
		var form = new mini.Form("#detailForm");
		var row = rebate_user.grid.getSelected(), w = rebate_user.detailWindow;
		if (row) {	
			rebate_user.detailWindow.set({
				title : "修改大客户信息"
			});
			var param ={
	     	    	url:"operatemgr/rebateuser/newinfo/"+row.userId,
	     	    	action:"GET",
	     	    	data:"",
	     	}
			Cms.ajax(param, function(row){
				row.action = "post";
				row.url = "operatemgr/rebateuser";   	      
    	        form.clear();
    	        form.setData(row);
    	        rebate_user.matchLimitLottery.setValue(rebate_user.match);
    			rebate_user.sportLimitLottery.setValue(rebate_user.sport);
    			rebate_user.numLimitLottery.setValue(rebate_user.num);
    			rebate_user.highLimitLottery.setValue(rebate_user.high);
    	        rebate_user.detailWindow.show();
     	   	});
		} else {
			mini.alert("请选择一行数据");
		}
	},
	add : function() {
		var form = new mini.Form("#detailForm");	
		rebate_user.detailWindow.set({
				title : "新增大客户信息"
		});
		form.clear();
		form.setData({
			action : "post",
			url : "operatemgr/rebateuser",
		});
		rebate_user.matchLimitLottery.setValue(rebate_user.match);
		rebate_user.sportLimitLottery.setValue(rebate_user.sport);
		rebate_user.numLimitLottery.setValue(rebate_user.num);
		rebate_user.highLimitLottery.setValue(rebate_user.high);
		rebate_user.status.select(0);
		rebate_user.rebateType.select(0);	
		rebate_user.detailWindow.show();
	},
	merge : function() {		
		var form = new mini.Form("detailForm"), indexChage = false;
		form.validate();
		if (!form.isValid()) {
			mini.alert("表单数据不完整");
			return;
		}
		if(rebate_user.userId.getValue()==null||rebate_user.userId.getValue()==""){
			alert("未查询到用户信息,请重新校验用户名");
			return;
		}
		debugger;
		var fastData = form.getData();
		//信息拼接
		var matchStatus =fastData.matchRuleType==""?0:1;
		var sportStatus =fastData.sportRuleType==""?0:1;
		var highStatus =fastData.highRuleType==""?0:1;
		var numStatus =fastData.numRuleType==""?0:1;
		if(matchStatus==1&&(fastData.matchRulePro==null||fastData.matchRulePro=="")){
			mini.alert("请输入竞彩返佣百分比");
			return;
		}
		if(sportStatus==1&&(fastData.sportRulePro==null||fastData.sportRulePro=="")){
			mini.alert("请输入竞技彩返佣百分比");
			return;
		}
		if(highStatus==1&&(fastData.highRulePro==null||fastData.highRulePro=="")){
			mini.alert("请输入高频彩返佣百分比");
			return;
		}
		if(numStatus==1&&(fastData.numRulePro==null||fastData.numRulePro=="")){
			mini.alert("请输入数字彩返佣百分比");
			return;
		}
		
		if(matchStatus==1)fastData.matchRule = JSON.stringify({lotteryCategory:4,status:matchStatus,rebate:fastData.matchRulePro});		
		if(sportStatus==1)fastData.sportRule = JSON.stringify({lotteryCategory:3,status:sportStatus,rebate:fastData.sportRulePro});
		if(highStatus==1)fastData.highRule = JSON.stringify({lotteryCategory:2,status:highStatus,rebate:fastData.highRulePro});
		if(numStatus==1)fastData.numRule = JSON.stringify({lotteryCategory:1,status:numStatus,rebate:fastData.numRulePro});
			
		var param = {
			url : fastData.url,
			action : fastData.action,
			data : fastData
		};
		Cms.saveDataStringify(param, function() {
			rebate_user.ids = null;
			rebate_user.detailWindow.hide();
			Cms.reload("datagrid");
		});
	},
	findUserName : function(){
		var userName = rebate_user.accountName.getValue();	
		if(userName==null||""==userName)return;
		var data = {pageIndex:0,pageSize:1};
		   data.accountName = userName;
		   var param = {
				url : "customermgr/user/list",
				action : "put",
				data : data
			};
		   $.ajax({
				url : param.url,
				data :param.data,
				type : param.action || 'GET',
			}).done(
				function(result) {
					if(result.code == Code.success){
						if(result.data.length==0){
							mini.alert("未查询到数据");
							rebate_user.actualName.setValue(null); 
							rebate_user.userId.setValue(null); 
						}else{
							rebate_user.actualName.setValue(result.data[0].actualName); 
							rebate_user.userId.setValue(result.data[0].id); 
						}				
					}else{
					    mini.alert("查询数据异常"); 
					    return;
					}
			}).fail(
					function(jqXHR, textStatus, errorThrown) {
						MiniCom.unmask();
						alert(jqXHR.responseText);
					}
			);
	},
	history:function(e){
		 var record = e.record;
	     var id = record.id;
	     var index = e.rowIndex;
	     var s = '<a class="mini-button"  plain="true" href="javascript:rebate_user.findHistory('+index+')">查询历史</a>';
	     return s;
	},
	findHistory:function(index){
		var row = rebate_user.grid.getRow(index);
		var datagridList = mini.get("#datagridDetailList");	
		rebate_user.findListWindow.set({
				title : "查询历史信息"
		});
		var param = {
    			url : "operatemgr/rebateuser/listDetailInfo/"+row.userId,
    			action : "GET",
    			data : ""
    	}
    	Cms.ajax(param, function(data){
    		if(data){      
    			datagridList.setData(data);
    		}else{
    			datagridList.setValue(null);
    		}
    	})
		rebate_user.findListWindow.show();
	}
};
rebate_user.init();