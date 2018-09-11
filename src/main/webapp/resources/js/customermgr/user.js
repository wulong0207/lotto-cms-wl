var Current ={
		"init":function(){
			mini.parse();
			
			Current.userInfo;
			Current.attrType =  mini.get("attrType");
			Current.status = mini.get("status");
			Current.timeType = mini.get("timeType");
			Current.grid = mini.get("datagrid");
			Current.editWindow = mini.get("editWindow");
			Current.channel =  mini.get("channel");
			Current.upoladWindow=  mini.get("upoladWindow");
			Current.upoladPasswordWindow=  mini.get("upoladPasswordWindow");
			Current.beachType = mini.get("beachType");
			Current.userType =  mini.get("userType");
			ComReq.dictionary("0207,0203,0208,0201,0202,0209,0210,0702,0211", function(result){
				 Dic.userstatus = result["0203"];
				 Dic.platform = result["0702"];
				 Current.attrType.setData(result["0207"]);
				 Current.status.setData(Dic.userstatus);
				 Current.timeType.setData(result["0208"]);
				 Current.attrType.select(0);
				 Current.timeType.select(0);
				 Current.userType.setData(result["0211"]);
				 mini.get("mobileLogin_edit").setData(result["0209"]);
				 mini.get("emailLogin_edit").setData(result["0210"]);
				 mini.get("headStatus_edit").setData(result["0202"]);				 
				 mini.get("sex_edit").setData(result["0201"]);
				 mini.get("accountStatus").setData(Dic.userstatus);
				 mini.get("platform_edit").setData(Dic.platform);
				 ComReq.channel(function(result){
					Dic.channel = result;
					var rs = Cms.joinDictionary(result);
					mini.get("channelId_edit").setData(rs);
					Current.channel.setData(rs);
					Current.search();
				});
			});
		},
		/*"ajaxFileUpload":function(){
			debugger;
			Cms.uploadWindow("_upload_images/customer",function(data){
				mini.get("headUrl_edit").setValue(data.url);
                $("#img").attr("src",data.url);
			});
         },*/
		renderBtn:function(e){
            var grid = e.sender,
				record = e.record,
				uid = record._uid,
                html;
            if (grid.isEditingRow(record)) {
                html = '<btn:operate privilege="UPLOAD">'+
                    '<a class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.cancelEditRow(\'bankCardDatagrid\',\'' + uid + '\')">取消</a>'+
                    '</btn:operate>&nbsp;&nbsp;&nbsp;&nbsp;';
                html += '<btn:operate privilege="UPLOAD">'+
                    '<a class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.confiremEditBankCard()">确定</a>'+
                    '</btn:operate>';
			} else {
                // 卡状态:0:删除;1:有效
				var newStatus,txt;
				if(record.status == 0) {
                    newStatus = 1;
                    txt = '绑定'
				} else {
                    newStatus = 0;
                    txt = '解绑'
				}
                html = '<btn:operate privilege="UPLOAD">'+
                    '<a class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.bundleBankCard('+newStatus+')">'+txt+'</a>'+
                    '</btn:operate>&nbsp;&nbsp;&nbsp;&nbsp;';
                html += '<btn:operate privilege="UPLOAD">'+
                    '<a class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.editRow(\'bankCardDatagrid\')">修改</a>'+
                    '</btn:operate>';
			}
			return html;
		},
    	bundleBankCard : function(newStatus) {
			var g = mini.get('bankCardDatagrid'),
				row = g.getSelected();
			$.ajax({
				url : 'paymentmgr/bankcard',
				data : {
					id : row.id,
					userId : row.userId,
					status : newStatus
				},
				type : 'put'
			})
			.done(function (ret) {
				if(ret>0) {
                    g.reload();
                    mini.showTips({
                        content: newStatus == 0 ? '解绑成功' : '绑定成功',
                        state: "success",
                        x: "center",
                        y: "center",
                        timeout: 2000
                    });
				} else {
                    console.error(ret);
				}
            }).fail(function (jqx) {
				console.error(jqx);
            });
		},
    	confiremEditBankCard : function () {
			var g = mini.get('bankCardDatagrid'),
				f = new mini.Form('bankCardDatagrid');
            if(!f.validate()) {
                mini.alert('数据有误!');
                return;
            }
			g.commitEdit();

        },
		batchUpdateBankCard : function (e) {
            var g = mini.get('bankCardDatagrid'),
                f = new mini.Form('bankCardDatagrid');
            if(!f.validate()) {
                mini.alert('数据有误!');
                return;
            }
            g.commitEdit();
			var	isChange = g.isChanged(),data=[],obj
                ;
			if(isChange) {
                g.getChanges().forEach(function (changeRow) {
                    obj = {
                        id		: changeRow.id,
                    userId  : changeRow.userId,
                    bankId  : changeRow.bankId,
                    bankName: changeRow.bankName,
                    cardCode: changeRow.cardCode,
                    bankType: changeRow.bankType,
                    overdue : changeRow.overdue
					}
                    data.push(obj);
                });
                $.ajax({
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    data :JSON.stringify(data),
                    url : 'paymentmgr/bankcard/s',
                    type : 'put'
                })
				.done(function (ret) {
					if(ret>0) {
                        g.reload();
						mini.showTips({
							content: '保存成功',
							state: "success",
							x: "center",
							y: "center",
							timeout: 2000
						});
					} else {
						console.error(ret);
					}
				}).fail(function (jqx) {
					console.error(jqx);
                });
			} else {
				mini.alert('请先修改再保存');
			}
        },
    	changeBankName : function(sender) {
			var grid = mini.get('bankCardDatagrid'),
				row = grid.getSelected();
			// 选提交，否则修改过的值会丢失
			grid.commitEditRow(row);
			grid.updateRow(row, {bankName : sender.selected.text});
		},
         openImage:function(){
           	 var lotterytypeCatalogue = '_upload_images/customer';
        		 Cms.imageManage(lotterytypeCatalogue,function(data){
        			 Current.setImage(data);
        		   });
    	},
    	setImage:function(data){
    		$("#img").attr("src",data.url);
    		mini.get("headUrl_edit").setValue(data.dir);
    	},
         "editwin":function(){
        	var row = Current.grid.getSelected();
     	    if (row) {
     	    	Current.editWindow.set({
    				title:"修改会员信息"
    			});
	     	    var param ={
	     	    	url:"customermgr/user/detail/"+row.id,
	     	    	action:"GET",
	     	    	data:"",
	     	    }	     	   
	     	    Cms.ajax(param, function(row){
	     	    	row.action = "put";
	    	        row.url="customermgr/user";
	    	        var form = new mini.Form("#editform");
	    	        form.clear();
	    	        form.setData(row);
	    	        if(row.headUrl){
	    	        	$("#img").attr("src",row.headUrl);	
	    	        }else{
	    	        	$("#img").attr("src","");
	    	        }
	    	        // 若没修改头像，则传空字符串
	    	        mini.get('headUrl_edit').setValue("");
	    	        Current.editWindow.show();
	    	        Current.setStatus(row);
	    	        Current.userInfo = row;
	     	   	});
	     	    var bg = mini.get('bankCardDatagrid');
                bg.load({userId : row.id});
                bg.hideColumn(3);
     	    }else{
     	    	 mini.alert("请选择一行数据");
     	    }
         },
         "setStatus":function(row){
        	if(!row) return;
        	var account = row.accountModify;
 	        if(account & account == 1){
 	        	Current.doStatus("accountModify","已修改","blue");
 	        }else{
 	        	Current.doStatus("accountModify","未修改","red");
 	        }
 	        var mobile = row.mobileCheck;
 	        if(mobile & mobile ==1){
 	        	Current.doStatus("mobileCheck","已验证","blue");
 	        }else{
 	        	Current.doStatus("mobileCheck","未验证","red");
 	        }
 	        
 	        var email = row.emailCheck;
	        if(email & email ==1){
	        	Current.doStatus("emailCheck","已验证","blue");
	        }else{
	        	Current.doStatus("emailCheck","未验证","red");
	        }
	        var head = row.headCheck;
 	        if(head & head ==1){
 	        	Current.doStatus("headCheck","已审核","blue");
 	        }else{
 	        	Current.doStatus("headCheck","未审核","red");
 	        }
         },
         "doStatus":function(id,html,color){
        	 $("#"+id).html(html);
        	 $("#"+id).css("color",color);
         }
         ,
		"search":function(){
			Current.grid.load(Current.getParamJson());
		},
		"getParamJson":function(){
			var type = Current.attrType.getValue();
			var typeValue = mini.get("attrContent").getValue();
			var status = Current.status.getValue();
			var channel = mini.get("channel").getValue();
			var timeType = Current.timeType.getValue();
			var startTime = mini.get("startTime").getFormValue();
			var endTime = mini.get("endTime").getFormValue();
			var channelId= Current.channel.getValue();
			var para ={
					accountStatus:status,
					channelId:channelId,
			};
			//参数判断，与赋值
			switch (type) {
			case "1":
				para.nickName = typeValue;
				break;
			case "2":
				para.accountName = typeValue;
				break;
			case "3":
				para.cusMobile = typeValue;
				break;
			case "4":
				para.actualName = typeValue;
				break;
			case "5":
				para.idNum = typeValue;
				break;
			}
			switch (timeType) {
			case "1":
				para.startRegistTime = startTime;
				para.endRegistTime = endTime;
				break;
			case "2":
				para.startLastLoginTime = startTime;
				para.endLastLoginTime = endTime;
				break;
			}
			return para;
		}
		,
		"excel":function(){
			var param = Cms.jsonParamStr(Current.getParamJson());
			var url = "customermgr/user/excel?"+param;
			location = url;
		},
		"onrowdblclick":function(){
			 mini.get("edit").doClick();
		},
		"doSubmit":function(){
	        Cms.submit(new mini.Form("#editform"),function(){
	        	Current.editWindow.hide();
		        Cms.reload("datagrid");  
		    });
	     },
	     "uploadSearch":function(){
	    	 Current.upoladWindow.show("left","30px");
	     },
	     "batchSearch":function(){
	    	  var param ={
		     	    	url:"customermgr/user/beach/search",
		     	    	action:"post",
		     	    	data:{
		     	    		"type":Current.beachType.getValue(),
		     	    		"content":mini.get("beachContent").getValue()
		     	    	},
		     	    }	     	   
		     	    Cms.ajax(param, function(data){
		     	    	 Current.upoladWindow.hide();
		            	 Current.grid.setData(data);
		     	    });
	     },
	     "updatePassword":function(){
	    	 mini.get("accountName_upad").setValue(Current.userInfo.accountName);
	    	 mini.get("password_upad").setValue("");
	    	 Current.upoladPasswordWindow.show();
	     },
	     "close":function(){
	    	 Current.upoladPasswordWindow.hide();
	     },
	     "submitPassword":function(){
	    	 var password = mini.get("password_upad").getValue();
	    	 if(!password || password.length != 6){
	    		 mini.alert("密码格式错误");
	    		 return;
	    	 }
	    	 Current.requestUpdatePassword("0",password,"确认修改密码？")
	     }
	     ,"requestUpdatePassword":function(type,password,message){
	    	 var param = {};
	    	 param.id =  Current.userInfo.id;
	    	 param.resetPassword = password;
	    	 param.type = type;
	    	 param.url = "customermgr/user/reset/password";
	    	 param.action = "post";
	    	 Cms.submitByData(param,function(){
	    		 if("1" == type){
	    			 mini.alert("发送成功");
	    		 }
	    		 Current.close();
	    	 },message);
	     },
	     "resetPassword":function(){
	    	 var mail = Current.userInfo.cusMail;
	    	 var mobile = Current.userInfo.cusMobile;
	    	 if(!mail && !mobile){
	    		 mini.alert("用户未绑定手机号和邮箱");
	    		 return;
	    	 }
	    	 Current.requestUpdatePassword("1","","确认重置密码？");
	     }
	     ,"cleanMessage":function(type){
	    	 var mailCount = Current.userInfo.cusMailCount;
	    	 var mobileCount = Current.userInfo.cusMobileCount;
	    	 if(type == '0' && mobileCount == 0){
	    		 mini.alert("次数为0，不能清空！");
	    		 return;
	    	 }
	    	 if(type == '1' && mailCount == 0){
	    		 mini.alert("次数为0，不能清空！");
	    		 return;
	    	 }
	    	 var param = {};
	    	 param.cusMail =  Current.userInfo.cusMail;
	    	 param.cusMobile =  Current.userInfo.cusMobile;
	    	 param.type = type;
	    	 param.url = "customermgr/user/clean/message";
	    	 param.action = "post";
	    	 Cms.submitByData(param,function(){
	    		 mini.alert("清空成功！", "提示", function(){
	    			 Current.editwin();
	    		 });
	    		 
	    	 },"是否清空发送信息？");
	     }
}
Current.init();
