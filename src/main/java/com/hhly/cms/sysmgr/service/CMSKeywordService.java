package com.hhly.cms.sysmgr.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.sysmgr.bo.CmsKeywordBO;
import com.hhly.skeleton.cms.sysmgr.vo.CmsKeywordVO;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface CMSKeywordService {
	/**
	 * 
	 * @Desc 批量新增敏感词
	 * @author tangxiaobo
	 * @CreatDate 2017年4月6日 下午6:22:12
	 * @param po
	 * @return
	 */
	int addKeywordList(CmsKeywordVO vo);

	/**
	 * 
	 * @Desc 敏感词分页列表
	 * @author tangxiaobo
	 * @CreatDate 2017年4月6日 下午6:22:12
	 * @param po
	 * @return
	 */
	PagingBO<CmsKeywordBO> findKeyword(CmsKeywordVO vo);

	/**
	 * 
	 * @Desc 批量更新敏感词
	 * @author tangxiaobo
	 * @CreatDate 2017年4月6日 下午6:22:12
	 * @param po
	 * @return
	 */
	int updateKeywordList(List<CmsKeywordVO> list);

	/**
	 * 
	 * @Desc 导出excel敏感词
	 * @author tangxiaobo
	 * @CreatDate 2017年4月6日 下午6:22:12
	 * @param po
	 * @return
	 */
	ByteArrayOutputStream getExcelStream(CmsKeywordVO vo);
}
