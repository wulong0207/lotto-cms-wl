var Current = {
	"init" : function() {
		mini.parse();
		Dic.reciveStatus=[{"id":0,"text":"禁用"},{"id":1,"text":"启用"}];
		Current.userSettingWindow=mini.get("userSettingWindow");
		ComReq.lottery("",function(result){
			Dic.lotterys = result;
		});
		//Current.search();
	},
	search:function(){
		var json={};
		json.accountName= mini.get("accountName").getValue();
		json.cusMobile=mini.get("cusMobile").getValue();
		json.nickName=mini.get("nickName").getValue();
		mini.get("userMsgConfigGrid").load(json);
	},
	edit:function(){
		Dic.reciveStatus=[{"id":0,"text":"禁用"},{"id":1,"text":"启用"}];
		var selectRow = mini.get("userMsgConfigGrid").getSelected();
		if(selectRow){
			//用户配置信息加载
			mini.get("userSettingGrid").load({"userId":selectRow.id});
			mini.get("openCodeReceiveSetGrid").load({"userId":selectRow.id,"type":1});
			mini.get("buyLotRemindSetGrid").load({"userId":selectRow.id,"type":2});
			Current.userSettingWindow.show();
			if(selectRow.msgMob==1){
				$("#msgMob").attr("checked",true);
			}else{
				$("#msgMob").attr("checked",false);
			}
			if(selectRow.msgSite==1){
				$("#msgSite").attr("checked",true);
			}else{
				$("#msgSite").attr("checked",false);
			}
			if(selectRow.msgApp==1){
				$("#msgApp").attr("checked",true);
			}else{
				$("#msgApp").attr("checked",false);
			}
//			if(selectRow.msgWechat==1){
//				$("#msgWechat").attr("checked",true);
//			}

			mini.get("mobNotDisturb").setValue(selectRow.mobNotDisturb);
			mini.get("appNotDisturb").setValue(selectRow.appNotDisturb);
			mini.get("userId").setValue(selectRow.id);
				
		}
	},
	saveUserConfig:function(){
		 var array=mini.get("userSettingGrid").data;
		 
		 for(var i=0;i<array.length;i++){
			 if(array[i].mob==1&&array[i].site==1&&array[i].app==1){
				 array.splice(i,1);
			 }
		 }
		 
		 var json={};
		 json.userId=mini.get("userId").getValue();
		 
		 var oc = mini.get("openCodeReceiveSetGrid").data;
		 var bl = mini.get("buyLotRemindSetGrid").data;
		 
		 var ulmc = new Array();
		 for(var i=0;i<oc.length;i++){
			var ob = oc[i];
			ob.type=1;
			ob.userId=json.userId;
			delete ob._id;
			ulmc.push(ob);
		 }
		 
		 for(var i=0;i<bl.length;i++){
			 var ob = bl[i];
			 ob.type=2;
			 ob.userId=json.userId;
			 delete ob._id;
			 ulmc.push(ob);
		 }
		 
		 if ($('#msgMob').attr('checked')) {
			 json.msgMob=1;
		 }else{
			 json.msgMob=0;
		 }
		 
		 if ($('#msgSite').attr('checked')) {
			 json.msgSite=1;
		 }else{
			 json.msgSite=0;
		 }
		 
		 if ($('#msgApp').attr('checked')) {
			 json.msgApp=1;
		 }else{
			 json.msgApp=0;
		 }
		 
//		 if ($('#msgWechat').attr('checked')) {
//			 json.msgWechat=1;
//		 }else{
//			 json.msgWechat=0;
//		 }
		 
		 json.mobNotDisturb=mini.get("mobNotDisturb").getValue();
		 json.appNotDisturb=mini.get("appNotDisturb").getValue();
		 
		 json.list=array;
		 json.lotMsgConfigList=ulmc;
		 
		var param={};
		param.url="operatemgr/msginfo/updateUserConfig";
		param.data={"str":JSON.stringify(json)};
		param.action="post";

		Cms.ajax(param, function(row){
			Current.userSettingWindow.hide();
			//重新加载更新列表
			mini.get("userMsgConfigGrid").reload();
 	   	});

	},
	onTimeValidation:function(e){
		if (e.isValid) {
            var pattern =  /^(\d{2}):(\d{2})-(\d{2}):(\d{2})$/;
            if (e.value.length != 11 || pattern.test(e.value) == false) {
                e.errorText = "格式不正确，请重新输入。例如：23:00-09:00";
                e.isValid = false;
            }
        }
	},
	// 接收全选设置
	onColsAllSelect:function(gridId,id){
		var val = $("#"+id).attr('checked')?1:0;
		var grid = mini.get(gridId);
		var rows = grid.getData();
		for(var i=0,l=rows.length;i<l;i++){
		   var row = rows[i];
		   grid.updateRow(row,{app:val});
		}
	},
	setALLCheckBox:function(ids,check){
		for(var i=0;i<ids.length;i++){
			var id = ids[i];
			var t = mini.get(id);
			t.setChecked(check);
		}
	}
}
Current.init();