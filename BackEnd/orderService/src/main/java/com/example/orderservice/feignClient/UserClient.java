package com.example.orderservice.feignClient;

import com.example.orderservice.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
//url待定
@FeignClient(value="userservice",url="http://47.103.9.250:9000")
//@FeignClient("userservice")
public interface UserClient {
    @PostMapping("/api/v1/userservice/returnclientchage")
    Result findPassengerById(@RequestParam("name") String name);

    @PostMapping("/api/v1/userservice/returndriverchage")
    Result findDriverById(@RequestParam("name") String name);
}
