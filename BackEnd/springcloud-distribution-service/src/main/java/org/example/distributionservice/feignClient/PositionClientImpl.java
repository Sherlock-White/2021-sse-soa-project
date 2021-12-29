package org.example.distributionservice.feignClient;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: LuBixing
 * @date: 2021/12/29 19:53
 */
@Component
public class PositionClientImpl implements PositionClient{
    @Override
    public List<Object> getNearDriverList(){
        return null;
    }
}