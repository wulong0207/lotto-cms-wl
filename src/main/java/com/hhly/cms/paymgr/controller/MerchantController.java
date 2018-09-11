package com.hhly.cms.paymgr.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.paymgr.entity.CompanyAccount;
import com.hhly.cms.paymgr.entity.Merchant;
import com.hhly.cms.paymgr.service.AccountService;
import com.hhly.cms.paymgr.service.MerchantService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.MD5Util;
import com.hhly.cms.utils.ThreeDesUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.util.EncryptUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author lgs on
 * @version 1.0
 * @desc
 * @date 2018/8/22.
 * @company 益彩网络科技有限公司
 */
@RequestMapping("paymgr/merchant")
@Controller
public class MerchantController extends BaseController {


    @Autowired
    private MerchantService merchantService;

    @RequestMapping("")
    @Authority(privilege = AuthEnum.SEARCH)
    public String index() {
        return "paymgr/merchant";
    }


    @RequestMapping("/list")
    @Authority(privilege = AuthEnum.SEARCH)
    @ResponseBody
    public Object findTransLogListPage(Merchant vo) {
        return merchantService.findMerchantListPage(vo);
    }


    @RequestMapping("/insert")
    @Authority(privilege = AuthEnum.ADD)
    @ResponseBody
    public Object insert(Merchant vo, HttpSession session) throws UnsupportedEncodingException {
        Merchant bo = merchantService.findMerchant(vo);
        if (bo != null && bo.getId() != null) {
            ResultBO resultBO = ResultBO.err();
            resultBO.setMessage("商户编号已经存在，无法继续添加");
            return resultBO;
        }
        String key = ThreeDesUtil.encryptMode(EncryptUtil.getRandomLowerString(10), EncryptUtil.getRandomCode4());
        vo.setPasswd(key);
        vo.setCreateTime(new Date());
        vo.setModifyTime(new Date());
        vo.setModifyBy(getUserName(session));
        return getResult(merchantService.insert(vo));
    }


    @RequestMapping("/update")
    @Authority(privilege = AuthEnum.UPD)
    @ResponseBody
    public Object update(Merchant vo, HttpSession session) {
        vo.setModifyBy(getUserName(session));
        vo.setModifyTime(new Date());
        return getResult(merchantService.update(vo));
    }

}
