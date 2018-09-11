var Current={
	init:function(){
		mini.parse();
		Current.datagrid = mini.get("datagrid");
		ComReq.dictionary("1901,1902,0805", function(result){
			 Dic.messageType = result["1901"];		
		     Dic.type = result["1902"];
		     Dic.sendStatus = result["0805"];
		     mini.get("sendStatus").setData(Dic.sendStatus);
		     Current.search();
		});
	},
	search:function(){
		var json={};
		json.userId=mini.get("userId").getValue();
		json.account=mini.get("account").getValue();
		json.beginTime=mini.get("beginTime").getValue();
		json.endTime=mini.get("endTime").getValue();
		json.status=mini.get("sendStatus").getValue();
		
		json = eval('(' + mini.encode(json,"yyyy-MM-dd HH:mm:ss") + ')');
		Current.datagrid.load(json);
	},
	reSend : function(e) {
		var grid = e.sender;
		var row = e.record;
		var html = '<a href="javascript:Current.sendMessage()" />重新发送</a>';
		return html;
	},
	sendMessage:function(){
		mini.confirm("确认重新发送？",
		        "提醒",
		        function(action) {
					if (action == "ok") {
						var currentRow = Current.datagrid.getSelected();
						var param = {
								message : currentRow["message"],
								account : currentRow["account"],
								id : currentRow["id"]
						};
						var params = {
								action : "post",
								url : "customerService/mUserMessage/sendMessage",
								data : param
						};
						Cms.ajax(params,function(data){
							Cms.reload("datagrid");
						});
					}
				});
	},
	/**
	 * @param e
	 * 渲染::消息内容：如果是邮件则直接显示验证码，保证列表显示的美观
	 */
	renderMsgContent:function(e) {
		var _record = e.record;
		// 发送类型1：短信 2：邮件（如果是邮件则仅显示验证码）
		if(_record.type && _record.type == 1) {
			return _record.message;
		} else {
			return "邮件验证码：" + _record.code;
		}
	}
};
Current.init();