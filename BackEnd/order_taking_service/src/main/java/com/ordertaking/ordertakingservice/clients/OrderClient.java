package com.ordertaking.ordertakingservice.clients;

import com.ordertaking.ordertakingservice.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("orderservice")
public interface OrderClient {
    @GetMapping("/v1/orders/completed/{order_id}")
    Double getPrice(@PathVariable("order_id") String order_id);
}
