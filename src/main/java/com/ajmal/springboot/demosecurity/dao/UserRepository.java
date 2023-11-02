package com.ajmal.springboot.demosecurity.dao;

import com.ajmal.springboot.demosecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
