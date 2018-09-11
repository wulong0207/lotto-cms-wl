lottery_bj={
    init:function(){
        mini.parse();
        //lottery_bj.issueStatus =  mini.get("issueStatus");
        lottery_bj.matchStatus1 = mini.get("matchStatus1");
        lottery_bj.isRecommend = mini.get("isRecommend");
        lottery_bj.matchStatus = mini.get("matchStatus");
        lottery_bj.isNeutral = mini.get("isNeutral");
        lottery_bj.statusUdSd = mini.get("statusUdSd");
        lottery_bj.statusLetWdf = mini.get("statusLetWdf");
        lottery_bj.statusGoal = mini.get("statusGoal");
        lottery_bj.statusHfWdf = mini.get("statusHfWdf");
        lottery_bj.statusScore = mini.get("statusScore");
        lottery_bj.lotteryIssueSelect = mini.get("lotteryIssueSelect");
        lottery_bj.lotteryIssue = mini.get("lotteryIssue");

        $.get("sysmgr/dic/dictionary?code=0402,0507,0307,0508,0514,0512,0513", function(result){
            Dic.yesNo = result['0307'];
            Dic.saleStatus = result['0402'];
            Dic.matchStatus = result['0507'];
            Dic.matchResult  = result['0508'];
            Dic.sportBJChildPay = result['0514'];
            Dic.sportBJChildPayStatus = result['0512'];
            Dic.udsd = result['0513'];
            lottery_bj.isRecommend.setData(Dic.yesNo);
            lottery_bj.isNeutral.setData(Dic.yesNo);
            //lottery_bj.issueStatus.setData(Dic.saleStatus);
            lottery_bj.matchStatus1.setData(Dic.matchStatus);
            lottery_bj.matchStatus.setData(Dic.matchStatus);
            lottery_bj.statusScore.setData(Dic.sportBJChildPayStatus);
            lottery_bj.statusHfWdf.setData(Dic.sportBJChildPayStatus);
            lottery_bj.statusGoal.setData(Dic.sportBJChildPayStatus);
            lottery_bj.statusLetWdf.setData(Dic.sportBJChildPayStatus);
            lottery_bj.statusUdSd.setData(Dic.sportBJChildPayStatus);
            
            lottery_bj.datagrid = mini.get("datagrid");
            lottery_bj.editWindow = mini.get("editWindow");
            lottery_bj.editForm =  new mini.Form("#editform");
            lottery_bj.sportDataBjGoalBOs = mini.get("sportDataBjGoalBOs");
            lottery_bj.sportDataBjHfWDFBOs = mini.get("sportDataBjHfWDFBOs");
            lottery_bj.sportDataBJScoreBOs = mini.get("sportDataBJScoreBOs");
            lottery_bj.sportDataBJWDFBOs = mini.get("sportDataBJWDFBOs");
            lottery_bj.sportDataBjUDSDBOs = mini.get("sportDataBjUDSDBOs");

            lottery_bj.grid();
        });

        ComReq.cutOffHistoryIssue("306",function(result){
            lottery_bj.lotteryIssueSelect.setData(result);
        });
    },
    grid:function(data){
        lottery_bj.datagrid.load(data);
    },
    search:function(){
        var data =  new mini.Form("#form").getData(true);
        lottery_bj.grid(data);
    },
    onrowdblclick:function(){
        var row = lottery_bj.datagrid.getSelected();
        $("#matchImg").attr("src",row.logoImg == undefined ? "" : row.logoImg);
        $("#homeImg").attr("src",row.homeLogoImg == undefined ? "" : row.homeLogoImg);
        $("#guestImg").attr("src",row.guestLogoImg == undefined ? "" : row.guestLogoImg);        
        if(row){
            lottery_bj.editForm.clear();
            row.url="lotterymgr/bj";
            row.action="post";
            var score = row.score;
            var hfWdf = row.hfWdf;
            var goalNum = row.goalNum;
            var udSd = row.udSd;
            var letWdf = row.letWdf;
            lottery_bj.setResult(score,hfWdf,goalNum,udSd,letWdf);

            var param = {
                url: "lotterymgr/bj/spInfo",
                data: {
                    againstId:row.againstId
                },
                action: "get"
            };
            Cms.ajax(param, function (data) {
                lottery_bj.sportDataBjGoalBOs.setData(data.sportDataBjGoalBOs);
                lottery_bj.sportDataBjHfWDFBOs.setData(data.sportDataBjHfWDFBOs);
                lottery_bj.sportDataBJScoreBOs.setData(data.sportDataBJScoreBOs);
                lottery_bj.sportDataBJWDFBOs.setData(data.sportDataBJWDFBOs);
                lottery_bj.sportDataBjUDSDBOs.setData(data.sportDataBjUDSDBOs);
            });


            lottery_bj.editForm.setData(row);
            lottery_bj.editWindow.show();

            $("#spLetWdf").text(row.spLetWdf ? row.spLetWdf : "");
            $("#spGoalNum").text(row.spGoalNum ? row.spGoalNum : "");
            $("#spHfWdf").text(row.spHfWdf ? row.spHfWdf : "");
            $("#spUdSd").text(row.spUdSd ? row.spUdSd : "");
            $("#spScore").text(row.spScore ? row.spScore : "");

            if(row.homeTeamShortName != null){
                $("#hometitle").text(row.homeTeamShortName ? row.homeTeamShortName : "");
            }else{
                $("#hometitle").text(row.homeTeamFullName ? row.homeTeamFullName : "");
            }
            if(row.guestTeamShortName != null){
                $("#guesttitle").text(row.guestTeamShortName ? row.guestTeamShortName : "");
            }else{
                $("#guesttitle").text(row.guestTeamFullName ? row.guestTeamFullName : "");
            }
            // 把图片隐藏url置为空
//            mini.get("logoUrl").setValue("");
//            mini.get("homeLogoUrl").setValue("");
//            mini.get("guestLogoUrl").setValue("");
        }else{
            mini.alert("请选择一行数据！");
        }
    },
    setResult:function(score,hfWdf,goalNum,udSd,letWdf){
        try{
            $("#goalNum").text(goalNum ? goalNum : "");//总进球数
            $("#udSd").text(lottery_bj.getUdsd(udSd));//上下盘单双
            $("#letSpf").text(lottery_bj.getResult(letWdf));
            var scoreText = "";
            switch (score) {
			case "90":
				scoreText = "胜其他";
				break;
			case "09":
				scoreText = "负其他";
				break;
			case "99":
				scoreText = "平其他";
				break;
			default:
				if(score){
					scoreText = score.substr(0,1)+":"+score.substr(1,2);
				}
				break;
			}
            $("#score").text(scoreText);
            if(hfWdf) {
            	var hfWdf = lottery_bj.getResult(hfWdf.substr(0,1))+" - "+lottery_bj.getResult(hfWdf.substr(1,2));
            	$("#hfWdf").text(hfWdf);
            }else{
            	$("#hfWdf").text("");
            }
        }catch(e){
            for(var p in e){
                console.log(p + "=" + e[p]);
            }
        }
    },
    getResult:function(e){
        for(var i=0;i<Dic.matchResult.length;i++){
            var matchResult = Dic.matchResult[i];
            if(e == matchResult.id){
                return matchResult.text;
            }
        }
        return ""
    },
    getUdsd:function(udSd){
        for(var i=0;i<Dic.udsd.length;i++){
            var udsdResult = Dic.udsd[i];
            if(udSd == udsdResult.id){
                return udsdResult.text;
            }
        }
        return "";
    },
    onActionRenderer:function(e){
        var grid = e.sender;
        var record = e.record;
        var uid = record._uid;
        var rowIndex = e.rowIndex;

        var s = ' <a class="mini-button"  plain="true" href="javascript:Cms.editRow(\'datagrid\',\'' + uid + '\')" >修改</a>&nbsp;&nbsp;&nbsp;'+
        '<a class="mini-button"  plain="true" href="javascript:lottery_bj.onrowdblclick()">详情</a>';

        if (grid.isEditingRow(record)) {
            var s = ' <a class="mini-button"  plain="true" href="javascript:lottery_bj.updateRow(\'datagrid\',\'' + uid + '\')" >保存</a>&nbsp;&nbsp;&nbsp;'+
                '<a class="mini-button"  plain="true" href="javascript:Cms.cancelEditRow(\'datagrid\',\'' + uid + '\')">取消</a>';
        }
        return s;
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
    		
    		var row = lottery_bj.datagrid.getRowByUID(uid);
            lottery_bj.datagrid.commitEdit();
            var rowData = lottery_bj.datagrid.getChanges();
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
            Cms.save(json, "lotterymgr/bj", function(){
                Cms.reload(gridname);
            });
    		
    	});
    	
    },
    ajaxFileUpload:function(img,url,file){        
    	var lotterytypeCatalogue = '_upload_images/sport';
    	Cms.imageManage(lotterytypeCatalogue,function(data){
        	mini.get(url).setValue(data.dir);
            $("#"+img).attr("src",data.url);
		})
    },
    submit:function(){
        Cms.submit(lottery_bj.editForm,function(){
            lottery_bj.editWindow.hide();
            lottery_bj.datagrid.reload();
        });
    },
    scoreValueChange:function(){
        var halfScore = mini.get("halfScore").getValue();
        var fullScore = mini.get("fullScore").getValue();
        var letNum = mini.get("letNum").getValue();
        var reg = /^[0-9]+:[0-9]+$/;
        var letNumReg = /^(-|\+)?\d+$/;
        if(!reg.test(halfScore)){
            alert("半场比分请输入正确比分格式！");
            return;
        }
        if(!reg.test(fullScore)){
            alert("全场比分请输入正确比分格式！");
            return;
        }

        if(!letNumReg.test(letNum)){
            alert("请输入正确的让分,只能是正负数字！");
            return;
        }
        var halfScores = halfScore.split(":");
        var fullScores = fullScore.split(":");
        var homeFullScore = parseInt(fullScores[0]);
        var guestFullScore = parseInt(fullScores[1]);
        var homeHalfScore = parseInt(halfScores[0]);
        var guestHalfScore = parseInt(halfScores[1]);

        if(homeHalfScore>homeFullScore){
            alert("主队半场比分不能大于全场！");
            return;
        }

        if(guestHalfScore>guestFullScore){
            alert("客队半场比分不能大于全场！");
            return;
        }

        var goalNum = homeFullScore + guestFullScore;
        var udsd = "";
        var hfWdf = "";
        var letSpf = "";

        if(homeHalfScore>guestHalfScore){
            hfWdf+=Constants.win;
        }else if(homeHalfScore<guestHalfScore){
            hfWdf+=Constants.lost;
        }else if(homeHalfScore==guestHalfScore){
            hfWdf+=Constants.draw;
        }

        if(homeFullScore>guestFullScore){
            hfWdf+=Constants.win;
        }else if(homeFullScore<guestFullScore){
            hfWdf+=Constants.lost;
        }else if(homeFullScore==guestFullScore){
            hfWdf+=Constants.draw;
        }
        letNum = parseInt(letNum);
        if((homeFullScore+letNum)>guestFullScore){
            letSpf = Constants.win;
        }else if((homeFullScore+letNum)<guestFullScore){
            letSpf = Constants.lost;
        }else if((homeFullScore+letNum)==guestFullScore){
            letSpf = Constants.draw;
        }

        if(goalNum>=3){// goalNum>=3 上盘
            if(goalNum%2==0){
                udsd="2"//上双
            }else{
                udsd="1"//上单
            }
        }else{
            if(goalNum%2==0){
                udsd="4"//下双
            }else{
                udsd="3"//下单
            }
        }

        var score = fullScores[0]+fullScores[1];
        lottery_bj.setResult(score,hfWdf,goalNum,udsd,letSpf);
    },
    comboxOnCloseClick:function(e){
        var obj = e.sender;
        obj.setText("");
        obj.setValue("");
    },
    excel:function(){
    	
    	var form = new mini.Form("#form");
		var data = form.getData();  
		
    	
    	location.href="lotterymgr/bj/export?"+Cms.jsonParamStr(data);;
    },    
    runTask:function(type, eve){
    	var issueSelVal = lottery_bj.lotteryIssueSelect.getValue();
    	var IssueVal = lottery_bj.lotteryIssue.getValue();
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
			param.url = "lotterymgr/bj/updCheckScore";
			param.action = "put";
			param.data = {'lotteryCode':'306'};
			message = "是否确认所有比分与彩果无误?";
			break;	
		case 5:	
			param.data = {'jobId':'000020','lotteryCode':'306'};
			break;
		}
			
        Cms.ajaxFirm(message, param, function (data) {
        	if(type == 5){
        		Cms.drawSchedule(306,"",1,function(){
					
				});
        	}else{
        		mini.alert('操作成功');
            	lottery_bj.datagrid.reload();
        	}
        })  	
    }
};
lottery_bj.init();
