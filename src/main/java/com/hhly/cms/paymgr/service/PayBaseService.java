package com.hhly.cms.paymgr.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hhly.skeleton.base.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * @author lgs on
 * @version 1.0
 * @desc
 * @date 2018/8/21.
 * @company 益彩网络科技有限公司
 */
public class PayBaseService {
    private static Logger logger = LoggerFactory.getLogger(PayBaseService.class);
    public RestTemplate rest = new RestTemplate();
    public Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    public static String LOTTO_PAY_PLATFORM_URL;

    static {
        LOTTO_PAY_PLATFORM_URL = PropertyUtil.getPropertyValue("sys_dynamic.properties", "lotto_pay_platform_url");
    }

    public <T> String httpExchange(RestTemplate rest, String url, T vo, HttpMethod method) {
        HttpEntity<T> request = new HttpEntity<>(vo);
        ResponseEntity<String> res = rest.exchange(
                url,
                method,
                request,
                new ParameterizedTypeReference<String>() {
                });
        String httpStr = res.getBody();
        logger.debug("请求返回：" + httpStr);
        Assert.isTrue(res.getStatusCode() == HttpStatus.OK, httpStr);
        return httpStr;
    }


    public <T> String httpExchange(RestTemplate rest, String url, T vo, HttpMethod method, Object... uriVariables) {
        HttpEntity<T> request = new HttpEntity<>(vo);
        ResponseEntity<String> res = rest.exchange(
                url,
                method,
                request,
                new ParameterizedTypeReference<String>() {
                }, uriVariables);
        String httpStr = res.getBody();
        logger.debug("请求返回：" + httpStr);
        Assert.isTrue(res.getStatusCode() == HttpStatus.OK, httpStr);
        return httpStr;
    }
}
