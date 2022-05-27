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
//@AllArgsConstructor
@NoArgsConstructor
public class Position {

    private String ts;

    private String id;

    private String jing;

    private String wei;

    public Position(String ts1,String id1,String jing1,String wei1){
        this.ts=ts1;
        this.id=id1;
        this.jing=jing1;
        this.wei=wei1;
    }

    public void setTs(String ts){this.ts=ts;}
    public void setId(String id){this.id=id;}
    public void setJing(String jing){this.jing=jing;}
    public void setWei(String wei){this.wei=wei;}


}
