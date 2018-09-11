package com.hhly.cms.ticket.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.lotterymgr.service.LotteryTypeService;
import com.hhly.cms.ticket.service.TicketInfoService;
import com.hhly.cmscore.cms.remote.service.ITicketMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketInfoBO;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketInfoExcelCmsBO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketInfoStatusVO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketInfoVO;

/**
 * @desc 票信息服务
 * @author huangb
 * @date 2017年2月21日
 * @company 益彩网络
 * @version v1.0
 */
@Service
public class TicketInfoServiceImpl implements TicketInfoService {

	/**
	 * 订单remote服务接口(包含追号的服务接口)
	 */
	@Autowired
	private ITicketMgrService iTicketMgrService;

	@Autowired
	private ExcelExportService excelExportService;
	/**
	 * 彩种服务
	 */
	@Autowired
	private LotteryTypeService lotteryTypeService;

	@Override
	public PagingBO<TicketInfoBO> findPagingTicket(TicketInfoVO ticketInfoVO) {
		return iTicketMgrService.findPagingTicket(ticketInfoVO);
	}

	@Override
	public TicketInfoBO findSingleTicket(TicketInfoVO ticketInfoVO) {
		return iTicketMgrService.findSingleTicket(ticketInfoVO);
	}

	@Override
	public ByteArrayOutputStream findExcelTicket(TicketInfoVO ticketInfoVO) {
		List<TicketInfoBO> data = iTicketMgrService.findExcelTicket(ticketInfoVO);
		// 转成导出数据对象
		List<TicketInfoExcelCmsBO> targetList = new ArrayList<TicketInfoExcelCmsBO>();
		TicketInfoExcelCmsBO target = null;
		if (data != null && !data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				target = new TicketInfoExcelCmsBO(i + 1, data.get(i), null);
				targetList.add(target);
			}
		}
		// 彩种名称处理
		Map<String, List<DictionaryBO>> lotteryMap = new HashMap<String, List<DictionaryBO>>();
		lotteryMap.put("lotteryName", lotteryTypeService.findTypeDictionary(new StringVO()));// 查询并设置彩种字典
		return excelExportService.dataToExeclByStreamDictionary("ticket info", targetList, lotteryMap);
	}

	@Override
	public int updTicket(TicketInfoVO ticketInfoVO) {
		return iTicketMgrService.updTicket(ticketInfoVO);
	}

	@Override
	public String updateTicketStatus(TicketInfoStatusVO vo) {
		return iTicketMgrService.updateTicketStatus(vo);
	}

	@Override
	public void uploadTicketNo(String ticketType, String ticketNo) {
		iTicketMgrService.uploadTicketNo(ticketType,ticketNo);
	}

	@Override
	public String updateTicketOperate(String operate, String id,String modifyBy) {
		return iTicketMgrService.updateTicketOperate(operate,id,modifyBy);
	}
}
