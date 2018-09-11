package com.hhly.cms.cooperate.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.utils.Authority;
import com.hhly.cmscore.cms.remote.service.ICooperateService;
import com.hhly.skeleton.cms.cooperate.vo.CooperateLotteryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author lgs on
 * @version 1.0
 * @desc 商户兑换彩种价格
 * @date 2018/3/8.
 * @company 益彩网络科技有限公司
 */
@Controller
@RequestMapping("/cooperate/lottery")
public class CooperateLotteryController extends BaseController {


    @Autowired
    private ICooperateService iCooperateService;

    @Authority(privilege = AuthEnum.ALL)
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(CooperateLotteryVO vo) {
        return iCooperateService.findCooperateLotteryPagingBO(vo);
    }


    @Authority(privilege = AuthEnum.ALL)
    @RequestMapping(value = "/list-by-channelId")
    @ResponseBody
    public Object getListByChannelId(CooperateLotteryVO vo) {
        return iCooperateService.findCooperateLotteryListByChannelId(vo);
    }


    /**
     * 保存商户彩种信息
     *
     * @param vo
     * @return
     */
    @Authority(privilege = AuthEnum.ALL)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Object save(CooperateLotteryVO vo, HttpSession session) {
        vo.setCreateBy(getUserName(session));
        vo.setModifyBy(getUserName(session));
        return getSaveResult(iCooperateService.saveCooperateLottery(vo));
    }

    /**
     * 删除彩种
     *
     * @param vo
     * @return
     */
    @Authority(privilege = AuthEnum.ALL)
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Object del(CooperateLotteryVO vo, HttpSession session) {
        vo.setCreateBy(getUserName(session));
        vo.setModifyBy(getUserName(session));
        return getSaveResult(iCooperateService.delCooperateLottery(vo));
    }
}
