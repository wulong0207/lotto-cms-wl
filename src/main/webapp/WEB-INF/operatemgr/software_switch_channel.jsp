<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	 <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
  	 <script src="<%=basePath%>resources/js/public/ajaxfileupload.js" type="text/javascript"></script>
    <title>渠道配置管理</title>
        <style type="text/css">
    	.secondLevel ul {
    		list-style:none;margin:0px;
    	}
    	.secondLevel ul li {float:left;}
    	.appContainer {
    		border: 1px solid #C2C2C2;
    		border-radius: 5px;
    		padding : 10px;
    		overflow: hidden;
    	}
    	.appFunction {
    		border-bottom: 1px solid #C2C2C2;
    		margin : 15px;
    	}
    	span.clear { clear: left; display: block; }
    </style>
  </head>
  <body>

	<div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true" borderStyle="border:solid 0px;" style="width:100%;height:100%;" 
		  idField="id" allowResize="true" 
		 url="operatemgr/software/switch/page" showColumnsMenu="true" showFilterRow="false" >
		<div property="columns">
			<div type="checkcolumn" width="5%"></div>
			<div type="indexcolumn" width="5%" align="center" headerAlign="center">序号</div>
			<div field="channelId" type="comboboxcolumn" headerAlign="center" align="center">渠道名称
		     <input property="editor" class="mini-combobox" url="operatemgr/marketchannel/dic" />
			</div>
			<div field="name" headerAlign="center" align="center">版本号</div>
			<div field="lotteryStatus" type="comboboxcolumn" headerAlign="center" align="center">购彩状态
		     <input property="editor" class="mini-combobox" data="Dic.lotteryStatus" />
			</div>
			<div field="hideArea" headerAlign="center" align="center">购彩隐藏区域</div>
			<div field="synOfficial" type="comboboxcolumn" headerAlign="center" align="center">与官方同步更新
		     <input property="editor" class="mini-combobox" data="Dic.synOfficial" />
			</div>
			<div field="hideContent" headerAlign="center" align="center">隐藏内容</div>
			<div field="switchModifyBy" headerAlign="center" align="center">修改人</div>
			<div field="switchModifyTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">修改时间</div>	
			<div field="switchRemark" headerAlign="center" align="center">备注</div>
			<div headerAlign="center" align="center" renderer="operate_software_switch.renderSyn">同步官方操作</div>
			<div headerAlign="center" align="center" renderer="operate_software_switch.renderOpt">操作</div>
		</div>
	</div>
	
	
	
	
		<div id="detailWindow" class="mini-window" 
		style="width:1400px; height:700px;" showMaxButton="false" 
		showCollapseButton="false" showShadow="true" showToolbar="false"
		showModal="true" allowResize="true" allowDrag="true" title="修改隐藏配置">
		
		<div id="SoftwareForm">
			<input name="id" class="mini-hidden">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
				渠道名称：	<input class="mini-textbox" name="channelName" enabled="false"/>
				渠道ID：<input class="mini-textbox" name="channelId" enabled="false"/>
				<br/>
				版本号：<input class="mini-textbox" name="name" enabled="false"/>
				<tr id="softwareHiddenTr">
					<td colspan="6">
				<div class="appContainer">
					<div class="appFunction">
					<span>APP功能隐藏</span>
					</div>
					<div class="appFunction" id="buyLottery">
						<input type="checkbox" class="firstLevel" value="" data-child="buyLottery" id="buyLotteryCheckBox"/>购彩功能
						&nbsp;&nbsp;&nbsp;隐藏区域:<input style="width:600px;" class="mini-treeselect" id="hideAreaSelect" multiSelect="true"  expandOnLoad="false" showFolderCheckBox="true" name="hideArea"/>
					</div>
					<div class="appFunction buyLottery"  id="index">
						&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" value="1" class="firstLevel" data-child="index"/>首页
					</div>
					<div class="secondLevel buyLottery index" data-parent="index">
						<ul>
							<li>功能隐藏 </li>
							<li><input type="checkbox" value="2"/>banner板块 </li>
							<li><input type="checkbox" value="16"/>中奖播报</li>
							<li><input type="checkbox" value="5"/>快投</li>
							<li><input type="checkbox" value="3"/>彩种导航</li>
						</ul>
					</div>
					<br/>
					<%--
					<div class="appFunction buyLottery" id="userCenter1">
						&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="firstLevel" value="" data-child="userCenter1"/>个人中心栏
					</div>
					--%>
					<div class="appFunction">
						&nbsp;&nbsp;&nbsp;&nbsp;个人中心栏
					</div>
					<div class="secondLevel buyLottery">
						<ul>
							<li>功能隐藏 </li>
							<li><input type="checkbox" value="17"/>充值 </li>
							<li><input type="checkbox" value="18"/>提款</li>
							<li><input type="checkbox" value="12"/>投注记录</li>
							<li><input type="checkbox" value="19"/>交易明细</li>
							<li><input type="checkbox" value="28"/>我的红包</li>
							<li><input type="checkbox" value="14"/>我要代理</li>
							<li><input type="checkbox" value="20"/>账户余额/红包余额</li>
						</ul>
					</div>
					<br/>
					<div class="appFunction">
						&nbsp;&nbsp;&nbsp;&nbsp;注册成功页面
					</div>
					<div class="secondLevel buyLottery">
						<ul>
							<li>功能隐藏 </li>
							<li><input type="checkbox" value="27"/>立即充值 </li>
						</ul>
					</div>
					<br/>
					<div class="appFunction">
						&nbsp;&nbsp;&nbsp;&nbsp;开奖公告
					</div>
					<div class="secondLevel buyLottery">
						<ul>
							<li>功能隐藏 </li>
							<li><input type="checkbox" value="29"/>去中奖 </li>
						</ul>
					</div>
					</br>
					<div class="appFunction">
						<input type="checkbox" value="7" class="firstLevel"/>直播栏
					</div>
					<div class="appFunction">
						<input type="checkbox" value="23" class="firstLevel" data-child="drawNotice"/>开奖公告
					</div>
					<div class="secondLevel drawNotice">
						<ul>
							<li>功能隐藏 </li>
							<li><input type="checkbox" value="24"/>全国类 </li>
							<li><input type="checkbox" value="25"/>高频类 </li>
							<li><input type="checkbox" value="26"/>地方性 </li>
						</ul>
					</div>
					</br>
					<div class="appFunction">
						<input type="checkbox" value="8" class="firstLevel"/>资讯栏
					</div>
					<div class="appFunction" id="userCenter2">
						<input type="checkbox" class="firstLevel" value="9" data-child="userCenter2"/>个人中心栏
					</div>
					<div class="secondLevel userCenter2 userCenter2"  data-parent="userCenter2">
						<ul>
							<li>功能隐藏 | </li>
							<li><input type="checkbox" value="13"/>个人资料</li>
							<li><input type="checkbox" value="21"/>联系客服</li>
							<li><input type="checkbox" value="10"/>设置 </li>
							<li><input type="checkbox" value="22"/>我的消息</li>
						</ul>
					</div>
					
					<span class="clear"></span>
				</div>
					</td>
					</tr>
										 
					<tr>
						<td colspan="3" align="right">
							 <a class="mini-button" onclick="operate_software_switch.updDetail">确认</a>
						</td>
						<td colspan="3" align="left">
						<a class="mini-button" onclick="operate_software_switch.closeDetailWindow">取消</a>
						</td>
					</tr>
					  <br/>
					   <tr>
					    <td colspan="6">操作信息:</td>
					  </tr>							  
					  <tr>
					    <td >创建时间</td>
					    <td ><input name="createTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/></td>
					    <td >修改时间</td>
					    <td ><input name="switchModifyTime" class="mini-datepicker"  allowInput="false" enabled="false" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/></td>
					    <td rowspan="2">备注</td>
					    <td rowspan="2"><input name="switchRemark" class="mini-textarea" vtype ="maxLength:200" style="width:200px;"/>
					    </td>
					  </tr>
					  <tr>
					    <td>创建人</td>
					    <td><input name="createBy" class="mini-textbox"  enabled="false" allowInput="false" style="width:200px;"/></td>
					    <td>修改人</td>
					    <td><input name="switchModifyBy" class="mini-textbox"  enabled="false" allowInput="false" style="width:200px;" /></td>
					  </tr>				
				</table>

				
				
				
		</div>
	</div>	
	
	
  </body>

<script src="<%=basePath%>resources/js/public/common.js?vsersion=<%=System.currentTimeMillis()%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/operatemgr/operate_software_switch.js?vsersion=<%=System.currentTimeMillis() %>" type="text/javascript"></script>

<script type="text/javascript">
Dic.synOfficial=[{id:0,text:'关闭'},{id:1,text:'开启'}];
Dic.lotteryStatus=[{id:0,text:'已关闭'},{id:1,text:'已开启'}];
var operate_software_channel = {
	secondLevelChange : function($secondDiv) {
		var secondLevelChecks = $secondDiv.children().find("input").get();
		//var secondLevelChecks = $('#'+$second.parent().data('parent')).children().find("input").get();
		// 如果所有二级栏目选中
		if(
			secondLevelChecks.every(function(ele, index) {
				return $(ele).attr('checked');
			})	
		) {
			// 上一个div即为对应的一级栏目
			//$(this).parent().parent().parent().prev().find("input").attr('checked', true);
			$('div#'+$secondDiv.data('parent')).find("input").attr('checked', true);
		} else {
			//$(this).parent().parent().parent().prev().find("input").attr('checked', false);
			$('div#'+$secondDiv.data('parent')).find("input").attr('checked', false);
		}
	}
}
$(function() {
	// 当父栏目选中时，子栏目自动全选
	$(".firstLevel").change(function() {
		$('div.'+$(this).data('child')+' input[type=checkbox]').attr('checked', $(this).prop('checked'));
	});
	// 当所有子栏目选中时，父栏目也选中
	$(".secondLevel  input[type=checkbox]").change(function() {
		var $secondDiv = $(this).parent().parent().parent();
		operate_software_channel.secondLevelChange($secondDiv);
	});
	var areaJson =
        [{"id":"北京市","text":"北京市"},{"id":"天津市","text":"天津市"},{"id":"上海市","text":"上海市"},{"id":"重庆市","text":"重庆市"},{"id":"香港","text":"香港"},{"id":"澳门","text":"澳门"},{"id":"广东省","text":"广东省"},{"id":"广州市","text":"广州市","pid":"广东省"},{"id":"深圳市","text":"深圳市","pid":"广东省"},{"id":"清远市","text":"清远市","pid":"广东省"},{"id":"韶关市","text":"韶关市","pid":"广东省"},{"id":"河源市","text":"河源市","pid":"广东省"},{"id":"梅州市","text":"梅州市","pid":"广东省"},{"id":"潮州市","text":"潮州市","pid":"广东省"},{"id":"汕头市","text":"汕头市","pid":"广东省"},{"id":"揭阳市","text":"揭阳市","pid":"广东省"},{"id":"汕尾市","text":"汕尾市","pid":"广东省"},{"id":"惠州市","text":"惠州市","pid":"广东省"},{"id":"东莞市","text":"东莞市","pid":"广东省"},{"id":"珠海市","text":"珠海市","pid":"广东省"},{"id":"中山市","text":"中山市","pid":"广东省"},{"id":"江门市","text":"江门市","pid":"广东省"},{"id":"佛山市","text":"佛山市","pid":"广东省"},{"id":"肇庆市","text":"肇庆市","pid":"广东省"},{"id":"云浮市","text":"云浮市","pid":"广东省"},{"id":"阳江市","text":"阳江市","pid":"广东省"},{"id":"茂名市","text":"茂名市","pid":"广东省"},{"id":"湛江市","text":"湛江市","pid":"广东省"},{"id":"英德市","text":"英德市","pid":"广东省"},{"id":"连州市","text":"连州市","pid":"广东省"},{"id":"乐昌市","text":"乐昌市","pid":"广东省"},{"id":"南雄市","text":"南雄市","pid":"广东省"},{"id":"兴宁市","text":"兴宁市","pid":"广东省"},{"id":"普宁市","text":"普宁市","pid":"广东省"},{"id":"陆丰市","text":"陆丰市","pid":"广东省"},{"id":"恩平市","text":"恩平市","pid":"广东省"},{"id":"台山市","text":"台山市","pid":"广东省"},{"id":"开平市","text":"开平市","pid":"广东省"},{"id":"鹤山市","text":"鹤山市","pid":"广东省"},{"id":"四会市","text":"四会市","pid":"广东省"},{"id":"罗定市","text":"罗定市","pid":"广东省"},{"id":"阳春市","text":"阳春市","pid":"广东省"},{"id":"化州市","text":"化州市","pid":"广东省"},{"id":"信宜市","text":"信宜市","pid":"广东省"},{"id":"高州市","text":"高州市","pid":"广东省"},{"id":"吴川市","text":"吴川市","pid":"广东省"},{"id":"廉江市","text":"廉江市","pid":"广东省"},{"id":"雷州市","text":"雷州市","pid":"广东省"},{"id":"广西壮族自治区","text":"广西壮族自治区"},{"id":"南宁市","text":"南宁市","pid":"广西壮族自治区"},{"id":"桂林市","text":"桂林市","pid":"广西壮族自治区"},{"id":"柳州市","text":"柳州市","pid":"广西壮族自治区"},{"id":"梧州市","text":"梧州市","pid":"广西壮族自治区"},{"id":"贵港市","text":"贵港市","pid":"广西壮族自治区"},{"id":"玉林市","text":"玉林市","pid":"广西壮族自治区"},{"id":"钦州市","text":"钦州市","pid":"广西壮族自治区"},{"id":"北海市","text":"北海市","pid":"广西壮族自治区"},{"id":"防城港市","text":"防城港市","pid":"广西壮族自治区"},{"id":"崇左市","text":"崇左市","pid":"广西壮族自治区"},{"id":"百色市","text":"百色市","pid":"广西壮族自治区"},{"id":"河池市","text":"河池市","pid":"广西壮族自治区"},{"id":"来宾市","text":"来宾市","pid":"广西壮族自治区"},{"id":"贺州市","text":"贺州市","pid":"广西壮族自治区"},{"id":"岑溪市","text":"岑溪市","pid":"广西壮族自治区"},{"id":"桂平市","text":"桂平市","pid":"广西壮族自治区"},{"id":"北流市","text":"北流市","pid":"广西壮族自治区"},{"id":"东兴市","text":"东兴市","pid":"广西壮族自治区"},{"id":"凭祥市","text":"凭祥市","pid":"广西壮族自治区"},{"id":"宜州市","text":"宜州市","pid":"广西壮族自治区"},{"id":"合山市","text":"合山市","pid":"广西壮族自治区"},{"id":"靖西市","text":"靖西市","pid":"广西壮族自治区"},{"id":"海南省","text":"海南省"},{"id":"海口市","text":"海口市","pid":"海南省"},{"id":"三亚市","text":"三亚市","pid":"海南省"},{"id":"三沙市","text":"三沙市","pid":"海南省"},{"id":"儋州市","text":"儋州市","pid":"海南省"},{"id":"文昌市","text":"文昌市","pid":"海南省"},{"id":"琼海市","text":"琼海市","pid":"海南省"},{"id":"万宁市","text":"万宁市","pid":"海南省"},{"id":"东方市","text":"东方市","pid":"海南省"},{"id":"五指山市","text":"五指山市","pid":"海南省"},{"id":"河北省","text":"河北省"},{"id":"石家庄市","text":"石家庄市","pid":"河北省"},{"id":"邯郸市","text":"邯郸市","pid":"河北省"},{"id":"唐山市","text":"唐山市","pid":"河北省"},{"id":"保定市","text":"保定市","pid":"河北省"},{"id":"秦皇岛市","text":"秦皇岛市","pid":"河北省"},{"id":"邢台市","text":"邢台市","pid":"河北省"},{"id":"张家口市","text":"张家口市","pid":"河北省"},{"id":"承德市","text":"承德市","pid":"河北省"},{"id":"沧州市","text":"沧州市","pid":"河北省"},{"id":"廊坊市","text":"廊坊市","pid":"河北省"},{"id":"衡水市","text":"衡水市","pid":"河北省"},{"id":"辛集市","text":"辛集市","pid":"河北省"},{"id":"晋州市","text":"晋州市","pid":"河北省"},{"id":"新乐市","text":"新乐市","pid":"河北省"},{"id":"遵化市","text":"遵化市","pid":"河北省"},{"id":"迁安市","text":"迁安市","pid":"河北省"},{"id":"霸州市","text":"霸州市","pid":"河北省"},{"id":"三河市","text":"三河市","pid":"河北省"},{"id":"定州市","text":"定州市","pid":"河北省"},{"id":"涿州市","text":"涿州市","pid":"河北省"},{"id":"安国市","text":"安国市","pid":"河北省"},{"id":"高碑店市","text":"高碑店市","pid":"河北省"},{"id":"泊头市","text":"泊头市","pid":"河北省"},{"id":"任丘市","text":"任丘市","pid":"河北省"},{"id":"黄骅市","text":"黄骅市","pid":"河北省"},{"id":"河间市","text":"河间市","pid":"河北省"},{"id":"冀州市","text":"冀州市","pid":"河北省"},{"id":"深州市","text":"深州市","pid":"河北省"},{"id":"南宫市","text":"南宫市","pid":"河北省"},{"id":"沙河市","text":"沙河市","pid":"河北省"},{"id":"武安市","text":"武安市","pid":"河北省"},{"id":"山西省","text":"山西省"},{"id":"太原市","text":"太原市","pid":"山西省"},{"id":"大同市","text":"大同市","pid":"山西省"},{"id":"朔州市","text":"朔州市","pid":"山西省"},{"id":"阳泉市","text":"阳泉市","pid":"山西省"},{"id":"长治市","text":"长治市","pid":"山西省"},{"id":"晋城市","text":"晋城市","pid":"山西省"},{"id":"忻州市","text":"忻州市","pid":"山西省"},{"id":"吕梁市","text":"吕梁市","pid":"山西省"},{"id":"晋中市","text":"晋中市","pid":"山西省"},{"id":"临汾市","text":"临汾市","pid":"山西省"},{"id":"运城市","text":"运城市","pid":"山西省"},{"id":"古交市","text":"古交市","pid":"山西省"},{"id":"潞城市","text":"潞城市","pid":"山西省"},{"id":"高平市","text":"高平市","pid":"山西省"},{"id":"原平市","text":"原平市","pid":"山西省"},{"id":"孝义市","text":"孝义市","pid":"山西省"},{"id":"汾阳市","text":"汾阳市","pid":"山西省"},{"id":"介休市","text":"介休市","pid":"山西省"},{"id":"侯马市","text":"侯马市","pid":"山西省"},{"id":"霍州市","text":"霍州市","pid":"山西省"},{"id":"永济市","text":"永济市","pid":"山西省"},{"id":"河津市","text":"河津市","pid":"山西省"},{"id":"辽宁省","text":"辽宁省"},{"id":"沈阳市","text":"沈阳市","pid":"辽宁省"},{"id":"大连市","text":"大连市","pid":"辽宁省"},{"id":"朝阳市","text":"朝阳市","pid":"辽宁省"},{"id":"阜新市","text":"阜新市","pid":"辽宁省"},{"id":"铁岭市","text":"铁岭市","pid":"辽宁省"},{"id":"抚顺市","text":"抚顺市","pid":"辽宁省"},{"id":"本溪市","text":"本溪市","pid":"辽宁省"},{"id":"辽阳市","text":"辽阳市","pid":"辽宁省"},{"id":"鞍山市","text":"鞍山市","pid":"辽宁省"},{"id":"丹东市","text":"丹东市","pid":"辽宁省"},{"id":"营口市","text":"营口市","pid":"辽宁省"},{"id":"盘锦市","text":"盘锦市","pid":"辽宁省"},{"id":"锦州市","text":"锦州市","pid":"辽宁省"},{"id":"葫芦岛市","text":"葫芦岛市","pid":"辽宁省"},{"id":"新民市","text":"新民市","pid":"辽宁省"},{"id":"瓦房店市","text":"瓦房店市","pid":"辽宁省"},{"id":"庄河市","text":"庄河市","pid":"辽宁省"},{"id":"北票市","text":"北票市","pid":"辽宁省"},{"id":"凌源市","text":"凌源市","pid":"辽宁省"},{"id":"调兵山市","text":"调兵山市","pid":"辽宁省"},{"id":"开原市","text":"开原市","pid":"辽宁省"},{"id":"灯塔市","text":"灯塔市","pid":"辽宁省"},{"id":"海城市","text":"海城市","pid":"辽宁省"},{"id":"凤城市","text":"凤城市","pid":"辽宁省"},{"id":"东港市","text":"东港市","pid":"辽宁省"},{"id":"大石桥市","text":"大石桥市","pid":"辽宁省"},{"id":"盖州市","text":"盖州市","pid":"辽宁省"},{"id":"凌海市","text":"凌海市","pid":"辽宁省"},{"id":"北镇市","text":"北镇市","pid":"辽宁省"},{"id":"兴城市","text":"兴城市","pid":"辽宁省"},{"id":"吉林省","text":"吉林省"},{"id":"长春市","text":"长春市","pid":"吉林省"},{"id":"吉林市","text":"吉林市","pid":"吉林省"},{"id":"白城市","text":"白城市","pid":"吉林省"},{"id":"松原市","text":"松原市","pid":"吉林省"},{"id":"四平市","text":"四平市","pid":"吉林省"},{"id":"辽源市","text":"辽源市","pid":"吉林省"},{"id":"通化市","text":"通化市","pid":"吉林省"},{"id":"白山市","text":"白山市","pid":"吉林省"},{"id":"德惠市","text":"德惠市","pid":"吉林省"},{"id":"榆树市","text":"榆树市","pid":"吉林省"},{"id":"磐石市","text":"磐石市","pid":"吉林省"},{"id":"蛟河市","text":"蛟河市","pid":"吉林省"},{"id":"桦甸市","text":"桦甸市","pid":"吉林省"},{"id":"舒兰市","text":"舒兰市","pid":"吉林省"},{"id":"洮南市","text":"洮南市","pid":"吉林省"},{"id":"大安市","text":"大安市","pid":"吉林省"},{"id":"双辽市","text":"双辽市","pid":"吉林省"},{"id":"公主岭市","text":"公主岭市","pid":"吉林省"},{"id":"梅河口市","text":"梅河口市","pid":"吉林省"},{"id":"集安市","text":"集安市","pid":"吉林省"},{"id":"临江市","text":"临江市","pid":"吉林省"},{"id":"延吉市","text":"延吉市","pid":"吉林省"},{"id":"图们市","text":"图们市","pid":"吉林省"},{"id":"敦化市","text":"敦化市","pid":"吉林省"},{"id":"珲春市","text":"珲春市","pid":"吉林省"},{"id":"龙井市","text":"龙井市","pid":"吉林省"},{"id":"和龙市","text":"和龙市","pid":"吉林省"},{"id":"扶余市","text":"扶余市","pid":"吉林省"},{"id":"黑龙江省","text":"黑龙江省"},{"id":"哈尔滨市","text":"哈尔滨市","pid":"黑龙江省"},{"id":"齐齐哈尔市","text":"齐齐哈尔市","pid":"黑龙江省"},{"id":"黑河市","text":"黑河市","pid":"黑龙江省"},{"id":"大庆市","text":"大庆市","pid":"黑龙江省"},{"id":"伊春市","text":"伊春市","pid":"黑龙江省"},{"id":"鹤岗市","text":"鹤岗市","pid":"黑龙江省"},{"id":"佳木斯市","text":"佳木斯市","pid":"黑龙江省"},{"id":"双鸭山市","text":"双鸭山市","pid":"黑龙江省"},{"id":"七台河市","text":"七台河市","pid":"黑龙江省"},{"id":"鸡西市","text":"鸡西市","pid":"黑龙江省"},{"id":"牡丹江市","text":"牡丹江市","pid":"黑龙江省"},{"id":"绥化市","text":"绥化市","pid":"黑龙江省"},{"id":"尚志市","text":"尚志市","pid":"黑龙江省"},{"id":"五常市","text":"五常市","pid":"黑龙江省"},{"id":"讷河市","text":"讷河市","pid":"黑龙江省"},{"id":"北安市","text":"北安市","pid":"黑龙江省"},{"id":"五大连池市","text":"五大连池市","pid":"黑龙江省"},{"id":"铁力市","text":"铁力市","pid":"黑龙江省"},{"id":"同江市","text":"同江市","pid":"黑龙江省"},{"id":"富锦市","text":"富锦市","pid":"黑龙江省"},{"id":"虎林市","text":"虎林市","pid":"黑龙江省"},{"id":"密山市","text":"密山市","pid":"黑龙江省"},{"id":"绥芬河市","text":"绥芬河市","pid":"黑龙江省"},{"id":"海林市","text":"海林市","pid":"黑龙江省"},{"id":"宁安市","text":"宁安市","pid":"黑龙江省"},{"id":"安达市","text":"安达市","pid":"黑龙江省"},{"id":"肇东市","text":"肇东市","pid":"黑龙江省"},{"id":"海伦市","text":"海伦市","pid":"黑龙江省"},{"id":"穆棱市","text":"穆棱市","pid":"黑龙江省"},{"id":"东宁市","text":"东宁市","pid":"黑龙江省"},{"id":"抚远市","text":"抚远市","pid":"黑龙江省"},{"id":"江苏省","text":"江苏省"},{"id":"南京市","text":"南京市","pid":"江苏省"},{"id":"徐州市","text":"徐州市","pid":"江苏省"},{"id":"连云港市","text":"连云港市","pid":"江苏省"},{"id":"宿迁市","text":"宿迁市","pid":"江苏省"},{"id":"淮安市","text":"淮安市","pid":"江苏省"},{"id":"盐城市","text":"盐城市","pid":"江苏省"},{"id":"扬州市","text":"扬州市","pid":"江苏省"},{"id":"泰州市","text":"泰州市","pid":"江苏省"},{"id":"南通市","text":"南通市","pid":"江苏省"},{"id":"镇江市","text":"镇江市","pid":"江苏省"},{"id":"常州市","text":"常州市","pid":"江苏省"},{"id":"无锡市","text":"无锡市","pid":"江苏省"},{"id":"苏州市","text":"苏州市","pid":"江苏省"},{"id":"常熟市","text":"常熟市","pid":"江苏省"},{"id":"张家港市","text":"张家港市","pid":"江苏省"},{"id":"太仓市","text":"太仓市","pid":"江苏省"},{"id":"昆山市","text":"昆山市","pid":"江苏省"},{"id":"江阴市","text":"江阴市","pid":"江苏省"},{"id":"宜兴市","text":"宜兴市","pid":"江苏省"},{"id":"溧阳市","text":"溧阳市","pid":"江苏省"},{"id":"扬中市","text":"扬中市","pid":"江苏省"},{"id":"句容市","text":"句容市","pid":"江苏省"},{"id":"丹阳市","text":"丹阳市","pid":"江苏省"},{"id":"如皋市","text":"如皋市","pid":"江苏省"},{"id":"海门市","text":"海门市","pid":"江苏省"},{"id":"启东市","text":"启东市","pid":"江苏省"},{"id":"高邮市","text":"高邮市","pid":"江苏省"},{"id":"仪征市","text":"仪征市","pid":"江苏省"},{"id":"兴化市","text":"兴化市","pid":"江苏省"},{"id":"泰兴市","text":"泰兴市","pid":"江苏省"},{"id":"靖江市","text":"靖江市","pid":"江苏省"},{"id":"东台市","text":"东台市","pid":"江苏省"},{"id":"邳州市","text":"邳州市","pid":"江苏省"},{"id":"新沂市","text":"新沂市","pid":"江苏省"},{"id":"浙江省","text":"浙江省"},{"id":"杭州市","text":"杭州市","pid":"浙江省"},{"id":"宁波市","text":"宁波市","pid":"浙江省"},{"id":"湖州市","text":"湖州市","pid":"浙江省"},{"id":"嘉兴市","text":"嘉兴市","pid":"浙江省"},{"id":"舟山市","text":"舟山市","pid":"浙江省"},{"id":"绍兴市","text":"绍兴市","pid":"浙江省"},{"id":"衢州市","text":"衢州市","pid":"浙江省"},{"id":"金华市","text":"金华市","pid":"浙江省"},{"id":"台州市","text":"台州市","pid":"浙江省"},{"id":"温州市","text":"温州市","pid":"浙江省"},{"id":"丽水市","text":"丽水市","pid":"浙江省"},{"id":"临安市","text":"临安市","pid":"浙江省"},{"id":"建德市","text":"建德市","pid":"浙江省"},{"id":"慈溪市","text":"慈溪市","pid":"浙江省"},{"id":"余姚市","text":"余姚市","pid":"浙江省"},{"id":"平湖市","text":"平湖市","pid":"浙江省"},{"id":"海宁市","text":"海宁市","pid":"浙江省"},{"id":"桐乡市","text":"桐乡市","pid":"浙江省"},{"id":"诸暨市","text":"诸暨市","pid":"浙江省"},{"id":"嵊州市","text":"嵊州市","pid":"浙江省"},{"id":"江山市","text":"江山市","pid":"浙江省"},{"id":"兰溪市","text":"兰溪市","pid":"浙江省"},{"id":"永康市","text":"永康市","pid":"浙江省"},{"id":"义乌市","text":"义乌市","pid":"浙江省"},{"id":"东阳市","text":"东阳市","pid":"浙江省"},{"id":"临海市","text":"临海市","pid":"浙江省"},{"id":"温岭市","text":"温岭市","pid":"浙江省"},{"id":"瑞安市","text":"瑞安市","pid":"浙江省"},{"id":"乐清市","text":"乐清市","pid":"浙江省"},{"id":"龙泉市","text":"龙泉市","pid":"浙江省"},{"id":"安徽省","text":"安徽省"},{"id":"合肥市","text":"合肥市","pid":"安徽省"},{"id":"芜湖市","text":"芜湖市","pid":"安徽省"},{"id":"蚌埠市","text":"蚌埠市","pid":"安徽省"},{"id":"淮南市","text":"淮南市","pid":"安徽省"},{"id":"马鞍山市","text":"马鞍山市","pid":"安徽省"},{"id":"淮北市","text":"淮北市","pid":"安徽省"},{"id":"铜陵市","text":"铜陵市","pid":"安徽省"},{"id":"安庆市","text":"安庆市","pid":"安徽省"},{"id":"黄山市","text":"黄山市","pid":"安徽省"},{"id":"滁州市","text":"滁州市","pid":"安徽省"},{"id":"阜阳市","text":"阜阳市","pid":"安徽省"},{"id":"宿州市","text":"宿州市","pid":"安徽省"},{"id":"六安市","text":"六安市","pid":"安徽省"},{"id":"亳州市","text":"亳州市","pid":"安徽省"},{"id":"池州市","text":"池州市","pid":"安徽省"},{"id":"宣城市","text":"宣城市","pid":"安徽省"},{"id":"巢湖市","text":"巢湖市","pid":"安徽省"},{"id":"桐城市","text":"桐城市","pid":"安徽省"},{"id":"天长市","text":"天长市","pid":"安徽省"},{"id":"明光市","text":"明光市","pid":"安徽省"},{"id":"界首市","text":"界首市","pid":"安徽省"},{"id":"宁国市","text":"宁国市","pid":"安徽省"},{"id":"福建省","text":"福建省"},{"id":"厦门市","text":"厦门市","pid":"福建省"},{"id":"福州市","text":"福州市","pid":"福建省"},{"id":"南平市","text":"南平市","pid":"福建省"},{"id":"三明市","text":"三明市","pid":"福建省"},{"id":"莆田市","text":"莆田市","pid":"福建省"},{"id":"泉州市","text":"泉州市","pid":"福建省"},{"id":"漳州市","text":"漳州市","pid":"福建省"},{"id":"龙岩市","text":"龙岩市","pid":"福建省"},{"id":"宁德市","text":"宁德市","pid":"福建省"},{"id":"福清市","text":"福清市","pid":"福建省"},{"id":"长乐市","text":"长乐市","pid":"福建省"},{"id":"邵武市","text":"邵武市","pid":"福建省"},{"id":"武夷山市","text":"武夷山市","pid":"福建省"},{"id":"建瓯市","text":"建瓯市","pid":"福建省"},{"id":"永安市","text":"永安市","pid":"福建省"},{"id":"石狮市","text":"石狮市","pid":"福建省"},{"id":"晋江市","text":"晋江市","pid":"福建省"},{"id":"南安市","text":"南安市","pid":"福建省"},{"id":"龙海市","text":"龙海市","pid":"福建省"},{"id":"漳平市","text":"漳平市","pid":"福建省"},{"id":"福安市","text":"福安市","pid":"福建省"},{"id":"福鼎市","text":"福鼎市","pid":"福建省"},{"id":"江西省","text":"江西省"},{"id":"南昌市","text":"南昌市","pid":"江西省"},{"id":"九江市","text":"九江市","pid":"江西省"},{"id":"景德镇市","text":"景德镇市","pid":"江西省"},{"id":"鹰潭市","text":"鹰潭市","pid":"江西省"},{"id":"新余市","text":"新余市","pid":"江西省"},{"id":"萍乡市","text":"萍乡市","pid":"江西省"},{"id":"赣州市","text":"赣州市","pid":"江西省"},{"id":"上饶市","text":"上饶市","pid":"江西省"},{"id":"抚州市","text":"抚州市","pid":"江西省"},{"id":"宜春市","text":"宜春市","pid":"江西省"},{"id":"吉安市","text":"吉安市","pid":"江西省"},{"id":"瑞昌市","text":"瑞昌市","pid":"江西省"},{"id":"共青城市","text":"共青城市","pid":"江西省"},{"id":"乐平市","text":"乐平市","pid":"江西省"},{"id":"瑞金市","text":"瑞金市","pid":"江西省"},{"id":"德兴市","text":"德兴市","pid":"江西省"},{"id":"丰城市","text":"丰城市","pid":"江西省"},{"id":"樟树市","text":"樟树市","pid":"江西省"},{"id":"高安市","text":"高安市","pid":"江西省"},{"id":"井冈山市","text":"井冈山市","pid":"江西省"},{"id":"贵溪市","text":"贵溪市","pid":"江西省"},{"id":"山东省","text":"山东省"},{"id":"济南市","text":"济南市","pid":"山东省"},{"id":"青岛市","text":"青岛市","pid":"山东省"},{"id":"聊城市","text":"聊城市","pid":"山东省"},{"id":"德州市","text":"德州市","pid":"山东省"},{"id":"东营市","text":"东营市","pid":"山东省"},{"id":"淄博市","text":"淄博市","pid":"山东省"},{"id":"潍坊市","text":"潍坊市","pid":"山东省"},{"id":"烟台市","text":"烟台市","pid":"山东省"},{"id":"威海市","text":"威海市","pid":"山东省"},{"id":"日照市","text":"日照市","pid":"山东省"},{"id":"临沂市","text":"临沂市","pid":"山东省"},{"id":"枣庄市","text":"枣庄市","pid":"山东省"},{"id":"济宁市","text":"济宁市","pid":"山东省"},{"id":"泰安市","text":"泰安市","pid":"山东省"},{"id":"莱芜市","text":"莱芜市","pid":"山东省"},{"id":"滨州市","text":"滨州市","pid":"山东省"},{"id":"菏泽市","text":"菏泽市","pid":"山东省"},{"id":"胶州市","text":"胶州市","pid":"山东省"},{"id":"即墨市","text":"即墨市","pid":"山东省"},{"id":"平度市","text":"平度市","pid":"山东省"},{"id":"莱西市","text":"莱西市","pid":"山东省"},{"id":"临清市","text":"临清市","pid":"山东省"},{"id":"乐陵市","text":"乐陵市","pid":"山东省"},{"id":"禹城市","text":"禹城市","pid":"山东省"},{"id":"安丘市","text":"安丘市","pid":"山东省"},{"id":"昌邑市","text":"昌邑市","pid":"山东省"},{"id":"高密市","text":"高密市","pid":"山东省"},{"id":"青州市","text":"青州市","pid":"山东省"},{"id":"诸城市","text":"诸城市","pid":"山东省"},{"id":"寿光市","text":"寿光市","pid":"山东省"},{"id":"栖霞市","text":"栖霞市","pid":"山东省"},{"id":"海阳市","text":"海阳市","pid":"山东省"},{"id":"龙口市","text":"龙口市","pid":"山东省"},{"id":"莱阳市","text":"莱阳市","pid":"山东省"},{"id":"莱州市","text":"莱州市","pid":"山东省"},{"id":"蓬莱市","text":"蓬莱市","pid":"山东省"},{"id":"招远市","text":"招远市","pid":"山东省"},{"id":"荣成市","text":"荣成市","pid":"山东省"},{"id":"乳山市","text":"乳山市","pid":"山东省"},{"id":"滕州市","text":"滕州市","pid":"山东省"},{"id":"曲阜市","text":"曲阜市","pid":"山东省"},{"id":"邹城市","text":"邹城市","pid":"山东省"},{"id":"新泰市","text":"新泰市","pid":"山东省"},{"id":"肥城市","text":"肥城市","pid":"山东省"},{"id":"河南省","text":"河南省"},{"id":"郑州市","text":"郑州市","pid":"河南省"},{"id":"开封市","text":"开封市","pid":"河南省"},{"id":"洛阳市","text":"洛阳市","pid":"河南省"},{"id":"平顶山市","text":"平顶山市","pid":"河南省"},{"id":"安阳市","text":"安阳市","pid":"河南省"},{"id":"鹤壁市","text":"鹤壁市","pid":"河南省"},{"id":"新乡市","text":"新乡市","pid":"河南省"},{"id":"焦作市","text":"焦作市","pid":"河南省"},{"id":"濮阳市","text":"濮阳市","pid":"河南省"},{"id":"许昌市","text":"许昌市","pid":"河南省"},{"id":"漯河市","text":"漯河市","pid":"河南省"},{"id":"三门峡市","text":"三门峡市","pid":"河南省"},{"id":"南阳市","text":"南阳市","pid":"河南省"},{"id":"商丘市","text":"商丘市","pid":"河南省"},{"id":"周口市","text":"周口市","pid":"河南省"},{"id":"驻马店市","text":"驻马店市","pid":"河南省"},{"id":"信阳市","text":"信阳市","pid":"河南省"},{"id":"荥阳市","text":"荥阳市","pid":"河南省"},{"id":"新郑市","text":"新郑市","pid":"河南省"},{"id":"登封市","text":"登封市","pid":"河南省"},{"id":"新密市","text":"新密市","pid":"河南省"},{"id":"偃师市","text":"偃师市","pid":"河南省"},{"id":"孟州市","text":"孟州市","pid":"河南省"},{"id":"沁阳市","text":"沁阳市","pid":"河南省"},{"id":"卫辉市","text":"卫辉市","pid":"河南省"},{"id":"辉县市","text":"辉县市","pid":"河南省"},{"id":"林州市","text":"林州市","pid":"河南省"},{"id":"禹州市","text":"禹州市","pid":"河南省"},{"id":"长葛市","text":"长葛市","pid":"河南省"},{"id":"舞钢市","text":"舞钢市","pid":"河南省"},{"id":"义马市","text":"义马市","pid":"河南省"},{"id":"灵宝市","text":"灵宝市","pid":"河南省"},{"id":"项城市","text":"项城市","pid":"河南省"},{"id":"巩义市","text":"巩义市","pid":"河南省"},{"id":"邓州市","text":"邓州市","pid":"河南省"},{"id":"永城市","text":"永城市","pid":"河南省"},{"id":"汝州市","text":"汝州市","pid":"河南省"},{"id":"济源市","text":"济源市","pid":"河南省"},{"id":"湖北省","text":"湖北省"},{"id":"武汉市","text":"武汉市","pid":"湖北省"},{"id":"十堰市","text":"十堰市","pid":"湖北省"},{"id":"襄阳市","text":"襄阳市","pid":"湖北省"},{"id":"荆门市","text":"荆门市","pid":"湖北省"},{"id":"孝感市","text":"孝感市","pid":"湖北省"},{"id":"黄冈市","text":"黄冈市","pid":"湖北省"},{"id":"鄂州市","text":"鄂州市","pid":"湖北省"},{"id":"黄石市","text":"黄石市","pid":"湖北省"},{"id":"咸宁市","text":"咸宁市","pid":"湖北省"},{"id":"荆州市","text":"荆州市","pid":"湖北省"},{"id":"宜昌市","text":"宜昌市","pid":"湖北省"},{"id":"随州市","text":"随州市","pid":"湖北省"},{"id":"丹江口市","text":"丹江口市","pid":"湖北省"},{"id":"老河口市","text":"老河口市","pid":"湖北省"},{"id":"枣阳市","text":"枣阳市","pid":"湖北省"},{"id":"宜城市","text":"宜城市","pid":"湖北省"},{"id":"钟祥市","text":"钟祥市","pid":"湖北省"},{"id":"汉川市","text":"汉川市","pid":"湖北省"},{"id":"应城市","text":"应城市","pid":"湖北省"},{"id":"安陆市","text":"安陆市","pid":"湖北省"},{"id":"广水市","text":"广水市","pid":"湖北省"},{"id":"麻城市","text":"麻城市","pid":"湖北省"},{"id":"武穴市","text":"武穴市","pid":"湖北省"},{"id":"大冶市","text":"大冶市","pid":"湖北省"},{"id":"赤壁市","text":"赤壁市","pid":"湖北省"},{"id":"石首市","text":"石首市","pid":"湖北省"},{"id":"洪湖市","text":"洪湖市","pid":"湖北省"},{"id":"松滋市","text":"松滋市","pid":"湖北省"},{"id":"宜都市","text":"宜都市","pid":"湖北省"},{"id":"枝江市","text":"枝江市","pid":"湖北省"},{"id":"当阳市","text":"当阳市","pid":"湖北省"},{"id":"恩施市","text":"恩施市","pid":"湖北省"},{"id":"利川市","text":"利川市","pid":"湖北省"},{"id":"仙桃市","text":"仙桃市","pid":"湖北省"},{"id":"天门市","text":"天门市","pid":"湖北省"},{"id":"潜江市","text":"潜江市","pid":"湖北省"},{"id":"湖南省","text":"湖南省"},{"id":"长沙市","text":"长沙市","pid":"湖南省"},{"id":"衡阳市","text":"衡阳市","pid":"湖南省"},{"id":"张家界市","text":"张家界市","pid":"湖南省"},{"id":"常德市","text":"常德市","pid":"湖南省"},{"id":"益阳市","text":"益阳市","pid":"湖南省"},{"id":"岳阳市","text":"岳阳市","pid":"湖南省"},{"id":"株洲市","text":"株洲市","pid":"湖南省"},{"id":"湘潭市","text":"湘潭市","pid":"湖南省"},{"id":"郴州市","text":"郴州市","pid":"湖南省"},{"id":"永州市","text":"永州市","pid":"湖南省"},{"id":"邵阳市","text":"邵阳市","pid":"湖南省"},{"id":"怀化市","text":"怀化市","pid":"湖南省"},{"id":"娄底市","text":"娄底市","pid":"湖南省"},{"id":"耒阳市","text":"耒阳市","pid":"湖南省"},{"id":"常宁市","text":"常宁市","pid":"湖南省"},{"id":"浏阳市","text":"浏阳市","pid":"湖南省"},{"id":"津市市","text":"津市市","pid":"湖南省"},{"id":"沅江市","text":"沅江市","pid":"湖南省"},{"id":"汨罗市","text":"汨罗市","pid":"湖南省"},{"id":"临湘市","text":"临湘市","pid":"湖南省"},{"id":"醴陵市","text":"醴陵市","pid":"湖南省"},{"id":"湘乡市","text":"湘乡市","pid":"湖南省"},{"id":"韶山市","text":"韶山市","pid":"湖南省"},{"id":"资兴市","text":"资兴市","pid":"湖南省"},{"id":"武冈市","text":"武冈市","pid":"湖南省"},{"id":"洪江市","text":"洪江市","pid":"湖南省"},{"id":"冷水江市","text":"冷水江市","pid":"湖南省"},{"id":"涟源市","text":"涟源市","pid":"湖南省"},{"id":"吉首市","text":"吉首市","pid":"湖南省"},{"id":"四川省","text":"四川省"},{"id":"成都市","text":"成都市","pid":"四川省"},{"id":"广元市","text":"广元市","pid":"四川省"},{"id":"绵阳市","text":"绵阳市","pid":"四川省"},{"id":"德阳市","text":"德阳市","pid":"四川省"},{"id":"南充市","text":"南充市","pid":"四川省"},{"id":"广安市","text":"广安市","pid":"四川省"},{"id":"遂宁市","text":"遂宁市","pid":"四川省"},{"id":"内江市","text":"内江市","pid":"四川省"},{"id":"乐山市","text":"乐山市","pid":"四川省"},{"id":"自贡市","text":"自贡市","pid":"四川省"},{"id":"泸州市","text":"泸州市","pid":"四川省"},{"id":"宜宾市","text":"宜宾市","pid":"四川省"},{"id":"攀枝花市","text":"攀枝花市","pid":"四川省"},{"id":"巴中市","text":"巴中市","pid":"四川省"},{"id":"达州市","text":"达州市","pid":"四川省"},{"id":"资阳市","text":"资阳市","pid":"四川省"},{"id":"眉山市","text":"眉山市","pid":"四川省"},{"id":"雅安市","text":"雅安市","pid":"四川省"},{"id":"崇州市","text":"崇州市","pid":"四川省"},{"id":"邛崃市","text":"邛崃市","pid":"四川省"},{"id":"都江堰市","text":"都江堰市","pid":"四川省"},{"id":"彭州市","text":"彭州市","pid":"四川省"},{"id":"江油市","text":"江油市","pid":"四川省"},{"id":"什邡市","text":"什邡市","pid":"四川省"},{"id":"广汉市","text":"广汉市","pid":"四川省"},{"id":"绵竹市","text":"绵竹市","pid":"四川省"},{"id":"阆中市","text":"阆中市","pid":"四川省"},{"id":"华蓥市","text":"华蓥市","pid":"四川省"},{"id":"峨眉山市","text":"峨眉山市","pid":"四川省"},{"id":"万源市","text":"万源市","pid":"四川省"},{"id":"简阳市","text":"简阳市","pid":"四川省"},{"id":"西昌市","text":"西昌市","pid":"四川省"},{"id":"康定市","text":"康定市","pid":"四川省"},{"id":"马尔康市","text":"马尔康市","pid":"四川省"},{"id":"贵州省","text":"贵州省"},{"id":"贵阳市","text":"贵阳市","pid":"贵州省"},{"id":"六盘水市","text":"六盘水市","pid":"贵州省"},{"id":"遵义市","text":"遵义市","pid":"贵州省"},{"id":"安顺市","text":"安顺市","pid":"贵州省"},{"id":"毕节市","text":"毕节市","pid":"贵州省"},{"id":"铜仁市","text":"铜仁市","pid":"贵州省"},{"id":"清镇市","text":"清镇市","pid":"贵州省"},{"id":"赤水市","text":"赤水市","pid":"贵州省"},{"id":"仁怀市","text":"仁怀市","pid":"贵州省"},{"id":"凯里市","text":"凯里市","pid":"贵州省"},{"id":"都匀市","text":"都匀市","pid":"贵州省"},{"id":"兴义市","text":"兴义市","pid":"贵州省"},{"id":"福泉市","text":"福泉市","pid":"贵州省"},{"id":"云南省","text":"云南省"},{"id":"昆明市","text":"昆明市","pid":"云南省"},{"id":"曲靖市","text":"曲靖市","pid":"云南省"},{"id":"玉溪市","text":"玉溪市","pid":"云南省"},{"id":"丽江市","text":"丽江市","pid":"云南省"},{"id":"昭通市","text":"昭通市","pid":"云南省"},{"id":"普洱市","text":"普洱市","pid":"云南省"},{"id":"临沧市","text":"临沧市","pid":"云南省"},{"id":"保山市","text":"保山市","pid":"云南省"},{"id":"安宁市","text":"安宁市","pid":"云南省"},{"id":"宣威市","text":"宣威市","pid":"云南省"},{"id":"芒市","text":"芒市","pid":"云南省"},{"id":"瑞丽市","text":"瑞丽市","pid":"云南省"},{"id":"大理市","text":"大理市","pid":"云南省"},{"id":"楚雄市","text":"楚雄市","pid":"云南省"},{"id":"个旧市","text":"个旧市","pid":"云南省"},{"id":"开远市","text":"开远市","pid":"云南省"},{"id":"蒙自市","text":"蒙自市","pid":"云南省"},{"id":"弥勒市","text":"弥勒市","pid":"云南省"},{"id":"景洪市","text":"景洪市","pid":"云南省"},{"id":"文山市","text":"文山市","pid":"云南省"},{"id":"香格里拉市","text":"香格里拉市","pid":"云南省"},{"id":"腾冲市","text":"腾冲市","pid":"云南省"},{"id":"陕西省","text":"陕西省"},{"id":"西安市","text":"西安市","pid":"陕西省"},{"id":"延安市","text":"延安市","pid":"陕西省"},{"id":"铜川市","text":"铜川市","pid":"陕西省"},{"id":"渭南市","text":"渭南市","pid":"陕西省"},{"id":"咸阳市","text":"咸阳市","pid":"陕西省"},{"id":"宝鸡市","text":"宝鸡市","pid":"陕西省"},{"id":"汉中市","text":"汉中市","pid":"陕西省"},{"id":"榆林市","text":"榆林市","pid":"陕西省"},{"id":"商洛市","text":"商洛市","pid":"陕西省"},{"id":"安康市","text":"安康市","pid":"陕西省"},{"id":"韩城","text":"韩城","pid":"陕西省"},{"id":"华阴","text":"华阴","pid":"陕西省"},{"id":"兴平","text":"兴平","pid":"陕西省"},{"id":"甘肃省","text":"甘肃省"},{"id":"兰州市","text":"兰州市","pid":"甘肃省"},{"id":"嘉峪关市","text":"嘉峪关市","pid":"甘肃省"},{"id":"金昌市","text":"金昌市","pid":"甘肃省"},{"id":"白银市","text":"白银市","pid":"甘肃省"},{"id":"天水市","text":"天水市","pid":"甘肃省"},{"id":"酒泉市","text":"酒泉市","pid":"甘肃省"},{"id":"张掖市","text":"张掖市","pid":"甘肃省"},{"id":"武威市","text":"武威市","pid":"甘肃省"},{"id":"庆阳市","text":"庆阳市","pid":"甘肃省"},{"id":"平凉市","text":"平凉市","pid":"甘肃省"},{"id":"定西市","text":"定西市","pid":"甘肃省"},{"id":"陇南市","text":"陇南市","pid":"甘肃省"},{"id":"玉门市","text":"玉门市","pid":"甘肃省"},{"id":"敦煌市","text":"敦煌市","pid":"甘肃省"},{"id":"临夏市","text":"临夏市","pid":"甘肃省"},{"id":"合作市","text":"合作市","pid":"甘肃省"},{"id":"青海省","text":"青海省"},{"id":"西宁市","text":"西宁市","pid":"青海省"},{"id":"海东市","text":"海东市","pid":"青海省"},{"id":"格尔木市","text":"格尔木市","pid":"青海省"},{"id":"德令哈市","text":"德令哈市","pid":"青海省"},{"id":"玉树市","text":"玉树市","pid":"青海省"},{"id":"西藏自治区","text":"西藏自治区"},{"id":"拉萨市","text":"拉萨市","pid":"西藏自治区"},{"id":"日喀则市","text":"日喀则市","pid":"西藏自治区"},{"id":"昌都市","text":"昌都市","pid":"西藏自治区"},{"id":"林芝市","text":"林芝市","pid":"西藏自治区"},{"id":"山南市","text":"山南市","pid":"西藏自治区"},{"id":"宁夏回族自治区","text":"宁夏回族自治区"},{"id":"银川市","text":"银川市","pid":"宁夏回族自治区"},{"id":"石嘴山市","text":"石嘴山市","pid":"宁夏回族自治区"},{"id":"吴忠市","text":"吴忠市","pid":"宁夏回族自治区"},{"id":"中卫市","text":"中卫市","pid":"宁夏回族自治区"},{"id":"固原市","text":"固原市","pid":"宁夏回族自治区"},{"id":"灵武市","text":"灵武市","pid":"宁夏回族自治区"},{"id":"青铜峡市","text":"青铜峡市","pid":"宁夏回族自治区"},{"id":"内蒙古自治区","text":"内蒙古自治区"},{"id":"呼和浩特市","text":"呼和浩特市","pid":"内蒙古自治区"},{"id":"包头市","text":"包头市","pid":"内蒙古自治区"},{"id":"乌海市","text":"乌海市","pid":"内蒙古自治区"},{"id":"赤峰市","text":"赤峰市","pid":"内蒙古自治区"},{"id":"呼伦贝尔市","text":"呼伦贝尔市","pid":"内蒙古自治区"},{"id":"通辽市","text":"通辽市","pid":"内蒙古自治区"},{"id":"乌兰察布市","text":"乌兰察布市","pid":"内蒙古自治区"},{"id":"鄂尔多斯市","text":"鄂尔多斯市","pid":"内蒙古自治区"},{"id":"巴彦淖尔市","text":"巴彦淖尔市","pid":"内蒙古自治区"},{"id":"满洲里市","text":"满洲里市","pid":"内蒙古自治区"},{"id":"扎兰屯市","text":"扎兰屯市","pid":"内蒙古自治区"},{"id":"牙克石市","text":"牙克石市","pid":"内蒙古自治区"},{"id":"根河市","text":"根河市","pid":"内蒙古自治区"},{"id":"额尔古纳市","text":"额尔古纳市","pid":"内蒙古自治区"},{"id":"乌兰浩特市","text":"乌兰浩特市","pid":"内蒙古自治区"},{"id":"阿尔山市","text":"阿尔山市","pid":"内蒙古自治区"},{"id":"霍林郭勒市","text":"霍林郭勒市","pid":"内蒙古自治区"},{"id":"锡林浩特市","text":"锡林浩特市","pid":"内蒙古自治区"},{"id":"二连浩特市","text":"二连浩特市","pid":"内蒙古自治区"},{"id":"丰镇市","text":"丰镇市","pid":"内蒙古自治区"},{"id":"新疆维吾尔自治区","text":"新疆维吾尔自治区"},{"id":"乌鲁木齐市","text":"乌鲁木齐市","pid":"新疆维吾尔自治区"},{"id":"克拉玛依市","text":"克拉玛依市","pid":"新疆维吾尔自治区"},{"id":"吐鲁番市","text":"吐鲁番市","pid":"新疆维吾尔自治区"},{"id":"哈密市","text":"哈密市","pid":"新疆维吾尔自治区"},{"id":"喀什市","text":"喀什市","pid":"新疆维吾尔自治区"},{"id":"阿克苏市","text":"阿克苏市","pid":"新疆维吾尔自治区"},{"id":"和田市","text":"和田市","pid":"新疆维吾尔自治区"},{"id":"阿图什市","text":"阿图什市","pid":"新疆维吾尔自治区"},{"id":"阿拉山口市","text":"阿拉山口市","pid":"新疆维吾尔自治区"},{"id":"博乐市","text":"博乐市","pid":"新疆维吾尔自治区"},{"id":"昌吉市","text":"昌吉市","pid":"新疆维吾尔自治区"},{"id":"阜康市","text":"阜康市","pid":"新疆维吾尔自治区"},{"id":"库尔勒市","text":"库尔勒市","pid":"新疆维吾尔自治区"},{"id":"伊宁市","text":"伊宁市","pid":"新疆维吾尔自治区"},{"id":"奎屯市","text":"奎屯市","pid":"新疆维吾尔自治区"},{"id":"塔城市","text":"塔城市","pid":"新疆维吾尔自治区"},{"id":"乌苏市","text":"乌苏市","pid":"新疆维吾尔自治区"},{"id":"阿勒泰市","text":"阿勒泰市","pid":"新疆维吾尔自治区"},{"id":"霍尔果斯市","text":"霍尔果斯市","pid":"新疆维吾尔自治区"},{"id":"石河子市","text":"石河子市","pid":"新疆维吾尔自治区"},{"id":"阿拉尔市","text":"阿拉尔市","pid":"新疆维吾尔自治区"},{"id":"图木舒克市","text":"图木舒克市","pid":"新疆维吾尔自治区"},{"id":"五家渠市","text":"五家渠市","pid":"新疆维吾尔自治区"},{"id":"北屯市","text":"北屯市","pid":"新疆维吾尔自治区"},{"id":"铁门关市","text":"铁门关市","pid":"新疆维吾尔自治区"},{"id":"双河市","text":"双河市","pid":"新疆维吾尔自治区"},{"id":"可克达拉市","text":"可克达拉市","pid":"新疆维吾尔自治区"},{"id":"昆玉市","text":"昆玉市","pid":"新疆维吾尔自治区"},{"id":"台湾","text":"台湾"},{"id":"台北市","text":"台北市","pid":"台湾"},{"id":"新北市","text":"新北市","pid":"台湾"},{"id":"桃园市","text":"桃园市","pid":"台湾"},{"id":"台中市","text":"台中市","pid":"台湾"},{"id":"台南市","text":"台南市","pid":"台湾"},{"id":"高雄市","text":"高雄市","pid":"台湾"},{"id":"基隆市","text":"基隆市","pid":"台湾"},{"id":"新竹市","text":"新竹市","pid":"台湾"},{"id":"嘉义市","text":"嘉义市","pid":"台湾"}]
	;
	mini.get("hideAreaSelect").loadList(areaJson, 'id', 'pid');
});
</script>
</html>
