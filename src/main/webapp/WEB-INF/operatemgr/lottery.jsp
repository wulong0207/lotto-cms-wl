<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script type="text/javascript" charset="utf-8" src="<%=basePath%>resources/js/public/jscolor.min.js"></script>
  	 <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/public/common.css">
    <title>彩种运营管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="ADD">
		        <a class="mini-button" iconCls="icon-add" plain="true" onclick="operate_lottery.add()">新增</a>
	        </btn:operate>
	        <btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_lottery.edit()">修改</a>
	        </btn:operate>
				</td>
			</tr>
			<tr>
				<td>
					发布平台<input id="platform" name="platform" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
					彩种状态<input id="status" name="status" class="mini-combobox"  style="width:150px;" emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
					是否默认<input id="isDefault" name="isDefault" class="mini-combobox" style="width:150px;"  emptyText="请选择" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"/>							
					<input id="searchTimeType"  name="searchTimeType" class="mini-combobox"  style="width:150px;" valueFromSelect ="true"/>					
					<input id="startWeek"  name="startWeek" style="width:70px;" class="mini-combobox" emptyText="请选择" valueFromSelect="true"/>	                					
					<input id="startTime" name="startTime" class="mini-textbox" style="width:100px;"  emptyText="00:00:00" />
				        到
				    <input id="endWeek"  name="endWeek" style="width:70px;" class="mini-combobox" emptyText="请选择" valueFromSelect="true"/>	                					
					<input id="endTime" name="endTime" class="mini-textbox" style="width:100px;"  emptyText="00:00:00" />
					<a class="mini-button" onclick="Cms.search('datagrid','form1',['startTime','endTime'])" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:92%;" 
		 url="operatemgr/lottery/list" idField="id" allowResize="true" multiSelect="true" 
		 showColumnsMenu="true" onrowdblclick="operate_lottery.edit" showFilterRow="false"
		 allowResize="false" 
		 >
		<div property="columns">
			<div type="checkcolumn" width="1%"></div>
			<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
			<div field="releaseCode"  headerAlign="center" align="center">发布编号</div>
			<div field="platform" type="comboboxcolumn" headerAlign="center" align="center">发布平台
		     <input property="editor" class="mini-combobox" data="Dic.platform" />
			</div>
			<div field="status"  type="comboboxcolumn" headerAlign="center" align="center">彩种状态
		     <input property="editor" class="mini-combobox" data="Dic.status" />
			</div>
			<div field="isDefault"  type="comboboxcolumn" headerAlign="center" align="center">是否默认
		     <input property="editor" class="mini-combobox" data="Dic.isDefault" />
			</div>
			<div field="lotteryDesc" headerAlign="center" align="center">备注说明</div>
			<div field="onlineWeekTime" headerAlign="center" align="center" renderer="operate_lottery.pushOnlineWeekTime">上线时间</div>					
			<div field="offlineWeekTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" renderer="operate_lottery.pushOfflineWeekTime">下线时间</div>			
			<div field="modifyBy"  headerAlign="center" align="center">修改人</div>
		</div>
	</div>
	
	<div id="detailWindow" title="新增彩种运营详情" class="mini-window" style="width:1100px;height:750px" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
 		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
	    	<btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="operate_lottery.merge">保存</a>
			</btn:operate>
				<a class="mini-button" onclick="operate_lottery.closeWindow">关闭</a>
		</div>
	    <div id="detailForm" class="form">
	    	<input name="id"  class="mini-hidden"/>
	     	<input name="url"  class="mini-hidden"/>
			 	<input name="action"  class="mini-hidden"/>
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <legend>彩种运营基本信息：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td style="width:100px;">发布平台</td>
	                <td style="width:150px;"><input id="platformDetail" name="platform" style="width:80%;" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
	                <td style="width:100px;">发布编号</td>
	                <td style="width:150px;"><input name="releaseCode"  style="width:80%;" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">彩种状态</td>
	                <td style="width:150px;"><input id="statusDetail" name="status" style="width:80%;" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">计划上线时间</td>
	                <td style="width:150px;">
	                <input id="onlineWeekDetail"  style="width:30%;" name="onlineWeek" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/>
	                <input name="onlineTime" style="width:50%;" class="mini-textbox" emptyText="00:00:00"  required="true"/>
	                </td>
	                <td style="width:100px;">计划下线时间</td>
	                <td style="width:150px;">
	                <input id="offlineWeekDetail"  style="width:30%;" name="offlineWeek" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/>	                
	                <input name="offlineTime" style="width:50%;" class="mini-textbox" emptyText="00:00:00" showTime="true" required="true"/>
	                </td>
	                <td style="width:100px;">是否默认</td>
	                <td style="width:150px;"><input id="isDefaultDetail"  style="width:80%;" name="isDefault" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>	           	           
	            </tr>
	        </table>
			  </fieldset>
			  
			  <fieldset style="border:solid 1px #aaa; padding:3px;height: 500px;">
			  	<legend>彩种显示信息：</legend>
			  	<div style="width:100%;">
			        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
			            <table style="width:100%;">
	            			<tr>
											<td width="10%" nowrap="nowrap">
												<btn:operate privilege="UPD">
									        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.moveUp('lotteryInfoDatagrid')">上移</a>
								        </btn:operate>
												<btn:operate privilege="UPD">
									        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.moveDown('lotteryInfoDatagrid')">下移</a>
								        </btn:operate>
								        <btn:operate privilege="ADD">
									        <a class="mini-button" iconCls="icon-add" plain="true" onclick="operate_lottery.addLotteryInfo">新增</a>
								        </btn:operate>
								        <btn:operate privilege="UPD">
									        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_lottery.editLotteryInfo">修改</a>
								        </btn:operate>
								        <btn:operate privilege="UPD">
									        <a class="mini-button" iconCls="icon-remove" plain="true" onclick="operate_lottery.deleteLotteryInfo">删除</a>
								        </btn:operate>
								        <%--
								        <btn:operate privilege="UPD">
									        <a class="mini-button" iconCls="icon-save" plain="true" onclick="operate_lottery.edit()">保存</a>
								        </btn:operate>
								        --%>
											</td>
										</tr>
			            </table>           
			        </div>
			    </div>
		  		<div id="lotteryInfoDatagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height: 450px;" 
						  idField="id" multiSelect="true" showPager="false"
						 showColumnsMenu="true" onrowdblclick="operate_lottery.editLotteryInfo" showFilterRow="false">
						<div property="columns">
							<div type="checkcolumn" width="3%"></div>
							<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
							<div field="typeAlias" headerAlign="center" align="center">彩种别名</div>
							<div field="categoryId" type="comboboxcolumn" headerAlign="center" align="center">彩种类型
						     <input property="editor" class="mini-combobox" data="Dic.categoryType" />
							</div>
							<div field="icon" type="comboboxcolumn" headerAlign="center" align="center">图标
						     <input property="editor" class="mini-combobox" data="Dic.icon" />
							</div>
							<div field="lotteryDescribe" headerAlign="center" align="center">彩种描述</div>
							<div field="typeKey" headerAlign="center" align="center">运营文案</div>
							<div field="typeKeyUrl" headerAlign="center" align="center">文案链接</div>
							<div field="typeUrl" headerAlign="center" align="center">彩种链接</div>
							<div field="display" type="comboboxcolumn" headerAlign="center" align="center">是否显示
						     <input property="editor" class="mini-combobox" data="Dic.display" />
							</div>
						</div>
						</div>
			  </fieldset>
			  
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <legend>操作信息：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td style="width:100px;">创建时间</td>
	                <td style="width:150px;"><input name="createTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	                <td style="width:100px;">修改时间</td>
	                <td style="width:150px;"><input name="modifyTime" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"/></td>
	            		<td style="width:100px;" rowspan="2">备注</td>
	                <td style="width:150px;" rowspan="2"><input name="lotteryDesc" class="mini-textarea"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">创建人</td>
	                <td style="width:150px;"><input name="createBy" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">修改人</td>
	                <td style="width:150px;"><input name="modifyBy" class="mini-textbox" enabled="false"/></td>
	            </tr>
	        </table>
			  </fieldset>
	    </div>
	</div>
	
	
	
	
	<div id="lotteryInfoWindow" title="彩种修改" class="mini-window" style="width:50%;height:50%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
 		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
	    <btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="operate_lottery.mergeDetailRow('lotteryInfoDatagrid','lotteryInfoForm','lotteryInfoWindow')">确认</a>
				<a class="mini-button" onclick="operate_lottery.cancelLotteryInfo">取消</a>
			</btn:operate>
		</div>
	    <div id="lotteryInfoForm" class="form">
	    	<input class="mini-hidden" name="id"/>
	    	<input class="mini-hidden" name="operation"/>
	    	 
		    <table style="width:100%;">
		              
		    
           <tr>
               <td>彩种类型</td>
               <td><input id="categoryId" name="categoryId" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true" onvaluechanged="operate_lottery.categoryIdChange"/></td>
               <td>彩种名称</td>
               <td><input id="typeId" name="typeId" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true" onvaluechanged="operate_lottery.lotteryTypeChange"/></td>
               <td>子彩种</td>
               <td><input id="lotteryChildCode" name="lotteryChildCode" class="mini-combobox" emptyText="请选择" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"/></td>
           </tr>
           <tr>
               <td>彩种别名</td>
               <td><input name="typeAlias" class="mini-textbox" required="true"/></td>
               <td>彩种链接</td>
               <td><input name="typeUrl" class="mini-textbox"/></td>
               <td>彩种颜色</td>
               <td><input id="color" class="jscolor {hash:true}" value="000000"></td>
           </tr>
           <tr>
               <td>是否显示</td>
               <td><input id="display" name="display" class="mini-combobox" emptyText="请选择" onvaluechanged="operate_lottery.displayChange" valueFromSelect="true" required="true"/></td>
               <td>下线时间</td>
               <td><input id="infoOfflineTime" name="offlineTime" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" enabled="false" showTime="true" timeFormat="HH:mm:ss"/></td>
               <td>彩种描述</td>
               <td><input name="lotteryDescribe" class="mini-textbox" style="width:200px;"/></td>
               
           </tr>
       </table> 
	    <fieldset style="border:solid 1px #aaa; padding:3px;">
		    <table style="width:100%;">
            <tr>
                <td>运营图标</td>
                <td><input id="icon" name="icon" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
                <td>打开方式</td>
                <td><input id="target" name="target" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
            </tr>
            <tr>
                <td>运营文案</td>
                <td><input name="typeKey" class="mini-textbox"/></td>
            </tr>
            <tr>
                <td>文案链接</td>
                <td><input name="typeKeyUrl" class="mini-textbox"/></td>
            </tr>
            <tr>
		            <td rowspan="2">广告图地址</td>
		            <td colspan="3"><input name="url" id="url" class="mini-textbox" style="width:100%;" enabled="false"/></td>
		            <td>
		            	<btn:operate privilege="UPD">
							<input type="button" value="从图库选择" onclick="operate_lottery.openImage()"/>
						</btn:operate>
		            </td>
	            </tr>
	             <tr>
	            	<td colspan="4"><img id="adImg" alt="广告图片" width="500px;" height="300px;"></td>	            	
	            </tr>	           
        </table>
		  </fieldset>
    </div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/operate_lottery.js" type="text/javascript"></script>
</html>
