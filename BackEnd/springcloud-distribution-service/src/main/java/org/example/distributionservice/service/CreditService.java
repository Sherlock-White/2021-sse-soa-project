package org.example.distributionservice.service;

import org.example.distributionservice.feignClient.CreditClient;
import org.example.distributionservice.result.Result;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @description:
 * @author: LuBixing
 * @date: 2021/12/21 21:12
 */
@Service
public class CreditService {
    private final int[] creditList;
    CreditClient creditClient;
    public CreditService(){
        this.creditList=null;
    }

    public CreditService(int driverCount) {
        if(driverCount > 0 ){
            this.creditList=new int[driverCount];
        }else{
            this.creditList = null;
        }
    }

    public int[] getCredit(String[] driverList){
        /*for (int index = 0; index< Objects.requireNonNull(driverList).length; index++) {
            String name = driverList[index];
            Result result = (Result) creditClient.findDriverById(name);
            Map<String, String> resultMap = (Map<String, String>) result.getObject();
            int credit = Integer.parseInt(resultMap.get("creditworthiness"));
            assert creditList != null;
            creditList[index] = credit;
        }*/
        for(int index = 0;index<driverList.length;index++){
            assert creditList != null;
            creditList[index] = 100;
        }
        return creditList;
    }
}
