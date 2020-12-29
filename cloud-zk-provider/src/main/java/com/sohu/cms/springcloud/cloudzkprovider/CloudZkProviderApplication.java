package com.sohu.cms.springcloud.cloudzkprovider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class CloudZkProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudZkProviderApplication.class, args);
    }

}
