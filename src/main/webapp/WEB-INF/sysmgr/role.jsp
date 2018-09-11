<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>角色管理</title>
</head>
<body>
<div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
    <table style="width:100%;">
        <tr>
            <td width="15%" nowrap="nowrap" >
            </td>
            <td width="5%" style="white-space:nowrap;">
                部门： <input id="departId" name="depart" class="mini-combobox" style="width:100px;" nullItemText="请选择部门"  value=""  showNullItem="true" emptyText="请选择部门" valueField="dicDataValue" textField="dicDataName" url="sysmgr/role/dic/list?code=101"/>
                角色： <input id="id" name="id" class="mini-combobox" style="width:100px;" nullItemText="请选择角色"  value=""  showNullItem="true" emptyText="请选择角色" valueField="roleId" textField="roleName" url="sysmgr/role/rolenames" />
                状态： <input id="status" name="roleStatus" class="mini-combobox" style="width:100px;" value="" showNullItem="true"  nullItemText="请选择状态"   emptyText="请选择状态"  />
                <btn:operate privilege="SEARCH" >
                    <a class="mini-button" onclick="hhly_role._search()" >查询</a>
                </btn:operate>
            </td>
            <td width="30%"></td>
            <td width="15%" nowrap="nowrap" >
                <btn:operate privilege="ADD" >
                    <a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="hhly_role._add">新增</a>
                </btn:operate>
                <btn:operate privilege="DEL" >
                    <a id="del" class="mini-button" iconCls="icon-remove" plain="true" onclick="hhly_role._del">删除</a>
                </btn:operate>
                <btn:operate privilege="SAVE" >
                    <a id="save" class="mini-button" iconCls="icon-save" plain="true" onclick="hhly_role._save">保存</a>
                </btn:operate>
            </td>
        </tr>
    </table>
</div>

<div class="mini-splitter"  style="width:100%;height:100%;border:0;"  borderStyle="border:solid 0px;" >

    <div  showCollapseButton="true" style="border:0;">
        <div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;"
             url="sysmgr/role/list" ajaxOptions="{type : 'get'}" idField="id" allowResize="true" pageSize="30" multiSelect="false" idField="roleId"
             showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
             onrowclick="hhly_role._onclick">
            <div property="columns">
                <div type="indexcolumn" width="3%">序号</div>
                <div type="comboboxcolumn" field="depart" headerAlign="center" align="center">部门
                    <input property="editor" class="mini-combobox"
                           style="width:100%;" url="sysmgr/role/dic/list?code=101" valueField="dicDataValue" textField="dicDataName"/>
                </div>
                <div field="roleName" headerAlign="center" align="center" >角色</div>
                <div type="comboboxcolumn" field="roleStatus" headerAlign="center" align="center">状态
                    <input property="editor" class="mini-combobox"
                           style="width:100%;" data="Data.onOff" />
                </div>
                <div field="remark" headerAlign="center" align="center">备注</div>
            </div>
        </div>
    </div>
    <div showCollapseButton="true"  style="width:700px;">
        <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
            <div id="roleForm">
                <table style="width:100%;">
                    <tr>
                        <td width="5%" style="white-space:nowrap;">
                            部门： <input id="depart" name="depart" class="mini-combobox" style="width:100px;" required="true" requiredErrorText="请选择部门" nullItemText="请选择部门"  value=""  showNullItem="true" emptyText="请选择部门" valueField="dicDataValue" textField="dicDataName" url="sysmgr/role/dic/list?code=101"/>
                        </td>
                        <td width="5%" style="white-space:nowrap;text-align: center;margin-top: 30px;" rowspan="2" >
                            备注： <input id="remark" name="remark" class="mini-textbox" style="width:100px;"  required="true" requiredErrorText="请输入备注" nullItemText="请输入备注"  value=""  showNullItem="true" emptyText="请输入备注" />
                        </td>
                    </tr>
                    <tr>
                        <td width="5%" style="white-space:nowrap;">
                            角色： <input id="roleName" name="roleName" class="mini-textbox" style="width:100px;" nullItemText="请输入角色"  value=""  showNullItem="true" emptyText="请输入角色"  required="true" requiredErrorText="请输入角色"/>
                        </td>
                    </tr>
                    <tr>
                        <td width="5%" style="white-space:nowrap;">
                            状态： <input id="roleStatus" name="roleStatus" class="mini-combobox" style="width:100px;"   emptyText="请选择状态"   required="true" requiredErrorText="请选择状态"/>
                        </td>
                        <input id="roleId" name="roleId" class="mini-hidden" />
                    </tr>
                </table>
            </div>
        </div>
        <div id="treegrid"  borderStyle="border:solid 0px;" class="mini-treegrid"
             style="width:700px;height:85%;border:solid 0px;" url="sysmgr/role/menus" treeColumn="menuTitle"
             idField="menuId"  showTreeIcon="true" parentField="parentMenuId" resultAsTree="false" expandOnLoad="true"
             allowResize="true" 
             showCheckBox="true"  allowCellSelect="false"
             enableHotTrack="false"
             autoCheckParent="true" showTreeLines="true"
             ondrawcell="hhly_role._ondrawcell"  onnodeselect="hhly_role._nodeSelect" onnodeclick="hhly_role._treeNodeClick"
             onnodecheck="hhly_role._onnodecheck" 
             ajaxOptions="{type : 'get'}">
            <div property="columns">
                <div type="indexcolumn">序号</div>
                <div name="menuTitle" field="menuTitle" width="250">模块名称</div>
                <div field="buttons" width="100%">操作
                </div>
            </div>
        </div>

    </div>
</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/sysmgr/role.js?version=<%=version%>" type="text/javascript"></script>
</html>
