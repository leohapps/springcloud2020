/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloud.consumer.order80.api;

import com.sohu.cms.springcloud.cloud.consumer.order80.service.PaymentFeignApi;
import com.sohu.cms.springcloud.cloudcommon.entity.CommonResult;
import com.sohu.cms.springcloud.cloudcommon.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloud.consumer.order80.api.OrderController
 * @description
 * @date 2020/12/31 4:33 下午
 */
@RestController
public class OrderController {

    public static final String PAYMENT_URL_PREFIX = "http://CLOUD-PAYMENT-SERVICE";

    public static final String PAYMENT_URL_POST = PAYMENT_URL_PREFIX + "/payment/create";

    public static final String PAYMENT_URL_GET = PAYMENT_URL_PREFIX + "/payment/get/";

    //@Autowired
    //private RestTemplate restTemplate;
    @Autowired
    private PaymentFeignApi paymentFeignApi;


  /*  @PostMapping(value = "/consumer/payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment) {
        return restTemplate.patchForObject(PAYMENT_URL_POST, payment, CommonResult.class);
    }*/

 /*   @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL_GET + id,  CommonResult.class);
    }*/

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id) {
        return paymentFeignApi.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String feignTimeout() {
        return paymentFeignApi.feignTimeOutTest();
    }

    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String testOk(@PathVariable("id") Long id) {
        return paymentFeignApi.testOk(id);
    }

    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
    public String testTimeout(@PathVariable("id") Long id) {
        return paymentFeignApi.testTimeout(id);
    }
}
