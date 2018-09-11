var Current = {
	"init" : function() {
		mini.parse();
		Current.editTemplateWindow = mini.get("editTemplateWindow");//编辑模板信息窗体
		Current.editWechatSetWindow = mini.get("editWechatSetWindow");//设置微信公众号模板窗体
		Current.template = mini.get("selectTemplateId");
		
		//初始化查询条件
		ComReq.dictionary("1201,1705,2101,2104,2105,2106", function(result) {
			Dic.templateStatus = result["1201"];//模板状态
			Dic.typeNode = result["1705"];//执行节点
			Dic.msgType = result["2101"];//消息分类
			Dic.condition = result["2104"];//用户唤醒查询条件
			Dic.smsChannel = result["2105"];//短信发送渠道
			Dic.toPage = result["2106"];//跳转界面
			Current.search();
		});
		
		ComReq.lottery("",function(result){
			Dic.lottery = result;
			var lots = [];
			for(var i= 0;i<result.length;i++){
				var lotInfo = result[i];
				if((lotInfo.id >= 100 && lotInfo.id < 200) || (lotInfo.id >= 300 && lotInfo.id < 400)){
					lots.push(lotInfo);
				}
			}
			Dic.lfLottery = lots;
		});

		//初始化模板下拉选项列表
		ComReq.msgTemplate("1001",function(result){
			Dic.template = result;
			Current.template.setData(Dic.template);
		});
		
		//获取微信公众号设置模板下拉选项列表
		ComReq.msgTemplate("1002",function(result){
			Dic.wechatTemplate = result;
		});
		
		$("legend").click(function(){
			$(this).parent().children("div").toggle();
		});
	},
	"search":function(){
		var selectTemplateId = Current.template.getValue();
		//加载模板列表数据
		mini.get("templateGrid").load({id:selectTemplateId});
	},    
    "ManagerCoupon":function(){
    	var selectRow = mini.get("templateGrid").getSelected()
	    var height = 500,width= 1000;
	    mini.open({
	        url: "operatemgr/msginfo/ManagerCoupon",
	        title: "管理优惠券",
	        width: width, height: height,
	        onload: function () {
	            var iframe = this.getIFrameEl();
	            //模板编号
	            iframe.contentWindow.initData(selectRow.id);
	        },
	        ondestroy: function (action) {
	            Cms.reload("editTemplateWindow");
	        }
	    });
    },
	//修改微信公众号模板
	"editwechatset":function(){
		Current.editWechatSetWindow.set({
			title:"修改微信公众号模板"
		});
		var templateId = mini.get("wechatTemplate").getValue();
		if(templateId == ""){
			mini.alert("请选择微信公众号模板");
			return;
		}
		var param ={
     	    	url:"operatemgr/msginfo/wechattemplate/"+templateId,
     	    	action:"GET",
     	    	data:"",
     	}
		Cms.ajax(param, function(row){
			row.action = "put";
			row.url = "operatemgr/msginfo/updateWechatTemplate";
	        var form = new mini.Form("#editWechatSetform");
	        form.clear();
	        form.setData(row);
	        mini.get("wechatTemplateTitle").set({
				enabled:false
			});
	        Current.handleWechatTile(row.title,row.color);
	        mini.parse();
		});
		Current.editWechatSetWindow.show();
	},
	//新增微信公众号模板
	"addwechatset":function(){
		Current.editWechatSetWindow.set({
			title:"新增微信公众号模板"
		});
		var row ={
     	    url:"operatemgr/msginfo/addWechatTemplate",
     	    action:"post"
     	}
		mini.get("wechatTemplateTitle").set({
			enabled:true
		});
		var form = new mini.Form("editWechatSetform");
		form.clear();
		form.setData(row);
		Current.editWechatSetWindow.show();
	},
	//点击查看信息模板详情
	"onTemplaterowClick":function(){
		mini.get("editTemplateWin").doClick();
	},
	//保存模板信息
	"doTemplateSubmit" : function() {
		var isCheck = mini.get("templateSiteType").getChecked();
		if(isCheck){
			if($("#st").val()){
				Current.temInfoSubmit();
			}else{
				mini.alert("站内信标题不能为空!");
			}
		}else{
			Current.temInfoSubmit();
		}
	},
	"temInfoSubmit":function(){
		Current.handleAppFields();
		Current.getTitlesAndContents();
		Cms.submit(new mini.Form("#edittemplateform"), function() {
			Current.editTemplateWindow.hide();
			Cms.reload("templateGrid");
		});
	},
	//保存微信公众号模板信息
	"doWechatTemplateSubmit":function(){
		Current.setTitleAndColor();
		Cms.submit(new mini.Form("#editWechatSetform"), function() {
			Current.reloadTemplateData();
			Current.editWechatSetWindow.hide();
		});
	},
	//增加微信公众号TITLE字段
	"addWechatRow":function(curATag){
		var cloneTR = "<tr class='dynamicTr'><td style='width:15%;'>字段名称</td>" +
			"<td style='width:20%;'><input name='titles' class='mini-textbox' style='width:100%' required='true'/></td>" +
			"<td style='width:13%;'>字体颜色</td><td><input name='colors' class='mini-textbox' style='width:40%' required='true' />" +
			"<a style='margin-left:2px' href='javascript:void(0)' onclick='Current.delRow(this,\"dynamicTr\")'>删除</a>" +
			"<a style='margin-left:5px' href='javascript:void(0)' onclick='Current.addWechatRow(this)'>增加</a></td></tr>";
		$(curATag).parent().parent().after(cloneTR);
		mini.parse();
	},
	//删除微信公众号TITLE字段
	"delRow":function(curATag,className){
		if($("."+className).length <= 1){
			mini.confirm("你确定要删除吗","提示",function(action){
				if(action == "ok"){
					$(curATag).parent().parent().remove();	
				}
			});
		}
		else{
			$(curATag).parent().parent().remove();
		}
	},
	//增加APP模板字段
	"addAppTemRow":function(curATag){
		var cloneTR = "<tr class='appExtrasTr'><td style='width:15%;'>附加字段</td><td style='width:150px'>" +
		"<input name='appExtras' class='mini-textbox' style='width:25%' emptyText='请输入字段' required='true'/>&nbsp;&nbsp;" +
					"<input name='appExtrasName' class='mini-textbox' style='width:28%' emptyText='请输入字段名称' required='true'/>&nbsp;&nbsp;" +
										"<a href='javascript:void(0)' onclick='Current.delRow(this,\"appExtrasTr\")'>删除</a>" +
												"<a style='margin-left:5px' href='javascript:void(0)' onclick='Current.addAppTemRow(this)'>增加</a></td></tr>";
		$(curATag).parent().parent().after(cloneTR);
		mini.parse();
	},
	// 处理APP附加字段
	"handleAppFields":function(){
		var vals = ""; 
		$(".appExtrasTr").each(function(){
			vals +=  ";" + $(this).find("input[name='appExtras']").val() + ":" + $(this).find("input[name='appExtrasName']").val();
		});
		vals = vals.substring(1);
		$("#appAddFields").val(vals);
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
	//打开模板管理窗体
	"openTemplateWin":function(){
		Current.template.setData(Dic.template);
		//加载模板列表数据
		mini.get("templateGrid").load({});
		mini.get("templateWindow").show();
	},
	//打开新增模板窗体
	"addtemplatewin":function(){
		Current.editTemplateWindow.set({
			title:"新增通知信息模板"
		});
		// 设置模板编号不能修改
		mini.get("typeId").set({
			enabled:true
		});
		Current.initSelect();
		$("#userAwakenConditionSet").hide();
		var form = new mini.Form("#edittemplateform");
		var row = {
			action : "post",
			url : "operatemgr/msginfo/addTemplate",
			noSendChannel : ""
		}
        form.clear();
        form.setData(row);
        Current.setEmptyVal();
		Current.initAddTemplateWin();
		Current.editTemplateWindow.show();
	},
	"initSelect":function(){
		mini.get("templateMsgType").setData(Dic.msgType);//消息分类下拉框
		mini.get("templateTypeNode").setData(Dic.typeNode);//执行节点下拉框
		mini.get("templateStatus").setData(Dic.templateStatus);//模板状态
		mini.get("wechatTemplate").setData(Dic.wechatTemplate);//微信公众号模板下拉
		mini.get("limitLottery").setData(Dic.lottery);//彩种
		mini.get("sendLottery").setData(Dic.lfLottery);//彩种
		mini.get("urlLottery").setData(Dic.lottery.concat(Dic.toPage));//彩种
		mini.get("condition").setData(Dic.condition);//用户唤醒查询条件
		mini.get("smsSendChannel").setData(Dic.smsChannel);// 短信发送渠道
	},
	//打开编辑模板窗体
	"edittemplatewin":function(){
		var selectRow = mini.get("templateGrid").getSelected();
		if(selectRow){
			Current.initSelect();
			Current.editTemplateWindow.set({
				title:"编辑通知信息模板"
			});
//			// 设置微信公众号模板不能修改
//			mini.get("wechatTemplate").set({
//				enabled:false
//			});
			// 设置模板编号不能修改
			mini.get("typeId").set({
				enabled:false
			});
			var param ={
	     	    url:"operatemgr/msginfo/templatedetail/"+selectRow.id,
	     	    action:"GET",
	     	    data:""
	     	}
			Cms.ajax(param, function(row){
				// 初始化界面，移除微信公众号TITLE行
				$(".wechatTemplate_title").remove();
				$(".appExtrasTr").remove();
				row.action = "put";
				row.url = "operatemgr/msginfo/updateTemplate";
    	        var form = new mini.Form("#edittemplateform");
    	        form.clear();
    	        form.setData(row);
    	        Current.setTitlesAndContents(row);
    	        Current.userAwakenConditionSet();
//    	        var wechatAddFields = row.wechatAddFields;
//    	        if(typeof(wechatAddFields) != "undefined" && wechatAddFields != ""){
//    	        	wechatAddFields = wechatAddFields.split(';');
//    	        	var len = wechatAddFields.length;
//        	        if(len > 0){
//        	        	var trs = "";
//        	        	for(var i=0;i<len;i++){
//        	        		var fields = wechatAddFields[i];
//        	        		trs += "<tr class='wechatTemplate_title'><td style='width:15px;' id='td_name"+i+"'>"+
//        	        		fields +"</td><td style='width:150px'>&nbsp;</td></tr>";
//        	        	}
//        	        	$("#afterAdd").after(trs);
//        	        }
//    	        }
    	        
    	        var appAddFields = row.appAddFields;
    	        if(typeof(appAddFields) != "undefined" && appAddFields != ""){
    	        	appAddFields = appAddFields.split(';');
    	        	var len = appAddFields.length;
        	        if(len > 0){
        	        	var trs = "";
        	        	for(var i=0;i<len;i++){
        	        		var fields = appAddFields[i].split(":");
        	        		trs += "<tr class='appExtrasTr'><td style='width:15%;'>附加字段</td><td style='width:150px'>" +
        	    			"<input name='appExtras' class='mini-textbox' style='width:25%' value='"+fields[0]+"'/>&nbsp;&nbsp;" +
        	    						"<input name='appExtrasName' class='mini-textbox' style='width:25%' value='"+fields[1]+"'/>&nbsp;&nbsp;" +
        	    											"<a href='javascript:void(0)' onclick='Current.delRow(this,\"appExtrasTr\")'>删除</a>" +
        	    													"<a style='margin-left:5px' href='javascript:void(0)' onclick='Current.addAppTemRow(this)'>增加</a></td></tr>";
        	        	}
        	        	$("#temAppTitleTr").after(trs);	
        	        }
    	        }
    	        Current.displaySendClient();
    	        mini.parse();
    	        Current.editTemplateWindow.show();
     	   	});
		}else{
			mini.alert("请选择一行数据");
		}
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
//		var sendClient = ["templateMobType","templateSiteType","templateAppType","templateWechatType"];
		var sendClient = ["templateMobType","templateSiteType","templateAppType"];
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
	"userAwakenConditionSet":function(){
		var msgType = mini.get("templateMsgType").getValue();
		if(msgType == '8'){
			$("#userAwakenConditionSet").show();
			Current.conditionLimit();
		}else{
			$("#userAwakenConditionSet").hide();
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
	//修改后重新加载微信公众号模板信息
	"reloadTemplateData":function(){
		//获取微信公众号模板ID
		var wechatId = $("#editWechatSetform input[name='id']").val();
		//获取当前选中微信公众号模板ID
		var templateId = mini.get("wechatTemplate").getValue();

		if(!isNaN(wechatId) && wechatId != ""){
			if(wechatId == templateId){
				//更新模板微信公众号TITLE数据
				Current.updateWechat();
			}
		}
		else{
			//初始化微信公众号设置模板下拉选项列表
			ComReq.msgTemplate("1002",function(result){
				Dic.wechatTemplate = result;
				mini.get("wechatTemplate").setData(Dic.wechatTemplate);
			});
		}
	},
	//选择微信公众号模板处理
	"updWechatTitle":function(){
		var id = mini.get("wechatTemplate").getValue();
		var param ={
     	    	url:"operatemgr/msginfo/wechattemplate/"+id,
     	    	action:"GET",
     	    	data:"",
     	}
		Cms.ajax(param, function(row){
			var titles = row.title.split(';');
	        var len = titles.length;
	        if(len > 0){
	        	var trs = "";
	        	$(".wechatTemplate_title").remove();
	        	for(var i=0;i<len;i++){
	        		trs += "<tr class='wechatTemplate_title'><td style='width:15px;' " +
	        			   "id='td_name"+i+"'>"+titles[i]+"</td><td style='width:150px'>" +
	        			   "<input id='wechat_title"+i+"' name='wechat_title' class='mini-textbox' " +
	        			   "style='width:100%;height:100%;'/></td></tr>";
	        	}
	        	$("#afterAdd").after(trs);
	        	mini.parse();
	        }
		});
	},
	// 模板窗体初始化
	"initAddTemplateWin":function(){
		mini.get("wechatTemplate").set({
			enabled:true,
		});
//		var sendClient = ["templateMobType","templateSiteType","templateAppType","templateWechatType"];
		var sendClient = ["templateMobType","templateSiteType","templateAppType"];
		Current.setALLCheckBox(sendClient,true);
		// 模板状态
		mini.get("templateStatus").setValue("1");
		mini.get("smsSendChannel").setValue("2");//短信发送渠道设置默认值为营销渠道
		$(".appExtrasTr").remove();
//		$(".wechatTemplate_title").remove();
	},
	//更新模板微信公众号TITLE数据
	"updateWechat":function(){
		// 更新模板TITLE
		var title = $("#titles").val();
		var titles = title.split(';');
        var len = $(".wechatTemplate_title").length;
        var tlen = titles.length;
        if(len > 0 && tlen >= len){
        	// 修改字段增多
        	var i;
        	for(i=0;i<len;i++){
        		$("#td_name"+i).text(titles[i]);
        	}
        	var trs = "";
        	var index = len-1;//添加位置
        	for(i=len;i<tlen;i++){
        		trs += "<tr class='wechatTemplate_title'><td style='width:15px;' " +
        				"id='td_name"+i+"'>"+titles[i]+"</td><td style='width:150px'>&nbsp;</td></tr>";
        	}
        	$("#td_name"+index).parent().after(trs);
        }
        else{
        	var i;
        	for(i=0;i<tlen;i++){
        		$("#td_name"+i).text(titles[i]);
        	}
        	
        	for(i=tlen;i<len;i++){
        		$("#td_name"+i).parent().remove();
        	}
        }
    	mini.parse();
	},
	setALLCheckBox:function(ids,check){
		for(var i=0;i<ids.length;i++){
			var id = ids[i];
			var t = mini.get(id);
			t.setChecked(check);
			$("." + id).show();
		}
	},
	setTitlesAndContents:function(row){
		$("#mt").val(row.mobTitle);
		$("#mc").val(row.mobContent);
		$("#at").val(row.appTitle);
		$("#ac").val(row.appContent);
		$("#st").val(row.siteTitle);
		$("#sc").val(row.siteContent);
//		$("#wt").val(row.wechatTitle);
//		$("#hc").val(row.headerCon);
//		$("#fc").val(row.footerCon);
	},
	getTitlesAndContents:function(){
		$("#mobTitle").val($("#mt").val());
		$("#mobContent").val($("#mc").val());
		$("#appTitle").val($("#at").val());
		$("#appContent").val($("#ac").val());
		$("#siteTitle").val($("#st").val());
		$("#siteContent").val($("#sc").val());
//		$("#wechatTitle").val($("#wt").val());
//		$("#headerCon").val($("#hc").val());
//		$("#footerCon").val($("#fc").val());
	},
	setEmptyVal:function(){
		$("#mt").val("");
		$("#mc").val("");
		$("#at").val("");
		$("#ac").val("");
		$("#st").val("");
		$("#sc").val("");
//		$("#wt").val("");
//		$("#hc").val("");
//		$("#fc").val("");
	},
	"conditionLimit":function(){
		// 条件分类
		var d1 = ["1","2","3","4","5","6"];
		var d2 = ["7"];
		var ld2 = ["8","9"];
		var bld = ["10"];
		var mld2 = ["11","12"];
		var md2 = ["13"];
		var conditionKey = mini.get("condition").getValue();
		if($.inArray(conditionKey,d1) > -1){
			mini.get("startDays").enable();
			mini.get("endDays").disable();
			mini.get("limitLottery").disable();
			mini.get("setMoney").disable();
			mini.get("setBalance").disable();
		}else if($.inArray(conditionKey,d2) > -1){
			mini.get("startDays").enable();
			mini.get("endDays").enable();
			mini.get("limitLottery").disable();
			mini.get("setMoney").disable();
			mini.get("setBalance").disable();
		}
		else if($.inArray(conditionKey,ld2) > -1){
			mini.get("startDays").enable();
			mini.get("endDays").enable();
			mini.get("limitLottery").enable();
			mini.get("setMoney").disable();
			mini.get("setBalance").disable();
		}else if($.inArray(conditionKey,bld) > -1){
			mini.get("startDays").enable();
			mini.get("endDays").disable();
			mini.get("limitLottery").enable();
			mini.get("setMoney").disable();
			mini.get("setBalance").enable();
		}else if($.inArray(conditionKey,mld2) > -1){
			mini.get("startDays").enable();
			mini.get("endDays").enable();
			mini.get("limitLottery").enable();
			mini.get("setMoney").enable();
			mini.get("setBalance").disable();
		}else if($.inArray(conditionKey,md2) > -1){
			mini.get("startDays").enable();
			mini.get("endDays").enable();
			mini.get("limitLottery").disable();
			mini.get("setMoney").enable();
			mini.get("setBalance").disable();
		}
	},
	onNoReplaceTypeId:function(e){
		// 验证模板编号不能重复
		var id = mini.get("id").getValue();
		if (e.isValid && id == "") {
			$.ajax({
				url : "operatemgr/msginfo/template/" + e.value,
				data : "",
				type : "GET",
				async:false, 
				success : function(res) {
					if((typeof res=='string')&&res.constructor==String)
						res = mini.decode(res);
					if(res.errorCode == Code.success){
						var row = res.data;
						if(row.typeId == e.value){
					    	  e.errorText = "模板编号不能重复,请重新输入";
				              e.isValid = false;
					    }
					}else{
						mini.alert(res.message); 
					}
					MiniCom.unmask();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					MiniCom.unmask();
					mini.alert(jqXHR.responseText);
				}
			});
	    }
	},
	selectValueText:function(id,text){
		this.id=id;
		this.text=text;
	}
}
Current.init();

var curInputId;
$(document).ready(function () {
    $("#template_contents input,#template_contents textarea").focus(function () {
    	curInputId = $(this).attr("id");
    });
    
    $(".readbtns").click(function () {
        $("#" + curInputId).insertAtCaret("\$\{" + $(this).val() + "\}\$");
    });

});