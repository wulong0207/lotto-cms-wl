package com.hhly.cms.customermgr.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.sysmgr.bo.CmsUserLogBO;
import com.hhly.skeleton.cms.sysmgr.vo.CmsUserLogVO;

/**
 * @desc    CMS用户操作日志
 * @author  Tony Wang
 * @date    2017年5月12日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface UserLogService {

	/**
	 * @desc   添加操作日志
	 * @author Tony Wang
	 * @create 2017年5月12日
	 * @param vo
	 * @return 
	 */
	int add(CmsUserLogVO vo);

	/**
	 * @desc   查询操作日志列表
	 * @author Tony Wang
	 * @create 2017年5月15日
	 * @param vo
	 * @return 
	 */
	PagingBO<CmsUserLogBO> list(CmsUserLogVO vo);

	/**
	 * @desc   根据条件查询操作日志
	 * @author Tony Wang
	 * @create 2017年5月26日
	 * @param vo
	 * @return 
	 */
	CmsUserLogBO find(CmsUserLogVO vo);
}
