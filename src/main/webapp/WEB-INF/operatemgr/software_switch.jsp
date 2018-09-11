<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>渠道管理</title>
  </head>
  <body>
			<div id="channelTreegrid" class="mini-treegrid" style="width:100%;height:100%"     
    treeColumn="channelName" idField="channelId" parentField="parentChannelId" resultAsTree="false"
    allowResize="true" expandOnLoad="true" showTreeIcon="true"
    showCheckBox="false" allowSelect="true" allowCellSelect="false"
    enableHotTrack="true" showTreeLines="false"
   >
				<div property="columns">
					<div type="checkcolumn" width="2%"></div>
					<div type="indexcolumn" width="2%" align="center" headerAlign="center">序号</div>
					<div field="channelId" width="2%" headerAlign="center" align="center">渠道ID</div>
					<div field="channelName" name="channelName" headerAlign="center">渠道名称</div>
					<div field="parentChannelId" headerAlign="center" align="center">父级渠道ID</div>
					<div field="terminalPlatform" type= "comboboxcolumn" headerAlign="center" align="center">终端名称
					     <input property="editor" class="mini-combobox" data="Dic.terminalPlatform" />
					</div>
					<div field="name" headerAlign="center" align="center">审核版本号</div>
					<div field="lotteryStatus" type="comboboxcolumn" headerAlign="center" align="center">购彩状态
				     <input property="editor" class="mini-combobox" data="Dic.lotteryStatus" />
					</div>
					<div headerAlign="center" align="center" renderer="operate_software_switch.renderBtn">操作</div>
					<div headerAlign="center" align="center" renderer="operate_software_switch.renderUploadBtn">上传</div>
					<div field="newAppUrl" name="newAppUrl" headerAlign="center">app下载地址</div>
				</div>
		</div>
		
		<%--
			<div id="uploadWin" class="mini-window" width="400px" height="200px">
		<form method="post" enctype="multipart/form-data">
			<input id="catalogue" name="catalogue" type="hidden" /> 
			图片分组：<input id="imageGroup_edit" name="groupid" class="mini-combobox" style="width: 100px;" valueFromSelect="true" /> 
			<br /> <br />
			<span id="prompt">选择要上传的文件(一次可以选择多张)：</span>
			<br /> 
			<span id="files"> </span>
		</form>
		<br />
		
		<br /> 
		<btn:operate privilege="UPLOAD">
		<input type="button" value="上传" onclick="Current.upload()" />
		</btn:operate> 
		<br />
		<br /> 上传进度：
		<progress></progress>
		<span id="progress">0 bytes</span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span
			id="info">"总大小: " + 0 + "bytes"</span>
	</div>
		--%>
			<div id="uploadWin" class="mini-window" width="400px" height="200px">
							<input name="originalFilename" class="mini-hidden" id="originalFilename">
							<form method="post" enctype="multipart/form-data" id="uploadFileForm">
								上传文件：<input type="file" name="appfile" id="appfile">
								<br>
								<span>请上传.ipa或.apk应用文件</span>&nbsp;&nbsp;&nbsp;&nbsp;<br>
								<span id="progress" style="color:red"></span><br>
								<btn:operate privilege="UPLOAD">
						    	<a class="mini-button" onclick="operate_software_switch.upload">上传</a>
						    	</btn:operate>
							</form>
		</div>	
		 
</body>
<script type="text/javascript">
$(function() {
	operate_software_switch.init();
});
</script>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/ajaxfileupload.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/operate_software_switch.js?vsersion=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
</html>
