<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>大乐透</title>
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
               <td>+</td>
               <td><input name="num5" class="mini-textbox" style="width:20px;" inputStyle="color:blue;" vtype ="int" maxlength="2"/></td>
               <td><input name="num6" class="mini-textbox" style="width:20px;" inputStyle="color:blue;" vtype ="int" maxlength="2"/></td>
            </tr>
            </table>
            </div>
        </fieldset>
        <fieldset style="border:solid 0px #aaa; padding:3px;">
            <legend >中奖明细：</legend>
            <div style="padding:5px;">
             <table width="100%;" border="1" bordercolor="#aaa" style="border-collapse:collapse;text-align: center;">
            <tr>
            <td width="13%">奖项</td>
            <td width="13%"><input name="name0" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="13%"><input name="name1" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="13%"><input name="name2" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="13%"><input name="name3" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="13%"><input name="name4" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="13%"><input name="name5" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            
            </tr>
            <tr>
            <td>基本注数</td>
            <td><input name="stake0" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="stake1" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="stake2" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="stake3" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="stake4" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="stake5" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            </tr>
             <tr>
            <td>基本金额</td>
            <td><input name="money0" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="money1" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="money2" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="money3" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money4" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money5" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            </tr>
            <tr>
            <td>追加注数</td>
            <td><input name="stakeAdd0" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="stakeAdd1" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="stakeAdd2" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="stakeAdd3" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="stakeAdd4" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="stakeAdd5" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            </tr>
            <tr>
            <td>追加金额</td>
            <td><input name="moneyAdd0" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="moneyAdd1" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="moneyAdd2" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10"/></td>
            <td><input name="moneyAdd3" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="moneyAdd4" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="moneyAdd5" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
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
	Current.name = ["一等奖","二等奖","三等奖","四等奖","五等奖","六等奖"];
	Current.money = ["","","","200","10","5"];
    var codeNum = 7;
    var drawDetailNum = 6;
    var lotteryCode = "DLT";
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
    	}
        data["moneyAdd3"]=100;
        data["moneyAdd4"]=5;
        data["moneyAdd5"]=0;
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
				Current.analysisDrawDetail_dlt(row);
				
				if(row.drawCode){
					var code = row.drawCode.split("|");
					var left = code[0].split(",");
					for (i = 0; i < left.length; i++) {
					   row["num"+i] = left[i];
					}
					var right = code[1].split(",");
					row["num5"] = right[0];
					row["num6"] = right[1];
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
	       if(i>0&&i!=5){
	          code+=",";
	       }else if(i==5){
	          code +="|";
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
	   var drawDetail =Current.joinDrawDetail_dlt(drawDetailNum);
	   mini.get("drawDetail").setValue(drawDetail);
	   Current.submit(form);
	}
</script>
</html>
