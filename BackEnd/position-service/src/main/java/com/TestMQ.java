package com;

import com.alibaba.fastjson.JSON;
import com.entity.Position;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class TestMQ implements CommandLineRunner {

    private String time= "1625555090000";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedRate = 2000)
    public void doMQ() throws ClassNotFoundException, SQLException {
        System.out.println("hahhfahdfhahdsfhsadf");
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
        long trueTime1= Long.parseLong(time)+300;
        String time1 = Long.toString(trueTime1);
        //
        ResultSet rs = stmt.executeQuery("select * from taxi.position2 where ts>"+time+"and ts<"+time1);
        //
        long trueTime= Long.parseLong(time)+300;
        time = Long.toString(trueTime);
        //
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
    }

    @Override
//    @Scheduled(cron = "0/10 * * * * ? ")
    public void run(String... strings) throws Exception {
        doMQ();
    }

}
