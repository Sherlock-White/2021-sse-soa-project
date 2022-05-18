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
    public String pickUp(@RequestParam(value = "order_id") String order_id){
        rabbitTemplate.convertAndSend("orderTaking","",new Order(order_id,4));
        return "success";
    }

    @ApiOperation(value = "行程结束")
    @PostMapping("v1/arrival")
    public String arrival(@RequestParam(value = "order_id") String order_id){
        rabbitTemplate.convertAndSend("orderTaking","",new Order(order_id,5));
        return "success";
    }
}
