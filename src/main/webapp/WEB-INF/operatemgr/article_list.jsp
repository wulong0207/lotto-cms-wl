<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/date.utils.js" type="text/javascript"></script>
</head>

<body>
    <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;"> 
      	<div id="form1">
		<table style="width:100%;">
			<tr>
				<td>
					文章标题<input name="articleTitle" class="mini-textbox"  style="width:150px;" /> 
					状态<input id="articleStatus" name="status" class="mini-combobox"  style="width:150px;" 
					emptyText="请选择" valueFromSelect ="true" showClose="true" oncloseclick="Cms.onCloseClick"/> 
					作者<input name="createBy" class="mini-textbox"  style="width:150px;" emptyText="请输入"/> 
					<input id="searchTimeType"  name="searchTimeType" class="mini-combobox"  style="width:150px;" valueFromSelect ="true"/>
					<input id="startTime" name="startTime" class="mini-datepicker" style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true"
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>
				        到<input id="endTime" name="endTime" class="mini-datepicker"  allowInput="false"  style="width:200px;" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" 
				        showOkButton="true" showClearButton="false" oncloseclick="Cms.onCloseClick" showClose="true"/>  
					<a class="mini-button" onclick="Cms.search('articleGrid','form1',['startTime','endTime'])" >搜索</a>
				</td>
			</tr>
			<tr>
				<td width="10%" nowrap="nowrap">
	        <btn:operate privilege="ADD">
            	<a class="mini-button" iconCls="icon-add" plain="true" onclick="operate_article.showAddArticle">新增</a>
            </btn:operate>
            <btn:operate privilege="UPD">
            	<a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_article.showEditArticle">修改</a>
            </btn:operate>         
            <btn:operate privilege="REJECT">
            	<a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_article.visitArticleStatus(0)">驳回</a>
            </btn:operate> 
            <btn:operate privilege="SUBMIT_AUDIT">
            	<a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_article.visitArticleStatus(2)">提交审核</a>                  
        	</btn:operate> 
            <btn:operate privilege="APPROVE">
            	<a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_article.visitArticleStatus(3)">审核通过</a>
            </btn:operate> 
            <btn:operate privilege="RESET_ARTICLE">
            	<a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_article.resetArticle()">重新生成文章</a>
            </btn:operate> 
            <%-- 
            <btn:operate privilege="RELEASE">
            	<a class="mini-button" iconCls="icon-edit" plain="true" onclick="operate_article.visitArticleStatus(4)">发布</a>
            </btn:operate> 
            --%> 
				</td>
			</tr>

		</table>
		</div>
	</div>
        <div class="mini-fit" >
            <div id="articleGrid" class="mini-datagrid" style="width:100%;height:100%;"
                borderStyle="border:0;" url="operatemgr/article/list" onrowdblclick="operate_article.showEditArticle">
                <div property="columns">
               	 	<div type="checkcolumn" width="3%"></div>
					<div type="indexcolumn" width="3%" align="center" headerAlign="center">序号</div>
					<div field="articleId" headerAlign="center" align="center">文章编号</div>          
					<div field="articleTitle" headerAlign="center" align="center">文章标题</div>          
					<div field="typeName" headerAlign="center" align="center">所在栏目</div>
					<div headerAlign="center" align="center" renderer="operate_article.showStatus">状态</div>
					<%--<div field="status" width="10%" type="comboboxcolumn" headerAlign="center" align="center">状态
				    <input property="editor" class="mini-combobox" data="Dic.status" />
					</div>
					--%>
					<div field="createBy" headerAlign="center" align="center">作者</div>        
					<div field="click" headerAlign="center" align="center">阅读数量</div>        
                </div>
            </div>  
        </div>
        
        </div> 
</body>
<script src="<%=basePath%>resources/js/public/js.cookie.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/operate_article.js?vsersion=<%=version%>" type="text/javascript"></script>
<script>
	$(function(){
	    ComReq.dictionary("0002,1513,1514", function(result){
	    	Dic.status = result["1513"];	
	    	Dic.time = result["1514"];	
	    	mini.get("articleStatus").setData(Dic.status);
	    	mini.get("searchTimeType").setData(Dic.time);
	    	mini.get("searchTimeType").select(0);
	    	//add by cheng chen 意见箱文章跳转使用
	    	setTimeout(function () { 
		    	var articleId = Cms.getCookie('articleId');
		    	Cms.delCookie('articleId');
		    	operate_article.openEditWin(articleId);
    		}, 200);
	    });
	});
</script>
</html>
