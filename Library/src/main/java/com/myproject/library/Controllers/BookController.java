package com.myproject.library.Controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.myproject.library.Models.Book;
import com.myproject.library.Services.BookService;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable int id, Model model) throws Exception{
        //var bookId = Integer.parseInt(id);
        model.addAttribute("book", bookService.findBook(id));
        return "book/book";
    }
    @GetMapping("/book/books")
    public String getBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "/book/books";
    }

    @GetMapping("/book/search")
    public String searchBooks(Model model) {
        model.addAttribute("title", new String());
        model.addAttribute("isbn", new String());
        model.addAttribute("dateAdded", new Date(0));
        model.addAttribute("publisher", new String());
        return "/book/search";

    }

    @PostMapping("/book/search")
    public String searchBooks(@ModelAttribute("title") String title, @ModelAttribute("isbn") String isbn,
            @ModelAttribute("publisher") String publisher, @ModelAttribute("dateAdded") Date dateAdded, Model model) {

        List<Book> searchResults = bookService.searchBooks(title, isbn, publisher, dateAdded);
        model.addAttribute("books", searchResults);
        return "/book/search-results";
    }

    @GetMapping("/book/addBook")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "/book/addBook";
    }

    @PostMapping("/book/addBook")
    public String addBook(@ModelAttribute("book") Book book, Model model) {
        Book newBook = book;
        bookService.saveBook(newBook);
        return "redirect:/book/books";
    }

    @PutMapping("book/update/{id}")
    String updateBook(@PathVariable String id, @ModelAttribute("Book") Book book) throws Exception{
        var bookId = Integer.parseInt(id);
        bookService.updateBook(bookId, book);
        return "book/" +id;
    }

    @DeleteMapping("/books/delete/{id}")
    String deleteBook(@PathVariable String id) {
        int bookId = Integer.parseInt(id);
        bookService.deleteBook(bookId);
        return "redirect:/book/books";
    }
}
