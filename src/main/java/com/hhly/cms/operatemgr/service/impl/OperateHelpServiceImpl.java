package com.hhly.cms.operatemgr.service.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hhly.cms.base.service.FileProxy;
import com.hhly.cms.operatemgr.service.OperateHelpService;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.common.OperateEnum;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.qiniu.QiniuUploadResultVO;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.cms.operatemgr.bo.OperateHelpBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateHelpTypeBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateHelpTopVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateHelpTypeVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateHelpVO;

/**
 * @desc    帮助中心
 * @author  Tony Wang
 * @date    2017年4月28日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class OperateHelpServiceImpl implements OperateHelpService {

	@Autowired
	private IOperateMgrService iOperateMgrService;
	
//	@Autowired
//	private TemplateEngine templateEngine;
	
	@Autowired
	protected FileProxy uploadFile;
	/**
	 * 当前帮助文章编号
	 */
	private static AtomicInteger helpCodeCounter;
	
	/**
	 * 当前日，用于判断日期是否有变更
	 */
	private static int currentDay4Help;
	
	private final Lock helpCodeLock = new ReentrantLock();
	
	static {
		currentDay4Help = Calendar.getInstance().get(Calendar.DATE);
	}
	
	@Override
	public List<OperateHelpTypeBO> listHelpType() {
		return iOperateMgrService.listHelpType();
	}

	@Override
	public PagingBO<OperateHelpBO> listHelp(OperateHelpVO vo) {
		return iOperateMgrService.listHelp(vo);
	}

	@Override
	public int mergeHelpType(OperateHelpTypeVO vo) {
		return iOperateMgrService.mergeHelpType(vo);
	}

	@Override
	public int addHelp(OperateHelpVO vo) throws IOException {
		// Cms负责生成html，core负责插入表记录
		Assert.isNull(vo.getId(), "文章已存在");
		validateHelp(vo);
		// 以路径和aritcleId生成完整的文章本地url,如  index/highlottery/sd11x5/xxxx.html;
		List<String> parents = iOperateMgrService.findHelpParentPaths(vo);
		Assert.notEmpty(parents, "文章的所有父栏目为空");
		StringBuilder helpParentDir = new StringBuilder();
		for(String parent : parents) {
			helpParentDir.append(parent).append(SymbolConstants.OBLIQUE_LINE);
		}
		// 保存相对路径，_help/web/news/xxx.html
		vo.setCode(nextHelpCode());
		String helpBaseDir = "_help";
		String helpUrl = new StringBuilder(helpBaseDir)
				.append(SymbolConstants.OBLIQUE_LINE)
				.append(helpParentDir)
				.append(vo.getCode())
				.append(".html")
				.toString();
		vo.setHelpUrl(helpUrl);
	    //saveHelpAsHtml(vo);
		return iOperateMgrService.addHelp(vo);
	}

//	private List<QiniuUploadResultVO> saveHelpAsHtml(OperateHelpVO vo) throws IOException {
//	    // 生成文章html文件
//    	Assert.notNull(vo.getHelpUrl());
//    	Map<String, Object> variables = new HashMap<>();
//		variables.put("title", StringUtil.convertObjToStr(vo.getTitle()));
//		variables.put("releaseTime", vo.getReleaseTime() == null ? "" : DateUtil.convertDateToStr(vo.getReleaseTime()));
//		variables.put("content", vo.getContent());
//		variables.put("label", StringUtil.convertObjToStr(vo.getLabel()));
//		variables.put("sources", StringUtil.convertObjToStr(vo.getSources()));
//		variables.put("url", StringUtil.convertObjToStr(vo.getUrl()));
//		variables.put("createBy", StringUtil.convertObjToStr(vo.getCreateBy()));
//		//variables.put("click", vo.getClick() == null ? 0 : vo.getClick());
//		variables.put("code", vo.getCode());
//		final Context context = new Context(Locale.CHINA);
//		context.setVariables(variables); 
//		final String helpleHtml = templateEngine.process("help_template", context);
//		return uploadFile.uploadFileStream( helpleHtml.getBytes(), vo.getHelpUrl());
//	}
	
	/**
	 * @desc   生成帮助文章编号
	 * @author Tony Wang
	 * @create 2017年4月27日
	 * @return 
	 */
	private String nextHelpCode() {
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
		helpCodeLock.lock();
		try {
			if(helpCodeCounter == null) {
				String maxHelpCode = iOperateMgrService.findMaxHelpCode(today);
				if(StringUtils.isBlank(maxHelpCode)) {
					// 如果同没有当天的文章编号，则从0开始
					helpCodeCounter = new AtomicInteger();
				} else {
					// 从2016072100001asdfadf截取出00001
					helpCodeCounter = new AtomicInteger(Integer.parseInt(maxHelpCode.substring(8, 13)));
				}
			}
			// 判断日期是否有变更，有则currentArticelId归0，无则加1
			int newDay = Calendar.getInstance().get(Calendar.DATE);
			if(newDay == currentDay4Help) {
				nextId = helpCodeCounter.incrementAndGet();
				Assert.isTrue(nextId<100000, "当天增加的文章已超过100000篇");
			} else {
				helpCodeCounter.set(0);
				nextId = helpCodeCounter.incrementAndGet();
				// 把日期换为新日期
				currentDay4Help = newDay;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			helpCodeLock.unlock();
		}
		StringBuilder nextHelpCode = new StringBuilder(today)
				.append(String.format("%05d", nextId))
				.append(UUID.randomUUID().toString().replaceAll("-", "").substring(0,7));
		return nextHelpCode.toString();
		
	}
	
	private void validateHelp(OperateHelpVO vo) {
		// 至少选择一个平台
		boolean show = !(Objects.equals(vo.getWap(), (byte)0) && Objects.equals(vo.getWeb(), (byte)0) &&  Objects.equals(vo.getAndroid(), (byte)0) && Objects.equals(vo.getIos(), (byte)0));
		Assert.isTrue(show, "请至少选择一个文章显示平台");
		if(CollectionUtils.isNotEmpty(vo.getHelpTops())) {
			// 如果文章有推送栏目，不能重复推送同一个栏目
			Long fisrtTypeId = -1L;
			for(OperateHelpTopVO top : vo.getHelpTops()) {
				Assert.isTrue(!Objects.equals(top.getTypeId(), fisrtTypeId), "不能重复推送同一个栏目");
				fisrtTypeId = top.getTypeId();
				// 如果推送(不是置顶)到本栏目，则可以不用设置上、下线时间
				if( !(Objects.equals(vo.getTypeId(), top.getTypeId()) && top.getIsTop()==0) ) {
					Assert.notNull(top.getUptime(), "上线时间不能为空");
					Assert.notNull(top.getDowntime(), "下线时间不能为空");
				}
			}
		}
		// 验证文章所属栏目不能为虚拟栏目
		OperateHelpTypeVO criteria = new OperateHelpTypeVO();
		criteria.setId(vo.getTypeId());
		OperateHelpTypeBO helpType = iOperateMgrService.findHelpType(criteria);
		Assert.notNull(helpType, "文章栏目为空");
		Assert.isTrue(Objects.equals(helpType.getVirtual(), (byte)0), "虚拟栏目不能添加文章");
	}
	
	@Override
	public OperateHelpBO findSingle(OperateHelpVO vo) {
		return iOperateMgrService.findSingleHelp(vo);
	}

	@Override
	public int updateHelp(OperateHelpVO vo) throws IOException {
		Assert.notNull(vo.getId(), "帮助文章id为空");
		// "待审核"的文章都不能编辑
		Assert.isTrue(OperateEnum.HelpStatus.isEditable(vo.getStatus()), "待审核的文章都不能编辑");
		validateHelp(vo);
//		List<QiniuUploadResultVO> list = saveHelpAsHtml(vo);
//		if (!ObjectUtil.isBlank(list)) {
//			QiniuUploadResultVO qiniuUploadResultVO = list.get(0);
//			vo.setHelpUrl(qiniuUploadResultVO.getFileName());
//		}
		return iOperateMgrService.updateHelp(vo);
	}

	@Override
	public int updateHelpStatus(OperateHelpVO vo) {
		return iOperateMgrService.updateHelpStatus(vo);
	}

	@Override
	public int updateHelpTypeOrder(List<OperateHelpTypeVO> vos) {
		return iOperateMgrService.updateHelpTypeOrder(vos);
	}

}
