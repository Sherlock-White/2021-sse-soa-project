package com.taxi.taxiservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String order_id;
    private String state ;
    private String time = new Long(System.currentTimeMillis()).toString();

    public Order(String order_id, String state){
        this.order_id=order_id;
        this.state=state;
    }
}
