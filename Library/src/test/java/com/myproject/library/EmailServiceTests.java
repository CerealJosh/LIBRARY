package com.myproject.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.myproject.library.Models.CheckOut;
import com.myproject.library.Services.EmailService;

@SpringBootTest
public class EmailServiceTests {
    @Autowired
    EmailService emailService;

    @Test
    void emailTest(){
        String username= "onujoshua2001@gmail.com";
        CheckOut checkOut = new CheckOut(5, 7);
        checkOut.setId(44);
        emailService.sendOverdueMail(checkOut);
        emailService.sendReminderMail(username);
    }
}
