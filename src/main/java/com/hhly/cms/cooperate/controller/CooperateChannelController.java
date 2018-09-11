package com.hhly.cms.cooperate.controller;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.utils.Authority;
import com.hhly.cmscore.cms.remote.service.ICooperateService;
import com.hhly.skeleton.base.common.cache.cooperate.CoOperateCacheEnum;
import com.hhly.skeleton.base.util.EncryptUtil;
import com.hhly.skeleton.base.util.NumberUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.cms.cooperate.bo.CooperateAgencyBO;
import com.hhly.skeleton.cms.cooperate.bo.CooperateCdKeyLotteryNumBO;
import com.hhly.skeleton.cms.cooperate.bo.CooperateCdkeyBO;
import com.hhly.skeleton.cms.cooperate.bo.CooperateChannelBO;
import com.hhly.skeleton.cms.cooperate.vo.CooperateAgencyVO;
import com.hhly.skeleton.cms.cooperate.vo.CooperateCdkeyVO;
import com.hhly.skeleton.cms.cooperate.vo.CooperateChannelVO;
import com.hhly.skeleton.cms.cooperate.vo.CooperateLotteryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author lgs on
 * @version 1.0
 * @desc
 * @date 2018/3/7.
 * @company 益彩网络科技有限公司
 */
@Controller
@RequestMapping("/cooperate/channel")
public class CooperateChannelController extends BaseController {

    @Autowired
    private ICooperateService iCooperateService;

    @Authority(privilege = AuthEnum.SEARCH)
    @RequestMapping("/")
    public String index() {
        return "cooperate/channel";
    }

    @Authority(privilege = AuthEnum.SEARCH)
    @RequestMapping("/list")
    @ResponseBody
    public Object list(CooperateChannelVO vo) {
        return iCooperateService.findPaging(vo);
    }


    @Authority(privilege = AuthEnum.SEARCH)
    @RequestMapping("/dist")
    @ResponseBody
    public Object dist() {
        return iCooperateService.selectChannelNameDictBO();
    }


    @Authority(privilege = AuthEnum.SAVE)
    @RequestMapping("/save")
    @DeleteBatchAssignCache(GetCacheEnumService = CoOperateCacheEnum.ChannelCacheEnum.class)
    @ResponseBody
    public Object save(CooperateChannelVO vo, HttpSession session) {
        vo.setCreateBy(getUserName(session));
        vo.setModifyBy(getUserName(session));
        return getSaveResult(iCooperateService.saveCooperateChannel(vo));
    }

    @Authority(privilege = AuthEnum.DEL)
    @RequestMapping("/del")
    @DeleteBatchAssignCache(GetCacheEnumService = CoOperateCacheEnum.ChannelCacheEnum.class)
    @ResponseBody
    public Object del(CooperateChannelVO vo) {
        if(iCooperateService.delCooperateChannel(vo)>0){
            return getResultSuccess(1);
        }
        return getResultSuccess(0);
    }

    @Authority(privilege = AuthEnum.SEARCH)
    @RequestMapping("/balance")
    @ResponseBody
    public Object selectByChannelId(CooperateChannelVO vo) {
        if (ObjectUtil.isBlank(vo.getMarketChannelId())) {
            CooperateCdkeyVO cooperateCdkeyVO = new CooperateCdkeyVO();
            cooperateCdkeyVO.setPageIndex(1);
            cooperateCdkeyVO.setPageSize(1);
            List<CooperateCdKeyLotteryNumBO> list = iCooperateService.findCooperateCdKeyLotteryNumBOList(cooperateCdkeyVO);
            if (list.size() < 1) {
                return getResultSuccess(0);
            } else {
                return getResultSuccess(NumberUtil.mul(2, Double.valueOf(list.get(0).getUsedNewTotal())));
            }
        }
        CooperateChannelBO bo = iCooperateService.selectByChannelId(vo.getMarketChannelId());
        if (bo == null || bo.getBalance() == null) {
            return  getResultSuccess(0);
        }
        return getResultSuccess(bo.getBalance());
    }


    @Authority(privilege = AuthEnum.SEARCH)
    @RequestMapping(value = "/list-by-channelId")
    @ResponseBody
    public Object getListByChannelId(CooperateAgencyVO vo) {
        return iCooperateService.findCooperateAgencyBOByChannelId(vo);
    }


    @Authority(privilege = AuthEnum.SAVE)
    @RequestMapping(value = "/save-child-channel")
    @ResponseBody
    public Object saveChildChannel(CooperateAgencyVO vo, HttpSession session) {
        vo.setCreateBy(getUserName(session));
        vo.setModifyBy(getUserName(session));
        return getResultSuccess(iCooperateService.saveChildChannel(vo));
    }

    @Authority(privilege = AuthEnum.DEL)
    @RequestMapping(value = "/del-child-channel")
    @ResponseBody
    public Object delChildChannel(CooperateAgencyVO vo, HttpSession session) {
        vo.setCreateBy(getUserName(session));
        vo.setModifyBy(getUserName(session));
        return getResultSuccess(iCooperateService.delChildChannel(vo));
    }

    @Authority(privilege = AuthEnum.UPD)
    @DeleteBatchAssignCache(GetCacheEnumService = CoOperateCacheEnum.ChannelCacheEnum.class)
    @RequestMapping(value = "/reset-password")
    @ResponseBody
    public Object resetPassword(CooperateChannelVO vo, HttpSession session) throws NoSuchAlgorithmException {
        vo.setCreateBy(getUserName(session));
        vo.setModifyBy(getUserName(session));

        String rcode = EncryptUtil.getSalt();
        vo.setPassword(EncryptUtil.createDefaultPassword(rcode));
        vo.setRcode(rcode);
        return getResultSuccess(iCooperateService.resetPassword(vo));
    }

    /**
     * 更新合作结束渠道启动状态
     *
     * @return
     */
    @Authority(privilege = AuthEnum.ALL)
    @DeleteBatchAssignCache(GetCacheEnumService = CoOperateCacheEnum.ChannelCacheEnum.class)
    @RequestMapping(value = "/update-is-over")
    @ResponseBody
    public Object updateStop() {
        return getResultSuccess(iCooperateService.updateStop());
    }
}

