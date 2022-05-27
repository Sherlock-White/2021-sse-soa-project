package com.mapper.tdengine;

import com.entity.Position;
import com.entity.Weather;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author CuiXi
 * @version 1.0
 * @Description:
 * @date 2021/3/11 14:30
 */
@Component
public interface PositionMapper {

    int insert(Position weather);

    int batchInsert(List<Position> weatherList);

    List<Position> select();

    void createDB();

    void createTable();
}
