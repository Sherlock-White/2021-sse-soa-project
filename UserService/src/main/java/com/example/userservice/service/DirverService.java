package com.example.userservice.service;


import com.example.userservice.dao.ClientDAO;
import com.example.userservice.dao.DriverDAO;
import com.example.userservice.entity.Client;
import com.example.userservice.entity.Driver;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirverService {

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    DriverDAO driverDAO;


    public boolean isExist(String name){
        Integer client=findByClientnameid(name);
        Integer driver=findByDrivernameid(name);
        if(client==null&&driver==null){
            return false;
        }
        return true;
    }

    /**
     * 查找dirver
     * @param name
     * @return
     */
    public Integer findByDrivernameid(String name) {
        Integer driver = driverDAO.findBywwwname(name);
        return driver;
    }
    public Driver findByDrivernamecl(String name){
        Driver driver=driverDAO.findByName(name);
        return driver;
    }
    /**
     * 根据name查找client
     * @param name
     * @return
     */
    public Integer findByClientnameid(String name){
        Integer client=clientDAO.findBywwwname(name);
        return client;
    }
    public Client findByClientnamecl(String name){
        Client client=clientDAO.findByName(name);
        return client;
    }

    public int resetphone(String name){
        Driver driver=driverDAO.findByName(name);
        driver.setPhone(null);
        driverDAO.save(driver);
        return 1;
    }

    public int resetphone(String name,String phone){
        Driver driver=driverDAO.findByName(name);
        driver.setPhone(phone);
        driverDAO.save(driver);
        return 1;
    }

    public int register(String name,String passwd,String phone){
        Driver dirver=new Driver();
        dirver.setName(name);
        if (name.equals("")) {
            return 1;
        }
        boolean exist = isExist(name);
        // 用户名已存在
        if (exist) {
            return 2;
        }
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5", passwd, salt, times).toString();

        dirver.setSalt(salt);
        dirver.setPasswd(encodedPassword);
        dirver.setPhone(phone);
        driverDAO.save(dirver);
        return 0;
    }
    public int reset(Driver driver,String newpasswd){

        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5", newpasswd, salt, times).toString();

        driver.setSalt(salt);
        driver.setPasswd(encodedPassword);
        driverDAO.save(driver);
        return 0;
    }
}
