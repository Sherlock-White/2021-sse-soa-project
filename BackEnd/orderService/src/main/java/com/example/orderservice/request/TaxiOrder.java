package com.example.orderservice.request;

import java.time.Instant;

public class TaxiOrder {
    private String order_id;
    private String passenger_id;
    private String driver_id;
    private Instant start_time;
    private Instant end_time;
    private String order_state;
    private Double price;
    private String departure;
    private String destination;


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPassenger_id() {
        return passenger_id;
    }

    public void setPassenger_id(String passenger_id) {
        this.passenger_id = passenger_id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public Instant getStart_time() {
        return start_time;
    }

    public void setStart_time(Instant start_time) {
        this.start_time = start_time;
    }

    public Instant getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Instant end_time) {
        this.end_time = end_time;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
