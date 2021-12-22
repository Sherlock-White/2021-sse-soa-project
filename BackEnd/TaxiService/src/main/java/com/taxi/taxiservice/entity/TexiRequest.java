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
    private String passenger_id = "";
    private String from = "";
    private Double from_lng = 0.0d;
    private Double from_lat = 0.0d;
    private String to = "";
    private Double to_lng = 0.0d;
    private Double to_lat = 0.0d;
    private long time = System.currentTimeMillis();

    public TexiRequest(String passenger_id,String from,
                       Double from_lng,Double from_lat,
                       String to,Double to_lng,Double to_lat){
        this.passenger_id=passenger_id;
        this.from=from;
        this.from_lng=from_lng;
        this.from_lat=from_lat;
        this.to=to;
        this.to_lng=to_lng;
        this.to_lat=to_lat;
    }
}
