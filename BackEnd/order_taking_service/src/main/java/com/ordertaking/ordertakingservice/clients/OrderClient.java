package com.ordertaking.ordertakingservice.clients;

import com.ordertaking.ordertakingservice.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("orderservice")
public interface OrderClient {
    @GetMapping("/{order}")
    Double getPrice(@PathVariable("order") Order order);
}
