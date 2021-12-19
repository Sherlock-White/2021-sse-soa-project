package com.example.orderservice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@TableName("taxiorder")
public class Order {
    private String order_id;
    private String passenger_id;
    private String driver_id;
    private Double price;
    private String departure;
    private String destination;




}
