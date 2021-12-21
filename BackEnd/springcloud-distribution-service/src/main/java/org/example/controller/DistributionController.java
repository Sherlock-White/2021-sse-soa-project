package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public int test(){
        /*GraphMatch graphMatch = init();
        for(int i = 0 ; i < count ; i ++) {
            search(graphMatch, i);
            clearOnPathSign(graphMatch);
        }
        log(graphMatch);*/
        return 1;
    }
}
