package com.codegym.personal_calculator.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMissMatch(MethodArgumentTypeMismatchException ex, Model model) {
        model.addAttribute("errorMessage", "Vui lòng nhập số hợp lệ!");
        model.addAttribute("errorType", "input");
        return "form";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorType", "input");
        return "form";
    }
}
