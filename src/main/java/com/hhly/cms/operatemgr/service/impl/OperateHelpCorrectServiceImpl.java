package com.hhly.cms.operatemgr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.operatemgr.service.OperateHelpCorrectService;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateHelpCorrectBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateHelpCorrectVO;

/**
 * @desc    
 * @author  cheng chen
 * @date    2017年4月25日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class OperateHelpCorrectServiceImpl implements OperateHelpCorrectService {

	@Autowired
	IOperateMgrService operateMgrService;

	@Override
	public PagingBO<OperateHelpCorrectBO> findOperateCorrectList(OperateHelpCorrectVO vo) {
		return operateMgrService.findOperateCorrectList(vo);
	}

	@Override
	public int updateOperateCorrect(OperateHelpCorrectVO vo) {
		return operateMgrService.updateOperateCorrect(vo);
	}
	
	
}
