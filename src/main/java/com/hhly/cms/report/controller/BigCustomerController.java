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
import com.hhly.cms.report.service.BigCustomerService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.cms.report.bo.BigCustomerDataBO;
import com.hhly.skeleton.cms.report.bo.BigCustomerStatisticsBO;
import com.hhly.skeleton.cms.report.bo.ReportLotteryTypeBO;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

/**
 * 
* @Description: 大客户数据报表 
* @author HouXiangBao289
* @date 2017年9月29日 下午3:46:23 
* @version V1.0.0
 */
@RequestMapping("bigcustomer")
@Controller
public class BigCustomerController extends BaseController{
	
	@Autowired
	BigCustomerService bigCustomerService;
	
	private String[] lotNames = {"竞彩足球","双色球","大乐透","竞彩篮球","北京单场","十一运夺金","重庆时时彩","江苏快3"};
	
	@RequestMapping("")
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "/report/bigcustomer";
	}
	
	/**
	 * 
	 * @Description 查询明细数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/detailDataList")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object detailDataList(ReportSearchVO vo){
//		vo.setLotteryCode(null);// 与彩种无关
		PagingBO<BigCustomerDataBO> resultData = bigCustomerService.findBigCustomerData(vo);
		return resultData;
	}

	/**
	 * 
	 * @Description 查询大客户图表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findStatistics")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  BigCustomerStatisticsBO findStatistics(ReportSearchVO vo){
		BigCustomerStatisticsBO bo = bigCustomerService.findStatistics(vo);
		return bo;
	}
	
	/**
	 * 
	 * @Description 查询平台销量图表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findPlatformSale")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  BigCustomerStatisticsBO findPlatformSale(ReportSearchVO vo){
		BigCustomerStatisticsBO bo = bigCustomerService.findPlatformSale(vo);
		return bo;
	}
	
	/**
	 * 
	 * @Description 查询彩种投注金额图表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findLotSale")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object findLotSale(ReportSearchVO vo){
		List<ReportLotteryTypeBO> list = bigCustomerService.findLotSale(vo);
		if(list.size() > 0){
			for(ReportLotteryTypeBO bo:list){
				bo.setLotteryName(Lottery.getLottery(bo.getLotteryCode()).getDesc());
			}
		}else{
			for(String lotName:lotNames){
				ReportLotteryTypeBO bo = new ReportLotteryTypeBO();
				bo.setLotteryName(lotName);
				bo.setOrderMoney(0.00);
				list.add(bo);
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @Description 查询彩种投注人数图表数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/findLotBetCount")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object findLotBetCount(ReportSearchVO vo){
		List<ReportLotteryTypeBO> list = bigCustomerService.findLotBetUserCount(vo);
		if(list.size() > 0){
			for(ReportLotteryTypeBO bo:list){
				bo.setLotteryName(Lottery.getLottery(bo.getLotteryCode()).getDesc());
			}
		}else{
			for(String lotName:lotNames){
				ReportLotteryTypeBO bo = new ReportLotteryTypeBO();
				bo.setLotteryName(lotName);
				bo.setOrderUser(0);
				list.add(bo);
			}
		}
		return list;
	}
    
    /**
     * 导出新老用户报表数据到excel
     * 
     * @param response
     * @param category
     * @param code
     * @throws IOException
     */
    @RequestMapping("bigCustomerDataExcel")
    @Authority(privilege = AuthEnum.EXPORT)
    public void bigCustomerDataExcel(HttpServletResponse response, ReportSearchVO vo)
            throws IOException {
//    	vo.setLotteryCode(null);// 与彩种无关
        ByteArrayOutputStream outputStream = bigCustomerService
                .findBigCustomerDataExcel(vo);
        excel("月度报表-大客户数据", outputStream, response);
    }

}
