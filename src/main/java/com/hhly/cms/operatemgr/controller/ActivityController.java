package com.hhly.cms.operatemgr.controller;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.operatemgr.service.ActivityService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.activity.vo.ActivityAnnualMeetMsgVO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.cache.operate.OperateCacheEnum;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.ExcelUtil;
import com.hhly.skeleton.base.util.HttpUtil;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.operatemgr.bo.OperateActivityChpGuessBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateActivityChpInfoBO;
import com.hhly.skeleton.cms.operatemgr.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2017-1-4 下午3:29:47
 * @Desc 活动管理
 */
@Controller
@RequestMapping(value = "/operatemgr/activity")
public class ActivityController extends BaseController {
	@Value("${lotto_activity_url}")
    protected String lottoActivitUrl;
	@Autowired
	private ActivityService activitylService;

	private static Logger logger = LogManager.getLogger(ActivityController.class);

	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(){
		return "operatemgr/activity";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object list(OperateActivityVO vo){
		return activitylService.findActivity(vo);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService=OperateCacheEnum.ActivityCacheEnum.class, isLikeQuery=true)
	public  Object udpate(@Valid(GroupValue.UPD)OperateActivityVO vo,HttpSession session) {
		vo.setModifyBy(getUserRealName(session));
		Assert.isTrue(vo.getActivityStartTime().getTime() < vo.getActivityEndTime().getTime(), "活动开始结束时间错误");
		int num = activitylService.updOperateActivity(vo);
		return getSaveResult(num);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService=OperateCacheEnum.ActivityCacheEnum.class, isLikeQuery=true)
	public  Object add(@Valid(GroupValue.ADD)OperateActivityVO vo,HttpSession session) {
		vo.setCreateBy(getUserRealName(session));
		Assert.isTrue(vo.getActivityStartTime().getTime() < vo.getActivityEndTime().getTime(), "活动开始结束时间错误");
		int num = activitylService.addOperateActivity(vo);
		return getSaveResult(num);
	}
	/**
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object findDetail(@PathVariable(value = "id")int id){
		return getResultSuccess(activitylService.findActivityDetail(id));
	}
	/**
	 * 导出excel
	 * @param response
	 * @param vo
	 * @throws IOException
	 */
	@RequestMapping("excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void exportOpExcel(HttpServletResponse response,OperateActivityVO vo) throws IOException{
		ByteArrayOutputStream outputStream=activitylService.getExcelActivity(vo);
		excel("activity", outputStream, response);
	}
	/**
	 * 查询所有渠道(用于加载树形)
	 * @return
	 */
	@RequestMapping(value = "/channel")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object channelList(@RequestParam(value = "channelId") String channelId){
		return activitylService.findChannelList(channelId);
	}
	
	/**
	 * 活动配置相关
	 */
	@RequestMapping("editwin/{type}")
	@Authority(privilege = AuthEnum.ALL)
	public Object activityEdit(@PathVariable("type")Integer type) {
		String head = "operatemgr/activityConfigDetail/";
		switch (type) {
			case 1:
			case 2:
			case 3:
				return head + "add_award";//加奖
			case 4:
				return head + "recharge";//充值送
			case 5:
				return head + "multiple_bet";//倍投立减
			case 6:
				return head + "add_code";//追号送
			case 7:
				return head + "notwin_sent";//不中返
			case 8:
				return head + "first_recharge";//首冲活动
			case 9:
				return head + "new_user";//新用户活动
			case 10:
				return head + "add_single";
			case 11:
				return head + "special";
			case 12:
				return head + "prize_award";
            case 13:
                return head + "redeem_code";//兑换码
            case 14:
				return head + "annual_meet";
			case 15:
				return head + "chp_guess";
            default:
                return "error";
		}
	}

	@RequestMapping(value="/config/merge", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService=OperateCacheEnum.ActivityCacheEnum.class, isLikeQuery=true)
	public Object mergeActivityConfig(@RequestBody @Valid(GroupValue.ADD) OperateActivityConfigVO vo,HttpSession session){
		vo.setCreateBy(getUserName(session));
		vo.setModifyBy(getUserName(session));
		return getSaveResult(activitylService.mergeActivityConfig(vo));
	}


    @RequestMapping(value = "/config/findCdkey", method = RequestMethod.POST)
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
    public Object findCdkey(OperateActivityConfigVO vo) {
        return new ResultBO(activitylService.findActivityConfigDetail(vo));
    }

    @RequestMapping(value = "/config/detail", method = RequestMethod.POST)
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
	public Object findActivityConfigDetail(OperateActivityConfigVO vo){
		return new ResultBO(activitylService.findActivityConfigDetail(vo));
	}	
	
	@RequestMapping(value="/rule/add", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService=OperateCacheEnum.ActivityCacheEnum.class, isLikeQuery=true)
	public Object addActivityRule(@RequestBody @Valid(GroupValue.ADD) OperateActivityRuleVO vo,HttpSession session){
		return getSaveResult(activitylService.addActivityRule(vo));
	}
	
	@RequestMapping(value="/rule/upd", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService=OperateCacheEnum.ActivityCacheEnum.class, isLikeQuery=true)
	public Object updActivityRule(@RequestBody @Valid(GroupValue.UPD) OperateActivityRuleVO vo,HttpSession session){
		return getSaveResult(activitylService.updActivityRule(vo));
	}
	
	@RequestMapping(value="/rule/del", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.DEL)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService=OperateCacheEnum.ActivityCacheEnum.class, isLikeQuery=true)
	public Object delActivityRule(@RequestParam Integer id){
		return getSaveResult(activitylService.delActivityRule(id));
	}
	
	@RequestMapping(value="/rule/list", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object findActivityRuleList(OperateActivityRuleVO vo){
		return activitylService.findActivityRuleList(vo);
	}

	@RequestMapping(value="/rule/detail", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object findActivityRuleDetail(@RequestParam Integer id){
		return activitylService.findActivityRuleDetail(id);
	}

	@RequestMapping(value = "/sport/list", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object findSportAgainstInfo(SportAgainstVO vo) {
		return activitylService.findSportAgainstInfo(vo);
	}

	@RequestMapping(value="/sport/add", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService=OperateCacheEnum.ActivityCacheEnum.class, isLikeQuery=true)
	public Object mergeSportRule(@RequestBody @Valid(GroupValue.ADD) OperateActivitySportRuleVO vo, HttpSession session) {
		vo.setCreateBy(getUserName(session));
		vo.setModifyBy(getUserName(session));
		int num = activitylService.mergeSportRule(vo);
		if (num > 0) {
			return ResultBO.ok(num);
		} else {
			return ResultBO.err();
		}
	}

	@RequestMapping(value = "/config/activitystatustrue", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object findActivityStatusTrue() {
		return new ResultBO(activitylService.findActivityStatusTrue());
	}

	@RequestMapping(value="/prizeaward/merge", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService=OperateCacheEnum.ActivityCacheEnum.class, isLikeQuery=true)
	public Object addPrizeAwardConfig(OperateActivityAwardVO vo,HttpSession session){
		vo.setCreateBy(getUserName(session));
		vo.setModifyBy(getUserName(session));
		return getResultSuccess(activitylService.addPrizeAwardConfig(vo));
	}

	@RequestMapping(value="/prizeaward/del", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.DEL)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService=OperateCacheEnum.ActivityCacheEnum.class, isLikeQuery=true)
	public Object delPrizeAwardConfig(OperateActivityAwardVO vo,HttpSession session){
		
		return getSaveResult(activitylService.delPrizeAwardConfig(vo));
	}
	
	@RequestMapping(value="/addcode/merge", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService=OperateCacheEnum.ActivityCacheEnum.class, isLikeQuery=true)
	public Object addAddCodeConfig(OperateActivityAddedVO vo,HttpSession session){
		vo.setCreateBy(getUserName(session));
		vo.setModifyBy(getUserName(session));
		return getResultSuccess(activitylService.addAddCodeConfig(vo));
	}

	@RequestMapping(value="/addcode/del", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.DEL)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService=OperateCacheEnum.ActivityCacheEnum.class, isLikeQuery=true)
	public Object delAddCodeConfig(OperateActivityAddedVO vo,HttpSession session){		
		return getSaveResult(activitylService.delAddCodeConfig(vo));
	}
	
	/***
	 * 年会赠送活动触发口
	 * @param vo
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/annualmeet/send", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	public Object sendAnnualMeet(ActivityAnnualMeetMsgVO vo,@RequestParam MultipartFile file,HttpSession session){		
		List<String> accountNamelist = null;
		try {
			// 1.解析文件
			if(file == null){
				return ResultBO.err();
			}
			if(file != null && !StringUtil.isBlank(file.getOriginalFilename())){
				accountNamelist = analysisFile(file);
			}			
			//会员账号名称集合不存在
			Assert.notEmpty(accountNamelist, "40166");
			String userIds = StringUtil.join(accountNamelist, SymbolConstants.COMMA);
			OperateActivityAddedVO addVo = new OperateActivityAddedVO();
			addVo.setId(vo.getAddId());
			addVo.setGiveIssue(vo.getGiveIssue());
			int num =activitylService.updateAddCodeIssue(addVo);
			if(num!=1){
				return ResultBO.err("更新期号入数据库失败");
			}
			Map<String, String> params =new HashMap<String, String>();
			params.put("activityCode", vo.getActivityCode());
			params.put("giveIssue", vo.getGiveIssue());
			params.put("isSendRed", vo.getIsSendRed().toString());
			params.put("userIds", userIds);
			String url= lottoActivitUrl+"activity/sendAnnualMeet";
			String str =HttpUtil.doPost(url,params);
			return JsonUtil.object2Json( ResultBO.ok(str));
		}catch (Exception e) {
			e.printStackTrace();
			return JsonUtil.object2Json(ResultBO.err(e.getMessage()));
		}
	}

	@RequestMapping("/cdkey/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object findCdkeyList(OperateActivityCdkeyVO vo) {
		return activitylService.findCdkeyList(vo);
	}

	@RequestMapping("/cdkey/count")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object findCdkeyCount(OperateActivityCdkeyVO vo) {
		return ResultBO.ok(activitylService.findCdkCount(vo));
	}
	
	@RequestMapping(value="/recharge/merge", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService=OperateCacheEnum.ActivityCacheEnum.class, isLikeQuery=true)
	public Object addRechargeConfig(@RequestBody OperateActivityRechargeVO vo,HttpSession session){
		vo.setCreateBy(getUserName(session));
		vo.setModifyBy(getUserName(session));
		return getResultSuccess(activitylService.addRechargeConfig(vo));
	}
	@RequestMapping(value = "/recharge/detail", method = RequestMethod.POST)
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
	public Object findRechargeDetail(OperateActivityRechargeVO vo){
		return activitylService.findRechargeDetail(vo);
	}

	/**
	 * 查询参与世界杯冠军竞猜的用户信息
	 * @return
	 */
	@RequestMapping("chp/user")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<OperateActivityChpInfoBO> pageChpUsers(OperateActivityChpInfoBO vo) {
		return activitylService.pageChpUsers(vo);
	}

    @RequestMapping("chp/draw")
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public ResultBO<?> chpDraw(Integer ruleId) {
        return new RestTemplate().getForObject(lottoActivitUrl+"activityChp/openAward?ruleId="+ruleId,ResultBO.class);
    }

    @RequestMapping("chp/send")
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public ResultBO<?> chpSend(Integer ruleId) {
        return new RestTemplate().getForObject(lottoActivitUrl+"activityChp/sendAward?ruleId="+ruleId,ResultBO.class);
    }

	@RequestMapping("chp/user/excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void excelChpUsers(HttpServletResponse response,OperateActivityChpInfoBO vo) throws IOException{
		ByteArrayOutputStream outputStream=activitylService.excelChpUsers(vo);
		excel("chp_user", outputStream, response);
	}

	/**
	 * 查询世界杯冠军竞猜球队信息
	 * @return
	 */
	@RequestMapping(value = "chp/team")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object findChpTeams(OperateActivityChpGuessBO vo) {
		return activitylService.findChpTeams(vo);
	}

	@RequestMapping(value = "chp/user/upload")
	@Authority(privilege = AuthEnum.UPLOAD)
	@ResponseBody
	public Object upload(@RequestParam MultipartFile file,HttpServletRequest request,HttpSession session)
			throws IllegalStateException, IOException {
		try {
			Assert.notNull(file, "请选择一个Excel文件");
            final String activityCode = request.getParameter("activityCode");
            Assert.isTrue(StringUtils.hasText(activityCode), "活动编码为空！");
			String originalFilename=file.getOriginalFilename();
			Assert.isTrue(originalFilename.endsWith(".xls") || originalFilename.endsWith(".xlsx"), "请上传excel文件");
			List<List<String>> datas = ExcelUtil.readExcel(file);
			Assert.isTrue(!CollectionUtils.isEmpty(datas) && datas.size()>1, "excel数据为空");
			datas.remove(0);
            final String realName = getUserRealName(session)+"_upload";
            final Date now = new Date();
            Set<String> nickNameSet = new HashSet<>();
            Set<String> homeNameSet = new HashSet<>();
            List<OperateActivityChpInfoBO> chpInfoes = datas.stream().map(d -> {
                OperateActivityChpInfoBO chp = new OperateActivityChpInfoBO();
                String nickName = d.get(0).trim();
                Assert.isTrue(StringUtils.hasText(nickName),"用户昵称不能为空");
				String homeName = d.get(2).trim();
				Assert.isTrue(StringUtils.hasText(homeName),"球队名称不能为空");
				homeNameSet.add(homeName);
                chp.setNickName(nickName);
                nickNameSet.add(nickName);
                Double totalScore = Double.parseDouble(d.get(1).replaceAll(",",""));
                chp.setTotalScore(totalScore);
                chp.setTeamScore(totalScore);
                chp.setActivityCode(activityCode);
                chp.setHomeName(homeName);
                chp.setCreateBy(realName);
                chp.setCreateTime(now);
                // 用户状态，1真实用户，2测试用户
                chp.setUserType((short)2);
                return chp;
            }).collect(Collectors.toList());
            // 检查excel中的用户昵称是否有重复,因为同一时间每个用户仅只能站队一次
            Assert.isTrue(nickNameSet.size()==chpInfoes.size(),"excel中的用户昵称有重复,请去重后再上传！");
            // 查询此活动配置的所有球队
			OperateActivityChpGuessBO vo = new OperateActivityChpGuessBO();
			vo.setActivityCode(activityCode);
			List<OperateActivityChpGuessBO> teams = activitylService.findChpTeams(vo);
			Assert.isTrue(!teams.isEmpty(),"请配置球队后再上传用户");
			homeNameSet.removeAll(teams.stream().map(t->t.getHomeName().trim()).collect(Collectors.toSet()));
			Assert.isTrue(homeNameSet.isEmpty(),"活动未配置球队"+homeNameSet.stream().collect(Collectors.joining(",","[","]")));
			Map<String,Integer> teamMap = teams.stream().collect(Collectors.toMap(t->t.getHomeName(),t->t.getId()));
			chpInfoes.stream().forEach(c->c.setActivityRuleId(Objects.requireNonNull(teamMap.get(c.getHomeName()),c.getHomeName()+"的配置id为空")));
            activitylService.addActivityChpInfo(chpInfoes);
			return String.valueOf(net.sf.json.JSONObject.fromObject(ResultBO.ok()));
		} catch (Exception e) {
			logger.error(e);
			ResultBO<?> ret = ResultBO.err();
			ret.setMessage(e.getMessage());
			return String.valueOf(net.sf.json.JSONObject.fromObject(ret));
		}
	}

}
