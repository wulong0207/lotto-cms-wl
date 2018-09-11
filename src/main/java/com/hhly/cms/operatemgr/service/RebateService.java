package com.hhly.cms.operatemgr.service;

import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateRebateUserBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateRebateUserVO;

public interface RebateService {
	/**
	 * 查询大客户信息
	 * @param vo
	 * @return
	 */
	PagingBO<OperateRebateUserBO> findRebateUser(OperateRebateUserVO vo);
	/**
	 * 新增大客户信息
	 * @param vo
	 * @return
	 */
	int addRebateUser(OperateRebateUserVO vo);
	
	/**
	 * 查询大客户最新一期的信息
	 * @param userId
	 * @return
	 */
	OperateRebateUserBO findByUser(Integer userId);
	/**
	 * 查询大客户历史数据
	 * @param userId
	 * @return
	 */
	List<OperateRebateUserBO> findUserHisList(Integer userId);
}
