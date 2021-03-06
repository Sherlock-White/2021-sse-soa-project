package com.taosdata.example.springbootdemo.controller;

import com.taosdata.example.springbootdemo.domain.Weather;
import com.taosdata.example.springbootdemo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/weather")
@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/lastOne")
    public Weather lastOne() {
        return weatherService.lastOne();
    }

    @GetMapping("/init")
    public int init() {
        return weatherService.init();
    }

    @GetMapping("/getWeather")
    public List<Weather> queryWeather() {
        return weatherService.query();
    }

    @PostMapping("/{temperature}/{humidity}")
    public int saveWeather(@PathVariable float temperature, @PathVariable float humidity) {
        return weatherService.save(temperature, humidity);
    }

    @GetMapping("/count")
    public int count() {
        return weatherService.count();
    }

    @GetMapping("/subTables")
    public List<String> getSubTables() {
        return weatherService.getSubTables();
    }

    @GetMapping("/avg")
    public List<Weather> avg() {
        return weatherService.avg();
    }

    @GetMapping("/test")
    public String test(){ return "succeessful connection!!!!!";}

}
