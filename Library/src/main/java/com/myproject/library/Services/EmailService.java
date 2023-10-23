package com.myproject.library.Services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.myproject.library.Models.CheckOut;

@Service
public class EmailService implements IEmailService{

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendReminderMail(String receiver){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kilerkazaam@gmail.com");
        message.setTo(receiver);
        message.setSubject("Book Return");
        message.setText("You have two days left to return your book!");
        javaMailSender.send(message);
    }

    public void sendOverdueMail(CheckOut checkOut, String username){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kilerkazaam@gmail.com");
        message.setTo(username);
        message.setSubject("Book Overdue");
        message.setText(username +" has failed to return book: " + checkOut.getBookId() + " within the 10 day Period, further action is required");;
        javaMailSender.send(message);
    }
}
