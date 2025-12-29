package com.codegym.email_configuration.controller;

import com.codegym.email_configuration.entity.Email;
import com.codegym.email_configuration.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("")
    public ModelAndView showForm() {
        ModelAndView modelAndView = new ModelAndView("form");
        modelAndView.addObject("email", new Email());
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateEmail(@ModelAttribute("email") Email email) {
        ModelAndView modelAndView = new ModelAndView("update");
        modelAndView.addObject("email", email);
        modelAndView.addObject("message", "Email updated successfully!");
        return modelAndView;
    }
}
