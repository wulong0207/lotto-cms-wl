<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>resources/js/public/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>resources/js/public/ueditor/ueditor.all.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>resources/js/public/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>resources/js/public/jscolor.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>resources/js/public/date.utils.js"></script>
    <style>
    	div.articleInfoDiv {
    		border: 1px solid #F5F5F5;
    		padding: 10px;
    	}
    	.mini-datepicker {
    		width: 180px;
    	}
    	img.articleImgClass {
    		width : 120px;
    		height:120px;
    	}
    	#titleDiv input{
    		height:50px;
    		width:100%;
    		font-size:20px;
    	}
    	.mini-treeselect {
    		width:200px;
    	}
    </style>
</head>

<body>
<div id="articleDetailWindow" class="mini-window" style="width:100%;height:98%;" showCloseButton="false">
        <div class="mini-toolbar">                
          <btn:operate privilege="SEARCH">
            <a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_article.preview">预览</a>                  
        	</btn:operate>
          <btn:operate privilege="ADD">
            <a class="mini-button" iconCls="icon-save" plain="true" onclick="operate_article.mergeArticle" id="saveBtn">保存</a>                  
        	</btn:operate>
        </div>
<div class="mini-splitter" style="width:100%;height:400%;">
    <div id="articleForm" size="16%" showCollapseButton="true" style="padding:2px;">
    	<%-- 修改文章时，保留置顶到本栏目的id --%>
    	<input class="mini-hidden" name="originalTopId" id="originalTopId"/>
    	<input class="mini-hidden" name="originalTop" id="originalTop"/>
    	<input class="mini-hidden" name="articleId"/>
    	<input class="mini-hidden" name="articleUrl"/>
    	<input class="mini-hidden" name="click"/>
    	<%--
    	<input class="mini-hidden" name="createTime"/>
    	<input class="mini-hidden" name="action"/>
    	--%>
   		<div>文章基本信息</div>
   		<div>
   			<span>文章栏目</span>
   			<input id="typeId" name="typeId" class="mini-treeselect" multiSelect="false" valueFromSelect="true"
        textField="typeFullName" valueField="id" parentField="typeParent"
         showFolderCheckBox="false" required="true" expandOnLoad="true" onbeforenodeselect="operate_article.filterArticleType"/>
   		</div>
   		<div>
   			<span>文章状态</span>
   			<input id="status" name="status" class="mini-combobox" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" enabled="false"/>
   		</div>
   		<div>
   			<span>文章显示</span>
   			<input id="display" name="display" class="mini-combobox" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick" multiSelect="true" required="true"/>
   		</div>
		<div>审核时间<input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></div>
		<div>审核时间<input name="auditTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></div>
		<div>审核人员<input name="auditBy" class="mini-textbox" enabled="false"></div>
		<div>修改时间<input name="modifyTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></div>
		<div>修改人员<input name="modifyBy" class="mini-textbox" enabled="false"></div>
		
		<div>多媒体</div>
		<div>图片</div>
		<div>视频</div>
		<div>音频</div>
		<div>投票</div>
		<div>模版</div>
    </div>
    
    <div showCollapseButton="true" id="articleDetailForm">
    	<div id="titleDiv"><input id="title" type="text" placeholder="请在这里输入标题"/></div>
    	<div>
    		发布时间：<input id="releaseTime" name="releaseTime" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true" required="true"/>  
    		作者：<input name="createBy" class="mini-textbox" enabled="false"/>
    	</div>
        <script id="editor" type="text/plain" ></script>
        <div class="articleInfoDiv">
    			<input id="articleFromCheck" class="mini-checkbox" text="文章来源"/>
    			<input id="articleFrom" name="articleFrom" class="mini-textbox"/>
    			<input id="fromUrlCheck" class="mini-checkbox" text="文章链接"/>
    			<input id="fromUrl" name="fromUrl" class="mini-textbox"/>
    		</div>
    		<div class="articleInfoDiv" id="uploadImageDiv">
    			<span>发布编辑</span><br/>
    			<span>封面  大图片建议尺寸：900像素 * 500像素</span>
				<btn:operate privilege="UPD">
					<input type="button" id="imgBtn" value="从图库选择" onclick="operate_article.openImage()"/>
				</btn:operate>
				<table>
					<tr id="articleImgTr">
					</tr>
				</table>
				
    		</div>
    		<div class="articleInfoDiv">
    			<span>摘要 选填，如果不填写会默认抓取正文前100个字</span><br/>
    			<textarea style="width:100%;" id="articleSummary" name="articleSummary" class="mini-textarea" emptyText="请输入摘要"></textarea><br/>
    			<span style="float:right">0/120</span>
    		</div>
    		<div class="articleInfoDiv">
    			<span>标签 选填，标签用回车分开  ，最多输入12个标签，每个标签最多8个字，不填写会默认为标题</span></br>
    			输入框:<input id="articleLabelInput" style="width:150px;"/>
    			<input id="articleLabel" name="articleLabel" class="mini-textboxlist" style="width:600px;" onvaluechanged="handleLabelChange"/>
    			<span id="articleLabelCounter">0/12</span>
    		</div>
    		<div class="articleInfoDiv">
    			<span>置顶  选填，可把本文章置顶在栏目中</span><br/>
    			<input id="isRecommend" class="mini-checkbox" text="置顶在栏目中"/>
    			<span>标题颜色</span><input id="color" class="jscolor {hash:true}" value="000000">
    			<span>上线时间</span>
    			<input id="uptime" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				<span>下线时间</span>
				<input id="downtime" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
    		</div>
    		<div class="articleInfoDiv" id="sendToOtherTypeDiv">
    			<span>推荐  选填，可把本文章推荐到其它栏目中</span><br/>
    			<span>文章栏目</span>
    			 
    			<input id="typeTopId" class="mini-treeselect" multiSelect="false"  valueFromSelect="true"
        textField="typeFullName" valueField="id" parentField="typeParent" checkRecursive="true"
        showFolderCheckBox="false" expandOnLoad="true" showClose="true" onbeforenodeselect="operate_article.filterTopType"/>
        		<a class="mini-button" onclick="operate_article.addSendToTop">增加</a>
				<a class="mini-button" onclick="onClick">全选</a>
				<a class="mini-button" onclick="onClick">反选</a>
				<a class="mini-button" onclick="onClick">全不选</a>
    		</div>
    </div>
</div>
</div>


</body>
<script src="<%=basePath%>resources/js/public/js.cookie.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/ie-fix.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/operate_article.js" type="text/javascript"></script>
<script>
// 	// 上传图片、视频等
// 	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
//     UE.Editor.prototype.getActionUrl = function(action) {  
//         if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadvideo') {  
//             return '${ctx}/manage/attach/uploadFile.do';  
//         } else {  
//             return this._bkGetActionUrl.call(this, action);  
//         }  
//     } 
  //实例化编辑器
  //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
  	//容器给定高度
	UE.getEditor('editor', {
		//initialFrameWidth : 600,
		initialFrameHeight : 700,
		autoHeightEnabled:false,
		readonly:false
	});
	var ue = UE.getEditor('editor');
	$(function() {
		//var tab = window.parent.operate_article.tabs.getActiveTab();
		//var cookie = Cookies.getJSON('articleTabForm_' + tab._id);

		$.get("operatemgr/article/type", function(nodes) {
			// 页面右侧的下拉框显示非虚拟目录
			var notVirtulNodes = [];
			$.each(nodes, function(index, node) {
				if (node.virtual === 0)
					notVirtulNodes.push(node);
				node.disabled = true;
			});
			mini.get("typeId").loadList(notVirtulNodes, 'id', 'typeParent');
			// 两个下拉框不能共用nodes
			var tmp = [];
			$.extend(true, tmp, nodes);
			mini.get("typeTopId").loadList(tmp, 'id', 'typeParent');
		});
		ComReq.dictionary("1513,1515", function(result) {
			mini.get("status").setData(result["1513"]);
			mini.get("display").setData(result["1515"]);
		});
		mini.get('articleDetailWindow').show();
		// 绑定标签输入框的回车键事件
		$('#articleLabelInput').keypress(function (e) {
		    if (e.which == '13') {
		    	// 最多输入12个标签，每个标签最多8个字
		    	// 一个中文字的长度也为1
		    	var label = mini.get('articleLabel'),
		    	oldText = label.getText(),
		    	inputText = $(this).val();
		    	if(inputText.length > 8) {
		    		mini.alert('每个标签最多8个字');
		    		return;
		    	}
		    	var len = oldText.split(',').length;
		    	if(len >= 12) {
		    		mini.alert('最多输入12个标签');
		    		return;
		    	}
		    	// 同时setValue和setText才有效
		    	// 这里的不关注label框的值，value随便加一个值
		    	var newText, newValue;
		    	if(!$.trim(oldText)) {
		    		// 如果oldText为空，则为第一个标签
		    		newValue = 'a';
		    		newText = inputText;
		    	} else {
		    		newText = oldText+','+$(this).val(),
			    	newValue = label.getValue() +',a';
		    	}
				label.setValue(newValue);
				label.setText(newText);
		        // 把输入框清空
		        $(this).val('');
		        // 显示已输入多少个标签
		        var labelCount = $.trim(oldText) ? len : 0;
		        $("#articleLabelCounter").text(labelCount+1+'/12');
		        
		    }
		});
			    
	    $("#cancel").live("click", function(){
	    	$(this).parent().remove();
	    })		
	});
	function initArticleTopInfo(article) {
		$.each(article.articleTops, function(index, articleTop) {
			// 如果是推送到本栏目
			if (articleTop.typeId === article.typeId) {
				// 置顶记录在top表中的id
				mini.get("originalTopId").setValue(articleTop.id);
				// 推送栏目的id
				mini.get("originalTop").setValue(articleTop.typeId);
				// 是否置顶
				mini.get("isRecommend").setChecked(articleTop.isTop === 1);
				//mini.get("color").setValue(articleTop.color);
				document.getElementById('color').jscolor
						.fromString(articleTop.color);
 				mini.get("uptime").setValue(new Date(articleTop.uptime));
				mini.get("downtime").setValue(new Date(articleTop.downtime));
			}
			// 如果是推送到其他栏目
			else {
				//document.getElementById('color'+articleTop.typeId).jscolor;
				operate_article.addSendToTop(articleTop);
			}
		});
		// 根据文章状态设置文章是否可编辑及是否可保存
		var status = article.status;
		if(status===2 || status===3) {
			ue.setDisabled();
		}
		// 待审核的才隐藏"保存"
		if(status===2) {
			$("#saveBtn").hide();
		}
		// 审核通过的文章可以修改推送栏目信息，其他信息不能修改
		if(status===3){
			setHtmlEnabled(false);
		}
	}
	function setHtmlEnabled(status) {
		var op = {
			enabled : status
		};
		$('#title').prop('disabled', !status);
		mini.get('display').set(op);
		mini.get('releaseTime').set(op);
		mini.get('articleFromCheck').set(op);
		mini.get('articleFrom').set(op);
		mini.get('fromUrlCheck').set(op);
		mini.get('fromUrl').set(op);
		$('#imgBtn').prop('disabled', !status);
		mini.get('articleSummary').set(op);
		$('#articleLabelInput').prop('disabled', !status);
		mini.get('articleLabel').set(op);
		mini.get('isRecommend').set(op);
		//$('#color').prop('disabled', !status).prop("class",null);
		$('#color').hide();
		mini.get('uptime').set(op);
		mini.get('downtime').set(op);
	}
	// 初始化封面图片数据
	function initImageInfo(article) {
		// 如果有封面图片则加载图片
		if (article.articleImg) {
			var isCancel = false;
			if(article.status == 1)
				isCancel = true;
			article.articleImg.split(',').forEach(
				function(img) {
					ComReq.imageDetail(img, function(data) {
						operate_article.setImage(data, isCancel);
					});
				}
			);
		}
	}
	
	function handleLabelChange(e) {
        updateLabelLength(e.value);
	}
	
	function updateLabelLength(labelText) {
		// 显示已输入多少个标签
		var len = labelText ? labelText.split(',').length : 0;
        $("#articleLabelCounter").text(len+'/12');
	}
	
	//自定义百度ueditor上传图片路径操作
	UE.Editor.prototype.bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	UE.Editor.prototype.getActionUrl = function(action){
		if(action != null && action != '' && action != undefined &&
				action.indexOf('operatemgr/image/upload') > -1){
			var url = '<%=basePath%>' + 'operatemgr/image/upload?catalogue=_editor&groupid=0&source=ueditor';
			console.log(url);
			return url;
		}else{
			return this.bkGetActionUrl.call(this, action);
		}
	}
</script>
</html>
