package com.example.userservice.dao;

import com.example.userservice.entity.Client;
import com.example.userservice.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriverDAO extends JpaRepository<Driver,Integer> {
    @Query(value = "select id from Driver  where name=:username")
    Integer findBywwwname(@Param("username") String username);

    Driver findByName(String username);
}
