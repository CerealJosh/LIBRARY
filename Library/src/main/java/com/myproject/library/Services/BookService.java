package com.myproject.library.Services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myproject.library.Models.Book;
import com.myproject.library.Repository.BookRepository;

@Service
public class BookService implements IBookService {

    @Autowired
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findBook(int id) throws Exception {
        Optional<Book> searchedBook = bookRepository.findById(id);
        if (!searchedBook.isPresent()) {
            throw new Exception("Book Not Found!!");
        }
        return searchedBook.get();
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    //Modify to accept date as string
    public List<Book> searchBooks(String title, String isbn, String publisher, Date dateAdded) {
        Specification<Book> spec = BookSpecification.searchBooks(title, isbn, publisher, dateAdded);
        return bookRepository.findAll(spec);
    }

    public void saveBook(Book book) {
        if (book == null) {
            return;
        }
        bookRepository.save(book);
    }

    public void updateBook(int bookId, Book book) throws Exception {
        Book tempBook = findBook(bookId);
        if (book == null) {
            throw new Exception("Empty Book!");
        }
        tempBook = book;
        saveBook(tempBook);
        bookRepository.save(book);
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}
