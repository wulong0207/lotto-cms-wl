<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>15选5</title>
  </head>
  <style>
</style>
  <body>
	    <form id="issueForm">
        <%@ include file="head.jsp"%>
		
		
        <fieldset style="border:solid 0px #aaa;padding:3px;">
            <legend >开奖号码：</legend>
            <div style="padding:5px;">
            <table>
            <tr>
               <td><input name="num0" class="mini-textbox" style="width:20px;" inputStyle="color:red;" vtype ="int" maxlength="2"/></td>
               <td><input name="num1" class="mini-textbox" style="width:20px;" inputStyle="color:red;" vtype ="int" maxlength="2"/></td>
               <td><input name="num2" class="mini-textbox" style="width:20px;" inputStyle="color:red;" vtype ="int" maxlength="2"/></td>
               <td><input name="num3" class="mini-textbox" style="width:20px;" inputStyle="color:red;" vtype ="int" maxlength="2"/></td>
               <td><input name="num4" class="mini-textbox" style="width:20px;" inputStyle="color:red;" vtype ="int" maxlength="2"/></td>
            </tr>
            </table>            
            </div>
        </fieldset>
        <%--
        15选5当期奖金设为两个奖等，一等奖和特别奖为高奖等，二等奖为低奖等，一等奖的特别情形设为特别奖。高奖等采用浮动设奖，低奖等采用固定设奖。
        当期奖金减去当期低奖等奖金为当期高奖等奖金。当期奖金设奖如下：特别奖：奖金总额为当期高奖等奖金的10%；一等奖：奖金总额为当期高奖等奖金的90%；
        二等奖：单注奖金额固定为10元。
         --%>
        <fieldset style="border:solid 0px #aaa; padding:3px;">
            <legend >中奖明细：</legend>
            <div style="padding:5px;">
             <table width="100%;" border="1" bordercolor="#aaa" style="border-collapse:collapse;text-align: center;">
            <tr>
            <td width="13%">奖项</td>
            <td width="13%"><input name="name0" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="13%"><input name="name1" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="13%"><input name="name2" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            </tr>
            <tr>
            <td>注数</td>
            <td><input name="stake0" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="stake1" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="stake2" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            </tr>
             <tr>
            <td>金额</td>
            <td><input name="money0" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="money1" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="money2" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            </tr>
            <tr>
            <td>加奖金额</td>
            <td><input name="moneyAdd0" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd1" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd2" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            </tr>
            </table>            
            </div>
        </fieldset>
        <%@ include file="foot.jsp"%> 
    </form>
      
  </body>
<script src="<%=basePath%>resources/js/public/common.js?version=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/lotterymgr/drawcode.js?version=<%=version%>" type="text/javascript"></script>
<script type="text/javascript">
	Current.name = ["特别奖","一等奖","二等奖"];
	Current.money = ["0","0","0"];
    var codeNum = 5;
    var drawDetailNum = 3;
    var lotteryCode ="15X5";
    mini.parse();
    var form = new mini.Form("issueForm");
    
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
    	for (i = 0; i < Current.name.length; i++) {
  		   data["name"+i]=(Current.name)[i];
  		   data["money"+i]=(Current.money)[i];
  		   data["moneyAdd"+i]= 0;
  		}
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
				//解析中奖详情
				Current.analysisDrawDetail(row);
				if(row.drawCode){
					var code = row.drawCode.split(",");
					for (i = 0; i < code.length; i++) {
					   row["num"+i] = code[i];
					}
				}

				form.setData(row);
			} else {
				mini.alert(result.msg);
			}
		});
	}
	function dosubmit(){
	   //开奖号码
	   var code ="";
	   var isNull = true;
	   for (i = 0; i < codeNum; i++) {
	       var num= $("#issueForm").find("input[name=num"+i+"]").val();
	       if(num && num!=""){
	          isNull = false;
	       }
	       if(i>0){
	          code+=",";
	       }
	       code+=num;
	   }
	   if(isNull){
	      code="";
	   }else{
	      if(!Draw.check(lotteryCode, code)){
	          return;
	      }
	   }
	   mini.get("drawCode").setValue(code);
	   var drawDetail =Current.joinDrawDetail(drawDetailNum);
	   mini.get("drawDetail").setValue(drawDetail);
	   Current.submit(form);
	}
    
</script>
</html>
