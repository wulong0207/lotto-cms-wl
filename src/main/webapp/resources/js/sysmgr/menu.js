var delIdArr = [];
function onAddBefore(e) {
    var tree = mini.get("tree1");
    var node = tree.getSelectedNode();
    var orderId = node.orderId;
    var newNode = {
        orderId : orderId,
    };
    reSort(node.orderId);
    tree.addNode(newNode, "before", node);

}
function onAddAfter(e) {
    var tree = mini.get("tree1");
    var node = tree.getSelectedNode();
    var orderId = parseInt(node.orderId) + 1;
    var newNode = {
        orderId : orderId,
    };
    reSort(orderId);
    tree.addNode(newNode, "after", node);
}
function onAddNode(e) {
    var tree = mini.get("tree1");
    var node = tree.getSelectedNode();
    var orderId = parseInt(node.orderId) + 1;
    var newNode = {
        orderId : orderId,
    };
    reSort(orderId);
    tree.addNode(newNode, "add", node);
}
function onEditNode(e) {
    var tree = mini.get("tree1");
    var node = tree.getSelectedNode();

    tree.beginEdit(node);
}
function onRemoveNode(e) {
    var tree = mini.get("tree1");
    var node = tree.getSelectedNode();

    if (node) {
        if (confirm("确定删除选中节点?")) {
            if (node.menuId != null) {
                delIdArr.push(node.menuId);
            }
            tree.removeNode(node);
        }
    }
}
function onMoveNode(opt) {
    var tree = mini.get("tree1");
    var node = tree.getSelectedNode();
    var targetNode = null;
    tree.findNodes(function(o) {
        if (parseInt(o.orderId) == (parseInt(node.orderId) + opt))
            return targetNode = o;
    });
    if (targetNode != null) {
        var oId = node.orderId;
        node.orderId = targetNode.orderId;
        targetNode.orderId = oId;
        if (opt < 0)
            tree.moveNode(node, targetNode, "before");
        else
            tree.moveNode(targetNode, node, "before");
    }
}
function onBeforeOpen(e) {
    var menu = e.sender;
    var tree = mini.get("tree1");
}

function saveData() {
    var tree = mini.get("tree1");
    var data = tree.getData();
    var json = mini.encode(data);


    var msgid = mini.loading("数据保存中，请稍后......", "保存数据");
    $.ajax({
        url : "sysmgr/menu",
        data : {
            treeJson : json,
            idJson : mini.encode(delIdArr)
        },
        type : "post",
        success : function(text) {
            if(text != "success"){
                mini.alert("提交失败!");

            }else
                mini.showTips({
                    content: "数据保存成功",
                    state: "success",
                    x: "center",
                    y: "center",
                    timeout: 3000
                });
            mini.get("tree1").load();
            mini.hideMessageBox(msgid);
        },
        error : function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
        }
    });

}
var isLoad = false;
var selId = "";
var selNode = null;
function onDrawNode(e) {
    var tree = e.sender;
    var node = e.node;

    var isLeaf = tree.isLeaf(node);

    //所有子节点加上超链接

}
function reSort(id) {
    var tree = mini.get("tree1");

    tree.findNodes(function(node) {
        if (parseInt(node.orderId) >= parseInt(id)) {
            node.orderId = parseInt(node.orderId) + 1;

        }
    });
}
function setUrl(o) {
    var tree = mini.get("tree1");
    if (selNode != null) {
        selNode.menuUrl = o.value ;
    }
}
function setMenuButtons(o){
    if (selNode != null) {
        selNode.menuButton = o.value ;//+ "|" + o.selected["text"];
    }
}
function setMenuTitle(o) {
    if (selNode != null) {
        selNode.menuTitle = o.value ;
    }
}


function nodeSelect(e, a, b) {
    var tree = e.sender;
    selNode = e.node;
    isLoad = true;
    var isLeaf = tree.isLeaf(selNode);
    selId = selNode.id;
    mini.get("menuUrl").setValue(selNode.menuUrl);
    mini.get("menuTitle").setValue(selNode.menuTitle);
    var actStr = "";
    if(selNode.menuButton != null){
        var act =  selNode.menuButton;
        var ar = act.split(",");

        for( var i = 0;i<ar.length;i++){
            if(ar[i].indexOf("|") > -1)
                actStr += ar[i].split("|")[0] + ",";
            else
                actStr += ar[i] + ",";
        }
    }
    if(actStr != "") actStr = actStr.substr(0,actStr.length -1);
    mini.get("pAct").setValue(selNode.menuButton);
    //tree.beginEdit(node);
}

function onCloseClick(e) {
    var obj = e.sender;
    obj.setText("");
    obj.setValue("");
}
$(function(){
    var com = mini.get("pAct");
    $.get("sysmgr/dic/dictionary?code=113", function(result){
        Data._buttons = result[113];
        com.setData(Data._buttons);
    });

    //com.load(Data._buttons);
});
