package com.hhly.cms.report.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.utils.Authority;
import com.hhly.report.remote.service.IRechargeService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.report.bo.RechargeOverviewBo;
import com.hhly.skeleton.cms.report.bo.RechargeRangeDataVO;
import com.hhly.skeleton.cms.report.bo.RechargeStyleDataBO;
import com.hhly.skeleton.cms.report.bo.SuccessOrFailStatisticsBO;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

@RequestMapping("/report/recharge")
@Controller
public class RechargeReportController extends BaseController {
	@Autowired
	private IRechargeService rechargeService;
	@Autowired
	private ExcelExportService excelExportService;

	@RequestMapping("")
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "report/rechargeReport";
	}

	@RequestMapping("rechargeOverview")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public RechargeOverviewBo rechargeOverview(ReportSearchVO vo) {
		return rechargeService.findOverview(vo);
	}

	@RequestMapping("personStatistics")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public RechargeRangeDataVO personStatistics(ReportSearchVO vo) {
		return rechargeService.personStatistics(vo);
	}

	@RequestMapping("rechargePersonList")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<RechargeRangeDataVO> rechargePersonList(ReportSearchVO vo) {
		return rechargeService.findRechargePersonList(vo);
	}

	@RequestMapping("moneyStatistics")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public RechargeRangeDataVO moneyStatistics(ReportSearchVO vo) {
		return rechargeService.moneyStatistics(vo);
	}

	@RequestMapping("rechargeMoneyList")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<RechargeRangeDataVO> rechargeMoneyList(ReportSearchVO vo) {
		return rechargeService.findRechargeMoneyList(vo);
	}

	@RequestMapping("styleStatistics")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public JSONObject styleStatistics(ReportSearchVO vo) {
		return rechargeService.styleStatistics(vo);
	}

	@RequestMapping("rechargeStyleList")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<RechargeStyleDataBO> rechargeStyleList(ReportSearchVO vo) {
		return rechargeService.findRechargeStyleList(vo);
	}

	@RequestMapping("statusStatistics")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public JSONObject statusStatistics(ReportSearchVO vo) {
		return rechargeService.statusStatistics(vo);
	}

	@RequestMapping("rechargeStatusList")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<SuccessOrFailStatisticsBO> rechargeStatusList(ReportSearchVO vo) {
		return rechargeService.findRechargeStatusList(vo);
	}

	@RequestMapping("rechargeStyles")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public List<DictionaryBO> getStyles() {
		List<DictionaryBO> rechargeStyles = rechargeService.getRechargeStyles();
		return rechargeStyles;
	}

	@RequestMapping("exportExcel")
	@Authority(privilege = AuthEnum.EXPORT)
	public void exportRookie(HttpServletResponse response, ReportSearchVO vo, @RequestParam("excel") Integer excel)
			throws IOException {
		//activeCode值
		if(excel ==0){
			excel("充值人数", excelExportService.dataToExeclByStream(rechargeService.findRechargePersonNoPageList(vo)), response);
		}else if(excel ==1){
			excel("充值金额", excelExportService.dataToExeclByStream(rechargeService.findRechargeMoneyNoPageList(vo)), response);
		}else if(excel ==2){
			excel("充值方式", excelExportService.dataToExeclByStream(rechargeService.findRechargeStyleNoPageList(vo)), response);
		}else if(excel ==3){
			excel("成功与失败", excelExportService.dataToExeclByStream(rechargeService.findRechargeStatusNoPageList(vo)), response);
		}
	}
}
