package com.hhly.cms.paymgr.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.paymgr.entity.RequestLog;
import com.hhly.cms.paymgr.service.RequestLogService;
import com.hhly.cms.utils.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lgs on
 * @version 1.0
 * @desc 交易流水管理
 * @date 2018/8/17.
 * @company 益彩网络科技有限公司
 */
@RequestMapping("paymgr/request-log")
@Controller
public class RequestLogController extends BaseController {

    @Autowired
    private RequestLogService requestLogService;

    @RequestMapping("")
    @Authority(privilege = AuthEnum.SEARCH)
    public String index() {
        return "paymgr/request_log";
    }


    @RequestMapping("/list")
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
    public Object findTransLogListPage(RequestLog vo) {
        return requestLogService.findRequestLogListPage(vo);
    }

}
