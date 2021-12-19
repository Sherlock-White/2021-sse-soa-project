package com.example.orderservice.rabbitListener;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.Statement;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.mapper.StatementMapper;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class Listener {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private StatementMapper statementMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.test.queue", durable = "true"),
            exchange = @Exchange(
                    value = "spring.test.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.DIRECT
            ),
            key = {"#.#"}))
    public void newOrderListen(String msg){
//        System.out.println("接收到消息：" + msg);
        JSONObject object=JSONObject.parseObject(msg);
        String passenger_id=object.getString("passenger_id");
        String departure=object.getString("departure");
        String destination=object.getString("destination");
        //插入新订单
        Order order=new Order();
        StringBuilder order_id=new StringBuilder();
        order_id.append(java.time.Year.now().toString());
        order_id.append(java.time.MonthDay.now().toString().replaceAll("-",""));
        if(order_id.toString().length()<"20210101".length()){
            order_id.insert(4,"0");
        }
        while(true) {
            order_id=new StringBuilder(order_id.substring(0,8));
            Random rd = new SecureRandom();
            for (int i = 0; i < 8; i++) {
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
        orderMapper.insert(order);
        //插入新流水
        Statement statement=new Statement();
        statement.setOrder_id(order_id.toString());
        statement.setOrder_state("已创建");
        statement.setStat_time(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
        StringBuilder stat_id= new StringBuilder();
        while(true) {
            stat_id=new StringBuilder();
            Random rd = new SecureRandom();
            for (int i = 0; i < 16; i++) {
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
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.test.queue", durable = "true"),
            exchange = @Exchange(
                    value = "spring.test.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.DIRECT
            ),
            key = {"#.#"}))
    public void orderTakenListen(String msg){
//        System.out.println("接收到消息：" + msg);
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
        statementQueryWrapper.eq("order_id",order_id);
        List<Statement> statementList=statementMapper.selectList(statementQueryWrapper);
        boolean flag=false;
        for(Statement statement:statementList){
            if(!statement.getOrder_state().equals("已创建")&&!statement.getOrder_state().equals("已取消")){
                return;
            }
            else if(statement.getOrder_state().equals("已取消")){
                flag=false;
                break;
            }
            else if(statement.getOrder_state().equals("已创建")){
                flag=true;
            }
        }
        if(flag) {
            Statement statement1 = new Statement();
            statement1.setOrder_id(order_id);
            statement1.setOrder_state("已派单");
            statement1.setStat_time(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
            StringBuilder stat_id = new StringBuilder();
            while (true) {
                stat_id = new StringBuilder();
                Random rd = new SecureRandom();
                for (int i = 0; i < 16; i++) {
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
            //读取取消订单队列消息，如果有取消需要通知派单微服务释放司机


        }else{//通知派单微服务释放司机
            rabbitTemplate.convertAndSend(message);
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.test.queue", durable = "true"),
            exchange = @Exchange(
                    value = "spring.test.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.DIRECT
            ),
            key = {"#.#"}))
    public void cancelListen(String msg){
//        System.out.println("接收到消息：" + msg);
        JSONObject object=JSONObject.parseObject(msg);
        String order_id=object.getString("order_id");
        //先判断订单是否存在
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",order_id);
        Order order=orderMapper.selectOne(orderQueryWrapper);
        if(order==null){
            return;
        }
        //定义发送给派单微服务的消息
        Map<String,String> message=new HashMap<>();
        message.put("order_id",order_id);
        message.put("driver_id",order.getDriver_id());
        //再判断可否取消
        QueryWrapper<Statement> statementQueryWrapper=new QueryWrapper<>();
        statementQueryWrapper.eq("order_id",order_id);
        List<Statement> statementList=statementMapper.selectList(statementQueryWrapper);
        boolean sendFlag=false;
        for(Statement statement:statementList){
            if(!statement.getOrder_state().equals("已创建")&&!statement.getOrder_state().equals("已派单")){
                //sendFlag=false;
                return;
            }else{
                sendFlag=true;
            }
        }
        //插入新流水
        Statement statement=new Statement();
        statement.setOrder_id(order_id);
        statement.setOrder_state("已取消");
        StringBuilder stat_id= new StringBuilder();
        while(true) {
            stat_id=new StringBuilder();
            Random rd = new SecureRandom();
            for (int i = 0; i < 16; i++) {
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
        //已派单状态下需要通知派单微服务释放司机
        if(sendFlag){
            rabbitTemplate.convertAndSend(message);
        }
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.test.queue", durable = "true"),
            exchange = @Exchange(
                    value = "spring.test.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.DIRECT
            ),
            key = {"#.#"}))
    public void passengerOnListen(String msg){
//        System.out.println("接收到消息：" + msg);
        JSONObject object=JSONObject.parseObject(msg);
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
            if(statement.getOrder_state().equals("已开始")||statement.getOrder_state().equals("已结束")||statement.getOrder_state().equals("已取消")){
                return;
            }
            else if(statement.getOrder_state().equals("已派单")){
                flag=true;
            }
        }
        //插入新流水
        if(flag){
            Statement statement1 = new Statement();
            statement1.setOrder_id(order_id);
            statement1.setOrder_state("已开始");
            statement1.setStat_time(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
            StringBuilder stat_id = new StringBuilder();
            while (true) {
                stat_id = new StringBuilder();
                Random rd = new SecureRandom();
                for (int i = 0; i < 16; i++) {
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

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.test.queue", durable = "true"),
            exchange = @Exchange(
                    value = "spring.test.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.DIRECT
            ),
            key = {"#.#"}))
    public void passengerOffListen(String msg){
//        System.out.println("接收到消息：" + msg);
        JSONObject object=JSONObject.parseObject(msg);
        String order_id=object.getString("order_id");
        Double price=object.getDouble("price");
        //判断是否有该订单
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",order_id);
        Order order=orderMapper.selectOne(orderQueryWrapper);
        if(order==null){
            return;
        }
        //判断是否已开始
        QueryWrapper<Statement> statementQueryWrapper=new QueryWrapper<>();
        statementQueryWrapper.eq("order_id",order_id);
        List<Statement> statementList=statementMapper.selectList(statementQueryWrapper);
        boolean flag=false;
        for(Statement statement:statementList){
            if(statement.getOrder_state().equals("已结束")||statement.getOrder_state().equals("已取消")){
                return;
            }
            else if(statement.getOrder_state().equals("已开始")){
                flag=true;
            }
        }
        //修改订单金额字段，插入新流水
        if(flag){
            //登入金额
            UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
            orderUpdateWrapper.eq("order_id", order_id).set("price", price);
            orderMapper.update(order, orderUpdateWrapper);
            //插入新流水
            Statement statement1 = new Statement();
            statement1.setOrder_id(order_id);
            statement1.setOrder_state("已结束");
            statement1.setStat_time(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
            StringBuilder stat_id = new StringBuilder();
            while (true) {
                stat_id = new StringBuilder();
                Random rd = new SecureRandom();
                for (int i = 0; i < 16; i++) {
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
