/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloudsentinelservice8401;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloudsentinelservice8401.FlowLimitController
 * @description
 * @date 2021/6/5 2:42 下午
 */
@RestController
public class FlowLimitController {

    @GetMapping("test/a")
    public String testA() {
        return "------------test A";
    }

    @GetMapping("test/b")
    public String testB() {
        return "------------test B";
    }

    @GetMapping("test/c")
    public String testC() {
        try {
        Thread.sleep(2000);
        } catch (Exception e) {
        }
        return "------------test B";
    }

}
