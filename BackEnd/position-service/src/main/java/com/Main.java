package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author CuiXi
 * @version 1.0
 * @Description:
 * @date 2021/3/11 14:15
 */
@SpringBootApplication
@EnableDiscoveryClient//代表自己是一个服务提供方
@MapperScan("com.mapper")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
}
