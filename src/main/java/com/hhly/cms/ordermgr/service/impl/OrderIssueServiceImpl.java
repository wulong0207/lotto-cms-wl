package com.hhly.cms.ordermgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.ordermgr.service.OrderIssueService;
import com.hhly.cmscore.cms.remote.service.IOrderMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderIssueBO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderIssueVO;

@Service
public class OrderIssueServiceImpl implements OrderIssueService {
	@Autowired
    private IOrderMgrService iOrderMgrService;
	 
	@Override
	public PagingBO<OrderIssueBO> page(OrderIssueVO vo) {
		return iOrderMgrService.pageOrderIssue(vo);
	}

	@Override
	public List<OrderIssueBO> list(OrderIssueVO vo) {
		return iOrderMgrService.listOrderIssue(vo);
	}

	@Override
	public int update(OrderIssueVO vo) {
		return iOrderMgrService.updateOrderIssue(vo);
	}
}
