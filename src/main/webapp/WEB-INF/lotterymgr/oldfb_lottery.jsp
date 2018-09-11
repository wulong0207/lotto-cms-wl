<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/top.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=basePath%>resources/js/public/boot.js" type="text/javascript"></script>
    <title>老足彩管理</title>
    <style type="text/css">
        table{
            border-collapse:collapse;
        }
        .table-bor td{
            border: 1px solid #CCCCCC;

        }

        .asLabel .mini-textbox-border,
        .asLabel .mini-textbox-input,
        .asLabel .mini-buttonedit-border,
        .asLabel .mini-buttonedit-input,
        .asLabel .mini-textboxlist-border
        {
            border:0;background:none;cursor:default;
        }
        .asLabel .mini-buttonedit-button,
        .asLabel .mini-textboxlist-close
        {
            display:none;
        }
        .asLabel .mini-textboxlist-item
        {
            padding-right:8px;
        }
    </style>
</head>
<body>
<div class="mini-toolbar" style="border-top:0;border-left:0;padding:0px;">
    <div id="form">
        <table style="width:100%;">
            <tr>
                <td width="15%" >
                
                     彩种：<input id="lotteryCode"  name="lotteryCode" class="mini-combobox"  style="width:150px;" emptyText="请选择彩种"   allowInput="true" valueFromSelect = "true"  oncloseclick="lottery_oldfb.comboxOnCloseClick" showClose="true" onvaluechanged="lottery_oldfb.lotteryCodeChange"/>
                      
                     彩期：<input id="lotteryIssue" name="lotteryIssue" class="mini-combobox"  style="width:150px;" popupHeight="670px;" popupMaxHeight="670px;" emptyText="请选择彩种"   allowInput="true" oncloseclick="lottery_oldfb.comboxOnCloseClick" showClose="true"> 
                               
                    <a class="mini-button" onclick="lottery_oldfb.runTask(1, this)">获取比赛</a>
                    <a class="mini-button" onclick="lottery_oldfb.runTask(2, this)">同步分析数据</a>
                    <a class="mini-button" onclick="lottery_oldfb.runTask(3, this)">过关统计</a>
  <!--                   <a class="mini-button" onclick="lottery_oldfb.runTask(4)">同步所有数据</a> -->
                    <a class="mini-button" onclick="lottery_oldfb.runTask(5, this)">抓取比分</a>
<!--                     <a class="mini-button" onclick="lottery_oldfb.runTask(6)">审核比分与彩果</a> -->
                    <btn:operate privilege="EXPORT">
			        	<a class="mini-button" iconCls="icon-download" plain="true" onclick="lottery_oldfb.excel()">导出</a>
			        </btn:operate>
                </td>
            </tr>
            <tr>
                <td width="5%" style="white-space:nowrap;">
                    系统编号： <input id="systemCode" name="systemCode"value="" class="mini-textbox" style="width:100px;"/>
                    彩期状态：<input id="issueStatus" name="issueStatus" onCloseClick="lottery_oldfb.comboxOnCloseClick" data="Dic.matchStatus" value="" class="mini-combobox" style="width:150px;"
                           emptyText="请选择"  allowInput="true" valueFromSelect="true" showClose="true" nullItemText="请选择..." showNullItem="false"/>
                    赛事状态：<input id="matchStatus1" name="matchStatus" onCloseClick="lottery_oldfb.comboxOnCloseClick" data="Dic.matchStatus" value="" class="mini-combobox" style="width:150px;"
                                emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" nullItemText="请选择..." showNullItem="false"/>
                </td>
            </tr>
            <tr>
                <td>
                    官方ID： <input id="officialId1" name="officialId" value="" class="mini-textbox" style="width:100px;"/>
                    分析ID： <input id="analysisId" name="analysisId" value="" class="mini-textbox" style="width:100px;"/>
                    比赛时间： <input id="matchBeginTime" name="matchBeginTime" class="mini-datepicker" allowInput="false" style="width:200px;"
                                 format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true"
                                 showClearButton="false"/>
                    到<input id="matchEndTime" name="matchEndTime" class="mini-datepicker" allowInput="false" style="width:200px;"
                            format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true"
                            showClearButton="false"/>
                    <a class="mini-button" onclick="lottery_oldfb.search()">查询</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div class="mini-splitter" vertical="true" style="width:100%;height:100%;" style="border:0;">
    <div size="90%" showCollapseButton="true" style="border:0;">
        <div id="datagrid" class="mini-datagrid" onpreload="MiniCom.onpreload" allowAlternating="true"  borderStyle="border:solid 0px;"
             style="width:100%;height:100%;"
             url="lotterymgr/old_fb/list" idField="againstId" allowResize="true" pageSize="30" multiSelect="true"
             showColumnsMenu="true" showFilterRow="false" showFilterRow="false"  showPager="true"
             onrowdblclick="Cms.editRow('datagrid')" oncellbeginedit="SportUtil.onSportCellBeingEdit">
            <div property="columns">
                <div type="checkcolumn" width="30"></div>
                <div type="indexcolumn" width="30" align="center" headerAlign="center">序号</div>
                <div field="issueCode" headerAlign="center" align="center" width="60">彩期
                </div>
                <div field="saleStatus" type="comboboxcolumn" headerAlign="center" align="center" width="50">彩期状态
                    <input  property="editor"   class="mini-combobox" data="Dic.saleStatus"  allowInput="false"/>
                </div>
                <div field="systemCode" headerAlign="center" align="center" width="60">系统编号
                </div>
                <div field="startTime" property="editor" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">
					比赛时间
                    <input name="startTime"  property="editor"  class="mini-datepicker" allowInput="false" style="width:200px;"
                           format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true"
                           showClearButton="false"/>
                </div>
                <div header="赛事信息" headerAlign="center" align="center">
                	<div property="columns">
						<div name="matchPrimaryId" field="matchPrimaryId" headerAlign="center" align="center" width="40">赛事ID
							<input property="editor" class="mini-textbox asLabel" allowInput="false"/>
						</div>
						<div field="matchFullName" headerAlign="center" align="center" renderer="SportUtil.getMatchName" width="60">赛事全称
							<div property="editor" class="mini-autocomplete" popupWidth="300" onitemclick="SportUtil.onSportFullNameChanged(e, 'matchFullName')"
								textField="matchFullName" valueField="matchFullName" searchField="matchFullName" required="true" requiredErrorText="赛事全称必须选择"
								onvalidation="SportUtil.onMatchCellValidation" valueFromSelect="true">     
						        <div property="columns">
						            <div header="ID" field="id" width="50"></div>
						            <div header="赛事名称" field="matchFullName"></div>
						            <div header="赛事名称" field="matchShortName"></div>
						        </div>
						    </div>
						</div>
						<div name="matchShortName" field="matchShortName" headerAlign="center" align="center" width="50">赛事简称
							<input property="editor" class="mini-textbox"/>
						</div>
					</div>
                </div>
                <div header="主队信息" headerAlign="center" align="center">
                    <div property="columns">
						<div name="homeTeamPrimaryId" field="homeTeamPrimaryId" headerAlign="center" align="center" width="40">主队ID
							<input property="editor" class="mini-textbox asLabel" allowInput="false"/>
						</div>
						<div field="homeTeamFullName" headerAlign="center" align="center" renderer="SportUtil.getHomeName">主队全称
							<div property="editor" class="mini-autocomplete" popupWidth="300" onitemclick="SportUtil.onSportFullNameChanged(e, 'homeTeamFullName')"
								textField="teamFullName" valueField="teamFullName" searchField="teamFullName" required="true" requiredErrorText="主队全称必须选择"
								onvalidation="SportUtil.onHomeTeamCellValidation" valueFromSelect="true">     
						        <div property="columns">
						            <div header="ID" field="id" width="50"></div>
						            <div header="球队名称" field="teamFullName"></div>
						            <div header="球队简称" field="teamShortName"></div>
						        </div>
						    </div>
						</div>
						<div name="homeTeamShortName" field="homeTeamShortName" property="editor" headerAlign="center" align="center" width="60">主队简称
							<input property="editor" class="mini-textbox"/>
						</div>
					</div>
                </div>
                <div header="客队信息" headerAlign="center" align="center">
                    <div property="columns">
						<div name="guestTeamPrimaryId" field="guestTeamPrimaryId" headerAlign="center" align="center" width="40">客队ID
							<input property="editor" class="mini-textbox asLabel" allowInput="false"/>
						</div>
						<div field="guestTeamFullName" headerAlign="center" align="center" renderer="SportUtil.getGuestName">客队全称
							<div property="editor" class="mini-autocomplete" popupWidth="300" onitemclick="SportUtil.onSportFullNameChanged(e, 'guestTeamFullName')"
								textField="teamFullName" valueField="teamFullName" searchField="teamFullName" required="true" requiredErrorText="客队全称必须选择"
								onvalidation="SportUtil.onGuestTeamCellValidation" valueFromSelect="true">     
						        <div property="columns">
						            <div header="ID" field="id" width="50"></div>
						            <div header="球队名称" field="teamFullName"></div>
						            <div header="球队简称" field="teamShortName"></div>
						        </div>
						    </div>
						</div>
						<div name="guestTeamShortName" field="guestTeamShortName" property="editor" headerAlign="center" align="center" width="60">客队简称
							<input property="editor" class="mini-textbox"/>
						</div>
					</div>
                </div>
                <div field="matchStatus" type="comboboxcolumn" headerAlign="center" align="center">赛事状态
                    <input  property="editor"   class="mini-combobox" data="Dic.matchStatus"/>
                </div>
                <div field="halfScore" property="editor" headerAlign="center" align="center" width="60">半场比分
                    <input property="editor" class="mini-textbox" vtype="score"/>
                </div>
                <div field="fullScore" property="editor" headerAlign="center" align="center" width="60">全场比分
                    <input property="editor" class="mini-textbox" vtype="score"/>
                </div>
                <div field="fourteenWdf"  type="comboboxcolumn" headerAlign="center" align="center" width="60">彩果
                    <input  property="editor"  class="mini-combobox" data="Dic.matchResult"/>
                </div>
                <div field="officialId" property="editor" headerAlign="center" width="60">官方ID
                    <input property="editor" class="mini-textbox" />
                </div>
                <div field="analysisUrl" property="editor" headerAlign="center" width="60">分析ID
                    <input property="editor" class="mini-textbox" />
                </div>
                <div field="wrong" headerAlign="center" align="center">其他数问题</div>
                <div field="opt" renderer="lottery_oldfb.onActionRenderer" width="100" headerAlign="center" align="center">操作
                </div>
            </div>
        </div>
    </div>
</div>

<div id="editWindow" class="mini-window" title=""
     style="width:1100px; height:800px;" showMaxButton="false"
     showCollapseButton="false" showShadow="true" showToolbar="false"
     showFooter="true" showModal="true" allowResize="true" allowDrag="true">

    <div id="editform">
        <input name="url" value="lotterymgr/old_fb" class="mini-hidden"/>
        <input name="action" value="post" class="mini-hidden"/>
        <input name="lotteryCode" class="mini-hidden"/>
        <input name="systemCode" class="mini-hidden">
        <input name="againstId" class="mini-hidden"/>
        <input name="drawOldInfoId" class="mini-hidden"/>
        <input id="matchId" name="matchId"  class="mini-hidden"/>
        <input id="matchPrimaryId" name="matchPrimaryId"  class="mini-hidden"/>
        <input id="homeTeamPrimaryId" name="homeTeamPrimaryId"  class="mini-hidden"/>
        <input id="guestTeamPrimaryId" name="guestTeamPrimaryId"  class="mini-hidden" />
        <table width="100%" border="0" cellpadding="2" cellspacing="2">
            <tr>
                <td  width="50%" style="text-align: center;">
                    <table>
                        <tr>
                            <td><span id="hometitle"></span></td>
                            <td>VS</td>
                            <td><span id="guesttitle"></span></td>
                        </tr>
                    </table>
                </td>
                <td width="50%" style="text-align: right;">
                    <a class="mini-button"  iconCls="icon-reload" >同步所有数据</a>
                    <a class="mini-button" onclick="lottery_oldfb.submit"  iconCls="icon-save" >保存</a>
                </td>
            </tr>
        </table>
        <div class="mini-toolbar">
            <table width="100%" border="0" cellpadding="2" cellspacing="2">
                <tr>
                    <th width="15%" style="text-align: left;">赛事基本信息：</th>
                    <th width="10%"></th>
                    <th width="15%"></th>
                    <th width="10%"></th>
                    <th width="15%"></th>
                    <th width="15%" style="text-align: right;"><a class="mini-button"  iconCls="icon-reload">同步数据</a></th>
                </tr>
            </table>
        </div>
        <table width="100%" border="0" cellpadding="2" cellspacing="2">
            <tbody>
            <tr>
                <table width="100%">
                    <tr>
                        <td width="15%" rowspan="3"><img id="matchImg" height="100px" style="margin-left: 30px;" alt="赛事图片"></td>
                        <td width="10%">赛事全称</td>
                        <td width="15%"><input id="matchFullName"  name="matchFullName" class="mini-textbox asLabel" readOnly="true" value="textbox" /></td>
                        <td width="10%">推荐赛事</td>
                        <td width="15%">
                            <input id="isRecommend" name="isRecommend" value=""  class="mini-combobox" style="width:200px;"
                                               emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" data="Dic.yesNo"/>
                        </td>
                        <td width="15%"></td>
                    </tr>
                    <tr>
                        <td width="10%">赛事简称</td>
                        <td width="15%"><input id="matchShortName" name="matchShortName" class="mini-textbox" style="width:200px;" /></td>
                        <td width="10%">赛事标签颜色</td>
                        <td width="15%">
                            <input id="color" name="color" class="mini-textbox" style="width:200px;" />
                        </td>
                        <td width="15%"></td>
                    </tr>
                    <tr>
                        <td width="10%">赛事资料ID</td>
                        <td width="15%"><input id="matchDataUrl" name="matchDataUrl" class="mini-textbox" style="width:200px;" /></td>
                        <td width="10%">赛事情报ID</td>
                        <td width="15%">
                            <input id="matchInfoUrl" name="matchInfoUrl" class="mini-textbox" style="width:200px;" />
                        </td>
                        <td width="15%"></td>
                    </tr>
                    <tr>
                        <td width="15%" style="text-align: center;">
                            <input id = "logoUrl" name="logoUrl"  class="mini-hidden" style="width:200px;" vtype ="maxLength:100" allowInput="false"  requiredErrorText="请先上传图片"/>
                            <input type="button" value="修改" onclick="lottery_oldfb.ajaxFileUpload('matchImg','logoUrl','matchfile')"/></td>
                        <td width="10%">赛事分析ID</td>
                        <td width="15%"><input id="analysisUrl" name="analysisUrl" class="mini-textbox" style="width:200px;" /></td>
                        <td width="10%">平均欧赔</td>
                        <td width="15%">
                            <input id="oddsWin" name="oddsWin" class="mini-textbox" emptyText="胜" style="width:50px;" />
                            <input id="oddsDraw" name="oddsDraw" class="mini-textbox" emptyText="平" style="width:50px;" />
                            <input id="oddsFail" name="oddsFail" class="mini-textbox" emptyText="负" style="width:50px;" />
                        </td>
                        <td width="15%"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td width="10%">比赛赛场</td>
                        <td width="15%"><input id="stadium" name="stadium"  class="mini-textbox" style="width:200px;" /></td>
                        <td width="10%">实时天气</td>
                        <td width="15%">
                            <input id="weather" name="weather"  class="mini-textbox" style="width:200px;" />
                        </td>
                        <td width="15%"></td>
                    </tr>
                </table>
            </tr>
            <tr>
                <div id="secondWindow">
                    <div class="mini-toolbar">
                        <table style="width:100%;">
                            <tr>
                                <th style="white-space:nowrap;text-align: left;" width="25%" >
                                    球队基本信息：
                                </th>
                                <th style="white-space:nowrap;text-align: center;" width="25%">
                                    主队
                                </th>
                                <th style="white-space:nowrap;text-align: center;" width="25%">
                                    客队
                                </th>
                                <th width="25%"  style="white-space:nowrap;text-align: right;">
                                    <a class="mini-button"  iconCls="icon-reload">同步数据</a></th>
                            </tr>
                        </table>
                    </div>
                    <table width="100%">
                        <tr>
                            <td width="15%" rowspan="3"><img id="homeImg"  height="100px" style="margin-left: 30px;" alt="主队图片"></td>
                            <td width="10%">球队全称</td>
                            <td width="15%"><input id="homeTeamFullName"  name="homeTeamFullName" class="mini-textbox asLabel" readOnly="true" value="textbox"  /></td>
                            <td width="10%">球队全称</td>
                            <td width="15%"><input id="guestTeamFullName"  name="guestTeamFullName" class="mini-textbox asLabel" readOnly="true" value="textbox"  />
                            </td>
                            <td width="15%" rowspan="3"><img id="guestImg" height="100px" style="margin-left: 30px;" alt="客队图片"></td>
                        </tr>
                        <tr>
                            <td width="10%">球队简称</td>
                            <td width="15%"><input id="homeTeamShortName"  name="homeTeamShortName"  class="mini-textbox" style="width:200px;" /></td>
                            <td width="10%">球队简称</td>
                            <td width="15%">
                                <input id="guestTeamShortName" name="guestTeamShortName"  class="mini-textbox" style="width:200px;" />
                            </td>

                        </tr>
                        <tr>
                            <td width="10%">球队资料ID</td>
                            <td width="15%"><input id="homeTeamDataUrl" name="homeTeamDataUrl"  class="mini-textbox" style="width:200px;" /></td>
                            <td width="10%">球队资料ID</td>
                            <td width="15%">
                                <input id="guestTeamDataUrl" name="guestTeamDataUrl"  class="mini-textbox" style="width:200px;" />
                            </td>

                        </tr>
                        <tr>
                            <td width="15%" style="text-align: center;">
                                <input id = "homeLogoUrl" name="homeLogoUrl"  class="mini-hidden" style="width:200px;" vtype ="maxLength:100" allowInput="false"  requiredErrorText="请先上传图片"/>
                                <input type="button" value="修改" onclick="lottery_oldfb.ajaxFileUpload('homeImg','homeLogoUrl','homefile')"/></td>
                            <td width="10%">球队排名</td>
                            <td width="15%"><input id="homeTeamOrder" name="homeTeamOrder"  class="mini-textbox" style="width:200px;" /></td>
                            <td width="10%">球队排名</td>
                            <td width="15%">
                                <input id="guestTeamOrder" name="guestTeamOrder"  class="mini-textbox" style="width:200px;" />
                            </td>
                            <td width="15%" style="text-align: center;">
                                <input id = "guestLogoUrl" name="guestLogoUrl"  class="mini-hidden" style="width:200px;" vtype ="maxLength:100" allowInput="false"  requiredErrorText="请先上传图片"/>
                                <input type="button" value="修改" onclick="lottery_oldfb.ajaxFileUpload('guestImg','guestLogoUrl','guestfile')"/></td>
                        </tr>
                    </table>


                </div>
            </tr>
            <tr>
                <div>
                    <div class="mini-toolbar">
                        <table style="width:100%;">
                            <tr>
                                <th style="white-space:nowrap;text-align: left;" width="50%" >
                                    开奖信息：
                                </th>
                                <th width="50%"  style="white-space:nowrap;text-align: right;">
                                    <a class="mini-button"  iconCls="icon-reload">同步数据</a></th>
                            </tr>
                        </table>
                    </div>
                    <div style="margin: 20px;">
                        <table width="100%" >
                            <tr>
                                <td width="15%" style="text-align: center">
                                    半场比分:
                                </td>
                                <td width="15%">
                                    <input id="halfScore" name="halfScore" vtype="score" onvaluechanged="lottery_oldfb.scoreValueChange" class="mini-textbox" style="width:200px;" />
                                </td>
                                <td width="15%" style="text-align: center">
                                    全场比分:
                                </td>
                                <td width="15%">
                                    <input id="fullScore" name="fullScore" vtype="score" onvaluechanged="lottery_oldfb.scoreValueChange" class="mini-textbox" style="width:200px;" />
                                </td>
                                <td width="15%" style="text-align: center">
                                    开奖时间:
                                </td>
                                <td width="15%">
                                    <input id="drawTime" name="drawTime"  class="mini-datepicker" allowInput="false" style="width:200px;"
                                           format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true"
                                           showClearButton="false"/>
                                </td>
                            </tr>
                        </table>
                        <table width="100%" class="table-bor" style="margin-top: 20px;">
                            <tr>
                                <td width="15%" style="text-align: left" colspan="5">
                                    &nbsp; &nbsp; &nbsp; &nbsp;开奖结果:
                                </td>
                            </tr>
                            <tr>
                                <td width="15%" style="text-align: center">
                                    玩法
                                </td>
                                <td width="15%" style="text-align: center">
                                    14场胜平负
                                </td>
                                <td width="15%" style="text-align: center">
                                    任选九场
                                </td>
                                <td width="15%" style="text-align: center">
                                    4场进球
                                </td>
                                <td width="15%" style="text-align: center">
                                    6场半全场
                                </td>
                            </tr>
                            <tr>
                                <td width="15%" style="text-align: center">
                                    开奖结果
                                </td>
                                <td width="15%" style="text-align: center">
                                    <span id="fourteenWdf1"></span>
                                </td>
                                <td width="15%" style="text-align: center">
                                    <span id="fourteenWdf2"></span>
                                </td>
                                <td width="15%" style="text-align: center">
                                    <span id="fourGoal"></span>
                                </td>
                                <td width="15%" style="text-align: center">
                                    <span id="sixHfWdf"></span>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </tr>
            <tr>
                <div>
                    <div class="mini-toolbar">
                        <table style="width:100%;">
                            <tr>
                                <th style="white-space:nowrap;text-align: left;" width="50%" >
                                    赛事官方数据：
                                </th>
                                <th width="50%"  style="white-space:nowrap;text-align: right;">
                                    <input id="secReold" name="secReold" class="mini-textbox" style="width:30px;" />
                                    <span style="margin-right:10px;">秒自动同步</span>
                                    <a class="mini-button"  iconCls="icon-reload">同步数据</a>
                                </th>
                            </tr>
                        </table>
                    </div>
                    <div style="margin: 20px;">
                        <table width="100%" >
                            <tr>
                                <td width="15%" style="text-align: center">
                                    赛事编号:
                                </td>
                                <td width="15%">
                                    <input id="officialMatchCode" name="officialMatchCode" class="mini-textbox asLabel" readOnly="true" value="textbox"  style="width:200px;" />
                                    <input id="issueCode" name="issueCode" class="mini-textbox asLabel" readOnly="true" value="textbox"  style="width:200px;" />
                                </td>
                                <td width="15%" style="text-align: center">
                                    赛事状态:
                                </td>
                                <td width="15%">
                                    <input id="matchStatus" name="matchStatus" class="mini-combobox" data="Dic.matchStatus"  style="width:200px;"/>
                                </td>
                                <td width="15%" style="text-align: center">
                                    官方ID:
                                </td>
                                <td width="15%">
                                    <input id="officialId" name="officialId" class="mini-textbox"  allowInput="true" style="width:200px;"/>
                                </td>
                            </tr>
                            <tr>
                                <td width="15%" style="text-align: center">
                                    比赛时间:
                                </td>
                                <td width="15%">
                                    <input id="startTime" name="startTime" class="mini-datepicker"  allowInput="false" style="width:200px;"
                                           format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true"
                                           showClearButton="false"/>
                                </td>
                                <td width="15%" style="text-align: center">
                                    截止时间:
                                </td>
                                <td width="15%">
                                    <input id="saleEndTime" name="saleEndTime"  class="mini-datepicker" allowInput="false" style="width:200px;"
                                           format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true"
                                           showClearButton="false"/>
                                </td>
                                <td width="15%" style="text-align: center">
                                    中立场:
                                </td>
                                <td width="15%">
                                    <input id="isNeutral" name="isNeutral" value=""  class="mini-combobox" style="width:200px;"
                                           emptyText="请选择" allowInput="true" valueFromSelect="true" showClose="true" data="Dic.yesNo"/>
                                </td>
                            </tr>
                        </table>
                        <hr style="border:1px dashed red" />
                        <table width="100%" class="table-bor" style="margin-top: 20px;">
                            <tr>
                                <td width="15%" style="text-align: left" >
                                    &nbsp; &nbsp; &nbsp; &nbsp;全场奖金:
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div id="fullFBBos" class="mini-datagrid" style="width:100%;height:200px;"
                                         allowResize="true"   multiSelect="true" showPager="false" >
                                        <div property="columns">
                                            <div field="releaseTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">发布时间</div>
                                            <div  field="spWin" headerAlign="center" align="center" >胜

                                            </div>
                                            <div   field="spDraw" headerAlign="center" align="center">平
                                            </div>
                                            <div   field="spFail" headerAlign="center" align="center">负
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>

                        </table>
                        <hr style="border:1px dashed  red" />
                        <table width="100%" class="table-bor" style="margin-top: 20px;">
                            <tr>
                                <td width="15%" style="text-align: left" >
                                    &nbsp; &nbsp; &nbsp; &nbsp;半场奖金:
                                </td>
                            </tr>
                            <tr>
                                <td> <div id="halfFBBos" class="mini-datagrid" style="width:100%;height:200px;"
                                          allowResize="true"   multiSelect="true"  showPager="false">
                                    <div property="columns">
                                        <div field="releaseTime" headerAlign="center" align="center" dateFormat="yyyy-MM-dd HH:mm:ss">发布时间</div>
                                        <div  field="spWin" headerAlign="center" align="center" >胜

                                        </div>
                                        <div   field="spDraw" headerAlign="center" align="center">平
                                        </div>
                                        <div   field="spFail" headerAlign="center" align="center">负
                                        </div>
                                    </div>
                                </div></td>

                            </tr>
                        </table>
                    </div>
                </div>
            </tr>
            <tr>
                <div>
                    <div class="mini-toolbar">
                        <table style="width:100%;">
                            <tr>
                                <th style="white-space:nowrap;text-align: left;" width="50%" >
                                    操作信息：
                                </th>
                                <th width="50%"  style="white-space:nowrap;text-align: right;">
                                </th>
                            </tr>
                        </table>
                    </div>
                    <div style="margin: 20px;">
                        <table width="100%" class="table-bor" style="margin-top: 20px;">
                            <tr>
                                <td width="15%" style="text-align: center">
                                    创建时间
                                </td>
                                <td width="15%" style="text-align: center">
                                    <input name="createTime" class="mini-datepicker"  allowInput="false" readonly="readonly" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td width="15%" style="text-align: center">
                                    修改时间
                                </td>
                                <td width="15%" style="text-align: center">
                                    <input id="modifyTime" name="modifyTime" class="mini-datepicker"  allowInput="false" readonly="readonly" style="width:200px;" format="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td width="15%" style="text-align: center" rowspan="2">
                                    备注
                                </td>
                                <td width="25%" style="text-align: center" rowspan="2">
                                    <textarea id="remark" name="remark"  class="mini-textarea" emptyText="请输入备注" width="100%" height="100%"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td width="15%" style="text-align: center">
                                    创建人
                                </td>
                                <td width="15%" style="text-align: center">
                                    <input id="createBy"  name="createBy" class="mini-textbox asLabel" readOnly="true" value="textbox"  />
                                </td>
                                <td width="15%" style="text-align: center">
                                    修改人
                                </td>
                                <td width="15%" style="text-align: center">
                                    <input id="modifyBy"  name="modifyBy" class="mini-textbox asLabel" readOnly="true" value="textbox"  />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
<script src="<%=basePath%>resources/js/public/common.js?version=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/lotterymgr/sport_utils.js?version=<%=version%>" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/lotterymgr/lottery_oldfb.js?version=<%=version%>" type="text/javascript"></script>
</html>
