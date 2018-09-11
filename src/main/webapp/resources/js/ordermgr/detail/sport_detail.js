var Sport_Detail = {
	
	init : function(){
		mini.parse();
		Sport_Detail.form = new mini.Form("#editform");
		Sport_Detail.oldFbEditWindow = mini.get("oldFbEditWindow");
		Sport_Detail.fbEditWindow = mini.get("fbEditWindow");
		Sport_Detail.bbEditWindow = mini.get("bbEditWindow");
		Sport_Detail.bjEditWindow = mini.get("bjEditWindow");
		Sport_Detail.wfEditWindow = mini.get("wfEditWindow");
		Sport_Detail.lotteryCode_edit = mini.get("lotteryCode_edit");
		Sport_Detail.lotteryIssue = mini.get("lotteryIssue");
		Sport_Detail.lotteryChildCode = mini.get("lotteryChildCode");
		Sport_Detail.buyScreen = mini.get("buyScreen");
		
		Sport_Detail.matchResult = {"0":"负","1":"平","3":"胜"};
	},	
	converRender : function(e) {
		// 父标签对象
		var grid = e.sender;
		// 单条标签数据
		var row = e.record;
		var html = ' <a plain="true" href="javascript:Sport_Detail.toShow(\'' + row.planContent + '\');" >查看</a>';
		return html;
	},
	toShow : function(planContent) {
		var lotteryCode = Sport_Detail.lotteryCode_edit.getValue();
		switch (lotteryCode) {
		case 300:
			Sport_Detail.fbConver(planContent);
			break;
		case 301:
			Sport_Detail.bbConver(planContent);
			break;
		case 302:
		case 303:
			return;
		case 304:	
		case 305:
			Sport_Detail.oldFbConver(planContent);
			break;
		case 306:
			Sport_Detail.bjConver(planContent);
			break;
		case 307:
			Sport_Detail.wfConver(planContent);
			break;
		}
	},
	oldFbBetContent : function(e){
		var str = "";
		var row = e.record;
		var matchBuyWayList = row.matchBuyWayList;
		$.each(matchBuyWayList, function(i, buyWay){
			str += buyWay.buyWayName + ",";
		})
		str = str.substring(0 , str.length - 1);
		return str;	
	},	
	oldFbWinInfo : function(e){
	  var row = e.record;
	  return row.wdfText;		
	},
	fbBetContent : function(e){
		var str = "";
		var row = e.record;
		var matchChildPalyList = row.matchChildPlayList;
		$.each(matchChildPalyList, function(i, child){
			str += "[";
			var matchBuyWayList = child.matchBuyWayList;
			$.each(matchBuyWayList, function(i, buyWay){
				str += buyWay.buyWayName + "@" + buyWay.buySp + ",";
			})
			str = str.substring(0 , str.length - 1);
			str += "],"
		})
		str = str.substring(0 , str.length - 1);
		return str;
	},
	fbWinInfo : function(e){
		var str = "";
		var row = e.record;
		var matchChildPalyList = row.matchChildPlayList;
		$.each(matchChildPalyList, function(i, child){
			switch (child.playWay) {
			case 'S':
				str += row.wdfText == null ? '' : (row.wdfText + ",");
				break;
			case 'R':
				str += row.letWdfText == null ? '' : (row.letWdfText + ",");
				break;
			case 'Q':
				str += row.scoreText == null ? '' : (row.scoreText + ",");
				break;
			case 'Z':
				str += row.goalNumText == null ? '' : (row.goalNumText + ",");
				break;
			case 'B':
				str += row.hfWdfText == null ? '' : (row.hfWdfText + ",");
				break;
			}
		})
		str = str.substring(0 , str.length - 1);
		return str;
	},
	bbBetContent : function(e){
		var str = "";
		var row = e.record;
		var matchChildPalyList = row.matchChildPlayList;
		$.each(matchChildPalyList, function(i, child){
			str += "[";
			var matchBuyWayList = child.matchBuyWayList;
			$.each(matchBuyWayList, function(i, buyWay){
				str += buyWay.buyWayName + "@" + buyWay.buySp + ",";
			})
			str = str.substring(0 , str.length - 1);
			str += "],"
		})
		str = str.substring(0 , str.length - 1);
		return str;
	},	
	bbWinInfo : function(e){
		var str = "";
		var row = e.record;
		var matchChildPalyList = row.matchChildPlayList;
		$.each(matchChildPalyList, function(i, child){
			switch (child.playWay) {
			case 'S':
				str += row.wdfText == null ? '' : (row.wdfText + ",");
				break;
			case 'R':
				str += row.letWdfText == null ? '' : (row.letWdfText + ",");
				break;
			case 'C':
				str += row.winScoreText == null ? '' : (row.winScoreText + ",");
				break;
			case 'D':
				str += row.scoreText == null ? '' : (row.scoreText + ",");
				break;
			}
		})
		str = str.substring(0 , str.length - 1);
		return str;	
	},
	bjBetContent : function(e){
		var str = "";
		var row = e.record;
		var matchChildPalyList = row.matchChildPlayList;
		$.each(matchChildPalyList, function(i, child){
			str += "[";
			var matchBuyWayList = child.matchBuyWayList;
			$.each(matchBuyWayList, function(i, buyWay){
				str += buyWay.buyWayName + "@" + buyWay.buySp + ",";
			})
			str = str.substring(0 , str.length - 1);
			str += "],"
		})
		str = str.substring(0 , str.length - 1);
		return str;
	},
	bjWinInfo : function(e){
		var str = "";
		var row = e.record;
		var matchChildPalyList = row.matchChildPlayList;
		$.each(matchChildPalyList, function(i, child){
			switch (child.playWay) {
			case 'R':
				str += row.letWdfText == null ? '' : (row.letWdfText + "(" + row.letWdfSp + ")" + ",");
				break;
			case 'Q':
				str += row.scoreText == null ? '' : (row.scoreText + "(" + row.scoreSp + ")" + ",");
				break;
			case 'Z':
				str += row.goalNumText == null ? '' : (row.goalNumText + "(" + row.goalNumSp + ")" + ",");
				break;
			case 'B':
				str += row.hfWdfText == null ? '' : (row.hfWdfText + "(" + row.hfWdfSp + ")" + ",");
				break;
			case 'D':
				str += row.udsdText == null ? '' : (row.udsdText + "(" + row.udsdSp + ")" + ",");
				break;				
			}
		})
		str = str.substring(0 , str.length - 1);
		return str;
	},	
	wfBetContent : function(e){
		var str = "";
		var row = e.record;
		var matchChildPalyList = row.matchChildPlayList;
		$.each(matchChildPalyList, function(i, child){
			str += "[";
			var matchBuyWayList = child.matchBuyWayList;
			$.each(matchBuyWayList, function(i, buyWay){
				str += buyWay.buyWayName + "@" + buyWay.buySp + ",";
			})
			str = str.substring(0 , str.length - 1);
			str += "],"
		})
		str = str.substring(0 , str.length - 1);
		return str;
	},
	wfWinInfo : function(e){
		var str = "";
		var row = e.record;
		var matchChildPalyList = row.matchChildPlayList;
		$.each(matchChildPalyList, function(i, child){
			str += row.letWdfText == null ? '' : (row.letWdfText + "(" + row.letWdfSp + ")" + ",");
		})
		str = str.substring(0 , str.length - 1);
		return str;
	},	
	oldFbConver : function(planContent){
		Sport_Detail.oldFbEditWindow.show();
		var lotteryCode = Sport_Detail.lotteryCode_edit.getValue();
		var issueCode = Sport_Detail.lotteryIssue.getValue();
	
		if(lotteryCode == 305)
			lotteryCode = 304;
		var match_param ={
				lotteryCode:lotteryCode,
				issueCode:issueCode,
				content : planContent
     	}
		$.post("lotterymgr/old_fb/order/matchlist", match_param, function(data){
			mini.get("oldFbContentGrid").setData(data);
		}, "json")		
	},
	fbConver : function(planContent) {
		var lotteryChildCode = Sport_Detail.lotteryChildCode.getValue();
		var lotteryCode = Sport_Detail.lotteryCode_edit.getValue();
		var buyScreen = Sport_Detail.buyScreen.getValue();
		Sport_Detail.fbEditWindow.show();
		var match_param = {
			lotteryCode : lotteryCode,
			lotteryChildCode : lotteryChildCode,
			systemCode : buyScreen,
			content : planContent
		}
		$.post("lotterymgr/fb/order/matchlist", match_param, function(data) {
			mini.get("fbcontentgrid").setData(data);
		}, "json")

	},
	bbConver : function(planContent){
		var lotteryChildCode = Sport_Detail.lotteryChildCode.getValue();
		var lotteryCode = Sport_Detail.lotteryCode_edit.getValue();
		var buyScreen = Sport_Detail.buyScreen.getValue();
		Sport_Detail.bbEditWindow.show();
		var match_param = {
				lotteryCode : lotteryCode,
				lotteryChildCode : lotteryChildCode,
				systemCode : buyScreen,
				content : planContent
			}
		$.post("lotterymgr/bb/order/matchlist", match_param, function(data) {
			mini.get("bbcontentgrid").setData(data);
		}, "json")		
	},
	bjConver: function(planContent){
		var lotteryChildCode = Sport_Detail.lotteryChildCode.getValue();
		var lotteryCode = Sport_Detail.lotteryCode_edit.getValue();
		var buyScreen = Sport_Detail.buyScreen.getValue();
		Sport_Detail.bjEditWindow.show();
		var match_param = {
				lotteryCode : lotteryCode,
				lotteryChildCode : lotteryChildCode,
				systemCode : buyScreen,
				content : planContent
			}
		$.post("lotterymgr/bj/order/matchlist", match_param, function(data) {
			mini.get("bjcontentgrid").setData(data);
		}, "json")			
	},
	wfConver: function(planContent){
		var lotteryChildCode = Sport_Detail.lotteryChildCode.getValue();
		var lotteryCode = Sport_Detail.lotteryCode_edit.getValue();
		var buyScreen = Sport_Detail.buyScreen.getValue();
		Sport_Detail.wfEditWindow.show();
		var match_param = {
				lotteryCode : lotteryCode,
				lotteryChildCode : lotteryChildCode,
				systemCode : buyScreen,
				content : planContent
			}
		$.post("lotterymgr/wf/order/matchlist", match_param, function(data) {
			mini.get("wfcontentgrid").setData(data);
		}, "json")			
	},	
	onIsDanRenderer : function (e) {
        if (e.value == 1) return "胆";
        else return "";
    }

}
Sport_Detail.init();