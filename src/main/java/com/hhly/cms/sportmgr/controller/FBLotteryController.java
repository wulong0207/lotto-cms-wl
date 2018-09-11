package com.hhly.cms.sportmgr.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.lotterymgr.service.LotteryTypeService;
import com.hhly.cms.ordermgr.service.OrderService;
import com.hhly.cms.sportmgr.service.FBLotteryService;
import com.hhly.cms.taskmgr.service.TaskService;
import com.hhly.cms.utils.Authority;
import com.hhly.cmscore.cms.remote.service.IRedisMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.common.TaskEnum.TaskId;
import com.hhly.skeleton.base.common.cache.sport.SportCacheEnum.SportCmsFBCacheEnum;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.HttpUtil;
import com.hhly.skeleton.base.util.PropertyUtil;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.base.valid.util.ParamUtil;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryTypeBO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryTypeVO;
import com.hhly.skeleton.cms.sportmgr.bo.SportFBLotteryInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportDataBaseVO;
import com.hhly.skeleton.lotto.base.sport.vo.FBDataVO;

/**
 * Created by lgs on 2016/12/1.
 * 竞彩足球管理
 */
@Controller
@RequestMapping(value = "lotterymgr/fb")
public class FBLotteryController extends BaseController {
	
	private static Logger log = Logger.getLogger(FBLotteryController.class);

    @Autowired
    private FBLotteryService fbLotteryService;
  	
	@Autowired
	private LotteryTypeService lotteryTypeService;
	
	@Autowired
	private OrderService orderService;	
	
    @Autowired
    private TaskService taskService;
    
	@Autowired
	private IRedisMgrService redisMgrService;

	private static final String lotto_crawl_url = PropertyUtil.getPropertyValue("sys_dynamic.properties", "lotto_crawl_url");

    @RequestMapping(method = RequestMethod.GET)
    @Authority(privilege = AuthEnum.SEARCH)
    public String index(){
        return "lotterymgr/fb_lottery";
    }


    @RequestMapping(value = "/list" , method = RequestMethod.POST)
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
    public PagingBO<SportFBLotteryInfoBO> list(@Valid("search") SMGLotteryParamVO vo) {
        return fbLotteryService.findFBData(vo);
    }


    /**
     * 根据对阵id查看详细SP值
     *
     * @param againstId
     * @return
     */
    @RequestMapping(value = "/spInfo", method = RequestMethod.GET)
    @ResponseBody
    @Authority(privilege = AuthEnum.SEARCH)
    public Object spInfo(@RequestParam(value = "againstId", required = true) Long againstId) {
        return getResultSuccess(fbLotteryService.findFBSp(againstId));
    }

    /**
     * 保存表单数据
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @Authority(privilege = AuthEnum.UPD)
    @ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = SportCmsFBCacheEnum.class)
    public Object save(FBDataVO vo, final HttpSession session) {
        ParamUtil.validation(vo,"upd");
        vo.setModifyBy(super.getUserRealName(session));
        vo.setModifyTime(new Date());
        fbLotteryService.saveFBData(vo);
        List<String> cacheList = new ArrayList<>();
        cacheList.add(CacheConstants.getSportFbSystemCodeMatchListCacheKey(vo.getSystemCode()));
        cacheList.add(CacheConstants.S_COMM_SPORT_OLD_MATCH_LIST + "*");
        redisMgrService.delCacheList(cacheList, false);
        return ResultBO.ok();
    }

    /**
     * 保存表格编辑数据
     * @param json
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @Authority(privilege = AuthEnum.UPD)
    @ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = SportCmsFBCacheEnum.class)
    public Object save(@RequestParam(value = "json", required = true) String json, final HttpSession session) {
        FBDataVO vo = JSON.parseObject(json, FBDataVO.class);
        ParamUtil.validation(vo,"upd");
        vo.setModifyBy(super.getUserRealName(session));
        vo.setModifyTime(new Date());
        fbLotteryService.saveFBData(vo);
        List<String> cacheList = new ArrayList<>();
        cacheList.add(CacheConstants.getSportFbSystemCodeMatchListCacheKey(vo.getSystemCode()));
        cacheList.add(CacheConstants.S_COMM_SPORT_OLD_MATCH_LIST + "*");
        redisMgrService.delCacheList(cacheList, false);
        return ResultBO.ok();
    }

	@RequestMapping(value="/schedule",method = RequestMethod.POST)
	@Authority(privilege =AuthEnum.MANUAL_EXECUTE)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = SportCmsFBCacheEnum.class)
	public  Object restartThread(FBDataVO vo) throws IOException, URISyntaxException {
			ResultBO<?> bo = null;
			Assert.notNull(vo, "20001");
			
			LotteryTypeVO typeVo = new LotteryTypeVO();
			typeVo.setLotteryCode(Lottery.FB.getName());
			LotteryTypeBO typeBo = lotteryTypeService.findSingle(typeVo);
			
			Assert.notNull(typeBo, "40298");
			
			//赛事开始时间
			Date startTime = vo.getStartTime();
			Assert.notNull(startTime, "30705");
			//本站销售截止时间
			Date saleEndTime = vo.getSaleEndTime();
			Assert.notNull(saleEndTime, "30704");
			
			if(DateUtil.compare(startTime, saleEndTime) != 1)
				return ResultBO.err("30701");
			
			//修改赛事开始时间和销售截止时间
			SportDataBaseVO updVo = new SportDataBaseVO();
			updVo.setAgainstId(vo.getAgainstId());
			updVo.setStartTime(vo.getStartTime());
			updVo.setSaleEndTime(vo.getSaleEndTime());
			fbLotteryService.updSportAgainstInfo(updVo);
			
			Date endCheckTime = DateUtil.addSecond(startTime, typeBo.getEndCheckTime());
			
			//当修改时间等于当天时, 说明当天有修改, 可以执行相关订单操作
			if(DateUtil.isToday(saleEndTime))
				orderService.updTicketAndCheckTime(Lottery.FB.getName(), vo.getIssueCode(), startTime, endCheckTime,
						saleEndTime, vo.getSystemCode());
			
			//彩期切换, 包含彩期生成, 彩期追号订单生成, 彩期赛事状态变更, 彩期最终检票
			Map<String,String> map = new HashMap<>();
			map.put("lotteryCode", vo.getLotteryCode());
			taskService.runTask(TaskId.ISSUE_CHANGE.getValue(), map);
			
			bo = ResultBO.ok();
		return bo;
	}
    
    @RequestMapping(value = "/updCheckScore", method = RequestMethod.PUT)
    @Authority(privilege = AuthEnum.UPD)
    @ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = SportCmsFBCacheEnum.class)
    public Object updCheckScore(HttpSession session, @RequestParam(value="lotteryCode")String lotteryCode){
    	ResultBO<?> bo = null;
    	int num = fbLotteryService.updCheckScore(lotteryCode, getUserName(session));
    	if(num == 0){
    		bo = ResultBO.err("30702");
    	}else{
    		List<String> cacheList = new ArrayList<>();
            cacheList.add(CacheConstants.getSportFbSystemCodeMatchListCacheKey(""));
            cacheList.add(CacheConstants.S_COMM_SPORT_OLD_MATCH_LIST);
            redisMgrService.delCacheList(cacheList, true);
    		bo = getSaveResult(num);
    	}
    	
    	return bo;
    }
    
    /**
     * 获取对阵
     * @return
     */
    @RequestMapping(value = "/getMatch",method = RequestMethod.GET)
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
    public Object getMatch(){
        String result = null;
        try {
            result = HttpUtil.doGet(lotto_crawl_url+"sport/lottery/footBallMatchInterim");
            result = HttpUtil.doGet(lotto_crawl_url+"sport/lottery/footBallMatchList");
            result = HttpUtil.doGet(lotto_crawl_url+"sport/lottery/poolResultList");
            result = HttpUtil.doGet(lotto_crawl_url+"sport/lottery/getWxMatchOddList");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return getResultSuccess(result);
    }
    
    /**
     * 导出excel
     * @throws IOException 
     */
    @RequestMapping("export")
    @Authority(privilege=AuthEnum.SEARCH)
    public void export(HttpServletResponse response,SMGLotteryParamVO vo) throws IOException{
    	
    	vo.setLotteryCode(300);
    	ByteArrayOutputStream outputStream=fbLotteryService.getExcelStream(vo);
    	excel("fb", outputStream, response);
    }
    
    @RequestMapping("/order/matchlist")
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public Object orderMatchList(@RequestParam(value = "lotteryCode") Integer lotteryCode, @RequestParam(value = "lotteryChildCode") Integer lotteryChildCode,
    		@RequestParam(value = "systemCode") String systemCode, @RequestParam(value = "content") String content){
    	return fbLotteryService.findFbOrderMatchDetail(lotteryCode, lotteryChildCode, systemCode, content);
    }      
}
