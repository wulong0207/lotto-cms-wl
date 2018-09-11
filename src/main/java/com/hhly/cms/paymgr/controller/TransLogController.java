package com.hhly.cms.paymgr.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.paymgr.entity.TransLog;

import com.hhly.cms.paymgr.service.AccountService;
import com.hhly.cms.paymgr.service.MerchantService;
import com.hhly.cms.paymgr.service.TransLogService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lgs on
 * @version 1.0
 * @desc 交易流水管理
 * @date 2018/8/17.
 * @company 益彩网络科技有限公司
 */
@RequestMapping("paymgr/trans-log")
@Controller
public class TransLogController extends BaseController {

    @Autowired
    private TransLogService transLogService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private AccountService accountService;

    @RequestMapping("")
    @Authority(privilege = AuthEnum.SEARCH)
    public String index() {
        return "paymgr/trans_log";
    }


    @RequestMapping("/list")
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
    public Object findTransLogListPage(TransLog vo) {
        PagingBO<TransLog> result = transLogService.findTransLogListPage(vo);
        if (result == null || result.getTotal() == 0) {
            return result;
        }
        List<TransLog> list = result.getData();
        Set<String> code = new HashSet<>();
        Set<String> id = new HashSet<>();
        for (TransLog transLog : list) {
            code.add(transLog.getCompanyCode());
            id.add(String.valueOf(transLog.getAccountId()));
        }

        Map<String, String> merchantMap = merchantService.getMerchantMap(getConStr(code));
        Map<String, String> accountMap = accountService.getCompanyAccountMap(getConStr(id));

        for (TransLog transLog : list) {
            transLog.setAccount(accountMap.get(String.valueOf(transLog.getAccountId())));
            transLog.setCompany(merchantMap.get(transLog.getCompanyCode()));
        }

        return result;
    }

    private String getConStr(Set<String> set) {
        final String[] result = {""};
        set.forEach(v -> {
            if (result[0].contains(",")) {
                result[0] = result[0] + "," + v;
            } else {
                result[0] = v;
            }
        });
        return result[0];
    }

    ;

}
