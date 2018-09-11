package com.hhly.cms.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * @author Tony Wang
 * @version 1.0
 * @desc
 * @date 2018/3/9 10:29
 * @company 益彩网络科技有限公司
 */
public abstract class RestUtil {

    private static RestTemplate rest = new RestTemplate();

    public static <T> String httpExchange(String url, T vo, HttpMethod method) {
        HttpEntity<T> request = new HttpEntity<>(vo);
        ResponseEntity<String> res = rest.exchange(
                url,
                method,
                request,
                new ParameterizedTypeReference<String>() {});
        String httpStr = res.getBody();
        Assert.isTrue(res.getStatusCode()== HttpStatus.OK, httpStr);
        return httpStr;
    }
}
