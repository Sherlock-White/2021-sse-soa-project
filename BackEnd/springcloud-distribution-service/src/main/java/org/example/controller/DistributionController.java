package org.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.service.DistributionService;

@RestController
public class DistributionController {

    @RequestMapping("/distribution")
    public int[][] test(){

        //司机和乘客记录均为“id”,"纬度","经度"
        String[][] passenger={
                {"1","31.283036","121.501564"},
                {"2","31.249582","121.455752"}};
        String[][] driver={
                {"1","31.286428","121.212090"},
                {"2","31.194202","121.320655"}};

        DistributionService ds=new DistributionService();
        ds.distribute(passenger,driver);

        return ds.value;
    }
}
