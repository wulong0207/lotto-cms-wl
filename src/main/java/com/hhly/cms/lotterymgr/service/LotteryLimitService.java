package com.hhly.cms.lotterymgr.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryLimitBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryLimitInfoBO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryLimitInfoVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryLimitVO;

import java.util.List;

/**
 * @desc 限号管理的服务接口
 * @author huangb
 * @date 2017年2月15日
 * @company 益彩网络
 * @version v1.0
 */
public interface LotteryLimitService {

	/**
	 * @desc 查询唯一的限号记录
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitVO
	 *            参数对象
	 * @return 查询唯一的限号记录
	 */
	LotteryLimitBO findSingleLimit(LotteryLimitVO lotteryLimitVO);

	/**
	 * @desc 查询限号分页列表
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitVO
	 *            参数对象
	 * @return 查询限号分页列表
	 */
	PagingBO<LotteryLimitBO> findPagingLimit(LotteryLimitVO lotteryLimitVO);

	/**
	 * @desc 新增限号
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitVO
	 *            参数对象
	 * @return 新增限号
	 */
	int addLimit(LotteryLimitVO lotteryLimitVO);

	/**
	 * @desc 修改限号
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitVO
	 *            参数对象
	 * @return 修改限号
	 */
	int updLimit(LotteryLimitVO lotteryLimitVO);

	/**
	 * @desc 查询唯一的限号内容记录
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitInfoVO
	 *            参数对象
	 * @return 查询唯一的限号内容记录
	 */
	LotteryLimitInfoBO findSingleLimitInfo(LotteryLimitInfoVO lotteryLimitInfoVO);

	/**
	 * @desc 查询限号内容分页列表
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitInfoVO
	 *            参数对象
	 * @return 查询限号内容分页列表
	 */
	PagingBO<LotteryLimitInfoBO> findPagingLimitInfo(LotteryLimitInfoVO lotteryLimitInfoVO);

	/**
	 * @desc 新增限号内容
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitInfoVO
	 *            参数对象
	 * @return 新增限号
	 */
	int addLimitInfo(LotteryLimitInfoVO lotteryLimitInfoVO);

	/**
	 * @desc 修改限号内容
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitInfoVO
	 *            参数对象
	 * @return 修改限号
	 */
	int updLimitInfo(LotteryLimitInfoVO lotteryLimitInfoVO);
	
	/**
	 * @desc 保存限号内容（批量操作：包含新增和修改的动作）
	 * @author huangb
	 * @date 2017年2月16日
	 * @param list 新增或修改的集合列表
	 * @return 保存限号内容（批量操作：包含新增和修改的动作）
	 */
	int saveLimitInfo(List<LotteryLimitInfoVO> list);
}
