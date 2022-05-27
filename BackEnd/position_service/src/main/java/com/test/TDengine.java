package com.test;

import java.sql.*;
import java.util.Properties;

/**
 * 查询
 */
public class TDengine {

    public static void main(String[] args) {
        try {
            Class.forName("com.taosdata.jdbc.TSDBDriver");
            String url = "jdbc:TAOS://:/test?user=root&password=taosdata&timezone=GMT%2b8";
            Properties properties = new Properties();
            properties.setProperty("charset", "UTF-8");
            properties.setProperty("locale", "en_US.UTF-8");
            properties.setProperty("timezone", "UTC-8");
            Connection conn = DriverManager.getConnection(url, properties);
            Statement stmt = conn.createStatement();
            Timestamp ts = null;
            int id = 0;

            stmt.execute("use test");
            ResultSet rs = stmt.executeQuery("select * from supp limit 10");
            while (rs.next()) {
                ts = rs.getTimestamp(1);
                id = rs.getInt(2);
                System.out.println(ts +"-------------"+id);
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


}
