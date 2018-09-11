<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>支付管理</title>
  </head>
  <body>
  
    <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
		<table style="width:100%;">
			<tr>
			     <td width="15%" nowrap="nowrap" >
			        <btn:operate privilege="ADD">
			     	<a id="add" class="mini-button" iconCls="icon-add" plain="true" onclick="payment.add()">新增</a>
			     	</btn:operate>
			     	<btn:operate privilege="UPD|SEARCH">
					<a id="edit" class="mini-button" iconCls="icon-edit" plain="true" onclick="payment.edit()">修改</a>
					</btn:operate>
					银行名称<input id="searchBankName" name="searchBankName" class="mini-textbox"/>
					<a id="edit" class="mini-button" onclick="payment.search">查询</a>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="95%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="paymentmgr/index/bankList" idField="id" allowResize="true" pageSize="30" multiSelect="false" 
				 showColumnsMenu="true" showFilterRow="false" showPager="false"
				 onrowdblclick="payment.edit" onpreload="MiniCom.onpreload"  onshowrowdetail="payment.showChannel">
				
				<div property="columns">
					<div type="expandcolumn" ></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="cname" headerAlign="center" align="center">银行简称</div>
					<div field="status" type= "comboboxcolumn" headerAlign="center" align="center">银行状态
						<input property="editor" class="mini-combobox" data="Dic.status" />
					</div>
					<div field="paytype" type= "comboboxcolumn" headerAlign="center" align="center" data="Dic.paytype">支付类型
						<input property="editor" class="mini-combobox" data="Dic.paytype" />
					</div>
					<div headerAlign="center" align="center" renderer="payment.loadBlogo">银行logo大</div>
					<div field="slogo" headerAlign="center" align="center" renderer="payment.loadSlogo">银行logo小</div>
					<div field="remark" headerAlign="center" align="center">备注</div>
				</div>
			</div>
			
			<div id="listDtail" style="display:none;">
		        <div id="channelGrid" class="mini-datagrid" style="width:100%;height:150px; float: left;" url="paymentmgr/index/channelList" showPager="false" >
		            <div property="columns">
		            	<div field="id" width="100" headerAlign="center" allowSort="true" align="center">序号</div>                
		                <div field="type" type= "comboboxcolumn" headerAlign="center" allowSort="true" align="center">渠道支付类型
		                	<input property="editor" class="mini-combobox" data="Dic.type" />
		                </div>                
		                <div field="name" headerAlign="center" allowSort="true" align="center">渠道名称</div>                
		                <div field="pc" type= "comboboxcolumn"  headerAlign="center" allowSort="true" align="center">PC
		                	<input property="editor" class="mini-combobox" data="Dic.available" />
		                </div>                
		                <div field="h5" type= "comboboxcolumn" headerAlign="center" allowSort="true" align="center">H5
		                	<input property="editor" class="mini-combobox" data="Dic.available" />
		                </div>                
		                <div field="android" type= "comboboxcolumn" headerAlign="center" allowSort="true" align="center">Android
		                	<input property="editor" class="mini-combobox" data="Dic.available" />
		                </div>                
		                <div field="ios" type= "comboboxcolumn" headerAlign="center" allowSort="true" align="center">Ios
		                	<input property="editor" class="mini-combobox" data="Dic.available" />
		                </div>                
		            </div>
		        </div> 
		        
    		</div>
    		
		</div>
		
    
	</div>
	
	<div id="editWindow" class="mini-window" title="" 
		style="width:1300px; height:800px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showModal="true" allowResize="true" allowDrag="true" >
		
		<div  id="editform">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					<tbody>
						<tr>
						   <table  width="100%">
						       <tr>
						         <input name="url" value="" class="mini-hidden"/>
								 <input name="action" value="" class="mini-hidden" />
								 <input name="id" class="mini-hidden" />
							  </tr>
							  <tr>
							    <td width="10%">银行名称</td>
							    <td width="30%"><input id="editCode" name="name" class="mini-textbox" style="width:200px;"  vtype ="maxLength:50" required="true"/></td>
							    <td width="10%" >银行简称</td>
							    <td width="30%"><input id="editCodeName" name="cname" class="mini-textbox" style="width:200px;" vtype ="maxLength:30" required="true"/></td>
							  </tr>
							  <tr>
							  	 <td>
							    	<img  id ="blogoImg" alt="银行大Logo" width="126px;" height="36px;">
							    </td>
							  	 <td>						
								     <input class="mini-hidden" id="blogo" name="blogo"/>
								     <input type="button" value="从图库选择" onclick="payment.openImage('blogo')"/>								    
								  </td>
								  
							     <td>
							    	<img  id ="slogoImg" alt="银行小Logo" width="126px;" height="36px;">
							    </td>
							  	 <td>						
								     <input class="mini-hidden" id ="slogo" name="slogo"/>
								     <input type="button" value="从图库选择" onclick="payment.openImage('slogo')"/>								    
								  </td>							    
							  </tr>
							  <tr>
							    <td>支付类型</td>
							    <td><input id ="paytype" name="paytype"  class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择"  style="width:200px;"   required="true"/></td>
							    <td>状态</td>
							    <td><input id="status" name="status" class="mini-combobox" emptyText="请选择" valueFromSelect = "true"  nullItemText="请选择" style="width:200px;"   required="true"/></td>
							  </tr>
							  <tr>
							    <td>备注</td>
							    <td><input name="remark" class="mini-textarea" vtype ="maxLength:200" style="width:200px;"/></td>
								  <td>银行编码</td>
								  <td><input name="code" class="mini-textbox"/></td>
							    <td align="center">
								  	 <btn:operate privilege="ADD|UPD">
								  	    <a class="mini-button" onclick="payment.save">保存信息</a>
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
						 					<a  class="mini-button" iconCls="icon-add" plain="true" onclick="Cms.addEditRow('onlineBrankChannelGrid')">新增</a>
						 					</btn:operate>
						 					<btn:operate privilege="UPD">
						 					<a  class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.editRow('onlineBrankChannelGrid')">修改</a>
						 					</btn:operate>
						 					<btn:operate privilege="DEL">
						 					<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="payment.deleteChannel('onlineBrankChannelGrid', 1)">删除</a>
						 					</btn:operate>
						 					<a  class="mini-button" iconCls="icon-reload" plain="true" onclick="Cms.reload('onlineBrankChannelGrid')">刷新</a>
						 					<btn:operate privilege="ADD|UPD">
						 					<a  class="mini-button" iconCls="icon-save" plain="true" onclick="payment.saveChannel('onlineBrankChannelGrid')">保存</a>
						 					</btn:operate>
										</td>
										<td style="float: right;">网银支付渠道 </td>
									</tr>
								</table>
							</div>
							
						   <div showCollapseButton="true">
						   <div title="网银支付渠道 " style="height: 250px">
						   <div id="onlineBrankChannelGrid" class="mini-datagrid" onpreload="MiniCom.onpreload" style="width:100%;height:100%;" 
							 url="paymentmgr/index/channelList" allowResize="true" onrowdblclick="Cms.editRow('onlineBrankChannelGrid')" showPager="false"
							  >
						        <div property="columns">      
						        		<div type="checkcolumn" width="3%"></div>    
						               
						                <div    field="cardtype" headerAlign="center" align="center"  type= "comboboxcolumn">卡类型
							                <input property="editor" class="mini-combobox" required="true" name="cardtype" data="Dic.cardtype"/>
						                </div>
						                <div    field="payChannelMgrId" headerAlign="center" align="center"  type= "comboboxcolumn">网银支付渠道
							                <input property="editor" class="mini-combobox" required="true" name="code" data="Dic.payChannel" onvaluechanged="payment.changePayChannelText"/>
						                </div>
						                <div    field="name" headerAlign="center" align="center" >支付渠道名称(隐藏)
							                <input property="editor" class="mini-textbox" required="true"/>
						                </div>
						                <div    field="code" headerAlign="center" align="center" >支付渠道code(隐藏)
							                <input property="editor" class="mini-textbox" required="true"/>
						                </div>
						                <div    headerAlign="center" align="center" renderer="payment.orderHtml" width="60px;">排序
						                </div>
						        		
						        		<div  headerAlign="center" align="center">使用设置
						        			<div property="columns"  headerAlign="center">
						        			 <div field="available" type="checkboxcolumn"trueValue="1" falseValue="0" width="50px;">是否可用
						        			 </div>
						        			 <div field="pause" type="checkboxcolumn"  trueValue="1" falseValue="0" width="50px;">启用暂停
						        			 </div>
						        			 <div field="begintime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" width="160px;">暂停开始时间
						        			 	<input property="editor" name="begintime" class="mini-datepicker" style="width:160px;" format="yyyy-MM-dd HH:mm:ss"/>
						        			 </div>
						        			 <div field="endtime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" width="160px;">暂停结束时间
						        			 	<input property="editor" name="endtime" class="mini-datepicker" style="width:160px;" format="yyyy-MM-dd HH:mm:ss"/>
						        			 </div>
						        			</div>
						        			
						        		</div>
						        		<div  header="使用平台" headerAlign="center">
						        			<div property="columns"  headerAlign="center">
						        			 <div field="pc" type="checkboxcolumn" trueValue="1" falseValue="0" width="40px;">PC</div>
						        			 <div field="h5" type="checkboxcolumn" trueValue="1" falseValue="0" width="40px;">H5</div>
						        			 <div field="android" type="checkboxcolumn" trueValue="1" falseValue="0" width="40px;">Android</div>
						        			 <div field="ios" type="checkboxcolumn" trueValue="1" falseValue="0" width="40px;">IOS</div>
						        			</div>
						        		</div>
						        		<div field="appInvokeType" type="checkboxcolumn" trueValue="1" falseValue="0" width="60px;">app调用SDK</div>
				                </div>
					    	</div>
						   </div>
						   </div>
						   
						   </div>
						</tr>
						
						
						<tr>
						    <div id="thirdWindow">
							<div  class="mini-toolbar">
								<table style="width:100%;">
									<tr>
										<td  style="white-space:nowrap;">
										    <btn:operate privilege="ADD">
						 					<a  class="mini-button" iconCls="icon-add" plain="true" onclick="Cms.addEditRow('quickPayChannelGrid')">新增</a>
						 					</btn:operate>
						 					<btn:operate privilege="UPD">
						 					<a  class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.editRow('quickPayChannelGrid')">修改</a>
						 					</btn:operate>
						 					<btn:operate privilege="DEL">
						 					<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="payment.deleteChannel('quickPayChannelGrid', 2)">删除</a>
						 					</btn:operate>
						 					<a  class="mini-button" iconCls="icon-reload" plain="true" onclick="Cms.reload('quickPayChannelGrid')">刷新</a>
						 					<btn:operate privilege="ADD|UPD">
						 					<a  class="mini-button" iconCls="icon-save" plain="true"  onclick="payment.saveChannel('quickPayChannelGrid')">保存</a>
						 					</btn:operate>
										</td>
										<td style="float: right;">快捷支付渠道 </td>
									</tr>
								</table>
							</div>
						   <div showCollapseButton="true">
						   <div title="快捷支付渠道 " style="height: 250px">
					    	
					    	<div id="quickPayChannelGrid" class="mini-datagrid" onpreload="MiniCom.onpreload" style="width:100%;height:100%;" 
							 url="paymentmgr/index/channelList" allowResize="true" onrowdblclick="Cms.editRow('quickPayChannelGrid')" showPager="false">
						        
						        <div property="columns">      
						        		<div type="checkcolumn" width="3%"></div>    
						                
						                <div    field="cardtype" headerAlign="center" align="center"  type= "comboboxcolumn">卡类型
							                <input property="editor" class="mini-combobox" required="true" name="cardtype" data="Dic.cardtype"/>
						                </div>
						                <div    field="payChannelMgrId" headerAlign="center" align="center" type="comboboxcolumn">快捷支付渠道
							                <input property="editor" vtype ="maxLength:10" class="mini-combobox" required="true" data="Dic.payChannel" onvaluechanged="payment.changeQuickChannelText"/>
						                </div>
						                <div field="name" headerAlign="center" align="center" >快捷支付渠道名称(隐藏)
							                <input property="editor" class="mini-textbox" required="true"/>
						                </div>
						                <div field="code" headerAlign="center" align="center" >快捷支付渠道code(隐藏)
							                <input property="editor" class="mini-textbox" required="true"/>
						                </div>
						                <div  headerAlign="center" align="center" renderer="payment.orderHtml" width="60px;">排序
						                </div>
						        		<div  headerAlign="center" align="center">使用设置
						        			<div property="columns"  headerAlign="center">
						        			 <div field="available" type="checkboxcolumn" trueValue="1" falseValue="0" width="50px;">是否可用
						        			 </div>
						        			 <div field="pause" type="checkboxcolumn"  trueValue="1" falseValue="0" width="50px;">启用暂停
						        			 </div>
						        			 <div field="begintime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" width="160px;">暂停开始时间
						        			 	<input property="editor" name="begintime" class="mini-datepicker" style="width:160px;" format="yyyy-MM-dd HH:mm:ss"/>
						        			 </div>
						        			 <div field="endtime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" width="160px;">暂停结束时间
						        			 	<input property="editor" name="endtime" class="mini-datepicker" style="width:160px;" format="yyyy-MM-dd HH:mm:ss"/>
						        			 </div>
						        			</div>
						        			
						        		</div>
						        		<div  header="使用平台" headerAlign="center">
						        			<div property="columns"  headerAlign="center">
						        			 <div field="pc" type="checkboxcolumn" trueValue="1" falseValue="0" width="40px;">PC</div>
						        			 <div field="h5" type="checkboxcolumn" trueValue="1" falseValue="0" width="40px;">H5</div>
						        			 <div field="android" type="checkboxcolumn" trueValue="1" falseValue="0" width="40px;">Android</div>
						        			 <div field="ios" type="checkboxcolumn" trueValue="1" falseValue="0" width="40px;">IOS</div>
						        			</div>
						        		</div>
						        		<div field="appInvokeType" type="checkboxcolumn" trueValue="1" falseValue="0" width="60px;">app调用SDK</div>
				                </div>
						        
					    	</div>
					    	
						   </div>
						   </div>
						   
						   </div>
						</tr>
						
						<tr>
						    <div id="thirdPartyWindow">
							<div  class="mini-toolbar">
								<table style="width:100%;">
									<tr>
										<td  style="white-space:nowrap;">
										    <btn:operate privilege="ADD">
						 					<a  class="mini-button" iconCls="icon-add" plain="true" onclick="Cms.addEditRow('thirdPartyPayChannelGrid')">新增</a>
						 					</btn:operate>
						 					<btn:operate privilege="UPD">
						 					<a  class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.editRow('thirdPartyPayChannelGrid')">修改</a>
						 					</btn:operate>
						 					<btn:operate privilege="DEL">
						 					<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="payment.deleteChannel('thirdPartyPayChannelGrid', 3)">删除</a>
						 					</btn:operate>
						 					<a  class="mini-button" iconCls="icon-reload" plain="true" onclick="Cms.reload('thirdPartyPayChannelGrid')">刷新</a>
						 					<btn:operate privilege="ADD|UPD">
						 					<a  class="mini-button" iconCls="icon-save" plain="true"  onclick="payment.saveChannel('thirdPartyPayChannelGrid')">保存</a>
						 					</btn:operate>
										</td>
										<td style="float: right;">第三方支付渠道</td>
									</tr>
								</table>
							</div>
						   <div showCollapseButton="true">
						   <div title="第三方支付渠道 " style="height: 280px">
					    	
					    	<div id="thirdPartyPayChannelGrid" class="mini-datagrid" onpreload="MiniCom.onpreload" style="width:100%;height:100%;" 
							 url="paymentmgr/index/channelList" allowResize="true" onrowdblclick="Cms.editRow('thirdPartyPayChannelGrid')" showPager="false"   multiSelect="true">
						        
						        <div property="columns">      
						        		<div type="checkcolumn" width="3%"></div>    
						                
						                <div    field="cardtype" headerAlign="center" align="center"  type= "comboboxcolumn">卡类型
							                <input property="editor" class="mini-combobox" required="true" name="cardtype" data="Dic.cardtype"/>
						                </div>
						                <div    field="payChannelMgrId" headerAlign="center" align="center" type="comboboxcolumn">快捷支付渠道
							                <input property="editor" vtype ="maxLength:10" class="mini-combobox" required="true" data="Dic.payChannel" onvaluechanged="payment.changeThirdPartyPayChannelText"/>
						                </div>
						                <div field="name" headerAlign="center" align="center" >快捷支付渠道名称(隐藏)
							                <input property="editor" class="mini-textbox" required="true"/>
						                </div>
						                <div field="code" headerAlign="center" align="center" >快捷支付渠道code(隐藏)
							                <input property="editor" class="mini-textbox" required="true"/>
						                </div>
						                
						                 <div headerAlign="center" align="center" renderer="payment.orderHtml" width="50px;">排序
						                </div>
						        		<div headerAlign="center" align="center">使用设置
						        			<div property="columns"  headerAlign="center">
						        			 <div field="available" type="checkboxcolumn" trueValue="1" falseValue="0" width="50px;">是否可用
						        			 </div>
						        			 <div field="pause" type="checkboxcolumn"  trueValue="1" falseValue="0" width="50px;">启用暂停
						        			 </div>
						        			 <div field="begintime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" width="160px;">暂停开始时间
						        			 	<input property="editor" name="begintime" class="mini-datepicker" style="width:160px;" format="yyyy-MM-dd HH:mm:ss"/>
						        			 </div>
						        			 <div field="endtime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss" width="160px;">暂停结束时间
						        			 	<input property="editor" name="endtime" class="mini-datepicker" style="width:1680px;" format="yyyy-MM-dd HH:mm:ss"/>
						        			 </div>
						        			</div>
						        			
						        		</div>
						        		<div  header="使用平台" headerAlign="center">
						        			<div property="columns"  headerAlign="center">
						        			 <div field="pc" type="checkboxcolumn" trueValue="1" falseValue="0" width="40px;">PC</div>
						        			 <div field="h5" type="checkboxcolumn" trueValue="1" falseValue="0" width="40px;">H5</div>
												<div field="wechat" type="checkboxcolumn" trueValue="1" falseValue="0" width="40px;">公众号</div>
						        			 <div field="android" type="checkboxcolumn" trueValue="1" falseValue="0" width="40px;">Android</div>
						        			 <div field="ios" type="checkboxcolumn" trueValue="1" falseValue="0" width="40px;">IOS</div>
						        			</div>
						        		</div>
						        		<div field="appInvokeType" type="checkboxcolumn" trueValue="1" falseValue="0" width="60px;">app调用SDK</div>
				                </div>
						        
					    	</div>
					    	
						   </div>
						   </div>
						   
						   </div>
						</tr>
						
						<tr>
						    <div id="fourthWindow">
							<div  class="mini-toolbar">
								<table style="width:100%;">
									<tr>
										<td  style="white-space:nowrap;">
										    <btn:operate privilege="ADD">
						 					<a  class="mini-button" iconCls="icon-add" plain="true" onclick="Cms.addEditRow('bankLimitGrid')">新增</a>
						 					</btn:operate>
						 					<btn:operate privilege="UPD">
						 					<a  class="mini-button" iconCls="icon-edit" plain="true" onclick="Cms.editRow('bankLimitGrid')">修改</a>
						 					</btn:operate>
						 					<btn:operate privilege="DEL">
						 					<a  class="mini-button" iconCls="icon-remove" plain="true" onclick="payment.deleteBankLimit">删除</a>
						 					</btn:operate>
						 					<a  class="mini-button" iconCls="icon-reload" plain="true" onclick="Cms.reload('bankLimitGrid')">刷新</a>
						 					<btn:operate privilege="ADD|UPD">
						 					<a  class="mini-button" iconCls="icon-save" plain="true"  onclick="payment.saveBankLimit('bankLimitGrid')">保存</a>
						 					</btn:operate>
										</td>
										<td style="float: right;">付款金额超限说明</td>
									</tr>
								</table>
							</div>
						   <div showCollapseButton="true">
						   <div title="付款金额超限说明" style="height: 100px">
					    	
					    	<div id="bankLimitGrid" class="mini-datagrid" onpreload="MiniCom.onpreload" style="width:100%;height:100%;" 
							 url="paymentmgr/index/bankLimitList" allowResize="true" onrowdblclick="Cms.editRow('bankLimitGrid')" showPager="false"   multiSelect="true">
						        
						        <div property="columns">      
						        		<div type="checkcolumn" width="3%"></div>    
						                
						                <div    field="limittime" headerAlign="center" align="center" >单笔限额(元)
							                <input property="editor" vtype ="maxLength:10" class="mini-textbox" required="true"/>
						                </div>
						                <div    field="limitday" headerAlign="center" align="center" >每日限额(元)
							                <input property="editor" vtype ="maxLength:10" class="mini-textbox" required="true"/>
						                </div>
						                <div    field="limitmonth" headerAlign="center" align="center" >每月限额(元)
							                <input property="editor" vtype ="maxLength:10" class="mini-textbox" required="true"/>
						                </div>
						                <div    field="cardType" headerAlign="center" align="center"  type= "comboboxcolumn">卡类型
							                <input property="editor" class="mini-combobox" name="cardType" data="Dic.limitCardType"/>
						                </div>
						                <div    field="condition" headerAlign="center" align="center" >需要满足的条件
							                <input property="editor" vtype ="maxLength:100" class="mini-textbox" style="width: 300px;"/>
						                </div>
						                <div    field="remark" headerAlign="center" align="center" >备注
							                <input property="editor" vtype ="maxLength:100" class="mini-textbox" style="width: 300px;"/>
						                </div>
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
		
  </body>
<script src="<%=basePath%>resources/js/public/common.js?version=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/paymentmgr/index.js?version=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
