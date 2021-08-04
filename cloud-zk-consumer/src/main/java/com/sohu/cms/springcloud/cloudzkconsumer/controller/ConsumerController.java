/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloudzkconsumer.controller;

import com.sohu.cms.springcloud.cloudzkconsumer.service.IJcTestService;
import com.sohu.cms.springcloud.cloudzkconsumer.service.OrderServiceImpl;
import com.sohu.dubbo.bean.jcDataCenter.vo.match.MatchInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloudzkconsumer.controller.ConsumerController
 * @description
 * @date 2020/11/16 1:12 下午
 */
@RestController
public class ConsumerController {
    private final Logger LOGGER = LoggerFactory.getLogger(ConsumerController.class);
    public static final String INVOKE_URL = "http://cloud-provider-payment";

    @Value("${server.port}")
    String serverPort;
    @Resource
    RestTemplate restTemplate;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private IJcTestService iJcTestService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index() {

        return "cloud with ZK : " + serverPort + "\t" + UUID.randomUUID().toString();
    }

    @RequestMapping(value = "call", method = RequestMethod.GET)
    public String paymentInvoke() {
        String result = restTemplate.getForObject(INVOKE_URL + "/hello",String.class);
        return result;
    }

    @GetMapping(value = "/dubbo/payment")
    public String dubboPayment() {
        return orderService.submitOrder();
    }

    @GetMapping(value = "/dubbo/match")
    public MatchInfo getMatchInfo() {
        return iJcTestService.testJcService();
    }

}
