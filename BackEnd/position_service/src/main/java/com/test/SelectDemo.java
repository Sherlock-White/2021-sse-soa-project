package com.test;

import java.sql.*;
import java.util.Properties;

/**
 * @author CuiXi
 * @version 1.0
 * @Description: 查询数量
 * @date 2021/3/9 11:48
 */
public class SelectDemo{
    public static void main(String[] args) {
        try {
            // load JDBC-restful driver
            Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
            String jdbcUrl = "jdbc:TAOS-RS://123.56.18.81:6041/test?user=root&password=taosdata&timezone=GMT%2b8";
            Properties properties = new Properties();
            properties.setProperty("charset", "UTF-8");
            properties.setProperty("locale", "en_US.UTF-8");
            properties.setProperty("timezone", "UTC-8");
            Connection conn = DriverManager.getConnection(jdbcUrl, properties);
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select count(*) from test.test01");
            while(resultSet.next()){
                int anInt = resultSet.getInt(1);
            }
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
