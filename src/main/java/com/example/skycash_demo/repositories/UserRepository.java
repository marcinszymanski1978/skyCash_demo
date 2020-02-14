package com.example.skycash_demo.repositories;

import com.example.skycash_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}