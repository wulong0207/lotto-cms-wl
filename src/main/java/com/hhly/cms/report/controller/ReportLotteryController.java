package com.hhly.cms.report.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.lotterymgr.service.LotteryTypeService;
import com.hhly.cms.utils.Authority;
import com.hhly.report.remote.service.IReportService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.MathUtil;
import com.hhly.skeleton.base.util.NumberUtil;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryChildBO;
import com.hhly.skeleton.cms.report.bo.ReportLogBO;
import com.hhly.skeleton.cms.report.bo.ReportLotteryChildBO;
import com.hhly.skeleton.cms.report.bo.ReportLotteryTypeBO;
import com.hhly.skeleton.cms.report.bo.ReportOrderRangeBO;
import com.hhly.skeleton.cms.report.bo.ReportUserBO;
import com.hhly.skeleton.cms.report.vo.ReportLogVO;
import com.hhly.skeleton.cms.report.vo.ReportLotteryChildVO;
import com.hhly.skeleton.cms.report.vo.ReportLotteryTypeVO;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

import net.sf.json.JSONObject;

/**
 * @desc    彩种数据
 * @author  Tony Wang
 * @date    2017年8月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping("report/lottery")
public class ReportLotteryController extends BaseController {
	
	@Autowired
	private IReportService reportService;
	
	@Autowired
	private ExcelExportService excelExportService;
	
	@Autowired
    private LotteryTypeService lotteryTypeService;
	
	private static Logger logger = Logger.getLogger(ReportLotteryController.class);
	
	private static final List<Integer> TOP8_LOTTERY = Arrays.asList(
			Lottery.FB.getName(),
			Lottery.SSQ.getName(),
			Lottery.DLT.getName(),
			Lottery.BB.getName(),
			Lottery.BJDC.getName(),
			Lottery.SD11X5.getName(),
			Lottery.CQSSC.getName(),
			Lottery.JSK3.getName());
	private static final List<String> TOP8_LOTTERY_NAME = Arrays.asList(
			Lottery.FB.getDesc(),
			Lottery.SSQ.getDesc(),
			Lottery.DLT.getDesc(),
			Lottery.BB.getDesc(),
			Lottery.BJDC.getDesc(),
			Lottery.SD11X5.getDesc(),
			Lottery.CQSSC.getDesc(),
			Lottery.JSK3.getDesc());
	
	@RequestMapping("")
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "/report/report_lottery";
	}
	
	/**
	 * @desc   数据概况
	 * @author Tony Wang
	 * @create 2017年9月8日
	 * @param vo
	 * @return 
	 */
	@RequestMapping(value = "/overview", method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public ReportUserBO overview(ReportSearchVO vo) {
		ReportSearchVO criteria = new ReportSearchVO();
		BeanUtils.copyProperties(vo, criteria);
		// 根据渠道找出所有子渠道
		processConditon(criteria);
		// 1：充值；2：投注；3：中奖'
		criteria.setType(2);
		
		ReportLogVO log = new ReportLogVO();
		// 投注用户、投注金额、投注次数一起查
		// 设置查询用户id去重,计算记录数,
		log.setDistinctUserCount(true);
		log.setSumOrderAmount(true);
		log.setCount(true);
		criteria.setLog(log);
		ReportLogBO reportLog = reportService.sumReportLogAsOne(criteria);
		int orderUser = 0;
		double orderAmount = 0;
		int orderTimes = 0;
		if(reportLog != null) {
			orderUser = reportLog.getDistinctUserCount()==null ? 0 : reportLog.getDistinctUserCount();
			orderAmount = reportLog.getOrderAmount()==null ? 0 : reportLog.getOrderAmount();
			orderTimes = reportLog.getCount()==null ? 0 : reportLog.getCount();
		}
		
		// 新用户投注人数
		int newOrderUser = 0;
		double newOrderAmount = 0;
		int newOrderTimes = 0;
		// 设置只查询新用户投注
		criteria.setTimes(1);
		// 查询出是新用户投注的user_id
		List<ReportLogBO> reportLogs = reportService.findReportLog(criteria);
		if(!CollectionUtils.isEmpty(reportLogs)) {
			List<Long> userIds = new ArrayList<>();
			for(ReportLogBO tmp : reportLogs) 
				userIds.add(tmp.getUserId());
			criteria.setTimes(null);
			criteria.setUserIds(userIds);
			reportLog = reportService.sumReportLogAsOne(criteria);
			if(reportLog != null) {
				newOrderUser = reportLog.getDistinctUserCount()==null ? 0 : reportLog.getDistinctUserCount();
				newOrderAmount = reportLog.getOrderAmount()==null ? 0 : reportLog.getOrderAmount();
				newOrderTimes = reportLog.getCount()==null ? 0 : reportLog.getCount();
			}
		}
		
		ReportUserBO user = new ReportUserBO();
		user.setOrderUser(orderUser);
		user.setOrderMoney(orderAmount);
		user.setNewOrderUser(newOrderUser);
		user.setNewOrderMoney(newOrderAmount);
		user.setAvgOrderMoney(orderUser == 0? 0 : NumberUtil.round(orderAmount/orderUser,2,BigDecimal.ROUND_HALF_UP));
		user.setNewAvgOrderMoney(newOrderUser==0 ? 0 : NumberUtil.round(newOrderAmount/newOrderUser,2,BigDecimal.ROUND_HALF_UP));
		user.setLiveness(orderUser == 0? 0d : NumberUtil.round(orderTimes/orderUser,2,BigDecimal.ROUND_HALF_UP));
		user.setNewLiveness(newOrderUser == 0? 0d : NumberUtil.round(newOrderTimes/newOrderUser,2,BigDecimal.ROUND_HALF_UP));
		return user;
	}
	
	/**
	 * @desc   投注人数、投注金额
	 * @author Tony Wang
	 * @create 2017年9月8日
	 * @param vo
	 * @return 
	 */
	@RequestMapping(value = "/orderrange", method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public ReportOrderRangeBO findOrderRange(ReportSearchVO vo) {
		// r_log的父渠道不包含子渠道的数据，其他表包含
		processConditon(vo);
		// 1：充值；2：投注；3：中奖'
		vo.setType(2);
		vo.setGroupBy("user_id");
		ReportLogVO logVO = new ReportLogVO();
		logVO.setSumOrderAmount(true);
		logVO.setMinTimes(true);
		vo.setLog(logVO);
		List<ReportLogBO> logs = reportService.sumReportLog(vo);
		ReportOrderRangeBO range = new ReportOrderRangeBO();
		int totalUser=0;
		int u_1_100=0;
		int u_101_500=0;
		int u_501_1000=0;
		int u_1001_5000=0;
		int u_5001_10000=0;
		int u_10001_50000=0;
		int u_50001_100000=0;
		int u_100000=0;
		double totalMoney=0;
		double o_1_100=0;
		double o_101_500=0;
		double o_501_1000=0;
		double o_1001_5000=0;
		double o_5001_10000=0;
		double o_10001_50000=0;
		double o_50001_100000=0;
		double o_100000=0;
		
		int u_1_100_new=0;
		int u_101_500_new=0;
		int u_501_1000_new=0;
		int u_1001_5000_new=0;
		int u_5001_10000_new=0;
		int u_10001_50000_new=0;
		int u_50001_100000_new=0;
		int u_100000_new=0;
		double o_1_100_new=0;
		double o_101_500_new=0;
		double o_501_1000_new=0;
		double o_1001_5000_new=0;
		double o_5001_10000_new=0;
		double o_10001_50000_new=0;
		double o_50001_100000_new=0;
		double o_100000_new=0;
		if(!CollectionUtils.isEmpty(logs)) {
			totalUser = logs.size();
			double orderAmount;
			for(ReportLogBO log : logs) {
				orderAmount = log.getOrderAmount();
				totalMoney = MathUtil.add(totalMoney, orderAmount);
				if(Double.compare(orderAmount, 1) >= 0 && Double.compare(orderAmount, 100) <=0) {
					u_1_100 ++;
					o_1_100 = MathUtil.add(o_1_100, orderAmount);
					// 如果是新投注
					if(Objects.equals(log.getTimes(), 1)) {
						u_1_100_new++;
						o_1_100_new = MathUtil.add(o_1_100_new, orderAmount);
					}
				}
				else if(Double.compare(orderAmount, 101) >= 0 && Double.compare(orderAmount, 500) <=0) {
					u_101_500 ++;
					o_101_500 = MathUtil.add(o_101_500, orderAmount);
					// 如果是新投注
					if(Objects.equals(log.getTimes(), 1)) {
						u_101_500_new++;
						o_101_500_new = MathUtil.add(o_101_500_new, orderAmount);
					}
				}
				else if(Double.compare(orderAmount, 501) >= 0 && Double.compare(orderAmount, 1000) <=0) {
					u_501_1000 ++;
					o_501_1000 = MathUtil.add(o_501_1000, orderAmount);
					// 如果是新投注
					if(Objects.equals(log.getTimes(), 1)) {
						u_501_1000_new++;
						o_501_1000_new = MathUtil.add(o_501_1000_new, orderAmount);
					}
						
				}
				else if(Double.compare(orderAmount, 1001) >= 0 && Double.compare(orderAmount, 5000) <=0) {
					u_1001_5000 ++;
					o_1001_5000 = MathUtil.add(o_1001_5000, orderAmount);
					// 如果是新投注
					if(Objects.equals(log.getTimes(), 1)) {
						u_1001_5000_new++;
						o_1001_5000_new = MathUtil.add(o_1001_5000_new, orderAmount);
					}
				}
				else if(Double.compare(orderAmount, 5001) >= 0 && Double.compare(orderAmount, 10000) <=0) {
					u_5001_10000 ++;
					o_5001_10000 = MathUtil.add(o_5001_10000, orderAmount);
					// 如果是新投注
					if(Objects.equals(log.getTimes(), 1)) {
						u_5001_10000_new++;
						o_5001_10000_new = MathUtil.add(o_5001_10000_new, orderAmount);
					}
				}
				else if(Double.compare(orderAmount, 10001) >= 0 && Double.compare(orderAmount, 50000) <=0) {
					u_10001_50000 ++;
					o_10001_50000 = MathUtil.add(o_10001_50000, orderAmount);
					// 如果是新投注
					if(Objects.equals(log.getTimes(), 1)) {
						u_10001_50000_new++;
						o_10001_50000_new = MathUtil.add(o_10001_50000_new, orderAmount);
					}
				}
				else if(Double.compare(orderAmount, 50001) >= 0 && Double.compare(orderAmount, 100000) <=0) {
					u_50001_100000 ++;
					o_50001_100000 = MathUtil.add(o_50001_100000, orderAmount);
					// 如果是新投注
					if(Objects.equals(log.getTimes(), 1)) {
						u_50001_100000_new++;
						o_50001_100000_new = MathUtil.add(o_50001_100000_new, orderAmount);
					}
				}
				else if(Double.compare(orderAmount, 100000) > 0) {
					u_100000 ++;
					o_100000 = MathUtil.add(o_100000, orderAmount);
					// 如果是新投注
					if(Objects.equals(log.getTimes(), 1)) {
						u_100000_new++;
						o_100000_new = MathUtil.add(o_100000_new, orderAmount);
					}
				}
			}
		}
		range.setU_1_100(u_1_100);
		range.setU_101_500(u_101_500);
		range.setU_501_1000(u_501_1000);
		range.setU_1001_5000(u_1001_5000);
		range.setU_5001_10000(u_5001_10000);
		range.setU_10001_50000(u_10001_50000);
		range.setU_50001_100000(u_50001_100000);
		range.setU_100000(u_100000);
		
		range.setU_1_100_new(u_1_100_new);
		range.setU_101_500_new(u_101_500_new);
		range.setU_501_1000_new(u_501_1000_new);
		range.setU_1001_5000_new(u_1001_5000_new);
		range.setU_5001_10000_new(u_5001_10000_new);
		range.setU_10001_50000_new(u_10001_50000_new);
		range.setU_50001_100000_new(u_50001_100000_new);
		range.setU_100000_new(u_100000_new);
		
		range.setO_1_100(o_1_100);
		range.setO_101_500(o_101_500);
		range.setO_501_1000(o_501_1000);
		range.setO_1001_5000(o_1001_5000);
		range.setO_5001_10000(o_5001_10000);
		range.setO_10001_50000(o_10001_50000);
		range.setO_50001_100000(o_50001_100000);
		range.setO_100000(o_100000);
		
		range.setO_1_100_new(o_1_100_new);
		range.setO_101_500_new(o_101_500_new);
		range.setO_501_1000_new(o_501_1000_new);
		range.setO_1001_5000_new(o_1001_5000_new);
		range.setO_5001_10000_new(o_5001_10000_new);
		range.setO_10001_50000_new(o_10001_50000_new);
		range.setO_50001_100000_new(o_50001_100000_new);
		range.setO_100000_new(o_100000_new);
		
		range.setTotalUser(totalUser);
		range.setTotalMoney(totalMoney);
		return range;
	}
	
	private void processConditon(ReportSearchVO vo) {
		// 因为r_log的父渠道不包含子渠道的数据,所以当选择时"全部"渠道、彩种、投注平台时，要置空，以便select所有数据
		// r_log表要查询子渠道数据
		if(StringUtils.hasText(vo.getChannel())) {
			if(!Objects.equals(vo.getChannel(), "0")) {
				// 若不是选择"所有渠道"，则根据渠道找出所有子渠道
				vo.setChannelIds(reportService.findChildChannelIds(vo));
			}
			vo.setChannel(null);
		}
		if(Objects.equals(vo.getLotteryCode(), 0)) 
			vo.setLotteryCode(null);
		if(Objects.equals(vo.getPlatform(), 0)) 
			vo.setPlatform(null);
	}
	
	
//	/**
//	 * @desc   有些落地表，如r_lottery_child，按如下查询时为空
//	 * 	WHERE
//	 * 	channel_id = 0
//	 * 	AND platform = 0
//	 * 	AND lottery_code = 0
//	 * @author Tony Wang
//	 * @create 2017年11月16日
//	 * @param vo 
//	 */
//	private void processConditon2(ReportSearchVO vo) {
//		if(Objects.equals(vo.getChannel(), "0")) 
//			// 若不是选择"所有渠道"，则根据渠道找出所有子渠道
//			vo.setChannel(null);
//		if(Objects.equals(vo.getLotteryCode(), 0)) 
//			vo.setLotteryCode(null);
//		if(Objects.equals(vo.getPlatform(), 0)) 
//			vo.setPlatform(null);
//	}

	/**
	 * @desc   投注人数、投注金额(分页)
	 * @author Tony Wang
	 * @create 2017年9月8日
	 * @param vo
	 * @return 
	 */
	@RequestMapping(value = "/orderrange/paging", method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<ReportOrderRangeBO> findOrderRangeWithPaing(ReportSearchVO vo) {
		vo.setGroupBy("dt");
		vo.setSortField("dt");
		vo.setSortOrder("desc");
		return reportService.sumReportOrderRangeWithPaging(vo);
	}
	
	/**
	 * @desc   导出投注人数
	 * @author Tony Wang
	 * @create 2017年9月8日
	 * @param vo
	 * @return 
	 */
	@RequestMapping("/orderuser/excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void exportOrderUser(HttpServletResponse response,ReportSearchVO vo) throws IOException{
		vo.setGroupBy("dt");
		vo.setSortField("dt");
		vo.setSortOrder("desc");
		excel("投注人数", excelExportService.dataToExeclByStream(reportService.findUserRangeExcel(vo)), response);
	}
	
	/**
	 * @desc   导出投注金额
	 * @author Tony Wang
	 * @create 2017年9月8日
	 * @param vo
	 * @return 
	 */
	@RequestMapping("/ordermoney/excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void exportOrderMoney(HttpServletResponse response,ReportSearchVO vo) throws IOException{
		vo.setGroupBy("dt");
		vo.setSortField("dt");
		vo.setSortOrder("desc");
		excel("投注金额", excelExportService.dataToExeclByStream(reportService.findMoneyRangeExcel(vo)), response);
	}
	
	/**
	 * @desc   玩法分布
	 * @author Tony Wang
	 * @create 2017年9月8日
	 * @param vo
	 * @return 
	 */
	@RequestMapping(value = "/child", method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public List<ReportLogBO> findLotteryChild(ReportSearchVO vo) {
		// 如果有查询某个彩种则查询，否则不查
		if(vo.getLotteryCode()==null || Objects.equals(vo.getLotteryCode(), 0))
			return null;
		// r_log的父渠道不包含子渠道的数据，其他表包含
		processConditon(vo);
		// 1：充值；2：投注；3：中奖'
		vo.setType(2);
		vo.setGroupBy("lottery_child_code");
		ReportLogVO logVO = new ReportLogVO();
		logVO.setSumOrderAmount(true);
		logVO.setDistinctUserCount(true);
		logVO.setChildCode(true);
		vo.setLog(logVO);
		List<ReportLogBO> logs = reportService.sumReportLogChild(vo);
		return logs;
	}
	
	@RequestMapping(value = "/winning", method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public List<ReportLogBO> findLotteryWinning(ReportSearchVO vo) {
		/* 投注金额、中奖金额不用去重
		 * 返奖数据 查询r_lottery_child 表的order_money、winning_money
		 * "返奖数据"：当选择单个彩种时按子玩法展示，当选择"所有彩种时"按产品规定的8个彩种展示 ：
		 * ②仅统计以下8个彩种的数据：竞彩足球，双色球，大乐透，竞彩篮球 ，北京单场，十一运夺金，重庆时时彩，江苏快三
		 */
		// r_log的父渠道不包含子渠道的数据，其他表包含
		processConditon(vo);
		// 1：充值；2：投注；3：中奖'
		vo.setTypes(Arrays.asList(2,3));
		ReportLogVO logVO = new ReportLogVO();
		logVO.setSumOrderAmount(true);
		logVO.setSumWinningAmount(true);
		List<ReportLogBO> logs;
		if(vo.getLotteryCode()==null || Objects.equals(vo.getLotteryCode(), 0)) {
			vo.setLotteryCodes(TOP8_LOTTERY);
			logVO.setLotteryCode(true);
			vo.setGroupBy("lottery_code");
			vo.setLog(logVO);
			logs = reportService.sumReportLogLottery(vo);
		} else {
			logVO.setChildCode(true);
			vo.setLog(logVO);
			vo.setGroupBy("lottery_child_code");
			logs = reportService.sumReportLogChild(vo);
		}
		return logs;
	}
	
	@RequestMapping(value = "/child/paging", method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<JSONObject> findLotteryChildPaging(ReportSearchVO vo) {
		// "玩法分布"：当选择单个彩种时按子玩法更新，否则清空
		if(vo.getLotteryCode()==null || Objects.equals(vo.getLotteryCode(), 0))
			return null;
		return findLotteryChild4Page(vo);
	}
	
	private PagingBO<JSONObject> findLotteryChild4Page(ReportSearchVO vo) {
		// 记录数即为根据ReportSearchVO 查询 group by dt 的数量
		ReportSearchVO criteria = new ReportSearchVO();
		BeanUtils.copyProperties(vo, criteria, new String[] { "pageIndex", "pageSize"});
		criteria.setGroupBy("dt");
		criteria.setSortField("dt");
		criteria.setSortOrder("desc");
		List<ReportLotteryChildBO> list = reportService.findReportLotteryChild(criteria);
		int total = list.size();
		Assert.isTrue(total>0, "分页查询玩法分布或查询某个彩种所有子玩法的返奖数据时,查询出的分页时间区间为空");
		List<Date> dates = new ArrayList<>();
		for (ReportLotteryChildBO bo : list) {
			dates.add(bo.getDt());
		}
		int startIndex = vo.getStartRow();
		int endIndex = vo.getEndRow() > total ? total - 1 : vo.getEndRow() - 1;
		// 由于查询出来的时间倒序
		Date endTime = dates.get(startIndex);
		Date startTime = dates.get(endIndex);
		// 根据分页的时间范围查询记录
		criteria = new ReportSearchVO();
		BeanUtils.copyProperties(vo, criteria,
				new String[] { "pageIndex", "pageSize", "timeType", "startTime", "endTime" });
		criteria.setStartTime(startTime);
		criteria.setEndTime(endTime);
		ReportLotteryChildVO childVO = new ReportLotteryChildVO();
		// "玩法分布"需要查询投注人数、投注金额及计算arpu值
		// 查询某个彩种的"返奖数据"需要查询投注金额、中奖金额、计算返奖率
		childVO.setOrderUser(true);
		childVO.setOrderMoney(true);
		childVO.setWinningMoney(true);
		criteria.setChild(childVO);
		list = reportService.findReportLotteryChild(criteria);
		// 按时间降序排序
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<Date, List<ReportLotteryChildBO>> map = new TreeMap(new Comparator<Date>() {
			@Override
			public int compare(Date o1, Date o2) {
				if(o1!=null && o2!=null){
					if(o1.after(o2)){
						return -1;
					}else if(o1.equals(o2)){
						return 0;
					}
				}
				return 1;
			}
		});
		// 按日期分组
		List<Date> queryDates = dates.subList(startIndex, endIndex + 1);
		for (Date date : queryDates) {
			map.put(date, new ArrayList<ReportLotteryChildBO>());
		}
		for (ReportLotteryChildBO lot : list) {
			map.get(lot.getDt()).add(lot);
		}
		// 页面需要展示的
		List<Integer> childCodes = new ArrayList<>(); 
		List<String> childNames = new ArrayList<>();
		// 查询该彩种所有的子玩法
		StringVO lotteryVO = new StringVO();
		lotteryVO.setStr(vo.getLotteryCode()+"");
		List<LotteryChildBO> children = lotteryTypeService.findChild(lotteryVO);
		for (LotteryChildBO child : children) {
			childCodes.add(child.getLotteryChildCode());
			childNames.add(child.getChildName());
		}
		List<JSONObject> data = new ArrayList<>();
		JSONObject json;
		List<ReportLotteryChildBO> mapValue;
		// 遍历每1天的数据，同一天的数据合并为一条记录，对应表格的一行
		for (Map.Entry<Date, List<ReportLotteryChildBO>> entry : map.entrySet()) {
			json = new JSONObject();
			json.put("dt", DateUtil.convertDateToStr(entry.getKey(), DateUtil.DATE_FORMAT));
			mapValue = entry.getValue();
			// 落地表中，channel_id为0表示所有渠道，platform为0表示所有平台，child_code为0表示所有子玩法
			double orderMoney,arpu,winningMoney,winningRatio;
			int orderUser;
			// 用于判断各个子玩法当天是否有客户购买，没有购买则各项数据置为0
			boolean [] isSales = new boolean[childCodes.size()];
			// 遍历某天中，所有子玩法销售数据
			for (ReportLotteryChildBO lot : mapValue) {
				// 正常情况下mapValue的list的size等于彩种的子玩法个数加上子玩法为"0"(表示全部)，的
				orderUser = lot.getOrderUser();
				orderMoney = lot.getOrderMoney();
				winningMoney = lot.getWinningMoney();
				arpu = orderUser == 0 ? 0 : MathUtil.round(orderMoney/orderUser, 2);
				winningRatio = MathUtil.compareTo(orderMoney, 0) == 0 ? 0 : MathUtil.round(winningMoney/orderMoney*100, 2);
				int index = childCodes.indexOf(lot.getChildCode());
				if (index > -1) {
					isSales[index] = true;
					json.put("orderMoney" + index, orderMoney);
					json.put("orderUser" + index, orderUser);
					// 投注金额/投注人数（人均投注额）
					json.put("arpu" + index, arpu);
					json.put("winningMoney" + index, winningMoney);
					json.put("winningRatio" + index, winningRatio);
					
				} 
				else if(Objects.equals(lot.getChildCode(), 0)) {
					json.put("totalOrderMoney", orderMoney);
					json.put("totalOrderUser", orderUser);
					json.put("totalArpu", arpu);
					json.put("totalWinningMoney", winningMoney);
					json.put("totalWinningRatio", winningRatio);
					
				}
				else {
					logger.info("***************************彩种:"+vo.getLotteryCode()+"不存在子玩法:"+lot.getChildCode());
				}
			}
			for(int i = 0 ; i < isSales.length ; i++) {
				if(!isSales[i]) {
					json.put("orderUser" + i, 0);
					// 投注金额/投注人数（人均投注额）
					json.put("arpu" + i, 0);
					json.put("orderMoney" + i, 0);
					json.put("winningMoney" + i, 0);
					// 返奖率
					json.put("winningRatio" + i, 0);
				}
			}
			data.add(json);
		}
		PagingBO<JSONObject> result = new PagingBO<>();
		result.setTotal(total);
		result.setData(data);
		result.setOther(childNames);
		return result;
	}

	@RequestMapping(value = "/winning/paging", method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<JSONObject> findLotteryWinningPaging(ReportSearchVO vo) {
		/* 投注金额、中奖金额不用去重
		 * 返奖数据 查询r_lottery_child 表的order_money、winning_money
		 * "返奖数据"：当选择单个彩种时按子玩法展示，当选择"所有彩种时"按产品规定的8个彩种展示 ：
		 * ②仅统计以下8个彩种的数据：竞彩足球，双色球，大乐透，竞彩篮球 ，北京单场，十一运夺金，重庆时时彩，江苏快三
		 */
		if(vo.getLotteryCode()==null || Objects.equals(vo.getLotteryCode(), 0)) {
			// 如果查询所有彩种，则查r_lottery_type表
			// 查询运营规定的8个彩种及彩种为0(全部彩种)数据
			List<Integer> lotteryCodes = new ArrayList<>(TOP8_LOTTERY);
			lotteryCodes.add(0);
			vo.setLotteryCodes(lotteryCodes);
			vo.setLotteryCode(null);
			return findLotteryType4Page(vo);
		} else {
			// 如果查询某个彩种，则查r_lottery_child表
			return findLotteryChild4Page(vo);
		}
	}
	
	private PagingBO<JSONObject> findLotteryType4Page(ReportSearchVO vo) {
		// 记录数即为根据ReportSearchVO 查询 group by dt 的数量
		ReportSearchVO criteria = new ReportSearchVO();
		BeanUtils.copyProperties(vo, criteria, new String[] { "pageIndex", "pageSize"});
		criteria.setGroupBy("dt");
		criteria.setSortField("dt");
		criteria.setSortOrder("desc");
		List<ReportLotteryTypeBO> list = reportService.findReportLotteryType2(criteria);
		int total = list.size();
		Assert.isTrue(total>0, "按前8个彩种，分页查询返奖数据时,查询出的分页时间区间为空");
		List<Date> dates = new ArrayList<>();
		for (ReportLotteryTypeBO bo : list) {
			dates.add(bo.getDt());
		}
		int startIndex = vo.getStartRow();
		int endIndex = vo.getEndRow() > total ? total - 1 : vo.getEndRow() - 1;
		// 由于查询出来的时间倒序
		Date endTime = dates.get(startIndex);
		Date startTime = dates.get(endIndex);
		// 根据分页的时间范围查询记录
		criteria = new ReportSearchVO();
		BeanUtils.copyProperties(vo, criteria,
				new String[] { "pageIndex", "pageSize", "timeType", "startTime", "endTime","lotteryCodes" });
		criteria.setStartTime(startTime);
		criteria.setEndTime(endTime);
		ReportLotteryTypeVO lotteryTypeVO = new ReportLotteryTypeVO();
		// "返奖数据"需要查询投注金额、中奖金额、计算返奖率
		lotteryTypeVO.setOrderMoney(true);
		lotteryTypeVO.setWinningMoney(true);
		criteria.setGroupBy("dt,lottery_code");
		criteria.setLotteryTypeVO(lotteryTypeVO);
		list = reportService.findReportLotteryType2(criteria);
		// 按时间降序排序
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<Date, List<ReportLotteryTypeBO>> map = new TreeMap(new Comparator<Date>() {
			@Override
			public int compare(Date o1, Date o2) {
				if(o1!=null && o2!=null){
					if(o1.after(o2)){
						return -1;
					}else if(o1.equals(o2)){
						return 0;
					}
				}
				return 1;
			}
		});
		// 按日期分组
		List<Date> queryDates = dates.subList(startIndex, endIndex + 1);
		for (Date date : queryDates) {
			map.put(date, new ArrayList<ReportLotteryTypeBO>());
		}
		for (ReportLotteryTypeBO lot : list) {
			map.get(lot.getDt()).add(lot);
		}
		List<JSONObject> data = new ArrayList<>();
		JSONObject json;
		List<ReportLotteryTypeBO> mapValue;
		// 遍历每1天的数据，同一天的数据合并为一条记录，对应表格的一行
		for (Map.Entry<Date, List<ReportLotteryTypeBO>> entry : map.entrySet()) {
			json = new JSONObject();
			json.put("dt", DateUtil.convertDateToStr(entry.getKey(), DateUtil.DATE_FORMAT));
			mapValue = entry.getValue();
			// 落地表中，channel_id为0表示所有渠道，platform为0表示所有平台，child_code为0表示所有子玩法
			double orderMoney,winningMoney,winningRatio;
			// 用于判断各彩种当天是否有客户购买，没有购买则各项数据置为0
			boolean [] isSales = new boolean[TOP8_LOTTERY.size()];
			// 遍历某天中，所有子玩法销售数据
			for (ReportLotteryTypeBO lot : mapValue) {
				// 正常情况下mapValue的list的size等于彩种的子玩法个数加上子玩法为"0"(表示全部)，的
				orderMoney = lot.getOrderMoney();
				winningMoney = lot.getWinningMoney();
				winningRatio = MathUtil.compareTo(orderMoney, 0) == 0 ? 0 : MathUtil.round(winningMoney/orderMoney*100, 2);
				int index = TOP8_LOTTERY.indexOf(lot.getLotteryCode());
				if (index > -1) {
					isSales[index] = true;
					json.put("orderMoney" + index, orderMoney);
					json.put("winningMoney" + index, winningMoney);
					json.put("winningRatio" + index, winningRatio);
				} 
				else if(Objects.equals(lot.getLotteryCode(), 0)) {
					json.put("totalOrderMoney", orderMoney);
					json.put("totalWinningMoney", winningMoney);
					json.put("totalWinningRatio", winningRatio);
				}
			}
			for(int i = 0 ; i < isSales.length ; i++) {
				if(!isSales[i]) {
					json.put("orderMoney" + i, 0);
					json.put("winningMoney" + i, 0);
					// 返奖率
					json.put("winningRatio" + i, 0);
				}
			}
			data.add(json);
		}
		PagingBO<JSONObject> result = new PagingBO<>();
		result.setTotal(total);
		result.setData(data);
		result.setOther(TOP8_LOTTERY_NAME);
		return result;
	}

	// TODO 投注方式 也是查r_lottery_type，但导出excel时，字段不同
	/**
	 * @desc   投注方式
	 * @author Tony Wang
	 * @create 2017年9月8日
	 * @param vo2
	 * @return 
	 */
	@RequestMapping(value = "/way", method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public ReportLotteryTypeBO findLotteryWay(ReportSearchVO vo) {
		processConditon(vo);
		// 1：充值；2：投注；3：中奖'
		vo.setType(2);
		vo.setGroupBy("flag");
		ReportLogVO log = new ReportLogVO();
		// 投注用户、投注金额、投注次数一起查
		// 设置查询用户id去重,计算记录数,
		log.setDistinctUserCount(true);
		log.setSumOrderAmount(true);
		log.setFlag(true);
		vo.setLog(log);
		List<ReportLogBO> logs = reportService.sumReportLog(vo);
		ReportLotteryTypeBO lot = new ReportLotteryTypeBO();
		for(ReportLogBO rLog : logs) {
			// flag '1：代购；2：追加；3：合买',
			int flag = rLog.getFlag();
			if(flag == 1) {
				lot.setBuyUser(rLog.getDistinctUserCount());
				lot.setBuyMoney(rLog.getOrderAmount());
			} 
			else if(flag == 2) {
				lot.setAddUser(rLog.getDistinctUserCount());
				lot.setAddMoney(rLog.getOrderAmount());
			}
			else if(flag == 3) {
				lot.setGroupUser(rLog.getDistinctUserCount());
				lot.setGroupMoney(rLog.getOrderAmount());
			}
		}
		return lot;
	}
	
	@RequestMapping(value = "/way/paging", method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<ReportLotteryTypeBO> findLotteryWayPaging(ReportSearchVO vo) {
		ReportLotteryTypeVO lotteryTypeVO = new ReportLotteryTypeVO();
		// "返奖数据"需要查询投注金额、中奖金额、计算返奖率
		lotteryTypeVO.setOrderMoney(true);
		lotteryTypeVO.setOrderUser(true);
		lotteryTypeVO.setBuyMoney(true);
		lotteryTypeVO.setBuyUser(true);
		lotteryTypeVO.setAddMoney(true);
		lotteryTypeVO.setAddUser(true);
		lotteryTypeVO.setGroupMoney(true);
		lotteryTypeVO.setGroupUser(true);
		vo.setLotteryTypeVO(lotteryTypeVO);
		// 正常情况下，某渠道、某彩种、某平台、某天中的数据在r_lottery_type表只有一行，可以不用sum
		vo.setGroupBy("dt");
		vo.setSortField("dt");
		vo.setSortOrder("desc");
		// 投注方式的表格要显示order_money、order_user、代购、追号合买的投注人数，金额
		return reportService.sumReportLotteryTypeWithPaging(vo);
	}
	
	/**
	 * @desc   导出投注方式
	 * @author Tony Wang
	 * @create 2017年9月8日
	 * @param vo
	 * @return 
	 */
	@RequestMapping("/way/excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void exportLotteryWay(HttpServletResponse response,ReportSearchVO vo) throws IOException{
		vo.setGroupBy("dt");
		vo.setSortField("dt");
		vo.setSortOrder("desc");
		excel("投注方式", excelExportService.dataToExeclByStream(reportService.findLotteryWayExcel(vo)), response);
	}
	
	/**
	 * 返奖
	 * @desc 
	 * @create 2017年12月8日
	 * @param response
	 * @param vo
	 * @throws IOException void
	 */
//	@RequestMapping("/winning/excel")
//	@Authority(privilege =AuthEnum.EXPORT)
//	public void exportLotteryWinning(HttpServletResponse response,ReportSearchVO vo) throws IOException{
//		vo.setGroupBy("dt");
//		vo.setSortField("dt");
//		vo.setSortOrder("desc");
//		excel("返奖数据", excelExportService.dataToExeclByStream(reportService.findLotteryWayExcel(vo)), response);
//	}
}
