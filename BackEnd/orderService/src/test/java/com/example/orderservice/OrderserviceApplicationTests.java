package com.example.orderservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.orderservice.feignClient.PosClient;
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
    @Autowired
    PosClient posClient;
    @Test
    void contextLoads() {
        String departure="同济大学";
        String fromPos = JSON.parseObject(JSON.parseArray(JSON.parseObject(posClient.getPos(departure)).get("geocodes").toString()).get(0).toString()).get("location").toString();
        String[] fromPosVec2=fromPos.split(",");
        System.out.println(Double.parseDouble(fromPosVec2[0]));
        System.out.println(Double.parseDouble(fromPosVec2[1]));
    }

}
