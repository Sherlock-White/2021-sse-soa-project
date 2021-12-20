package com.example.orderservice;

import com.example.orderservice.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Wrapper;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoField;
import java.util.Date;

@SpringBootTest
class OrderserviceApplicationTests {
//    @Autowired
//    OrderMapper orderMapper;
    @Test
    void contextLoads() {
        String dateNowStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        dateNowStr=dateNowStr.replaceAll("-","");
        dateNowStr=dateNowStr.replaceAll(":","");
        dateNowStr=dateNowStr.replaceAll(" ","");
        System.out.println(dateNowStr);
    }

}
