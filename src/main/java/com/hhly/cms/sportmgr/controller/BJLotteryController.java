package com.hhly.cms.sportmgr.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.sportmgr.service.BJLotteryService;
import com.hhly.cms.utils.Authority;
import com.hhly.cmscore.cms.remote.service.IRedisMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.valid.util.ParamUtil;
import com.hhly.skeleton.cms.sportmgr.bo.SportBJLotteryInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.BJDataVO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;

/**
 * Created by lgs on 2016/12/1.
 * 竞彩足球管理
 */
@Controller
@RequestMapping(value = "lotterymgr/bj")
public class BJLotteryController extends BaseController {

    @Autowired
    private BJLotteryService bjLotteryService;
    
	@Autowired
	private IRedisMgrService redisMgrService;

    @RequestMapping(method = RequestMethod.GET)
    @Authority(privilege = AuthEnum.SEARCH)
    public String index() {
        return "lotterymgr/bj_lottery";
    }


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
    public PagingBO<SportBJLotteryInfoBO> list(SMGLotteryParamVO vo) {
        PagingBO<SportBJLotteryInfoBO> result = bjLotteryService.findBJData(vo);
        return result;
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
        return getResultSuccess(bjLotteryService.findBJSp(againstId));
    }

    /**
     * 保存表单数据
     *
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @Authority(privilege = AuthEnum.UPD)
    @ResponseBody
    public Object save(BJDataVO vo,final HttpSession session) {
        ParamUtil.validation(vo, "upd");
        vo.setModifyBy(super.getUserRealName(session));
        vo.setModifyTime(new Date());
        bjLotteryService.saveBJData(vo);
        List<String> cacheList = new ArrayList<>();
        cacheList.add(CacheConstants.S_COMM_SPORT_BJ_MATCH);
        redisMgrService.delCacheList(cacheList, true);
        return ResultBO.ok();
    }

    /**
     * 保存表格编辑数据
     *
     * @param json
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @Authority(privilege = AuthEnum.UPD)
    @ResponseBody
    public Object save(@RequestParam(value = "json", required = true) String json, final HttpSession session) {
        BJDataVO vo = JSON.parseObject(json, BJDataVO.class);
        ParamUtil.validation(vo, "upd");
        vo.setModifyBy(super.getUserRealName(session));
        vo.setModifyTime(new Date());
        bjLotteryService.saveBJData(vo);
        List<String> cacheList = new ArrayList<>();
        cacheList.add(CacheConstants.S_COMM_SPORT_BJ_MATCH);
        redisMgrService.delCacheList(cacheList, true);
        return ResultBO.ok();
    }
    
    @RequestMapping(value = "/updCheckScore", method = RequestMethod.PUT)
    @Authority(privilege = AuthEnum.UPD)
    @ResponseBody
    public Object updCheckScore(HttpSession session, @RequestParam(value="lotteryCode")String lotteryCode){
    	ResultBO<?> bo = null;
    	int num = bjLotteryService.updCheckScore(lotteryCode, getUserName(session));
    	if(num == 0){
    		bo = ResultBO.err("30702");
    	}else{
    		List<String> cacheList = new ArrayList<>();
            cacheList.add(CacheConstants.S_COMM_SPORT_BJ_MATCH);
            redisMgrService.delCacheList(cacheList, true);
    		bo = getSaveResult(num);
    	}
    	
    	return bo;
    }
    
    /**
     * 导出excel
     * @throws IOException 
     */
    @RequestMapping("export")
    @Authority(privilege=AuthEnum.SEARCH)
    public void export(HttpServletResponse response,SMGLotteryParamVO vo) throws IOException{
    	
    	vo.setLotteryCode(306);
    	ByteArrayOutputStream outputStream=bjLotteryService.getExcelStream(vo);
    	excel("bj", outputStream, response);
    }
    
    @RequestMapping("/order/matchlist")
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public Object orderMatchList(@RequestParam(value = "lotteryCode") Integer lotteryCode, @RequestParam(value = "lotteryChildCode") Integer lotteryChildCode,
    		@RequestParam(value = "systemCode") String systemCode, @RequestParam(value = "content") String content){
    	return bjLotteryService.findBjOrderMatchDetail(lotteryCode, lotteryChildCode, systemCode, content);
    } 
    
}
