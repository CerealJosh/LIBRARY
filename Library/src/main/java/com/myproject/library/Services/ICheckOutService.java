package com.myproject.library.Services;

import java.util.List;

import com.myproject.library.Models.CheckOut;

public interface ICheckOutService {
    CheckOut createCheckOut(int bookId, int userId);
    List<CheckOut> retrieveCheckoutsList();
    CheckOut retrieveCheckOut(int id) throws Exception;
    void eraseCheckOut(int id, int bookId);
    List<CheckOut> retrieveByExternalId(int userId, String idType) throws Exception;
}
