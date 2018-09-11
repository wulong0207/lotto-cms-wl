package com.hhly.cms.report.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.report.bo.ChannelBO;
import com.hhly.skeleton.cms.report.bo.ChannelDataBO;
import com.hhly.skeleton.cms.report.bo.SaleDataBO;
import com.hhly.skeleton.cms.report.bo.UserDataBO;
import com.hhly.skeleton.cms.report.bo.UserNewOldBO;
import com.hhly.skeleton.cms.report.bo.UserRegisterBO;
import com.hhly.skeleton.cms.report.bo.UserSilentBO;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

/**
 * @ClassName: SalesReportService 
 * @Description: 销量报表接口
 * @author wuLong
 * @date 2017年7月6日 上午10:42:03 
 */
public interface UserDataService {

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

//	/**
//	 * 
//	 * @Description 导出沉默用户数据 
//	 * @author HouXiangBao289
//	 * @param vo
//	 * @return
//	 */
//	ByteArrayOutputStream getSilentUserDataExcel(ReportSearchVO vo);

	/**
	 * 
	 * @Description 导出新老用户数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	ByteArrayOutputStream getNewOldUserDataExcel(ReportSearchVO vo);

	/**
	 * 
	 * @Description 导出注册与有效报表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	ByteArrayOutputStream getRegisterDataExcel(ReportSearchVO vo);
	
	/**
	 * 
	 * @Description 获取渠道列表
	 * @author HouXiangBao289
	 * @return
	 */
	List<ChannelBO> findChannelList();
	
	/***************************新增查询销售报表数据 2018-6-6 *******************/
	/**
	 * 导出渠道报表Excel数据
	 * @param vo
	 * @return
	 */
	ByteArrayOutputStream findChannelExcelData(ReportSearchVO vo);
	
	/**
	 * 查询渠道报表数据
	 * @param vo
	 * @return
	 */
	PagingBO<ChannelDataBO> findChannelData(ReportSearchVO vo);
	
	
	/**
	 * 导出销售报表Excel数据
	 * @param vo
	 * @return
	 */
	ByteArrayOutputStream findSaleExcelData(ReportSearchVO vo);
	
	/**
	 * 查询销售报表数据
	 * @param vo
	 * @return
	 */
	PagingBO<SaleDataBO> findSaleData(ReportSearchVO vo);
	
}
