package com.hhly.cms.lotterymgr.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.lotterymgr.service.LotteryTypeService;
import com.hhly.cms.lotterymgr.service.SendPrizeService;
import com.hhly.cmscore.cms.remote.service.IOrderMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderInfoCmsBO;
import com.hhly.skeleton.cms.ordermgr.bo.SendPrizeExcelBO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderInfoCmsSearchVO;

/**
 * @desc 派奖服务接口
 * @author huangb
 * @date 2017年2月12日
 * @company 益彩网络
 * @version v1.0
 */
@Service
public class SendPrizeServiceImpl implements SendPrizeService {

	/**
	 * 派奖远程服务（就是订单服务）
	 */
	@Autowired
	private IOrderMgrService iOrderMgrService;

	/**
	 * 彩种服务
	 */
	@Autowired
	private LotteryTypeService lotteryTypeService;
	
	/**
	 * 导出服务
	 */
	@Autowired
	private ExcelExportService excelExportService;

	@Override
	public PagingBO<OrderInfoCmsBO> findPagingSendPrize(OrderInfoCmsSearchVO orderInfoCmsSearch) {
		return iOrderMgrService.findPagingSendPrize(orderInfoCmsSearch);
	}

	@Override
	public ByteArrayOutputStream findExcelSendPrize(OrderInfoCmsSearchVO orderInfoCmsSearch) {
		List<OrderInfoCmsBO> data = iOrderMgrService.findExcelSendPrize(orderInfoCmsSearch);
		List<SendPrizeExcelBO> targetList = new ArrayList<SendPrizeExcelBO>();
		SendPrizeExcelBO target = null;
		if (data != null && !data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				target = new SendPrizeExcelBO(i + 1, data.get(i), null);
				targetList.add(target);
			}
		}
		// 彩种名称处理
		Map<String, List<DictionaryBO>> lotteryMap = new HashMap<String, List<DictionaryBO>>();
		lotteryMap.put("lotteryName", lotteryTypeService.findTypeDictionary(new StringVO()));// 查询并设置彩种字典
		return excelExportService.dataToExeclByStreamDictionary("send prize", targetList, lotteryMap);
	}

	@Override
	public String getAwardInfo(OrderInfoCmsSearchVO orderInfoCmsSearch) {
		return iOrderMgrService.getAwardInfo(orderInfoCmsSearch);
	}
}
