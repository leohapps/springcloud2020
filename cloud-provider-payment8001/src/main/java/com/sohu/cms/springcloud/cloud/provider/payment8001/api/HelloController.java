/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloud.provider.payment8001.api;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloud.provider.payment8001.api.HelloController
 * @description
 * @date 2020/11/3 6:15 下午
 */
@RestController
public class HelloController {

    private final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
    
    @Autowired
    private EurekaClient client;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index() {
        Application application = client.getApplication("cloud-payment-service");
        if (application == null) {
            return "null";
        }
        List<InstanceInfo> instances = application.getInstances();
        instances.forEach(instanceInfo -> LOGGER.info("instance: {}", instanceInfo.getIPAddr()));
        return "ok";
    }

}
