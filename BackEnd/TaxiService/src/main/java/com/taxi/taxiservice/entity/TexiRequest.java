package com.taxi.taxiservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TexiRequest {
    private String passengerId = "";
    private String from = "";
    private Long[] fromll = new Long[2];
    private String to = "";
    private Long[] toll = new Long[2];
    private Boolean cancel = false;
    private Integer state = 1;
    private long time = System.currentTimeMillis();

}
