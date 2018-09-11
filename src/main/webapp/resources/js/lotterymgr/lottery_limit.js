var LimitObj = {
		"init" : function() {
			mini.parse();
			// 主页查询条件
			LimitObj.lotteryCategory = mini.get("lotteryCategory");
			LimitObj.lotteryCode = mini.get("lotteryCode");
			LimitObj.issueStart = mini.get("issueStart");
			LimitObj.issueEnd = mini.get("issueEnd");
			LimitObj.timeStart = mini.get("timeStart");
			LimitObj.timeEnd = mini.get("timeEnd");
			LimitObj.qryStartTime = mini.get("qryStartTime");
			LimitObj.qryEndTime = mini.get("qryEndTime");
			LimitObj.status = mini.get("status");
			LimitObj.limitType = mini.get("limitType");
			// 限号新增修改字段
			LimitObj.limitId_edit = mini.get("limitId_edit");
			LimitObj.lotteryCategory_edit = mini.get("lotteryCategory_edit");
			LimitObj.lotteryCode_edit = mini.get("lotteryCode_edit");
			LimitObj.status_edit = mini.get("status_edit");
			LimitObj.limitType_edit = mini.get("limitType_edit");
			LimitObj.issueStart_edit = mini.get("issueStart_edit");
			LimitObj.issueEnd_edit = mini.get("issueEnd_edit");
			LimitObj.timeStart_edit = mini.get("timeStart_edit");
			LimitObj.timeEnd_edit = mini.get("timeEnd_edit");
			
			// 主页控件
			LimitObj.grid = mini.get("datagrid");// 限号列表grid
			LimitObj.editWindow = mini.get("editWindow");// 编辑窗体
			LimitObj.limitInfoGrid = mini.get("limitInfoGrid");// 限号内容grid
			
			
			// 数据字典加载
			ComReq.dictionary("0303,0308,0309,0310", function(result) {
				 Dic.lotteryCategory = result["0303"];// 彩种类型
				 Dic.limitStatus = result["0308"];// 限号状态
				 Dic.limitType = result["0309"];// 限号类型
				 Dic.limitInfoStatus = result["0310"];// 限号内容状态
				 
				 LimitObj.lotteryCategory.setData(Dic.lotteryCategory);
				 LimitObj.lotteryCategory_edit.setData(Dic.lotteryCategory);
				 LimitObj.status.setData(Dic.limitStatus);
				 LimitObj.status_edit.setData(Dic.limitStatus);
				 LimitObj.limitType.setData(Dic.limitType);
				 LimitObj.limitType_edit.setData(Dic.limitType);
			});
			
			// 所有彩种加载
			ComReq.lottery("",function(result) {
				 Dic.allCode = result;
				 
				 LimitObj.lotteryCode.setData(result);
				 LimitObj.lotteryCode_edit.setData(result);
			});
			
			// 关联彩种子玩法
			ComReq.lotteryChild("", function(result) {
				Dic.lotteryChild = result;
			});
		},
		"search" : function() {
		    if (MiniCom.isValidForm("searchForm") == false) {
	             mini.alert("请输入正确的查询条件！");
		    	 return;
		    }
			LimitObj.grid.load(LimitObj.getParamJson());
		},
		"getParamJson" : function() {
			var para = {
					lotteryCode:LimitObj.lotteryCode.getValue(),
					issueStart:LimitObj.issueStart.getValue(),
					issueEnd:LimitObj.issueEnd.getValue(),
					timeStart:LimitObj.timeStart.getFormValue(),
					timeEnd:LimitObj.timeEnd.getFormValue(),
					qryStartTime:LimitObj.qryStartTime.getFormValue(),
					qryEndTime:LimitObj.qryEndTime.getFormValue(),
					status:LimitObj.status.getValue(),
					limitType:LimitObj.limitType.getValue()
				};
			return para;
		},
		"closeLotCategory":function(e) {
			var obj = e.sender;
			obj.setText("");
			obj.setValue("");
			// 关联彩种
			LimitObj.relationLotCode(null, LimitObj.lotteryCode);
		},
		"changeLotCategory":function() {
			 LimitObj.relationLotCode(LimitObj.lotteryCategory, LimitObj.lotteryCode);
		},
		/**
		 * 渲染::限号内容(玩法+内容)
		 */
		"rendererLimitContent":function(e) {
			var _record = e.record;
			if(!_record.limitInfoList || _record.limitInfoList.length == 0) {
				return ;
			}
			var _content = "";
			for(var i = 0; i < _record.limitInfoList.length; i++) {
				_content += "|";
				if(_record.limitInfoList[i].lotteryChild) {
					_content += _record.limitInfoList[i].lotteryChild.childName;
				}
				if(_record.limitInfoList[i].limitContent) {
					_content += _record.limitInfoList[i].limitContent;
				}
			}
	        return _content.substring(1);
	     
		},
		"onrowdblclick" : function() {
			 mini.get("edit").doClick();
		},
		"addLimit" : function() {
			// 新增限号操作,彩种可修改;限号内容不可以操作;
	        LimitObj.lotteryCategory_edit.enable();
	        LimitObj.lotteryCode_edit.enable();
	        $("#fset_limit_info").hide();
	        
			LimitObj.editWindow.setTitle("添加限号");
			LimitObj.editWindow.show();
	    	var form = new mini.Form("#editForm");
	    	form.clear();
	    	// 添加的请求方式和url
	    	var param = {
	    	    action:"post",
	    	   	url:"lotterymgr/limit"
	    	};
	    	form.setData(param);
		},
		"editLimit" : function() {
        	var row = LimitObj.grid.getSelected();
     	    if (row) {
     	    	//TaskObj.grid.loading("加载中。。。。");
     	    	// 查详情
	     	    var param = {
	     	    	url:"lotterymgr/limit/detail/" + row.id,
	     	    	action:"GET",
	     	    	data:"",
	     	    };
	     	    Cms.ajax(param, function(row) {
	     	    	// 设置修改操作的提交信息
	     	    	row.action = "put";
	    	        row.url = "lotterymgr/limit";
	    	        var form = new mini.Form("#editForm");
	    	        form.clear();
	    	        // 彩种集合重新赋值
	    	        LimitObj.lotteryCode_edit.setData(Dic.allCode);
	    	        form.setData(row);
	    	        // 修改限号操作,彩种不可修改;限号内容可以操作;
	    	        LimitObj.lotteryCategory_edit.disable();
	    	        LimitObj.lotteryCode_edit.disable();
	    	        $("#fset_limit_info").show();
	    	        
	    	        LimitObj.editWindow.set({title:"修改限号"});
	    	        LimitObj.editWindow.show();
	    	        // 关联彩种子玩法
	    			ComReq.lotteryChild(row.lotteryCode, function(result) {
	    				Dic.lotteryChild = result;
	    			});
	    	        // 加载 限号内容
	    	        LimitObj.loadLimitInfo(row.id);
	     	   	});
	     	  // TaskObj.grid.unmask();
     	    } else {
     	    	 mini.alert("请选择一行数据");
     	    }
		},
		"doSubmit" : function() {
			Cms.submit(new mini.Form("#editForm"), function() {
				LimitObj.editWindow.hide();
				LimitObj.grid.reload();
		    });
		},
		"closeLotCategoryEdit":function(e) {
			var obj = e.sender;
			obj.setText("");
			obj.setValue("");
			// 关联彩种
			LimitObj.relationLotCode(null, LimitObj.lotteryCode_edit);
		},
		"changeLotCategoryEdit":function() {
			 LimitObj.relationLotCode(LimitObj.lotteryCategory_edit, LimitObj.lotteryCode_edit);
		},
		/**
		 * @desc 通过彩种类型，关联相关彩种列表
		 * @param lotCategoryObj 彩种类型对象
		 * @param lotCodeObj 彩种对象
		 */
		"relationLotCode":function(lotCategoryObj, lotCodeObj) {
			var code = !lotCategoryObj ? "" : lotCategoryObj.getValue();
			// 关联彩种
			ComReq.lottery(code, function(result) {
				lotCodeObj.setData(result);
			});
		 },
		 /**
		  * @desc 自定义校验，限号彩期范围和限号时间范围不能都为空
		  * @param e
		  */
		"onIssueValidation":function(e) {
			if (e.isValid) {
				var _issueStart = LimitObj.issueStart_edit.getValue();
				var _issueEnd = LimitObj.issueEnd_edit.getValue();
                if (!_issueStart && !_issueEnd
                		&& !LimitObj.timeStart_edit.getValue() && !LimitObj.timeEnd_edit.getValue()) {
                    e.errorText = "限号彩期范围或限号时间范围不能都为空！";
                    e.isValid = false;
                } else if (_issueStart && _issueEnd && _issueEnd < _issueStart) {
                    e.errorText = "限号彩期的结束彩期不能小于开始彩期！";
                    e.isValid = false;
                }
            }
		},
		/**
		  * @desc 自定义校验，限号时间范围验证
		  * @param e
		  */
		"onTimeValidation" : function(e) {
			if (e.isValid) {
				var _timeStart = LimitObj.timeStart_edit.getValue();
				var _timeEnd = LimitObj.timeEnd_edit.getValue();
                if (_timeStart && _timeEnd && _timeEnd.getTime() < _timeStart.getTime()) {
                    e.errorText = "限号时间的结束时间不能小于开始时间！";
                    e.isValid = false;
                }
            }
		},
		"loadLimitInfo":function(limitId) {
			var param = {
					limitId:limitId
	     	}
			LimitObj.limitInfoGrid.load(param, function(data) {
				if(data.result.errorCode && data.result.errorCode != Code.success) {
					mini.alert(data.result.message);
				}
			});
		},
		/**
		 * @desc grid动态编辑
		 * @param e
		 * @returns {String}
		 */
		"onActionRenderer":function(e) {
            var gridAdd = e.sender;
            var record = e.record;
            var uid = record._uid;
            var s = "";
            var id = record.id;
            if(id && gridAdd.isEditingRow(record)) { // 编辑的行
                s = '<a class="mini-button" plain="true" href="javascript:Cms.cancelEditRow(\'limitInfoGrid\',\'' + uid + '\')">取消</a>'
            } else if(gridAdd.isEditingRow(record)) {// 新增的行
                s = '<a class="mini-button" plain="true" href="javascript:Cms.delEditRow(\'limitInfoGrid\',\'' + uid + '\')">取消</a>'
            }
            return s;
		},
		/**
		 * @desc 渲染子玩法名称 (因为切换不同彩种时子玩法名称显示不出，所以这里手动渲染名称)
		 * @param e
		 */
		"rendererLotChildName":function(e) {
			var _record = e.record;
			var _val = e.value;
			if(!Dic.lotteryChild || Dic.lotteryChild.length == 0) {
				return e.value;
			}
			for(var i = 0; i < Dic.lotteryChild.length; i++) {
				if(Dic.lotteryChild[i].id == _val) {
					return Dic.lotteryChild[i].text;
				}
			}
			return e.value;
	    	/*if(_record.lotteryChild) {
	    		return _record.lotteryChild.childName;
	    	} else {
	    		return e.value;
	    	}*/
		},
		"saveLimitInfo" : function() {
			// grid的编辑行是作为form验证的
	        Cms.formValidate("#limitInfoGrid", "保存数据有误！", function() {
		        // 验证提交所有行编辑
		        LimitObj.limitInfoGrid.commitEdit();
		        // 获取增加、删除、修改后的数据集合
		        // (注：getChanges方法对于新增动作只提交可编辑的字段；修改动作则会提交整个row的字段（不管是否可编辑）；所以操作时需注意)
		        var rows = LimitObj.limitInfoGrid.getChanges();
		        if(rows.length == 0){
		           mini.alert("请先修改表格数据，再保存！");
		           return;
		        } 
		        var _limitId = LimitObj.limitId_edit.getValue();
		        for(i = 0; i < rows.length; i++) {
		            rows[i].limitId = _limitId;
		            // 不需要提交的字段手动删除
		            //（由于grid的getChanges获取的是row所有的数据(虽然可以设置获取仅修改的字段，但如果只修改了某个字段，其他重要字段又不能获取,导致后台校验不能通过)，所有这里手动清除不需要提交的字段）
		            delete rows[i].lotteryChild;
		            delete rows[i].updateTime;
		            delete rows[i].createTime;
		        }
		        var json = mini.encode(rows);
		        Cms.save(json, "lotterymgr/limit/info/save", function() {
		        	LimitObj.limitInfoGrid.reload();
		        });
	        });
	     }
} 

// 初始化
LimitObj.init();