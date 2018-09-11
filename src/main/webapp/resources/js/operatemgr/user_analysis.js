var UserAnalysisObj = {
		"init":function() {
			mini.parse();
			// 查询参数
			UserAnalysisObj.userQryType = mini.get("userQryType");
			UserAnalysisObj.userQryVal = mini.get("userQryVal");
			UserAnalysisObj.channelId = mini.get("channelId");
			UserAnalysisObj.startTime = mini.get("startTime");
			UserAnalysisObj.endTime = mini.get("endTime");
			// grid
			UserAnalysisObj.grid = mini.get("datagrid");
			// 上传窗口及参数
			UserAnalysisObj.upoladWindow=  mini.get("upoladWindow");
			UserAnalysisObj.uploadType = mini.get("uploadType");
			
			// 渠道来源（渠道号->渠道名字）
			ComReq.channel(function(result) {
				Dic.channelName = result;
			});
			// 字典数据
			ComReq.dictionary("0207", function(result) {
				 Dic.userQryType  = result["0207"]; // 用户查询类型
				 
				 UserAnalysisObj.userQryType.setData(Dic.userQryType);
				 UserAnalysisObj.userQryType.select(0);
				 UserAnalysisObj.search();
			});
		},
		"search":function() {
			UserAnalysisObj.grid.load(UserAnalysisObj.getParamJson());
		},
		"getParamJson":function() {
			var _userQryType = UserAnalysisObj.userQryType.getValue();
			var _userQryVal = UserAnalysisObj.userQryVal.getValue();
			var _channelId = UserAnalysisObj.channelId.getValue();
			var _startTime = UserAnalysisObj.startTime.getFormValue();
			var _endTime = UserAnalysisObj.endTime.getFormValue();
			// 查询条件(会员信息和钱包状态)
			var params = {
					channelId:_channelId,
					startTime:_startTime,
					endTime:_endTime
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
		"excel":function() {
			var param = Cms.jsonParamStr(UserAnalysisObj.getParamJson());
			var url = "operatemgr/useranalysis/excel?" + param;
			location = url;
		},
	    "uploadSearch":function() {
	    	 var form = new mini.Form("#searchForm");
	    	 form.clear()
	    	 UserAnalysisObj.uploadType.select(0);
	    	 UserAnalysisObj.upoladWindow.show("left","30px");
	     },
	     "ajaxFileUploadSearch":function(id) {
    	    var form = new mini.Form("#searchForm");
    	    form.validate();
		    if (form.isValid() == false) {
	             mini.alert("选择文件类型错误");
		    	 return;
		    }
    	    var type = UserAnalysisObj.uploadType.getValue();
			var h = "#"+id+" > input:file";
            Cms.upload(UserAnalysisObj.upoladWindow, h, "operatemgr/useranalysis/upload/search?type=" + type, function(data) {
            	UserAnalysisObj.upoladWindow.hide();
            	UserAnalysisObj.grid.setData(data);
            });
         }
}

// 初始化数据
UserAnalysisObj.init();