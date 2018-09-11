var Current = {
	"init" : function() {
		mini.parse();
		Current.imageGroup_edit = mini.get("imageGroup_edit");
		Current.totalSize = 0;
		ComReq.dictionary("1522", function(result) {
			Dic.imageGroup = result["1522"];
			Current.imageGroup_edit.setData(Dic.imageGroup);
			Current.imageGroup_edit.select(0);
		});
		// 上传图片保存路径
		Current.catalogue = Cms.getCookie('catalogue');
		document.getElementById('catalogue').value = Current.catalogue;
		var type = Cms.getCookie('type');
		if(type && type == "single"){
			//单个图片上传
			$("#prompt").html("选择要上传的文件：");
			Current.fileInput = "<input type=\"file\" name=\"file\" accept=\"image/png,image/gif,image/jpeg\"  /><span></span><br/>";
		}else{
			//多个图片上传
			$("#prompt").html("选择要上传的文件(可以选择多张)：");
			Current.fileInput = "<input type=\"file\" name=\"file\" multiple=\"multiple\" accept=\"image/png,image/gif,image/jpeg\" /><span></span><br/>";	
		}
		Current.initImputFile();
	},
	upload : function() {
		debugger;
		var formData = new FormData($('form')[0]);
		// ajax异步上传
		$.ajax({
			url : "operatemgr/image/upload",
			type : "POST",
			data : formData,
			xhr : function() { // 获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数
				myXhr = $.ajaxSettings.xhr();
				if (myXhr.upload) { // 检查upload属性是否存在
					// 绑定progress事件的回调函数
					myXhr.upload.addEventListener('progress', function(e) {
						if (e.lengthComputable) {
							$('progress').attr({
								value : e.loaded,
								max : e.total
							}); // 更新数据到进度条
							var percent = e.loaded / e.total * 100;
							$('#progress').html(e.loaded + "/" + e.total + " bytes. "+ percent.toFixed(2) + "%");
						}
					}, false);
				}
				return myXhr; // xhr对象返回给jQuery使用
			},
			success : function(result) {
				if (result.errorCode == Code.success) {
					if(result.data.length > 0){
						var data = result.data[0];
						data.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
						window.CloseOwnerWindow(data);
					}else{
						mini.alert("上传失败");
					}
				} else {
					mini.alert(result.message);
				}
			},
			contentType : false, // 必须false才会自动加上正确的Content-Type
			processData : false // 必须false才会避开jQuery对 formdata 的默认处理
		});
	},
	changeFile : function() {
		$(':file').change(
				function() {
					var files = this.files;
					$(this).next().html("");
					for (var i = 0; i < files.length; i++) {
						var file = files[i]; // 假设file标签没打开multiple属性，那么只取第一个文件就行了
						name = file.name;
						size = file.size;
						type = file.type;
						url = window.URL.createObjectURL(file); // 获取本地文件的url，如果是图片文件，可用于预览图片
						$(this).next().html($(this).next().html() + "<br/>文件名：" + name+ " 文件类型：" + type + " 文件大小：" + size + "bytes");
						Current.totalSize += size;
					}
					$("#info").html("总大小: " + Current.totalSize + "bytes");
				});

	},
	initImputFile:function(){
		$("#files").html(Current.fileInput);
		$("#info").html("总大小: " + 0 + "bytes");
		Current.changeFile();
	}

}
Current.init();