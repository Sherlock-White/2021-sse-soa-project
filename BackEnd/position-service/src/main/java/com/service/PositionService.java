package com.service;

import com.entity.Position;
import com.entity.Weather;
import com.mapper.tdengine.PositionMapper;
import com.mapper.tdengine.WeatherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author CuiXi
 * @version 1.0
 * @Description:
 * @date 2021/3/11 15:18
 */
@Service
@Transactional(transactionManager = "tdengineTransactionManager")
public class PositionService {

    @Autowired
    private PositionMapper weatherMapper;

    public List<Position> query() {
        return weatherMapper.select();
    }



}

