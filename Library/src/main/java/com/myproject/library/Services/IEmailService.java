package com.myproject.library.Services;

import com.myproject.library.Models.CheckOut;

public interface IEmailService {
    void sendReminderMail(String receiver);
    void sendOverdueMail(CheckOut checkOut, String username);
}
