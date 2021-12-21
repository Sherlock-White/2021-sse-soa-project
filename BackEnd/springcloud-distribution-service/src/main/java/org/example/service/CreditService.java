package org.example.service;

/**
 * @description:
 * @author: LuBixing
 * @date: 2021/12/21 21:12
 */
public class CreditService {
    private final int[] credit;

    public CreditService(){
        this.credit=null;
    }

    public CreditService(int passenger_count,int driver_count) {
        if(passenger_count > 0 && driver_count > 0 ){
            this.credit=new int[driver_count];
        }else{
            this.credit = null;
        }
    }

    public int[] getCredit(){
        return credit;
    }
}
