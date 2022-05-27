package com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.entity.Position;

public class JdbcRestfulDemo {

    //private static ImageStrings list;

    private static final String host = "139.224.251.185";
    public static void query() {
        try {
            List<Position> list = new ArrayList<Position>();
            // load JDBC-restful driver
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
            ResultSet rs = stmt.executeQuery("select * from taxi.position2 where id=2");
            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    Position position = null;
//                    position.setTs(rs.getString(1));
//                    position.setId(rs.getString(2));
//                    position.setId(rs.getString(3));
//                    position.setId(rs.getString(4));
                    list.add(position);
                    System.out.print(position);
                    //System.out.print(meta.getColumnLabel(i) + ": " + rs.getString(i) + "t");
                }
                System.out.println();
            }
            rs.close();

            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public ResultSetMetaData queryAll() throws ClassNotFoundException, SQLException {
//        Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
//        // use port 6041 in url when use JDBC-restful
//        String url = "jdbc:TAOS-RS://" + host + ":6041/?user=root&password=taosdata";
//        Properties properties = new Properties();
//        properties.setProperty("charset", "UTF-8");
//        properties.setProperty("locale", "en_US.UTF-8");
//        properties.setPropert("timezone", "UTC-8");
//        Connection conn = DriverManager.getConnection(url, properties);
//        Statement stmt = conn.createStatement();
////            stmt.execute("create database if not exists restful_test");
////            stmt.execute("use restful_test");
////            stmt.execute("create table restful_test.weather(ts timestamp, temperature float) tags(location nchar(64))");
////            stmt.executeUpdate("insert into t1 using restful_test.weather tags('北京') values(now, 18.2)");
//        ResultSet rs = stmt.executeQuery("select * from taxi.position2 where id=2");
//        ResultSetMetaData meta = rs.getMetaData();
//        return meta;
//    }
}

