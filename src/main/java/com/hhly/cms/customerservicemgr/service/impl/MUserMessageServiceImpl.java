package com.hhly.cms.customerservicemgr.service.impl;

import com.hhly.cms.customerservicemgr.service.MUserMessageService;
import com.hhly.cmscore.cms.remote.service.ICustomerServiceMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.customerservice.bo.MUserMessageBO;
import com.hhly.skeleton.cms.customerservice.vo.MUserMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class MUserMessageServiceImpl implements MUserMessageService {

	@Autowired
	private ICustomerServiceMgrService iCustomerServiceMgrService;
	
	@Override
	public PagingBO<MUserMessageBO> findMUserMeaasge(MUserMessageVO vo) {
		return iCustomerServiceMgrService.findMUserMeaasge(vo);
	}

	@Override
	public void updateStatus(Integer id, Integer sendStatus) {
		iCustomerServiceMgrService.updateStatus(id, sendStatus);
	}

}
