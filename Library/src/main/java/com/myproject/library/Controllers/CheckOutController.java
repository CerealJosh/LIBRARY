package com.myproject.library.Controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.myproject.library.Models.CheckOut;
import com.myproject.library.Models.User;
import com.myproject.library.Services.CheckOutService;
import com.myproject.library.Services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CheckOutController {
    @Autowired
    private UserService userService;
    @Autowired
    private CheckOutService service;

    public CheckOutController(CheckOutService service) {
        this.service = service;
    }

    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            User user = userService.loadUserByEmail(email);
            m.addAttribute("user", user);
        }
    }

    @GetMapping("/checkout/{id}")
    public String createCheckOut(@PathVariable int id, Principal p, Model model) {
        if (p != null) {
            String email = p.getName();
            User user = userService.loadUserByEmail(email);
            CheckOut checkOut = service.createCheckOut(id, user.getId());
            model.addAttribute("checkOut", checkOut);
        }
        return "checkout/checkout-success";
    }

    @GetMapping("/checkout/checkouts")
    public String getCheckouts(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String r = auth.getAuthorities().toString();
        if (!r.equals("[Librarian]")) {
            return "NotAuthorized";
        }
        List<CheckOut> checkOuts = service.retrieveCheckoutsList();
        model.addAttribute("checkouts", checkOuts);
        return "checkout/checkout-list";
    }

    @GetMapping("/checkout/checkout/{id}")
    public String getCheckOutDetails(@PathVariable("id") int id, Model model) {
        try {
            CheckOut checkOut = service.retrieveCheckOut(id);
            model.addAttribute("checkout", checkOut);
            return "checkout/checkout-details";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/checkout/checkouts/search")
    public String searchCheckOuts(@RequestParam("id") int id, @RequestParam("type") String type, Model model) {
        try {
            List<CheckOut> checkOuts = service.retrieveByExternalId(id, type);
            model.addAttribute("checkouts", checkOuts);
            return "checkout/checkout-search-results";
        } catch (Exception e) {

        }
        return "error";
    }

    @GetMapping("/checkout/delete/{id}_{bookid}")
    public String deleteCheckOuts(@PathVariable String id, @PathVariable String bookid, HttpServletRequest request) {
        int intId = Integer.parseInt(id);
        int intBookId = Integer.parseInt(bookid);
        System.out.println(bookid);
        service.eraseCheckOut(intId, intBookId);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
