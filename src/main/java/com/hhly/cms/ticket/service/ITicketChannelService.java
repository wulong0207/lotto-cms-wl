package com.hhly.cms.ticket.service;


import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketChannelBO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketChannelVO;

import java.util.List;

/**
 * 
 * @desc 出票渠道管理
 * @author jiangwei
 * @date 2017-2-5
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface ITicketChannelService {
    
    PagingBO<TicketChannelBO> listOrderTicketChannel(TicketChannelVO vo);
    /**
     * 查询所有出票商名封装为字典
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017-2-6 上午10:13:03
     * @return
     */
    List<DictionaryBO> listDrawerNameDicticonary();

    int updOrderTicketChannel(TicketChannelVO vo);

    int addOrderTicketChannel(TicketChannelVO vo);
    
    List<DictionaryBO> listDrawerIdNameDicticonary(String lotteryCode);

}
