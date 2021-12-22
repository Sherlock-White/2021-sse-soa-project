package org.example.distributionservice.service;

import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: LuBixing
 * @date: 2021/12/21 21:12
 */
@Service
public class CreditService {
    private final int[] credit;

    public CreditService(){
        this.credit=null;
    }

    public CreditService(int driverCount) {
        if(driverCount > 0 ){
            this.credit=new int[driverCount];
        }else{
            this.credit = null;
        }
    }

    public int[] getCredit(){
        return credit;
    }
}
