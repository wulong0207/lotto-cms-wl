package com.hhly.cms.ordermgr.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.ordermgr.service.OrderService;
import com.hhly.cms.taskmgr.service.TaskService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.JsonUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.common.OrderEnum.CancelType;
import com.hhly.skeleton.base.common.TaskEnum.TaskId;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderInfoCmsSearchVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderInfoVO;
/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-12-6 上午9:32:10
 * @Desc 会员方案基本信息管理
 */
@Controller
@RequestMapping(value = "/ordermgr/basic")
public class OrderInfoController extends BaseController {
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * task服务，提供同步/异步任务调用
	 */
	@Autowired
	private TaskService taskService;
	
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(HttpServletResponse response,String orderCode){
		Cookie cookie = new Cookie("orderCode", orderCode);
		response.addCookie(cookie);
		return "ordermgr/basic";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	@ParameterValid
	public  Object list(@Valid("search")OrderInfoCmsSearchVO vo){
		return  orderService.find(vo);
	}
	/**
	 * 查询看详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detail/{orderCode}")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object detail(@PathVariable("orderCode")String orderCode){
		StringVO vo = new StringVO();
		vo.setStr(orderCode);
		return getResultSuccess(orderService.findOrderInfoDetail(vo));
	}
	/**
	 * 查询订单详情信息
	 * @param orderCode
	 * @return
	 */
	@RequestMapping(value = "/content/detail/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	@ParameterValid
	public  Object listDetail(@Valid("detail")OrderInfoCmsSearchVO vo){
		return  orderService.findOrderDetail(vo);
	}
	/**
	 * 修改方案编号
	 * @param vo
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	public  Object udpate(@Valid("cmsupd")OrderInfoVO vo,HttpSession session) {
		vo.setModifyBy(getUserRealName(session));
		int num = orderService.updOrderInfo(vo);
		return getSaveResult(num);
	}
	
	/**
	 * 订单详情查看页面
	 * @param code
	 * @return
	 */
	@RequestMapping("editwin/{lotteryCode}")
	@Authority(privilege=AuthEnum.SEARCH)
	public String  issueEdit(@PathVariable("lotteryCode")Integer lotteryCode){
		String head = "ordermgr/detail/";
		Lottery lot = Lottery.getLottery(lotteryCode);
		if(lot==null){
			return "error";
		}else if(Lottery.FB == lot ||Lottery.BB == lot  || Lottery.ZC6 == lot 
				|| Lottery.JQ4 == lot || Lottery.SFC == lot || Lottery.ZC_NINE == lot
				|| Lottery.BJDC == lot || Lottery.SFGG == lot){
			 return head+"sport";
		}
		return head+"number";
	}
	
	/**
	 * 导出excel
	 * @param response
	 * @param vo
	 * @throws IOException
	 */
	@RequestMapping("/excel/{type}")
	@Authority(privilege =AuthEnum.EXPORT)
	@ParameterValid
	public void exportOpExcel(HttpServletResponse response,
			@Valid("search")OrderInfoCmsSearchVO vo,
			@PathVariable("type")String type) throws IOException{
		ByteArrayOutputStream outputStream=orderService.getOrderInfoExcel(vo,type);
		excel("order", outputStream, response);
	}
	
	/**
	 * 上传添加订单
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/upload/add")
	@Authority(privilege=AuthEnum.UPLOAD)
	@ResponseBody
	public  Object uploadSearch(@RequestParam MultipartFile file) throws IOException{
		//Result<?> result ;
		String str = new String(file.getBytes());
		//将内容回车换行替换
		//参数验证
		str = str.replaceAll("[\\t\\r]", SymbolConstants.ENPTY_STRING);
		//String value[] = str.split("\\n");
		//List<String> list = new ArrayList<String>();
		return JsonUtil.object2Json(ResultBO.ok());
	}
	/**
	 * 修改订单状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月13日 下午5:26:43
	 * @param vo
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/status",method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	public  Object udpateStatus(@Valid("status")OrderInfoVO vo,HttpSession session) {
		vo.setModifyBy(getUserRealName(session));
		int num = orderService.updateOrderStatus(vo);
		return getResultSuccess(num);
	}
	/**
	 * 订单执行操作
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月15日 下午4:42:55
	 * @param vo
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/operate",method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	public  Object udpateOperate(@Valid("operate")OrderInfoVO vo,HttpSession session) {
		if(vo.getOperate() == 0){
			String[] orders = StringUtils.tokenizeToStringArray(vo.getOrderCode(), ",");
			for (String order : orders) {
				//检查票状态
				Map<String,String> param = new HashMap<>();
				param.put("orderCode", order);
				ResultBO<?> result =  taskService.runTaskSync("000026", param);
				if(!ResultBO.SUCCESS_CODE.equals(result.getSuccess())){
					return new ResultBO<String>(result.getSuccess(), result.getErrorCode(), vo.getOrderCode());
				}
			}
			return getResultSuccess(vo.getOrderCode());
		}else{
			String orderCode = orderService.updateOrderOperate(vo);	
			return getResultSuccess(orderCode);
		}
	}
	
	/**
	 * @desc 单个撤单：即单个订单撤单; <br>
	 *       规则1：目标订单支付成功(值2) <br>
	 *       规则2：订单状态必须是出票失败(状态值7) <br>
	 *       规则3：以距离官方截止时间的剩余时间(分钟)做弹框提示，提供是否强制撤单的选择按钮，如果选择“是”则强制撤单，“否”则不处理
	 * @author huangb
	 * @date 2017年1月19日
	 * @param orderCode
	 *            撤单的订单code
	 * @param isCheck
	 *            是否检查剩余时间并弹框提示
	 * @param cancelOrderType 
	 * 			  撤单原因类型
	 * @param cancelDesc 
	 * 			 撤单原因
	 * @return 单个撤单
	 */
	@RequestMapping(value = "/cancel")
	@Authority(privilege = AuthEnum.CANCEL_ORDER)
	@ResponseBody
	public Object cancelOrder(@RequestParam("orderCode") String orderCode,
			@RequestParam(value = "isCheck", required = false) String isCheck,
			@RequestParam(value = "cancelOrderType", required = false) Short cancelOrderType,
			@RequestParam(value = "cancelDesc", required = false) String cancelDesc) {
		
		// 任务触发
		Map<String, String> map = new HashMap<>();
		map.put("orderCode", orderCode);
		map.put("isCheck", isCheck);
		// 添加撤单原因
		String cancelReason = "";
		if (cancelOrderType != null) {
			if (cancelOrderType.shortValue() == CancelType.MANUAL.getValue().shortValue()) {
				cancelReason = cancelDesc;
			} else if (cancelOrderType.shortValue() == CancelType.TRANS.getValue().shortValue()) {
				cancelReason = CancelType.TRANS.getDesc();
			} else if (cancelOrderType.shortValue() == CancelType.LIMIT.getValue().shortValue()) {
				cancelReason = CancelType.LIMIT.getDesc();
			}
		}
		map.put("cancelReason", cancelReason);
		// 撤单结果
		ResultBO<?> resultBo = taskService.runTaskSync(TaskId.MANUAL_ORDER_CANCEL.getValue(), map);
		
		return resultBo;
	}
	
	/**
	 * @desc 批量撤单：按彩种、彩期撤单; <br>
	 * @author huangb
	 * @date 2017年6月12日
	 * @param lotteryCode
	 *            彩种
	 * @param issueCode
	 *            彩期
	 * @param cancelOrderType 
	 * 			  撤单原因类型
	 * @param cancelDesc 
	 * 			 撤单原因
	 * @return 批量撤单：按彩种、彩期撤单;
	 */
	@RequestMapping(value = "/batchCancel")
	@Authority(privilege = AuthEnum.CANCEL_ORDER)
	@ResponseBody
	public Object batchCancelOrder(@RequestParam("lotteryCode") Integer lotteryCode,
			@RequestParam("issueCode") String issueCode,@RequestParam("buyScreen") String buyScreen,
			@RequestParam(value = "cancelOrderType", required = false) Short cancelOrderType,
			@RequestParam(value = "cancelDesc", required = false) String cancelDesc) {
		// 任务触发
		Map<String, String> map = new HashMap<>();
		map.put("lotteryCode", lotteryCode.toString());
		map.put("issueCode", issueCode);
		map.put("screenCodes", buyScreen);
		// 添加撤单原因
		String cancelReason = "";
		if (cancelOrderType != null) {
			if (cancelOrderType.shortValue() == CancelType.MANUAL.getValue().shortValue()) {
				cancelReason = cancelDesc;
			} else if (cancelOrderType.shortValue() == CancelType.TRANS.getValue().shortValue()) {
				cancelReason = CancelType.TRANS.getDesc();
			} else if (cancelOrderType.shortValue() == CancelType.LIMIT.getValue().shortValue()) {
				cancelReason = CancelType.LIMIT.getDesc();
			}
		}
		map.put("cancelReason", cancelReason);
			
		taskService.runTask(TaskId.MANUAL_ORDER_BATCH_CANCEL.getValue(), map);
		// 撤单结果
		return getResult(true);
	}
	
	/**
	 * @desc 撤单中退款：撤单中的订单执行退款操作(自动退款流程的手动补充)<br>
	 *       规则：目标订单是否是撤单中状态，如果是则执行退款动作，否则不处理
	 * @author huangb
	 * @date 2017年11月16日
	 * @param orderCode
	 *            订单code
	 * @return 撤单中退款：撤单中的订单执行退款操作
	 */
	@RequestMapping(value = "/refund")
	@Authority(privilege = AuthEnum.CANCEL_ORDER)
	@ResponseBody
	public Object cancellingRefund(@RequestParam("orderCode") String orderCode) {
		
		// 任务触发
		Map<String, String> map = new HashMap<>();
		map.put("orderCode", orderCode);
		
		// 退款结果
		ResultBO<?> resultBo = taskService.runTaskSync(TaskId.MANUAL_ORDER_REFUND.getValue(), map);
		
		return resultBo;
	}
}
