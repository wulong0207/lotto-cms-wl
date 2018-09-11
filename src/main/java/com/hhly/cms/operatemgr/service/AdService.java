package com.hhly.cms.operatemgr.service;

import java.util.List;

import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateAdBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateAdMenuBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateAdTypeBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateAdMenuVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateAdTypeVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateAdVO;


/**
 * @desc    广告图管理
 * @author  Tony Wang
 * @date    2017年2月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */

public interface AdService {

	/**
	 * @desc   查询广告列表
	 * @author Tony Wang
	 * @create 2017年2月8日
	 * @param vo
	 * @return  
	 */
	PagingBO<OperateAdBO> list(OperateAdVO vo);

	
	/**
	 * @desc   vo有id则update广告信息，无则insert
	 * @author Tony Wang
	 * @create 2017年2月16日
	 * @param vo
	 * @return  
	 
	int merge(OperateAdVO vo);
	 */

	/**
	 * @desc   更新广告
	 * @author Tony Wang
	 * @create 2017年4月6日
	 * @param vo
	 * @return 
	 */
	int udpate(OperateAdVO vo);


	/**
	 * @desc   添加广告
	 * @author Tony Wang
	 * @create 2017年4月6日
	 * @param vo
	 * @return 
	 */
	int add(OperateAdVO vo);


	/**
	 * @desc   查询广告类型
	 * @author Tony Wang
	 * @create 2017年4月25日
	 * @param vo
	 * @return 
	 */
	List<OperateAdTypeBO> listType(OperateAdTypeVO vo);


	/**
	 * @desc   广告图页面列表
	 * @author Tony Wang
	 * @create 2017年5月10日
	 * @param vo
	 * @return 
	 */
	PagingBO<OperateAdMenuBO> listMenu(OperateAdMenuVO vo);

	/**
	 * @desc   增加或更新广告图页面
	 * @author Tony Wang
	 * @create 2017年5月10日
	 * @param vo
	 * @return 
	 */
	int mergeMenu(OperateAdMenuVO vo);


	/**
	 * @desc   删除广告页面
	 * @author Tony Wang
	 * @create 2017年5月10日
	 * @param vo
	 * @return 
	 */
	int deleteMenu(OperateAdMenuVO vo);


	/**
	 * @desc   查询广告图页面，以数据字典形式返回
	 * @author Tony Wang
	 * @create 2017年5月10日
	 * @param vo
	 * @return 
	 */
	List<DictionaryBO> findMenuAsDic(OperateAdMenuVO vo);


	/**
	 * @desc   查询广告图位置信息，以数据字典形式返回
	 * @author Tony Wang
	 * @create 2017年5月11日
	 * @param vo
	 * @return 
	 */
	List<DictionaryBO> findMenuPositionAsDic(OperateAdMenuVO vo);
}
