package com.hhly.cms.paymgr.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.paymgr.entity.CompanyAccount;
import com.hhly.cms.paymgr.entity.Merchant;
import com.hhly.cms.paymgr.entity.RequestLog;
import com.hhly.cms.paymgr.service.AccountService;
import com.hhly.cms.paymgr.service.RequestLogService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.MD5Util;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author lgs on
 * @version 1.0
 * @desc 交易流水管理
 * @date 2018/8/17.
 * @company 益彩网络科技有限公司
 */
@RequestMapping("paymgr/account")
@Controller
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("")
    @Authority(privilege = AuthEnum.SEARCH)
    public String index() {
        return "paymgr/account";
    }


    @RequestMapping("/list")
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
    public Object findTransLogListPage(CompanyAccount vo) {
        return accountService.findCompanyAccountListPage(vo);
    }


    @RequestMapping("/insert")
    @Authority(privilege = AuthEnum.ADD)
    @ResponseBody
    public Object insert(CompanyAccount vo, HttpSession session) {
        CompanyAccount bo = accountService.findCompanyAccount(vo);
        if (bo != null && bo.getId() != null) {
            ResultBO resultBO = ResultBO.err();
            resultBO.setMessage("账号已经存在，无法继续添加");
            return resultBO;
        }
        vo.setCreateTime(new Date());
        vo.setModifyTime(new Date());
        vo.setModifyBy(getUserName(session));
        vo.setLoginPwd(MD5Util.encrypt(vo.getLoginPwd()));
        return getResult(accountService.insert(vo));
    }


    @RequestMapping("/update")
    @Authority(privilege = AuthEnum.UPD)
    @ResponseBody
    public Object update(CompanyAccount vo, HttpSession session) {
        vo.setModifyBy(getUserName(session));
        vo.setModifyTime(new Date());
        return getResult(accountService.update(vo));
    }


}
