/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloudzkconsumer.config;

import com.google.common.collect.Lists;
import com.sohu.cms.springcloud.cloudcommon.api.payment.OrderPayment;
import org.apache.dubbo.config.MethodConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloudzkconsumer.config.DubboConfig
 * @description
 * @date 2020/12/29 5:32 下午
 */
//@Configuration
public class DubboConfig {

    //@Bean
    public ReferenceConfig<OrderPayment> serviceConfig() {
        ReferenceConfig<OrderPayment> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(OrderPayment.class);
        referenceConfig.setVersion("1.0.0");
        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setName("payment");
        methodConfig.setTimeout(2000);
        referenceConfig.setMethods(Lists.newArrayList(methodConfig));
        return referenceConfig;
    }
}
