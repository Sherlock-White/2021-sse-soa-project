package org.example;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="时序数据库接口")
@RestController
@RequestMapping("/influxDB")
public class Controller {

    @ApiOperation("查询数据库")
    @ApiImplicitParam(name = "params", value = "查询参数",dataType = "String")
    @GetMapping(value="/getData")
    public String test(){
        return "服务1时序数据库";
    }

}
