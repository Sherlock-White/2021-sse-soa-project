package com.example.orderservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="getPos",url="https://restapi.amap.com")
public interface PosClient {
    @GetMapping("/v3/geocode/geo?key=5d377f781223f6c299389cc8c484c723&address={address}")
    String getPos(@PathVariable("address") String address);
}