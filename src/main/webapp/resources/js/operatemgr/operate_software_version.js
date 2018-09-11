var Software_Version = {
		"init" : function(){
			mini.parse();
			//页面标签对象
			Software_Version.dataGrid = mini.get("dataGrid");
			Software_Version.SoftwareWindow = mini.get("SoftwareWindow");
			
			//查询条件
			Software_Version._code = mini.get("_code");
			Software_Version._name = mini.get("_name");
			Software_Version._type = mini.get("_type");
			Software_Version._isnew = mini.get("_isnew");
			Software_Version._isupdate = mini.get("_isupdate");
			Software_Version.beginTime = mini.get("beginTime");
			Software_Version.endTime = mini.get("endTime");
			
			//form对象
			Software_Version.SoftwareForm = new mini.Form("#SoftwareForm");
			Software_Version.code = mini.get("code");
			Software_Version.isnew = mini.get("isnew");
			Software_Version.isupdate = mini.get("isupdate");
			Software_Version.type = mini.get("type");
			Software_Version.url = mini.get("url");
			Software_Version.name = mini.get("name");
			Software_Version.size = mini.get("size");
			Software_Version.appUrl = mini.get("appUrl");
			
			//初始化查询条件
			ComReq.dictionary("2401,2402,2403", function(result) {
				Dic.isnew = result["2401"];//是否最新版本
				Dic.isupdate = result["2402"];//是否强制更新
				Dic.mobileType = result["2403"]; //移动端类型
				
				Software_Version._type.setData(Dic.mobileType);
				Software_Version._isnew.setData(Dic.isnew);
				Software_Version._isupdate.setData(Dic.isupdate);
				Software_Version.isnew.setData(Dic.isnew);
				Software_Version.isupdate.setData(Dic.isupdate);
				Software_Version.type.setData(Dic.mobileType);
				Software_Version.search();
				// 市场渠道下拉框赋值
				$.ajax({
					url : 'operatemgr/marketchannel/dic',
					async : false
				}).done(function(data) {
					data.shift();
					Dic.channel=data;
					mini.get("channelId4Search").setData(Dic.channel);
					mini.get("channelId").setData(Dic.channel);
				});
			});	
			
			
			Software_Version.typeChange();
		},
		"onRowdblClick" : function (){
			Software_Version.toEdit();
		},
		"getParamJson" : function(){
			var _code = Software_Version._code.getValue();
			var _name = Software_Version._name.getValue();
			var _type = Software_Version._type.getValue();
			var _isnew = Software_Version._isnew.getValue();
			var _isupdate = Software_Version._isupdate.getValue();
			var _beginTime = Software_Version.beginTime.getFormValue();
			var _endTime = Software_Version.endTime.getFormValue();
			
			var params = {
					code:_code,
					name:_name,
					type:_type,
					isnew:_isnew,
					isupdate:_isupdate,
					beginTime:_beginTime,
					endTime:_endTime,					
					sortField : "S.id",
					sortOrder : "desc"
			};
			return params;
		},
		"toAdd" : function(){
			Software_Version.SoftwareWindow.set({
				title : "新增"
			});
			Software_Version.SoftwareForm.clear();
			var row = {};
			row.action = "post";
		    row.httpUrl= "";
		    Software_Version.SoftwareForm.setData(row);
		    $("#operateBtn").attr("onClick","Software_Version.add();")
			Software_Version.SoftwareWindow.show();		
		    $("#progress").html("");
		    $("#fileAddress").val("");
		    $("#appfile").val("");
		    mini.get('type').setEnabled(true);
		    mini.get('status').select(0);
		},
		"add" : function(){
			Software_Version.SoftwareForm.validate();
		    if (Software_Version.SoftwareForm.isValid() == false){
	             mini.alert("表单数据有误");
		    	 return;
		    }
		    var data = Software_Version.SoftwareForm.getData(true, false);			
			var param = {};
			param.url = "operatemgr/software/version/add";
			param.data = data;
			param.action = "post";
			Cms.ajaxFirm("确定保存?", param, function(data){
				Software_Version.SoftwareWindow.hide();	
				Software_Version.dataGrid.reload();
				Cms.showTips("保存成功!", 2000);
			});
			$("#progress").html("");
            $("#fileAddress").val("");
            $("#appfile").val("");
		},
		"toEdit" : function(){
			var row = Software_Version.dataGrid.getSelected();
			if(row){
				Software_Version.SoftwareWindow.set({
					title : "编辑"
				});
				Software_Version.SoftwareForm.clear();
			    $("#operateBtn").attr("onClick","Software_Version.edit();");
			    Software_Version.SoftwareForm.setData(row);
			    mini.get('type').setEnabled(false);
				Software_Version.SoftwareWindow.show();
				
			} else {
		        mini.alert("请选择一行数据");
		    }		
			 $("#progress").html("");
			    $("#fileAddress").val("");
			    $("#appfile").val("");
		},
		"edit" : function(){
			Software_Version.SoftwareForm.validate();
		    if (Software_Version.SoftwareForm.isValid() == false){
	             mini.alert("表单数据有误");
		    	 return;
		    }
		    var data = Software_Version.SoftwareForm.getData(true, false);
		    var row = Software_Version.dataGrid.getSelected();
			// 判断是否最新版本、是否强制更新、版本更新说明是否有变化
			// 如果有重新上传
		    data.changeUpdateOrNewOrDesc = data.isupdate != row.isupdate || data.isnew !=row.isnew || data.describe != row.describe;
		    data.reupload = data.url != row.url;
		    data.changeIsNew = data.isnew != row.isnew;
		    data.oldCode = row.code;
			var param = {};
			param.url = "operatemgr/software/version/upd";
			param.data = data;
			param.action = "post";
			
			Cms.ajaxFirm("确定修改?", param, function(data){
				Software_Version.SoftwareWindow.hide();	
				Software_Version.dataGrid.reload();  
				Cms.showTips("修改成功!", 2000);
			});
			$("#progress").html("");
            $("#fileAddress").val("");
            $("#appfile").val("");
		},		
		"search" : function(){
			Software_Version.dataGrid.load(Software_Version.getParamJson());
		},
		"updIsnew" : function(){
			var row = Software_Version.dataGrid.getSelected();
			if(row && row.isnew == 1){
				var param = {};
				param.url = "operatemgr/software/version/updIsnew?isnew=" + row.isnew + "&type=" + row.type;
				param.action = "get";
				Cms.ajaxFirm("确定要停用?", param, function(){
					Cms.showTips("停用成功!", 2000);
					Software_Version.dataGrid.reload();  
				});
			} else {
		        mini.alert("无需停用, 请选择有效的行数据!!!");
		    }			
		},
		"del" : function(){
			var row = Software_Version.dataGrid.getSelected();
			if(row){
				Cms.del(Software_Version.dataGrid, "operatemgr/software/version/upd", function(){});
			} else {
		        mini.alert("请选择一行数据");
		    }
		},
		"close":function(){
			Software_Version.SoftwareWindow.hide();
			$("#progress").html("");
            $("#fileAddress").val("");
            $("#appfile").val("");
            var originalFilename = $("#originalFilename").val();
            if(originalFilename){
            	$.ajax({
            		type: 'POST',
            		url: "operatemgr/software/version/delFile",
            		data: {"fileName":originalFilename},
            		success: function(data){
            			if(typeof(obj.errorCode) != 'undefined') {  
    	                    if(obj.errorCode == Code.success) {  
    	                    	
    	                    }
            			}
            		},
            		dataType: "json"
            	});
            }
		},
		"upload":function() {
			var url = "operatemgr/software/version/upload";
			$("#progress").html("");
            $("#fileAddress").val("");
			$.ajaxFileUpload({  
	            url:url,  
	            secureuri:false,  
	            fileElementId:'appfile',//file标签的id  
	            dataType: 'json',//返回数据的类型  
	            data:{name:'logan'},//一同上传的数据  
	            success: function (obj, status) {  
	                //把图片替换  
	                if(typeof(obj.errorCode) != 'undefined') {  
	                    if(obj.errorCode == Code.success) {  
	                        $("#progress").html("文件大小："+obj.byte+" bytes");
	                        $("#fileAddress").val(obj.fileAddress);
	                        Software_Version.url.setValue(obj.fileAddress);
	                        Software_Version.code.setValue(obj.version);
	                        Software_Version.name.setValue(obj.versionName);
	                        if(obj.appType=="Android"){
	                        	Software_Version.type.select(1);
	                        }else{
	                        	Software_Version.type.select(0);
	                        }
	                        Software_Version.size.setValue(obj.versionSize);
	                        Software_Version.appUrl.setValue(obj.mobileAddress);
	                    } else {  
	                        $("#progress").html(obj.message);  
	                    }  
	                }  
	            },  
	            error: function (data, status, e) {  
	                alert(e);  
	            }  
	        });  
		},
		"typeChange":function(){
			var tp = mini.get('type').getValue()
			if(tp==1){//ios
				Software_Version.code.allowInput = true;
				Software_Version.url.allowInput = true;
				Software_Version.appUrl.allowInput = true;
				Software_Version.name.allowInput = true;
				Software_Version.type.allowInput = true;
				Software_Version.size.allowInput = true;
			}else if(tp==2){//android
				Software_Version.code.allowInput = false;
				Software_Version.url.allowInput = false;
				Software_Version.appUrl.allowInput = false;
				Software_Version.name.allowInput = false;
				Software_Version.type.allowInput = false;
				Software_Version.size.allowInput = false;
			}
		}
		
}
Software_Version.init();