package com.hhly.cms.ordermgr.service.impl;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.ordermgr.service.OrderService;
import com.hhly.cmscore.cms.remote.service.IOrderMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.ordermgr.bo.*;
import com.hhly.skeleton.cms.ordermgr.vo.OrderInfoCmsSearchVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
    private IOrderMgrService iOrderMgrService;
	 
	@Autowired
	private ExcelExportService excelExportService;

	@Override
	public PagingBO<OrderInfoCmsBO> find(OrderInfoCmsSearchVO vo) {
		return iOrderMgrService.findOrderInfo(vo);
	}

	@Override
	public OrderInfoDetailCmsBO findOrderInfoDetail(StringVO vo) {
		return iOrderMgrService.findOrderInfoDetail(vo);
	}

	@Override
	public PagingBO<OrderDetailBO> findOrderDetail(OrderInfoCmsSearchVO vo) {
		return iOrderMgrService.findOrderDetail(vo);
	}

	@Override
	public int updOrderInfo(OrderInfoVO vo) {
		
		return iOrderMgrService.updOrderInfo(vo);
	}

	@Override
	public ByteArrayOutputStream getOrderInfoExcel(OrderInfoCmsSearchVO vo,String type) {
		switch (type) {
		case "info":
			return exportOrder(vo);
		case "user":
			return exportUser(vo);
		}
		return null;
	}

	private ByteArrayOutputStream exportUser(OrderInfoCmsSearchVO vo) {
		List<OrderInfoCmsUserExcelBO> data=  iOrderMgrService.getOrderInfoUserExcel(vo);
		return excelExportService.dataToExeclByStream("orderuser",data);
	}

	private ByteArrayOutputStream exportOrder(OrderInfoCmsSearchVO vo) {
		List<OrderInfoCmsExcelBO> data=  iOrderMgrService.getOrderInfoExcel(vo);
		return excelExportService.dataToExeclByStream("orderinfo",data);
	}

	@Override
	public int updateOrderStatus(OrderInfoVO vo) {
		return iOrderMgrService.updateOrderStatus(vo);
	}

	@Override
	public String updateOrderOperate(OrderInfoVO vo) {
		return iOrderMgrService.updateOrderOperate(vo);
	}

	@Override
	public int updTicketAndCheckTime(Integer lotteryCode, String issueCode, Date endTicketTime, Date endCheckTime,
			Date endSysTime, String systemCode) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("lotteryCode", lotteryCode);
			param.put("issueCode", issueCode);
			param.put("endTicketTime", endTicketTime);
			param.put("endCheckTime", endCheckTime);
			param.put("endSysTime", endSysTime);
			param.put("systemCode", systemCode);		
		return iOrderMgrService.updTicketAndCheckTime(param);
	}

	@Override
	public int updCancelOrderRemark(String orderCode, String remark) {
		return iOrderMgrService.updCancelOrderRemark(orderCode, remark);
	}
	
}
