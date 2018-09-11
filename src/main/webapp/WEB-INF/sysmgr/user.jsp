<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

         <base href="<%=basePath%>">
      <title>用户管理</title>
      <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
      <table style="width:100%;">
          <tr>
              <td width="15%" nowrap="nowrap" >
              </td>
              <td width="5%" style="white-space:nowrap;">
                  <input id="options" name="options" class="mini-combobox" style="width:100px;"  data="Data.userOptions" value="userName" />：
                  <input id="value" name="value" class="mini-textbox" style="width:100px;" value=""/>
                  部门： <input id="departId" name="depart" class="mini-combobox" style="width:100px;" nullItemText="请选择部门"  value=""  showNullItem="true" emptyText="请选择部门" valueField="dicDataValue" textField="dicDataName" url="sysmgr/role/dic/list?code=101"/>
                  角色： <input id="id" name="id" class="mini-combobox" style="width:100px;" nullItemText="请选择角色"  value=""  showNullItem="true" emptyText="请选择角色" valueField="roleId" textField="roleName" url="sysmgr/role/rolenames" />
                  状态： <input id="status" name="userStatus" class="mini-combobox" style="width:100px;"   emptyText="请选择状态"  nullItemText="请选择角色"  value=""  showNullItem="true" />
                  <btn:operate privilege="SEARCH" >
                      <a class="mini-button" onclick="hhly_user._search()" >查询</a>
                  </btn:operate>
              </td>
              <td width="30%"></td>
              <td width="15%" nowrap="nowrap" >
                  <btn:operate privilege="ADD" >
                      <a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="hhly_user._add">新增</a>
                  </btn:operate>
                  <btn:operate privilege="DEL" >
                      <a id="del" class="mini-button" iconCls="icon-remove" plain="true" onclick="hhly_user._del">删除</a>
                  </btn:operate>
                  <btn:operate privilege="SAVE" >
                      <a id="save" class="mini-button" iconCls="icon-save" plain="true" onclick="hhly_user._save">保存</a>
                  </btn:operate>
              </td>
          </tr>
      </table>
  </div>

  <div class="mini-splitter"  style="width:100%;height:100%;border:0;"  borderStyle="border:solid 0px;" id="main">

      <div  showCollapseButton="true" style="border:0;">
          <div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;"
               url="sysmgr/user/list" idField="userId" allowResize="true" pageSize="30" multiSelect="false"
               showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
               onrowclick="hhly_user._onclick">
              <div property="columns">
                  <div type="indexcolumn" width="3%">序号</div>
                  <div field="userName" headerAlign="center" align="center">用户账号</div>
                  <div field="userCname" headerAlign="center" align="center">用户昵称
                  </div>
                  <div field="userMobile" headerAlign="center" align="center" >手机号码</div>
                  <div field="userRealName" headerAlign="center" align="center">姓名
                  </div>
                  <div field="userEmail" headerAlign="center" align="center" >邮箱</div>
                  <div type="comboboxcolumn" field="depart" headerAlign="center" align="center">部门
                      <input property="editor" class="mini-combobox"
                             style="width:100%;" url="sysmgr/user/dic?code=101" valueField="dicDataValue" textField="dicDataName"/>
                  </div>
                  <div type="comboboxcolumn" field="cmsRoleId" headerAlign="center" align="center">角色
                      <input property="editor" class="mini-combobox"
                             style="width:100%;" valueField="roleId" textField="roleName" url="sysmgr/user/rolenames" />
                  </div>
                  <div type="comboboxcolumn" field="userStatus" headerAlign="center" align="center">状态
                      <input property="editor" class="mini-combobox"
                             style="width:100%;" data="Data.onOff" />
                  </div>
                  <div field="lastLoginTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">最后登录时间</div>
                  <div field="remark" headerAlign="center" align="center" >备注</div>
              </div>
          </div>
      </div>
      <div showCollapseButton="true"  style="width:700px;">
          <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
              <div id="userForm">
                  <table style="width:100%;">
                      <tr>
                          <td width="8%">用户账号：</td>
                          <td width="10%" style="white-space:nowrap;">
                               <input id="userName" name="userName" class="mini-textbox" style="width:150px;" required="true" requiredErrorText="请输入用户ID" nullItemText="请输入用户ID"  value=""  showNullItem="true" emptyText="请输入用户ID"/>
                          </td>
                          <td width="8%">部门：</td>
                          <td width="10%" style="white-space:nowrap;">
                              <input id="depart" name="depart" class="mini-combobox" style="width:150px;" required="true" requiredErrorText="请选择部门" nullItemText="请选择部门"  value=""  showNullItem="true" emptyText="请选择部门" valueField="dicDataValue" textField="dicDataName" url="sysmgr/role/dic/list?code=101"/>
                          </td>
                          <td width="11%">用户昵称：</td>
                          <td width="10%" style="white-space:nowrap;">
                              <input id="userCname" name="userCname" class="mini-textbox" style="width:150px;" nullItemText="请输入用户昵称"  value=""  showNullItem="true" emptyText="请输入用户昵称"  required="true" requiredErrorText="请输入用户昵称"/>
                          </td>
                      </tr>
                      <tr>
                          <td width="8%">手机号码：</td>
                          <td width="10%" style="white-space:nowrap;">
                              <input id="userMobile" name="userMobile" class="mini-textbox" style="width:150px;" vtype="int;maxLength:11;" maxlength="11" nullItemText="请输入手机号码"  value=""  showNullItem="true" emptyText="请输入手机号码"  required="true" requiredErrorText="请输入手机号码"/>
                          </td>
                          <td width="8%">状态：</td>
                          <td width="10%" style="white-space:nowrap;">
                               <input id="userStatus" name="userStatus" class="mini-combobox" style="width:150px;"   emptyText="请选择状态"   data="Data.onOff" required="true" requiredErrorText="请选择状态"/>
                          </td>
                          <td width="8%">头像：</td>
			              <td width="20%" rowspan="4">
			              	<img id ="img" alt="头像" width="100px;" height="100px;">
							<input id="headUrl_edit" name="headUrl"  class="mini-hidden"/>
							<input type="button" value="从图库选择" onclick="hhly_user.openImage()"/>	
			              </td>
                      </tr>
                      <tr>
                          <td width="11%"> 姓名：</td>
                          <td width="10%" style="white-space:nowrap;">
                              <input id="userRealName" name="userRealName" class="mini-textbox" style="width:150px;" nullItemText="请输入姓名"  value=""  showNullItem="true" emptyText="请输入姓名"  required="true" requiredErrorText="请输入姓名"/>
                          </td>
                          <td width="11%"> 最后登录时间：</td>
                          <td width="10%" style="white-space:nowrap;">
                               <input id="lastLoginTime" name="lastLoginTime" format="yyyy-MM-dd HH:mm:ss"  showTime="true" allowInput="false" class="mini-datepicker"   style="width:150px;" disabled="disabled" />
                          </td>
                          <input id="userId" name="userId" class="mini-hidden" />
                      </tr>
                      <tr>
                          <td width="8%">邮箱：</td>
                          <td width="10%" style="white-space:nowrap;">
                              <input id="userEmail" name="userEmail" class="mini-textbox" style="width:150px;" vtype="email" nullItemText="请输入邮箱"  value=""  showNullItem="true" emptyText="请输入邮箱"  required="true" requiredErrorText="请输入邮箱"/>
                          </td>
                          <td width="8%">角色：</td>
                          <td width="10%" style="white-space:nowrap;">
                               <input id="cmsRoleId" name="cmsRoleId" class="mini-combobox" style="width:150px;" valueField="roleId" textField="roleName" url="sysmgr/user/rolenames" onvaluechanged="hhly_user._checkboxSelectChange"/>
                          </td>
                      </tr>
                       <tr>
                          <td width="8%"> 备注：</td>
                          <td width="10%" style="white-space:nowrap;" >
                              <input id="remark" name="remark" class="mini-textarea" style="width:150px;"  required="true" requiredErrorText="请输入备注" nullItemText="请输入备注"  value=""  showNullItem="true" emptyText="请输入备注" />
                          </td>
                      </tr>
                  </table>
              </div>
          </div>
          <div id="treegrid"  borderStyle="border:solid 0px;" class="mini-treegrid"
               style="width:700px;height:70%;border:solid 0px;" url="sysmgr/user/menus" treeColumn="menuTitle"
               idField="menuId" parentField="parentMenuId" resultAsTree="false"
               allowResize="true" expandOnLoad="true" showTreeIcon="true"
               showCheckBox="true"  allowCellSelect="false"
               enableHotTrack="false"
               autoCheckParent="true" showTreeLines="true"
               ondrawcell="hhly_user._ondrawcell"
               ajaxOptions="{type : 'get'}"
               onnodecheck="hhly_user._onnodecheck">
               
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
  <script src="<%=basePath%>resources/js/sysmgr/user.js" type="text/javascript"></script>
</html>
