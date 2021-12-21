package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient//代表自己是一个服务提供方
@EnableFeignClients
public class DistributionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DistributionServiceApplication.class,args);
    }
}
