<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="common/top.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>彩票销售后台管理系统</title>
	<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

</style>
</head>

<body>

	<div id="layout1" class="mini-layout" splitSize="0"  style="width:100%;height:100%;">
		<div class="header" region="north" height="70"  showSplit="false" splitSize="0"
			showHeader="false" style="border:0;background-image: url(resources/images/public/maintop.jpg)">

			<table width="100%" height="100%" border="0">
				<tbody>
					<tr>
						<td width="3%"></td>
						<td width="50%" align="center" valign="middle"><h1>
							</h1></td>
						<td width="28%" align="left" valign="middle"></td>
						<td width="9%" align="center" valign="middle"><a
							class="mini-button" plain="true" iconCls="icon-user"
							onClick="loginOut">安全退出</a></td>
						<td align="center" valign="middle" style="display: none;">
						      <input id="language" name="language" class="mini-combobox" style="width:100px;"
									 textField="language" valueField="id" onvaluechanged="onvaluechanged"/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div title="south" region="south" showSplit="false" bodyStyle="background-color:#d9e7f8;border:0;text-align:center;" showHeader="false"
			height="30">
			<div style="line-height:28px;text-align:center;cursor:default">
						
			Copyright© 2017 深圳市华海乐盈网络科技有限公司
			<!-- 
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="curtime" style="color: #FFF; font-weight:bold; font-size:16px; padding:1px; background-color:#333;"></span>
			 -->
			</div>
		</div>
		<div showHeader="true" showSplitIcon="false"  region="west" width="180" maxWidth="250"
			minWidth="100" showCollapseButton="true">
			<!--OutlookMenu-->
			<div id="leftTree" showTreeIcon="true" showTreeLines="true" splitSize="0"
				class="mini-outlooktree" url="index/menu" onitemselect="onItemSelect" onnodeclick="onNodeSelect"
				idField="menuId" activeIndex="3" parentField="parentMenuId" style="background-color:#a5cbfa"
				iconField="iconcls" textField="menuTitle" borderStyle="border:0" >
			</div>

		</div>
		<div title="center" region="center"   bodyStyle="overflow:hidden; ">
<!-- 			<iframe id="mainframe" frameborder="0" name="main"
			src="desktop.jsp"	style="width:100%;height:100%;" border="0"></iframe> -->
			 <div id="mainTabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;"      
                     plain="false" bodyStyle="padding:0px;border:0px" >
                <div title=" "  url="desktop.jsp" > </div>
		</div>
	</div>
</div>

</body>

</html>
<style type="text/css">
.mini-layout-region{
border-left:0px;
border-right:1px solid #99BCE8;
border-top:1px solid #99BCE8;
border-bottom:1px solid #99BCE8;
}
</style>
<script type="text/javascript">
	   
        mini.parse();
        
        //init iframe src
        var iframe = document.getElementById("mainframe"); 
        function loginOut(){
        	window.location="login/out";
        }
        var selectUrl = "";
        function geSelecttUrl(){
        	return selectUrl;
        }
        function onItemSelect(e) {
            var item = e.item;
            iframe.src = item.menuUrl;
            selectUrl = item.menuUrl;
        }  
        
        
        function showTab(node) {
            var tabs = mini.get("mainTabs");

            var id = "tab$" + node.menuId;
            var tab = tabs.getTab(id);
          
            if (!tab) {
                tab = {};
                tab._nodeid = node.menuId;
                tab.name = id;
                tab.title = node.menuTitle;
                tab.showCloseButton = true;

                //这里拼接了url，实际项目，应该从后台直接获得完整的url地址
                tab.url = node.menuUrl;

                tabs.addTab(tab);
            }
            selectUrl = tab.menuUrl;
            tabs.activeTab(tab);
        }
        function onNodeSelect(e){
        	 var node = e.node;
             var isLeaf = e.isLeaf;
			 var url = node.menuUrl;
			 if(url == "ticketmgr/monitorcommon"){
				 window.open(url);
				 return;
			 }
             
             if (isLeaf) {
                 showTab(node);
             }
        }
        
        /**

        */
        //语言选择
        var  languageData = [{ id: 'zh_CN', language: '简体中文' },{ id: 'en', language: 'English' }];
        mini.get("language").setData(languageData);
        var language = mini.get("language");
        var coolieValue =  getCookie("Language");
        language.setValue(coolieValue==""?"zh_CN":coolieValue);
        function onvaluechanged(){
            var lan = language.getValue();
            setCookie("Language",lan,10);
			window.location.reload();
        }
		function setCookie(cname, cvalue, exdays) {
		    var d = new Date();
		    d.setTime(d.getTime() + (exdays*24*60*60*1000));
		    var expires = "expires="+d.toUTCString();
		    document.cookie = cname + "=" + cvalue + "; " + expires;
		}
		function getCookie(name){
			var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
			if(arr=document.cookie.match(reg))
			return unescape(arr[2]);
			else
			return "";
		}
    </script>
