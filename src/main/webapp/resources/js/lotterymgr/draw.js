var Current ={
		"init":function(){
			mini.parse();
			
			Current.lotteryCategory = mini.get("lotteryCategory");
			Current.lotteryCode= mini.get("lotteryCode");
			Current.issueCode = mini.get("issueCode");
			
			Current.sportLottery = mini.get("sportLottery");
			Current.sportLotteryChild = mini.get("sportLotteryChild");
			
			ComReq.dictionary("0303", function(result){
				 Dic.lotteryCategory = result["0303"];
				 Current.lotteryCategory.setData(Dic.lotteryCategory);
			});
			ComReq.lottery("",function(result){
				Current.lotteryCode.setData(result);
			});
			ComReq.lottery("3",function(result){
				Current.sportLottery.setData(result);
			});
		},
		"lotteryCategoryChange":function(){
			 var code = Current.lotteryCategory.getValue();
			 ComReq.lottery(code,function(result){
				 Current.lotteryCode.setData(result);
			 });
		},
		"onCloseClickType":function(e){
			var obj = e.sender;
			obj.setText("");
			obj.setValue("");
			ComReq.lottery("",function(result){
				 Current.lotteryCode.setData(result);
			});
		},
		"lotteryCodeChange":function(){
			var code = Current.lotteryCode.getValue();
			if(code){
				ComReq.cutOffIssue(code,function(result){
					Current.issueCode.setData(result);
				});
			}
		},
		"onCloseClickCode":function(e){
			var obj = e.sender;
			   obj.setText("");
			   obj.setValue("");
			   Current.issueCode.setData([]);
		},
		"sportLotteryChange":function(){
			 var code = Current.sportLottery.getValue();
			 if(code){
				ComReq.lotteryChild(code,function(result){
						Current.sportLotteryChild.setData(result);
				});
			 }
		}
		,
		"drawSchedule":function(resetType){
			if(resetType == 'order'){
				return;
			}
			var param = new mini.Form(resetType).getData();
			var lotteryIssue="";
			var lotteryCode = param.lotteryCode;;
			if(resetType == 'issue'){
				lotteryIssue = param.lotteryIssue;
				if(lotteryIssue == ""){
					mini.alert("请选择彩期");
				}
			}else if(resetType == 'sport'){
				if(param.lotteryChild){
					lotteryIssue +=param.lotteryChild;
				}
				if(param.code){
					lotteryIssue +=param.code;
				}
			}
			if(lotteryCode){
				Cms.drawSchedule(lotteryCode,lotteryIssue,1,function(){
					
				});
			}else{
				mini.alert("请选择彩种");
			}
		
		}
		,
		"triggerResteDraw":function(resetType){
			if(!MiniCom.isValidForm(resetType)) {
				mini.alert("数据填写完整");
				return;
			}
			var param = new mini.Form(resetType).getData();
			//param.resetType = resetType;
			// 按订单
			if(resetType == 'order') {
				param.orderCodes = param.orderCodes.replace(/，/g, ',');
				param.jobId = "000021";
			} 
			else if(resetType == 'issue'){
				// 重置开奖id 
				param.jobId = "000021";
			}
			else if(resetType == 'sport') {
				param.jobId = "000022";
			} else {
				throw new Error("重置开奖类型错误");
			}
			var op = {
				data : param
			};
			Task.runTask(op,function(result){
				if(resetType == 'order'){
					mini.alert("重置开奖成功");
				}else{
					Current.drawSchedule(resetType);
				}
			});
		},
		"getParamJson":function(){
			var para ={
					lotteryCategory:Current.lotteryCategory.getValue(),
					lotteryCode:Current.lotteryCode.getValue(),
					issueCode:Current.issueCode.getValue(),
					saleStatus:Current.saleStatus.getValue(),
				};
			return para;
		}
} 

Current.init();