<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>快投模块管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="ADD">
		        <a class="mini-button" iconCls="icon-add" plain="true" onclick="operate_fast.add()">新增</a>
	        </btn:operate>
	        <btn:operate privilege="UPD">
		        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_fast.edit()">修改</a>
	        </btn:operate>
				</td>
			</tr>
			<tr>
				<td>
					发布平台<input id="platform" name="platform" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
					模块页面<input id="position" name="position" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					快投状态<input id="status" name="status" class="mini-combobox"  style="width:150px;" emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 		
					是否默认<input id="isDefault" name="isDefault" class="mini-combobox" style="width:150px;"  emptyText="请选择" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"/>		
					<input id="searchTimeType"  name="searchTimeType" class="mini-combobox"  style="width:150px;" valueFromSelect ="true"/>
					<input id="startWeek"  name="startWeek" style="width:70px;" class="mini-combobox" emptyText="请选择" valueFromSelect="true"/>	                					
					<input id="startTime" name="startTime" class="mini-textbox" style="width:100px;"  emptyText="00:00:00" />
				        到
				    <input id="endWeek"  name="endWeek" style="width:70px;" class="mini-combobox" emptyText="请选择" valueFromSelect="true"/>	                					
					<input id="endTime" name="endTime" class="mini-textbox" style="width:100px;"  emptyText="00:00:00" />  					
					<a class="mini-button" onclick="Cms.search('datagrid','form1')" >查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:92%;" 
		 url="operatemgr/fast/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
		 showColumnsMenu="true" onrowdblclick="operate_fast.edit" showFilterRow="false">
		<div property="columns">
			<div type="checkcolumn" width="3%"></div>
			<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
			<div field="fastCode" headerAlign="center" align="center">发布编号</div>
			<div field="platform" type="comboboxcolumn" headerAlign="center" align="center">发布平台
		     <input property="editor" class="mini-combobox" data="Dic.platform" />
			</div>
			<div field="status" type="comboboxcolumn" headerAlign="center" align="center">快投状态
		     <input property="editor" class="mini-combobox" data="Dic.status" />
			</div>
			<div field="isDefault" type="comboboxcolumn" headerAlign="center" align="center">是否默认
		     <input property="editor" class="mini-combobox" data="Dic.isDefault" />
			</div>			
			<div field="fastDesc" headerAlign="center" align="center">备注说明</div>
			<div field="onlineWeekTime" headerAlign="center" align="center" renderer="operate_fast.pushOnlineWeekTime">上线时间</div>					
			<div field="offlineWeekTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" renderer="operate_fast.pushOfflineWeekTime">下线时间</div>	
			<div field="modifyBy" headerAlign="center" align="center">修改人</div>
			<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>	
			<div field="updateTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>	
		</div>
	</div>
	<div id="detailWindow" title="新增快投运营详情" class="mini-window" style="width:1100px;height:85%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
 		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
	    <btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="operate_fast.merge">保存</a>
			</btn:operate>
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
	                <td style="width:150px;"><input name="fastCode"  style="width:80%;" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">快投状态</td>
	                <td style="width:150px;"><input id="statusDetail"  style="width:80%;" name="status" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
	            </tr>
	            <tr>
	            	<td style="width:100px;">模块页面</td>
	                <td style="width:150px;"><input id="positionDetail" name="position" style="width:80%;" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
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
	            </tr>
	            <tr>
	            	<td style="width:100px;">是否默认</td>
	                <td style="width:150px;"><input id="isDefaultDetail"  style="width:80%;" name="isDefault" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>	           
	            </tr>
	        </table>
			</fieldset>
			  
			<fieldset style="border:solid 1px #aaa; padding:3px;height: 500px;">
			  	<legend>快投显示信息：注：主站Web首最多显示3个快投模块。</legend>
			  	<div style="width:100%;">
			        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
			            <table style="width:100%;">
	            			<tr>
											<td width="10%" nowrap="nowrap">
												<btn:operate privilege="UPD">
									        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.moveUp('fastInfoDatagrid')">上移</a>
								        </btn:operate>
												<btn:operate privilege="UPD">
									        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.moveDown('fastInfoDatagrid')">下移</a>
								        </btn:operate>
								        <btn:operate privilege="ADD">
									        <a class="mini-button" iconCls="icon-add" plain="true" onclick="operate_fast.addFastInfo">新增</a>
								        </btn:operate>
								        <btn:operate privilege="UPD">
									        <a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_fast.editFastInfo">修改</a>
								        </btn:operate>
								        <btn:operate privilege="UPD">
									        <a class="mini-button" iconCls="icon-remove" plain="true" onclick="operate_fast.deleteFastInfo">删除</a>
								        </btn:operate>
								        <%--
								        <btn:operate privilege="UPD">
									        <a class="mini-button" iconCls="icon-save" plain="true" onclick="operate_fast.edit()">保存</a>
								        </btn:operate>
								        --%>
											</td>
										</tr>
			            </table>           
			        </div>
			    </div>
		  		<div id="fastInfoDatagrid" class="mini-datagrid"  onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height: 450px;" 
						 url="operatemgr/fast/listFastInfo" idField="id" allowResize="true" showPager="false" multiSelect="true" 
						 showColumnsMenu="true" onrowdblclick="operate_fast.editFastInfo" showFilterRow="false"  >
						<div property="columns">
							<div type="checkcolumn" width="3%"></div>
							<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
							<div field="typeId" type="comboboxcolumn" headerAlign="center" align="center">彩种名称
						     <input property="editor" class="mini-combobox" data="Dic.allCode"/>
							</div>
							<div headerAlign="center" align="center" renderer="operate_fast.orderHtml">投注类型</div>
							<div field="fastAlias" headerAlign="center" align="center">页签别名</div>
							<div field="fastUrl" headerAlign="center" align="center">页签链接</div>
							<div field="icon" type="comboboxcolumn" headerAlign="center" align="center">图标
						     <input property="editor" class="mini-combobox" data="Dic.icon" />
							</div>
							<div field="typeKey" headerAlign="center" align="center">运营文案</div>
							<div field="typeKeyUrl" headerAlign="center" align="center">文案链接</div>						
							<div field="fastSelected" type="comboboxcolumn" headerAlign="center" align="center">默认选中
							 <input property="editor" class="mini-combobox" data="Dic.fastSelected" />
							</div>
							<div field="multiple" headerAlign="center" align="center">默认倍数</div>						     
						</div>
				</div>
		    </fieldset>
			  
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
			    <legend>操作信息：</legend>
			    <table style="width:100%;">
	            <tr>
	                <td style="width:100px;">创建时间</td>
	                <td  style="width:150px;"><input name="createTime" style="width:80%;" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"  timeFormat="HH:mm:ss"  showTime="true"/></td>
	                <td style="width:100px;">修改时间</td>
	                <td style="width:150px;"><input name="updateTime"  style="width:80%;" class="mini-datepicker" enabled="false" format="yyyy-MM-dd HH:mm:ss"  timeFormat="HH:mm:ss"  showTime="true"/></td>
	            		<td style="width:100px;" rowspan="2">备注</td>
	                <td style="width:150px;" rowspan="2"><input name="fastDesc"  style="width:80%;" class="mini-textarea"/></td>
	            </tr>
	            <tr>
	                <td style="width:100px;">创建人</td>
	                <td style="width:150px;"><input name="createBy"  style="width:80%;" class="mini-textbox" enabled="false"/></td>
	                <td style="width:100px;">修改人</td>
	                <td style="width:150px;"><input name="modifyBy"  style="width:80%;" class="mini-textbox" enabled="false"/></td>
	            </tr>
	        </table>
			  </fieldset>
	    </div>
	</div>
	
	
	
	
	
	<div id="fastInfoWindow" title="模板修改" class="mini-window" style="width:50%;height:50%" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
 		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
	    <btn:operate privilege="ADD|UPD">
				<a class="mini-button" onclick="operate_fast.mergeDetailRow('fastInfoDatagrid','fastInfoForm','fastInfoWindow')">确认</a>
				<a class="mini-button" onclick="operate_fast.cancelFastInfo">取消</a>
			</btn:operate>
		</div>
	    <div id="fastInfoForm" class="form">
	    	<input class="mini-hidden" name="id"/>
	    	<input class="mini-hidden" name="operation"/>
		    <table style="width:100%;">
           <tr>
               <td>彩种名称</td>
               <td><input id="typeId" name="typeId" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true" onvaluechanged="operate_fast.getCategory"/></td>
           	   <td>页签别名</td>
               <td><input name="fastAlias" class="mini-textbox" required="true"/></td>
           </tr>
           <tr>
               <td>投注类型</td>
               <td><input id="categoryId" name="categoryId" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
               <td>页签链接</td>
               <td><input name="fastUrl" class="mini-textbox" required="true"/></td>
           </tr>
           <tr>
               <td>默认选中</td>
               <td><input id="fastSelected" name="fastSelected" class="mini-combobox" emptyText="请选择" valueFromSelect="true" required="true"/></td>
               <td>默认倍数</td>
               <td><input name="multiple" class="mini-textbox" required="true"/></td>
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
                <td><input name="typeKeyUrl" class="mini-textbox" /></td>
            </tr>
           </table>
	    </fieldset>
    </div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/operate_fast.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
