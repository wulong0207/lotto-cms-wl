var Current = {
	"init" : function() {
		mini.parse();
		Current.grid = mini.get("datagrid");
		Current.accountName = mini.get("accountName");
		Current.cusMobile = mini.get("cusMobile");
		Current.agentStatus = mini.get("agentStatus");
		var statusData = [{"id":0,"text":"禁用"},{"id":1,"text":"启用"}];
		Current.agentStatus.setData(statusData);
		Current.search();
	
	},
	"search" : function() {
		Current.grid.load(Current.getParamJson());
	},
	"getParamJson" : function() {
		var accountName = Current.accountName.getValue();
		var cusMobile = Current.cusMobile.getValue();
		var agentStatus = Current.agentStatus.getValue();
		var para = {
				accountName : accountName,
				cusMobile : cusMobile,
				agentStatus : agentStatus
		};
		return para;
	},
    "onActionRenderer":function (e) {
        var grid = e.sender;
        var record = e.record;
        var uid = record.id;
        var rowIndex = e.rowIndex;
        var s = ' <a class="mini-button"  plain="true" onclick="Current.apply('+uid+')" >设置</a>&nbsp;';
        var str = "";
        //处于禁用时
        if(record.agentStatus==0){
        	str = '<a class="mini-button"  plain="true" onclick="Current.unable('+uid+',1)" >启用</a>';
        }else{
        	str = '<a class="mini-button"  plain="true" onclick="Current.unable('+uid+',0)" color="red">禁用</a>';
        }
        return s+str;
    },
    "apply":function(id){
      var  grid  = Current.grid;
	  var row = grid.getSelected();
      if (row) {
          mini.open({
              url: "agent/set?agentId="+id,
              title: "返佣设置", width: "80%", height: "80%",
              onload: function () {
                  var iframe = this.getIFrameEl();
                  var data = { action: "edit", id: row.agentId };
//                  iframe.contentWindow.SetData(data);
              },
              ondestroy: function (action) {
                  grid.reload();
              }
          });
      } else {
          alert("请选中一条记录");
      }
    },
    "unable":function(id,status){
//    	mini.loading("正在操作");
        $.ajax({
            url: "agent/updateStatus?agentId="+id+"&agentStatus="+status,
            success: function (text) {
            	Current.grid.reload(Current.getParamJson());
            },
            error: function () {
                alert("更新出错");
            }
        });
    },
    "onOptRenderer":function(e){
    	var value = e.value;
    	var s = "";
    	if(value==0){
    		s = "禁用";
    	}else{
    		s = "启用";
    	}
    	return s;
    }
}
Current.init();