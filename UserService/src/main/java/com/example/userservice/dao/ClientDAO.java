package com.example.userservice.dao;

import com.example.userservice.entity.Client;
import com.example.userservice.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientDAO extends JpaRepository<Client,Integer> {
//    @Query("select p from Client p where p.name=:username")
//    Client findBymyName(@Param("username") String  username);
//    Client findByName(String username);
    @Query(value = "select id from Client  where name=:username")
    Integer findBywwwname(@Param("username") String username);

    Client findByName(String username);
    Client findByPhone(String phone);

}
