package com.codegym.book_borrowing_app.service;

import com.codegym.book_borrowing_app.entity.Book;

import java.util.List;

public interface IBookService extends IService<Book> {
    String borrowBook(Long id);
    void returnBook(String borrowCode);
//    Boolean update(Book book);

}
