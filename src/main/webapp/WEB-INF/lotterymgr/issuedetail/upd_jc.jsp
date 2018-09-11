<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>竞彩</title>
  </head>
  <style>
</style>
  <body>
	    <form id="issueForm">
        <%@ include file="head.jsp"%>
        <%@ include file="foot.jsp"%> 
    </form>
      
  </body>
<script src="<%=basePath%>resources/js/public/common.js?version=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/lotterymgr/drawcode.js?version=<%=version%>" type="text/javascript"></script>
<script type="text/javascript">
    var lotteryCode ="JC";
    mini.parse();
    var form = new mini.Form("issueForm");
    var operate =document.getElementById("operateButton");
	operate.style.display="none";
	
    function init(succsssBack){
	    ComReq.dictionary("0401,0402,0403", function(result){
	        Dic.issueType = result["0401"];
			Dic.saleStatus = result["0402"];
			Dic.yesNo = result["0403"];
			mini.get("saleStatus_edit").setData(Dic.saleStatus);
			mini.get("currentIssue").setData(Dic.issueType);
			mini.get("issueLastest").setData(Dic.yesNo);
			succsssBack();
	    });
    }
    function setAddData(data){
       init(function(){
       		form.setData(data);
       });
       
    }
    function initData(id){
       init(function(){
       		setData(id);
       });
    }
	function setData(id) {
		$.get("lotterymgr/issue/detail?id=" + id, function(result) {
			if (Code.success == result.errorCode) {
				var row = result.data;
				row.action = "put";
				row.url = "lotterymgr/issue";
				form.setData(row);
			} else {
				mini.alert(result.msg);
			}
		});
	}
	function dosubmit(){
		Current.submit(form);
	}
    
</script>
</html>
