package com.hhly.cms.report.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.report.remote.service.IReportService;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

public class ReportBaseServiceImpl {
	
	@Autowired
	protected IReportService reportService;
	
	/**
	 * 导出服务
	 */
	@Autowired
	protected ExcelExportService excelExportService;
	
	/**
	 * 
	 * @Description 处理传参数据 
	 * @author HouXiangBao289
	 * @param vo
	 */
	protected void handleSearchParam(ReportSearchVO vo){
		if(vo.getStartTime() == null && vo.getEndTime() == null && vo.getTimeType() != null){
			// 查询规则：大于等于开始时间，小于等于结束时间
			switch (vo.getTimeType()) {
				case 0://昨天
					String Yesterday = DateUtil.getBeforeOrAfterDate(-1);
					Date time = DateUtil.convertStrToDate(Yesterday, "yyyy-MM-dd");
					vo.setStartTime(time);
					vo.setEndTime(time);
					break;
				case 1://今天
					Date today = DateUtil.getNowDate("yyyy-MM-dd");
					vo.setStartTime(today);
					vo.setEndTime(today);
					break;
				case 2://上周
					Date[] dates = DateUtil.getLastTimeInterval();
					vo.setStartTime(dates[0]);
					vo.setEndTime(dates[1]);
					break;
				case 3://本周
					dates = DateUtil.getTimeInterval(new Date());
					vo.setStartTime(dates[0]);
					vo.setEndTime(dates[1]);
					break;
				case 4://上月
					dates = DateUtil.getbeforeMonth();
					vo.setStartTime(dates[0]);
					vo.setEndTime(dates[1]);
					break;
				case 5://本月
					dates = DateUtil.getNowMonth();
					vo.setStartTime(dates[0]);
					vo.setEndTime(dates[1]);
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

}
