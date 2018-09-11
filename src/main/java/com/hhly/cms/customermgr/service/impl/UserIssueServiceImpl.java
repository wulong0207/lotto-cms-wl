package com.hhly.cms.customermgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hhly.cms.customermgr.service.UserIssueService;
import com.hhly.cmscore.cms.remote.service.ICustomerMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.customermgr.bo.UserIssueBO;
import com.hhly.skeleton.cms.customermgr.bo.UserIssueExcelBO;
import com.hhly.skeleton.cms.customermgr.vo.UserIssueVO;

@Service
public class UserIssueServiceImpl implements UserIssueService {

	@Autowired
	ICustomerMgrService iCustomerMgrService;
	
	@Override
	public PagingBO<UserIssueBO> page(UserIssueVO vo) {
		return iCustomerMgrService.pageUserIssue(vo);
	}

	@Override
	public List<UserIssueBO> find(UserIssueVO vo) {
		return iCustomerMgrService.findUserIssue(vo);
	}

	@Override
	public int update(UserIssueVO vo) {
		Assert.notNull(vo.getId(), "======>更新发单用户时id为空，参数为"+vo);
		return iCustomerMgrService.updateUserIssue(vo);
	}

	@Override
	public List<UserIssueExcelBO> excel(UserIssueVO vo) {
		return iCustomerMgrService.excelUserIssue(vo);
	}
}
