package com.hhly.cms.operatemgr.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.utils.Authority;
import com.hhly.cmscore.cms.remote.service.ICmsCacheService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.operatemgr.bo.LotteryCacheBO;
import com.hhly.skeleton.cms.operatemgr.vo.LotteryCacheVO;

import net.sf.json.JSONArray;

/**
 * @desc
 * @author YiJian
 * @date 2017年6月28日 下午3:37:35
 * @company 深圳益彩网络科技有限公司
 * @version v1.0
 */
@Controller
@RequestMapping(value = "/cmscache")
public class CmsCacheController extends BaseController {
	
	@Autowired
    private ICmsCacheService cmsCacheService;
	
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(){
		return "operatemgr/cache_list";
	}
	
	/**
	 * 分页查询
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object list(LotteryCacheVO vo){
		return cmsCacheService.findCacheListByPage(vo);
	}
	
	/**
	 * 详情查询
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/detail/{cKey}")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object detail(@PathVariable(value="cKey") String cKey){
		LotteryCacheVO vo = new LotteryCacheVO();
		vo.setCacheKey(cKey);
		return getResultSuccess(cmsCacheService.findCache(vo).get(0));
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@ParameterValid
	public Object add(HttpSession session,@Valid(GroupValue.ADD) LotteryCacheVO vo){	
		List<LotteryCacheBO> caches = cmsCacheService.findCache(vo);
		Assert.isFalse(caches.size()>0,"40999");
		return getSaveResult(cmsCacheService.insertCache(vo));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	public Object update(HttpSession session,@Valid(GroupValue.UPD) LotteryCacheVO vo){
		return getSaveResult(cmsCacheService.updateCache(vo));
	}
	
	
	/**
	 * 修改红包状态为已作废
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/clear/{cKey}",method = RequestMethod.GET)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public  Object clear(@PathVariable(value="cKey") String cKey){
		cmsCacheService.clearCache(cKey);
		return ResultBO.ok();
	}

	
	/**
	 * 删除缓存
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/del/{id}" , method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.DEL)
	@ResponseBody
	public  Object del(@PathVariable(value = "id")short id) {
		LotteryCacheVO vo = new LotteryCacheVO();
		vo.setId(id);
		LotteryCacheBO cache = cmsCacheService.findCache(vo).get(0);
		cmsCacheService.clearCache(cache.getCacheKey());
		return getSaveResult(cmsCacheService.delCacheById(id));
	}
	
	
	@RequestMapping(value = "/test/{min}/{max}" , method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.ALL)
	@ResponseBody
	public String getTest(HttpServletRequest request,@PathVariable(value="min") Integer min,@PathVariable(value="max") Integer max){
		JSONArray array = JSONArray.fromObject(cmsCacheService.findMessage(min,max));
		return array.toString();
	}
	
	@RequestMapping(value = "/count" , method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.ALL)
	@ResponseBody
	public Object getCount(HttpServletRequest request){
		return cmsCacheService.findCount();
	}
}
