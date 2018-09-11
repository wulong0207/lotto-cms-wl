package com.hhly.cms.report.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hhly.cms.report.service.UserDataService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.report.bo.ChannelBO;
import com.hhly.skeleton.cms.report.bo.ChannelDataBO;
import com.hhly.skeleton.cms.report.bo.ChannelDataExcelBO;
import com.hhly.skeleton.cms.report.bo.SaleDataBO;
import com.hhly.skeleton.cms.report.bo.SaleDataExcelBO;
import com.hhly.skeleton.cms.report.bo.UserDataBO;
import com.hhly.skeleton.cms.report.bo.UserNewOldBO;
import com.hhly.skeleton.cms.report.bo.UserNewOldExcelBO;
import com.hhly.skeleton.cms.report.bo.UserRegisterBO;
import com.hhly.skeleton.cms.report.bo.UserRegisterExcelBO;
import com.hhly.skeleton.cms.report.bo.UserSilentBO;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

@Service
public class UserDataServiceImpl extends ReportBaseServiceImpl implements UserDataService {

	@Override
	public UserDataBO findUserDataStatistics(ReportSearchVO vo) {
		handleSearchParam(vo);
		return reportService.findUserDataStatistics(vo);
	}

	@Override
	public PagingBO<UserRegisterBO> findUserRegisterReport(ReportSearchVO vo) {
		handleSearchParam(vo);
		return reportService.findUserRegisterReport(vo);
	}

	@Override
	public UserRegisterBO findRegisterUserSum(ReportSearchVO vo) {
		handleSearchParam(vo);
		return reportService.findRegisterUserSum(vo);
	}

	@Override
	public UserSilentBO findSilentUserSum(ReportSearchVO vo) {
		handleSearchParam(vo);
		return reportService.findSilentUserSum(vo);
	}

//	@Override
//	public PagingBO<UserSilentBO> findSilentUserData(ReportSearchVO vo) {
//		handleSearchParam(vo);
//		return reportService.findSilentUserData(vo);
//	}

	@Override
	public UserNewOldBO findNewOldUserSum(ReportSearchVO vo) {
		handleSearchParam(vo);
		return reportService.findNewOldUserSum(vo);
	}

	@Override
	public PagingBO<UserNewOldBO> findNewOldUserData(ReportSearchVO vo) {
		handleSearchParam(vo);
		return reportService.findNewOldUserData(vo);
	}
	
//	@Override
//	public ByteArrayOutputStream getSilentUserDataExcel(ReportSearchVO vo) {
//		List<UserSilentBO> list = reportService.findSilentUserExcelData(vo);
//		List<UserSilentExcelBO> targetList = new ArrayList<UserSilentExcelBO>();
//		UserSilentExcelBO excelBO= null;
//		for(int i = 0;i < list.size();i++){
//			excelBO = new UserSilentExcelBO(i+1,list.get(i));
//			targetList.add(excelBO);
//		}
//		return excelExportService.dataToExeclByStream("silentUserData", targetList);
//	}

	@Override
	public ByteArrayOutputStream getNewOldUserDataExcel(ReportSearchVO vo) {
		handleSearchParam(vo);
		List<UserNewOldBO> list = reportService.findNewOldUserExcelData(vo);
		List<UserNewOldExcelBO> targetList = new ArrayList<UserNewOldExcelBO>();
		UserNewOldExcelBO excelBO= null;
		for(int i = 0;i < list.size();i++){
			excelBO = new UserNewOldExcelBO(i+1,list.get(i));
			targetList.add(excelBO);
		}
		return excelExportService.dataToExeclByStream("newOldUserData", targetList);
	}

	@Override
	public ByteArrayOutputStream getRegisterDataExcel(ReportSearchVO vo) {
		handleSearchParam(vo);
		List<UserRegisterBO> list = reportService.findRegisterUserExcelData(vo);
		List<UserRegisterExcelBO> targetList = new ArrayList<UserRegisterExcelBO>();
		UserRegisterExcelBO excelBO= null;
		for(int i = 0;i < list.size();i++){
			excelBO = new UserRegisterExcelBO(i+1,list.get(i));
			targetList.add(excelBO);
		}
		return excelExportService.dataToExeclByStream("registerUserData", targetList);
	}

	@Override
	public List<ChannelBO> findChannelList() {
		return reportService.findChannelList();
	}

	@Override
	public ByteArrayOutputStream findChannelExcelData(ReportSearchVO vo) {
		List<ChannelDataExcelBO> data = reportService.findChannelExcelData(vo);
		return excelExportService.dataToExeclByStream("ChannelReportData", data);
	}

	@Override
	public PagingBO<ChannelDataBO> findChannelData(ReportSearchVO vo) {
		return reportService.findChannelData(vo);
	}

	@Override
	public ByteArrayOutputStream findSaleExcelData(ReportSearchVO vo) {
		List<SaleDataExcelBO> data = reportService.findSaleExcelData(vo);
		return excelExportService.dataToExeclByStream("SaleData", data);
	}

	@Override
	public PagingBO<SaleDataBO> findSaleData(ReportSearchVO vo) {
		return reportService.findSaleData(vo);
	}

}
