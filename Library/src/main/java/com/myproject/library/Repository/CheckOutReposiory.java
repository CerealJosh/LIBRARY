package com.myproject.library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.library.Models.CheckOut;

@Repository
public interface CheckOutReposiory extends JpaRepository<CheckOut, Integer>{
    
}
