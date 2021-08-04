/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloudzkconsumer.service;

import com.sohu.cms.springcloud.cloudcommon.api.payment.OrderPayment;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloudzkconsumer.service.OrderServiceImpl
 * @description
 * @date 2020/12/24 5:45 下午
 */
@Slf4j
@Service
public class OrderServiceImpl {

    //@Reference(version = "1.0.0", interfaceClass = OrderPayment.class)
    private OrderPayment orderPayment;

    public String submitOrder() {
        long orderId = new Random().nextInt(10000);
        log.info("generator orderId " + orderId);
        return orderPayment.payment(orderId);
    }
}
