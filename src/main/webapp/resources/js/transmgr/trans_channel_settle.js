trans_channel_settle={
	status : {
		// 1：已结算；2：延后结算；3：已派发；4：不结算；5：未结算
		settled : {val : 1},
		delay : {val : 2},
		dispatched : {val : 3},
		forbid : {val : 4},
		unsettle : {val:5}
	},
  init:function(){
    mini.parse();
    trans_channel_settle.grid = mini.get("datagrid");
    trans_channel_settle.settleStatus = mini.get("settleStatus");
    trans_channel_settle.settleType = mini.get("settleType");
    trans_channel_settle.searchTimeType = mini.get("searchTimeType");

    ComReq.dictionary("0912,0913,0914", function(result){
 		Dic.settleStatus = result["0912"];		
 		Dic.settleType = result["0913"];		
 		Dic.searchTimeType = result["0914"];		
   		    
    trans_channel_settle.settleStatus.setData(Dic.settleStatus);
    trans_channel_settle.settleType.setData(Dic.settleType);
    trans_channel_settle.searchTimeType.setData(Dic.searchTimeType);
    mini.get("settleStatusDetail").setData(Dic.settleStatus);
    mini.get("settleTypeDetail").setData(Dic.settleType);
    
    trans_channel_settle.searchTimeType.select(0);
    trans_common.search(trans_channel_settle.grid);
    });
  },
//  search:function(){
//		var form = new mini.Form("#form1");
//		var data = form.getData();      //获取表单多个控件的数据
//		data.startTime = mini.get("startTime").getFormValue();
//		data.endTime = mini.get("endTime").getFormValue();
//  	trans_channel_settle.grid.load(data);
//	},
	viewDetail: function() {
		var row = trans_channel_settle.grid.getSelected(), detailWindow,form;
    if (row) {
    	detailWindow = mini.get("detailWindow");
    	detailWindow.setTitle(row.transChannelSettleCode + " 渠道结算详情");
    	form = new mini.Form("#detailForm");
      form.clear();
      form.setData(row);
      detailWindow.show();
    }
	},
	// 结算
	settle : function() {
		var rows,param;
		rows = trans_channel_settle.grid.getSelecteds();
		if(!rows || rows.length === 0) {
			mini.alert("至少选中一条记录!");
			return;
		}
		// 只有状态为未结算(或延后结算？)才能结算
		if( rows.some(function(row) {return row.settleStatus !==  trans_channel_settle.status.unsettle.val}) ) {
			mini.alert("只有状态为未结算的才能结算!");
			return;
		}
		$.ajax({
      headers: { 
        'Accept': 'application/json',
        'Content-Type': 'application/json' 
      },
      'type': 'POST',
      'url': "transmgr/channelsettle/settle",
      'data': JSON.stringify(Cms.getSubsetObj(rows,['transChannelSettleCode','channelId'])),
      'dataType': 'json'
    }).done(
			function(){
				mini.showTips({
           content: "结算成功!",
           state: "success",
           x: "center",
           y: "center",
           timeout: 2000
       	});	
				trans_channel_settle.grid.reload();
			}
    ).fail(
    	function() {
    		mini.alert("结算失败!");
    	}
    );
	}
};
trans_channel_settle.init();