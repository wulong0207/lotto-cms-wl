package com.hhly.cms.operatemgr.service;

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

public interface OperateHelpCorrectService {
	
	/**
	 * 查询意见箱集合
	 * @author chen cheng
	 * @create 2017年4月27日
	 * @param vo
	 * @return
	 */
	PagingBO<OperateHelpCorrectBO> findOperateCorrectList(OperateHelpCorrectVO vo);
	
	/**
	 * 修改意见箱信息
	 * @author chen cheng
	 * @create 2017年4月27日 
	 * @param vo
	 * @return
	 */
	int updateOperateCorrect(OperateHelpCorrectVO vo);
}
