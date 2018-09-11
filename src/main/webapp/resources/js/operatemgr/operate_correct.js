var Correct = {
	"init" : function() {
		mini.parse();
		//页面标签对象
		Correct.dataGrid = mini.get("dataGrid");
		Correct.editWindow = mini.get("editWindow");
		
		//意见箱查询条件
		Correct.sources = mini.get("sources");
		Correct.beginTime = mini.get("beginTime");
		Correct.endTime = mini.get("endTime");
		Correct.status = mini.get("status");
		Correct.createBy = mini.get("createBy");
		
		//意见箱from提交
//		Correct.helpId = mini.get("helpId");
//		Correct.correct = mini.get("correct");
//		Correct.helpId = mini.get("helpId");
		
		//加载字典初始化
		ComReq.dictionary("2201,2202,2203", function(result){
			Dic.sources = result["2201"];
			Dic.status = result["2202"];
			Dic.type = result["2203"];
			Correct.sources.setData(Dic.sources);
			Correct.status.setData(Dic.status);
			
			Correct.search();
		});
	},
	"onCloseClick" : function(e) {
		var obj = e.sender;
		obj.setText("");
		obj.setValue("");

	},
	"onRowdblClick" : function(e){
		var row = Correct.dataGrid.getSelected();
		if(row){
			var editForm = new mini.Form("#editForm");
			editForm.clear();
			row.action = "post";
		    row.url= "operatemgr/correct/update";
			editForm.setData(row);
			Correct.editWindow.show();
		} else {
	        mini.alert("请选择一行数据");
	    }
	},
	"articleRenderer":function(e){
		//父标签对象
		var grid = e.sender;
		//单条标签数据
		var row = e.record;
		var helpId = e.value;
		var articleTitle = row.articleTitle;
		return helpId ? ' <a plain="true" href="javascript:Correct.toArticleDetail(\''+helpId+'\', \''+articleTitle+'\')" >'+ helpId + '</a>' : "";
	},
	"toArticleDetail" : function(id,title){
		Cms.setCookie('articleId', id);
		Cms.setCookie(id, title);
		MiniCom.openMainTab(id, '文章列表', 'operatemgr/article');
	},
	"search" : function(){
		var correctForm = new mini.Form("#correctForm");
		var data = correctForm.getData(true, false)
		Correct.dataGrid.load(data);
	},
	"edit" : function(){
		var editForm = new mini.Form("#editForm");
		var data = editForm.getData(true, false);
		if(data.status == 0){
			Cms.submit(editForm,function(){
			Correct.editWindow.hide();
	        Cms.reload("dataGrid");  
			});
		}else{
			mini.alert("意见已经处理, 请勿重复提交!!");
		}
	},
	"close" : function(){
		Correct.editWindow.hide();
	}
}

Correct.init();