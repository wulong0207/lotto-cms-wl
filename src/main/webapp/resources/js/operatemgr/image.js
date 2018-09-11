var Current = {
	"init" : function() {
		Current.chooseValue = true;
		mini.parse();
		Current.imageGroup = mini.get("imageGroup");
		ComReq.dictionary("1522", function(result) {
			Dic.imageGroup = result["1522"];
			Current.imageGroup.setData(Dic.imageGroup);
		});
		Current.page = new Page(32);
		Current.search();
		// 上传图片保存路径
		Current.catalogue = Cms.getCookie('catalogue');
	},
	/**
	 * 设置节点的值
	 * @param node 父节点
	 * @param img 数据
	 */
	setValue : function(node, img) {
		var splitUrl = img.url.split(".");
		var children = node.children;
		children[0].src = img.url;
		children[0].title = "图片详情\n文件名：" + img.name + "\n图片格式："
							+ splitUrl[splitUrl.length - 1]
							+ "\n分辨率大小：" + img.resolution + "\n大小："
							+ img.sizes + "KB\n创建日期：" + img.createTime
							+ "\n上传人员：" + img.createBy;
		children[2].value = img.id;
		children[4].value = img.name;
		children[5].value = JSON.stringify(img);
	},
	/**
	 * 获取节点值
	 * @param node
	 * @returns {json 对象}
	 */
	getValue : function(node) {
		var children = node.children;
		return {
			src : children[0].src,
			title : children[0].title,
			id : children[2].value,
			name : children[4].value,
			info: children[5].value
		}
	},
	/**
	 * 获取被选中图片
	 * @returns {Array}
	 */
	choose : function() {
		var images = [];
		var chooses = $("input[name=choose]");
		for (var i = 0; i < chooses.length; i++) {
			var choose = chooses[i];
			if (choose.checked == true) {
				var parent = choose.parentNode;
				images.push(Current.getValue(parent));
			}
		}
		return images;
	},
	/**
	 * 全选
	 */
	chooseAll : function() {
		var chooses = $("input[name=choose]");
		for (var i = 0; i < chooses.length; i++) {
			var choose = chooses[i];
			choose.checked = Current.chooseValue;
		}
		Current.chooseValue = !Current.chooseValue;
	},
	/**
	 * 反选
	 */
	chooseAgainst : function() {
		var chooses = $("input[name=choose]");
		for (var i = 0; i < chooses.length; i++) {
			var choose = chooses[i];
			choose.checked = !choose.checked;
		}
	},
	/**
	 * 弹出上传图片窗口
	 */
	uploadWindow : function() {
		mini.open({
            url: "operatemgr/image/upload/page?catalogue="+Current.catalogue,
            title: "上传图片",
            width: 800, height: 600,
            ondestroy: function (data) {
            	if(data && data !="close"){
            		Current.search();
					Cms.showTips("上传成功",3000)
            	}
            }
	    });
	},
	/**
	 * 关闭窗口
	 * @returns
	 */
	closeWindow : function() {
		var image = Current.choose();
		if (image.length > 1) {
			mini.alert("选择图片错误，只能选择一张图片");
			return;
		} else if (image.length == 0) {
			mini.alert("未选择图片");
			return;
		}
		if (window.CloseOwnerWindow)
			return window.CloseOwnerWindow(eval('(' + image[0].info + ')'));
		else
			window.close();
	},
	search : function() {
		var param = {
			url : "operatemgr/image/list",
			action : "get",
			data : {
				groupid : Current.imageGroup.getValue(),
				name : mini.get("imageName").getValue(),
				pageIndex : Current.page.pageIndex,
				pageSize : Current.page.pageSize
			}
		}
		Cms.ajax(param, function(result) {
			Current.page.setTotal(result.total);
			$("#total").html(result.total);
			$("#page").html(Current.page.showPageNum());
			if (result.data) {
				// 填充数据
				var tds =$("#imgTable").find("td")
				for (var i = 0; i < tds.length; i++) {
					if (i < result.data.length) {
						var img = result.data[i];
						tds[i].style.display = "";
						Current.setValue(tds[i],img);
					} else {
						tds[i].style.display = "none";
					}
				}
				// 绑定事件
				var imgs = $("td > img");
				for (var i = 0; i < imgs.length; i++) {
					var img = imgs[i];
					img.onclick = function() {
						var children = this.parentNode.children;
						var choose = children[3];
						var check = choose.checked;
						choose.checked = !check;
					}
					img.ondblclick = function(){
						window.open(this.src);
					}
				}
				Current.chooseValue = false;
				Current.chooseAll();
			}
		});
	},
	up : function() {
		Current.page.up(function() {
			Current.search();
		});
	},
	next : function() {
		Current.page.next(function() {
			Current.search();
		});
	},
	skip : function() {
		var skip = mini.get("skipValue").getValue();
		Current.page.skip(skip, function() {
			Current.search();
		});
	},
	move : function() {
		var value = Current.imageGroup.getValue();
		var formValue = Current.imageGroup.getText();
		if(!value){
		   mini.alert("请选择分组！");
		   return;
		}
		var image = Current.choose();
		if(image.length == 0){
			mini.alert("请选择移动分组的图片");
			return;
		}
		mini.confirm("是否移动到"+formValue, "提示", function(e) {
			if (e == "ok") {
				var id = image[0].id
				for(var i=1;i<image.length;i++){
					id = id +"," + image[i].id
				}
				var param = {
						action:"PUT",
						url:"operatemgr/image/move",
						data:{
							ids:id,
							groupid:value
						}
				}
				Cms.ajax(param,function(result){
					Cms.showTips("移动成功",3000)
					Current.search();
				});
			}
		});
	},
	deleteImage:function(){
		var image = Current.choose();
		if(image.length == 0){
			mini.alert("请选择删除图片");
			return;
		}
		mini.confirm("是否确定删除？", "提示", function(e) {
			if (e == "ok") {
				var id = image[0].id
				for(var i=1;i<image.length;i++){
					id = id +"," + image[i].id
				}
				var param = {
						action:"POST",
						url:"operatemgr/image/delete",
						data:{
							ids:id
						}
				}
				Cms.ajax(param,function(result){
					Cms.showTips("删除成功",3000)
					Current.search();
				});
			}
		});
	},
	saveImage:function(){
		var image = Current.choose();
		if(image.length == 0){
			mini.alert("请选择修改图片");
			return;
		}
		mini.confirm("是否确定修改？", "提示", function(e) {
			if (e == "ok") {
				var param = {
						action:"PUT",
						url:"operatemgr/image"
				}
				var count = 0;
				for(var i=0;i<image.length;i++){
					param.data = {
							id:image[i].id,
							name:image[i].name
					};
					Cms.ajax(param,function(result){
						count++;
						if(count == image.length){
							Cms.showTips("保存成功",3000)
						}
					});
				}
			}
		});
	},
	reload:function(){
		Current.search();
	},
	searchAgain:function(){
		Current.page.pageIndex = 0;
		Current.search();
	}

}
Current.init();