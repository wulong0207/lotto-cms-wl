<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>彩期管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td width="15%" nowrap="nowrap" >
			     	<btn:operate privilege="EXPORT">
			        <a class="mini-button" iconCls="icon-download" plain="true" onclick="Current.excel()">导出</a>
			        </btn:operate>
			        <btn:operate privilege="ADD">
			     	<a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="Current.addLotteryIssue()">新增</a>
			     	</btn:operate>
			     	<btn:operate privilege="UPD|SEARCH">
					<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.editwin()">修改</a>
					</btn:operate> 
					<btn:operate privilege="MANUAL_EXECUTE">
					<a id="restart" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.restartThread()">重启线程</a>
					</btn:operate> 
					<btn:operate privilege="MANUAL_EXECUTE">
					<a id="" class="mini-button" iconCls="icon-edit" plain="true" onclick="Current.executeChaseCode()">执行追号</a>
					</btn:operate>	
				</td>
				<td width="5%"  style="white-space:nowrap;">
				         彩种类型：<input id="lotteryCategory"   class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种类型"    allowInput="true" valueFromSelect = "true"  oncloseclick="Current.onCloseClickType" showClose="true" onvaluechanged="Current.lotteryCategoryChange"/> 
				         彩种：<input id="lotteryCode"  class="mini-combobox"  style="width:150px;"
					   emptyText="请选择彩种"   allowInput="true" valueFromSelect = "true"  oncloseclick="Current.onCloseClickCode" showClose="true" onvaluechanged="Current.lotteryCodeChange"/> 
				         彩期：<input id="issueCode"  class="mini-combobox"  style="width:150px;"
					   emptyText="请选择或输入"    allowInput="true"   oncloseclick="Cms.onCloseClick" showClose="true"/> 
					状态：<input id="saleStatus"  class="mini-combobox"  style="width:100px;"
					   emptyText="请选择"  allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true"/> 
					<a class="mini-button" onclick="Current.search()" >查询</a>
				</td>
				<td width="30%"></td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="lotterymgr/issue/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
				 onrowdblclick="Current.onrowdblclick" onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="issueCode" headerAlign="center" align="center" allowSort="true" sortField="issue_code">彩种期号(排)</div>
					<div field="lotteryName" headerAlign="center" align="center">彩种名称</div>
					<div field="currentIssue" type= "comboboxcolumn" headerAlign="center" align="center">彩期状态
					     <input property="editor" class="mini-combobox" data="Dic.issueType" />
					</div>
					<div field="issueLastest" type= "comboboxcolumn" headerAlign="center" align="center">最新开奖
					     <input property="editor" class="mini-combobox" data="Dic.yesNo" />
					</div>					
					<div field="saleTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">本站开始送票时间</div>
					<div field="saleEndTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">本站截止销售时间</div>
					<div field="saleStatus" type= "comboboxcolumn" headerAlign="center" align="center">销售状态
					    <input property="editor" class="mini-combobox" data="Dic.saleStatus" />
					</div>
					<div field="drawCode" headerAlign="center" align="center" >开奖号码</div>
					<!-- <div field="updateTime" width="12%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="createTime" width="12%" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div> -->
				</div>
			</div>
		</div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/lotterymgr/lotteryissue.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
