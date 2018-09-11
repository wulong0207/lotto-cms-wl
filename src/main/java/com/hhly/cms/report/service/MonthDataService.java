package com.hhly.cms.report.service;

import java.io.ByteArrayOutputStream;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.report.bo.MonthBetRangeBO;
import com.hhly.skeleton.cms.report.bo.MonthLotDataBO;
import com.hhly.skeleton.cms.report.bo.MonthPlatformDataBO;
import com.hhly.skeleton.cms.report.bo.MonthRegisterBO;
import com.hhly.skeleton.cms.report.bo.PlatformFirstBO;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

/**
 * @ClassName: SalesReportService 
 * @Description: 销量报表接口
 * @author wuLong
 * @date 2017年7月6日 上午10:42:03 
 */
public interface MonthDataService {

	/**
	 * 
	 * @Description 查询平台明细数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	PagingBO<MonthPlatformDataBO> findPlatformData(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 导出数据 
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	ByteArrayOutputStream findPlatformDataExcel(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询平台销量图表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	MonthPlatformDataBO findPlatformSumMoney(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询平台投注人数图表数据
	 * @author HouXiangBao289
	 * @param vo
	 */
	MonthPlatformDataBO findPlatformUserCount(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询彩种明细数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	PagingBO<MonthLotDataBO> findLotData(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 导出数据 
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	ByteArrayOutputStream findLotDataExcel(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询各彩种投注人数
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	MonthLotDataBO selectLotUserSum(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询各彩种销量
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	MonthLotDataBO selectLotBetMoneySum(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询投注明细数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	PagingBO<MonthBetRangeBO> findBetData(ReportSearchVO vo);
	
	public ByteArrayOutputStream findBetCountExcelData(ReportSearchVO vo);
	public ByteArrayOutputStream findBetMoneyExcelData(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询投注数据(图表数据)
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	MonthBetRangeBO findBetDataSum(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询注册与有效明细数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	PagingBO<MonthRegisterBO> findRegisterUserData(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 导出数据 
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	ByteArrayOutputStream findRegisterUserDataExcel(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询注册与有效(图表数据)
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	MonthRegisterBO findMonthRegisterUserSum(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询注册与有效报表各平台新增有效用户数
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	PlatformFirstBO findPlatformUserSum(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询投注金额(图表数据)
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	MonthBetRangeBO findNewUserBetMoneySum(ReportSearchVO vo);
	
}
