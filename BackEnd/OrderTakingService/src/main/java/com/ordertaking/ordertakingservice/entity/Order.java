package com.ordertaking.ordertakingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String o_id;
    private int state;
    private long time = System.currentTimeMillis();

    public Order(String o_id, int state){
        this.o_id=o_id;
        this.state=state;
    }
}
