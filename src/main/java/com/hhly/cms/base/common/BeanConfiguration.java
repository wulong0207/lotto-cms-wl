package com.hhly.cms.base.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Tony Wang
 * @version 1.0
 * @desc
 * @date 2018/5/5 10:31
 * @company 益彩网络科技有限公司
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
