var bp_funnel = {
	init : function() {
		mini.parse();
		bp_funnel.datagrid = mini.get('datagrid');
        bp_funnel.datagrid.ajaxMethod="post";
		bp_funnel.detailWindow = mini.get('detailWindow');
		bp_funnel.datagrid.load();
		$.ajax({
			url : 'bp/point/dic',
			async : false
		}).done(function(data){
			Dic.points = data;
			mini.get('bpCode1').setData(Dic.points);
			mini.get('bpCode2').setData(Dic.points);
		});
		$.ajax({
			url : "operatemgr/software/version/name/distinct",
			async : false
		}).done(function(data){
			Dic.versions = [];
			data.forEach(function(version) {
				Dic.versions.push({id:version.code, text:version.name});
			});
			mini.get('versionsId').setData(Dic.versions);
		});
		ComReq.dictionary("1515", function(result) {
            mini.get("type").setData(result["1515"]);
            Dic.type = result["1515"];
		});
		bp_funnel.numbers = ['零','一','二','三','四','五','六','七','八'];
	},
	toFunnelAnalysis : function(e) {
		MiniCom.openMainTab(null, '漏斗分析', 'bp/funnel/analysis');
	},
	toAdd : function() {
		new mini.Form('detailWindow').clear();
		bp_funnel.clearStep();
		var w = bp_funnel.detailWindow;
		w.setTitle('创建漏斗');
		w.show();
	},
	toEdit : function() {
		var rows = bp_funnel.datagrid.getSelecteds();
		if(rows.length !== 1) {
			mini.alert('请只选择一行数据');
			return;
		}
		var f = new mini.Form('detailWindow');
		f.clear();
		f.setData(rows[0]);
		bp_funnel.clearStep();
		// 显示所有'漏斗步骤'
		var bpCodes = rows[0].bpCode.split(',');
		mini.get('bpCode1').setValue(bpCodes[0]);
		mini.get('bpCode2').setValue(bpCodes[1]);
		if(bpCodes.length>2) {
			for(var i=2,l=bpCodes.length; i<l; i++) {
				bp_funnel.addStep().setValue(bpCodes[i]);
			}
		}
		var w = bp_funnel.detailWindow;
		w.setTitle('编辑漏斗');
		w.show();
	},
	clearStep : function() {
		$('#detailWindow .funnel-step').slice(2).remove();
	},
	toDelete : function(e) {
		var rows = bp_funnel.datagrid.getSelecteds();
		if(rows.length>0) {
		    mini.confirm("确定删除？", "删除漏斗", function(e) {
		    	var ids = rows.map(function(row) {
		    		return row.id;
		    	});
		    	if(e=="ok"){
					$.ajax({
						headers: { 
							'Accept': 'application/json',
							'Content-Type': 'application/json' 
						},
						url : 'bp/funnel',
						data : JSON.stringify({ids:ids}),
						type : 'DELETE',
					}).done(
						function(res) {
							if(res.errorCode == Code.success){
								bp_funnel.datagrid.reload();
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
		} else {
			mini.alert('请至少选择一行数据');
		}
	},
	merge : function(e) {
		if(!MiniCom.isValidForm('detailWindow')) {
			mini.alert('表单数据未填写完整');
			return;
		}
		var param = new mini.Form("detailWindow").getData();
		var bpCode = [];
		for(key in param) {
			if(key.indexOf('bpCode')>-1)
				bpCode.push(param[key]);
		}
		param.bpCode = bpCode.join(',');
		param.versionsId = mini.get('versionsId').value;
		$.ajax({
			url : 'bp/funnel',
			data : param,
			type : 'PUT',
		}).done(
			function(res) {
				if(res.errorCode == Code.success){
					bp_funnel.datagrid.reload();
					mini.showTips({
						content: res.message,
						state: "success",
						x: "center",
						y: "center",
						timeout: 2000
					});
					setTimeout(function(){
						bp_funnel.detailWindow.hide();
					},1500);
				}else{
					mini.alert(res.message); 
				}
		})
	},
	search : function() {
		var data = new mini.Form("form1").getData();
		bp_funnel.datagrid.load(data);
	},
	addStep : function() {
		var $steps = $('div.funnel-step');
		n = $steps.length+1,
		myBpCode = 'bpCode'+n;
		if(n>8) {
			mini.alert('最多8步');
			return;
		}
		$step = $('<div>',{class:'funnel-step'})
			.append($('<span>').text('步骤'+bp_funnel.numbers[n]+'：'))
			.append(
					$('<input>',{
						name:myBpCode, id:myBpCode, class:'mini-combobox',emptyText:'请选择',
						valueFromSelect:true,oncloseclick:'Cms.onCloseClick',
						 style:"width:500px",allowInput:"true"
						})
					)
			.append($('<span>',{class:'closeStep'}).text('x'));
		$('#addStepBtn').before($step);
		mini.parse();
		var bpCodeDom = mini.get(myBpCode);
		bpCodeDom.setData(Dic.points);
		return bpCodeDom;
	},
	cancel : function(windowId) {
		mini.get(windowId).hide();
	},
}
bp_funnel.init();
$(function() {
	// 动态绑定关闭漏斗步骤事件
	$('.closeStep').live('click', function() {
		$(this).parent().remove();
	})
});