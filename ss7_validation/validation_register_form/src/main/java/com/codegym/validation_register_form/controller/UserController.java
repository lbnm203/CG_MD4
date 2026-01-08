package com.codegym.validation_register_form.controller;

import com.codegym.validation_register_form.entity.User;
import com.codegym.validation_register_form.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "/form";
    }

    @PostMapping("/success")
    public String register(
            @Valid
            @ModelAttribute("user") User user,
            BindingResult bindingResult,
            Model model) {

        if (userService.existsByEmail(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Email đã tồn tại");
        }

        if (bindingResult.hasErrors()) {
            return "/form";
        }
        userService.save(user);
        model.addAttribute("user", user);
        return "result";
    }
}
