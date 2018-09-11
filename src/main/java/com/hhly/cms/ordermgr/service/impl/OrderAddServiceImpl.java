package com.hhly.cms.ordermgr.service.impl;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.lotterymgr.service.LotteryTypeService;
import com.hhly.cms.ordermgr.service.OrderAddService;
import com.hhly.cmscore.cms.remote.service.IOrderMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryWinningVO;
import com.hhly.skeleton.cms.ordermgr.bo.*;
import com.hhly.skeleton.cms.ordermgr.vo.OrderAddCmsVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderAddContentVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderAddIssueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangb
 *
 * @Date 2017年1月17日
 *
 * @Desc 追号服务
 */
@Service
public class OrderAddServiceImpl implements OrderAddService {

	/**
	 * 订单remote服务接口(包含追号的服务接口)
	 */
	@Autowired
	private IOrderMgrService iOrderMgrService;

	@Autowired
	private ExcelExportService excelExportService;
	/**
	 * 彩种服务
	 */
	@Autowired
	private LotteryTypeService lotteryTypeService;
	/**
	 * 字典服务
	 */
	//@Autowired
	//private DictionaryService dictionaryService;

	/*************************************** 追号计划相关 **********************************************/
	@Override
	public PagingBO<OrderAddBO> findPagingOrderAdd(OrderAddCmsVO orderAddCms) {
		return iOrderMgrService.findPagingOrderAdd(orderAddCms);
	}

	@Override
	public OrderAddBO findSingleOrderAdd(OrderAddCmsVO orderAddCms) {
		return iOrderMgrService.findSingleOrderAdd(orderAddCms);
	}

	@Override
	public PagingBO<OrderAddContentBO> findPagingOrderAddContent(OrderAddContentVO orderAddContent) {
		return iOrderMgrService.findPagingOrderAddContent(orderAddContent);
	}

	@Override
	public PagingBO<OrderAddIssueBO> findPagingOrderAddIssue(OrderAddIssueVO orderAddIssue) {
		return iOrderMgrService.findPagingOrderAddIssue(orderAddIssue);
	}

	/*@Override
	public int updOrderAddIssueStatusByIds(String ids) {
		return iOrderMgrService.updOrderAddIssueStatusByIds(ids);
	}*/
	
	
	@Override
	public ByteArrayOutputStream findExcelOrderAdd(OrderAddCmsVO orderAddCms, String type) {
		switch (type) {
		case "info":
			return exportOrderAdd(orderAddCms);
		case "user":
			return exportUser(orderAddCms);
		}
		return null;
	}

	@Override
	public ByteArrayOutputStream findExcelOrderAddIssue(OrderAddIssueVO orderAddIssue) {
		List<OrderAddIssueBO> data = iOrderMgrService.findExcelOrderAddIssue(orderAddIssue);
		List<OrderAddIssueExcelCmsBO> targetList = new ArrayList<OrderAddIssueExcelCmsBO>();
		OrderAddIssueExcelCmsBO target = null;
		if (data != null && !data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				target = new OrderAddIssueExcelCmsBO(i + 1, data.get(i));
				targetList.add(target);
			}
		}
		return excelExportService.dataToExeclByStream("chaseissue", targetList);
	}

	/**
	 * @desc 导出追号信息
	 * @author huangb
	 * @date 2017年1月20日
	 * @param orderAddCms
	 *            参数对象
	 * @return 导出追号信息
	 */
	private ByteArrayOutputStream exportOrderAdd(OrderAddCmsVO orderAddCms) {
		List<OrderAddBO> data = iOrderMgrService.findExcelOrderAdd(orderAddCms);
		// 转成导出数据对象
		List<OrderAddExcelCmsBO> targetList = new ArrayList<OrderAddExcelCmsBO>();
		OrderAddExcelCmsBO target = null;
		if (data != null && !data.isEmpty()) {
			// 停追条件要特殊处理（对于停追类型=奖项的，要字典值转换）;;分彩种查
			Map<String, String> gradeMap = new HashMap<String, String>(); // 奖级map（DataValue-DataName）
			/*if (Objects.equals(Lottery.SSQ.getName(),data.get(0).getLotteryCode())) {
				List<String> dicCodeList = new ArrayList<String>();
				dicCodeList.add(DicDataEnum.ISSUE_SSQ_GRADE.getDicCode());
				Map<String, Map<String, String>> ssqGrade = dictionaryService.findDictionaryMap(dicCodeList);
				if (null != ssqGrade && !ssqGrade.isEmpty()) {
					gradeMap = ssqGrade.get(DicDataEnum.ISSUE_SSQ_GRADE.getDicCode());
				}
			}*/ // 其他彩种各自判断
			
			// 奖项列表从彩种奖项表中关联
			List<DictionaryBO> gradeList = lotteryTypeService.findWinningDictionary(new LotteryWinningVO(orderAddCms.getLotteryCode()));
			for (DictionaryBO temp : gradeList) {
				gradeMap.put(temp.getId(), temp.getText());
			}
			for (int i = 0; i < data.size(); i++) {
				target = new OrderAddExcelCmsBO(i + 1, data.get(i), gradeMap);
				targetList.add(target);
			}
		}
		// 彩种名称处理
		Map<String, List<DictionaryBO>> lotteryMap = new HashMap<String, List<DictionaryBO>>();
		lotteryMap.put("lotteryName", lotteryTypeService.findTypeDictionary(new StringVO()));// 查询并设置彩种字典
		return excelExportService.dataToExeclByStreamDictionary("chaseinfo", targetList, lotteryMap);
	}

	/**
	 * @desc 导出追号相关的用户信息
	 * @author huangb
	 * @date 2017年1月20日
	 * @param orderAddCms
	 *            参数对象
	 * @return 导出追号相关用户信息
	 */
	private ByteArrayOutputStream exportUser(OrderAddCmsVO orderAddCms) {
		List<OrderAddUserExcelCmsBO> data = iOrderMgrService.findExcelOrderAddUser(orderAddCms);
		return excelExportService.dataToExeclByStream("chaseuser", data);
	}
}
