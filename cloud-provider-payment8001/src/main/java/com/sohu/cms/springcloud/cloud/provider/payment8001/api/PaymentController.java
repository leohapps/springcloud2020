/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloud.provider.payment8001.api;

import ch.qos.logback.core.util.TimeUtil;
import com.sohu.cms.springcloud.cloud.provider.payment8001.service.PaymentService;
import com.sohu.cms.springcloud.cloudcommon.entity.CommonResult;
import com.sohu.cms.springcloud.cloudcommon.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloud.provider.payment8001.api.PaymentController
 * @description
 * @date 2020/12/31 4:04 下午
 */
@Slf4j
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @PostMapping(value = "/payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        return result > 0?
                new CommonResult<>(200, "插入成功", port, payment)
                :new CommonResult<>(400, "插入失败");
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return payment != null?   new CommonResult<>(200, "查询成功", port, payment)
                :new CommonResult<>(404, "未找到");
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String feignTimeOutTest() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return port;
    }

    @PostMapping(value = "/payment/feign/timeout")
    public CommonResult<Payment> submintPayment(@RequestParam String plan
                                                , @RequestParam String content
                                                , @RequestParam String title) {
        log.info("the plan {}", plan);
        log.info("the content {}", content);
        log.info("the title {}", title);
        Payment payment = paymentService.getPaymentById(31L);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new CommonResult<>(200, "查询成功", port, payment);
    }

    @GetMapping(value = "/payment/hystrix/ok/{id}")
    public String testOk(@PathVariable("id") Long id) {
        return paymentService.testOk(id);
    }

    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    public String testTimeout(@PathVariable("id") Long id) {
        return paymentService.testTimeout(id);
    }

    @GetMapping(value = "/payment/hystrix/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        return paymentService.paymentCircuitBreaker(id);
    }


}
