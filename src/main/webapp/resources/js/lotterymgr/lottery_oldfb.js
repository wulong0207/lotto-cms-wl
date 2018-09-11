lottery_oldfb={
    init:function(){
        mini.parse();
        lottery_oldfb.issueStatus =  mini.get("issueStatus");
        lottery_oldfb.matchStatus1 = mini.get("matchStatus1");
        lottery_oldfb.isRecommend = mini.get("isRecommend");
        lottery_oldfb.matchStatus = mini.get("matchStatus");
        lottery_oldfb.isNeutral = mini.get("isNeutral");
        lottery_oldfb.lotteryCode = mini.get("lotteryCode");
        lottery_oldfb.lotteryIssue = mini.get("lotteryIssue");
        
        $.get("sysmgr/dic/dictionary?code=0402,0506,0307,0508", function(result){
            Dic.yesNo = result['0307'];
            Dic.saleStatus = result['0402'];
            Dic.matchStatus = result['0506'];
            Dic.matchResult  = result['0508'];
            lottery_oldfb.isRecommend.setData(Dic.yesNo);
            lottery_oldfb.isNeutral.setData(Dic.yesNo);
            lottery_oldfb.issueStatus.setData(Dic.saleStatus);
            lottery_oldfb.matchStatus1.setData(Dic.matchStatus);
            lottery_oldfb.matchStatus.setData(Dic.matchStatus);
            
            lottery_oldfb.datagrid = mini.get("datagrid");
            lottery_oldfb.editWindow = mini.get("editWindow");
            lottery_oldfb.editForm =  new mini.Form("#editform");
            lottery_oldfb.fullFBBos = mini.get("fullFBBos");
            lottery_oldfb.halfFBBos = mini.get("halfFBBos"); 
            
            lottery_oldfb.grid();
            
        });
        
        var json=[{"id":304,"text":"十四场"},{"id":302,"text":"足彩:六场半全场"},{"id":305,"text":"九场胜负彩"},{"id":303,"text":"足彩:四场进球彩"}];
        lottery_oldfb.lotteryCode.setData(json);
    },
    grid:function(data){
        if(!data)
            data = {lotteryCode:304};
        else{
            !data.lotteryCode && (data.lotteryCode=304);
        }
        lottery_oldfb.datagrid.load(data);
    },
    search:function(){
        var data =  new mini.Form("#form").getData(true);
        var lotteryCode = data.lotteryCode;
        if(lotteryCode == '305')
        	data.lotteryCode = "304";
        lottery_oldfb.grid(data);
    },
    lotteryCodeChange:function(){
    	 var code = lottery_oldfb.lotteryCode.getValue();

    	 if(code){
				ComReq.cutOffHistoryIssue(code,function(result){
					lottery_oldfb.lotteryIssue.setData(result);
				});
			}
		 
    },
    onrowdblclick:function(){
        var row = lottery_oldfb.datagrid.getSelected();
        $("#matchImg").attr("src",row.logoUrl == undefined ? "" : row.logoUrl);
        $("#homeImg").attr("src",row.homeLogoUrl == undefined ? "" : row.homeLogoUrl);
        $("#guestImg").attr("src",row.guestLogoUrl == undefined ? "" : row.guestLogoUrl);
        if(row){
            lottery_oldfb.editForm.clear();
            row.url="lotterymgr/old_fb";
            row.action="post";
            var fourteenWdf = row.fourteenWdf;
            var fourGoal = row.fourGoal == null ? "" : row.fourGoal.toString();
            fourGoal = fourGoal.length == 2 ? fourGoal.substring(0,1) + ":" + fourGoal.substring(1,2) : fourGoal; 
            var sixHfWdf = row.sixHfWdf;
            lottery_oldfb.setResult(fourGoal,fourteenWdf,sixHfWdf);
            var param = {
                url: "lotterymgr/old_fb/spInfo",
                data: {
                    againstId:row.againstId
                },
                action: "get"
            };
            Cms.ajax(param, function (data) {
                lottery_oldfb.fullFBBos.setData(data.fullFBBos);
                lottery_oldfb.halfFBBos.setData(data.halfFBBos);
            });
            lottery_oldfb.editForm.setData(row);
            lottery_oldfb.editWindow.show();
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
            mini.get("logoUrl").setValue("");
            mini.get("homeLogoUrl").setValue("");
            mini.get("guestLogoUrl").setValue("");
        }else{
            mini.alert("请选择一行数据！");
        }
    },
    setResult:function(fourGoal,fourteenWdf,sixHfWdf){
        try{
            $("#fourGoal").text(fourGoal);
            $("#fourteenWdf1").text(lottery_oldfb.getResult(fourteenWdf));
            $("#fourteenWdf2").text(lottery_oldfb.getResult(fourteenWdf));
            var sixHfWdfText = lottery_oldfb.getResult(sixHfWdf.substr(0,1))+" - "+lottery_oldfb.getResult(sixHfWdf.substr(1,2));
            $("#sixHfWdf").text(sixHfWdfText);
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
        '<a class="mini-button"  plain="true" href="javascript:lottery_oldfb.onrowdblclick()">详情</a>';

        if (grid.isEditingRow(record)) {
            var s = ' <a class="mini-button"  plain="true" href="javascript:lottery_oldfb.updateRow(\'datagrid\',\'' + uid + '\')" >保存</a>&nbsp;&nbsp;&nbsp;'+
                '<a class="mini-button"  plain="true" href="javascript:Cms.cancelEditRow(\'datagrid\',\'' + uid + '\')">取消</a>';
        }
        return s;
    },
    updateRow:function(gridname,uid){
    	
    	
    	Cms.rowFormValidate("#" + gridname, function() {
    		
    		 var row = lottery_oldfb.datagrid.getRowByUID(uid);
    	        lottery_oldfb.datagrid.commitEdit();
    	        var rowData = lottery_oldfb.datagrid.getChanges();
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
    	        Cms.save(json, "lotterymgr/old_fb", function(){
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
        Cms.submit(lottery_oldfb.editForm,function(){
            lottery_oldfb.editWindow.hide();
            lottery_oldfb.datagrid.reload();
        });
    },
    scoreValueChange:function(){
        var halfScore = mini.get("halfScore").getValue();
        var fullScore = mini.get("fullScore").getValue();
        var reg = /^[0-9]*:[0-9]*$/;
        if(!reg.test(halfScore)){
            alert("半场比分请输入正确比分格式！");
            return;
        }
        if(!reg.test(fullScore)){
            alert("全场比分请输入正确比分格式！");
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

        var fourGoal = homeFullScore + ':' +  guestFullScore;
        var fourteenWdf = "";
        var sixHfWdf = "";

        if(homeHalfScore>guestHalfScore){
            sixHfWdf+=Constants.win;
        }else if(homeHalfScore<guestHalfScore){
            sixHfWdf+=Constants.lost;
        }else if(homeHalfScore==guestHalfScore){
            sixHfWdf+=Constants.draw;
        }

        if(homeFullScore>guestFullScore){
            fourteenWdf = Constants.win;
            sixHfWdf+=Constants.win;
        }else if(homeFullScore<guestFullScore){
            fourteenWdf = Constants.lost;
            sixHfWdf+=Constants.lost;
        }else if(homeFullScore==guestFullScore){
            fourteenWdf = Constants.draw;
            sixHfWdf+=Constants.draw;
        }
        lottery_oldfb.setResult(fourGoal,fourteenWdf,sixHfWdf);
    },
    comboxOnCloseClick:function(e){
        var obj = e.sender;
        obj.setText("");
        obj.setValue("");
    },
    getMatch: function () {
        var param = {
            url: "lotterymgr/old_fb/getMatch",
            action: "get"
        };
        Cms.ajax(param, function (data) {
            alert(data.message);
            lottery_oldfb.datagrid.reload();
        })
    },
    excel:function(){
    	
    	var form = new mini.Form("#form");
		var data = form.getData();  
		
    	
    	location.href="lotterymgr/old_fb/export?"+Cms.jsonParamStr(data);;
    },
    runTask:function(type, eve){
    	var lotteryIssue = lottery_oldfb.lotteryIssue.getValue();
        var lotteryCode = lottery_oldfb.lotteryCode.getValue();
        var param = {
                url: "taskmgr/job/runTask",
                action: "get"
            };
        var message = "确定是否执行 <span style='color:red'>"+eve.text+"</span>?";
        switch (type) {
		case 1:
			//获取比赛按钮
			param.data =  {'jobId':'000030', "issueCode":lotteryIssue};
			break;
		case 2:
			//同步分析数据按钮
			if(!lotteryCode){
				mini.alert("请选择彩种!!!");
				return;
			}
			param.data =  {'jobId':'000031', 'matchType':1, 'lotteryCode':lotteryCode};
			break;
		case 3:	
		case 4:	
		case 5:	
		case 6:	
			mini.alert("此功能暂未实现");
			return;
		}
        Cms.ajaxFirm(message, param, function (data) {
        	Cms.showTips("执行成功!", 2000);
//            lottery_oldfb.datagrid.reload();
        })  	
    }
};
lottery_oldfb.init();
