package com.hhly.cmscore.cms.remote.service;

import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.LotteryCacheBO;
import com.hhly.skeleton.cms.operatemgr.vo.CmsCacheVO;
import com.hhly.skeleton.cms.operatemgr.vo.LotteryCacheVO;
import com.hhly.skeleton.lotto.base.sport.bo.SPMessageBO;

/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-12-5 下午3:59:11
 * @Desc 会员管理
 */
public interface ICmsCacheService {
	
	/**
	 * 分页查询
	 * @param vo
	 * @return
	 */
	PagingBO<LotteryCacheBO> findCacheListByPage(LotteryCacheVO vo);

	/**
	 * 添加缓存
	 * @param po
	 * @return
	 */
	int insertCache(LotteryCacheVO vo);

	/**
	 *删除缓存
	 * @param ids
	 * @return
	 */
	int delCacheById(short id);
	
	/**
	 * 查询缓存
	 * @param vo
	 * @return
	 */
	List<LotteryCacheBO> findCache(LotteryCacheVO vo);
	
	
	/**
	 * 修改缓存
	 * @param po
	 * @return
	 */
	int updateCache(LotteryCacheVO vo);
	
	/**
	 * 清除缓存
	 * @param cacheKey
	 */
	void clearCache(String cacheKey);
	
	List<SPMessageBO> findMessage(Integer min,Integer max);
	
	int findCount();
}
