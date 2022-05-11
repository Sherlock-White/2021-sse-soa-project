package org.example.distributionservice.feignClient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//url待定
@FeignClient(value="position-service",url="http://139.224.251.185:9004",fallback = PosClientImpl.class)
public interface PosClient {
    @GetMapping("/position/getAll")
    List<Object> getNearDriverList();
}