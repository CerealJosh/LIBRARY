package com.myproject.library.Services;

import java.sql.Date;
import java.util.List;

import com.myproject.library.Models.Book;

public interface IBookService {
    List<Book> searchBooks(String title, String isbn, String publisher, Date dateAdded);
}
