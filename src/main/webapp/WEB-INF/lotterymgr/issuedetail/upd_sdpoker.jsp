<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>山东快乐扑克</title>
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
               <td colspan="3">
               <div id="rblFlower" class="mini-radiobuttonlist" repeatDirection="vertical" repeatLayout="table"
                data="[{id:'♠',text:'♠'},{id:'♥',text:'♥'},{id:'♣',text:'♣'},{id:'♦',text:'♦'}]">
			  </div> 
               </td>
            </tr>
            <tr>
               <td><input id="num0" name="num0" class="mini-textbox" style="width:40px;" inputStyle="color:red;"  maxlength="3" onValueChanged="change('num0')"/></td>
               <td><input id="num1" name="num1" class="mini-textbox" style="width:40px;" inputStyle="color:red;"  maxlength="3" onValueChanged="change('num1')"/></td>
               <td><input id="num2" name="num2" class="mini-textbox" style="width:40px;" inputStyle="color:red;"  maxlength="3" onValueChanged="change('num2')"/></td>
            </tr>
            </table>            
            </div>
        </fieldset>
        <fieldset style="border:solid 0px #aaa; padding:3px;">
            <legend >中奖明细：</legend>
            <div style="padding:5px;">
             <table width="100%;" border="1" bordercolor="#aaa" style="border-collapse:collapse;text-align: center;">
            <tr>
            <td width="10%">奖项</td>
            <td width="10%"><input name="name0" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="10%"><input name="name1" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="10%"><input name="name2" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="10%"><input name="name3" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="10%"><input name="name4" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="10%"><input name="name5" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="10%"><input name="name6" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="10%"><input name="name7" class="mini-textbox" style="width:100%;" enabled="false"/></td>
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
            </tr>
            <tr>
            <td width="10%">奖项</td>
            <td width="10%"><input name="name8" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="10%"><input name="name9" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="10%"><input name="name10" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="10%"><input name="name11" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="10%"><input name="name12" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="10%"><input name="name13" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="10%"><input name="name14" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            <td width="10%"><input name="name15" class="mini-textbox" style="width:100%;" enabled="false"/></td>
            </tr>
             <tr>
            <td>金额</td>
            <td><input name="money8" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money9" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money10" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money11" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money12" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money13" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money14" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            <td><input name="money15" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" enabled="false"/></td>
            </tr>
             <tr>
            <td>加奖金额</td>
            <td><input name="moneyAdd8" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd9" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd10" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd11" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd12" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd13" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd14" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
            <td><input name="moneyAdd15" class="mini-textbox" style="width:100%;" vtype ="int;maxLength:10" /></td>
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
	Current.name = ["同花包选",
	                "同花单选",
	                "同花顺包选",
	                "同花顺单选",
	                "顺子包选",
	                "顺子单选",
	                "豹子包选",
	                "豹子单选",
	                "对子包选",
	                "对子单选",
	                "任选一",
	                "任选二",
	                "任选三",
	                "任选四",
	                "任选五",
	                "任选六"];
	Current.money = ["22",
	                 "90",
	                 "535",
	                 "2150",
	                 "33",
	                 "400",
	                 "500",
	                 "6400",
	                 "7",
	                 "88",
	                 "5",
	                 "33",
	                 "116",
	                 "46",
	                 "22",
	                 "12"];
    var codeNum = 3;
    var drawDetailNum =16;
    var lotteryCode ="SDPOKER";
    mini.parse();
    var form = new mini.Form("issueForm");
    var flower = mini.get("rblFlower");
     function init(succsssBack){
 	    ComReq.dictionary("0401,0402,0403", function(result){
	        Dic.issueType = result["0401"];
			Dic.saleStatus = result["0402"];
			Dic.yesNo = result["0403"];
			mini.get("saleStatus_edit").setData(Dic.saleStatus);
			mini.get("currentIssue").setData(Dic.issueType);
			mini.get("issueLastest").setData(Dic.yesNo);
			flower.select(0);
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
					   row["num"+i] =changeShow(code[i]);
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
	       code+=changeDb(num);
	   }
	   if(isNull){
	      code="";
	   }else{
	      if(!Draw.check(lotteryCode, code)){
	    	  mini.alert("开奖号码格式错误");
	          return;
	      }
	   }
	   mini.get("drawCode").setValue(code);
	   var drawDetail =Current.joinDrawDetail_2(drawDetailNum);
	   mini.get("drawDetail").setValue(drawDetail);
	   Current.submit(form);
	}
	
	function change(id){
		var v = mini.get(id).getValue();
		var f = flower.getValue();
		mini.get(id).setValue(f+v);
	}
	function changeShow(code){
		var str = code.split("_");
		switch (str[0]) {
		case "1":
			return "♠"+str[1];
		case "2":
			return "♥"+str[1];
		case "3":
			return "♣"+str[1];
		case "4":
			return "♦"+str[1];
		default:
			break;
		}
		mini.alert("开奖号码格式错误");
	}
	function changeDb(code){
		var str = code.split("");
		var end = "";
		if(str.length ==2){
			end = str[1];
		}
		if(str.length ==3){
			end = str[1] +str[2];
		}
		switch (str[0]) {
		case "♠":
			return "1_"+end;
		case "♥":
			return "2_"+end;
		case "♣":
			return "3_"+end;
		case "♦":
			return "4_"+end;
		}
	}
	
</script>
</html>
