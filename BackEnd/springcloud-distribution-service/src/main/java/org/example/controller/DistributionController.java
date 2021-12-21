package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.service.DistributionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.example.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@Api(tags = "派单服务接口")
public class DistributionController {
    @RequestMapping(value="/distribution",method = RequestMethod.GET)
    @ApiOperation("获取派单信息")


    public void test(){
        //司机和乘客记录均为“id”,"纬度","经度"
        String[][] passenger={
                {"1","31.283036","121.501564"},
                {"2","31.249582","121.455752"}};
        String[][] driver={
                {"1","31.286428","121.212090"},
                {"2","31.194202","121.320655"}};



        DistributionService distributionService = new DistributionService(5);
        distributionService.distribute();
//        return distributionService.getValue();
//        return ds.getValue();
    }
}
