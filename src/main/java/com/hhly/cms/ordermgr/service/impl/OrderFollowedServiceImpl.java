package com.hhly.cms.ordermgr.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.ordermgr.service.OrderFollowedService;
import com.hhly.cmscore.cms.remote.service.IOrderMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderFollowedBO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderFollowedVO;

@Service
public class OrderFollowedServiceImpl implements OrderFollowedService {
	@Autowired
    private IOrderMgrService iOrderMgrService;
	@Autowired
	private ExcelExportService excelExportService;

	@Override
	public List<OrderFollowedBO> list(OrderFollowedVO vo) {
		return iOrderMgrService.listOrderFollowed(vo);
	}

	@Override
	public PagingBO<OrderFollowedBO> page(OrderFollowedVO vo) {
		return iOrderMgrService.pageOrderFollowed(vo);
	}

	@Override
	public ByteArrayOutputStream excel(OrderFollowedVO vo) {
		vo.setJoinOrder(true);
		vo.setJoinUser(true);
		return excelExportService.dataToExeclByStream("跟单",iOrderMgrService.listOrderFollowedExcel(vo));
	}
}
