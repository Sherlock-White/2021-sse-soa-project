package com.example.orderservice.rabbitListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.Statement;
import com.example.orderservice.feignClient.PosClient;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.mapper.StatementMapper;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class Listener {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private StatementMapper statementMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private PosClient posClient;

    //final private String key="5d377f781223f6c299389cc8c484c723";

    @Transactional
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "newOrder", durable = "true"),
            exchange = @Exchange(
                    value = "taxiHailing",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.FANOUT
            )
    ))
    public void newOrderListen(String msg){
        System.out.println("接收到消息：" + msg);
        JSONObject object=JSONObject.parseObject(msg);
        String passenger_id=object.getString("passenger_id");
        String departure=object.getString("from");
        String destination=object.getString("to");
        if(existsUnpaidOrder(passenger_id)){return;}
        //Double from_lng=object.getDouble("from_lng");
        //Double from_lat=object.getDouble("from_lat");
        //Double to_lng=object.getDouble("to_lng");
        //Double to_lat=object.getDouble("to_lat");
        //尝试调用高德接口
        Double from_lng=120.332981;
        Double from_lat=31.332981;
        Double to_lng=120.328815;
        Double to_lat=31.271292;

        String from_geocodes=JSON.parseObject(posClient.getPos(departure)).get("geocodes").toString();
        if(!from_geocodes.equals("[]")) {
            String fromPos = JSON.parseObject(JSON.parseArray(from_geocodes).get(0).toString()).get("location").toString();
            String[] fromPosVec2 = fromPos.split(",");
            if (fromPosVec2.length == 2) {
                from_lng = Double.parseDouble(fromPosVec2[0]);
                from_lat = Double.parseDouble(fromPosVec2[1]);
            }
        }
        String to_geocodes=JSON.parseObject(posClient.getPos(destination)).get("geocodes").toString();
        if(!to_geocodes.equals("[]")) {
            String toPos = JSON.parseObject(JSON.parseArray(to_geocodes).get(0).toString()).get("location").toString();
            String[] toPosVec2 = toPos.split(",");
            if (toPosVec2.length == 2) {
                to_lng = Double.parseDouble(toPosVec2[0]);
                to_lat = Double.parseDouble(toPosVec2[1]);
            }
        }
        System.out.println(from_lng);
        System.out.println(from_lat);
        System.out.println(to_lng);
        System.out.println(to_lat);
        //插入新订单
        Order order=new Order();
        String dateNowStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        dateNowStr=dateNowStr.replaceAll("-","");
        dateNowStr=dateNowStr.replaceAll(":","");
        dateNowStr=dateNowStr.replaceAll(" ","");
        StringBuilder order_id=new StringBuilder(dateNowStr);
        while(true) {
            order_id=new StringBuilder(order_id.substring(0,14));
            Random rd = new SecureRandom();
            for (int i = 0; i < 4; i++) {
                int bit = rd.nextInt(10);
                order_id.append(String.valueOf(bit));
            }
            QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
            orderQueryWrapper.eq("order_id",order_id.toString());
            if(orderMapper.selectOne(orderQueryWrapper)==null){
                break;
            }
        }
        order.setOrder_id(order_id.toString());
        order.setPassenger_id(passenger_id);
        order.setDeparture(departure);
        order.setDestination(destination);
        order.setFrom_lng(from_lng);
        order.setFrom_lat(from_lat);
        order.setTo_lng(to_lng);
        order.setTo_lat(to_lat);
        orderMapper.insert(order);
        //插入新流水
        Statement statement=new Statement();
        statement.setOrder_id(order_id.toString());
        statement.setOrder_state("1");
        statement.setStat_time(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
        StringBuilder stat_id= new StringBuilder();
        while(true) {
            stat_id=new StringBuilder();
            Random rd = new SecureRandom();
            for (int i = 0; i < 20; i++) {
                int bit = rd.nextInt(10);
                stat_id.append(String.valueOf(bit));
            }
            QueryWrapper<Statement> statementQueryWrapper=new QueryWrapper<>();
            statementQueryWrapper.eq("stat_id",stat_id.toString());
            if(statementMapper.selectOne(statementQueryWrapper)==null){
                break;
            }
        }
        statement.setStat_id(stat_id.toString());
        statementMapper.insert(statement);
        //通知派单微服务派单
        Map<String,String> message=new HashMap<>();
        message.put("order_id",order_id.toString());
        message.put("passenger_id",passenger_id);
        message.put("from_lng",from_lng.toString());
        message.put("from_lat",from_lat.toString());
        message.put("to_lng",to_lng.toString());
        message.put("to_lat",to_lat.toString());
        rabbitTemplate.convertAndSend("dispatch","",JSON.toJSONString(message));
    }


    @Transactional
    @RabbitListener(queues = {"DispatchResponse"})
    public void orderTakenListen(String msg){
        System.out.println("接收到派单消息：" + msg);
        JSONObject object=JSONObject.parseObject(msg);
        String order_id=object.getString("order_id");
        String driver_id=object.getString("driver_id");
        //定义取消消息
        Map<String,String> message=new HashMap<>();
        message.put("order_id",order_id);
        message.put("driver_id",driver_id);
        //判断是否有该订单
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",order_id);
        Order order=orderMapper.selectOne(orderQueryWrapper);
        if(order==null){
            return;
        }
        //判断是否已派单
        if(order.getDriver_id()==null||order.getDriver_id().equals("")||order.getDriver_id().isEmpty()) {
            UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
            orderUpdateWrapper.eq("order_id", order_id).set("driver_id", driver_id);
            orderMapper.update(order, orderUpdateWrapper);
        }
        //判断是否有已取消的流水，为后续分情况处理，插入新流水
        QueryWrapper<Statement> statementQueryWrapper=new QueryWrapper<>();
        statementQueryWrapper.eq("order_id",order_id).orderByDesc("stat_time");
        List<Statement> statementList=statementMapper.selectList(statementQueryWrapper);
        for(Statement statement:statementList){
            if(!statement.getOrder_state().equals("1")){
                return;
            }
        }
        Statement statement1 = new Statement();
        statement1.setOrder_id(order_id);
        statement1.setOrder_state("2");
        statement1.setStat_time(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
        StringBuilder stat_id = new StringBuilder();
        while (true) {
            stat_id = new StringBuilder();
            Random rd = new SecureRandom();
            for (int i = 0; i < 20; i++) {
                int bit = rd.nextInt(10);
                stat_id.append(String.valueOf(bit));
            }
            QueryWrapper<Statement> statementWrapper = new QueryWrapper<>();
            statementWrapper.eq("stat_id", stat_id.toString());
            if (statementMapper.selectOne(statementWrapper) == null) {
                break;
            }
        }
        statement1.setStat_id(stat_id.toString());
        statementMapper.insert(statement1);

    }

    @Transactional
    @RabbitListener(queues = {"cancelOrderFromHailing"})
    public void cancelOrderFromHailing(String msg){
//        System.out.println("接收到消息：" + msg);
        JSONObject object=JSONObject.parseObject(msg);
        String passenger_id=object.getString("passenger_id");
        //获取最新订单
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("passenger_id",passenger_id).orderByDesc("order_id");
        String order_id;
        Order order;
        if(!orderMapper.selectList(orderQueryWrapper).isEmpty()) {
            order = orderMapper.selectList(orderQueryWrapper).get(0);
            if (order == null) {
                return;
            }
            order_id=order.getOrder_id();
        }else{
            return;
        }
        //定义发送给派单微服务的消息
        Map<String,String> message=new HashMap<>();
        message.put("order_id",order_id);
        message.put("driver_id",order.getDriver_id());
        //再判断可否取消
        QueryWrapper<Statement> statementQueryWrapper=new QueryWrapper<>();
        statementQueryWrapper.eq("order_id",order_id).orderByDesc("stat_time");
        List<Statement> statementList = statementMapper.selectList(statementQueryWrapper);
        boolean sendFlag=false;
        for(Statement statement:statementList){
            if(!statement.getOrder_state().equals("1")&&!statement.getOrder_state().equals("2")){
                sendFlag=false;
                return;
            }else if(statement.getOrder_state().equals("2")){
                sendFlag=true;
            }else{
                sendFlag=true;
            }
        }
        /*
        //插入新流水
        Statement statement=new Statement();
        statement.setOrder_id(order_id);
        statement.setOrder_state("3");
        StringBuilder stat_id= new StringBuilder();
        while(true) {
            stat_id=new StringBuilder();
            Random rd = new SecureRandom();
            for (int i = 0; i < 20; i++) {
                int bit = rd.nextInt(10);
                stat_id.append(String.valueOf(bit));
            }
            QueryWrapper<Statement> statementWrapper=new QueryWrapper<>();
            statementWrapper.eq("stat_id",stat_id.toString());
            if(statementMapper.selectOne(statementWrapper)==null){
                break;
            }
        }
        statement.setStat_id(stat_id.toString());
        statement.setStat_time(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
        statementMapper.insert(statement);
        */
        //已派单状态下需要通知派单微服务释放司机
        if(sendFlag){

            rabbitTemplate.convertAndSend("cancelDispatch","",JSON.toJSONString(message));
        }
    }


    @Transactional
    @RabbitListener(queues = {"CancelFromDispatch"})
    public void cancelOrderFromDispatching(String msg){
//        System.out.println("接收到消息：" + msg);
        JSONObject object=JSONObject.parseObject(msg);
        String passenger_id=object.getString("order_id");
        String driver_id=object.getString("driver_id");
        String state=object.getString("is_distributed");
        //获取最新订单
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",passenger_id).orderByDesc("order_id");
        String order_id;
        Order order;
        if(!orderMapper.selectList(orderQueryWrapper).isEmpty()) {
            order = orderMapper.selectList(orderQueryWrapper).get(0);
            if (order == null) {
                return;
            }
            order_id=order.getOrder_id();
        }else{
            return;
        }
        //再判断可否取消
        QueryWrapper<Statement> statementQueryWrapper=new QueryWrapper<>();
        statementQueryWrapper.eq("order_id",order_id).orderByDesc("stat_time");
        List<Statement> statementList = statementMapper.selectList(statementQueryWrapper);
        for(Statement statement:statementList){
            if(!statement.getOrder_state().equals("1")&&!statement.getOrder_state().equals("2")){
                return;
            }
        }
        //插入新流水
        Statement statement=new Statement();
        statement.setOrder_id(order_id);
        statement.setOrder_state("3");
        StringBuilder stat_id= new StringBuilder();
        while(true) {
            stat_id=new StringBuilder();
            Random rd = new SecureRandom();
            for (int i = 0; i < 20; i++) {
                int bit = rd.nextInt(10);
                stat_id.append(String.valueOf(bit));
            }
            QueryWrapper<Statement> statementWrapper=new QueryWrapper<>();
            statementWrapper.eq("stat_id",stat_id.toString());
            if(statementMapper.selectOne(statementWrapper)==null){
                break;
            }
        }
        statement.setStat_id(stat_id.toString());
        statement.setStat_time(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
        statementMapper.insert(statement);
    }

//    @Transactional
//    @RabbitListener(queues={"orderTaking"})
//    public void passengerOnListen(String msg){
////        System.out.println("接收到消息：" + msg);
//        JSONObject object=JSONObject.parseObject(msg);
//        String order_id=object.getString("order_id");
//        //判断是否有该订单
//        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
//        orderQueryWrapper.eq("order_id",order_id);
//        Order order=orderMapper.selectOne(orderQueryWrapper);
//        if(order==null){
//            return;
//        }
//        //判断是否已派单
//        QueryWrapper<Statement> statementQueryWrapper=new QueryWrapper<>();
//        statementQueryWrapper.eq("order_id",order_id);
//        List<Statement> statementList=statementMapper.selectList(statementQueryWrapper);
//        boolean flag=false;
//        for(Statement statement:statementList){
//            if(statement.getOrder_state().equals("4")||statement.getOrder_state().equals("5")||statement.getOrder_state().equals("3")){
//                return;
//            }
//            else if(statement.getOrder_state().equals("2")){
//                flag=true;
//            }
//        }
//        //插入新流水
//        if(flag){
//            Statement statement1 = new Statement();
//            statement1.setOrder_id(order_id);
//            statement1.setOrder_state("4");
//            statement1.setStat_time(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
//            StringBuilder stat_id = new StringBuilder();
//            while (true) {
//                stat_id = new StringBuilder();
//                Random rd = new SecureRandom();
//                for (int i = 0; i < 20; i++) {
//                    int bit = rd.nextInt(10);
//                    stat_id.append(String.valueOf(bit));
//                }
//                QueryWrapper<Statement> statementWrapper = new QueryWrapper<>();
//                statementWrapper.eq("stat_id", stat_id.toString());
//                if (statementMapper.selectOne(statementWrapper) == null) {
//                    break;
//                }
//            }
//            statement1.setStat_id(stat_id.toString());
//            statementMapper.insert(statement1);
//        }
//
//    }

    @Transactional
    @RabbitListener(queues="orderTaking")
    public void passengerListen(String msg){
        System.out.println("接收到消息：" + msg);
        JSONObject object=JSONObject.parseObject(msg);
        if(object.getString("state").equals("6")) {
            String order_id = object.getString("order_id");
            //Double price = 25.50;
            //判断是否有该订单
            QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
            orderQueryWrapper.eq("order_id", order_id);
            Order order = orderMapper.selectOne(orderQueryWrapper);
            if (order == null) {
                return;
            }
            //判断是否已开始
            QueryWrapper<Statement> statementQueryWrapper = new QueryWrapper<>();
            statementQueryWrapper.eq("order_id", order_id);
            List<Statement> statementList = statementMapper.selectList(statementQueryWrapper);
            boolean flag = false;
            for (Statement statement : statementList) {
                if (statement.getOrder_state().equals("6") || statement.getOrder_state().equals("3")) {
                    return;
                } else if (statement.getOrder_state().equals("5")) {
                    flag = true;
                }
            }
            //插入新流水
            if (flag) {
                Statement statement1 = new Statement();
                statement1.setOrder_id(order_id);
                statement1.setOrder_state("6");
                statement1.setStat_time(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
                StringBuilder stat_id;
                while (true) {
                    stat_id = new StringBuilder();
                    Random rd = new SecureRandom();
                    for (int i = 0; i < 20; i++) {
                        int bit = rd.nextInt(10);
                        stat_id.append(String.valueOf(bit));
                    }
                    QueryWrapper<Statement> statementWrapper = new QueryWrapper<>();
                    statementWrapper.eq("stat_id", stat_id.toString());
                    if (statementMapper.selectOne(statementWrapper) == null) {
                        break;
                    }
                }
                statement1.setStat_id(stat_id.toString());
                statementMapper.insert(statement1);
            }
        }
        //乘客上车
        else if(object.getString("state").equals("4")){
            String order_id=object.getString("order_id");
            //判断是否有该订单
            QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
            orderQueryWrapper.eq("order_id",order_id);
            Order order=orderMapper.selectOne(orderQueryWrapper);
            if(order==null){
                return;
            }
            //判断是否已派单
            QueryWrapper<Statement> statementQueryWrapper=new QueryWrapper<>();
            statementQueryWrapper.eq("order_id",order_id);
            List<Statement> statementList=statementMapper.selectList(statementQueryWrapper);
            boolean flag=false;
            for(Statement statement:statementList){
                if(statement.getOrder_state().equals("6")||statement.getOrder_state().equals("4")||statement.getOrder_state().equals("5")||statement.getOrder_state().equals("3")){
                    return;
                }
                else if(statement.getOrder_state().equals("2")){
                    flag=true;
                }
            }
            //插入新流水
            if(flag){
                Statement statement1 = new Statement();
                statement1.setOrder_id(order_id);
                statement1.setOrder_state("4");
                statement1.setStat_time(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
                StringBuilder stat_id = new StringBuilder();
                while (true) {
                    stat_id = new StringBuilder();
                    Random rd = new SecureRandom();
                    for (int i = 0; i < 20; i++) {
                        int bit = rd.nextInt(10);
                        stat_id.append(String.valueOf(bit));
                    }
                    QueryWrapper<Statement> statementWrapper = new QueryWrapper<>();
                    statementWrapper.eq("stat_id", stat_id.toString());
                    if (statementMapper.selectOne(statementWrapper) == null) {
                        break;
                    }
                }
                statement1.setStat_id(stat_id.toString());
                statementMapper.insert(statement1);
            }
        }
    }

    //是否存在未结束的订单
    private boolean existsUnpaidOrder(String passenger_id){
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("passenger_id",passenger_id).orderByDesc("order_id");
        if(orderMapper.selectList(orderQueryWrapper).isEmpty()){return false;}
        Order order=orderMapper.selectList(orderQueryWrapper).get(0);
        if(order==null){
            return false;
        }
        QueryWrapper<Statement> statementQueryWrapper=new QueryWrapper<>();
        statementQueryWrapper.eq("order_id",order.getOrder_id()).orderByDesc("stat_time");
        Statement statement=statementMapper.selectList(statementQueryWrapper).get(0);
        if(!statement.getOrder_state().equals("3")&&!statement.getOrder_state().equals("6")){
            return true;
        }else{
            return false;
        }
    }
}
