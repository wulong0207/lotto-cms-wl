package com.hhly.cms.operatemgr.service;

import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateLotteryBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateLotteryInfoBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateLotteryInfoVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateLotteryVO;

/**
 * @desc    彩种运营管理
 * @author  Tony Wang
 * @date    2017年2月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface OperateLotteryService {

	/**
	 * @desc   分页查询彩种运营信息
	 * @author Tony Wang
	 * @create 2017年2月17日
	 * @param vo
	 * @return  
	 */
	PagingBO<OperateLotteryBO> list(OperateLotteryVO vo);

//	/**
//	 * @desc   vo有id则update彩种运营信息，无则insert
//	 * @author Tony Wang
//	 * @create 2017年2月17日
//	 * @param vo
//	 * @return  
//	 */
//	int merge(OperateLotteryVO vo);

	/**
	 * @desc   增加彩种运营信息
	 * @author Tony Wang
	 * @create 2017年2月20日
	 * @param vo
	 * @return
	 */
	int add(OperateLotteryVO vo);

	/**
	 * @desc   更新彩种运营信息
	 * @author Tony Wang
	 * @create 2017年2月20日
	 * @param vo
	 * @return  
	 */
	int update(OperateLotteryVO vo);

	/**
	 * @desc   查询彩种运营详情信息
	 * @author Tony Wang
	 * @create 2017年2月21日
	 * @param vo
	 * @return  
	 */
	List<OperateLotteryInfoBO> listLotteryInfo(OperateLotteryInfoVO vo);

	/**
	 * @desc   查询符合条件的运营方案数量
	 * @author Tony Wang
	 * @create 2017年2月22日
	 * @param vo
	 * @return  
	 */
	int count(OperateLotteryVO vo);

}
