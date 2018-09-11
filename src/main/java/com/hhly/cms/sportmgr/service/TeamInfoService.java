
package com.hhly.cms.sportmgr.service;

import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.sportmgr.bo.SportTeamInfoBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportTeamSourceInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SportTeamInfoVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportTeamSourceInfoVO;

/**
 * @desc    
 * @author  cheng chen
 * @date    2018年1月20日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface TeamInfoService {
	
	/**
	 * 查找球队信息
	 * @param sportTeamInfoVO
	 * @return
	 */
	List<SportTeamInfoBO> findLikeList(SportTeamInfoVO vo);
	
	/**
	 * 分页查询球队信息
	 * @param sportMatchInfoVO
	 * @return
	 * @date 2018年1月20日下午2:20:00
	 * @author cheng.chen
	 */
	PagingBO<SportTeamInfoBO> findList(SportTeamInfoVO vo);	
	
	/**
	 * 新增球队信息
	 * @param vo
	 * @return
	 * @date 2018年1月20日下午4:32:53
	 * @author cheng.chen
	 */
	int insertTeamInfo(SportTeamInfoVO vo);
	
	/**
	 * 修改球队信息
	 * @param vo
	 * @return
	 * @date 2018年1月20日下午4:34:18
	 * @author cheng.chen
	 */
	int updateTeamInfo(SportTeamInfoVO vo);
	
	/**
	 * 批量删除球队信息
	 * @param vo
	 * @return
	 * @date 2018年1月20日下午4:29:38
	 * @author cheng.chen
	 */
	int deleteBatchTeamInfo(StringVO vo);
	
	/**
	 * 分页查询球队渠道信息
	 * @param sportMatchInfoVO
	 * @return
	 * @date 2018年1月20日下午2:20:00
	 * @author cheng.chen
	 */
	PagingBO<SportTeamSourceInfoBO> findList(SportTeamSourceInfoVO vo);	
	
	/**
	 * 修改球队渠道信息
	 * @param vo
	 * @return
	 * @date 2018年1月20日下午4:34:18
	 * @author cheng.chen
	 */
	int updateTeamSourceInfo(SportTeamSourceInfoVO vo);	
	
	/**
	 * 删除绑定
	 * @return
	 * @date 2018年1月26日下午3:38:25
	 * @author cheng.chen
	 */
	int removeBind(Integer id);
	
	/**
	 * 批量删除渠道球队信息
	 * 
	 * @date 2018年3月22日下午12:26:58
	 * @author cheng.chen
	 */
	void deleteBatchTeamSource(StringVO vo);
}
