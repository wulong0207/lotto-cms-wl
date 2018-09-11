operate_article={
  init:function(){
    mini.parse();
    operate_article.articleTypeWindow = mini.get("articleTypeWindow");
    operate_article.articleTypeDetailWindow = mini.get("articleTypeDetailWindow");
    operate_article.articleTypeTreegrid = mini.get("articleTypeTreegrid");
    operate_article.parentTreegrid = mini.get("parentTreegrid");
    operate_article.tabs = mini.get("tabs");
    operate_article.catalogue = "_upload_images/operate/article";
    
    ComReq.dictionary("0002,1513,1514,1523", function(result){
    	Dic.display = result["0002"];
    	Dic.rstatus = result["0002"];
    	Dic.status = result["1513"];	
    	Dic.virtual = result["1523"];	
    	Dic.time = result["1514"];	
    	mini.get("typeStatus").setData(Dic.display);
    	mini.get("virtual").setData(Dic.virtual);
    	mini.get("rstatus").setData(Dic.rstatus);
    });
    Cms.addTab({
    	tabs : operate_article.tabs,
    	url : 'operatemgr/article/articles',
    	title : '文章列表',
    	showCloseButton : false
    });
  },
  
  /**
   * 修改栏目
   */
  editArticleType : function() {
  	operate_article.articleTypeTreegrid.load("operatemgr/article/type");
  	operate_article.articleTypeWindow.show();
  },
  
  showAddArticleType : function() {
	 // 设置标识符可改
	 mini.get("identifiers").set({enabled : true});
	 // 加载"上级栏目"的树形结构
	 $.get("operatemgr/article/type",function(nodes) {
		 var nodes = nodes || [];
			// 增加顶级栏目的父栏目到数组第一位
			nodes.unshift({
				id : -1,
				typeLevel : 0,
				typeFullName : '无'
			});
			mini.get("typeParent").loadList(nodes, 'id', 'typeParent');
			mini.get("typeParent").set({
				//data : nodes,
				// 设置下拉框能选择
				enabled : true
			});
		// TODO 生产环境时，叶子结点不可选为"上级目录"
	 });
	
  	var form = new mini.Form("articleTypeForm");
		form.clear();
		form.setData({action : "POST"});
  	operate_article.articleTypeDetailWindow.show();
	},
	
	showEditArticleType : function() {
		var row = operate_article.articleTypeTreegrid.getSelectedNode();
		if(row) {
			// 渲染要修改的文章栏目
			row.action = 'PUT';
			new mini.Form('articleTypeForm').setData(row);
			// 标识符不可改
			mini.get("identifiers").set({enabled : false});
			// 加载"上级栏目"的树形结构
			$.get("operatemgr/article/type",function(nodes) {
				var nodes = nodes || [];
				// 增加顶级栏目的父栏目
				nodes.push({
					id : -1,
					typeLevel : 0,
					typeFullName : '无'
				});
				mini.get("typeParent").loadList(nodes, 'id', 'typeParent');
				mini.get("typeParent").set({
					//data : nodes,
					// 选中该栏目的父栏目
					value : row.typeParent,
					// 上级栏目不能修改，设置下拉框不能选择
					enabled : false
				});
			 });
			operate_article.articleTypeDetailWindow.show();
		} else {
			mini.alert("请选择一行数据");
		}
		
	},
	mergeArticleType : function(levelField) {
		if(!MiniCom.isValidForm('articleTypeForm')) {
			mini.alert('表单数据有误');
			return;
		}
		var data = new mini.Form('articleTypeForm').getData();
		if(/.*[\u4e00-\u9fa5]+.*$/.test(data.identifiers)) {
	        mini.alert("栏目标识符不能含有中文！");  
	        return;  
        }  
		var t = operate_article.parentTreegrid;
		data.url = 'operatemgr/article/type';
		// 设置上级栏目
		data.typeParent = mini.get('typeParent').getValue();
		//  如果是新增栏目，则该栏目的level为上级目录的level加1，修改时栏目的level不变
		if(!data.id)
		  data.typeLevel = mini.get('typeParent').getSelectedNode()[levelField]+1;
		// 栏目最多为四级
		if(data.typeLevel > 4) {
			mini.alert('栏目最多为四级');
			return;
		}
		param = {
			url : 'operatemgr/article/type',
			action : data.action,
			data : data
		}
		
		Cms.saveDataStringify(param, function() {
			operate_article.articleTypeDetailWindow.hide();
			Cms.reload("articleTypeTreegrid");
		});
	},
	/**
	 * @desc 若顺序有改变，则保存文章栏目顺序
	 * @returns 
	 */
	saveArticleTypeOrder : function(treeGridId) {
		var treeGrid = mini.get(treeGridId);
		var param = {
			action : 'put',
			url : 'operatemgr/article/type/order',
			data : treeGrid.getList()
		};
		if(treeGrid.isChanged()) {
			Cms.saveDataStringify(param, function() {
				Cms.reload("articleTypeTreegrid");
			});
		}
	},
	closeArticleType : function() {
		operate_article.articleTypeDetailWindow.hide();
	},
	
	/**
	 * 展示文章列表
	 */
	listArticles : function(event) {
		// 非叶子结点可能有推送文章
		var tab = operate_article.tabs.getActiveTab(),
		iframeEl = operate_article.tabs.getTabIFrameEl(tab),
		grid =  iframeEl.contentWindow.mini.get("articleGrid");
		grid.load({
			typeId : event.node.id
		});
	},
	/**
	 * 打开新增文章窗口
	 */
	showAddArticle : function() {
	    Cms.addTab({
	    	tabs : window.parent.operate_article.tabs,
	    	// contoller会返回tab页面的url
	    	url : 'operatemgr/article/article',
	    	title: '新增文章',
	    	callBack : operate_article.setTabCookie,
	    	action : "POST",
	    	articleId : null,
	    });
	},
	
	/**
	 * 打开修改文章窗口
	 */
	showEditArticle : function() {
		var row = mini.get("articleGrid").getSelected();
		if (row) {
			Cms.addTab({
		    	tabs : window.parent.operate_article.tabs,
		    	url : 'operatemgr/article/article',
		    	title: row.articleTitle,
		    	callBack : operate_article.setTabCookie,
		    	action : "POST",
		    	articleId : row.id,
		    });
		} else {
			mini.alert("请选择一行数据");
		}
	},
//add by cheng chen 意见箱文章跳转使用	
	openEditWin:function(id){
		if (id != null && id != undefined && id != "" && id != "\"\"") {
			var title = Cms.getCookie(id);
			Cms.delCookie(id);
			Cms.addTab({
		    	tabs : window.parent.operate_article.tabs,
		    	url : 'operatemgr/article/article',
		    	title: title,
		    	callBack : operate_article.setTabCookie,
		    	action : "POST",
		    	articleId : id,
		    });
		} 
	},
	/**
	 * 修改文章状态
	 */
	visitArticleStatus : function(newStatus) {
		var row = mini.get("articleGrid").getSelected();
		if (row) {
			mini.confirm("确定操作文章?", "提示", function(e) {
		    	if(e=="ok"){
		    		var param ={
							url:"operatemgr/article/status",
							action:"put",
							data: {
								id : row.id,
								status : row.status,
								newStatus : newStatus,
								releaseTime : dateUtils.formatDate(row.releaseTime)
							}
					}
					Cms.ajax(param,function(data){
						mini.alert("操作文章状态成功");
						Cms.reload("articleGrid");
					});
		    		
		    	}
		    }); 
		} else {
			mini.alert("请选择一行数据");
		}
	},
	setTabCookie : function(e, op) {
		// 以tab的id为key加字符串设置cookie
		Cookies.set('articleTabForm_'+ e.tab._id, {
			id : op.articleId,
			action : op.action
		});
		// 如果是修改文章则加载此文章的详情
		var tab = window.parent.operate_article.tabs.getActiveTab(),
		contentWindow = window.parent.operate_article.tabs.getTabIFrameEl(tab).contentWindow;
		if(op.articleId) {
			$.get('operatemgr/article/'+op.articleId,function(article) {
				// 初始化标题框的值
				contentWindow.$("div#titleDiv input").val(article.articleTitle);
				// 设置标签文字为文章标题
				tab.title = article.articleTitle;
				// 设置标签框的值
				var label = contentWindow.mini.get("articleLabel");                       
				//label.setValue("1,,1,");
				// label文本有多少逗号就设置多少个value,这里的不关注label框的值，value随便加一个值
				for(var i=0, n=article.articleLabel.split(',').length, val=''; i<n ; i++) {
					val += i===(n-1) ? 'a' : 'a,';
				}
				label.setValue(val);
				label.setText(article.articleLabel);
				// 显示已输入多少个标签
				contentWindow.updateLabelLength(article.articleLabel);
				var ue = contentWindow.UE.getEditor('editor');
				ue.setContent(article.articleContent);
				// 如果有内容则选中对应的checkbox
				article.articleFrom && contentWindow.mini.get("articleFromCheck").setChecked(true);
				article.fromUrl && contentWindow.mini.get("fromUrlCheck").setChecked(true);
				
				//var tabs = e.sender;
			    //var iframe = tabs.getTabIFrameEl(e.tab);
			    // 如果有封面图片则加载图片
                contentWindow.initImageInfo(article);
				// 封面图片显示在正文中
				//contentWindow.mini.get("articleImgDisplay").setValue(article.articleImgDisplay == 1);
				// 如果有article_top表信息
				if(article.articleTops) {
					// 调用详情页面article_detail的函数，在详情页面的页面的上下文中才能获取详情页面元素
	                contentWindow.initArticleTopInfo(article);
				}
				// 保证字典选加载
				ComReq.dictionary("1513,1515", function(result) {
					contentWindow.mini.get("status").setData(result["1513"]);
					contentWindow.mini.get("display").setData(result["1515"]);
					var articleForm =  new contentWindow.mini.Form("articleForm"),
					articleDetailForm =  new contentWindow.mini.Form("articleDetailForm");
					articleForm.setData(article);
					articleDetailForm.setData(article);
					// 设置栏目下拉框的值
					contentWindow.mini.get("typeId").setValue(article.typeId);
					// 设置栏目下拉框不可选择
					contentWindow.mini.get("typeId").set({"enabled" : false});
					// 设置文章在哪些端显示,0不显示;1显示 ，各端对应的数据字典值为 1web 2wap 3android 4ios
					var displayValue =[];
					article.web && displayValue.push(1);
					article.wap && displayValue.push(2);
					article.android && displayValue.push(3);
					article.ios && displayValue.push(4);
					contentWindow.mini.get("display").setValue(displayValue.join(","));
				});
			});
		} 
		// 新增文章则带出栏目
		else {
			var tree =  window.parent.mini.get('tree1');
			var node =  window.parent.mini.get('tree1').getSelectedNode();
			if(node && node.virtual===0 && tree.isLeaf(node) ) {
				contentWindow.mini.get("typeId").setValue(node.id);
			}
		}
	},
	mergeArticle : function() {
		// 验证添加文章tab页面的必填字段
		if(!MiniCom.isValidForm('articleDetailForm')) {
			mini.alert('数据没填写完整');
			return;
		}
		if(!MiniCom.isValidForm('articleForm')) {
			mini.alert('数据没填写完整');
			return;
		}
		// 如果选中置顶到本栏目则要填写上下线时间
		if(mini.get("isRecommend").checked) {
			if(!mini.get("uptime").getValue() || !mini.get("downtime").getValue()){
				mini.alert('请选择置顶本栏目的上下线时间');
				return;
			}
		}
		var articleContent = UE.getEditor('editor').getContent();
		if(!$.trim(articleContent)) {
			mini.alert('请输入内容');
			return;
		}
		var formData,
		formDetailData,
		articleTop,
		articleTops = [],
		// 选中的tab
		tab = window.parent.operate_article.tabs.getActiveTab(),
		// 标题有自己的input框，取此框的输入值
		//articleTitle = $.trim($(articleContent).first().text()), // 默认第一个HTML标签是标题，若页面结构有修改则要对应修改获取title的代码
		articleTitle = $.trim($("div#titleDiv input").val());
		if(!articleTitle) {
			mini.alert('请输入文章标题');
			return;
		}
		var data = {
			//url : 'operatemgr/article',
			articleContent : articleContent,
			// 
			articleTitle : articleTitle
		}
		, uptime, downtime, title, error, errorMsg;
		
		// 通过cookie读取文章id和action提交方式
		$.extend(data, Cookies.getJSON('articleTabForm_'+tab._id));
		// 根据是否有文章id来判断是新增还是修改，且访问不同的controller方法
		data.url  = 'operatemgr/article/' + (data.id ? 'update' : 'add');
		
		formData = new mini.Form('articleForm').getData();
		formDetailData = new mini.Form('articleDetailForm').getData();
		// 如果选中"文章来源"
		mini.get("articleFromCheck").checked && (data.articleFrom = formDetailData.articleFrom);
		// 如果选中"文章链接"
		mini.get("fromUrlCheck").checked && (data.fromUrl = formDetailData.fromUrl);
		// 摘票 选填，如果不填写会默认抓取正文前100个字
		//$(articleContent).first().next().text();
		data.articleSummary = $.trim(formDetailData.articleSummary) || 
			$.trim($(articleContent).eq(0).text()).substring(0,100);
		// 最多输入12个标签，每个标签最多8个字，不填写会默认为标题
		// 把articleLabel的值替换为文本text
		data.articleLabel = $.trim(mini.get('articleLabel').getText()) ||
			articleTitle.substring(0, 8);
		// 替换中文逗号
		data.articleLabel = data.articleLabel.replace(/，/g,',');
		
		uptime = mini.get("uptime").getValue();//formDetailData.uptime;
		downtime = mini.get("downtime").getValue();//formDetailData.downtime;
		// 如果选中置顶到本栏目则要检查上下线时间
		if(mini.get("isRecommend").checked) {
			if(uptime.getTime() >= downtime.getTime()) {
				mini.alert("上线时间必须小于下线时间");
				return;
			}
		}
		
		// 此文章在本栏目的置顶信息
		articleTop = {
			id : mini.get("originalTopId").getValue(),
			typeId : mini.get("typeId").getSelectedNode().id,
			//articleId : ,
			title : articleTitle,
			color : document.getElementById('color').jscolor.toHEXString(),
			uptime : uptime,//mini.get("uptime").getFormValue(),
			downtime : downtime//mini.get("downtime").getFormValue(),
		}
		// 是否"置顶在栏目中"
		articleTop.isTop = mini.get("isRecommend").checked ? 1 : 0;
		articleTops.push(articleTop);
		// 动态添加的置顶栏目信息
		//var $hasChecked = $("tr.sendToTopTr1 input[name='sendToTop']:checked:enabled",'#articleDetailForm'), 
		var $sendTopDiv, $isTop, articleType;
		$.each($("div.mySendTop"), function(index, sendTopDiv) {
			$sendTopDiv = $(sendTopDiv);
			// 注意由data()方法返回的是字符串
			articleType = $sendTopDiv.data("articletype");
			// 如果选中推送到其他栏目
			// formDetailData["sendToTop" + articleType] == 'true'
			if(mini.get("sendToTop" + articleType).checked) {
				articleTop = {
					// 若是修改文章则有id,否则无id
					id : $sendTopDiv.data('articletopid'),
					typeId : articleType,
					// 在其栏目中是否置顶
					isTop : mini.get("isTop" + articleType).checked ? 1 : 0//formDetailData["isTop" + articleType] == 'true' ? 1 : 0,
					//articleId : ,
				}
				// 标题、上下线时间
				//$topTr = $("tr.sendToTopTr2#"+typeId,'#articleDetailForm');
				//mini.get("").checked;
				uptime = mini.get("uptime" + articleType).getValue();//formDetailData["uptime" + articleType];
				downtime = mini.get("downtime" + articleType).getValue();//ormDetailData["downtime" + articleType];
				title = mini.get("title" + articleType).getValue();//formDetailData["title" + articleType];
				if(!uptime || !downtime) {
					error = true;
					errorMsg = "请选择上线时间和下线时间"
					return;
				}
				if(uptime.getTime() >= downtime.getTime()) {
					error = true;
					errorMsg = "上线时间必须小于下线时间"
					return;
				}
				if(!title) {
					error = true;
					errorMsg = "请输入标题"
					return;
				}
				articleTop.title = title;
				articleTop.uptime = uptime;
				articleTop.downtime = downtime;
				articleTop.color = document.getElementById('color'+ articleType).jscolor.toHEXString();
				articleTops.push(articleTop);
			} 
			// else {这里是没勾选，即要删除article_top中间表的id}
		});
		if(error) {
			mini.alert(errorMsg);
			error = false;
			errorMsg = '';
			return;
		}
		var len = articleTops.length;
		if(len > 1) {
			for(var i=0, len = articleTops.length; i < len-1; i++ ) {
				if(articleTops[i].typeId === articleTops[i+1].typeId) {
					mini.alert("不能重复推送同一个栏目");
					return;
				}
			}
		}
		data.articleTops = articleTops;
		// 最多插入4张图片
		data.articleImg = formDetailData.articleImg;
		// 如果有第1张图片，才判断是否有第2、第3、第4张图片
		if(data.articleImg){
			if(formDetailData.articleImg2)
				data.articleImg += ','+formDetailData.articleImg2;
			if(formDetailData.articleImg3)
				data.articleImg += ','+formDetailData.articleImg3;
			if(formDetailData.articleImg4)
				data.articleImg += ','+formDetailData.articleImg4;
		}
		// 这个字段暂时没啥用，只存第一张图片的时间
		data.articleImgCreateTime = formDetailData.articleImgCreateTime;
		data.releaseTime = formDetailData.releaseTime;
		//封面图片显示:0不显示;1显示, 此选框需求删除
		//data.articleImgDisplay = mini.get("articleImgDisplay").checked ? 1 : 0;
		data.createBy = formDetailData.createBy;
		$.extend(data, formData);
		//  层级
		//data.typeLevel = mini.get('typeId').getSelected().data[levelField];
		// 获取文章栏目名称
		//data.typeName = mini.get('typeId').getSelected().data.typeFullName;
		data.typeName = mini.get("typeId").getSelectedNode().typeFullName;
		// 处理显示值 
		// 各端对应的数据字典值为 1web 2wap 3android 4ios
		data.web = data.display.indexOf("1") == -1 ? 0 : 1;
		data.wap = data.display.indexOf("2") == -1 ? 0 : 1;
		data.android = data.display.indexOf("3") == -1 ? 0 : 1;
		data.ios = data.display.indexOf("4") == -1 ? 0 : 1;
		if(data.web === 0 && data.wap === 0 && data.android === 0 && data.ios === 0) {
			mini.alert('请至少选择一个文章显示平台');
			return;
		}
		// 文章的栏目编号
		data.typeCode = mini.get("typeId").getSelectedNode().typeCode;
		var param = {
			action : data.action,
			url : data.url,
			data : data
		};
		Cms.saveDataStringify(param,function removeTab() {
			//保存成功则1.5秒后关闭标签
			setTimeout(function(){ 
				var tabs = window.parent.operate_article.tabs,
				tab = tabs.getActiveTab();
				//tabs.reloadTab(tabs.getTab(0));
				// 刷新第一个标签，即"文章列表"
				tabs.getTabIFrameEl(tabs.getTab(0)).contentWindow.mini.get('articleGrid').reload();
	            if (tab) {
	                tabs.removeTab(tab);
	            }
			}, 1000);
			
        });
	},
	// 只有非虚拟目录的叶子结点才能添加文章
	filterArticleType : function (e) {
	  //禁止选中父节点或虚拟目录
	  if (e.isLeaf == false || e.virtual === 1){
		  e.cancel = true;
		  mini.alert("只能选择叶子结点和非虚拟目录");
	  }
	},
	// 非本文章目录的结点都可推送，包括叶子结点和非叶子结点，虚拟或排虚拟栏目
	filterTopType : function (e) {
		if ( e.node.id === mini.get('originalTop').getValue() ) {
			e.cancel = true;
			mini.alert("不能推送到自身栏目");
		}
	},
	// 增加置顶或推送设置
	addSendToTop : function (e) {
		// mini.get("typeTopId").getList();
		// 根据选择的结点pid，寻找父结点，组成导航url
		var isFromEditArticle = e.id, 
		nodes= mini.get("typeTopId").getList(), 
		selectedNode;
		if(isFromEditArticle) {
			$.each(nodes,function(index, node){
				// 匹配文章推送的栏目
				if(e.typeId === node.id) {
					selectedNode = node;
					return;
				}
			});
		} else {
			selectedNode = mini.get("typeTopId").getSelectedNode();
		}
		// 如果是新增文章且没有选择栏目
		if(!isFromEditArticle && !selectedNode) {
			mini.alert("请选择一个栏目");
			return;
		}
		// 判断是否置顶栏目是否已经生成
		if($("div#sendToOtherTypeDiv div#mySendTop"+selectedNode.id).length>0) {
			mini.alert("已推送此栏目");
			return;
		}
			
		var navUrls = [selectedNode.typeFullName], i, urlText = '', $div;
		operate_article.findParentUrl(selectedNode, nodes, navUrls);
		for(i = navUrls.length - 1; i >= 0 ; i--) {
			urlText += navUrls[i];
			if(i !== 0 )
				urlText += ' > ';
		}
		// 统一加"class"属性，方便控制"全选"、"反选"，加"id"以防止生成重复置顶栏目
		// 若是修改文章，则会有上articleTopId
		$div = $("<div>", {'data-articletype' : selectedNode.id, 'data-articletopid' : e.id, id : 'mySendTop'+selectedNode.id, class : 'mySendTop'})
			.append($("<input>", {'id' : 'sendToTop' + selectedNode.id, class : 'mini-checkbox', checked : 'true'}))
			.append($("<span>").text(urlText))
			.append($("<input>", {'id' : 'isTop' + selectedNode.id, class : 'mini-checkbox', checked : 'true'}))
			.append($("<span>").text('置顶在栏目中'))
			.append($("<br>"))
			.append($("<span>").text('标题'))
			.append($("<input>", {'id' : 'title' + selectedNode.id, class : 'mini-textbox', checked : 'true'}))
			.append($("<input>", {'id' : 'color' + selectedNode.id, class : 'jscolor {hash:true}', value : '000000'}))
			.append($("<span>").text('上线时间'))
			.append($("<input>", {'id' : 'uptime' + selectedNode.id, class : 'mini-datepicker', format : 'yyyy-MM-dd HH:mm:ss', timeFormat : 'HH:mm:ss', showTime : 'true' , showOkButton : 'true', showClearButton : 'false', oncloseclick : 'Cms.onCloseClick', showClose : 'true'}))
			.append($("<span>").text('下线时间'))
			.append($("<input>", {'id' : 'downtime' + selectedNode.id, class : 'mini-datepicker', format : 'yyyy-MM-dd HH:mm:ss', timeFormat : 'HH:mm:ss', showTime : 'true' , showOkButton : 'true', showClearButton : 'false', oncloseclick : 'Cms.onCloseClick', showClose : 'true'}))
		
		$("#sendToOtherTypeDiv").append($div);
		// 动态添加的mini控件要重新解析
		mini.parse();
		// 解析jscolor
		window.jscolor.installByClassName('jscolor');		
		// 如果是修改文章，则设置文章栏目推送信息的值
		if(isFromEditArticle) {
			// 是否推送到栏目，默认选中
			// mini.get("sendToTop" + selectedNode.id).setChecked(true);
			// 是否置顶
			var tab = window.parent.operate_article.tabs.getActiveTab(),
			iframeEl = window.parent.operate_article.tabs.getTabIFrameEl(tab);
			mini.get("isTop" + selectedNode.id).setChecked(e.isTop === 1);
			// 置顶标题
			mini.get("title" + selectedNode.id).setValue(e.title);
			mini.get("uptime" + selectedNode.id).setValue(new Date(e.uptime));
			mini.get("downtime" + selectedNode.id).setValue(new Date(e.downtime));
			iframeEl.contentDocument.getElementById('color' + selectedNode.id).jscolor.fromString(e.color);
			//mini.get("color" + selectedNode.id).setValue(e.color);
		}
	},
	findParentUrl : function(node, nodes, navUrls) {
		$.each(nodes, function(index, currNode) {
			if(currNode.id === node.typeParent) {
				navUrls.push(currNode.typeFullName);
				// 如果不是根结点，继续查找父结点
				if(currNode.typeParent !== -1) {
					operate_article.findParentUrl(currNode, nodes, navUrls);
				} else {
					// 如果是根结点
					return;
				}
			}
		});
	},
	setImage:function(data, isCancel){
		// 检查已经选择了多少张图片
		var len, $div, splitUrl, firstImgTimeTag, articleImgUrl;
		len = $("div.articleImgDiv").length;
		if(len >= 4) {
			mini.alert("最多选择4张图片");
			return;
		}
		// 只保存第一图片的创建时间，但有name属性时，miniUI会自动提取该值
		firstImgTimeTag = len == 0 ? "articleImgCreateTime" : "";
		// 四张图片url在数据库的中存成一个字段，用逗号隔开       
		articleImgUrl = "articleImg" + (len === 0 ? "": len+1);    
		  
		$div = $("<div>",{class :"articleImgDiv", style:"position:relative;"})
			.append($("<img>", {id : "articleImgTag"+len, class : "articleImgClass", alt:"封面图片", src : data.url}));
			if(isCancel)
				$div.append($("<span>", {id : "cancel", text: "X", style:"color:red;font-size:17px;position:absolute; top:0px; right:10px; z-index:99;"}));
			$div.append($("<br>"))
			.append("文件名：").append($("<input>", {id:"articleImgName"+len, class:"mini-textbox", enabled:"false"}))
			.append($("<br>"))
			.append("图片格式：").append($("<input>", {id:"articleImgFormat"+len, class:"mini-textbox", enabled:"false"}))
			.append($("<br>"))
			.append("大小：").append($("<input>", {id:"articleImgSize"+len, class:"mini-textbox", enabled:"false"}))
			.append($("<br>"))
			.append("创建日期：").append($("<input>", {id:"articleImgCreateTime"+len, name : firstImgTimeTag,class:"mini-datepicker", enabled:"false", format:"yyyy-MM-dd HH:mm:ss"}))
			.append($("<br>"))
			.append($("<input>", {class:"mini-hidden" ,name:articleImgUrl,id:"articleImg"+len}));
		$("tr#articleImgTr").append($("<td>").append($div));
		mini.parse();
		splitUrl = data.url.split(".");
		//$("articleImgTag"+len).attr("src",data.url);
	    mini.get("articleImgName"+len).setValue(data.name);
	    mini.get("articleImgFormat"+len).setValue(splitUrl[splitUrl.length - 1]);
	    mini.get("articleImgSize"+len).setValue(data.sizes);
	    mini.get("articleImgCreateTime"+len).setValue(data.createTime);
	    mini.get("articleImg"+len).setValue(data.dir);
	},
	openImage:function(){
		Cms.imageManage(window.parent.operate_article.catalogue,function(data){
			operate_article.setImage(data, true);
		});
	},
	orderHtml:function(e){
        var gridId = e.sender.id;
        var node = e.node;
        var orderBtn = '';
        var hasPre = TreeGridUtils.preSibling(gridId, node);
        var hasNext = TreeGridUtils.nextSibling(gridId, node);
        if(hasPre)
        	orderBtn += '<a class="mini-button"  plain="true" href="javascript:TreeGridUtils.up(\''+gridId+'\')" >▲</a>';
        if(hasPre && hasNext) 
        	orderBtn += '&nbsp;&nbsp;&nbsp;';
        if(hasNext)
        	orderBtn += '<a class="mini-button"  plain="true" href="javascript:TreeGridUtils.down(\''+gridId+'\')" >▼</a>';
        return orderBtn;
    },
    // 预览文章
    preview : function(e) {
    	var formData = new mini.Form('articleForm').getData(),
		formDetailData = new mini.Form('articleDetailForm').getData(),
    	rawContent = "{0}" +
			"<p><span>发布时间：{1}&nbsp; &nbsp;&nbsp; 作者：{2}</span></p>"+
    		"<p>创建时间：{3}&nbsp; &nbsp;&nbsp; " +
			"修改时间：{4}&nbsp; &nbsp;&nbsp; " +
			"审核时间：{5}&nbsp; &nbsp;&nbsp; " +
			"审核人：{6}</p>"
			+"<hr/>";
    	var title = $.trim($("div#titleDiv input").val());
    	var createTime = dateUtils.formatDate(formData.createTime);
    	var modifyTime = dateUtils.formatDate(formData.modifyTime);
    	var releaseTime = dateUtils.formatDate(formDetailData.releaseTime);
    	var auditTime = formData.auditTime ? dateUtils.formatDate(formData.auditTime) : formData.auditTime;
    	var auditBy = formData.auditBy;
    	var createBy = formDetailData.createBy;
    	var preHtml = rawContent.format(title, releaseTime, createBy, createTime, modifyTime, auditTime, auditBy);
    	ue.execCommand("preview",{'pre':preHtml});
    },
    showStatus:function(e){
        //0被驳回;1编辑中;2待审核;3审核通过 
    	var status = e.row.status;
        if(status === 0) {
        	return "被驳回";
        } else if(status === 1) {
        	return "编辑中";
        } else if(status === 2) {
        	return "待审核";
        } else if(status === 3) {
        	var releaseTime=e.row.releaseTime.getTime();
            var nowTime=new Date().getTime();
        	return releaseTime>nowTime ? "审核通过" : "已发布";
        }
	},
	resetArticle : function() {
//		var param = {
//			type: 'PUT',
//			action: 'operatemgr/article/reset',
//			tip : '确定重新生成所有文章?',
//			tipTitle : '提示',
//			ingTip : '重新生成中。。。',
//			data : {}
//		};
//		Cms.saveDataStringify(param, function(data) {
//			mini.showTips({
//				content: data.message,
//				state: String.format('文章共{0}篇,重新生成成功:{1}篇,失败:{2}篇',data.data),
//				x: "center",
//				y: "center",
//				timeout: 3000
//			});
//		});
	    mini.confirm("确定重新生成所有文章?", "提示", function(e) {
	    	if(e=="ok"){
	    		MiniCom.mask("重新生成中。。。");
	    		$.ajax({
	    			type: 'PUT',
	    			url: 'operatemgr/article/reset'
    			}).done(function(data) {
    				MiniCom.unmask();
    				if(data.errorCode == Code.success){
    					var count = data.data;
    					mini.alert(String.format('文章共{0}篇,重新生成成功:{1}篇,失败:{2}篇',count[0],count[1],count[2]));
					}else{
						MiniCom.unmask();
	    				console.error(data);
					}
    			}).fail(function(data) {
    				MiniCom.unmask();
    				console.error(data);
    			});
	    	}
	    }); 
	}
};