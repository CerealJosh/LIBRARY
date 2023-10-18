package com.myproject.library.Models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CheckOut {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int Id;
    public int UserId;
    public int BookId;
    public Date BorrowDate;
    public Date ReturnDate;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getBookId() {
        return BookId;
    }

    public void setBookId(int bookId) {
        BookId = bookId;
    }

    public Date getBorrowDate() {
        return BorrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        BorrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(Date returnDate) {
        ReturnDate = returnDate;
    }
}
