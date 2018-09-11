lottery_fb={
    init:function(){
        mini.parse();
        //lottery_fb.issueStatus =  mini.get("issueStatus");
        lottery_fb.matchStatus_search = mini.get("matchStatus_search");
        lottery_fb.isRecommend = mini.get("isRecommend");
        lottery_fb.matchStatus_edit = mini.get("matchStatus_edit");
        lottery_fb.isNeutral = mini.get("isNeutral");
        lottery_fb.statusWdf = mini.get("statusWdf");
        lottery_fb.statusLetWdf = mini.get("statusLetWdf");
        lottery_fb.statusGoal = mini.get("statusGoal");
        lottery_fb.statusHfWdf = mini.get("statusHfWdf");
        lottery_fb.statusScore = mini.get("statusScore");
        lottery_fb.officialId_search = mini.get("officialId_search");
        lottery_fb.analysisId_search = mini.get("analysisId_search");
        lottery_fb.lotteryIssueSelect = mini.get("lotteryIssueSelect");
        lottery_fb.lotteryIssue = mini.get("lotteryIssue");

        $.get("sysmgr/dic/dictionary?code=0402,0507,0307,0508,0510,0511", function(result){
            Dic.yesNo = result['0307'];
            Dic.saleStatus = result['0402'];
            Dic.matchStatus = result['0507'];
            Dic.matchResult  = result['0508'];
            Dic.sportFbChildPay = result['0510'];
            Dic.sportFbChildPayStatus = result['0511'];
            lottery_fb.isRecommend.setData(Dic.yesNo);
            lottery_fb.isNeutral.setData(Dic.yesNo);
            //lottery_fb.issueStatus.setData(Dic.saleStatus);
            lottery_fb.matchStatus_search.setData(Dic.matchStatus);
            lottery_fb.matchStatus_edit.setData(Dic.matchStatus);
            lottery_fb.statusScore.setData(Dic.sportFbChildPayStatus);
            lottery_fb.statusHfWdf.setData(Dic.sportFbChildPayStatus);
            lottery_fb.statusGoal.setData(Dic.sportFbChildPayStatus);
            lottery_fb.statusLetWdf.setData(Dic.sportFbChildPayStatus);
            lottery_fb.statusWdf.setData(Dic.sportFbChildPayStatus);
            
            lottery_fb.datagrid = mini.get("datagrid");
            lottery_fb.editWindow = mini.get("editWindow");
            lottery_fb.editForm =  new mini.Form("#editform");
            lottery_fb.sportDataFbGoalBOs = mini.get("sportDataFbGoalBOs");
            lottery_fb.sportDataFbHfWDFBOs = mini.get("sportDataFbHfWDFBOs");
            lottery_fb.sportDataFbScoreBOs = mini.get("sportDataFbScoreBOs");
            lottery_fb.sportDataFbPingScoreBOs = mini.get("sportDataFbPingScoreBOs");
            lottery_fb.sportDataFbFuScoreBOs = mini.get("sportDataFbFuScoreBOs");
            lottery_fb.sportLetNumDataFbWDFBOs = mini.get("sportLetNumDataFbWDFBOs");
            lottery_fb.sportNotLetNumDataFbWDFBOs = mini.get("sportNotLetNumDataFbWDFBOs");

            lottery_fb.grid();
        });

        ComReq.cutOffHistoryIssue("300",function(result){
            lottery_fb.lotteryIssueSelect.setData(result);
        });
    },
    grid:function(data){
        lottery_fb.datagrid.load(data);
    },
    search:function(){
        var data =  new mini.Form("#form").getData(true);
        this.grid(data);
    },
    onrowdblclick:function(){
        var row = lottery_fb.datagrid.getSelected();
        $("#matchImg").attr("src",row.logoImg == undefined ? "" : row.logoImg);
        $("#homeImg").attr("src",row.homeLogoImg == undefined ? "" : row.homeLogoImg);
        $("#guestImg").attr("src",row.guestLogoImg == undefined ? "" : row.guestLogoImg);   
        if(row){
            lottery_fb.editForm.clear();
            row.url="lotterymgr/fb";
            row.action="post";
            var score = row.score || "";
            var hfWdf = row.hfWdf || "";
            var goalNum = row.goalNum || "";
            var fullSpf = row.fullSpf;
            var letSpf = row.letSpf;
            lottery_fb.setResult(score,hfWdf,goalNum,fullSpf,letSpf);
            var param = {
                url: "lotterymgr/fb/spInfo",
                data: {
                    againstId:row.againstId
                },
                action: "get"
            };
            Cms.ajax(param, function (data) {
                lottery_fb.sportDataFbGoalBOs.setData(data.sportDataFbGoalBOs);
                lottery_fb.sportDataFbHfWDFBOs.setData(data.sportDataFbHfWDFBOs);
                lottery_fb.sportDataFbScoreBOs.setData(data.sportDataFbScoreBOs);
                lottery_fb.sportDataFbPingScoreBOs.setData(data.sportDataFbScoreBOs);
                lottery_fb.sportDataFbFuScoreBOs.setData(data.sportDataFbScoreBOs);
                lottery_fb.sportLetNumDataFbWDFBOs.setData(data.sportLetNumDataFbWDFBOs);
                var letNum = data.sportLetNumDataFbWDFBOs[data.sportLetNumDataFbWDFBOs.length-1];
                mini.get("letNum").setValue(letNum == null ? "" : letNum.letNum > 0 ? "+" + letNum.letNum : letNum.letNum);
                lottery_fb.sportNotLetNumDataFbWDFBOs.setData(data.sportNotLetNumDataFbWDFBOs);
            });


            lottery_fb.editForm.setData(row);
            lottery_fb.editWindow.show();
            if(row.homeTeamShortName != null){
                $("#hometitle").text(row.homeTeamShortName);
            }else{
                $("#hometitle").text(row.homeTeamFullName);
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
    setResult:function(score,hfWdf,goalNum,fullSpf,letSpf){
        try{
            $("#goalNum").text(goalNum);//总进球数
            $("#fullSpf").text(lottery_fb.getResult(fullSpf));
            $("#letSpf").text(lottery_fb.getResult(letSpf));
            var scoreText;
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
				scoreText = score.substr(0,1)+":"+score.substr(1,2);
					break;
			}
            $("#score").text(scoreText);
            var hfWdf = lottery_fb.getResult(hfWdf.substr(0,1))+" - "+lottery_fb.getResult(hfWdf.substr(1,2));
            $("#hfWdf").text(hfWdf);
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
        '<a class="mini-button"  plain="true" href="javascript:lottery_fb.onrowdblclick()">详情</a>';

        if (grid.isEditingRow(record)) {
            var s = ' <a class="mini-button"  plain="true" href="javascript:lottery_fb.updateRow(\'datagrid\',\'' + uid + '\')" >保存</a>&nbsp;&nbsp;&nbsp;'+
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
    		
    		 var row = lottery_fb.datagrid.getRowByUID(uid);
    	        lottery_fb.datagrid.commitEdit();
    	        var rowData = lottery_fb.datagrid.getChanges();
    	        if(rowData.length == 0){
    	            mini.alert("请先修改表格数据，再保存");
    	            return;
    	        }
    	        // 把图片路径置空
    	        var tmp = {};
    	        tmp = $.extend(true, tmp, rowData[0]);
    	        tmp.logoUrl = null;
    	        tmp.homeLogoUrl = null;
    	        tmp.guestLogoUrl = null;
    	        var json = mini.encode(tmp);
    	        Cms.save(json, "lotterymgr/fb", function(){
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
        Cms.submit(lottery_fb.editForm,function(){
            lottery_fb.editWindow.hide();
            lottery_fb.datagrid.reload();
        });
    },
    scoreValueChange:function(){
        var halfScore = mini.get("halfScore").getValue();
        var fullScore = mini.get("fullScore").getValue();
        var letNum = mini.get("letNum").getValue();
        var reg = /^[0-9]*:[0-9]*$/;
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
        if(goalNum > 7)
        	goalNum = 7;
        var fullSpf = "";
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
            fullSpf = Constants.win;
            hfWdf+=Constants.win;
        }else if(homeFullScore<guestFullScore){
            fullSpf = Constants.lost;
            hfWdf+=Constants.lost;
        }else if(homeFullScore==guestFullScore){
            fullSpf = Constants.draw;
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
        var score = "";
        if(homeFullScore > guestFullScore){
        	if(homeFullScore >= 4 && guestFullScore >=3)
        		score = "90";
        }else if (homeFullScore < guestFullScore){
        	if(homeFullScore >= 3 && guestFullScore >= 4)
        		score = "09";
        }else if(homeFullScore == guestFullScore && homeFullScore > 3 && guestFullScore > 3){
        	score = "99";
        }
        if(!score){
        	score = fullScores[0]+fullScores[1];
        }
        lottery_fb.setResult(score,hfWdf,goalNum,fullSpf,letSpf);
    },
    comboxOnCloseClick:function(e){
        var obj = e.sender;
        obj.setText("");
        obj.setValue("");
    },
    getMatch:function(){
        var param = {
            url:"lotterymgr/fb/getMatch",
            action:"get"
        };
        Cms.ajax(param,function(data){
            alert(data.message);
            lottery_fb.datagrid.reload();
        })
    },
    excel:function(){
    	
    	var form = new mini.Form("#form");
		var data = form.getData();  
		
    	
    	location.href="lotterymgr/fb/export?"+Cms.jsonParamStr(data);
    },
	restartThread:function(){
		mini.confirm("确定重启<span style=\"color: red;\">竞技足球</span>线程?", "提示", function(e) {
			if(e=="ok"){
				var param = {
						url:"lotterymgr/fb/schedule",
						data:lottery_fb.editForm.getData(true, false),
						action:"post"
				}
				Cms.ajax(param,function(){
					mini.alert("操作成功！");
		            lottery_fb.editWindow.hide();
					lottery_fb.datagrid.reload();
				});
			}
		});
	},    
    runTask:function(type, eve){
    	var issueSelVal = lottery_fb.lotteryIssueSelect.getValue();
    	var IssueVal = lottery_fb.lotteryIssue.getValue();
        var param = {
                url: "taskmgr/job/runTask",
                action: "get"
            }
        var message = "确定是否执行 <span style='color:red'>"+eve.text+"</span>?";
        switch (type) {
		case 1:
			param.data =  {'jobId':'000031'};
			break;
		case 2:
			//同步分析数据按钮
			param.data =  {'jobId':'000031', 'matchType':1, 'lotteryCode':'300'};
			break;
		case 4:	
			param.url = "lotterymgr/fb/updCheckScore";
			param.action = "put";
			param.data = {'lotteryCode':'300'};
			message = "是否确认所有比分与彩果无误?";
			break;
		case 3:	
			mini.alert("此功能暂未实现");
			return;
		case 5:	
			param.data = {'jobId':'000020','lotteryCode':'300'};
			break;			
		}
        Cms.ajaxFirm(message, param, function (data) {
        	if(type == 5){
        		Cms.drawSchedule(300,"",1,function(){
					
				});
        	}else{
	        	mini.alert("执行成功");
	        	lottery_fb.datagrid.reload();
        	}
        })  	
    }
};
lottery_fb.init();
