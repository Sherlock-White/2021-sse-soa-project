
package org.example.distributionservice.rabbitMQ;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
//import net.sf.json.JSONObject;

import org.example.distributionservice.feignClient.PosClient;
import org.example.distributionservice.service.DistributionService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Listener {
    @Autowired
    private RabbitTemplate rabbitTemplate3;//发送给用户微服务改变司机状态
    @Autowired
    private RabbitTemplate rabbitTemplate2;//订单取消返回订单微服务
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Qualifier("org.example.distributionservice.feignClient.PosClient")
    @Autowired
    private PosClient posClient;
    //@RabbitListener(queues = {"dispatch"})
    @Transactional
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "dispatch", durable = "true"),
//            exchange = @Exchange(
//                    value = "dispatch",
//                    ignoreDeclarationExceptions = "true",
//                    type = ExchangeTypes.FANOUT
//            )
//    ))
    @RabbitListener(queues = {"dispatch"})
    public void newDistributionListen(String msg){
        System.out.println(msg);
        //获取订单信息
        JSONObject object = JSONObject.parseObject(msg);
        System.out.println(object.toString());
        String order_id=object.getString("order_id");
        String passenger_id=object.getString("passenger_id");
        String from_lng=object.getString("from_lng");   //经度
        String from_lat=object.getString("from_lat");   //纬度
        String to_lng=object.getString("to_lng");
        String to_lat=object.getString("to_lat");
        String[][] passenger ={{passenger_id,from_lat,from_lng}};
        /*String[][] driver ={
                {"driver1","31.286428","121.212090"},
                {"driver2","31.194202","121.320655"}};*/

        //获取周围司机
//        List<Object> driverList = posClient.getNearDriverList();
//        System.out.println(driverList.toString());
        //{ts=2021-07-06 15:04:20.0, id=247, jing=30.73341, wei=104.04514}
//        String[][] driver = new String[driverList.size()/5][3];
//        for(int i=0;i<driverList.size();i=i+5){
//            Object obj = driverList.get(i);
//            Map map = JSONObject.parseObject(JSONObject.toJSONString(obj), Map.class);
//            driver[i/5][0] = "driver"+map.get("id");
//            driver[i/5][1] = map.get("jing").toString();//是纬度
//            driver[i/5][2] = map.get("wei").toString(); //是经度
//        }
        //System.out.println(driver);

//        DistributionService distributionService = new DistributionService(1,driverList.size()/5,passenger,driver);
//        int[] result = distributionService.distribute();
//        System.out.println("派单结果"+result[0]);

        //通知订单微服务更新状态
        Map<String,String> message=new HashMap<>();
        message.put("order_id",order_id);
//        message.put("driver_id",driver[result[0]][0]);
        message.put("driver_id","sxy");       //测试直接返回247
        message.put("is_distributed","2");//1表示未派单，2表示已派单
        System.out.println(message);

        rabbitTemplate.convertAndSend("DispatchResponse","",JSON.toJSONString(message));
    }
    @Transactional
    @RabbitListener(queues={"cancelDispatch"})
    public void cancelOrderListen(Message messagePar){
        String msg=new String(messagePar.getBody());
        System.out.println("Start"+msg);

        //获取订单信息'
        JSONObject object=JSONObject.parseObject(msg);
        System.out.println(object.toString());
        String order_id=object.getString("order_id");
        String driver_id=object.getString("driver_id");
        //判断是否改变司机状态
        if(driver_id.equals("")){
            System.out.println(driver_id + "is null");
            return;
        }
        Map<String,String> drivermessage=new HashMap<>();
        drivermessage.put("driver_id",driver_id);
        rabbitTemplate3.convertAndSend("ReleaseDriver","",JSON.toJSONString(drivermessage));
        //通知订单微服务取消订单
        Map<String,String> message=new HashMap<>();
        message.put("order_id",order_id);
        message.put("driver_id",driver_id);
        message.put("is_distributed","1");//1表示未派单，2表示已派单
        System.out.println(message);
        rabbitTemplate2.convertAndSend("CancelFromDispatch","",JSON.toJSONString(message));
    }
}
