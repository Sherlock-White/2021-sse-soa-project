package com.example.orderservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient("userservice")
public interface UserClient {
    @GetMapping("/v1/passengers/{id}")
    Map<String,String> findPassengerById(@PathVariable("id") String id);

    @GetMapping("/v1/drivers/{id}")
    Map<String,String> findDriverById(@PathVariable("id") String id);
}
