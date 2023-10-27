package com.myproject.library.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.myproject.library.Models.CheckOut;
import com.myproject.library.Models.User;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private UserService service;
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendReminderMail(String receiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kilerkazaam@gmail.com");
        message.setTo(receiver);
        message.setSubject("Book Return");
        message.setText("You have two days left to return your book!");
        javaMailSender.send(message);
    }

    public void sendOverdueMail(CheckOut checkOut) {
        var adminUsers = service.getUserByRole("Librarian");
        for (User user : adminUsers) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("kilerkazaam@gmail.com");
            message.setTo(user.getEmail());
            message.setSubject("Book Overdue");
            message.setText("User: " +checkOut.getUserId() + " has failed to return book: " + checkOut.getBookId()
                    + " within the 10 day Period, further action is required");
            ;
            javaMailSender.send(message);
        }
        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setFrom("kilerkazaam@gmail.com");
        // message.setTo(username);
        // message.setSubject("Book Overdue");
        // message.setText(username + " has failed to return book: " + checkOut.getBookId()
        //         + " within the 10 day Period, further action is required");
        // ;
        // javaMailSender.send(message);
    }
}
