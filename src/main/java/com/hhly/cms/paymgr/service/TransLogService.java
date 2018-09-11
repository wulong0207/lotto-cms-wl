package com.hhly.cms.paymgr.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hhly.cms.paymgr.entity.TransLog;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * @author lgs on
 * @version 1.0
 * @desc
 * @date 2018/8/17.
 * @company 益彩网络科技有限公司
 */
@Service
public class TransLogService extends PayBaseService {

    public String TRANS_LOG = "transLog/";

    public String TRANS_LOG_LIST_PAGE = TRANS_LOG + "list/page";

    /**
     * 查询交易记录
     *
     * @param vo
     * @return
     */
    public PagingBO<TransLog> findTransLogListPage(TransLog vo) {
        return gson.fromJson(this.httpExchange(rest, LOTTO_PAY_PLATFORM_URL + TRANS_LOG_LIST_PAGE, vo, HttpMethod.POST),
                new TypeToken<PagingBO<TransLog>>() {
                }.getType());
    }


}
