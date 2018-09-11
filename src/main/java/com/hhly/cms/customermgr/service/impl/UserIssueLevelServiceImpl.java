package com.hhly.cms.customermgr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.customermgr.service.UserIssueLevelService;
import com.hhly.cmscore.cms.remote.service.ICustomerMgrService;
import com.hhly.skeleton.cms.customermgr.bo.UserIssueLevelBO;
import com.hhly.skeleton.cms.customermgr.vo.UserIssueLevelVO;

@Service
public class UserIssueLevelServiceImpl implements UserIssueLevelService {

	@Autowired
	ICustomerMgrService iCustomerMgrService;

	@Override
	public int merge(UserIssueLevelVO vo) {
		return iCustomerMgrService.mergeUserIssueLevel(vo);
	}

	@Override
	public UserIssueLevelBO findOne(UserIssueLevelVO vo) {
		return iCustomerMgrService.findOneUserIssueLevel(vo);
	}
}
