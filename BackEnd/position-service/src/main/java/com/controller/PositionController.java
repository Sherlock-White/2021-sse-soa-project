package com.controller;

import com.entity.Position;
import com.entity.Weather;
import com.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author CuiXi
 * @version 1.0
 * @Description:
 * @date 2021/3/11 15:19
 */
@RequestMapping("/position")
@RestController
public class PositionController {
    @Autowired
    private PositionService weatherService;

    @GetMapping("/getId")
    public List<Position> queryWeather(){
        return weatherService.query();
    }
    
    @GetMapping("/test")
    public String test(){return "successful connection!!";}
}
