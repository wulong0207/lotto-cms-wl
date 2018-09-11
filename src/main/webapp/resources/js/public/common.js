﻿﻿﻿var Msg = {
		"save":{"success":"保存成功","fail":"保存失败"}
};
var Code = {
		success:"10001"
}
var Data ={
		"adminCategory":[{id: 1, text: '福彩'}, {id: 2, text: '体彩'}],
		"lotteryCategory":[{id: 1, text: '数字彩'}, {id: 2, text: '高频彩'}, {id: 3, text: '竞技彩'}],
		"autoType":[{id: 1, text: '手动'}, {id: 2, text: '自动'}],
		"yesNo":[{id: 1, text: '是'},{id: 0, text: '否'}],
		"saleStatus":[{id: 1, text: '正常销售'},{id: 0, text: '暂停销售'}],
		"onOff":[{id: 1, text: '启用'}, {id: 0, text: '禁用'}],
		_buttons:[
		],
		userOptions:[
			{ "id": "userName", "text": "用户账号" },
			{ "id": "userRealName", "text": "姓名" },
			{ "id": "userMobile", "text": "手机号码" },
			{ "id": "userEmail", "text": "邮箱" },
		],
		week:[
			{ "id": "周一", "text": "周一" },
			{ "id": "周二", "text": "周二" },
			{ "id": "周三", "text": "周三" },
			{ "id": "周四", "text": "周四" },
			{ "id": "周五", "text": "周五" },
			{ "id": "周六", "text": "周六" },
			{ "id": "周日", "text": "周日" }
		]
};

var Constants = {
	win : "3",
	draw : "1",
	lost : "0"
}

var Dic = {
		"allCode":[],//所有彩种
		"numCode":[],//数字彩
		"highCode":[],//高频彩
	    "sportCode":[],//竞技彩
	    "adminCategory":[],//彩种主类型
		"lotteryCategory":[],//彩种类型
		"autoType":[],
		"yesNo":[{'id':'0','text':'否'},{'id':'1','text':'是'}],
		"saleStatus":[],
		"onOff":[],
		"area":[],
		"matchStatus":[],//赛事状态
		"matchResult":[],//赛事结果
		"sportFbChildPay":[],//竞彩足球子玩法
		"sportFbChildPayStatus":[],//竞彩足球子玩法状态
		"sportBJChildPay":[],//北京单场足球子玩法
		"sportBJChildPayStatus":[],//北京单场足球子玩法状态
	    "udsd":[],//北京单场足球上单下单上双下双
		"sportBBChildPay":[],//竞彩篮球子玩法
		"sportBBChildPayStatus":[],//竞彩篮球子玩法状态,
	    "matchType":[],//赛事类型
		"userRegisterChannel":[],//用户注册渠道
    "lotteryType": [],//彩种字典
    "redeemCodeType": []//兑换码类型
};

/**
 * 
 */
var Cms = {
		/**
		 * 
		 * @param miniFrom 提交表单（需要包含提交数据，url,请求类型）
		 * @param msg 提示信息
		 * @param successBack 回调函数
		 */
		"submit":function(miniFrom,successBack,message){
			miniFrom.validate();
		    if (miniFrom.isValid() == false){
	             mini.alert("表单数据有误");
		    	 return;
		    }
		    var data = miniFrom.getData(true, false);
		    Cms.submitByData(data,successBack,message);
		},
		"submitByData" :function(data,successBack,message){
			if(!message){
				message = "确定保存?";
			}
		    mini.confirm(message, "提示", function(e) {
		    	if(e=="ok"){
		    		MiniCom.mask("保存中。。。");
		    		$.ajax({
						url : data.url,
						data : data,
                        dataType: "json",
						type : data.action,
						success : function(res) {
							MiniCom.unmask();
							if(res == "1" || res.errorCode == Code.success){
								mini.showTips({
						            content: res.message,
						            state: "success",
						            x: "center",
						            y: "center",
						            timeout: 2000
						        });
								successBack();
							}else{
								mini.alert(res.message); 
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {
							MiniCom.unmask();
							mini.alert(jqXHR.responseText);
						}
					});
		    	}
		    }); 
		},
		/**
		 * 无需提示，直接保存方法
		 * @param miniFrom 提交表单（需要包含提交数据，url,请求类型）
		 * @param successBack 回调函数
		 */
		"noConfirmSubmit":function(miniFrom,successBack){
			miniFrom.validate();
		    if (miniFrom.isValid() == false){
	             mini.alert("表单数据有误");
		    	 return;
		    }
		    var data = miniFrom.getData(true, false);
		    $.ajax({
				url : data.url,
				data : data,
				type : data.action,
				success : function(res) {
					MiniCom.unmask();
					if(res == "1" || res.errorCode == Code.success){
						mini.showTips({
				            content: res.msg,
				            state: "success",
				            x: "center",
				            y: "center",
				            timeout: 2000
				        });
						successBack();
					}else{
						mini.alert(res.msg); 
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					MiniCom.unmask();
					mini.alert(jqXHR.responseText);
				}
			});
		},
		/**
		 * 请求参数使用url方式，多个用“_”分割
		 * @param miniGrid mini数据
		 * @param url 请求路径
		 * @param succsssBack 成功回调函数
		 */
		"delete":function(gridName,url,id){
			   var grid = mini.get(gridName);
			   var param = {};
			   param.url = url;
			   param.action = "delete";
			   param.confirmMsg = "确定要删除选中行吗?";
			   param.successMsg = "成功删除!";
			   param.id = id;
			   Cms.dispose(grid, param,function(){});
		},
		/**
		 * 请求参数使用url方式，多个用“_”分割
		 * @param miniGrid mini数据
		 * @param url 请求路径
		 * @param succsssBack 成功回调函数
		 */
		"del":function(miniGrid,url,succsssBack){
			   var param = {};
			   param.url = url;
			   param.action = "delete";
			   param.confirmMsg = "确定要删除选中行吗?";
			   param.successMsg = "成功删除!";
			   param.id = "id";
			   Cms.dispose(miniGrid, param, succsssBack);
		},
		/**
		 * 多行处理
		 * @param miniGrid 处理grid
		 * @param param json 参数 confirmMsg提示信息，successMsg成功提示信息，
		 * action请求方式,url 请求路径,id拼接ID
		 * @param succsssBack
		 */
		"dispose":function(miniGrid,param,succsssBack){
			 var rows = miniGrid.getSelecteds();
		   	   if(rows.length > 0){
		   	       mini.confirm(param.confirmMsg, "提示", function(e) {
						if (e == "ok") {
							 var idArray = [];
							 var id = param.id;
			                 for (var i = 0;i < rows.length; i++) {
			                     var r = rows[i];
			                     idArray.push(r[id]);
			                 }
			                var ids = idArray.join('_');
			                MiniCom.mask("操作中，请稍后......");
							$.ajax({
								url :param.url+"/"+ids,
								type:param.action,
								success : function(res) {
									MiniCom.unmask();
									if (res.errorCode ==Code.success) {								
										mini.showTips({
											content : param.successMsg,
											state : "success",
											x : "center",
											y : "center",
											timeout : 3000
										});
										miniGrid.reload();
									} else {
										mini.alert(res.message);
									}
								},
								error : function(jqXHR, textStatus, errorThrown) {
									MiniCom.unmask();
									alert(jqXHR.responseText);
								}
							});
						}
					});
		   	   }else{
		   		   mini.alert("请选中一条记录");
		   	   }
		},
		/**
		 * 删除行信息
		 * @param id
		 */
		"deleteRow":function(id){
			var grid = mini.get(id);
			var rows = grid.getSelecteds();
			grid.removeRows(rows);
		},
		/**
		 * @param miniGridObj grid对象
		 * @param param 参数对象(包括idField:删除行的字段名标识；separate:字段值之间的分隔符；url：请求路径;  参数可扩展)
		 */
		"delSelecteds" : function(miniGridObj, param) {
			Cms.batchHandle(miniGridObj, param);
		},
		/**
		 * @desc 批量处理(基于grid数据，可批量删除、启用、禁用、执行等动作)
		 * @param miniGridObj grid对象
		 * @param param 参数对象(包括idField:grid行的字段名标识；separate:字段值之间的分隔符；url：请求路径;  参数可扩展)
		 */
		"batchHandle" : function(miniGridObj, param) {
			   var rows = miniGridObj.getSelecteds();
		   	   if(rows.length > 0) {
		   	       mini.confirm("确定要执行该操作吗?", "提示", function(e) {
						if (e == "ok") {
							var idArray = [];
							for (var i = 0; i < rows.length; i++) {
								var r = rows[i];
								// id字段(按该字段删除)
								idArray.push(r[param.idField]);
							}
							// 参数值之间连接的分隔符，默认逗号
							var _separate = !param.separate ? "," : param.separate;
			                var _ids = idArray.join(_separate);
			                param.data = {ids : _ids};
			                
			                miniGridObj.loading("操作中，请稍后......");
			                Cms.ajaxSend(param, function(res) {
			                	if (res.errorCode == Code.success) {
			                		mini.showTips({
			                			content : "操作成功!",
			                			state : "success",
										x : "center",
										y : "center",
										timeout : 3000
									});
									miniGridObj.reload();
								} else {
									miniGridObj.unmask();
									mini.alert(res.message);
								}
			                });
						}
					});
		   	   } else {
		   		   mini.alert("请至少选择一条记录！");
		   	   }
		},
		/**
		 * 初始化编辑框
		 * @param miniEdit 弹出框
		 * @param miniForm 表单
		 * @param title 标题
		 * @param data 数据
		 */
		"initEdit":function(miniEdit,miniForm,title,data){
			miniEdit.setTitle(title);
			miniEdit.show();
			miniForm.clear();
			form.setData(data);
		},
		/**
		 * 对表格数据进行保存
		 * @param json 修改行的json
		 * @param url 提交url
		 * @param msg 提示消息
		 * @param successBack 回调函数
		 */
		
		"save":function(json,url,successBack){
			  var param = {};
			  param.url = url;
			  param.action = "PUT";
			  param.json = json;
			  Cms.saveData(param,successBack);
		},
		/**
		 * 
		 * @param param
		 * @param successBack
		 */
		"saveData":function(param,successBack){
			mini.confirm("确定保存?", "提示", function(e) {
				if (e == "ok") {
					MiniCom.mask("保存中。。。");
					$.ajax({
						url : param.url,
						data :param,
						type : param.action,
						success : function(res) {
							MiniCom.unmask();
							if(res.errorCode == Code.success){
								mini.showTips({
						            content: res.message,
						            state: "success",
						            x: "center",
						            y: "center",
						            timeout: 2000
						        });
								successBack();
							}else{
								mini.alert(res.message); 
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {
							MiniCom.unmask();
							alert(jqXHR.responseText);
						}
					});
				}
			});
		},
		/**
		 * 调用JSON.stringify，保存数据
		 * @param param
		 * @param successBack
		 */
		"saveDataStringify":function(param,successBack){
			mini.confirm(param.tip || "确定保存?", param.tipTitle || "提示", function(e) {
				if (e == "ok") {
					MiniCom.mask(param.ingTip || "保存中。。。");
					$.ajax({
						headers: { 
							'Accept': 'application/json',
							'Content-Type': 'application/json' 
						},
						url : param.url,
						data :JSON.stringify(param.data),
						type : param.action || 'GET',
					}).done(
						function(res) {
							MiniCom.unmask();
							if(res.errorCode == Code.success){
								mini.showTips({
									content: res.message,
									state: "success",
									x: "center",
									y: "center",
									timeout: 2000
								});
								successBack && successBack();
							}else{
								mini.alert(res.message); 
							}
					}).fail(
							function(jqXHR, textStatus, errorThrown) {
								MiniCom.unmask();
								alert(jqXHR.responseText);
							}
					);
				}
			});
		},
		/**
		 * 数据重新加载
		 */
		"reload":function(gridName){
			mini.get(gridName).reload();
		},
		/**
		 * 添加行并进行编辑
		 */
		"addEditRow":function(gridName){
			var grid = mini.get(gridName);
			var row = {};
			grid.addRow(row, 0);
			grid.beginEditRow(row);
		},
		/**
		 * 编辑选择行
		 */
		"editRow":function(gridName,before){
			before && typeof(before) == 'function' && before();
			var grid = mini.get(gridName);
			var row = grid.getSelected();
			if(row){
				grid.beginEditRow(row);
			}else{
				mini.alert("请选中一条记录");
			}
			
		},
		/**
		 * 取消存在行编辑
		 */
		"cancelEditRow":function(gridName,uid){
			var grid = mini.get(gridName);
			var row = grid.getRowByUID(uid);
			grid.cancelEditRow (row);
		},
		/**
		 * 删除新添加行
		 */
		"delEditRow":function(gridName,uid){
			var grid = mini.get(gridName);
			var row = grid.getRowByUID(uid);
			grid.removeRow (row);
		},
		"moveUp":function(gridName){
			var grid = mini.get(gridName);
			grid.moveUp(grid.getSelecteds());
			/*var grid = mini.get(gridName);
			var row = grid.getRowByUID(uid);
			var array = new Array();
            array[0] = row;
            grid.moveUp(array);*/
		},
		"moveDown":function(gridName){
			var grid = mini.get(gridName);
			grid.moveDown(grid.getSelecteds());
			/*var grid = mini.get(gridName);
			var row = grid.getRowByUID(uid);
			var array = new Array();
            array[0] = row;
            grid.moveDown(array);*/
		},
		"onCloseClick":function(e){
			var obj = e.sender;
			obj.setText("");
			obj.setValue("");
		},
		/**
		 * 验证表单数据
		 * @param name 表单名
		 * @param failMsg 提示信息
		 * @param successBack 回调函数
		 */
		"formValidate":function(name,failMsg,successBack){
			var miniFrom = new mini.Form(name);
	        miniFrom.validate();
			if (!miniFrom.isValid()){
	        mini.alert(failMsg || '表单数据有误');
			    return;
			}
			successBack && successBack();
		},
		"rowFormValidate":function(name, successBack) {
			var miniForm = new mini.Form(name);
			miniForm.validate();
	        if (!miniForm.isValid()){
	        	mini.alert(miniForm.getErrorTexts()[0]);
	        	return;
	        }
	        successBack && successBack();
		},
		/**
		 *  上传文件
		 * @param miniWin 锁定窗体
		 * @param id input
		 * @param url 提交url
		 * @param successBack 回调函数
		 */
		"upload":function(miniWin,id,url,successBack){
			 	var inputFile = $(id)[0];
				if(inputFile == null){
					mini.alert("请选择图片");
				}
			    miniWin.loading("图片上传中。。。。。。。。。");
		        $.ajaxFileUpload({
		            url: url,                 //用于文件上传的服务器端请求地址
		            fileElementId: inputFile,           //文件上传域的ID
		            //data: { a: 1, b: true },          //附加的额外参
		            dataType: 'json',
		            type :"post",
		            success: function (data, status)    //服务器成功响应处理函数
		            {
		            	miniWin.unmask();
		            	if(data.errorCode == Code.success){
		            		mini.showTips({
					            content: data.message,
					            state: "success",
					            x: "center",
					            y: "center",
					            timeout: 2000
					        });
			            	successBack(data.data);
		            	}else{
		            		mini.alert(data.message);
		            	}
		            },
		            error: function (data, status,e)   //服务器响应失败处理函数
		            {
		            	miniWin.unmask();
		                mini.alert(e);
		                
		            },
		            complete: function () {
		                var jq = $(id);
		                jq.before(inputFile);
		                jq.remove();
		            }
		        });
		},
		/**
		 *  上传文件
		 * @param miniWin 锁定窗体
		 * @param id input
		 * @param url 提交url
		 * @param successBack 回调函数
		 */
		"uploadData":function(miniWin,miniFrom,id,successBack){
			 	var inputFile = $(id)[0];
				if(inputFile == null){
					mini.alert("请选择文件");
				}
				miniFrom.validate();
			    if (miniFrom.isValid() == false){
		             mini.alert("表单数据有误");
			    	 return;
			    }
			    var data = miniFrom.getData(true, false);
			    miniWin.loading("上传中...");
		        $.ajaxFileUpload({
		            url: data.url,                 //用于文件上传的服务器端请求地址
		            fileElementId: inputFile,           //文件上传域的ID
		            data: data,          //附加的额外参
		            dataType:'json',
		            type: "post",
		            success: function (data, status)    //服务器成功响应处理函数
		            {
		            	miniWin.unmask();
		            	if(data.errorCode == undefined){
		            		data = eval(data);
						}
		            	if(data.errorCode == Code.success){
		            		mini.showTips({
					            content: data.message,
					            state: "success",
					            x: "center",
					            y: "center",
					            timeout: 2000
					        });
			            	successBack();
		            	}else{
		            		mini.alert(data.message);
		            	}
		            },
		            error: function (data, status,e)   //服务器响应失败处理函数
		            {
		            	miniWin.unmask();
						mini.alert(data.responseText);
		            },
		            complete: function () {
		                var jq = $(id);
		                jq.before(inputFile);
		                jq.remove();
		            }
		        });
		},
		/**
		 * 
		 * @param para
		 * @returns {String}
		 */
		"jsonParamStr":function(para){
			var str = "";
			for(var p in para){
				if(!para[p] && para[p]!=0){
					continue;
				}
				if(str!=""){
					str+="&";
				}
				str+=p+"="+para[p];
			}
			return str;
		},
		"ajax":function(param,successBack){
			MiniCom.mask("加载中。。。");
			$.ajax({
				url : param.url,
				data : param.data,
				type : param.action,
				success : function(res) {
					if((typeof res=='string')&&res.constructor==String)
						try {
							res = mini.decode(res);
						} catch (e) {
						}						
					if(res.errorCode == Code.success){
						successBack(res.data);
					}else{
						mini.alert(res.message); 
					}
					MiniCom.unmask();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					MiniCom.unmask();
					mini.alert(jqXHR.responseText);
				}
			});
		},
		"ajaxResultJsonStr":function(param,successBack){
			$.ajax({
				url : param.url,
				data : param.data,
				type : param.action,
				success : function(result) {
					var res ;
					try {
						res = eval('(' + result + ')');
					} catch (e) {
						mini.alert(result.message); 
						return;
					}
					if(res.errorCode == Code.success){
						successBack(res.data);
					}else{
						mini.alert(res.message); 
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					mini.alert(jqXHR.responseText);
				}
			});
		},
		"ajaxFirm":function(firmMsg,param,successBack){
			mini.confirm(firmMsg, "提示", function(e) {
				if (e == "ok") {
					Cms.ajax(param, successBack);
				}
			});
		},
		/**
		 * @param param 参数对象（可扩展）
		 * @param sucCallback 请求成功回调
		 * @param errCallback 请求失败回调
		 * @desc ajax请求数据
		 */
		"ajaxSend" : function(param, sucCallback, errCallback) {
			$.ajax({
				url : param.url,
				data : !param.data ? "" : param.data,
				type : !param.type ? "get" : param.type,
				async : param.async,
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success : function(res) {
					sucCallback(res);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					if(!errCallback) {
						mini.alert(jqXHR.responseText);
						return;
					}
					errCallback(jqXHR, textStatus, errorThrown);
				}
			});
		},
		"setCookie":function(name,value){
			var Days = 30;
			var exp = new Date();
			exp.setTime(exp.getTime() + Days*24*60*60*1000);
			document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString() +"; path=/";;
		},
		"getCookie":function(name){
			var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
			if(arr=document.cookie.match(reg))
			return unescape(arr[2]);
			else
			return null;
		},
		"delCookie" :function (name){
			var exp = new Date();
			exp.setTime(exp.getTime() - 1000*60*60*24);
			var cval=getCookie(name);
			if(cval!=null)
			document.cookie= name + "="+cval+";expires="+exp.toGMTString() +"; path=/";
		},
		/**
		 * 根据需要提取字段
		 * @param rows  mini表格中选中的多行数据
		 * @param fields 需要的字段数组
		 */
		getSubsetObj : function(rows, fields) {
			if( Object.prototype.toString.apply(rows) !== "[object Array]"
				 || Object.prototype.toString.apply(fields) !== "[object Array]"
				) 
			return;
			var subsetObj = rows.map(function(row) {
				var obj = {};
				for(field in row) {
					if(fields.includes(field))
						obj[field] = row[field];
				}
				return obj;
			});
			return subsetObj;
		},
		/**
		 * 根据字典key 获取字典值
		 * @param dictionary
		 * @param key
		 * @returns
		 */
		"getDictionaryValue":function(dictionary,key){
			key = ""+key;
			if(dictionary){
				for ( var i = 0; i < dictionary.length; i++) {
					if(key == dictionary[i].id){
						return dictionary[i].text;
					}
				}
			}
			return "";
		},
		/**根据搜索条件重新加载datagrid
		 * @param gridId	
		 * @param formId	搜索条件表单id
		 * @param searchTimeIds 数组,各个搜索时间的id
		 */
		search : function(gridId, formId, searchTimeIds) {
			var form = new mini.Form("#"+formId);
			var data = form.getData();      //获取表单多个控件的数据
			if($.isArray(searchTimeIds)) {
				$.each(searchTimeIds, function(index, searchTimeId) {
					data[searchTimeId] = mini.get(searchTimeId).getFormValue();
				});
			}
			mini.get(gridId).load(data);
		},
		addTab : function(options) {
			var op = options || {};
		    //add tab
			var showCloseButton;
			if(op.showCloseButton === true || typeof op.showCloseButton === 'undefined') {
				showCloseButton = true;
			} else {
				showCloseButton = false;
			}
		    var tab = {
	    		title: op.title || "tabTitle", 
	    		url: op.url, 
	    		showCloseButton: showCloseButton
		    };
		    tab.onload = function(e) {
		    	options.callBack && options.callBack(e, op);
		    }
		    tab.ondestroy = function (e) {
		        //var tabs = e.sender;
		        //var iframe = tabs.getTabIFrameEl(e.tab);
		        //获取子页面返回数据
		        //var pageReturnData = iframe.contentWindow.getData ? iframe.contentWindow.getData() : "";
		        //alert(e.tab.removeAction + " : " + pageReturnData);
		        //如果禁止销毁的时候，自动active一个新tab：e.autoActive = false;
		    }
		    options.tabs.addTab(tab);
		    //active tab
		    options.tabs.activeTab(tab);
		},
		showTips:function(message,time){
			mini.showTips({
				content : message,
				state : "success",
				x : "center",
				y : "center",
				timeout : time
			});
		},
		/**
		 * 图片上传
		 * @param dir 上传图片保存目录
		 * @param success 上传成功回调（data图片信息）
		 */
		uploadWindow:function(dir,success){
			mini.open({
	            url: "operatemgr/image/upload/page?type=single&catalogue="+dir,
	            title: "上传图片",
	            width: 800, height: 600,
	            ondestroy: function (data) {
	            	if(data && data !="close"){
						Cms.showTips("上传成功",3000)
						success(data);
	            	}
	            }
		    });
		},
		/**
		 * 图片管理
		 * @param dir 上传图片目录
		 * @param success 回调函数
		 */
		imageManage:function(dir,success){
			mini.open({
	            url: "operatemgr/image?catalogue="+dir,
	            title: "图片管理",
	            width: 1150, height: 750,
	            ondestroy: function (data) {
	            	if(data && data !="close"){
	            		success(data);
	            	}
	            }
		    });
		},
		/**
		 * 进度查询(开奖，派奖)
		 * @param lotteryCode
		 * @param lotteryIssue
		 */
		drawSchedule:function(lotteryCode,lotteryIssue,handleType,callBack){
			mini.open({
	            url: "lotterymgr/draw/schedule?lotteryCode="+lotteryCode+"&lotteryIssue="+lotteryIssue+"&handleType="+handleType,
	            title: "开奖进度",
	            width: 400, height: 500,
	            ondestroy: function (data) {
	            	callBack();
	            }
		    });
		},
		/**
		 * 拼接字典显示文本（文本+id）
		 * @param result
		 * @returns {Array}
		 */
		joinDictionary:function(result){
			var rs = [];
			for(var s in result){
				if(!result[s].id && !result[s].text){
					continue;
				}
				var temp  =  {};
				temp.id = result[s].id;
				temp.text = result[s].text +"_"+ result[s].id;
				rs.push(temp);
			}
			return rs;
		}
}
/**
 * miniui公共方法提取
 */
var MiniCom = {
		/**
		 * 加载中弹层
		 * @param msg
		 */
		"mask":function(msg){
			mini.mask({
	            el: document.body,
	            cls: 'mini-mask-loading',
	            html: msg
	       });
		},
		/**
		 * 取消弹层
		 */
		"unmask":function(){
			mini.unmask(document.body);
		},
		/**
		 * 打开新的tab
		 * @param id 用ID表示tab 每一个id只能打开一个tab
		 * @param title 标题
		 * @param url 页面路径
		 */
		"openMainTab":function(id,title,url){
			var node = {
					menuId:id,
					menuTitle:title,
					menuUrl:url,
			}
			window.parent.showTab(node);
		},
		/**
		 * 加载表格数据前判断是否发生异常
		 * @author jiangwei
		 * @param e 
		 */
		"onpreload":function(e){
			var result = e.result;
			if(result.errorCode){
				if(result.errorCode !=Code.success){
					mini.alert(result.message);
				}
			}
		},
		/**
		 * @param formId form的id
		 * @returns 验证form表单是否有效
		 */
	    "isValidForm" : function(formId) {
	    	formId = !formId ? "searchForm" : formId;
	    	var _form = new mini.Form("#" + formId);
	    	_form.validate(); // 表单验证
	    	return _form.isValid();
	     },
	     "queryTime" : function(type, id) {
				var current = mini.get(id);
				var time = "23:59:59";
				if ("start" == type) {
					time = "00:00:00"
				}
				if (!current.getFormValue()) {
					var date = new Date();
					var seperator1 = "-";
					var month = date.getMonth() + 1;
					var strDate = date.getDate();
					if (month >= 1 && month <= 9) {
						month = "0" + month;
					}
					if (strDate >= 0 && strDate <= 9) {
						strDate = "0" + strDate;
					}
					time = date.getFullYear() + seperator1 + month + seperator1
							+ strDate + " " + time;
				} else {
					time = current.getFormValue();
				}
				current.setValue(time);
	}
}
/**
 * 公共的请求
 */
var ComReq = {
	/**
	 * 根据类型查询
	 * 
	 * @param category
	 */
	"lottery" : function(category,callBack) {
		$.get("lotterymgr/type/dictionary?code=" + category, function(result) {
			callBack(result);
		});
	},
	/**
	 * 根据彩种code查询对应子玩法
	 * @param lotteryCode 彩种code
	 */
	"lotteryChild" : function(lotteryCode, callBack) {
		$.get("lotterymgr/type/child/dictionary?lotteryCode=" + lotteryCode, function(result) {
			callBack(result);
		});
	},
	/**
	 * 根据彩种code查询对应子玩法
	 * @param lotteryCode 彩种code
	 */
	"lotteryCheckChild" : function(lotteryCodeStr, callBack) {
		$.get("lotterymgr/type/child/dic?lotteryCodeStr=" + lotteryCodeStr, function(result) {
			callBack(result);
		});
	},	
	/**
	 * 根据彩种code查询对应中奖级别
	 * @param lotteryCode 彩种code
	 */
	"lotteryWinning" : function(lotteryCode, callBack) {
		$.get("lotterymgr/type/wingrade/dictionary?lotteryCode=" + lotteryCode, function(result) {
			callBack(result);
		});
	},
	/**
	 * 字典查询
	 * @param code
	 * @param callBack
	 */
	"dictionary":function(code,callBack){
		$.get("sysmgr/dic/dictionary?code="+code, function(result){
			callBack(result);
		});
	},
	/**
	 * 查询期号
	 * @param code 彩种（查询最近20期）
	 * @param callBack
	 */
	"issue":function(code,callBack){
		$.get("lotterymgr/issue/dictionary/issuecode?qryCount=20&lotteryCode="+code, function(result){
			callBack(result);
		});
	},
	/**
	 * 查询当前期
	 * @param code
	 * @param callBack
	 */
	"findissue":function(code,callBack){
		$.get("lotterymgr/issue/dictionary/findIssue?&lotteryCode="+code+"&currentIssue=1", function(result){
			callBack(result);
		});
	}
	,
	/**
	 * 查询截止期
	 * @param code 彩种（查询最近20期）
	 * @param callBack
	 */
	"cutOffIssue":function(code,callBack){
		$.get("lotterymgr/issue/dictionary/issuecode?type=1&qryCount=20&lotteryCode="+code, function(result){
			callBack(result);
		});
	},
	/**
	 * 查询当前期+历史期
	 * @param code 彩种（查询最近30期）
	 * @param callBack
	 */
	"cutOffHistoryIssue":function(code,callBack){
		$.get("lotterymgr/issue/dictionary/cutOffHistoryIssue?qryCount=30&lotteryCode="+code, function(result){
			callBack(result);
		});
	},
	/**
	 * 渠道查询
	 * @param callBack
	 */
	"channel":function(callBack){
		$.get("customermgr/user/channel", function(result){
			callBack(result);
		});
	},
	/**
	 * 获取出票渠道ID和名称字典
	 * @param callBack
	 */
	"ticketChannel":function(callBack){
		$.get("ticketmgr/ticketchannel/drawer/idname/dictionary", function(result){
			callBack(result);
		});
	},
	/**
	 * 获取出票渠道ID和名称字典
	 * @param callBack
	 */
	"msgTemplate":function(code,callBack){
		$.get("operatemgr/msginfo/dic?code="+code, function(result){
			callBack(result);
		});
	},
	/**
	 * 获取出票渠道ID和名称字典
	 * @param callBack
	 */
	"imageDetail":function(url,callBack){
		$.get("operatemgr/image/detail?dir="+url, function(result){
			if(result.errorCode == Code.success){
				var data = result.data;
				callBack(data);
			}else{
				mini.alert(result.message); 
			}
		});
	},
	"marketChannel" : function(code, callBack){
		$.get("operatemgr/marketchannel/dic?code="+code, function(result){
			callBack(result);
		});		
	},
	"channelTree" : function(channelId, callBack){
		$.get("operatemgr/marketchannel/tree?channelId="+channelId, function(result){
			callBack(result);
		});			
	}
	
};

var sport={
	"setLetNum":function(e){
		var grid = e.sender;
		var record = e.record;
		var letNum = record.letNum;
		if(letNum>0){
			letNum = "+"+letNum;
		}
		return letNum;
	}
};

var Task={
	"runTask":function(options,callBack) {
		var op = options || {};
		$.get(op.url || "taskmgr/job/runTask", op.data, function(res) {
			if(res.errorCode == Code.success){
				if(op.done) 
					op.done(res);
				 else {
					 if(callBack){
						 callBack(res.data);
					 }else{
						 mini.showTips({
					            content: res.message,
					            state: "success",
					            x: "center",
					            y: "center",
					            timeout: 2000
					     });  
					 }
				 }
			}else{
				if(op.fail) 
					op.fail(res);
				 else {
					 mini.alert(res.message); 
					 return;
				 }
			}
		});
	}
}
//字符串处理类
var StringUtils={
	"isBank":function(e){
		if(e == null|| e.length==0){
			return true;
		}
		return false;
	},
	/**
	 * @param str 字符串
	 * @returns {Boolean} 字符串为空或长度为0
	 */
	isEmpty : function(str) {
		return !str || str.length == 0;
	},
};
/**
 * 数字操作工具
 */
var numUtils = {
	/**
	 * @param str 字符串
	 * @returns {Boolean} 字符串是否为数字
	 */
	isDigits : function(str) {
		if(StringUtils.isEmpty(str)) {
			return false;
		}
		return !isNaN(str);
	},
	/**
	 * @desc 格式化金额数值（将数字转换成逗号分隔的样式,保留指定小数位）
	 * @param val 金额值
	 * @param n 小数位数
	 * @returns 格式化金额数值
	 */
	fmoney : function(val, n) {
		// 非数字，直接返回
		if(!numUtils.isDigits(val)) {
			return val;
		}
		var n = n > 0 && n <= 20 ? n : 2;
		val = parseFloat((val + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
		var l = val.split(".")[0].split("").reverse();
		var r = val.split(".")[1];
		var t = "";
		for (var i = 0; i < l.length; i++) {
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
		}
		return t.split("").reverse().join("") + "." + r;
	},
	/**
	 * @desc 还原金额值(将格式化的金额数值转成数字)
	 * @param val 金额值
	 * @returns 还原金额值
	 */
	rmoney : function(val) {
		return parseFloat(val.replace(/[^\d\.-]/g, ""));
	}
}

var TreeGridUtils = {
	/**
	 * @desc 获取所有兄弟结点
	 * @returns Array 默认返回选中节点的所有兄弟结点
	 */
	siblings : function(treeGridId, node) {
		var treeGrid = mini.get(treeGridId);
		var node = node || treeGrid.getSelectedNode();
		var parentNode = treeGrid.getParentNode (node);
		// getAllChildNodes ( node ) 获得所有子节点集合。
		var childNodes = treeGrid.getChildNodes(parentNode);
		return childNodes;
		
	},
	/**
	 * @desc 上一个兄弟结点
	 * @returns 
	 */
	preSibling : function(treeGridId, node) {
		// 如果node是第一个元素则无上一个兄弟结点
		var node = node || mini.get(treeGridId).getSelectedNode();
		var siblings = TreeGridUtils.siblings(treeGridId, node);
		var preIndex;
		$.each(siblings, function(index, sibling) {
			if(sibling._id === node._id) {
				preIndex = index-1;
				return;
			}
		});
		return siblings[preIndex];
	},
	/**
	 * @desc 下一个兄弟结点
	 * @returns 
	 */
	nextSibling : function(treeGridId, node) {
		// 如果node是最后一个元素则无下一个兄弟结点
		var node = node || mini.get(treeGridId).getSelectedNode();
		var siblings = TreeGridUtils.siblings(treeGridId, node);
		var nextIndex;
		$.each(siblings, function(index, sibling) {
			if(sibling._id === node._id) {
				nextIndex = index+1;
				return;
			}
		});
		return siblings[nextIndex];
	},
	/**
	 * @desc 同级内上移结点
	 * @returns 
	 */
	up : function(treeGridId, node) {
		var treeGrid = mini.get(treeGridId);
    	var node = node || treeGrid.getSelectedNode();
    	var preSibling = TreeGridUtils.preSibling(treeGridId, node);
    	treeGrid.moveNode(node, preSibling, "before");
	},
	/**
	 * @desc 同级内下移结点
	 * @returns 
	 */
	down : function(treeGridId, node) {
		var treeGrid = mini.get(treeGridId);
    	var node = node || treeGrid.getSelectedNode();
    	var nextSibling = TreeGridUtils.nextSibling(treeGridId, node);
    	treeGrid.moveNode(node, nextSibling, "after");
	}
}

/**
 * 分页对象
 * 图片管理页面有使用（image.js）
 */
var Page = function(pageSize) {
	var obj = new Object();
	obj.pageIndex = 0;
	obj.pageSize = pageSize;
	obj.totalPage = 0;
	obj.total  =0;

	obj.up = function(search) {
		if(this.pageIndex >= 1){
			this.pageIndex = this.pageIndex -1;
			search();
		}
	}
	obj.next = function(search) {
		if(this.pageIndex < this.totalPage -1){
			this.pageIndex = this.pageIndex +1;
			search();
		}
	}
	obj.skip = function(skip,search) {
		skip = parseInt(skip) - 1;
		if(skip >= 0 && skip < this.totalPage){
			this.pageIndex = skip;
			search();
		}
	}
	obj.setTotal = function(total){
		this.total = total;
		this.totalPage  = Math.ceil(total / this.pageSize);
	}
	obj.showPageNum = function(){
		return (this.pageIndex + 1)  +"/" + (this.totalPage ==0 ? 1 : this.totalPage)
	}
	return obj;
}
/**
 * 添加格式化时间
 * 使用 new Date().format("yyyy-MM-dd hh:mm:ss")
 */
Date.prototype.format = function(fmt) { 
    var o = { 
       "M+" : this.getMonth()+1,                 //月份 
       "d+" : this.getDate(),                    //日 
       "h+" : this.getHours(),                   //小时 
       "m+" : this.getMinutes(),                 //分 
       "s+" : this.getSeconds(),                 //秒 
       "q+" : Math.floor((this.getMonth()+3)/3), //季度 
       "S"  : this.getMilliseconds()             //毫秒 
   }; 
   if(/(y+)/.test(fmt)) {
           fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
   }
    for(var k in o) {
       if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
   return fmt; 
}

/***
 * @param args 参数列表
 * @returns {String} 字符串格式化
 */
String.prototype.format = function(args) {
	var result = this;
	if (arguments.length > 0) {
		if (arguments.length == 1 && typeof (args) == "object") {
			for (var key in args) {
				if (args[key] != undefined) {
					var reg = new RegExp("({" + key + "})", "g");
					result = result.replace(reg, args[key]);
				}
			}
		} else {
			for (var i = 0; i < arguments.length; i++) {
				if (arguments[i] != undefined) {
					var reg = new RegExp("({)" + i + "(})", "g");
					result = result.replace(reg, arguments[i]);
				}
			}
		}
	}
	return result.toString();
}

