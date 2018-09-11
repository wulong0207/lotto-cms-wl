package com.hhly.cms.ordermgr.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.recommend.bo.RcmdUserCheckBO;
import com.hhly.skeleton.cms.recommend.vo.RcmdUserCheckVO;

/**
 * @desc    推单用户单管理
 * @author  Tony Wang
 * @date    2017年10月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface OrderRcmdUserService {
	/*************************************************** 推荐人审核 start*****************/
	
	PagingBO<RcmdUserCheckBO> findRcmdUserCheckList(RcmdUserCheckVO vo);
	
	int setStatus(RcmdUserCheckVO vo);

	int addRcmdUser(RcmdUserCheckVO vo);

	/*************************************************** 推荐人审核 end*****************/
}
