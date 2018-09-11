trans_user={
  init:function(){
    mini.parse();
    trans_user.grid = mini.get("datagrid");
    trans_user.lotteryCategory = mini.get("lotteryCategory");
    trans_user.startIssue = mini.get("startIssue");
    trans_user.userSearchType = mini.get("userSearchType");
    trans_user.transTimeType = mini.get("transTimeType");
    trans_user.transType = mini.get("transType");
    trans_user.transStatus = mini.get("transStatus");
    trans_user.lotteryCode = mini.get("lotteryCode");
    trans_user.statisType = mini.get("statisType");
    trans_user.other;

    ComReq.dictionary("0207,0303,0903,0901,0902", function(result){
 		Dic.lotteryCategory = result["0303"];		
 		Dic.userSearchType = result["0207"];		
 		Dic.transTimeType = result["0903"];		
 		Dic.transType = result["0901"];		
 		Dic.transStatus = result["0902"];		
   		    
    trans_user.lotteryCategory.setData(Dic.lotteryCategory);
    trans_user.userSearchType.setData(Dic.userSearchType);
    trans_user.transTimeType.setData(Dic.transTimeType);
    trans_user.transType.setData(Dic.transType);
    trans_user.transStatus.setData(Dic.transStatus);
    mini.get("transTypeDetail").setData(Dic.transType);
    mini.get("transStatusDetail").setData(Dic.transStatus);
    trans_user.userSearchType.select(0);
    trans_user.transTimeType.select(0);
    trans_common.search(trans_user.grid);
    });
    ComReq.lottery("",function(result){
    	trans_user.lotteryCode.setData(result);
		});
  },
  lotteryCategoryChange : function() {
		 ComReq.lottery(trans_user.lotteryCategory.getValue(),function(result){
			 trans_user.lotteryCode.setData(result);
		 });
  },
  lotteryCodeChange:function(){
		var code = trans_user.lotteryCode.getValue();
		if(code){
			ComReq.issue(code,function(result){
				trans_user.startIssue.setData(result);
			});
		}
	},
	viewDetail: function() {
		var row = trans_user.grid.getSelected(), detailWindow,form;
	    if (row) {
	    	detailWindow = mini.get("detailWindow");
	    	detailWindow.setTitle(row.transCode + " 流水详情");
	    	form = new mini.Form("#detailForm");
	      form.clear();
	      form.setData(row);
	      mini.get("transAmountDetail").setValue(row.transAmount + '元');
	      mini.get("cashAmountDetail").setValue(row.cashAmount + '元');
	      mini.get("redTransAmountDetail").setValue(row.redTransAmount + '元');
	      mini.get("serviceChargeDetail").setValue(row.serviceCharge + '元');
	      detailWindow.show();
    }
	},
	onDrawSummaryCell:function(e){
		var index = e.column._index;
		if(index==0){
			trans_user.other = e.result.other;
			e.cellHtml="统"
		}else if(index==1){
			e.cellHtml="计："
		}else if (e.field == "transType") { 
			e.cellHtml ="<input type=\"radio\" name=\"sis\" checked=\"checked\" onclick=\"trans_user.getChangeSis('0')\" />单页统计";
		}else if(e.field == "transStatus"){
			e.cellHtml ="<input type=\"radio\" name=\"sis\" onclick=\"trans_user.getChangeSis('1')\"/>条件统计";
		}else if(e.field =="transAmount"){
			e.cellHtml ="<span id =\"sisTransAmount\">"+e.result.other.singleTransAmount+"</span>";
		}else if(e.field =="cashAmount"){
			e.cellHtml ="<span id =\"sisCashAmount\">"+e.result.other.singleCashAmount+"</span>";
		}else if(e.field =="redTransAmount"){
			e.cellHtml ="<span id =\"sisRedTransAmount\">"+e.result.other.singleRedTransAmount+"</span>";
		}else if(e.field =="serviceCharge"){
			e.cellHtml ="<span id =\"sisServiceCharge\">"+e.result.other.singleServiceCharge+"</span>";
		}
	},
	getChangeSis:function(type){
		if(trans_user.other){
			if("0"== type){
				$("#sisTransAmount").html(trans_user.other.singleTransAmount);
				$("#sisCashAmount").html(trans_user.other.singleCashAmount);
				$("#sisRedTransAmount").html(trans_user.other.singleRedTransAmount);
				$("#sisServiceCharge").html(trans_user.other.singleServiceCharge);
			}else{
				$("#sisTransAmount").html(trans_user.other.querySumTransAmount);
				$("#sisCashAmount").html(trans_user.other.querySumCashAmount);
				$("#sisRedTransAmount").html(trans_user.other.querySumRedAmount);
				$("#sisServiceCharge").html(trans_user.other.querySumServiceCharge);
			}
		}
	},
};
trans_user.init();