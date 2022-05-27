package com.example.orderservice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@TableName("statement")
public class Statement {
    private String stat_id;
    private String order_id;
    private Instant stat_time;
    private String order_state;




}
