package com.hhly.cms.lotterymgr.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.lotterymgr.service.SendPrizeService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.common.OrderEnum.OrderStatus;
import com.hhly.skeleton.base.common.OrderEnum.OrderWinningStatus;
import com.hhly.skeleton.base.common.OrderEnum.PayStatus;
import com.hhly.skeleton.cms.ordermgr.vo.OrderInfoCmsSearchVO;

/**
 * 
 * @desc 派奖管理
 * @author jiangwei
 * @date 2017-2-4
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/lotterymgr/prize")
public class SendPrizeController extends BaseController {

	/**
	 * 派奖服务
	 */
	@Autowired
	private SendPrizeService sendPrizeService;

	/**
	 * @desc 跳转到首页
	 * @author huangb
	 * @date 2017年2月11日
	 * @return 跳转到首页
	 */
	@RequestMapping()
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "lotterymgr/prize";
	}
	
	/**
	 * @desc 分页查询
	 * @author huangb
	 * @date 2017年2月8日
	 * @param orderInfoCmsSearch
	 *            查询参数
	 * @return 分页查询列表
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object list(OrderInfoCmsSearchVO orderInfoCmsSearch) {
		List<Short> winningStatusList = new ArrayList<>();
		if(orderInfoCmsSearch.getWinningStatus()!=null){
			winningStatusList.add(orderInfoCmsSearch.getWinningStatus().shortValue());
		}else{
			winningStatusList.add(OrderWinningStatus.WINNING.getValue());
			winningStatusList.add(OrderWinningStatus.GET_WINNING.getValue());
		}
		orderInfoCmsSearch.setWinningStatusList(winningStatusList);
		orderInfoCmsSearch.setPayStatus((int)PayStatus.SUCCESS_PAY.getValue());
		orderInfoCmsSearch.setOrderStatus((int)OrderStatus.TICKETED.getValue());
		return sendPrizeService.findPagingSendPrize(orderInfoCmsSearch);
	}

	/**
	 * @desc 导出excle
	 * @author huangb
	 * @date 2017年1月20日
	 * @param response
	 * @param orderInfoCmsSearch
	 *            查询对象
	 * @throws IOException
	 */
	@RequestMapping("/excel")
	@Authority(privilege = AuthEnum.EXPORT)
	public void exportExcel(HttpServletResponse response, OrderInfoCmsSearchVO orderInfoCmsSearch) throws IOException {
		List<Short> winningStatusList = new ArrayList<>();
		winningStatusList.add(OrderWinningStatus.WINNING.getValue());
		winningStatusList.add(OrderWinningStatus.GET_WINNING.getValue());
		orderInfoCmsSearch.setWinningStatusList(winningStatusList);
		orderInfoCmsSearch.setPayStatus((int)PayStatus.SUCCESS_PAY.getValue());
		orderInfoCmsSearch.setOrderStatus((int)OrderStatus.TICKETED.getValue());
		ByteArrayOutputStream outputStream = sendPrizeService.findExcelSendPrize(orderInfoCmsSearch);
		excel("send_prize", outputStream, response);
	}
	
	/**
	 * 查询中奖信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月30日 上午10:00:35
	 * @param orderInfoCmsSearch
	 * @return
	 */
	@RequestMapping(value = "/award")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object awardInfo(OrderInfoCmsSearchVO orderInfoCmsSearch) {
		return getResultSuccess(sendPrizeService.getAwardInfo(orderInfoCmsSearch));
	}
}
