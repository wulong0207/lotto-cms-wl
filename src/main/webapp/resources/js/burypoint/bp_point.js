var bp_point = {
	init : function() {
		mini.parse();
		bp_point.datagrid = mini.get('datagrid');
		bp_point.datagrid.load();
	},
	updateDic : function() {
        $.ajax({
            url : 'bp/point/pointpage/dic',
            async : false
        }).done(
            function(data) {
			mini.get('pageId').setData(data);
		}).fail(function (jqx) {
			console.error(jqx);
        });

        $.ajax({
            url : 'bp/point/mode/dic',
            async : false
        }).done(
            function(data) {
			mini.get('modeId').setData(data);
		}).fail(function (jqx) {
			console.error(jqx);
        });

        $.ajax({
            url : 'bp/point/button/dic',
            async : false
        }).done(
            function(data) {
			mini.get('buttonId').setData(data);
		}).fail(function (jqx) {
			console.error(jqx);
        });


	},
	toDelete : function(e) {
		var rows = bp_point.datagrid.getSelecteds();
		if(rows.length>0) {
		    mini.confirm("删除埋点后将无法再读取该埋点的数据，确认删除此<font color='red'>"+rows.length+"</font>个埋点！", "删除埋点", function(e) {
		    	var ids = rows.map(function(row) {
		    		return row.id;
		    	});
		    	if(e=="ok"){
					$.ajax({
						headers: { 
							'Accept': 'application/json',
							'Content-Type': 'application/json' 
						},
						url : 'bp/point',
						data : JSON.stringify({ids:ids}),
						type : 'DELETE',
					}).done(
						function(res) {
							if(res.errorCode == Code.success){
								bp_point.datagrid.reload();
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
	toAdd : function() {
		MiniCom.openMainTab(null, '添加埋点', 'bp/point/add');
	},
	search : function() {
		var codeOrNameLike = mini.get('codeOrNameLike').getValue();
		bp_point.datagrid.load({codeOrNameLike : codeOrNameLike});
	},
}
bp_point.init();