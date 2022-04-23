package com.taxi.taxiservice.controller;

import com.taxi.taxiservice.clients.OrderClient;
import com.taxi.taxiservice.entity.CancelRequest;
import com.taxi.taxiservice.entity.Order;
import com.taxi.taxiservice.entity.TexiRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@CrossOrigin("*")
@Api(value = "打车")
public class TaxiHailingController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private OrderClient orderClient;

    @ApiOperation(value = "用户打车请求")
    @PostMapping("v1/hailing")
    public String hail(@RequestParam(value = "passenger_id") String passenger_id,
                       @RequestParam(value = "from") String from,
                       @RequestParam(value = "from_lng") Double from_lng,
                       @RequestParam(value = "from_lat") Double from_lat,
                       @RequestParam(value = "to") String to,
                       @RequestParam(value = "to_lng") Double to_lng,
                       @RequestParam(value = "to_lat") Double to_lat){
        rabbitTemplate.convertAndSend("taxiHailing","",
                new TexiRequest(passenger_id,from,from_lng,from_lat,to,to_lng,to_lat));
        return "hi";
    }

    @ApiOperation(value = "获取订单价格")
    @PostMapping("v1/getPrice")
    public Double getPrice(@RequestParam(value = "order_id") String order_id){
        return orderClient.getPrice(order_id);
    }

    @ApiOperation(value = "支付")
    @PostMapping("v1/pay")
    public void pay(@RequestParam(value = "order_id") String order_id){
        rabbitTemplate.convertAndSend("ordertaking","",new Order(order_id,6));
    }

    @ApiOperation(value = "用户取消打车请求")
    @PostMapping("v1/cancellation")
    public String cancel(@RequestParam(value = "passenger_id") String passenger_id){
        rabbitTemplate.convertAndSend("hailingCancel","",new CancelRequest(passenger_id));
        return "hi";
    }
}
