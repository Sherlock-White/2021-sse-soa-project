package org.example.distributionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableDiscoveryClient//代表自己是一个服务提供方
//@EnableFeignClients
@Configuration
public class DistributionServiceApplication {
    public static void main(String[] args) {
        System.out.println("来了");
        SpringApplication.run(DistributionServiceApplication.class,args);

    }
}
