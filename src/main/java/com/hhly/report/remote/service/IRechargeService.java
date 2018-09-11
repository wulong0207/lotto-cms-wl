package com.hhly.report.remote.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.report.bo.RechargeOverviewBo;
import com.hhly.skeleton.cms.report.bo.RechargeRangeDataVO;
import com.hhly.skeleton.cms.report.bo.RechargeStyleDataBO;
import com.hhly.skeleton.cms.report.bo.SuccessOrFailStatisticsBO;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

public interface IRechargeService {

	/**
	 * 
	 * @desc 充值数据概况
	 * @create 2017年9月12日
	 * @param vo
	 * @return RechargeOverviewBo
	 */
	RechargeOverviewBo findOverview(ReportSearchVO vo);

	/**
	 * 充值人数
	 * 
	 * @param searchVO
	 * @return
	 */
	PagingBO<RechargeRangeDataVO> findRechargePersonList(ReportSearchVO searchVO);
	
	List<RechargeRangeDataVO> findRechargePersonNoPageList(ReportSearchVO searchVO);
	
	/**
	 * 
	 * @desc 充值人数统计
	 * @create 2017年9月14日
	 * @param searchVO
	 * @return RechargeRangeDataVO
	 */
	RechargeRangeDataVO personStatistics(ReportSearchVO searchVO);

	/**
	 * 充值金额
	 * 
	 * @param searchVO
	 * @return
	 */
	PagingBO<RechargeRangeDataVO> findRechargeMoneyList(ReportSearchVO searchVO);
	
	List<RechargeRangeDataVO> findRechargeMoneyNoPageList(ReportSearchVO searchVO);
	
	/**
	 * 充值金额统计
	 * @desc 
	 * @create 2017年9月14日
	 * @param searchVO
	 * @return RechargeRangeDataVO
	 */
	RechargeRangeDataVO moneyStatistics(ReportSearchVO searchVO);

	/**
	 * 充值方式
	 * 
	 * @param searchVO
	 * @return
	 */
	PagingBO<RechargeStyleDataBO> findRechargeStyleList(ReportSearchVO searchVO);
	
	List<RechargeStyleDataBO> findRechargeStyleNoPageList(ReportSearchVO searchVO);
	
	/**
	 * 充值方式 统计
	 * @desc 
	 * @create 2017年9月14日
	 * @param searchVO
	 * @return List<RechargeRangeDataVO>
	 */
	JSONObject styleStatistics(ReportSearchVO searchVO);

	/**
	 * 成功与失败
	 * 
	 * @param searchVO
	 * @return
	 */
	PagingBO<SuccessOrFailStatisticsBO> findRechargeStatusList(ReportSearchVO searchVO);
	
	List<SuccessOrFailStatisticsBO> findRechargeStatusNoPageList(ReportSearchVO searchVO);
	
	/**
	 * 成功与失败统计
	 * @desc 
	 * @create 2017年9月14日
	 * @param searchVO
	 * @return List<Map<String,Object>>
	 */
	JSONObject statusStatistics(ReportSearchVO searchVO); 
	
	/**
	 * 
	 * @desc 充值渠道
	 * @create 2017年9月13日
	 * @return List<DictionaryBO>
	 */
	List<DictionaryBO> getRechargeStyles();
}
