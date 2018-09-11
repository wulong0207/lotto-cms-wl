operate_help={
  init:function(){
    mini.parse();
    operate_help.helpTypeWindow = mini.get("helpTypeWindow");
    operate_help.helpTypeDetailWindow = mini.get("helpTypeDetailWindow");
    operate_help.helpTypeTreegrid = mini.get("helpTypeTreegrid");
    operate_help.parentTreegrid = mini.get("parentTreegrid");
    operate_help.tabs = mini.get("tabs");
    operate_help.catalogue = "_upload_images/operate/help";
    
    ComReq.dictionary("0002,1513,1514,1523", function(result){
    	Dic.display = result["0002"];	
    	Dic.status = result["1513"];	
    	Dic.virtual = result["1523"];	
    	Dic.time = result["1514"];	
    	mini.get("typeStatus").setData(Dic.display);
    	mini.get("virtual").setData(Dic.virtual);
    });
    Cms.addTab({
    	tabs : operate_help.tabs,
    	url : 'operatemgr/help/helps',
    	title : '帮助列表',
    	showCloseButton : false
    });
  },
  
  /**
   * 修改栏目
   */
  editHelpType : function() {
  	operate_help.helpTypeTreegrid.load("operatemgr/help/type");
  	operate_help.helpTypeWindow.show();
  },
  
  showAddHelpType : function() {
	 // 设置标识符可改
	 mini.get("identifiers").set({enabled : true});
	 // 加载"上级栏目"的树形结构
	 $.get("operatemgr/help/type",function(nodes) {
		 var nodes = nodes || [];
			// 增加顶级栏目的父栏目到数组第一位
			nodes.unshift({
				id : -1,
				menu : 0,
				fullName : '无'
			});
			mini.get("parent").loadList(nodes, 'id', 'parent');
			mini.get("parent").set({
				//data : nodes,
				// 设置下拉框能选择
				enabled : true
			});
		// TODO 生产环境时，叶子结点不可选为"上级目录"
	 });
	
  	var form = new mini.Form("helpTypeForm");
		form.clear();
		form.setData({action : "POST"});
  	operate_help.helpTypeDetailWindow.show();
	},
	
	showEditHelpType : function() {
		var row = operate_help.helpTypeTreegrid.getSelectedNode();
		if(row) {
			// 渲染要修改的文章栏目
			row.action = 'PUT';
			new mini.Form('helpTypeForm').setData(row);
			// 标识符不可改
			mini.get("identifiers").set({enabled : false});
			// 加载"上级栏目"的树形结构
			$.get("operatemgr/help/type",function(nodes) {
				var nodes = nodes || [];
				// 增加顶级栏目的父栏目
				nodes.push({
					id : -1,
					menu : 0,
					fullName : '无'
				});
				mini.get("parent").loadList(nodes, 'id', 'parent');
				mini.get("parent").set({
					//data : nodes,
					// 选中该栏目的父栏目
					value : row.parent,
					// 上级栏目不能修改，设置下拉框不能选择
					enabled : false
				});
			 });
			operate_help.helpTypeDetailWindow.show();
		} else {
			mini.alert("请选择一行数据");
		}
		
	},
	mergeHelpType : function(levelField) {
		if(!MiniCom.isValidForm('helpTypeForm')) {
			mini.alert('表单数据有误');
			return;
		}
		var data = new mini.Form('helpTypeForm').getData();
		if(/.*[\u4e00-\u9fa5]+.*$/.test(data.identifiers)) {
	        mini.alert("栏目标识符不能含有中文！");  
	        return;  
        }  
		var t = operate_help.parentTreegrid;
		data.url = 'operatemgr/help/type';
		// 设置上级栏目
		data.parent = mini.get('parent').getValue();
		//  如果是新增栏目，则该栏目的level为上级目录的level加1，修改时栏目的level不变
		if(!data.id)
		  data.menu = mini.get('parent').getSelectedNode()[levelField]+1;
		if(data.menu > 4) {
			mini.alert('栏目最多为四级');
			return;
		}
		param = {
			url : 'operatemgr/help/type',
			action : data.action,
			data : data
		}
		
		Cms.saveDataStringify(param, function() {
			operate_help.helpTypeDetailWindow.hide();
			Cms.reload("helpTypeTreegrid");
		});
	},
	/**
	 * @desc 若顺序有改变，则保存文章栏目顺序
	 * @returns 
	 */
	saveHelpTypeOrder : function(treeGridId) {
		var treeGrid = mini.get(treeGridId);
		var param = {
			action : 'put',
			url : 'operatemgr/help/type/order',
			data : treeGrid.getList()
		};
		if(treeGrid.isChanged()) {
			Cms.saveDataStringify(param, function() {
				Cms.reload("helpTypeTreegrid");
			});
		}
	},
	closeHelpType : function() {
		operate_help.helpTypeDetailWindow.hide();
	},
	
	/**
	 * 展示文章列表
	 */
	listHelps : function(event) {
		// 非叶子结点可能有推送的文章
		var tab = operate_help.tabs.getActiveTab(),
		iframeEl = operate_help.tabs.getTabIFrameEl(tab),
		grid =  iframeEl.contentWindow.mini.get("helpGrid");
		grid.load({
			typeId : event.node.id
		});
	},
	/**
	 * 打开新增文章窗口
	 */
	showAddHelp : function() {
	    Cms.addTab({
	    	tabs : window.parent.operate_help.tabs,
	    	// contoller会返回tab页面的url
	    	url : 'operatemgr/help/help',
	    	title: '新增文章',
	    	callBack : operate_help.setTabCookie,
	    	action : "POST",
	    	helpId : null,
	    });
	},
	
	/**
	 * 打开修改文章窗口
	 */
	showEditHelp : function() {
		var row = mini.get("helpGrid").getSelected();
		if (row) {
			Cms.addTab({
		    	tabs : window.parent.operate_help.tabs,
		    	url : 'operatemgr/help/help',
		    	title: row.title,
		    	callBack : operate_help.setTabCookie,
		    	action : "POST",
		    	helpId : row.id,
		    });
		} else {
			mini.alert("请选择一行数据");
		}
	},
	/**
	 * 修改文章状态
	 */
	visitHelpStatus : function(newStatus) {
		var row = mini.get("helpGrid").getSelected();
		if (row) {
			mini.confirm("确定操作文章?", "提示", function(e) {
		    	if(e=="ok"){
		    		var param ={
							url:"operatemgr/help/status",
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
						Cms.reload("helpGrid");
					});
		    		
		    	}
		    }); 
		} else {
			mini.alert("请选择一行数据");
		}
	},
	setTabCookie : function(e, op) {
		// 以tab的id为key加字符串设置cookie
		Cookies.set('helpTabForm_'+ e.tab._id, {
			id : op.helpId,
			action : op.action
		});
		// 如果是修改文章则加载此文章的详情
		var tab = window.parent.operate_help.tabs.getActiveTab(),
		contentWindow = window.parent.operate_help.tabs.getTabIFrameEl(tab).contentWindow;
		if(op.helpId) {
			$.get('operatemgr/help/'+op.helpId,function(help) {
				// 初始化标题框的值
				contentWindow.$("div#titleDiv input").val(help.title);
				// 设置标签文字为文章标题
				tab.title = help.title;
				// 设置标签框的值
				var label = contentWindow.mini.get("label");                       
				// label文本有多少逗号就设置多少个value,这里的不关注label框的值，value随便加一个值
				for(var i=0, n=help.label.split(',').length, val=''; i<n ; i++) {
					val += i===(n-1) ? 'a' : 'a,';
				}
				label.setValue(val);
				label.setText(help.label);
				// 显示已输入多少个标签
				contentWindow.updateLabelLength(help.label);
				var ue = contentWindow.UE.getEditor('editor');
				ue.setContent(help.content);
				// 如果有内容则选中对应的checkbox
				help.sources && contentWindow.mini.get("helpSourcesCheck").setChecked(true);
				help.url && contentWindow.mini.get("helpUrlCheck").setChecked(true);
				
				//var tabs = e.sender;
			    //var iframe = tabs.getTabIFrameEl(e.tab);
			    // 如果有封面图片则加载图片
                contentWindow.initImageInfo(help);
				// 封面图片显示在正文中
				//contentWindow.mini.get("icoDisplay").setValue(help.icoDisplay == 1);
				// 如果有help_top表信息
				if(help.helpTops) {
					// 调用详情页面help_detail的函数，在详情页面的页面的上下文中才能获取详情页面元素
					contentWindow.initHelpTopInfo(help);
				}
				// 保证字典选加载
				ComReq.dictionary("1513,1515", function(result) {
					contentWindow.mini.get("status").setData(result["1513"]);
					contentWindow.mini.get("display").setData(result["1515"]);

					var helpForm =  new contentWindow.mini.Form("helpForm"),
					helpDetailForm =  new contentWindow.mini.Form("helpDetailForm");
					helpForm.setData(help);
					helpDetailForm.setData(help);
					// 设置栏目下拉框的值
					contentWindow.mini.get("typeId").setValue(help.typeId);
					// 设置栏目下拉框不可选择
					contentWindow.mini.get("typeId").set({"enabled" : false});
					// 设置文章在哪些端显示,0不显示;1显示 ，各端对应的数据字典值为 1web 2wap 3android 4ios
					var displayValue =[];
					help.web && displayValue.push(1);
					help.wap && displayValue.push(2);
					help.android && displayValue.push(3);
					help.ios && displayValue.push(4);
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
	mergeHelp : function() {
		// 验证添加文章tab页面的必填字段
		if(!MiniCom.isValidForm('helpDetailForm')) {
			mini.alert('表单数据有误');
			return;
		}
		if(!MiniCom.isValidForm('helpForm')) {
			mini.alert('表单数据有误');
			return;
		}
		// 如果选中置顶到本栏目则要填写上下线时间
		if(mini.get("isRecommend").checked) {
			if(!mini.get("uptime").getValue() || !mini.get("downtime").getValue()){
				mini.alert('请选择置顶本栏目的上下线时间');
				return;
			}
		}
		var content = UE.getEditor('editor').getContent();
		if(!$.trim(content)) {
			mini.alert('请输入内容');
			return;
		}
		var formData,
		formDetailData,
		helpTop,
		helpTops = [],
		// 选中的tab
		tab = window.parent.operate_help.tabs.getActiveTab(),
		// 标题有自己的input框，取此框的输入值
		//title = $.trim($(content).first().text()), // 默认第一个HTML标签是标题，若页面结构有修改则要对应修改获取title的代码
		title = $.trim($("div#titleDiv input").val());
		if(!title) {
			mini.alert('请输入文章标题');
			return;
		}
		var data = {
			//url : 'operatemgr/help',
			content : content,
			// 
			title : title
		}
		, uptime, downtime, title, error, errorMsg;
		
		// 通过cookie读取文章id和action提交方式
		$.extend(data, Cookies.getJSON('helpTabForm_'+tab._id));
		
		formData = new mini.Form('helpForm').getData();
		formDetailData = new mini.Form('helpDetailForm').getData();
		// 如果选中"文章来源"
		mini.get("helpSourcesCheck").checked && (data.sources = formDetailData.sources);
		// 如果选中"文章链接"
		mini.get("helpUrlCheck").checked && (data.url = formDetailData.url);
		// 摘票 选填，如果不填写会默认抓取正文前100个字
		//$(content).first().next().text();
		var $content = $(content);
		data.summary = $.trim(formDetailData.summary) || 
			$.trim($content.eq(0).text()).substring(0,100);
		// 若有上传音频，要提取音频路径单独存到表字段中，只保存第一个音频
		var audios = $content.find('audio');
		if(audios)
			data.audio = $(audios.get(0)).prop('src');
		// 最多输入12个标签，每个标签最多8个字，不填写会默认为标题
		data.label = $.trim(mini.get('label').getText()) || title.substring(0, 8);
		// 替换中文逗号
		data.label = data.label.replace(/，/g,',');
		
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
		helpTop = {
			id : mini.get("originalTopId").getValue(),
			typeId : mini.get("typeId").getSelectedNode().id,
			//helpId : ,
			title : title,
			uptime : uptime,//mini.get("uptime").getFormValue(),
			downtime : downtime//mini.get("downtime").getFormValue(),
		}
		// 是否"置顶在栏目中"
		helpTop.isTop = mini.get("isRecommend").checked ? 1 : 0;
		helpTops.push(helpTop);
		// 动态添加的置顶栏目信息
		//var $hasChecked = $("tr.sendToTopTr1 input[name='sendToTop']:checked:enabled",'#helpDetailForm'), 
		var $sendTopDiv, $isTop, helpType;
		$.each($("div.mySendTop"), function(index, sendTopDiv) {
			$sendTopDiv = $(sendTopDiv);
			// 注意由data()方法返回的是字符串
			helpType = $sendTopDiv.data("helptype");
			// 如果选中推送到其他栏目
			if(mini.get("sendToTop" + helpType).checked) {
				helpTop = {
					// 若是修改文章则有id,否则无id
					id : $sendTopDiv.data('helptopid'),
					typeId : helpType,
					// 在其栏目中是否置顶
					isTop : mini.get("isTop" + helpType).checked ? 1 : 0,
					//helpId : ,
				}
				// 标题、上下线时间
				//$topTr = $("tr.sendToTopTr2#"+typeId,'#helpDetailForm');
				//mini.get("").checked;
				uptime = mini.get("uptime" + helpType).getValue();
				downtime = mini.get("downtime" + helpType).getValue();
				title = mini.get("title" + helpType).getValue();
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
				helpTop.title = title;
				helpTop.uptime = uptime;
				helpTop.downtime = downtime;
				helpTops.push(helpTop);
			} 
			// else {这里是没勾选，即要删除help_top中间表的id}
		});
		if(error) {
			mini.alert(errorMsg);
			error = false;
			errorMsg = '';
			return;
		}
		data.helpTops = helpTops;
		data.ico = formDetailData.ico;
		data.releaseTime = formDetailData.releaseTime;
		//封面图片显示:0不显示;1显示
		data.icoDisplay = mini.get("icoDisplay").checked ? 1 : 0;
		data.createBy = formDetailData.createBy;
		$.extend(data, formData);
		//  层级
		//data.menu = mini.get('typeId').getSelected().data[levelField];
		// 获取文章栏目名称
		//data.typeName = mini.get('typeId').getSelected().data.fullName;
		data.typeName = mini.get("typeId").getSelectedNode().fullName;
		// 处理显示值 
		// 各端对应的数据字典值为 1web 2wap 3android 4ios
		data.web = data.display.indexOf("1") == -1 ? 0 : 1;
		data.wap = data.display.indexOf("2") == -1 ? 0 : 1;
		data.android = data.display.indexOf("3") == -1 ? 0 : 1;
		data.ios = data.display.indexOf("4") == -1 ? 0 : 1;
		//帮助中心表有旧字段display，所以这里要置空
		//data.display=null;
		if(data.web === 0 && data.wap === 0 && data.android === 0 && data.ios === 0) {
			mini.alert('请至少选择一个文章显示平台');
			return;
		}
		var param = {
			action : data.action,
			// 根据是否有文章id来判断是新增还是修改，且访问不同的controller方法
			url : 'operatemgr/help/' + (data.id ? 'update' : 'add'),
			data : data
		};
		Cms.saveDataStringify(param,function removeTab() {
			//保存成功则1.5秒后关闭标签
			setTimeout(function(){ 
				var tabs = window.parent.operate_help.tabs;
				var tab = tabs.getActiveTab();
				// 刷新第一个标签，即"文章列表"
				tabs.getTabIFrameEl(tabs.getTab(0)).contentWindow.mini.get('helpGrid').reload();
	            if (tab) {
	                tabs.removeTab(tab);
	            }
			}, 1000);
			
        });
	},
	// 只有非虚拟目录的叶子结点才能添加文章
	filterHelpType : function (e) {
	  //禁止选中父节点或虚拟目录
	  if (e.isLeaf == false || e.virtual === 1){
		  e.cancel = true;
		  mini.alert("只能选择叶子结点和非虚拟目录");
	  }
	},
	// 非本文章目录的结点都可推送，包括叶子结点和非叶子结点，虚拟或排虚拟栏目
	filterTopType : function (e) {
		if( e.node.id === mini.get('originalTop').getValue() ) {
			e.cancel = true;
			mini.alert("不能推送到自身栏目");
		}
	},
	// 增加置顶或推送设置
	addSendToTop : function (e) {
		// mini.get("typeTopId").getList();
		// 根据选择的结点pid，寻找父结点，组成导航url
		var isFromEditHelp = e.id, 
		nodes= mini.get("typeTopId").getList(), 
		selectedNode;
		if(isFromEditHelp) {
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
		if(!isFromEditHelp && !selectedNode) {
			mini.alert("请选择一个栏目");
			return;
		}
		// 判断是否置顶栏目是否已经生成
		if($("div#sendToOtherTypeDiv div#mySendTop"+selectedNode.id).length>0) {
			mini.alert("已推送此栏目");
			return;
		}
			
		var navUrls = [selectedNode.fullName], i, urlText = '', $div;
		operate_help.findParentUrl(selectedNode, nodes, navUrls);
		for(i = navUrls.length - 1; i >= 0 ; i--) {
			urlText += navUrls[i];
			if(i !== 0 )
				urlText += ' > ';
		}
		// 统一加"class"属性，方便控制"全选"、"反选"，加"id"以防止生成重复置顶栏目
		// 若是修改文章，则会有上helpTopId
		$div = $("<div>", {'data-helptype' : selectedNode.id, 'data-helptopid' : e.id, id : 'mySendTop'+selectedNode.id, class : 'mySendTop'})
			.append($("<input>", {'id' : 'sendToTop' + selectedNode.id, class : 'mini-checkbox', checked : 'true'}))
			.append($("<span>").text(urlText))
			.append($("<input>", {'id' : 'isTop' + selectedNode.id, class : 'mini-checkbox', checked : 'true'}))
			.append($("<span>").text('置顶在栏目中'))
			.append($("<br>"))
			.append($("<span>").text('标题'))
			.append($("<input>", {'id' : 'title' + selectedNode.id, class : 'mini-textbox', checked : 'true'}))
			.append($("<span>").text('上线时间'))
			.append($("<input>", {'id' : 'uptime' + selectedNode.id, class : 'mini-datepicker', format : 'yyyy-MM-dd HH:mm:ss', timeFormat : 'HH:mm:ss', showTime : 'true' , showOkButton : 'true', showClearButton : 'false', oncloseclick : 'Cms.onCloseClick', showClose : 'true'}))
			.append($("<span>").text('下线时间'))
			.append($("<input>", {'id' : 'downtime' + selectedNode.id, class : 'mini-datepicker', format : 'yyyy-MM-dd HH:mm:ss', timeFormat : 'HH:mm:ss', showTime : 'true' , showOkButton : 'true', showClearButton : 'false', oncloseclick : 'Cms.onCloseClick', showClose : 'true'}))
		
		$("#sendToOtherTypeDiv").append($div);
		// 动态添加的mini控件要重新解析
		mini.parse();
		// 如果是修改文章，则设置文章栏目推送信息的值
		if(isFromEditHelp) {
			// 是否推送到栏目，默认选中
			// mini.get("sendToTop" + selectedNode.id).setChecked(true);
			// 是否置顶
			mini.get("isTop" + selectedNode.id).setChecked(e.isTop === 1);
			// 置顶标题
			mini.get("title" + selectedNode.id).setValue(e.title);
			mini.get("uptime" + selectedNode.id).setValue(new Date(e.uptime));
			mini.get("downtime" + selectedNode.id).setValue(new Date(e.downtime));
			//var tab = window.parent.operate_help.tabs.getActiveTab(),
			//iframeEl = window.parent.operate_help.tabs.getTabIFrameEl(tab);
			//iframeEl.contentDocument.getElementById('color' + selectedNode.id).jscolor.fromString(e.color);
			//mini.get("color" + selectedNode.id).setValue(e.color);
		}
	},
	findParentUrl : function(node, nodes, navUrls) {
		$.each(nodes, function(index, currNode) {
			if(currNode.id === node.parent) {
				navUrls.push(currNode.fullName);
				// 如果不是根结点，继续查找父结点
				if(currNode.parent !== -1) {
					operate_help.findParentUrl(currNode, nodes, navUrls);
				} else {
					// 如果是根结点
					return;
				}
			}
		});
	},
	setImage:function(data, erasable){
		// 检查已经选择了多少张图片
		var len, $div, splitUrl;
		len = $("div.helpImgDiv").length;
		if(len >= 1) {
			mini.alert("最多选择1张图片");
			return;
		}
		  
		$div = $("<div>",{class :"helpImgDiv"})
			.append($("<img>", {id : "helpImgTag", class : "helpImgClass", alt:"封面图片", src : data.url}))
			if(erasable)
				$div.append($("<span>", {id : "erase", text: "X", style:"color:red;font-size:17px;position:absolute; top:0px; right:10px; z-index:99;"}));
			$div.append($("<br>"))
			.append("文件名：").append($("<input>", {id:"helpImgName", class:"mini-textbox", enabled:"false"}))
			.append($("<br>"))
			.append("图片格式：").append($("<input>", {id:"helpImgFormat", class:"mini-textbox", enabled:"false"}))
			.append($("<br>"))
			.append("大小：").append($("<input>", {id:"helpImgSize", class:"mini-textbox", enabled:"false"}))
			.append($("<br>"))
			.append("创建日期：").append($("<input>", {id:"helpImgCreateTime",class:"mini-datepicker", enabled:"false", format:"yyyy-MM-dd HH:mm:ss"}))
			.append($("<br>"))
			.append($("<input>", {class:"mini-hidden" ,name:"ico", id:"ico"}));
		$("tr#helpImgTr").append($("<td>").append($div));
		mini.parse();
		splitUrl = data.url.split(".");
		//$("helpImgTag"+len).attr("src",data.url);
	    mini.get("helpImgName").setValue(data.name);
	    mini.get("helpImgFormat").setValue(splitUrl[splitUrl.length - 1]);
	    mini.get("helpImgSize").setValue(data.sizes);
	    mini.get("helpImgCreateTime").setValue(data.createTime);
	    mini.get("ico").setValue(data.dir);
	},
	openImage:function(){
		Cms.imageManage(operate_help.catalogue,function(data){
			// 第二个参数表示新添加的图片可删除
			operate_help.setImage(data, true);
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
    // 预览文章
    preview : function(e) {
    	var formData = new mini.Form('helpForm').getData(),
		formDetailData = new mini.Form('helpDetailForm').getData(),
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
    	var auditBy = dateUtils.formatDate(formData.auditBy);
    	var createBy = formDetailData.createBy;
    	var preHtml = rawContent.format(title, releaseTime, createBy, createTime, modifyTime, auditTime, auditBy);
    	ue.execCommand("preview",{'pre':preHtml});
    }
};