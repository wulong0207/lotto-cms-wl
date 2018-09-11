package com.hhly.cms.lotterymgr.controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.lotterymgr.service.LotteryIssueService;
import com.hhly.cms.lotterymgr.service.LotteryTypeService;
import com.hhly.cms.ordermgr.service.OrderService;
import com.hhly.cms.taskmgr.service.TaskService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.LotteryEnum.ConIssue;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.common.TaskEnum.TaskId;
import com.hhly.skeleton.base.common.cache.issue.IssueCacheEnum.LotteryIssueCacheEnum;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryIssueCmsBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryTypeBO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryIssueCmsVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryTypeVO;

/**
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-11-23 下午5:03:17
 * @Desc 彩期管理
 */
@Controller
@RequestMapping(value = "/lotterymgr/issue")
public class LotteryIssueController extends BaseController {
	
	@Autowired
	private LotteryIssueService issueService;
	
	@Autowired
	private LotteryTypeService lotteryTypeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(){
		return "lotterymgr/lottery_issue";
	}
	/**
	 * 查询当前期信息
	 * @param lotteryCode
	 * @param currentIssue
	 * @return
	 */
	@RequestMapping(value = "/dictionary/findIssue")
	@Authority(privilege=AuthEnum.ALL)
	@ResponseBody
	public Object findIssue(Integer lotteryCode, Short currentIssue){
		return  issueService.findIssue(lotteryCode,currentIssue);
	}
	/**
	 * 根据彩种查期号
	 * @param lotteryCode
	 * @return
	 */
	@RequestMapping(value = "/dictionary/issuecode")
	@Authority(privilege=AuthEnum.ALL)
	@ResponseBody
	public  Object issueCode(LotteryIssueCmsVO vo){
		return  issueService.findIssueCode(vo);
	}
	
	/**
	 * 根据彩种查期号
	 * @param lotteryCode
	 * @return
	 */
	@RequestMapping(value = "/dictionary/cutOffHistoryIssue")
	@Authority(privilege=AuthEnum.ALL)
	@ResponseBody
	public  Object cutOffHistoryIssue(LotteryIssueCmsVO vo){
		return  issueService.cutOffHistoryIssue(vo);
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object list(LotteryIssueCmsVO vo){
		return issueService.findLotteryIssue(vo);
	}
	/**
	 * 修改彩期
	 * @param vo
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryIssueCacheEnum.class)
	public  Object udpate(LotteryIssueCmsVO vo,HttpSession session) {
		vo.setModifyBy(getUserRealName(session));
		int num = issueService.updLotteryIssue(vo);
		return getSaveResult(num);
	}
	/**
	 * 新增彩种
	 * @param vo
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@Authority(privilege =AuthEnum.ADD)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryIssueCacheEnum.class)
	public  Object add(LotteryIssueCmsVO vo,HttpSession session) {
		vo.setCreateBy(getUserRealName(session));
		int num = issueService.addLotteryIssue(vo);
		return getSaveResult(num);
	}
	/**
	 * 导出彩种类型到excel
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void exportOpExcel(HttpServletResponse response,LotteryIssueCmsVO vo) throws IOException{
		ByteArrayOutputStream outputStream=issueService.getLotteryIssueExcel(vo);
		excel("issue", outputStream, response);
	}
	/**
	 * 查询彩期详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detail")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object findDetail(@RequestParam(value = "id", required = true) String id){
		StringVO vo =new StringVO();
		vo.setStr(id);
		LotteryIssueCmsBO  bo = issueService.findLotteryIssueDetail(vo);
		return getResultSuccess(bo);
	}
	/**
	 * 彩种详情查看页面
	 * @param code
	 * @return
	 */
	@RequestMapping("editwin/{code}")
	@Authority(privilege=AuthEnum.SEARCH)
	public String  issueEdit(@PathVariable("code")Integer code){
		String head = "lotterymgr/issuedetail/";
		Lottery lot = Lottery.getLottery(code);
		if(lot==null){
			return "error";
		}else if(Lottery.FB == lot 
				||Lottery.BB == lot
				||Lottery.BJDC == lot
				||Lottery.SFGG == lot
				||Lottery.CHP == lot
				||Lottery.FNL == lot){
			 return head+"upd_jc";
		}else if(lot.name().indexOf("11X5")!=-1){
			return head+"elevenfive";
		}else if(lot.name().indexOf("SSC")!=-1){
			//时时彩
			 return head+"upd_ssc";
		}else if(lot.name().lastIndexOf("K3") != -1){
			 //快三
			 return head+"upd_k3";
		}else if(lot.name().lastIndexOf("KL10") != -1){
			return head + "upd_kl10";
		}
		return head+"upd_"+lot.name().toLowerCase();
	}
	/**
	 * 重启彩期线程
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月13日 上午10:26:13
	 * @param lotteryCode 彩种
	 * @return
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	@RequestMapping(value="/schedule",method = RequestMethod.POST)
	@Authority(privilege =AuthEnum.MANUAL_EXECUTE)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryIssueCacheEnum.class)
	public  Object restartThread(@RequestParam(required = true,value = "lotteryCode")int lotteryCode) throws IOException, URISyntaxException {
		//upd by cheng.chen 重启线程功能变更 2017-05-22
		ResultBO<?> bo = null;
		if(!Lottery.isUpdEndTime(lotteryCode)){
			LotteryIssueCmsVO issueVo = new LotteryIssueCmsVO();
			issueVo.setLotteryCode(lotteryCode);
			LotteryIssueCmsBO issueBo = issueService.findIssue(lotteryCode, ConIssue.CURRENT.getValue());
			
			Assert.notNull(issueBo, "40299");
			
			LotteryTypeVO typeVo = new LotteryTypeVO();
			typeVo.setLotteryCode(issueBo.getLotteryCode());
			LotteryTypeBO typeBo = lotteryTypeService.findSingle(typeVo);
			
			Assert.notNull(typeBo, "40298");
			
			//官方截止销售时间
			Date officialEndTime = issueBo.getOfficialEndTime();
			Assert.notNull(officialEndTime, "30703");
			//本站截止销售时间
			Date saleEndTime = issueBo.getSaleEndTime();
			Assert.notNull(saleEndTime, "30704");
	
			Date endCheckTime = DateUtil.addSecond(officialEndTime, typeBo.getEndCheckTime());
			//当修改时间等于当天时, 说明当天有修改, 可以执行相关订单操作
			if(DateUtil.isToday(issueBo.getModifyTime()))
				orderService.updTicketAndCheckTime(issueBo.getLotteryCode(), issueBo.getIssueCode(), officialEndTime, endCheckTime,
						saleEndTime, null);
			
			Map<String,String> map = new HashMap<>();
			map.put("lotteryCode", lotteryCode + "");
			//彩期切换, 包含彩期生成, 彩期追号订单生成, 彩期赛事状态变更, 彩期最终检票
			taskService.runTask(TaskId.ISSUE_CHANGE.getValue(), map);
			bo = ResultBO.ok();
		}else{
			bo = ResultBO.err("30700");
		}
		return bo;
	}
	/**
	 * 审核开奖号码
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月13日 上午11:37:45
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/audit/code",method = RequestMethod.POST)
	@Authority(privilege =AuthEnum.CHECK)
	@ResponseBody
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryIssueCacheEnum.class)
	public  Object audit(@Valid("audit")LotteryIssueCmsVO vo,HttpSession session)  {
		vo.setModifyBy(getUserRealName(session));
		int num = issueService.updateAuditDrawCode(vo);
		return getSaveResult(num);
	}

	/**
	 * @desc 手动执行开奖
	 * @author huangb
	 * @date 2017年5月9日
	 * @param lotteryCode
	 *            彩种code
	 * @param issueCode
	 *            彩期
	 * @return 手动执行开奖
	 */
	@RequestMapping(value = "/draw/code", method = RequestMethod.POST)
	@Authority(privilege = AuthEnum.MANUAL_EXECUTE)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryIssueCacheEnum.class)
	public Object draw(@RequestParam(value = "lotteryCode") String lotteryCode,
			@RequestParam(value = "issueCode") String issueCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("lotteryCode", lotteryCode);
		map.put("lotteryIssue", issueCode);
		return taskService.runTaskSync(TaskId.MANUAL_DRAW_CODE.getValue(), map);
	}

	/**
	 * @desc 手动执行追号
	 * @author huangb
	 * @date 2017年5月5日
	 * @param lotteryCode
	 *            彩种
	 * @return 手动执行追号
	 */
	@RequestMapping(value = "/manualChase", method = RequestMethod.POST)
	@Authority(privilege = AuthEnum.MANUAL_EXECUTE)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryIssueCacheEnum.class)
	public Object manualChase(@RequestParam(value = "lotteryCode") String lotteryCode) {
		Map<String, String> map = new HashMap<>();
		map.put("lotteryCode", lotteryCode);
		taskService.runTask(TaskId.MANUAL_CHASE.getValue(), map);
		return getResult(true);
	}
}
