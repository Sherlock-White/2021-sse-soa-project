package com.example.userservice.Realm;


import com.example.userservice.entity.Client;
import com.example.userservice.entity.Driver;
import com.example.userservice.service.ClientService;
import com.example.userservice.service.DirverService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class UserRealm extends AuthorizingRealm {


    @Autowired
    ClientService clientService;

    @Autowired
    DirverService driverService;

    // 简单重写获取授权信息方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权=》doGetAuthorizationInfo");
        return null;
    }

    // 获取认证信息，即根据 token 中的用户名从数据库中获取密码、盐等并返回
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = token.getPrincipal().toString();
        Client client=null;
        Driver driver=null;
        client = clientService.findByClientnamecl(userName);
        driver= driverService.findByDrivernamecl(userName);
        String passwordInDB=null;
        String salt=null;
        if(client!=null){
            passwordInDB = client.getPasswd();
            salt = client.getSalt();
        }
        if(driver!=null){
            passwordInDB = driver.getPasswd();
            salt = driver.getSalt();
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, passwordInDB, ByteSource.Util.bytes(salt), getName());
        System.out.println("执行了认证=》doGetAuthenticationInfo");
        return authenticationInfo;
//        return null;
    }
}

