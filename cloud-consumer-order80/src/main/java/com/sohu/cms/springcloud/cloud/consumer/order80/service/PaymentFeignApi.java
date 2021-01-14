/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloud.consumer.order80.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sohu.cms.springcloud.cloudcommon.entity.CommonResult;
import com.sohu.cms.springcloud.cloudcommon.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloud.consumer.order80.service.PaymentFeignApi
 * @description
 * @date 2021/1/5 3:56 下午
 */

@Service
@FeignClient(value = "CLOUD-PAYMENT-SERVICE", fallback = PaymentFeignApiFallBack.class)
public interface PaymentFeignApi {

    @GetMapping(value = "/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping(value = "/payment/feign/timeout")
    String feignTimeOutTest();

    @GetMapping(value = "/payment/hystrix/ok/{id}")
    String testOk(@PathVariable("id") Long id);

    // 无需再加HystrixCommand注解，再启动配置中打开开关即可。
    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    String testTimeout(@PathVariable("id") Long id);
}
