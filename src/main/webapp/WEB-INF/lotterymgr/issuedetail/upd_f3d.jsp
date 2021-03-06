<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>福彩3D</title>
  </head>
  <style>
</style>
  <body>
	    <form id="issueForm">
        <%@ include file="head.jsp"%>
		<input id = "drawCodeTest" name="drawCodeTest" class="mini-hidden" />
        <fieldset style="border:solid 0px #aaa;padding:3px;">
            <legend></legend>
            <div style="padding:5px;">
            <table>
            <tr>
               <td>开奖号码：</td>
               <td><input name="num0" class="mini-textbox" style="width:40px;" inputStyle="color:red;" vtype ="int" maxlength="1"/></td>
               <td><input name="num1" class="mini-textbox" style="width:40px;" inputStyle="color:red;" vtype ="int" maxlength="1"/></td>
               <td><input name="num2" class="mini-textbox" style="width:40px;" inputStyle="color:red;" vtype ="int" maxlength="1"/></td>
            </tr>
            <tr>
               <td>试机号码：</td>
               <td><input name="test0" class="mini-textbox" style="width:40px;" inputStyle="color:red;" vtype ="int" maxlength="1"/></td>
               <td><input name="test1" class="mini-textbox" style="width:40px;" inputStyle="color:red;" vtype ="int" maxlength="1"/></td>
               <td><input name="test2" class="mini-textbox" style="width:40px;" inputStyle="color:red;" vtype ="int" maxlength="1"/></td>
            </tr>
            </table>            
            </div>
        </fieldset>
        <fieldset style="border:solid 0px #aaa; padding:3px;">
            <legend >中奖明细：</legend>
            <div style="padding:5px;">
             <table width="30%;" border="1" bordercolor="#aaa" style="border-collapse:collapse;text-align: center;">
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
            <td><input name="money0" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money1" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
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
	Current.name = ["直选","组三","组六"]
	Current.money = ["1040","346","173"]
    var codeNum = 3;
    var drawDetailNum =3;
    var lotteryCode ="PL3";
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
					var code = row.drawCode.split("|");
					for (i = 0; i < code.length; i++) {
					   row["num"+i] = code[i];
					}
				}
				if(row.drawCodeTest){
					var code = row.drawCodeTest.split("|");
					for (i = 0; i < code.length; i++) {
					   row["test"+i] = code[i];
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
	   if(!drawCode("drawCode","num")){
		   return;
	   }
	   //试机号码
	   if(!drawCode("drawCodeTest","test")){
		   return;
	   }
	   var drawDetail =Current.joinDrawDetail(drawDetailNum);
	   mini.get("drawDetail").setValue(drawDetail);
	   Current.submit(form);
	}
	
	function drawCode(attr, name) {
		var code = "";
		var isNull = true;
		for (i = 0; i < codeNum; i++) {
			var num = $("#issueForm").find("input[name=" + name + i + "]")
					.val();
			if (num && num != "") {
				isNull = false;
			}
			if (i > 0) {
				code += "|";
			}
			code += num;
		}
		if (isNull) {
			code = "";
		} else {
			if (!Draw.check(lotteryCode, code)) {
				return false;
			}
		}
		mini.get(attr).setValue(code);
		return true;
	}
</script>
</html>
