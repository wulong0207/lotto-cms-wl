trans_red={
  init:function(){
    mini.parse();
    trans_red.grid = mini.get("datagrid");
    trans_red.lotteryCategory = mini.get("lotteryCategory");
    trans_red.startIssue = mini.get("startIssue");
    trans_red.userSearchType = mini.get("userSearchType");
    trans_red.searchTimeType = mini.get("searchTimeType");
    trans_red.transType = mini.get("transType");
    trans_red.transStatus = mini.get("transStatus");
    trans_red.lotteryCode = mini.get("lotteryCode");
    trans_red.redType = mini.get("redType");
    trans_red.other;

    ComReq.dictionary("0207,0303,0904,0901,0902,0915", function(result){
 		Dic.lotteryCategory = result["0303"];		
 		Dic.userSearchType = result["0207"];		
 		Dic.searchTimeType = result["0904"];		
 		Dic.transType = result["0901"];		
 		Dic.transStatus = result["0902"];
 		Dic.redType = result["0915"];	
   		    
    trans_red.lotteryCategory.setData(Dic.lotteryCategory);
    trans_red.userSearchType.setData(Dic.userSearchType);
    trans_red.searchTimeType.setData(Dic.searchTimeType);
    trans_red.transType.setData(Dic.transType);
    trans_red.transStatus.setData(Dic.transStatus);
    trans_red.redType.setData(Dic.redType);
    trans_red.userSearchType.select(0);
    trans_red.searchTimeType.select(0);
    });
    ComReq.lottery("",function(result){
    	trans_red.lotteryCode.setData(result);
		});
    trans_common.search(trans_red.grid);
  },
//  search:function(){
//		var form = new mini.Form("#form1");
//		var data = form.getData();      //获取表单多个控件的数据
//		data.startTime = mini.get("startTime").getFormValue();
//		data.endTime = mini.get("endTime").getFormValue();
//  	trans_red.grid.load(data);
//	},
  lotteryCategoryChange : function() {
		 ComReq.lottery(trans_red.lotteryCategory.getValue(),function(result){
			 trans_red.lotteryCode.setData(result);
		 });
  },
  lotteryCodeChange:function(){
		var code = trans_red.lotteryCode.getValue();
		if(code){
			ComReq.issue(code,function(result){
				trans_red.startIssue.setData(result);
			});
		}
	},
	onDrawSummaryCell:function(e){
		var index = e.column._index;
		if(index==0){
			trans_red.other = e.result.other;
			e.cellHtml="统"
		}else if(index==1){
			e.cellHtml="计："
		}else if (e.field == "nickName") { 
			e.cellHtml ="<input type=\"radio\" name=\"sis\" checked=\"checked\" onclick=\"trans_red.getChangeSis('0')\" />单页统计";
		}else if(e.field == "redTransCode"){
			e.cellHtml ="<input type=\"radio\" name=\"sis\" onclick=\"trans_red.getChangeSis('1')\"/>条件统计";
		}else if(e.field =="transAmount"){
			e.cellHtml ="<span id =\"sisTransAmount\">"+e.result.other.singleTransAmount+"</span>";
		}else if(e.field =="aftTransAmount"){
			e.cellHtml ="<span id =\"sisAftTransAmount\">"+e.result.other.singleAftTransAmount+"</span>";
		}
	},
	getChangeSis:function(type){
		if(trans_red.other){
			if("0"== type){
				$("#sisTransAmount").html(trans_red.other.singleTransAmount);
				$("#sisAftTransAmount").html(trans_red.other.singleAftTransAmount);
			}else{
				$("#sisTransAmount").html(trans_red.other.queryTransAmount);
				$("#sisAftTransAmount").html(trans_red.other.queryAftTransAmount);
			}
		}
	},
};
trans_red.init();