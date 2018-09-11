package com.hhly.cms.paymgr.service;

import com.google.gson.reflect.TypeToken;
import com.hhly.cms.paymgr.entity.RequestLog;
import com.hhly.cms.paymgr.entity.TransLog;
import com.hhly.skeleton.base.bo.PagingBO;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * @author lgs on
 * @version 1.0
 * @desc
 * @date 2018/8/21.
 * @company 益彩网络科技有限公司
 */
@Service
public class RequestLogService extends PayBaseService {

    public String REQUEST_LOG = "requestLog/";

    public String REQUEST_LOG_LIST_PAGE = REQUEST_LOG + "list/page";

    /**
     * 查询请求记录
     *
     * @param vo
     * @return
     */
    public Object findRequestLogListPage(RequestLog vo) {
        return gson.fromJson(this.httpExchange(rest, LOTTO_PAY_PLATFORM_URL + REQUEST_LOG_LIST_PAGE, vo, HttpMethod.POST),
                new TypeToken<PagingBO<RequestLog>>() {
                }.getType());
    }

}
