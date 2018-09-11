package com.hhly.cms.burypoint.service;


import com.hhly.cms.burypoint.vo.*;
import com.hhly.skeleton.base.bo.PagingBO;

import java.util.List;

/**
 * @desc    数据埋点服务
 * @author  Tony Wang
 * @date    2017年12月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface BuryPointService {

	/**
	 * @desc   分页显示漏斗
	 * @author Tony Wang
	 * @create 2017年12月14日
	 * @param vo
	 * @return 
	 */
	PagingBO<BpFunnelVO> pageFunnel(BpFunnelVO vo);

	/**
	 * @desc   新增或修改漏斗
	 * @author Tony Wang
	 * @create 2017年12月14日
	 * @param vo
	 * @return 
	 */
	int mergeFunnel(BpFunnelVO vo);

	/**
	 * @desc   分页显示用户数据
	 * @author Tony Wang
	 * @create 2017年12月14日
	 * @param vo
	 * @return 
	 */
	PagingBO<BpUserBaseInfoPO> pageUser(BpUserVO vo);

	/**
	 * @desc   漏斗分析
	 * @author Tony Wang
	 * @create 2017年12月16日
	 * @param vo
	 * @return 
	 */
	String analyzeFunnel(BpFunnelAnalyVO vo);

	/**
	 * @desc   删除埋点
	 * @author Tony Wang
	 * @create 2017年12月16日
	 * @param vo
	 * @return 
	 */
	int deletePoint(BpPointVO vo);

	/**
	 * @desc   查询埋点页面
	 * @author Tony Wang
	 * @create 2017年12月19日
	 * @param vo
	 * @return 
	 */
	List<BpPageVO> findPage(BpPageVO vo);

	/**
	 * @desc   添加埋点页面
	 * @author Tony Wang
	 * @create 2017年12月19日
	 * @param vo
	 * @return 
	 */
	int addPage(BpPageVO vo);

	/**
	 * @desc   添加埋点板块
	 * @author Tony Wang
	 * @create 2017年12月19日
	 * @param vo
	 * @return 
	 */
	int addMode(BpModeVO vo);

	/**
	 * @desc   查询埋点板块
	 * @author Tony Wang
	 * @create 2017年12月20日
	 * @param vo
	 * @return 
	 */
	List<BpModeVO> findMode(BpModeVO vo);

	/**
	 * @desc   添加埋点按钮
	 * @author Tony Wang
	 * @create 2017年12月20日
	 * @param vo
	 * @return 
	 */
	int addButton(BpButtonVO vo);

	/**
	 * @desc   查询埋点按钮
	 * @author Tony Wang
	 * @create 2017年12月20日
	 * @param vo
	 * @return 
	 */
	List<BpButtonVO> findButton(BpButtonVO vo);

	/**
	 * @desc   添加埋点
	 * @author Tony Wang
	 * @create 2017年12月21日
	 * @param vo
	 * @return 
	 */
	int addPoint(BpPointVO vo);

	/**
	 * @desc   根据条件删除埋点按钮
	 * @author Tony Wang
	 * @create 2017年12月21日
	 * @param vo
	 * @return 
	 */
	int deleteButton(BpButtonVO vo);

	/**
	 * @desc   根据条件删除板块按钮
	 * @author Tony Wang
	 * @create 2017年12月21日
	 * @param vo
	 * @return 
	 */
	int deleteMode(BpModeVO vo);

	/**
	 * @desc   根据条件删除埋点页面
	 * @author Tony Wang
	 * @create 2017年12月21日
	 * @param vo
	 * @return 
	 */
	int deletePage(BpPageVO vo);

	/**
	 * @desc   分页显示埋点
	 * @author Tony Wang
	 * @create 2017年12月21日
	 * @param vo
	 * @return 
	 */
	PagingBO<BpPointVO> pagePoint(BpPointVO vo);

	/**
	 * @desc   删除漏斗
	 * @author Tony Wang
	 * @create 2017年12月21日
	 * @param vo
	 * @return 
	 */
	int deleteFunnel(BpFunnelVO vo);

	/**
	 * @desc   查询埋点
	 * @author Tony Wang
	 * @create 2017年12月22日
	 * @param vo
	 * @return 
	 */
	List<BpPointVO> findPoint(BpPointVO vo);

	/**
	 * @desc   查询漏斗
	 * @author Tony Wang
	 * @create 2017年12月26日
	 * @param vo
	 * @return 
	 */
	List<BpFunnelVO> findFunnel(BpFunnelVO vo);

	/**
	 * @desc   查询单个漏斗
	 * @author Tony Wang
	 * @create 2017年12月26日
	 * @param vo
	 * @return 
	 */
	BpFunnelVO findOneFunnel(BpFunnelVO vo);

	/**
	 * @desc   查询符合条件的所有用户id
	 * @author Tony Wang
	 * @create 2017年12月26日
	 * @param vo
	 * @return
	 */
    List<Long> findUserIds(BpFunnelAnalyVO vo);
}
