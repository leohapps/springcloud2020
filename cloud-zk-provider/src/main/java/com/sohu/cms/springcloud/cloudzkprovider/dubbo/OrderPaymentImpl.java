/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloudzkprovider.dubbo;

import com.sohu.cms.springcloud.cloudcommon.api.payment.OrderPayment;
import org.springframework.stereotype.Service;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloudzkprovider.dubbo.OrderPaymentImpl
 * @description
 * @date 2020/12/24 4:35 下午
 */

@Service
@org.apache.dubbo.config.annotation.Service(version = "1.0.0")
public class OrderPaymentImpl implements OrderPayment {

    @Override
    public String payment(Long orderId) {
        return "order-"+ orderId+ " has payed success";
    }
}
