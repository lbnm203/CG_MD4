package com.codegym.swap_money.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SwapController {

    @GetMapping("/")
    public String showForm() {
        return "form";
    }

    @PostMapping("/result")
    public String convertCurrency(@RequestParam("rate") double rate, @RequestParam("usd") double usd, Model model) {
        double vnd = rate * usd;

        model.addAttribute("rate", rate);
        model.addAttribute("usd", usd);
        model.addAttribute("vnd", vnd);

        return "result";
    }
}
