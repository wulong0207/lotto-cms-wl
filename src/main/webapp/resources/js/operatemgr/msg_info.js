var Current = {
	"init" : function() {
		mini.parse();
		Current.grid = mini.get("datagrid");//通知信息列表

		//通知信息列表查询条件
		Current.searchUserType = mini.get("searchUserType");//选择用户条件
		Current.searchTimeType = mini.get("searchTimeType");//选择时间条件
		Current.sendType = mini.get("sendType");//发送类型
		Current.status = mini.get("status");//消息状态
		Current.templateId = mini.get("templateId");//消息类型
		Current.msgType = mini.get("msgType");//消息分类
		
		//初始化查询条件
		ComReq.dictionary("1201,1701,1702,1703,1704,1705,1706,1707,2101", function(result) {
			Dic.searchUserType = result["1701"];//用户类型
			Dic.searchTimeType = result["1702"];//时间类型
			Dic.sendType = result["1703"];//发送方式
			Dic.status = result["1704"];//通知消息状态
			Dic.msgType = result["2101"];//消息分类
			
			Current.searchUserType.setData(Dic.searchUserType);
			Current.searchTimeType.setData(Dic.searchTimeType);
			Current.sendType.setData(Dic.sendType);
			Current.status.setData(Dic.status);
			Current.msgType.setData(Dic.msgType);
			
			//Current.status.setValue("2");//默认查询发送失败信息
            Current.searchUserType.setValue(2);//默认“会员账户”的查询条件
			//初始化模板下拉选项列表
			ComReq.msgTemplate("1001",function(result1){
				Dic.template = result1;
				Current.templateId.setData(Dic.template);
				Current.search();
			});
		});
	},
	//通知信息查询
	"search" : function() {
		Current.grid.load(Current.getParamJson());
	},
	//通知信息查询条件参数
	"getParamJson":function(){
		var searchUserType = Current.searchUserType.getValue();
		var searchTimeType = Current.searchTimeType.getValue();
		var sendType = Current.sendType.getValue();
		var status = Current.status.getValue();
		var templateId = Current.templateId.getValue();
		var msgType = Current.msgType.getValue();
		
		var startTime = mini.get("startTime").getFormValue();
		var endTime = mini.get("endTime").getFormValue();
		var searchUserInfo = mini.get("searchUserInfo").getValue();
		var msgBatch = mini.get("msgBatch").getValue();
		
		var para = {
			searchUserType : searchUserType,
			searchTimeType : searchTimeType,
			sendType : sendType,
			status : status,
			templateId : templateId,
			startTime : startTime,
			endTime : endTime,
			searchUserInfo : searchUserInfo,
			msgBatch : msgBatch,
			msgType : msgType
		}
		return para;
	},
	//打开通知信息详情
	"onRowClick" : function() {
		var row = Current.grid.getSelected();
		if(row){
			//加载用户接收信息配置
			//mini.get("UserConfigGrid").load();
			var param ={
	     	    	url:"operatemgr/msginfo/detail/"+row.id,
	     	    	action:"GET",
	     	    	data:"",
	     	}
			Cms.ajax(param, function(row){
				//初始化
				mini.get("template_detail").setData(Dic.template);
				mini.get("msg_type_detail").setData(Dic.msgType);
				mini.get("msgSendType_detail").setData(Dic.sendType);
				mini.get("msgStatus_detail").setData(Dic.status);
		        var form = new mini.Form("#msgDetail_div");
		        form.clear();
		        form.setData(row);
		        //备注
		        $("#msgDesc").text(row.msgDesc?row.msgDesc:"");
		        //发送失败原因
		        $("#sendError").text(row.sendError?row.sendError:"");
		        //信息标题
		        $("#msgTitle").text(row.msgTitle?row.msgTitle:"");
		        //信息内容
		        $("#msgContent").text(row.msgContent?row.msgContent:"");
			});
			mini.get("msgDetailWindow").show();
		}
	},
	//更新消息对象
	"updateMsg":function(vid,vstatus){
		this.id = vid;
		this.status=vstatus;
	},
	//更新消息状态(取消发送和重新发送)
	"updateMsgStatus":function(setStatus){
		var rows = Current.grid.getSelecteds();
		var len = rows.length;
		if (rows.length > 0) {
			var list = new Array();
			for(var i=0;i<len;i++){
				list[i]=new Current.updateMsg(rows[i].id,setStatus);
			}
			var json = mini.encode(list);
	        Cms.save(json, "operatemgr/msginfo/updateMsgStatus", function(){
	            Cms.reload("datagrid");
	        });
		}
	}
}
Current.init();