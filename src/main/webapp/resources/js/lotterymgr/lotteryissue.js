var Current ={
		"init":function(){
			mini.parse();
			Current.lotteryCategory = mini.get("lotteryCategory");
			Current.lotteryCode= mini.get("lotteryCode");
			Current.issueCode = mini.get("issueCode");
			Current.saleStatus = mini.get("saleStatus");
			Current.grid = mini.get("datagrid");
			Current.editWindow = mini.get("editWindow");
			Current.drawGrid = mini.get("draw_grid");
			
			ComReq.dictionary("0303,0401,0402,0403", function(result){
				 Dic.lotteryCategory = result["0303"];		
			     Dic.issueType = result["0401"];
			     Dic.saleStatus = result["0402"];
			     Dic.yesNo = result["0403"];
			   		    
			     Current.lotteryCategory.setData(Dic.lotteryCategory);
			     Current.saleStatus.setData(Dic.saleStatus);
			     Current.search();
				
			});
			ComReq.lottery("",function(result){
				Current.lotteryCode.setData(result);
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
				ComReq.issue(code,function(result){
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
		"search":function(){
			Current.grid.load(Current.getParamJson());
		},
		"onrowdblclick":function(){
			 mini.get("edit").doClick();
		},
		"editwin":function(){
			var row = Current.grid.getSelected();
			if(row){				
			    var code = row.lotteryCode;
			    var height = 600;
			    if("BB"==code
			    		||"FB"==code){
			    	height = 350;
			    }
			    mini.open({
	                url: "lotterymgr/issue/editwin/"+code,
	                title: "修改彩期",
	                width: 1100, height: height,
	                onload: function () {
	                	var iframe = this.getIFrameEl();
                        iframe.contentWindow.initData(row.id);
                    },
                    ondestroy: function (action) {
                    	Cms.reload("datagrid");  
                    }
			    });
			}else{
				mini.alert("请选择一行数据！");
			}
		},
		"addLotteryIssue":function(){
			var code = Current.lotteryCode.getValue();
			var name = Current.lotteryCode.getText();
			if(code){
			    mini.open({
	                url: "lotterymgr/issue/editwin/"+code,
	                title: "添加彩期("+name+")",
	                width: 1100, height: 600,
	                onload: function () {
	                	var data ={
	        			    	action:"post",
	        			    	url:"lotterymgr/issue",
	        			    	lotteryCode:code,
	        			}
	                	var iframe = this.getIFrameEl();
                        iframe.contentWindow.setAddData(data);
                    },
                    ondestroy: function (action) {
                    	Cms.reload("datagrid");  
                    }
			    });
			}else{
				mini.alert("请在右边下拉框选择添加彩种！");
			}
		},
		"doSubmit":function(){
			var code = mini.get("lotteryCode_add").getValue();
			mini.alert(code +Dic.allCode[code]);
			return;
			Cms.submit(new mini.Form("#editform"),function(){
				Current.editWindow.hide();
		        Cms.reload("datagrid");  
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
		},
		"excel":function(){
			var param = Cms.jsonParamStr(Current.getParamJson());
			var url = "lotterymgr/issue/excel?"+param;
			location = url;
		},
		"restartThread":function(){
			var lotteryCode  = Current.lotteryCode.getValue();
			if(!lotteryCode){
				mini.alert("请在下拉框中选择彩种");
				return;
			}
			var text = Current.lotteryCode.getText();
			mini.confirm("确定重启<span style=\"color: red;\">"+text+"</span>线程?", "提示", function(e) {
				if(e=="ok"){
					var param = {
							url:"lotterymgr/issue/schedule",
							data:{
								lotteryCode:lotteryCode
							},
							action:"post"
					}
					Cms.ajax(param,function(){
						mini.alert("操作成功！");
					});
				}
			});
		},
		"executeChaseCode":function(){
			var lotteryCode  = Current.lotteryCode.getValue();
			if(!lotteryCode){
				mini.alert("请在下拉框中选择彩种");
				return;
			}
			var text = Current.lotteryCode.getText();
			mini.confirm("确定执行<span style=\"color: red;\">"+text+"</span>追号?", "提示", function(e) {
				if(e == "ok") {
					var param = {
							url:"lotterymgr/issue/manualChase",
							data:{
								lotteryCode:lotteryCode
							},
							action:"post"
					}
					Cms.ajax(param,function() {
						mini.alert("操作成功！");
					});
				}
			});
		}
} 

Current.init();