/**
 * Created by lgs on 2016/11/15.
 * 用户管理
 */
hhly_user={
    _init:function(){
        mini.parse();
        hhly_user.status=mini.get("status");
        var onOff;
        $.get("sysmgr/dic/dictionary?code=0206", function(result){
            onOff = result['0206'];
            hhly_user.status.setData(onOff);
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
            userStatus:mini.get("status").getValue(),
        };
        data[mini.get("options").getValue()]=mini.get("value").getValue();
        this._grid(data);
    },
    /**
     *单击表格执行的函数
     * @private
     */
    _onclick:function(){
    	var userNameInput = mini.get("userName");
    	userNameInput.set({enabled : false});
        var datagrid=mini.get("datagrid");
        var row = datagrid.getSelected();
        var roleId=row.cmsRoleId;
        var userId=row.userId;
        roleId = roleId == undefined ? 0 : roleId;
        var form = new mini.Form("#userForm");
        form.setData(row);
        $("#img").attr("src",row.headUrl ? row.headUrl : "");
        mini.get('headUrl_edit').setValue("");
        var data = {
            roleId:roleId,
            userId:userId
        }
        hhly_user._treegrid(data);
    },
    /**
     * 加载角色所管理的树
     * @param data
     * @private
     */
    _treegrid:function(data){
        var _treegrid=mini.get("treegrid");
        _treegrid.load(data);
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
                var clickFn = 'hhly_user._checkFunc(\'' + id + '\',\'' + fn.button + '\', this.checked)';
                var checked = fn.checked==='true' ? 'checked' : '';
                html += '<input  al=\'checkNode' + id + '\' onclick="' + clickFn + '" ' + checked + ' type="checkbox" name="'
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
        			hhly_user._checkFunc(menuId+"",name,true);
        		}else{
        			hhly_user._checkFunc(menuId+"",name,false);
        			obj[i].checked = '';
        		}
        		
        	}      	
    	}
    	
    },
    url:"sysmgr/user",
    _checkboxSelectChange:function(){
        var cb = mini.get("cmsRoleId");
        var roleId = cb.getValue();
        var userId = mini.get("userId").getValue();
        var data = {
            roleId:roleId,
            userId:userId
        }
        hhly_user._treegrid(data);
    },
    _add:function(){
    	var userNameInput = mini.get("userName");
    	userNameInput.set({enabled : true});
        var form = new mini.Form("#userForm");
        form.clear();
        hhly_user._treegrid();
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
                        idArray.push(r.userId);
                    }
                    var ids = idArray.join('_');
                    datagrid.loading("操作中，请稍后......");
                    $.ajax({
                        url :hhly_user.url+"/"+ids,
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
    /**
     * 保存数据
     * @private
     */
    _save:function(){
        var treegrid = mini.get("treegrid");
        var treedata = treegrid.getData();
        var treeJson = mini.encode(treedata);
        var form = new mini.Form("#userForm");
        var formdata = mini.encode(form.getData());
        form.validate();
        if (form.isValid() == false){
            mini.alert("表单数据有误");
            return;
        }
        var data = {
            treeJson:treeJson,
            form:formdata
        };

        var msgid = mini.loading("数据保存中，请稍后......", "保存数据");
        $.ajax({
            url : hhly_user.url,
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
                    hhly_user._treegrid();
                    hhly_user._grid();
                }
                mini.hideMessageBox(msgid);
            },
            error : function(jqXHR, textStatus, errorThrown) {
                mini.hideMessageBox(msgid);
                alert(jqXHR.responseText);
            }
        });
    },
	/*ajaxFileUpload:function(){
		Cms.uploadWindow("_upload_images/user",function(data){
			mini.get("headUrl_edit").setValue(data.url);
            $("#img").attr("src",data.url);
		});
     },*/
    openImage:function(fileInputId){
   	 var lotterytypeCatalogue = '_upload_images/user';
		 Cms.imageManage(lotterytypeCatalogue,function(data){
			 hhly_user.setImage(data);
		   });
	},
	setImage:function(data){
		$("#img").attr("src",data.url);
		mini.get("headUrl_edit").setValue(data.dir);
	}
};
hhly_user._init();
