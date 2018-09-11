package com.hhly.cms.ticket.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketConfigBO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketConfigVO;

/**
 * 
 * @desc 出票渠道配置
 * @author jiangwei
 * @date 2017-2-5
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface ITicketChannelConfigService {

    PagingBO<TicketConfigBO> listTicketConfig(TicketConfigVO vo);

    int updTicketConfig(TicketConfigVO vo);

    int addTicketConfig(TicketConfigVO vo);

}
