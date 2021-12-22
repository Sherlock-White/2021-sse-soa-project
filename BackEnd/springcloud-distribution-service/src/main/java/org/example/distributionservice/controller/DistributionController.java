package org.example.distributionservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
}
