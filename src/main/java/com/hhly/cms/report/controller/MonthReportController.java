package com.hhly.cms.report.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.report.service.MonthDataService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.cms.report.bo.MonthBetRangeBO;
import com.hhly.skeleton.cms.report.bo.MonthLotDataBO;
import com.hhly.skeleton.cms.report.bo.MonthPlatformDataBO;
import com.hhly.skeleton.cms.report.bo.MonthRegisterBO;
import com.hhly.skeleton.cms.report.bo.PlatformFirstBO;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

/**
 * 
* @Description: 月度报表 
* @author HouXiangBao289
* @date 2017年9月29日 下午3:46:23 
* @version V1.0.0
 */
@RequestMapping("monthdata")
@Controller
public class MonthReportController extends BaseController{
	
	@Autowired
	MonthDataService monthDataService;
	
	@RequestMapping("")
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "/report/monthdata";
	}
	
	/**
	 * 
	 * @Description 查询平台报表明细数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/platformList")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object platformList(ReportSearchVO vo){
		return monthDataService.findPlatformData(vo);
	}
	
	/**
	 * 
	 * @Description 查询彩种报表明细数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/lotList")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object lotList(ReportSearchVO vo){
		return monthDataService.findLotData(vo);
	}
	
	/**
	 * 
	 * @Description 查询投注明细数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/betList")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object buyUserCountList(ReportSearchVO vo){
		return monthDataService.findBetData(vo);
	}

	
	/**
	 * 
	 * @Description 查询注册与有效明细数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/registerList")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object registerList(ReportSearchVO vo){
		return monthDataService.findRegisterUserData(vo);
	}
	
	/**
	 * 
	 * @Description 查询用户注册与有效图表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findRegisterSum")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  MonthRegisterBO findRegisterUserSum(ReportSearchVO vo){
		MonthRegisterBO bo = monthDataService.findMonthRegisterUserSum(vo);
		if(bo == null){bo = new MonthRegisterBO();}
		return bo;
	}
	
	/**
	 * 
	 * @Description 查询平台销量图表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findPlatformSumMoney")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  MonthPlatformDataBO findPlatformSumMoney(ReportSearchVO vo){
		MonthPlatformDataBO bo = monthDataService.findPlatformSumMoney(vo);
		if(bo == null){bo = new MonthPlatformDataBO();}
		return bo;
	}
	
	/**
	 * 
	 * @Description 查询平台投注人数图表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findPlatformUserCount")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  MonthPlatformDataBO findPlatformUserCount(ReportSearchVO vo){
		MonthPlatformDataBO bo = monthDataService.findPlatformUserCount(vo);
		if(bo == null){bo = new MonthPlatformDataBO();}
		return bo;
	}
	
	/**
	 * 
	 * @Description 查询各彩种投注人数
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/selectLotUserSum")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  MonthLotDataBO selectLotUserSum(ReportSearchVO vo){
		MonthLotDataBO bo = monthDataService.selectLotUserSum(vo);
		if(bo == null){bo = new MonthLotDataBO();}
		return bo;
	}
	
	/**
	 * 
	 * @Description 查询各彩种销量
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/selectLotBetMoneySum")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  MonthLotDataBO selectLotBetMoneySum(ReportSearchVO vo){
		MonthLotDataBO bo = monthDataService.selectLotBetMoneySum(vo);
		if(bo == null){bo = new MonthLotDataBO();}
		return bo;
	}
	
	/**
	 * 
	 * @Description 查询投注数据(图表数据)
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findBetDataSum")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  MonthBetRangeBO findBetMoneySum(ReportSearchVO vo){
		MonthBetRangeBO bo = monthDataService.findBetDataSum(vo);
		if(bo == null){bo = new MonthBetRangeBO();}
		return bo;
	}
	
	/**
	 * 
	 * @Description 查询新用户投注(图表数据)
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findNewUserBetMoneySum")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  MonthBetRangeBO findNewUserBetMoneySum(ReportSearchVO vo){
		MonthBetRangeBO bo = monthDataService.findNewUserBetMoneySum(vo);
		if(bo == null){bo = new MonthBetRangeBO();}
		return bo;
	}
	
	/**
	 * 
	 * @Description 查询注册与有效报表各平台新增有效用户数
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findPlatformUserSum")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  PlatformFirstBO findPlatformUserSum(ReportSearchVO vo){
		PlatformFirstBO bo = monthDataService.findPlatformUserSum(vo);
		if(bo == null){bo = new PlatformFirstBO();}
		return bo;
	}

	/**
     * 导出注册与有效报表数据到excel
     * 
     * @param response
     * @param category
     * @param code
     * @throws IOException
     */
    @RequestMapping("registerUserDataexcel")
    @Authority(privilege = AuthEnum.EXPORT)
    public void registerUserDataexcel(HttpServletResponse response, ReportSearchVO vo)
            throws IOException {
        ByteArrayOutputStream outputStream = monthDataService
                .findRegisterUserDataExcel(vo);
        excel("month-register", outputStream, response);
    }
    
    /**
     * 导出月度报表-平台数据到excel
     * 
     * @param response
     * @param category
     * @param code
     * @throws IOException
     */
    @RequestMapping("platformDataExcel")
    @Authority(privilege = AuthEnum.EXPORT)
    public void platformDataExcel(HttpServletResponse response, ReportSearchVO vo)
            throws IOException {
        ByteArrayOutputStream outputStream = monthDataService
                .findPlatformDataExcel(vo);
        excel("month-platform", outputStream, response);
    }
    
    /**
     * 导出月度报表-彩种数据到excel
     * 
     * @param response
     * @param category
     * @param code
     * @throws IOException
     */
    @RequestMapping("lotDataExcel")
    @Authority(privilege = AuthEnum.EXPORT)
    public void lotDataexcel(HttpServletResponse response, ReportSearchVO vo)
            throws IOException {
        ByteArrayOutputStream outputStream = monthDataService
                .findLotDataExcel(vo);
        excel("month-lottery", outputStream, response);
    }
    
    /**
     * 导出月度报表-投注人数数据到excel
     * 
     * @param response
     * @param category
     * @param code
     * @throws IOException
     */
    @RequestMapping("betUserDataExcel")
    @Authority(privilege = AuthEnum.EXPORT)
    public void betUserDataExcel(HttpServletResponse response, ReportSearchVO vo)
            throws IOException {
        ByteArrayOutputStream outputStream = monthDataService
                .findBetCountExcelData(vo);
        excel("month-sale", outputStream, response);
    }
    
    /**
     * 导出月度报表-投注金额数据到excel
     * 
     * @param response
     * @param category
     * @param code
     * @throws IOException
     */
    @RequestMapping("betMoneyDataExcel")
    @Authority(privilege = AuthEnum.EXPORT)
    public void betMoneyDataExcel(HttpServletResponse response, ReportSearchVO vo)
            throws IOException {
        ByteArrayOutputStream outputStream = monthDataService
                .findBetMoneyExcelData(vo);
        excel("month-sale", outputStream, response);
    }

}
