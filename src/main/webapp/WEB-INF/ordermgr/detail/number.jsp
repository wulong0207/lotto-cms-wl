<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>方案管理</title>
  </head>
<body>
	<div  id="editform">
        <%@ include file="head.jsp"%>
	        <fieldset id="numHighContent" style="border:solid 1px #aaa;padding:3px;">
            <legend >方案内容和开奖结果：<span id="drawCode_edit"><span></legend>
            <div class="mini-splitter" vertical="true" style="width:100%;height:250px;" style="border:0;">
				<div size="95%" showCollapseButton="true" style="border:0;">
					<div id="contentgrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
						 url="ordermgr/basic/content/detail/list" idField="id" allowResize="true" showColumnsMenu="true" pageSize="10" multiSelect="true">
						
						<div property="columns">
							<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
							<div field="planContent"  headerAlign="center" align="center">方案内容</div>
							<div field="multiple"  headerAlign="center" align="center">倍数</div>
							<div field="amount"  headerAlign="center" align="center">金额</div>
							<div field="playIntro"  headerAlign="center" align="center">玩法</div>
							<div field="codeWay"  type= "comboboxcolumn"  headerAlign="center" align="center">选号方式
							   <input property="editor" class="mini-combobox" data="Dic.codeWay" />
							</div>
							<div field="contentType"  type= "comboboxcolumn"  headerAlign="center" align="center">内容类型
							   <input property="editor" class="mini-combobox" data="Dic.contentType" />
							</div>
							<div field="winningDetail" headerAlign="center" align="center">中奖明细</div>
							<div field="preBonus" headerAlign="center" align="center">中奖金额</div>
						</div>
					</div>
				</div>
			</div>
            </fieldset>        
        <%@ include file="foot.jsp"%> 
        </div>
    </form>
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
</html>
<script type="text/javascript">
	mini.parse();
	var form = new mini.Form("#editform");
	
	function init(succsssBack){
	    ComReq.dictionary("0605,0606,0607,0608,0609,0610,0618,0620,0702", function(result){
			 Dic.codeWay = result["0605"];
			 Dic.contentType = result["0606"];
			 Dic.buyType =result["0607"];
			 Dic.payStatus = result["0608"];
			 Dic.orderStatus =result["0609"];
			 Dic.winningStatus = result["0610"];
			 Dic.isDltAdd = result["0618"];
			 Dic.checkTicket  = result["0620"]
			 Dic.platform = result["0702"];	 
			 mini.get("buyType_edit").setData(Dic.buyType);
			 mini.get("payStatus_edit").setData(Dic.payStatus);
			 mini.get("orderStatus_edit").setData(Dic.orderStatus);
			 mini.get("winningStatus_edit").setData(Dic.winningStatus);
			 mini.get("isDltAdd_edit").setData(Dic.isDltAdd);
			 mini.get("checkTicket_edit").setData(Dic.checkTicket);
			 mini.get("platform_edit").setData(Dic.platform);
			 mini.get("lotteryCode_edit").setData(Dic.allCode);
	    });
		ComReq.lottery("",function(result){
			 Dic.allCode = result;
			 mini.get("lotteryCode_edit").setData(Dic.allCode);
			 succsssBack();
		});		    
	};

	function initData(orderCode){
	    init(function(){
	    		setData(orderCode);
	    });
	 };
	 
	function setData(orderCode) {
		$.get("ordermgr/basic/detail/"+orderCode, function(result) {
			if (Code.success == result.errorCode) {
				var row = result.data;
		    	row.action = "put";
	        row.url="ordermgr/basic";

	        form.clear();
	        form.setData(row);
	        $("#orderCodeShow").html(row.orderCode);
	        $("#drawCode_edit").html(row.drawCode);
			} else {
				mini.alert(result.msg);
			}
		});
		var param ={
				orderCode:orderCode
     	}		
		mini.get("contentgrid").load(param,function(data){
			if(data.result.code && data.result.code != Code.success){
				mini.alert(data.result.message);
			}
		});			
	} 
	function closeWindow(action) {
		if (window.CloseOwnerWindow)
			return window.CloseOwnerWindow(action);
		else
			window.close();
	}
	function cancel() {
		closeWindow("cancel");
	}	
</script>

				 