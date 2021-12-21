package com.taxi.taxiservice.controller;

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

    @ApiOperation(value = "用户打车请求")
    @PostMapping("v1/hailing")
    public String hail(@RequestParam(value = "p_id") String p_id,
                            @RequestParam(value = "from") String from,
                            @RequestParam(value = "fromll") String fromll,
                            @RequestParam(value = "to") String to,
                            @RequestParam(value = "toll") String toll){
        rabbitTemplate.convertAndSend("itcast.fanout","",new TexiRequest());
        return "hi";
    }

    @ApiOperation(value = "用户打车请求")
    @PostMapping("v1/cancellation")
    public String cancel(@RequestParam(value = "p_id") String p_id,
                       @RequestParam(value = "from") String from,
                       @RequestParam(value = "fromll") String fromll,
                       @RequestParam(value = "to") String to,
                       @RequestParam(value = "toll") String toll){
        rabbitTemplate.convertAndSend("itcast.fanout","",new TexiRequest());
        return "hi";
    }
}
