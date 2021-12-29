package com.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.*;


@Api(tags = "地图接口")
@CrossOrigin
@RestController
public class Controller {
//    @Resource
//    private GetPositionService getPositionService;
    public String[][] location;

    @RabbitListener(queues ="position")
    public void ListenMQ(String msg) {
        System.out.println(msg);
//        string转jsonArray
        JSONArray jsonArray = JSONArray.parseArray(msg);
        System.out.println(jsonArray);
//       将收到的数据录入position数组
        String[][] position = new String[jsonArray.size()/5][2];
        if(jsonArray.size()>0){
            for (int i = 0; i < jsonArray.size()/5; i=i+5) {
//                    jsonArray转object
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                position[i][0] = jsonObject.getString("jing");
                position[i][1] = jsonObject.getString("wei");
                System.out.println(position[i][0]+","+position[i][1]);
            }
//                String[] data=Arrays.toString(position);
//                System.out.println(Arrays.toString(position));
        }
        this.location=position;
////        return position;
//        this.Location=position;
//        System.out.println(this.Location);
    }

    @ApiOperation("监控查询")
    @RequestMapping(value="/map",method = RequestMethod.GET)
    @ResponseBody
    public  String[][] sendLocation() {
//        getPositionService.Location
//        GetPositionService getPositionService=new GetPositionService();
        String[][] data= this.location;
        return data;
    }

}
