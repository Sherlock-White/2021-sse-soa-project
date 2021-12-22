/*package org.example.feignClient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.transform.Result;
import java.util.Map;
//url待定
@FeignClient(value="positonservice",url="http://47.103.9.250:9000")
//@FeignClient("userservice")
public interface PositionClient {
    @PostMapping("/api/v1/userservice/returnclientchage")
    Result findPassengerById(@RequestParam("name") String name);

    @PostMapping("/api/v1/userservice/returndriverchage")
    Result findDriverById(@RequestParam("name") String name);
}
*/