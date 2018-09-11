package com.hhly.cms.ticket.service.impl;

import com.hhly.cms.ticket.service.ITicketChannelService;
import com.hhly.cmscore.cms.remote.service.ITicketMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketChannelBO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketChannelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TicketChannelServiceImpl implements ITicketChannelService {
    
     @Autowired
     private ITicketMgrService iMaintenanceMgrService;

    @Override
    public PagingBO<TicketChannelBO> listOrderTicketChannel(
            TicketChannelVO vo) {
        return iMaintenanceMgrService.listTicketChannel(vo);
    }

    @Override
    public List<DictionaryBO> listDrawerNameDicticonary() {
        List<String> list = iMaintenanceMgrService.listDrawerName();
        List<DictionaryBO> dicList = new ArrayList<DictionaryBO>();
        DictionaryBO dic = null;
        for (String name : list) {
            dic = new DictionaryBO();
            dic.setId(name);
            dic.setText(name);
            dicList.add(dic);
        }
        return dicList;
    }

    @Override
    public int updOrderTicketChannel(TicketChannelVO vo) {
        return iMaintenanceMgrService.updOrderTicketChannel(vo);
    }

    @Override
    public int addOrderTicketChannel(TicketChannelVO vo) {
        return iMaintenanceMgrService.addOrderTicketChannel(vo);
    }

    @Override
    public List<DictionaryBO> listDrawerIdNameDicticonary(String lotteryCode) {
        List<TicketChannelBO> list = iMaintenanceMgrService.listDrawerIdName(lotteryCode);
        List<DictionaryBO> dicList = new ArrayList<DictionaryBO>();
        DictionaryBO dic = null;
        for (TicketChannelBO tc : list) {
            dic = new DictionaryBO();
            dic.setId(tc.getTicketChannelId());
            dic.setText(tc.getDrawerName());
            dicList.add(dic);
        }
        return dicList;
    }
}
