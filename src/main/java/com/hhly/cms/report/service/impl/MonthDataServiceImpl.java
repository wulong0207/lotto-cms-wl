package com.hhly.cms.report.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hhly.cms.report.service.MonthDataService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.StringUtil;
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
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

@Service
public class MonthDataServiceImpl extends ReportBaseServiceImpl implements MonthDataService {

	/**
	 * 
	 * @Description 处理传参数据 
	 * @author HouXiangBao289
	 * @param vo
	 */
	private void handleParam(ReportSearchVO vo){
		if(vo.getStartTime() == null && vo.getEndTime() == null && vo.getTimeType() != null){
			// 查询规则：按月份查询
			switch (vo.getTimeType()) {
				case 0://上月
					vo.setStartTime(DateUtil.getBeforeMonth());
					vo.setEndTime(DateUtil.getBeforeMonth());
					break;
				case 1://本月
					Date month = DateUtil.getNowDate();
					vo.setStartTime(month);
					vo.setEndTime(month);
					break;
				case 2://本季度
					vo.setStartTime(DateUtil.getQuarterMonth(1,true));
					vo.setEndTime(DateUtil.getQuarterMonth(1,false));
					break;
				case 3://上季度
					vo.setStartTime(DateUtil.getQuarterMonth(2,true));
					vo.setEndTime(DateUtil.getQuarterMonth(2,false));
					break;
			}
		}
		if(StringUtil.isBlank(vo.getChannel())){
			vo.setChannel("0");
			vo.setChannelIds(null);
		}else{
			vo.setChannelIds(reportService.findChildChannelIds(vo));
		};
//		System.out.println(DateUtil.convertDateToStr(vo.getStartTime()) + ":" + DateUtil.convertDateToStr(vo.getEndTime()) + "---------------------------------------------------------------------------");
	}

	@Override
	public PagingBO<MonthPlatformDataBO> findPlatformData(ReportSearchVO vo) {
		handleParam(vo);
		return reportService.findPlatformData(vo);
	}

	@Override
	public ByteArrayOutputStream findPlatformDataExcel(ReportSearchVO vo) {
		handleParam(vo);
		List<MonthPlatformDataExcelBO> list = reportService.findPlatformExcelData(vo);
		return excelExportService.dataToExeclByStream("platform-data", list);
	}

	@Override
	public MonthPlatformDataBO findPlatformSumMoney(ReportSearchVO vo) {
		handleParam(vo);
		return reportService.findPlatformSumMoney(vo);
	}

	@Override
	public MonthPlatformDataBO findPlatformUserCount(ReportSearchVO vo) {
		handleParam(vo);
		return reportService.findPlatformUserCount(vo);
	}

	@Override
	public PagingBO<MonthLotDataBO> findLotData(ReportSearchVO vo) {
		handleParam(vo);
		return reportService.findLotData(vo);
	}

	@Override
	public ByteArrayOutputStream findLotDataExcel(ReportSearchVO vo) {
		handleParam(vo);
		List<MonthLotDataExcelBO> list = reportService.findLotExcelData(vo);
		return excelExportService.dataToExeclByStream("lottery-data", list);
	}

	@Override
	public MonthLotDataBO selectLotUserSum(ReportSearchVO vo) {
		handleParam(vo);
		return reportService.selectLotUserSum(vo);
	}

	@Override
	public MonthLotDataBO selectLotBetMoneySum(ReportSearchVO vo) {
		handleParam(vo);
		return reportService.selectLotBetMoneySum(vo);
	}

	@Override
	public PagingBO<MonthBetRangeBO> findBetData(ReportSearchVO vo) {
		handleParam(vo);
		return reportService.findBetData(vo);
	}

	@Override
	public ByteArrayOutputStream findBetMoneyExcelData(ReportSearchVO vo) {
		handleParam(vo);
		List<MonthRangeMoneyExcelBO> list = reportService.findBetMoneyExcekData(vo);
		return excelExportService.dataToExeclByStream("sale-money", list);
	}
	
	@Override
	public ByteArrayOutputStream findBetCountExcelData(ReportSearchVO vo) {
		handleParam(vo);
		List<MonthRangeCountExcelBO> list = reportService.findBetCountExcekData(vo);
		return excelExportService.dataToExeclByStream("sale-data", list);
	}

	@Override
	public MonthBetRangeBO findBetDataSum(ReportSearchVO vo) {
		handleParam(vo);
		return reportService.findBetDataSum(vo);
	}

	@Override
	public PagingBO<MonthRegisterBO> findRegisterUserData(ReportSearchVO vo) {
		handleParam(vo);
		return reportService.findRegisterUserData(vo);
	}

	@Override
	public ByteArrayOutputStream findRegisterUserDataExcel(ReportSearchVO vo) {
		handleParam(vo);
		List<MonthRegisterExcelBO> list = reportService.findMonthRegisterExcelData(vo);
		return excelExportService.dataToExeclByStream("register-data", list);
	}

	@Override
	public MonthRegisterBO findMonthRegisterUserSum(ReportSearchVO vo) {
		handleParam(vo);
		return reportService.findMonthRegisterUserSum(vo);
	}

	@Override
	public PlatformFirstBO findPlatformUserSum(ReportSearchVO vo) {
		handleParam(vo);
		return reportService.findPlatformUserSum(vo);
	}

	@Override
	public MonthBetRangeBO findNewUserBetMoneySum(ReportSearchVO vo) {
		handleParam(vo);
		return reportService.findNewUserBetMoneySum(vo);
	}


}
