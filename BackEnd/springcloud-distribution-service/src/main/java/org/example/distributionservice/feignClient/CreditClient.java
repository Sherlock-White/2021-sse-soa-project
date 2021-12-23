package org.example.distributionservice.feignClient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.transform.Result;
import java.util.List;

@FeignClient(value="userservice",url="http://47.103.9.250:9000")
public interface CreditClient {
    @PostMapping("api/vi/userservice/creditworthness")
    String[] findDriverById(@RequestParam("name") String[] name);
}