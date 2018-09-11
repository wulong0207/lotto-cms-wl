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
            <fieldset style="border:solid 1px #aaa; padding:3px;">
	            <legend >上传地址：</legend>
	            <div style="padding:5px;">
	              <table width="100%;">
	                  <tr>
	                    <td>投注内容</td>
	                    <td><input name="bettingContentUrl" class="mini-textbox" style="width:40%;" readonly="readonly"/>
	                    <a  target="_blank" id="contentUrl" href="">查看</a>
	                    </td>
	                  </tr>
	              </table>            
	            </div>
	        </fieldset>
	         
            <fieldset id="sportContent" style="border:solid 1px #aaa; padding:3px;">
            <legend >方案内容和开奖结果：<span id="drawCode_edit"><span></legend>
            <div class="mini-splitter" vertical="true" style="width:100%;height:250px;" style="border:0;">
				<div size="95%" showCollapseButton="true" style="border:0;">
					<div id="contentgrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
						 url="ordermgr/basic/content/detail/list" idField="id" allowResize="true" showColumnsMenu="true" pageSize="10" multiSelect="true">
						
						<div property="columns">
							<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
							<div field="planContent"  headerAlign="center" align="center">方案内容</div>
							<div field="passWay" name="passWay" headerAlign="center" align="center">过关方式</div>
							<div field="multiple"  headerAlign="center" align="center">倍数</div>
							<div field="amount"  headerAlign="center" align="center">金额</div>
							<div field="playIntro" headerAlign="center" align="center">玩法</div>
							
							<div field="codeWay"  type= "comboboxcolumn"  headerAlign="center" align="center">选号方式
							   <input property="editor" class="mini-combobox" data="Dic.codeWay" />
							</div>
							<div field="contentType"  type= "comboboxcolumn"  headerAlign="center" align="center">内容类型
							   <input property="editor" class="mini-combobox" data="Dic.contentType" />
							</div>
							<div field="winningDetail" headerAlign="center" align="center">中奖明细</div>
							<div field="preBonus" headerAlign="center" align="center">中奖金额</div>
							<div field="contentConver" headerAlign="center" align="center" renderer="Sport_Detail.converRender">内容转换</div>														
						</div>
					</div>
				</div>
			</div>
	        </fieldset> 	               
        <%@ include file="foot.jsp"%> 
        </div>
    </form>

    <div id="oldFbEditWindow" class="mini-window" title="老足彩内容转换"
		style="width:1000px; height:400px;" showMaxButton="false"
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="false" allowDrag="true" >
		<div class="mini-splitter" vertical="true" style="width:100%;height:250px;" style="border:0;">
				<div size="95%" showCollapseButton="true" style="border:0;">
					<div id="oldFbContentGrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
						 idField="id" allowResize="false" showColumnsMenu="true" multiSelect="true" showPager="false">
						<div property="columns">
							<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
							<div field="systemCode"  headerAlign="center" align="center">场次编号</div>
							<div field="homeName"  headerAlign="center" align="center">主队</div>
							<div field="guestName"  headerAlign="center" align="center">客队</div>
							<div field="halfScore"  headerAlign="center" align="center">半场比分</div>
							<div field="fullScore"  headerAlign="center" align="center">全场比分</div>
							<div field="betContent" headerAlign="center" align="center" renderer="Sport_Detail.oldFbBetContent">投注内容</div>
							<div field="isDan" headerAlign="center" align="center" renderer="Sport_Detail.onIsDanRenderer">胆码</div>
							<div field="winInfo"  headerAlign="center" align="center" renderer="Sport_Detail.oldFbWinInfo">彩果</div>
						</div>
					</div>
				</div>
			</div>
    </div> 
        
    <div id="fbEditWindow" class="mini-window" title="竞彩足球内容转换"
		style="width:1000px; height:400px;" showMaxButton="false"
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		<div class="mini-splitter" vertical="true" style="width:100%;height:250px;" style="border:0;">
				<div size="95%" showCollapseButton="true" style="border:0;">
					<div id="fbcontentgrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
						 idField="id" allowResize="false" showColumnsMenu="true" multiSelect="true" showPager="false">
						<div property="columns">
							<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
							<div field="systemCode"  headerAlign="center" align="center">场次编号</div>
							<div field="matchStartTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">比赛时间</div>
							<div field="homeName"  headerAlign="center" align="center">主队</div>
							<div field="letNum"  headerAlign="center" align="center">让球</div>
							<div field="guestName"  headerAlign="center" align="center">客队</div>
							<div field="halfScore"  headerAlign="center" align="center">半场比分</div>
							<div field="fullScore"  headerAlign="center" align="center">全场比分</div>
							<div field="betContent" headerAlign="center" align="center" renderer="Sport_Detail.fbBetContent">投注内容</div>
							<div field="isDan" headerAlign="center" align="center" renderer="Sport_Detail.onIsDanRenderer">胆码</div>
							<div field="winInfo"  headerAlign="center" align="center" renderer="Sport_Detail.fbWinInfo">彩果</div>
						</div>
					</div>
				</div>
			</div>
    </div>
    
    <div id="bbEditWindow" class="mini-window" title="竞彩篮球内容转换"
		style="width:1000px; height:400px;" showMaxButton="false"
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		<div class="mini-splitter" vertical="true" style="width:100%;height:250px;" style="border:0;">
				<div size="95%" showCollapseButton="true" style="border:0;">
					<div id="bbcontentgrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
						 idField="id" allowResize="false" showColumnsMenu="true" multiSelect="true" showPager="true">
						<div property="columns">
							<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
							<div field="systemCode"  headerAlign="center" align="center">场次编号</div>
							<div field="matchStartTime"  headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">比赛时间</div>
							<div field="guestName"  headerAlign="center" align="center">客队</div>
							<div field="letNum"  headerAlign="center" align="center">让分</div>
							<div field="homeName"  headerAlign="center" align="center">主队</div>
							<div field="sizeScore"  headerAlign="center" align="center">大小分</div>
							<div field="fullScore"  headerAlign="center" align="center">全场比分</div>
							<div field="betContent" headerAlign="center" align="center" renderer="Sport_Detail.bbBetContent">投注内容</div>
							<div field="isDan" headerAlign="center" align="center" renderer="Sport_Detail.onIsDanRenderer">胆码</div>
							<div field="winInfo"  headerAlign="center" align="center" renderer="Sport_Detail.bbWinInfo">彩果</div>
						</div>
					</div>
				</div>
			</div>
    </div>
    
    <div id="bjEditWindow" class="mini-window" title="北京单场内容转换"
		style="width:1000px; height:400px;" showMaxButton="false"
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		<div class="mini-splitter" vertical="true" style="width:100%;height:250px;" style="border:0;">
				<div size="95%" showCollapseButton="true" style="border:0;">
					<div id="bjcontentgrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
						 idField="id" allowResize="false" showColumnsMenu="true" multiSelect="true" showPager="false">
						<div property="columns">
							<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
							<div field="systemCode"  headerAlign="center" align="center">场次编号</div>
							<div field="matchStartTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">比赛时间</div>
							<div field="homeName"  headerAlign="center" align="center">主队</div>
							<div field="letNum"  headerAlign="center" align="center">让球</div>
							<div field="guestName"  headerAlign="center" align="center">客队</div>
							<div field="halfScore"  headerAlign="center" align="center">半场比分</div>
							<div field="fullScore"  headerAlign="center" align="center">全场比分</div>
							<div field="betContent" headerAlign="center" align="center" renderer="Sport_Detail.bjBetContent">投注内容</div>
							<div field="isDan" headerAlign="center" align="center" renderer="Sport_Detail.onIsDanRenderer">胆码</div>
							<div field="winInfo"  headerAlign="center" align="center" renderer="Sport_Detail.bjWinInfo">彩果</div>
						</div>
					</div>
				</div>
			</div>
    </div>
    
    <div id="wfEditWindow" class="mini-window" title="胜负过关内容转换"
		style="width:1000px; height:400px;" showMaxButton="false"
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showFooter="true" showModal="true" allowResize="true" allowDrag="true" >
		<div class="mini-splitter" vertical="true" style="width:100%;height:250px;" style="border:0;">
				<div size="95%" showCollapseButton="true" style="border:0;">
					<div id="wfcontentgrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
						 idField="id" allowResize="false" showColumnsMenu="true" multiSelect="true" showPager="false">
						<div property="columns">
							<div type="indexcolumn"  align="center" headerAlign="center">序号</div>
							<div field="systemCode"  headerAlign="center" align="center">场次编号</div>
							<div field="matchStartTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">比赛时间</div>
							<div field="homeName"  headerAlign="center" align="center">主队</div>
							<div field="letNum"  headerAlign="center" align="center">让球</div>
							<div field="guestName"  headerAlign="center" align="center">客队</div>
							<div field="fullScore"  headerAlign="center" align="center">全场比分</div>
							<div field="betContent" headerAlign="center" align="center" renderer="Sport_Detail.wfBetContent">投注内容</div>
							<div field="isDan" headerAlign="center" align="center" renderer="Sport_Detail.onIsDanRenderer">胆码</div>
							<div field="winInfo"  headerAlign="center" align="center" renderer="Sport_Detail.wfWinInfo">彩果</div>
						</div>
					</div>
				</div>
			</div>
    </div>        
            
</body>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/ordermgr/detail/sport_detail.js?vsersion=<%=version%>" type="text/javascript"></script>
<script type="text/javascript">
	mini.parse();
	sportEditWindow = mini.get("sportEditWindow");
	var form = new mini.Form("#editform");

    function init(succsssBack){
	    ComReq.dictionary("0508,0605,0606,0607,0608,0609,0610,0618,0620,0702", function(result){
	    	 Dic.matchResult  = result['0508'];
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
			succsssBack();
	    });
		ComReq.lottery("",function(result){
			 Dic.allCode = result;
			 mini.get("lotteryCode_edit").setData(Dic.allCode);
		 });	    
    }
    function initData(orderCode){
        init(function(){
        		setData(orderCode);
        });
     }
 	function setData(orderCode) {
 		var lotteryCode = "";
 		var issueCode = "";
 		$.get("ordermgr/basic/detail/"+orderCode, function(result) {
 			if (Code.success == result.errorCode) {
 				var row = result.data;
 		    	row.action = "put";
    	        row.url="ordermgr/basic";
    	        
    	        form.clear();
    	        form.setData(row);
    	        lotteryCode = row.lotteryCode;
    	        issueCode = row.issueCode;
    	        $("#orderCodeShow").html(row.orderCode);
 			} else {
 				mini.alert(result.msg);
 			}
 		});
		var detail_param ={
				orderCode:orderCode
     	}
		mini.get("contentgrid").load(detail_param,function(data){
			if(lotteryCode == '302' || lotteryCode == '303' || lotteryCode == '304'
					|| lotteryCode == '305')
				mini.get("contentgrid").hideColumn('passWay');
			if(data.result.code && data.result.code != Code.success){
				mini.alert(data.result.message);
			}
		});
 	}
 	
</script>
</html>