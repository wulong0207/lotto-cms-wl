lottery_bb={
    init:function(){
        mini.parse();
        //lottery_bb.issueStatus =  mini.get("issueStatus");
        lottery_bb.matchStatus1 = mini.get("matchStatus1");
        lottery_bb.isRecommend = mini.get("isRecommend");
        lottery_bb.matchStatus = mini.get("matchStatus");
        lottery_bb.isNeutral = mini.get("isNeutral");
        lottery_bb.statusWf = mini.get("statusWf");
        lottery_bb.statusLetWf = mini.get("statusLetWf");
        lottery_bb.statusBigSmall = mini.get("statusBigSmall");
        lottery_bb.statusScoreWf = mini.get("statusScoreWf");
        lottery_bb.lotteryIssueSelect = mini.get("lotteryIssueSelect");
        lottery_bb.lotteryIssue = mini.get("lotteryIssue");

        $.get("sysmgr/dic/dictionary?code=0402,0507,0307,0508,0515,0511", function (result) {
            Dic.yesNo = result['0307'];
            Dic.saleStatus = result['0402'];
            Dic.matchStatus = result['0507'];
            Dic.matchResult  = result['0508'];
            Dic.sportBBChildPay = result['0515'];
            Dic.sportBBChildPayStatus = result['0511'];
            lottery_bb.isRecommend.setData(Dic.yesNo);
            lottery_bb.isNeutral.setData(Dic.yesNo);
            //lottery_bb.issueStatus.setData(Dic.saleStatus);
            lottery_bb.matchStatus1.setData(Dic.matchStatus);
            lottery_bb.matchStatus.setData(Dic.matchStatus);
            lottery_bb.statusBigSmall.setData(Dic.sportBBChildPayStatus);
            lottery_bb.statusLetWf.setData(Dic.sportBBChildPayStatus);
            lottery_bb.statusWf.setData(Dic.sportBBChildPayStatus);
            lottery_bb.statusScoreWf.setData(Dic.sportBBChildPayStatus);
            
            lottery_bb.datagrid = mini.get("datagrid");
            lottery_bb.editWindow = mini.get("editWindow");
            lottery_bb.editForm =  new mini.Form("#editform");
            lottery_bb.sportDataBbSSSBOs = mini.get("sportDataBbSSSBOs");
            lottery_bb.letSportDataBbWFBOs = mini.get("letSportDataBbWFBOs");
            lottery_bb.notLetSportDataBbWFBOs = mini.get("notLetSportDataBbWFBOs");
            lottery_bb.sportDataBbWSBOs = mini.get("sportDataBbWSBOs");

            lottery_bb.grid();
        });


        ComReq.cutOffHistoryIssue("301",function(result){
            lottery_bb.lotteryIssueSelect.setData(result);
        });
    },
    grid:function(data){
        lottery_bb.datagrid.load(data);
    },
    search:function(){
        var data =  new mini.Form("#form").getData(true);
        lottery_bb.grid(data);
    },
    onrowdblclick:function(){
        var row = lottery_bb.datagrid.getSelected();
        $("#matchImg").attr("src",row.logoImg == undefined ? "" : row.logoImg);
        $("#homeImg").attr("src",row.homeLogoImg == undefined ? "" : row.homeLogoImg);
        $("#guestImg").attr("src",row.guestLogoImg == undefined ? "" : row.guestLogoImg);        
        if(row){
            lottery_bb.editForm.clear();
            row.url="lotterymgr/bb";
            row.action="post";
            var fullWf = row.fullWf;
            var letWf = row.letWf;
            var sizeScore = row.sizeScore;
            var winScore = row.winScore;
            var fullScore = row.fullScore;
            lottery_bb.setResult(fullWf,letWf,sizeScore,winScore,fullScore);
            var param = {
                url: "lotterymgr/bb/spInfo",
                data: {
                    againstId:row.againstId
                },
                action: "get"
            };
            Cms.ajax(param, function (data) {
                lottery_bb.sportDataBbSSSBOs.setData(data.sportDataBbSSSBOs);
                lottery_bb.letSportDataBbWFBOs.setData(data.letSportDataBbWFBOs);
                lottery_bb.notLetSportDataBbWFBOs.setData(data.notLetSportDataBbWFBOs);
                lottery_bb.sportDataBbWSBOs.setData(data.sportDataBbWSBOs);
            });

            lottery_bb.editForm.setData(row);
            lottery_bb.editWindow.show();
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
        }else{
            mini.alert("请选择一行数据！");
        }
    },
    setResult:function(fullWf,letWf,sizeScore,winScore,fullScore){
        try{
            $("#fullWf").text("");
            $("#letWf").text("");
            $("#sizeScore").text("");
            $("#winScore").text("");
            var fullScores = fullScore.split(":");
            $("#fullWf").text("主"+lottery_bb.getResult(fullWf));//获取主胜
            //获取让分胜负
            $("#letWf").text(lottery_bb.getLetWfOrSize(letWf,function(e){
                var letWf = e.split("|");
                var result = letWf[0]+"让分主"+lottery_bb.getResult(letWf[1]);
                return result;
            }));
            //获取大小分
            var totalScore =parseInt(fullScores[0])+parseInt(fullScores[1]);
            $("#sizeScore").text(lottery_bb.getLetWfOrSize(sizeScore,function(e){
                var letWf = e.split("|");
                var result = "";
                var score = parseFloat(letWf[0]);

                if(totalScore>score){
                    result = score+"大分";
                }else{
                    result = score+"小分";
                }

                return result;
            }));
            $("#winScore").text(lottery_bb.getWinScore(winScore));
        }catch(e){
            for(var p in e){
                console.log(p + "=" + e[p]);
            }
        }
    },
    /**
     * 获取胜负
     * @param e
     * @returns {*}
     */
    getResult:function(e){
        for(var i=0;i<Dic.matchResult.length;i++){
            var matchResult = Dic.matchResult[i];
            if(e==matchResult.id){
                return matchResult.text;
            }
        }
        return ""
    },
    /**
     * 获取让分胜负或者大小分
     * @param letWf 让分值或者大小分值
     * @param getWF 获取胜负值或者大小分值
     * @returns {string}
     */
    getLetWfOrSize:function(letWf,getWF){
        var result = "";
        if(letWf!=null&&letWf.indexOf(",")!=-1){
            var letWfs = letWf.split(",");
            for(var i=0;i<letWfs.length;i++){
                if(result == "" && result.indexOf(",")==-1){
                    result = getWF(letWfs[i]);
                }else{
                    result += "," + getWF(letWfs[i]);
                }
            }
        } else {
            result += getWF(letWf);
        }

        return result;
    },
    winScores:{"1":"1-5","2":"6-10","3":"11-15","4":"16-20","5":"21-25","6":"25分以上"},
    /**
     * 获取胜多少分
     * @param winScore
     * @returns {string}
     */
    getWinScore:function(winScore){
        var result = "";
        var win = winScore.substr(0,1);
        var score = winScore.substr(1,2);

        if(win=="0"){
            result = "主胜";
        }else if(win=="1"){
            result = "客胜";
        }

        result += lottery_bb.winScores[score];

        return result;
    },
    onActionRenderer:function(e){
        var grid = e.sender;
        var record = e.record;
        var uid = record._uid;
        var rowIndex = e.rowIndex;

        var s = ' <a class="mini-button"  plain="true" href="javascript:Cms.editRow(\'datagrid\',\'' + uid + '\')" >修改</a>&nbsp;&nbsp;&nbsp;'+
        '<a class="mini-button"  plain="true" href="javascript:lottery_bb.onrowdblclick()">详情</a>';

        if (grid.isEditingRow(record)) {
            var s = ' <a class="mini-button"  plain="true" href="javascript:lottery_bb.updateRow(\'datagrid\',\'' + uid + '\')" >保存</a>&nbsp;&nbsp;&nbsp;'+
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
    /**
     * 获取最新让分
     * @param e
     * @returns {显示文本}
     */
    setLetScore:function(e){
        var record = e.record;
        var letSportDataBbWFBOs = record.letSportDataBbWFBOs;
        var letScore = letSportDataBbWFBOs[0].letScore;
        var text = "";
        if(letScore>0){
            text += "+"+letScore;
        }else{
            text = letScore;
        }
        return text;
    },
    /**
     * 获取最新预设总分
     * @param e
     * @returns {显示文本}
     */
    setTotalScore:function(e){
        var record = e.record;
        var sportDataBbSSSBOs = record.sportDataBbSSSBOs;
        var presetScore = sportDataBbSSSBOs[0].presetScore;
        var text = presetScore;
        return text;
    },
    updateRow:function(gridname,uid){
    	
    	Cms.rowFormValidate("#" + gridname, function() {
    		var row = lottery_bb.datagrid.getRowByUID(uid);
            lottery_bb.datagrid.commitEdit();
            var rowData = lottery_bb.datagrid.getChanges();
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
            Cms.save(json, "lotterymgr/bb", function(){
                Cms.reload(gridname);
            });
    	});
    	
    },
    ajaxFileUpload:function(img,url,file){
    	var lotterytypeCatalogue = '_upload_images/sport';
    	Cms.imageManage(lotterytypeCatalogue,function(data){
    		mini.get(url).setValue(data.dir);
            $("#"+img).attr("src",data.url);
		   });
    },
    submit:function(){
        var data = lottery_bb.editForm.getData();
        data.letSportDataBbWFBOs = lottery_bb.letSportDataBbWFBOs.getData();
        data.sportDataBbSSSBOs = lottery_bb.sportDataBbSSSBOs.getData();
 
        if(lottery_bb.scoreValueValidate() == false)
        	return;
        if(lottery_bb.fullScoreValidate() == false) {
        	return;
        }
        lottery_bb.editForm.validate();
        if (lottery_bb.editForm.isValid() == false){
            mini.alert("表单数据有误");
            return;
        }
        data = mini.encode(data);
        mini.confirm("确定保存?", "提示", function(e) {
            if(e=="ok"){
                $.ajax({
                    url : "lotterymgr/bb",
                    data : {"json":data},
                    type : "post",
                    success : function(res) {
                        if(res == "1" || res.errorCode == Code.success){
                            mini.showTips({
                                content: res.message,
                                state: "success",
                                x: "center",
                                y: "center",
                                timeout: 2000
                            });
                            lottery_bb.editWindow.hide();
                            lottery_bb.datagrid.reload();
                        }else{
                            mini.alert(res.message);
                        }
                    },
                    error : function(jqXHR, textStatus, errorThrown) {
                        mini.alert(jqXHR.responseText);
                    }
                });
            }
        });
    },
    scoreValueChange:function(){
        var firstScore = mini.get("firstScore").getValue();
        var secondScore = mini.get("secondScore").getValue();
        var thirdScore = mini.get("thirdScore").getValue();
        var fourthScore = mini.get("fourthScore").getValue();
        var outTimeScore = mini.get("outTimeScore").getValue();
        var fullScore = mini.get("fullScore").getValue();
        if(!fullScore)
        	return;
        
        if(lottery_bb.scoreValueValidate() == false)
        	return;

        var letSportDataBbWFBOs = lottery_bb.letSportDataBbWFBOs.getData();
        var sportDataBbSSSBOs = lottery_bb.sportDataBbSSSBOs.getData();
        var firstScores = firstScore.split(":");
        var secondScores = secondScore.split(":");
        var thirdScores = thirdScore.split(":");
        var fourthScores = fourthScore.split(":");


        var fullScores = fullScore.split(":");
        var _firstScore1 = parseInt(firstScores[0]);
        var _firstScore2 = parseInt(firstScores[1]);
        var _secondScore1 = parseInt(secondScores[0]);
        var _secondScore2 = parseInt(secondScores[1]);
        var _thirdScore1 = parseInt(thirdScores[0]);
        var _thirdScore2 = parseInt(thirdScores[1]);
        var _fourthScore1 = parseInt(fourthScores[0]);
        var _fourthScore2 = parseInt(fourthScores[1]);

        var _outTimeScores1 = 0;
        var _outTimeScores2 = 0;

        var totalGuestScore = _firstScore1 + _secondScore1 + _thirdScore1 + _fourthScore1;
        var totalHomeScore = _firstScore2 + _secondScore2 + _thirdScore2 + _fourthScore2;

        if (outTimeScore != null && outTimeScore != "") {
            if(totalHomeScore!=totalGuestScore){
                alert("前四节比分主队与客队未打平，不能有加时比分。");
                return false;
            }
            var outTimeScores = outTimeScore.split(":");
            _outTimeScores1 = parseInt(outTimeScores[0]);
            _outTimeScores2 = parseInt(outTimeScores[1]);
        }else{
		    if(totalGuestScore == totalHomeScore) {
		   		alert("前四节主队与客队打平，必须有加时比分。");
		    	return false;
		    }
        }


        var _fullScore1 = parseInt(fullScores[0]);
        var _fullScore2 = parseInt(fullScores[1]);


        totalGuestScore = totalGuestScore + _outTimeScores1;
        totalHomeScore = totalHomeScore + _outTimeScores2;

        var fullScoreText = totalGuestScore + ":" + totalHomeScore;
        if (_fullScore1 != totalGuestScore || _fullScore2 != totalHomeScore) {
            mini.get("fullScore").setValue(fullScoreText);
        }
        if(totalHomeScore  == totalGuestScore) {
        	alert("全场比分不能打平。");
        	return false;
        }
        
        //获取全场胜负
        var fullWf = "";
        //获取主胜客胜，以及胜分差
        var winScore = "";
        if (totalHomeScore > totalGuestScore) {
            winScore = getScore("0",totalHomeScore,totalGuestScore); //主胜

            fullWf = Constants.win;
        } else if (totalHomeScore < totalGuestScore) {
            winScore = getScore("1",totalHomeScore,totalGuestScore);//客胜
            fullWf = Constants.lost;
        }
        function getScore(winFlag,totalHomeScore,totalGuestScore){
            var result = "";
            var  score = 0;
            if(winFlag=="0"){
                score =  totalHomeScore - totalGuestScore;
            } else {
                score =  totalGuestScore - totalHomeScore;
            }

            var text = "";
            if(score >= 1 && score <= 5){
                text = "1";
            } else if(score >= 6 && score <= 10){
                text = "2";
            } else if(score >= 11 && score <= 15){
                text = "3";
            } else if(score >= 16 && score <= 20){
                text = "4";
            } else if(score >= 21 && score <= 25){
                text = "5";
            } else if(score > 25 ){
                text = "6";
            }

            result = winFlag + text;
            return result;
        }
        /*获取让分胜负*/
        var letWf = "";
        var letWfArr = [];
        for (var i = 0; i < letSportDataBbWFBOs.length; i++) {
            var letScore = parseFloat(letSportDataBbWFBOs[i].letScore);
            var letSpf = "";
            if(lottery_bb.isRepeat(letScore, letWfArr)){
            	continue;
            }else{
                letWfArr.push(letScore);
            }
            
            if ((totalHomeScore + letScore) > totalGuestScore) {
                letSpf = Constants.win;
            } else if ((totalHomeScore + letScore) < totalGuestScore) {
                letSpf = Constants.lost;
            }
            letSpf = letScore + "|" + letSpf;
            if (letWf == "" && letWf.indexOf(",") == -1) {
                letWf = letSpf;
            } else {
                letWf += "," + letSpf;
            }
        }

        //获取大小分
        var sizeScore = "";
        for (var i = 0; i < sportDataBbSSSBOs.length; i++) {
            var presetScore = parseFloat(sportDataBbSSSBOs[i].presetScore);
            var presetScoreText = "";
            if(sizeScore.indexOf(presetScore) > -1)
            	continue;            
            var totalScore = totalHomeScore + totalGuestScore;
            if (totalScore > presetScore) {
                presetScoreText = "99";//大分
            } else if (totalScore < presetScore) {
                presetScoreText = "00";//小分
            }
            presetScoreText = presetScore + "|" + presetScoreText;
            if (sizeScore == "" && sizeScore.indexOf(",") == -1) {
                sizeScore = presetScoreText;
            } else {
                sizeScore += "," + presetScoreText;
            }
        }

        lottery_bb.setResult(fullWf, letWf, sizeScore, winScore, fullScoreText)
    },
    fullScoreValidate: function() {
    	var firstScore = mini.get("firstScore").getValue();
        var secondScore = mini.get("secondScore").getValue();
        var thirdScore = mini.get("thirdScore").getValue();
        var fourthScore = mini.get("fourthScore").getValue();
        var outTimeScore = mini.get("outTimeScore").getValue();
        var fullScore = mini.get("fullScore").getValue();
        if(!firstScore || !secondScore || !thirdScore || !fourthScore || !fullScore) {
        	return true;
        }
        
        var firstScores = firstScore.split(":");
        var secondScores = secondScore.split(":");
        var thirdScores = thirdScore.split(":");
        var fourthScores = fourthScore.split(":");
        var fullScores = fullScore.split(":");
        
        var _firstScore1 = parseInt(firstScores[0]);
        var _firstScore2 = parseInt(firstScores[1]);
        var _secondScore1 = parseInt(secondScores[0]);
        var _secondScore2 = parseInt(secondScores[1]);
        var _thirdScore1 = parseInt(thirdScores[0]);
        var _thirdScore2 = parseInt(thirdScores[1]);
        var _fourthScore1 = parseInt(fourthScores[0]);
        var _fourthScore2 = parseInt(fourthScores[1]);

        var _outTimeScores1 = 0;
        var _outTimeScores2 = 0;

        var totalGuestScore = _firstScore1 + _secondScore1 + _thirdScore1 + _fourthScore1;
        var totalHomeScore = _firstScore2 + _secondScore2 + _thirdScore2 + _fourthScore2;

        if (outTimeScore != null && outTimeScore != "") {
            if(totalHomeScore!=totalGuestScore){
                alert("前四节比分主队与客队未打平，不能有加时比分。");
                return false;
            }
            var outTimeScores = outTimeScore.split(":");
            _outTimeScores1 = parseInt(outTimeScores[0]);
            _outTimeScores2 = parseInt(outTimeScores[1]);
            
        }else{
		    if(totalGuestScore == totalHomeScore) {
		   		alert("前四节主队与客队打平，必须有加时比分。");
		    	return false;
		    }
        }
        
        var _fullScore1 = parseInt(fullScores[0]);
        var _fullScore2 = parseInt(fullScores[1]);
        totalGuestScore = totalGuestScore + _outTimeScores1;
        totalHomeScore = totalHomeScore + _outTimeScores2;
        if (_fullScore1 != totalGuestScore) {
        	alert("客队全场比分与每节比分总和不匹配。");
        	return false;
        }
        if (_fullScore2 != totalHomeScore) {
        	alert("主队全场比分与每节比分总和不匹配。");
        	return false;
        }
        if(totalHomeScore  == totalGuestScore) {
        	alert("全场比分不能打平。");
        	return false;
        }
    },
    scoreValueValidate:function(){
    var firstScore = mini.get("firstScore").getValue();
    var secondScore = mini.get("secondScore").getValue();
    var thirdScore = mini.get("thirdScore").getValue();
    var fourthScore = mini.get("fourthScore").getValue();
    var outTimeScore = mini.get("outTimeScore").getValue();
    var fullScore = mini.get("fullScore").getValue();
    var reg = /^[0-9]+:[0-9]+$/;
    if(firstScore || secondScore || thirdScore || thirdScore || fullScore){
        if(!reg.test(firstScore)){
            alert("第一节比分请输入正确比分格式！");
            return false;
        }
        if(!reg.test(secondScore)){
            alert("第二节比分请输入正确比分格式！");
            return false;
        }
        if(!reg.test(thirdScore)){
            alert("第三节比分请输入正确比分格式！");
            return false;
        }
        if(!reg.test(fourthScore)){
            alert("第四节比分请输入正确比分格式！");
            return false;
        }
        if(outTimeScore != null && outTimeScore != "" 
        	&& !reg.test(outTimeScore)){
            alert("加时比分请输入正确比分格式！");
            return false;
        }
        if(!reg.test(fullScore)){
            alert("全场比分请输入正确比分格式！");
            return false;
        }       	
    }
     return true;
    },
    comboxOnCloseClick:function(e){
        var obj = e.sender;
        obj.setText("");
        obj.setValue("");
    },
    getMatch: function () {
        var param = {
            url: "lotterymgr/bb/getMatch",
            action: "get"
        };
        Cms.ajax(param, function (data) {
        	alert(data.message);
            lottery_bb.datagrid.reload();
        })
    },
    excel:function(){
    	
    	var form = new mini.Form("#form");
		var data = form.getData();  
		
    	
    	location.href="lotterymgr/bb/export?"+Cms.jsonParamStr(data);;
    },
	restartThread:function(){
		mini.confirm("确定重启<span style=\"color: red;\">竞技篮球</span>线程?", "提示", function(e) {
			if(e=="ok"){
				var param = {
						url:"lotterymgr/bb/schedule",
						data:lottery_bb.editForm.getData(true, false),
						action:"post"
				}
				Cms.ajax(param,function(){
					mini.alert("操作成功！");
			        lottery_bb.editWindow.hide();
		            lottery_bb.datagrid.reload();
				});
			}
		});
	},    
    runTask:function(type, eve){
    	var issueSelVal = lottery_bb.lotteryIssueSelect.getValue();
    	var IssueVal = lottery_bb.lotteryIssue.getValue();
        var param = {
                url: "taskmgr/job/runTask",
                action: "get"
            };
        var message = "确定是否执行 <span style='color:red'>"+eve.text+"</span>?";
        switch (type) {
		case 1:
			//获取比赛按钮
			param.data =  {'jobId':'000032'};
			break;
		case 2:
			//同步分析数据按钮
			param.data =  {'jobId':'000031', 'matchType':2, 'lotteryCode':'301'};
			break;
		case 4:	
			param.url = "lotterymgr/bb/updCheckScore";
			param.action = "put";
			param.data = {'lotteryCode':'301'};
			message = "是否确认所有比分与彩果无误?";
			break;			
		case 3:	
			mini.alert("此功能暂未实现");
			return;			
		case 5:	
			param.data = {'jobId':'000020','lotteryCode':'301'};
			break;			
		}
        Cms.ajaxFirm(message, param, function (data) {
        	if(type == 5){
        		Cms.drawSchedule(301,"",1,function(){
					
				});
        	}else{
        		mini.alert("执行成功");
            	lottery_bb.datagrid.reload();
        	}
        })  	
    },
    isRepeat : function(str, arr){
        for (var i = 0; i < arr.length; i++) {
        	if(str == arr[i]){
        		return true;
        	}
		}
        return false;
    }
};
lottery_bb.init();
