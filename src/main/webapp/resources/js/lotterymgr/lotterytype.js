    mini.parse();
    
	var grid = mini.get("datagrid");
	var editWindow = mini.get("editWindow");
	var zb_grid = mini.get("zb_grid");
	var child_grid = mini.get("child_grid");
	var lotteryType = mini.get("lotteryType");
	var lotteryCode= mini.get("lotteryCode");
	var sailDayCycle_grid = mini.get("sailDayCycle_grid");
	var vacation_grid = mini.get("vacation_grid");
	var platform = mini.get("platform");
	
	//加载字典初始化
	ComReq.dictionary("0303,0307,0302,0306,0304,0305,0301,1105", function(result){
		    Dic.lotteryCategory = result["0303"];
	        Dic.yesNo = result["0307"];
	        Dic.adminCategory = result["0302"];
	        Dic.autoType = result["0306"];
	        Dic.saleStatus = result["0304"];
	        Dic.area = result["0305"];
	        Dic.stopAddIssue = result["0301"];
	        Dic.platform = result["1105"];
	    	
			lotteryType.setData(Dic.lotteryCategory);
	    	mini.get("adminCategory").setData( Dic.adminCategory);
	    	mini.get("lotteryCategory").setData( Dic.lotteryCategory);
	    	mini.get("autoType").setData( Dic.autoType);
	    	mini.get("saleStatus").setData( Dic.saleStatus);
	    	mini.get("synIssue").setData( Dic.yesNo);
	    	mini.get("area").setData( Dic.area);
	    	mini.get("stopAddIssue").setData( Dic.stopAddIssue);
	    	platform.setData(Dic.platform);
	    	// 子玩法销售状态
	    	Dic.childSaleStatus = [];
	    	for(var i = 0; i < Dic.saleStatus.length; i++) {
	    		if(Dic.saleStatus[i].id <= 1){
	    			Dic.childSaleStatus.push(Dic.saleStatus[i]);
	    		}
	    	}
	    	search();
		
	});
	
	ComReq.lottery("", function(result){
		lotteryCode.setData(result);
	});
	
	function lotteryTypeChange(){
	   var code = lotteryType.getValue();
	   if(code){
		   ComReq.lottery(code, function(result){
				lotteryCode.setData(result);
		   });
	   }
	}
	
	function onCloseClickType(e){
	   var obj = e.sender;
	   obj.setText("");
	   obj.setValue("");
	   ComReq.lottery("", function(result){
			lotteryCode.setData(result);
	   });
	}
	
	function search(){
		grid.load(getParamJson());
	}
	
	function getParamJson(){
		var code = mini.get("code").getValue();
	    var type = mini.get("lotteryType").getValue();
	    var lotteryCode = mini.get("lotteryCode").getValue();
	    if(!lotteryCode){
	       lotteryCode = code;
	    }
		var para ={
				lotteryCategory:type,
				lotteryCode:lotteryCode
			};
	    return para;
	}
	function addLotteryType(){
		mini.get("editCode").set({
			allowInput:true,
			required:true,
			enabled:true,
		});
		mini.get("editCodeName").set({
			allowInput:true,
			required:true,
			enabled:true,
		});
		editWindow.set({
			height:"400px",
			title:"添加彩种"
		});
	    editWindow.show();
	    $("#secondWindow").hide();
	    
	    var row = {
    	    action:"post",
    	   	url:"lotterymgr/type"
    	}
    	var form = new mini.Form("#editform");
    	form.clear();
	    form.setData(row);
	    
	    $("#img").attr("src","");
	    $("#imgMobile").attr("src","");
	    $("#typeName").html("");
	    $("#sst").show();
    	$("#sst_").show();
    	$("#est").show();
    	$("#est_").show();
    	$("#sdc").show();
    	$("#sdc_").show();
	}
	function edit(){
	    var row = grid.getSelected();
	    if (row) {
	    	mini.get("editCode").set({
				allowInput:false,
				required:false,
				enabled:false,
			});
			mini.get("editCodeName").set({
				allowInput:false,
				required:false,
				enabled:false,
			});
			editWindow.set({
				height:"800px",
				title:"修改彩种"
			});
		    editWindow.show();
		    $("#secondWindow").show();
		    
		    
	    	row.action = "put";
	        row.url="lotterymgr/type";
	        var form = new mini.Form("#editform");
	        form.clear();
	        form.setData(row);
	        $("#typeName").html(row.lotteryName);
	        $("#createBy").html(row.createBy);
	        $("#modifyBy").html(row.modifyBy);
	        $("#img").attr("src",row.lotteryLogoUrl);
	        $("#imgMobile").attr("src",row.lotteryLogoMobile);
	        mini.get('lotteryLogoUrl').setValue("");
	        mini.get('lotteryLogoMobile').setValue("");
	        searchBetting(row.lotteryCode);
	        searchChild(row.lotteryCode);
	        
	        if(row.lotteryCategory==1){
	        	$("#sst").show();
	        	$("#sst_").show();
	        	$("#est").show();
	        	$("#est_").show();
	        	$("#sdc").hide();
	        	$("#sdc_").hide();
	        	$("#dt").show()
	        	$("#dt_").show()
	        }else if(row.lotteryCategory==2){
	        	$("#sst").show();
	        	$("#sst_").show();
	        	$("#est").show();
	        	$("#est_").show();
	        	$("#sdc").show();
	        	$("#sdc_").show();
	        	$("#dt").hide();
	        	$("#dt_").hide();
	        }else{
	        	$("#sst").show();
	        	$("#sst_").show();
	        	$("#est").show();
	        	$("#est_").show();
	        	$("#sdc").hide();
	        	$("#sdc_").hide();
	        	$("#dt").hide();
	        	$("#dt_").hide();
	        }
	        onChangeSaleStatus();
	    } else {
	        mini.alert("请选择一行数据");
	    }
	}
	function onrowdblclick(){
	    mini.get("edit").doClick();
	}
	
	function searchBetting(code){
	    var para ={
				code:code
			};
		zb_grid.load(para);
	}
	function searchChild(code){
	    var para ={
				code:code
			};
		child_grid.load(para);
	}
	
	function onActionRenderer(e) {
            var gridAdd = e.sender;
            var record = e.record;
            var uid = record._uid;
            var s="";
            var id = record.id;
            if(id && gridAdd.isEditingRow(record)){
                s = '<a class="mini-button"  plain="true" href="javascript:Cms.cancelEditRow(\'zb_grid\',\'' + uid + '\')">取消</a>'
            }else if (gridAdd.isEditingRow(record)) {
                s = '<a class="mini-button"  plain="true" href="javascript:Cms.delEditRow(\'zb_grid\',\'' + uid + '\')">取消</a>'
            }
            return s;
     }
     
     function saveBetting(){
        Cms.formValidate("#zb_grid","保存数据有误",function(){
	        var typeCode = grid.getSelected().lotteryCode;
	        zb_grid.commitEdit();
	        var rows = zb_grid.getChanges();
	        if(rows.length == 0){
	           mini.alert("请先修改表格数据，再保存");
	           return;
	        } 
	        for(i=0; i<rows.length ;i++){
	            rows[i].lotteryCode = typeCode;
	        }
	        var json = mini.encode(rows);
	        Cms.save(json, "lotterymgr/type/betting", function(){
	            Cms.reload("zb_grid");
	        });
        });
     }
     
     function onActionRendererChild(e) {
            var gridAdd = e.sender;
            var record = e.record;
            var uid = record._uid;
            var s="";
            var id = record.id;
            if(id && gridAdd.isEditingRow(record)){
                s = '<a class="mini-button"  plain="true" href="javascript:Cms.cancelEditRow(\'child_grid\',\'' + uid + '\')">取消</a>'
            }else if (gridAdd.isEditingRow(record)) {
                s = '<a class="mini-button"  plain="true" href="javascript:Cms.delEditRow(\'child_grid\',\'' + uid + '\')">取消</a>'
            }
            return s;
     }
     
     function saveChild(){
        Cms.formValidate("#child_grid","保存数据有误",function(){
	        var typeCode = grid.getSelected().lotteryCode;
	        child_grid.commitEdit();
	        var rows = child_grid.getChanges();
	        if(rows.length == 0){
	           mini.alert("请先修改表格数据，再保存");
	           return;
	        } 
	        for(i=0; i<rows.length ;i++){
	            rows[i].lotteryCode = typeCode;
	        }
	        var json = mini.encode(rows);
	        Cms.save(json, "lotterymgr/type/child", function(){
	            Cms.reload("child_grid");
	        });
        });
     }
     function doSubmit(){
    	var saleStatusValue = mini.get("saleStatus").getValue();
     	if(saleStatusValue == 0) {// 暂停销售
     		if(platform.getValue() == ""){
     			mini.alert("暂停销售平台不能为空");
     			return false;
     		}
     	} else {
     		platform.setValue("");
     	}
        Cms.submit(new mini.Form("#editform"),function(){
	        editWindow.hide();
	        Cms.reload("datagrid");  
	    });
     }
//     function ajaxFileUpload(){
//    	 Cms.uploadWindow("image/lottery",function(data){
//    		 mini.get("lotteryLogoUrl").setValue(data.url);
//             $("#img").attr("src",data.url);
//		 });
//     }
     function updWindow(id){
    	 mini.get(id).show();
    	 if(id=="updStartSailTime"){
    		 var time  = mini.get("startSailTime").getValue();
    		 var form = new mini.Form("#startSailTimeForm");
			 form.clear();
    		 if(time&&time!=""){
    			 var times = time.split(",");
    			 var row ={};
    			 for(var i=0;i<times.length;i++){
    				 var week = times[i].split("|");
    				 row["sweek"+week[0]]=week[1];
    				 //row["sweek"+week[0]] = week[1].replace(/[^:]/g, "").length === 2 ? week[1] : week[1]+":00";
    			 }
    			 form.setData(row)
    		 }
    	 }else if(id=="updEndSailTime"){
    		 var time  = mini.get("endSailTime").getValue();
    		 var form = new mini.Form("#endSailTimeForm");
			 form.clear();
    		 if(time&&time!=""){
    			 var times = time.split(",");
    			 var row ={};
    			 for(var i=0;i<times.length;i++){
    				 var week = times[i].split("|");
    				 row["eweek"+week[0]]=week[1];
    				 row["eday"+week[0]]=week[2];
    				 //row["eweek"+week[0]] = week[1].replace(/[^:]/g, "").length === 2 ? week[1] : week[1]+":00";
    			 }
    			 form.setData(row)
    		 }
    	 }else  if(id=="updDrawTime"){
    		 var time  = mini.get("drawTime").getValue();
    		 var form = new mini.Form("#drawTimeForm");
			 form.clear();
    		 if(time&&time!=""){
    			 var times = time.split(",");
    			 var row ={};
    			 for(var i=0;i<times.length;i++){
    				 var week = times[i].split("|");
    				 row["dweek"+week[0]]=week[1];
    			 }
    			 form.setData(row)
    		 }
    	 }else if(id == "updComeOutTime"){
    		 var time  = mini.get("comeOutTime").getValue();
    		 var form = new mini.Form("#comeOutTimeForm");
    		 form.clear();
    		 if(time&&time!=""){
    			 var times = time.split(",");
    			 var row ={};
    			 for(var i=0;i<times.length;i++){
    				 var week = times[i].split("|");
    				 var invertal = week[1].split("-");
    				 // 判断是否有"秒"
    				 row["startweek"+week[0]] = invertal[0];//.replace(/[^:]/g, "").length === 2 ? invertal[0] : invertal[0]+":00";
    				 row["endweek"+week[0]]   = invertal[1];//.replace(/[^:]/g, "").length === 2 ? invertal[1] : invertal[1]+":00";
    			 }
    			 form.setData(row)
    		 }
    		 
    	 }
     }
     
     function initWeekTime(id,form){
    	 
     }
     function startSailTimeOK(){
    	 var str="";
    	 for(var i=1;i<8;i++){
    		 var val =  mini.get("sweek"+i).getFormValue();
    		 if(val){
    			if(i>1&&str!=""){
    				str+=",";
    			} 
    			str+=i+"|"+val;
    		 }
    	 }
    	 mini.get("startSailTime").setValue(str);
    	 new mini.Form("#startSailTimeForm").clear();
    	 mini.get("updStartSailTime").hide();
     }
     function drawTimeOK(){
    	 var str="";
    	 for(var i=1;i<8;i++){
    		 var val =  mini.get("dweek"+i).getFormValue();
    		 if(val){
    			if(i>1&&str!=""){
    				str+=",";
    			} 
    			str+=i+"|"+val;
    		 }
    	 }
    	 mini.get("drawTime").setValue(str);
    	 new mini.Form("#drawTimeForm").clear();
    	 mini.get("updDrawTime").hide();
     }
     function endSailTimeOK(){
    	 var str="";
    	 for(var i=1;i<8;i++){
    		 var w =  mini.get("eweek"+i).getFormValue();
    		 var d =  mini.get("eday"+i).getValue();
    		 if(w){
    			if(i>1&&str!=""){
    				str+=",";
    			} 
    			str+=i+"|"+w+"|"+d;
    		 }
    	 }
    	 mini.get("endSailTime").setValue(str);
    	 mini.get("updEndSailTime").hide();
     }
     function comeOutTimeOK(){
    	 var str="";
    	 for(var i=1;i<8;i++){
    		 var s =  mini.get("startweek"+i).getFormValue();
    		 var e =  mini.get("endweek"+i).getFormValue();
    		 if(s && e){
    			if(i>1 && str!=""){
    				str+=",";
    			} 
    			str+=i+"|"+s+"-"+e;
    		 }
    	 }
    	 mini.get("comeOutTime").setValue(str);
    	 mini.get("updComeOutTime").hide();
     }
     function onActionRendererSailDayCycle(e) {
         var gridAdd = e.sender;
         var record = e.record;
         var uid = record._uid;
         var s="";
         var id = record.id;
         if(id && gridAdd.isEditingRow(record)){
             s = '<a class="mini-button"  plain="true" href="javascript:Cms.cancelEditRow(\'sailDayCycle_grid\',\'' + uid + '\')">取消</a>';
             s +='<a class="mini-button"  plain="true" href="javascript:Cms.delEditRow(\'sailDayCycle_grid\',\'' + uid + '\')">删除</a>';
         }else if (gridAdd.isEditingRow(record)) {
             s = '<a class="mini-button"  plain="true" href="javascript:Cms.delEditRow(\'sailDayCycle_grid\',\'' + uid + '\')">取消</a>';
         }
         return s;
     }
     function updSailDayCycle(){
    	 mini.get("updSailDayCycle").show();
    	 var data = [];
    	 var sailDayCycle  = mini.get("sailDayCycle").getValue();
    	 if(sailDayCycle){
    		 var sails = sailDayCycle.split(",");
    		 for(var i=0;i<sails.length;i++){
    			 var row={};
    			 var sail = sails[i].split("|");
    			 var cycl = sail[0].split("-");
    			 row["id"] = i+1;
    			 row["second"] = sail[1];
    			 row["start"] = cycl[0];
    			 row["end"] = cycl[1];    			
    			 data.push(row);
    		 }
    	 }
    	 sailDayCycle_grid.setData(data);
     }
     
     function sailDayCycleOK(){
         Cms.formValidate("#sailDayCycle_grid","修改数据有误",function(){
 	        sailDayCycle_grid.commitEdit();
 	        var rows = sailDayCycle_grid.getData();
 	        var str = "";
 	        for(var i=0;i<rows.length;i++){
 	    	    var row = rows[i]
 	    	    if(i>0){
 	    	    	str+=",";
 	    	    }
 	    	    str+=row.start+"-"+row.end+"|"+row.second;
 	    	}
 	       mini.get("sailDayCycle").setValue(str);
 	       mini.get("updSailDayCycle").hide();
         });
      }
     
     
     
     function onActionRendererVacation(e) {
         var gridAdd = e.sender;
         var record = e.record;
         var uid = record._uid;
         var s="";
         var id = record.id;
         if(id && gridAdd.isEditingRow(record)){
             s = '<a class="mini-button"  plain="true" href="javascript:Cms.cancelEditRow(\'vacation_grid\',\'' + uid + '\')">取消</a>';
             s +='<a class="mini-button"  plain="true" href="javascript:Cms.delEditRow(\'vacation_grid\',\'' + uid + '\')">删除</a>';
         }else if (gridAdd.isEditingRow(record)) {
             s = '<a class="mini-button"  plain="true" href="javascript:Cms.delEditRow(\'vacation_grid\',\'' + uid + '\')">取消</a>';
         }
         return s;
     }
     function updVacation(){
    	 mini.get("updVacation").show();
    	 var data = [];
    	 var sailDayCycle  = mini.get("vacations").getValue();
    	 if(sailDayCycle){
    		 var sails = sailDayCycle.split(",");
    		 for(var i=0;i<sails.length;i++){
    			 var row ={};
    			 row["id"] = i+1;
    			 row["date"] =sails[i];			
    			 data.push(row);
    		 }
    	 }
    	 vacation_grid.setData(data);
     }
     
     function vacationOK(){
         Cms.formValidate("#vacation_grid","修改数据有误",function(){
        	vacation_grid.commitEdit();
 	        var rows = vacation_grid.getData();
 	        var str = "";
 	        for(var i=0;i<rows.length;i++){
 	    	    if(i>0){
 	    	    	str+=",";
 	    	    }
 	    	    var row  =rows[i].date;
 	    	    //判断是否是字符串，不是解析成日期
 	    	    if(Object.prototype.toString.call(row)!="[object String]"){
 	    	    	var month = row.getMonth()+1;
 	    	    	var date = row.getDate();
 	    	    	if(month < 10){
 	    	    		month = "0" + month;
 	    	    	}
 	    	    	if(date < 10){
 	    	    		date = "0" + date;
 	    	    	}
 		    	    str+=row.getFullYear()+"-"+month+"-"+date;
 	    	    }else{
 	    	    	 str+=rows[i].date;
 	    	    }
 	    	}
 	       mini.get("vacations").setValue(str);
 	       mini.get("updVacation").hide();
         });
      }
     
     function excel(){
    	 var param = Cms.jsonParamStr(getParamJson());
		 var url = "lotterymgr/type/excel?"+param;
		 location = url;
     }
     var lotterytypeCatalogue = '_upload_images/lotterymgr/type';
     function openImage(){
		 Cms.imageManage(lotterytypeCatalogue,function(data){
			   setImage(data);
		   });
	}
    function setImage(data){
		$("#img").attr("src",data.url);
		mini.get("lotteryLogoUrl").setValue(data.dir);
	}
    function openImage4Mobile(){
   	 Cms.imageManage(lotterytypeCatalogue,function(data){
   		$("#imgMobile").attr("src",data.url);
		mini.get("lotteryLogoMobile").setValue(data.dir);
   	 });
    }
    
    function onChangeSaleStatus(){
    	if(mini.get("saleStatus").getValue() == 0) {// 暂停销售
    		platform.show();
    	}else{
    		platform.hide();
    	}
    }