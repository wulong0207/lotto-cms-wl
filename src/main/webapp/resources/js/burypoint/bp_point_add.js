var bp_point_add = {
	init : function() {
		mini.parse();
		bp_point_add.w = mini.get('detailWindow');
		bp_point_add.w.show();
		bp_point_add.positionForm = new mini.Form('positionWindow');
	},
	sureSelectPage : function() {
		var data = bp_point_add.positionForm.getData();
		var pageId = data.pageId;
		var ret = {status:true};
		if(!pageId) {
			ret.status = false;
			ret.msg = '请选择一个页面';
		}
		return ret;
	},
	sureSelectMode : function() {
		var ret = bp_point_add.sureSelectPage();
		if(!ret.status) return ret;
		var data = bp_point_add.positionForm.getData();
		var modeId = data.modeId;
		if(!modeId) {
			ret.status = false;
			ret.msg = '请选择一个板块';
		}
		return ret;
	},
	toWindow : function(windowId, reherse, callBack) {
		if(reherse) {
			var ret = reherse();
			if(!ret.status) {
				mini.alert(ret.msg);
				return;
			}
		}
        callBack && callBack();
		var w = mini.get(windowId);
		var f = new mini.Form(windowId);
		f.clear();
		w.show();
	},
    clearPageModeButton : function() {
        $.get("bp/point/pointpage/dic")
		.done(function(data) {
			mini.get('pageId2').setData(data);
			mini.get('modeId2').setData(null);
			mini.get('buttonId2').setData(null);
		})
		.fail(function() {
			console.error('根据查询埋点页面出错');
		});
	},
	save : function() {
		if(!MiniCom.isValidForm('detailForm')) {
			mini.alert('表单数据有误');
			return;
		}
		var pointData = new mini.Form('detailForm').getData();
		$.post(
			'bp/point',
			pointData
		).done(
			function(res) {
				if(res.errorCode == Code.success){
					mini.showTips({
						content: res.message,
						state: "success",
						x: "center",
						y: "center",
						timeout: 2000
					});
					setTimeout(function(){ 
						var tabs = window.parent.mini.get("mainTabs");
						// 找出‘数据埋点设置’页刷新
						var pointTab;
						tabs.tabs.forEach(function(tab){
							if(tab.title === '数据埋点设置') {
								pointTab = tab;
								return;
							}
						});
						if(pointTab) {
							// 页面、板块、按钮的字典要重新刷新
							var cw = tabs.getTabIFrameEl(pointTab).contentWindow;
                            cw.bp_point.updateDic();
                            cw.mini.get('datagrid').reload();
						}
						//保存成功则1.5秒后关闭标签页
						var tab = tabs.getActiveTab();
			            //tabs.getTabIFrameEl(tabs.getTab(0)).contentWindow.mini.get('articleGrid').reload();
			            if (tab) {
			                tabs.removeTab(tab);
			            }
					}, 1000);
					// 关闭当前
				}else{
					mini.alert(res.message); 
				}
		})
	},
	cancel : function(windowId) {
		mini.get(windowId).hide();
	},
	addPage : function() {
		var pageName = mini.get('pageName').getValue();
		if(!pageName || !pageName.trim()) {
			mini.alert('请输入页面名称！');
			return;
		}
		$.ajax({
			url : 'bp/point/pointpage',
			data : {'name' : pageName},
			type : 'POST',
		}).done(
			function(res) {
				if(res.errorCode == Code.success){
					mini.get('pageWindow').hide();
                    bp_point_add.findPage('pageId2', 'modeId2', 'buttonId2');
					mini.showTips({
						content: res.message,
						state: "success",
						x: "center",
						y: "center",
						timeout: 2000
					});
				}else{
					mini.alert(res.message); 
				}
		})
	},
	addMode : function() {
		var modeName = mini.get('modeName').getValue(),
		pageId = bp_point_add.positionForm.getData().pageId;
		if(!modeName || !modeName.trim()) {
			mini.alert('请输入板块名称！');
			return;
		}
		var data = {
			'name' : modeName,
			'pageId' : pageId,
		};
		$.ajax({
			url : 'bp/point/mode',
			data : data,
			type : 'POST',
		}).done(
			function(res) {
				if(res.errorCode == Code.success){
					mini.get('modeWindow').hide();
                    bp_point_add.findMode('pageId2', 'modeId2', 'buttonId2');
					mini.showTips({
						content: res.message,
						state: "success",
						x: "center",
						y: "center",
						timeout: 2000
					});
				}else{
					mini.alert(res.message); 
				}
			})
	},
	addButton : function() {
		var buttonName = mini.get('buttonName').getValue(),
		modeId = bp_point_add.positionForm.getData().modeId;
		if(!buttonName || !buttonName.trim()) {
			mini.alert('请输入按钮名称！');
			return;
		}
		var data = {
			'name' : buttonName,
			'modeId' : modeId,
		};
		$.ajax({
			url : 'bp/point/button',
			data : data,
			type : 'POST',
		}).done(
			function(res) {
				if(res.errorCode == Code.success){
					mini.get('buttonWindow').hide();
                    bp_point_add.findButton('modeId2','buttonId2')
					mini.showTips({
						content: res.message,
						state: "success",
						x: "center",
						y: "center",
						timeout: 2000
					});
				}else{
					mini.alert(res.message); 
				}
			})
	},
	findPage : function(pageId, modeId, buttonId) {
		var pageIdDom = mini.get(pageId).getValue();
		$.get("bp/point/pointpage/dic",{pageId : pageId})
		.done(function(data) {
			mini.get(pageId).setData(data);
			mini.get(modeId).setData(null);
			mini.get(buttonId).setData(null);
			// 主页面的pageId字典也更新
            mini.get('pageId').setData(data);
		})
		.fail(function() {
			console.error('根据查询埋点页面出错');
		});
	},
	findMode : function(sourceId, targetId, clearId) {
		var pageId = mini.get(sourceId).getValue();
		$.get("bp/point/mode/dic",{pageId : pageId})
		.done(function(data) {
			mini.get(targetId).setData(data);
			mini.get(clearId).setData(null);
            // 如果主页面的pageId跟这里的pageId一样，则主页面的板块字典也更新
			var mainPageId = mini.get('pageId').getValue();
			if(pageId == mainPageId) {
                mini.get('modeId').setData(data);
			}
		})
		.fail(function() {
			console.error('根据页面id查询板块出错');
		});
	},
	findButton : function(sourceId, targetId) {
		var modeId = mini.get(sourceId).getValue();
		$.get("bp/point/button/dic",{modeId : modeId})
		.done(function(data) {
			mini.get(targetId).setData(data);
            // 如果主页面的modeId跟这里的modeId一样，则主页面的按钮字典也更新
            var mainModeId = mini.get('modeId').getValue();
            if(mainModeId == modeId) {
                mini.get('buttonId').setData(data);
            }
		})
		.fail(function() {
			console.error('根据页面id查询板块出错');
		});
	},
	deletePosition : function() {
		var positionData = bp_point_add.positionForm.getData(),
		pageId = positionData.pageId,
		modeId = positionData.modeId,
		buttonId = positionData.buttonId;
		if(!pageId && !modeId && !buttonId) {
			mini.alert('请选择要删除的数据');
			return;
		}
	    mini.confirm("确定删除?", "删除", function(e) {
	    	if(e=="ok"){
	    		$.ajax({
					url : String.format('bp/point/posititon?pageId={0}&modeId={1}&buttonId={2}',pageId,modeId,buttonId),
					type : 'DELETE',
				}).done(
					function(res) {
						if(res.errorCode == Code.success){
							if(buttonId)
                                bp_point_add.findButton('modeId2','buttonId2');
							else if(modeId)
                                bp_point_add.findMode('pageId2','modeId2','buttonId2');
							else if(pageId)
                                bp_point_add.findPage('pageId2','modeId2','buttonId2');
							mini.showTips({
								content: res.message,
								state: "success",
								x: "center",
								y: "center",
								timeout: 2000
							});
						}else{
							mini.alert(res.message); 
						}
				}).fail(
					function(jqXHR, textStatus, errorThrown) {
						console.error(jqXHR);
					}
				);
	    	}
	    }); 
	},
	closeTab : function() {
		var tabs = window.parent.mini.get("mainTabs");
		//保存成功则1.5秒后关闭标签页
		var tab = tabs.getActiveTab();
        if (tab) {
            tabs.removeTab(tab);
        }
	},
}
bp_point_add.init();