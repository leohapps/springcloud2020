/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloud.consumer.order80.service;

import com.sohu.cms.springcloud.cloudcommon.entity.CommonResult;
import com.sohu.cms.springcloud.cloudcommon.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloud.consumer.order80.service.PaymentFeignApiFallBack
 * @description
 * @date 2021/1/6 6:00 下午
 */
@Slf4j
@Component
public class PaymentFeignApiFallBack implements PaymentFeignApi {

    @Override
    public CommonResult<Payment> getPaymentById(Long id) {
        return null;
    }

    @Override
    public String feignTimeOutTest() {
        return null;
    }

    @Override
    public String testOk(Long id) {
        return null;
    }

    @Override
    public String testTimeout(Long id) {
        log.info("timeout oh my god");
        return "timeout oh my god";
    }
}
