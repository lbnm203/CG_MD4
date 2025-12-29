package com.codegym.personal_calculator.controller;

import com.codegym.personal_calculator.service.ICalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class CalculatorController {
    private final ICalculatorService calculatorService;

    @Autowired
    public CalculatorController(ICalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @RequestMapping("")
    public String showForm(){
        return "form";
    }

    @PostMapping("/result")
    public String calculate(@RequestParam("aValue") double aValue, @RequestParam("operation") String operation, @RequestParam("bValue") double bValue, Model model){

        double result = 0;
        switch (operation){
            case "add":
                result = calculatorService.add(aValue, bValue);
                break;
            case "sub":
                result = calculatorService.sub(aValue, bValue);
                break;
            case "mul":
                result = calculatorService.mul(aValue, bValue);
                break;
            case "div":
                if (bValue == 0) {
                    throw new IllegalArgumentException("Không được phép chia cho 0");
                }
                result = calculatorService.div(aValue, bValue);
                break;
            default:
                throw new IllegalArgumentException("Hãy chọn một toán tử hợp lệ!");
        }
        model.addAttribute("result", result);
        model.addAttribute("aValue", aValue);
        model.addAttribute("bValue", bValue);
        model.addAttribute("operation", operation);
        return "form";
    }
}
