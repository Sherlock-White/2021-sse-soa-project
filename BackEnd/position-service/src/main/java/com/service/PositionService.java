package com.service;

import com.entity.Position;
import com.entity.Weather;
import com.mapper.tdengine.PositionMapper;
import com.mapper.tdengine.WeatherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author CuiXi
 * @version 1.0
 * @Description:
 * @date 2021/3/11 15:18
 */
@Service
@Transactional(transactionManager = "tdengineTransactionManager")
public class PositionService {

    private static final String host = "139.224.251.185";
    @Autowired
    private PositionMapper weatherMapper;

    public List<Position> query() {
        return weatherMapper.select();
    }

    public List<Position> queryAll() throws ClassNotFoundException, SQLException {
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
        ResultSet rs = stmt.executeQuery("select * from taxi.position2 where id=400");
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


}

