/*
 * Copyright (c) 2020. Sohu.
 * All rights reserved.
 */

package com.sohu.cms.springcloud.cloudzkconsumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author xiaoliu208902
 * @version V1.0
 * @className com.sohu.cms.springcloud.cloudzkconsumer.LogAspect
 * @description
 * @date 2020/12/30 2:06 下午
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut(value = "execution(* com.sohu.cms.springcloud.cloudzkconsumer.service.*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public void processLog(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String name = signature.getMethod().getName();
        StopWatch started = StopWatch.createStarted();
        try {
            Object proceed = pjp.proceed();
        } catch (Throwable throwable) {
            log.error("dubbo error!**********************");
        }
        started.stop();
        log.info("{} method finish, cost time {} ms", name, started.getTime());
    }
}
