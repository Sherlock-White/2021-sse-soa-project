package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.example.service.DistanceService;

@RestController
@Api(tags = "距离服务接口")
public class DistanceController {
    @RequestMapping(value="/distance",method= RequestMethod.GET)
    @ApiOperation("获取距离")
    public int[][] test(){

        //司机和乘客记录均为“id”,"纬度","经度"
        String[][] passenger={
                {"1","31.283036","121.501564"},
                {"2","31.249582","121.455752"}};
        String[][] driver={
                {"1","31.286428","121.212090"},
                {"2","31.194202","121.320655"}};

        DistanceService ds=new DistanceService();
        ds.distribute(passenger,driver);

        return ds.value;
    }
}
