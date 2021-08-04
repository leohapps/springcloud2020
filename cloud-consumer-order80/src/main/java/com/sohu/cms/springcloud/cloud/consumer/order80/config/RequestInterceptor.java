/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloud.consumer.order80.config;

import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.lottery.interceptor.RequestInterceptor
 * @description  feign spring-cloud-openfeign会把requestParam请求直接挂在url上，导致URI-too-large。 需要写拦截器将参数放入body体内
 * 并且设置 httpHeader为 Content-type application/x-www-form-urlencoded;charset=UTF-8
 * 在配置文件中指定拦截器
 * feign:
 *   client:
 *     config:
 *       default:
 *         requestInterceptors: com.sohu.lottery.interceptor.RequestInterceptor
 *
 * @date 2021/1/22 4:48 下午
 */
@Slf4j
public class RequestInterceptor implements feign.RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        if ("post".equalsIgnoreCase(template.method()) && template.body() == null) {
            String query = template.queryLine();
            log.info("the post request url is {}", query);
            template.queries(new HashMap<>());
            if (StringUtils.hasText(query) && query.startsWith("?")) {
                template.body(query.substring(1));
            }
            template.header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        }
    }
}
