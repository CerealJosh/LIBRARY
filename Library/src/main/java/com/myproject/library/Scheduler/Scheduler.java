package com.myproject.library.Scheduler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.myproject.library.Models.CheckOut;
import com.myproject.library.Services.CheckOutService;
import com.myproject.library.Services.EmailService;
import com.myproject.library.Services.UserService;

@Component
public class Scheduler {
    @Autowired
    CheckOutService service;
    @Autowired
    EmailService emailservice;
    @Autowired
    UserService userService;

    List<CheckOut> checkOuts = new ArrayList<CheckOut>();

    @Scheduled(fixedRateString = "PT24H")
    public void retrieve() {
        checkOuts = service.retrieveCheckoutsList();
        for (CheckOut checkOut : checkOuts) {
            int daysGone = LocalDate.from(LocalDate.now()).compareTo(checkOut.borrowDate);
            if (daysGone == 8) {
                reminder(checkOut);
            } else if (daysGone == 10) {
                overdue(checkOut);
            }
        }
    }

    public void reminder(CheckOut checkOut) {
        var user = userService.findbyUserId(checkOut.getUserId());
        emailservice.sendReminderMail(user.getEmail());
    }

    public void overdue(CheckOut checkOut) {
        emailservice.sendOverdueMail(checkOut);
    }
}
