package com.hhly.cms.report.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hhly.cms.report.service.BigCustomerService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.report.bo.BigCustomerDataBO;
import com.hhly.skeleton.cms.report.bo.BigCustomerDataExcelBO;
import com.hhly.skeleton.cms.report.bo.BigCustomerStatisticsBO;
import com.hhly.skeleton.cms.report.bo.ReportLotteryTypeBO;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;
@Service
public class BigCustomerServiceImpl extends ReportBaseServiceImpl implements BigCustomerService {

	@Override
	public BigCustomerStatisticsBO findStatistics(ReportSearchVO vo) {
		handleSearchParam(vo);
		return reportService.findStatistics(vo);
	}

	@Override
	public BigCustomerStatisticsBO findPlatformSale(ReportSearchVO vo) {
		handleSearchParam(vo);
		return reportService.findPlatformSale(vo);
	}

	@Override
	public List<ReportLotteryTypeBO> findLotSale(ReportSearchVO vo) {
		handleSearchParam(vo);
		return reportService.findLotSale(vo);
	}

	@Override
	public List<ReportLotteryTypeBO> findLotBetUserCount(ReportSearchVO vo) {
		handleSearchParam(vo);
		return reportService.findLotBetUserCount(vo);
	}

	@Override
	public PagingBO<BigCustomerDataBO> findBigCustomerData(ReportSearchVO vo) {
		handleSearchParam(vo);
		return reportService.findBigCustomerData(vo);
	}

	@Override
	public ByteArrayOutputStream findBigCustomerDataExcel(ReportSearchVO vo) {
		handleSearchParam(vo);
		List<BigCustomerDataExcelBO> list = reportService.findBigCustomerExcelData(vo);
		return excelExportService.dataToExeclByStream("大客户数据", list);
	}

}
