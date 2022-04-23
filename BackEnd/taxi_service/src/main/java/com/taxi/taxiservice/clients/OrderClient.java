package com.taxi.taxiservice.clients;

import com.taxi.taxiservice.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("orderservice")
public interface OrderClient {
    @GetMapping("/{order_id}")
    Double getPrice(@PathVariable("order_id") String order_id);
}
