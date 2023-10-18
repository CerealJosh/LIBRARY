package com.myproject.library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.library.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    
}
