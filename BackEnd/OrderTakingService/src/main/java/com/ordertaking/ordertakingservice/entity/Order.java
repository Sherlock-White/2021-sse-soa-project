package com.ordertaking.ordertakingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String order_id;
    private int state;
    private long time = System.currentTimeMillis();

    public Order(String order_id, int state){
        this.order_id=order_id;
        this.state=state;
    }
}
