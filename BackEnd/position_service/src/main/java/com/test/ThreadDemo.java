package com.test;

import java.sql.*;
import java.util.Properties;

/**
 * @author CuiXi
 * @version 1.0
 * @Description: 添加
 * @date 2021/3/9 11:48
 */
public class ThreadDemo {
    public static boolean flag = true;

    public static void main(String[] args) {
        Insert in = new Insert();
        for (int i = 0; i < 100; i++) {
            new Thread(in).start();
        }
//        new Thread(in).start();
//        new Thread(in).start();
    }


}

class Insert implements Runnable {


    @Override
    public void run() {
        try {
            // load JDBC-restful driver
            Class.forName("com.taosdata.jdbc.TSDBDriver");
            String jdbcUrl = "jdbc:TAOS://:/test?user=root&password=taosdata&timezone=GMT%2b8";
            Properties properties = new Properties();
            properties.setProperty("charset", "UTF-8");
            properties.setProperty("locale", "en_US.UTF-8");
            properties.setProperty("timezone", "UTC-8");
            Connection conn = DriverManager.getConnection(jdbcUrl, properties);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("use test");
//            stmt.executeUpdate("create table if not exists test02 (ts timestamp, id int)");
//            stmt.executeUpdate("CREATE TABLE testSupp (ts timestamp,temperature int, humidity float) TAGS (location binary(64), groupdId int);");
            stmt.executeUpdate("INSERT INTO test001 USING testSupp TAGS (Beijng.Chaoyang, 1) VALUES (now, 1,2.22)");
//            int affectedRows = stmt.executeUpdate("insert into test.test02 values(now, 1)");
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
