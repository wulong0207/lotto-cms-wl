var Draw = {
	/**
	 * 开奖号码验证
	 * 
	 * @param lotteryCode
	 *            彩种
	 * @param drawCode
	 *            开奖号码
	 * @returns {Boolean}
	 */
	"check" : function(lotteryCode, drawCode) {
		var isCorrect = false;
		switch (lotteryCode) {
		case "SSQ":
			isCorrect = Draw.ssq(drawCode);
			break;
		case "11X5":
			isCorrect = Draw.elevenFive(drawCode);
			break;
		case "SFC":
			isCorrect = Draw.sfc(drawCode);
			break;
		case "JQ4":
			isCorrect = Draw.jq4(drawCode);
			break;
		case "ZC6":
			isCorrect = Draw.zc6(drawCode);
			break;
		case "DLT":
			isCorrect = Draw.dlt(drawCode);
			break;
		case "QLC":
			isCorrect = Draw.qlc(drawCode);
			break;
		case "QXC":
			isCorrect = Draw.qxc(drawCode);
			break;
		case "15X5":
			isCorrect = Draw.fifteenFive(drawCode);
			break;
		case "PL3":
			isCorrect = Draw.pl3(drawCode);
			break;
		case "PL5":
			isCorrect = Draw.pl5(drawCode);
			break;
		case "SSC":
			isCorrect = Draw.ssc(drawCode);
			break;
		case "K3":
			isCorrect = Draw.k3(drawCode);
			break;
		case "CQKL10":
			isCorrect = Draw.cqkl10(drawCode);
			break;
		case "SDPOKER":
			isCorrect = Draw.sdpoker(drawCode);
			break;
		default:
			mini.alert("不存在改彩种验证规则", "开奖号码验证失败");
			break;
		}
		return isCorrect;
	},
	/**
	 * 双色球验证
	 * 
	 * @param code
	 * @returns {Boolean}
	 */
	"ssq" : function(code) {
		var all = code.split("|");
		var left = all[0].split(",");
		var right = [ all[1] ];
		if (CodeUtil.isRep(left) && CodeUtil.rangeNum(left, 1, 33)
				&& CodeUtil.codeLen(left, 2) && CodeUtil.codeLen(right, 2)
				&& CodeUtil.rangeNum(right, 1, 16)
				&& CodeUtil.arrayLen(left, 6)) {
			return true;
		} else {
			return false;
		}
	},
	"k3" : function(code) {
		var all = code.split(",");
		if (CodeUtil.rangeNum(all, 1, 6) && CodeUtil.codeLen(all, 1)
				&& CodeUtil.arrayLen(all, 3)) {
			return true;
		}
		return false;
	},
	/**
	 * 11选5
	 * 
	 * @param code
	 * @returns {Boolean}
	 */
	"elevenFive" : function(code) {
		var all = code.split(",");
		if (CodeUtil.isRep(all) && CodeUtil.rangeNum(all, 1, 11)
				&& CodeUtil.codeLen(all, 2) && CodeUtil.arrayLen(all, 5)) {
			return true;
		}
		return false;
	},
	/**
	 * 14场
	 * 
	 * @param code
	 * @returns {Boolean}
	 */
	"sfc" : function(code) {
		var all = code.split("|");
		if (CodeUtil.include(all, [ "0", "1", "3", "*", "_" ])
				&& CodeUtil.arrayLen(all, 14)) {
			return true;
		}
		return false;
	},
	/**
	 * 4场
	 * 
	 * @param code
	 * @returns {Boolean}
	 */
	"jq4" : function(code) {
		var all = code.split("|");
		if (CodeUtil.include(all, [ "0", "1", "2", "3", "*", "_" ])
				&& CodeUtil.arrayLen(all, 8)) {
			return true;
		}
		return false;
	},
	/**
	 * 6场半全场
	 * 
	 * @param code
	 * @returns {Boolean}
	 */
	"zc6" : function(code) {
		var all = code.split("|");
		if (CodeUtil.include(all, [ "0", "1", "3", "*", "_" ])
				&& CodeUtil.arrayLen(all, 12)) {
			return true;
		}
		return false;
	},
	/**
	 * 大乐透验证
	 * 
	 * @param code
	 * @returns {Boolean}
	 */
	"dlt" : function(code) {
		var all = code.split("|");
		var left = all[0].split(",");
		var right = all[1].split(",");
		if (CodeUtil.isRep(left) && CodeUtil.rangeNum(left, 1, 35)
				&& CodeUtil.codeLen(left, 2) && CodeUtil.arrayLen(left, 5)

				&& CodeUtil.isRep(right) && CodeUtil.rangeNum(right, 1, 12)
				&& CodeUtil.codeLen(right, 2) && CodeUtil.arrayLen(right, 2)) {
			return true;
		} else {
			return false;
		}
	},
	/**
	 * 七乐彩验证
	 * 
	 * @param code
	 * @returns {Boolean}
	 */
	"qlc" : function(code) {
		var all = code.split(",");
		if (CodeUtil.isRep(all) && CodeUtil.rangeNum(all, 1, 30)
				&& CodeUtil.codeLen(all, 2) && CodeUtil.arrayLen(all, 8)) {
			return true;
		}
		return false;
	},
	/**
	 * 七星彩
	 * 
	 * @param code
	 * @returns {Boolean}
	 */
	"qxc" : function(code) {
		var all = code.split("|");
		if (CodeUtil.rangeNum(all, 0, 9) && CodeUtil.codeLen(all, 1)
				&& CodeUtil.arrayLen(all, 7)) {
			return true;
		}
		return false;
	},
	/**
	 * 15选5
	 * 
	 * @param code
	 * @returns {Boolean}
	 */
	"fifteenFive" : function(code) {
		var all = code.split(",");
		if (CodeUtil.isRep(all) && CodeUtil.rangeNum(all, 1, 15)
				&& CodeUtil.codeLen(all, 2) && CodeUtil.arrayLen(all, 5)) {
			return true;
		}
		return false;
	},
	/**
	 * 排列3
	 * 
	 * @param code
	 * @returns {Boolean}
	 */
	"pl3" : function(code) {
		var all = code.split("|");
		if (CodeUtil.rangeNum(all, 0, 9) && CodeUtil.codeLen(all, 1)
				&& CodeUtil.arrayLen(all, 3)) {
			return true;
		}
		return false;
	},
	/**
	 * 排列5
	 * 
	 * @param code
	 * @returns {Boolean}
	 */
	"pl5" : function(code) {
		var all = code.split("|");
		if (CodeUtil.rangeNum(all, 0, 9) && CodeUtil.codeLen(all, 1)
				&& CodeUtil.arrayLen(all, 5)) {
			return true;
		}
		return false;
	},
	/**
	 * 时时彩
	 * 
	 * @param code
	 */
	"ssc" : function(code) {
		return Draw.pl5(code);
	},
	/**
	 * 快乐10分
	 * 
	 * @param code
	 */
	"cqkl10" : function(code) {
		var all = code.split(",");
		if (CodeUtil.isRep(all) && CodeUtil.rangeNum(all, 1, 20)
				&& CodeUtil.codeLen(all, 2) && CodeUtil.arrayLen(all, 8)) {
			return true;
		}
		return false;
	},
	"sdpoker" : function(code) {
		var all = code.split("|");
		if (all[0] == all[1] || all[0] == all[2] || all[1] == all[2]) {
			return false;
		}
		if (CodeUtil.arrayLen(all, 3)) {
			for (var i = 0; i < all.length; i++) {
				var str = all[i].split("_");
				if (str.length != 2) {
					return false;
				}
				var num1 = Number(str[0]);
				if (num1 >= 1 && num1 <= 4) {
					switch (str[1]) {
					case "2":
					case "3":
					case "4":
					case "5":
					case "6":
					case "7":
					case "8":
					case "9":
					case "10":
					case "J":
					case "Q":
					case "K":
					case "A":
						continue;
					default:
						break;
					}
					return false;
				}
				return false;
			}
			return true;
		}
		return false;
	}
}
/**
 * 工具类
 */
var CodeUtil = {
	/**
	 * 验证重复
	 * 
	 * @param code
	 * @returns {Boolean} 不重复返回true
	 */
	"isRep" : function(code) {
		for (i = 0; i < code.length; i++) {
			for (j = i + 1; j < code.length; j++) {
				if (code[i] == code[j]) {
					mini.alert(code[i] + "与" + code[j] + "重复", "开奖号码格式不正确");
					return false;
				}
			}
		}
		return true;
	},
	/**
	 * 验证号码范围
	 * 
	 * @param code
	 * @param min
	 * @param max
	 * @returns {Boolean} 在范围类 true
	 */
	"rangeNum" : function(code, min, max) {
		for (i = 0; i < code.length; i++) {
			var num = Number(code[i]);
			if (num > max || num < min) {
				mini.alert(code[i] + "只能在" + min + "-" + max + "之间",
						"开奖号码格式不正确");
				return false;
			}
		}
		return true;
	},
	/**
	 * 验证号码长度
	 * 
	 * @param code
	 * @param len
	 * @returns {Boolean} 合法返回true
	 */
	"codeLen" : function(code, len) {
		for (i = 0; i < code.length; i++) {
			if (code[i].length != len) {
				mini.alert(code[i] + "长度不等于" + len, "开奖号码格式不正确");
				return false;
			}
		}
		return true;
	},
	"include" : function(code, incl) {
		for (i = 0; i < code.length; i++) {
			var isIn = false;
			for (j = 0; j < incl.length; j++) {
				if (code[i] == incl[j]) {
					isIn = true;
					break;
				}
			}
			if (!isIn) {
				mini.alert(code[i] + "只能包含" + incl.toString(), "开奖号码格式不正确");
				return false;
			}
		}
		return true;
	},
	"arrayLen" : function(code, len) {
		if (code.length != len) {
			mini.alert("个数不正确", "开奖号码格式不正确");
			return false;
		}
		return true;
	}
}
var Current = {
	"init" : function() {

	},
	"check" : function() {
		var drawCode = mini.get("drawCode").getValue();
		if (!drawCode) {
			mini.alert("该彩期没有开奖号码！")
			return;
		}
		var _lotteryCode = mini.get("lotteryCode_edit").getValue();
		var _issueCode = mini.get("issueCode_edit").getValue();
		var id = mini.get("id").getValue();
		mini.confirm("确定审核开奖号码<span style=\"color: red;\">" + drawCode
				+ "</span>", "提示", function(e) {
			if (e == "ok") {
				var param = {
					url : "lotterymgr/issue/audit/code",
					data : {
						id : id,
						drawCode : drawCode,
						lotteryCode : _lotteryCode,
						issueCode : _issueCode
					},
					action : "post"
				}
				Cms.ajax(param, function() {
					mini.alert("审核成功", "提醒", function() {
						// Current.cancel();
					});
				});
			}
		});
	},
	"drowCode" : function() {
		var _lotteryCode = mini.get("lotteryCode_edit").getValue();
		var _lotteryName = mini.get("lotteryName_edit").getValue();
		var _issueCode = mini.get("issueCode_edit").getValue();
		if (!_lotteryCode || !_issueCode) {
			mini.alert("手动开奖的彩种和彩期不能为空！")
			return;
		}
		mini.confirm("确定对<span style=\"color: red;\">" + _lotteryName
				+ "</span>的<span style=\"color: red;\">" + _issueCode
				+ "</span>期手动开奖吗？", "提示", function(e) {
			if (e == "ok") {
				var param = {
					url : "lotterymgr/issue/draw/code",
					data : {
						lotteryCode : _lotteryCode,
						issueCode : _issueCode
					},
					action : "post"
				}
				Cms.ajax(param, function(data) {
					Cms.drawSchedule(_lotteryCode, _issueCode, 1, function() {

					});
				});
			}
		});
	},
	"closeWindow" : function(action) {
		if (window.CloseOwnerWindow)
			return window.CloseOwnerWindow(action);
		else
			window.close();
	},
	"cancel" : function() {
		Current.closeWindow("cancel");
	},
	"analysisDrawDetail" : function(row) {
		var drawDetail = [];
		if (row.drawDetail) {
			drawDetail = row.drawDetail.split(",");
		}
		var length = drawDetail.length;
		for (i = 0; i < Current.name.length; i++) {
			if (i < length) {
				var de = drawDetail[i].split("|");
				row["name" + i] = de[0];
				row["stake" + i] = de[1];
				row["money" + i] = de[2];
				row["moneyAdd" + i] = de[3];
			} /*
				 * else { row["name" + i] = Current.name[i]; if(Current.money){
				 * row["money" + i] = Current.money[i]; } }
				 */

		}
	},
	"analysisDrawDetail_2" : function(row) {
		var drawDetail = [];
		if (row.drawDetail) {
			drawDetail = row.drawDetail.split(",");
		}
		var length = drawDetail.length;
		for (i = 0; i < Current.name.length; i++) {
			if (i < length) {
				var de = drawDetail[i].split("|");
				row["name" + i] = de[0];
				row["money" + i] = de[1];
				row["moneyAdd" + i] = de[2];
			} /*
				 * else { row["name" + i] = Current.name[i]; if(Current.money){
				 * row["money" + i] = Current.money[i]; } }
				 */
		}
	},
	"analysisDrawDetail_dlt" : function(row) {

		var drawDetail = [];
		if (row.drawDetail) {
			drawDetail = row.drawDetail.split(",");
		}
		var length = drawDetail.length;

		for (i = 0; i < Current.name.length; i++) {
			if (i < length) {
				var de = drawDetail[i].split("|");
				row["name" + i] = de[0];
				row["stake" + i] = de[1];
				row["money" + i] = de[2];
				row["stakeAdd" + i] = de[3];
				row["moneyAdd" + i] = de[4];
			}/*
				 * else{ row["name" + i] = Current.name[i]; if(Current.money){
				 * row["money" + i] = Current.money[i]; } }
				 */
		}
	},
	"joinDrawDetail" : function(num) {
		var drawDetail = "";
		for (i = 0; i < num; i++) {
			var name = $("#issueForm").find("input[name=name" + i + "]").val();
			var stake = $("#issueForm").find("input[name=stake" + i + "]")
					.val();
			var money = $("#issueForm").find("input[name=money" + i + "]")
					.val();
			var moneyAdd = $("#issueForm")
					.find("input[name=moneyAdd" + i + "]").val();
			if (!moneyAdd) {
				moneyAdd = 0;
			}
			if (i > 0) {
				drawDetail += ",";
			}
			drawDetail += name + "|" + stake + "|" + money + "|" + moneyAdd;
		}
		return drawDetail;
	},
	"joinDrawDetail_2" : function(num) {
		var drawDetail = "";
		for (i = 0; i < drawDetailNum; i++) {
			var name = $("#issueForm").find("input[name=name" + i + "]").val();
			var money = $("#issueForm").find("input[name=money" + i + "]")
					.val();
			var moneyAdd = $("#issueForm")
					.find("input[name=moneyAdd" + i + "]").val();
			if (!moneyAdd) {
				moneyAdd = 0;
			}
			if (i > 0) {
				drawDetail += ",";
			}
			drawDetail += name + "|" + money + "|" + moneyAdd;
		}
		return drawDetail;
	},
	"joinDrawDetail_dlt" : function(num) {
		var drawDetail = "";
		for (i = 0; i < drawDetailNum; i++) {
			var name = $("#issueForm").find("input[name=name" + i + "]").val();
			var stake = $("#issueForm").find("input[name=stake" + i + "]")
					.val();
			var money = $("#issueForm").find("input[name=money" + i + "]")
					.val();
			var stakeAdd = $("#issueForm")
					.find("input[name=stakeAdd" + i + "]").val();
			var moneyAdd = $("#issueForm")
					.find("input[name=moneyAdd" + i + "]").val();

			if (!moneyAdd) {
				moneyAdd = 0;
			}
			if (i > 0) {
				drawDetail += ",";
			}
			drawDetail += name + "|" + stake + "|" + money + "|" + stakeAdd
					+ "|" + moneyAdd;
		}
		return drawDetail;
	},
	"submit" : function(form) {
		var se = mini.get("salesAmount_edit").getValue();
		var je = mini.get("jackpotAmount_edit").getValue();
		var msg;
		if (!se || !je) {
			msg = "<span style=\"color: red;\">销售总额</span>与<span style=\"color: red;\">奖池金额" +
					"</span>为<span style=\"color: red;\">空</span>，是否继续保存？";
		}
		Cms.submit(form, function() {

		}, msg);
	}

}
Current.init();