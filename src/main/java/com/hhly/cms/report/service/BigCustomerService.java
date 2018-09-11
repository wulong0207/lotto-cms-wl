package com.hhly.cms.report.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.report.bo.BigCustomerDataBO;
import com.hhly.skeleton.cms.report.bo.BigCustomerStatisticsBO;
import com.hhly.skeleton.cms.report.bo.ReportLotteryTypeBO;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

public interface BigCustomerService {
	/**
	 * 
	 * @Description 查询大客户投注数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	BigCustomerStatisticsBO findStatistics(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询投注平台数据 
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	BigCustomerStatisticsBO findPlatformSale(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询彩种销量
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	List<ReportLotteryTypeBO> findLotSale(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询各彩种投注人数
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	List<ReportLotteryTypeBO> findLotBetUserCount(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询大客户数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	PagingBO<BigCustomerDataBO> findBigCustomerData(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 导出数据 
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	ByteArrayOutputStream findBigCustomerDataExcel(ReportSearchVO vo);

}
