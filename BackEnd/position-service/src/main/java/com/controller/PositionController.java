package com.controller;

import com.alibaba.fastjson.JSON;
import com.entity.Position;
import com.service.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author CuiXi
 * @version 1.0
 * @Description:
 * @date 2021/3/11 15:19
 */

@Api(tags = "position服务接口")
@RequestMapping("/position")
@RestController
public class PositionController {

    private int ASC_N=0;
    @Autowired
    private PositionService weatherService;

//
//    @ApiOperation("查询")
//    @GetMapping(value="/getId")
//    public List<Position> queryWeather(){
//        return weatherService.query();
//    }


    @ApiOperation("获取同一时间车的位置")
    @CrossOrigin
    @GetMapping(value="/getAll")
    public List<Position> query() throws SQLException, ClassNotFoundException {
        final String host = "139.224.251.185";
        //return weatherService.queryAll();
        List<Position> list = new ArrayList<Position>();
        Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
        // use port 6041 in url when use JDBC-restful
        String url = "jdbc:TAOS-RS://" + host + ":6041/?user=root&password=taosdata";
        Properties properties = new Properties();
        properties.setProperty("charset", "UTF-8");
        properties.setProperty("locale", "en_US.UTF-8");
        properties.setProperty("timezone", "UTC-8");
        Connection conn = DriverManager.getConnection(url, properties);
        Statement stmt = conn.createStatement();
//            stmt.execute("create database if not exists restful_test");
//            stmt.execute("use restful_test");
//            stmt.execute("create table restful_test.weather(ts timestamp, temperature float) tags(location nchar(64))");
//            stmt.executeUpdate("insert into t1 using restful_test.weather tags('北京') values(now, 18.2)");
        ResultSet rs = stmt.executeQuery("select * from taxi.position2 order by ts ASC limit "+ASC_N+",10");
        ASC_N+=10;
        ResultSetMetaData meta = rs.getMetaData();
        while (rs.next()) {
            for (int i = 1; i <= meta.getColumnCount(); i++) {
//                    Position position= null;
//                    position.setTs(rs.getString(1));
////                    position.setTs(rs.getString(1));
////                    position.setId(rs.getString(2));
////                    position.setJing(rs.getString(3));
////                    position.setWei(rs.getString(4));
                Position position = new Position(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
                list.add(position);
                //System.out.print(list);
                //System.out.print(meta.getColumnLabel(i) + ": " + rs.getString(i) + "t");
            }
            System.out.println();
        }
        System.out.println(list);
        return list;
        //return "nhao";
    }


    @Autowired
    private RabbitTemplate rabbitTemplate;

//    private void sendMessage(){
//        rabbitTemplate.convertAndSend("position",);
//    }

    @CrossOrigin
    @GetMapping(value="/getTime")
    @ApiOperation("获取某一时刻车的位置")
    public List<Position> queryTime(@RequestBody @ApiParam(value = "time", required = true) String ts) throws SQLException, ClassNotFoundException {
        final String host = "139.224.251.185";
        //return weatherService.queryAll();
        List<Position> list = new ArrayList<Position>();
        Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
        // use port 6041 in url when use JDBC-restful
        String url = "jdbc:TAOS-RS://" + host + ":6041/?user=root&password=taosdata";
        Properties properties = new Properties();
        properties.setProperty("charset", "UTF-8");
        properties.setProperty("locale", "en_US.UTF-8");
        properties.setProperty("timezone", "UTC-8");
        Connection conn = DriverManager.getConnection(url, properties);
        Statement stmt = conn.createStatement();
//            stmt.execute("create database if not exists restful_test");
//            stmt.execute("use restful_test");
//            stmt.execute("create table restful_test.weather(ts timestamp, temperature float) tags(location nchar(64))");
//            stmt.executeUpdate("insert into t1 using restful_test.weather tags('北京') values(now, 18.2)");
        ResultSet rs = stmt.executeQuery("select * from taxi.position2 where ts="+ts);
        ResultSetMetaData meta = rs.getMetaData();
        while (rs.next()) {
            for (int i = 1; i <= meta.getColumnCount(); i++) {
//                    Position position= null;
//                    position.setTs(rs.getString(1));
////                    position.setTs(rs.getString(1));
////                    position.setId(rs.getString(2));
////                    position.setJing(rs.getString(3));
////                    position.setWei(rs.getString(4));
                Position position = new Position(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
                list.add(position);
                //System.out.print(list);
                //System.out.print(meta.getColumnLabel(i) + ": " + rs.getString(i) + "t");
            }
            System.out.println();
        }
        System.out.println(list);
        rabbitTemplate.convertAndSend("position", JSON.toJSONString(list));
        //return "nhao";
        return list;
    }


    @CrossOrigin
    @GetMapping(value="/testMQ")
    public String testRabbitMQ() throws SQLException, ClassNotFoundException {
        rabbitTemplate.convertAndSend("position", "JSON.toJSONString(list)");
        return "hahah";
        //return "nhao";
    }

}
