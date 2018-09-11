package com.hhly.cms.ticket.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.ticket.service.ITicketMonitorService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.cms.ticketmgr.bo.OrderTicketMonitorBO;

/**
 * @desc 出票报警管理类
 * @author jiangwei
 * @date 2017-2-13
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/ticketmgr/monitorcommon")
public class TicketMonitorController  extends BaseController {
    @Autowired
    private ITicketMonitorService ticketMonitorService;
    
    @RequestMapping()
    @Authority(privilege=AuthEnum.SEARCH)
    public String  index(){
        return "ticketmgr/monitor_common";
    }
    @RequestMapping(value = "/list")
    @Authority(privilege=AuthEnum.SEARCH)
    @ResponseBody
    public Object ticketMonitor(@RequestParam(value = "lotteryCodeStr", required = false)String lotteryCodeStr, 
    		@RequestParam(value = "lotteryCategory", required = false) Integer lotteryCategory){
    	List<String> codes = null;
//    	Assert.notEmpty(lotteryCodes, "40298");
    	if(ObjectUtil.isBlank(lotteryCodeStr)){
    		codes = ticketMonitorService.queryLotteryMonitorConfig(lotteryCategory);
    	}else{
    		codes =Arrays.asList(lotteryCodeStr.split(SymbolConstants.UNDERLINE));
    	}
    	List<OrderTicketMonitorBO> list = new ArrayList<>();
    	for (String code : codes) {
    		list.add(ticketMonitorService.getTicketMonitor(Integer.valueOf(code)));
		}
        return getResultSuccess(list);
    }
}
