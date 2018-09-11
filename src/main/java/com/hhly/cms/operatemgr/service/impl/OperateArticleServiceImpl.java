package com.hhly.cms.operatemgr.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.hhly.cms.base.service.FileProxy;
import com.hhly.cms.operatemgr.service.OperateArticleService;
import com.hhly.cms.operatemgr.service.rss.Channel;
import com.hhly.cms.operatemgr.service.rss.Item;
import com.hhly.cms.operatemgr.service.rss.Rss;
import com.hhly.cms.operatemgr.service.rss.Source;
import com.hhly.cms.sysmgr.service.CMSUserService;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.common.ArticleEnum;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.exception.ServiceRuntimeException;
import com.hhly.skeleton.base.qiniu.QiniuUploadResultVO;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.cms.operatemgr.bo.OperateArticleBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateArticleTypeBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateArticleTopVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateArticleTypeVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateArticleVO;
import com.hhly.skeleton.cms.sysmgr.bo.CMSUserBO;
import com.hhly.skeleton.cms.sysmgr.vo.CMSUserVO;

/**
 * @desc 文章管理
 * @author Tony Wang
 * @date 2017年3月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class OperateArticleServiceImpl implements OperateArticleService {

	@Autowired
	private IOperateMgrService iOperateMgrService;

	@Autowired
	private CMSUserService cmsUserService;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	protected FileProxy uploadFile;

	/**
	 * 当前文章编号
	 */
	private static AtomicInteger articelIdCounter;

	/**
	 * 当前日，用于判断日期是否有变更
	 */
	private static int currentDay4Articel;

	private final Lock articelIdLock = new ReentrantLock();

	private static Logger logger = Logger.getLogger(OperateArticleServiceImpl.class);

	static {
		currentDay4Articel = Calendar.getInstance().get(Calendar.DATE);
	}

	/**
	 * @desc 查询文章栏目名称
	 * @author Tony Wang
	 * @create 2017年3月1日
	 * @return
	 */
	@Override
	public List<OperateArticleTypeBO> listArticleType() {
		return iOperateMgrService.listArticleType();
	}

	/**
	 * @desc 查询文章
	 * @author Tony Wang
	 * @create 2017年3月1日
	 * @param vo
	 * @return
	 */
	@Override
	public PagingBO<OperateArticleBO> listArticle(OperateArticleVO vo) {
		String createBy = vo.getCreateBy();
		// 如果查询条件有"用户昵称",则到cms_user表中查询出用户对应的id
		if (StringUtils.isNotBlank(createBy)) {
			CMSUserVO criteria = new CMSUserVO();
			criteria.setUserCname(vo.getCreateBy());
			List<CMSUserBO> users = cmsUserService.findUsers(criteria);
			Assert.isTrue(CollectionUtils.isNotEmpty(users) && users.size() == 1, "用户昵称不存在或用户昵称有重复值");
			vo.setCreatorId(users.get(0).getUserId().longValue());
		}
		return iOperateMgrService.listArticle(vo);
	}

	@Override
	public int mergeArticleType(OperateArticleTypeVO vo) {
		int num = iOperateMgrService.mergeArticleType(vo);
		if(num > 0){
			try {
				updateArticleRss();
			} catch (Exception e) {
				logger.error("更新RSS异常:"+e.getMessage(),e);
			}
		}
		return num;
	}
	
	

	/**
	 * @desc 增加文章
	 * @author Tony Wang
	 * @create 2017年3月17日
	 * @param vo
	 * @return
	 */
	@Override
	public int addArticle(OperateArticleVO vo) throws IOException {
		// Cms负责生成html，core负责插入表记录
		// TODO 每篇文章的置顶标题最多24个中文字
		Assert.isNull(vo.getId(), "文章已存在");
		validateArticle(vo);
		// 以路径和aritcleId生成完整的文章本地url,如 index/highlottery/sd11x5/xxxx.html;
		List<String> typeParents = iOperateMgrService.findArticleParentPaths(vo);
		Assert.notEmpty(typeParents, "文章的所有父栏目为空");
		StringBuilder articleParentDir = new StringBuilder();
		// 根据文章的栏目查询出所有父栏目标识符,拼出文章完整的保存路径
		for (String typeParent : typeParents) {
			articleParentDir.append(typeParent).append(SymbolConstants.OBLIQUE_LINE);
		}
		// 保存相对的路径，如_article/web/news/xxx.html
		vo.setArticleId(nextArticleId());
		String articleBaseDir = "_article";
		String articleUrl = new StringBuilder(articleBaseDir).append(SymbolConstants.OBLIQUE_LINE).append(articleParentDir).append(vo.getArticleId()).append(".html").toString();
		vo.setArticleUrl(articleUrl);
		saveArticleAsHtml(vo);
		int num =  iOperateMgrService.addArticle(vo);
		if(num > 0){
			try {
				updateArticleRss();
			} catch (Exception e) {
				logger.error("更新RSS异常:"+e.getMessage(),e);
			}
		}
		return num;
	}

	/**
	 * @desc 更新文章
	 * @author Tony Wang
	 * @create 2017年4月12日
	 * @param vo
	 * @return
	 */
	@Override
	public int updateArticle(OperateArticleVO vo) throws IOException {
		Assert.notNull(vo.getId(), "文章id为空");
		// "待审核"的文章都不能编辑
		Assert.isTrue(ArticleEnum.ArticleStatus.isEditable(vo.getStatus()), "待审核的文章都不能编辑");
		validateArticle(vo);
		String url = vo.getArticleUrl();
		logger.info("更新文章路径名称："+url);
		//先删除后修改
		uploadFile.deleteFile(url.substring(0, url.indexOf(".html")+".html".length()));
		
		List<QiniuUploadResultVO> list = saveArticleAsHtml(vo);
		if (!ObjectUtil.isBlank(list)) {
			QiniuUploadResultVO qiniuUploadResultVO = list.get(0);
			vo.setArticleUrl(qiniuUploadResultVO.getFileName());
		}
		int num = iOperateMgrService.updateArticle(vo);
		if(num > 0){
			try {
				updateArticleRss();
			} catch (Exception e) {
				logger.error("更新RSS异常:"+e.getMessage(),e);
			}
		}
		return num;
	}

	private List<QiniuUploadResultVO> saveArticleAsHtml(OperateArticleVO vo) throws IOException {
		// 生成文章html文件
		Assert.notNull(vo.getArticleUrl());
		Map<String, Object> variables = new HashMap<>();
		variables.put("articleTitle", StringUtil.convertObjToStr(vo.getArticleTitle()));
		variables.put("releaseTime", vo.getReleaseTime() == null ? "" : DateUtil.convertDateToStr(vo.getReleaseTime()));
		variables.put("articleContent", vo.getArticleContent());
		variables.put("articleLabel", StringUtil.convertObjToStr(vo.getArticleLabel()));
		variables.put("articleFrom", StringUtil.convertObjToStr(vo.getArticleFrom()));
		variables.put("fromUrl", StringUtil.convertObjToStr(vo.getFromUrl()));
		variables.put("createBy", StringUtil.convertObjToStr(vo.getCreateBy()));
		// variables.put("click", vo.getClick() == null ? 0 : vo.getClick());
		variables.put("articleId", vo.getArticleId());
		final Context context = new Context(Locale.CHINA);
		context.setVariables(variables);
		final String articleHtml = templateEngine.process("article_template", context);
		return uploadFile.uploadFileStream(articleHtml.getBytes(), vo.getArticleUrl());
	}

	private void validateArticle(OperateArticleVO vo) {
		// 至少选择一个平台
		boolean show = !(Objects.equals(vo.getWap(), (byte) 0) && Objects.equals(vo.getWeb(), (byte) 0) && Objects.equals(vo.getAndroid(), (byte) 0) && Objects.equals(vo.getIos(), (byte) 0));
		Assert.isTrue(show, "请至少选择一个文章显示平台");
		if (CollectionUtils.isNotEmpty(vo.getArticleTops())) {
			// 如果文章有推送栏目，不能重复推送同一个栏目
			Long fisrtTypeId = -1L;
			for (OperateArticleTopVO top : vo.getArticleTops()) {
				Assert.isTrue(!Objects.equals(top.getTypeId(), fisrtTypeId), "不能重复推送同一个栏目");
				fisrtTypeId = top.getTypeId();
				// 如果推送(不是置顶)到本栏目，则可以不用设置上、下线时间
				if (!(Objects.equals(vo.getTypeId(), top.getTypeId()) && top.getIsTop() == 0)) {
					Assert.notNull(top.getUptime(), "上线时间不能为空");
					Assert.notNull(top.getDowntime(), "下线时间不能为空");
				}
			}
		}
		// 验证文章所属栏目不能为虚拟栏目
		OperateArticleTypeVO criteria = new OperateArticleTypeVO();
		criteria.setId(vo.getTypeId());
		OperateArticleTypeBO articleType = iOperateMgrService.findArticleType(criteria);
		Assert.notNull(articleType, "文章栏目为空");
		Assert.isTrue(Objects.equals(articleType.getVirtual(), (byte) 0), "虚拟栏目不能添加文章");
	}

	/**
	 * @desc   生成文章编号
	 * @author Tony Wang
	 * @create 2017年4月27日
	 * @return 
	 */
	private String nextArticleId() {
		/* 
		 * 文章编号生成规则:年 月 日 +五位(13位)递增，防止刷文章，再加上随机字符串(7位)如：
		 * 2016072100001asdfadf 2016072100002hjgjkgkjghjk
		 * 文章编号不能递增
		 * UUID.randomUUID().toString().replaceAll("-", "");
		 * 
		 * 新增文章时，文章作者是保存cms用户的昵称
		 */
		// 检查currentArticelId是否已初始化
		String today = DateUtil.convertDateToStr(new Date(), DateUtil.DATE_FORMAT_NO_LINE);
		int nextId;
		articelIdLock.lock();
		try {
			if (articelIdCounter == null) {
				String maxArticleId = iOperateMgrService.findMaxArticleId(today);
				if (StringUtils.isBlank(maxArticleId)) {
					// 如果没有当天的文章编号，则从0开始
					articelIdCounter = new AtomicInteger();
				} else {
					// 从2016072100001asdfadf截取出00001
					articelIdCounter = new AtomicInteger(Integer.parseInt(maxArticleId.substring(8, 13)));
				}
			}
			// 判断日期是否有变更，有则currentArticelId归0，无则加1
			int newDay = Calendar.getInstance().get(Calendar.DATE);
			if (newDay == currentDay4Articel) {
				nextId = articelIdCounter.incrementAndGet();
				Assert.isTrue(nextId < 100000, "当天增加的文章已超过100000篇");
			} else {
				articelIdCounter.set(0);
				nextId = articelIdCounter.incrementAndGet();
				// 更新当前日期
				currentDay4Articel = newDay;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			articelIdLock.unlock();
		}
		StringBuilder nextArticleId = new StringBuilder(today).append(String.format("%05d", nextId)).append(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 7));
		return nextArticleId.toString();
	}

	/**
	 * @desc 查询单篇文章
	 * @author Tony Wang
	 * @create 2017年3月21日
	 * @param operateArticleVO
	 * @return
	 */
	@Override
	public OperateArticleBO findSingle(OperateArticleVO vo) {
		return iOperateMgrService.findSingleArticle(vo);
	}

	/**
	 * @desc 修改文章状态
	 * @author Tony Wang
	 * @create 2017年4月14日
	 * @param vo
	 * @return
	 */
	@Override
	public int updateArticleStatus(OperateArticleVO vo) {
		return iOperateMgrService.updateArticleStatus(vo);
	}

	/**
	 * @desc 更新文章栏目顺序
	 * @author Tony Wang
	 * @create 2017年4月17日
	 * @param vos
	 * @return
	 */
	@Override
	public int updateArticleTypeOrder(List<OperateArticleTypeVO> vos) {
		return iOperateMgrService.updateArticleTypeOrder(vos);
	}

	@Override
	public int[] resetArticle(OperateArticleVO vo) {
		// 七牛自动刷新的文件每天至多500个
		vo.setShwoArticleContent(true);
		List<OperateArticleBO> list = iOperateMgrService.findArticle(vo);
		int total = 0;
		AtomicInteger success = new AtomicInteger(0), fail = new AtomicInteger(0);
		if (CollectionUtils.isNotEmpty(list)) {
			total = list.size();
            Comparator<OperateArticleBO> byStatus = (a1,a2)-> Integer.compare(a2.getStatus(),a1.getStatus());
            Comparator<OperateArticleBO> byReleaseTime = (a1,a2)-> a1.getReleaseTime().after(a2.getReleaseTime()) ? 1:(a1.getReleaseTime().before(a2.getReleaseTime()) ? -1 : 0);
            list.sort(byStatus.thenComparing(byReleaseTime).thenComparing((a1,a2)->Long.compare(a2.getId(),a1.getId())));
            list.parallelStream().forEach(article->{
                // 生成文章html文件
                Map<String, Object> variables = new HashMap<>();
                variables.put("articleTitle", StringUtil.convertObjToStr(article.getArticleTitle()));
                variables.put("releaseTime", article.getReleaseTime() == null ? "" : DateUtil.convertDateToStr(article.getReleaseTime()));
                variables.put("articleContent", article.getArticleContent());
                variables.put("articleLabel", StringUtil.convertObjToStr(article.getArticleLabel()));
                variables.put("articleFrom", StringUtil.convertObjToStr(article.getArticleFrom()));
                variables.put("fromUrl", StringUtil.convertObjToStr(article.getFromUrl()));
                variables.put("createBy", StringUtil.convertObjToStr(article.getCreateBy()));
                variables.put("click", vo.getClick() == null ? "" : vo.getClick());
                variables.put("articleId", article.getArticleId());
                final Context context = new Context(Locale.CHINA);
                context.setVariables(variables);
                final String articleHtml = templateEngine.process("article_template", context);
                try {
                    uploadFile.uploadFileStream(articleHtml.getBytes(), article.getArticleUrl());
                    logger.info(String.format("****************>>>>>>>重新生成文章成功,文章id:%d", article.getId()));
                    success.getAndIncrement();
                } catch (Exception e) {
                    e.printStackTrace();
                    fail.getAndIncrement();
                    logger.error(String.format("****************>>>>>>>重新生成文章失败,文章id:%d", article.getId()));
                }
            });
		}
		return new int[] { total, success.get(), fail.get() };
	}

	@Override
	public void updateArticleRss() {
		List<OperateArticleBO> list = iOperateMgrService.listArticleRss();
		if(list.isEmpty()){
			return;
		}
		Rss rss  = new Rss();
		Channel channel = new Channel();
		rss.setChannel(channel);
		List<Item> items = new ArrayList<>();
		channel.setItems(items);
		for (OperateArticleBO bo : list) {
			Item item = new Item();
			items.add(item);
			item.setAuthor("2N彩票");
			item.setDescription(bo.getArticleContent());
			item.setLink(uploadFile.getUrl()+ bo.getArticleUrl());
			item.setPubDate(DateUtil.timeZone(bo.getReleaseTime()));
			item.setTitle(bo.getArticleTitle());
			item.setSource(new Source(bo.getFromUrl(),bo.getArticleFrom()));
		}
		MockMultipartFile multipartFile = new MockMultipartFile("meitihezuo.xml",rss.toXml().getBytes()); 
		try {
			//先删除后修改
			uploadFile.deleteFile("meitihezuo.xml");
			uploadFile.uploadFile(multipartFile,"meitihezuo.xml");
		} catch (IllegalStateException | IOException e) {
			logger.error("rss更新失败", e);
			throw new ServiceRuntimeException("rss 更新失败"); 
		}
	}
	
	public static void main(String[] args) {
		String url = "dafsd/dfasdf/ddf.html?v=45646";
		System.out.println(url.substring(0, url.indexOf(".html")+".html".length()));
	}
	
}
