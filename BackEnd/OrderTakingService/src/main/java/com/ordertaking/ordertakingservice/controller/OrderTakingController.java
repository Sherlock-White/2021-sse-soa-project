package com.ordertaking.ordertakingservice.controller;

import com.ordertaking.ordertakingservice.entity.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@CrossOrigin("*")
@Api(value = "接单")
public class OrderTakingController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ApiOperation(value = "司机接到乘客")
    @PostMapping("v1/pickingup")
    public void pickUp(@RequestParam(value = "o_id") String o_id){
        rabbitTemplate.convertAndSend("itcast.fanout","",new Order(o_id,2));
    }

    @ApiOperation(value = "订单完成")
    @PostMapping("v1/arrival")
    public void arrival(@RequestParam(value = "o_id") String o_id){
        rabbitTemplate.convertAndSend("itcast.fanout","",new Order(o_id,1));
    }

}
