<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>图片管理</title>
  </head>
  <body>
<div id="panel1" class="mini-panel"  iconCls="icon-add" style="width:100%;height:100%;" 
    showToolbar="true" showCollapseButton="true" showHeader="false" showFooter="true" allowResize="true" collapseOnTitleClick="true">
    <div property="toolbar" >
					<btn:operate privilege="UPD">
					<a class="mini-button" iconCls="icon-downgrade" plain="true" onclick="Current.move()">移动</a>
					</btn:operate>
					<a class="mini-button" iconCls="icon-remove" plain="true" onclick="Current.deleteImage()">删除</a>
					<btn:operate privilege="UPD">
					<a class="mini-button" iconCls="icon-save" plain="true" onclick="Current.saveImage()">保存</a>
					</btn:operate>
					<btn:operate privilege="UPLOAD">
					<a class="mini-button" iconCls="icon-upload" plain="true" onclick="Current.uploadWindow()">上传</a>
					</btn:operate>
					<a class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.chooseAll()">全选</a>
					<a class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.chooseAgainst()">反选</a>
					 图片分组：<input id="imageGroup"   class="mini-combobox"  style="width:100px;" emptyText="请选择"  valueFromSelect = "true" oncloseclick="Cms.onCloseClick" showClose="true" />
					图片名：<input id="imageName" class="mini-textbox" style="width:100px;"/>
					<a class="mini-button" onclick="Current.searchAgain()">查询</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="mini-button" onclick="Current.closeWindow()">确定</a>
    </div>
     <div property="footer">
        总数<span id="total">50</span>
      	页数<span id="page">1/2</span>
      	<a class="mini-button" onclick="Current.up();">上一页</a>
      	<a class="mini-button" onclick="Current.next();">下一页</a>
        <input id="skipValue" class="mini-textbox"  style='vertical-align:middle;width: 50px;' vtype ="int"/>
        <a class="mini-button" onclick="Current.skip();">跳转</a>
        <a class="mini-button" iconCls="icon-reload" plain="true" onclick="Current.reload()"></a>
    </div>
	<table id="imgTable">
	  <tr>
	    <td style="width: 140px;">
		    <img   width="135px;" height="135px;" title="图片详情：&#10文件名：XXXX &#10图片格式：PNG &#10分辨率大小：1924*924"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	  </tr>
	  <tr>
	   <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	  </tr>
	  <tr>
	    <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	  </tr>
	  <tr>
	    <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	     <td style="width: 140px;">
		    <img   width="135px;" height="135px;"><br/>
		    <input type="hidden"/>
		    <input  name="choose" type="checkbox"/>
		    <input  type="text" style="width:100px;"/><input type="hidden"/>
	    </td>
	  </tr>
	</table>
	</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/image.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
