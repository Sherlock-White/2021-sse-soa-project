package org.example.distributionservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.distributionservice.service.DistributionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "派单服务接口")
public class DistributionController {
    @RequestMapping(value="/distribution",method = RequestMethod.GET)
    @ApiOperation("获取派单信息")
    public int[] test(){
        DistributionService distributionService = new DistributionService(2,2);
        distributionService.distribute();
        return distributionService.getResult();
    }
}
