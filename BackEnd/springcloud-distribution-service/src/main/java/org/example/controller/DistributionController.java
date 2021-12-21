package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.example.service.DistributionService;

/**
 * @description:
 * @author: LuBixing
 * @date: 2021/12/20 22:10
 */
@RestController
@Api(tags = "派单服务接口")
public class DistributionController {
    @RequestMapping(value="/distribution",method = RequestMethod.GET)
    @ApiOperation("获取派单信息")
    public int[] test(){
        DistributionService distributionService = new DistributionService(5);
        distributionService.distribute();
        return distributionService.getValue();
    }
}
