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
        String departure="同济大学嘉定校区";
        String destination="同济大学四平路校区";
        String fromPos = JSON.parseObject(JSON.parseArray(JSON.parseObject(posClient.getPos(departure)).get("geocodes").toString()).get(0).toString()).get("location").toString();
        String[] fromPosVec2=fromPos.split(",");
        Double from_lng=Double.parseDouble(fromPosVec2[0]);
        Double from_lat=Double.parseDouble(fromPosVec2[1]);
        String a=posClient.getPos(destination);
        String to_geocodes=JSON.parseObject(posClient.getPos(destination)).get("geocodes").toString();
        if(to_geocodes.equals("[]")){
            System.out.println("是空的");
        }

        System.out.println("to_geocodes:"+to_geocodes);
//        String toPos = JSON.parseObject(JSON.parseArray(JSON.parseObject(posClient.getPos(destination)).get("geocodes").toString()).get(0).toString()).get("location").toString();
//        String[] toPosVec2=toPos.split(",");
//        Double to_lng=Double.parseDouble(toPosVec2[0]);
//        Double to_lat=Double.parseDouble(toPosVec2[1]);
//        System.out.println(departure+":"+from_lng+","+from_lat);
//        System.out.println(destination+":"+to_lng+","+to_lat);
    }

}
