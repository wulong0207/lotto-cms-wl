package com.hhly.cmscore.cms.remote.service;

import java.util.List;


/**
 * 
 * @author lidecheng
 * @Version 1.0
 * @CreatDate 2016-12-7 上午10:12:17
 * @Desc 运营管理
 */
public interface IRedisMgrService{
	
	/**
	 * 批量删除缓存
	 * @param cacheList
	 * @param flag
	 * @date 2017年8月1日下午5:16:46
	 * @author cheng.chen
	 */
	void delCacheList(List<String> cacheList, Boolean flag);

}
