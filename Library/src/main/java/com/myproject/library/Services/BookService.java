package com.myproject.library.Services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myproject.library.Exceptions.BookNotFoundException;
import com.myproject.library.Models.Book;
import com.myproject.library.Repository.BookRepository;

@Service
public class BookService implements IBookService {

    @Autowired
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findBook(int id) throws BookNotFoundException {
        Optional<Book> searchedBook = bookRepository.findById(id);
        if (!searchedBook.isPresent()) {
            throw new BookNotFoundException("Book Not Found!!");
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
    }
    
    public void returnBook(int bookId) throws Exception {
        Book tempBook = findBook(bookId);
        tempBook.setAvailability(true);
        saveBook(tempBook);
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    public void saveCoverPhoto(Book book, MultipartFile file) throws IOException {
        String fileName = book.getId() + "-" + file.getOriginalFilename();
        Path uploadPath = Paths.get("src/main/resources/static/uploads/books/"+book.getIsbn());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        }
        book.setCoverPhoto(fileName);
        bookRepository.save(book);
    }
}
