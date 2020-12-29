/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloudpurezkconsmuer;

import com.sohu.cms.springcloud.cloudcommon.api.payment.OrderPayment;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloudpurezkconsmuer.ConsumerController
 * @description
 * @date 2020/12/29 3:10 下午
 */
@RestController
public class ConsumerController {

    @Reference(version = "1.0.0")
    OrderPayment orderPayment;

    @RequestMapping(value = "/dubbo/get")
    public String getOrderPayment(@RequestParam("id") Long id) {
       return orderPayment.payment(id);
    }
}
