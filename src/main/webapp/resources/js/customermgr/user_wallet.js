var WalletObj = {
		"init":function() {
			mini.parse();
			// 查询参数
			WalletObj.userQryType = mini.get("userQryType");
			WalletObj.userQryVal = mini.get("userQryVal");
			WalletObj.status = mini.get("status");
			// grid
			WalletObj.grid = mini.get("datagrid");
			// 主页查询统计数据
			WalletObj.other;
			// 上传窗口及参数
			WalletObj.upoladWindow=  mini.get("upoladWindow");
			WalletObj.uploadType = mini.get("uploadType");
			
			// 字典数据
			ComReq.dictionary("0206,0207", function(result) {
				 Dic.walletStatus = result["0206"]; // 钱包状态
				 Dic.userQryType  = result["0207"]; // 用户查询类型
				 
				 WalletObj.userQryType.setData(Dic.userQryType);
				 WalletObj.status.setData(Dic.walletStatus);
				 WalletObj.userQryType.select(0);
				 WalletObj.search();
			});
		},
		"search":function() {
			WalletObj.grid.load(WalletObj.getParamJson());
		},
		"getParamJson":function() {
			var _userQryType = WalletObj.userQryType.getValue();
			var _userQryVal = WalletObj.userQryVal.getValue();
			var _status = WalletObj.status.getValue();
			// 查询条件(会员信息和钱包状态)
			var params = {
					status:_status
			};
			// 用户查询类型(有查询值才处理该参数)
			if(_userQryVal) {
				switch (_userQryType) {
				case "1":
					params["user.nickName"] = _userQryVal;
					break;
				case "2":
					params["user.accountName"] = _userQryVal;
					break;
				case "3":
					params["user.cusMobile"] = _userQryVal;
					break;
				case "4":
					params["user.actualName"] = _userQryVal;
					break;
				case "5":
					params["user.idNum"] = _userQryVal;
					break;
				}
			}
			return params;
		},
		/**
		 * 画汇总单元格内容
		 */
		"drawSummaryCell":function(e) {
			var index = e.column._index;
			// 按指定列统计的，统计列与汇总列对齐(一一对应)
			if(index == 0) {
				WalletObj.other = e.result.other;
				e.cellHtml="统"
			} else if(index == 1) {
				e.cellHtml="计："
			} else if (e.field == "user.accountName") { 
				e.cellHtml = "<input type=\"radio\" name=\"sis\" checked=\"checked\" onclick=\"WalletObj.getChangeSis('0')\" />单页统计";
			} else if(e.field == "user.nickName") {
				e.cellHtml = "<input type=\"radio\" name=\"sis\" onclick=\"WalletObj.getChangeSis('1')\"/>条件统计";
			} else if(e.field == "totalCashBalance") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisTotalCashBalance\">" + numUtils.fmoney(e.result.other[0], 2) + "</span>";
			} else if(e.field == "winningBalance") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisWinningBalance\">" + numUtils.fmoney(e.result.other[1], 2) + "</span>";
			} else if(e.field == "top80Balance") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisTop80Balance\">" + numUtils.fmoney(e.result.other[2], 2) + "</span>";
			} else if(e.field == "top20Balance") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisTop20Balance\">" + numUtils.fmoney(e.result.other[3], 2) + "</span>";
			} else if(e.field == "effRedBalance") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisEffRedBalance\">" + numUtils.fmoney(e.result.other[4], 2) + "</span>";
			} else if(e.field == "readyRedBalance") {
				e.cellStyle = "border-left:#d2d2d2 1px solid;border-right:#d2d2d2 1px solid;text-align:center;";
				e.cellHtml = "<span id =\"sisReadyRedBalance\">" + numUtils.fmoney(e.result.other[5], 2) + "</span>";
			}
		},
		"getChangeSis":function(type) {
			if(WalletObj.other) {
				if("0" == type) {
					$("#sisTotalCashBalance").html(numUtils.fmoney(WalletObj.other[0], 2));
					$("#sisWinningBalance").html(numUtils.fmoney(WalletObj.other[1], 2));
					$("#sisTop80Balance").html(numUtils.fmoney(WalletObj.other[2], 2));
					$("#sisTop20Balance").html(numUtils.fmoney(WalletObj.other[3], 2));
					$("#sisEffRedBalance").html(numUtils.fmoney(WalletObj.other[4], 2));
					$("#sisReadyRedBalance").html(numUtils.fmoney(WalletObj.other[5], 2));
				} else {
					$("#sisTotalCashBalance").html(numUtils.fmoney(WalletObj.other[6], 2));
					$("#sisWinningBalance").html(numUtils.fmoney(WalletObj.other[7], 2));
					$("#sisTop80Balance").html(numUtils.fmoney(WalletObj.other[8], 2));
					$("#sisTop20Balance").html(numUtils.fmoney(WalletObj.other[9], 2));
					$("#sisEffRedBalance").html(numUtils.fmoney(WalletObj.other[10], 2));
					$("#sisReadyRedBalance").html(numUtils.fmoney(WalletObj.other[11], 2));
				}
			}
		},
		"excel":function() {
			var param = Cms.jsonParamStr(WalletObj.getParamJson());
			var url = "customermgr/user/wallet/excel?"+param;
			location = url;
		},
		"enableWallet":function() {
			// 批量启用
			Cms.batchHandle(WalletObj.grid, {
				idField : "id",
				separate : ",",
				url	: "customermgr/user/wallet/enableWallet",
				type : "post"
			});
		},
		"disableWallet":function() {
			// 批量禁用
			Cms.batchHandle(WalletObj.grid, {
				idField : "id",
				separate : ",",
				url	: "customermgr/user/wallet/disableWallet",
				type : "post"
			});
		},
	    "uploadSearch":function() {
	    	 var form = new mini.Form("#searchForm");
	    	 form.clear()
	    	 WalletObj.uploadType.select(0);
	    	 WalletObj.upoladWindow.show("left","30px");
	     },
	     "ajaxFileUploadSearch":function(id) {
    	    var form = new mini.Form("#searchForm");
    	    form.validate();
		    if (form.isValid() == false) {
	             mini.alert("选择文件类型错误");
		    	 return;
		    }
    	    var type = WalletObj.uploadType.getValue();
			var h = "#"+id+" > input:file";
            Cms.upload(WalletObj.upoladWindow, h, "customermgr/user/wallet/upload/search?type=" + type, function(data) {
            	//mini.get("fileSearch").setText("");
            	WalletObj.upoladWindow.hide();
            	WalletObj.grid.setData(data);
            });
         }
}

// 初始化数据
WalletObj.init();