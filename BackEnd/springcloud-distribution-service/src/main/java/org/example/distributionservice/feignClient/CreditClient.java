package org.example.distributionservice.feignClient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.transform.Result;
@FeignClient(value="userservice",url="http://47.103.9.250:9000",fallback = CreditClientImpl.class)
public interface CreditClient {
    @PostMapping("userservice/creditworthiness")
    String[] findDriverById(@RequestParam("name") String[] nameList);
}