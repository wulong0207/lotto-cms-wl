package com.hhly.cms.operatemgr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.operatemgr.service.OperateLotteryService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.common.cache.operate.OperateCacheEnum.OperLottCacheEnum;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.operatemgr.vo.OperateLotteryInfoVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateLotteryVO;

/**
 * @desc    彩种运营管理
 * @author  Tony Wang
 * @date    2017年2月7日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value="/operatemgr/lottery")
public class LotteryController extends BaseController { 
	@Autowired	
	private OperateLotteryService operateLotteryService;

	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "operatemgr/lottery";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object list(OperateLotteryVO vo){
		return operateLotteryService.list(vo);
	}
	
	@RequestMapping(value = "/listLotteryInfo")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object listLotteryInfo(OperateLotteryInfoVO vo){
		return operateLotteryService.listLotteryInfo(vo);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService = OperLottCacheEnum.class)
	public @ResponseBody Object udpate(@RequestBody@Valid(GroupValue.UPD)OperateLotteryVO vo,HttpSession session) {
		vo.setModifyBy(getUserRealName(session));
		int num = operateLotteryService.update(vo);	
		return getSaveResult(num);	
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService = OperLottCacheEnum.class)
	public @ResponseBody Object add(@RequestBody@Valid(GroupValue.ADD)OperateLotteryVO vo,HttpSession session) {
		vo.setCreateBy(getUserRealName(session));
		vo.setModifyBy(getUserRealName(session));
		int num = operateLotteryService.add(vo);
		return getSaveResult(num);
	}
}
