package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author CuiXi
 * @version 1.0
 * @Description:
 * @date 2021/3/11 14:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {

    private Date ts;

    private int id;

    private float jing;

    private float wei;

}
