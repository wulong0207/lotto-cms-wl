package com.hhly.cms.operatemgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.operatemgr.service.RebateService;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateRebateUserBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateRebateUserVO;
@Service
public class RebateServiceImpl implements RebateService {
	@Autowired
	 private IOperateMgrService iOperateMgrService;
	@Override
	public PagingBO<OperateRebateUserBO> findRebateUser(OperateRebateUserVO vo) {
		return iOperateMgrService.findRebateUser(vo);
	}
	@Override
	public int addRebateUser(OperateRebateUserVO vo) {
		return iOperateMgrService.addRebateUser(vo);
	}
	
	/**
	 * 查询大客户最新的信息
	 * @param userId
	 * @return
	 */
	public OperateRebateUserBO findByUser(Integer userId){
		return iOperateMgrService.findByUser(userId);
	}
	@Override
	public List<OperateRebateUserBO> findUserHisList(Integer userId) {
		return iOperateMgrService.findUserHisList(userId);
	}
	
	
}
