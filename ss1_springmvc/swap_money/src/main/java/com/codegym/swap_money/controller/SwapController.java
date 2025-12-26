package com.codegym.swap_money.controller;

import com.codegym.swap_money.exception.InvalidAmountException;
import com.codegym.swap_money.service.ISwapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SwapController {

    @Autowired
    private ISwapService swapService;

    @GetMapping("/")
    public String showForm() {
        return "form";
    }

    @PostMapping("/result")
    public String convertCurrency(@RequestParam("rate") double rate,
                                   @RequestParam("usd") double usd,
                                   Model model) throws InvalidAmountException {
        double vnd = swapService.swap(rate, usd);

        model.addAttribute("rate", rate);
        model.addAttribute("usd", usd);
        model.addAttribute("vnd", vnd);

        return "result";
    }

    @ExceptionHandler(InvalidAmountException.class)
    public String handleInvalidAmount(InvalidAmountException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "form";
    }
}
