package com.myproject.library.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.library.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    User findByEmail(String email);
    List<User> findByRole(String role);
}
