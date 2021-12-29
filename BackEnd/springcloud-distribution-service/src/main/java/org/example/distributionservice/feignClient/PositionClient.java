package org.example.distributionservice.feignClient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;
//url待定
@FeignClient(value="position-service",url="http://139.224.251.185:9004",fallback = PositionClientImpl.class)
public interface PositionClient {
    @GetMapping("/position/getAll")
    List<Object> getNearDriverList();
}