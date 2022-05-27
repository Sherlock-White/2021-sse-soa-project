package com.example.userservice.dao;

import com.example.userservice.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorDAO extends JpaRepository<Administrator,Integer> {
    Administrator findByName(String name);
}
