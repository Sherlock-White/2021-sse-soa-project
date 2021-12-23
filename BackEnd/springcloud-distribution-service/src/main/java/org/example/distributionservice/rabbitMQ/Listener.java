package org.example.distributionservice.rabbitMQ;

//import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.juli.logging.Log;
import org.example.distributionservice.service.DistributionService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class Listener {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //@RabbitListener(queues = {"dispatch"})
    @Transactional
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "dispatch", durable = "true"),
            exchange = @Exchange(
                    value = "dispatch",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.FANOUT
            )
    ))
    public void newDistributionListen(String msg){

        JSONObject object = JSONObject.fromObject(msg);
        System.out.println(object.toString());
        String order_id=object.getString("order_id");
        String passenger_id=object.getString("passenger_id");
        String from_lng=object.getString("from_lng");   //经度
        String from_lat=object.getString("from_lat");   //纬度
        String to_lng=object.getString("to_lng");
        String to_lat=object.getString("to_lat");

        String[][] passenger ={{passenger_id,from_lat,from_lng}};
        String[][] driver ={
                {"driver1","31.286428","121.212090"},
                {"driver2","31.194202","121.320655"}};

        DistributionService distributionService = new DistributionService(1,2,passenger,driver);
        int[] result = distributionService.distribute();
        System.out.println("派单结果"+result[0]);

        //通知订单微服务更新状态
        Map<String,String> message=new HashMap<>();
        message.put("order_id",order_id);
        message.put("driver_id","driver"+result[0]);
        message.put("is_distributed","2");//1表示未派单，2表示已派单
        System.out.println(message);

        rabbitTemplate.convertAndSend("DispatchResponse","",JSON.toJSONString(message));
    }
}