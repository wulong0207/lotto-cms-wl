package com.hhly.cms.report.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.report.service.UserDataService;
import com.hhly.cms.utils.Authority;
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
 * 
* @Description:用户数据报表 
* @author HouXiangBao289
* @date 2017年9月12日 下午5:29:00 
* @version V1.0.0
 */
@RequestMapping("userdata")
@Controller
public class UserDataController extends BaseController{
	
	@Autowired
	UserDataService userDataService;
	
	@RequestMapping("")
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "/report/userdata";
	}

	/**
	 * 
	 * @Description 数据概况 
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/statistics")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  UserDataBO statistics(ReportSearchVO vo){
		return userDataService.findUserDataStatistics(vo);
	}
	
	/**
	 * 
	 * @Description 渠道数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/channelTree")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  List<ChannelBO> channelTree(){
		 List<ChannelBO> list = userDataService.findChannelList();
		 return list;
	}
	
	/**
	 * 
	 * @Description 查询用户注册与有效报表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findRegisterList")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object findUserRegisterReport(ReportSearchVO vo){
		return userDataService.findUserRegisterReport(vo);
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
	public  UserRegisterBO findRegisterUserSum(ReportSearchVO vo){
		UserRegisterBO bo = userDataService.findRegisterUserSum(vo);
		return bo;
	}
	
	/**
	 * 
	 * @Description 查询新老用户报表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findNewOldUserData")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object findNewOldUserData(ReportSearchVO vo){
		return userDataService.findNewOldUserData(vo);
	}
	
	/**
	 * 
	 * @Description 新老用户图表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findNewOldUserSum")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public UserNewOldBO findNewOldUserSum(ReportSearchVO vo){
		UserNewOldBO bo = userDataService.findNewOldUserSum(vo);
		return bo;
	}
	
//	/**
//	 * 
//	 * @Description 查询沉默用户报表数据
//	 * @author HouXiangBao289
//	 * @param vo
//	 * @return
//	 */
//	@RequestMapping(value = "/findSilentUserData")
//	@Authority(privilege=AuthEnum.SEARCH)
//	@ResponseBody
//	public  Object findSilentUserData(ReportSearchVO vo){
//		return userDataService.findSilentUserData(vo);
//	}
	
	/**
	 * 
	 * @Description 沉默用户图表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findSilentUserSum")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  UserSilentBO findSilentUserSum(ReportSearchVO vo){
		UserSilentBO bo = userDataService.findSilentUserSum(vo);
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
        ByteArrayOutputStream outputStream = userDataService
                .getRegisterDataExcel(vo);
        excel("registerData", outputStream, response);
    }
    
    /**
     * 导出新老用户报表数据到excel
     * 
     * @param response
     * @param category
     * @param code
     * @throws IOException
     */
    @RequestMapping("newOldUserDataexcel")
    @Authority(privilege = AuthEnum.EXPORT)
    public void newOldUserDataexcel(HttpServletResponse response, ReportSearchVO vo)
            throws IOException {
        ByteArrayOutputStream outputStream = userDataService
                .getNewOldUserDataExcel(vo);
        excel("newOldUserData", outputStream, response);
    }
    
//    /**
//     * 导出沉默用户报表数据到excel
//     * 
//     * @param response
//     * @param category
//     * @param code
//     * @throws IOException
//     */
//    @RequestMapping("silentUserDataexcel")
//    @Authority(privilege = AuthEnum.EXPORT)
//    public void silentUserDataexcel(HttpServletResponse response, ReportSearchVO vo)
//            throws IOException {
//        ByteArrayOutputStream outputStream = userDataService
//                .getSilentUserDataExcel(vo);
//        excel("silentUserData", outputStream, response);
//    }
    
    /**
	 * 
	 * @Description 查询销售报表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findSaleDataList")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  PagingBO<SaleDataBO> findSaleDataList(ReportSearchVO vo){
		return userDataService.findSaleData(vo);
	}
	
	@RequestMapping("saleData")
	@Authority(privilege = AuthEnum.SEARCH)
	public String saleData() {
		return "/report/saleData";
	}

	
	/**
	 * 
	 * @Description 查询渠道报表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findChannelDataList")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  PagingBO<ChannelDataBO> findChannelDataList(ReportSearchVO vo){
		return userDataService.findChannelData(vo);
	}
	
	@RequestMapping("channelData")
	@Authority(privilege = AuthEnum.SEARCH)
	public String channelData() {
		return "/report/channelData";
	}
    
    /**
     * 导出渠道销售数据到excel
     * 
     * @param response
     * @param category
     * @param code
     * @throws IOException
     */
    @RequestMapping("channelReportExcel")
    @Authority(privilege = AuthEnum.EXPORT)
    public void channelReportExcel(HttpServletResponse response, ReportSearchVO vo)
            throws IOException {
        ByteArrayOutputStream outputStream = userDataService
                .findChannelExcelData(vo);
        excel("channelReport", outputStream, response);
    }
    
    /**
     * 导出销售数据到excel
     * 
     * @param response
     * @param category
     * @param code
     * @throws IOException
     */
    @RequestMapping("saleReportExcel")
    @Authority(privilege = AuthEnum.EXPORT)
    public void saleReportExcel(HttpServletResponse response, ReportSearchVO vo)
            throws IOException {
        ByteArrayOutputStream outputStream = userDataService
                .findSaleExcelData(vo);
        excel("saleReport", outputStream, response);
    }

}
