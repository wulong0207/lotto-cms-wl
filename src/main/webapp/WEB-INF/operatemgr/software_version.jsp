<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title></title>
    <style type="text/css">
    	.secondLevel ul {
    		list-style:none;margin:0px;
    	}
    	.secondLevel ul li {float:left;}
    	.redText{color:red;}
    	.appContainer {
    		border: 1px solid #C2C2C2;
    		border-radius: 5px;
    		padding : 10px;
    		overflow: hidden;
    	}
    	.appFunction {
    		border-bottom: 1px solid #C2C2C2;
    		margin : 15px;
    	}
    	span.clear { clear: left; display: block; }
    </style>
  </head>
<body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td width="15%" nowrap="nowrap">
			        <btn:operate privilege="ADD">
				     <a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="Software_Version.toAdd()">新增</a>
			     	</btn:operate>
			        <btn:operate privilege="UPD">
			        <a id="upd" class="mini-button" iconCls="icon-edit" plain="true" onclick="Software_Version.toEdit()">修改</a>
			        </btn:operate>
			     	<btn:operate privilege="DEL">
					<a id="del" class="mini-button" iconCls="icon-remove" plain="true" onclick="Software_Version.updIsnew()">停用</a>
					</btn:operate>
				</td>
				<td  width="5%" style="white-space:nowrap;">
				        版本号: <input id="_code"  class="mini-combobox"  style="width:120px;" allowInput="true" showClose="true" emptyText="请选择"
				         url = "operatemgr/software/version/base" valueFromSelect = "true" oncloseclick="Cms.onCloseClick"/>
				         版本名称:  <input id="_name" class="mini-textbox" emptyText="请输入创建人信息"  vtype="maxLength:50" /> 
				         类型:  <input id="_type" class="mini-combobox" allowInput="true" showClose="true" emptyText="请选择"
				         valueFromSelect = "true" oncloseclick="Cms.onCloseClick"/> 
				       是否最新版本: <input id="_isnew"  class="mini-combobox"  style="width:100px;" showClose="true" oncloseclick="Cms.onCloseClick"/>      
				        是否强制更新: <input id="_isupdate"  class="mini-combobox"  style="width:100px;" showClose="true" oncloseclick="Cms.onCloseClick"/>   
				   <%--    
				        市场渠道: <input id="channelId4Search"  class="mini-combobox"  style="width:100px;" showClose="true" oncloseclick="Cms.onCloseClick"/>   
					--%>
				</td>
				<td width="30%"></td>
			</tr>
			<tr>
				<td width="15%">
				</td>
				<td nowrap="nowrap">
				         创建时间: <input id="beginTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/> 				        
					<a class="mini-button" onclick="Software_Version.search()">查询</a>	
				</td>			
			</tr>
		</table>
	</div>	
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="dataGrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="operatemgr/software/version/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
				 onrowdblclick="Software_Version.onRowdblClick()" onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn" width="2%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<%--
					<div field="channelId" align="center" headerAlign="center" type="comboboxcolumn">渠道名称
						<input property="editor" class="mini-combobox" data="Dic.channel" /> 
					</div>
					--%>
					<div field="code" align="center" headerAlign="center">版本号</div>
					<div field="name" align="center" headerAlign="center">版本名称</div>
					<%--
					<div field="status" align="center" headerAlign="center" type="comboboxcolumn">审核状态
						<input property="editor" class="mini-combobox" data="[{id:0,text:'审核中'},{id:1,text:'已通过'}]" /> 
					</div>
					--%>
					<div field="type" align="center" headerAlign="center" type="comboboxcolumn">类型
						<input property="editor" class="mini-combobox" data="Dic.mobileType" /> 
					</div>
					<div field="isnew" headerAlign="center" align="center" type="comboboxcolumn">是否最新版本
						<input property="editor" class="mini-combobox" data="Dic.isnew" /> 
					</div>
					<div field="isupdate" headerAlign="center" align="center" type="comboboxcolumn">是否强制更新
						<input property="editor" class="mini-combobox" data="Dic.isupdate" /> 
					</div>
					<div field="size" headerAlign="center" align="center">版本大小</div>
					<div field="describe" headerAlign="center" align="center">版本更新说明</div>
					<div field="url" headerAlign="center" align="center">下载地址</div>
				    <div field="appUrl" headerAlign="center" align="center">移动端地址</div>
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
					<div field="createBy" headerAlign="center" align="center">创建人</div>
					<div field="modifyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="modifyBy" headerAlign="center" align="center">修改人</div>
					<div field="remark" headerAlign="center" align="center">备注</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="SoftwareWindow" class="mini-window" 
		style="width:1400px; height:450px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showModal="true" allowResize="true" allowDrag="true" >
		
		<div id="SoftwareForm">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					<%--
					<tr>
					<td>渠道名称：</td>
					<td >
						<input id="channelId" name="channelId" class="mini-combobox" style="width:200px;" required="true" showClose="true" emptyText="请选择"/>
					</td>
					<td>审核状态：</td>
					<td >
						<input id="status" name="status" class="mini-combobox" style="width:200px;" required="true" showClose="true" emptyText="请选择" data="[{id:0,text:'审核中'},{id:1,text:'已通过'}]"/>
					</td>
					</tr>
					--%>
					<input name="channelId" class="mini-hidden"/>
					 <tr>
						<td style="width:80px;">版本号：</td>
						<td >
							<input id="code" name="code" class="mini-textbox" style="width:200px;" required="true" vtype="int" allowInput="false"/>
						</td>
						<td style="width:80px;">下载地址：</td>
						<td >
							<input id="url" name="url" class="mini-textbox" style="width:200px;" required="true" vtype="url"   allowInput="false"/>
						</td>
						<td style="width:80px;">移动端地址：</td>
						<td >
							<input id="appUrl" name="appUrl" class="mini-textbox" style="width:200px;" required="true" vtype="url"   allowInput="false"/>
						</td>												
					</tr>
					 <tr>
						<td style="width:80px;">版本名称：</td>
						<td >
							<input id="name" name="name" class="mini-textbox" style="width:200px;" required="true" allowInput="false"/>
						</td>
						<td style="width:80px;">版本大小：</td>
						<td >
							<input id="size" name="size" class="mini-textbox" style="width:200px;" required="true" allowInput="false" />
						</td>						
					</tr>
					<tr>
						<td>类型：</td>
						<td>
							<input id="type" name="type" class="mini-combobox"  style="width:100px;" showClose="false" emptyText="请选择"  valueFromSelect="true" onvaluechanged="Software_Version.typeChange"/>
						</td>
						<td style="width:150px;" >是否最新版本：</td>
						<td>
							<input id="isnew" name="isnew" class="mini-combobox"  style="width:100px;" showClose="false" emptyText="请选择"  valueFromSelect="true" required="true" />
						</td>
						<td style="width:150px;">是否强制更新：</td>
						<td>
						<input id="isupdate" name="isupdate"  class="mini-combobox"  style="width:100px;" showClose="false" emptyText="请选择" valueFromSelect="true" required="true" />
						</td>																	
					</tr>										
					<tr>
						<td style="width:120px;">版本更新说明：</td>
						<td colspan="3"><textarea id="describe" name="describe" class="mini-textarea" emptyText="请输入版本更新说明"  style="width:400px;height:90px;" required="true"> </textarea></td>
						<td colspan="2">
							<input name="fileAddress" class="mini-hidden" id="fileAddress">
							<input name="originalFilename" class="mini-hidden" id="originalFilename">
							<form method="post" enctype="multipart/form-data" id="uploadFileForm">
								上传文件：<input type="file" name="appfile" id="appfile">
								<br>
								<span>请上传.ipa或.apk应用文件</span>&nbsp;&nbsp;&nbsp;&nbsp;<br><span id="progress" style="color:red"></span><br>
								<input type="button" value="上传" onclick="Software_Version.upload()" />
							</form>
						</td>
					</tr>
					
					
					<tr>
						<td colspan="3" align="right">
							 <input id="id" name="id" class="mini-hidden"/>
							 <input id="httpUrl" name="httpUrl" value="" class="mini-hidden"/>
							 <input id="action" name="action" value="post" class="mini-hidden"/>
							<button style="margin-top: 50px;" id="operateBtn">确认</button>
						</td>
						<td colspan="3" align="left">
							<button style="margin-top: 50px;" id="closeBtn" onclick="Software_Version.close();">取消</button>
						</td>
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

				
				
				
		</div>
	</div>			
	
</body>
<script src="<%=basePath%>resources/js/public/ie-fix.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/ajaxfileupload.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/operate_software_version.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>