package org.example.distributionservice.feignClient;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: LuBixing
 * @date: 2021/12/29 19:31
 */
@Component
public class CreditClientImpl implements CreditClient {
    @Override
    public String[] findDriverById(String[] nameList) {
        return null;
    }
}
