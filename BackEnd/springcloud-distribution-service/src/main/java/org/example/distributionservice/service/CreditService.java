package org.example.distributionservice.service;

import org.example.distributionservice.feignClient.CreditClient;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.util.List;
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

    public String[] getCredit(String[] driverList){
        //["driver1","driver2"]
        //List<String>
        //Result result = (Result) creditClient.findDriverById(driverList);
        //Map<String, String> resultMap = (Map<String, String>) result.getObject();

        //拿到数组，挨个转一下int装进去
        //int credit = Integer.parseInt(resultMap.get("creditworthiness"));
        //assert creditList != null;
        //creditList[index] = credit;

        /*for (int index = 0; index< Objects.requireNonNull(driverList).length; index++) {
            //String name = driverList[index];
            Result result = (Result) creditClient.findDriverById(driverList);
            Map<String, String> resultMap = (Map<String, String>) result.getObject();
            int credit = Integer.parseInt(resultMap.get("creditworthiness"));
            assert creditList != null;
            creditList[index] = credit;
        }*/
        //String name = driverList[index];
        String[] creditList = creditClient.findDriverById(driverList);
        return creditList;
    }
}
