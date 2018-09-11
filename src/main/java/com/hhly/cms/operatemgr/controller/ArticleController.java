package com.hhly.cms.operatemgr.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.operatemgr.service.OperateArticleService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.cache.operate.OperateCacheEnum.ArticleCacheEnum;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.operatemgr.bo.OperateArticleBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateArticleTypeBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateArticleTypeVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateArticleVO;

/**
 * @desc    文章运营管理
 * @author  Tony Wang
 * @date    2017年2月28日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value="/operatemgr/article")
public class ArticleController extends BaseController { 
	
	@Autowired	
	private OperateArticleService operateArticleService;
    
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "operatemgr/article";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<OperateArticleBO> list(OperateArticleVO vo){
		return operateArticleService.listArticle(vo);
	}
	
	@RequestMapping(value = "/{articleId}",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public OperateArticleBO findSingleArticle(@PathVariable long articleId){
		return operateArticleService.findSingle(new OperateArticleVO(articleId));
	}
	
	@RequestMapping(value = "/articles")
	@Authority(privilege=AuthEnum.SEARCH)
	public String toArticleList(){
		return "operatemgr/article_list";
	}
	
	@RequestMapping(value = "/article")
	@Authority(privilege={AuthEnum.SEARCH})
	public  String toArticleDetail(){
		return "operatemgr/article_detail";
	}
	
	@RequestMapping(value = "/add", method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = ArticleCacheEnum.class)
	public ResultBO<?> addArticle(@RequestBody OperateArticleVO vo, HttpSession session) throws IOException{
		vo.setCreatorId(Long.parseLong(session.getAttribute("userId").toString()));
		vo.setCreateBy(getUserCName(session));
		vo.setModifyBy(getUserCName(session));
		int num = operateArticleService.addArticle(vo);
		return getSaveResult(num);
	}
	
	
	@RequestMapping(value = "/update", method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = ArticleCacheEnum.class)
	public ResultBO<?> updateArticle(@RequestBody OperateArticleVO vo, HttpSession session) throws IOException{
		vo.setModifyBy(getUserCName(session));
		int num = operateArticleService.updateArticle(vo);
		return getSaveResult(num);
	}
	
	/**
	 * @desc   修改文章状态 驳回 审核通过 发布
	 * @author Tony Wang
	 * @create 2017年4月25日
	 * @param vo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/status", method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = ArticleCacheEnum.class)
	public ResultBO<?> updateArticleStatus(OperateArticleVO vo, HttpSession session){
		// 设置操作人员昵称
		vo.setOperator(getUserCName(session));
		int num = operateArticleService.updateArticleStatus(vo);
		return getSaveResult(num);
	}
	
	@RequestMapping(value = "/type", method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public List<OperateArticleTypeBO> listArticleType(){
		return operateArticleService.listArticleType();
	}
	
	
	@RequestMapping(value = "/type", method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ParameterValid
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = ArticleCacheEnum.class)
	public ResultBO<?> addArticleType(@RequestBody@Valid(GroupValue.ADD) OperateArticleTypeVO vo, HttpSession session){
		Assert.isNull(vo.getId(), "文章栏目已存在");
		int num = operateArticleService.mergeArticleType(vo);
		return getSaveResult(num);
	}
	
	@RequestMapping(value = "/type", method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ParameterValid
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = ArticleCacheEnum.class)
	public ResultBO<?> updateArticleType(@RequestBody@Valid(GroupValue.UPD) OperateArticleTypeVO vo, HttpSession session){
		int num = operateArticleService.mergeArticleType(vo);
		return getSaveResult(num);
	}

	/**
	 * @desc   更新文章栏目顺序
	 * @author Tony Wang
	 * @create 2017年4月17日
	 * @param vo
	 * @param session
	 * @return 
	 */
	@RequestMapping(value = "/type/order", method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = ArticleCacheEnum.class)
	public ResultBO<?> updateArticleTypeOrder(@RequestBody List<OperateArticleTypeVO> vos){
		int num = operateArticleService.updateArticleTypeOrder(vos);
		return getSaveResult(num);
	}
	
	/**
	 * @desc   重新生成文章
	 * @author Tony Wang
	 * @create 2017年4月17日
	 * @param vo
	 * @return 
	 */
	@RequestMapping(value = "/reset", method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.RESET_ARTICLE)
	@ResponseBody
	public ResultBO<?> reset(OperateArticleVO vo){
		return ResultBO.ok(operateArticleService.resetArticle(vo));
	}
}
