package com.codegym.blog_applications.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.thymeleaf.exceptions.TemplateInputException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            BindException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class,
            NumberFormatException.class
    })
    public String handleBadRequest(Exception ex) {
        ex.printStackTrace();
        return "error/page_400";
    }

    @ExceptionHandler({
            NoResultException.class,
            EntityNotFoundException.class,
            NoHandlerFoundException.class,
    })
    public String handleNotFound(Exception ex) {
        ex.printStackTrace();
        return "error/page_404";
    }

    @ExceptionHandler({
            Exception.class,
            NoResourceFoundException.class,
            TemplateInputException.class
    })
    public String handleServerError(Exception ex) {
        ex.printStackTrace();
        return "error/page_500";
    }
}