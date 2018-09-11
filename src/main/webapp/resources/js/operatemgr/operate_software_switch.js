var operate_software_switch = {
	init : function() {
		mini.parse();
		ComReq.dictionary("0702", function(result) {
			Dic.terminalPlatform = result["0702"];
			Dic.lotteryStatus=[{id:0,text:'已关闭'},{id:1,text:'已开启'}];
		});
		mini.get('channelTreegrid').load('operatemgr/software/switch/list');
	},
	renderBtn:function(e){
		// 是叶子结点才有版本包信息
    	if(e.isLeaf) {
    		var html = '<btn:operate privilege="UPD">'+
    		'<a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_software_switch.toMarketChannel()">操作配置</a>'+
            '</btn:operate>'
            return html;
    	}
    },
    renderUploadBtn:function(e){
    	// 是叶子结点才可能上传文件
    	if(e.isLeaf) {
    		var html = '<btn:operate privilege="UPLOAD">'+
        	'<a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_software_switch.toUpload()">上传</a>'+
        	'</btn:operate>'
        	return html;
    	}
    },
    toUpload : function(e) {
    	new mini.Form('uploadWin').clear();
    	$("#appfile").val("");
    	$("#progress").html("");
    	mini.get('uploadWin').show();
    },
	"upload":function() {
		var url = "operatemgr/software/switch/upload";
		$("#progress").html("");
        $("#fileAddress").val("");
        var grid = mini.get('channelTreegrid');
        var channelId = grid.getSelected().channelId;
		$.ajaxFileUpload({  
            url:url,  
            secureuri:false,  
            fileElementId:'appfile',//file标签的id  
            dataType: 'json',//返回数据的类型  
            data:{channelId:channelId},//一同上传的数据  
            success: function (obj, status) { 
                //把图片替换  
                if(typeof(obj.errorCode) != 'undefined') {  
                    if(obj.errorCode == Code.success) {  
                        $("#progress").html("上传文件成功,文件大小："+obj.byte+" bytes");
                        $("#fileAddress").val(obj.fileAddress);
                        grid.reload();
                    } else {  
                        $("#progress").html(obj.message);  
                    }  
                }  
            },  
            error: function (data, status, e) {  
                alert(e);  
            }  
        });  
	},
    renderSyn:function(e){
    	var synOffical = e.row.synOfficial;
    	var text = synOffical === 0 ? '开启同步' : '关闭同步';
    	var newSyn = synOffical === 0 ? 1 : 0;
    	var html = '<btn:operate privilege="UPD">'+
    	'<a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_software_switch.visitSynOfficial('+newSyn+')">'+text+'</a>'+
    	'</btn:operate>';
    	return html;
    },
	renderOpt:function(e){
		var lotteryStauts = e.row.lotteryStatus;
		var text = lotteryStauts === 0 ? '开启购彩' : '关闭购彩';
		var newStatus = lotteryStauts === 0 ? 1 : 0;
		var html = '<btn:operate privilege="UPD">'+
    	'<a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_software_switch.updLotteryStauts('+newStatus+')">'+text+'</a>'+
		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
		'<a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_software_switch.toDetail()">修改</a>'+
        '</btn:operate>'
        return html;
    },
    toDetail : function() {
    	// 根据hide字段设置checkbox
    	var row = mini.get('datagrid').getSelected();
		var hides = row.hide ? row.hide.split(',') : [];
		var $checkbox;
	    $.each($("#softwareHiddenTr input[type=checkbox]"), function(index, ele) {
	    	$checkbox = $(ele);
	    	$checkbox.attr('checked', hides.indexOf($checkbox.val()) > -1);
	    });
	    var form =  new mini.Form("SoftwareForm");
	    form.clear();
	    form.setData(row);
	    // 当所有子栏目选中时，父栏目也选中
	    $('div.secondLevel').each(function(index, ele) {
	    	operate_software_channel.secondLevelChange($(ele));
	    })
	    // 判断购彩框是否要选中
	    var buyLotteryCheckBox = $('#buyLotteryCheckBox');
	    var buyLotteryClass = buyLotteryCheckBox.data('child');
	    var allLotteryCheckBoxs = $('div.'+buyLotteryClass).find("input[type=checkbox]").get();
    	buyLotteryCheckBox.attr('checked', allLotteryCheckBoxs.every(function(ele, index) {
			return $(ele).prop('checked');
		}));
    	var w = mini.get('detailWindow');
    	w.show();
    },
    closeDetailWindow : function() {
    	var w = mini.get('detailWindow');
    	w.hide();
    },
    updDetail : function() {
    	var data =  new mini.Form("SoftwareForm").getData(true, false);
    	// 拼接hide字段
		var hide = [];
		var tmp;
		$.each($("#softwareHiddenTr input[type=checkbox]:checked"), function(index, ele) {
			tmp = $(ele).val();
			tmp && hide.push(tmp);
		});
		data.hide = hide.join(',');
		$.ajax({
			url : 'operatemgr/software/switch',
			data : data,
			type : 'PUT',
		}).done(
			function(res) {
				mini.get('datagrid').load({
					'channelId' : mini.get('datagrid').getSelected().channelId
				});
				if(res.errorCode == Code.success){
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
    visitSynOfficial : function(newStatus) {
    	var row = mini.get('datagrid').getSelected();
    	var param = {
    			id : row.id,
    			synOfficial:newStatus,
    			channelId : row.channelId
    	}
			$.ajax({
				url : 'operatemgr/software/switch/synofficial',
				data : param,
				type : 'PUT',
			}).done(
				function(res) {
					mini.get('datagrid').reload();
					if(res.errorCode == Code.success){
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
    updLotteryStauts : function(newStatus) {
    	var row = mini.get('datagrid').getSelected();
    	var param = {
    			id : row.id,
    			hide : row.hide,
    			lotteryStatus : newStatus
    	}
    	$.ajax({
    		url : 'operatemgr/software/switch/lotterystatus',
    		data : param,
    		type : 'PUT',
    	}).done(
    			function(res) {
    				mini.get('datagrid').reload();
    				if(res.errorCode == Code.success){
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
    toMarketChannel : function(e) {
    	 var tabs = window.parent.mini.get("mainTabs");
    	 var row = mini.get('channelTreegrid').getSelectedNode();
    	Cms.addTab({
	    	tabs : tabs,
	    	url : 'operatemgr/software/switch/channel',
	    	title: row.channelName+'配置管理',
	    	callBack : operate_software_switch.loadByChannel,
	    	channelId : row.channelId,
	    });
    },
    loadByChannel : function(e, op) {
    	var tabs = window.parent.mini.get("mainTabs");
    	var tab = tabs.getActiveTab(),
		contentWindow = tabs.getTabIFrameEl(tab).contentWindow;
    	var grid = contentWindow.mini.get('datagrid');
    	grid.load({
			'channelId' : op.channelId
		});
    },
}