package com.sohu.cms.springcloud.cloud.provider.paymentzk8004;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CloudProviderPaymentZk8004Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudProviderPaymentZk8004Application.class, args);
    }

}
