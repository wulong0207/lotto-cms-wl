/**
 * Created by lgs on 2016/11/15.
 * 角色管理
 */

hhly_role={
    _init:function(){
        mini.parse();
        mini.get("roleStatus").setData(Data.onOff);
        hhly_role.status=mini.get("status");
        var onOff;
        $.get("sysmgr/dic/dictionary?code=0206", function(result){
            onOff = result['0206'];
            hhly_role.status.setData(onOff);
        });        
        this._grid();
        
    },
    /**
     * 加载数据
     * @param data
     * @private
     */
    _grid:function(data){
        var datagrid=mini.get("datagrid");
        datagrid.load(data);
    },
    /**
     * 按照条件查询
     * @private
     */
    _search:function(){
        var data={
            depart:mini.get("departId").getValue(),
            roleId:mini.get("id").getValue(),
            roleStatus:mini.get("status").getValue()
        }
        this._grid(data);
    },
    /**
     *
     * @private
     */
    _onclick:function(){
        var datagrid=mini.get("datagrid");
        var row = datagrid.getSelected();
        var roleId=row.roleId;
        roleId = roleId == undefined ? 0 : roleId;
        mini.get("depart").setValue(row.depart);
        mini.get("roleName").setValue(row.roleName);
        mini.get("roleStatus").setValue(row.roleStatus);
        mini.get("remark").setValue(row.remark);
        mini.get("roleId").setValue(roleId);
        var data = {
            roleId:roleId
        }
        hhly_role._treegrid(data);
    },
    /**
     * 加载角色所管理的树
     * @param data
     * @private
     */
    _treegrid:function(data){
        var _treegrid=mini.get("treegrid");
        _treegrid.load(data);
        
        $("input:checkbox:checked").each(function(){
        	$(this).attr("disabled","disabled");
        });
        
    },
    /**
     * 设置check选择button
     * @param e
     * @private
     */
    _ondrawcell:function(e) {
        var tree = e.sender,
            record = e.record,
            field = e.field,
            id = record[tree.getIdField()],
            buttons = record.buttons;
        function createCheckboxs(buttons) {
            if (!buttons) return "";
            var html = "";
            for (var i = 0, l = buttons.length; i < l; i++) {
                var fn = buttons[i];
                var clickFn = 'hhly_role._checkFunc(\'' + id + '\',\'' + fn.button + '\', this.checked)';
                var checked = fn.checked==='true' ? 'checked' : '';
                html += '<input al=\'checkNode' + id + '\' onclick="' + clickFn + '" ' + checked + ' type="checkbox" name="'
                + fn.button + '" hideFocus />' + fn.name + '';
            }
            return html;
        }
        if (field == 'buttons') {
            e.cellHtml = createCheckboxs(buttons);
        }
    },
    _checkFunc:function(id, action, checked) {
        var record = mini.get("treegrid").getRecord(id);
        if(!record) return;
        var funs = record.buttons;
        if (!funs) return;
        function getAction(action) {
            for (var i = 0, l = funs.length; i < l; i++) {
                var o = funs[i];
                if (o.button == action) return o;
            }
        }
        var obj = getAction(action);
        if (!obj) return;
        obj.checked = checked;
    },
    _onnodecheck:function(e){    	
    	setChecked(e.node._id,e.node.menuId,e.checked);
    	var children = e.node.children;   	
    	if(children!=undefined){
    		for(var i=0;i<children.length;i++){
        		var son = children[i];
        		setChecked(son._id,son.menuId,e.checked);
        	}
    		
    	} 	   	
    	function setChecked(id,menuId,checked){   		
    		var obj = $("input[al=checkNode"+menuId+"]");
        	for(var i=0;i<obj.length;i++){
        		var name = obj[i].name;
        		if(checked!=true){
        			obj[i].checked = 'checked';
        			hhly_role._checkFunc(menuId+"",name,true);
        		}else{
        			hhly_role._checkFunc(menuId+"",name,false);
        			obj[i].checked = '';
        		}
        		
        	}      	
    	}
    	
    },
    /**
     * 保存数据
     * @private
     */
    _save:function(){
        var treegrid = mini.get("treegrid");
        var treedata = treegrid.getData();
        var treeJson = mini.encode(treedata);
        var form = new mini.Form("#roleForm");
        var formdata =  mini.encode(form.getData());
        form.validate();
        if (form.isValid() == false){
            mini.alert("表单数据有误");
            return;
        }
        var data = {
            treeJson:treeJson,
            form:formdata
        }

        var msgid = mini.loading("数据保存中，请稍后......", "保存数据");
        $.ajax({
            url : hhly_role.url,
            data :data,
            type : "post",
            success : function(o) {
                if(o == '0'){
                    mini.alert("数据保存失败");
                }
                else {
                    mini.showTips({
                        content: "数据保存成功",
                        state: "success",
                        x: "center",
                        y: "center",
                        timeout: 3000
                    });
                    hhly_role._treegrid();
                    hhly_role._grid();
                }
                mini.hideMessageBox(msgid);
            },
            error : function(jqXHR, textStatus, errorThrown) {
                mini.hideMessageBox(msgid);
                alert(jqXHR.responseText);
            }
        });
    },
    url:"sysmgr/role",
    _add:function(){
        var form = new mini.Form("#roleForm");
        form.clear();
        hhly_role._treegrid();
    },
    _del:function(){
        var datagrid=mini.get("datagrid");
        var rows = datagrid.getSelecteds();
        if(rows.length > 0){
            mini.confirm("确定要删除选中行吗?", "提示", function(e) {
                if (e == "ok") {
                    var idArray = [];
                    for (var i = 0;i < rows.length; i++) {
                        var r = rows[i];
                        idArray.push(r.roleId);
                    }
                    var ids = idArray.join('_');
                    datagrid.loading("操作中，请稍后......");
                    $.ajax({
                        url :hhly_role.url+"/"+ids,
                        type:"delete",
                        success : function(o) {
                            if (o == "-1") {
                                mini.alert("删除异常!");
                            } else {
                                mini.showTips({
                                    content : "成功删除!",
                                    state : "success",
                                    x : "center",
                                    y : "center",
                                    timeout : 3000
                                });
                                datagrid.reload();
                            }
                        }
                    });
                }
            });
        }else{
            mini.alert("请选中一条记录");
        }
    },
};
hhly_role._init();


