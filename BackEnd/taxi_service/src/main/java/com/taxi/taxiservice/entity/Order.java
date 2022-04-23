package com.taxi.taxiservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String order_id;
    private int state = 0;
    private long time = System.currentTimeMillis();

    public Order(String order_id, int state){
        this.order_id=order_id;
        this.state=state;
    }
}
