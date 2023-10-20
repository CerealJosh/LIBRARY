package com.myproject.library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.myproject.library.Models.Book;


@Repository
public interface BookRepository extends JpaRepository<Book ,Integer>, JpaSpecificationExecutor<Book> {
}
