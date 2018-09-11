<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>返佣设置</title>
  </head>
  <body>
    <div style="width:100%;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="mini-button" iconCls="icon-add" onclick="Current.addRow" plain="true" tooltip="增加">增加</a>
                        <a class="mini-button" iconCls="icon-remove" onclick="Current.removeRow" plain="true">删除</a>
                        <span class="separator"></span>
                        <a class="mini-button" iconCls="icon-save" onclick="Current.saveData" plain="true">保存</a>            
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div><input type="hidden" id="agentId" value="${agentId}"/></div>
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:200px;" 
        url="agent/agentRebateConfigs" idField="id" 
        allowResize="true" showPager="false"
        allowCellEdit="true" allowCellSelect="true" multiSelect="true" 
        editNextOnEnterKey="true"  editNextRowCell="true" oncellvalidation="Current.onCellValidation">
        <div property="columns">
            <div type="indexcolumn"></div>
            <div field="directMinMoney" name="directMinMoney" headerAlign="center" allowSort="true" width="150" numberFormat="¥#,0.00">直属总额最小值
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
            </div>
            <div field="directMaxMoney" headerAlign="center" allowSort="true" width="150" numberFormat="¥#,0.00">直属总额最大值
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
            </div>
            <div field="directRatio" headerAlign="center" allowSort="true" width="150" numberFormat="p">直属返佣比例
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
            </div>
            <div field="agentMinMoney" headerAlign="center" allowSort="true" width="150" numberFormat="¥#,0.00">代理总额最小值
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
            </div>
            <div field="agentMaxMoney" headerAlign="center" allowSort="true" width="150" numberFormat="¥#,0.00">代理总额最大值
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
            </div>
            <div field="agentRatio" headerAlign="center" allowSort="true" width="150" numberFormat="p">代理返佣比例
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
            </div>
        </div>
    </div>
  
	<div>
		<h1>变更日志</h1>
		<c:forEach items="${history}" var="item">
			<p><fmt:formatDate type="both"  value="${item.configCreateTime}" />变更为:</p>
			<table border="1" style="border-collapse:collapse;" align="center">
		  		<thead>
		  			<tr>
		  				<th>直属消费区间</th>
		  				<th>直属返佣比率</th>
		  				<th>代理消费区间</th>
		  				<th>代理返佣比率</th>
		  			</tr>
		  		</thead>
		  		<tbody>
		  			<c:forEach items="${item.configs}" var="config">
			  			<tr>
			  				<!--下级用户消费区间  -->
			  				
			  				<td align="center" style="width: 300px">
			  					<c:if test="${config.directMinMoney!=null  or config.directMaxMoney!=null}">
				  					<c:if test="${config.directMinMoney!=null }">
					  					${config.directMinMoney }&lt;= 
					  				</c:if>
					  				x
					  				<c:if test="${config.directMaxMoney!=null }">
					  				&lt;${config.directMaxMoney }
					  				</c:if>
					  			</c:if>
			  				 </td>
			  				<td align="center" style="width: 100px">
				  				<c:if test="${config.directRatio !=null}">
					  					${config.directRatio }
					  			</c:if>
			  				</td>
			  		
			  				<!-- 下级代理的用户消费区间 -->
			  				<td align="center" style="width: 300px">
			  					<c:if test="${config.agentMinMoney!=null or config.agentMaxMoney!=null}">
				  					<c:if test="${config.agentMinMoney!=null }">
					  					${config.agentMinMoney }&lt;= 
					  				</c:if>
					  				x
					  				<c:if test="${config.agentMaxMoney!=null }">
					  				&lt;${config.agentMaxMoney }
					  				</c:if>
			  					 </c:if>
			  				 </td>
			  				<td align="center" style="width: 100px">
				  				<c:if test="${config.agentRatio !=null}">
				  					${config.agentRatio }
				  				</c:if>
			  				</td> 
			  			</tr>
			  		</c:forEach>	
		  		</tbody>
			</table>
			<div style="height:30px"></div>
		</c:forEach>
	</div>

</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/agentSet.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
