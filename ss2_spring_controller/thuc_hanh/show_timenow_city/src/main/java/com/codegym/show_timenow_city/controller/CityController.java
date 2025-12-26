package com.codegym.show_timenow_city.controller;

import com.codegym.check_valid_email.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {
    private final IEmailService emailService;

    @Autowired
    public EmailController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping(value = "/check-valid-email")
    public String check(@RequestParam("email") String email, Model model) {
        boolean isValid = isValidEmail(email);
        if (!isValid) {
            model.addAttribute("message", "Email của bạn không hợp lệ");
            return "home";
        }
        model.addAttribute("email", email);
        return "notification";
    }

    public boolean isValidEmail(String email) {
        return emailService.isValidEmail(email);
    }


}
