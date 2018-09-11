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
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.sportmgr.service.OldFBLotteryService;
import com.hhly.cms.taskmgr.service.TaskService;
import com.hhly.cms.utils.Authority;
import com.hhly.cmscore.cms.remote.service.IRedisMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.TaskEnum;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.util.HttpUtil;
import com.hhly.skeleton.base.util.PropertyUtil;
import com.hhly.skeleton.base.valid.util.ParamUtil;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.lotto.base.sport.vo.OldDataVO;

/**
 * Created by lgs on 2016/12/1.
 * 老足彩管理
 */
@Controller
@RequestMapping(value = "lotterymgr/old_fb")
public class OldFBLotteryController extends BaseController{
	
	private static Logger log = Logger.getLogger(OldFBLotteryController.class);

    @Autowired
    private OldFBLotteryService oldFBLotteryService;
    
    @Autowired
    private TaskService taskService;
    
	@Autowired
	private IRedisMgrService redisMgrService;
	
	private static final String lotto_crawl_url = PropertyUtil.getPropertyValue("sys_dynamic.properties", "lotto_crawl_url");
    
    @RequestMapping(method = RequestMethod.GET)
    @Authority(privilege = AuthEnum.SEARCH)
    public String index(){
        return "lotterymgr/oldfb_lottery"; 
    }

    /**
     * 查询所有老足彩对阵
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/list" , method = RequestMethod.POST)
    @ResponseBody
    @Authority(privilege = AuthEnum.SEARCH)
    public Object list(SMGLotteryParamVO vo) {
        PagingBO<?> list= oldFBLotteryService.findOldLotteryInfo(vo);
        return list;
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
        return getResultSuccess(oldFBLotteryService.findOldSp(againstId));
    }


    /**
     * 保存表单数据
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @Authority(privilege = AuthEnum.UPD)
    public Object save(OldDataVO vo , final HttpSession session){
        vo.setModifyBy(super.getUserRealName(session));
        vo.setModifyTime(new Date());
        oldFBLotteryService.saveOldData(vo);
        try {
    		List<String> cacheList = new ArrayList<>();
	        cacheList.add(CacheConstants.S_COMM_SPORT_OLD_MATCH_LIST + "*");
	        redisMgrService.delCacheList(cacheList, false); 
	        
        	Map<String, String> param = new HashMap<>();
    		param.put("systemCodes", vo.getSystemCode());
    		taskService.runTaskSync(TaskEnum.TaskId.SPORT_MATCH_STAUTS_UPD.getValue(), param);    		
		} catch (Exception e) {
			log.error("老足彩保存表单删除缓存http请求  : " + e.getMessage());
		}
        return ResultBO.ok();
    }


    /**
     * 保存表格编辑数据
     * @param json
     * @return
     * @throws URISyntaxException 
     * @throws IOException 
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    @Authority(privilege = AuthEnum.UPD)
    public Object save(@RequestParam(value = "json",required = true) String json, final HttpSession session){
        OldDataVO vo  = JSON.parseObject(json,OldDataVO.class);
        vo.setModifyBy(super.getUserRealName(session));
        vo.setModifyTime(new Date());
        ParamUtil.validation(vo,"upd");
        oldFBLotteryService.saveOldData(vo);
        try {

    		List<String> cacheList = new ArrayList<>();
	        cacheList.add(CacheConstants.S_COMM_SPORT_OLD_MATCH_LIST + "*");
	        redisMgrService.delCacheList(cacheList, false); 
	        
        	Map<String, String> param = new HashMap<>();
    		param.put("systemCodes", vo.getSystemCode());
    		taskService.runTaskSync(TaskEnum.TaskId.SPORT_MATCH_STAUTS_UPD.getValue(), param);
    		
		} catch (Exception e) {
			log.error("老足彩保存表格删除缓存http请求  : " + e.getMessage());
		}
        return ResultBO.ok();
    }
    /**
     * 获取对阵
     *
     * @return
     */
    @RequestMapping(value = "/getMatch", method = RequestMethod.GET)
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
    public Object getMatch() {
        String result = null;
        try {
            result = HttpUtil.doGet(lotto_crawl_url + "sport/lottery/getOldSfcMatch");
            result = HttpUtil.doGet(lotto_crawl_url + "sport/lottery/getOldSixHFMatch");
            result = HttpUtil.doGet(lotto_crawl_url + "sport/lottery/getOldGoalMatch");
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
    	ByteArrayOutputStream outputStream=oldFBLotteryService.getExcelStream(vo);
    	excel("oldfb", outputStream, response);
    }
    
    @RequestMapping("/order/matchlist")
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public Object orderMatchList(@RequestParam(value = "lotteryCode") Integer lotteryCode, @RequestParam(value = "issueCode") String issueCode,
    		@RequestParam(value = "content") String content){
    	return oldFBLotteryService.findOldOrderMatchDetail(lotteryCode, issueCode, content);
    }
}
