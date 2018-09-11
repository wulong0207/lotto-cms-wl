<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>彩种管理</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td width="15%" nowrap="nowrap" >
			        <btn:operate privilege="EXPORT">
			        <a class="mini-button" iconCls="icon-download" plain="true" onclick="excel()">导出</a>
			        </btn:operate>
			        <btn:operate privilege="ADD">
			     	<a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="addLotteryType()">新增</a>
			     	</btn:operate>
			     	<btn:operate privilege="UPD|SEARCH">
					<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="edit()">修改</a>
					</btn:operate> 			
				</td>
				<td width="5%" style="white-space:nowrap;">
				         彩种id： <input id="code" class="mini-textbox" style="width:220px;" emptyText="请输入彩种id" />
				         彩种类型：<input id="lotteryType"   class="mini-combobox"  style="width:220px;"
					   emptyText="选择彩种类型"  allowInput="true" valueFromSelect = "true"  oncloseclick="onCloseClickType" showClose="true" onvaluechanged="lotteryTypeChange"/>
				         彩种：<input id="lotteryCode"  class="mini-combobox"  style="width:220px;"
					   emptyText="请选择彩种"  allowInput="true" valueFromSelect = "true"  oncloseclick="Cms.onCloseClick" showClose="true"/>  
					<a class="mini-button" onclick="search()"  >查询</a>
				</td>
				<td width="30%"></td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="lotterymgr/type/list" idField="id" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" showFilterRow="false"
				 onrowdblclick="onrowdblclick" onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="lotteryCode" headerAlign="center" align="center">彩种id</div>
					<div field="lotteryName" headerAlign="center" align="center">彩种名称</div>
					<div field="adminCategory" type= "comboboxcolumn" headerAlign="center" align="center">行政类别
					     <input property="editor" class="mini-combobox" data="Dic.adminCategory" />
					</div>
					<div field="lotteryCategory" type= "comboboxcolumn" headerAlign="center" align="center">彩种类型
					    <input property="editor" class="mini-combobox" data="Dic.lotteryCategory" />
					</div>
					<div field="area" type= "comboboxcolumn" headerAlign="center" align="center">所属省份
						<input property="editor" class="mini-combobox" data="Dic.area" />
					</div>
					<div field="autoType" type= "comboboxcolumn" headerAlign="center" align="center">开派奖
					    <input property="editor" class="mini-combobox" data="Dic.autoType" />
					</div>
					<div field="synIssue" type= "comboboxcolumn" headerAlign="center" align="center">同步彩种
						  <input property="editor" class="mini-combobox" data="Dic.yesNo" />
					</div>
					<div field="saleTime" headerAlign="center" align="center">开售时间</div>
					<div field="buyEndTime" headerAlign="center" align="center">截止时间</div>
					<div field="endCheckTime" headerAlign="center" align="center">检票时间</div>
					<div field="splitMaxNum" headerAlign="center" align="center">拆票倍数</div>
					<div field="splitMaxAmount" headerAlign="center" align="center">拆票金额</div>
					<div field="orderId" headerAlign="center" align="center" >排序号</div>
					<div field="updateTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
					<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="editWindow" class="mini-window" title="" 
		style="width:1400px; height:700px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showModal="true" allowResize="true" allowDrag="true" >
		
		<div  id="editform">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					<tbody>
						<tr>
						   <table  width="100%">
						       <tr>
						         <input name="url" value="type" class="mini-hidden"/>
								 <input name="action" value="put" class="mini-hidden" />
								 <input name="id" class="mini-hidden" />
							     <td colspan="6"><span id="typeName"></span><span style="float: right;"><span style="color: red;">注：</span><span>所有时间为“秒”，以官方销售截止为基准“加上或减去”对应该的时间。</span><span></th>
							  </tr>
							  <tr>
							    <td width="12%">彩种id</td>
							    <td width="20%"><input id="editCode" name="lotteryCode" class="mini-textbox" style="width:200px;"  vtype ="maxLength:10" /></td>
							    <td width="12%" >彩种名称</td>
							    <td width="20%"><input id="editCodeName" name="lotteryName" class="mini-textbox" style="width:200px;" vtype ="maxLength:10" /></td>
							    <td width="15%" rowspan ="3">彩种Logo
							     <input id="lotteryLogoUrl" name="lotteryLogoUrl"  class="mini-hidden"/>
							     <input type="button" value="从图库选择" onclick="openImage()"/>
							     <%--<input type="button" value="上传" onclick="ajaxFileUpload()"/> --%>
							    </td>
							    <td width="15%" rowspan ="3">
							       <img  id ="img" alt="彩种Logo" width="100px;" height="100px;">
							    </td>
							    
							    
							    
							    <td width="15%" rowspan ="3">移动端Logo
							     <input id="lotteryLogoMobile" name="lotteryLogoMobile"  class="mini-hidden"/>
							     <input type="button" value="从图库选择" onclick="openImage4Mobile()"/>
							    </td>
							    <td width="15%" rowspan ="3">
							       <img  id ="imgMobile" alt="移动端Logo" width="100px;" height="100px;">
							    </td>
							  </tr>
							  <tr>
							    <td>行政类别</td>
							    <td><input id = "adminCategory" name="adminCategory"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"    style="width:200px;"  required="true"/></td>
							    <td>彩种类型</td>
							    <td><input id = "lotteryCategory" name="lotteryCategory" class="mini-combobox" emptyText="请选择"  valueFromSelect = "true"  nullItemText="请选择"  style="width:200px;"   required="true"/></td>
							  </tr>
							  <tr>
							    <td>销售状态</td>
							    <td>
							    	<input id ="saleStatus" name="saleStatus"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"  style="width:200px;"   required="true" onvaluechanged="onChangeSaleStatus"/>
							    	<div id="platform" name="platform" class="mini-checkboxlist" textField="text" valueField="id"></div>
							    </td>
							    <td>所属省份</td>
							    <td><input id="area" name="area" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择" style="width:200px;"   required="true"/></td>
							  </tr>
							  <tr>
							    <td>开派奖类型</td>
							    <td><input id="autoType" name="autoType"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"  style="width:200px;"   required="true"/></td>
							    <td>同步彩期</td>
							    <td><input id="synIssue" name="synIssue"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择" style="width:200px;"   required="true"/></td>
							    <td id="cot">出票时间段</td>
							    <td id="cot_"><input id="comeOutTime" name=comeOutTime class="mini-textbox"  style="width:150px;" readonly="readonly"/><a class="mini-button" onclick="updWindow('updComeOutTime')">编辑</a></td>
							  </tr>
							  <tr>
							    <td>彩期生成</td>
							    <td><input id="stopAddIssue" name="stopAddIssue"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"  style="width:200px;"   required="true"/></td>
							    <td>彩期格式</td>
							    <td><input name="format"class="mini-textbox" vtype ="maxLength:15" style="width:200px;"   required="true"/></td>
							    
							    <td id="sst">官方可送票时间</td>
							    <td id="sst_"><input id="startSailTime" name="startSailTime" class="mini-textbox"  style="width:150px;" readonly="readonly"/><a class="mini-button" onclick="updWindow('updStartSailTime')">编辑</a></td>
							  </tr>
							  <tr>
							    <td>送票时间</td>
							    <td><input name="saleTime" class="mini-textbox" vtype ="int;maxLength:5" style="width:200px;"   required="true"/></td>
							    <td>生成彩期数量</td>
							    <td><input name="conIssueNum" class="mini-textbox" vtype ="int;maxLength:3" style="width:200px;"   required="true"/></td>
							    <td id="est">官方截止销售时间</td>
							    <td id="est_"><input id="endSailTime" name="endSailTime" class="mini-textbox"  style="width:150px;" readonly="readonly"/><a class="mini-button" onclick="updWindow('updEndSailTime')">编辑</a></td>
							  </tr>
							  <tr>
							    <td>购买截止时间</td>
							    <td><input name="buyEndTime" class="mini-textbox" vtype ="int;maxLength:5" style="width:200px;"   required="true"/></td>
							    <td>折票最大倍数</td>
							    <td><input name="splitMaxNum" class="mini-textbox" vtype ="int;maxLength:5" style="width:200px;"   required="true"/></td>
							    <td id="dt">开奖时间</td>
							    <td id="dt_"><input id="drawTime" name="drawTime" class="mini-textbox"  style="width:150px;" readonly="readonly"/><a class="mini-button" onclick="updWindow('updDrawTime')">编辑</a></td>
							    <td id="sdc">销售日销售周期</td>
							    <td id="sdc_"><input id="sailDayCycle" name="sailDayCycle" class="mini-textbox"  style="width:150px;" readonly="readonly"/><a class="mini-button" onclick="updSailDayCycle()">编辑</a></td>
							  </tr>
							  <tr>
							    <td>截止后检票时间</td>
							    <td><input name="endCheckTime" class="mini-textbox" vtype ="int;maxLength:5" style="width:200px;"   required="true"/></td>
							    <td>折票最大金额</td>
							    <td><input name="splitMaxAmount" class="mini-textbox" vtype ="int;maxLength:5" style="width:200px;"   required="true"/></td>
							    <td>休市时间</td>
							    <td><input id="vacations" name="vacations" class="mini-textbox"  style="width:150px;"  readonly="readonly"/><a class="mini-button" onclick="updVacation()">编辑</a></td>
							  </tr>
							   <tr>
							    <td colspan="6">操作信息:</td>
							  </tr>
							  <tr>
							    <td >创建时间</td>
							    <td ><input name="createTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/></td>
							    <td >修改时间</td>
							    <td ><input name="updateTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/></td>
							    <td>排序号</td>
							    <td><input name="orderId" class="mini-textbox" vtype="int;maxLength:3" style="width:200px;"/>
							    </td>
							  </tr>
							  <tr>
							    <td>创建人</td>
							    <td><input name="createBy" class="mini-textbox"  enabled="false" allowInput="false" style="width:200px;"/></td>
							    <td>修改人</td>
							    <td><input name="modifyBy" class="mini-textbox"  enabled="false" allowInput="false" style="width:200px;" /></td>
							    <td>最低投注金额</td>
							    <td><input name="minBet" class="mini-textbox" vtype="int;maxLength:10" style="width:200px;"/></td>
							  </tr>
							  <tr>
							    <td>默认最低投注倍数</td>
							    <td><input name="mrMultiple" class="mini-textbox"  vtype="int;maxLength:5" style="width:200px;"/></td>
							    <td>最低投注倍数</td>
							    <td><input name="minMultiple" class="mini-textbox" vtype="int;maxLength:5" style="width:200px;"/></td>
							  </tr>
							  <tr>
								  <td></td>
								  <td></td>
								  <td></td>
								  <td></td>
							     <td></td>
							  	 <td style="float: right;">
							  	 <btn:operate privilege="ADD|UPD">
							  	    <a class="mini-button" onclick="doSubmit">保存信息</a>
							  	 </btn:operate>
							  	 </td>
							  </tr>
							</table>
						</tr>
						<tr>
						    <div id="secondWindow">
							<div  class="mini-toolbar">
								<table style="width:100%;">
									<tr>
										<td  style="white-space:nowrap;">
										    <btn:operate privilege="ADD">
						 					<a  class="mini-button" iconCls="icon-add" plain="true" onclick="Cms.addEditRow('zb_grid')">新增</a>
						 					</btn:operate>
						 					<btn:operate privilege="UPD">
						 					<a  class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.editRow('zb_grid')">修改</a>
						 					</btn:operate>
						 					<btn:operate privilege="DEL">
						 					<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="Cms.delete('zb_grid','lotterymgr/type/betting','id')">删除</a>
						 					</btn:operate>
						 					<a  class="mini-button" iconCls="icon-reload" plain="true" onclick="Cms.reload('zb_grid')">刷新</a>
						 					<btn:operate privilege="ADD|UPD">
						 					<a  class="mini-button" iconCls="icon-save" plain="true" onclick="saveBetting()">保存</a>
						 					</btn:operate>
										</td>
										<td style="float: right;">注数和倍数截止时间</td>
									</tr>
								</table>
							</div>
						   <div showCollapseButton="true">
						   <div title="注数和倍数截止时间 " style="height: 180px">
						   <div id="zb_grid" class="mini-datagrid" onpreload="MiniCom.onpreload" style="width:100%;height:100%;" 
							 url="lotterymgr/type/betting/list" allowResize="true" onrowdblclick="Cms.editRow('zb_grid')" showPager="false"   multiSelect="true">
						        <div property="columns">      
						        		<div type="checkcolumn" width="3%"></div>    
						                <div headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">#</div>
						                <div    field="bettindNum" headerAlign="center" align="center" >注数大于
							                <input property="editor" vtype ="int;maxLength:6" class="mini-textbox"   required="true"/>
						                </div>
						        		<div   field="multipleNum" headerAlign="center" align="center">可投倍数
						        			<input  property="editor"  vtype ="int;maxLength:6" class="mini-textbox"    required="true"/>
						        		</div>
						        		<div  field="endTime" headerAlign="center" align="center">对应彩期的本站截止销售时间距离的秒数
						        			<input  property="editor" vtype="range:0,99999" class="mini-textbox"   required="true"/>
						        		</div>
						        		<div field="updateTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
										<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
				                </div>
					    	</div>
						   </div>
						   </div>
						   <div  class="mini-toolbar">
								<table style="width:100%;">
									<tr>
										<td  style="white-space:nowrap;">
										    <btn:operate privilege="ADD">
						 					<a  class="mini-button" iconCls="icon-add" plain="true" onclick="Cms.addEditRow('child_grid')">新增</a>
						 					</btn:operate>
						 					<btn:operate privilege="UPD">
						 					<a  class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.editRow('child_grid')">修改</a>
						 					</btn:operate>
						 					<btn:operate privilege="DEL">
						 					<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="Cms.delete('child_grid','lotterymgr/type/child','id')">删除</a>
						 					</btn:operate>
						 					<a  class="mini-button" iconCls="icon-reload" plain="true" onclick="Cms.reload('child_grid')">刷新</a>
						 					<btn:operate privilege="ADD|UPD">
						 					<a  class="mini-button" iconCls="icon-save" plain="true" onclick="saveChild()">保存</a>
						 					</btn:operate>	
										</td>
										<td style="float: right;">子玩法管理</td>
									</tr>
								</table>
							</div>
						   <div showCollapseButton="true">
						   <div title="子玩法管理 " style="height: 180px">
						   <div id="child_grid" class="mini-datagrid" onpreload="MiniCom.onpreload" style="width:100%;height:100%;" 
							 url="lotterymgr/type/child/list"  onrowdblclick="Cms.editRow('child_grid')" allowResize="true"  showPager="false"   multiSelect="true" >
						        <div property="columns">      
						        		<div type="checkcolumn" width="3%"></div>    
						        		<div headerAlign="center" align="center" renderer="onActionRendererChild" cellStyle="padding:0;">#</div>
						                <div    field="childName" headerAlign="center" align="center" >子玩法名称
							                <input  property="editor" vtype ="maxLength:20" class="mini-textbox"  required="true"/>
						                </div>
						        		<div   field="lotteryChildCode" headerAlign="center" align="center">子玩法ID
						        			<input  property="editor" vtype ="int;maxLength:10" class="mini-textbox"  required="true"/>
						        		</div>
						        		<div   field="saleStatus" type= "comboboxcolumn" headerAlign="center" align="center">销售状态
						        			<input  property="editor" class="mini-combobox" data="Dic.childSaleStatus"  emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"   required="true"/>
						        		</div>
						        		<div field="updateTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
										<div field="createTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
						        		
				                </div>
					    	</div>
						   </div>
						   </div>
						   </div>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	 <div id="updStartSailTime" class="mini-window" title="官方开售时间(官方可送票时间)" 
		style="width:200px; height:250px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
			<a class="mini-button" onclick="startSailTimeOK">确定</a>
		</div>	
		  <div id="startSailTimeForm">
		        <table width="100%;">
		          <tr>
		          	 <td>星期一</td>
		         	 <td><input id="sweek1" name="sweek1" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期二</td>
		         	 <td><input id="sweek2" name="sweek2" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期三</td>
		         	 <td><input id="sweek3" name="sweek3" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期四</td>
		         	 <td><input id="sweek4" name="sweek4" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期五</td>
		         	 <td><input id="sweek5" name="sweek5" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期六</td>
		         	 <td><input id="sweek6" name="sweek6" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期日</td>
		         	 <td><input id="sweek7" name="sweek7" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
			    </table>
		  </div>
		</div>
	    <div id="updComeOutTime" class="mini-window" title="出票时间段" 
		style="width:400px; height:250px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
			<a class="mini-button" onclick="comeOutTimeOK">确定</a>
		</div>	
		  <div id="comeOutTimeForm">
		        <table width="100%;">
		          <tr>
		          	 <td>星期一</td>
		         	 <td><input id="startweek1" name="startweek1" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		         	 <td>至</td>
		         	 <td><input id="endweek1" name="endweek1" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期二</td>
		         	 <td><input id="startweek2" name="startweek2" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		         	 <td>至</td>
		         	 <td><input id="endweek2" name="endweek2" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期三</td>
		         	 <td><input id="startweek3" name="startweek3" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		         	 <td>至</td>
		         	 <td><input id="endweek3" name="endweek3" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期四</td>
		         	<td><input id="startweek4" name="startweek4" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		         	<td>至</td>
		         	 <td><input id="endweek4" name="endweek4" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期五</td>
		         	 <td><input id="startweek5" name="startweek5" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		         	 <td>至</td>
		         	 <td><input id="endweek5" name="endweek5" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期六</td>
		         	 <td><input id="startweek6" name="startweek6" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		         	 <td>至</td>
		         	 <td><input id="endweek6" name="endweek6" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期日</td>
		         	<td><input id="startweek7" name="startweek7" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		         	<td>至</td>
		         	 <td><input id="endweek7" name="endweek7" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
			    </table>
		  </div>
		</div>
		<div id="updEndSailTime" class="mini-window" title="官方截止销售时间(高频彩：当天第一期截止时间。数字，竞技彩：当天该期截止时间)" 
		style="width:600px; height:250px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
			<a class="mini-button" onclick="endSailTimeOK">确定</a>
		</div>	
		  <div id="endSailTimeForm">
		        <table width="100%;">
		          <tr>
		          	 <td>星期一</td>
		         	 <td><input id="eweek1" name="eweek1" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		         	 <td>跨天</td>
		         	 <td><input id="eday1" name="eday1"  class="mini-combobox"  valueFromSelect = "true" value="1" data="[{id:'0',text:'否'},{id:'1',text:'是'}]"  style="width:50px;" /></td>
		          </tr>
		          <tr>
		          	 <td>星期二</td>
		         	 <td><input id="eweek2" name="eweek2" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		         	 <td>跨天</td>
		         	 <td><input id="eday2" name="eday2"  class="mini-combobox"  valueFromSelect = "true" value="1" data="[{id:'0',text:'否'},{id:'1',text:'是'}]"  style="width:50px;" /></td>
		          </tr>
		          <tr>
		          	 <td>星期三</td>
		         	 <td><input id="eweek3" name="eweek3" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		         	 <td>跨天</td>
		         	 <td><input id="eday3" name="eday3"  class="mini-combobox"  valueFromSelect = "true" value="1" data="[{id:'0',text:'否'},{id:'1',text:'是'}]"  style="width:50px;" /></td>
		          </tr>
		          <tr>
		          	 <td>星期四</td>
		         	 <td><input id="eweek4" name="eweek4" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		         	 <td>跨天</td>
		         	 <td><input id="eday4" name="eday4"  class="mini-combobox"  valueFromSelect = "true" value="1" data="[{id:'0',text:'否'},{id:'1',text:'是'}]"  style="width:50px;" /></td>
		          </tr>
		          <tr>
		          	 <td>星期五</td>
		         	 <td><input id="eweek5" name="eweek5" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		         	 <td>跨天</td>
		         	 <td><input id="eday5" name="eday5"  class="mini-combobox"  valueFromSelect = "true" value="1" data="[{id:'0',text:'否'},{id:'1',text:'是'}]"  style="width:50px;" /></td>
		          </tr>
		          <tr>
		          	 <td>星期六</td>
		         	 <td><input id="eweek6" name="eweek6" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		         	 <td>跨天</td>
		         	 <td><input id="eday6" name="eday6"  class="mini-combobox"  valueFromSelect = "true" value="1" data="[{id:'0',text:'否'},{id:'1',text:'是'}]"  style="width:50px;" /></td>
		          </tr>
		          <tr>
		          	 <td>星期日</td>
		         	 <td><input id="eweek7" name="eweek7" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		         	 <td>跨天</td>
		         	 <td><input id="eday7" name="eday7"  class="mini-combobox"  valueFromSelect = "true" value="1" data="[{id:'0',text:'否'},{id:'1',text:'是'}]"  style="width:50px;" /></td>
		          </tr>
			    </table>
		  </div>
		</div>
		<div id="updSailDayCycle" class="mini-window" title="官方截止销售时间" 
		style="width:600px; height:300px;" >
		      <div  class="mini-toolbar">
								<table style="width:100%;">
									<tr>
										<td  style="white-space:nowrap;">
						 					<a  class="mini-button" iconCls="icon-add" plain="true" onclick="Cms.addEditRow('sailDayCycle_grid')">新增</a>
						 					<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="Cms.deleteRow('sailDayCycle_grid')">删除</a>
						 					<a  class="mini-button" iconCls="icon-save" plain="true" onclick="sailDayCycleOK()">确定</a>
										</td>
									</tr>
								</table>
							</div>
						   <div showCollapseButton="true">
						   <div title="高频彩销售周期 " style="height: 180px">
						   <div id="sailDayCycle_grid" class="mini-datagrid" style="width:100%;height:100%;" 
							     onrowdblclick="Cms.editRow('sailDayCycle_grid')" allowResize="true"  showPager="false"   multiSelect="true">
						        <div property="columns"> 
						        		<div type="checkcolumn" width="3%"></div>    
						        		<div headerAlign="center" align="center" renderer="onActionRendererSailDayCycle" cellStyle="padding:0;">#</div>
						                <div    field="start" headerAlign="center" align="center" >开售期数
							                <input  property="editor" vtype ="int;maxLength:5" class="mini-textbox"  required="true"/>
						                </div>
						        		<div   field="end" headerAlign="center" align="center">结束期数
						        			<input  property="editor" vtype ="int;maxLength:5" class="mini-textbox"  required="true"/>
						        		</div>
						        		<div   field="second" headerAlign="center" align="center">销售时间（秒）
						        			<input  property="editor" vtype ="int;maxLength:10" class="mini-textbox"  required="true"/>
						        		</div>
				                </div>
					    	</div>
						   </div>
						   </div>
		</div>
		<div id="updDrawTime" class="mini-window" title="官方开奖时间" 
		style="width:200px; height:250px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		<div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
			<a class="mini-button" onclick="drawTimeOK">确定</a>
		</div>	
		  <div id="drawTimeForm">
		        <table width="100%;">
		          <tr>
		          	 <td>星期一</td>
		         	 <td><input id="dweek1" name="dweek1" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期二</td>
		         	 <td><input id="dweek2" name="dweek2" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期三</td>
		         	 <td><input id="dweek3" name="dweek3" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期四</td>
		         	 <td><input id="dweek4" name="dweek4" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期五</td>
		         	 <td><input id="dweek5" name="dweek5" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期六</td>
		         	 <td><input id="dweek6" name="dweek6" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
		          <tr>
		          	 <td>星期日</td>
		         	 <td><input id="dweek7" name="dweek7" class="mini-timespinner"  format="HH:mm"  style="width:100px;"/></td>
		          </tr>
			    </table>
		  </div>
		</div>
		<div id="updVacation" class="mini-window" title="休市时间" 
		style="width:600px; height:300px;" >
		      <div  class="mini-toolbar">
								<table style="width:100%;">
									<tr>
										<td  style="white-space:nowrap;">
						 					<a  class="mini-button" iconCls="icon-add" plain="true" onclick="Cms.addEditRow('vacation_grid')">新增</a>
						 					<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="Cms.deleteRow('vacation_grid')">删除</a>
						 					<a  class="mini-button" iconCls="icon-save" plain="true" onclick="vacationOK()">确定</a>
										</td>
									</tr>
								</table>
							</div>
						   <div showCollapseButton="true">
						   <div title="高频彩销售周期 " style="height: 180px">
						   <div id="vacation_grid" class="mini-datagrid"  style="width:100%;height:100%;" 
							     onrowdblclick="Cms.editRow('vacation_grid')" allowResize="true"  showPager="false"   multiSelect="true">
						        <div property="columns"> 
						        		<div type="checkcolumn" width="3%"></div>    
						        		<div  headerAlign="center" align="center" renderer="onActionRendererVacation" cellStyle="padding:0;">#</div>
						                <div    field="date" headerAlign="center" align="center" dateFormat="yyyy-MM-dd">休市日期
							                <input  property="editor"  class="mini-datepicker"  required="true"/>
						                </div>
				                </div>
					    	</div>
						   </div>
						   </div>
		</div> 
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/lotterymgr/lotterytype.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
