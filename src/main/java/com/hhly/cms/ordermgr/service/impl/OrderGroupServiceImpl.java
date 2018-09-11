package com.hhly.cms.ordermgr.service.impl;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.ordermgr.service.OrderGroupService;
import com.hhly.cmscore.cms.remote.service.IOrderMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.ordermgr.bo.GroupUserBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderGroupBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderGroupContentBO;
import com.hhly.skeleton.cms.ordermgr.vo.GroupUserVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderGroupVO;

@Service
public class OrderGroupServiceImpl implements OrderGroupService {
	
	@Autowired
    private IOrderMgrService iOrderMgrService;
	
	@Autowired
	private ExcelExportService excelExportService;

	@Override
	public PagingBO<OrderGroupBO> findGroup(OrderGroupVO vo) {
		return iOrderMgrService.findGroup(vo);
	}

	@Override
	public PagingBO<OrderGroupContentBO> findGroupContent(OrderGroupVO vo) {
		return iOrderMgrService.findGroupContent(vo);
	}

	@Override
	public OrderGroupBO findGroupById(Integer id) {
		return iOrderMgrService.findGroupById(id);
	}

	@Override
	public int update(OrderGroupVO vo) {
		return iOrderMgrService.update(vo);
	}

	@Override
	public ByteArrayOutputStream excel(OrderGroupVO vo) {
		return excelExportService.dataToExeclByStream("合买用户",iOrderMgrService.findGroupUserExcel(vo));
	}

	@Override
	public ResultBO<?> recommand(OrderGroupVO vo) {
		return iOrderMgrService.recommand(vo);
	}

	@Override
	public ResultBO<?> top(OrderGroupVO vo) {
		return iOrderMgrService.top(vo);
	}

	@Override
	public ResultBO<?> siteGuarantee(Integer groupId) {
		return iOrderMgrService.siteGuarantee(groupId);
	}

	@Override
	public PagingBO<GroupUserBO> findGroupUser(GroupUserVO vo) {
		return iOrderMgrService.findGroupUser(vo);
	}

	@Override
	public ByteArrayOutputStream exprotGroupUserExcel(GroupUserVO vo) {
		return excelExportService.dataToExeclByStream("推单用户",iOrderMgrService.exprotGroupUserExcel(vo));
	}

	@Override
	public void updateRecommand(GroupUserVO vo) {
		iOrderMgrService.updateRecommand(vo);
	}

	@Override
	public void updateUserFlag(GroupUserVO vo) {
		iOrderMgrService.updateUserFlag(vo);
	}	
}
