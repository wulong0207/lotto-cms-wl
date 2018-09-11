package com.hhly.cms.dicmgr.service;

import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.dicmgr.bo.DicDataBO;
import com.hhly.skeleton.cms.dicmgr.bo.DicDataDetailBO;
import com.hhly.skeleton.cms.dicmgr.vo.DicDataDetailVO;
import com.hhly.skeleton.cms.dicmgr.vo.DicDataVO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-11-14 上午11:56:44
 * @Desc 系统字典管理
 */
public interface DictionaryService {

	PagingBO<DicDataBO> find(DicDataVO vo);

	int add(DicDataVO vo);

	int update(DicDataVO vo);

	List<DicDataDetailBO> findDetail(StringVO vo);

	PagingBO<DicDataDetailBO> findDetails(DicDataDetailVO vo);

	int addDetail(DicDataDetailVO vo);

	int updDetail(DicDataDetailVO vo);

	int delDetail(StringVO vo);
	/**
	 * 一次性查询多个字典集合
	 * @param vo 多个用“,”隔开
	 * @return
	 */
	Map<String,List<DictionaryBO>> findDictionary(StringVO vo);
	/**
	 * 查询字典
	 * @param dicCode
	 * @return
	 */
	Map<String,Map<String,String>> findDictionaryMap(Collection<String> dicCode);
	
	/**
	 * 
	 * @Description 推单分析师级别
	 * @author HouXiangBao289
	 * @return
	 */
	List<DictionaryBO> findRcmdUserLevel();
}
