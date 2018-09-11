<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>菜单管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
    <script src="<%=basePath%>resources/js/sysmgr/menu.js?version=1.2" type="text/javascript"></script>
  </head>
  
  <body>
  <div class="mini-toolbar" style="border:0;padding:0px;">
      <table style="width:100%;">
          <tr>
              <td style="width:100%;">
                    <btn:operate privilege="SAVE"  >
                      <a id="add" class="mini-button"
                         iconCls="icon-save" plain="true" onclick="saveData()">保存</a>
                    </btn:operate>
              </td>
          </tr>
      </table>
  </div>

  <div class="mini-splitter" style="width:100%;height:100%;border-top:0;border-left:0;">

      <div size="340"  style="padding:0px;border-left:0;">
          <div class="mini-fit" borderStyle="border:0px;">
              <div id="panel1" borderStyle="border:0px;" class="mini-panel" title="系统菜单" style="width:100%;height:100%"
                   showFooter="true" allowResize="true">
                  <ul id="tree1" class="mini-tree" borderStyle="border:0px;" url="sysmgr/menu/list"
                      style="width:550px;padding:5px;" showTreeIcon="true"
                      textField="menuTitle" idField="menuId" parentField="parentMenuId"
                      sortIdField="orderId" urlField="menuUrl" resultAsTree="false" levelField="menuLevel"
                      contextMenu="#treeMenu" expandOnLoad="true"
                      ajaxOptions="{type : 'get'}"
                      onnodeselect="nodeSelect" >
                  </ul>
                  <ul id="treeMenu" class="mini-contextmenu"
                      onbeforeopen="onBeforeOpen">
                      <li iconCls="icon-move" onclick="onMoveNode(-1)">上移动菜单项</li>
                      <li iconCls="icon-move" onclick="onMoveNode(1)">下移动菜单项</li>
                      <li class="separator"></li>
                      <li><span iconCls="icon-add">新增菜单项</span>
                          <ul>
                              <li onclick="onAddBefore">所选之前插入菜单项</li>
                              <li onclick="onAddAfter">所选之后插入菜单项</li>
                              <li onclick="onAddNode">新增子菜单项</li>
                          </ul></li>
                      <li name="edit" iconCls="icon-edit" onclick="onEditNode">编辑节点</li>
                      <li name="remove" iconCls="icon-remove" onclick="onRemoveNode">删除节点</li>
                  </ul>
              </div>
          </div>
      </div>

      <div style="padding-top:50px;padding-left:10px;">
          <table class="form-table" border="0" cellpadding="1"
                 cellspacing="2"  style="border:1px solid red;margin:auto;" >
              <tr>
                  <td class="form-label" style="width:80px;">页面名称：</td>
                  <td style="width:150px"><input id="menuTitle" name="menuTitle"
                                                 class="mini-textbox" onvaluechanged="setMenuTitle" style="width:443px;" /></td>

              </tr>
              <tr>
                  <td class="form-label" style="width:80px;">页面Url：</td>
                  <td style="width:150px"><input id="menuUrl" name="menuUrl"
                                                 class="mini-textbox" onvaluechanged="setUrl" style="width:443px;" /></td>
              </tr>
              <tr>
                  <td class="form-label">操作：</td>
                  <td colspan="3">
                      <div id="pAct" class="mini-combobox" style="width:443px;"  popupWidth="200" textField="text" valueField="id"
                           url="" value="" multiSelect="true"  showClose="true" onvaluechanged="setMenuButtons"  oncloseclick="onCloseClick" >
                          <div property="columns">
                              <div header="id" field="id"></div>
                              <div header="方法描述" field="text"></div>
                          </div>
                      </div>
                  </td>
              </tr>
          </table>
      </div>

  </div>
  </body> 
</html>
