<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>冠亚军对阵</title>
  </head>
  <body>
  <div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
  	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;<a class="mini-button" onclick="lottery_gyj.draw">开奖</a>
				</td>
			</tr>
			<tr>
				<td width="10%" nowrap="nowrap">
					彩期<input id="lotteryIssue" name="lotteryIssue" class="mini-combobox"  style="width:150px;"
							 emptyText="请选择" oncloseclick="Cms.onCloseClick" showClose="true"/>
					销售状态<input id="matchStatus" name="matchStatus" class="mini-combobox"  style="width:150px;"
							   emptyText="请选择" oncloseclick="Cms.onCloseClick" showClose="true"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
			        <a class="mini-button" onclick="lottery_gyj.search">查询</a>
				</td>
			</tr>
		</table>
	</div>
	</div>
	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:87%;" 
		 url="lotterymgr/gyj/page" idField="id" allowResize="true" multiSelect="true"
		 showColumnsMenu="true" showFilterRow="false" pageSize="50" onrowdblclick="Cms.editRow('datagrid')">
		<div property="columns">
			<div type="checkcolumn" width="5%"></div>
			<div type="indexcolumn" width="5%" align="center" headerAlign="center">序号</div>
			<div field="matchName" headerAlign="center" align="center">赛事名</div>
			<div field="homeName" headerAlign="center" align="center">球队</div>
			<div field="systemCode" headerAlign="center" align="center">赛事编号</div>
			<div name="matchStatus" field="matchStatus" type="comboboxcolumn" headerAlign="center" align="center">销售状态
				<input property="editor" class="mini-combobox" data="Dic.matchStatus"/>
			</div>
			<div name="oddsWin" field="oddsWin" headerAlign="center" align="center">最新SP
				<input property="editor" class="mini-textbox" vtype="float"/>
			</div>
			<div name="winProb" field="winProb" headerAlign="center" align="center">最新概率
				<input property="editor" class="mini-textbox"/>
			</div>
			<div field="modifyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>
			<div renderer="lottery_gyj.onActionRenderer" headerAlign="center" align="center">操作</div>

		</div>
	</div>
  </body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/lotterymgr/lottery_gyj.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
</html>
