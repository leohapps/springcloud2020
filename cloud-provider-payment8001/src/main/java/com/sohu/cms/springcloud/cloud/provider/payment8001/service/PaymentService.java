/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloud.provider.payment8001.service;

import com.sohu.cms.springcloud.cloud.provider.payment8001.dao.PaymentMapper;
import com.sohu.cms.springcloud.cloudcommon.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloud.provider.payment8001.service.PaymentService
 * @description
 * @date 2020/12/31 3:59 下午
 */
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
}
