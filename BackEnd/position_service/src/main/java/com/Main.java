package com;

import com.alibaba.fastjson.JSON;
import com.entity.Position;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author CuiXi
 * @version 1.0
 * @Description:
 * @date 2021/3/11 14:15
 */
@SpringBootApplication
@EnableDiscoveryClient//代表自己是一个服务提供方
@MapperScan("com.mapper")
@EnableScheduling
public class Main {

   // private static final String host = "139.224.251.185";
    public static void main(String[] args) {
//        //RabbitTemplate rabbitTemplate = null;
        SpringApplication.run(Main.class,args);
//        try {
////            List<Position> list = new ArrayList<Position>();
////            // load JDBC-restful driver
////            Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
////            // use port 6041 in url when use JDBC-restful
////            String url = "jdbc:TAOS-RS://" + host + ":6041/?user=root&password=taosdata";
////            Properties properties = new Properties();
////            properties.setProperty("charset", "UTF-8");
////            properties.setProperty("locale", "en_US.UTF-8");
////            properties.setProperty("timezone", "UTC-8");
////            Connection conn = DriverManager.getConnection(url, properties);
////            Statement stmt = conn.createStatement();
//////            stmt.execute("create database if not exists restful_test");
//////            stmt.execute("use restful_test");
//////            stmt.execute("create table restful_test.weather(ts timestamp, temperature float) tags(location nchar(64))");
//////            stmt.executeUpdate("insert into t1 using restful_test.weather tags('北京') values(now, 18.2)");
////            ResultSet rs = stmt.executeQuery("select * from taxi.position2 where id=2");
////            ResultSetMetaData meta = rs.getMetaData();
////            //System.out.print(meta);
////            while (rs.next()) {
////                for (int i = 1; i <= meta.getColumnCount(); i++) {
//////                    Position position= null;
//////                    position.setTs(rs.getString(1));
////////                    position.setTs(rs.getString(1));
////////                    position.setId(rs.getString(2));
////////                    position.setJing(rs.getString(3));
////////                    position.setWei(rs.getString(4));
////                    Position position = new Position(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
////                    list.add(position);
////                    System.out.print(list);
////                    //System.out.print(meta.getColumnLabel(i) + ": " + rs.getString(i) + "t");
////                }
////                System.out.println();
////            }
////            rs.close();
////
////            stmt.close();
////            conn.close();
//
//            //****test--rabbitMQ****//
//            List<Position> list = new ArrayList<Position>();
//            Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
//            // use port 6041 in url when use JDBC-restful
//            String url = "jdbc:TAOS-RS://" + host + ":6041/?user=root&password=taosdata";
//            Properties properties = new Properties();
//            properties.setProperty("charset", "UTF-8");
//            properties.setProperty("locale", "en_US.UTF-8");
//            properties.setProperty("timezone", "UTC-8");
//            Connection conn = DriverManager.getConnection(url, properties);
//            Statement stmt = conn.createStatement();
////            stmt.execute("create database if not exists restful_test");
////            stmt.execute("use restful_test");
////            stmt.execute("create table restful_test.weather(ts timestamp, temperature float) tags(location nchar(64))");
////            stmt.executeUpdate("insert into t1 using restful_test.weather tags('北京') values(now, 18.2)");
//            ResultSet rs = stmt.executeQuery("select * from taxi.position2 where id=1");
//            ResultSetMetaData meta = rs.getMetaData();
//            while (rs.next()) {
//                for (int i = 1; i <= meta.getColumnCount(); i++) {
////                    Position position= null;
////                    position.setTs(rs.getString(1));
//////                    position.setTs(rs.getString(1));
//////                    position.setId(rs.getString(2));
//////                    position.setJing(rs.getString(3));
//////                    position.setWei(rs.getString(4));
//                    Position position = new Position(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
//                    list.add(position);
//                    //System.out.print(list);
//                    //System.out.print(meta.getColumnLabel(i) + ": " + rs.getString(i) + "t");
//                }
//                System.out.println();
//            }
//            System.out.println(list);
//
//            //input into RabbitMQ
//            //assert false;
//            rabbitTemplate.convertAndSend("position", JSON.toJSONString(list));
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
