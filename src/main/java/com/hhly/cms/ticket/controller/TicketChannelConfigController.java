package com.hhly.cms.ticket.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.taskmgr.service.TaskService;
import com.hhly.cms.ticket.service.ITicketChannelConfigService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketConfigVO;
/**
 * 
 * @desc 出票渠道配置
 * @author jiangwei
 * @date 2017-2-5
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/ticketmgr/ticketconfig")
public class TicketChannelConfigController extends BaseController {
	//出票商组
	public static final String DEALER_GROUP = "12";
    
    @Autowired
    private ITicketChannelConfigService ticketChannelConfigService;
    
    @Autowired
    private TaskService taskService;
    
    @RequestMapping()
    @Authority(privilege=AuthEnum.SEARCH)
    public String  index(){
        return "ticketmgr/ticket_channel_config";
    }
    
    @RequestMapping(value = "/list")
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public  Object list(TicketConfigVO vo){
        return ticketChannelConfigService.listTicketConfig(vo);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    @Authority(privilege=AuthEnum.UPD)
    @ResponseBody
    @ParameterValid
    public  Object udpate(@Valid(GroupValue.UPD)TicketConfigVO vo,HttpSession session) {
        vo.setModifyBy(getUserRealName(session));
        int num = ticketChannelConfigService.updTicketConfig(vo);
        updateDealer(vo.getLotteryCode().toString());
        return getSaveResult(num);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @Authority(privilege=AuthEnum.ADD)
    @ResponseBody
    @ParameterValid
    public  Object add(@Valid(GroupValue.ADD)TicketConfigVO vo,HttpSession session) {
        vo.setCreateBy(getUserRealName(session));
        int num = ticketChannelConfigService.addTicketConfig(vo);
        updateDealer(vo.getLotteryCode().toString());
        return getSaveResult(num);
    }
    /**
     * 修改出票商任务组
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月11日 上午11:11:44
     * @param lotteryCode
     */
    private void updateDealer(String lotteryCode){
    	 Map<String, String> param = new HashMap<>();
         param.put("lotteryCode",lotteryCode);
         taskService.runTaskByGroup(DEALER_GROUP,param);
    }
    
}
