package com.sohu.cms.springcloud.cloudpurezkconsmuer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class CloudPureZkConsmuerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudPureZkConsmuerApplication.class, args);
    }

}
