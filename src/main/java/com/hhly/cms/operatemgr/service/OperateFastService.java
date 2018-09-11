package com.hhly.cms.operatemgr.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateFastBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateFastInfoBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateFastInfoVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateFastVO;

/**
 * @desc    彩种运营管理
 * @author  lidecheng
 * @date    2017年2月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface OperateFastService {

	/**
	 * @desc   分页查询彩种运营信息
	 * @author lidecheng
	 * @create 2017年2月17日
	 * @param vo
	 * @return  
	 */
	PagingBO<OperateFastBO> list(OperateFastVO vo);

//	/**
//	 * @desc   vo有id则update彩种运营信息，无则insert
//	 * @author lidecheng
//	 * @create 2017年2月17日
//	 * @param vo
//	 * @return  
//	 */
//	int merge(OperateFastVO vo);

	/**
	 * @desc   增加彩种运营信息
	 * @author lidecheng
	 * @create 2017年2月20日
	 * @param vo
	 * @return
	 */
	int add(OperateFastVO vo);

	/**
	 * @desc   更新彩种运营信息
	 * @author lidecheng
	 * @create 2017年2月20日
	 * @param vo
	 * @return  
	 */
	int update(OperateFastVO vo);

	/**
	 * @desc   分页查询彩种运营详情信息
	 * @author lidecheng
	 * @create 2017年2月21日
	 * @param vo
	 * @return  
	 */
	PagingBO<OperateFastInfoBO> listFastInfo(OperateFastInfoVO vo);

	/**
	 * @desc   查询符合条件的运营方案数量
	 * @author lidecheng
	 * @create 2017年2月22日
	 * @param vo
	 * @return  
	 */
	int count(OperateFastVO vo);

}
