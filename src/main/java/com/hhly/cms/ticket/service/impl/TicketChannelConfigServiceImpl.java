package com.hhly.cms.ticket.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.ticket.service.ITicketChannelConfigService;
import com.hhly.cmscore.cms.remote.service.ITicketMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketConfigBO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketConfigVO;
@Service
public class TicketChannelConfigServiceImpl implements ITicketChannelConfigService {
    
     @Autowired
     private ITicketMgrService iMaintenanceMgrService;
     

    @Override
    public PagingBO<TicketConfigBO> listTicketConfig(TicketConfigVO vo) {
    	PagingBO<TicketConfigBO> result = iMaintenanceMgrService.listTicketConfig(vo);
    	if(CollectionUtils.isNotEmpty(result.getData())){
    		for (TicketConfigBO bo  : result.getData()) {
    			if(StringUtils.isEmpty(bo.getEndSendSpace())){
    				continue;
    			}
    			String [] str = bo.getEndSendSpace().split(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR);
    			if(str.length != 3){
    				continue;
    			}
    			bo.setBeforeTime(str[0]);
    			bo.setInterval(str[1]);
    			bo.setAfterTime(str[2]);
			}
    	}
        return result;
    }

    @Override
    public int updTicketConfig(TicketConfigVO vo) {
    	vo.setEndSendSpace(getEndSendSpace(vo));
        return iMaintenanceMgrService.updTicketConfig(vo);
    }

    @Override
    public int addTicketConfig(TicketConfigVO vo) {
    	vo.setEndSendSpace(getEndSendSpace(vo));
        return iMaintenanceMgrService.addTicketConfig(vo);
    }
    /**
     * 拼接送票间隔
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年7月5日 上午10:27:20
     * @param vo
     * @return
     */
    private String  getEndSendSpace(TicketConfigVO vo){
    	return vo.getBeforeTime()+SymbolConstants.VERTICAL_BAR+vo.getInterval()+SymbolConstants.VERTICAL_BAR+vo.getAfterTime();
    }
}
