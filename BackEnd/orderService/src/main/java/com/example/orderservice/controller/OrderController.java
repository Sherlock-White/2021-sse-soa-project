package com.example.orderservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.Statement;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.mapper.StatementMapper;
import com.example.orderservice.request.*;
import com.example.orderservice.result.*;
import com.example.orderservice.feignClient.*;
import com.sun.net.httpserver.Authenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.zaxxer.hikari.util.ClockSource.toMillis;

@Api(value = "订单微服务")
@RestController
@CrossOrigin("*")
@RequestMapping()
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private StatementMapper statementMapper;
    @Autowired
    private UserClient userClient;
    @Autowired
    private PosClient posClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ApiOperation(value = "获取乘客进行中的订单")
    @GetMapping("/v1/passengers/{passenger_id}/orders/current")
    public TaxiOrder getCurrentOrderForPassenger(@PathVariable String passenger_id){
        TaxiOrder taxiOrder=new TaxiOrder();
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("passenger_id",passenger_id).orderByDesc("order_id");
        Order order=orderMapper.selectList(orderQueryWrapper).get(0);
        if(order!=null) {
            taxiOrder.setOrder_id(order.getOrder_id());
            taxiOrder.setPassenger_id(order.getPassenger_id());
            taxiOrder.setDriver_id(order.getDriver_id());
            taxiOrder.setDeparture(order.getDeparture());
            taxiOrder.setDestination(order.getDestination());
            taxiOrder.setFrom_lng(order.getFrom_lng());
            taxiOrder.setFrom_lat(order.getFrom_lat());
            taxiOrder.setTo_lng(order.getTo_lng());
            taxiOrder.setTo_lat(order.getTo_lat());

            Result result= userClient.findPassengerById(order.getPassenger_id());
            Map<String,String> resultMap=(Map<String, String>) result.getObject();
            taxiOrder.setPassenger_phone(resultMap.get("phone"));
            result=userClient.findDriverById(order.getDriver_id());
            resultMap=(Map<String, String>) result.getObject();
            taxiOrder.setDriver_phone(resultMap.get("phone"));

            //查询流水
            QueryWrapper<Statement> statementQueryWrapper = new QueryWrapper<>();
            statementQueryWrapper.eq("order_id", order.getOrder_id()).orderByDesc("stat_time");
            List<Statement> statementList = statementMapper.selectList(statementQueryWrapper);
            taxiOrder.setOrder_state(statementList.get(0).getOrder_state());
            for (Statement statement : statementList) {
                if (statement.getOrder_state().equals("5") || statement.getOrder_state().equals("3")) {
                    taxiOrder.setEnd_time(statement.getStat_time());
                } else if (statement.getOrder_state().equals("2")) {
                    taxiOrder.setStart_time(statement.getStat_time());
                }
            }
        }
        if(!taxiOrder.getOrder_state().equals("5")&&!taxiOrder.getOrder_state().equals("3")) {
            return taxiOrder;
        }
        return null;
    }

    @ApiOperation(value ="按照时间顺序获取某位乘客的所有订单")
    @GetMapping("/v1/passengers/{passenger_id}/orders")
    public List<TaxiOrder> getOrdersForPassenger(@PathVariable String passenger_id){
        List<TaxiOrder> orderList=new ArrayList<TaxiOrder>();
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("passenger_id",passenger_id).orderByDesc("order_id");
        List<Order> tempList=orderMapper.selectList(orderQueryWrapper);
        for(Order order:tempList){
            TaxiOrder taxiOrder=new TaxiOrder();
            taxiOrder.setOrder_id(order.getOrder_id());
            taxiOrder.setPassenger_id(order.getPassenger_id());
            taxiOrder.setDriver_id(order.getDriver_id());
            taxiOrder.setDeparture(order.getDeparture());
            taxiOrder.setDestination(order.getDestination());
            taxiOrder.setPrice(order.getPrice());
            taxiOrder.setFrom_lng(order.getFrom_lng());
            taxiOrder.setFrom_lat(order.getFrom_lat());
            taxiOrder.setTo_lng(order.getTo_lng());
            taxiOrder.setTo_lat(order.getTo_lat());

            Result result= userClient.findPassengerById(order.getPassenger_id());
            Map<String,String> resultMap=(Map<String, String>) result.getObject();
            taxiOrder.setPassenger_phone(resultMap.get("phone"));
            result = userClient.findDriverById(order.getDriver_id());
            resultMap = (Map<String, String>) result.getObject();
            taxiOrder.setDriver_phone(resultMap.get("phone"));
            //查询流水
            QueryWrapper<Statement> statementQueryWrapper=new QueryWrapper<>();
            statementQueryWrapper.eq("order_id",order.getOrder_id()).orderByDesc("stat_time");
            List<Statement> statementList=statementMapper.selectList(statementQueryWrapper);
            taxiOrder.setOrder_state(statementList.get(0).getOrder_state());
            //获取订单开始时间
            Statement stat;
            statementQueryWrapper.clear();
            statementQueryWrapper.eq("order_id",order.getOrder_id()).eq("order_state","4");
            stat=statementMapper.selectOne(statementQueryWrapper);
            if(stat!=null){taxiOrder.setStart_time(stat.getStat_time());}
            //获取订单结束时间
            statementQueryWrapper.clear();
            statementQueryWrapper.eq("order_id",order.getOrder_id()).eq("order_state","5");
            stat=statementMapper.selectOne(statementQueryWrapper);
            if(stat!=null){taxiOrder.setEnd_time(stat.getStat_time());}
            statementQueryWrapper.clear();
            statementQueryWrapper.eq("order_id",order.getOrder_id()).eq("order_state","3");
            stat=statementMapper.selectOne(statementQueryWrapper);
            if(stat!=null){taxiOrder.setEnd_time(stat.getStat_time());}
            orderList.add(taxiOrder);
        }
        return orderList;
        //return tmp.getOrdersForPassenger(passenger_id);
    }

    @ApiOperation(value ="按照时间顺序获取某位司机的所有订单")
    @GetMapping("/v1/drivers/{driver_id}/orders")
    public List<TaxiOrder> getOrdersForDriver(@PathVariable String driver_id){
        List<TaxiOrder> orderList=new ArrayList<TaxiOrder>();
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("driver_id",driver_id).orderByDesc("order_id");
        List<Order> tempList=orderMapper.selectList(orderQueryWrapper);
        for(Order order:tempList){
            TaxiOrder taxiOrder=new TaxiOrder();
            taxiOrder.setOrder_id(order.getOrder_id());
            taxiOrder.setPassenger_id(order.getPassenger_id());
            taxiOrder.setDriver_id(order.getDriver_id());
            taxiOrder.setDeparture(order.getDeparture());
            taxiOrder.setDestination(order.getDestination());
            taxiOrder.setPrice(order.getPrice());
            taxiOrder.setFrom_lng(order.getFrom_lng());
            taxiOrder.setFrom_lat(order.getFrom_lat());
            taxiOrder.setTo_lng(order.getTo_lng());
            taxiOrder.setTo_lat(order.getTo_lat());

            Result result= userClient.findPassengerById(order.getPassenger_id());
            Map<String,String> resultMap=(Map<String, String>) result.getObject();
            taxiOrder.setPassenger_phone(resultMap.get("phone"));
            result=userClient.findDriverById(order.getDriver_id());
            resultMap=(Map<String, String>) result.getObject();
            taxiOrder.setDriver_phone(resultMap.get("phone"));
            //查询流水
            QueryWrapper<Statement> statementQueryWrapper=new QueryWrapper<>();
            statementQueryWrapper.eq("order_id",order.getOrder_id()).orderByDesc("stat_time");
            List<Statement> statementList=statementMapper.selectList(statementQueryWrapper);
            taxiOrder.setOrder_state(statementList.get(0).getOrder_state());
            //获取订单开始时间
            Statement stat;
            statementQueryWrapper.clear();
            statementQueryWrapper.eq("order_id",order.getOrder_id()).eq("order_state","4");
            stat=statementMapper.selectOne(statementQueryWrapper);
            if(stat!=null){taxiOrder.setStart_time(stat.getStat_time());}
            //获取订单结束时间
            statementQueryWrapper.clear();
            statementQueryWrapper.eq("order_id",order.getOrder_id()).eq("order_state","5");
            stat=statementMapper.selectOne(statementQueryWrapper);
            if(stat!=null){taxiOrder.setEnd_time(stat.getStat_time());}
            statementQueryWrapper.clear();
            statementQueryWrapper.eq("order_id",order.getOrder_id()).eq("order_state","3");
            stat=statementMapper.selectOne(statementQueryWrapper);
            if(stat!=null){taxiOrder.setEnd_time(stat.getStat_time());}
            orderList.add(taxiOrder);
        }
        return orderList;
    }

    @ApiOperation(value ="根据订单id获取订单")
    @GetMapping("/v1/orders/{order_id}")
    public TaxiOrder getOrdersByID(@PathVariable String order_id){
        TaxiOrder taxiOrder=new TaxiOrder();
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",order_id);
        Order order=orderMapper.selectOne(orderQueryWrapper);
        taxiOrder.setOrder_id(order.getOrder_id());
        taxiOrder.setPassenger_id(order.getPassenger_id());
        taxiOrder.setDriver_id(order.getDriver_id());
        taxiOrder.setDeparture(order.getDeparture());
        taxiOrder.setDestination(order.getDestination());
        taxiOrder.setPrice(order.getPrice());
        taxiOrder.setFrom_lng(order.getFrom_lng());
        taxiOrder.setFrom_lat(order.getFrom_lat());
        taxiOrder.setTo_lng(order.getTo_lng());
        taxiOrder.setTo_lat(order.getTo_lat());

        Result result= userClient.findPassengerById(order.getPassenger_id());
        Map<String,String> resultMap=(Map<String, String>) result.getObject();
        taxiOrder.setPassenger_phone(resultMap.get("phone"));
        result=userClient.findDriverById(order.getDriver_id());
        resultMap=(Map<String, String>) result.getObject();
        taxiOrder.setDriver_phone(resultMap.get("phone"));
        //查询流水
        QueryWrapper<Statement> statementQueryWrapper=new QueryWrapper<>();
        statementQueryWrapper.eq("order_id",order.getOrder_id()).orderByDesc("stat_time");
        List<Statement> statementList=statementMapper.selectList(statementQueryWrapper);
        taxiOrder.setOrder_state(statementList.get(0).getOrder_state());
        //获取订单开始时间
        Statement stat;
        statementQueryWrapper.clear();
        statementQueryWrapper.eq("order_id",order.getOrder_id()).eq("order_state","4");
        stat=statementMapper.selectOne(statementQueryWrapper);
        if(stat!=null){taxiOrder.setStart_time(stat.getStat_time());}
        //获取订单结束时间
        statementQueryWrapper.clear();
        statementQueryWrapper.eq("order_id",order.getOrder_id()).eq("order_state","5");
        stat=statementMapper.selectOne(statementQueryWrapper);
        if(stat!=null){taxiOrder.setEnd_time(stat.getStat_time());}
        statementQueryWrapper.clear();
        statementQueryWrapper.eq("order_id",order.getOrder_id()).eq("order_state","3");
        stat=statementMapper.selectOne(statementQueryWrapper);
        if(stat!=null){taxiOrder.setEnd_time(stat.getStat_time());}
        return taxiOrder;
    }

    /*
    @PostMapping("/cancelOrder")
    public boolean cancelOrder(@RequestParam String order_id){
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("order_id",order_id);
        if(orderMapper.selectOne(orderQueryWrapper)==null){
            return false;
        }
        QueryWrapper<Statement> statementQueryWrapper=new QueryWrapper<>();
        statementQueryWrapper.eq("order_id",order_id);
        List<Statement> statementList=statementMapper.selectList(statementQueryWrapper);
        boolean sendFlag=false;
        for(Statement statement:statementList){
            if(!statement.getOrder_state().equals("1")&&!statement.getOrder_state().equals("2")){
                //sendFlag=false;
                return false;
            }else if(statement.getOrder_state().equals("2")){
                sendFlag=true;
            }
        }
        Statement statement=new Statement();
        statement.setOrder_id(order_id);
        statement.setOrder_state("3");
        //插入流水
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
        //已派单状态下需要通知派单微服务释放司机
        if(sendFlag){

        }
        return true;
    }


    @Transactional
    @PostMapping("/newOrder")
    public void newOrder(@RequestParam("passenger_id") String passenger_id,
                         @RequestParam("departure") String departure,
                         @RequestParam("destination") String destination){
        if(this.existsUnpaidOrder(passenger_id)){return;}
        //尝试调用高德接口
        String fromPos = JSON.parseObject(JSON.parseArray(JSON.parseObject(posClient.getPos(departure)).get("geocodes").toString()).get(0).toString()).get("location").toString();
        String[] fromPosVec2=fromPos.split(",");
        Double from_lng=Double.parseDouble(fromPosVec2[0]);
        Double from_lat=Double.parseDouble(fromPosVec2[1]);
        String toPos = JSON.parseObject(JSON.parseArray(JSON.parseObject(posClient.getPos(destination)).get("geocodes").toString()).get(0).toString()).get("location").toString();
        String[] toPosVec2=toPos.split(",");
        Double to_lng=Double.parseDouble(toPosVec2[0]);
        Double to_lat=Double.parseDouble(toPosVec2[1]);
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
        message.put("departure",departure);
        message.put("destination",destination);
        rabbitTemplate.convertAndSend("dispatch","",message);
    }

    @PostMapping("/orderTaken")
    public void orderTaken(@RequestParam("order_id") String order_id,@RequestParam("driver_id") String driver_id){
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
            if(!statement.getOrder_state().equals("1")&&!statement.getOrder_state().equals("3")){
                return;
            }
            else if(statement.getOrder_state().equals("3")){
                flag=false;
                break;
            }
            else if(statement.getOrder_state().equals("1")){
                flag=true;
            }
        }
        if(flag) {
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
            //读取取消订单队列消息，如果有取消需要通知派单微服务释放司机
        }else{//通知派单微服务释放司机

        }
    }

    @PostMapping("/passengerOn")
    public void passengerOn(@RequestParam("order_id") String order_id){
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
            if(statement.getOrder_state().equals("4")||statement.getOrder_state().equals("5")||statement.getOrder_state().equals("3")){
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

    @PostMapping("/passengerOff")
    public void passengerOff(@RequestParam("order_id") String order_id,@RequestParam("price") Double price){
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
            if(statement.getOrder_state().equals("5")||statement.getOrder_state().equals("3")){
                return;
            }
            else if(statement.getOrder_state().equals("4")){
                flag=true;
            }
        }
        //修改订单金额字段，插入新流水
        if(flag){
            UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
            orderUpdateWrapper.eq("order_id", order_id).set("price", price);
            orderMapper.update(order, orderUpdateWrapper);

            Statement statement1 = new Statement();
            statement1.setOrder_id(order_id);
            statement1.setOrder_state("5");
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

    private boolean existsUnpaidOrder(String passenger_id){
        QueryWrapper<Order> orderQueryWrapper=new QueryWrapper<>();
        orderQueryWrapper.eq("passenger_id",passenger_id).orderByDesc("order_id");
        Order order=orderMapper.selectList(orderQueryWrapper).get(0);
        if(order==null){
            return false;
        }
        QueryWrapper<Statement> statementQueryWrapper=new QueryWrapper<>();
        statementQueryWrapper.eq("order_id",order.getOrder_id()).orderByDesc("stat_time");
        Statement statement=statementMapper.selectList(statementQueryWrapper).get(0);
        if(!statement.getOrder_state().equals("3")&&!statement.getOrder_state().equals("5")){
            return true;
        }else{
            return false;
        }
    }*/
}
