package com.myproject.library.Controllers;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.myproject.library.Models.Book;
import com.myproject.library.Models.User;
import com.myproject.library.Services.BookService;
import com.myproject.library.Services.UserService;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            User user = userService.loadUserByEmail(email);
            m.addAttribute("user", user);
        }
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable int id, Model model) throws Exception {
        // var bookId = Integer.parseInt(id);
        model.addAttribute("book", bookService.findBook(id));
        return "book/book";
    }

    @GetMapping("/book/books")
    public String getBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String r = auth.getAuthorities().toString();
        if (!r.equals("[Librarian]")) {
            return "/book/books";
        }
        return "book/books-admin";
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
            @ModelAttribute("publisher") String publisher, @ModelAttribute("dateAdded") String dateAdded, Model model) {
        if (dateAdded.isBlank()) {
            List<Book> searchResults = bookService.searchBooks(title, isbn, publisher, null);
            model.addAttribute("books", searchResults);
            return "/book/search-results";
        }
        List<Book> searchResults = bookService.searchBooks(title, isbn, publisher, Date.valueOf(dateAdded));
        model.addAttribute("books", searchResults);
        return "/book/search-results";
    }

    @GetMapping("/book/addBook")
    public String addBook(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String r = auth.getAuthorities().toString();
        if (!r.equals("[Librarian]")) {
            return "NotAuthorized";
        }
        model.addAttribute("book", new Book());
        return "/book/addBook";
    }

    @PostMapping("/book/addBook")
    public String addBook(@ModelAttribute("book") Book book, Model model, @RequestParam("file") MultipartFile file)
            throws IOException {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String r = auth.getAuthorities().toString();
            if (!r.equals("[Librarian]")) {
                return "NotAuthorized";
            }
            Book newBook = book;
            book.setDateAdded(LocalDate.now());
            if (file.getBytes() != null) {
                newBook.getId();
                bookService.saveCoverPhoto(newBook, file);
            } else {
                bookService.saveBook(newBook);
            }
            model.addAttribute("book", book);
            return "book/book-success";
        } catch (Exception e) {
            return "book/book-error";
        }
    }

    @PutMapping("book/update/{id}")
    String updateBook(@PathVariable String id, @ModelAttribute("Book") Book book) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String r = auth.getAuthorities().toString();
        if (!r.equals("[Librarian]")) {
            return "NotAuthorized";
        }
        var bookId = Integer.parseInt(id);
        bookService.updateBook(bookId, book);
        return "book/" + id;
    }

    @GetMapping("/book/delete/{id}")
    String deleteBook(@PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String r = auth.getAuthorities().toString();
        if (!r.equals("[Librarian]")) {
            return "NotAuthorized";
        }
        int bookId = Integer.parseInt(id);
        bookService.deleteBook(bookId);
        return "redirect:/book/books";
    }

}
