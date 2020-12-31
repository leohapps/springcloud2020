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
 * @className com.sohu.cms.ribbon.rule.CustomRule
 * @description 注意，ribbon规则变更的配置不能再componentScan扫描路径下（官网）
 * @date 2020/12/31 6:10 下午
 */
@Configuration
public class CustomRule {

    @Bean
    public IRule myRule() {
        return new RandomRule();
    }
}
