package com.hhly.cms.report.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.utils.Authority;
import com.hhly.report.remote.service.IReportService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.cms.report.bo.ReportLotteryTypeBO;
import com.hhly.skeleton.cms.report.bo.ReportUserBO;
import com.hhly.skeleton.cms.report.bo.ReportUserCompositeBO;
import com.hhly.skeleton.cms.report.bo.ReportUserRookieExcelBO;
import com.hhly.skeleton.cms.report.vo.ReportLotteryTypeVO;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

import net.sf.json.JSONObject;

/**
 * @desc 运营简报
 * @author Tony Wang
 * @date 2017年8月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping("report/brief")
public class ReportBriefController extends BaseController {

	@Autowired
	private IReportService reportService;

	@Autowired
	private ExcelExportService excelExportService;
	
	private static final Integer FIRST=1;
	@RequestMapping("")
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "/report/report_brief";
	}

//	@RequestMapping(value = "/info", method = RequestMethod.GET)
//	@Authority(privilege = AuthEnum.SEARCH)
//	@ResponseBody
//	public Object info(ReportSearchVO vo) {
//		return reportService.findReportUser(vo);
//	}

	/**
	 * @desc 数据概况
	 * @author Tony Wang
	 * @create 2017年9月8日
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/overview", method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public ReportUserCompositeBO overview(ReportSearchVO vo) {
		return reportService.overviewReportUser(vo);
	}

	/**
	 * @desc 新用户数据
	 * @author Tony Wang
	 * @create 2017年9月8日
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/rookie", method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public List<ReportUserBO> findRookieData(ReportSearchVO vo) {
		vo.setGroupBy("dt");
		vo.setSortField("dt");
		vo.setSortOrder("asc");
		return reportService.findReportUser(vo);
	}

	/**
	 * @desc 新用户数据
	 * @author Tony Wang
	 * @create 2017年9月8日
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/rookie/paging", method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<ReportUserBO> findRookieDataWithPaing(ReportSearchVO vo) {
		vo.setGroupBy("dt");
		vo.setSortField("dt");
		vo.setSortOrder("desc");
		return reportService.findReportUserWithPaging(vo);
	}

	// TODO 新用户数据和全站投注情况都是查r_user表，只是查询字段不同
	@RequestMapping("/rookie/excel")
	@Authority(privilege = AuthEnum.EXPORT)
	public void exportRookie(HttpServletResponse response, ReportSearchVO vo) throws IOException {
		List<ReportUserRookieExcelBO> ret = reportService.findReportUserRookieExcel(vo);
		excel("新用户数据", excelExportService.dataToExeclByStream(ret), response);
	}

	@RequestMapping("/distribution/excel")
	@Authority(privilege = AuthEnum.EXPORT)
	public void exportDistribution(HttpServletResponse response, ReportSearchVO vo) throws IOException {
		excel("全站投注情况", excelExportService.dataToExeclByStream(reportService.findReportUserDistributionExcel(vo)),
				response);
	}

	/**
	 * @desc 投注用户
	 * @author Tony Wang
	 * @create 2017年9月17日
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/distribution/user", method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public List<ReportLotteryTypeBO> findUserDistribution(ReportSearchVO vo) {
		return findDistribution(vo, true);

	}

	/**
	 * @desc 金额分布
	 * @author Tony Wang
	 * @create 2017年9月17日
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/distribution/money", method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public List<ReportLotteryTypeBO> findMoneyDistribution(ReportSearchVO vo) {
		return findDistribution(vo, false);

	}

	/**
	 * 分布 用于图统计
	 * 
	 * @desc
	 * @create 2017年12月7日
	 * @param list
	 *            void
	 */
	private List<ReportLotteryTypeBO> findDistribution(ReportSearchVO vo, boolean resortByOrderUser) {
		// 通过r_log获取用户分布和
		List<ReportLotteryTypeBO> list;
		List<ReportLotteryTypeBO> newList;
		ReportLotteryTypeBO tmp;
		ReportLotteryTypeBO other = new ReportLotteryTypeBO();
		int otherOrderUser = 0, otherNewOrderUser = 0;
		double otherOrderMoney = 0, otherNewOrderMoney = 0;
		List<ReportLotteryTypeBO> subList = new ArrayList<>();
		if (resortByOrderUser) {
			// 投注用户分布
			list = reportService.getDisOrderUser(vo);
			//1表示首次
			vo.setTimes(FIRST);
			newList = reportService.getDisOrderUser(vo);
		} else {
			//投注金额分布
			list = reportService.getDisOrderMoney(vo);
			vo.setTimes(FIRST);
			newList = reportService.getDisOrderMoney(vo);
		}
		addLotteryName(list);
		for (int i = 0; i < list.size(); i++) {
			tmp = list.get(i);
			for (ReportLotteryTypeBO bo : newList) {
				if(bo.getLotteryCode().equals(tmp.getLotteryCode())){
					tmp.setNewOrderMoney(bo.getOrderMoney()==null?0:bo.getOrderMoney());
					tmp.setNewOrderUser(bo.getOrderUser()==null?0:bo.getOrderUser());
					break;
				}
			}
			//不是前八，归入其他
			if(i>=8){
				if (tmp.getOrderUser() != null) {
					otherOrderUser += tmp.getOrderUser();
				}
				if (tmp.getNewOrderUser() != null) {
					otherNewOrderUser += tmp.getNewOrderUser();
				}
				if (tmp.getOrderMoney() != null) {
					otherOrderMoney += tmp.getOrderMoney();
				}
				if (tmp.getNewOrderMoney() != null) {
					otherNewOrderMoney += tmp.getNewOrderMoney();
				}
			}
		}	
		other.setOrderUser(otherOrderUser);
		other.setNewOrderUser(otherNewOrderUser);
		other.setOrderMoney(otherOrderMoney);
		other.setNewOrderMoney(otherNewOrderMoney);
		other.setLotteryName("其他");
		if(list.size()>=8){
			subList = list.subList(0, 8);
		}else{
			subList = list;
		}
		if(list.size()!=0){
			subList.add(other);
		}
		return subList;
	}

	/**
	 * @desc 分页查询投注用户分布
	 * @author Tony Wang
	 * @create 2017年9月8日
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/distribution/user/paging", method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<JSONObject> findUserDistributionWithPaing(ReportSearchVO vo) {
		ReportLotteryTypeVO type = new ReportLotteryTypeVO();
		type.setOrderUser(true);
		vo.setLotteryTypeVO(type);
		return findDistributionWithPaing(vo, "orderUser");
	}

	/**
	 * @desc 分页查询投注金额分布
	 * @author Tony Wang
	 * @create 2017年9月8日
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/distribution/money/paging", method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<JSONObject> findMoneyDistributionWithPaing(ReportSearchVO vo) {
		ReportLotteryTypeVO type = new ReportLotteryTypeVO();
		type.setOrderMoney(true);
		vo.setLotteryTypeVO(type);
		return findDistributionWithPaing(vo, "orderMoney");
	}

	private PagingBO<JSONObject> findDistributionWithPaing(ReportSearchVO vo, String sortField) {
		// 按页面的条件，查询出销量(人或钱)前8的彩种
		ReportSearchVO criteria = new ReportSearchVO();
		BeanUtils.copyProperties(vo, criteria, new String[] { "pageIndex", "pageSize" });
		criteria.setPageIndex(0);
		criteria.setPageSize(8);
		criteria.setSortField(sortField);
		criteria.setSortOrder("desc");
		criteria.setGroupBy("lottery_code");
		List<ReportLotteryTypeBO> list = reportService.sumReportLotteryType(criteria);
		List<Integer> lotteryCodes = new ArrayList<>();
		// 销量前8的彩种名称，用来在显示表格的表头
		List<String> lotteryNames = new ArrayList<>();
		for (ReportLotteryTypeBO bo : list) {
			lotteryCodes.add(bo.getLotteryCode());
			Lottery lottery = Lottery.getLottery(bo.getLotteryCode());
			if (lottery != null) {
				lotteryNames.add(lottery.getDesc());
			}
		}
		// 记录数即为根据ReportSearchVO 查询 group by dt 的数量
		criteria = new ReportSearchVO();
		BeanUtils.copyProperties(vo, criteria, new String[] { "pageIndex", "pageSize", "sortOrder" });
		criteria.setGroupBy("dt");
		list = reportService.sumReportLotteryType(criteria);
		int total = list.size();
		List<Date> dates = new ArrayList<>();
		for (ReportLotteryTypeBO bo : list) {
			dates.add(bo.getDt());
		}
		if (dates.size() == 0) {
			return null;
		}
		int startIndex = vo.getStartRow();
		int endIndex = vo.getEndRow() > total ? total - 1 : vo.getEndRow() - 1;
		Date startTime = dates.get(startIndex);
		Date endTime = dates.get(endIndex);
		// 根据分页的时间范围查询记录
		criteria = new ReportSearchVO();
		BeanUtils.copyProperties(vo, criteria,
				new String[] { "pageIndex", "pageSize", "timeType", "startTime", "endTime" });
		criteria.setStartTime(startTime);
		criteria.setEndTime(endTime);
		// 按时间分页，则查询按时间分组  不需要统计所有彩种既lottery_code=0
		list = reportService.findReportLotteryType(criteria);
		// 按时间升序排序
		Map<Date, List<ReportLotteryTypeBO>> map = new TreeMap<>();
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
		//每天   总投注人数，总投注金额
		int totalOrderUser = 0;
		double totalOrderMoney = 0;
		criteria.setGroupBy("dt");
		List<ReportUserBO> totalUserBo = reportService.findReportUser2(criteria);
		// 遍历每1天的数据，同一天的数据合并为一条记录，对应表格的一行
		for (Map.Entry<Date, List<ReportLotteryTypeBO>> entry : map.entrySet()) {
			json = new JSONObject();
			json.put("dt", DateUtil.convertDateToStr(entry.getKey(), DateUtil.DATE_FORMAT));
			mapValue = entry.getValue();
			// 拼接彩种数据
			for (ReportLotteryTypeBO lot : mapValue) {
				int index = lotteryCodes.indexOf(lot.getLotteryCode());
				if (index > -1) {
					json.put("orderUser" + index, lot.getOrderUser());
					json.put("orderMoney" + index, lot.getOrderMoney());
				}
			}
			for (ReportUserBO reportUserBO : totalUserBo) {
				if(reportUserBO.getAddDate().equals(entry.getKey())){
					totalOrderUser = reportUserBO.getOrderUser();
					totalOrderMoney = reportUserBO.getOrderMoney();
				}
			}
			json.put("totalOrderUser", totalOrderUser);
			json.put("totalOrderMoney", totalOrderMoney);
			data.add(json);
		}
		PagingBO<JSONObject> result = new PagingBO<>();
		result.setTotal(total);
		result.setData(data);
		result.setOther(lotteryNames);
		return result;

	}

	private void addLotteryName(List<ReportLotteryTypeBO> list) {
		for (ReportLotteryTypeBO reportLotteryTypeBO : list) {
			Lottery lottery = Lottery.getLottery(reportLotteryTypeBO.getLotteryCode());
			if (lottery != null) {
				reportLotteryTypeBO.setLotteryName(lottery.getDesc());
			}
		}
	}
}
