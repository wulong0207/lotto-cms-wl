<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>帮助中心管理</title>
  </head>
  <body>
      
<div class="mini-splitter" style="width:100%;height:100%;">
    <div size="10%" showCollapseButton="true">
        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">                
          <btn:operate privilege="UPD">
            <a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_help.editHelpType">修改栏目</a>                  
        	</btn:operate>
        </div>
        <div class="mini-fit">
            <ul id="tree1" class="mini-tree" url="operatemgr/help/type" style="width:100%;" onnodeclick="operate_help.listHelps"
                showTreeIcon="true" textField="fullName" idField="id" parentField="parent" expandOnLoad="true" resultAsTree="false" ajaxOptions="{type : 'get'}">
            </ul>
        </div>
    </div>
    <div showCollapseButton="true">
    
      <div id="tabs" name="tabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">  
      </div>  
        
    </div>        
</div>

	<div id="helpTypeWindow" title="栏目编辑" class="mini-window" style="width:80%;height:80%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
 		<div property="footer" style="text-align:right;">
	    <btn:operate privilege="ADD">
				<a class="mini-button" iconCls="icon-add" onclick="operate_help.showAddHelpType">新增</a>
			</btn:operate>
	    <btn:operate privilege="UPD">
				<a class="mini-button" iconCls="icon-edit" onclick="operate_help.showEditHelpType">修改</a>
			</btn:operate>
	    <btn:operate privilege="ADD|UPD">
	    	<%--这里只保存栏目顺序 --%>
			<a class="mini-button" iconCls="icon-save" onclick="operate_help.saveHelpTypeOrder('helpTypeTreegrid')">保存</a>
			</btn:operate>
		</div>
		<div id="helpTypeTreegrid" class="mini-treegrid" style="width:100%;height:100%"     
    treeColumn="fullName" idField="id" parentField="parent" resultAsTree="false"
    allowResize="true" expandOnLoad="true" showTreeIcon="true"
    showCheckBox="false" allowSelect="true" allowCellSelect="false"
    enableHotTrack="true" showTreeLines="false"
    ajaxOptions="{type : 'get'}">
		    <div property="columns">
		    		<div type="checkcolumn"></div>
		        <div type="indexcolumn">序号</div>
		        <div field="code">栏目编号</div>
		        <div name="fullName" field="fullName">栏目名称</div>
		        <div field="shortName">栏目简称</div>
		        <div field="virtual" type="comboboxcolumn" headerAlign="center" align="center">
				     虚拟栏目<input property="editor" class="mini-combobox" data="Dic.virtual" />
				</div>
		        <div field="status" type="comboboxcolumn" headerAlign="center" align="center">
				     显示<input property="editor" class="mini-combobox" data="Dic.display" />
				</div>
		        <div field="identifiers">栏目标识符</div>
		        <div field="descriptor">栏目描述</div>
		        <div headerAlign="center" align="center" renderer="operate_help.orderHtml">排序</div>
		    </div>
		</div>
	</div>
    
 	<div id="helpTypeDetailWindow" title="栏目修改" class="mini-window" style="width:60%;height:60%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
 		<div property="footer" style="text-align:center;">
	    <btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="operate_help.mergeHelpType('menu')">保存</a>
				<a class="mini-button" onclick="operate_help.closeHelpType">取消</a>
			</btn:operate>
		</div>
	    <div id="helpTypeForm" class="form">
	    	<input class="mini-hidden" name="id"/>
	    	<input class="mini-hidden" name="action"/>
	    	<input class="mini-hidden" name="menu"/>
	    	<input class="mini-hidden" name="code"/>
		    <table style="width:100%;">
           <tr>
               <td>栏目名称</td>
               <td><input name="fullName" class="mini-textbox" required="true"/></td>
               <td>栏目简称</td>
               <td><input name="shortName" class="mini-textbox" required="true"/></td>
           </tr>
           <tr>
               <td>标识符</td>
               <td><input id="identifiers" name="identifiers" class="mini-textbox" emptyText="请输入或选择" required="true"/></td>
               <td>虚拟栏目</td>
               <td><input id="virtual" name="virtual" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
           </tr>
           <tr>
               <td>上级栏目</td>
               <td>
               <input id="parent" name="parent" class="mini-treeselect" multiSelect="false" valueFromSelect="true"
		        textField="fullName" valueField="id" parentField="parent"
		        showFolderCheckBox="false" required="true" expandOnLoad="true" width="250px"/>
               </td>
               <td>是否显示</td>
               <td><input id="typeStatus" name="status" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
           </tr>
           <tr>
           		<td>栏目描述</td>
           		<td colspan="3"><input name="descriptor" style="width:76%;" class="mini-textarea"/></td>
           </tr>
       </table>
    </div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/operate_help.js?vsersion=<%=version%>" type="text/javascript"></script>
<script>
	$(function() {
		operate_help.init();
	});
</script>
</html>
