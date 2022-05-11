package com.example.userservice.rabbitListener;

import com.alibaba.fastjson.JSONObject;
import com.example.userservice.dao.DriverDAO;
import com.example.userservice.entity.Driver;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class Listener {
    @Autowired
    DriverDAO driverDAO;

    @Transactional
    @RabbitListener(queues = {"ReleaseDriver"})
    public void ReleaseDriver(String msg)
    {
        System.out.println("接收到消息：" + msg);
        JSONObject object=JSONObject.parseObject(msg);
        String driver_id=object.getString("driver_id");
        Driver driver = driverDAO.findByName(driver_id);
        driver.setState((long)0);
        driverDAO.save(driver);
    }

}
