package com.hhly.cms.customermgr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.customermgr.service.UserLogService;
import com.hhly.cmscore.cms.remote.service.ISysMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.sysmgr.bo.CmsUserLogBO;
import com.hhly.skeleton.cms.sysmgr.vo.CmsUserLogVO;

@Service
public class UserLogServiceImpl implements UserLogService {

	@Autowired
    private ISysMgrService iSysMgrService;
	
	@Override
	public int add(CmsUserLogVO vo) {
		return iSysMgrService.addUserLog(vo);
	}

	/**
	 * @desc   查询操作日志列表
	 * @author Tony Wang
	 * @create 2017年5月15日
	 * @param vo
	 * @return 
	 */
	@Override
	public PagingBO<CmsUserLogBO> list(CmsUserLogVO vo) {
		return iSysMgrService.listUserLog(vo);
	}

	/**
	 * @desc   根据条件查询操作日志
	 * @author Tony Wang
	 * @create 2017年5月26日
	 * @param vo
	 * @return 
	 */
	@Override
	public CmsUserLogBO find(CmsUserLogVO vo) {
		return iSysMgrService.findUserLog(vo);
	}

}
