operate_ad={
  init:function(){
    mini.parse();
    operate_ad.grid = mini.get("datagrid");
    operate_ad.platform = mini.get("platform");
    operate_ad.status = mini.get("status");
    operate_ad.publishStatus = mini.get("publishStatus");
    operate_ad.menu = mini.get("menu");
    operate_ad.position = mini.get("position");
    operate_ad.menuDetail = mini.get("menuDetail");
    operate_ad.positionDetail = mini.get("positionDetail");
    operate_ad.searchTimeType = mini.get("searchTimeType");
    operate_ad.detailWindow = mini.get("detailWindow");
    operate_ad.lotteryCode = mini.get("lotteryCode");
    operate_ad.catalogue = "_upload_images/operate/ad";

    ComReq.dictionary("1501,1503,1504,1002,1502,1505,1506,1508,1524", function(result){
 		Dic.platform = result["1501"];	
 		Dic.status = result["1002"];		
 		Dic.publishStatus = result["1502"];
 		// Dic.menu, Dic.position只用于广告列表显示，不用于下拉框选择
 		Dic.menu = result["1503"];		
 		Dic.position = result["1504"];		
 		Dic.searchTimeType = result["1505"];		
 		Dic.display = result["1506"];		
 		Dic.target = result["1508"];		
    operate_ad.platform.setData(Dic.platform);
    operate_ad.status.setData(Dic.status);
    operate_ad.publishStatus.setData(Dic.publishStatus);
    //operate_ad.menu.setData(Dic.menu);
    //operate_ad.position.setData(Dic.position);
    operate_ad.searchTimeType.setData(Dic.searchTimeType);
    mini.get("defaultAd").setData(result["1524"]);
    operate_ad.setMenu(operate_ad.menu, operate_ad.position,0);
    operate_ad.setMenu(operate_ad.menuDetail, operate_ad.positionDetail);
    ComReq.lottery("", function(result) {
		Dic.allCode = result;
		operate_ad.lotteryCode.setData(Dic.allCode);
	});
	$.ajax({
		url : "operatemgr/marketchannel/list"
	}).done(function(data) {
		// 2018-02-06，需求不要"全部渠道"
		data.unshift({'channelName':'全部渠道','channelId':0});
        Dic.channels = data;
        mini.get("channels").loadList(data);
        // 过滤出所有非叶子渠道结点，修改添加新渠道时，页面显示勾选但表实际没值
		Dic.notLeafChannelIds = Dic.channels.map(c=>c.parentChannelId);
	});
    mini.get("platformDetail").setData(Dic.platform);
    mini.get("statusDetail").setData(Dic.status);
    //mini.get("menuDetail").setData(Dic.menu);
    //mini.get("positionDetail").setData(Dic.position);
    mini.get("displayDetail").setData(Dic.display);
    mini.get("targetDetail").setData(Dic.target);
    operate_ad.searchTimeType.select(0);
    Cms.search("datagrid", "form1", ["startTime", "endTime"]);
    });
  },
  setMenu : function(menuEl, positionEl, menuIndex) {
	  $.ajax({
	    	url : "operatemgr/ad/menu/dic"
	    }).done(function(data) {
	    	menuEl.setData(data);
          	menuEl.setValue(menuIndex);
	    	// 如果至少有一个广告页面配置信息
	    	if(data && data[0]) {
	    		operate_ad.setPositionByMenu(menuEl.getValue(), positionEl);
	    	}
	    });
  },
  setPositionByMenu : function(menu, positionEl, position, callBack) {
	  $.ajax({
	    	url : "operatemgr/ad/menu/position/dic/" + menu
	    }).done(function(data) {
	    	positionEl.setData(data);
	    	// 默认选中第一个广告位置,若是编辑页面则不要默认选中第一个
	    	// 是编辑页面时会传position，遍历此页面的页面位置信息，position的在数据中的索引即为下拉框的选中位置
	    	var index;
	    	if(position) {
	    		//data是数据字典{id:xx, text: xxx}
	    		$.each(data, function(i, item) {
	    			if(position == item.id) {
	    				index = i;
	    				return
	    			}
	    		});
	    		positionEl.select(index);
	    	}
          	callBack && callBack();
	    });
  },
  handleMenuChange : function(e) {
	  operate_ad.setPositionByMenu(e.value, operate_ad.position);
  },
  handleDetailMenuChange : function(e) {
	  operate_ad.setPositionByMenu(e.value, operate_ad.positionDetail);
  },
  edit : function() {
		var row = operate_ad.grid.getSelected(),w = operate_ad.detailWindow;
		if (row) {
			w.set({
				title : "修改广告图详情"
			});
			row.action = "put";
			row.url = "operatemgr/ad";
			var form = new mini.Form("#detailForm");
			form.clear();
			operate_ad.setPositionByMenu(row.menu, operate_ad.positionDetail, row.position);
			form.setData(row);
			// 查询广告类型表，设置"广告对象","广告渠道"下拉框的值
			$.ajax({
		    	url : "operatemgr/ad/type/"+row.id
		    }).done(function(data) {
		    	// 保存原始数据，用于判断是否有变化
				operate_ad.orginalUserTypes = data[0].join(",");
				operate_ad.orginalChannels = data[1].join(",");
				// miniUI的下拉框同时设置多个值的方法是setValue("1,2,3")
		    	mini.get("userTypesDetail").setValue(operate_ad.orginalUserTypes);
		    	mini.get("channels").setValue(data[1].filter(c=>!Dic.notLeafChannelIds.contains(c)));
		    });
			// 查询图片信息
			$("#adImg").attr("src","");
			ComReq.imageDetail(row.img,function(data){
				operate_ad.setImage(data);
			});
			w.show();
		} else {
			mini.alert("请选择一行数据");
		}
  },
	add : function() {
		var form = new mini.Form("#detailForm");
		form.clear();
		form.setData({
			action : "post",
			url : "operatemgr/ad",
		});
		operate_ad.menuDetail.select(0);
        operate_ad.setPositionByMenu(operate_ad.menuDetail.getValue(), operate_ad.positionDetail,null, function () {
            operate_ad.positionDetail.select(0);
        });

		mini.get("defaultAd").select(0);
		// 把图片去掉
		$("#adImg").attr("src","");
		operate_ad.detailWindow.show();
	},
	merge : function() {
		var form = new mini.Form("#detailForm");
		form.validate();
	    if (form.isValid() == false){
             mini.alert("表单数据有误");
	    	 return;
	    }
	    var data = form.getData(true, false);
	    data.channels = tree = mini.get("channels").getValue();
	    data.adObjectChange =
	    	(data.userTypes !== operate_ad.orginalUserTypes) || (data.channels !== operate_ad.orginalChannels);
		Cms.submitByData(data, function() {
			operate_ad.detailWindow.hide();
			operate_ad.clearData();
			Cms.reload("datagrid");
		});
	},
	setImage:function(data){
			var splitUrl = data.url.split(".");
			$("#adImg").attr("src",data.url);
			mini.get("img").setValue(data.dir);
		    mini.get("fileName").setValue(data.name);
		    mini.get("fileFormat").setValue(splitUrl[splitUrl.length - 1]);
		    mini.get("fileSize").setValue(data.sizes);
		    mini.get("fileCreateTime").setValue(data.createTime);
	},
	openImage:function(){
			 Cms.imageManage(operate_ad.catalogue,function(data){
				   operate_ad.setImage(data);
			   });
	},
	rowPublishStatus:function(e){
		var grid = e.sender;
		var onlineTime=e.row.onlineTime.getTime();
        var offlineTime=e.row.offlineTime.getTime();
        var nowTime=new Date().getTime();
        
        if(nowTime<onlineTime){
        	return "待发布";
        }else if(nowTime>=onlineTime && nowTime<=offlineTime){
        	return "发布中";
        }else if(nowTime>offlineTime){
        	return "发布结束";
        }	
        	
	},
	clearData : function() {
		// 清除数据
		operate_ad.orginalUserTypes = "";
		operate_ad.orginalChannels = "";
	},
	// 检查下拉框的值，如果"广告对象"选中"全部"选项，则其他选项自动取消，若选中非"全部"选项，这本下拉框的"全部"选项取消，"广告渠道"下拉框的"全部"选项选中
    checkUserTypeValue : function(e) {
        // "广告对象"和"广告渠道"下拉框的第一个值都为"全部"选项
        var checkAllValue = e.sender.data[0].id;
        var comboBox = mini.get(e.sender.id);
        // 无论A选什么值，B拉框都是选择"全部"选项
        var anotherComboBox = mini.get("channels");
        anotherComboBox.setValue(anotherComboBox.data[0].channelId);
        // 若是点击"全部"按钮
        if(e.item.id == checkAllValue) {
            // 选中"全部"
            if((","+e.sender.value+",").indexOf(","+checkAllValue+",") != -1) {
                // setData清空所有选择，再选中第一个
                comboBox.setData(e.sender.data);
                comboBox.select(0);
            }
        }
        // 若是点击其他按钮
        else {
            // 若值包含"全部"，则从值中去除，再给下拉框赋值
            if((","+e.sender.value+",").indexOf(","+checkAllValue+",") != -1) {
                // 如把"0,1,2"截成"1,2", +2是为了把逗号也去掉，
                comboBox.setValue(e.sender.value.substring(e.sender.value.indexOf(checkAllValue)+2));
            }
        }
    },
    checkChannelValue : function (e) {
        if(operate_ad.isClickAllChannel) {
        	if((","+e.value+",").indexOf(",0,") > -1)
				// 若选中"全选"，则清除其他值
				mini.get("channels").setValue(0);
		} else {
            // 当不是点击"全部渠道"时，若值包含"全部"，则从值中去除，再给下拉框赋值
            if((","+e.value+",").indexOf(",0,") > -1) {
                // 如把"0,1,2"截成"1,2", +2是为了把逗号也去掉，
                mini.get("channels").setValue(e.value.substring(e.value.indexOf('0')+2));
            }
		}
        // 无论A选什么值，B拉框都是选择"全部"选项
        var anotherComboBox = mini.get("userTypesDetail");
        anotherComboBox.setValue(anotherComboBox.data[0].id);
    },
    isCheckAllChannel : function (e) {
		// 用变量告诉checkChannelValue方法是否点击"全部渠道"
        operate_ad.isClickAllChannel = e.node.channelId == 0;
    },
	checkAll : function (e) {
        var tree = mini.get("channels"),
            treeData = tree.getData();
        checkItAll(treeData);
        tree.setData(treeData);
        function checkItAll(nodes) {
            nodes.forEach(function (node) {
                if(node.children) {
                    checkItAll(node.children)
                } else {
                    node.checked=true;
                }
            });
        }
    },
    checkReverse : function (e) {
		var tree = mini.get("channels"),
        treeData = tree.getData();
        checkItReverse(treeData);
        tree.setData(treeData);
		function checkItReverse(nodes) {
            nodes.forEach(function (node) {
            	if(node.children) {
                    node.checked=false;
                    checkItReverse(node.children)
				} else {
                    node.checked=!node.checked;
				}
            });
        }
    },

    getChildChannelData : function (treeData, childChannelData) {
        treeData.forEach(function (channelData) {
			if(channelData.children)
                operate_ad.getChildChannelData(channelData.children, childChannelData);
			else
                childChannelData.push(channelData);
        });
    }
};
operate_ad.init();