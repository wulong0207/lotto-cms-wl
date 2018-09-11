package com.hhly.cms.customermgr.service.impl;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.customermgr.service.UserWinningStatisticsService;
import com.hhly.cmscore.cms.remote.service.ICustomerMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.customermgr.bo.UserWinningStatisticsBO;
import com.hhly.skeleton.cms.customermgr.vo.UserWinningStatisticsVO;
@Service
public class UserWinningStatisticsServiceImpl implements UserWinningStatisticsService {
	@Autowired
    private ICustomerMgrService iCustomerMgrService;
	
	@Autowired
	private ExcelExportService excelExportService;

	@Override
	public ByteArrayOutputStream listUserWinningStatisticsExcel(UserWinningStatisticsVO vo) {
		return excelExportService.dataToExeclByStream("userWinningStatistics", iCustomerMgrService.listUserWinningStatisticsExcel(vo));
	}

	@Override
	public PagingBO<UserWinningStatisticsBO> listUserWinningStatistics(UserWinningStatisticsVO vo) {
		return iCustomerMgrService.listUserWinningStatistics(vo);
	}
	
}
