package org.example.distributionservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.distributionservice.service.CreditService;
import org.example.distributionservice.service.DistributionService;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "派单")
@CrossOrigin("*")
public class DistributionController {
    @RequestMapping(value="/distribution",method = RequestMethod.GET)
    @ApiOperation("获取派单信息")
    public int[] test(){
        String[][] passenger ={
                {"1","31.283036","121.501564"},
                {"2","31.249582","121.455752"}};
        String[][] driver ={
                {"1","31.286428","121.212090"},
                {"2","31.194202","121.320655"}};
        DistributionService distributionService = new DistributionService(2,2,passenger,driver);
        return distributionService.distribute();
    }

    @RequestMapping(value="/test",method = RequestMethod.GET)
    @ApiOperation("信用度测试")
    public int[] testCredit(){
        CreditService creditService = new CreditService(2);
        String[] driverList = new String[2];
        driverList[0]="driver1";
        driverList[1]="driver2";
        return creditService.getCredit(driverList);
    }
}
