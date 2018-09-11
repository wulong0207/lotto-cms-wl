package com.hhly.cms.ticket.service.impl;

import com.hhly.cms.ticket.service.ITicketMonitorService;
import com.hhly.cmscore.cms.remote.service.ITicketMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ticketmgr.bo.OrderTicketMonitorBO;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketMonitorCommonBO;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketMonitorConfigBO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketMonitorCommonVO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketMonitorConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketMonitorServiceImpl implements ITicketMonitorService {
    @Autowired
    private ITicketMgrService ticketMgrService;

    @Override
    public PagingBO<TicketMonitorConfigBO> listTicketMonitorConfig(
            TicketMonitorConfigVO vo) {
        return ticketMgrService.listTicketMonitorConfig(vo);
    }

    @Override
    public int updTicketMonitorConfig(TicketMonitorConfigVO vo) {
        return ticketMgrService.updTicketMonitorConfig(vo);
    }

    @Override
    public int addTicketMonitorConfig(TicketMonitorConfigVO vo) {
        return ticketMgrService.addTicketMonitorConfig(vo);
    }

    @Override
    public List<TicketMonitorCommonBO> listTicketMonitorCommon() {
        return ticketMgrService.listTicketMonitorCommon();
    }

    @Override
    public int updTicketMonitorCommon(List<TicketMonitorCommonVO> list) {
        return ticketMgrService.updTicketMonitorCommon(list);
    }

    @Override
    public OrderTicketMonitorBO getTicketMonitor(int lotteryCode) {
        return ticketMgrService.getTicketMonitor(lotteryCode);
    }

	@Override
	public List<String> queryLotteryMonitorConfig(Integer lotteryCategory) {
		return ticketMgrService.queryLotteryMonitorConfig(lotteryCategory);
	}
    
    
}
