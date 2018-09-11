var Current = {
	"init" : function() {
		mini.parse();
		Current.sendMsgWindow = mini.get("sendMsgWindow");//发送新信息窗体
		Current.grid = mini.get("publish-datagrid");
		//发送新消息列表
		Current.newMsgStatus = mini.get("newMsgStatus");//发送状态
		Current.newMsgTimeType = mini.get("newMsgTimeType");//发送时间条件
		Current.msgTemplateId = mini.get("msgTemplateId");//消息类型
		Current.newMsgType = mini.get("newMsgType");
		
		//初始化查询条件
		ComReq.dictionary("1706,1707,2101,2106", function(result1) {
			Dic.newMsgStatus= result1["1706"];//发送消息状态
			Dic.newMsgTimeType = result1["1707"];//发送时间类型
			Dic.msgType = result1["2101"];//消息分类
			Dic.toPage = result1["2106"];//跳转界面
			Current.newMsgStatus.setData(Dic.newMsgStatus);
			Current.newMsgTimeType.setData(Dic.newMsgTimeType);
			Current.newMsgType.setData(Dic.msgType);
			//初始化模板下拉选项列表
			ComReq.msgTemplate("1001",function(result2){
				Dic.template = result2;
				Current.msgTemplateId.setData(Dic.template);
				Current.search();
			});
		});
		
		ComReq.lottery("",function(result){
			Dic.lottery = result;
		});
		
		//获取微信公众号设置模板下拉选项列表
		ComReq.msgTemplate("1002",function(result){
			Dic.wechatTemplate = result;
		});
	},
	//发送信息查询
	"search" : function() {
		Current.grid.load(Current.getNewMsgParamJson());
	},
	//发送信息查询条件参数
	"getNewMsgParamJson" : function() {
		var newMsgTimeType = Current.newMsgTimeType.getValue();
		var newMsgStatus = Current.newMsgStatus.getValue();
		var msgTemplateId = Current.msgTemplateId.getValue();
		var publishMsgType = Current.newMsgType.getValue();
		var publishStartTime = mini.get("publishStartTime").getFormValue();
		var publishEndTime = mini.get("publishEndTime").getFormValue();
		var publishMsgBatch = mini.get("publishMsgBatch").getValue();
		
		var para = {
			searchTimeType : newMsgTimeType,
			status : newMsgStatus,
			templateId : msgTemplateId,
			startTime : publishStartTime,
			endTime : publishEndTime,
			msgBatch : publishMsgBatch,
			msgType : publishMsgType
		};
		return para;
	},
	//打开发送新信息窗体
	"addNewMsgWin":function() {
		$("#look_upload").hide();
		Current.sendMsgWindow.set({
			title:"发送新信息"
		});
		
		mini.get("uploadUserfile").set({
			required:true
		});
		
		mini.get("wechatTemplate_add").set({
			enabled:false
		});
		var row = {
			action : "post",
			url : "operatemgr/msginfo/addNewMsg",
		}
		//$(".wechatTemplate_add").remove();
		$(".wechat_add_fields").remove();
		$(".sendAppExtrasTr").remove();
		mini.get("template_add").setData(Dic.template);// 消息类型下拉框初始化数据
		mini.get("status_add").setData(Dic.newMsgStatus);// 状态下拉框初始化数据
		mini.get("msgType_add").setData(Dic.msgType);//消息分类
		mini.get("wechatTemplate_add").setData(Dic.wechatTemplate);//微信公众号模板下拉
		mini.get("urlLottery").setData(Dic.lottery.concat(Dic.toPage));//彩种
		$("#uploadUserfile > input:file").val("");
		var form = new mini.Form("#editform");
		form.clear();
		form.setData(row);
		mini.get("status_add").setValue("0");
		mini.get("sendUsers").setValue("uploadUser");
//		var sendClient = ["mobType","siteType","appType","wechatType"];
		var sendClient = ["mobType","siteType","appType"];
		Current.setALLCheckBox(sendClient,true);
		Current.sendMsgWindow.show();
	},
	//编辑新信息
	"editNewMsgWin":function(){
		var selectRow = Current.grid.getSelected();
		if(selectRow){
			mini.get("template_add").setData(Dic.template);// 消息类型下拉框初始化数据
			mini.get("status_add").setData(Dic.newMsgStatus);// 状态下拉框初始化数据
			mini.get("msgType_add").setData(Dic.msgType);//消息分类
			mini.get("wechatTemplate_add").setData(Dic.wechatTemplate);// 微信公众号模板下拉框初始化数据
			mini.get("urlLottery").setData(Dic.lottery.concat(Dic.toPage));//彩种
			Current.sendMsgWindow.set({
				title:"编辑信息"
			});
			var param ={
	     	    url:"operatemgr/msginfo/newmsg/"+selectRow.id,
	     	    action:"GET",
	     	    data:""
	     	}
			Cms.ajax(param, function(row){
				row.url = "operatemgr/msginfo/updatenewmsg";
    	        var form = new mini.Form("#editform");
    	        form.clear();
    	        form.setData(row);
    	        Current.displaySendClient();
    	        Current.initTemplateInfo("edit",row.templateId);
    	        mini.get("uploadUserfile").set({
    				required:false
    			});
    	        $("#uploadUserfile > input:file").val("");
    	        Current.sendMsgWindow.show();
     	   	});
		}else{
			mini.alert("请选择一行数据");
		}
	},
	//保存发送新信息
	"doSubmit" : function() {
		Current.handleSendAppFields();
		Current.handleWechatFields();
		var h = "#uploadUserfile > input:file";
        Cms.uploadData(Current.sendMsgWindow,new mini.Form("#editform"),h,function(){
            Current.sendMsgWindow.hide();
			Cms.reload("publish-datagrid");
        });
	},
	// 处理APP附加字段值
	"handleSendAppFields":function(){
		var vals = ""; 
		$(".sendAppExtrasTr").each(function(){
			vals +=  ";" + $(this).find("input[name='appExtras']").val() + ":" + $(this).find("input[name='appExtrasName']").val()+ ":" + $(this).find("input[name='appExtrasValue']").val();
		});
		vals = vals.substring(1);
		$("#appFields").val(vals);
	},
	//处理微信附加字段值
	"handleWechatFields":function(){
		var wechatFields = ""; 
		$(".wechat_add_fields").each(function(index){
			wechatFields += ";" + $("#wechat_fields"+index).text() +":"+ mini.get("wechat_fields_value"+index).getValue();
		});
		wechatFields = wechatFields.substring(1);
		$("#wechatFields").val(wechatFields);
	},
	//选择发送类型处理
	"onValueChanged":function(id){
		var checked = mini.get(id).getChecked();
		if(checked){
			$("."+id).show();
		}else{
			$("."+id).hide();
		}
	},
	//根据发送端发送状态隐藏显示对应信息
	"displaySendClient":function(){
//		var sendClient = ["mobType","siteType","appType","wechatType"];
		var sendClient = ["mobType","siteType","appType"];
		for(var i=0;i<sendClient.length;i++){
			var id = sendClient[i];
			var checked = mini.get(id).getChecked();
			if(checked){
				$("."+id).show();
			}else{
				$("."+id).hide();
			}
		}
	},
	//解析展示微信公众号标题字段
	"handleWechatTile":function(title,color){
		$(".dynamicTr").remove();
		var titles = title.split(';');
	    var colors = color.split(';');
	    var len = titles.length;
	    if(len > 0){
	    	var trs = "";
	    	for(var i=0;i<len;i++){
	    		trs +=
		    	"<tr class='dynamicTr'><td style='width:15%;'>字段名称</td>" +
		    	"<td style='width:20%;'><input name='titles' required='true' class='mini-textbox' " +
		    	"value='"+titles[i]+"' style='width:100%'/></td><td style='width:13%;'>字体颜色</td>" +
		    	"<td><input name='colors' required='true' class='mini-textbox' value='"+colors[i] +"' " +
		    	"style='width:40%'/><a style='margin-left:2px' href='javascript:void(0)' " +
		    	"onclick='Current.delRow(this,'dynamicTr')'>删除</a><a style='margin-left:5px' " +
		    	"href='javascript:void(0)' onclick='Current.addWechatRow(this)'>增加</a></td></tr>";
	    	}
	    	$("#headColorAddRow").after(trs);
	    }
	},
	// 组装微信公众号设置模板信息
	"setTitleAndColor":function(){
		var ids = ["titles","colors"];
		for(var i=0;i<ids.length;i++){
			var vals = "";
			$("input[name='" + ids[i] + "']").each(function(){
				vals += ";" + $(this).val();
			});
			vals = vals.substring(1);
			$("#" + ids[i]).val(vals);
		}
	},
	
	//选择发送用户名单
	"selectUserType":function(userType){
		var type = userType.getValue();
		if(type == "uploadUser"){
			mini.get("uploadUserfile").set({
				required:true
			});
			$("#upload_div").show();
		}else{
			mini.get("uploadUserfile").set({
				required:false
			});
			$("#upload_div").hide();
		}
	},
	//选择模板
	"selectTemplate":function(){
		var template = mini.get("template_add");
		var templateId = template.getValue();
		if(template.getText().indexOf("手动") >= 0){
			Current.initTemplateInfo("select",templateId);
			$("#templateName").val(template.getText());
		}else{
			template.setValue(null);
			mini.alert("只支持手动模板消息发送！");
		}
	},
	// 编辑信息界面初始化模板数据
	"initTemplateInfo":function(type,templateId){
		var param ={
     	    	url:"operatemgr/msginfo/templatedetail/"+templateId,
     	    	action:"GET",
     	    	data:"",
     	}
		Cms.ajax(param, function(row){
			$(".wechat_add_fields").remove();
			$(".sendAppExtrasTr").remove();
			if("select" == type){
				//标题和内容初始化
				var form = new mini.Form("#templateContent");
		        form.setData(row);
		        mini.get("activityUrl").setValue(row.activityUrl);
		        mini.get("urlLottery").setValue(row.toLotteryCode);
		        //发送端
		        var statusForm = new mini.Form("#clientSendStatus");
		        statusForm.setData(row);
		        Current.displaySendClient();
//		        if(typeof(row.wechatAddFields) != "undefined" && row.wechatAddFields != "" ){
//		        	var wechatAddFields = row.wechatAddFields.split(';');
//			        var len = wechatAddFields.length;
//			        if(len > 0){
//			        	var trs = "";
//			        	for(var i=0;i<len;i++){
//			        		var cols = wechatAddFields[i];
//			        		trs += "<tr class='wechat_add_fields'><td style='width:15px;' " +
//	        				"id='wechat_fields"+i+"'>"+cols+"</td><td style='width:150px'>" +
//	        				"<input id='wechat_fields_value"+i+"' name='wechat_fields' class='mini-textbox' " +
//	        				"style='width:100%;height:100%;'/></td></tr>";
//			        	}
//			        	$("#tr_after_add").after(trs);
//			        }
//		        }
		        
		        if(typeof(row.appAddFields) != "undefined" && row.appAddFields != ""){
		        	var appAddFields = row.appAddFields.split(';');
		        	var len = appAddFields.length;
	    	        if(len > 0){
	    	        	var trs = "";
	    	        	for(var i=0;i<len;i++){
	    	        		var fields = appAddFields[i].split(":");
	    	        		trs += "<tr class='sendAppExtrasTr'><td style='width:15%;'>附加字段</td><td style='width:150px'>" +
	    	    			"<input name='appExtras' class='mini-textbox' style='width:20%' value='"+fields[0]+"' allowInput='false'/>&nbsp;&nbsp;" +
	    	    					"<input name='appExtrasName' class='mini-textbox' style='width:20%' value='"+fields[1]+"' allowInput='false'/>&nbsp;&nbsp;" +
	    	    						"<input name='appExtrasValue' class='mini-textbox' style='width:28%' emptyText='请输入字段值'/>&nbsp;&nbsp;" +
	    	    											"</td></tr>";
	    	        	}
	    	        	$("#appTitleTr").after(trs);	
	    	        }
		        }
			}else{
//				var wechatFields = $("#wechatFields").val();
//				if(typeof(wechatFields) != "undefined" && wechatFields != ""){
//		        	var wechatAddFields = wechatFields.split(';');
//			        var len = wechatAddFields.length;
//			        if(len > 0){
//			        	var trs = "";
//			        	for(var i=0;i<len;i++){
//			        		var cols = wechatAddFields[i].split(":");
//			        		trs += "<tr class='wechat_add_fields'><td style='width:15px;' " +
//	        				"id='wechat_fields"+i+"'>"+cols[0]+"</td><td style='width:150px'>" +
//	        				"<input id='wechat_fields_value"+i+"' name='wechat_fields' class='mini-textbox' " +
//	        				"style='width:100%;height:100%;' value='"+cols[1]+"'/></td></tr>";
//			        	}
//			        	$("#tr_after_add").after(trs);
//			        }
//		        }
		        
				var appFields = $("#appFields").val();
		        if(typeof(appFields) != "undefined" && appFields != ""){
		        	var appAddFields = appFields.split(';');
		        	var len = appAddFields.length;
	    	        if(len > 0){
	    	        	var trs = "";
	    	        	for(var i=0;i<len;i++){
	    	        		var fields = appAddFields[i].split(":");
	    	        		trs += "<tr class='sendAppExtrasTr'><td style='width:15%;'>附加字段</td><td style='width:150px'>" +
	    	    			"<input name='appExtras' class='mini-textbox' style='width:20%' value='"+fields[0]+"' allowInput='false'/>&nbsp;&nbsp;" +
	    	    					"<input name='appExtrasName' class='mini-textbox' style='width:20%' value='"+fields[1]+"' allowInput='false'/>&nbsp;&nbsp;" +
	    	    						"<input name='appExtrasValue' class='mini-textbox' style='width:28%' value='"+fields[2]+"'/>&nbsp;&nbsp;" +
	    	    											"</td></tr>";
	    	        	}
	    	        	$("#appTitleTr").after(trs);	
	    	        }
		        }
			}
    	    mini.parse();
		});
	},
	
	setALLCheckBox:function(ids,check){
		for(var i=0;i<ids.length;i++){
			var id = ids[i];
			var t = mini.get(id);
			t.setChecked(check);
			$("." + id).show();
		}
	},
	findTemplateListByType:function(selectId,setId){
		var msgTypeSelect = mini.get(selectId);
		var msgType = msgTypeSelect.getValue();
		if(msgType == "5"){
			
		}else{
			msgTypeSelect.setValue("5");
			mini.alert("只支持手动模板消息发送！");
		}
		
		//初始化模板下拉选项列表（其他分类）
		ComReq.msgTemplate("10035",function(result){
			mini.get(setId).setData(result);
		});
		
	},
	//更新消息对象
	updateMsg:function(vid,vmsgBatch,vstatus){
		this.id = vid;
		this.msgBatch = vmsgBatch;
		this.status=vstatus;
	},
	cancelNewMsg:function(){
		var rows = Current.grid.getSelecteds();
		var len = rows.length;
		if (rows.length > 0) {
			var list = new Array();
			for(var i=0;i<len;i++){
				list[i]=new Current.updateMsg(rows[i].id,rows[i].msgBatch,3);
			}
			var json = mini.encode(list);
	        Cms.save(json, "operatemgr/msginfo/cancelSendMsg", function(){
	            Cms.reload("publish-datagrid");
	        });
		}
	}
}
Current.init();