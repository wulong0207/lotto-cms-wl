package com.hhly.cms.paymgr.service;

import com.google.gson.reflect.TypeToken;
import com.hhly.cms.paymgr.entity.CompanyAccount;
import com.hhly.cms.paymgr.entity.Merchant;
import com.hhly.cms.paymgr.entity.RequestLog;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.util.ObjectUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lgs on
 * @version 1.0
 * @desc
 * @date 2018/8/21.
 * @company 益彩网络科技有限公司
 */
@Service
public class AccountService extends PayBaseService {

    public String COMPANY_ACCOUNT = "companyAccount/";

    public String COMPANY_ACCOUNT_LIST_PAGE = COMPANY_ACCOUNT + "list/page";


    /**
     * 查询支付账号
     *
     * @param vo
     * @return
     */
    public Object findCompanyAccountListPage(CompanyAccount vo) {
        return gson.fromJson(this.httpExchange(rest, LOTTO_PAY_PLATFORM_URL + COMPANY_ACCOUNT_LIST_PAGE, vo, HttpMethod.POST),
                new TypeToken<PagingBO<CompanyAccount>>() {
                }.getType());
    }


    /**
     * 查询支付账号
     *
     * @param vo
     * @return
     */
    public CompanyAccount findCompanyAccount(CompanyAccount vo) {
        CompanyAccount result = rest.getForObject(LOTTO_PAY_PLATFORM_URL + COMPANY_ACCOUNT + "/{getAccount}", CompanyAccount.class, vo.getGetAccount());
        return result;
    }


    /**
     * 查询支付账号
     *
     * @param ids
     * @return
     */
    public List<CompanyAccount> findCompanyAccountByIds(String ids) {
        return gson.fromJson(this.httpExchange(rest, LOTTO_PAY_PLATFORM_URL + COMPANY_ACCOUNT + "/ids/{ids}", null, HttpMethod.GET, ids),
                new TypeToken<List<CompanyAccount>>() {
                }.getType());
    }


    public Map<String, String> getCompanyAccountMap(String ids) {
        Map<String, String> map = new HashMap<>();
        List<CompanyAccount> list = findCompanyAccountByIds(ids);
        if (ObjectUtil.isBlank(list)) {
            return map;
        }
        list.forEach(v -> {
            map.put(String.valueOf(v.getId()), v.getGetAccount());
        });
        return map;
    }

    /**
     * 新增支付账号
     *
     * @param vo
     * @return
     */
    public Boolean insert(CompanyAccount vo) {
        return gson.fromJson(this.httpExchange(rest, LOTTO_PAY_PLATFORM_URL + COMPANY_ACCOUNT, vo, HttpMethod.POST),
                new TypeToken<Boolean>() {
                }.getType());
    }


    /**
     * 新增支付账号
     *
     * @param vo
     * @return
     */
    public Boolean update(CompanyAccount vo) {
        return gson.fromJson(this.httpExchange(rest, LOTTO_PAY_PLATFORM_URL + COMPANY_ACCOUNT, vo, HttpMethod.PUT),
                new TypeToken<Boolean>() {
                }.getType());
    }
}
