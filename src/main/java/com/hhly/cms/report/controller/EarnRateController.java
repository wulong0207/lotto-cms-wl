package com.hhly.cms.report.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.utils.Authority;
import com.hhly.report.remote.service.IEarnRateService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.report.bo.RateStatuisticsBO;
import com.hhly.skeleton.cms.report.bo.UserDetailBO;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

/**
 * 
 * @desc 盈利率
 * @author chenghougui
 * @Date 2017年9月16日
 * @Company 益彩网络科技公司
 * @version
 */
@RequestMapping("report/earnRate")
@Controller
public class EarnRateController extends BaseController {
	@Autowired
	private IEarnRateService earnRateService;
	@Autowired
	private ExcelExportService excelExportService;

	//盈利率
	@RequestMapping("")
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "report/earnRate";
	}

	@RequestMapping("rateStatistics")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public RateStatuisticsBO getRateStatistics(ReportSearchVO vo) {
		return earnRateService.getRateStatistics(vo);
	}
	
	@RequestMapping("lotteryTypes")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public List<DictionaryBO> getlotteryTypes(){
		return earnRateService.getlotteryTypes();
	}
	
	
	// 盈利率排行榜  
	@RequestMapping("earnRateRank")
	@Authority(privilege=AuthEnum.SEARCH)
	public String earnRateRank(){
		return "report/earnRateRank";
	}
	
	//盈利率明细
	@RequestMapping("earnRateDetail")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<UserDetailBO> getDetail(ReportSearchVO vo){
		return earnRateService.getRateRankDetail(vo);
	}
	
	@RequestMapping("exportExcel")
	@Authority(privilege = AuthEnum.EXPORT)
	public void exportRookie(HttpServletResponse response, ReportSearchVO vo)
			throws IOException {
		List<UserDetailBO> rateRankList = earnRateService.getRateRankList(vo);
		if(rateRankList!=null){
			excel("rateRank", excelExportService.dataToExeclByStream(rateRankList), response);	
		}
	}
	
}
