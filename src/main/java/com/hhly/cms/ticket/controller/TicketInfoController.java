package com.hhly.cms.ticket.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.taskmgr.service.TaskService;
import com.hhly.cms.ticket.service.TicketInfoService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.exception.ValidException;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.Param;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketInfoStatusVO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketInfoVO;

/**
 * @desc 票信息控制
 * @author huangb
 * @date 2017年2月21日
 * @company 益彩网络
 * @version v1.0
 */
@Controller
@RequestMapping(value = "/ticketmgr/ticketinfo")
public class TicketInfoController extends BaseController {
	
	/**
	 * 重新送票
	 */
    private  final String UPDATE_SEND = "3";
    /**
     * 重查出票
     */
    private  final String UPDATE_OUT = "4";
    /**
     * 切换出票商
     */
    private  final String UPDATE_DEALER = "5";

	/**
	 * 票local服务
	 */
	@Autowired
	private TicketInfoService ticketInfoService;

	@Autowired
	private TaskService taskService;
	/**
	 * @desc 票信息首页
	 * @author huangb
	 * @date 2017年2月21日
	 * @return 进入票信息首页
	 */
	@RequestMapping()
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "ticketmgr/ticket_info";
	}

	/**
	 * @desc 票管理：分页查询
	 * @author huangb
	 * @date 2017年2月21日
	 * @param ticketInfoVO
	 *            参数对象
	 * @return 票管理：分页查询
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object list(@Valid("search") TicketInfoVO ticketInfoVO) {
		return ticketInfoService.findPagingTicket(ticketInfoVO);
	}

	/**
	 * @desc 票管理：查询详情
	 * @author huangb
	 * @date 2017年2月21日
	 * @param id
	 *            票增长id
	 * @return 票管理：查询详情
	 */
	@RequestMapping(value = "/detail/{id}")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object detail(@PathVariable("id") Long id) {
		return getResultSuccess(ticketInfoService.findSingleTicket(new TicketInfoVO(id)));
	}

	/**
	 * @desc 票管理：修改票信息(包括票状态、回执内容、票图片、票内容、备注)
	 * @author huangb
	 * @date 2017年2月21日
	 * @param ticketInfoVO
	 *            参数对象
	 * @param session
	 * @return 票管理：修改票信息(包括票状态、回执内容、票图片、票内容、备注)
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@Authority(privilege = AuthEnum.UPD)
	public @ResponseBody Object update(@Valid("upd") TicketInfoVO ticketInfoVO, HttpSession session) {
		ticketInfoVO.setModifyBy(getUserName(session));
		int num = ticketInfoService.updTicket(ticketInfoVO);
		// 入库结果
		return getSaveResult(num);
	}

	/**
	 * @desc 票管理：查询excle导出
	 * @author huangb
	 * @date 2017年2月21日
	 * @param response
	 * @param ticketInfoVO
	 *            参数对象
	 * @throws IOException
	 */
	@RequestMapping("/excel")
	@Authority(privilege = AuthEnum.EXPORT)
	public void exportExcel(HttpServletResponse response, @Valid("search") TicketInfoVO ticketInfoVO)
			throws IOException {
		ByteArrayOutputStream outputStream = ticketInfoService.findExcelTicket(ticketInfoVO);
		excel("ticket_info", outputStream, response);
	}	
	/**
	 * 修改票状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月20日 下午2:39:06
	 * @param vo
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/status",method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	public  Object udpateStatus(@Valid(GroupValue.UPD)TicketInfoStatusVO vo,HttpSession session) {
		vo.setModifyBy(getUserRealName(session));
		String result = ticketInfoService.updateTicketStatus(vo);
		return getResultSuccess(result);
	}
	
	/**
	 *  上传票号
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月21日 上午10:46:53
	 * @param ticketType 票类型
	 * @param ticketNo 票号
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/upload/ticket",method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid({
		@Param(index =0 ,notNull =true,min=1,max=2,msg="票类型"),
		@Param(index =1 ,notNull =true,msg="票号")
	})
	public  Object uploadTicket(String ticketType,String ticketNo,HttpSession session) {
		getUserRealName(session);
		ticketInfoService.uploadTicketNo(ticketType,ticketNo);
		return getResult(true);
	}
	
	/**
	 * 票操作
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月5日 下午2:13:30
	 * @param operate
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/operate",method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid({
		@Param(index =0 ,notNull =true,msg="操作类型"),
		@Param(index =1 ,notNull =true,msg="票ID")
	})
	public  Object operate(String operate,String id,String channelId,HttpSession session) {
		String result = ticketInfoService.updateTicketOperate(operate,id,getUserRealName(session));
		Map<String,String> param = new HashMap<>();
		if(UPDATE_SEND.equals(operate)){
			param.put("ticket", id);
			param.put("type", "0");
			return taskService.runTaskSync("000025", param);
		}else if(UPDATE_DEALER.equals(operate)){
			if(StringUtils.isEmpty(channelId)){
				throw new ValidException("请选择切换渠道商");
			}
			param.put("channelId", channelId);
			param.put("ticket", id);
			param.put("type", "1");
			return taskService.runTaskSync("000025", param);
		}else if(UPDATE_OUT.equals(operate)){
			param.put("batchNum", result);
			return taskService.runTaskSync("000026", param);
		}
		return getResultSuccess(result);
	}
}
