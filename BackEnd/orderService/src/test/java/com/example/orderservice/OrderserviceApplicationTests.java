package com.example.orderservice;

import com.example.orderservice.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Wrapper;
import java.time.temporal.ChronoField;

@SpringBootTest
class OrderserviceApplicationTests {
//    @Autowired
//    OrderMapper orderMapper;
    @Test
    void contextLoads() {

        System.out.println("Monthday="+java.time.MonthDay.now().toString());
        System.out.println("year="+java.time.Year.now());
        System.out.println(java.time.Instant.now().plusMillis(8).toString());
        System.out.println(java.time.Instant.now().plusMillis(24*14).toString());
        StringBuilder order_id=new StringBuilder();
        order_id.append(java.time.Year.now().toString());
        order_id.append(java.time.MonthDay.now().toString().replaceAll("-",""));
        if(order_id.toString().length()<"20210101".length()){
            order_id.insert(4,"0");
        }
        System.out.println(order_id);
    }

}
