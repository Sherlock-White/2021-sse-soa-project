package com.example.userservice.service;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.userservice.util.SendSms;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class SmsService {


    /**
     * @description:
     */


        @Resource
        private RedisService redisService;


        public Object sendSms(String phoneNums, String templeteCode, String templateParam) throws Exception {
            return null;
        }


        public Object querySendDetails(String phoneNumber, String bizId) throws Exception {
            return null;
        }


        public String mysendMessage(String mobile, int type) {
            //首先判断验证码是否存在 如果存在 则修改验证码过期时间延长至180S
            String verifyCode = (String)redisService.get(mobile);
            if(StringUtils.isBlank(verifyCode)){
                //如果验证码为空 则新增验证码
                verifyCode = SendSms.zcCode();
            }
            //redis存储验证码并设置180S的过期时间 过期redis自动删除
            redisService.set(mobile,verifyCode,180L);
            //短信发送验证码 异步发送
//            String verifyCode = SendSms.zcCode();
            String content = null;
            switch(type){

                case 1:
                    SendSms.messagePost1(mobile,verifyCode);
                    break;
                case 2:
                    SendSms.messagePost2(mobile,verifyCode);
                    break;
                case 3:
                    SendSms.messagePost3(mobile,verifyCode);
                    break;

                default:break;
            }

            return verifyCode;

        }


        public String mysendMessage1(String truename,String identitynum) throws IOException {
            String result=SendSms.messagePost4(truename,identitynum);
            return result;
        }

        //校验

        public Boolean checkIsCorrectCode(String mobile, String identifyCode) {
            boolean success = false;
            //首先判断验证码是否存在 如果存在 则修改验证码过期时间延长至180S
            String verifyCode = (String)redisService.get(mobile);
            if(StringUtils.isNotBlank(verifyCode) && verifyCode.equals(identifyCode)) {
                success = true;
            }
            return success;
        }


}
