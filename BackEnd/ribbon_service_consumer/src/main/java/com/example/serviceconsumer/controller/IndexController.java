package com.example.serviceconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class IndexController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getPos")
    public String sayHello(){
        String url = "http://position-service/position/getAll";
        String result = restTemplate.getForObject(url , String.class);
        return result;
    }
}
