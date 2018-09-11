package com.hhly.cms.ordermgr.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.ordermgr.service.OrderGroupService;
import com.hhly.cms.taskmgr.service.TaskService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.TaskEnum.TaskId;
import com.hhly.skeleton.cms.ordermgr.bo.OrderGroupBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderGroupContentBO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderGroupVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderIssueVO;

/**
 * 
* @Description: 合买
* @author HouXiangBao289
* @date 2018年5月3日 下午3:58:58 
* @version V1.0.0
 */
@Controller
@RequestMapping("/ordermgr/group")
public class OrderGroupController extends BaseController {
	
	@Autowired
	private OrderGroupService orderGroupService;
	
	/**
	 * task服务，提供同步/异步任务调用
	 */
	@Autowired
	private TaskService taskService;

	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "ordermgr/order_group";
	}
	
	@RequestMapping("/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<OrderGroupBO> list(OrderGroupVO vo){
		return orderGroupService.findGroup(vo);
	}
	
	@RequestMapping("/userList")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<OrderGroupContentBO> userList(OrderGroupVO vo){
		return orderGroupService.findGroupContent(vo);
	}
	
	@RequestMapping("/excel")
	@Authority(privilege=AuthEnum.EXPORT)
	@ResponseBody
	public void excel(HttpServletResponse response,HttpSession session,OrderGroupVO vo)throws IOException{
		excel("orderUsers",orderGroupService.excel(vo),response);
	}
	
	@RequestMapping(value="findGroupById")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public Object updateRecommendReason(OrderIssueVO vo,HttpSession session){
		return getResultSuccess(orderGroupService.findGroupById(vo.getId()));
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public ResultBO<?> update(OrderGroupVO vo,HttpSession session){
		int ret = orderGroupService.update(vo);
		return getSaveResult(ret);
	}
	
	
	@RequestMapping(value="/recommand",method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public ResultBO<?> recommand(OrderGroupVO vo) {
		return orderGroupService.recommand(vo);
	}

	@RequestMapping(value="/top",method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public ResultBO<?> top(OrderGroupVO vo) {
		return orderGroupService.top(vo);
	}

	@RequestMapping(value="/siteGuarantee",method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public ResultBO<?> siteGuarantee(Integer groupId) {
		return orderGroupService.siteGuarantee(groupId);
	}
	
	@RequestMapping(value="/abortionOfManual",method=RequestMethod.POST)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public ResultBO<?> cancelGroupOrder(String orderCode) {
		// 任务触发
		Map<String, String> map = new HashMap<>();
		//区分手动流产和系统流产   "true":手动流产  
		map.put("orderCode", orderCode);
		ResultBO<?> resultBo = taskService.runTaskSync(TaskId.MANUAL_ORDER_ABORT.getValue(), map);
		return resultBo;
	}
}
