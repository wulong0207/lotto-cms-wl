package com.hhly.cms.ticket.service.impl;

import com.hhly.cms.ticket.service.IAlarmService;
import com.hhly.cmscore.cms.remote.service.ITicketMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketAlarmConfigBO;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketAlarmInfoBO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketAlarmConfigVO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketAlarmInfoVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AlarmServiceImpl implements IAlarmService {
    @Autowired
    private ITicketMgrService iMaintenanceMgrService;

    @Override
    public PagingBO<TicketAlarmInfoBO> listTicketAlarmInfo(TicketAlarmInfoVO vo) {
        return iMaintenanceMgrService.listTicketAlarmInfo(vo);
    }

    @Override
    public int updTicketAlarmInfoStatus(TicketAlarmInfoVO vo) {
        return iMaintenanceMgrService.updTicketAlarmInfoStatus(vo);
    }

    @Override
    public PagingBO<TicketAlarmConfigBO> listTicketAlarmConfig(
            TicketAlarmConfigVO vo) {
        return iMaintenanceMgrService.listTicketAlarmConfig(vo);
    }

    @Override
    public int updTicketAlarmConfig(TicketAlarmConfigVO vo) {
        return iMaintenanceMgrService.updTicketAlarmConfig(vo);
    }

    @Override
    public int addTicketAlarmConfig(TicketAlarmConfigVO vo) {
        return iMaintenanceMgrService.addTicketAlarmConfig(vo);
    }

	@Override
	public int addTicketAlarmInfo(TicketAlarmInfoVO vo) {
		return iMaintenanceMgrService.addTicketAlarmInfo(vo);
	}

	@Override
	public List<TicketAlarmInfoBO> find(TicketAlarmInfoVO vo) {
		return iMaintenanceMgrService.findTicketAlarmInfo(vo);
	}
}
