package com.ordertaking.ordertakingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class OrderTakingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderTakingServiceApplication.class, args);
    }

}
