package com.chao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
    /***
     *加载启动类，已启动类为当前springboot的配置标准
     * @param args
     */
    public static void main(String[]args){
        SpringApplication.run(EurekaApplication.class,args);
    }
}
