
package com.hhly.cms.sportmgr.service;

import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.sportmgr.bo.SportMatchInfoBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportMatchSourceInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SportMatchInfoVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportMatchSourceInfoVO;

/**
 * @desc    
 * @author  cheng chen
 * @date    2018年1月20日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface MatchInfoService {

    /**
     * 模糊查找赛事信息
     * @param sportMatchVO
     * @return
     */
	List<SportMatchInfoBO> findLikeList(SportMatchInfoVO vo);
	
	/**
	 * 分页查询赛事信息
	 * @param sportMatchInfoVO
	 * @return
	 * @date 2018年1月20日下午2:20:00
	 * @author cheng.chen
	 */
	PagingBO<SportMatchInfoBO> findList(SportMatchInfoVO vo);
	
	/**
	 * 新增赛事信息
	 * @param sportMatchInfoVO
	 * @return
	 * @date 2018年1月20日下午2:20:57
	 * @author cheng.chen
	 */
	int insertMatchInfo(SportMatchInfoVO vo);
	
	/**
	 * 修改赛事信息
	 * @param sportMatchInfoVO
	 * @return
	 * @date 2018年1月20日下午2:20:57
	 * @author cheng.chen
	 */
	int updateMatchInfo(SportMatchInfoVO vo);	
	
	/**
	 * 批量删除赛事信息
	 * @param vo
	 * @return
	 * @date 2018年1月20日下午4:29:38
	 * @author cheng.chen
	 */
	int deleteBatchMatchInfo(StringVO vo);
	
	/**
	 * 查询渠道赛事集合
	 * @param vo
	 * @return
	 * @date 2018年1月24日下午5:55:21
	 * @author cheng.chen
	 */
	PagingBO<SportMatchSourceInfoBO> findList(SportMatchSourceInfoVO vo);
	
	/**
	 * 修改渠道赛事
	 * @param vo
	 * @return
	 * @date 2018年1月24日下午7:05:46
	 * @author cheng.chen
	 */
	int updateMatchSourceInfo(SportMatchSourceInfoVO vo);
	
	/**
	 * 删除绑定
	 * @return
	 * @date 2018年1月26日下午3:38:25
	 * @author cheng.chen
	 */
	int removeBind(Integer id);
	
	/**
	 * 批量删除渠道赛事信息
	 * 
	 * @date 2018年3月22日下午12:28:05
	 * @author cheng.chen
	 */
	void deleteBatchMatchSource(StringVO vo);
}
