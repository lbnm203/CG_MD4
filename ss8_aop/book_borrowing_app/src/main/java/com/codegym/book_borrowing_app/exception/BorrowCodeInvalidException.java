package com.codegym.book_borrowing_app.exception;

public class BorrowCodeInvalidException extends RuntimeException {
    public BorrowCodeInvalidException(String message) {
        super(message);
    }
}
