package com.hhly.report.remote.service;

import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.report.bo.BigCustomerDataBO;
import com.hhly.skeleton.cms.report.bo.BigCustomerDataExcelBO;
import com.hhly.skeleton.cms.report.bo.BigCustomerStatisticsBO;
import com.hhly.skeleton.cms.report.bo.ChannelBO;
import com.hhly.skeleton.cms.report.bo.ChannelDataBO;
import com.hhly.skeleton.cms.report.bo.ChannelDataExcelBO;
import com.hhly.skeleton.cms.report.bo.MonthBetRangeBO;
import com.hhly.skeleton.cms.report.bo.MonthLotDataBO;
import com.hhly.skeleton.cms.report.bo.MonthLotDataExcelBO;
import com.hhly.skeleton.cms.report.bo.MonthPlatformDataBO;
import com.hhly.skeleton.cms.report.bo.MonthPlatformDataExcelBO;
import com.hhly.skeleton.cms.report.bo.MonthRangeCountExcelBO;
import com.hhly.skeleton.cms.report.bo.MonthRangeMoneyExcelBO;
import com.hhly.skeleton.cms.report.bo.MonthRegisterBO;
import com.hhly.skeleton.cms.report.bo.MonthRegisterExcelBO;
import com.hhly.skeleton.cms.report.bo.PlatformFirstBO;
import com.hhly.skeleton.cms.report.bo.ReportLogBO;
import com.hhly.skeleton.cms.report.bo.ReportLotteryChildBO;
import com.hhly.skeleton.cms.report.bo.ReportLotteryTypeBO;
import com.hhly.skeleton.cms.report.bo.ReportLotteryWayExcelBO;
import com.hhly.skeleton.cms.report.bo.ReportMoneyRangeExcelBO;
import com.hhly.skeleton.cms.report.bo.ReportOrderRangeBO;
import com.hhly.skeleton.cms.report.bo.ReportUserBO;
import com.hhly.skeleton.cms.report.bo.ReportUserCompositeBO;
import com.hhly.skeleton.cms.report.bo.ReportUserDistributionExcelBO;
import com.hhly.skeleton.cms.report.bo.ReportUserRangeExcelBO;
import com.hhly.skeleton.cms.report.bo.ReportUserRookieExcelBO;
import com.hhly.skeleton.cms.report.bo.SaleDataBO;
import com.hhly.skeleton.cms.report.bo.SaleDataExcelBO;
import com.hhly.skeleton.cms.report.bo.UserDataBO;
import com.hhly.skeleton.cms.report.bo.UserNewOldBO;
import com.hhly.skeleton.cms.report.bo.UserRegisterBO;
import com.hhly.skeleton.cms.report.bo.UserSilentBO;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

public interface IReportService {

	/**
	 * @desc   数据概况：查询r_user表,sum求和
	 * @author Tony Wang
	 * @create 2017年9月18日
	 * @param vo
	 * @return 
	 */
	ReportUserBO sumReportUser(ReportSearchVO vo);
	
	/**
	 * @desc   查询r_user表
	 * @author Tony Wang
	 * @create 2017年9月1日
	 * @param vo
	 * @return 
	 */
	List<ReportUserBO> findReportUser(ReportSearchVO vo);
	
	List<ReportUserBO> findReportUser2(ReportSearchVO vo);

	/**
	 * @desc   运营简报数据概况
	 * @author Tony Wang
	 * @create 2017年9月4日
	 * @param vo
	 * @return 
	 */
	ReportUserCompositeBO overviewReportUser(ReportSearchVO vo);

	/**
	 * @desc   分页查询用户数据
	 * @author Tony Wang
	 * @create 2017年9月15日
	 * @param vo
	 * @return 
	 */
	PagingBO<ReportUserBO> findReportUserWithPaging(ReportSearchVO vo);

	/**
	 * @desc   查询记录，用于导出excel
	 * @author Tony Wang
	 * @create 2017年9月16日
	 * @param vo
	 * @return 
	 */
	//List<ReportUserExcelBO> findReportUserExcel(ReportSearchVO vo);
	
	List<ReportUserRookieExcelBO> findReportUserRookieExcel(ReportSearchVO vo);
	
	List<ReportUserDistributionExcelBO> findReportUserDistributionExcel(ReportSearchVO vo);
	
	List<ReportLotteryWayExcelBO> findLotteryWayExcel(ReportSearchVO vo);

	/**
	 * @desc   查询r_lottery_type表
	 * @author Tony Wang
	 * @create 2017年9月17日
	 * @param vo
	 * @return 
	 */
	List<ReportLotteryTypeBO> sumReportLotteryType(ReportSearchVO vo);
	
	List<ReportLotteryTypeBO> getDisOrderMoney(ReportSearchVO vo);
	
	List<ReportLotteryTypeBO> getDisOrderUser(ReportSearchVO vo);
	
	/**
	 * @desc   分页查询r_lottery_type表
	 * @author Tony Wang
	 * @create 2017年9月17日
	 * @param vo
	 * @return 
	 */
	PagingBO<ReportLotteryTypeBO> sumReportLotteryTypeWithPaging(ReportSearchVO vo);

	/**
	 * @desc   查询r_order_range记录的和值
	 * @author Tony Wang
	 * @create 2017年9月18日
	 * @param vo
	 * @return 
	 */
	List<ReportOrderRangeBO> sumReportOrderRange(ReportSearchVO vo);

	/**
	 * @desc   按天分页查询r_order_range记录的和值
	 * @author Tony Wang
	 * @create 2017年9月18日
	 * @param vo
	 * @return 
	 */
	PagingBO<ReportOrderRangeBO> sumReportOrderRangeWithPaging(ReportSearchVO vo);

	//PagingBO<ReportLotteryChildBO> sumReportLotteryChildWithPaging(ReportSearchVO vo);
	
//	/**
//	 * @desc   查询r_lottery_child表
//	 * @author Tony Wang
//	 * @create 2017年9月18日
//	 * @param vo
//	 * @return 
//	 */
//	List<ReportLotteryChildBO> sumReportLotteryChild(ReportSearchVO vo);
	
	List<ReportLotteryChildBO> findReportLotteryChild(ReportSearchVO criteria);
	
	/**************************用户数据报表start****************************/
	
	/**
	 * 用户数据报表
	 * @Description 查询用户数据概况 
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	UserDataBO findUserDataStatistics(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询用户注册与有效报表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	PagingBO<UserRegisterBO> findUserRegisterReport(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询用户注册与有效图表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	UserRegisterBO findRegisterUserSum(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 沉默用户图表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	UserSilentBO findSilentUserSum(ReportSearchVO vo);
	
//	/**
//	 * 
//	 * @Description 沉默用户报表数据 
//	 * @author HouXiangBao289
//	 * @param vo
//	 * @return
//	 */
//	PagingBO<UserSilentBO> findSilentUserData(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 新老用户图表数据
	 * @author HouXiangBao289
	 * @return
	 */
	UserNewOldBO findNewOldUserSum(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 新老用户报表数据 
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	PagingBO<UserNewOldBO> findNewOldUserData(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 新老用户报表Excel数据 
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	List<UserNewOldBO> findNewOldUserExcelData(ReportSearchVO vo);
//	/**
//	 * 
//	 * @Description 沉默用户报表Excel数据 
//	 * @author HouXiangBao289
//	 * @param vo
//	 * @return
//	 */
//	List<UserSilentBO> findSilentUserExcelData(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 用户注册与有效数据Excel报表 
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	List<UserRegisterBO> findRegisterUserExcelData(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 获取渠道列表
	 * @author HouXiangBao289
	 * @return
	 */
	List<ChannelBO> findChannelList();
	/**************************用户数据报表end****************************/
	/**
	 * 
	 * @Description 查询子渠道ID 
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	String[] findChildChannelIds(ReportSearchVO vo);
	/**************************r_order_range表end****************************/
	List<ReportUserRangeExcelBO> findUserRangeExcel(ReportSearchVO vo);

	List<ReportMoneyRangeExcelBO> findMoneyRangeExcel(ReportSearchVO vo);
	/**************************r_order_range表end****************************/

	/**
	 * r_lottery_type
	 * @Description 查询子渠道ID 
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	List<ReportLotteryTypeBO> findReportLotteryType(ReportSearchVO criteria);
	
	
	List<ReportLotteryTypeBO> findReportLotteryType2(ReportSearchVO criteria); 

	/**************************r_log表start****************************/
	/**
	 * @desc   查询r_log
	 * @author Tony Wang
	 * @create 2017年9月30日
	 * @param criteria
	 * @return 
	 */
	List<ReportLogBO> findReportLog(ReportSearchVO criteria);
	
	/**
	 * @desc   查询符合条件的r_log记录，sum求和成一条记录
	 * @author Tony Wang
	 * @create 2017年11月9日
	 * @param criteria
	 * @return 
	 */
	ReportLogBO sumReportLogAsOne(ReportSearchVO criteria);
	
	/**
	 * @desc   查询符合条件的r_log记录，sum求和
	 * @author Tony Wang
	 * @create 2017年11月9日
	 * @param criteria
	 * @return 
	 */
	List<ReportLogBO> sumReportLog(ReportSearchVO criteria);
	
//	/**
//	 * @desc   查询去重后的人数
//	 * @author Tony Wang
//	 * @create 2017年9月29日
//	 * @param vo
//	 * @return 
//	 */
	List<ReportLogBO> countReportLog(ReportSearchVO vo);

	/**
	 * @desc   查询用户数
	 * @author Tony Wang
	 * @create 2017年9月30日
	 * @param criteria
	 * @return 
	 */
	int countUser(ReportSearchVO criteria);
	

	/**
	 * @desc   查询子玩法相关
	 * @author Tony Wang
	 * @create 2017年11月15日
	 * @param vo
	 * @return 
	 */
	List<ReportLogBO> sumReportLogChild(ReportSearchVO vo);

	/**
	 * @desc   查询彩种相关
	 * @author Tony Wang
	 * @create 2017年11月15日
	 * @param vo
	 * @return 
	 */
	List<ReportLogBO> sumReportLogLottery(ReportSearchVO vo);
	/**************************r_log表end****************************/

	
/**********************月度报表 start***********************/
	
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
	
	/**********************月度报表 and***********************/
	
	/**********************大客户报表 start***********************/
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
	
	List<MonthPlatformDataExcelBO> findPlatformExcelData(ReportSearchVO vo);
	List<MonthLotDataExcelBO> findLotExcelData(ReportSearchVO vo);
	List<MonthRangeMoneyExcelBO> findBetMoneyExcekData(ReportSearchVO vo);
	List<MonthRangeCountExcelBO> findBetCountExcekData(ReportSearchVO vo);
	List<MonthRegisterExcelBO> findMonthRegisterExcelData(ReportSearchVO vo);
	List<BigCustomerDataExcelBO> findBigCustomerExcelData(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 查询投注金额(图表数据)
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	MonthBetRangeBO findNewUserBetMoneySum(ReportSearchVO vo);
	/**********************大客户报表 end***********************/
	
	/***************************新增查询销售报表数据 2018-6-6 *******************/
	/**
	 * 查询渠道报表Excel数据
	 * @param vo
	 * @return
	 */
	List<ChannelDataExcelBO> findChannelExcelData(ReportSearchVO vo);
	
	/**
	 * 查询渠道报表数据
	 * @param vo
	 * @return
	 */
	PagingBO<ChannelDataBO> findChannelData(ReportSearchVO vo);
	
	
	/**
	 * 查询销售报表Excel数据
	 * @param vo
	 * @return
	 */
	List<SaleDataExcelBO> findSaleExcelData(ReportSearchVO vo);
	
	/**
	 * 查询销售报表数据
	 * @param vo
	 * @return
	 */
	PagingBO<SaleDataBO> findSaleData(ReportSearchVO vo);
}
