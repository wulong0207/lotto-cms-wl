package com.hhly.cms.ticket.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.ticket.service.ITicketChannelService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketChannelVO;
/**
 * 
 * @desc 出票渠道管理
 * @author jiangwei
 * @date 2017-2-5
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/ticketmgr/ticketchannel")
public class TicketChannelController extends BaseController {
    
    @Autowired
    private ITicketChannelService ticketChannelService;
    
    @RequestMapping()
    @Authority(privilege=AuthEnum.SEARCH)
    public String  index(){
        return "ticketmgr/ticket_channel";
    }
    
    @RequestMapping(value = "/list")
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public  Object list(TicketChannelVO vo){
        return ticketChannelService.listOrderTicketChannel(vo);
    }
    
    @RequestMapping(value = "/drawer/dictionary")
    @Authority(privilege = AuthEnum.ALL)
    @ResponseBody
    public  Object drawerName() {
        return ticketChannelService.listDrawerNameDicticonary();
    }
    /**
     * 获取渠道名称和ID的字典
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-6 下午4:58:46
     * @return
     */
    @RequestMapping(value = "/drawer/idname/dictionary")
    @Authority(privilege = AuthEnum.ALL)
    @ResponseBody
    public  Object drawerIdName(String lotteryCode) {
        return ticketChannelService.listDrawerIdNameDicticonary(lotteryCode);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    @Authority(privilege=AuthEnum.UPD)
    @ResponseBody
    @ParameterValid
    public  Object udpate(@Valid(GroupValue.UPD)TicketChannelVO vo,HttpSession session) {
        vo.setModifyBy(getUserRealName(session));
        int num = ticketChannelService.updOrderTicketChannel(vo);
        return getSaveResult(num);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @Authority(privilege=AuthEnum.ADD)
    @ResponseBody
    @ParameterValid
    public  Object add(@Valid(GroupValue.ADD)TicketChannelVO vo,HttpSession session) {
        vo.setCreateBy(getUserRealName(session));
        vo.setModifyBy(vo.getCreateBy());
        int num = ticketChannelService.addOrderTicketChannel(vo);
        return getSaveResult(num);
    }
}
