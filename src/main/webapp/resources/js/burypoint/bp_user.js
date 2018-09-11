var bp_user = {
	init : function() {
        $.ajax({
            url : "operatemgr/marketchannel/dic",
            async : false
        }).done(function(data){
            Dic.marketchannel = data;
        }).fail(function (jqx) {
			console.error(jqx);
        });
		//mini.parse();
        bp_user.datagrid = mini.get('datagrid');
	},
}
bp_user.init();