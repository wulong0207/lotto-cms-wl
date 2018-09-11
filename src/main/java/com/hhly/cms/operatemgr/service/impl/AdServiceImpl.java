package com.hhly.cms.operatemgr.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hhly.cms.dicmgr.service.DictionaryService;
import com.hhly.cms.operatemgr.service.AdService;
import com.hhly.cms.utils.DicUtils;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.model.DicDataEnum;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.dicmgr.bo.DicDataDetailBO;
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
@Service
public class AdServiceImpl implements AdService {

	@Autowired
	private IOperateMgrService iOperateMgrService;
	
	@Autowired
	private DictionaryService dictionaryService;
	
	private static AtomicInteger advCodeCounter;
	
	private static int currentDay4Adv;
	
	private final Lock advCodeLock = new ReentrantLock();
	
	static {
		currentDay4Adv = Calendar.getInstance().get(Calendar.DATE);
	}
	
	/**
	 * @desc   查询广告列表
	 * @author Tony Wang
	 * @create 2017年2月8日
	 * @param vo
	 * @return  
	 */
	@Override
	public PagingBO<OperateAdBO> list(OperateAdVO vo) {
		return iOperateMgrService.findOperateAd(vo);
	}

	/**
	 * @desc   vo有id则update广告信息，无则insert
	 * @author Tony Wang
	 * @create 2017年2月16日
	 * @param vo
	 * @return  
	 
	@Override
	public int merge(OperateAdVO vo) {
		return iOperateMgrService.mergeOperateAd(vo);
	}*/

	/**
	 * @desc   更新广告
	 * @author Tony Wang
	 * @create 2017年4月6日
	 * @param vo
	 * @return 
	 */
	@Override
	public int udpate(OperateAdVO vo) {
		return iOperateMgrService.udpateOperateAd(vo);
	}

	/**
	 * @desc   添加广告
	 * @author Tony Wang
	 * @create 2017年4月6日
	 * @param vo
	 * @return 
	 */
	@Override
	public int add(OperateAdVO vo) {
		vo.setAdvCode(nextAdvCode());
		return iOperateMgrService.addOperateAd(vo);

	}
	
	private String nextAdvCode() {
		String today = DateUtil.convertDateToStr(new Date(), DateUtil.DATE_FORMAT_NO_LINE);
		int nextAdvCode;
		advCodeLock.lock();
		try {
			if(advCodeCounter == null) {
				String maxAdvCode = iOperateMgrService.findMaxAdvCode(today);
				if(StringUtils.isBlank(maxAdvCode)) {
					// 如果没有当天的编号，则从0开始
					advCodeCounter = new AtomicInteger();
				} else {
					// 从2016072100001截取出00001
					advCodeCounter = new AtomicInteger(Integer.parseInt(maxAdvCode.substring(8)));
				}
			}
			// 判断日期是否有变更，有则currentArticelId归0，无则加1
			int newDay = Calendar.getInstance().get(Calendar.DATE);
			if(newDay == currentDay4Adv) {
				nextAdvCode = advCodeCounter.incrementAndGet();
				Assert.isTrue(nextAdvCode<100000, "当天增加的广告超过100000");
			} else {
				advCodeCounter.set(0);
				nextAdvCode = advCodeCounter.incrementAndGet();
				// 更新当前日期
				currentDay4Adv = newDay;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			advCodeLock.unlock();
		}
		return new StringBuilder(today)
				.append(String.format("%05d", nextAdvCode))
				.toString();
	}

	/**
	 * @desc   查询广告类型
	 * @author Tony Wang
	 * @create 2017年4月25日
	 * @param vo
	 * @return 
	 */
	@Override
	public List<OperateAdTypeBO> listType(OperateAdTypeVO vo) {
		return iOperateMgrService.listAdType(vo);
	}

	/**
	 * @desc   广告图页面列表
	 * @author Tony Wang
	 * @create 2017年5月10日
	 * @param vo
	 * @return 
	 */
	@Override
	public PagingBO<OperateAdMenuBO> listMenu(OperateAdMenuVO vo) {
		return iOperateMgrService.listAdMenu(vo);
	}

	/**
	 * @desc   增加或更新广告图页面
	 * @author Tony Wang
	 * @create 2017年5月10日
	 * @param vo
	 * @return 
	 */
	@Override
	public int mergeMenu(OperateAdMenuVO vo) {
		return iOperateMgrService.mergeAdMenu(vo);
	}

	/**
	 * @desc   删除广告页面
	 * @author Tony Wang
	 * @create 2017年5月10日
	 * @param vo
	 * @return 
	 */
	@Override
	public int deleteMenu(OperateAdMenuVO vo) {
		return iOperateMgrService.deleteAdMenu(vo);
	}

	@Override
	public List<DictionaryBO> findMenuAsDic(OperateAdMenuVO vo) {
		StringVO strVO = new StringVO(DicDataEnum.OPERATE_AD_MENU.getDicCode());
		List<DicDataDetailBO> dicDataDetails = dictionaryService.findDetail(strVO);
		if(CollectionUtils.isEmpty(dicDataDetails))
			return  Collections.<DictionaryBO>emptyList();
		List<OperateAdMenuBO> menus = iOperateMgrService.findAdMenus(vo);
		if(CollectionUtils.isEmpty(menus))
			return  Collections.<DictionaryBO>emptyList();
		// 提取OPERATE_ADV_MENU表中有记录的menu值
		List<String> menuVals = new ArrayList<>();
		for(OperateAdMenuBO menu : menus) {
			menuVals.add(menu.getMenu().toString());
		}
		//for(DicDataDetailBO detail : dicDataDetails) {
		//	if(menuVals.contains(detail.getDicDataValue()))
		//		dicDataDetails.remove(detail);
		//}
		Iterator<DicDataDetailBO> it = dicDataDetails.iterator();
		// 只显示OPERATE_ADV_MENU中有记录的广告页面
		while(it.hasNext()){
			DicDataDetailBO dicDetail = it.next();
			if(!menuVals.contains(dicDetail.getDicDataValue())) {
				it.remove();
			}
		}
		return DicUtils.toDic(dicDataDetails, "dicDataValue", String.class, "dicDataName", String.class);
	}

	/**
	 * @desc   查询广告图位置信息，以数据字典形式返回
	 * @author Tony Wang
	 * @create 2017年5月11日
	 * @param vo
	 * @return 
	 */
	@Override
	public List<DictionaryBO> findMenuPositionAsDic(OperateAdMenuVO vo) {
		Assert.notNull(vo.getMenu(), "请选择广告页面");
		StringVO strVO = new StringVO(DicDataEnum.OPERATE_AD_POSITION.getDicCode());
		List<DicDataDetailBO> dicDataDetails = dictionaryService.findDetail(strVO);
		if(CollectionUtils.isEmpty(dicDataDetails))
			return  Collections.<DictionaryBO>emptyList();
		List<OperateAdMenuBO> menus = iOperateMgrService.findAdMenus(vo);
		if(CollectionUtils.isEmpty(menus))
			return  Collections.<DictionaryBO>emptyList();
		Assert.isTrue(menus.size()==1, "此广告页面存在重复的广告位置配置信息");
		Assert.notNull(menus.get(0).getPosition(), "此广告页面未配置广告位置信息");
		// 提取此广告页面在OPERATE_ADV_MENU中的广告位置配置信息
		List<String> positions = Arrays.<String>asList(menus.get(0).getPosition().split(SymbolConstants.COMMA));
		Iterator<DicDataDetailBO> it = dicDataDetails.iterator();
		// 只显示此广告页面在OPERATE_ADV_MENU配置的广告位置信息
		while(it.hasNext()){
			DicDataDetailBO dicDetail = it.next();
			if(!positions.contains(dicDetail.getDicDataValue())) {
				it.remove();
			}
		}
		return DicUtils.toDic(dicDataDetails, "dicDataValue", String.class, "dicDataName", String.class);
	}
}
