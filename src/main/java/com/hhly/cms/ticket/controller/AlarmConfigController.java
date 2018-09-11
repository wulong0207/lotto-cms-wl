package com.hhly.cms.ticket.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.ticket.service.IAlarmService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketAlarmConfigVO;
/**
 * 
 * @desc 报警信息
 * @author jiangwei
 * @date 2017-2-5
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/ticketmgr/alarmconfig")
public class AlarmConfigController extends BaseController {
    @Autowired
    private IAlarmService alarmService;
    
    /**
     * 报警配置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-8 上午9:51:04
     * @return
     */
    @RequestMapping()
    @Authority(privilege=AuthEnum.SEARCH)
    public String  indexConfig(){
        return "ticketmgr/alarm_config";
    }
    /**
     * 报警配置分页查询
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-8 上午9:56:17
     * @param vo
     * @return
     */
    @RequestMapping(value = "/list")
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public  Object list(TicketAlarmConfigVO vo){
        return alarmService.listTicketAlarmConfig(vo);
    }
   
    
    /**
     * 修改配置报警信息
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-8 上午10:27:24
     * @param vo
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @Authority(privilege=AuthEnum.UPD)
    @ResponseBody
    @ParameterValid
    public  Object udpate(@Valid(GroupValue.UPD)TicketAlarmConfigVO vo,HttpSession session) {
        vo.setModifyBy(getUserRealName(session));
        int num = alarmService.updTicketAlarmConfig(vo);
        return getSaveResult(num);
    }
    
    /**
     * 添加报警配置信息
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-8 上午10:27:38
     * @param vo
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @Authority(privilege=AuthEnum.ADD)
    @ResponseBody
    @ParameterValid
    public  Object add(@Valid(GroupValue.ADD)TicketAlarmConfigVO vo,HttpSession session) {
        vo.setCreateBy(getUserRealName(session));
        vo.setModifyBy(vo.getCreateBy());
        int num = alarmService.addTicketAlarmConfig(vo);
        return getSaveResult(num);
    }
    
}
