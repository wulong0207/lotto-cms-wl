lottery_wf={
    init:function(){
        mini.parse();
        //lottery_wf.issueStatus =  mini.get("issueStatus");
        lottery_wf.matchStatus1 = mini.get("matchStatus1");
        lottery_wf.isRecommend = mini.get("isRecommend");
        lottery_wf.matchStatus = mini.get("matchStatus");
        lottery_wf.isNeutral = mini.get("isNeutral");
        lottery_wf.statusLetWf = mini.get("statusLetWf");
        lottery_wf.lotteryIssueSelect = mini.get("lotteryIssueSelect");
        lottery_wf.lotteryIssue = mini.get("lotteryIssue");

        $.get("sysmgr/dic/dictionary?code=0402,0507,0307,0508,0501,0512", function(result){
            Dic.yesNo = result['0307'];
            Dic.saleStatus = result['0402'];
            Dic.matchStatus = result['0507'];
            Dic.matchResult  = result['0508'];
            Dic.sportFbChildPayStatus = result['0512'];
            Dic.matchType = result['0501'];
            lottery_wf.isRecommend.setData(Dic.yesNo);
            lottery_wf.isNeutral.setData(Dic.yesNo);
            //lottery_wf.issueStatus.setData(Dic.saleStatus);
            lottery_wf.matchStatus1.setData(Dic.matchStatus);
            lottery_wf.matchStatus.setData(Dic.matchStatus);
            lottery_wf.statusLetWf.setData(Dic.sportFbChildPayStatus);
            
            lottery_wf.datagrid = mini.get("datagrid");
            lottery_wf.editWindow = mini.get("editWindow");
            lottery_wf.editForm =  new mini.Form("#editform");
            lottery_wf.sportDataSfWFBOs = mini.get("sportDataSfWFBOs");

            lottery_wf.grid();
        });

        ComReq.cutOffHistoryIssue("307",function(result){
            lottery_wf.lotteryIssueSelect.setData(result);
        });
    },
    grid:function(data){
        lottery_wf.datagrid.load(data);
    },
    search:function(){
        var data =  new mini.Form("#form").getData(true);
        lottery_wf.grid(data);
    },
    onrowdblclick:function(){
        var row = lottery_wf.datagrid.getSelected();
        $("#matchImg").attr("src",row.logoImg == undefined ? "" : row.logoImg);
        $("#homeImg").attr("src",row.homeLogoImg == undefined ? "" : row.homeLogoImg);
        $("#guestImg").attr("src",row.guestLogoImg == undefined ? "" : row.guestLogoImg);   
        if(row){
            lottery_wf.editForm.clear();
            row.url="lotterymgr/wf";
            row.action="post";
            var letSf = row.letSf;
            lottery_wf.setResult(letSf);

            var param = {
                url: "lotterymgr/wf/spInfo",
                data: {
                    againstId:row.againstId
                },
                action: "get"
            };
            Cms.ajax(param, function (data) {
                lottery_wf.sportDataSfWFBOs.setData(data.sportDataSfWFBOs);
            });

            lottery_wf.editForm.setData(row);
            lottery_wf.editWindow.show();
            $("#spLetWf").text(row.spLetWf ? row.spLetWf : "");
            if(row.homeTeamShortName != null){
                $("#hometitle").text(row.homeTeamShortName ? row.homeTeamShortName : "");
            }else{
                $("#hometitle").text(row.homeTeamFullName ? row.homeTeamShortName : "");
            }
            if(row.guestTeamShortName != null){
                $("#guesttitle").text(row.guestTeamShortName);
            }else{
                $("#guesttitle").text(row.guestTeamFullName);
            }
            // 把图片隐藏url置为空
//            mini.get("logoUrl").setValue("");
//            mini.get("homeLogoUrl").setValue("");
//            mini.get("guestLogoUrl").setValue("");
        }else{
            mini.alert("请选择一行数据！");
        }
    },
    setResult:function(letSf){
        try{
            $("#letSf").text(lottery_wf.getResult(letSf));
        }catch(e){
            for(var p in e){
                console.log(p + "=" + e[p]);
            }
        }
    },
    getResult:function(e){
        for(var i=0;i<Dic.matchResult.length;i++){
            var matchResult = Dic.matchResult[i];
            if(e==matchResult.id){
                return matchResult.text;
            }
        }
        return ""
    },
    onActionRenderer:function(e){
        var grid = e.sender;
        var record = e.record;
        var uid = record._uid;
        var rowIndex = e.rowIndex;

        var s = ' <a class="mini-button"  plain="true" href="javascript:Cms.editRow(\'datagrid\',\'' + uid + '\')" >修改</a>&nbsp;&nbsp;&nbsp;'+
        '<a class="mini-button"  plain="true" href="javascript:lottery_wf.onrowdblclick()">详情</a>';

        if (grid.isEditingRow(record)) {
            var s = ' <a class="mini-button"  plain="true" href="javascript:lottery_wf.updateRow(\'datagrid\',\'' + uid + '\')" >保存</a>&nbsp;&nbsp;&nbsp;'+
                '<a class="mini-button"  plain="true" href="javascript:Cms.cancelEditRow(\'datagrid\',\'' + uid + '\')">取消</a>';
        }
        return s;
    },
    onMatchTypeRenderer:function(e) {
    	for(var i = 0; i < Dic.matchType.length; i++) {
    		if(Dic.matchType[i].id == e.value) {
    			return Dic.matchType[i].text;
    		}
    	}
    	return e.value;
    },
    /**
     * 设置其他数问题
     * @param e
     */
    setWrong:function(e){
        var record = e.record;
        var result = '<span style="color:red;">'+record.wrong+'</span>';
        return result;
    },
    updateRow:function(gridname,uid){
    	
    	Cms.rowFormValidate("#" + gridname, function() {
    		
    		var row = lottery_wf.datagrid.getRowByUID(uid);
            lottery_wf.datagrid.commitEdit();
            var rowData = lottery_wf.datagrid.getChanges();
            if(rowData.length == 0){
                mini.alert("请先修改表格数据，再保存");
                return;
            }
	        var tmp = {};
	        tmp = $.extend(true, tmp, rowData[0]);
	        tmp.logoUrl = null;
	        tmp.homeLogoUrl = null;
	        tmp.guestLogoUrl = null;
	        var json = mini.encode(tmp);
            Cms.save(json, "lotterymgr/wf", function(){
                Cms.reload(gridname);
            });
    		
    	});
    	
    },
    ajaxFileUpload:function(img,url,file){
    	Cms.imageManage("_upload_images/sport",function(data){
        	mini.get(url).setValue(data.dir);
            $("#"+img).attr("src",data.url);
		})
    },
    submit:function(){
        Cms.submit(lottery_wf.editForm,function(){
            lottery_wf.editWindow.hide();
            lottery_wf.datagrid.reload();
        });
    },
    scoreValueChange:function(){

        var fullScore = mini.get("fullScore").getValue();
        var letNum = mini.get("letNum").getValue();
        var reg = /^[0-9]+:[0-9]+$/;
        var letNumReg = /^(-|\+)?\d+(\.\d+)?$/;

        if(!reg.test(fullScore)){
            alert("全场比分请输入正确比分格式！");
            return;
        }

        if(!letNumReg.test(letNum)){
            alert("请输入正确的让分,只能是正负数值！");
            return;
        }

        var fullScores = fullScore.split(":");
        var homeFullScore = parseInt(fullScores[0]);
        var guestFullScore = parseInt(fullScores[1]);

        var letSf = "";

        letNum = parseInt(letNum);
        if((homeFullScore+letNum)>guestFullScore){
            letSf = Constants.win;
        }else if((homeFullScore+letNum)<guestFullScore){
            letSf = Constants.lost;
        }else if((homeFullScore+letNum)==guestFullScore){
            letSf = Constants.draw;
        }
        lottery_wf.setResult(letSf);
    },
    comboxOnCloseClick:function(e){
        var obj = e.sender;
        obj.setText("");
        obj.setValue("");
    },
    excel:function(){
    	
    	var form = new mini.Form("#form");
		var data = form.getData();  
		
    	
    	location.href="lotterymgr/wf/export?"+Cms.jsonParamStr(data);;
    },
    runTask:function(type, eve){
    	var issueSelVal = lottery_wf.lotteryIssueSelect.getValue();
    	var IssueVal = lottery_wf.lotteryIssue.getValue();
        var param = {
                url: "taskmgr/job/runTask",
                action: "get"
            }
        var message = "确定是否执行 <span style='color:red'>"+eve.text+"</span>?";
        switch (type) {
		case 1:
//			param.data =  {'jobId':'000030', "lotteryIssue":issueSelVal || IssueVal};
//			mini.alert("此功能暂未实现");
//			return;
		case 2:
			//同步分析数据按钮
//			param.data =  {'jobId':'000031', 'matchType':1, 'lotteryCode':'300'};
//			break;
		case 3:	
			mini.alert("此功能暂未实现");
			return;
		case 4:	
			param.url = "lotterymgr/wf/updCheckScore";
			param.action = "put";
			param.data = {'lotteryCode':'307'};
			message = "是否确认所有比分与彩果无误?";
			break;	
		case 5:	
			param.data = {'jobId':'000020','lotteryCode':'307'};
			break;				
		}
        Cms.ajaxFirm(message, param, function (data) {
        	if(type == 5){
        		Cms.drawSchedule(307,"",1,function(){
					
				});
        	}else{
	        	mini.alert('操作成功');
	        	lottery_wf.datagrid.reload();
        	}
        })  	
    }
};
lottery_wf.init();
