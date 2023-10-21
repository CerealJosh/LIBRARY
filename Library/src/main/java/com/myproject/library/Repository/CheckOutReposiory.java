package com.myproject.library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.library.Models.CheckOut;
import java.util.List;


@Repository
public interface CheckOutReposiory extends JpaRepository<CheckOut, Integer>{
    List<CheckOut> findByBookId(int bookId);
    List<CheckOut> findByUserId(int userId);
}
