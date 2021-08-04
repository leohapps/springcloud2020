/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloud.ribbon.consumer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloud.ribbon.consumer.api.ConsumerController
 * @description
 * @date 2020/11/4 10:47 上午
 */

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/ribbon/consumer")
    public String helloConsumer() {
        return restTemplate.getForEntity("http://CLOUD-PAYMENT-SERVICE/hello", String.class).getBody();
    }
}
