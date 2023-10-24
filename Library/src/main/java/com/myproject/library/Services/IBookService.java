package com.myproject.library.Services;

import java.sql.Date;
import java.util.List;

import com.myproject.library.Models.Book;

public interface IBookService {
    List<Book> searchBooks(String title, String isbn, String publisher, Date dateAdded);

    List<Book> getAllBooks();

    void saveBook(Book book);

    void updateBook(int bookId, Book book) throws Exception;

    void returnBook(int bookId) throws Exception;

    void deleteBook(int id);

    Book findBook(int id) throws Exception;
}
