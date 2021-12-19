package com.example.userservice.controller;

import com.example.userservice.dao.ClientDAO;
import com.example.userservice.dao.DriverDAO;
import com.example.userservice.entity.Client;
import com.example.userservice.entity.Driver;
import com.example.userservice.result.Result;
import com.example.userservice.result.ResultCode;
import com.example.userservice.result.ResultFactory;
import com.example.userservice.service.ClientService;
import com.example.userservice.service.DirverService;
import com.example.userservice.service.SmsService;
import com.example.userservice.util.SendSms;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("api/")
public class LoginController {

    @Autowired
    ClientService clientService;

    @Autowired
    DirverService driverService;

    @Autowired
    SmsService sendMessage;

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    DriverDAO driverDAO;


    @GetMapping("/{id}")
    public Integer test(@PathVariable("id")Integer id){
        return 1;
    }

    @PostMapping("register")
    public Result register(String name, String passwd,String phone,String identifyCode,String identity){

        Boolean success=sendMessage.checkIsCorrectCode(phone,identifyCode);
        if(success==false){
            return ResultFactory.buildFailResult("验证码不正确");
        }
        int status=0;
        if(identity.equals("1")){
            status =clientService.register(name,passwd,phone);
            if(status==0){
                return ResultFactory.buildResult(ResultCode.SUCCESS,"注册成功",1);  //1代表client
            }

        }
        else{
            status =driverService.register(name,passwd,phone);
            if(status==0){
                return ResultFactory.buildResult(ResultCode.SUCCESS,"注册成功",2); //2代表driver
            }
        }
        switch (status) {
            case 1:
                return ResultFactory.buildFailResult("用户名不能为空");
            case 2:
                return ResultFactory.buildFailResult("用户已存在");
        }
        return ResultFactory.buildFailResult("未知错误");
    }


    @PostMapping("login")
    public Result login(String name, String passwd){
        Integer user=0;
        Client client=null;
        Driver driver=null;
        client=clientService.findByClientnamecl(name);
        driver=driverService.findByDrivernamecl(name);
        if(client!=null){
            user=1;
        }
        if(driver!=null){
            user=2;
        }
        if(user==0){
            return ResultFactory.buildFailResult("账号错误");
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name,passwd);
        usernamePasswordToken.setRememberMe(true);
        try {
            subject.login(usernamePasswordToken);
            return ResultFactory.buildResult(ResultCode.SUCCESS,"登录成功",user); //1代表client，2代表driver
        } catch (AuthenticationException e) {
            return ResultFactory.buildFailResult("密码错误");
        }

    }


    @GetMapping("logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultFactory.buildSuccessResult("成功登出");
    }


    @PostMapping("reset")
    public Result reset(String name,String newpasswd,String phone,String identifyCode){

        Boolean success=sendMessage.checkIsCorrectCode(phone,identifyCode);
        if(success==false){
            return ResultFactory.buildFailResult("验证码不正确");
        }
        Integer identify=0;
        Client client=null;
        Driver driver=null;
        client = clientService.findByClientnamecl(name);
        driver= driverService.findByDrivernamecl(name);
        if(client!=null){
            identify=1;
        }
        if(driver!=null){
            identify=2;
        }
        if(identify==1){
            clientService.reset(client,newpasswd);
            return ResultFactory.buildResult(ResultCode.SUCCESS,"重置密码成功",1);  //1代表client
        }
        if(identify==2) {
            driverService.reset(driver,newpasswd);
            return ResultFactory.buildResult(ResultCode.SUCCESS,"重置密码成功",2);  //2代表driver
        }
        return ResultFactory.buildFailResult("未知错误");
    }

    @PostMapping("resetphone")
    public Result resetphone(String name,String phone,String identifyCode){

//        Boolean success=sendMessage.checkIsCorrectCode(phone,identifyCode);
//        if(success==false){
//            return ResultFactory.buildFailResult("验证码不正确");
//        }
        Integer identify=0;
        Client client=null;
        Driver driver=null;
        client = clientService.findByClientnamecl(name);
        driver= driverService.findByDrivernamecl(name);
        if(client!=null){
            identify=1;
        }
        if(driver!=null){
            identify=2;
        }
        if(identify==1){
            clientService.resetphone(name);
            return ResultFactory.buildResult(ResultCode.SUCCESS,"解绑手机成功",1);  //1代表client
        }
        if(identify==2) {
            driverService.resetphone(name);
            return ResultFactory.buildResult(ResultCode.SUCCESS,"解绑手机成功",2);  //2代表driver
        }
        return ResultFactory.buildFailResult("未知错误");
    }

    @PostMapping("resetphone1")
    public Result resetphone1(String name,String phone,String identifyCode){

        Boolean success=sendMessage.checkIsCorrectCode(phone,identifyCode);
        if(success==false){
            return ResultFactory.buildFailResult("验证码不正确");
        }
        Integer identify=0;
        Client client=null;
        Driver driver=null;
        client = clientService.findByClientnamecl(name);
        driver= driverService.findByDrivernamecl(name);
        if(client!=null){
            identify=1;
        }
        if(driver!=null){
            identify=2;
        }
        if(identify==1){
            clientService.resetphone(name,phone);
            return ResultFactory.buildResult(ResultCode.SUCCESS,"重置手机成功",1);  //1代表client
        }
        if(identify==2) {
            driverService.resetphone(name,phone);
            return ResultFactory.buildResult(ResultCode.SUCCESS,"重置手机成功",2);  //2代表driver
        }
        return ResultFactory.buildFailResult("未知错误");
    }




    @PostMapping("registercode")
    public Result registercode(String phone){
        String verifyCode=sendMessage.mysendMessage(phone,1); //1代表注册
        return ResultFactory.buildSuccessResult(verifyCode);

    }

    @PostMapping("resetcode")
    public Result resetcode(String name,String phone){
        Integer identify=0;
        Client client=null;
        Driver driver=null;
        client = clientService.findByClientnamecl(name);
        driver= driverService.findByDrivernamecl(name);
        if(client!=null){
           identify=1;
        }
        if(driver!=null){
           identify=2;
        }
        if(identify==0){
            return ResultFactory.buildFailResult("不存在该用户");
        }
        if(identify==1){
            String temp=client.getPhone();
            if (temp==null){
                return ResultFactory.buildFailResult("账号手机不匹配");
            }
            if(temp.equals(phone)){

            }
            else {
                return ResultFactory.buildFailResult("账号手机不匹配");
            }
        }
        if(identify==2){
            String temp1=driver.getPhone();
            if (temp1==null){
                return ResultFactory.buildFailResult("账号手机不匹配");
            }
            if(temp1.equals(phone)){

            }
            else {
                return ResultFactory.buildFailResult("账号手机不匹配");
            }
        }
        String verifyCode=sendMessage.mysendMessage(phone,2); //2代表重置密码
        return ResultFactory.buildSuccessResult(verifyCode);
    }

    @PostMapping("resetphonecode")
    public Result resetphonecode(String name,String phone){
//        Integer identify=0;
//        Client client=null;
//        Driver driver=null;
//        client = clientService.findByClientnamecl(name);
//        driver= driverService.findByDrivernamecl(name);
//        if(client!=null){
//            identify=1;
//        }
//        if(driver!=null){
//            identify=2;
//        }
//        if(identify==0){
//            return ResultFactory.buildFailResult("不存在该用户");
//        }
//        if(identify==1){
//            String temp=client.getPhone();
//            if (temp==null){
//                return ResultFactory.buildFailResult("账号手机不匹配");
//            }
//            if(temp.equals(phone)){
//
//            }
//            else {
//                return ResultFactory.buildFailResult("账号手机不匹配");
//            }
//        }
//        if(identify==2){
//            String temp1=driver.getPhone();
//            if (temp1==null){
//                return ResultFactory.buildFailResult("账号手机不匹配");
//            }
//            if(temp1.equals(phone)){
//
//            }
//            else {
//                return ResultFactory.buildFailResult("账号手机不匹配");
//            }
//        }
        String verifyCode=sendMessage.mysendMessage(phone,3); //3代表重置手机
        return ResultFactory.buildSuccessResult(verifyCode);
    }





    //下面是账户管理部分
    @PostMapping("returnclientchage")
    public Result returnclientchage(String name){
       Client client =clientService.findByClientnamecl(name);
//        clientDAO.save(client);
        return ResultFactory.buildResult(ResultCode.SUCCESS,"修改资料成功",client);  //1代表client
    }
    @PostMapping("clientchage")
    public Result clientchage(@RequestBody Client client){

        clientDAO.save(client);
        return ResultFactory.buildResult(ResultCode.SUCCESS,"修改资料成功",1);  //1代表client
    }
    @PostMapping("returndriverchage")
    public Result returndriverchage(String name){
        Driver driver=driverService.findByDrivernamecl(name);
//        clientDAO.save(client);
        return ResultFactory.buildResult(ResultCode.SUCCESS,"修改资料成功",driver);  //1代表client
    }
    @PostMapping("driverchage")
    public Result driverchage(@RequestBody Driver driver){

        driverDAO.save(driver);
        return ResultFactory.buildResult(ResultCode.SUCCESS,"修改资料成功",1);  //1代表client
    }

    @PostMapping("identify")
    public Result identify(String name,String identifynum) throws IOException {
        String result = sendMessage.mysendMessage1(name, identifynum);
        return ResultFactory.buildResult(ResultCode.SUCCESS,"身份认证成功",result);  //1代表client
    }

}
