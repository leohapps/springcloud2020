/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloud.provider.payment8001.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sohu.cms.springcloud.cloud.provider.payment8001.dao.PaymentMapper;
import com.sohu.cms.springcloud.cloudcommon.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloud.provider.payment8001.service.PaymentService
 * @description
 * @date 2020/12/31 3:59 下午
 */
@Slf4j
@Service
public class PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    public int create(Payment payment) {
        return paymentMapper.insert(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentMapper.selectByPrimaryKey(id);
    }


    public String testOk(Long id) {
        return Thread.currentThread().getName() + ": the id is " + id + "ok, O(∩_∩)O哈哈~";
    }

    @HystrixCommand(fallbackMethod = "testTimeoutHandler" , commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String testTimeout(Long id) {
//        int a = 10/0;
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Thread.currentThread().getName() + ": the id is " + id + "timeout, ┭┮﹏┭┮";
    }

    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakerHandler", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
    })
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("********* id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + " 调用成功，流水号： " + serialNumber;
    }

    public String paymentCircuitBreakerHandler(Integer id) {
        return "id 不能为负数，请稍后再试， id = " + id;
    }

    public String testTimeoutHandler(Long id, Throwable e) {
        log.error("the errorMsg is ", e);
        return Thread.currentThread().getName() + ": the id is " + id + " 超时或者异常， ┭┮﹏┭┮";
    }
}
