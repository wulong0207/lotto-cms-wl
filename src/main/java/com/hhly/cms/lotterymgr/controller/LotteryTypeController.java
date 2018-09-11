package com.hhly.cms.lotterymgr.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.lotterymgr.service.LotteryTypeService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.JsonUtil;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.common.LotteryEnum.SynIssue;
import com.hhly.skeleton.base.common.cache.lottery.LotteryCacheEnum.LotteryTypeCacheEnum;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.base.valid.util.ParamUtil;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryBettingMulCmsVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryChildCmsVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryTypeVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryWinningVO;

/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-11-17 上午10:48:44
 * @Desc 彩种管理
 */
@Controller
@RequestMapping(value = "/lotterymgr/type")
public class LotteryTypeController extends BaseController {
    @Autowired
    private LotteryTypeService lotteryTypeService;
    @RequestMapping()
    @Authority(privilege = AuthEnum.SEARCH)
    public String index() {
        return "lotterymgr/lottery_type";
    }

    @RequestMapping(value = "/list")
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
    public Object list(LotteryTypeVO vo) {
        return lotteryTypeService.find(vo);
    }

    /**
     * 查询投注注数，倍数截止时间
     * 
     * @param code
     * @return
     */
    @RequestMapping(value = "/betting/list")
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
    public Object bettingList(
            @RequestParam(value = "code", required = true) String code) {
        StringVO vo = new StringVO();
        vo.setStr(code);
        return lotteryTypeService.findBettingMul(vo);

    }

    /**
     * 查询彩种的子玩法销售
     * 
     * @param code
     * @return
     */
    @RequestMapping(value = "/child/list")
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
    public Object childList(
            @RequestParam(value = "code", required = true) String code) {
        StringVO vo = new StringVO();
        vo.setStr(code);
        return lotteryTypeService.findChild(vo);

    }

    /**
     * 修改彩期
     * 
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @Authority(privilege = AuthEnum.UPD)
    @ResponseBody
    @ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryTypeCacheEnum.class)
    public Object udpate(@Valid(GroupValue.UPD) LotteryTypeVO vo, HttpSession session) {
        vo.setModifyBy(getUserRealName(session));
        validVO(vo);
        int num = lotteryTypeService.updLotteryType(vo);
        return getSaveResult(num);
    }

    /**
     * 新增彩种
     * 
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @Authority(privilege = AuthEnum.ADD)
    @ResponseBody
    @ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryTypeCacheEnum.class)
    public Object add(@Valid(GroupValue.ADD) LotteryTypeVO vo, HttpSession session) {
        vo.setCreateBy(getUserRealName(session));
        validVO(vo);
        int num = lotteryTypeService.addLotteryType(vo);
        return getSaveResult(num);
    }

    /**
     * 验证vo
     * 
     * @param vo
     */
    private void validVO(LotteryTypeVO vo) {
		if (Objects.equals(SynIssue.SYN.getValue(), vo.getSynIssue()) 
				&& Lottery.isOldFoot(vo.getLotteryCode())) {
			return;
		}
        switch (vo.getLotteryCategory()) {
        case 1:
        	Assert.hasText(vo.getDrawTime(), "官方开奖时间不能为空");
        	Assert.hasText(vo.getStartSailTime(), "官方可送票时间不能为空");
            Assert.hasText(vo.getEndSailTime(), "官方截止销售时间不能为空");
            break;
        case 2:
        	Assert.hasText(vo.getStartSailTime(), "官方可送票时间不能为空");
            Assert.hasText(vo.getEndSailTime(), "官方截止销售时间不能为空");
            Assert.hasText(vo.getSailDayCycle(), "销售日销售周期不能为空");
            break;
        case 3:
        	Assert.hasText(vo.getStartSailTime(), "官方可送票时间不能为空");
            Assert.hasText(vo.getEndSailTime(), "官方截止销售时间不能为空");
            break;
        case 4:
            break;
        default:
            throw new IllegalArgumentException("彩种类型错误");
        }
        if(ObjectUtil.isBlank(vo.getMinBet())||vo.getMinBet()<2){
        	throw new IllegalArgumentException("最低投注金额不能空并且最低2元");
        }
        if(ObjectUtil.isBlank(vo.getMinMultiple())||vo.getMinMultiple()<1){
        	throw new IllegalArgumentException("最低投注倍数不能空并且最低1倍");
        }
        if(ObjectUtil.isBlank(vo.getMrMultiple())||vo.getMrMultiple()<1){
        	throw new IllegalArgumentException("默认最低投注倍数不能空并且最低1倍");
        }
    }
    
    @RequestMapping(value = "/betting", method = RequestMethod.POST)
    @Authority(privilege = AuthEnum.ADD)
    @ResponseBody
    @ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryTypeCacheEnum.class)
    public Object addBetting(@Valid(GroupValue.ADD) LotteryBettingMulCmsVO vo) {
        int num = lotteryTypeService.addLotteryBettingMul(vo);
        return getSaveResult(num);
    }

    @RequestMapping(value = "/child", method = RequestMethod.POST)
    @Authority(privilege = AuthEnum.ADD)
    @ResponseBody
    @ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryTypeCacheEnum.class)
    public Object addChild(@Valid(GroupValue.ADD) LotteryChildCmsVO vo) {
        int num = lotteryTypeService.addLotteryChild(vo);
        return getSaveResult(num);
    }

    @RequestMapping(value = "/betting", method = RequestMethod.PUT)
    @Authority(privilege = AuthEnum.UPD)
    @ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryTypeCacheEnum.class)
    public Object updBetting(
            @RequestParam(value = "json", required = true) String json) {
        @SuppressWarnings("unchecked")
        List<LotteryBettingMulCmsVO> list = JsonUtil.json2ObjectList(json,
                LotteryBettingMulCmsVO.class);
        for (LotteryBettingMulCmsVO vo : list) {
            ParamUtil.validation(vo, null);
        }
        int num = lotteryTypeService.saveLotteryBettingMul(list);
        return getSaveResult(num);
    }

    @RequestMapping(value = "/child", method = RequestMethod.PUT)
    @Authority(privilege = AuthEnum.UPD)
    @ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryTypeCacheEnum.class)
    public Object updChild(
            @RequestParam(value = "json", required = true) String json) {
        @SuppressWarnings("unchecked")
        List<LotteryChildCmsVO> list = JsonUtil.json2ObjectList(json,
                LotteryChildCmsVO.class);
        for (LotteryChildCmsVO vo : list) {
            ParamUtil.validation(vo, null);
        }
        int num = lotteryTypeService.saveLotteryChild(list);
        return getSaveResult(num);
    }

    @RequestMapping(value = "/betting/{id}", method = RequestMethod.DELETE)
    @Authority(privilege = AuthEnum.DEL)
    @ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryTypeCacheEnum.class)
    public Object delBetting(@PathVariable(value = "id") String id) {
        StringVO vo = new StringVO();
        vo.setStr(id);
        int num = lotteryTypeService.delBettingMul(vo);
        return getSaveResult(num);
    }

    @RequestMapping(value = "/child/{id}", method = RequestMethod.DELETE)
    @Authority(privilege = AuthEnum.DEL)
    @ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = LotteryTypeCacheEnum.class)
    public Object delChild(@PathVariable(value = "id") String id) {
        StringVO vo = new StringVO();
        vo.setStr(id);
        int num = lotteryTypeService.delChild(vo);
        return getSaveResult(num);
    }
    /**
     * 导出彩种类型到excel
     * 
     * @param response
     * @param category
     * @param code
     * @throws IOException
     */
    @RequestMapping("excel")
    @Authority(privilege = AuthEnum.EXPORT)
    public void exportOpExcel(HttpServletResponse response, LotteryTypeVO vo)
            throws IOException {
        ByteArrayOutputStream outputStream = lotteryTypeService
                .getLotteryTypeExcel(vo);
        excel("lotteryType", outputStream, response);
    }

    /**
     * 查询彩种
     * 
     * @param code
     * @return
     */
    @RequestMapping(value = "/dictionary")
    @Authority(privilege = AuthEnum.ALL)
    @ResponseBody
    public Object listTypeDic(
            @RequestParam(value = "code", required = false) String code) {
        StringVO vo = new StringVO();
        vo.setStr(code);
        return lotteryTypeService.findTypeDictionary(vo);
    }

    /**
     * @desc 彩种子玩法字典
     * @author huangb
     * @date 2017年1月19日
     * @param code
     * @return
     */
    @RequestMapping(value = "/child/dictionary")
    @Authority(privilege = AuthEnum.ALL)
    @ResponseBody
    public Object childDic(LotteryChildCmsVO lotteryChildCms) {
        return lotteryTypeService.findChildDictionary(lotteryChildCms);
    }
    
	/**
	 * 彩种子玩法字典
	 * 
	 * @param lotteryCodeStr
	 * @return
	 * @date 2017年5月9日上午11:03:30
	 * @author cheng.chen
	 */
	@RequestMapping(value = "/child/dic")
	@Authority(privilege = AuthEnum.ALL)
	@ResponseBody
	public Object childDic(@RequestParam(value = "lotteryCodeStr", required = true) String lotteryCodeStr) {
		if(!StringUtil.isBlank(lotteryCodeStr)){
			String[] lotteryCodes = lotteryCodeStr.split(SymbolConstants.COMMA);
			List<Integer> lotteryCodeList = new ArrayList<Integer>();
			for (String lotteryCode : lotteryCodes) {
				lotteryCodeList.add(Integer.valueOf(lotteryCode));
			}
			LotteryChildCmsVO lotteryChildCms = new LotteryChildCmsVO();
			lotteryChildCms.setLotteryCodeList(lotteryCodeList);
			return lotteryTypeService.findChildDictionary(lotteryChildCms);
		}
		return Collections.EMPTY_LIST;
	}
    
    /**
     * @desc 彩种中奖级别字典
     * @author huangb
     * @date 2017年2月24日
     * @param lotteryChildCms
     * @return
     */
    @RequestMapping(value = "/wingrade/dictionary")
    @Authority(privilege = AuthEnum.ALL)
    @ResponseBody
    public Object winGradeDic(LotteryWinningVO lotteryWinningVO) {
        return lotteryTypeService.findWinningDictionary(lotteryWinningVO);
    }
}
