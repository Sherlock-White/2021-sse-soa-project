package com.example.userservice.controller;

import com.example.userservice.dao.AdministratorDAO;
import com.example.userservice.dao.ClientDAO;
import com.example.userservice.dao.DriverDAO;
import com.example.userservice.entity.Administrator;
import com.example.userservice.entity.Client;
import com.example.userservice.entity.Driver;
import com.example.userservice.result.Result;
import com.example.userservice.result.ResultCode;
import com.example.userservice.result.ResultFactory;
import com.example.userservice.service.ClientService;
import com.example.userservice.service.DirverService;
import com.example.userservice.service.SmsService;
import com.example.userservice.util.SendSms;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api("用户管理")
@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/userservice")
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

    @Autowired
    AdministratorDAO administratorDAO;


    @GetMapping("/{id}")
    public Integer test(@PathVariable("id") Integer id) {
        return 1;
    }

    @ApiOperation("注册")
    @PostMapping("registration")
    public Result register(@RequestParam(value = "name") String name, @RequestParam(value = "passwd") String passwd, @RequestParam(value = "phone") String phone, @RequestParam(value = "identifyCode") String identifyCode, @RequestParam(value = "identity") String identity) {

        Boolean success = sendMessage.checkIsCorrectCode(phone, identifyCode);
        if (success == false) {
            return ResultFactory.buildFailResult("验证码不正确");
        }
        int status = 0;
        if (identity.equals("1")) {
            status = clientService.register(name, passwd, phone);
            if (status == 0) {
                return ResultFactory.buildResult(ResultCode.SUCCESS, "注册成功", 1);  //1代表client
            }

        } else {
            status = driverService.register(name, passwd, phone);
            if (status == 0) {
                return ResultFactory.buildResult(ResultCode.SUCCESS, "注册成功", 2); //2代表driver
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

    @ApiOperation("登录")
    @PostMapping("login")
    public Result login(@RequestParam(value = "name") String name, @RequestParam(value = "passwd") String passwd) {
        Integer user = 0;
        Client client = null;
        Driver driver = null;
        client = clientService.findByClientnamecl(name);
        driver = driverService.findByDrivernamecl(name);
        if (client != null) {
            user = 1;
        }
        if (driver != null) {
            user = 2;
        }
        if (user == 0) {
            return ResultFactory.buildFailResult("账号错误");
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name, passwd);
        usernamePasswordToken.setRememberMe(true);
        try {
            subject.login(usernamePasswordToken);
            return ResultFactory.buildResult(ResultCode.SUCCESS, "登录成功", user); //1代表client，2代表driver
        } catch (AuthenticationException e) {
            return ResultFactory.buildFailResult("密码错误");
        }

    }

    @ApiOperation("登出")
    @GetMapping("logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultFactory.buildSuccessResult("成功登出");
    }

    @ApiOperation("重置密码")
    @PostMapping("resetpasswords")
    public Result reset(@RequestParam(value = "name") String name, @RequestParam(value = "newpasswd") String newpasswd, @RequestParam(value = "phone") String phone, @RequestParam(value = "identifyCode") String identifyCode) {

        Boolean success = sendMessage.checkIsCorrectCode(phone, identifyCode);
        if (success == false) {
            return ResultFactory.buildFailResult("验证码不正确");
        }
        Integer identify = 0;
        Client client = null;
        Driver driver = null;
        client = clientService.findByClientnamecl(name);
        driver = driverService.findByDrivernamecl(name);
        if (client != null) {
            identify = 1;
        }
        if (driver != null) {
            identify = 2;
        }
        if (identify == 1) {
            clientService.reset(client, newpasswd);
            return ResultFactory.buildResult(ResultCode.SUCCESS, "重置密码成功", 1);  //1代表client
        }
        if (identify == 2) {
            driverService.reset(driver, newpasswd);
            return ResultFactory.buildResult(ResultCode.SUCCESS, "重置密码成功", 2);  //2代表driver
        }
        return ResultFactory.buildFailResult("未知错误");
    }

    @ApiOperation("解绑手机")
    @PostMapping("unbindphones")
    public Result resetphone(@RequestParam(value = "name") String name, @RequestParam(value = "phone") String phone, @RequestParam(value = "identifyCode") String identifyCode) {

        Boolean success = sendMessage.checkIsCorrectCode(phone, identifyCode);
        if (success == false) {
            return ResultFactory.buildFailResult("验证码不正确");
        }
        Integer identify = 0;
        Client client = null;
        Driver driver = null;
        client = clientService.findByClientnamecl(name);
        driver = driverService.findByDrivernamecl(name);
        if (client != null) {
            identify = 1;
        }
        if (driver != null) {
            identify = 2;
        }
        if (identify == 1) {
            clientService.resetphone(name);
            return ResultFactory.buildResult(ResultCode.SUCCESS, "解绑手机成功", 1);  //1代表client
        }
        if (identify == 2) {
            driverService.resetphone(name);
            return ResultFactory.buildResult(ResultCode.SUCCESS, "解绑手机成功", 2);  //2代表driver
        }
        return ResultFactory.buildFailResult("未知错误");
    }

    @ApiOperation("重置手机")
    @PostMapping("resetphones")
    public Result resetphone1(@RequestParam(value = "name") String name, @RequestParam(value = "phone") String phone, @RequestParam(value = "identifyCode") String identifyCode) {

        Boolean success = sendMessage.checkIsCorrectCode(phone, identifyCode);
        if (success == false) {
            return ResultFactory.buildFailResult("验证码不正确");
        }
        Integer identify = 0;
        Client client = null;
        Driver driver = null;
        client = clientService.findByClientnamecl(name);
        driver = driverService.findByDrivernamecl(name);
        if (client != null) {
            identify = 1;
        }
        if (driver != null) {
            identify = 2;
        }
        if (identify == 1) {
            clientService.resetphone(name, phone);
            return ResultFactory.buildResult(ResultCode.SUCCESS, "重置手机成功", 1);  //1代表client
        }
        if (identify == 2) {
            driverService.resetphone(name, phone);
            return ResultFactory.buildResult(ResultCode.SUCCESS, "重置手机成功", 2);  //2代表driver
        }
        return ResultFactory.buildFailResult("未知错误");
    }


    @ApiOperation("注册验验证码")
    @PostMapping("registercode")
    public Result registercode(@RequestParam(value = "phone") String phone) {
        String verifyCode = sendMessage.mysendMessage(phone, 1); //1代表注册
        return ResultFactory.buildSuccessResult(verifyCode);

    }

    @ApiOperation("重置密码验证码")
    @PostMapping("resetcode")
    public Result resetcode(@RequestParam(value = "name") String name, @RequestParam(value = "phone") String phone) {
        Integer identify = 0;
        Client client = null;
        Driver driver = null;
        client = clientService.findByClientnamecl(name);
        driver = driverService.findByDrivernamecl(name);
        if (client != null) {
            identify = 1;
        }
        if (driver != null) {
            identify = 2;
        }
        if (identify == 0) {
            return ResultFactory.buildFailResult("不存在该用户");
        }
        if (identify == 1) {
            String temp = client.getPhone();
            if (temp == null) {
                return ResultFactory.buildFailResult("账号手机不匹配");
            }
            if (temp.equals(phone)) {

            } else {
                return ResultFactory.buildFailResult("账号手机不匹配");
            }
        }
        if (identify == 2) {
            String temp1 = driver.getPhone();
            if (temp1 == null) {
                return ResultFactory.buildFailResult("账号手机不匹配");
            }
            if (temp1.equals(phone)) {

            } else {
                return ResultFactory.buildFailResult("账号手机不匹配");
            }
        }
        String verifyCode = sendMessage.mysendMessage(phone, 2); //2代表重置密码
        return ResultFactory.buildSuccessResult(verifyCode);
    }

    @ApiOperation("重置手机验证码")
    @PostMapping("resetphonecode")
    public Result resetphonecode(@RequestParam(value = "name") String name, @RequestParam(value = "phone") String phone) {
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
        String verifyCode = sendMessage.mysendMessage(phone, 3); //3代表重置手机
        return ResultFactory.buildSuccessResult(verifyCode);
    }


    //下面是账户管理部分
    @ApiOperation("返回客户信息")
    @PostMapping("returnclientchage")
    public Result returnclientchage(@RequestParam(value = "name") String name) {
        Client client = clientService.findByClientnamecl(name);
//        clientDAO.save(client);
        return ResultFactory.buildResult(ResultCode.SUCCESS, "修改资料成功", client);  //1代表client
    }

    @ApiOperation("客户信息更改")
    @PostMapping("clientchage")
    public Result clientchage(@RequestBody Client client) {

        clientDAO.save(client);
        return ResultFactory.buildResult(ResultCode.SUCCESS, "修改资料成功", 1);  //1代表client
    }

    @ApiOperation("返回司机信息")
    @PostMapping("returndriverchage")
    public Result returndriverchage(@RequestParam(value = "name") String name) {
        Driver driver = driverService.findByDrivernamecl(name);
//        clientDAO.save(client);
        return ResultFactory.buildResult(ResultCode.SUCCESS, "修改资料成功", driver);  //1代表client
    }
    @ApiOperation("司机信息更改")
    @PostMapping("driverchage")
    public Result driverchage(@RequestBody Driver driver) {

        driverDAO.save(driver);
        return ResultFactory.buildResult(ResultCode.SUCCESS, "修改资料成功", 1);  //1代表client
    }

    @ApiOperation("身份认证")
    @PostMapping("identify")
    public Result identify(@RequestParam(value = "name") String name, @RequestParam(value = "truename") String truename, @RequestParam(value = "identifynum") String identifynum) throws IOException {
        String result = sendMessage.mysendMessage1(truename, identifynum);
//        String description=result.get("description");
        boolean status = result.contains("一致");
        if (status) {
            Driver driver = driverDAO.findByName(name);
            driver.setTruename(truename);
            driver.setIdentitynum(identifynum);
            driverDAO.save(driver);
            return ResultFactory.buildResult(ResultCode.SUCCESS, "身份认证成功", result);  //1代表client
        } else {
            return ResultFactory.buildResult(ResultCode.FAIL, "身份认证失败", result);  //1代表client
        }

    }

    @ApiOperation("管理员登录")
    @PostMapping("adlogin")
    public Result ad(String name, String passwd) {
        Administrator administrator = administratorDAO.findByName(name);
        if (administrator.getPasswd().equals(passwd)) {
            return ResultFactory.buildResult(ResultCode.SUCCESS, "登录成功", 0);  //1代表client
        }
        return ResultFactory.buildResult(ResultCode.FAIL, "登录失败", 0);  //1代表client
    }
    @ApiOperation("信用度")
    @PostMapping("creditworthiness")
    public List<Long> creditworthiness(@RequestParam(value = "paramlist")List<String> paramlist){
        List<Long> list= new ArrayList<Long>();
        for (String temp:paramlist){
            Long l=driverService.credit(temp);
            list.add(l);
            System.out.println(l);
        }

        return list;

    }
}
