<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>意见箱</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="correctForm">
		<table style="width:100%;">
			<tr>
				<td width="5%"  style="white-space:nowrap;">
					 意见来源：<input id="sources" name="sources" class="mini-combobox" style="width:220px;" emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true"
					 oncloseclick="Correct.onCloseClick">
					 提交时间： <input id="beginTime" name="beginTime" class="mini-datepicker" allowInput="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" 
					 		showTime="true" showOkButton="true"  showTodayButton="true" showClearButton ="false" showClose="true" oncloseclick="Correct.onCloseClick"/>
                    	到<input id="endTime" name="endTime" class="mini-datepicker" allowInput="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss"
                    		 showTime="true" showOkButton="true"  showTodayButton="true" showClearButton ="false" showClose="true" oncloseclick="Correct.onCloseClick"/>
					  处理状态：<input id="status" name="status" class="mini-combobox" style="width:220px;" emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true"
					 oncloseclick="Correct.onCloseClick">
					创建人： <input id="createBy" name="createBy" class="mini-textbox" emptyText="请输入创建人信息"  vtype="maxLength:50" />
					 
					<a class="mini-button" onclick="Correct.search()" >查询</a>
				</td>
				<td width="30%"></td>
			</tr>
		</table>
	</div>
</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="dataGrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="operatemgr/correct/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
				 onrowdblclick="Correct.onRowdblClick()" onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<!-- <div type="checkcolumn" width="3%"></div> -->
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="id" width="7%" headerAlign="center" align="center" allowSort="true" sortField="id">意见ID</div>
					<div field="helpId" headerAlign="center" align="center" renderer="Correct.articleRenderer">文章ID</div>
					<div field="abcTitle" headerAlign="center" align="center">文章Tile</div>
					<div field="sources" headerAlign="center" align="center" type="comboboxcolumn">意见来源
						 <input property="editor" class="mini-combobox" data="Dic.sources" /> 
					</div>
					
					<div field="correct" headerAlign="center" align="center">意见内容</div>
					
					<div field="createBy" headerAlign="center" align="center">创建人</div>
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">提交时间</div>
					<div field="status" headerAlign="center" align="center" type="comboboxcolumn">处理状态
						 <input property="editor" class="mini-combobox" data="Dic.status" /> 
					</div>
					<div field="type" headerAlign="center" align="center" type="comboboxcolumn">类型
						 <input property="editor" class="mini-combobox" data="Dic.type" /> 
					</div>					
					<div field="modifyBy" headerAlign="center" align="center">操作人员</div>
					<div field="modifyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">操作时间</div>
					<div field="remark" headerAlign="center" align="center">备注</div>
				<!-- 	<div field="drawCode" width="12%" headerAlign="center" align="center" renderer="">操作</div> -->
				</div>
			</div>
		</div>
	</div>
</body>

	<div id="editWindow" class="mini-window" title="修改意见信息" 
		style="width:800px;height:400px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showModal="true" allowResize="true" allowDrag="true" >
		
		<div  id="editForm">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					<tbody>
						<tr>
						   <table  width="100%">
						       <tr>
						       		<td>文章id</td>
						        	<td colspan="3"><input id="helpId" name="helpId" class="mini-textbox" style="width:200px;" allowInput="false" /></td>
							  </tr>
						       <tr>
						       		<td>意见内容 </td>
						        	<td colspan="3"><textarea id="correct" name="correct" class="mini-textarea" style="width:400px;height:150px;" allowInput="false"  vtype ="maxLength:200"></textarea></td>
							  </tr>
							  <tr>
							  	<td colspan="3" align="right"><a class="mini-button" onclick="Correct.edit()" style="width:60px;margin-right:20px;">处理</a>
							  	 <input name="id" value="-1" class="mini-hidden">
							  	 <input name="status" value="-1" class="mini-hidden">
		         				 <input name="url" value="type" class="mini-hidden"/>
								 <input name="action" value="put" class="mini-hidden" />
							  	</td>
							  	<td colspan="3" align="left"><a class="mini-button" onclick="Correct.close()" style="width:60px;margin-right:20px;">取消</a></td>							  								  	
							  </tr>
							  <br/>
							   <tr>
							    <td colspan="6">操作信息:</td>
							  </tr>							  
							  <tr>
							    <td >创建时间</td>
							    <td ><input name="createTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/></td>
							    <td >修改时间</td>
							    <td ><input name="modifyTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/></td>
							    <td rowspan="2">备注</td>
							    <td rowspan="2"><input name="remark" class="mini-textarea" vtype ="maxLength:200" style="width:200px;"/>
							    </td>
							  </tr>
							  <tr>
							    <td>创建人</td>
							    <td><input name="createBy" class="mini-textbox"  enabled="false" allowInput="false" style="width:200px;"/></td>
							    <td>修改人</td>
							    <td><input name="modifyBy" class="mini-textbox"  enabled="false" allowInput="false" style="width:200px;" /></td>
							  </tr>
							</table>
						</tr>
	
					</tbody>
				</table>
			</div>
		</div>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/operate_correct.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>