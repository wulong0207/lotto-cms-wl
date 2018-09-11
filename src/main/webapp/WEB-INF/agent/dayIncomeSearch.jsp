<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>月度详情</title>
  </head>
  <body>	
	<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
	
		<div size="90%" showCollapseButton="true" style="border:0;">
			<div id="datagrid" class="mini-datagrid"  allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
				 url="agent/dayIncomeList" idField="agentId" allowResize="true" pageSize="30" multiSelect="true" 
				 showColumnsMenu="true" showFilterRow="false" allowMoveColumn="true"  onpreload="MiniCom.onpreload">
				
				<div property="columns">
					<div field="dt" headerAlign="center" align="center">月份</div>
					<div field="directBuyCount" headerAlign="center" align="center">当日业务投注人数</div>
					<div field="directBuyMoney" headerAlign="center" align="center">当日业务投注金额</div>
					<div field="todayDirectIncome" headerAlign="center" align="center">当日业务返佣金额</div>
					<div field="agentBuyCount" headerAlign="center" align="center" >当日代理投注人数</div>
					<div field="agentBuyMoney" headerAlign="center" align="center" >当日代理投注金额</div>
					<div field="todayAgentIncome" headerAlign="center" align="center" >当日代理返佣金额</div>
					<div field="todayIncome" headerAlign="center" align="center" >当日总返佣金额</div>
				</div>
			</div>
		</div>
	</div>
	
	
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/dayIncomeSearch.js?vsersion=<%=version%>" type="text/javascript"></script>
<script type="text/javascript">
    mini.parse();
    function initData(id,mt) {
        Current.init(id,mt);
    }
</script>
</html>
