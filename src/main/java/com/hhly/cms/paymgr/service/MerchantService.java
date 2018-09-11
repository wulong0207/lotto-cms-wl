package com.hhly.cms.paymgr.service;

import com.google.gson.reflect.TypeToken;
import com.hhly.cms.paymgr.entity.CompanyAccount;
import com.hhly.cms.paymgr.entity.Merchant;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.util.ObjectUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lgs on
 * @version 1.0
 * @desc
 * @date 2018/8/22.
 * @company 益彩网络科技有限公司
 */
@Service
public class MerchantService extends PayBaseService {


    public String MERCHANT = "merchant/";

    public String MERCHANT_LIST_PAGE = MERCHANT + "list/page";


    /**
     * 查询支付账号
     *
     * @param vo
     * @return
     */
    public Object findMerchantListPage(Merchant vo) {
        return gson.fromJson(this.httpExchange(rest, LOTTO_PAY_PLATFORM_URL + MERCHANT_LIST_PAGE, vo, HttpMethod.POST),
                new TypeToken<PagingBO<Merchant>>() {
                }.getType());
    }


    /**
     * 查询支付账号
     *
     * @param vo
     * @return
     */
    public Merchant findMerchant(Merchant vo) {
        Merchant result = rest.getForObject(LOTTO_PAY_PLATFORM_URL + MERCHANT + "/{getAccount}", Merchant.class, vo.getCode());
        return result;
    }


    /**
     * 查询支付账号
     *
     * @param codes
     * @return
     */
    public List<Merchant> findMerchantByCodes(String codes) {
        return gson.fromJson(this.httpExchange(rest, LOTTO_PAY_PLATFORM_URL + MERCHANT + "/codes/{codes}", null, HttpMethod.GET, codes),
                new TypeToken<List<Merchant>>() {
                }.getType());
    }


    public Map<String, String> getMerchantMap(String codes) {
        Map<String, String> map = new HashMap<>();
        List<Merchant> list = findMerchantByCodes(codes);
        if (ObjectUtil.isBlank(list)) {
            return map;
        }
        list.forEach(v -> {
            map.put(v.getCode(), v.getCompany());
        });
        return map;
    }

    /**
     * 新增支付账号
     *
     * @param vo
     * @return
     */
    public Boolean insert(Merchant vo) {
        return gson.fromJson(this.httpExchange(rest, LOTTO_PAY_PLATFORM_URL + MERCHANT, vo, HttpMethod.POST),
                new TypeToken<Boolean>() {
                }.getType());
    }


    /**
     * 新增支付账号
     *
     * @param vo
     * @return
     */
    public Boolean update(Merchant vo) {
        return gson.fromJson(this.httpExchange(rest, LOTTO_PAY_PLATFORM_URL + MERCHANT, vo, HttpMethod.PUT),
                new TypeToken<Boolean>() {
                }.getType());
    }

}
