package com.hhly.cms.ordermgr.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.ordermgr.service.OrderAddService;
import com.hhly.cms.taskmgr.service.TaskService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.common.TaskEnum.TaskId;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.ordermgr.vo.OrderAddCmsVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderAddContentVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderAddIssueVO;

/**
 * @author huangb
 *
 * @Date 2017年1月17日
 *
 * @Desc 追号计划控制
 */
@Controller
@RequestMapping(value = "/ordermgr/chase")
public class OrderAddController extends BaseController {

	/**
	 * 追号计划local服务
	 */
	@Autowired
	private OrderAddService orderAddService;
	
	@Autowired
	private TaskService taskService;

	/**
	 * @return 追号首页
	 * @Desc 进入追号首页
	 */
	@RequestMapping()
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "ordermgr/chase";
	}

	/**
	 * @param orderAddCms
	 *            追号参数对象
	 * @return 追号计划列表
	 * @Desc 查询追号计划列表
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ParameterValid
	@ResponseBody
	public Object list(@Valid("search") OrderAddCmsVO orderAddCms) {
		return orderAddService.findPagingOrderAdd(orderAddCms);
	}

	/**
	 * @param id
	 *            追号计划自增id(也可用自定义编号)
	 * @return 追号计划详情结果
	 * @Desc 查询追号计划详情结果
	 */
	@RequestMapping(value = "/detail/{id}")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object detail(@PathVariable("id") Long id) {
		return getResultSuccess(orderAddService.findSingleOrderAdd(new OrderAddCmsVO(id)));
	}

	/**
	 * @param orderAddContent
	 *            追号内容参数对象
	 * @return 追号内容列表
	 * @Desc 查詢追号内容列表
	 */
	@RequestMapping(value = "/content/list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object orderAddContentList(OrderAddContentVO orderAddContent) {
		return orderAddService.findPagingOrderAddContent(orderAddContent);
	}

	/**
	 * @param orderAddIssue
	 *            追号期数参数对象
	 * @return 追号期数列表
	 * @Desc 查询追号期数列表
	 */
	@RequestMapping(value = "/issue/list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object orderAddIssueList(OrderAddIssueVO orderAddIssue) {
		return orderAddService.findPagingOrderAddIssue(orderAddIssue);
	}

	/**
	 * @desc 单个撤单：即单个追号计划单期撤单; <br>
	 *       规则1：计划支付成功且为追号中的才有撤单操作 <br>
	 *       规则2：只有等待追号或追号失败的追号彩期才可撤单 <br>
	 *       规则3：撤销某期时先查询该期之前是否有未处理（状态为等待追号或追号失败）的追号期，如果有提示先处理之前的，否则该期不能处理
	 * @author huangb
	 * @date 2017年1月19日
	 * @param ids
	 *            撤单的id列表(逗号分隔)
	 * @return 单个撤单
	 */
	@RequestMapping(value = "/issue/cancel")
	@Authority(privilege = AuthEnum.CANCEL_ORDER)
	@ResponseBody
	public Object cancelChaseIssue(@RequestParam("chaseIssueId") String chaseIssueId) {
		// int num = orderAddService.updOrderAddIssueStatusByIds(ids);
		// 任务触发
		Map<String, String> map = new HashMap<>();
		map.put("chaseIssueId", chaseIssueId);

		// 撤单结果
		return taskService.runTaskSync(TaskId.MANUAL_CHASE_CANCEL.getValue(), map);
	}

	/**
	 * @desc 批量撤单：即所有符合条件的追号计划按指定彩种、指定期撤单; <br>
	 *       规则1：计划支付成功且为追号中的才有撤单操作 <br>
	 *       规则2：只有等待追号或追号失败的追号彩期才可撤单 <br>
	 *       规则3：上面的“指定期”指录入的期号，而且只能是当前期或当前期之前的期号，之后的追号彩期不可撤单；
	 * @author huangb
	 * @date 2017年1月19日
	 * @param lotteryCode
	 *            彩种
	 * @param issueCode
	 *            彩期
	 * @return 批量撤单：即所有符合条件的追号计划按指定彩种、指定期撤单
	 */
	@RequestMapping(value = "/issue/batchCancel")
	@Authority(privilege = AuthEnum.CANCEL_ORDER)
	@ResponseBody
	public Object batchCancelChaseIssue(@RequestParam("lotteryCode") Integer lotteryCode,
			@RequestParam("issueCode") String issueCode) {
		// 任务触发
		Map<String, String> map = new HashMap<>();
		map.put("lotteryCode", lotteryCode.toString());
		map.put("issueCode", issueCode);
		taskService.runTask(TaskId.MANUAL_CHASE_BATCH_CANCEL.getValue(), map);
		// 撤单结果
		return getResult(true);
	}

	/**
	 * @desc 导出excel
	 * @author huangb
	 * @date 2017年1月20日
	 * @param response
	 * @param orderAddCms
	 * @param type
	 * @throws IOException
	 */
	@RequestMapping("/excel/{type}")
	@Authority(privilege = AuthEnum.EXPORT)
	@ParameterValid
	public void exportExcel(HttpServletResponse response, @Valid("search") OrderAddCmsVO orderAddCms,
			@PathVariable("type") String type) throws IOException {
		ByteArrayOutputStream outputStream = orderAddService.findExcelOrderAdd(orderAddCms, type);
		excel("order_chase", outputStream, response);
	}
	
	/**
	 * @desc 导出订单追号彩期excel
	 * @author huangb
	 * @date 2017年1月20日
	 * @param response
	 * @param orderAddIssue
	 *            查询对象
	 * @throws IOException
	 */
	@RequestMapping("/issue/excel")
	@Authority(privilege = AuthEnum.EXPORT)
	public void exportExcel(HttpServletResponse response, OrderAddIssueVO orderAddIssue) throws IOException {
		ByteArrayOutputStream outputStream = orderAddService.findExcelOrderAddIssue(orderAddIssue);
		excel("order_chase_issue", outputStream, response);
	}
	
	/**
	 * @desc 批量撤单：即单个计划剩余期撤单 <br>
	 *       规则1：追号计划支付成功才能批量撤单操作 <br>
	 *       规则2：只有等待追号或追号失败的追号彩期才可撤单 <br>
	 * @author huangb
	 * @date 2017年9月16日
	 * @param orderAddCode
	 *            目标追号计划编号
	 * @return 批量撤单：即单个计划剩余期撤单
	 */
	@RequestMapping(value = "/issue/cancelRemain")
	@Authority(privilege = AuthEnum.CANCEL_ORDER)
	@ResponseBody
	public Object cancelChaseRemainIssue(@RequestParam("orderAddCode") String orderAddCode) {
		// 任务触发
		Map<String, String> map = new HashMap<>();
		map.put("orderAddCode", orderAddCode);

		taskService.runTask(TaskId.MANUAL_CHASE_CANCEL_REMAIN.getValue(), map);
		// 撤单结果
		return getResult(true);
	}
	
	/**
	 * @desc 撤单中退款：撤单中的追号计划执行退款操作(自动退款流程的手动补充)<br>
	 *       规则：目标追号计划是否是撤单中状态(包含3种撤单中状态 6,7,8)，如果是则执行退款动作，否则不处理
	 * @author huangb
	 * @date 2017年11月16日
	 * @param orderAddCode
	 *            追号计划编号
	 * @return 撤单中退款：撤单中的追号计划执行退款操作
	 */
	@RequestMapping(value = "/issue/refund")
	@Authority(privilege = AuthEnum.CANCEL_ORDER)
	@ResponseBody
	public Object cancellingRefund(@RequestParam("orderAddCode") String orderAddCode) {
		// 任务触发
		Map<String, String> map = new HashMap<>();
		map.put("orderAddCode", orderAddCode);

		// 退款结果
		return taskService.runTaskSync(TaskId.MANUAL_CHASE_REFUND.getValue(), map);
	}
}
