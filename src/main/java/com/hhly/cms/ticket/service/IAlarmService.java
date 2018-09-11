package com.hhly.cms.ticket.service;

import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketAlarmConfigBO;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketAlarmInfoBO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketAlarmConfigVO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketAlarmInfoVO;

/**
 * 
 * @desc 报警业务处理
 * @author jiangwei
 * @date 2017-2-5
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface IAlarmService {

    PagingBO<TicketAlarmInfoBO> listTicketAlarmInfo(TicketAlarmInfoVO vo);

    int updTicketAlarmInfoStatus(TicketAlarmInfoVO vo);

    PagingBO<TicketAlarmConfigBO> listTicketAlarmConfig(TicketAlarmConfigVO vo);

    int updTicketAlarmConfig(TicketAlarmConfigVO vo);

    int addTicketAlarmConfig(TicketAlarmConfigVO vo);
    
	int addTicketAlarmInfo(TicketAlarmInfoVO vo);

	List<TicketAlarmInfoBO> find(TicketAlarmInfoVO vo);

}
