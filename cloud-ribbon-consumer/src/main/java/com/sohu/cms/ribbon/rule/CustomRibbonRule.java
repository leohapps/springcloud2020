/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.ribbon.rule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.ribbon.rule.CustomRibbonRule
 * @description
 * @date 2020/12/31 2:41 下午
 */
@Configuration
public class CustomRibbonRule {

    @Bean
    public IRule iRule() {
        return new RandomRule();
    }
}
