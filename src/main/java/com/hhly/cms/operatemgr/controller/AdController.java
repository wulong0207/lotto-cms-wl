package com.hhly.cms.operatemgr.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.operatemgr.service.AdService;
import com.hhly.cms.utils.Authority;
import com.hhly.cmscore.cms.remote.service.IRedisMgrService;
import com.hhly.skeleton.base.common.cache.lottery.LotteryCacheEnum.LotteryTypeCacheEnum;
import com.hhly.skeleton.base.common.cache.operate.OperateCacheEnum.AdCacheEnum;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.operatemgr.bo.OperateAdTypeBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateAdTypeVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateAdVO;

/**
 * @desc    广告图管理
 * @author  Tony Wang
 * @date    2017年2月7日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value="/operatemgr/ad")
public class AdController extends BaseController { 
	@Autowired	
	private AdService adService;
	
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "operatemgr/ad";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object list(OperateAdVO vo){
		return adService.list(vo);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ParameterValid
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = AdCacheEnum.class)
	public Object udpate(@Valid(GroupValue.UPD)OperateAdVO vo,HttpSession session) {
		vo.setModifyBy(getUserRealName(session));
		int num = adService.udpate(vo);	
		return getSaveResult(num);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ParameterValid
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = AdCacheEnum.class)
	public Object add(@Valid(GroupValue.ADD)OperateAdVO vo,HttpSession session) {
		vo.setCreateBy(getUserRealName(session));
		int num = adService.add(vo);
		return getSaveResult(num);
	}
	
	@RequestMapping(value = "type/{advId}")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object listType(OperateAdTypeVO vo){
		List<OperateAdTypeBO> adTypes =  adService.listType(vo);
		Set<Long> userTypes = new HashSet<>();
		Set<String> channels = new HashSet<>();
		for(OperateAdTypeBO adType : adTypes) {
			userTypes.add(adType.getmTypeId());
			channels.add(adType.getChannelId());
		}
		List<Long> userTypesList = new ArrayList<>(userTypes);
		List<String> channelsList = new ArrayList<>(channels);
		Collections.sort(userTypesList);
		Collections.sort(channelsList);
		return new Object[]{userTypesList, channelsList};
	}
}
