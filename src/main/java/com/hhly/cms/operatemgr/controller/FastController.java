package com.hhly.cms.operatemgr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.operatemgr.service.OperateFastService;
import com.hhly.cms.utils.Authority;
import com.hhly.cmscore.cms.remote.service.IRedisMgrService;
import com.hhly.skeleton.base.common.cache.operate.OperateCacheEnum.ArticleCacheEnum;
import com.hhly.skeleton.base.common.cache.operate.OperateCacheEnum.FastCacheEnum;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.operatemgr.vo.OperateFastInfoVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateFastVO;

/**
 * 
 * @author lidecheng
 * @Version 1.0
 * @CreatDate 2017-2-23
 * @Desc 快投管理
 */
@Controller
@RequestMapping(value = "/operatemgr/fast")
public class FastController extends BaseController {
	
	@Autowired	
	private OperateFastService operateFastService;

	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "operatemgr/fast";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object list(OperateFastVO vo){
		return operateFastService.list(vo);
	}
	
	@RequestMapping(value = "/listFastInfo")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object listFastInfo(OperateFastInfoVO vo){
		return operateFastService.listFastInfo(vo);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ParameterValid
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = FastCacheEnum.class)
	public  Object udpate(@RequestBody@Valid(GroupValue.UPD)OperateFastVO vo,HttpSession session) {
		// TODO　若有数据库中已有发布中的广告，则前端不能再添加此状态的广告
		//Assert.isTrue(vo.getOfflineTime().after(vo.getOnlineTime()), "下线时间必须在上线时间之后");
		vo.setModifyBy(getUserRealName(session));
		int num = operateFastService.update(vo);
		return getSaveResult(num);		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ParameterValid
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService = FastCacheEnum.class)
	public  Object add(@RequestBody@Valid(GroupValue.ADD)OperateFastVO vo,HttpSession session) {
		//Assert.isTrue(vo.getOfflineTime().after(vo.getOnlineTime()), "下线时间必须在上线时间之后");
		vo.setCreateBy(getUserRealName(session));
		vo.setModifyBy(getUserRealName(session));
		Assert.isNull(vo.getId());
		int num = operateFastService.add(vo);	
		return getSaveResult(num);
	}
}
