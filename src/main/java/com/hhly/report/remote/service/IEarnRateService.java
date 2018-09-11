package com.hhly.report.remote.service;

import java.util.List;

import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.report.bo.RateStatuisticsBO;
import com.hhly.skeleton.cms.report.bo.RechargeRangeDataVO;
import com.hhly.skeleton.cms.report.bo.UserDetailBO;
import com.hhly.skeleton.cms.report.vo.ReportSearchVO;

public interface IEarnRateService {

	RateStatuisticsBO getRateStatistics(ReportSearchVO vo);

	List<DictionaryBO> getlotteryTypes();

	PagingBO<UserDetailBO> getRateRankDetail(ReportSearchVO vo);

	List<UserDetailBO> getRateRankList(ReportSearchVO vo);
}
