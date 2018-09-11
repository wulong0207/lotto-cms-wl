<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>限号管理</title>
  </head>
  <body>
  
  <div id="searchForm" class="mini-toolbar" style="width:100%;border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
				<td>
			        <btn:operate privilege="ADD">
			     	<a class="mini-button" iconCls="icon-add" plain="true" onclick="LimitObj.addLimit()">新增</a>
			     	</btn:operate>
			     	<btn:operate privilege="UPD">
					<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="LimitObj.editLimit()">修改</a>
					</btn:operate>
				</td>
			</tr>
			
			<tr>
			   <td>
				  彩种类型：<input id="lotteryCategory" class="mini-combobox" style="width:150px;"
					   emptyText="请选择彩种类型" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="LimitObj.closeLotCategory" onvaluechanged="LimitObj.changeLotCategory"/> 
				         彩种：<input id="lotteryCode" class="mini-combobox" style="width:150px;"
					   emptyText="请选择彩种" allowInput="true" valueFromSelect="true" showClose="true" required="true" oncloseclick="Cms.onCloseClick"/> 
				 限号彩期：<input id="issueStart" class="mini-textbox" style="width:120px;" vtype="int"/>到<input id="issueEnd" class="mini-textbox" style="width:120px;" vtype="int"/>
				 限号时间：<input id="timeStart" class="mini-datepicker" allowInput="false" style="width:180px;" 
				     format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick"/>
				        到<input id="timeEnd" class="mini-datepicker" allowInput="false" style="width:180px;"
				     format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick"/>
				</td>
			</tr>
			
			<tr>
			   <td>
			           修改时间：<input id="qryStartTime" class="mini-datepicker" allowInput="false" style="width:180px;" 
				     format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick"/>
				        到<input id="qryEndTime" class="mini-datepicker" allowInput="false" style="width:180px;"
				     format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick"/>
				 限号状态：<input id="status" class="mini-combobox" style="width:150px;"
					   emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
				 限号类型：<input id="limitType" class="mini-combobox" style="width:150px;"
					   emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="Cms.onCloseClick"/>
					   <a class="mini-button" onclick="LimitObj.search()">查询</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:90%;border:0;">
	
		<div size="100%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="lotterymgr/limit/list" idField="id" allowResize="true" showPageSize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" 
				 onpreload="MiniCom.onpreload" onrowdblclick="LimitObj.onrowdblclick()">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="lotteryCode" headerAlign="center" align="center" type="comboboxcolumn">彩种名称
					     <input property="editor" class="mini-combobox" data="Dic.allCode" />
					</div>
					<div field="status" headerAlign="center" align="center" type="comboboxcolumn">限号状态
						<input property="editor" class="mini-combobox" data="Dic.limitStatus" />
					</div>
					<div field="limitType" headerAlign="center" align="center" type="comboboxcolumn">限号类型
						<input property="editor" class="mini-combobox" data="Dic.limitType" />
					</div>
					<div headerAlign="center" align="center" renderer="LimitObj.rendererLimitContent">限号内容</div>
					<div field="issueStart" headerAlign="center" align="center">开始彩期</div>
					<div field="issueEnd" headerAlign="center" align="center">结束彩期</div>
					<div field="timeStart" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">开始时间</div>
					<div field="timeEnd" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">结束时间</div>
					<div field="updateTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="editWindow" class="mini-window" title="" 
		style="width:900px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
			<btn:operate privilege="ADD|UPD">
			<a class="mini-button" onclick="LimitObj.doSubmit()">保存</a>
			</btn:operate>
		</div>
		
		<div id="editForm">
		    <input name="action"  class="mini-hidden"/>
			<input name="url"  class="mini-hidden" />
			<input id="limitId_edit" name="id"  class="mini-hidden" />
			
		    <fieldset style="border:solid 1px #aaa; padding:3px;">
	            <legend>限号基本信息：</legend>
	            <div style="padding:5px;">
	            <table width="100%;">
	            
	            <tr>
	            <td width="13%">彩种类型：</td>
	            <td width="20%">
	            	<input id="lotteryCategory_edit" class="mini-combobox" style="width:150px;"
					   emptyText="请选择彩种类型" allowInput="true" valueFromSelect="true" showClose="true" oncloseclick="LimitObj.closeLotCategoryEdit" onvaluechanged="LimitObj.changeLotCategoryEdit"/> 
	            </td>
	            <td width="13%">彩种：</td>
	            <td width="20%">
	            	<input id="lotteryCode_edit" name="lotteryCode" class="mini-combobox" style="width:150px;"
					   emptyText="请选择彩种" allowInput="true" valueFromSelect="true" showClose="true" required="true" oncloseclick="Cms.onCloseClick"/> 
	            </td>
	            <td width="13%">状态：</td>
	            <td width="20%"><input id="status_edit" name="status" class="mini-combobox" style="width:150px;" 
	            	   emptyText="请选择" allowInput="true" valueFromSelect="true"  showClose="true" required="true" oncloseclick="Cms.onCloseClick"/></td>
	            </tr>
	            
	            <tr>
	            <td>限号类型：</td>
	            <td><input id="limitType_edit" name="limitType" class="mini-combobox" style="width:150px;" 
	               	   emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" required="true" oncloseclick="Cms.onCloseClick"/></td>
	            <td>限号彩期：</td>
	            <td colspan="3">
	            	<input id="issueStart_edit" name="issueStart" class="mini-textbox" style="width:120px;" vtype="int"/>
	            	到<input id="issueEnd_edit" name="issueEnd" class="mini-textbox" style="width:120px;" vtype="int" onvalidation="LimitObj.onIssueValidation"/>
	            </td>
	            </tr>
	            
	            <tr>
	            <td>限号时间：</td>
	            <td colspan="5">
	            	<input id="timeStart_edit" name="timeStart" class="mini-datepicker" allowInput="false" style="width:180px;" 
				     format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick"/>
				        到<input id="timeEnd_edit" name="timeEnd" class="mini-datepicker" allowInput="false" style="width:180px;"
				     format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" showClose="true" oncloseclick="Cms.onCloseClick" onvalidation="LimitObj.onTimeValidation"/>
	            </td>
	            </tr>
	            
	            </table>            
	            </div>
	        </fieldset>
        
        	<fieldset id="fset_limit_info" style="border:solid 1px #aaa;padding:3px;">
            <legend>限号内容：</legend>
            <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
				<table style="width:100%;">
					<tr>
						<td>
						    <btn:operate privilege="ADD">
					     	<a class="mini-button" iconCls="icon-add" plain="true" onclick="Cms.addEditRow('limitInfoGrid')">新增</a>
					     	</btn:operate>
					     	<btn:operate privilege="UPD">
							<a class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.editRow('limitInfoGrid')">修改</a>
							</btn:operate>
							<a  class="mini-button" iconCls="icon-reload" plain="true" onclick="Cms.reload('limitInfoGrid')">刷新</a>
		 					<btn:operate privilege="ADD|UPD">
		 					<a  class="mini-button" iconCls="icon-save" plain="true" onclick="LimitObj.saveLimitInfo()">保存</a>
		 					</btn:operate>
						</td>
						<td width="50%"></td>
					</tr>
				</table>
			</div>
			
            <div class="mini-splitter" vertical="true" style="width:100%;height:200px;" style="border:0;">
				<div size="95%" showCollapseButton="true" style="border:0;">
					<div id="limitInfoGrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
						 url="lotterymgr/limit/info/list" idField="id" allowResize="true" showColumnsMenu="true" pageSize="5" sizeList="[5,10,20,50,100]" multiSelect="true" 
						 onpreload="MiniCom.onpreload" onrowdblclick="Cms.editRow('limitInfoGrid')">
						
						<div property="columns">
							<div type="checkcolumn" width="3%"></div>
							<div type="indexcolumn" align="center" headerAlign="center">序号</div>
							<div headerAlign="center" align="center" renderer="LimitObj.onActionRenderer" cellStyle="padding:0;">#</div>
							<div field="lotteryChildCode" headerAlign="center" align="center" type="comboboxcolumn" renderer="LimitObj.rendererLotChildName">限号玩法
								<input property="editor" width="90%" class="mini-combobox" data="Dic.lotteryChild" required="true"/>
							</div>
							<div field="limitContent" headerAlign="center" align="center">限号内容
								<input property="editor" width="100%" class="mini-textbox" vtype="rangeChar:1,200" required="true"/>
							</div>
							<div field="status" headerAlign="center" align="center" type="comboboxcolumn">限号内容状态
							   <input property="editor" width="90%" class="mini-combobox" data="Dic.limitInfoStatus" required="true"/>
							</div>
							<div field="updateTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
							<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
						</div>
					</div>
				</div>
			</div>
            </fieldset>
        		
		   	<fieldset style="border:solid 1px #aaa;padding:3px;">
            	<legend>操作信息：</legend>
            	<div style="padding:5px;">
             	<table width="100%;">
             	
				<tr>
	            <td>创建人：</td>
	            <td><input name="createBy" class="mini-textbox" enabled="false" allowInput="false" style="width:200px;"/></td>
	            <td>创建时间：</td>
	            <td><input name="createTime" class="mini-datepicker" allowInput="false" enabled="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/></td>
	            <td>备注：</td>
	            <td rowspan="2"><input name="remark" class="mini-textarea" vtype="maxLength:100" style="width:100%;" required="false"/></td>
	            </tr>
	            
	            <tr>
	            <td>修改人：</td>
	            <td><input name="modifyBy" class="mini-textbox" enabled="false" allowInput="false" style="width:200px;"/></td>
	            <td>修改时间：</td>
	            <td><input name="updateTime" class="mini-datepicker" allowInput="false" enabled="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </tr>
	            
           	 	</table>            
            	</div>
        	</fieldset>
		</div>
	</div>
		
  </body>
<script src="<%=basePath%>resources/js/public/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/lotterymgr/lottery_limit.js" type="text/javascript"></script>
</html>