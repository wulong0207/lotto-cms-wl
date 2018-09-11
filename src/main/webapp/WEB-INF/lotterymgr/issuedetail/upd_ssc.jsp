<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>时时彩</title>
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
               <td><input name="num0" class="mini-textbox" style="width:40px;" inputStyle="color:red;" vtype ="int" maxlength="1"/></td>
               <td><input name="num1" class="mini-textbox" style="width:40px;" inputStyle="color:red;" vtype ="int" maxlength="1"/></td>
               <td><input name="num2" class="mini-textbox" style="width:40px;" inputStyle="color:red;" vtype ="int" maxlength="1"/></td>
               <td><input name="num3" class="mini-textbox" style="width:40px;" inputStyle="color:red;" vtype ="int" maxlength="1"/></td>
               <td><input name="num4" class="mini-textbox" style="width:40px;" inputStyle="color:red;" vtype ="int" maxlength="1"/></td>
            </tr>
            </table>            
            </div>
        </fieldset>
        <fieldset style="border:solid 0px #aaa; padding:3px;">
            <legend >中奖明细：</legend>
            <div style="padding:5px;">
             <table width="100%;" border="1" bordercolor="#aaa" style="border-collapse:collapse;text-align: center;">
            <tr>
            <td width="7%">奖项</td>
            <td width="7%"><input name="name0" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="7%"><input name="name1" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="7%"><input name="name2" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="7%"><input name="name3" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="7%"><input name="name4" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="7%"><input name="name5" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="7%"><input name="name6" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="7%"><input name="name7" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="7%"><input name="name8" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            </tr>
            <tr>
            <td>金额</td>
            <td><input name="money0" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money1" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money2" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money3" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money4" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money5" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money6" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money7" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money8" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            </tr>
             <tr>
            <td>加奖金额</td>
            <td><input name="moneyAdd0" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd1" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd2" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd3" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd4" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd5" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd6" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd7" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd8" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
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


	Current.name = ["五星直选","五星通选","三星直选","三星组三","三星组六","二星直选","二星组选","一星","大小单双"];
	Current.money = ["100000","20440","1000","320","160","100","50","10","4"];

    var codeNum = 5;
    var drawDetailNum = 9;
    var lotteryCode ="SSC";
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
				Current.analysisDrawDetail_2(row);
				if(row.drawCode){
					var code = row.drawCode.split("|");
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
	          code+="|";
	       }
	       code+=num;
	   }
	   if(isNull){
	      code="";
	   }else{
	      /* if(!Draw.check(lotteryCode, code)){
	          return;
	      } */
	   }
	   mini.get("drawCode").setValue(code);
	   var drawDetail =Current.joinDrawDetail_2(drawDetailNum);
	   mini.get("drawDetail").setValue(drawDetail);
	   Current.submit(form);
	}
    
</script>
</html>
