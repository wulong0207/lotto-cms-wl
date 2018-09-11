package com.hhly.cms.ticket.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ticketmgr.bo.OrderTicketMonitorBO;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketMonitorCommonBO;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketMonitorConfigBO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketMonitorCommonVO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketMonitorConfigVO;

import java.util.List;

/**
 * @desc 票监控配置
 * @author jiangwei
 * @date 2017-2-13
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface ITicketMonitorService {

    /**
     * 出票监控配置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-16 上午11:32:23
     * @param vo
     * @return
     */
    PagingBO<TicketMonitorConfigBO> listTicketMonitorConfig(TicketMonitorConfigVO vo);

    /**
     * 修改出票监控配置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-16 上午11:32:14
     * @param vo
     * @return
     */
    int updTicketMonitorConfig(TicketMonitorConfigVO vo);

    /**
     * 添加出票监控配置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-16 上午11:31:59
     * @param vo
     * @return
     */
    int addTicketMonitorConfig(TicketMonitorConfigVO vo);

    /**
     * 出票监控常规配置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-16 上午11:31:50
     * @return
     */
    List<TicketMonitorCommonBO> listTicketMonitorCommon();
    
    /**
     * 修改出票监控常规配置
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-16 上午11:31:32
     * @param list
     * @return
     */
    int updTicketMonitorCommon(List<TicketMonitorCommonVO> list);
    
    /**
     * 出票监控信息
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-16 上午11:31:19
     * @return
     */
    OrderTicketMonitorBO getTicketMonitor(int lotteryCode);
    
    /**
     * 查询出票监控设置的彩种code集合
     * @return
     * @date 2017年8月19日下午4:58:04
     * @author cheng.chen
     */
    List<String> queryLotteryMonitorConfig(Integer lotteryCategory);


}
