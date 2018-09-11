package com.hhly.cms.ticket.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.base.rabbitmq.AlarmInfoListenter;
import com.hhly.cms.ticket.service.IAlarmService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketAlarmInfoBO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketAlarmInfoVO;
/**
 * 
 * @desc 报警信息
 * @author jiangwei
 * @date 2017-2-5
 * @company 益彩网络科技公司
 * @version 1.0
 */
/**
 * @desc    
 * @author  Tony Wang
 * @date    2017年10月25日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/ticketmgr/alarm")
public class AlarmInfoController extends BaseController {
    @Autowired
    private IAlarmService alarmService;
    
    /**
     * 报警信息
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-8 上午9:50:50
     * @return
     */
    @RequestMapping()
    @Authority(privilege=AuthEnum.SEARCH)
    public String  index(){
        return "ticketmgr/alarm_info";
    }
    /**
     * 报警信息分页查询
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-8 上午9:56:03
     * @param vo
     * @return
     */
    @RequestMapping(value = "/list")
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public  Object list(TicketAlarmInfoVO vo){
        return alarmService.listTicketAlarmInfo(vo);
    }
    /**
     * 批量处理报警信息
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-7 下午6:05:14
     * @param id 信息主键ID
     * @param session
     * @return
     */
    @RequestMapping(value="/dispose/{id}",method = RequestMethod.PUT)
    @Authority(privilege=AuthEnum.UPD)
    @ResponseBody
    public  Object udpdateStatus(@PathVariable(value="id")String id,HttpSession session) {
        TicketAlarmInfoVO vo = new TicketAlarmInfoVO();
        vo.setStatus((short)1);
        vo.setDealBy(getUserRealName(session));
        String [] ids= StringUtils.tokenizeToStringArray(id, SymbolConstants.UNDERLINE);
        vo.setIds(Arrays.asList(ids));
        Assert.isTrue(!vo.getIds().isEmpty(), "请选中行数据！");
        alarmService.updTicketAlarmInfoStatus(vo);
        for(String tmpId : ids) {
        	AlarmInfoListenter.remove(tmpId);
        }
        return getResultSuccess(null);
    }
    

    /**
     * @desc   获取警报信息，用于monitor_common.jsp的走马灯中
     * @author Tony Wang
     * @create 2017年10月25日
     * @return 
     */
    @RequestMapping(value = "/marquee",method=RequestMethod.GET)
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public Map<String, String> getAlarmInfoByMq() {
    	return AlarmInfoListenter.getInfo();
    }
    
    /**
     * @desc   首次访问monitor_common.jsp时，初始化报警信息map
     * @author Tony Wang
     * @create 2017年10月25日
     * @param vo 
     */
    @RequestMapping(value = "/marquee",method=RequestMethod.PUT)
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public void initAlarmInfoByMq(TicketAlarmInfoVO vo) {
    	// 0未处理;1处理
    	vo.setStatus((short)0);
    	// 前100条
    	vo.setPageIndex(0);
    	vo.setPageSize(20);
    	vo.setAlarmChildList(AlarmInfoListenter.getAlarmChildList());
    	vo.setAlarmType((short)1);
    	// 查询表中未处理的报警信息，初始化到map中
    	List<TicketAlarmInfoBO> alarmInfos = alarmService.find(vo);
    	AlarmInfoListenter.init(alarmInfos);
    }
}
