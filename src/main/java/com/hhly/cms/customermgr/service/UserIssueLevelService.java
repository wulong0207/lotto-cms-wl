package com.hhly.cms.customermgr.service;

import com.hhly.skeleton.cms.customermgr.bo.UserIssueLevelBO;
import com.hhly.skeleton.cms.customermgr.vo.UserIssueLevelVO;

/**
 * @desc    发单用户
 * @author  Tony Wang
 * @date    2017年10月13日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface UserIssueLevelService {
	
	/**
	 * @desc   更新发单用户级别
	 * @author Tony Wang
	 * @create 2017年10月13日
	 * @param vo
	 * @return 
	 */
	int merge(UserIssueLevelVO vo);

	/**
	 * @desc   查询1条用户级别记录
	 * @author Tony Wang
	 * @create 2017年10月13日
	 * @param vo
	 * @return 
	 */
	UserIssueLevelBO findOne(UserIssueLevelVO vo);

}
