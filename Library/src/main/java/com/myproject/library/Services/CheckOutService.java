package com.myproject.library.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.library.Models.CheckOut;
import com.myproject.library.Repository.CheckOutReposiory;

@Service
public class CheckOutService implements ICheckOutService {
    @Autowired
    CheckOutReposiory reposiory;

    @Autowired
    BookService service;

    @Override
    public CheckOut createCheckOut(int bookId, int userId) {
        CheckOut newCheckOut = new CheckOut(bookId, userId);
        try {
            var book = service.findBook(bookId);
            book.setAvailability(false);
            service.updateBook(book.getId(), book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        newCheckOut.setBorrowDate(LocalDate.now());
        reposiory.save(newCheckOut);
        return newCheckOut;
    }

    @Override
    public List<CheckOut> retrieveCheckoutsList() {
        return reposiory.findAll();
    }

    @Override
    public CheckOut retrieveCheckOut(int id) throws Exception {
        var tempCheckOut = reposiory.findById(id);
        if (!tempCheckOut.isPresent()) {
            throw new Exception("Not found!");
        }
        return tempCheckOut.get();
    }

    @Override
    public void eraseCheckOut(int id, int bookId) {
        try {
            service.returnBook(bookId);
            reposiory.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CheckOut> retrieveByExternalId(int id, String idType) throws Exception {
        List<CheckOut> tempCheckOut = new ArrayList<CheckOut>();
        if (idType.equals("user")) {
            tempCheckOut = reposiory.findByUserId(id);
        } else {
            tempCheckOut = reposiory.findByBookId(id);
        }
        if (tempCheckOut.isEmpty()) {
            throw new Exception("Not found!");
        }
        return tempCheckOut;
    }
}
