var SportUtil = {
	"getMatchName" : function(e) {
		var record = e.record;
		var matchFullName = record.matchFullName;
		var s = "";
		if (StringUtils.isBank(matchFullName)) {
			s = record.matchName;
		} else {
			s = matchFullName;
		}
		return s;
	},
	"getHomeName" : function(e) {
		var record = e.record;
		var homeTeamFullName = record.homeTeamFullName;
		var s = "";
		if (StringUtils.isBank(homeTeamFullName)) {
			s = record.homeName;
		} else {
			s = homeTeamFullName;
		}
		return s;
	},
	"getGuestName" : function(e) {
		var record = e.record;
		var guestTeamFullName = record.guestTeamFullName;
		var s = "";
		if (StringUtils.isBank(guestTeamFullName)) {
			s = record.guestName;
		} else {
			s = guestTeamFullName;
		}
		return s;
	},
	"onSportCellBeingEdit" : function(e) {
		var record = e.record;
		var field = e.field;
		var editor = e.editor;
		var url, value;
		var matchType = typeof(record.matchType) == "undefined" ? "" : record.matchType;
		if (field == "matchFullName") {// 赛事全称
			url = "sportmgr/base/findMatchInfo?matchType=" + matchType;
			value = SportUtil.getMatchName(e);
		} else if (field == "homeTeamFullName") {// 主队全称
			url = "sportmgr/base/findTeamInfo?teamType=" + matchType;
			value =SportUtil.getHomeName(e);
		} else if (field == "guestTeamFullName") {// 客队全称
			url = "sportmgr/base/findTeamInfo?teamType=" + matchType;
			value = SportUtil.getGuestName(e);
		}
		if (url) {
			editor.setUrl(url);
			editor.setText(value);
			editor.setValue(value);
		}
	},
	"onSportFullNameChanged" : function(e, field) {
		var sender = e.sender;
		var data = e.item;
		if (typeof (data) == "undefined") {
			return;
		}
		var grid = sender.getParent();
		var row = grid.getEditorOwnerRow(sender);
		if (field == "matchFullName") {
			var shortNameEditor = grid.getCellEditor("matchShortName", row);
			shortNameEditor.setValue(data.matchShortName ? data.matchShortName : "");
			var matchPrimaryIdEditor = grid.getCellEditor("matchPrimaryId", row);
			matchPrimaryIdEditor.setValue(data.id);
		} else if (field == "homeTeamFullName") {
			var shortNameEditor = grid.getCellEditor("homeTeamShortName", row);
			shortNameEditor.setValue(data.teamShortName ? data.teamShortName : "");
			var homeTeamPrimaryIdEditor = grid.getCellEditor("homeTeamPrimaryId", row);
			homeTeamPrimaryIdEditor.setValue(data.id);
		} else if (field == "guestTeamFullName") {
			var shortNameEditor = grid.getCellEditor("guestTeamShortName", row);
			shortNameEditor.setValue(data.teamShortName ? data.teamShortName : "");
			var guestTeamPrimaryIdEditor = grid.getCellEditor("guestTeamPrimaryId", row);
			guestTeamPrimaryIdEditor.setValue(data.id);
		}
	},
	"onMatchCellValidation" : function(e) {
		if (!e.isValid) {
			return;
		}
		var sender = e.sender;
		var grid = sender.getParent();
		var row = grid.getEditorOwnerRow(sender);
		var matchPrimaryIdEditor = grid.getCellEditor("matchPrimaryId", row);
		var matchPrimaryId = matchPrimaryIdEditor.getValue();
		if(StringUtils.isBank(matchPrimaryId)) {
			e.isValid = false;
			e.errorText = "没有选择赛事！";
			return;
		}
	},
	"onHomeTeamCellValidation" : function(e) {
		if (!e.isValid) {
			return;
		}
		var sender = e.sender;
		var grid = sender.getParent();
		var row = grid.getEditorOwnerRow(sender);
		var homeTeamPrimaryIdEditor = grid.getCellEditor("homeTeamPrimaryId", row);
		var homeTeamPrimaryId = homeTeamPrimaryIdEditor.getValue();
		if(StringUtils.isBank(homeTeamPrimaryId)) {
			e.isValid = false;
			e.errorText = "主队没有选择球队！";
			return;
		}
		var guestTeamPrimaryIdEditor = grid.getCellEditor("guestTeamPrimaryId", row);
		var guestTeamPrimaryId = guestTeamPrimaryIdEditor.getValue();
		if(homeTeamPrimaryId == guestTeamPrimaryId) {
			e.isValid = false;
			e.errorText = "主队和客队不能相同！";
			return;
		}
	},
	"onGuestTeamCellValidation" : function(e) {
		if (!e.isValid) {
			return;
		}
		var sender = e.sender;
		var grid = sender.getParent();
		var row = grid.getEditorOwnerRow(sender);
		var guestTeamPrimaryIdEditor = grid.getCellEditor("guestTeamPrimaryId", row);
		var guestTeamPrimaryId = guestTeamPrimaryIdEditor.getValue();
		if(StringUtils.isBank(guestTeamPrimaryId)) {
			e.isValid = false;
			e.errorText = "客队没有选择球队！";
			return;
		}
		var homeTeamPrimaryIdEditor = grid.getCellEditor("homeTeamPrimaryId", row);
		var homeTeamPrimaryId = homeTeamPrimaryIdEditor.getValue();
		if(homeTeamPrimaryId == guestTeamPrimaryId) {
			e.isValid = false;
			e.errorText = "主队和客队不能相同！";
			return;
		}
	}
};
mini.VTypes["scoreErrorText"] = "比分格式错误";
mini.VTypes["score"] = function(v) {
	var reg = /^[0-9]+:[0-9]+$/;
	if (typeof (v) != 'undefined' && v != '' && !reg.test(v)) {
		return false;
	}
	return true;
}