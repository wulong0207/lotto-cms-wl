package com.hhly.cms.customermgr.service;

import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.customermgr.bo.UserIssueBO;
import com.hhly.skeleton.cms.customermgr.bo.UserIssueExcelBO;
import com.hhly.skeleton.cms.customermgr.vo.UserIssueVO;

/**
 * @desc    发单用户
 * @author  Tony Wang
 * @date    2017年10月13日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface UserIssueService {
	/**
	 * @desc   分页查询发单用户
	 * @author Tony Wang
	 * @create 2017年10月13日
	 * @param vo
	 * @return 
	 */
	PagingBO<UserIssueBO> page(UserIssueVO vo);
	
	/**
	 * @desc   查询发单用户
	 * @author Tony Wang
	 * @create 2017年10月13日
	 * @param vo
	 * @return 
	 */
	List<UserIssueBO> find(UserIssueVO vo);

	/**
	 * @desc   更新发单用户
	 * @author Tony Wang
	 * @create 2017年10月13日
	 * @param vo
	 * @return 
	 */
	int update(UserIssueVO vo);

	List<UserIssueExcelBO> excel(UserIssueVO vo);
}
