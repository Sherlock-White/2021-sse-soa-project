package com.example.userservice.rabbitListener;

import com.alibaba.fastjson.JSONObject;
import com.example.userservice.dao.DriverDAO;
import com.example.userservice.entity.Driver;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Listener {
    @Autowired
    DriverDAO driverDAO;

    @Transactional
    @RabbitListener(queues = {"ReleaseDriver"})
    public void ReleaseDriver(org.springframework.amqp.core.Message message)
    {
        String msg = new String(message.getBody());
        System.out.println("接收到消息：" + msg);
        JSONObject object=JSONObject.parseObject(msg);
        if(!object.getString("driver_id").isEmpty()&&!object.getString("driver_id").equals("")) {
            String driver_id = object.getString("driver_id");
            Driver driver = driverDAO.findByName(driver_id);
            if(driver!=null) {
                driver.setState((long) 0);
                driverDAO.save(driver);
            }
        }
    }

}
