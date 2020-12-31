package com.sohu.cms.springcloud.cloud.provider.payment8001;

import com.sohu.cms.ribbon.config.CustomRibbonRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;


@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = "com.sohu.cms.springcloud.cloud.provider.payment8001.dao")
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = CustomRibbonRule.class)
public class CloudProviderPayment8001Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudProviderPayment8001Application.class, args);
    }

}
