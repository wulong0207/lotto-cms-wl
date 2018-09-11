package com.hhly.cms.operatemgr.service;

import java.io.IOException;
import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateArticleBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateArticleTypeBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateArticleTypeVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateArticleVO;

/**
 * @desc    文章管理
 * @author  Tony Wang
 * @date    2017年3月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface OperateArticleService {

	/**
	 * @desc   查询文章栏目名称
	 * @author Tony Wang
	 * @create 2017年3月1日
	 * @return  
	 */
	List<OperateArticleTypeBO> listArticleType();

	/**
	 * @desc   查询文章
	 * @author Tony Wang
	 * @create 2017年3月1日
	 * @param vo
	 * @return  
	 */
	PagingBO<OperateArticleBO> listArticle(OperateArticleVO vo);

	/**
	 * @desc   增加或更新文章栏目
	 * @author Tony Wang
	 * @create 2017年3月2日
	 * @param vo
	 * @return  
	 */
	int mergeArticleType(OperateArticleTypeVO vo);

	/**
	 * @desc   增加或更新文章
	 * @author Tony Wang
	 * @create 2017年3月8日
	 * @param vo
	 * @return 
	 
	int mergeArticle(OperateArticleVO vo);
	 */
	
	/**
	 * @desc   增加文章
	 * @author Tony Wang
	 * @create 2017年3月17日
	 * @param vo
	 * @return 
	 * @throws IOException 
	 */
	int addArticle(OperateArticleVO vo) throws IOException;

	/**
	 * @desc   查询单篇文章
	 * @author Tony Wang
	 * @create 2017年3月21日
	 * @param operateArticleVO
	 * @return 
	 */
	OperateArticleBO findSingle(OperateArticleVO vo);

	/**
	 * @desc   更新文章
	 * @author Tony Wang
	 * @create 2017年4月12日
	 * @param vo
	 * @return 
	 * @throws IOException 
	 */
	int updateArticle(OperateArticleVO vo) throws IOException;

	/**
	 * @desc   修改文章状态
	 * @author Tony Wang
	 * @create 2017年4月14日
	 * @param vo
	 * @return 
	 */
	int updateArticleStatus(OperateArticleVO vo);

	/**
	 * @desc   更新文章栏目顺序
	 * @author Tony Wang
	 * @create 2017年4月17日
	 * @param vos
	 * @return 
	 */
	int updateArticleTypeOrder(List<OperateArticleTypeVO> vos);

	/**
	 * @desc   重新生成文章
	 * @author Tony Wang
	 * @create 2017年9月27日
	 * @param vo
	 * @return 
	 */
	int[] resetArticle(OperateArticleVO vo);
	/**
	 * 更新rss
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年2月27日 上午10:16:02
	 */
	void updateArticleRss();
}
