package com.hhly.cms.lotterymgr.service;

import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryIssueCmsBO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryIssueCmsVO;

import java.io.ByteArrayOutputStream;
import java.util.List;


/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-11-17 下午5:45:02
 * @Desc 彩种管理
 */
public interface LotteryIssueService {

	List<DictionaryBO> findIssueCode(LotteryIssueCmsVO vo);

	PagingBO<LotteryIssueCmsBO> findLotteryIssue(LotteryIssueCmsVO vo);

	int updLotteryIssue(LotteryIssueCmsVO vo);

	int addLotteryIssue(LotteryIssueCmsVO vo);

	ByteArrayOutputStream getLotteryIssueExcel(LotteryIssueCmsVO vo);

	LotteryIssueCmsBO findLotteryIssueDetail(StringVO vo);
	
	/**
	 * 通过彩种和当前期状态查询
	 * @param lotteryCode 彩种信息
	 * @param currentIssue 0:非当前期,1:当前期
	 * @return
	 * @date 2017年5月15日下午4:36:00
	 * @author cheng.chen
	 */
	LotteryIssueCmsBO findIssue(Integer lotteryCode, Short currentIssue);
	
    /**
     * 审核开奖号码
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年3月13日 上午11:39:39
     * @param id
     * @return
     */
	int updateAuditDrawCode(LotteryIssueCmsVO vo);
	/**
	 * 查询当前期+历史期
	 * @param vo
	 * @author wul687 2018-08-15
	 * @return
	 */
	public List<DictionaryBO> cutOffHistoryIssue(LotteryIssueCmsVO vo) ;

}
