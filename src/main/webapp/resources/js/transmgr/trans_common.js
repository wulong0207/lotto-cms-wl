trans_common={
  search:function(grid, formId){
	if(formId) {
		if(!MiniCom.isValidForm(formId)) {
			mini.alert('查询条件有误，请检查!');
			return;
		}
	}
  	grid.load(trans_common.getParam());
	},
	excel:function(url){
		location = url + "/excel?" + Cms.jsonParamStr(trans_common.getParam());
	},
	getParam : function() {
		var form = new mini.Form("#form1");
		var data = form.getData(true);      //获取表单多个控件的数据
		return data;
	}
};
