package com.taxi.taxiservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelRequest {
    private String passenger_id = "";
    private long time = System.currentTimeMillis();

    public CancelRequest(String passenger_id){
        this.passenger_id=passenger_id;
    }
}
