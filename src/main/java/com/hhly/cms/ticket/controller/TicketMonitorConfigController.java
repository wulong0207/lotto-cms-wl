package com.hhly.cms.ticket.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.ticket.service.ITicketMonitorService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.JsonUtil;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.base.valid.util.ParamUtil;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketMonitorCommonVO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketMonitorConfigVO;

/**
 * @desc 出票报警管理类
 * @author jiangwei
 * @date 2017-2-13
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/ticketmgr/monitorconfig")
public class TicketMonitorConfigController extends BaseController{
    @Autowired
    private ITicketMonitorService ticketMonitorService;
    
    @RequestMapping()
    @Authority(privilege=AuthEnum.SEARCH)
    public String  index(){
        return "ticketmgr/monitor_config";
    }
    
    /**
     * 分页查询
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-13 下午5:09:59
     * @param vo
     * @return
     */
    @RequestMapping(value = "/list")
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public  Object list(TicketMonitorConfigVO vo){
        return ticketMonitorService.listTicketMonitorConfig(vo);
    }
    /**
     * 修改监控信息配置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-13 下午5:10:16
     * @param vo
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @Authority(privilege=AuthEnum.UPD)
    @ResponseBody
    @ParameterValid
    public  Object udpate(@Valid(GroupValue.UPD)TicketMonitorConfigVO vo,HttpSession session) {
        vo.setModifyBy(getUserRealName(session));
        int num = ticketMonitorService.updTicketMonitorConfig(vo);
        return getSaveResult(num);
    }
    
    /**
     * 添加监控信息配置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-13 下午5:10:36
     * @param vo
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @Authority(privilege=AuthEnum.ADD)
    @ResponseBody
    @ParameterValid
    public  Object add(@Valid(GroupValue.ADD)TicketMonitorConfigVO vo,HttpSession session) {
        vo.setCreateBy(getUserRealName(session));
        vo.setModifyBy(vo.getCreateBy());
        int num = ticketMonitorService.addTicketMonitorConfig(vo);
        return getSaveResult(num);
    }
    
    /**
     * 获取常规配置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-13 下午6:06:01
     * @return
     */
    @RequestMapping(value = "/common/list")
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public  Object list(){
        return ticketMonitorService.listTicketMonitorCommon();
    }
    
    /**
     * 修改常规配置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-13 下午6:08:18
     * @param json
     * @return
     */
    @RequestMapping(value = "/common",method = RequestMethod.PUT)
    @Authority(privilege=AuthEnum.UPD)
    @ResponseBody
    public  Object update(@RequestParam(value = "json", required = true) String json,HttpSession session){
        @SuppressWarnings("unchecked")
        List<TicketMonitorCommonVO> list = JsonUtil.json2ObjectList(json,TicketMonitorCommonVO.class);
        for (TicketMonitorCommonVO vo : list) {
            ParamUtil.validation(vo,GroupValue.UPD);
            vo.setModifyBy(getUserRealName(session));
        }
        ticketMonitorService.updTicketMonitorCommon(list);
        return getResultSuccess(null);
    }
}
